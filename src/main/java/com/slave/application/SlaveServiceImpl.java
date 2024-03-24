package com.slave.application;

import com.shared.JobFile;
import com.shared.SlaveService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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
    public boolean setupProblemEnvironment(ArrayList<JobFile> jobFiles, String problemCode) {
        // Create Problem Folder
        String problemDir = FilePathConstants.PROBLEM_PARAMS_FOLDER + problemCode;
        File problemFolder = new File(problemDir);
        problemFolder.mkdir();

        // Create Problem Files
        for (JobFile jobFile : jobFiles) {
            try {
                FileOutputStream fileOut = new FileOutputStream(problemDir + "/" + jobFile.getName());
                fileOut.write(jobFile.getFileBytes());
                fileOut.close();
                System.out.println(jobFile.getName() + " file successfully created");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO Exception while writing " + jobFile.getName() + " file");
                return false;
            }
        }

        // Create Slave params Files
        String slaveParamsText = "parent.0 = slave.params" + System.lineSeparator() +
                                 "parent.1 = " + problemCode + ".params";
        File slaveParamsFile = new File(problemDir + "/" + problemCode + "Slave.params");
        FileWriter fWriter;
        try {
            fWriter = new FileWriter(slaveParamsFile);
            fWriter.write(slaveParamsText);
            fWriter.close();
            System.out.println("Successfully wrote the slave file");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception");
        }

        return true;
    }

    @Override
    public boolean startInference(String problemCode) {
        File paramsFile = new File(FilePathConstants.PROBLEM_PARAMS_FOLDER + problemCode + "/" + problemCode + "Slave.params");
        System.out.println(paramsFile);
        try {
            ec.eval.Slave.main(new String[]{"-file", paramsFile.getCanonicalPath()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //Internal Functions

}