package com.slave.application.services;

import com.slave.shared.JobFile;
import com.slave.shared.SlaveService;
import com.slave.shared.SystemInformation;
import com.slave.application.engines.EvolutionEngine;
import com.slave.application.utils.constants.FilePathConstants;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SlaveServiceImpl extends UnicastRemoteObject implements SlaveService {
    //Internal Data
    private String distribution;
    private String problemCode;
    private OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
    private String slaveAddress;

    //Constructor
    public SlaveServiceImpl(String slaveAddress) throws RemoteException {
        this.slaveAddress = slaveAddress;
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
    public SystemInformation getSystemInformation() {
        return new SystemInformation(
                System.getProperty("user.name"),
                operatingSystemMXBean.getName(),
                operatingSystemMXBean.getVersion(),
                operatingSystemMXBean.getArch(),
                operatingSystemMXBean.getAvailableProcessors()
        );
    }

    @Override
    public boolean setupProblemEnvironment(ArrayList<JobFile> jobFiles, String problemCode, String distribution) {
        try {
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
            } else if (distribution.equals("ISLANDS")) {

            }

            return true;
        } catch (SecurityException e) {
            System.err.println("Security Exception at setupProblemEnvironment");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Null pointer exception at setupProblemEnvironment");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at setupProblemEnvironment");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean startInference(String problemCode) {
        try {
            System.out.println("Start Inference");
            System.out.println("PROBLEM CODE: " + problemCode);
            EvolutionEngine evolutionEngine = new EvolutionEngine(this.problemCode, distribution, problemCode, slaveAddress);
            evolutionEngine.start();
            return true;
        } catch (IllegalThreadStateException e) {
            System.err.println("Illegal thread state exception at startInference");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at startInference");
        }
        return false;
    }

    @Override
    public boolean stopInference() {
        System.out.println("STOP INFERENCE");
        return true;
    }

    //Internal Functions

}