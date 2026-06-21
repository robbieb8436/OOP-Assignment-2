package org.uob.a2.model;

import org.uob.a2.engine.Context;
import org.uob.a2.engine.Producer;
import org.uob.a2.engine.SimulationState;
import org.uob.a2.engine.Tickable;
import java.util.Map;

public class TachyonHarvester extends Producer implements Tickable {
	// Defined Production Rate: 10 TACHYON_FRAGMENT per tick
    private static final int PRODUCTION_AMOUNT = 10;
    
    // Defined Build Cost: 500 CREDIT
    private static final Map<ResourceType, Integer> TACHYON_HARVESTER_COST = Map.of(
        ResourceType.LUNAR_STONE, 5,
        ResourceType.GOLD_BAR, 5);

	// constructor
    public TachyonHarvester(String name) {
        super(name, ResourceType.TACHYON_FRAGMENT, PRODUCTION_AMOUNT); 
    }
	
    @Override
    public Map<ResourceType, Integer> getBuildCost() {
        return TACHYON_HARVESTER_COST;
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
        return "TachyonHarvester," + this.getName() + "," + this.getProduct().name() + "," + this.getAmount();
    }
}