package hr.algebra.hostandtravel.repository;

import java.util.List;

public interface Repository <T> {
    List<T> getAllEntities();
    T getEntity(int id);
    T saveEntity(T entity);
    Boolean deleteEntity(int id);
}
