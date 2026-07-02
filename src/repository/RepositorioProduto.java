package repository;

import entities.Produto;
import exceptions.ProdutoDuplicadoException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositorioProduto implements Repositorio<Produto> {
    private Set<Produto> produtos = new HashSet<>();


    @Override
    public boolean salvar(Produto item) {
        if (produtos.add(item)) {
            return true;
        }
        throw new ProdutoDuplicadoException("Produto com mesmo Id já existe!");
    }

    @Override
    public List<Produto> buscarTodos() {
        return List.copyOf(produtos);
    }

    @Override
    public Produto buscarPorId(int id) {
        return null;
    }

    @Override
    public void remover(Produto item) {

    }
}
