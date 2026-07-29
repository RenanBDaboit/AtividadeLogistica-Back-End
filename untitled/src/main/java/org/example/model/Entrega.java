package org.example.model;

import org.example.enums.StatusEntrega;

import java.sql.Date;

public class Entrega {

    private int id;
    private int pedido_id;
    private int motorista_id;
    private Date data_saida;
    private Date data_entrega;
    private StatusEntrega status;

    public Entrega(int id, int pedido_id, int motorista_id, Date data_saida, Date data_entrega, StatusEntrega status) {
        this.id = id;
        this.pedido_id = pedido_id;
        this.motorista_id = motorista_id;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status = status;
    }

    public Entrega(int pedido_id, int motorista_id, Date data_saida, Date data_entrega, StatusEntrega status) {
        this.pedido_id = pedido_id;
        this.motorista_id = motorista_id;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public int getMotorista_id() {
        return motorista_id;
    }

    public void setMotorista_id(int motorista_id) {
        this.motorista_id = motorista_id;
    }

    public Date getData_saida() {
        return data_saida;
    }

    public void setData_saida(Date data_saida) {
        this.data_saida = data_saida;
    }

    public Date getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(Date data_entrega) {
        this.data_entrega = data_entrega;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public void setStatus(StatusEntrega status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Entrega{" +
                "pedido_id=" + pedido_id +
                ", motorista_id=" + motorista_id +
                ", data_saida=" + data_saida +
                ", data_entrega=" + data_entrega +
                ", status=" + status +
                '}';
    }
}
