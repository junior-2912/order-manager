package application;

import entities.Cliente;
import entities.ItemPedido;
import entities.Pedido;
import entities.Produto;
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
        System.out.println("13 - Remover pedido");
        System.out.println("14 - Adicionar items ao pedido");
        System.out.println("15 - Alterar status do pedido");
        System.out.println("16 - Cancelar pedido");
        System.out.println("17 - Confirmar pedido");
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
}


