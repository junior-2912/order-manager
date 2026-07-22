package services;

import entities.Pedido;
import exceptions.OperacaoArquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;

public class PersistencePedido implements Persistence<Pedido> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public void salvar(Pedido item) {
        Path caminho = Path.of("data\\pedidos.csv");
        try {
            if (Files.notExists(caminho.getParent())) {
                Files.createDirectories(caminho.getParent());
            }
            Files.writeString(caminho,item.getId() + ";" + item.getCliente().getId() + ";" + item.getDataPedido() + ";" + item.getStatusPedido() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            PersistenceItemPedido itemPedido = new PersistenceItemPedido();
            itemPedido.salvar(item);
        } catch (IOException e) {
            throw new OperacaoArquivo(e.getMessage());
        }
    }
}
