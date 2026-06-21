package org.uob.a2.model;

import org.uob.a2.engine.Context;
import org.uob.a2.engine.Producer;
import org.uob.a2.engine.SimulationState;
import org.uob.a2.engine.Tickable;
import java.util.Map;

public class AsteroidMine extends Producer implements Tickable {
    // Defined Production Rate: 10 GOLD_ORE per tick
    private static final int PRODUCTION_AMOUNT = 10;
    
    // Defined Build Cost: 500 CREDIT
    private static final Map<ResourceType, Integer> ASTEROID_MINE_COST = Map.of(ResourceType.CREDITS, 500);

	// constructor
    public AsteroidMine(String name) {
        super(name, ResourceType.GOLD_ORE, PRODUCTION_AMOUNT); 
    }
	
    @Override
    public Map<ResourceType, Integer> getBuildCost() {
        return ASTEROID_MINE_COST;
    }

    @Override
    public String tick(Context ctx) {
        produce(ctx);
        
        return this.getName() + " produced " + this.getAmount() + " " + this.getProduct();
    }

	@Override
    public void produce(Context ctx) {
        ctx.getSimulationState().addResource(this.getProduct(), this.getAmount());
    }

	// string output for CSV file
	@Override
    public String toCSV() {
        return "AsteroidMine," + this.getName() + "," + this.getProduct().name() + "," + this.getAmount();
    }
}