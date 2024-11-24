/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package reserva_restaurante;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import reserva_restaurante.model.RESERVA;
import reserva_restaurante.model.dao.RESERVA_RESTAURANTE_DAOImpl;

/**
 * FXML Controller class
 *
 * @author Olivalzin
 */
public class TELA_CONFIRMACAO_RESERVAController implements Initializable {

    @FXML
    private Button Bttn_Excluir_Tela2;
    @FXML
    private DatePicker Data_Tela2;
    @FXML
    private Button Bttn_Confirmar_Tela2;
    @FXML
    private ChoiceBox<String> Horario_Tela2;
    @FXML
    private Button Bttn_Voltar_tela2;
    @FXML
    private Button Bttn_Pesquisar_tela2;

    //Tabela com as informações das reservas
    @FXML
    private TableColumn<RESERVA, String> colNome;
    @FXML
    private TableColumn<RESERVA, String> colTitulo;
    @FXML
    private TableColumn<RESERVA, String> colHorario;
    @FXML
    private TableColumn<RESERVA, String> colData;
    @FXML
    private TableColumn<RESERVA, String> colNumPessoas;
    @FXML
    private TableColumn<RESERVA, String> colTelefone;
    @FXML
    private TableColumn<RESERVA, String> colEmail;
    @FXML
    private TableColumn<RESERVA, String> colComentario;
    @FXML
    private TableColumn<RESERVA, Boolean> colSelecionar;
    @FXML
    private TableView<RESERVA> tableViewReservas;

    private String estadoFormulario = "inicial";
    private ObservableList<RESERVA> reservas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservas = FXCollections.observableArrayList(); // Inicializa a lista observável

        // Atribui itens à ChoiceBox
        Horario_Tela2.getItems().addAll("19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00");

