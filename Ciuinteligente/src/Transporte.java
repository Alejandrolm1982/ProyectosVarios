import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Transporte {
    // Para escribir caracteres especiales y poner out.println
    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    // Respuestas
    private String origen = null;
    private String destino = null;

    // Base de datos [6][8]
    /* Dos transportes, 3 líneas cada uno, a b c d e f g h i j k m ñ w x y z, 17 paradas */
    private String m1[][] = {
            {"bus", "linea 1", "a", "d", "f", "h", "i", "k"},
            {"bus", "linea 2", "b", "c", "e", "h", "j", "m"},
            {"bus", "linea 3", "k", "e", "f", "d", "b", "a"},
            {"metro", "linea 1", "e", "x", "w", "g", "y", "z"},
            {"metro", "linea 2", "a", "c", "h", "g", "m", "i"},
            {"metro", "linea 3", "m", "h", "k", "ñ", "j", "b"},
    };

    public void solicitarDatos() {
        Scanner sc1 = new Scanner(System.in);
        out.println("¿Cuál es tu origen?");
        origen = sc1.nextLine();
        out.println("¿Cuál es tu destino?");
        destino = sc1.nextLine();
        sc1.close();
    }

    public void algoritmo() {
        boolean encontrado = false;

        // Leer toda la matriz en busca del origen
        for (int lineao = 0; lineao < m1.length; lineao++) {
            for (int col0 = 2; col0 < m1[lineao].length; col0++) {
                // Si existe el origen
                if (m1[lineao][col0].equalsIgnoreCase(origen)) {
                    // Verificar si hay un destino en la misma línea
                    for (int col1 = 2; col1 < m1[lineao].length; col1++) {
                        if (m1[lineao][col1].equalsIgnoreCase(destino)) {
                            out.println("Tienes una línea directa en el " + m1[lineao][0] + " y en la " + m1[lineao][1]);
                            encontrado = true;
                            break; // Salir del bucle si se encuentra una línea directa
                        }
                    }

                    // Buscar si el destino está en otra línea
                    for (int linead = 0; linead < m1.length; linead++) {
                        if (lineao != linead) { // Asegurarse de que no sea la misma línea
                            for (int col1 = 2; col1 < m1[linead].length; col1++) {
                                if (m1[linead][col1].equalsIgnoreCase(destino)) {
                                    // Si están en la misma parada, es un transbordo
                                    if (col0 == col1) {
                                        out.println("Puedes subirte en la " + m1[lineao][1] + " de " + m1[lineao][0] + " hasta la parada " + m1[lineao][col0] + " donde tienes que hacer transbordo a la " + m1[linead][1] + " de " + m1[linead][0] + " hasta " + destino);
                                    } else {
                                        // Encuentra la parada donde se puede hacer transbordo
                                        String paradaTransbordo = buscarParadaTransbordo(m1[lineao], m1[linead]);
                                        if (paradaTransbordo != null) {
                                            out.println("Puedes subirte en la " + m1[lineao][1] + " de " + m1[lineao][0] + " hasta la parada " + paradaTransbordo + " donde tienes que hacer transbordo a la " + m1[linead][1] + " de " + m1[linead][0] + " hasta " + destino);
                                        } else {
                                            out.println("No se puede realizar el transbordo entre las líneas.");
                                        }
                                    }
                                    encontrado = true;
                                    break; // Salir si se encuentra el destino
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!encontrado) {
            out.println("No se han encontrado coincidencias.");
        }
    }

    private String buscarParadaTransbordo(String[] lineaOrigen, String[] lineaDestino) {
        for (int col0 = 2; col0 < lineaOrigen.length; col0++) {
            for (int col1 = 2; col1 < lineaDestino.length; col1++) {
                if (lineaOrigen[col0].equalsIgnoreCase(lineaDestino[col1])) {
                    return lineaOrigen[col0]; // Retorna la parada donde se puede hacer el transbordo
                }
            }
        }
        return null; // Si no hay paradas comunes
    }

    public static void main(String[] args) {
        Transporte transporte = new Transporte();
        transporte.solicitarDatos();
        transporte.algoritmo();
    }
}
