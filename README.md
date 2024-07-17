# DECS-Slave

DECS-Slave is a component of the Distributed Evolutionary Computing System developed by Bruno Guiomar for his degree thesis.\
This system follows a Coordinator - Slave architecture where there is one Coordinator
in the network and multiple Slaves that process the tasks delegated by the coordinator.\
This repository only contains the source code for the system Slave.\
The main Coordinator source code can be found in a separate repository (DECS).

## Running the application

In order to run the Slave process, there must be a running Coordinator in the local network.
The Slave will then establish a connection with the respective Coordinator and process all
delegated tasks.\
A simple terminal-style configurations menu is available for communications settings.\
There are two ways of running the Slave process.
- Direct Way: you can bypass the terminal-style GUI by providing the necessary arguments.
  - java Slave <local-address> <local-port> <coordinator-address> <coordinator-port>
  - Local address and port correspond to the machine running the Slave process
  - Coordinator address and port correspond to the machine running the Coordinator process
- Assisted Way: you can start the Slave process using the assistance terminal-style graphical user interface.
This interface will guide you in the process of argument input and easy interaction with this vital component
of the DECS system. In order to use the assistant, you just need to run the following command:
  - java Slave <local-address>

After a successful connection with the Coordinator, this Slave can be accessed via the
web interface.

## Other Information
### Author
**Name:** Bruno Guiomar\
**Email:** [Bruno.guiomar33@gmail.com](mailto:bruno.guiomar33@gmail.com?subject=DECS%20Inquire)\
**GitHub:** [BrunoPss](https://github.com/BrunoPss)

### Repository
[**DECS**](https://github.com/BrunoPss/DECS)\
[**DECS-Slave**](https://github.com/BrunoPss/DECS-Slave)

### Licence
This software is licensed under the GPL-2.0 License.
