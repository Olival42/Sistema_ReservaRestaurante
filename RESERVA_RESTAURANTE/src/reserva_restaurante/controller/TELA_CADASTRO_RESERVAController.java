/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package reserva_restaurante;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import reserva_restaurante.model.RESERVA;
import reserva_restaurante.model.dao.RESERVA_RESTAURANTE_DAOImpl;

/**
 * FXML Controller class
 *
 * @author Olivalzin
 */
public class TELA_CADASTRO_RESERVAController implements Initializable {

    @FXML
    private Button Bttn_Cancelar;
    @FXML
    private TextField TextField_Email;
    @FXML
    private CheckBox BoxTick_Novidades;
    @FXML
    private TextArea Box_Comentario;
    @FXML
    private TextField TextField_Telefone;
    @FXML
    private DatePicker DatePicker_tela1;
    @FXML
    private ChoiceBox<String> ChoiceBox_Horario;
    @FXML
    private TextField TextField_Nome;
    @FXML
    private ChoiceBox<String> ChoiceBox_Titulo;
    @FXML
    private TextField TextField_DDD;
    @FXML
    private TextField TextField_NumP;
    @FXML
    private Button Bttn_Reservar;
    @FXML
    private Button Bttn_Confirmar;

    private String estadoFormulario = "inicial";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Seta o DatePicker para o dia atual
        DatePicker_tela1.setValue(LocalDate.now());

        // Atribui itens à ChoiceBox
        ChoiceBox_Horario.getItems().addAll("19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00");

        ChoiceBox_Titulo.getItems().addAll("Mr", "Mrs", "Miss", "Ms", "None");
        ChoiceBox_Titulo.getSelectionModel().select("None");

