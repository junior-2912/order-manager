package entities;

public class ItemPedido {
    private Produto produto;
    private int quantidadeProduto;
    private double precoProdutoMomentoCompra;

    public ItemPedido(Produto produto, int quantidadeProduto) {
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
        this.precoProdutoMomentoCompra = produto.getPreco();
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public double getPrecoProdutoMomentoCompra() {
        return precoProdutoMomentoCompra;
    }

    public double calcularSubTotal() {
        return precoProdutoMomentoCompra * quantidadeProduto;
    }


}
