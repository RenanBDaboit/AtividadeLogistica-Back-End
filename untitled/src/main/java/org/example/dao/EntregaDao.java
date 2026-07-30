package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.db.ConnectionFactory;
import org.example.enums.StatusEntrega;
import org.example.model.Entrega;

public class EntregaDao {
    
    public void gerarEntrega(Entrega entrega) throws SQLException {
        String command = """
                    INSERT INTO entregas (
                        pedido_id, motorista_id, data_saida, data_entrega, status_entrega
                    ) VALUES (
                        ?,?,?,?,?
                    )
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {

            stmt.setInt(1, entrega.getPedido_id());
            stmt.setInt(2, entrega.getMotorista_id());
            stmt.setDate(3, entrega.getData_saida());
            stmt.setDate(4, entrega.getData_entrega());
            stmt.setString(5, entrega.getStatus().toString());

            stmt.executeUpdate();
            
        }
    }

    public void atualizarStatusEntrega(int id, StatusEntrega status) throws SQLException {
        String command = """
                    UPDATE 
                        entregas 
                    SET 
                        status = ?
                    WHERE 
                        id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            
            stmt.setString(1, status.toString());
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } 
    }

    public ArrayList<String> listarEntregasCLienteMotorista() throws SQLException{
        String command = """
                    SELECT 
                        c.nome, m.nome, e.data_saida, e.data_entrega, e.status
                    FROM
                        entregas e
                    JOIN
                        motoristas m
                        ON
                            e.motorista_id = m.id
                    JOIN
                        pedidos p
                        ON
                            e.pedido_id = p.id
                    JOIN
                        clientes c
                        ON
                            p.cliente_id = c.id
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command);
             ResultSet rs = stmt.executeQuery()) {
            
            ArrayList<String> res = new ArrayList<>();

            StringBuilder linha = new StringBuilder();

            while (rs.next()) {
                String nome_cliente = rs.getString("c.nome");
                String nome_motorista = rs.getString("m.nome");
                String data_saida = rs.getString("e.data_saida");
                String data_entrega = rs.getString("e.data_entrega");
                String status = rs.getString("status");

                linha.append(nome_cliente).append(" | ").append(nome_motorista).append(" | ")
                    .append(data_saida).append(" | ").append(data_entrega).append(" | ").append(status);

                res.add(linha.toString());
            }

            return res;
        }
    }

    public ArrayList<String> entregasAtrasadasPorCidade(String cidade) throws SQLException {
        String command = """
                    SELECT 
                        e.id, e.status, c.cidade
                    FROM
                        entregas e
                    JOIN
                        pedidos p
                        ON
                            e.pedido_id = p.id
                    JOIN
                        clientes c
                        ON
                            p.cliente_id = c.id
                    WHERE
                        c.cidade = ? 
                        AND
                        e.status = 'ATRASADA'
                """;

            try (Connection conn = ConnectionFactory.conectar();
                 PreparedStatement stmt = conn.prepareStatement(command)) {
                
                stmt.setString(1, cidade);

                ResultSet rs = stmt.executeQuery();

                ArrayList<String> res = new ArrayList<>();

                StringBuilder linha = new StringBuilder();
    
                while (rs.next()) {
                    String entrega_id = rs.getString("e.id");
                    String status = rs.getString("e.status");
                    String cidade_retorno = rs.getString("c.cidade");
    
                    linha.append(entrega_id).append(" | ").append(status).append(" | ").append(cidade_retorno);
    
                    res.add(linha.toString());
                }
    
                return res;

            }
    }
}