        // Customiza o DatePicker para desabilitar dias anteriores ao dia de hoje
        Data_Tela2.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;");
                }
            }
        });

        Bttn_Pesquisar_tela2.setDisable(true);

        Bttn_Pesquisar_tela2.setOnAction(event -> {
            pesquisa();
            limpaCampos();
        });

        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        colData.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getDataNoFormatoBR()));
        colNumPessoas.setCellValueFactory(new PropertyValueFactory<>("numPessoas"));
        colTelefone.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getTelefoneCompleto()));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        colSelecionar.setCellValueFactory(new PropertyValueFactory<>("selected"));

        colSelecionar.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionar));

        setReservas(RESERVA_RESTAURANTE_DAOImpl.getInstance().getTodasReservas());

        // A tabela é ligada à lista de reservas
        tableViewReservas.setItems(reservas);
        // Atualizar a tabela e definir a editabilidade da coluna
        tableViewReservas.setEditable(true);

        adicionarListenersCamposTexto();

        atualizarBotoes_tela2();
    }

    private void limpaCampos() {
        // Reseta a data
        Data_Tela2.setValue(null);
        // Reseta a ChoiceBox
        Horario_Tela2.getSelectionModel().clearSelection();
    }

    public void setReservas(List<RESERVA> reservas) {
        this.reservas.setAll(reservas); // Atualiza a lista observável com as novas reservas
    }

    private void adicionarListenersCamposTexto() {
        Data_Tela2.valueProperty().addListener((observable, oldValue, newValue) -> verificarTodosCamposPreenchidos());
    }

    private void verificarTodosCamposPreenchidos() {
        // Verifica se ambos os campos estão preenchidos
        boolean camposPreenchidos = Data_Tela2.getValue() != null;
        // Habilita ou desabilita o botão Pesquisar
        Bttn_Pesquisar_tela2.setDisable(!camposPreenchidos);
    }

    private void pesquisa() {
        LocalDate data = Data_Tela2.getValue();
        String horario = Horario_Tela2.getValue();

        List<RESERVA> reservasEncontradas = (List<RESERVA>) RESERVA_RESTAURANTE_DAOImpl.getInstance().buscarPorDataEHorario(data.toString(), horario);

        if (reservasEncontradas.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Nenhuma Reserva Encontrada");
            alerta.setHeaderText(null);
            alerta.setContentText("Nenhuma reserva foi encontrada com essa informação.");
            alerta.show();
            return;
        } else {
            // Exibir as reservas encontradas (ajuste conforme a estrutura da tela de consulta)
            setReservas(reservasEncontradas);
        }
        tableViewReservas.refresh();
    }

    @FXML
    private void handleExcluir() {
        // Filtra as reservas selecionadas onde 'selected' é true
        List<RESERVA> reservasSelecionadas = reservas.stream()
                .filter(RESERVA::isSelected)
                .collect(Collectors.toList());  // Coleta todas as reservas selecionadas

        // Se nenhuma reserva foi selecionada, mostra um alerta
        if (reservasSelecionadas.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Nenhuma Reserva Selecionada");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecione pelo menos uma reserva para excluir.");
            alerta.show();
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação de Exclusão");
        alerta.setHeaderText(null);
        alerta.setContentText("Tem certeza que deseja excluir?");
        alerta.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alerta.showAndWait();

        if (alerta.getResult() == ButtonType.YES) {
            for (RESERVA reserva : reservasSelecionadas) {
                LocalDate data = LocalDate.parse(reserva.getData());
                String horario = reserva.getHorario();
                int numPessoas = Integer.parseInt(reserva.getNumPessoas());

                NUM_PESSOAS_GERENCIADOR.getInstancia().removerNumPessoas(data, horario, numPessoas);
                RESERVA_RESTAURANTE_DAOImpl.getInstance().removerReserva(reserva.getId());
            }
            // Processa a exclusão das reservas
            reservas.removeAll(reservasSelecionadas);  // Remove todas as reservas selecionadas da lista

            // Exibe o alerta de sucesso
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Remoção de Reserva");
            alerta.setHeaderText(null);
            alerta.setContentText(reservasSelecionadas.size() + " reserva(s) excluída(s).");
            alerta.show();
        } else {
            return;
        }

        tableViewReservas.refresh();

        estadoFormulario = "inicial";
        limpaCampos();
        atualizarBotoes_tela2();
    }

    @FXML
    private void handleConfirmar() {
        // Filtra as reservas selecionadas onde 'selected' é true
        List<RESERVA> reservasSelecionadas = reservas.stream()
                .filter(RESERVA::isSelected)
                .collect(Collectors.toList());  // Coleta todas as reservas selecionadas

        // Se nenhuma reserva foi selecionada, mostra um alerta
        if (reservasSelecionadas.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Nenhuma Reserva Selecionada");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecione pelo menos uma reserva para confirmar.");
            alerta.show();
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação");
        alerta.setHeaderText(null);
        alerta.setContentText("Tem certeza que deseja confirmar?");
        alerta.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alerta.showAndWait();

        if (alerta.getResult() == ButtonType.YES) {
            for (RESERVA reserva : reservasSelecionadas) {
                RESERVA_RESTAURANTE_DAOImpl.getInstance().removerReserva(reserva.getId());
            }
            reservas.removeAll(reservasSelecionadas);  // Remove todas as reservas selecionadas da lista

            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Reservas salvas");
            alerta.setHeaderText(null);
            alerta.setContentText(reservasSelecionadas.size() + " reserva(s) confirmada(s).");
            alerta.show();
        } else {
            return;
        }

        tableViewReservas.refresh();

        estadoFormulario = "inicial";
        atualizarBotoes_tela2();
        limpaCampos();
    }

    @FXML
    private void handleVoltar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/reserva_restaurante/view/TELA_CADASTRO_RESERVA.fxml"));
            Stage telaAtual = (Stage) Bttn_Voltar_tela2.getScene().getWindow();
            telaAtual.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarBotoes_tela2() {
        switch (estadoFormulario) {
            case "inicial":
                Bttn_Confirmar_Tela2.setDisable(false);
                Bttn_Excluir_Tela2.setDisable(false);
                break;
            case "editando":
                Bttn_Confirmar_Tela2.setDisable(false);
                Bttn_Excluir_Tela2.setDisable(false);
                break;
        }
    }

}
