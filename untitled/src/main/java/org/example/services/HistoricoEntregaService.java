package org.example.services;

import org.example.model.HistoricoEntrega;
import org.example.dao.HistoricoEntregaDao;

import java.sql.Date;
import java.sql.SQLException;

public class HistoricoEntregaService {
    
    public void registrarEventoEntrega(int entrega_id, Date data_evento, String descricao) throws RuntimeException {
        validarEntregaId(entrega_id);
        validarDescricao(descricao);

        var historicoEntregaDao = new HistoricoEntregaDao();
        
        var historicoEntrega = new HistoricoEntrega(entrega_id, data_evento, descricao);

        try {
            historicoEntregaDao.registrarEventoEntrega(historicoEntrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void validarEntregaId(int entrega_id) throws RuntimeException{
        if (entrega_id <= 0) {
            throw new RuntimeException("Erro: ID da entrega inválido");
        }
    }

    public void validarDescricao(String descricao) throws RuntimeException{
        if (descricao.isBlank()) {
            throw new RuntimeException("Erro: Descrição em branco");
        }
    }
}
