package com.company;

import java.net.*;
import java.io.*;
import java.util.Date;

/* Example 9-3 uses try-with-resources to autoclose the server socket. However, it deliberately
does not use try-with-resources for the client sockets accepted by the server
socket. This is because the client socket escapes from the try block into a separate thread.
If you used try-with-resources, the main thread would close the socket as soon as it got
to the end of the while loop, likely before the spawned thread had finished using it.
There’s actually a denial-of-service attack on this server though. Because Example 9-3
spawns a new thread for each connection, numerous roughly simultaneous incoming
connections can cause it to spawn an indefinite number of threads. Eventually, the Java
virtual machine will run out of memory and crash. A better approach is to use a fixed
thread pool */




public class MultithreadedDaytimeServer {

    public final static int PORT = 13;


    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    Thread task = new DaytimeThread(connection);
                    task.start();
                } catch (IOException ex) {}
            }
        } catch (IOException ex) {
            System.err.println("Couldn't start server");
        }
    }








    private static class DaytimeThread extends Thread{

        private Socket connection;
        DaytimeThread(Socket connection) {
            this.connection = connection;
        }


        @Override
        public void run() {
            try {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() +"\r\n");
                out.flush();
            } catch (IOException ex) {
                System.err.println(ex);
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {
                        // ignore;
                }
            }
        }
    }



    }




