package services;

import entities.Produto;
import exceptions.OperacaoArquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PersistenceProduto implements Persistence<Produto> {
    @Override
    public void salvar(Produto item) {
        Path caminho = Path.of("data\\produtos.csv");
        try {
            if (Files.notExists(caminho.getParent())) {
                Files.createDirectories(caminho.getParent());
            }
            Files.writeString(caminho, + item.getId() + ";" + item.getNome() + ";" + item.getPreco() + ";" + item.getQuantidadeEstoque() + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }
}
