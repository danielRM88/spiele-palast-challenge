package com.rosato.spielepalast.backend.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rosato.spielepalast.backend.models.Facility;
import com.rosato.spielepalast.backend.models.Facility.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/facility")
public class FacilityController {
  public static class EntityRequest {
    private String type;

    public void setType(String type) {
      this.type = type;
    }

    public String getType() {
      return type;
    }
  }

  public static class ClockRateRequest {
    @NotNull
    @Size(min = 1, max = 100)
    private Long rate;

    public void setRate(Long rate) {
      this.rate = rate;
    }

    public Long getRate() {
      return rate;
    }
  }

  public static class SpawnRateRequest {
    private Long spawnRate;

    public void setSpawnRate(Long spawnRate) {
      this.spawnRate = spawnRate;
    }

    public Long getSpawnRate() {
      return spawnRate;
    }
  }

  @Autowired
  private Facility facility;

  @GetMapping("")
  public Facility getFacility() {
    return facility;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public void createEntity(@RequestBody EntityRequest request) {
    EntityType entityType = EntityType.valueOf(request.getType());
    facility.addEntity(entityType);
  }

  @DeleteMapping("")
  @ResponseStatus(HttpStatus.OK)
  public void deleteEntity(@RequestBody EntityRequest request) {
    EntityType entityType = EntityType.valueOf(request.getType());
    facility.fireEntity(entityType);
  }

  @PostMapping("/set-clock-rate")
  @ResponseStatus(HttpStatus.OK)
  public void setClockRate(@RequestBody @Valid ClockRateRequest request) {
    facility.setClockRate(request.getRate());
  }

  @PostMapping("/set-spawn-rate")
  @ResponseStatus(HttpStatus.OK)
  public void setSpawnRate(@RequestBody SpawnRateRequest request) {
    facility.setSpawnRate(request.getSpawnRate());
  }
}