        DatePicker_tela1.setDayCellFactory(data -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3");
                }
            }
        });

        // Adiciona listeners para monitorar quando o usuário digitar
        adicionarListenersCamposTexto();

        // Atualiza o estado dos botões
        atualizarBotoes_tela1();
    }

    private void limpaCampos() {
        TextField_Telefone.clear();
        TextField_Nome.clear();
        TextField_DDD.clear();
        Box_Comentario.clear();
        TextField_Email.clear();
        TextField_NumP.clear();
        // Reseta a ChoiceBox do título
        ChoiceBox_Titulo.getSelectionModel().clearSelection();
        // Reseta a CheckBox
        BoxTick_Novidades.setSelected(false);
        // Reseta a ChoiceBox do horário
        ChoiceBox_Horario.getSelectionModel().clearSelection();
        // Reseta o DatePicker para o dia atual
        DatePicker_tela1.setValue(LocalDate.now());
        ChoiceBox_Titulo.getSelectionModel().select("None");
    }

    // Adiciona listeners para monitorar quando o usuário digitar
    private void adicionarListenersCamposTexto() {
        ChangeListener<String> listener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            verificarCamposPreenchidos();
        };

        TextField_Email.textProperty().addListener(listener);
        BoxTick_Novidades.selectedProperty().addListener((observable, oldValue, newValue) -> {
            verificarCamposPreenchidos();
        });
        Box_Comentario.textProperty().addListener(listener);
        TextField_Telefone.textProperty().addListener(listener);
        DatePicker_tela1.valueProperty().addListener((observable, oldValue, newValue) -> {
            verificarCamposPreenchidos();
        });
        ChoiceBox_Horario.valueProperty().addListener(listener);
        TextField_Nome.textProperty().addListener(listener);
        ChoiceBox_Titulo.valueProperty().addListener(listener);
        TextField_DDD.textProperty().addListener(listener);
        TextField_NumP.textProperty().addListener(listener);
    }

    private boolean todosCamposObrigatoriosPreenchidos() {
        return !TextField_Email.getText().isEmpty()
                && !TextField_Telefone.getText().isEmpty()
                && !TextField_Nome.getText().isEmpty()
                && !TextField_DDD.getText().isEmpty()
                && !TextField_NumP.getText().isEmpty()
                && DatePicker_tela1.getValue() != null // Verifica se uma data foi selecionada
                && ChoiceBox_Horario.getValue() != null; // Verifica se uma opção foi selecionada
    }

    private void verificarCamposPreenchidos() {
        boolean algumCampoPreenchido = !TextField_Email.getText().isEmpty()
                || BoxTick_Novidades.isSelected()
                || !Box_Comentario.getText().isEmpty()
                || !TextField_Telefone.getText().isEmpty()
                || DatePicker_tela1.getValue() != null
                || ChoiceBox_Horario.getValue() != null
                || !TextField_Nome.getText().isEmpty()
                || ChoiceBox_Titulo.getValue() != null
                || !TextField_DDD.getText().isEmpty()
                || !TextField_NumP.getText().isEmpty();

        if (todosCamposObrigatoriosPreenchidos()) {
            estadoFormulario = "editando";
        } else if (algumCampoPreenchido) {
            estadoFormulario = "a editar";
        } else {
            estadoFormulario = "inicial";
        }

        atualizarBotoes_tela1();
    }

    public boolean isNomeValido(String nome) {
        // Expressão regular para letras maiúsculas, minúsculas e espaços
        return nome.matches("[A-Za-zÀ-ÖØ-öø-ÿ ]+");
    }

    private boolean eValidoEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean eValidoDDD(String ddd) {
        return ddd.matches("^(?!00\\b)[0-9]{2}$");
    }

    private boolean eValidoTelefone(String telefone) {
        // Remove "-" caso tenha
        telefone = telefone.replace("-", "");
        // Verifica se o telefone contém apenas dígitos e tem 8 ou 9 caracteres
        return telefone.matches("\\d{8,9}");
    }

    private boolean eValidoNumPessoas(String numPessoas) {
        if (numPessoas.matches("\\d{1,2}")) {
            int valor = Integer.parseInt(numPessoas);
            if (valor >= 1 && valor <= 20) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void handleReservar() {
        List<String> mensagensDeErro = new ArrayList<>();

        String titulo = ChoiceBox_Titulo.getValue();
        String nome = TextField_Nome.getText();
        String ddd = TextField_DDD.getText().trim();
        String telefone = TextField_Telefone.getText().trim();
        String email = TextField_Email.getText();
        String numPessoasStr = TextField_NumP.getText().trim();
        LocalDate data = DatePicker_tela1.getValue();
        String horario = ChoiceBox_Horario.getValue();
        String comentario = Box_Comentario.getText();

        // Conversão de String para int para o número de pessoas
        int numPessoas = Integer.parseInt(numPessoasStr);

        if (!todosCamposObrigatoriosPreenchidos()) {
            mensagensDeErro.add("Por favor, preencha todos os campos obrigatórios antes de salvar.");
        }
        // Validação do Nome
        if (!isNomeValido(nome)) {
            mensagensDeErro.add("Por favor, preencha o campo NOME apenas com letras.");
        }
        // Validação do email
        if (!eValidoEmail(email)) {
            mensagensDeErro.add("Por favor, preencha o campo EMAIL com um email válido.");
        }
        // Validação do DDD
        if (!eValidoDDD(ddd)) {
            mensagensDeErro.add("Por favor, preencha o campo DDD com um DDD válido (2 dígitos).");
        }
        // Validação do telefone
        if (!eValidoTelefone(telefone)) {
            mensagensDeErro.add("Por favor, preencha o campo TELEFONE com um número válido (8 ou 9 dígitos).");
        }
        // Validação do número de pessoas
        if (!eValidoNumPessoas(numPessoasStr)) {
            mensagensDeErro.add("Por favor, preencha o campo NÚMERO DE PESSOAS com um número válido (De 1 a 20).");
        }
        // Verifica e adiciona reserva
        if (!NUM_PESSOAS_GERENCIADOR.getInstancia().adicionarNumPessoas(data, horario, numPessoas)) {
            mensagensDeErro.add("O número total de pessoas excede o limite para este horário. Tente outro horário ou data.");
        }

        if (!mensagensDeErro.isEmpty()) {
            Alert alerta = new Alert(AlertType.WARNING);
            alerta.setTitle("Erros de Validação");
            alerta.setHeaderText(null);
            alerta.setContentText(String.join("\n\n", mensagensDeErro)); // Juntar todas as mensagens com quebra de linha
            alerta.show();
            return;
        }

        RESERVA novaReserva = new RESERVA(titulo.toString(), nome, ddd, telefone, email, numPessoasStr, data.toString(), horario.toString(), comentario);

        if (novaReserva != null) {
            try {
                RESERVA_RESTAURANTE_DAOImpl.getInstance().salvar(novaReserva);

                estadoFormulario = "inicial";
                limpaCampos();

                atualizarBotoes_tela1();

                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Reserva salva");
                alerta.setHeaderText(null);
                alerta.setContentText("Reserva salva com sucesso!");
                alerta.show();
            } catch (Exception e) {
                e.printStackTrace(); // Log do erro no console
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Erro ao salvar");
                alerta.setHeaderText(null);
                alerta.setContentText("Ocorreu um erro ao salvar a reserva.");
                alerta.show();
            }
        }
    }

    @FXML
    private void handleCancelar() {
        // Lógica para reverter alterações, se necessário
        limpaCampos();
        estadoFormulario = "inicial";
        atualizarBotoes_tela1();
    }

    @FXML
    private void handleConfirmar() {
        try {
            // Carrega o layout da segunda tela
            Parent root = FXMLLoader.load(getClass().getResource("/reserva_restaurante/view/TELA_CONFIRMACAO_RESERVA.fxml"));
            Stage telaAtual = (Stage) Bttn_Confirmar.getScene().getWindow();
            telaAtual.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarBotoes_tela1() {
        switch (estadoFormulario) {
            case "inicial":
                Bttn_Reservar.setDisable(true);
                Bttn_Cancelar.setDisable(true);
                break;
            case "a editar":
                Bttn_Reservar.setDisable(true);
                Bttn_Cancelar.setDisable(false);
                break;
            case "editando":
                Bttn_Reservar.setDisable(false);
                Bttn_Cancelar.setDisable(false);
                break;
        }
    }
}
