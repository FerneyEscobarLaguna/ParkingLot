package dominio.integracion;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.junit.Test;
import org.mockito.Mockito;

import co.ceiba.conection.Conection;
import persistencia.repositorio.ParkingAvailability;

public class ParkingAvailabilityTest {
	private static final Logger LOGGER = Logger.getLogger( ParkingRegisterServiceTest.class.getName());
	@Test
	public void obtenerDisponibilidadTipoVehiculoTest(){
		//arrange
		String tipoVehiculo = "C";
		
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getInt("DISPONIBILIDAD")).thenReturn(20);	
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingAvailability pa = new ParkingAvailability(con);
		//act
		double disponibilidad = pa.obtenerDisponibilidadTipoVehiculo(tipoVehiculo);
		//assert
		assertEquals(20,disponibilidad,0);
	}
}
