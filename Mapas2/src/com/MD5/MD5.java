package com.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {


	/**
	* Metodo que te da el hash MD5 en un String en caracteres
	* hexadecimales
	* @param data Cadena de la cual queremos calcular el hash MD5
	* @param key Clave que emplearemos en el calculo del hash.
	* @return Un String con el valor resultante del hash en caracteres
	* hexadecimales
	*/
	public static String digest(String data, String key) {
	try{
	//Generamos un objeto MessageDigest que implementa el algoritmo MD5
	//esto puede lanzar una NoSuchAlgorithmException si el algoritmo
	//no est� soportado. Pero se supone que esto nunca ocurrir�
	MessageDigest md5 = MessageDigest.getInstance("MD5");
	//Actulizamos el digest
	md5.update(data.getBytes());
	//En ocasiones se quiere aplicar una clave al algoritmo, esto
	//se consigue actualizando el digest con los bytes de la clave
	//Esto se utiliza por ejemplo en el m�todo de autentificaci�n
	//APOP de POP3 en el que a una marca de tiempo (timestamp)
	//se le aplica el algorimto MD5 con la contrase�a de usuario
	//como clave.
	//En el protocolo MSN el m�todo de autentificaci�n es similar
	//Realmente obtendremos el mismo resultado si concatenamos
	//los datos y la clave y asumimos la clave como una
	//cadena de longitud cero. Esto se puede comprobar en el ejemplo del main()
	byte[] result = md5.digest(key.getBytes());
	//Creamos un StringBuffer donde guardaremos el hash como caracteres
	StringBuffer sb = new StringBuffer();
	for(int i=0; i<result.length; i++){
	//Obtenemos el string correspondiente al byte
	String s = Integer.toHexString(result[i]);
	int length = s.length();
	//Si es de dos d�gitos o m�s
	//a�adimos los dos �ltimos d�gitos
	//El byte representado en hexadecimal tendr� m�s de dos
	//caracteres si es negativo, por ejemplo. Pero
	//a nosotros s�lo nos interesan los dos �ltimos caracteres
	if(length >= 2)
	sb.append(s.substring(length-2, length));
	//Si es de solo un d�gito a�adimos un 0 y el d�gito.
	else {
	sb.append("0");
	sb.append(s);
	}
	}
	//Finalmente devolvemos un String con el contenido del StringBuffer
	return sb.toString();
	}
	catch(NoSuchAlgorithmException e){
		//esto ocurre si el algoritmo MD5 no est� soportado
		return null;
		}
		}
		/**
		* Ejemplo de uso de la funcion digest().
		* Este ejemplo lo he cogido de
		* <a
		href="http://www.rfc-es.org/rfc/rfc1939-es.txt">http://www.rfc-es.org/rfc/rfc1939-es.txt</a>,
		pagina 15
		*/
		/* public static void main(String[] args) {
		String data = "root";
		String key = "12121212";
		System.out.println("Ejemplo hash MD5");
		System.out.println("tdata : "+data);
		System.out.println("tkey : "+key);
		System.out.println("thash : "+digest(data, key));

		}*/
		
public static void main(String Args[])
{
	String md5=null;
	md5=digest("12345","MD5"); 
	System.out.println(md5);
}



}
