package co.ceiba.conection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Conection {
	private java.sql.Connection con;
	
	public Conection(){	
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try{
			con = (java.sql.Connection) DriverManager.getConnection("jdbc:sqlserver://mssql3.gear.host","parqueaderoceiba","Do4sjHrB!Dd~");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String query){
		try{
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			return rs;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void executeUpdate(String update){
		try{
			PreparedStatement pst = con.prepareStatement(update);
			pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
