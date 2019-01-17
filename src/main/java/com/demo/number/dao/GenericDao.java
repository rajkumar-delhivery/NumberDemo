package com.demo.number.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;



@Configuration
@EnableTransactionManagement
public interface GenericDao<E,PK  extends Serializable> {
    PK save(E newInstance);
    
    @Transactional(readOnly = false)
    void update(E transientObject);    
    /**
     * Use this API to trigger the update query immediately 
     * This should be used for special case only
     * @param transientObject
     */
    
    @Transactional(readOnly = false)
    void saveOrUpdate(E transientObject);
    
    void delete(E persistentObject);
    E findById(PK id);
    List<E> findAll();
    List<E> findAllByProperty(String propertyName,Object value);
    List<E> findAllByPropertiesUsingOrderBy(String value1,String value2,String value);
    List<E> findAllByPropertiesUsingOrderBy(String value1,String value2,long value);
    List<E> findAllWithinRowLimit(int rowLimit);
    List<E> findByProperty(String propertyName,Object value);

	List<E> findAllUsingINClause(String property, List<Object> values);

	List<E> findAllUsingINClauseAndWithPropertyValue(String property1, Object value1, String property2,
			List<Object> values);


	List<E> findValuesBasedOnLeftJoin(Class dao, String key);

	List<E> findValuesBasedOnLeftJoin(Class dao1, Class dao2, String key);

	List<E> findAllByPropertiesUsingOrderBy(String key1, String key2, Object value2, String key3, Object value3);


	List<E> findAllByPropertiesUsingOrderByWithLimit(String key1, String key2, Object value2, String key3,
			Object value3, int pageNo, int recordSize);

	List<E> findAllWithinRowLimit(String keyToSort, Map<String, Object> mapPropValue, int pageNo, int rowLimit);

	List<E> findAllUsingINClauseAndWithPropertyValuegt(String property1, Object value1, String property2,
			List<Object> values);

	void deleteAll(List<E> list);
	
	List<E> findAllByPropertiesWithBetween(Map<String, Object> mapPropValue,  String betweenColumn, Object value1, Object value2);

	List<E> findAllByProperties(Map<String, Object> mapPropValue);

}
