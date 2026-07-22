package services;

import entities.ItemPedido;
import entities.Pedido;
import exceptions.OperacaoArquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PersistenceItemPedido {
    public static void salvar(Pedido pedido) {
        try {
            Path caminho = Path.of("data\\itens_pedido.csv");
            if (Files.notExists(caminho.getParent())) {
                Files.createDirectories(caminho.getParent());
            }
            for (ItemPedido item : pedido.buscarItensPedido()) {
                Files.writeString(caminho, pedido.getId() + ";" + item.getProduto().getId() + ";"
                                + item.getQuantidadeProduto() + ";"
                                + item.getPrecoProdutoMomentoCompra()
                                + System.lineSeparator(),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }
}
