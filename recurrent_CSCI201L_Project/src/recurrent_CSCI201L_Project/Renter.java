package recurrent_CSCI201L_Project;

public class Renter extends User {
	public Renter(String username, String password, String image, String emailAddress) {
		super(username, password, image, emailAddress);
		userType = "renter";
	}
}
