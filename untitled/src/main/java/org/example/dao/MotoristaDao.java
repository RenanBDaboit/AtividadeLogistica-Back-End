package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.db.ConnectionFactory;
import org.example.model.Motorista;

public class MotoristaDao {
    
    public void cadastrarMotorista(Motorista motorista) throws SQLException {
        String command = """
                    INSERT INTO motoristas (
                        nome, cnh, veiculo, cidade_base
                    ) VALUES (
                        ?,?,?,?
                    )
                """;

        try (Connection conn = ConnectionFactory.conectar();
                PreparedStatement stmt = conn.prepareStatement(command)) {
            
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCnh());
            stmt.setString(3, motorista.getVeiculo());
            stmt.setString(4, motorista.getCidade_base());

            stmt.executeUpdate();
        } 
    }

    public ArrayList<String> totalPorMotorista() throws SQLException{
        String command = """
                    SELECT
                        m.nome. COUNT(e.id)
                    FROM
                        motoristas m
                    JOIN
                        entregas e
                        ON
                            m.id = e.motorista_id
                    GROUP BY
                        m.nome
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command);
             ResultSet rs = stmt.executeQuery()) {

            ArrayList<String> res = new ArrayList<>();

            StringBuilder linha = new StringBuilder();

            while (rs.next()) {
                String nome_motorista = rs.getString("m.nome");
                String total_entregas = rs.getString("COUNT(e.id)");

                linha.append(nome_motorista).append(" | ").append(total_entregas);

                res.add(linha.toString());
            }

            return res;
        }
    }
}
