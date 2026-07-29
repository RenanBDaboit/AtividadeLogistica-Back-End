package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public ArrayList<String> pedidosPendentesPorEstado(String estado) throws SQLException{
        String command = """
                    SELECT
                        p.id, p.status, c.estado
                    FROM 
                        pedidos p
                    JOIN
                        clientes c
                        ON
                            c.id = p.cliente_id
                    WHERE
                        c.estado = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            
            stmt.setString(1, estado);

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> res = new ArrayList<>();

            StringBuilder linha = new StringBuilder();

            while (rs.next()) {
                String pedido_id = rs.getString("p.id");
                String status = rs.getString("p.status");
                String estado_retornado = rs.getString("c.estado");

                linha.append(pedido_id).append(" | ").append(status).append(" | ").append(estado_retornado);

                res.add(linha.toString());
            }

            return res;
        } 
    }
}
