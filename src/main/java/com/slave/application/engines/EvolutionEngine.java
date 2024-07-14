package com.slave.application.engines;

import com.slave.application.utils.constants.FilePathConstants;
import ec.EvolutionState;
import ec.Evolve;
import ec.util.Output;
import ec.util.ParameterDatabase;

import java.io.File;

import static ec.Evolve.cleanup;

/**
 * <b>Evolution Engine Class</b>
 * <p>
 *     This class implements the evolution engine for DECS-Slave instances.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class EvolutionEngine extends Thread {
    //Internal Data
    private String problemCode;
    private String islandCode;
    private String distribution;
    private String slaveAddress;


    /**
     * Class Constructor
     * @param problemCode Problem short code
     * @param distribution Problem distribution method
     * @param islandCode Island short code
     * @param slaveAddress DECS-Slave instance network address (IP)
     */
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
    /**
     * Starts the inference of a distributed evaluation problem
     */
    private void distEvalSlaveInference() {
        try {
            File paramsFile = new File(FilePathConstants.PROBLEM_PARAMS_FOLDER + this.problemCode + "/" + this.problemCode + "Slave.params");
            System.out.println(paramsFile);

            System.out.println("Inference Started");
            ec.eval.Slave.main(new String[]{"-file", paramsFile.getCanonicalPath()});
            System.out.println("Inference Ended");
        } catch (NullPointerException e) {
            System.err.println("Null pointer exception at disEvalSlaveInference");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at disEvalSlaveInference");
            e.printStackTrace();
        }
    }

    /**
     * Starts the inference of a island model distribution problem
     */
    private void islandsSlaveInference() {
        try {
            File paramsFile = new File(FilePathConstants.PROBLEM_PARAMS_FOLDER + this.problemCode + "/" + this.islandCode + ".params");
            System.out.println(paramsFile);

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
        } catch (NullPointerException e) {
            System.err.println("Null pointer exception at islandsSlaveInference");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}