package org.uob.a2.model;

public enum ResourceType {
    ORE,
    METAL,
    CREDITS,
    //KEEP THE ABOVE FOR TESTING BUT ADD YOUR OWN
	GEODE_FRAGMENT,
	GEODE_CRYSTAL,
    LUNAR_STONE,
	GOLD_ORE,
	GOLD_BAR,
	TACHYON_FRAGMENT,
	TACHYON_CRYSTAL,
}
// Start with: 1000 CREDIT and 100 LUNAR_STONE
// Producers 
// Lunar Drill:
//     Produces: LUNAR_STONE (10)
//     Build Cost: 500 CREDIT

// Asteroid Mine:
//     Produces: GOLD_ORE (10)
//     Build Cost: 500 CREDIT

// Geode Scanner:
//     Produces: GEODE_FRAGMENT (5)
//     Build Cost: 2 GOLD_BAR

// Tachyon Harvester:
//     Produces: TACHYON_FRAGMENT (5)
//     Build Cost: 5 LUNAR_STONE, 5 GOLD_BAR

// ------------------------------------------------------------------------

// Converters 
// Refining Forge:
//     Consumes: GOLD_ORE (10) -> Produces: GOLD_BAR (1)
//     Build Cost: 10 LUNAR_STONE

// Crystal Synthesiser:
//     Consumes: GEODE_FRAGMENT (10) -> Produces: GEODE_CRYSTAL (1)
//     Build Cost: 10 GOLD_BAR

// Tachyon Amplifier:
//     Consumes: TACHYON_FRAGMENT (10) -> Produces: TACHYON_CRYSTAL (1)
//     Build Cost: 1 GEODE_CRYSTAL, 1 GOLD_BAR

// ------------------------------------------------------------------------

// Consumer 
// Hyperspace Drive:
//     Consumes per level: GEODE_CRYSTAL (5) + TACHYON_CRYSTAL (5)
//     Build Cost: 10 GEODE_CRYSTAL, 10 TACHYON_CRYSTAL, 50 GOLD_BAR
//     Final Action: JUMP (consumes max Level -> provides large influx of CREDIT)