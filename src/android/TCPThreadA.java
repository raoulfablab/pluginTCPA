/*
FREE
*/
package fr.fablab.TCPA;


import java.net.*;
import java.io.OutputStream;

/* *******************************************************************
   **                                                               **
   **                                                               **
   **                                                               **
   ******************************************************************* */

   // modèle :
   // https://github.com/gramakri/cordova-plugin-datagram/blob/master/src/android/Datagram.java
   // http://www.liafa.jussieu.fr/~sighirea/cours/reseauxM/java.thread.html

public class TCPThreadA extends Thread {

    String message="";

    //String portString ="8899";
    String ipString=""; // "192.168.0.199";
    String portString="";
    int portInt=0;

    TCPSock tcpSock;
    OutputStream out;

    /* ******************************************
     * Constructeur
     * stockage adresses IP et port
     */
    public TCPThreadA(TCPSock sockx) {
     tcpSock=sockx;
     message="";
    }

    /* ******************************************
     * run ()
     * instanciation socket
     */

    public void run (){
    /* ******************************************
     *  emission message
     */

             try
              {
               tcpSock.sock.setSendBufferSize(tcpSock.tableauBytes.length);
               //out=sock.getOutputStream();
               tcpSock.out.write(tcpSock.tableauBytes);
               tcpSock.out.flush();
               //tcpSock.sock.shutdownOutput();
               //tcpSock.sock.close();
              }catch(Exception exp){
               System.out.println(exp);
              } // fin de try

    } // fin de public void run ()


} // fin de class TCPThreadA
