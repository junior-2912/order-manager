package services;

import entities.Cliente;
import entities.ItemPedido;
import entities.Pedido;
import entities.Produto;
import enums.StatusPedido;
import exceptions.EstoqueInsuficienteException;
import repository.RepositorioCliente;
import repository.RepositorioPedido;
import repository.RepositorioProduto;

import java.util.List;

public class PedidoService {
    private RepositorioPedido repositorioPedido = new RepositorioPedido();
    private RepositorioCliente repositorioCliente = new RepositorioCliente();
    private RepositorioProduto repositorioProduto = new RepositorioProduto();

    public boolean cadastrarProduto(Produto produto) {
        return repositorioProduto.salvar(produto);
    }

    public boolean cadastrarCliente(Cliente cliente) {
        return repositorioCliente.salvar(cliente);
    }

    public boolean cadastrarPedido(Pedido pedido, ItemPedido itemPedido) {
        if (itemPedido.getQuantidadeProduto() > itemPedido.getProduto().getQuantidadeEstoque()) {
            throw new EstoqueInsuficienteException("A quantidade pedida é maior que a disponivel no estoque!");
        }
        return repositorioPedido.salvar(pedido);
    }

    public void removerProduto(Produto produto) {
        repositorioProduto.remover(produto);
    }

    public boolean adicionarProdutoAoEstoque(int quantidade, int idProduto) {
        Produto produto = buscarProdutoPorId(idProduto);
        if (quantidade <= 0) {
            return false;
        }
        produto.setQuantidadeEstoque(quantidade);
        return true;
    }

    public void removerCliente(Cliente cliente) {
        repositorioCliente.remover(cliente);
    }

    public List<Produto> buscarTodosProdutos() {
        return repositorioProduto.buscarTodos();
    }

    public List<Cliente> buscarTodosClientes() {
        return repositorioCliente.buscarTodos();
    }

    public List<Pedido> buscarTodosPedidos() {
        return repositorioPedido.buscarTodos();
    }

    public Produto buscarProdutoPorId(int id) {
        return repositorioProduto.buscarPorId(id);
    }

    public Cliente buscarClientePorId(int id) {
        return repositorioCliente.buscarPorId(id);
    }

    public Pedido buscarPedidoPorId(int id) {
        return repositorioPedido.buscarPorId(id);
    }

    public boolean adicionarItensAoPedido(Produto produto, int quantidadeProduto, int idPedido) {
        Pedido pedido = repositorioPedido.buscarPorId(idPedido);
        return pedido.addItemPedido(new ItemPedido(produto, quantidadeProduto));
    }

    public void alterarStatusPedido(int idPedido, String status) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        pedido.setStatusPedido(StatusPedido.valueOf(status.toUpperCase()));
    }

    public void cancelarPedido(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        pedido.cancelarPedido();
    }

    public double confirmarPedido(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        return pedido.calcularTotal();
    }
}
