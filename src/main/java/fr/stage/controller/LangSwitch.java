package fr.stage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/langSwitch")
public class LangSwitch {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView langSwitch(
	    @RequestHeader(value = "referer", required = true) final String referer) {
	return new ModelAndView("redirect:" + referer);
    }
}
