package ru.job4j.articles.store;

import java.util.List;

public interface Store<T> {
    T save(T model);

    boolean saveAll(List<T> models);

    List<T> findAll();
}
