package entities;

import java.util.Objects;

public class ItemPedido {
    private final Produto produto;
    private final int quantidadeProduto;
    private final double precoProdutoMomentoCompra;

    public ItemPedido(Produto produto, int quantidadeProduto) {
        if (quantidadeProduto < 0) throw new IllegalArgumentException("Valor invalido!");
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
        this.precoProdutoMomentoCompra = produto.getPreco();
    }

    public ItemPedido(Produto produto, int quantidadeProduto, double precoProdutoMomentoCompra) {
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
        this.precoProdutoMomentoCompra = precoProdutoMomentoCompra;
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
        return Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(produto);
    }

    //Ajustar esse toString
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItemPedido{");
        sb.append("produto=").append(produto);
        sb.append(", quantidadeProduto=").append(quantidadeProduto);
        sb.append(", precoProdutoMomentoCompra=").append(precoProdutoMomentoCompra);
        sb.append('}');
        return sb.toString();
    }
}
