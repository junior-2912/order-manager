package entities;

import enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private StatusPedido statusPedido;
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public Pedido(int id, Cliente cliente, LocalDateTime dataPedido) {
        this.id = id;
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.statusPedido = StatusPedido.PENDENTE;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Pedido pedido = (Pedido) o;
        return id == pedido.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public double calcularTotal() {
        return itensPedido.stream()
                .map(ItemPedido::calcularSubTotal)
                .reduce(0.0, Double::sum);
    }
}
