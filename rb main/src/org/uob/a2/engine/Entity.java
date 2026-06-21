package org.uob.a2.engine;

import org.uob.a2.model.*;

import java.util.EnumMap;
import java.util.Map;

public abstract class Entity {
    protected String name;
    protected Map<ResourceType, Integer> costs = new EnumMap<>(ResourceType.class);
    protected int costAmount;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCost(ResourceType resource, int amount) {
        costs.put(resource, amount);
    }

    public Map<ResourceType, Integer> getCosts() {
        return costs;
    }
    
    public abstract Map<ResourceType, Integer> getBuildCost();

    public abstract String toCSV();
   
}