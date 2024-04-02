package com.slave.application;

import ec.util.ParameterDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
            File simpleParamsFile = new File(FilePathConstants.PROBLEM_PARAMS_FOLDER + this.problemCode + "/" + this.problemCode.substring(0, this.problemCode.length()-4) + ".params");
            Charset charset = StandardCharsets.UTF_8;
            String content = Files.readString(simpleParamsFile.toPath(), charset);
            String pattern = "(stat\\.file\\s+)[\\.\\/]+\\w+\\/(\\w+\\.stats)";
            String replacement = "$1out.stats";
            String output = content.replaceFirst(pattern, replacement);

            FileWriter fileWriter = new FileWriter(simpleParamsFile);
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ec.eval.Slave.main(new String[]{"-file", paramsFile.getCanonicalPath()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}