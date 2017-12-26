package persistencia.repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import co.ceiba.conection.Conection;
import co.ceiba.service.IParkingAvailability;

public class ParkingAvailability implements IParkingAvailability{

	private static final Logger LOGGER = Logger.getLogger( ParkingRegisterService.class.getName() );
	private Conection con;
	
	public ParkingAvailability(Conection con) {
		this.con = con;
	}
	
	public int obtenerDisponibilidadTipoVehiculo(String tipoVehiculo) {
		con.conect();
		String query = "SELECT DISPONIBILIDAD FROM DISPONIBILIDAD_VEHICULO WHERE TIPO_VEHICULO_ID='"+tipoVehiculo+"'";
		ResultSet rs = con.executeQuery(query);
		int disponivilidad=0;
		try {
			if(rs.next()){
				disponivilidad = rs.getInt("DISPONIBILIDAD");
			}
			else{
				disponivilidad = 0;
			}
			con.close();
			return disponivilidad;
		} catch (SQLException e) {
			LOGGER.log(LOGGER.getLevel(), e.toString());
		}
		return 0;
	}

}
