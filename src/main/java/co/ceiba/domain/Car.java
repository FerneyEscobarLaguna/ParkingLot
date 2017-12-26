package co.ceiba.domain;

public class Car extends Vehicle{

	public Car(String placa) {
		super(placa,"C"); 
	}	
	
	public Car() { 
		super(null,"C");
	}
	
	public double getCostParking(int hoursParking) {
		int diasCobrar=0;
		int horasCobrar=0;
		double valorcobrar=0;
		if(hoursParking>9){
			if(hoursParking>24){
				diasCobrar=hoursParking/24;
				horasCobrar=hoursParking%24;
			}else{
				diasCobrar=1;
				horasCobrar=0;				
			}
		}else{
			diasCobrar=0;
			horasCobrar=hoursParking;
		}
		valorcobrar=(diasCobrar*8000d) + (horasCobrar*1000d);
		return valorcobrar;
	}	
}
