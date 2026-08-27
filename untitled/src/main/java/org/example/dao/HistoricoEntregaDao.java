package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.example.db.ConnectionFactory;
import org.example.model.HistoricoEntrega;

public class HistoricoEntregaDao {
    
    public void registrarEventoEntrega(HistoricoEntrega historicoEntrega) throws SQLException {
        String command = """
                    INSERT INTO HistoricoEntrega (
                        entrega_id, data_evento, descricao
                    ) VALUES (
                        ?,?,?
                    )
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            
            stmt.setInt(1, historicoEntrega.getEntrega_id());
            stmt.setDate(2, historicoEntrega.getData_evento());
            stmt.setString(3, historicoEntrega.getDescricao());

            stmt.executeUpdate();
        } 
    }
}
