package org.example.view;

import org.example.dao.ClienteDao;

import java.sql.SQLException;
import java.util.Scanner;

public class SistemaView {

    private static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        System.out.println("""
                        ==============================
                        \tBem-Vindo
                        1. Cadastrar Cliente
                    """);
        int opcao = Integer.parseInt(scanner.nextLine());

        switch (opcao) {
            case 1: {
                cadastrarCliente();
            }
        }
    }

    public static void cadastrarCliente(){
        System.out.println("Nome: ");
        String nome = scanner.nextLine();

        System.out.println("CPF / CNPJ: ");
        String cpf_cnpj = scanner.nextLine();

        System.out.println("Endereço: ");
        String endereco = scanner.nextLine();

        System.out.println("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.println("Estado: ");
        String estado = scanner.nextLine();

        ClienteDao clienteDao = new ClienteDao();

        try {
            clienteDao.cadastrarCliente(nome, cpf_cnpj, endereco, cidade, estado);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
