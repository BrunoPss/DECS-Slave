package com.slave.application.gui;

public class TextContent {
    public static final String TERMINAL_INPUT_SYMBOL = "> ";
    public static String SLAVE_IP_ADDRESS_TEXT(String content) {
        return String.format(
            """
            DECS-Slave
            Please select the %s IP address for communication
            %s""", content, TERMINAL_INPUT_SYMBOL
        );
    }
    public static String SLAVE_PORT_TEXT(String content) {
        return String.format(
            """
            Please insert the %s port for communication
            %s""", content, TERMINAL_INPUT_SYMBOL
        );
    }
    public static String SLAVE_ADDRESS_CONFIRMATION(String address, String ip, String content) {
        return String.format(
                """
                Please confirm the following information with (Y/N)!
                %s local IP Address: %s
                %s local Port      : %s
                %s""", content, address, content, ip, TERMINAL_INPUT_SYMBOL
        );
    }
    public static final String MAIN_MENU_TEXT =
            String.format(
            """
            DECS-Slave
            Start Menu
            1) Connect to the Coordinator
            2) Help
            3) About
            Please select one option to continue...
            %s""", TERMINAL_INPUT_SYMBOL
            );
}