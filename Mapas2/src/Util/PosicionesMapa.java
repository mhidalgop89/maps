package Util;

import java.util.ArrayList;
import java.util.List;
//algoritmo: Ruben Pacheco
//traducción a java: mhidalgo
public class PosicionesMapa {
	
	public List<Coordenadas> retornaPosicionesOptimas(List<Coordenadas> coordTotales)
	{
		int indiceOptimo=0;
		 List<Coordenadas> coordenadasOptimas = new ArrayList<Coordenadas>();
		 coordenadasOptimas.add(coordTotales.get(0));
		for(int i=0; i<98;i++)
		{
			indiceOptimo = (int) Math.floor( (i*(coordTotales.size()/100)) );
			coordenadasOptimas.add( coordTotales.get( indiceOptimo ) );
		}
		coordenadasOptimas.add(coordTotales.get(coordTotales.size()-1));
		
		 return coordenadasOptimas;
	}

}
