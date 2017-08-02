package com.pkd.assignment.espn.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.pkd.assignment.espn.model.UrlInfo;
/**
 * @author Provash
 * Derby embeded dao implementation fror CRUD operation
 */
public class UrlShorterEmbededDbDao implements UrlShorterDao {
	private static Connection derbyDbConnection;
	public static final String FETCH_ALL_TABLES_INFO = "select * from SYS.SYSTABLES";
	public static final String FETCH_ALL_URLINFO_DATA = "select * from URLINFO";
	private static final String URL_SHORTER_DB_CONNECTION_STRING = "jdbc:derby:urlShorterDB;create=true;user=root;password=root";
	private static final String DROP_URLINFO_TABLE_QUERY = "DROP TABLE URLINFO";
	private static final String CREATE_URLINFO_TABLE_QUERY = "CREATE TABLE URLINFO (SHORTURL VARCHAR(6) NOT NULL PRIMARY KEY, LONGURL VARCHAR(250), CREATIONDATE DATE, EXPIRYDATE DATE, STATUS INT, OWNER VARCHAR(10))";
	private static final String INSERT_DATA_IN_URLINFO_TABLE_QUERY = "INSERT INTO URLINFO (SHORTURL, LONGURL, CREATIONDATE, EXPIRYDATE , STATUS, OWNER ) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String FETCH_LONG_URL_QUERY = "SELECT LONGURL FROM URLINFO WHERE SHORTURL= ?";
	private static final int ACTIVE_DATA = 1;
	private static final int PASSIVE_DATA = 0;
	private static final int DEFAULT_EXPIRY_DAYS = 365;
	private static final String DEFAULT_USER = "anonymous";

	public static void createDatabaseConnection() {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			derbyDbConnection = DriverManager.getConnection(URL_SHORTER_DB_CONNECTION_STRING);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Statement s = null;
		try {
			s = derbyDbConnection.createStatement();
			try {
				s.execute(DROP_URLINFO_TABLE_QUERY);
			} catch (SQLException e) {
				System.out.println("************DROP Fail :: URLINFO Table not exist :: " + e.getMessage());
			}
			s.execute(CREATE_URLINFO_TABLE_QUERY);
			System.out.println("************Successfully Created Table :: URLINFO**************");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (s != null)
					s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeDatabaseConnection() {
		try {
			if (derbyDbConnection != null)
				derbyDbConnection.close();
			DriverManager.getConnection("jdbc:derby:urlShorterDB;shutdown=true");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean insertShorterUrlInfo(UrlInfo info) {
		PreparedStatement stmt = null;
		try {
			stmt = derbyDbConnection.prepareStatement(INSERT_DATA_IN_URLINFO_TABLE_QUERY);

			if (!(info.getShortUrl() == null || info.getLongUrl() == null)
					&& !(info.getShortUrl().isEmpty() || info.getLongUrl().isEmpty())) {

				stmt.setString(1, info.getShortUrl());
				stmt.setString(2, info.getLongUrl());
				if (info.getCreationDate() == null) {
					Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
					stmt.setDate(3, currentDate);
				} else {
					stmt.setDate(3, new Date(info.getCreationDate().getTime()));
				}

				if (info.getExpDate() == null) {
					Calendar c = Calendar.getInstance();
					c.setTime(new java.util.Date());
					c.add(Calendar.DAY_OF_YEAR, DEFAULT_EXPIRY_DAYS);
					stmt.setDate(4, new Date(c.getTime().getTime()));
				} else {
					stmt.setDate(4, new Date(info.getExpDate().getTime()));
				}

				if (info.getStatus() == null)
					stmt.setInt(5, ACTIVE_DATA);
				else
					stmt.setInt(5, info.getStatus().getIntStatus());
				
				if(info.getOwnerId() == null || info.getOwnerId().isEmpty()){
					stmt.setString(6, DEFAULT_USER);
				}else{
					stmt.setString(6, info.getOwnerId());
				}
			} else{
				return false;
			}
			stmt.executeUpdate();
			System.out.println("*****Record inserted successful :: " + info.getShortUrl());
			return true;
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String fetchLongUrlInfo(String shortUrl) {
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			stmt = derbyDbConnection.prepareStatement(FETCH_LONG_URL_QUERY);
			if (!(shortUrl == null || shortUrl.isEmpty())) {
				stmt.setString(1, shortUrl);
			}
			results = stmt.executeQuery();
			results.next();
			return results.getString(1);
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		} finally {
			try {
				if (results != null)
					results.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void eqecuteAndPrintQueryRes(String query) {
		Statement s = null;
		ResultSet results = null;
		try {
			s = derbyDbConnection.createStatement();
			results = s.executeQuery(query);
			ResultSetMetaData rsmd = results.getMetaData();
			int numberCols = rsmd.getColumnCount();
			for (int i = 1; i <= numberCols; i++) {
				System.out.print(rsmd.getColumnLabel(i) + "\t\t");
			}

			System.out.println(
					"============================================================================================");
			while (results.next()) {
				String col1 = results.getString(1);
				String col2 = results.getString(2);
				String col3 = results.getString(3);
				System.out.println(col1 + "\t\t" + col2 + "\t\t" + col3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
