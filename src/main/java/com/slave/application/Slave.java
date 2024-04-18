package com.slave.application;

import com.shared.SlaveInfo;
import com.shared.SlaveService;
import com.slave.application.gui.TerminalGUI;
import com.slave.application.services.SlaveServiceImpl;
import com.slave.application.services.SlaveServiceManager;
import ec.app.majority.func.E;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Slave extends SlaveServiceImpl {
    //Internal Data
    private SlaveServiceManager slaveServiceManager;
    private TerminalGUI terminalGUI;

    //Constructor
    public Slave() throws RemoteException {
        slaveServiceManager = new SlaveServiceManager();
        terminalGUI = new TerminalGUI(slaveServiceManager);
    }

    public static void main(String[] args) throws RemoteException {
        Slave slave = new Slave();

        // There are two ways of starting the Slave
        // Direct -> Provide 4 arguments <local-address> <local-port> <coordinator-address> <coordinator-port>
        // Assisted -> Provide no arguments
        if (args.length == 4) {
            String addressVerifyRegex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
            try {
                if (!args[0].matches(addressVerifyRegex)) {
                    throw new Exception("Invalid IP Address");
                }
                slave.startService(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
            } catch (NumberFormatException e) {
                System.err.println("Wrong argument format");
                System.err.println("Port values probably in the wrong format (must be numbers)");
                System.exit(1);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        else {
            slave.startGUI();
        }


        /*
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

         */
    }

    //Get Methods


    //Set Methods


    //Methods


    //Overrides


    //Internal Functions
    private void startService(String localAddress, int localPort, String coordinatorAddress, int coordinatorPort) {
        slaveServiceManager.startService(localAddress, localPort, coordinatorAddress, coordinatorPort);
    }
    private void startGUI() {
        // Show Initial GUI Menu
        terminalGUI.showStartMenu();
    }
}