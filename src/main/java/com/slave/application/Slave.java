package com.slave.application;

import com.slave.application.gui.TerminalGUI;
import com.slave.application.gui.TextContent;
import com.slave.application.services.SlaveServiceImpl;
import com.slave.application.services.SlaveServiceManager;

import java.rmi.RemoteException;

public class Slave extends SlaveServiceImpl {
    //Internal Data
    private SlaveServiceManager slaveServiceManager;
    private TerminalGUI terminalGUI;

    //Constructor
    public Slave(String slaveAddress) throws RemoteException {
        super(slaveAddress);
        slaveServiceManager = new SlaveServiceManager();
        terminalGUI = new TerminalGUI(slaveServiceManager);
    }

    public static void main(String[] args) throws RemoteException {
        if (args.length > 0) {
            Slave slave = new Slave(args[0]);
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
            } else {
                slave.startGUI();
            }
        } else {
            System.err.println("Argument missing!");
            System.err.println("java Slave <local-address>");
            System.exit(1);
        }
    }

    //Get Methods


    //Set Methods


    //Methods


    //Overrides


    //Internal Functions
    private void startService(String localAddress, int localPort, String coordinatorAddress, int coordinatorPort) {
        slaveServiceManager.startService(localAddress, localPort, TextContent.SLAVE_DEFAULT_ID, coordinatorAddress, coordinatorPort);
    }
    private void startGUI() {
        // Show Initial GUI Menu
        terminalGUI.showStartMenu();
    }
}