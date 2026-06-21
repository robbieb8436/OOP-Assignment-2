package org.uob.a2.engine;

import org.uob.a2.*;
import org.uob.a2.model.*;

public abstract class Consumer extends Entity implements Tickable {

    protected ResourceType consumedResource;
    protected int amount;
	protected int level;


     public Consumer(String name, ResourceType resource, int amount) {
        super(name);
        this.consumedResource = resource;
        this.amount = amount;
		this.level = 0;
    }

    public ResourceType getProduct() {
        return consumedResource;
    }
    
    public int getAmount() {
        return amount;
    }

	public int getLevel() {
        return level;
    }

    public abstract void consume(Context ctx);
}