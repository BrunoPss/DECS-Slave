package com.slave.application.services;

import com.shared.SlaveInfo;
import com.shared.SlaveService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class SlaveServiceManager {
    //Internal Data
    private SlaveInfo slaveInfo;
    private String coordinatorAddress;
    private int coordinatorPort;
    private String slaveAddress;

    //Constructor
    public SlaveServiceManager() {

    }

    //Get Methods


    //Set Methods


    //Methods
    public void startService(String slaveAddress, int slavePort, String slaveID, String coordinatorAddress, int coordinatorPort) {
        this.coordinatorAddress = coordinatorAddress;
        this.coordinatorPort = coordinatorPort;
        this.slaveAddress = slaveAddress;

        // Create Slave Info Object
        slaveInfo = new SlaveInfo(slaveID, slaveAddress, slavePort);

        // Create RMI Registry
        createRegistry();
        // Bind Remote Interface
        bindRemoteInterface();
        // Connect to the Coordinator
        connect2Coordinator();
    }

    //Overrides


    //Internal Functions
    private void createRegistry() {
        try {
            System.out.println(slaveInfo.getAddress() + " " + slaveInfo.getPort());
            System.setProperty("java.rmi.server.hostname", slaveInfo.getAddress());
            LocateRegistry.createRegistry(slaveInfo.getPort());
            System.err.println("[STATUS] -> RMI successfully created!");
        } catch (RemoteException e) {
            System.out.println("[STATUS] -> RMI already started!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at createRegistry");
            e.printStackTrace();
        }
    }

    private void bindRemoteInterface() {
        try {
            SlaveService slaveService = new SlaveServiceImpl(slaveAddress);
            Naming.rebind("//" + slaveInfo.getAddress() + ":" + slaveInfo.getPort() + "/" + slaveInfo.getId(), slaveService);
            System.err.println("[STATUS] -> RMI Bind Success");
        } catch (RemoteException e) {
            System.out.println("[STATUS] -> Remote Exception");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("[STATUS] -> Malformed URL Exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at bindRemoteInterface");
            e.printStackTrace();
        }
    }

    private void connect2Coordinator() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ServerAddress = InetAddress.getByName(coordinatorAddress);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(out);
            obj.writeObject(slaveInfo);
            byte[] buffer = out.toByteArray();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ServerAddress, coordinatorPort);
            socket.send(packet);
            System.err.println("[STATUS] -> DGRAM Sent");

            socket.close();
        } catch (SocketException e) {
            System.out.println("Socket Exception");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host Exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at connect2Coordinator");
            e.printStackTrace();
        }
    }
}