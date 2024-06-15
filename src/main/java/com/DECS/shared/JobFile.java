package com.decs.shared;

import java.io.Serializable;

public class JobFile implements Serializable {
    //Internal Data
    private static final long serialVersionUID = 1L;
    private String name;
    private byte[] content;

    //Constructor
    public JobFile(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    //Get Methods
    public String getName() { return this.name; }
    public byte[] getFileBytes() { return this.content; }

    //Set Methods


    //Methods


    //Overrides


    //Internal Functions

}