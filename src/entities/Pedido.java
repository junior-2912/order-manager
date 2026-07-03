package entities;

import enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pedido {
    private final int id;
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private StatusPedido statusPedido;
    private Set<ItemPedido> itensPedido = new HashSet<>();

    public Pedido(int id, Cliente cliente, ItemPedido itemPedido) {
        this.id = id;
        this.cliente = cliente;
        this.itensPedido.add(itemPedido);
        this.dataPedido = LocalDateTime.now();
        this.statusPedido = StatusPedido.PENDENTE;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public boolean addItemPedido(ItemPedido itemPedido) {
        if (itemPedido != null) {
            boolean itemValido = itensPedido.add(itemPedido);
            if (itemValido) {
                itemPedido.getProduto().setQuantidadeEstoque(itemPedido.getProduto().getQuantidadeEstoque() -
                        itemPedido.getQuantidadeProduto());
            }
            return itemValido;
        }
        throw new IllegalArgumentException("Item de pedido invalido!");
    }


    public void removeItemPedido(ItemPedido itemPedido) {
        itemPedido.getProduto().setQuantidadeEstoque(itemPedido.getProduto().getQuantidadeEstoque() +
                itemPedido.getQuantidadeProduto());
        itensPedido.remove(itemPedido);
    }


    public List<ItemPedido> buscarItensPedido() {
        return List.copyOf(itensPedido);
    }

    public void cancelarPedido() {
        itensPedido
                .forEach(itemPedido -> itemPedido.getProduto().setQuantidadeEstoque(itemPedido.getProduto().getQuantidadeEstoque() +
                        itemPedido.getQuantidadeProduto()));
        itensPedido.clear();
        this.statusPedido = StatusPedido.CANCELADO;
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
