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

	public AttendeeRegistrationResult register(User user){
		
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		
		try{
			
			Key memberKey = KeyFactory.createKey("Member", user.getEmail());
			Entity member = null;
			
			try {
				member = datastoreService.get(memberKey);
				return new AttendeeRegistrationResult(String.format("%s has already registered", user.getEmail()));
				
			} catch (EntityNotFoundException e) {
				
				member = new Entity(memberKey);
				member.setProperty("DateRegistered", new Date());
				
				datastoreService.put(transaction, member);
				transaction.commit();
				
				return new AttendeeRegistrationResult();
			}
			
			
		}catch (RuntimeException ex){
			return new AttendeeRegistrationResult(ex.getMessage());
		}finally{
			if(transaction != null && transaction.isActive())
				transaction.rollback();
		}
	}
}
