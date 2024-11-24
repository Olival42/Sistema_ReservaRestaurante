/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reserva_restaurante;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Olivalzin
 */
public class NUM_PESSOAS_GERENCIADOR {

    private static NUM_PESSOAS_GERENCIADOR instancia;

    private final int NUMERO_DE_PESSOAS_POR_HORARIO = 20;
    private Map<LocalDate, Map<String, Integer>> reservasPorDataEHorario;

    private NUM_PESSOAS_GERENCIADOR() {
        reservasPorDataEHorario = new HashMap<>();
    }

    public static NUM_PESSOAS_GERENCIADOR getInstancia() {
        if (instancia == null) {
            instancia = new NUM_PESSOAS_GERENCIADOR();
        }
        return instancia;
    }

    public boolean adicionarNumPessoas(LocalDate data, String horario, int numPessoas) {
        reservasPorDataEHorario.putIfAbsent(data, new HashMap<>());
        Map<String, Integer> horarios = reservasPorDataEHorario.get(data);
        int pessoasAtuais = horarios.getOrDefault(horario, 0);

        if (pessoasAtuais + numPessoas > NUMERO_DE_PESSOAS_POR_HORARIO) {
            return false;
        } else if (pessoasAtuais + numPessoas <= NUMERO_DE_PESSOAS_POR_HORARIO) {
            horarios.put(horario, numPessoas + pessoasAtuais);
        }
        return true;
    }

    public void removerNumPessoas(LocalDate data, String horario, int numPessoas) {
        if (reservasPorDataEHorario.containsKey(data)) {
            Map<String, Integer> horarios = reservasPorDataEHorario.get(data);
            int pessoasAtuais = horarios.getOrDefault(horario, 0);

            int remocaoPessoas = pessoasAtuais - numPessoas;
            if (remocaoPessoas >= 0) {
                horarios.put(horario, remocaoPessoas);
            } else {
                horarios.remove(horario);
            }
        }
    }
}
