package repository;

import entities.Cliente;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositorioCliente implements Repositorio<Cliente> {
    private Set<Cliente> clientes = new HashSet<>();

    @Override
    public boolean salvar(Cliente item) {
        return false;
    }

    @Override
    public List<Cliente> buscarTodos() {
        return List.of();
    }

    @Override
    public Cliente buscarPorId(int id) {
        return null;
    }

    @Override
    public void remover(Cliente item) {

    }
}
