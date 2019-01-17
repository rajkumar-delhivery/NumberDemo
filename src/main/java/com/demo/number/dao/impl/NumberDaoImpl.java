package com.demo.number.dao.impl;

import org.springframework.stereotype.Repository;
import com.demo.number.dto.Number;
import com.demo.number.dao.NumberDao;

/**
 *Extend the GenericDaoImpl for the basic DB operations
 * 
 * @author raj
 *
 *
 *
 */

@Repository("numberDao")
public class NumberDaoImpl extends GenericDaoImpl<Number, Integer> implements NumberDao{

	@Override
	protected Class<Number> getEntityClass() {
		// TODO Auto-generated method stub
		return Number.class;
	}

}
