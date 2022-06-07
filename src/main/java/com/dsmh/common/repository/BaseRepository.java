package com.dsmh.common.repository;

/**
 * @author TeamScorpio
 * @since 2022/3/5
 */
public interface BaseRepository<T> {
    void create(T entity);

    void modify(T entity);

    void remove(T entity);

}
