package com.slave.application.gui;

import com.slave.application.services.SlaveServiceManager;

import javax.swing.*;
import java.util.Scanner;

public class TerminalGUI {
    //Internal Data
    private Scanner scanner;
    private SlaveServiceManager slaveServiceManager;
    private String localAddress;
    private int localPort;
    private String slaveID;
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
        try {
            String[] values;
            // Local IP + Port address input
            values = showAddressInput("slave");
            localAddress = values[0];
            localPort = Integer.parseInt(values[1]);

            // Coordinator IP + Port address input
            values = showAddressInput("coordinator");
            coordinatorAddress = values[0];
            coordinatorPort = Integer.parseInt(values[1]);

            // Slave ID
            showIDInput();

            // Main Menu
            int option;
            do {
                option = showMenu(TextContent.MAIN_MENU_TEXT, 3);
                switch (option) {
                    case 1 -> connectCoordinator();
                    case 2 -> showHelpMenu();
                    case 3 -> showAboutMenu();
                }
            } while (option != 1);
        } catch (NumberFormatException e) {
            System.err.println("Number format exception at showStartMenu");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at showStartMenu");
            e.printStackTrace();
        }
    }

    //Overrides


    //Internal Functions
    private void connectCoordinator() {
        slaveServiceManager.startService(localAddress, localPort, slaveID, coordinatorAddress, coordinatorPort);
    }
    private void showHelpMenu() {
        System.out.println(TextContent.HELP_PAGE_TEXT);
    }
    private void showAboutMenu() {
        System.out.println(TextContent.ABOUT_PAGE_TEXT);
    }

    private String[] showAddressInput(String agent) {
        try {
            boolean repeat;
            String address;
            String port = "0";
            do {
                System.out.print(TextContent.IP_ADDRESS_TEXT(agent));
                address = scanner.nextLine();
                do {
                    System.out.print(TextContent.PORT_TEXT(agent));
                    try {
                        port = scanner.nextLine();
                        repeat = false;
                    } catch (NumberFormatException e) {
                        System.err.println("Port value must be a number");
                        repeat = true;
                    }
                } while (repeat);
                System.out.print(TextContent.ADDRESS_CONFIRMATION(address, port, agent));
                String opt = scanner.nextLine();
                if (opt.equalsIgnoreCase("y")) {
                    repeat = false;
                } else if (opt.equalsIgnoreCase("n")) {
                    repeat = true;
                } else {
                    System.err.println("Wrong input value!");
                    repeat = true;
                }
            } while (repeat);

            return new String[]{address, port};
        } catch (Exception e) {
            System.err.println("Exception at showAddressInput");
            e.printStackTrace();
            return new String[]{"127.0.0.1", "9999"};
        }
    }
    private void showIDInput() {
        try {
            System.out.print(TextContent.SLAVE_ID_SET_TEXT);
            String idValue = scanner.nextLine();
            if (idValue.isBlank()) {
                slaveID = TextContent.SLAVE_DEFAULT_ID;
            } else {
                slaveID = idValue;
            }
        } catch (Exception e) {
            System.err.println("Exception at showIDInput");
            e.printStackTrace();
        }
    }
    private int showMenu(String content, int limit) {
        try {
            boolean repeat;
            int option = 0;
            do {
                do {
                    System.out.print(content);
                    try {
                        option = Integer.parseInt(scanner.nextLine());
                        repeat = false;
                    } catch (NumberFormatException e) {
                        System.err.println("Option must be a number!");
                        repeat = true;
                    }
                } while (repeat);
                if (option <= 0 || option >= limit + 1) {
                    System.err.println("Option not found!");
                    repeat = true;
                }
            } while (repeat);

            return option;
        } catch (NumberFormatException e) {
            System.err.println("Number format exception at showMenu");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at showMenu");
            e.printStackTrace();
        }
        return 0;
    }
}