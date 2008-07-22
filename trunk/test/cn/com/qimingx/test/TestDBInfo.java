package cn.com.qimingx.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

public class TestDBInfo {
	public static void main(String[] args) throws Exception {
		testmysql();
		// testmsSQL();
		if (false) {
			String url = "jdbc:hsqldb:hsql://localhost/webSiteDB";
			String user = "sa";
			String passwd = "";

			showHSQLDBInfo(url, user, passwd);
		}

		// insertDataToUsers(driver, url, user, passwd);
		// showTableInfo();
	}

	public static void testmysql() {
		try {
			// Create a variable for the connection string.
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://10.171.10.236/webdb";
			String user = "root";
			String passwd = "chenfeng";
			showDBInfos(url, user, passwd, driver);
			// Class.forName("com.mysql.jdbc.Driver");
			// Connection conn = DriverManager.getConnection(url, user, passwd);
			// System.out.println("conn:" + conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showMSSQLDBInfo() {
		try {
			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			String url = "jdbc:sqlserver://10.171.10.81:1433;databaseName=com_inc";
			String user = "lxl";
			String passwd = "lxl";
			showDBInfos(url, user, passwd, driver);
			// Class.forName(driver);
			// Connection conn = DriverManager.getConnection(url, user, passwd);
			// System.out.println("conn:" + conn);
			//
			// Statement stmt = conn.createStatement();
			// ResultSet rs = stmt.executeQuery("select * from Users");
			// ResultSetMetaData rsmd = rs.getMetaData();
			// // while (rs.next()) {
			// for (int i = 0; i < rsmd.getColumnCount(); i++) {
			// String name = rsmd.getColumnName(i + 1);
			// int type = rsmd.getColumnType(i + 1);
			// System.out.println(name + "[" + type + ","
			// + SQLTypeUtils.getJdbcTypeName(type));
			// }
			// // }
			// rs.close();
			// stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showOracleDBInfo(String url, String user, String passwd) {
		String driver = "oracle.jdbc.OracleDriver";
		showDBInfos(url, user, passwd, driver);
	}

	public static void showHSQLDBInfo(String url, String user, String passwd) {
		String driver = "org.hsqldb.jdbcDriver";
		showDBInfos(url, user, passwd, driver);
	}

	// 显示数据库信息
	public static void showDBInfos(String url, String user, String passwd,
			String driver) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);
			DatabaseMetaData dmd = conn.getMetaData();

			System.out.println("**************************\nUser:"
					+ dmd.getUserName());

			// Schemas
			System.out.println("**************************\nProductName:"
					+ dmd.getDatabaseProductName());
			System.out.println("**************************\nProductVersion:"
					+ dmd.getDatabaseMajorVersion() + "."
					+ dmd.getDatabaseMinorVersion());
			ResultSet rs = dmd.getSchemas();

			System.out.println("**************************\nSchemas:");
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int length = rsmd.getColumnCount();
				for (int i = 0; i < length; i++) {
					System.out.println(i + ":" + rs.getString(i + 1));
				}
				System.out.println("====================");
			}
			rs.close();

			// Catalogs
			rs = dmd.getCatalogs();
			System.out.println("**************************\nCatalogs:");
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int length = rsmd.getColumnCount();
				for (int i = 0; i < length; i++) {
					System.out.println(i + ":" + rs.getString(i + 1));
					System.out.println("====================");
				}
			}
			rs.close();

			// TableTypes：
			rs = dmd.getTableTypes();
			System.out.println("**************************\nTableTypes:");
			while (rs.next()) {
				System.out.println(":" + rs.getString("TABLE_TYPE"));
			}
			rs.close();

			// Tables
			rs = dmd.getTables(null, "PUBLIC", null, null);
			System.out.println("**************************\nTables:");
			while (rs.next()) {
				String table = rs.getString("TABLE_NAME");
				if (table.indexOf('/') > -1 || table.indexOf('$') > -1) {
					continue;
				}
				System.out.println(table);
			}
			rs.close();

			// Columns
			System.out.println("**************************\nTable_Columns:");
			rs = dmd.getColumns(null, "PUBLIC", "USERS", null);
			while (rs.next()) {
				// String table = rs.getString("PKTABLE_NAME");
				String pk = rs.getString("COLUMN_NAME");
				System.out.print(pk + "\t");
			}
			System.out.println("");
			rs.close();

			// Table PKs
			System.out.println("**************************\nTable_PK:");
			rs = dmd.getPrimaryKeys(null, "PUBLIC", "ARTICLES");
			while (rs.next()) {
				// String table = rs.getString("PKTABLE_NAME");
				String pk = rs.getString("COLUMN_NAME");
				String pkName = rs.getString("PK_NAME");
				System.out.println(pk + "[" + pkName + "]");
			}
			rs.close();

			// Table FKs
			System.out.println("**************************\nTable_FKs:");
			rs = dmd.getImportedKeys(null, "PUBLIC", "ARTICLES");
			// showResultSetInfo(rs);
			while (rs.next()) {
				String fkTable = rs.getString("PKTABLE_NAME");
				String fk = rs.getString("PKCOLUMN_NAME");
				String fkName = rs.getString("FK_NAME");
				System.out.println(fkTable + "." + fk + "[" + fkName + "]");
			}
			rs.close();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showResultSetInfo(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			System.out.println("@@@:" + rsmd.getColumnName(i));
		}
	}

	public static void insertDataToUsers(String driver, String url,
			String user, String passwd) throws Exception {

		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, passwd);

			// show Table Info
			// showTableInfo(conn);

			// insert to
			insertUser(conn);

			// update users
			// updateUser(conn);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void updateUser(Connection conn) throws SQLException {
		String sql = "update USERS SET LASTLOGINTIME=? where ID = 4";
		PreparedStatement stmt = conn.prepareStatement(sql);
		Object obj = new Timestamp(System.currentTimeMillis());
		stmt.setObject(1, obj, Types.TIMESTAMP);
		// stmt.setObject(2, 2, Types.INTEGER);

		System.out.println("update.sql:" + sql);
		System.out.println("update.sql.param:" + obj);
		int updateRows = stmt.executeUpdate();

		System.out.println("updateRows:" + updateRows);
		stmt.close();
	}

	public static void insertUser(Connection conn) throws SQLException {
		String sql = "insert into Users(createtime,name,password,remark)";
		sql += " values(?,?,?,?)";

		Date now = new Date();
		Timestamp time = new Timestamp(now.getTime());
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setObject(1, time, Types.DATE);
		stmt.setObject(2, "Test2", Types.VARCHAR);
		stmt.setObject(3, "@$#%###W#", Types.VARCHAR);
		stmt.setObject(4, "Test2", Types.VARCHAR);

		int updateRows = stmt.executeUpdate();
		System.out.println("updateRows:" + updateRows);
		stmt.close();
	}
}
