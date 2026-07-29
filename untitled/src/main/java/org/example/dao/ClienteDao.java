package org.example.dao;

import org.example.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDao {

    public void cadastrarCliente(String nome, String cpf_cnpj, String endereco, String cidade, String estado) throws SQLException {
        String command = """
                    INSERT INTO Cliente (
                        nome, cpf_cnpj, endereco, cidade, estado
                    ) values (
                        ?,?,?,?,?
                    )
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf_cnpj);
            stmt.setString(3, endereco);
            stmt.setString(4, cidade);
            stmt.setString(5, estado);

            stmt.executeUpdate();
        }
    }
}
