package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.Contacts;
import com.takeoff.repository.ContactsRepository;

@Service
public class ContactsService {
	
	@Autowired
	ContactsRepository contactsRepository;
	
	public String addContacts(String name1,String contact1,String name2,String contact2,String name3,String contact3)
	{
		if(name1.trim().length() > 0 && contact1.trim().length() == 10)
		{
			Contacts contacts=new Contacts();
			contacts.setName(name1);
			contacts.setContact(contact1);
			contactsRepository.save(contacts);
		}
		
		if(name2.trim().length() > 0 && contact2.trim().length() == 10)
		{
			Contacts contacts=new Contacts();
			contacts.setName(name2);
			contacts.setContact(contact2);
			contactsRepository.save(contacts);
		}
		
		if(name3.trim().length() > 0 && contact3.trim().length() == 10)
		{
			Contacts contacts=new Contacts();
			contacts.setName(name3);
			contacts.setContact(contact3);
			contactsRepository.save(contacts);
		}
		return "{\"refererid\":\"TO10001\"}";
	}

	public List<Contacts> getContacts() {
		return contactsRepository.findAll();
	}

}
