package shortvideo.declantea.me.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractHibernateDao<T extends Serializable, K extends Serializable> {

    private final Class<T> tClass;

    @Autowired
    private SessionFactory sessionFactory;

    public AbstractHibernateDao() {
        this.tClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T findOne(K id) {
        return getCurrentSession().get(tClass, id);
    }

    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + tClass.getName()).list();
    }

    public T create(T entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public T update(T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(K entityId) {
        T entity = findOne(entityId);
        delete(entity);

    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return this.getCurrentSession().getCriteriaBuilder();
    }

    protected CriteriaQuery getCriteriaQuery() {
        return this.getCriteriaBuilder().createQuery(tClass);
    }

    protected Root getRoot() {
        return this.getCriteriaQuery().from(tClass);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}

