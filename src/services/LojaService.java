package services;

import entities.Cliente;
import entities.ItemPedido;
import entities.Pedido;
import entities.Produto;
import enums.StatusPedido;
import exceptions.ClienteNaoEncontradoException;
import exceptions.EstoqueInsuficienteException;
import exceptions.OperacaoArquivo;
import exceptions.PedidoFinalizadoException;
import repository.RepositorioCliente;
import repository.RepositorioPedido;
import repository.RepositorioProduto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LojaService {
    private RepositorioPedido repositorioPedido = new RepositorioPedido();
    private RepositorioCliente repositorioCliente = new RepositorioCliente();
    private RepositorioProduto repositorioProduto = new RepositorioProduto();

    private Persistence<Produto> produtoPersistence = new PersistenceProduto();
    private Persistence<Cliente> clientePersistence = new PersistenceCliente();
    private Persistence<Pedido> pedidoPersistence = new PersistencePedido();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public LojaService() {
        carregarProdutos(Path.of("data\\produtos.csv"));
        carregarClientes(Path.of("data\\clientes.csv"));
        carregarPedidos(Path.of("data\\pedidos.csv"));
        carregarItens(Path.of("data\\itens_pedido.csv"));
    }


    public boolean cadastrarProduto(Produto produto) {
        boolean isCadastrado = repositorioProduto.salvar(produto);
        produtoPersistence.salvar(produto);
        return isCadastrado;
    }

    public boolean cadastrarCliente(Cliente cliente) {
        boolean isCadastrado = repositorioCliente.salvar(cliente);
        clientePersistence.salvar(cliente);
        return isCadastrado;
    }

    public boolean cadastrarPedido(Pedido pedido) {
        boolean isCadastrado = repositorioPedido.salvar(pedido);
        pedidoPersistence.salvar(pedido);
        return isCadastrado;
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
        if (pedido.getStatusPedido() == StatusPedido.PAGO) {
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

    public List<Map.Entry<Produto, Integer>> listarProdutosMaisVendidos() {
        return repositorioPedido.buscarTodos()
                .stream()
                .flatMap(pedido -> pedido.buscarItensPedido().stream())
                .collect(Collectors.groupingBy(ItemPedido::getProduto,
                        Collectors.summingInt(ItemPedido::getQuantidadeProduto)))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry<Produto, Integer>::getValue).reversed())
                .limit(3)
                .toList();
    }


    public double calcularFaturamentoTotal() {
        return repositorioPedido.buscarTodos().stream()
                .filter(p -> p.getStatusPedido().equals(StatusPedido.PAGO))
                .map(Pedido::calcularTotal)
                .reduce(0.0, Double::sum);
    }

    public double calcularTicketMedio() {
        return repositorioPedido.buscarTodos().stream().mapToDouble(Pedido::calcularTotal).average().orElse(0);
    }

    public Cliente buscarClienteComMaisCompras() {
        Map<Cliente, List<Pedido>> mapa = repositorioPedido.buscarTodos()
                .stream()
                .collect(Collectors.groupingBy(Pedido::getCliente));

        return mapa.entrySet()
                .stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nao encontrado!")).getKey();
    }

    private void carregarProdutos(Path caminho) {
        try {
            List<String> linhas = Files.readAllLines(caminho);

            for (String linha : linhas) {
                String[] dados = linha.split(";");
                Produto produto = new Produto(Integer.parseInt(dados[0]),
                        dados[1],
                        Double.parseDouble(dados[2]),
                        Integer.parseInt(dados[3]));
                repositorioProduto.salvar(produto);
            }
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }


    private void carregarClientes(Path caminho) {
        try {
            List<String> linhas = Files.readAllLines(caminho);

            for (String linha : linhas) {
                String[] dados = linha.split(";");
                Cliente cliente = new Cliente(Integer.parseInt(dados[0]), dados[1], dados[2]);
                repositorioCliente.salvar(cliente);
            }
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }

    private void carregarPedidos(Path caminho) {
        try {
            List<String> linhas = Files.readAllLines(caminho);

            for (String linha : linhas) {
                String[] dados = linha.split(";");
                Pedido pedido = new Pedido(Integer.parseInt(dados[0]),
                        repositorioCliente.buscarPorId(Integer.parseInt(dados[1])),
                        LocalDateTime.parse(dados[2], FORMATTER),
                        StatusPedido.valueOf(dados[3]));

                repositorioPedido.salvar(pedido);
            }
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }

    private void carregarItens(Path caminho) {
        try {
            List<String> linhas = Files.readAllLines(caminho);

            for (String linha : linhas) {
                String[] dados = linha.split(";");

                Pedido pedido = repositorioPedido.buscarPorId(Integer.parseInt(dados[0]));
                Produto produto = repositorioProduto.buscarPorId(Integer.parseInt(dados[1]));
                pedido.addItemPedido(new ItemPedido(produto, Integer.parseInt(dados[2])));
            }
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }
}
