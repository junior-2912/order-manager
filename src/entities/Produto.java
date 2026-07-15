package entities;

public class Produto {
    private final int id;
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public Produto(int id, String nome, double preco, int quantidadeEstoque) {
        if (preco <= 0 || quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Valores invalidos!");
        }
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void entradaEstoque(int quantidadeEstoque) {
        setQuantidadeEstoque(this.quantidadeEstoque + quantidadeEstoque);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                " - " + nome +
                " - " + String.format("%.2f", preco) +
                " - Quantidade: " + quantidadeEstoque;
    }
}
