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
import persistencia.repositorio.ParkingRate;

public class ParkingRateTest {
	private static final Logger LOGGER = Logger.getLogger( ParkingRegisterServiceTest.class.getName() );
	@Test
	public void oobtenerTarifaHoraTest(){
		//arrange
		String tipoVehiculo = "C";
		String tiempoHoras = "H";
		
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getDouble("TARIFA")).thenReturn((double) 10000);	
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRate pr = new ParkingRate(con);
		//act
		double tarifaHoras = pr.obtenerTarifa(tipoVehiculo, tiempoHoras);
		//assert
		assertEquals(0,10000,tarifaHoras);
	}
	
	@Test
	public void oobtenerTarifaDiaTest(){
		//arrange
		String tipoVehiculo = "C";
		String tiempoDias = "D";
		
		ResultSet rs = mock(ResultSet.class);
		try {
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getDouble("TARIFA")).thenReturn((double) 10000);	
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		
		Conection con = mock(Conection.class);
		when(con.executeQuery(Mockito.anyString())).thenReturn(rs);
		ParkingRate pr = new ParkingRate(con);
		//act
		double tarifaDias = pr.obtenerTarifa(tipoVehiculo, tiempoDias);
		//as
		assertEquals(0,10000,tarifaDias);
	}
}
