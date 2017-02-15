/**
 * 
 */
package fr.epita.ima.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.client.am.SqlException;

import fr.epita.ima.datamodel.Identity;
import fr.epita.ima.launcher.Messages;

/**
 * This is the DAO. It helps create connection with the database. 
 * All the actions on the database are to be done via this object.
 * @author Neha
 *
 */
public class IdentityDAO {
	private Connection currentConnection;

	/**
	 * Create database connection using the connection parameters.
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		try {
			this.currentConnection.getSchema();
		} catch (Exception e) {
			// TODO read those information from a file
			String user = Messages.getString("username"); //$NON-NLS-1$
			String password = Messages.getString("password"); //$NON-NLS-1$
			String connectionString = Messages.getString("connectionString"); //$NON-NLS-1$
			this.currentConnection = DriverManager.getConnection(connectionString, user, password);
		}
		return this.currentConnection;
	}

	/**
	 * This method releases the connection resource after the manipulation of data.
	 * This frees all the resources and avoids any broken pipes.
	 */
	private void releaseResources() {
		try {
			this.currentConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read all the identities from the database and stores in the Arraylist.
	 * 
	 * @return list of identities stored in ArrayList
	 * @throws SQLException Exception is thrown in case of database connection or access issue.
	 */
	public List<Identity> readAllIdentities() throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();

		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from IDENTITIES"); //$NON-NLS-1$
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			int uid = rs.getInt("IDENTITY_ID"); //$NON-NLS-1$
			String displayName = rs.getString("IDENTITY_DISPLAYNAME"); //$NON-NLS-1$
			String email = rs.getString("IDENTITY_EMAIL"); //$NON-NLS-1$
			String bdate = rs.getString("IDENTITY_BIRTHDATE"); //$NON-NLS-1$
			Identity identity = new Identity(uid, displayName, email, bdate);
			identities.add(identity);
		}
		releaseResources();
		return identities;
	}

	/**
	 * write an identity in the database.
	 * 
	 * @param identity Object that shall be written in the database
	 * @throws SQLException Exception is thrown in case of database connection or access issue.
	 */
	public void write(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "INSERT INTO IDENTITIES(IDENTITY_ID, IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, " //$NON-NLS-1$
				+ "IDENTITY_BIRTHDATE) VALUES(?,?,?,?)"; //$NON-NLS-1$
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setInt(1, identity.getId());
		pstmt.setString(2, identity.getName());
		pstmt.setString(3, identity.getEmail());
		// TODO implement date for identity
		pstmt.setString(4, identity.getBdate());

		pstmt.execute();
		releaseResources();
	}

	
	/**
	 * Deletes the selected identity from the database.
	 * @param identity
	 * @throws SQLException Exception is thrown in case of database connection or access issue.
	 */
	public void removeIdentity(Identity identity) throws SQLException {
		// delete from EPITA.IDENTITIES where IDENTITY_ID = 5;
		Connection connection = getConnection();
		String sqlQuery = "delete from EPITA.IDENTITIES where IDENTITY_ID =" + identity.getId(); //$NON-NLS-1$
		PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
		pstmt.executeUpdate();
	}

	/**
	 * Selects the identity from the database for the passed id.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Identity selectIdentity(int id) throws SQLException {
		Connection connection = getConnection();
		String sqlQuery = "select * from EPITA.IDENTITIES where IDENTITY_ID =" + id; //$NON-NLS-1$
		PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			int uid = rs.getInt("IDENTITY_ID"); //$NON-NLS-1$
			String displayName = rs.getString("IDENTITY_DISPLAYNAME"); //$NON-NLS-1$
			String email = rs.getString("IDENTITY_EMAIL"); //$NON-NLS-1$
			String bdate = rs.getString("IDENTITY_BIRTHDATE"); //$NON-NLS-1$
			Identity identity = new Identity(uid, displayName, email, bdate);
			releaseResources();
			return identity;
		} else {
			releaseResources();
			return null;
		}
		
	}
	
	/**
	 * Updates the records of the identity passed in the parameter.
	 * @param identity
	 * @throws SqlException
	 */
	public void updateIdentity(Identity identity) throws SqlException{
		Connection connection;
		try {
			connection = getConnection();
		String sqlQuery = "update EPITA.IDENTITIES set IDENTITY_DISPLAYNAME = '"+identity.getName()+"', " //$NON-NLS-1$ //$NON-NLS-2$
				+ "IDENTITY_EMAIL = '"+identity.getEmail()+"', IDENTITY_BIRTHDATE = '"+identity.getBdate()+"'where IDENTITY_ID = " + identity.getId(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
		pstmt.executeUpdate();
		releaseResources();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
