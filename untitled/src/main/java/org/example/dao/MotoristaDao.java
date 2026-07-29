package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void totalPorMotorista(){
        String command = """
                    SELECT
                        m.nome. count(e.id)
                    FROM
                        motoristas m
                    JOIN
                        entregas e
                        ON
                            m.id = e.motorista_id
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = ) {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
