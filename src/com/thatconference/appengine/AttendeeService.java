package com.thatconference.appengine;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.users.User;

public class AttendeeService {

	public AttendeeRegistrationResult register(User user){
		
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
				
		try{
			
			Key userKey = KeyFactory.createKey("User", user.getEmail());
		
			Query query = new Query("Attendee", userKey);
			PreparedQuery preparedQuery = datastoreService.prepare(query);
			Entity attendee = preparedQuery.asSingleEntity();

			if(attendee == null){
				attendee = new Entity("Attendee", userKey);
				attendee.setProperty("DateRegistered", new Date());
				datastoreService.put(attendee);
							
				transaction.commit();
				
				return new AttendeeRegistrationResult();
			}
			
			return new AttendeeRegistrationResult(String.format("%s has already registered", user.getEmail()));
						
		}catch (RuntimeException ex){
			return new AttendeeRegistrationResult(ex.getMessage());
		}finally{
			if(transaction != null && transaction.isActive())
				transaction.rollback();
		}
	}
}
