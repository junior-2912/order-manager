package repository;

import entities.Pedido;
import exceptions.PedidoDuplicadoException;
import exceptions.PedidoNaoEncontradoException;

import java.util.*;

public class RepositorioPedido implements Repositorio<Pedido> {
    private Set<Pedido> pedidos = new HashSet<>();
    private Map<Integer, Pedido> pedidosPorId = new HashMap<>();


    @Override
    public boolean salvar(Pedido item) {
        if (item != null) {
            if (pedidos.add(item)) {
                pedidosPorId.put(item.getId(), item);
                return true;
            }
            throw new PedidoDuplicadoException("Ja existe um pedido com esse Id!");
        }
        return false;
    }

    @Override
    public List<Pedido> buscarTodos() {
        return List.copyOf(pedidos);
    }

    @Override
    public Pedido buscarPorId(int id) {
        if (pedidosPorId.containsKey(id)) return pedidosPorId.get(id);
        throw new PedidoNaoEncontradoException("Pedido com o id informado nao existe!");
    }

    @Override
    public void remover(Pedido item) {
        pedidos.remove(item);
        pedidosPorId.remove(item.getId());
    }
}
