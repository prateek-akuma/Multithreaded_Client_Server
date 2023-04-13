/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericnode;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Blob;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap; 
import java.io.BufferedInputStream;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject; 
/**
 *
 * @author wlloyd
 */
public class GenericNode extends UnicastRemoteObject implements RMI 
{  
    /**
     * @param args the command line arguments
     */
    static HashMap <String, String> hm = new HashMap <String, String>();
    static HashMap <String, String> hts = new HashMap <String, String>();
      public GenericNode() throws RemoteException {
        super();
    }
    public void put(String key, String val) throws RemoteException {
        hm.put(key, val);
        System.out.println("server response:put key= " + key);
    }
    
    public String get(String key) throws RemoteException {
        System.out.println("server response: key= " + key+" val= "+hm.get(key));
            return hm.get(key);    
    }
    public String store() throws RemoteException{
        String temp=" ";
        for(String i: hm.keySet()){
            temp=temp+"\n"+"key: "+ i+ " value: "+hm.get(i);
        }
        System.out.println("server response: " + temp);
        return temp;
    }
    
    public void exit() throws RemoteException{
        System.exit(0);
    }  
    public static void main(String[] args) throws IOException {
         

        if (args.length > 0)
        {
            if (args[0].equals("rmis"))
            {
                System.out.println("RMI SERVER");
                try
                {
                   RMI r=new GenericNode();
                   Registry reg=LocateRegistry.getRegistry();
                   reg.bind("GenericNode", r);                   
                    // insert code to start RMI Server
                }
                catch (Exception e)
                {
                    System.out.println("Error initializing RMI server.");
                    e.printStackTrace();
                }
            }
            if (args[0].equals("rmic"))
            {
                System.out.println("RMI CLIENT");
                String addr = args[1];
                String cmd = args[2];
                String key = (args.length > 3) ? args[3] : "";
                String val = (args.length > 4) ? args[4] : "";
                // insert code to make RMI client request 
                try{
                Registry regi = LocateRegistry.getRegistry(addr);
                    RMI stub = (RMI)regi.lookup("GenericNode");
                    if (cmd.equals("put")) {
                        stub.put(key, val);
                         System.out.println("server response:put key= " + key);
                    } else if (cmd.equals("get")) {
                        String msg = stub.get(key);
                        System.out.println("server response: get key= "+key+"val= "+msg);                        
                    } 
                    else if(cmd.equals("del")){
                        hm.remove(key);
                        System.out.println("server response del key= "+key);
                    }
                    else if(cmd.equals("exit")){
                        stub.exit();
                        System.out.println("Closing Client...");
                        System.exit(0);
                    }
                    else if(cmd.equals("store")){
                        //String temp="";
                        String msg="";
                    //for(String i: hm.keySet()){
                        msg=stub.store();
                        //temp=temp+"\n"+"key: "+ i+ " value: "+hm.get(i);
                        ///System.out.println("key: "+ i+ " value: "+hm.get(i));
                       
                    //}
                    System.out.println(msg);
                    }
                }
                catch(Exception e){}  
               
            }
            if (args[0].equals("tc"))
            {
                System.out.println("TCP CLIENT");
                String addr = args[1];
                int port = Integer.parseInt(args[2]);
                String cmd = args[3];
                String key = (args.length > 4) ? args[4] : "";
                String val = (args.length > 5) ? args[5] : "";
                SimpleEntry<String, String> se = new SimpleEntry<String, String>(key, val);
                // insert code to make TCP client request to server at addr:port
                
                Socket s = new Socket(addr, port);
                
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                dout.writeUTF(cmd+" "+key+"+"+val);
                DataInputStream input = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                //System.out.println("123");
                String br= input.readUTF();
                System.out.println("Server response: "+br);
                s.close();
            }
            
            if (args[0].equals("ts"))
            {   
               
               
                //BufferedReader reader;
                //reader= new BufferedReader(new FileReader("nodes.cfg"));
                //String line= reader.readLine();
         
                /*while(line!=""){
                    System.out.println(line);
                    //InetAddress address=InetAddress.getByName(args[2]);
                    //ServerSocket serverSocket = new ServerSocket(port,50, address);
                    String[] s=line.split(":");
                    InetAddress addressi=InetAddress.getByName(s[0]);
                    int porti=Integer.parseInt(s[1]);
                    Socket si = new Socket(addressi, porti);
                    //Socket si = new Socket(addressi, porti);
                    DataOutputStream douti = new DataOutputStream(si.getOutputStream());
                    douti.writeUTF("yooo anna");
                    DataInputStream inputi = new DataInputStream(new BufferedInputStream(si.getInputStream()));
                    System.out.println(inputi.readUTF());
                    //line= reader.readLine();
                }
                   */
                System.out.println("TCP SERVER");
                int port = Integer.parseInt(args[1]);
                InetAddress address1=InetAddress.getByName(args[2]);
                // insert code to start TCP server on port
                //ServerSocket s = new ServerSocket(port);
                ServerSocket s2 = new ServerSocket(port,50, address1);
                String[] thisip=s2.getInetAddress().toString().split("/");
                //System.out.println("this server add is "+thisip[1]);
                System.out.println("Waiting for client connection...");
                while (true) {
                //System.out.println("Waiting for client connection...");
                Socket ss = s2.accept();
                System.out.println("New Client Connected"

                                   + ss.getInetAddress() 

                                         .getHostAddress());
                DataInputStream dout = new DataInputStream(ss.getInputStream());
                DataOutputStream output= new DataOutputStream(ss.getOutputStream());
                String yoo= dout.readUTF();
                System.out.println("Client says: "+ yoo );
                String cmd=yoo.substring(0, yoo.indexOf(" "));
                String key=yoo.substring(yoo.indexOf(" ")+1, yoo.indexOf("+"));
                String val=yoo.substring(yoo.indexOf("+")+1);
                System.out.println("comd "+cmd+" key "+key+" val "+val);
                if (cmd.equalsIgnoreCase("exit")){
                    output.writeUTF("Exited");
                    ss.close();
                    break;
                }
                else{
                    
                    Thread t = new ClientHandler(ss,cmd,key,val,output,thisip);
                    t.start();
                }
                
                 
                
            }            
            }

            if (args[0].equals("uc"))
            {
               System.out.println("UDP CLIENT");
                String addr = args[1];
                int sendport = Integer.parseInt(args[2]);
                int recvport = sendport + 1;
                String cmd = args[3];
                String key = (args.length > 4) ? args[4] : "";
                String val = (args.length > 5) ? args[5] : "";
                //SimpleEntry<String, String> se = new SimpleEntry<String, String>(key, val);
                // insert code to make UDP client request to server at addr:send/recvport
                DatagramSocket socket=new DatagramSocket();
                DatagramSocket socket1=new DatagramSocket(recvport);
                InetAddress ip = InetAddress.getByName(addr);
                String message=cmd+" "+key+"+"+val+"\n";
                byte[] buf=message.getBytes();
                DatagramPacket sp=new DatagramPacket(buf,buf.length,ip,sendport);
                socket.send(sp);
                byte[] receive=new byte[65536];
                DatagramPacket rp=new DatagramPacket(receive,receive.length);     
                socket1.receive(rp);
                //System.out.println("Socket1 add: "+rp.getAddress());
                //System.out.println("Socket add n: "+sp.getAddress());
                String data=new String(rp.getData(),0,rp.getLength());
                System.out.println("received response "+data);
                //System.out.println("Source port number: " + rp.getPort());
                socket.close();
                socket1.close();
                
            }
            if (args[0].equals("us"))
            {
                
               System.out.println("UDP SERVER");
                int port = Integer.parseInt(args[1]);
                
                // insert code to start UDP server on port
                DatagramSocket s=new DatagramSocket(port);
               
                byte[] receivedata=new byte[1024];
                while(true){
                DatagramPacket rp = new DatagramPacket(receivedata, receivedata.length);
                s.receive(rp);
                String message=new String(rp.getData(),0,rp.getLength());
                System.out.println("Client MSG: "+ message);
                String cmd=message.substring(0, message.indexOf(" "));
                String key=message.substring(message.indexOf(" ")+1, message.indexOf("+"));
                String val=message.substring(message.indexOf("+")+1,message.indexOf("\n"));
                System.out.println(cmd+" key "+key+" val "+val);
                InetAddress caddr = rp.getAddress();
                System.out.println(rp.getSocketAddress());
                int cport = rp.getPort();
                if(cmd.equalsIgnoreCase("exit")){
                    System.out.println("the server is shutting down");
                    String msg="the server is shutting down";
                    byte[] sendata = msg.getBytes();
                    DatagramPacket sp = new DatagramPacket(sendata, sendata.length, caddr, port+1);
                    s.send(sp);
                    s.close();
                }
                else{
                    new Thread(new Responder(s,cmd,key,val,port,caddr)).start();
                }
                
                }
            }

        }
        else
        {
            String msg = "GenericNode Usage:\n\n" +
                         "Client:\n" +
                         "uc/tc <address> <port> put <key> <msg>  UDP/TCP CLIENT: Put an object into store\n" + 
                         "uc/tc <address> <port> get <key>  UDP/TCP CLIENT: Get an object from store by key\n" + 
                         "uc/tc <address> <port> del <key>  UDP/TCP CLIENT: Delete an object from store by key\n" + 
                         "uc/tc <address> <port> store  UDP/TCP CLIENT: Display object store\n" + 
                         "uc/tc <address> <port> exit  UDP/TCP CLIENT: Shutdown server\n" + 
                         "rmic <address> put <key> <msg>  RMI CLIENT: Put an object into store\n" + 
                         "rmic <address> get <key>  RMI CLIENT: Get an object from store by key\n" + 
                         "rmic <address> del <key>  RMI CLIENT: Delete an object from store by key\n" + 
                         "rmic <address> store  RMI CLIENT: Display object store\n" + 
                         "rmic <address> exit  RMI CLIENT: Shutdown server\n\n" + 
                         "Server:\n" +
                         "us/ts <port>  UDP/TCP SERVER: run udp or tcp server on <port>.\n" +
                         "rmis  run RMI Server.\n";
            System.out.println(msg);
        }                       
        
        
    }
    
 

