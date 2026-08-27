package org.example.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.dao.PedidoDao;
import org.example.enums.StatusPedido;
import org.example.model.Pedido;

public class PedidoService {

    private final ClienteService clienteService = new ClienteService();
    
    public void criarPedido(int cliente_id, Date data_pedido, double volume_m3, double peso_kg, StatusPedido status) throws RuntimeException{
        validarClienteId(cliente_id);
        validarVolume(volume_m3);
        validarPeso(peso_kg);

        var pedido = new Pedido(cliente_id, data_pedido, volume_m3, peso_kg, status);
        
        PedidoDao pedidoDao = new PedidoDao();

        try {
            pedidoDao.criarPedido(pedido);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> pedidosPendentesPorEstado(String estado) throws RuntimeException{
        
        clienteService.validarEstado(estado);
        
        PedidoDao pedidoDao = new PedidoDao();

        ArrayList<String> relatorioPedidosPendentes = null;

        try {
            relatorioPedidosPendentes = pedidoDao.pedidosPendentesPorEstado(estado);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        validarListaPedidos(relatorioPedidosPendentes);

        return relatorioPedidosPendentes;
    }

    public ArrayList<String> buscarPedidoPorCpfCnpj(String cpf_cnpj) throws RuntimeException{
        clienteService.validarCpfCnpj(cpf_cnpj);
        
        PedidoDao pedidoDao = new PedidoDao();

        ArrayList<String> pedidosPorCpf = null;

        try {
            pedidosPorCpf = pedidoDao.buscarPedidoPorCpfCnpj(cpf_cnpj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        validarListaPedidos(pedidosPorCpf);

        return pedidosPorCpf;
    }

    public void cancelarPedido(int id) throws RuntimeException {
        validarId(id);

        PedidoDao pedidoDao = new PedidoDao();

        try {
            pedidoDao.cancelarPedido(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void validarId(int id) throws RuntimeException{
        if (id <= 0) {
            throw new RuntimeException("Erro: ID do pedido inválido");
        }
    }

    public void validarClienteId(int cliente_id) throws RuntimeException {
        if (cliente_id <= 0) {
            throw new RuntimeException("Erro: ID do cliente inválido");
        }
    }

    public void validarVolume(double volume_m3) throws RuntimeException{
        if (volume_m3 <= 0) {
            throw new RuntimeException("Erro: Volume inválido");
        }
    }

    public void validarPeso(double peso_kg) throws RuntimeException {
        if (peso_kg <= 0) {
            throw new RuntimeException("Erro: Peso inválido");
        }
    }

    public void validarListaPedidos(ArrayList<String> listaPedidos) throws RuntimeException {
        if (listaPedidos.isEmpty()) {
            throw new RuntimeException("Lista vazia");
        }
    }
}
