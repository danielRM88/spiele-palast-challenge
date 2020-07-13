package com.rosato.spielepalast.backend.models;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class ClockTimeTravel extends Clock {

  private final Instant t0Instant;
  private final LocalDateTime t0LocalDateTime;
  private final Instant whenObjectCreatedInstant;
  private final ZoneOffset zoneOffset;
  private Long rate = 1l;

  public ClockTimeTravel() {
    this.t0LocalDateTime = LocalDateTime.now();
    this.zoneOffset = ZoneOffset.of("+00:00");
    this.t0Instant = this.t0LocalDateTime.toInstant(this.zoneOffset);
    this.whenObjectCreatedInstant = Instant.now();
    this.rate = 1l;
  }

  public ClockTimeTravel(LocalDateTime startDate) {
    this.t0LocalDateTime = startDate;
    this.zoneOffset = ZoneOffset.of("+00:00");
    this.t0Instant = this.t0LocalDateTime.toInstant(this.zoneOffset);
    this.whenObjectCreatedInstant = Instant.now();
    this.rate = 1l;
  }

  public Long getRate() {
    return this.rate;
  }

  public synchronized void setRate(Long rate) {
    if (rate.compareTo(1000000000l) == 1) {
      rate = 1000000000l;
    }

    this.rate = rate;
  }

  private Instant nextInstant() {
    Instant now = Instant.now();
    long diff = now.toEpochMilli() - whenObjectCreatedInstant.toEpochMilli();
    return t0Instant.plusMillis(diff * rate);
  }

  public Long getOneSecondInMillis() {
    return (1000l / rate);
  }

  public Integer getOneSecondInNanoseconds() {
    Double proportion = (1000d / rate.doubleValue());
    Double nano = proportion * 1000000d;

    if (nano.compareTo(999999d) == 1) {
      nano = 999999d;
    }

    return nano.intValue();
  }

  @Override
  public synchronized Instant instant() {
    return nextInstant();
  }

  @Override
  public ZoneId getZone() {
    return zoneOffset;
  }

  @Override
  public Clock withZone(ZoneId zone) {
    return this;
  }
}