    public static class ClientHandler extends Thread{
        
        final Socket s;
        final String cmd;
        final String key;
        final String val;
        final DataOutputStream output;
        final String[] thisip;

        public ClientHandler(Socket s,String cmd, String key, String val, DataOutputStream output,String[] thisip){
            this.s=s;
            this.cmd=cmd;
            this.key=key;
            this.val=val;
            this.output=output;
            this.thisip=thisip;
            
        }
        public void run(){

            try{
                
                
        
                if (cmd.equalsIgnoreCase("pput1")){
                    //System.out.println("inside pput1");
                    if(hts.containsKey(key)){
                        //System.out.println(key+" present");
                        if("lock".equals(hts.get(key))){
                            System.out.println("locked" + key);
                            output.writeUTF("abort");
                        }
                        else{
                            System.out.println(key+" is unlocked");
                            hts.put(key, "lock");
                            output.writeUTF("ack");
                        }
                        
                    }
                    else{
                        System.out.println(key+" not present");
                        output.writeUTF("ack");
                    }
                }
                
                if (cmd.equalsIgnoreCase("ddel1")){
                    //System.out.println("inside ddel1");
                    if(hts.containsKey(key)){
                        System.out.println(key+" present");
                        if("lock".equals(hts.get(key))){
                            System.out.println("locked" + key);
                            output.writeUTF("abort");
                        }
                        else{
                            System.out.println(key+" is unlocked");
                            hts.put(key, "lock");
                            output.writeUTF("ack");
                        }
                        
                    }
                    else{
                        System.out.println(key+" not present");
                        output.writeUTF("ack");
                    }
                }
                
                if (cmd.equalsIgnoreCase("pput2")){
                    //System.out.println("inside pput2");
                    if(hts.containsKey(key)){
                        //System.out.println(key+" present");
                        hm.put(key, val);
                        hts.put(key,"unlock");
                        output.writeUTF("success");
                    }
                    else{
                        //System.out.println(key+" not present so initializing");
                        hm.put(key, val);
                        hts.put(key,"unlock");
                        output.writeUTF("success");
                    }
                }
                
                if (cmd.equalsIgnoreCase("ddel2")){
                    //System.out.println("inside ddel2");
                    if(hts.containsKey(key)){
                        System.out.println(key+" present");
                        hm.remove(key);
                        hts.remove(key);
                        output.writeUTF("success");
                    }
                    else{
                        output.writeUTF("success");
                    }
                    
                }
                
                if(cmd.equalsIgnoreCase("dputabort")){
                    hts.put(key,"unlock");
                    output.writeUTF("aborted");
                }
                if(cmd.equalsIgnoreCase("ddelabort")){
                    hts.put(key,"unlock");
                    output.writeUTF("aborted");
                }
                
                if(cmd.equalsIgnoreCase("get")){
                    String yy = hm.get(key);
                    output.writeUTF("get key=" + key + " val=" + yy);
                }
                if (cmd.equalsIgnoreCase("store")) {
                    String temp = "";
                    for (String i : hm.keySet()) {
                        temp = temp + "\n" + "key: " + i + " value: " + hm.get(i);
                    }
                    output.writeUTF(temp);
                }
                
                if (cmd.equalsIgnoreCase("del")){
                BufferedReader reader;
                reader= new BufferedReader(new FileReader("./nodes.cfg"));
                String line= reader.readLine();
                String myip=thisip[1]+":4040";
                int countip=0;
                int countack=0;
                String track="";
                boolean Abort = false;
                while(line!=null){
                    countip=countip+1;
       
     
                    if(line.equals(myip)){
                        //System.out.println(line+" KABOOM!!!");
                        line= reader.readLine();
                        continue;
                    }
                    System.out.println(line);
                    //InetAddress address=InetAddress.getByName(args[2]);
                    //ServerSocket serverSocket = new ServerSocket(port,50, address);
                    String[] s=line.split(":");
                    //InetAddress addressi=InetAddress.getByName(s[0]);
                    String addressi=s[0];
                    int porti=Integer.parseInt(s[1]);
                    System.out.println(addressi +"  "+porti);
                    Socket si = new Socket(addressi, porti);
                    //Socket si = new Socket(addressi, porti);
                    DataOutputStream douti = new DataOutputStream(si.getOutputStream());
                    douti.writeUTF("ddel1"+" "+key+"+"+val);
                    DataInputStream inputi = new DataInputStream(new BufferedInputStream(si.getInputStream()));
                    if ("ack".equals(inputi.readUTF()))
                    {
                        countack=countack+1;
                        track=track+s[0]+":"+porti+" ";
                    }
                    else{
                        Abort=true;
                    }
                    si.close();
          
                    line= reader.readLine();
                }
                if(Abort){
                    countack=-1;
                    String[] tracknodes= track.split(" ");
                    for (String tracknode : tracknodes) {
                        System.out.println("Reset node is " + tracknode);
                        String[] tempe=tracknode.split(":");
                        Socket s3 = new Socket(tempe[0],Integer.parseInt(tempe[1]));
                    //Socket si = new Socket(addressi, porti);
                    DataOutputStream douti = new DataOutputStream(s3.getOutputStream());
                    douti.writeUTF("ddelabort" + " " + key + "+" + val);
                    DataInputStream inputi = new DataInputStream(new BufferedInputStream(s3.getInputStream()));
                    if ("aborted".equals(inputi.readUTF())) {
                        System.out.println("Aborted");
                    }
                    s3.close();
           
                    }
                    
                    
                    
                }
                System.out.println((countip-1)+"  "+countack);
                BufferedReader reader1;
                reader1= new BufferedReader(new FileReader("./nodes.cfg"));
                String line1= reader1.readLine();
                if((countip-1)== countack){
                    int countip1=0;
                    int countsuc=0;
                    while(line1!=null){
                        countip1=countip1+1;
          
                    if(line1.equals(myip)){
                        //System.out.println(line1+" KABOOM2!!!");
                        line1= reader1.readLine();
                        continue;
                    }
                    System.out.println(line1);
                    //InetAddress address=InetAddress.getByName(args[2]);
                    //ServerSocket serverSocket = new ServerSocket(port,50, address);
                    String[] s=line1.split(":");
                    //InetAddress addressi=InetAddress.getByName(s[0]);
                    String addressi=s[0];
                    int porti=Integer.parseInt(s[1]);
                    System.out.println(addressi +"  "+porti);
                    Socket si = new Socket(addressi, porti);
                    //Socket si = new Socket(addressi, porti);
                    DataOutputStream douti = new DataOutputStream(si.getOutputStream());
                    douti.writeUTF("ddel2"+" "+key+"+"+val);
                    DataInputStream inputi = new DataInputStream(new BufferedInputStream(si.getInputStream()));
                    if ("success".equals(inputi.readUTF()))
                    {
                        countsuc=countsuc+1;
                    }
                    si.close();
                    line1= reader1.readLine();
                }
                    if((countip1-1)==countsuc){
                        
                        hm.remove(key);
                        hts.remove(key);
                        output.writeUTF("del key="+key);
                    }
                    else{
                        output.writeUTF("Server error: del key="+key);
                    }
                }
                else{
                    System.out.println("Aborted");
                }
                   
                   
                }
                
                if (cmd.equalsIgnoreCase("put")){
                BufferedReader reader;
                reader= new BufferedReader(new FileReader("./nodes.cfg"));
                String line= reader.readLine();
                String myip=thisip[1]+":4040";
                int countip=0;
                int countack=0;
                String track="";
                boolean Abort = false;
                while(line!=null){
                    countip=countip+1;
       
          
                    if(line.equals(myip)){
                        System.out.println(line+" KABOOM!!!");
                        line= reader.readLine();
                        continue;
                    }
                    System.out.println("ip in nodes.cfg is "+line);
                    //InetAddress address=InetAddress.getByName(args[2]);
                    //ServerSocket serverSocket = new ServerSocket(port,50, address);
                    String[] s=line.split(":");
                    //InetAddress addressi=InetAddress.getByName(s[0]);
                    if(s.length<2){
                        System.out.println("not line not string");
                        break;
                    }
                    
                    String addressi=s[0];
                    int porti=Integer.parseInt(s[1]);
                    System.out.println(addressi +"  "+porti);
                    Socket si = new Socket(addressi, porti);
                    //Socket si = new Socket(addressi, porti);
                    DataOutputStream douti = new DataOutputStream(si.getOutputStream());
                    douti.writeUTF("pput1"+" "+key+"+"+val);
                    DataInputStream inputi = new DataInputStream(new BufferedInputStream(si.getInputStream()));
                    if ("ack".equals(inputi.readUTF()))
                    {
                        countack=countack+1;
                        track=track+s[0]+":"+porti+" ";
                    }
                    else{
                        Abort=true;
                    }
                    si.close();
          
                    line= reader.readLine();
                }
                if(Abort){
                    countack=-1;
                    String[] tracknodes= track.split(" ");
                    for (String tracknode : tracknodes) {
                        System.out.println("Reset node is " + tracknode);
                        String[] tempe=tracknode.split(":");
                        Socket s3 = new Socket(tempe[0],Integer.parseInt(tempe[1]));
                    //Socket si = new Socket(addressi, porti);
                    DataOutputStream douti = new DataOutputStream(s3.getOutputStream());
                    douti.writeUTF("dputabort" + " " + key + "+" + val);
                    DataInputStream inputi = new DataInputStream(new BufferedInputStream(s3.getInputStream()));
                    if ("aborted".equals(inputi.readUTF())) {
                        System.out.println("Aborted");
                    }
                    s3.close();
           
                    }
                    
                    
                    
                }
                System.out.println((countip-1)+"  "+countack);
                BufferedReader reader1;
                reader1= new BufferedReader(new FileReader("./nodes.cfg"));
                String line1= reader1.readLine();
                if((countip-1)== countack){
                    int countip1=0;
                    int countsuc=0;
                    while(line1!=null){
                        countip1=countip1+1;
          
                    if(line1.equals(myip)){
                        //System.out.println(line1+" KABOOM2!!!");
                        line1= reader1.readLine();
                        continue;
                    }
                    System.out.println(line1);
                    //InetAddress address=InetAddress.getByName(args[2]);
                    //ServerSocket serverSocket = new ServerSocket(port,50, address);
                    String[] s=line1.split(":");
                    //InetAddress addressi=InetAddress.getByName(s[0]);
                    String addressi=s[0];
                    int porti=Integer.parseInt(s[1]);
                    System.out.println(addressi +"  "+porti);
                    Socket si = new Socket(addressi, porti);
                    //Socket si = new Socket(addressi, porti);
                    DataOutputStream douti = new DataOutputStream(si.getOutputStream());
                    douti.writeUTF("pput2"+" "+key+"+"+val);
                    DataInputStream inputi = new DataInputStream(new BufferedInputStream(si.getInputStream()));
                    if ("success".equals(inputi.readUTF()))
                    {
                        countsuc=countsuc+1;
                    }
                    si.close();
                    line1= reader1.readLine();
                }
                    if((countip1-1)==countsuc){
                        
                        hm.put(key, val);
                        hts.put(key, "unlock");
                        output.writeUTF("put key="+key);
                    }
                    else{
                        output.writeUTF("Server error: put key="+key);
                    }
                }
                else{
                    System.out.println("Aborted");
                }
                   
                   
                }
            
            
            }
            catch (IOException e) { 

                e.printStackTrace(); 

            } 
                
            
        }
        

    }
    
