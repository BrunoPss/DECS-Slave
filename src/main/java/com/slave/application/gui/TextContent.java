package com.slave.application.gui;

public class TextContent {
    public static final String TERMINAL_INPUT_SYMBOL = "> ";
    public static final String SLAVE_DEFAULT_ID = "DECS_Slave";
    public static String IP_ADDRESS_TEXT(String content) {
        return String.format(
            """
            DECS-Slave
            Please select the %s IP address for communication
            %s""", content, TERMINAL_INPUT_SYMBOL
        );
    }
    public static String PORT_TEXT(String content) {
        return String.format(
            """
            Please insert the %s port for communication
            %s""", content, TERMINAL_INPUT_SYMBOL
        );
    }
    public static String ADDRESS_CONFIRMATION(String address, String ip, String content) {
        content = content.substring(0, 1).toUpperCase() + content.substring(1);
        return String.format(
                """
                Please confirm the following information with (Y/N)!
                %s local IP Address: %s
                %s local Port      : %s
                %s""", content, address, content, ip, TERMINAL_INPUT_SYMBOL
        );
    }
    public static final String SLAVE_ID_SET_TEXT =
            String.format(
            """
            Slave ID Configuration
            Default ID: %s
            
            Please insert the ID value for the Slave or press ENTER to continue with the default value.
            %s""", SLAVE_DEFAULT_ID, TERMINAL_INPUT_SYMBOL
            );
    public static final String MAIN_MENU_TEXT =
            String.format(
            """
            DECS-Slave
            Start Menu
            1) Connect to the Coordinator
            2) Change Configurations
            3) Help
            4) About
            Please select one option to continue...
            %s""", TERMINAL_INPUT_SYMBOL
            );
    public static final String CHANGE_CONFIG_MENU_TEXT =
            String.format(
                    """
                    DECS-Slave
                    Change Configurations
                    1) Change Local Machine Address
                    2) Change Coordinator Address
                    3) Change Slave ID
                    4) Exit
                    Please select one option to continue...
                    %s""", TERMINAL_INPUT_SYMBOL
            );
}