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
                    INSERT INTO Cliente (
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
                        Cliente c
                    JOIN
                        Pedido p
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

            while (rs.next()) {

                StringBuilder linha = new StringBuilder();

                String nome_cliente = rs.getString("c.nome");
                String soma_volumes = rs.getString("SUM(p.volume_m3)");

                linha.append(nome_cliente).append(" | ").append(soma_volumes);

                res.add(linha.toString());
            }

            return res;
        }
    }

    public void excluirCliente(int id) throws SQLException {
        String command1 = """
                    DELETE FROM
                        HistoricoEntrega
                    WHERE
                        entrega_id IN (
                            SELECT
                                e.id
                            FROM
                                Entrega e
                            JOIN
                                Pedido p
                                ON
                                    p.id = e.pedido_id
                            WHERE
                                p.cliente_id = ?
                        );
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
                        pedido_id IN (
                            SELECT
                                id
                            FROM
                                Pedido
                            WHERE
                                cliente_id = ?
                        );
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command2)) {
                
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }

        String command3 = """
                    DELETE FROM
                        Pedido
                    WHERE
                        cliente_id = ?;
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command3)) {
                
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }

        String command4 = """
                    DELETE FROM
                        Cliente
                    WHERE
                        id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(command4)) {
                
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
}
