package services;

import entities.Cliente;
import entities.ItemPedido;
import entities.Pedido;
import entities.Produto;
import enums.StatusPedido;
import exceptions.EstoqueInsuficienteException;
import exceptions.PedidoFinalizadoException;
import repository.RepositorioCliente;
import repository.RepositorioPedido;
import repository.RepositorioProduto;

import java.util.*;
import java.util.stream.Collectors;

public class LojaService {
    private RepositorioPedido repositorioPedido = new RepositorioPedido();
    private RepositorioCliente repositorioCliente = new RepositorioCliente();
    private RepositorioProduto repositorioProduto = new RepositorioProduto();

    public boolean cadastrarProduto(Produto produto) {
        return repositorioProduto.salvar(produto);
    }

    public boolean cadastrarCliente(Cliente cliente) {
        return repositorioCliente.salvar(cliente);
    }

    public boolean cadastrarPedido(Pedido pedido) {
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
        produto.entradaEstoque(quantidade);
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
        if (quantidadeProduto > produto.getQuantidadeEstoque()) {
            throw new EstoqueInsuficienteException("Estoque insuficiente!");
        }
        ItemPedido itemPedido = new ItemPedido(produto, quantidadeProduto);
        boolean isAdd = pedido.addItemPedido(itemPedido);
        if (isAdd) {
            produto.baixarEstoque(quantidadeProduto);
        }
        return isAdd;
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
        if (pedido.getStatusPedido() == StatusPedido.PAGO || pedido.getStatusPedido() == StatusPedido.ENTREGUE) {
            throw new PedidoFinalizadoException("Pedido ja esta finalizado!");
        }
        pedido.setStatusPedido(StatusPedido.PAGO);
        return pedido.calcularTotal();
    }

    public List<Pedido> listarPedidosPorCliente(int idCliente) {
        Cliente cliente = repositorioCliente.buscarPorId(idCliente);
        List<Pedido> pedidos = new ArrayList<>();
        pedidos = repositorioPedido.buscarTodos()
                .stream()
                .filter(pedido -> pedido.getCliente() == cliente)
                .toList();
        return pedidos;
    }

    public List<Produto> listarProdutosMaisVendidos() {
        List<Produto> produtosMaisVendidos = repositorioPedido.buscarTodos()
                .stream()
                .flatMap(pedido -> pedido.buscarItensPedido().stream())
                .collect(Collectors.groupingBy(ItemPedido::getProduto,
                        Collectors.summingInt(ItemPedido::getQuantidadeProduto)))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry<Produto, Integer>::getValue).reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        return produtosMaisVendidos;
    }
}
