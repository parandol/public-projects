package kr.ejsoft.spring.with.vuejs.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "Home...");
		mav.setViewName("index");
		return mav;
	}
	
	
	@RequestMapping(value="/data", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> data(@RequestParam(name="msg") String msg) {
		logger.debug("Message : {}", msg);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Server message with " + msg);
		return map;
	}
}
