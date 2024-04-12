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
    private String distribution;
    private String problemCode;

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
    public boolean setupProblemEnvironment(ArrayList<JobFile> jobFiles, String problemCode, String distribution) {
        this.problemCode = problemCode;
        this.distribution = distribution;

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

        if (distribution.equals("DIST_EVAL")) {
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
        }
        else if (distribution.equals("ISLANDS")) {

        }

        return true;
    }

    @Override
    public boolean startInference(String problemCode) {
        System.out.println("Start Inference");
        System.out.println("PROBLEM CODE: " + problemCode);
        EvolutionEngine evolutionEngine = new EvolutionEngine(this.problemCode, distribution, problemCode);
        evolutionEngine.start();
        return true;
    }

    @Override
    public boolean stopInference() {
        System.out.println("STOP INFERENCE");
        return true;
    }

    //Internal Functions

}