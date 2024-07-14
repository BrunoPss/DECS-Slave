package com.slave.application.gui;

/**
 * <b>Text Content Class</b>
 * <p>
 *     This class contains all GUI textual content.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class TextContent {
    /**
     * Class Private Constructor
     * <p>This class cannot be instantiated.</p>
     */
    private TextContent() {}
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
            2) Help
            3) About
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
    public static final String HELP_PAGE_TEXT =
            """
            Each DECS-Slave instance should be executed only after a DECS instance is running to ensure a successful
            registration process.
            If DECS does not automatically recognize a new instance of DECS-Slave, restart the process.
            Make sure that all participant computers are connected in a local network and can communicate.
            If the registration process keeps failing, double check your network configurations for blocked communications.
            """;
    public static final String ABOUT_PAGE_TEXT =
            """
            The Distributed Evolutionary Computing System (DECS) is a solid platform developed by Bruno Guiomar,
            that aims to achieve easy user interaction while building a solid distributed processing transparency
            that simplifies the backend process of task distribution. By using ECJ as its evolutionary engine,
            DECS supports multiple types of evolutionary algorithms, distribution models, and configurations.
            While aiming for adaptability, the system can adapt itself to handle multiple processing nodes in
            the form of DECS-Slave instances and effectively distribute tasks in order to reach high efficiency.
            """;
}