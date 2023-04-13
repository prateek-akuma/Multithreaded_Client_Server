import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public interface RMI extends Remote {
    public void put(String key, String val) throws RemoteException;
    public String get(String key) throws RemoteException;
    public String store() throws RemoteException;
    public void exit() throws RemoteException;
}