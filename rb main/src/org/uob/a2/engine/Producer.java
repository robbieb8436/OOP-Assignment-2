package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public abstract class Producer extends Entity implements Tickable {

    protected ResourceType product;
    protected int amount;

    public Producer(String name, ResourceType product, int amount) {
        super(name);
        this.product = product;
        this.amount = amount;
    }

    public ResourceType getProduct() {
        return product;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public abstract void produce(Context ctx);
    public abstract String toCSV();
}