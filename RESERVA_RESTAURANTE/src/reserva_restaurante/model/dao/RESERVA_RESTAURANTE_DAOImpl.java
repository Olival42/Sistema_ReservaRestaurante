/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reserva_restaurante.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import reserva_restaurante.model.RESERVA;

/**
 *
 * @author Olivalzin
 */
public class RESERVA_RESTAURANTE_DAOImpl implements RESERVA_RESTAURANTE_DAO {

    private static RESERVA_RESTAURANTE_DAOImpl instance;
    private List<RESERVA> reservas;

    private RESERVA_RESTAURANTE_DAOImpl() {
        reservas = new ArrayList<>();
    }

    public static RESERVA_RESTAURANTE_DAOImpl getInstance() {
        if (instance == null) {
            instance = new RESERVA_RESTAURANTE_DAOImpl();
        }
        return instance;
    }

    @Override
    public void salvar(RESERVA reserva) {
        reservas.add(reserva);
    }

    @Override
    public void atualizar(RESERVA reserva) {
        Optional<RESERVA> reservaExixtente = reservas.stream()
                .filter(r -> r.getId() == reserva.getId())
                .findFirst();

        reservaExixtente.ifPresent(r -> {
            r.setTitulo(reserva.getTitulo());
            r.setNome(reserva.getNome());
            r.setDdd(reserva.getDdd());
            r.setTelefone(reserva.getTelefone());
            r.setEmail(reserva.getEmail());
            r.setNumPessoas(reserva.getNumPessoas());
            r.setData(reserva.getData());
            r.setHorario(reserva.getHorario());
            r.setComentario(reserva.getComentario());
        });
    }

    @Override
    public List<RESERVA> getTodasReservas() {
        return reservas;
    }

    @Override
    public void removerReserva(int id) {
        reservas.removeIf(reserva -> reserva.getId() == id);
    }

    public List<RESERVA> buscarPorDataEHorario(String data, String horario) {
        return reservas.stream()
                .filter(reserva -> reserva.getData().equals(data)
                && (horario == null || reserva.getHorario().equals(horario)))
                .collect(Collectors.toList());

    }
}
