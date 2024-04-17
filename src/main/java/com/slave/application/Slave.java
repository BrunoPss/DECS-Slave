package com.slave.application;

import com.shared.SlaveInfo;
import com.shared.SlaveService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Slave extends SlaveServiceImpl {
    //Internal Data

    //Constructor
    public Slave() throws RemoteException {

    }

    public static void main(String[] args) throws RemoteException {
        String address = args[0];
        int port = Integer.parseInt(args[1]);

        String serverIP = "192.168.56.1";
        int serverPort = 46000;

        SlaveInfo slaveInfo = new SlaveInfo("DECS-SLAVE", address, port);

        try {
            System.setProperty("java.rmi.server.hostname", address);
            LocateRegistry.createRegistry(port);
            System.err.println("[STATUS] -> RMI successfully created!");
        } catch (RemoteException e) {
            System.out.println("[STATUS] -> RMI already started!");
        }

        try {
            SlaveService slaveService = new SlaveServiceImpl();
            Naming.rebind("//" + slaveInfo.getAddress() + ":" + slaveInfo.getPort() + "/" + slaveInfo.getId(), slaveService);
            System.err.println("[STATUS] -> RMI Bind Success");
        } catch (RemoteException e) {
            System.out.println("Remote Exception");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL Exception");
            e.printStackTrace();
        }

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ServerAddress = InetAddress.getByName(serverIP);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(out);
            obj.writeObject(slaveInfo);
            byte[] buffer = out.toByteArray();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ServerAddress, serverPort);
            socket.send(packet);
            System.err.println("[STATUS] -> DGRAM Sent");

            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Socket Exception");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Unknown Host Exception");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception");
        }

        //File paramsFile = new File("src/s.params");

        //try {
        //    ec.eval.Slave.main(new String[]{"-file", paramsFile.getCanonicalPath()});
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        /*
        try {
            System.setProperty("java.rmi.server.hostname","192.168.1.114");
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("[STATUS] -> RMI successfully created!");
        } catch (RemoteException e) {
            System.out.println("[STATUS] -> RMI already started!");
        }

        try {
            com.shared.SlaveService slaveService = new com.slave.application.SlaveServiceImpl();
            Naming.rebind("com.slave.application.Slave", slaveService);
            System.err.println("com.slave.application.Slave Ready");
        } catch (RemoteException e) {
            System.out.println("Remote Exception");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL Exception");
            e.printStackTrace();
        }
         */
    }

    //Get Methods


    //Set Methods


    //Methods


    //Overrides


    //Internal Functions

}