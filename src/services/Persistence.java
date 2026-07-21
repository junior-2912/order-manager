package services;

public interface Persistence<T> {
    void salvar(T item);
}
