package recurrent_CSCI201L_Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParseConfig {
	private String ipAddress;
	private String dataBase;
	private String user;
	private String password;

	public ParseConfig() throws IOException {
		FileReader myFile = new FileReader(
				"C:\\Users\\Mommy\\CSCI201Final\\recurrent_CSCI201L_Project\\src\\config.txt");

		BufferedReader bufferReader = new BufferedReader(myFile);
		String line = bufferReader.readLine();
		ipAddress = line.substring(10);

		String line2 = bufferReader.readLine();
		dataBase = line2.substring(3);

		String line3 = bufferReader.readLine();
		user = line3.substring(5);

		String line4 = bufferReader.readLine();
		password = line4.substring(9);

	}

	public String getAddress() {
		return ipAddress;
	}

	public String getuser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getDataBase() {
		return dataBase;
	}

	public static void main(String[] args) throws IOException {
		ParseConfig pc = new ParseConfig();
	}
}
