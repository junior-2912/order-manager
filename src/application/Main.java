package application;

import entities.Cliente;
import entities.ItemPedido;
import entities.Pedido;
import entities.Produto;
import enums.StatusPedido;
import exceptions.*;
import services.PedidoService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        PedidoService pedidoService = new PedidoService();
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
                    case 1 -> cadastrarCliente(entrada, pedidoService);
                    case 2 -> cadastrarProduto(entrada, pedidoService);
                    case 3 -> criarPedido(entrada, pedidoService);
                    case 4 -> adicionarProdutoAoEstoque(entrada, pedidoService);
                    case 5 -> listarClientes(pedidoService);
                    case 6 -> listarProdutos(pedidoService);
                    case 7 -> listarPedidos(pedidoService);
                    case 8 -> buscarClientePorId(entrada, pedidoService);
                    case 9 -> buscarProdutoPorId(entrada, pedidoService);
                    case 10 -> buscarPedidoPorId(entrada, pedidoService);
                    case 11 -> removerCliente(entrada, pedidoService);
                    case 12 -> removerProduto(entrada, pedidoService);
                    case 13 -> adicionarItensAoPedido(entrada, pedidoService);
                    case 14 -> alterarStatusPedido(entrada, pedidoService);
                    case 15 -> cancelarPedido(entrada, pedidoService);
                    case 16 -> confirmarPedido(entrada, pedidoService);
                    default -> System.out.println("Digite um numero valido!");
                }
            } catch (ClienteDuplicadoException | ClienteNaoEncontradoException | PedidoDuplicadoException |
                     PedidoNaoEncontradoException | EstoqueInsuficienteException | ProdutoDuplicadoException |
                     ProdutoNaoEncontradoException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Digite o que lhe é pedido!");
                return;
            }
        }
        entrada.close();
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
    }

    public static void cadastrarCliente(Scanner entrada, PedidoService pedidoService) {

        System.out.print("Digite o id do cliente: ");
        int id = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Digite o nome do cliente: ");
        String nome = entrada.nextLine();
        System.out.print("Digite o email do cliente: ");
        String email = entrada.nextLine();

        if (pedidoService.cadastrarCliente(new Cliente(nome, id, email))) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Cliente é nulo!");
        }
    }

    public static void cadastrarProduto(Scanner entrada, PedidoService pedidoService) {

        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Digite o nome do produto: ");
        String nome = entrada.nextLine();
        System.out.print("Digite o preço do produto: ");
        double preco = entrada.nextDouble();
        System.out.print("Digite a quantidade do produto no estoque: ");
        int quantidade = entrada.nextInt();

        if (pedidoService.cadastrarProduto(new Produto(id, nome, preco, quantidade))) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Produto é nulo!");
        }
    }

    public static void criarPedido(Scanner entrada, PedidoService pedidoService) {

        System.out.print("Digite o id do pedido: ");
        int id = entrada.nextInt();
        System.out.print("Digite o id do cliente dono do pedido: ");
        int idCliente = entrada.nextInt();
        System.out.print("Digite o id do produto: ");
        int idProduto = entrada.nextInt();
        System.out.println("Digite a quantidade do produto: ");
        int quantidadeProduto = entrada.nextInt();

        ItemPedido itemPedido = new ItemPedido(pedidoService.buscarProdutoPorId(idProduto), quantidadeProduto);

        if (pedidoService.cadastrarPedido(new Pedido(id, pedidoService.buscarClientePorId(idCliente), itemPedido), itemPedido)) {
            System.out.println("Pedido cadastrado com sucesso!");
        } else {
            System.out.println("Pedido é nulo!");
        }
    }

    public static void adicionarProdutoAoEstoque(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();
        System.out.print("Digite a quantidade a dar entrada: ");
        int quantidade = entrada.nextInt();
        if (pedidoService.adicionarProdutoAoEstoque(quantidade, id)) {
            System.out.println("Quantidade deve ser maior que zero!");
        }
    }

    public static void listarClientes(PedidoService pedidoService) {
        pedidoService.buscarTodosClientes().forEach(System.out::println);
    }

    public static void listarProdutos(PedidoService pedidoService) {
        pedidoService.buscarTodosClientes().forEach(System.out::println);
    }

    public static void listarPedidos(PedidoService pedidoService) {
        pedidoService.buscarTodosClientes().forEach(System.out::println);
    }

    public static void buscarClientePorId(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do cliente: ");
        int id = entrada.nextInt();
        System.out.println(pedidoService.buscarClientePorId(id));
    }

    public static void buscarProdutoPorId(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();
        System.out.println(pedidoService.buscarProdutoPorId(id));
    }

    public static void buscarPedidoPorId(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do pedido: ");
        int id = entrada.nextInt();
        System.out.println(pedidoService.buscarPedidoPorId(id));
    }

    public static void removerCliente(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do cliente: ");
        int id = entrada.nextInt();

        pedidoService.removerCliente(pedidoService.buscarClientePorId(id));
        System.out.println("Cliente removido com sucesso!");
    }

    public static void removerProduto(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do produto: ");
        int id = entrada.nextInt();

        pedidoService.removerProduto(pedidoService.buscarProdutoPorId(id));
        System.out.println("Produto removido com sucesso!");
    }

    public static void adicionarItensAoPedido(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do pedido que sera adicionado os produtos: ");
        int idPedido = entrada.nextInt();
        System.out.print("Digite o id do produto a ser adicionado: ");
        int idProduto = entrada.nextInt();
        System.out.print("Digite a quantidade: ");
        int quantidadeProduto = entrada.nextInt();

        if (pedidoService.adicionarItensAoPedido(pedidoService.buscarProdutoPorId(idProduto), quantidadeProduto, idPedido)) {
            System.out.println("Item adicionado com sucesso! ");
        } else {
            System.out.println("Nao foi possivel adicionar o item! ");
        }
    }

    public static void alterarStatusPedido(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do pedido a ser alterado: ");
        int idPedido = entrada.nextInt();
        entrada.nextLine();
        System.out.print("Digite o status atualizado do pedido (PENDENTE, PAGO, ENVIADO, ENTREGUE, CANCELADO): ");
        String status = entrada.nextLine();

        pedidoService.alterarStatusPedido(idPedido, status);
    }

    public static void cancelarPedido(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do pedido a ser cancelado: ");
        pedidoService.cancelarPedido(entrada.nextInt());
    }

    public static void confirmarPedido(Scanner entrada, PedidoService pedidoService) {
        System.out.print("Digite o id do pedido a ser finalizado: ");
        pedidoService.confirmarPedido(entrada.nextInt());
    }
}


