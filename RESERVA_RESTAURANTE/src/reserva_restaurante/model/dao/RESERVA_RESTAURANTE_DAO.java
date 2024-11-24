/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package reserva_restaurante.model.dao;

import java.util.List;
import reserva_restaurante.model.RESERVA;

/**
 *
 * @author Olivalzin
 */
public interface RESERVA_RESTAURANTE_DAO {
    public void salvar(RESERVA reserva);

    public void atualizar(RESERVA reserva);
    
    public List<RESERVA> getTodasReservas();
    
    public void removerReserva(int id);
    
    public List<RESERVA> buscarPorDataEHorario(String data, String horario);
}
