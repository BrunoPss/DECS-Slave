package com.slave.application.engines;

import com.slave.application.utils.constants.FilePathConstants;
import ec.EvolutionState;
import ec.Evolve;
import ec.util.Output;
import ec.util.ParameterDatabase;

import java.io.File;

import static ec.Evolve.cleanup;

public class EvolutionEngine extends Thread {
    //Internal Data
    private String problemCode;
    private String islandCode;
    private String distribution;
    private String slaveAddress;


    //Constructor
    public EvolutionEngine(String problemCode, String distribution, String islandCode, String slaveAddress) {
        this.problemCode = problemCode;
        this.distribution = distribution;
        this.islandCode = islandCode;
        this.slaveAddress = slaveAddress;
    }

    //Get Methods


    //Set Methods


    //Methods
    @Override
    public void run() {
        if (distribution.equals("DIST_EVAL")) {
            distEvalSlaveInference();
        }
        else if (distribution.equals("ISLANDS")) {
            islandsSlaveInference();
        }
    }

    //Overrides


    //Internal Functions
    private void distEvalSlaveInference() {
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

    private void islandsSlaveInference() {
        File paramsFile = new File(FilePathConstants.PROBLEM_PARAMS_FOLDER + this.problemCode + "/" + this.islandCode + ".params");
        System.out.println(paramsFile);

        try {
            System.out.println("Inference Started");
            // Create Parameter Database
            ParameterDatabase paramDatabase = new ParameterDatabase(paramsFile,
                    new String[]{"-file", paramsFile.getCanonicalPath()});

            // Manually setting the local IP address
            // This was intentionally changed in ECJ source code
            // The original implementation is problematic...
            Evolve.myAddress = slaveAddress;

            // Build Output
            Output out = Evolve.buildOutput();

            EvolutionState evaluatedState = Evolve.initialize(paramDatabase, 0, out);

            int result = EvolutionState.R_NOTDONE;
            evaluatedState.run(EvolutionState.C_STARTED_FRESH);
            evaluatedState.finish(result);

            cleanup(evaluatedState);
            System.out.println("Inference Ended");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}