package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDao {

    public void cadastrarCliente(Cliente cliente) throws SQLException {
        String command = """
                    INSERT INTO clientes (
                        nome, cpf_cnpj, endereco, cidade, estado
                    ) values (
                        ?,?,?,?,?
                    )
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf_cnpj());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEstado());

            stmt.executeUpdate();
        }
    }

    public ArrayList<String> clientesComMaisVolume() throws SQLException{
        String command = """
                    SELECT
                        c.nome, SUM(p.volume_m3)
                    FROM
                        clientes c
                    JOIN
                        pedidos p
                        ON
                            c.id = p.cliente_id
                    GROUP BY
                        c.nome
                    ORDER BY
                        SUM(p.volume_m3) DESC
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command);
             ResultSet rs = stmt.executeQuery()) {
            
                ArrayList<String> res = new ArrayList<>();

                StringBuilder linha = new StringBuilder();
    
                while (rs.next()) {
                    String nome_cliente = rs.getString("c.nome");
                    String soma_volumes = rs.getString("SUM(p.volume_m3)");
    
                    linha.append(nome_cliente).append(" | ").append(soma_volumes);
    
                    res.add(linha.toString());
                }
    
                return res;

        }
    }
}
