package org.example.view;

import org.example.dao.ClienteDao;
import org.example.dao.EntregaDao;
import org.example.dao.HistoricoEntregaDao;
import org.example.dao.MotoristaDao;
import org.example.dao.PedidoDao;
import org.example.enums.StatusEntrega;
import org.example.enums.StatusPedido;
import org.example.model.Cliente;
import org.example.model.Entrega;
import org.example.model.HistoricoEntrega;
import org.example.model.Motorista;
import org.example.model.Pedido;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class SistemaView {

    private static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        System.out.println("""
                        ==============================
                        \tBem-Vindo
                        1. Cadastrar Cliente
                        2 - Cadastrar Motorista
                        3 - Criar Pedido
                        4 - Atribuir Pedido a Motorista (Gerar Entrega)
                        5 - Registrar Evento de Entrega (Histórico)
                        6 - Atualizar Status da Entrega
                        7 - Listar Todas as Entregas com Cliente e Motorista
                        8 - Relatório: Total de Entregas por Motorista
                        9 - Relatório: Clientes com Maior Volume Entregue
                        10 - Relatório: Pedidos Pendentes por Estado
                        11 - Relatório: Entregas Atrasadas por Cidade
                        12 - Buscar Pedido por CPF/CNPJ do Cliente 
                    """);
        int opcao = Integer.parseInt(scanner.nextLine());

        switch (opcao) {
            case 1: {
                cadastrarCliente();
                break;
            }

            case 2: {
                cadastrarMotorista();
                break;
            }

            case 3: {
                criarPedido();
                break;
            }

            case 4: {
                gerarEntrega();
                break;
            }

            case 5: {
                registrarEventoEntrega();
                break;
            }

            case 6: {
                atualizarStatusEntrega();
                break;
            }

            case 7: {
                listarEntregasCLienteMotorista();
                break;
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

        var cliente = new Cliente(nome, cpf_cnpj, endereco, cidade, estado);
        
        try {
            clienteDao.cadastrarCliente(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarMotorista(){
        System.out.println("Nome: ");
        String nome = scanner.nextLine();

        System.out.println("CNH: ");
        String cnh = scanner.nextLine();

        System.out.println("Veículo: ");
        String veiculo = scanner.nextLine();

        System.out.println("Cidade base: ");
        String cidade_base = scanner.nextLine();

        MotoristaDao motoristaDao = new MotoristaDao();

        var motorista = new Motorista(nome, cnh, veiculo, cidade_base);
        
        try {
            motoristaDao.cadastrarMotorista(motorista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public static void criarPedido(){
        System.out.println("ID do cliente: ");
        int cliente_id = Integer.parseInt(scanner.nextLine());

        Date data_pedido = Date.valueOf(LocalDate.now());

        System.out.println("Volume (m³): ");
        double volume_m3 = Double.parseDouble(scanner.nextLine());

        System.out.println("Peso (kg): ");
        double peso_kg = Double.parseDouble(scanner.nextLine());

        StatusPedido status = StatusPedido.PENDENTE;

        PedidoDao pedidoDao = new PedidoDao();

        var pedido = new Pedido(cliente_id, data_pedido, volume_m3, peso_kg, status);

        try {
            pedidoDao.criarPedido(pedido);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void gerarEntrega(){
        System.out.println("ID do pedido: ");
        int pedido_id = Integer.parseInt(scanner.nextLine());

        System.out.println("ID do motorista: ");
        int motorista_id = Integer.parseInt(scanner.nextLine());

        Date data_saida = Date.valueOf(LocalDate.now());

        System.out.println("Data de entrega (AAAA-MM-DD): ");
        Date data_entrega = Date.valueOf(scanner.nextLine());

        StatusEntrega status = StatusEntrega.EM_ROTA;

        var entrega = new Entrega(pedido_id, motorista_id, data_saida, data_entrega, status);

        EntregaDao entregaDao = new EntregaDao();

        try {
            entregaDao.gerarEntrega(entrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registrarEventoEntrega() {
        System.out.println("ID da entrega: ");
        int entrega_id = Integer.parseInt(scanner.nextLine());

        Date data_evento = Date.valueOf(LocalDate.now());

        System.out.println("Descrição: ");
        String descricao = scanner.nextLine();

        var historicoEntrega = new HistoricoEntrega(entrega_id, data_evento, descricao);

        HistoricoEntregaDao historicoEntregaDao = new HistoricoEntregaDao();

        try {
            historicoEntregaDao.registrarEventoEntrega(historicoEntrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarStatusEntrega() {
        System.out.println("ID da entrega: ");
        int entrega_id = Integer.parseInt(scanner.nextLine());

        System.out.println("""
                    ===================
                    \tSelecione novo status:
                    1. Em Rota
                    2. Entregue
                    3. Atrasada
                """);
        int opcaoStatus = Integer.parseInt(scanner.nextLine());

        StatusEntrega status = null;

        switch (opcaoStatus) {
            case 1: {
                status = StatusEntrega.EM_ROTA;
                break;
            }

            case 2: {
                status = StatusEntrega.ENTREGUE;
                break;
            }

            case 3: {
                status = StatusEntrega.ATRASADA;
                break;
            }
        }

        EntregaDao entregaDao = new EntregaDao();

        try {
            entregaDao.atualizarStatusEntrega(entrega_id, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarEntregasCLienteMotorista(){
        EntregaDao entregaDao = new EntregaDao();

        try {
            ArrayList<String> listaEntregas = entregaDao.listarEntregasCLienteMotorista();

            for (String linha : listaEntregas) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
