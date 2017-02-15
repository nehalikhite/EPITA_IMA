/**
 * 
 */
package fr.epita.ima.datamodel;

/**
 * This is the main business object. This object shall be created, manipulated and saved in the database.
 * @author Neha
 *
 */
public class Identity {
	private int id;
	private String name;
	private String email;
	private String bdate;

	/**
	 * This is the constructor for class Identity. It is used to instantiate
	 * objects of the type Identity
	 */
	public Identity(int iden_uid, String iden_Name, String iden_email, String bdate) {
		this.id = iden_uid;
		this.name = iden_Name;
		this.email = iden_email;
		this.bdate = bdate;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method is used to set the name of an Identity
	 */
	public void setName(String iden_Name) {
		this.name = iden_Name;
	}

	/**
	 * This method returns the name of an Identity
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is used to set the email of an Identity
	 */
	public void setEmail(String iden_email) {
		this.email = iden_email;
	}

	/**
	 * This method returns the email of an Identity
	 */
	public String getEmail() {
		return email;

	}

	/*
	 * See java.lang.Object#toString() This method returns the object 'Identity'
	 * in the form of a String
	 */
	public String toString() {
		return "[" + id + " " + name + " " + email + "]";

	}

	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bday) {
		this.bdate = bday;
	}
}
