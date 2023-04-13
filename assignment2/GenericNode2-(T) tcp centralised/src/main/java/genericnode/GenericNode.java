

package genericnode;



import java.util.Dictionary;

import java.util.Enumeration;

import java.util.Hashtable;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.InetSocketAddress;

import java.net.ServerSocket;

import java.net.Socket;

import java.sql.Blob;

import java.util.AbstractMap.SimpleEntry;

import java.util.concurrent.ConcurrentHashMap;

import java.rmi.*;

import java.rmi.registry.LocateRegistry;

import java.rmi.registry.Registry;

import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;







/**

 *

 * @author wlloyd

 */

public class GenericNode extends UnicastRemoteObject 

{  

    /**

     * @param args the command line arguments

     */

    static HashMap <String, String> hm = new HashMap <String, String>();

    static int l=0;

    static String centraltcp;

    static int tcpport;

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

                String hostname="192.168.56.1";

                Socket socket=new Socket(addr,port);

                OutputStream output = socket.getOutputStream();

                PrintWriter writer = new PrintWriter(output, true);

                if(cmd.equals("exit")){

                writer.println(cmd);

                }else{

                    writer.println(cmd);

                writer.println(key+","+val+","+cmd);

                }

                InputStream input = socket.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

               

                String line = reader.readLine();

