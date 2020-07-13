package com.rosato.spielepalast.backend.models;

import java.util.LinkedList;

import com.rosato.spielepalast.backend.models.Facility.EntityType;

public class Bicycle extends GameEntity {
  public Bicycle(Facility facility) {
    setUpkeep(80d);
    setSpeed(25d);
    setCapacity(25);
    setParcels(new LinkedList<>());
    setFacility(facility);
    setCurrentLocation(facility.getLocation());
  }

  public String getEntityName() {
    return "Bicycle";
  }

  @Override
  protected void collectUpkeep() {
    getFacility().collectUpkeep(EntityType.BICYCLE);
  }
}
