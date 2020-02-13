package multi.database.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import multi.database.service.WithDaoService;

@Controller
public class MultiDatabaseController {
	private static final Logger logger = LoggerFactory.getLogger(MultiDatabaseController.class);
	
	@Autowired
	private WithDaoService withDaoService;

	@RequestMapping(value="/withdao", method=RequestMethod.GET)
	public ModelAndView home() {

		withDaoService.moveDb1toDb2("model_id");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("withdao");
		return mav;
	}

	
	
	
	@Autowired
	private WithDaoService withMapperService;
	
	@RequestMapping(value="/withmapper", method=RequestMethod.GET)
	public ModelAndView account() {
		withMapperService.moveDb1toDb2("model_id");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("withmapper");

		return mav;
	}
}