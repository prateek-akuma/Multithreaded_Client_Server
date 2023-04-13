/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package genericnode;

import java.rmi.Remote;
import java.rmi.*;

/**
 *
 * @author sweety
 */
public interface RMI extends Remote {
    public void put(String key, String val) throws RemoteException;
    public String get(String key) throws RemoteException;
    public String store() throws RemoteException;
    public void exit() throws RemoteException;
}

