package co.ceiba.service;

import java.util.Date;

public final class Tool {
	private Tool(){
		
	}
	
	public static int diferenciaHoras(Date fechaIngreso,Date fechaSalida){
		long diff = fechaSalida.getTime() - fechaIngreso.getTime();
		long segundos = diff / 1000;
		long minutos = segundos / 60;
		return Math.round(minutos / 60f);		
	}
}
