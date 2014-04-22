package serverStuff;

public class Person {

	String Name, Vorname, Username, Email, Password, loginUser, loginPassword;
	
	public String getLoginUserName() {
		return loginUser;
	}
	
	public String getLoginPassword() {
		return loginPassword;
	}
	
	public void setLoginUserName(String Username) {
		loginUser=Username;
	}
	
	public void setLoginPassword(String Password) {
	    loginPassword=Password;
	}
	
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return Vorname;
	}

	/**
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		Vorname = vorname;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return Username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		Username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

	
	
}
