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
                    INSERT INTO Pedido (
                        cliente_id, data_pedido, volume_m3, peso_kg, status
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
                        p.id, p.status_pedido, c.estado
                    FROM 
                        Pedido p
                    JOIN
                        Cliente c
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

            while (rs.next()) {

                StringBuilder linha = new StringBuilder();

                String pedido_id = rs.getString("p.id");
                String status = rs.getString("p.status_pedido");
                String estado_retornado = rs.getString("c.estado");

                linha.append(pedido_id).append(" | ").append(status).append(" | ").append(estado_retornado);

                res.add(linha.toString());
            }

            return res;
        } 
    }

    public ArrayList<String> buscarPedidoPorCpfCnpj(String cpf_cnpj) throws SQLException {
        String command = """
                    SELECT
                        p.id, c.nome, c.cpf, p.volume_m3, p.peso_kg
                    FROM
                        Pedido p 
                    JOIN
                        Cliente c
                        ON
                            c.id = p.cliente_id
                    WHERE
                        c.cpf LIKE ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            
            stmt.setString(1, cpf_cnpj);

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> res = new ArrayList<>();

            while (rs.next()) {

                StringBuilder linha = new StringBuilder();

                String pedido_id = rs.getString("p.id");
                String nome_cliente = rs.getString("c.nome");
                String cpf_cnpj_digitado = rs.getString("c.cpf");
                String volume_m3 = rs.getString("p.volume_m3");
                String peso_kg = rs.getString("p.peso_kg");

                linha.append(pedido_id).append(" | ").append(nome_cliente).append(" | ")
                    .append(cpf_cnpj_digitado).append(" | ").append(volume_m3).append(" | ").append(peso_kg);

                res.add(linha.toString());
            }

            return res;
        }
    }

    public void cancelarPedido(int id) throws SQLException{
        String command = """
                    UPDATE
                        Pedido
                    SET
                        status_pedido = 'CANCELADO'
                    WHERE
                        id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } 
    }
}
