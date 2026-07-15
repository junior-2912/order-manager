package entities;

import enums.StatusPedido;
import exceptions.ProdutoNaoEncontradoException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pedido {
    private final int id;
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private StatusPedido statusPedido;
    private Set<ItemPedido> itensPedido = new HashSet<>();

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
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
                itemPedido.atualizarEstoque(itemPedido.getQuantidadeProduto(), "-");
            }
            return itemValido;
        }
        throw new ProdutoNaoEncontradoException("Item de pedido invalido ou nao encontrado!");
    }


    public List<ItemPedido> buscarItensPedido() {
        return List.copyOf(itensPedido);
    }

    public void cancelarPedido() {
        itensPedido.forEach(itemPedido -> itemPedido.atualizarEstoque(itemPedido.getQuantidadeProduto(), "+"));
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

    @Override
    public String toString() {
        return "Id: " + id + " - Dono do pedido: " + cliente.getNome() + "\n"
                + "Data do pedido: " + dataPedido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - "
                + "Status: " + statusPedido
                + "\n " + itensPedido;
    }

    public double calcularTotal() {
        return itensPedido.stream()
                .map(ItemPedido::calcularSubTotal)
                .reduce(0.0, Double::sum);
    }
}
