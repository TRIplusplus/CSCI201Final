package recurrent_CSCI201L_Project;

public class Renter {
	private String username;
	private String password;
	private String emailAddress;

	public Renter(String username, String password, String emailAddress) {
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
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
}
