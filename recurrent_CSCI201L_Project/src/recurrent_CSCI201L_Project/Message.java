package recurrent_CSCI201L_Project;

import java.sql.Date;

public class Message {
	private String sender;
	private String receiver;
	private String title;
	private String message;
	private boolean read;
	private Date date;
	private int id;
	
	public Message(String sender, String receiver, String title, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.message = message;
		this.read = false;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean isRead() {
		return read;
	}
	
	public void markAsRead() {
		read = true;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int ID) {
		this.id = ID;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
}
