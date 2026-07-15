package entities;

import java.util.Objects;

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

    public void atualizarEstoque(int quantidadeProduto) {
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeProduto);
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
}
