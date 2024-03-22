package com.slave.application;

import com.shared.JobFile;
import com.shared.SlaveService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class SlaveServiceImpl extends UnicastRemoteObject implements SlaveService {
    //Internal Data


    //Constructor
    public SlaveServiceImpl() throws RemoteException {

    }

    //Get Methods


    //Set Methods


    //Methods


    //Overrides
    @Override
    public boolean checkStatus() {
        return true;
    }

    @Override
    public boolean setupProblemEnvironment(ArrayList<JobFile> jobFiles) {
        for (JobFile f : jobFiles) {
            System.out.println(f.getName());
        }

        // Create .conf file
        //try {
        //    FileOutputStream fileOut = new FileOutputStream(jobFiles.get("CONF").getName());
        //    fileOut.write(jobFiles.get("CONF").getFileBytes());
        //    fileOut.close();
        //    System.out.println(".conf file successfully written");
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    System.err.println("IO Exception while writing .conf file");
        //    return false;
        //}

        // Create .params files


        return true;
    }

    //Internal Functions

}