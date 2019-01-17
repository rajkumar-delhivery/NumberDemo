
package com.demo.number.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.demo.number.dao.GenericDao;


/**
 * Implements the Generic methods for the database related operations
 * 
 * @author raj
 *
 * @param <E>
 * @param <PK>
 */

public abstract class GenericDaoImpl<E, PK extends Serializable> extends HibernateDaoSupport
		implements GenericDao<E, PK> {

	

	@SuppressWarnings("unchecked")
	@Override
	public PK save(E newInstance) {
		return (PK) getHibernateTemplate().save(newInstance);
	}

	@Override
	public E findById(PK id) {
		return (E) getHibernateTemplate().get(getEntityClass(), id);
	}

	static int count = 0;

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		return (List<E>) getHibernateTemplate().findByCriteria(createDetachedCriteria());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllByProperty(String propertyName, Object value) {
		System.out.println("criteria : start");
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq(propertyName, value));
		System.out.println("criteria : " + criteria.toString());
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllByProperties(Map<String, Object> mapPropValue) {
		System.out.println("criteria : start");
		DetachedCriteria criteria = createDetachedCriteria();
		for(Map.Entry<String, Object> entry :  mapPropValue.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(),entry.getValue()));
		}
		System.out.println("criteria : " + criteria.toString());
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public /* E */ List<E> findByProperty(String propertyName, Object value) {
		// System.out.println("criteria : start2222 "+value);
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq(propertyName, value));
		// System.out.println("criteria : " + criteria.toString());
		return (List<E>) /* (E) */ getHibernateTemplate().findByCriteria(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllByPropertiesUsingOrderBy(String value1, String value2, String value) {
		System.out.println("criteria : start");
		DetachedCriteria criteria = createDetachedCriteria();

		criteria.addOrder(Order.desc(value1));
		criteria.add(Restrictions.eq(value2, value));
		System.out.println("criteria : " + criteria.toString());
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllByPropertiesUsingOrderBy(String value1, String value2, long value) {
		System.out.println("criteria : start");
		DetachedCriteria criteria = createDetachedCriteria();

		criteria.addOrder(Order.desc(value1));
		criteria.add(Restrictions.eq(value2, value));
		System.out.println("criteria : " + criteria.toString());
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);
	}

	
	
	@Override
	public List<E> findAllWithinRowLimit(int rowLimit) {
		getHibernateTemplate().setMaxResults(rowLimit);
		return (List<E>) getHibernateTemplate()
				.find("select obj from " + getEntityClass().getSimpleName() + " obj order by obj.createDate");
	}

	@SuppressWarnings("unchecked")
	public List<E> findByExample(E object) {
		List<E> resultList = getHibernateTemplate().findByExample(object, 0, 1);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<E> findByExample(E object, int firstResult, int maxResults) {
		List<E> resultList = getHibernateTemplate().findByExample(object, firstResult, maxResults);
		return resultList;
	}

	public void update(E transientObject) {
		getHibernateTemplate().update(transientObject);
	}

	public void saveOrUpdate(E transientObject) {
		getHibernateTemplate().saveOrUpdate(transientObject);
	}

	public void delete(E persistentObject) {
		getHibernateTemplate().delete(persistentObject);
	}

	protected abstract Class<E> getEntityClass();

	protected DetachedCriteria createDetachedCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getEntityClass());
		return detachedCriteria;

	}

	@Override
	public List<E> findAllUsingINClause(String property, List<Object> values) {

		DetachedCriteria criteria = createDetachedCriteria();

		criteria.add(Restrictions.in(property, values));
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);

	}

	@Override
	public List<E> findAllUsingINClauseAndWithPropertyValue(String property1, Object value1, String property2,
			List<Object> values) {

		DetachedCriteria criteria = createDetachedCriteria();

		criteria.add(Restrictions.and(Restrictions.eq(property1, value1), Restrictions.in(property2, values)));
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);

	}
	
	@Override
	public List<E> findAllUsingINClauseAndWithPropertyValuegt(String property1, Object value1, String property2,
			List<Object> values) {

		DetachedCriteria criteria = createDetachedCriteria();

		criteria.add(Restrictions.and(Restrictions.gt(property1, value1), Restrictions.in(property2, values)));
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);

	}

	@Override
	public List<E> findValuesBasedOnLeftJoin(Class dao, String key) {
		DetachedCriteria criteria1 = DetachedCriteria.forClass(dao).setProjection(Property.forName(key));

		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Property.forName(key).in(criteria1));

		return (List<E>) getHibernateTemplate().findByCriteria(criteria);

	}

	@Override
	public List<E> findValuesBasedOnLeftJoin(Class dao1, Class dao2, String key) {
		DetachedCriteria criteria1 = DetachedCriteria.forClass(dao1).setProjection(Property.forName(key));

		DetachedCriteria criteria = DetachedCriteria.forClass(dao2);
		criteria.add(Property.forName(key).in(criteria1));

		return (List<E>) getHibernateTemplate().findByCriteria(criteria);

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllByPropertiesUsingOrderBy(String key1, String key2, Object value2, String key3,
			Object value3) {
		DetachedCriteria criteria = createDetachedCriteria();

		criteria.addOrder(Order.desc(key1));
		criteria.add(Restrictions.and(Restrictions.eq(key2, value2), Restrictions.eq(key3, value3)));
		return (List<E>) getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllByPropertiesUsingOrderByWithLimit(String key1, String key2, Object value2, String key3,
			Object value3, int pageNo, int recordSize) {
		Criteria cr = createDetachedCriteria().getExecutableCriteria(getSessionFactory().getCurrentSession());
		cr.addOrder(Order.desc(key1));
		cr.add(Restrictions.and(Restrictions.eq(key2, value2), Restrictions.eq(key3, value3)));
		cr.setFirstResult((pageNo - 1) * recordSize);
		cr.setMaxResults(recordSize);
		return (List<E>) cr.list();
	}

	@Override
	public List<E> findAllWithinRowLimit(String keyToSort, Map<String, Object> mapPropValue, int pageNo, int rowLimit) {

		Session session = null;
		try {
			session = getSessionFactory().openSession();/* getSessionFactory().openSession() */
			Criteria cr = createDetachedCriteria().getExecutableCriteria(session);
			cr.addOrder(Order.desc(keyToSort));
			for(Map.Entry<String, Object> entry :  mapPropValue.entrySet()) {
				cr.add(Restrictions.eq(entry.getKey(),entry.getValue()));
			}
			cr.setFirstResult((pageNo - 1) * rowLimit);
			cr.setMaxResults(rowLimit);
			return (List<E>) cr.list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	
	@Override
	public void deleteAll(List<E> list) {
		System.out.println("criteria : start");
		DetachedCriteria criteria = createDetachedCriteria();

		getHibernateTemplate().deleteAll(list);;
	}
	
	
	
	@Override
	public List<E> findAllByPropertiesWithBetween(Map<String, Object> mapPropValue,  String betweenColumnKey, Object value1, Object value2){
		
		DetachedCriteria cr = createDetachedCriteria();
		for(Map.Entry<String, Object> entry :  mapPropValue.entrySet()) {
			cr.add(Restrictions.eq(entry.getKey(),entry.getValue()));
		}
		
		cr.add(Restrictions.between(betweenColumnKey, value1, value2));
		
		return (List<E>) getHibernateTemplate().findByCriteria(cr);
	}
	
	
}
