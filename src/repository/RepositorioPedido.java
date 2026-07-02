package repository;

import entities.Pedido;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositorioPedido implements Repositorio<Pedido> {
    private Set<Pedido> pedidos = new HashSet<>();


    @Override
    public boolean salvar(Pedido item) {
        return false;
    }

    @Override
    public List<Pedido> buscarTodos() {
        return List.of();
    }

    @Override
    public Pedido buscarPorId(int id) {
        return null;
    }

    @Override
    public void remover(Pedido item) {

    }
}
