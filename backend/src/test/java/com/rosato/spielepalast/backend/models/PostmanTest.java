package com.rosato.spielepalast.backend.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostmanTest {
  @Test
  public void shouldDeliverAllParcels() throws InterruptedException {
    Facility facility = new Facility(LocalDateTime.parse("2020-07-10T20:00:00"));
    facility.setClockRate(4000l);
    facility.addParcel(new Parcel(10d, 200d, facility.getCurrentTime()));
    facility.addParcel(new Parcel(20d, 140d, facility.getCurrentTime()));
    facility.addParcel(new Parcel(5d, 10d, facility.getCurrentTime()));
    Postman postman = new Postman(facility);
    Thread t1 = new Thread(postman);
    t1.start();
    t1.join();
    assertEquals(3, facility.getDeliveries().size());
    assertEquals(25, facility.getReward());
  }
}