                System.out.println(line);

               

               

               

               

               

            }



            if (args[0].equals("ts"))

            {

                System.out.println("TCP SERVER");

                int port = Integer.parseInt(args[1]);

               

                ServerSocket serverSocket = new ServerSocket(port);

                serverSocket.setReuseAddress(true);

                if(port != 1440){

                centraltcp=args[2];

                tcpport = Integer.parseInt(args[3]);

                Socket centralSocket=new Socket(centraltcp,1440);

                OutputStream output1 = centralSocket.getOutputStream();

                PrintWriter writer1 = new PrintWriter(output1, true);

                String cmd1="put";

                Socket socketip=new Socket();

                socketip.connect(new InetSocketAddress("google.com",80));

                String ip1 = socketip.getLocalAddress().getHostAddress();

                writer1.println(cmd1);

                writer1.println(ip1+","+port+","+cmd1);

                InputStream input1 = centralSocket.getInputStream();

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(input1));

               

                String line1 = reader1.readLine();

                }

                

               

                while(true){

                Socket socket = serverSocket.accept();

                

                               

               

                

                ClientHandlerTCP clientSock

                    = new ClientHandlerTCP(socket);

                   

 

                // This thread will handle the client

                // separately

                new Thread(clientSock).start();



                //break the while loop if cmd is exit

               

                }

                // insert code to start TCP server on port

            }

            if (args[0].equals("uc"))

            {

                try{

                System.out.println("UDP CLIENT");

                String addr = args[1];

                int sendport = Integer.parseInt(args[2]);

                int recvport = sendport + 1;

                String cmd = args[3];

                String key = (args.length > 4) ? args[4] : "";

                String val = (args.length > 5) ? args[5] : "";

                InetAddress ip = InetAddress.getByName(addr);

                Socket socketip=new Socket();

                socketip.connect(new InetSocketAddress("google.com",80));

                String ip1 = socketip.getLocalAddress().getHostAddress();

                SimpleEntry<String, String> se = new SimpleEntry<String, String>(key, val);

                // insert code to make UDP client request to server at addr:send/recvport

                DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = (key+","+val+","+cmd+","+sendport+","+recvport+","+ip1).getBytes();

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,sendport);

                ds.send(DpSend);

                DatagramSocket ds1 = new DatagramSocket(recvport);

                byte[] receive = new byte[65535];

             

                    DatagramPacket DpReceive = null;

                   

 

       

            DatagramPacket DpReceive1 = new DatagramPacket(receive, receive.length);

 

            // Step 3 : revieve the data in byte buffer.

            ds1.receive(DpReceive1);

            ds1.close();

            ds.close();

 

            System.out.println("Server:-" + data(receive));

                }

                catch (Exception e)

                {

                    System.out.println("Server was Occupied !");

                   

                }

            }

            if (args[0].equals("us"))

            {

                System.out.println("UDP SERVER");

                try{

                final int port = Integer.parseInt(args[1]);

                DatagramSocket ds = new DatagramSocket(port);

               

                // insert code to start UDP server on port

                while(true){

                   

                   

                    byte[] receive = new byte[65535];

             

                    //DatagramPacket DpReceive = null;

                   

 

       

            DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);

 

            // Step 3 : revieve the data in byte buffer.

            ds.receive(DpReceive);

            String addr1=DpReceive.getAddress().toString();  

            System.out.println("Client:-" + data(receive));

            String[] s=data(receive).toString().split("[,]",0);

            String key=s[0];

            String val=s[1];

            String cmd=s[2];

            String sendport=s[3];

            String recvport=s[4];

            String addr=s[5];

            System.out.println("Client:-"+key+":"+val+":"+addr+":"+addr1);

            System.out.println(DpReceive.getAddress().toString());

            if(cmd.equals("exit")){

                DatagramSocket ds1 = new DatagramSocket();

                byte buf[] = null;

                buf = ("Server shutdown !").getBytes();

                InetAddress ip = InetAddress.getByName(addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, DpReceive.getAddress(),Integer.parseInt(recvport));

                ds.send(DpSend);

                System.out.println("Shutting Down Server !!");

                break;

            }



           

                ClientHandlerUDP clientSock

                    = new ClientHandlerUDP(ds,key,val,cmd,sendport.toString(),recvport.toString(),addr);

                new Thread(clientSock).start();    

               

                }

                }

                catch (Exception e)

                {

                    System.out.println("Server was Occupied !");

                   

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

   

  public static StringBuilder data(byte[] a)

    {

        if (a == null)

            return null;

        StringBuilder ret = new StringBuilder();

        int i = 0;

        while (a[i] != 0)

        {

            ret.append((char) a[i]);

            i++;

        }

        return ret;

    }

   

   



private static class ClientHandlerTCP implements Runnable {

    private final Socket clientSocket;

   

   



    // Constructor

    public ClientHandlerTCP(Socket socket)

    {

        this.clientSocket = socket;

    }



    public void run()

    {

        PrintWriter out = null;

        BufferedReader in = null;

        try {

            

                

               

            InputStream input = clientSocket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String cmd = reader.readLine();

            System.out.println("packet recived:"+cmd);

            

            

            if(cmd.equals("put")){

                

                

                String line = reader.readLine();

                String str[]=line.split("[,]",0);

                String key=str[0];

                String val = str[1];

                if(hm.containsKey(key)){

                //synchronized(gn.hm){

            System.out.println(key+":"+val+":"+cmd);

            //OutputStream output = clientSocket.getOutputStream();

            //PrintWriter writer = new PrintWriter(output, true);

            ///writer.println("key entered");

            hm.put(key,val);

            System.out.println(hm);

                //}

            }

            else{

                System.out.println("-----new key-----");

                System.out.println(key+":"+val+":"+cmd);

            OutputStream output = clientSocket.getOutputStream();

            PrintWriter writer = new PrintWriter(output, true);

            writer.println("key entered");

            hm.put(key,val);

            System.out.println(hm);

            }

            }

            else if(cmd.equals("exit")){

                System.out.println("Shutting system down !!");

                OutputStream output = clientSocket.getOutputStream();

                PrintWriter writer = new PrintWriter(output, true);

                Socket centralSocket=new Socket(centraltcp,1440);

                OutputStream output1 = centralSocket.getOutputStream();

                PrintWriter writer1 = new PrintWriter(output1, true);

                String cmd1="del";

                Socket socketip=new Socket();

                socketip.connect(new InetSocketAddress("google.com",80));

                String ip1 = socketip.getLocalAddress().getHostAddress();

                writer1.println(cmd1);

                writer1.println(ip1+","+"port"+","+cmd1);

                InputStream input1 = centralSocket.getInputStream();

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(input1));

                writer.println("Server ShutDown");

                output.close();

            }

            else if(cmd.equals("dput1")){

                if(l==0){

                    Socket centralSocket=new Socket(centraltcp,1440);

                    OutputStream output1 = centralSocket.getOutputStream();

                    PrintWriter writer1 = new PrintWriter(output1, true);

                String cmd1="store";

                writer1.println(cmd1);

                writer1.println("21312434"+","+"2345"+","+cmd1);

                InputStream input1 = centralSocket.getInputStream();

                BufferedReader reader1 = new BufferedReader(new InputStreamReader(input1));

               

                String line1 = reader1.readLine();

                String str[]=line1.split("[,]",0);

                for (int i=0;i<str.length;i++){

                    System.out.println(str[i]);

                }

                System.out.println(line1);

                

                }

                String line = reader.readLine();

                System.out.println("packet recived:"+line);

                String str[]=line.split("[,]",0);

                String key = str[0];

                String val= str[1];

                if((hm.containsKey(key))){

                OutputStream output = clientSocket.getOutputStream();

            PrintWriter writer = new PrintWriter(output, true);

            String s=hm.get(key);

                writer.println(s);

                }else{

                    OutputStream output = clientSocket.getOutputStream();

                    PrintWriter writer = new PrintWriter(output, true);

                    writer.println("no Such key !");

                }

            }

            else if(cmd.equals("get")){

                String line = reader.readLine();

                System.out.println("packet recived:"+line);

                String str[]=line.split("[,]",0);

                String key = str[0];

                if((hm.containsKey(key))){

                OutputStream output = clientSocket.getOutputStream();

            PrintWriter writer = new PrintWriter(output, true);

            String s=hm.get(key);

                writer.println(s);

                }else{

                    OutputStream output = clientSocket.getOutputStream();

                    PrintWriter writer = new PrintWriter(output, true);

                    writer.println("no Such key !");

                }

            }

            else if(cmd.equals("del")){

                String line = reader.readLine();

                System.out.println("packet recived:"+line);

                String str[]=line.split("[,]",0);

                String key = str[0];

                if((hm.containsKey(key))){

                    synchronized(hm){

                OutputStream output = clientSocket.getOutputStream();

            PrintWriter writer = new PrintWriter(output, true);

            String s=hm.remove(key);

                writer.println(s);

                    }

                }

                else{

                    OutputStream output = clientSocket.getOutputStream();

                    PrintWriter writer = new PrintWriter(output, true);

                    writer.println("no Such key !");

                }

            }else if((cmd.equals("store"))){

                System.out.println((hm.entrySet()).toString().getBytes("UTF-8").length);

                OutputStream output = clientSocket.getOutputStream();

                    PrintWriter writer = new PrintWriter(output, true);

                    if((hm.entrySet()).toString().getBytes("UTF-8").length < 65000){

                    writer.println(hm.entrySet());

                    writer.println("returned all possible key value pairs");

                    }else{

                       

                       /* Enumeration en=hm.keys();

                        String key5="TRIMMED:";

                        for(int i=0;i<5;i++){

                            String s=en.nextElement().toString();

                            key5=key5+s+"-"+gn.hm.get(s)+";";

                        }

                        writer.println(key5);*/

                   

                    }

            }            

        }

        catch (IOException e) {

            e.printStackTrace();

        }

        finally {

            try {

                if (out != null) {

                    out.close();

                }

                if (in != null) {

                    in.close();

                    clientSocket.close();

                }

            }

            catch (IOException e) {

                e.printStackTrace();

            }

        }

       

    }

}

