package hr.algebra.hostandtravel.repository;

import java.util.List;

public interface Repository <T> {
    List<T> getAllEntities();
    T getEntity(int id);
    Boolean updateEntity(T entity);
    T insertEntity(T entity);
    Boolean deleteEntity(int id);
}
