package com.mycompany.chatconsockets;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {

    private int puerto;
    private PropertyChangeSupport support;

    public Servidor(int puerto) {
        this.puerto = puerto;
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public void run() {
        ServerSocket servidor = null;
        Socket socket = null;
        DataInputStream entrada;

        try {
            //Creamos el servidor del socket
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            //Siempre estara escuchando peticiones
            while (true) {
                //Espero que el cliente se contecte
                socket = servidor.accept();
                System.out.println("Cliente conectado");
                entrada = new DataInputStream(socket.getInputStream());

                //Leemos el mensaje
                String mensaje = entrada.readUTF();
                System.out.println(mensaje);

                // Cuando un cambio de estado sucede, usa lo siguiente para notificar de lo observado
                support.firePropertyChange("mensaje", null, mensaje);

                socket.close();
                System.out.println("Cliente desconectado");
            }
        } catch (IOException error) {
            System.out.println(error);
        }
    }
}