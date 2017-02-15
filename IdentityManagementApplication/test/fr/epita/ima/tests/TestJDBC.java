/**
 * 
 */
package fr.epita.ima.tests;

import java.sql.SQLException;

import org.apache.derby.client.am.SqlException;

import fr.epita.ima.datamodel.Identity;
import fr.epita.ima.dbconnection.IdentityDAO;
/**
 * @author neha
 *
 */
public class TestJDBC {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		//The goal is to connect to this url : jdbc:derby://localhost:1527/IAM;create=true
		
		TestJDBC test = new TestJDBC();
		Identity identity = new Identity(5, "jaggu", "email@email.com", "1934-5-2");
		test.testCreateData(identity);
		test.testReadAllData();
		identity.setName("Jagdish");
		test.testUpdateRecord(identity);
//		test.testDeleteRecord(identity);
		

	}

	public void testCreateData(Identity identity){
		IdentityDAO dao = new IdentityDAO();
		try {
			dao.write(identity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testReadAllData() throws SQLException {
		IdentityDAO dao = new IdentityDAO();
		System.out.println(dao.readAllIdentities());
	}
	
	public void testDeleteRecord(Identity identity){
		IdentityDAO dao = new IdentityDAO();
		try {
			dao.removeIdentity(identity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testUpdateRecord(Identity identity){
		IdentityDAO dao = new IdentityDAO();
		try {
			dao.updateIdentity(identity);
		} catch (SqlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