    public static class Responder implements Runnable {

    DatagramSocket s = null;
    final String cmd;
    final String key;
    final String val;
    final int port;
    final InetAddress caddr;
    

    public Responder(DatagramSocket s, String cmd, String key, String val, int port, InetAddress caddr) {
        this.s = s;
        //this.packet = packet;
        this.cmd=cmd;
        this.key=key;
        this.val=val;
        this.port=port;
        this.caddr=caddr;
    }

    public void run() {

        try{
            System.out.println("Server check: "+ caddr);

        if (cmd.equalsIgnoreCase("put")){
                    hm.put(key,val);
                    String msg="put key="+key;
                    byte[] sendata = msg.getBytes();
                    DatagramPacket sp = new DatagramPacket(sendata, sendata.length, caddr, port+1);
                    s.send(sp);
                }
        else if(cmd.equalsIgnoreCase("get")){
                    String value=hm.get(key);
                    String msg="server response get: key="+key+" val="+value;
                    byte[] sendata = msg.getBytes();
                    DatagramPacket sp = new DatagramPacket(sendata, sendata.length, caddr, port+1);
                    s.send(sp);
                }
        else if(cmd.equalsIgnoreCase("store")){
                    
                    String temp="";
                    
                    for(String i: hm.keySet()){
                        temp=temp+"key: "+ i+ " value: "+ hm.get(i)+"\n";
                    }
                    
                    byte[] sendata = temp.getBytes();
                    System.out.println(temp);
                    DatagramPacket sp = new DatagramPacket(sendata, sendata.length, caddr, port+1);
                    s.send(sp);
                }
        else if(cmd.equalsIgnoreCase("del")){
                    hm.remove(key);
                    String msg="server response delete: key="+key;
                    byte[] sendata = msg.getBytes();
                    DatagramPacket sp = new DatagramPacket(sendata, sendata.length, caddr, port+1);
                    s.send(sp);
                }
        }
        catch (Exception e) { 

                e.printStackTrace(); 

            } 

    }
}
}
