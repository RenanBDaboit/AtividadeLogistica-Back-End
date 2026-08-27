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
                    INSERT INTO Motorista (
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
                        m.nome, COUNT(e.id)
                    FROM
                        Motorista m
                    LEFT JOIN
                        Entrega e
                        ON
                            m.id = e.motorista_id
                    GROUP BY
                        m.nome
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command);
             ResultSet rs = stmt.executeQuery()) {

            ArrayList<String> res = new ArrayList<>();

            while (rs.next()) {

                StringBuilder linha = new StringBuilder();

                String nome_motorista = rs.getString("m.nome");
                String total_entregas = rs.getString("COUNT(e.id)");

                linha.append(nome_motorista).append(" | ").append(total_entregas);

                res.add(linha.toString());
            }

            return res;
        }
    }

    public void excluirMotorista(int id) throws SQLException {
        String command1 = """
                    DELETE FROM
                        HistoricoEntrega
                    WHERE entrega_id IN (
                        SELECT
                            id
                        FROM
                            Entrega
                        WHERE
                            motorista_id = ?
                        )
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command1)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } 

        String command2 = """
                    DELETE FROM
                        Entrega
                    WHERE
                        motorista_id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command2)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }

        String command3 = """
                    DELETE FROM
                        Motorista
                    WHERE
                        id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command3)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
}
