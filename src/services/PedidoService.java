package services;

import repository.RepositorioCliente;
import repository.RepositorioPedido;
import repository.RepositorioProduto;

public class PedidoService {
    private RepositorioPedido repositorioPedido = new RepositorioPedido();
    private RepositorioCliente repositorioCliente = new RepositorioCliente();
    private RepositorioProduto repositorioProduto = new RepositorioProduto();
}
