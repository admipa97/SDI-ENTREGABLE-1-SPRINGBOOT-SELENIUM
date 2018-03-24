package com.uniovi.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerService {

	private Logger log;

	public LoggerService(Object object) {

		File file = new File("/log");
		if (!file.exists()) {
			file.mkdir();
		}
		log = LoggerFactory.getLogger(object.getClass());
	}

	public void info(String message) {
		log.info(message);
	}

	public void error(String message) {
		log.error(message);
	}

	public void debug(String message) {
		log.debug(message);
	}
}
