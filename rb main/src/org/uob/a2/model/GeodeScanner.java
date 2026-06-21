package org.uob.a2.model;

import org.uob.a2.engine.Context;
import org.uob.a2.engine.Producer;
import org.uob.a2.engine.SimulationState;
import org.uob.a2.engine.Tickable;
import java.util.Map;

public class GeodeScanner extends Producer implements Tickable {
	// Defined Production Rate: 5 GEODE_FRAGMENT per tick
    private static final int PRODUCTION_AMOUNT = 5;
    
    // Defined Build Cost: 500 CREDIT
    private static final Map<ResourceType, Integer> GEODE_SCANNER_COST = Map.of(ResourceType.GOLD_BAR, 2);

	// constructor
    public GeodeScanner(String name) {
        super(name, ResourceType.GEODE_FRAGMENT, PRODUCTION_AMOUNT); 
    }
	
    @Override
    public Map<ResourceType, Integer> getBuildCost() {
        return GEODE_SCANNER_COST;
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
        return "GeodeScanner," + this.getName() + "," + this.getProduct().name() + "," + this.getAmount();
    }
}