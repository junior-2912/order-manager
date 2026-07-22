package application;

import entities.Cliente;
import entities.Pedido;
import entities.Produto;
import exceptions.*;
import services.LojaService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        LojaService lojaService = new LojaService();
        int indice;
        while (true) {
            try {
                mostrarMenu();
                System.out.print("Digite um numero de acordo com o indice: ");
                indice = entrada.nextByte();
                entrada.nextLine();
                if (indice == 0) {
                    System.out.println("Adeus! Fechando o programa...");
                    break;
                }
                switch (indice) {
                    case 1 -> cadastrarCliente(entrada, lojaService);
                    case 2 -> cadastrarProduto(entrada, lojaService);
                    case 3 -> criarPedido(entrada, lojaService);
                    case 4 -> adicionarProdutoAoEstoque(entrada, lojaService);
                    case 5 -> listarClientes(lojaService);
                    case 6 -> listarProdutos(lojaService);
                    case 7 -> listarPedidos(lojaService);
                    case 8 -> buscarClientePorId(entrada, lojaService);
                    case 9 -> buscarProdutoPorId(entrada, lojaService);
                    case 10 -> buscarPedidoPorId(entrada, lojaService);
                    case 11 -> removerCliente(entrada, lojaService);
                    case 12 -> removerProduto(entrada, lojaService);
                    case 13 -> adicionarItensAoPedido(entrada, lojaService);
                    case 14 -> alterarStatusPedido(entrada, lojaService);
                    case 15 -> cancelarPedido(entrada, lojaService);
                    case 16 -> confirmarPedido(entrada, lojaService);
                    case 17 -> listarPedidosPorCliente(entrada, lojaService);
                    case 18 -> buscarProdutoPorNome(entrada, lojaService);
                    case 19 -> gerarRelatorio(lojaService);
                    default -> System.out.println("Digite um numero valido!");
                }
            } catch (ClienteDuplicadoException | ClienteNaoEncontradoException | PedidoDuplicadoException |
                     PedidoNaoEncontradoException | EstoqueInsuficienteException | ProdutoDuplicadoException |
                     ProdutoNaoEncontradoException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Digite o que lhe é pedido!");
            }
        }
        entrada.close();
    }

    public static void buscarProdutoPorNome(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o nome do produto: ");
        String nome = entrada.nextLine();
        List<Produto> produtos = lojaService.buscarProdutoPorNome(nome);
        if (produtos.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Produto nao encontrado!");
        } else {
            System.out.println("Produto encontrado!");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    public static void gerarRelatorio(LojaService lojaService) {
        System.out.println("RELATORIO GERAL DA LOJA");
        System.out.println("Pedidos realizados: " + lojaService.buscarTodosPedidos().size());
        System.out.println("Faturamento total: " + lojaService.calcularFaturamentoTotal());
        System.out.println("Ticket medio: " + lojaService.calcularTicketMedio());
        System.out.println("Cliente com mais pedidos: " + lojaService.buscarClienteComMaisCompras());
        System.out.println("3 Produtos mais vendidos:");
        for (Map.Entry<Produto, Integer> entry : lojaService.listarProdutosMaisVendidos()) {
            System.out.println(entry.getKey().getNome() + " -> " + entry.getValue());
        }
    }

    public static void listarPedidosPorCliente(Scanner entrada, LojaService lojaService) {
        System.out.println("Digite o id do cliente: ");
        int id = entrada.nextInt();

        List<Pedido> pedidos = lojaService.listarPedidosPorCliente(id);
        System.out.println("Cliente: " + lojaService.buscarClientePorId(id).getNome() + "\n Pedidos:");
        pedidos.forEach(System.out::println);
    }

    public static void mostrarMenu() {
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Cadastrar Produto");
        System.out.println("3 - Criar Pedido");
        System.out.println("4 - Dar entrada em produto");
        System.out.println("5 - Listar Clientes");
        System.out.println("6 - Listar Produtos");
        System.out.println("7 - Listar Pedidos");
        System.out.println("8 - Buscar clientes por id");
        System.out.println("9 - Buscar produtos por id");
        System.out.println("10 - Buscar pedidos por id");
        System.out.println("11 - Remover cliente");
        System.out.println("12 - Remover produto");
        System.out.println("13 - Adicionar items ao pedido");
        System.out.println("14 - Alterar status do pedido");
        System.out.println("15 - Cancelar pedido");
        System.out.println("16 - Confirmar pedido");
        System.out.println("17 - Pedidos por cliente");
        System.out.println("18 - Buscar produto por nome");
        System.out.println("19 - Relatorio geral");
    }

    public static void cadastrarCliente(Scanner entrada, LojaService lojaService) {

        System.out.print("Digite o id do cliente: ");
        int id = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Digite o nome do cliente: ");
        String nome = entrada.nextLine();
        System.out.print("Digite o email do cliente: ");
        String email = entrada.nextLine();

        if (lojaService.cadastrarCliente(new Cliente(id, nome, email))) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Cliente é nulo!");
        }
    }

    public static void cadastrarProduto(Scanner entrada, LojaService lojaService) {

        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Digite o nome do produto: ");
        String nome = entrada.nextLine();
        System.out.print("Digite o preço do produto: ");
        double preco = entrada.nextDouble();
        System.out.print("Digite a quantidade do produto no estoque: ");
        int quantidade = entrada.nextInt();

        if (lojaService.cadastrarProduto(new Produto(id, nome, preco, quantidade))) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Produto é nulo!");
        }
    }

    public static void criarPedido(Scanner entrada, LojaService lojaService) {

        System.out.print("Digite o id do pedido: ");
        int id = entrada.nextInt();
        System.out.print("Digite o id do cliente dono do pedido: ");
        int idCliente = entrada.nextInt();

        if (lojaService.cadastrarPedido(new Pedido(id, lojaService.buscarClientePorId(idCliente)))) {
            System.out.println("Pedido cadastrado com sucesso!");
        } else {
            System.out.println("Pedido é nulo!");
        }
    }

    public static void adicionarProdutoAoEstoque(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();
        System.out.print("Digite a quantidade a dar entrada: ");
        int quantidade = entrada.nextInt();
        if (!lojaService.adicionarProdutoAoEstoque(quantidade, id)) {
            System.out.println("Quantidade deve ser maior que zero!");
        } else {
            System.out.println("Adicionado com sucesso!");
        }
    }

    public static void listarClientes(LojaService lojaService) {
        lojaService.buscarTodosClientes().forEach(System.out::println);
    }

    public static void listarProdutos(LojaService lojaService) {
        lojaService.buscarTodosProdutos().forEach(System.out::println);
    }

    public static void listarPedidos(LojaService lojaService) {
        lojaService.buscarTodosPedidos().forEach(System.out::println);
    }

    public static void buscarClientePorId(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do cliente: ");
        int id = entrada.nextInt();
        System.out.println(lojaService.buscarClientePorId(id));
    }

    public static void buscarProdutoPorId(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();
        System.out.println(lojaService.buscarProdutoPorId(id));
    }

    public static void buscarPedidoPorId(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do pedido: ");
        int id = entrada.nextInt();
        System.out.println(lojaService.buscarPedidoPorId(id));
    }

    public static void removerCliente(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do cliente: ");
        int id = entrada.nextInt();

        lojaService.removerCliente(lojaService.buscarClientePorId(id));
        System.out.println("Cliente removido com sucesso!");
    }

    public static void removerProduto(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();

        lojaService.removerProduto(lojaService.buscarProdutoPorId(id));
        System.out.println("Produto removido com sucesso!");
    }

    public static void adicionarItensAoPedido(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do pedido que sera adicionado os produtos: ");
        int idPedido = entrada.nextInt();
        System.out.print("Digite o id do produto a ser adicionado: ");
        int idProduto = entrada.nextInt();
        System.out.print("Digite a quantidade: ");
        int quantidadeProduto = entrada.nextInt();

        if (lojaService.adicionarItensAoPedido(lojaService.buscarProdutoPorId(idProduto), quantidadeProduto, idPedido)) {
            System.out.println("Item adicionado com sucesso! ");
        } else {
            System.out.println("Nao foi possivel adicionar o item! ");
        }
    }

    public static void alterarStatusPedido(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do pedido a ser alterado: ");
        int idPedido = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Digite o status atualizado do pedido (PENDENTE, PAGO, ENVIADO, ENTREGUE, CANCELADO): ");
        String status = entrada.nextLine();

        lojaService.alterarStatusPedido(idPedido, status);
    }

    public static void cancelarPedido(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do pedido a ser cancelado: ");
        lojaService.cancelarPedido(entrada.nextInt());
    }

    public static void confirmarPedido(Scanner entrada, LojaService lojaService) {
        System.out.print("Digite o id do pedido a ser finalizado: ");
        double valor = lojaService.confirmarPedido(entrada.nextInt());
        System.out.printf("Valor total: " + String.format("%.2f", valor) + System.lineSeparator());
    }


}


