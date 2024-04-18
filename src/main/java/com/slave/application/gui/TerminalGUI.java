package com.slave.application.gui;

import com.slave.application.Slave;
import com.slave.application.services.SlaveServiceManager;

import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

public class TerminalGUI {
    //Internal Data
    private Scanner scanner;
    private SlaveServiceManager slaveServiceManager;
    private String localAddress;
    private int localPort;
    private String coordinatorAddress;
    private int coordinatorPort;

    //Constructor
    public TerminalGUI(SlaveServiceManager slaveServiceManager) {
        this.scanner = new Scanner(System.in);
        this.slaveServiceManager = slaveServiceManager;
    }

    //Get Methods


    //Set Methods


    //Methods
    public void showStartMenu() {
        // Local IP + Port address input
        boolean repeat;
        do {
            System.out.print(TextContent.SLAVE_IP_ADDRESS_TEXT("local"));
            localAddress = scanner.nextLine();
            localPort = 0;
            do {
                System.out.print(TextContent.SLAVE_PORT_TEXT("local"));
                try {
                    localPort = Integer.parseInt(scanner.nextLine());
                    repeat = false;
                } catch (NumberFormatException e) {
                    System.err.println("Port value must be a number");
                    repeat = true;
                }
            } while (repeat);
            System.out.print(TextContent.SLAVE_ADDRESS_CONFIRMATION(localAddress, String.valueOf(localPort), "Slave"));
            String opt = scanner.nextLine();
            if (opt.equalsIgnoreCase("y")) {
                repeat = false;
            }
            else if (opt.equalsIgnoreCase("n")) {
                repeat = true;
            }
            else {
                System.err.println("Wrong input value!");
                repeat = true;
            }
        } while (repeat);

        // Coordinator IP + Port address input
        do {
            System.out.print(TextContent.SLAVE_IP_ADDRESS_TEXT("coordinator"));
            coordinatorAddress = scanner.nextLine();
            coordinatorPort = 0;
            do {
                System.out.print(TextContent.SLAVE_PORT_TEXT("coordinator"));
                try {
                    localPort = Integer.parseInt(scanner.nextLine());
                    repeat = false;
                } catch (NumberFormatException e) {
                    System.err.println("Port value must be a number");
                    repeat = true;
                }
            } while (repeat);
            System.out.print(TextContent.SLAVE_ADDRESS_CONFIRMATION(coordinatorAddress, String.valueOf(coordinatorPort), "Coordinator"));
            String opt = scanner.nextLine();
            if (opt.equalsIgnoreCase("y")) {
                repeat = false;
            }
            else if (opt.equalsIgnoreCase("n")) {
                repeat = true;
            }
            else {
                System.err.println("Wrong input value!");
                repeat = true;
            }
        } while (repeat);

        // Main Menu
        int option = 0;
        do {
            do {
                System.out.print(TextContent.MAIN_MENU_TEXT);
                try {
                    option = Integer.parseInt(scanner.nextLine());
                    repeat = false;
                } catch (NumberFormatException e) {
                    System.err.println("Option must be a number!");
                    repeat = true;
                }
            } while (repeat);
            if (option <= 0 || option >= 4) {
                System.err.println("Option not found!");
                repeat = true;
            }
        } while (repeat);

        switch (option) {
            case 1 -> connectCoordinator();
            case 2 -> showHelpMenu();
            case 3 -> showAboutMenu();
        }
    }

    //Overrides


    //Internal Functions
    private void connectCoordinator() {
        slaveServiceManager.startService(localAddress, localPort, coordinatorAddress, coordinatorPort);
    }
    private void showHelpMenu() {

    }
    private void showAboutMenu() {

    }
}