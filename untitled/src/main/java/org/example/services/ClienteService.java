package org.example.services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.example.dao.ClienteDao;
import org.example.model.Cliente;

public class ClienteService {

    public void cadastrarCliente(String nome, String cpf_cnpj, String endereco, String cidade, String estado) throws RuntimeException{
        validarNome(nome);
        validarCpfCnpj(cpf_cnpj);
        validarEndereco(endereco);
        validarCidade(cidade);
        validarEstado(estado);

        var cliente = new Cliente(nome, cpf_cnpj, endereco, cidade, estado);

        var clienteDao = new ClienteDao();

        try {
            clienteDao.cadastrarCliente(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> clientesComMaisVolume() throws RuntimeException {
        ClienteDao clienteDao = new ClienteDao();

        ArrayList<String> relatorioPorCliente = null;

        try {
            relatorioPorCliente = clienteDao.clientesComMaisVolume();

            for (String linha : relatorioPorCliente) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        validarListaClientes(relatorioPorCliente);

        return relatorioPorCliente;
    }

    public void excluirCliente(int id) throws RuntimeException {
        validarId(id);

        ClienteDao clienteDao = new ClienteDao();

        try {
            clienteDao.excluirCliente(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void validarId(int id) throws RuntimeException{
        if (id <= 0) {
            throw new RuntimeException("Erro: ID do cliente inválido");
        }
    }

    public void validarNome(String nome) throws RuntimeException {
        if (nome.isBlank()) {
            throw new RuntimeException("Erro: Nome em branco");
        }
    }

    public void validarCpfCnpj(String cpf_cnpj) throws RuntimeException{
        if (cpf_cnpj.isBlank()) {
            throw new RuntimeException("Erro: CPF/CNPJ em branco");
        }
        if (!(cpf_cnpj.length() == 11) && !(cpf_cnpj.length() == 14)) {
            throw new RuntimeException("Erro: CPF/CNPJ com formato errado ou digitos faltando, escreva somente os digitos do código");
        }
    }

    public void validarEndereco(String endereco) throws RuntimeException{
        if (endereco.isBlank()) {
            throw new RuntimeException("Erro: Endereço em branco");
        }
    }

    public void validarCidade(String cidade) throws RuntimeException{
        if (cidade.isBlank()) {
            throw new RuntimeException("Erro: Cidade em branco");
        }
    }

    public void validarEstado(String estado) throws RuntimeException{
        if (estado.isBlank()) {
            throw new RuntimeException("Erro: Estado em branco");
        }
    }

    public void validarListaClientes(ArrayList<String> listaClientes) throws RuntimeException {
        if (listaClientes.isEmpty()) {
            throw new RuntimeException("Lista vazia");
        }
    }
}