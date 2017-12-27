package co.ceiba.conection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
public class Conection {
	private java.sql.Connection con;
	private static final Logger LOGGER = Logger.getLogger( Conection.class.getName() );
	
	public Conection(){	
		//Constructor vacio ya que la conexion solo se realiza cuano es necesario (metodo conect)
	}
	
	public void conect(){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://mssql3.gear.host","parqueaderoceiba","Do4sjHrB!Dd~");
		}catch(SQLException|ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String query){
		PreparedStatement pst = null;
		try{
			pst = con.prepareStatement(query);
			pst.executeQuery();
			return pst.getResultSet();
		}catch(SQLException e){
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		return null;
	}
	
	public void executeUpdate(String update) throws SQLException{
		PreparedStatement pst = null;	
		try{
			pst = con.prepareStatement(update);
			pst.executeUpdate();
		} finally{
			try {
				if(pst!=null){
					pst.close();
					close();
				}
			} catch (SQLException e) {
				LOGGER.log(LOGGER.getLevel(), e.toString());
			}
		}
	}
	
	public void close(){
		try {
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
	}
	
	public boolean isClosed(){
		try {
			if(con!=null)
				return con.isClosed();
			else
				return true;
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
			return true;
		}
	}
}
