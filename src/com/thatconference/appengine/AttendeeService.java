package com.thatconference.appengine;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.users.User;

public class AttendeeService {

	public AttendeeRegistrationResult register(User user, String firstName, String lastName){
		
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
				
		try{
			
			Key attendeeKey = KeyFactory.createKey("Attendee", user.getEmail());
			Entity attendee = null;
			
			try {
				attendee = datastoreService.get(attendeeKey);
			} catch (EntityNotFoundException e) {
				
				attendee = new Entity("Attendee",user.getEmail());
				attendee.setProperty("FirstName", firstName);
				attendee.setProperty("LastName", lastName);
				attendee.setProperty("DateRegistered", new Date());
				datastoreService.put(attendee);
				
				transaction.commit();
				
				return new AttendeeRegistrationResult();
			}

			return new AttendeeRegistrationResult(String.format("%s has already registered", attendee.getKey().getName()));
						
		}catch (RuntimeException ex){
			return new AttendeeRegistrationResult(ex.getMessage());
		}finally{
			if(transaction != null && transaction.isActive())
				transaction.rollback();
		}
	}
}

