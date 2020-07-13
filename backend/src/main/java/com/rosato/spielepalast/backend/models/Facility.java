package com.rosato.spielepalast.backend.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.github.javafaker.Faker;
import com.rosato.spielepalast.backend.models.Parcel.Point;

public class Facility {

  public static class Delivery {
    private Point deliveryLocation;
    private Long hoursToDeliver;
    private Long rate;

    public Delivery(Point deliveryLocation, Long hoursToDeliver, Long rate) {
      this.deliveryLocation = deliveryLocation;
      this.hoursToDeliver = hoursToDeliver;
      this.rate = rate;
    }

    public Point getDeliveryLocation() {
      return deliveryLocation;
    }

    public Long getHoursToDeliver() {
      return hoursToDeliver;
    }

    public Long getRate() {
      return this.rate;
    }
  }

  public static enum EntityType {
    POSTMAN, TRUCK, BICYCLE
  }

  private Spawner spawner;
  private ClockTimeTravel clock;
  private Queue<Parcel> parcels;
  private Point location;
  private LinkedList<Delivery> deliveries = new LinkedList<>();
  private Double reward;
  private Stack<Postman> postmans;
  private Stack<Truck> trucks;
  private Stack<Bicycle> bicycles;
  private Long spawnRate;
  private ArrayList<Point> targetLocations;

  public Facility() {
    this.targetLocations = fetchTargetLocations();
    this.parcels = new LinkedList<>();
    this.clock = new ClockTimeTravel();
    this.reward = 10000d;
    this.location = new Point(2.7d, 1.5d);
    this.postmans = new Stack<>();
    this.trucks = new Stack<>();
    this.bicycles = new Stack<>();
    this.spawnRate = 10l;
    this.spawner = new Spawner(this);
    Thread thread = new Thread(spawner);
    thread.start();
  }

  public Facility(LocalDateTime startDate) {
    this.targetLocations = fetchTargetLocations();
    this.parcels = new LinkedList<>();
    this.clock = new ClockTimeTravel(startDate);
    this.reward = 10000d;
    this.location = new Point(2.7d, 1.5d);
    this.postmans = new Stack<>();
    this.trucks = new Stack<>();
    this.bicycles = new Stack<>();
    this.spawnRate = 10l;
    this.spawner = new Spawner(this);
    Thread thread = new Thread(spawner);
    thread.start();
  }

  public Integer getPostmansCount() {
    return this.postmans.size();
  }

  public Integer getByciclesCount() {
    return this.bicycles.size();
  }

  public Integer getTrucksCount() {
    return this.trucks.size();
  }

  public Queue<Parcel> getParcels() {
    return this.parcels;
  }

  public void addEntity(EntityType type) {
    if (type == EntityType.POSTMAN) {
      Postman postman = new Postman(this);
      Thread t = new Thread(postman);
      t.start();
      this.postmans.add(postman);
    } else if (type == EntityType.BICYCLE) {
      Bicycle bicycle = new Bicycle(this);
      Thread t = new Thread(bicycle);
      t.start();
      this.bicycles.add(bicycle);
    } else {
      Truck truck = new Truck(this);
      Thread t = new Thread(truck);
      t.start();
      this.trucks.add(truck);
    }
  }

  public void fireEntity(EntityType type) {
    if (type == EntityType.POSTMAN) {
      if (!this.postmans.isEmpty()) {
        Postman postman = this.postmans.pop();
        postman.fire();
      }
    } else if (type == EntityType.BICYCLE) {
      if (!this.bicycles.isEmpty()) {
        Bicycle bicycle = this.bicycles.pop();
        bicycle.fire();
      }
    } else {
      if (!this.trucks.isEmpty()) {
        Truck truck = this.trucks.pop();
        truck.fire();
      }
    }
  }

  public synchronized void addParcel(Parcel parcel) {
    if (parcels == null) {
      parcels = new LinkedList<>();
    }

    parcels.add(parcel);
  }

  public synchronized Parcel retrieveParcel() {
    Parcel parcel = null;

    // TODO: retrieve efficiently for entity
    if (parcels != null) {
      parcel = parcels.poll();
    }

    return parcel;
  }

  public synchronized int getParcelCount() {
    return parcels.size();
  }

  public synchronized Point getLocation() {
    return location;
  }

  public synchronized Instant getCurrentTime() {
    return clock.instant();
  }

  public String getCurrentTimeFormatted() {
    Instant currentTime = getCurrentTime();
    return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(getZone()).format(currentTime);
  }

  public synchronized ZoneId getZone() {
    return clock.getZone();
  }

  public synchronized void setClockRate(Long rate) {
    this.spawner.stop();
    this.spawner = new Spawner(this);
    Thread thread = new Thread(spawner);
    this.clock.setRate(rate);
    thread.start();
  }

