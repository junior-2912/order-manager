package repository;

import entities.Cliente;
import exceptions.ClienteDuplicadoException;
import exceptions.ClienteNaoEncontradoException;

import java.util.*;

public class RepositorioCliente implements Repositorio<Cliente> {
    private Set<Cliente> clientes = new HashSet<>();
    private Map<Integer, Cliente> clientePorId = new HashMap<>();

    @Override
    public boolean salvar(Cliente item) {
        if (item != null) {
            if (clientes.add(item)) {
                clientePorId.put(item.getId(), item);
                return true;
            }
            throw new ClienteDuplicadoException("Já existe um cliente com esse Id!");
        }
        return false;
    }

    @Override
    public List<Cliente> buscarTodos() {
        return List.copyOf(clientes);
    }

    @Override
    public Cliente buscarPorId(int id) {
        if (clientePorId.containsKey(id)) return clientePorId.get(id);
        throw new ClienteNaoEncontradoException("Cliente com o id informado nao existe!");
    }

    @Override
    public void remover(Cliente item) {
        clientes.remove(item);
    }
}
