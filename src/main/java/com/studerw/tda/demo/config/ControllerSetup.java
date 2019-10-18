package com.studerw.tda.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerSetup {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerSetup.class);

	public ControllerSetup(){
		LOGGER.info("creating ControllerSetup -- should only see me once.");
	}

	@InitBinder
    public void initBinder(WebDataBinder binder) {
		LOGGER.info("Initiating WebBinder with StringTrimmer... - How many times am I called???");
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }
}
