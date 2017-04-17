package recurrent_CSCI201L_Project;

public class Message {
	private String sender;
	private String receiver;
	private String title;
	private String message;
	private boolean read;
	
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
}
