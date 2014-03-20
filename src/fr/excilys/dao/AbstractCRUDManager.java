/**
 * 
 */
package fr.excilys.dao;

import java.sql.SQLException;

/**
 * @author rnonnon
 * 
 */
public abstract class AbstractCRUDManager<T> implements ICRUDManager<T> {

    protected IConnectionManager connectionManager = ConnectionManager
	    .getInstance();

    @Override
    public abstract void create(final T object) throws SQLException;

    @Override
    public abstract void find(final T object) throws SQLException;

    // @Override
    // public T find(final T object) {
    // return find(object, "id");
    // }

    // public T find(final T object, String idField) {
    // StringBuffer query = new StringBuffer();
    //
    // String objectClassName = object.getClass().getName();
    // final Field[] fields = object.getClass().getDeclaredFields();
    // int idIndex = getIndexOf(idField, fields);
    // Method[] methods = object.getClass().getMethods();
    // isGetter(methods[0]);
    //
    // //2 cases
    // //if "id" attribute found -> search on id
    // if(idIndex != -1){
    // query.append("SELECT o FROM ");
    // query.append(objectClassName);
    // query.append(" WHERE o.id = ");
    // query.append(object.)
    // }
    // //if not -> search on all attributes
    // else{
    //
    // }
    // return null;
    // }
    //
    // private int getIndexOf(String name, Field[] fields) {
    // int index = 0;
    // boolean found = false;
    // while ((index < fields.length) && !found) {
    // if (fields[index].getName().equals(name)) {
    // found = true;
    // }
    // else {
    // ++index;
    // }
    // }
    // if (!found) {
    // index = -1;
    // }
    // return index;
    // }

    @Override
    public abstract void update(final T object) throws SQLException;

    @Override
    public abstract void delete(final T object) throws SQLException;

    public <idType> String genericFindQuery(final Class classObject, idType id) {
	StringBuffer query = new StringBuffer();
	query.append("SELECT o FROM");
	query.append(classObject.getName());
	query.append(" WHERE o.id = ");
	query.append(id);
	return query.toString();
    }

    // public String genericInsertQuery(final T object){
    // StringBuffer query = new StringBuffer();
    // query.append("INSERT INTO ");
    // query.append(object.getClass().getName());
    // query.append(" ( ");
    // final Field[] fields = object.getClass().getDeclaredFields();
    // for(int i=0;i<fields.length;++i){
    // query.append()
    //
    // }
    // query.append(" ) ");
    // return "";
    // }
}
