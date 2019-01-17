package com.demo.number.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.number.dto.Response;
import com.demo.number.service.NumberService;
import com.demo.number.dto.Number;

/**
 * The Controller contains 2 api. One for home Page and other One is
 * '/number-incrementor'
 * 
 * @author raj
 * @version 1.0
 * 
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private NumberService numberService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	/**
	 * The Rest-APi increase the Number Object field value By One.
	 * 
	 * @return Incremented Number Object
	 */
	@RequestMapping(value = "/number-incrementor", method = RequestMethod.POST)

	public @ResponseBody Object incrementNumber() {

		Response response = new Response();
		synchronized (this) {
			try {

				Number number = numberService.incrementNumber();
				response.setStatus("success");
				response.setResponse(number);
				return response;

			} catch (Exception e) {
				response.setStatus("failure");
				response.setResponse(e.getMessage());
				return response;
			}
		}
	}

}
