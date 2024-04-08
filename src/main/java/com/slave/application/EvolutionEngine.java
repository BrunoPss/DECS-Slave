package com.slave.application;

import java.io.File;

public class EvolutionEngine extends Thread {
    //Internal Data
    private String problemCode;


    //Constructor
    public EvolutionEngine(String problemCode) {
        this.problemCode = problemCode;
    }

    //Get Methods


    //Set Methods


    //Methods
    @Override
    public void run() {
        slaveInference();
    }

    //Overrides


    //Internal Functions
    private void slaveInference() {
        File paramsFile = new File(FilePathConstants.PROBLEM_PARAMS_FOLDER + this.problemCode + "/" + this.problemCode + "Slave.params");
        System.out.println(paramsFile);

        try {
            System.out.println("Inference Started");
            ec.eval.Slave.main(new String[]{"-file", paramsFile.getCanonicalPath()});
            System.out.println("Inference Ended");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}