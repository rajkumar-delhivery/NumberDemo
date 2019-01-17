package com.demo.number.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.number.dao.NumberDao;
import com.demo.number.dto.Number;
import com.demo.number.service.NumberService;

/**
 * Service class for the Number Model/Entity
 * @author raj
 *
 */

@Service
public class NumberServiceImpl implements NumberService{

	@Autowired
	NumberDao numberDao;
	
	Logger log = LoggerFactory.getLogger(NumberServiceImpl.class);
	
	
	/**
	 * Fetch the Number Object from the DB.
	 * Then increase the number field value by 1 and save the updated Object in DB.
	 */
	
	@Transactional(readOnly=false)
	@Override
	public  Number incrementNumber() {
		Number number = null;
		
		List<Number> numbers  = null;
			try {
				numbers = numberDao.findAll();
			} catch (Exception e) {
				log.error("Ex "+e.getMessage());
			}
			
			if(numbers == null || numbers.isEmpty()) {
				log.info("DB is empty, creating an entry");
				number = new Number(1);
				numberDao.save(number);
				log.info("Data Saved");
				return number;
			}
			else {
				
				number = numbers.get(0);
				log.info("Number field value >> "+number.getNumber());
				number.setNumber(number.getNumber() + 1);
				numberDao.saveOrUpdate(number);
				log.info("Number field new value >> "+number.getNumber());
				return number;
			}
	}

	
}
