package com.rosato.spielepalast.backend.models;

import java.util.ArrayList;

import com.rosato.spielepalast.backend.models.Parcel.Point;

public class Spawner implements Runnable {
  private Facility facility;
  private boolean stop;
  private Long spawnRate;

  public Spawner(Facility facility) {
    this.facility = facility;
    this.spawnRate = facility.getSpawnRate();
  }

  public void setSpawnRate(Long rate) {
    this.spawnRate = rate;
  }

  @Override
  public void run() {
    while (!stop && true) {
      try {
        ArrayList<Point> locations = facility.getTargetLocations();
        Integer i = (int) (Math.random() * (locations.size() - 0));
        Point p = locations.get(i);
        Parcel parcel = new Parcel(p.getX(), p.getY(), facility.getCurrentTime());
        facility.addParcel(parcel);
        Long rate = facility.getOneSecondInMillis() * 60 * this.spawnRate;
        Thread.sleep(rate);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void stop() {
    this.stop = true;
  }
}
