package com.rosato.spielepalast.backend.models;

import java.util.LinkedList;

import com.rosato.spielepalast.backend.models.Facility.EntityType;

public class Postman extends GameEntity {
  public Postman(Facility facility) {
    setUpkeep(10d);
    setSpeed(10d);
    setCapacity(25);
    setParcels(new LinkedList<>());
    setFacility(facility);
    setCurrentLocation(facility.getLocation());
  }

  public String getEntityName() {
    return "Postman";
  }

  @Override
  protected void collectUpkeep() {
    getFacility().collectUpkeep(EntityType.POSTMAN);
  }
}
