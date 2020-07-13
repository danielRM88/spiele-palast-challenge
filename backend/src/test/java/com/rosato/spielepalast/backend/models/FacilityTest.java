package com.rosato.spielepalast.backend.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FacilityTest {
  @Test
  public void shouldAddParcel() {
    Facility facility = new Facility();
    Parcel parcel = new Parcel(10d, 100d, facility.getCurrentTime());
    facility.addParcel(parcel);

    assertEquals(1, facility.getParcelCount());
  }

  @Test
  public void shouldRetrieveParcel() {
    Facility facility = new Facility();
    facility.addParcel(new Parcel(10d, 100d, facility.getCurrentTime()));
    Parcel parcel = facility.retrieveParcel();

    assertNotNull(parcel);
  }
}
