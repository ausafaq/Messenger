package org.ausaf.dev.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.ausaf.dev.messenger.database.DatabaseClass;
import org.ausaf.dev.messenger.exception.DataNotFoundException;
import org.ausaf.dev.messenger.model.Message;

public class MessageService {
	
	private static Map<Long, Message> messages = DatabaseClass.getMessages();
	
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}
	
	// Filtering by year
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	// Pagination	
	public List<Message> getAllMessagesPaginated(int start, int size) {
		List<Message> list = new ArrayList<Message>(messages.values());
		if(start + size > list.size()) {
			return new ArrayList<Message>();
		}
		return list.subList(start, start + size);
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null) {
			throw new DataNotFoundException("Message with id " + id + " not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}

}
