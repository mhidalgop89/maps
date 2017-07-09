package ucsg.gmaps.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		Document doc =  Jsoup.parse(readURL("http://127.0.0.1:8080/Mapas2/Login/index.html"));
		System.out.println("-->"+doc.getElementById("inputEmail3").val());
		
        Elements p_tags = doc.getAllElements();//doc.select("p");
        for(Element p : p_tags)
        {
            System.out.println("P tag is "+p.text());
        }
	}
	
	
	
    public static String readURL(String url) {

        String fileContents = "";
        String currentLine = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            fileContents = reader.readLine();
            while (currentLine != null) {
                currentLine = reader.readLine();
                fileContents += "\n" + currentLine;
            }
            reader.close();
            reader = null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Message", JOptionPane.OK_OPTION);
            e.printStackTrace();

        }

        return fileContents;
    }
}
