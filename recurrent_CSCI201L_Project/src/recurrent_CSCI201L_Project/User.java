package recurrent_CSCI201L_Project;

public class User {
	protected String username;
	protected String password;
	protected String image;
	protected String emailAddress;
	protected String userType;

	public User(String username, String password, String image, String emailAddress) {
		this.username = username;
		this.password = password;
		this.image = image;
		this.emailAddress = emailAddress;
		this.userType = null;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getImage() {
		return image;
	}

	public String getUserType() {
		return userType;
	}
}
