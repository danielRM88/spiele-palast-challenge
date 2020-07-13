package com.rosato.spielepalast.backend.models;

import java.util.LinkedList;

import com.rosato.spielepalast.backend.models.Facility.EntityType;

public class Truck extends GameEntity {
  public Truck(Facility facility) {
    setUpkeep(500d);
    setSpeed(50d);
    setCapacity(100);
    setParcels(new LinkedList<>());
    setFacility(facility);
    setCurrentLocation(facility.getLocation());
  }

  public String getEntityName() {
    return "Truck";
  }

  @Override
  protected void collectUpkeep() {
    getFacility().collectUpkeep(EntityType.TRUCK);
  }
}