  public synchronized Long getClockRate() {
    return this.clock.getRate();
  }

  public synchronized void registerDelivery(Point location, Long hoursToDelivery) {
    deliveries.add(new Delivery(location, hoursToDelivery, getClockRate()));
    addReward(hoursToDelivery);
  }

  public void addReward(Long hoursToDelivery) {
    if (hoursToDelivery < 18l) {
      reward += 10d;
    } else if (hoursToDelivery < 54l) {
      reward += 5d;
    } else {
      reward += 2.5d;
    }
  }

  public Double getReward() {
    return this.reward;
  }

  public synchronized void collectUpkeep(EntityType type) {
    if (type == EntityType.POSTMAN) {
      this.reward -= 10d;
    } else if (type == EntityType.BICYCLE) {
      this.reward -= 80d;
    } else {
      this.reward -= 500d;
    }
  }

  public synchronized Long getOneSecondInMillis() {
    return this.clock.getOneSecondInMillis();
  }

  public synchronized Integer getOneSecondInNanoseconds() {
    return this.clock.getOneSecondInNanoseconds();
  }

  public synchronized LinkedList<Delivery> getDeliveries() {
    return deliveries;
  }

  public void setDeliveries(LinkedList<Delivery> deliveries) {
    this.deliveries = deliveries;
  }

  public boolean getLunchBreak() {
    boolean endOfShift = false;

    LocalDateTime currentTime = LocalDateTime.ofInstant(getCurrentTime(), getZone());

    String year = Integer.toString(currentTime.getYear());
    String month = String.format("%02d", currentTime.getMonthValue());
    String day = String.format("%02d", currentTime.getDayOfMonth());

    String startLunchBreakString = "" + year + "-" + month + "-" + day + "T12:00:00";
    String endLunchBreakString = "" + year + "-" + month + "-" + day + "T13:00:00";

    LocalDateTime startOfLunchBreak = LocalDateTime.parse(startLunchBreakString);
    LocalDateTime endOfLunchBreak = LocalDateTime.parse(endLunchBreakString);

    if (currentTime.isAfter(startOfLunchBreak) && currentTime.isBefore(endOfLunchBreak)) {
      endOfShift = true;
    }

    return endOfShift;
  }

  public boolean getEndOfShift() {
    boolean endOfShift = false;

    LocalDateTime currentTime = LocalDateTime.ofInstant(getCurrentTime(), getZone());

    String year = Integer.toString(currentTime.getYear());
    String month = String.format("%02d", currentTime.getMonthValue());
    String day = String.format("%02d", currentTime.getDayOfMonth());

    String endShiftString = "" + year + "-" + month + "-" + day + "T21:00:00";
    String endShiftString2 = "" + year + "-" + month + "-" + day + "T23:59:59";
    String endShiftString3 = "" + year + "-" + month + "-" + day + "T00:00:00";
    String endShiftString4 = "" + year + "-" + month + "-" + day + "T06:00:00";

    LocalDateTime endOfShiftTimeNight = LocalDateTime.parse(endShiftString);
    LocalDateTime endOfShiftTimeNight2 = LocalDateTime.parse(endShiftString2);
    LocalDateTime endOfShiftTimeNight3 = LocalDateTime.parse(endShiftString3);
    LocalDateTime endOfShiftTimeNight4 = LocalDateTime.parse(endShiftString4);

    if ((currentTime.isAfter(endOfShiftTimeNight) && currentTime.isBefore(endOfShiftTimeNight2))
        || (currentTime.isAfter(endOfShiftTimeNight3) && currentTime.isBefore(endOfShiftTimeNight4))) {
      endOfShift = true;
    }

    return endOfShift;
  }

  public synchronized Stack<Postman> getPostmans() {
    return this.postmans;
  }

  public synchronized Stack<Bicycle> getBicycles() {
    return this.bicycles;
  }

  public synchronized Stack<Truck> getTrucks() {
    return this.trucks;
  }

  public synchronized Long getSpawnRate() {
    return spawnRate;
  }

  public void setSpawnRate(Long spawnRate) {
    this.spawnRate = spawnRate;
    this.spawner.stop();
    this.spawner = new Spawner(this);
    Thread thread = new Thread(spawner);
    thread.start();
  }

  private ArrayList<Point> fetchTargetLocations() {
    // TODO: use provided locations from coding challenge
    ArrayList<Point> locations = new ArrayList<>();
    Faker faker = new Faker();

    for (int i = 0; i < 20; i++) {
      Double x = Math.random() * (5 - 0);
      Double y = Math.random() * (5 - 0);

      locations.add(new Point(x, y, faker.address().streetAddress()));
    }

    return locations;
  }

  public ArrayList<Point> getTargetLocations() {
    ArrayList<Point> locations = new ArrayList<>(this.targetLocations);
    locations.add(getLocation());
    return locations;
  }
}
