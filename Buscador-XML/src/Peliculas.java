import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Peliculas {

    // PARA FACILITAR LOS SOUT
    public void consola(String arg1) {
        System.out.println(arg1);
    }

    String respuesta;

    public String scan() {
        Scanner scan1 = new Scanner(System.in);
        respuesta = scan1.nextLine();
        return null;
    }

    public void peticion1() {
        consola("¿Quiere consultar información de películas? (SI/NO)");
        scan();
    }

    public void lectura(String arg1) {
        try {

            File file1 = new File("src/XML/xmlData1.xml");
            DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder db1 = dbf1.newDocumentBuilder();
            Document document1 = db1.parse(file1);
            document1.getDocumentElement().normalize();
            NodeList nodelist1 = document1.getElementsByTagName(arg1);
            consola("Cuando quieras, estamos atentos");
            scan();
            boolean matchFound = false;

            // Convertir la respuesta del usuario a minúsculas
            String respuestaMinusculas = respuesta.toLowerCase();

            // Se recorre la lista de nodos extraída
            for (int i = 0; i < nodelist1.getLength(); i++) {
                Node node1 = nodelist1.item(i);

                if (node1.getNodeType() == Node.ELEMENT_NODE) {
                    Element element1 = (Element) node1;

                    // Convertir los valores extraídos del XML a minúsculas
                    String titulo = element1.getElementsByTagName("titulo").item(0).getTextContent().toLowerCase();
                    String director = element1.getElementsByTagName("director").item(0).getTextContent().toLowerCase();
                    String actor = element1.getElementsByTagName("actor").item(0).getTextContent().toLowerCase();
                    String anio_lanzamiento = element1.getElementsByTagName("año_lanzamiento").item(0).getTextContent().toLowerCase();
                    String genero = element1.getElementsByTagName("genero").item(0).getTextContent().toLowerCase();

                    // Comparar la entrada del usuario con los valores en minúsculas
                    if (titulo.contains(respuestaMinusculas) || director.contains(respuestaMinusculas) || actor.contains(respuestaMinusculas) || genero.contains(respuestaMinusculas) || anio_lanzamiento.contains(respuestaMinusculas)) {
                        consola("Titulo: " + element1.getElementsByTagName("titulo").item(0).getTextContent());
                        consola("Director: " + element1.getElementsByTagName("director").item(0).getTextContent());
                        consola("Actor: " + element1.getElementsByTagName("actor").item(0).getTextContent());
                        consola("Año de lanzamiento: " + element1.getElementsByTagName("año_lanzamiento").item(0).getTextContent());
                        consola("Género: " + element1.getElementsByTagName("genero").item(0).getTextContent());
                        consola("Duración: " + element1.getElementsByTagName("duracion").item(0).getTextContent());
                        consola("");
                        matchFound = true;
                    }
                }
            }

            if (!matchFound) {
                consola("No se han encontrado coincidencias.");
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void flujo() throws ParserConfigurationException, IOException, SAXException {
        boolean continuar = true;
        while (continuar) {
            // Convertir respuesta a minúsculas por seguridad
            respuesta = respuesta.toLowerCase();

            switch (respuesta) {
                case "si":
                    lectura("pelicula");
                    consola("¿Quiere consultar otra película? (SI/NO)");
                    scan(); // Escanear nueva respuesta después de la búsqueda
                    if (!respuesta.equalsIgnoreCase("si")) {
                        continuar = false; // Salir del bucle si la respuesta no es "si"
                        consola("Hasta la próxima");
                    }
                    break;
                case "no":
                    consola("Hasta la próxima");
                    continuar = false; // Salir del bucle
                    break;
                default:
                    consola("No ha introducido una opción válida. Intente de nuevo.");
                    peticion1(); // Volver a preguntar si la entrada no es válida
                    break;
            }
        }
    }

    public void start() throws ParserConfigurationException, IOException, SAXException {
        peticion1();
        flujo();
    }
}
