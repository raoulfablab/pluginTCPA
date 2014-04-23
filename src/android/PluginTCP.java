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
   **  140421080000 : Développement                                 **
   **                 Pour facilter la mise au point PluginTCP.     **
   **                                                               **
   **                                                               **
   **   TCPMainA.java devient :                                     **
   **                 public class PluginTCP extends CordovaPlugin  **
   **                                                               **
   **   TCPSock.java                                                **
   **   TCPThreadA.java      :                                      **
   **                 classes supplémentaires                       **
   **                                                               **
   **   CallbackContext.java :                                      **
   **                 simule une procédure js existant déjà         **
   **                                                               **
   **   Test.java                                                   **
   **                 joue le role de l'appel js                    **
   **                                                               **
   **                                                               **
   ** ------------------------------------------------------------- **
   **                                                               **
   **   Les 3 classes suivantes                                     **
   **                                                               **
   **   TCPMainA.java devient PluginTCP                             **
   **   TCPSock.java                                                **
   **   TCPThreadA.java                                             **
   **                                                               **
   **   sont copiées (et ajustées) dans :                           **
   **                                                               **
   **   /home/leo/Lsites/GIT/pluginTCPA                             **
   **                                                               **
   **                                                               **
   *******************************************************************

  find .|grep java|xargs grep -si 140420070024

 */

   // modèle :
   // https://github.com/gramakri/cordova-plugin-datagram/blob/master/src/android/Datagram.java
   // http://www.liafa.jussieu.fr/~sighirea/cours/reseauxM/java.thread.html

public class PluginTCP extends CordovaPlugin {

 boolean ok=false;
 String message="";

 String portString ="";  // "8899"
 String ipString="";     // "192.168.0.199";
 int portInt=0;

 TCPSock tcpSock;
 CallbackContext callbackContext;

 Thread tCPThreadA=null;

    /* ******************************************
     * Sommaire
     *
     * 140421162700 Constructeur 
     * 140421162701 execute      ==> "param" "essai" "connecter" "deconnecter" "init"
     * 140421162702 init(String[] args)
     * 140421162703
     * 140421162704
     * 140421162705
     */

    
 
    /* ******************************************
     * Constructeur (140421162700)
     */
    public PluginTCP() {
       

    }

    /* ******************************************
     * Analyse action demandée par HTML5 cordova js
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    //public boolean execute(String action, String args[], CallbackContext callbackContextx) {
    // callbackContext=callbackContextx; // debug
     message="";
     ok=false;
     if("param".equals(action)) { // init IP et numPort
      ok=param(args); // String[] args = {"192.168.0.199","8899"};
//System.out.println("140421170900 args="+args[0]+","+args[1]);
     }else
      if("essai".equals(action)) { // émision message
       ok=tcpSock.essai(args); // String[] args[0]="[0,1,2,3,4]";
       message=tcpSock.message;
      }else
        if("connecter".equals(action)) {
       }else
        if("deconnecter".equals(action)) {
        }else
         if("initSocket".equals(action)) {
          ok=initSocket(); // args non utilisé
          message=tcpSock.message;
         }else{
           message="\nERROR!!! java 140418082542 : action inconnue";
           ok=false;
         }

     if(ok) message="[PluginTCP.execute() action="+action+"]";
     else message="[PluginTCP.execute() action="+action+" : ["+message+"]]";

     if (ok) {callbackContext.success(message); return true;}
     else { callbackContext.error(message); return false;}

    } // fin de public boolean execute

/*

init : tcpSock = new TCPSock(); ==> tCPThreadA = new TCPThreadA(tcpSock);

*/

    /* ******************************************
     *  initialiser ip et port
     */
    public boolean param(JSONArray args) {
    //public boolean param(String args[]) {
      String chaineSource="";
      message="";


          try{
//System.out.println("args.length()="+args.length());
//System.out.println("args.getString(0)="+args.getString(0));
//System.out.println("args.getString(1)="+args.getString(1));

           chaineSource = "";
           chaineSource = args.getString(0);
           //chaineSource = args[0];
           ipString=chaineSource;

           chaineSource = args.getString(1);
           //chaineSource = args[1];

           portString=chaineSource;
           portInt=Integer.parseInt(portString);
           
           message="TCPSock.param() :"+"ip="+ipString+" port="+portInt;
           return true;

           } catch (JSONException e){
	    message="\nERROR!!! java JSONException 140418092340 ("+e+")";
            return false;
           }

    } // fin de public void param

    /* ******************************************
     * initSocket() : instancier tCPThreadA (140421162702)
     */
    //public boolean initSocket(String[] args) {
    public boolean initSocket() {
     message="";
     tcpSock = new TCPSock();
//System.out.println("================ TRACE 1 ============");

     tcpSock.initAdresse(ipString,portInt);

     ok=tcpSock.connecterSock();
     if(!ok){
      message="\nERROR!!! PluginTCP.initSocket() java [\n"+tcpSock.message+"\n]";
      return false;
     }
//System.out.println("================ TRACE 2 ============");

     return true;
    } // fin de public void param

} // fin de class PluginTCP

