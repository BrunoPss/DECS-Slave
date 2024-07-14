package com.slave.application.services;

import com.decs.shared.SlaveInfo;
import com.decs.shared.SlaveService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * <b>Slave Service Manager Class</b>
 * <p>
 *     This class manages the service provided by the slave instance.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class SlaveServiceManager {
    //Internal Data
    private SlaveInfo slaveInfo;
    private String coordinatorAddress;
    private int coordinatorPort;
    private String slaveAddress;

    /**
     * Class Default Constructor
     */
    public SlaveServiceManager() {}

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Starts the slave service
     * @param slaveAddress Slave network address (IP)
     * @param slavePort Slave network port
     * @param slaveID Slave short identification
     * @param coordinatorAddress Coordinator network address (IP)
     * @param coordinatorPort Coordinator network port
     */
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

    /**
     * Creates a remote method invocation registry (RMI registry)
     */
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

    /**
     * Binds the remote interface with the RMI registry
     */
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

    /**
     * Initiates the registration process in the coordinator service
     */
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