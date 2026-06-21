package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public abstract class Converter extends Entity implements Tickable {
    protected ResourceType input;
    protected ResourceType output;
    protected int inputAmount;
    protected int outputAmount;

     public Converter(String name, ResourceType input, int inputAmount, ResourceType output, int outputAmount) {
        super(name);
        this.input = input;
        this.output = output;
        this.inputAmount = inputAmount;
        this.outputAmount = outputAmount;
    }

    public ResourceType getInput() {
        return input;  
    }
    
    public ResourceType getOutput() {
        return output;   
    }
    
    public int getInputAmount() {
        return inputAmount;
    }
    
    public int getOutputAmount() {
        return outputAmount;
    }
    
    public abstract void convert(Context ctx);
}