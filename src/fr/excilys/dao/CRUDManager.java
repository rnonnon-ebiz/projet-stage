/**
 * 
 */
package fr.excilys.dao;


/**
 * @author rnonnon
 * 
 */
public class CRUDManager<T> implements ICRUDManager<T> {

    IConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public void create(T object) {

    }

    @Override
    public T find(T object) {
	// Field[] fields = object.getClass().getFields();
	// int idIndex = 0;
	// for(int i=0;i<fields.length;++i){
	// if(fields[i].getAnnotations().equals("@ID"))
	// }
	return null;
    }

    @Override
    public void update(T object) {
	// TODO Auto-generated method stub

    }

    @Override
    public void delete(T object) {
	// TODO Auto-generated method stub

    }

}
