import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Peliculas pelicula1 = new Peliculas();
        pelicula1.start();
    }
}