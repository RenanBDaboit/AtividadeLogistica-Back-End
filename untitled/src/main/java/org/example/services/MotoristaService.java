package org.example.services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.example.dao.MotoristaDao;
import org.example.model.Motorista;

public class MotoristaService {
    
    public void cadastrarMotorista(String nome, String cnh, String veiculo, String cidade_base) throws RuntimeException{
        validarNome(nome);
        validarCnh(cnh);
        validarVeiculo(veiculo);
        validarCidadeBase(cidade_base);

        var motorista = new Motorista(nome, cnh, veiculo, cidade_base);

        var motoristaDao = new MotoristaDao();

        try {
            motoristaDao.cadastrarMotorista(motorista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> totalPorMotorista() {
        ArrayList<String> relatorioPorMotorista = null;

        var motoristaDao = new MotoristaDao();

        try {
            relatorioPorMotorista = motoristaDao.totalPorMotorista();

            for (String linha : relatorioPorMotorista) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        validarListaMotorista(relatorioPorMotorista);

        return relatorioPorMotorista;
    }

    private void validarNome(String nome) throws RuntimeException{
        if (nome.isBlank()) {
            throw new RuntimeException("Erro: Nome em branco");
        }
    }

    private void validarCnh(String cnh) throws RuntimeException{
        if (cnh.isBlank()) {
            throw new RuntimeException("Erro: CNH em branco");
        }

        if (!(cnh.length() == 9)) {
            throw new RuntimeException("Erro: CNH com formato errado ou digitos faltando, coloque apenas os números do código");
        }
    }

    private void validarVeiculo(String veiculo) throws RuntimeException {
        if (veiculo.isBlank()) {
            throw new RuntimeException("Erro: Veiculo em branco");
        }
    }

    private void validarCidadeBase(String cidade_base) throws RuntimeException {
        if (cidade_base.isBlank()) {
            throw new RuntimeException("Erro: Cidade em branco");
        }
    }

    private void validarListaMotorista(ArrayList<String> listaMotorista) throws RuntimeException{
        if (listaMotorista.isEmpty()) {
            throw new RuntimeException("Lista vazia");
        }
    }
}
