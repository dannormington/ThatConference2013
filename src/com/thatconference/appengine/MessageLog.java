package com.thatconference.appengine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageLog {

	private static final String LOG_NAME = "That Conference Service Log";

	private static final Logger log = Logger.getLogger(LOG_NAME);

	static {
		log.setLevel(java.util.logging.Level.FINEST);
	}

	public static void log(String message){
		log.log(Level.INFO, message);
	}

	public static void log(Exception e){

		if(e == null)
			return;

		log.log(Level.SEVERE, e.getClass().getName(), e);
	}
}
