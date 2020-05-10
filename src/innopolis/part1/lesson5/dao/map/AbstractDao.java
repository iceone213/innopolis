package innopolis.part1.lesson5.dao.map;

import innopolis.part1.lesson5.dao.GenericDAO;
import innopolis.part1.lesson5.exception.PersistException;
import innopolis.part1.lesson5.model.Identified;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public abstract class AbstractDao<T extends Identified<PK>, PK extends Serializable>
        implements GenericDAO<T, PK> {

    private Class<T> clazz;

    protected Map<PK, T> elements;

    public AbstractDao(Class<T> elementClass, Map<PK, T> map) {
        this.clazz = elementClass;
        this.elements = map;
    }

    @Override
    public T getByPK(PK key) throws PersistException {
        T ob = elements.get(key);
        return ob;
    }

    @Override
    public Collection<T> getAll() throws PersistException {
        return elements.values();
    }

    @Override
    public T save(T ob) throws PersistException {
        elements.put(ob.getId(), ob);
        return ob;
    }

    @Override
    public T update(T ob) throws PersistException {
        elements.put(ob.getId(), ob);
        return ob;
    }

    @Override
    public T update(PK key, T ob) throws PersistException {
        elements.put(key, ob);
        return ob;
    }

    @Override
    public T delete(T ob) throws PersistException {
        elements.remove(ob);
        return ob;
    }

    @Override
    public T deleteByPK(PK key) throws PersistException {
        return elements.remove(key);
    }

    @Override
    public Collection<T> addAll(Collection<T> obs) throws PersistException {
        for (T ob : obs) {
            elements.put(ob.getId(), ob);
        }
        return obs;
    }

}