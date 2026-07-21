package services;

import entities.Cliente;
import exceptions.OperacaoArquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PersistenceCliente implements Persistence<Cliente> {
    @Override
    public void salvar(Cliente item) {
        Path caminho = Path.of("data\\clientes.csv");
        try {
            if (Files.notExists(caminho.getParent())) {
                Files.createDirectories(caminho.getParent());
            }
            Files.writeString(caminho, item.getId() + ";" + item.getNome() + ";" + item.getEmail() + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }
}
