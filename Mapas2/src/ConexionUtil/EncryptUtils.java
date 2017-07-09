package ConexionUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;



public class EncryptUtils {
	
	  public static final String DEFAULT_ENCODING = "UTF-8";
	    static BASE64Encoder enc = new BASE64Encoder();
	    static BASE64Decoder dec = new BASE64Decoder();

	    public static String base64encode(String text) {
	        try {
	            String rez = enc.encode(text.getBytes(DEFAULT_ENCODING));
	            return rez;
	        } catch (UnsupportedEncodingException e) {
	            return null;
	        }
	    }//base64encode

	    public static String base64decode(String text) {

	        try {
	            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
	        } catch (IOException e) {
	            return null;
	        }

	    }//base64decode

	    public static void main(String[] args) {
	        String cadena = "adrian#12345#ES#10/10/201409:15";
	        System.out.println("ENCRIPTAR "+ cadena);
	        System.out.println("ENCRIPTADO " +encriptar(cadena));
	        System.out.println("DESENCRIPTAR "+ desencriptar(encriptar(cadena)));
	    }

	    public static String encriptar(String cadena) {

	        String encoded = base64encode(xorMessage(cadena, "sistemagpsencriptacion"));
	        return encoded;
	    }

	    public static String desencriptar(String cadena) {

	        return xorMessage(base64decode(cadena), "sistemagpsencriptacion");

	    }

	    public static String xorMessage(String message, String key) {
	        try {
	            if (message == null || key == null) {
	                return null;
	            }

	            char[] keys = key.toCharArray();
	            char[] mesg = message.toCharArray();

	            int ml = mesg.length;
	            int kl = keys.length;
	            char[] newmsg = new char[ml];

	            for (int i = 0; i < ml; i++) {
	                newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
	            }//for i
	            mesg = null;
	            keys = null;
	            return new String(newmsg);
	        } catch (Exception e) {
	            return null;
	        }
	    }//xorMessa

}