private static class ClientHandlerUDP implements Runnable {

    private final DatagramSocket clientSocket;

    String key;

    String cmd;

    String val;

    String sendport;

    String recvport;

    String addr;

   

   



    // Constructor

    public ClientHandlerUDP(DatagramSocket socket,String key,String val,String cmd,String sport,String rport,String addr)

    {

        this.clientSocket = socket;

        this.key=key;

        this.val=val;

        this.cmd=cmd;

        this.sendport=sport;

        this.recvport=rport;

        this.addr=addr;

       

    }



    /**

     * @param port

     */

    public void run()

    {

       

           

        try {

               

           

            if(this.cmd.equals("put")){

                if(hm.containsKey(this.key)){

                synchronized(hm){

            System.out.println(this.key+":"+this.val+":"+this.cmd);

           

            hm.put(this.key,this.val);

            System.out.println(hm);

            DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = ("key inserted !").getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

                }

            }

            else{

                System.out.println("-----new key-----");

                System.out.println(key+":"+val+":"+cmd);

                DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = ("key inserted !").getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

           

            hm.put(key,val);

            System.out.println(hm);

            }

            }

            else if(cmd.equals("get")){

                if((hm.containsKey(key))){

            String s=hm.get(key);

            DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = s.getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

                }else{

                    DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = ("No such key !").getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

                }

            }

            else if(cmd.equals("del")){

                if((hm.containsKey(key))){

                    synchronized(hm){

               

            String s=hm.remove(key);

            DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = ("Key Removed !").getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

                    }

                }

                else{

                    DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = ("No such key !").getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

                }

            }else if((cmd.equals("store"))){

                System.out.println((hm.entrySet()).toString().getBytes("UTF-8").length);

               

                    if((hm.entrySet()).toString().getBytes("UTF-8").length < 65000){

                        DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                buf = (hm.entrySet()).toString().getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

                    }else{

                       

                        /*Enumeration en=hm.keys();

                        String key5="TRIMMED:";

                        for(int i=0;i<5;i++){

                            String s=en.nextElement().toString();

                            key5=key5+s+"-"+hm.get(s)+";";

                        }*/

                        DatagramSocket ds = new DatagramSocket();

                byte buf[] = null;

                //buf = key5.getBytes();

                InetAddress ip = InetAddress.getByName(this.addr);

                DatagramPacket DpSend =new DatagramPacket(buf, buf.length, ip,Integer.parseInt(this.recvport));

                ds.send(DpSend);

                ds.close();

                       

                   

                    }

            }          

        }

        catch (Exception e) {

            System.out.println("Server Occupied !");

           

        }

       

        }

       

    }

}
