import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.crypto.dsig.XMLObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    //2f738d0336649532bcc85a3889569587
    public static String getWeather(String message, Model model) throws IOException, SAXException, ParserConfigurationException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&mode=xml&units=metric&appid=2f738d0336649532bcc85a3889569587");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        String body = result;
        String weatherResult = "";

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(body.getBytes()));

        NodeList nodeList = document.getElementsByTagName("temperature");
        weatherResult += "Температура: ";
        for(int x=0,size= nodeList.getLength(); x<size; x++)
            weatherResult += nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue();
        weatherResult += "\n" + "Влажность: ";
        NodeList nodeListHumidity = document.getElementsByTagName("humidity");
        for(int x=0,size= nodeListHumidity.getLength(); x<size; x++)
            weatherResult += nodeListHumidity.item(x).getAttributes().getNamedItem("value").getNodeValue();
        weatherResult += "\n" + "Ветер: ";
        NodeList nodeListWind = document.getElementsByTagName("speed");
        for(int x=0,size= nodeListWind.getLength(); x<size; x++)
            weatherResult += nodeListWind.item(x).getAttributes().getNamedItem("value").getNodeValue();
        return weatherResult + " ms";
    }
}

