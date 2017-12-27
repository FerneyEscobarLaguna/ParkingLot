package dominio.unitaria;

import co.ceiba.conection.Conection;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class ConectionTest {
	public void conectTest(){
		// arrange
		Conection con = new Conection();
		// act
		boolean close = con.isClosed();
		con.conect();
		// assert
		assertTrue(close);//Se verifica que se encontraba desconectado
		close = con.isClosed();
		assertFalse(close);//Se verifica que concto
		con.close();
		close = con.isClosed();//Se verifica que se cerro la conexion
		assertTrue(close);
	}
	
	public void executeQueryTest(){
		// arrange
		Conection con = new Conection();
		String query = "SELECT 'something'";
		// act
		con.conect();
		java.sql.ResultSet rs = con.executeQuery(query);
		// assert
		assertNotNull(rs);
		con.close();
	}
	
	public void executeUpdateTest(){
		// arrange
		Conection con = new Conection();
		String update = "UPDATE REGISTRO_PARQUEADERO SET FECHA_SALIDA=FECHA_SALIDA WHERE PLACA IS NULL";
		// act
		boolean close = con.isClosed();
		con.conect();
		boolean conect = !con.isClosed();
		try {
			con.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		// assert
		assertTrue(close);//Se verifica que se encontraba desconectado
		assertTrue(conect);
		close = con.isClosed();//Se verifica que se cerro la conexion
		assertTrue(close);
	}
}
