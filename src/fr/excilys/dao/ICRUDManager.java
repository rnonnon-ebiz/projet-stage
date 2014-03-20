/**
 * 
 */
package fr.excilys.dao;

/**
 * @author rnonnon
 * 
 */
public interface ICRUDManager<T> {

    public void create(T object);

    public T find(T object);

    public void update(T object);

    public void delete(T object);

}