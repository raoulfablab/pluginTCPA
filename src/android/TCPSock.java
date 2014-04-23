/*
FREE
*/
package fr.fablab.TCPA;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import org.json.JSONException;

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

public class TCPSock {

    public String message="";
    public boolean ok=false;

    String portString ="";  // "8899"
    String ipString="";     // "192.168.0.199";
    int portInt=0;

    Socket       sock  =null;
    OutputStream out   =null;
    byte[] tableauBytes;

    //Thread tCPThreadA=null;
    //TCPThreadA tCPThreadA=null;
    boolean threadLibre=false;

    /* ******************************************
     * Constructeur
     */
    //public TCPSock(String ipStringx, int portIntx) {
    public TCPSock() {
     message="";
    }

    /* ******************************************
     * acquisition adresses IP et port
     */
    //public TCPSock(String ipStringx, int portIntx) {
    public void initAdresse(String ipStringx, int portIntx) {
     this.ipString=ipStringx;
     this.portInt=portIntx;
    }

    /* ******************************************
     * instancier et connecter sock
     */
    //public TCPSock(String ipStringx, int portIntx) {
    public boolean connecterSock() {
     message="";
     try {
      // http://docs.oracle.com/javase/7/docs/api/java/net/Socket.html#Socket(java.lang.String,%20int)
      // Creates a stream socket and connects it to the specified port number on the named host.
      // instanciation socket + connexion TCP :
      sock=new Socket(ipString,portInt);
      out=sock.getOutputStream();
      return true;
     }catch(Exception exp) {
      message=message+"\nERROR!!! TCPSock.connecterSock() java : "+ipString+","+portInt;
      message=message+"\nERROR!!! TCPSock.java 140418073239 exp=("+exp+")";
      return false;
     }
    }


    /* ******************************************
     *  fermer socket message
     */
    public boolean fermer() {
              try
              {
               sock.shutdownOutput();
               sock.close();
               return true;
              }catch(Exception exp)
              {
               System.out.println(exp);
              }
              return false;
    } // fin de boolean envoi()

    /* ******************************************
     *  emission message
     */
    public boolean envoi() {
              try
              {
               sock.setSendBufferSize(tableauBytes.length);
               //out=sock.getOutputStream();
               out.write(tableauBytes);
               out.flush();
               //sock.shutdownOutput();
               //sock.close();
               return true;
              }catch(Exception exp)
              {
               System.out.println(exp);
              }
              return false;
    } // fin de boolean envoi()

    /* ******************************************
     *  emission message
     */
    public boolean envoiThread() {
         Thread tCPThreadA = new TCPThreadA(this);
         tCPThreadA.start(); // lance TCPThreadA.run()
         return true;
    } // fin de boolean envoi()

    /* ******************************************
     *  formatage et envoi du message
     */
    public boolean essai(JSONArray args) {
    //public boolean essai(String args[]) {
      String chaineSource="";
      String chaineDest="";
      message="";
      String[] chaineTableau;

         // charger l'argument "[O,1,..n]" dans String chaineSource
         try{
          //chaineSource = args[0];
          chaineSource = args.getString(0);
         } catch (JSONException e){
	  message="\nERROR!!! PCPSock.java 140421102650 : ("+e+")";
          return false;
         }

         // charger l'argument "O,1,..n" dans String chaineDest
         chaineDest="";
         for(int i=0; i<chaineSource.length(); i++){
          if( ((chaineSource.charAt(i)>='0') &&
              (chaineSource.charAt(i)<='9')) || 
              (chaineSource.charAt(i)==',')
           ){
           chaineDest=chaineDest+chaineSource.charAt(i);
          }
         }

         // charger l'argument O,1,..n dans String[] chaineTableau
         chaineTableau=chaineDest.split(",");

//System.out.println("TRACE ===> chaineTableau.length="+chaineTableau.length);


         // charger l'argument ["O","1",.."n"] byte[] tableauBytes
         tableauBytes = new byte[chaineTableau.length];
         for(int i=0; i<chaineTableau.length; i++){
          try{
            int j=Integer.parseInt(chaineTableau[i]);
            tableauBytes[i]=(byte)j;
          }catch (NumberFormatException e){
           message="ECHEC java 140312165515 e=["+e+"]";
           return false;
          }
         }

         envoiThread();

         return true;
    } // fin de boolean essai(String args[])


} // fin de class TCPTSock
