package persistencia.repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import co.ceiba.conection.Conection;
import co.ceiba.service.IParkingRate;

public class ParkingRate implements IParkingRate{
	
	private static final Logger LOGGER = Logger.getLogger( ParkingRegisterService.class.getName() );
	private Conection con;
	
	public ParkingRate(Conection con) {
		this.con = con;
	}

	public double obtenerTarifa(String tipoVehiculo, String tiempo) {
		con.conect();
		String query = "";
		if(tiempo.equals("H"))
			query = "SELECT TARIFA_HORA AS TARIFA FROM TARIFA_TIPO_VEHICULO WHERE TIPO_VEHICULO_ID='"+tipoVehiculo+"'";
		else
			query = "SELECT TARIFA_DIA AS TARIFA FROM TARIFA_TIPO_VEHICULO WHERE TIPO_VEHICULO_ID='"+tipoVehiculo+"'";
		
		ResultSet rs = con.executeQuery(query);
		double tarifa=0;
		try {
			if(rs.next()){
				tarifa = rs.getDouble("TARIFA");
			}
			else{
				tarifa = 0;
			}
			con.close();
			return tarifa;
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		return 0;
	}

}
