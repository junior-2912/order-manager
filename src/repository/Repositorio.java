package repository;

import java.util.List;

public interface Repositorio <T> {
    boolean salvar(T item);

    List<T> buscarTodos();

    T buscarPorId(int id);

    void remover(T item);
}
