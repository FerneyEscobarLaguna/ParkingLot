package co.ceiba.service;

import java.util.Date;

public final class Tool {
	private Tool(){
		
	}
	
	public static int diferenciaHoras(Date fechaIngreso,Date fechaSalida){
		long diff = fechaSalida.getTime() - fechaIngreso.getTime();
		long segundos = diff / 1000;
		long minutos = segundos / 60;
		return (int) Math.ceil(minutos / 60f);		
	}
	
	public static int calcularHorasDiasCobro(int totalHoras,int horaInicioDia,boolean retornarDias){
		int diasCobrar=0;
		int horasCobrar=0;
		if(totalHoras>=horaInicioDia){
			diasCobrar=totalHoras/24;
			horasCobrar=totalHoras%24;
			if(horasCobrar>=horaInicioDia){
				diasCobrar++;
				horasCobrar=0;
			}
		}else{
			diasCobrar=0;
			horasCobrar=totalHoras;
		}
		
		if(retornarDias)
			return diasCobrar;
		return horasCobrar;
	}
}
