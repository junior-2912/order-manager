package entities;

public class ItemPedido {
    private Produto produto;
    private int quantidadeProduto;
    private double precoProdutoMomentoCompra;

    public ItemPedido(Produto produto, int quantidadeProduto) {
        if (quantidadeProduto < 0) throw new IllegalArgumentException("Valor invalido!");
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido that = (ItemPedido) o;
        return quantidadeProduto == that.quantidadeProduto && Double.compare(precoProdutoMomentoCompra, that.precoProdutoMomentoCompra) == 0 && produto.equals(that.produto);
    }

    @Override
    public int hashCode() {
        int result = produto.hashCode();
        result = 31 * result + quantidadeProduto;
        result = 31 * result + Double.hashCode(precoProdutoMomentoCompra);
        return result;
    }
}
