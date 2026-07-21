package repository;

import entities.Produto;
import exceptions.ProdutoDuplicadoException;
import exceptions.ProdutoNaoEncontradoException;

import java.util.*;

public class RepositorioProduto implements Repositorio<Produto> {
    private Set<Produto> produtos = new HashSet<>();
    private Map<Integer, Produto> produtoPorId = new HashMap<>();

    @Override
    public boolean salvar(Produto item) {
        if (item != null) {
            if (produtos.add(item)) {
                produtoPorId.put(item.getId(), item);
                return true;
            }
            throw new ProdutoDuplicadoException("Produto com mesmo Id já existe!");
        }
        return false;
    }

    @Override
    public List<Produto> buscarTodos() {
        return List.copyOf(produtos.stream().sorted(Comparator.comparing(Produto::getPreco).reversed()).toList());
    }

    @Override
    public Produto buscarPorId(int id) {
        if (produtoPorId.containsKey(id)) return produtoPorId.get(id);
        throw new ProdutoNaoEncontradoException("Produto com o id informado nao existe!");
    }

    @Override
    public void remover(Produto item) {
        produtos.remove(item);
        produtoPorId.remove(item.getId());
    }
}
