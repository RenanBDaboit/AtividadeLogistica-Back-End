package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.example.db.ConnectionFactory;
import org.example.model.Pedido;

public class PedidoDao {
    
    public void criarPedido(Pedido pedido) throws SQLException {
        String command = """
                    INSERT INTO pedidos (
                        cliente_id, data_pedido, volume_m3, peso_kg, status_pedido
                    ) VALUES (
                        ?,?,?,?,?
                    )
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            
            stmt.setInt(1, pedido.getCliente_id());
            stmt.setDate(2, pedido.getData_pedido());
            stmt.setDouble(3, pedido.getVolume_m3());
            stmt.setDouble(4, pedido.getPeso_kg());
            stmt.setString(5, pedido.getStatus().toString());

            stmt.executeUpdate();
        } 
    }
}
