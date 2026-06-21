package org.uob.a2.engine;

import org.uob.a2.model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Collections; 

public class SimulationState {

    private final List<Producer> producers;
    private final List<Converter> converters;
    private final List<Consumer> consumers;
    private final Map<ResourceType, Integer> inventory; 
    private final List<Map<ResourceType, Integer>> resourceHistory; 

    public SimulationState() {
        this.producers = new ArrayList<>();
        this.converters = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.inventory = new EnumMap<>(ResourceType.class);
        this.resourceHistory = new ArrayList<>();
    }

	// function for assisting in loading
	// cannot simply override state in engine as private final
	public void setStateFromLoad(
	    Map<ResourceType, Integer> loadedInventory,
	    List<Producer> loadedProducers,
	    List<Converter> loadedConverters,
	    List<Consumer> loadedConsumers,
	    List<Map<ResourceType, Integer>> loadedHistory) {
		
	    // clear existing data
	    this.producers.clear();
	    this.converters.clear();
	    this.consumers.clear();
	    this.inventory.clear();
	    this.resourceHistory.clear();

	    this.inventory.putAll(loadedInventory);

	    // use addAll to transfer the loaded entity objects
	    this.producers.addAll(loadedProducers);
	    this.converters.addAll(loadedConverters);
	    this.consumers.addAll(loadedConsumers);
    
	    // set history
	    this.resourceHistory.addAll(loadedHistory);
	}

	// handle additions/deductions
    public void addResource(ResourceType resource, int amount) {
        inventory.merge(resource, amount, Integer::sum); 
    
	    if (inventory.getOrDefault(resource, 0) < 0) {
	        inventory.put(resource, 0);
	    }
    }

	// set initials and load amounts
    public void updateResource(ResourceType resource, int amount) {
	    if (amount >= 0) {
	        inventory.put(resource, amount); 
	    }
    }

	// return the amount of a resource
    public int getResourceAmount(ResourceType resource) { 
        return inventory.getOrDefault(resource, 0);
    }

	// manually remove resource
    public boolean removeResource(ResourceType resource, int amount) { 
        int currentAmount = getResourceAmount(resource);
        if (currentAmount >= amount) {
            inventory.put(resource, currentAmount - amount);
            return true;
        }
        return false;
    }
    
    public void updateHistory() { 
        resourceHistory.add(new EnumMap<>(inventory));
    }

	// add entitiy to the lists
    public void addEntity(Entity entity) {
        if (entity instanceof Producer) {
            producers.add((Producer) entity);
        } else if (entity instanceof Converter) {
            converters.add((Converter) entity);
        } else if (entity instanceof Consumer) {
            consumers.add((Consumer) entity);
        }
    }

	// return all entities
    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        entities.addAll(producers);
        entities.addAll(converters);
        entities.addAll(consumers);
        return entities;
    }

	// get entities that are tickable
    public List<Tickable> getAllTickableEntities() {
        List<Tickable> entities = new ArrayList<>();
        entities.addAll(producers);
        entities.addAll(converters);
        entities.addAll(consumers);
        return entities;
    }


	// other getters below
    public List<Producer> getProducers() {
        return producers;
    }

    public List<Converter> getConverters() {
        return converters;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public Map<ResourceType, Integer> getInventory() {
        return Collections.unmodifiableMap(inventory); 
    }
    
    public List<Map<ResourceType, Integer>> getHistory() {
        return resourceHistory;
    }
}