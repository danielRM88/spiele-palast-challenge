package com.rosato.spielepalast.backend.models;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.rosato.spielepalast.backend.models.Parcel.Point;

public abstract class GameEntity implements Runnable {

  public class ParcelDeliveryComparator implements Comparator<Parcel> {
    private Point currentLocation;

    public ParcelDeliveryComparator(Point currentLocation) {
      this.currentLocation = currentLocation;
    }

    @Override
    public int compare(Parcel o1, Parcel o2) {
      Double distance1 = currentLocation.getDistanceTo(o1.getTargetAddress());
      Double distance2 = currentLocation.getDistanceTo(o2.getTargetAddress());
      return distance1.compareTo(distance2);
    }
  }

  private Double upkeep;
  private Double speed;
  private Integer capacity;
  private Facility facility;
  private List<Parcel> parcels;
  private Point currentLocation;
  private boolean employed = true;

  public Double getUpkeep() {
    return upkeep;
  }

  public void setUpkeep(Double upkeep) {
    this.upkeep = upkeep;
  }

  public Double getSpeed() {
    return speed;
  }

  public Double getSpeedInKmsPerSecond() {
    return (speed / (60d * 60d));
  }

  public void setSpeed(Double speed) {
    this.speed = speed;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public void setFacility(Facility facility) {
    this.facility = facility;
  }

  public List<Parcel> getParcels() {
    return parcels;
  }

  public void setParcels(List<Parcel> parcels) {
    this.parcels = parcels;
  }

  public synchronized Point getCurrentLocation() {
    return currentLocation;
  }

  public synchronized void setCurrentLocation(Point currentLocation) {
    this.currentLocation = currentLocation;
  }

  @Override
  public void run() {
    while (employed && true) {
      while (parcels.size() < getCapacity() && facility.getParcelCount() != 0) {
        parcels.add(facility.retrieveParcel());
      }

      Collections.sort(parcels, new ParcelDeliveryComparator(currentLocation));
      while (employed && !parcels.isEmpty() && !facility.getEndOfShift()) {
        Parcel parcel = parcels.get(0);
        Point destination = parcel.getTargetAddress();
        boolean delivered = travelTo(destination);
        if (delivered) {
          Long hours = ChronoUnit.HOURS.between(parcel.getSpawningTime(), facility.getCurrentTime());
          parcels.remove(0);
          facility.registerDelivery(destination, hours);
        }
        Collections.sort(parcels, new ParcelDeliveryComparator(currentLocation));
      }

      travelTo(facility.getLocation());

      while (!parcels.isEmpty()) {
        facility.addParcel(parcels.get(0));
        parcels.remove(0);
      }

      if (facility.getEndOfShift()) {
        collectUpkeep();
      }

      while (employed && facility.getEndOfShift()) {
      }
    }
  }

  private boolean travelTo(Point destination) {
    Double angle = currentLocation.getAngle(destination);
    Instant startTime = facility.getCurrentTime();
    Instant currentTime = startTime;
    Instant lastTime = startTime;
    Double lastDistance = Math.abs(currentLocation.getDistanceTo(destination));
    Double currentDistance = lastDistance;
    int arrived = lastDistance.compareTo(currentDistance);
    Double speedInKmsPerSecond = getSpeedInKmsPerSecond();
    Double cos = Math.cos(angle);
    Double sin = Math.sin(angle);
    while (employed && (arrived == 1 || arrived == 0)
        && (!facility.getEndOfShift() || destination.equals(facility.getLocation()))) {

      while (facility.getLunchBreak()) {
      }

      lastTime = currentTime;
      currentTime = facility.getCurrentTime();
      Long seconds = ChronoUnit.SECONDS.between(lastTime, currentTime);
      Double kms = speedInKmsPerSecond * seconds.doubleValue();
      Double xPosition = (cos * kms) + currentLocation.getX();
      Double yPosition = (sin * kms) + currentLocation.getY();

      setCurrentLocation(new Point(xPosition, yPosition));
      lastDistance = currentDistance;
      currentDistance = Math.abs(currentLocation.getDistanceTo(destination));
      arrived = lastDistance.compareTo(currentDistance);

      try {
        Thread.sleep(facility.getOneSecondInMillis());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return (arrived == -1);
  }

  public void fire() {
    employed = true;
  }

  protected Facility getFacility() {
    return facility;
  }

  protected abstract void collectUpkeep();
}
