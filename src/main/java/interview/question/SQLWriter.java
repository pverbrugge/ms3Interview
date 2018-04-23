package interview.question;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SQLWriter {
	Connection conn = null;
	public SQLWriter(){
		this.conn = this.connect();
		this.create();
	}
	protected Connection connect() {
		Connection connection = null;
		try {
			String url = "jdbc:sqlite:memory:myDB2";
			connection = DriverManager.getConnection(url);
		}catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return connection;
	}
	
	public void create() {
		String sql = "CREATE TABLE IF NOT EXISTS data (\n"
				+ " A TEXT NOT NULL, \n"
				+ " B TEXT NOT NULL, \n"
				+ " C TEXT NOT NULL, \n"
				+ " D TEXT NOT NULL, \n"
				+ " E TEXT NOT NULL, \n"
				+ " F TEXT NOT NULL, \n"
				+ " G DOUBLE NOT NULL, \n"
				+ " H BOOLEAN NOT NULL, \n"
				+ " I BOOLEAN NOT NULL, \n"
				+ " J TEXT NOT NULL\n"
				+ ");";
		
		try{
			Statement stmt = this.conn.createStatement();
			stmt.execute(sql);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insert(String A,String B,String C,String D,String E,String F,Double G,Boolean H,Boolean I,String J) {
		String sql = "INSERT INTO data(A,B,C,D,E,F,G,H,I,J) VALUES(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1 , A);
			pstmt.setString(2 , B);
			pstmt.setString(3 , C);
			pstmt.setString(4 , D);
			pstmt.setString(5 , E);
			pstmt.setString(6 , F);
			pstmt.setDouble(7, G);
			pstmt.setBoolean(8, H);
			pstmt.setBoolean(9, I);
			pstmt.setString(10, J);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	

}
