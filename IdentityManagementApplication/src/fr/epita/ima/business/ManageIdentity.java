/**
 * 
 */
package fr.epita.ima.business;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.derby.client.am.SqlException;

import fr.epita.ima.datamodel.Identity;
import fr.epita.ima.dbconnection.IdentityDAO;

/**
 * This is the business class that interfaces between the view and the database.
 * It helps create, update, delete and list the identities. It main purpose is
 * to manage the business object correctly and isolate the data access from the
 * user interface.
 * 
 * @author Neha
 *
 */
public class ManageIdentity {

	// Creation of dao reference;
	IdentityDAO dao;

	
	/**
	 * This method is the interface between the user and the database that helps create the Identity object.
	 * 
	 * @param name Identity name
	 * @param email Identity email id
	 * @param bdate Birthdate (YYY-MM-DD)
	 */
	public void create(String name, String email, String bdate) {
		dao = new IdentityDAO();
		try {
			int nextId = generateId();
			Identity identity = new Identity(nextId, name, email, bdate);
			dao.write(identity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * This method gets the last record in the database and increments the id by 1.
	 * @return
	 * @throws SQLException
	 */
	private int generateId() throws SQLException {
		ArrayList<Identity> listIdentities = (ArrayList<Identity>) dao.readAllIdentities();
		Identity lastIdentity = (Identity) listIdentities.get(listIdentities.size() - 1);
		int nextId = lastIdentity.getId() + 1;
		return nextId;
	}

	/**
	 * Delete method is used to delete the identity with the id passed in the parameter.
	 * @param id of the Identity to be deleted.
	 */
	public void delete(int id) {
		dao = new IdentityDAO();
		try {
			Identity identity = dao.selectIdentity(id);
			if (identity != null) {
				dao.removeIdentity(identity);
				System.out.println("Identity Deleted");
			} else {
				System.out.println("Delete failed. Record does not exist.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method helps select the identity based on the id passed as parameter.
	 * @param id of the Identity to be selected
	 * @return
	 */
	public Identity selectIdentity(int id) {
		dao = new IdentityDAO();
		Identity identity = null;
		try {
			identity = dao.selectIdentity(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return identity;
	}

	/**
	 * This method helps update the identity based on the Identity selected, passed as parameter.
	 * @param identity Identity object updated with new values.
	 */
	public void update(Identity identity) {
		dao = new IdentityDAO();
		try {
			dao.updateIdentity(identity);
			System.out.println("Identity Updated");
		} catch (SqlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method displays all the records from the database.
	 */
	public void listAll() {
		dao = new IdentityDAO();
		try {
			ArrayList<Identity> listIdentities = (ArrayList<Identity>) dao.readAllIdentities();
			for (int i = 0; i < listIdentities.size(); i++) {
				Identity identity = listIdentities.get(i);
				System.out.println("ID : " + identity.getId() + " | Name :" + identity.getName() + " | Email :"
						+ identity.getEmail() + " | Birthdate :" + identity.getBdate());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
