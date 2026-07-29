package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
