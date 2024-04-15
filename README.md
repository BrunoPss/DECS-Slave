# DECS-Slave

DECS-Slave is a component of the Distributed Evolutionary Computing System developed by Bruno Guiomar
for his bachelor's thesis.\
This system follows a Coordinator - Slave architecture where there is one Coordinator
in the network and multiple Slaves that process the tasks delegated by the coordinator.\
This repository only contains the source code for the system Slave.\
The main Coordinator source code can be found in a separate repository (DECS).

## Running the application

In order to run the Slave process, there must be a running Coordinator in the local network.
The Slave will then establish a connection with the respective Coordinator and process all
delegated tasks.\
A simple terminal-style configurations menu is available for communications settings.\
After a successful connection with the Coordinator, this Slave can be accessed via the
web interface.