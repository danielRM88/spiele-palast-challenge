package com.rosato.spielepalast.backend.models;

import java.time.Instant;

public class Parcel {

  public static class Point {
    private String name;
    private Double x;
    private Double y;

    public Point(Double x, Double y) {
      this.name = "";
      this.x = x;
      this.y = y;
    }

    public Point(Double x, Double y, String name) {
      this.name = name;
      this.x = x;
      this.y = y;
    }

    public Double getX() {
      return x;
    }

    public String getXFormatted() {
      return String.format("%.2f", x);
    }

    public Double getY() {
      return y;
    }

    public String getName() {
      return this.name;
    }

    public String getYFormatted() {
      return String.format("%.2f", y);
    }

    public Double getAngle(Point that) {
      Double x = that.x - this.x;
      Double y = that.y - this.y;
      Double angle = Math.atan2(y, x);

      return angle;
    }

    public Long calculateTimeToDelivery(Point destination, Double speed) {
      Double distance = getDistanceTo(destination);
      Double hours = distance / speed;

      return hours.longValue();
    }

    public Double getDistanceTo(Point destination) {
      Double xDistance = Math.pow((destination.x - this.x), 2);
      Double yDistance = Math.pow((destination.y - this.y), 2);
      Double distance = Math.sqrt(xDistance + yDistance);

      return distance;
    }

    @Override
    public String toString() {
      return "x: " + this.x + " - y: " + this.y;
    }
  }

  private Point point;
  private Instant spawningTime;

  public Parcel(Double x, Double y, Instant spawningTime) {
    point = new Point(x, y);
    this.spawningTime = spawningTime;
  }

  public synchronized Point getTargetAddress() {
    return point;
  }

  public Instant getSpawningTime() {
    return spawningTime;
  }
}
