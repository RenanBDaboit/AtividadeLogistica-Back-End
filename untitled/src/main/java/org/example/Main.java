package org.example;

import org.example.db.ConnectionFactory;
import org.example.view.SistemaView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try (Connection conn = ConnectionFactory.conectar()){
            if (conn != null){
                System.out.println("rodou");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int opcao = 1;

        while (opcao != 0) {
            opcao = SistemaView.menu();
        }
    }
}