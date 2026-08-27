package org.example.services;

import org.example.dao.EntregaDao;
import org.example.dao.PedidoDao;
import org.example.enums.StatusEntrega;
import org.example.model.Entrega;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;


public class EntregaService {
    
    private final ClienteService clienteService = new ClienteService();

    public void gerarEntrega(int pedido_id, int motorista_id, Date data_saida, Date data_entrega, StatusEntrega status) throws RuntimeException {
        validarPedidoId(pedido_id);
        validarMotoristaId(motorista_id);
        validarDataEntrega(data_saida, data_entrega);

        EntregaDao entregaDao = new EntregaDao();
        
        var entrega = new Entrega(pedido_id, motorista_id, data_saida, data_entrega, status);

        try {
            entregaDao.gerarEntrega(entrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarStatusEntrega(int entrega_id, StatusEntrega status) throws RuntimeException{
        validarEntregaId(entrega_id);
        validarStatus(status);

        EntregaDao entregaDao = new EntregaDao();

        try {
            entregaDao.atualizarStatusEntrega(entrega_id, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> listarEntregasCLienteMotorista() throws RuntimeException{
        EntregaDao entregaDao = new EntregaDao();

        ArrayList<String> listaEntregas = null;

        try {
            listaEntregas = entregaDao.listarEntregasCLienteMotorista();

            for (String linha : listaEntregas) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        validarListaEntregas(listaEntregas);

        return listaEntregas;
    }

    public ArrayList<String> entregasAtrasadasPorCidade(String cidade) throws RuntimeException{
        
        clienteService.validarCidade(cidade);
        
        EntregaDao entregaDao = new EntregaDao();

        try {
            ArrayList<String> relatorioEntregasAtrasadas = entregaDao.entregasAtrasadasPorCidade(cidade);

            for (String linha : relatorioEntregasAtrasadas) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void excluirEntrega(int id) throws RuntimeException {
        validarEntregaId(id);

        EntregaDao entregaDao = new EntregaDao();

        try {
            entregaDao.excluirEntrega(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void validarEntregaId(int entrega_id) throws RuntimeException{
        if (entrega_id <= 0) {
            throw new RuntimeException("Erro: ID da entrega inválido");
        }
    }

    public void validarPedidoId(int pedido_id) throws RuntimeException{
        if (pedido_id <= 0) {
            throw new RuntimeException("Erro: ID do pedido inválido");
        }
    }

    public void validarMotoristaId(int motorista_id) throws RuntimeException{
        if (motorista_id <= 0) {
            throw new RuntimeException("Erro: ID do motorista inválido");
        }
    }

    public void validarDataEntrega(Date data_saida, Date data_entrega) throws RuntimeException{
        if (!data_entrega.after(data_saida)) {
            throw new RuntimeException("Erro: Data da saída depois da data de entrega");
        }
    }

    public void validarStatus(StatusEntrega status) throws RuntimeException {
        if (status == null) {
            throw new RuntimeException("Erro: Status da entrega vazio");
        }
    }

    public void validarListaEntregas(ArrayList<String> listaEntregas) throws RuntimeException {
        if (listaEntregas.isEmpty()) {
            throw new RuntimeException("Lista vazia");
        }
    }
}
