/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reserva_restaurante.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Olivalzin
 */
public class RESERVA {

    private static int contador = 1;

    private int id;
    private String titulo;
    private String nome;
    private String ddd;
    private String telefone;
    private String email;
    private String numPessoas;
    private String data;
    private String horario;
    private String comentario;

    private BooleanProperty selected;

    public RESERVA(String titulo, String nome, String ddd, String telefone, String email,
            String numPessoas, String data, String horario, String comentario) {
        this.id = contador++;
        this.titulo = titulo;
        this.nome = nome;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
        this.numPessoas = numPessoas;
        this.data = data;
        this.horario = horario;
        this.comentario = comentario;
        this.selected = new SimpleBooleanProperty(false);
    }

    // Getter e Setter para id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter para titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter e Setter para nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter para ddd
    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    // Getter e Setter para telefone
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Getter e Setter para email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter e Setter para numPessoas
    public String getNumPessoas() {
        return numPessoas;
    }

    public void setNumPessoas(String numPessoas) {
        this.numPessoas = numPessoas;
    }

    // Getter e Setter para data
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // Getter e Setter para horario
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    // Getter e Setter para comentario
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    // Método para acessar a propriedade selected
    public BooleanProperty selectedProperty() {
        return selected;
    }

    // Métodos para obter e definir o valor da propriedade
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    // Método para retornar o telefone completo (DDD + número)
    public String getTelefoneCompleto() {
        return "(" + ddd + ") " + telefone;
    }

    public String getDataNoFormatoBR() {
        LocalDate localDate = LocalDate.parse(data);

        // Formata a data para o padrão brasileiro (dd/MM/yyyy)
        DateTimeFormatter formataSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDate.format(formataSaida);
    }
}
