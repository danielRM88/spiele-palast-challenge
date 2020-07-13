import React, { useState, useEffect } from "react";
import Map from "./Map";
import axios from "axios";
import FacilityInfoPanel from "./FacilityInfoPanel";
import EntitiesPanel from "./EntitiesPanel";

const Facility = () => {
  const [facility, setFacility] = useState({});
  const [type, setType] = useState("POSTMAN");
  const [allEntities, setAllEntities] = useState([]);

  useEffect(() => {
    setInterval(() => {
      fetchFacility();
    }, 1000);
  }, []);

  const fetchFacility = async () => {
    const res = await axios.get("http://localhost:8080/v1/facility", {
      headers: {
        "Content-Type": "application/json",
      },
    });

    setFacility(res.data);
    const f = res.data;
    if (
      f.postmans !== undefined &&
      f.bicycles !== undefined &&
      f.trucks !== undefined &&
      f.targetLocations !== undefined
    ) {
      const locations = f.targetLocations.map((location, index) => {
        return {
          currentLocation: {
            xformatted: location.xformatted,
            yformatted: location.yformatted,
          },
          entityName: "Location",
          parcels: 0,
          speed: 0,
          label: location.name,
        };
      });
      const entities = f.postmans
        .concat(f.bicycles)
        .concat(f.trucks)
        .concat(locations);
      setAllEntities(entities);
    }
  };

  return (
    <div className="container">
      <h1>Facility</h1>
      <br />
      <div className="row">
        <div className="col-sm-4">
          <FacilityInfoPanel facility={facility} />
          <EntitiesPanel facility={facility} />
        </div>
        <div className="col-sm-8">
          <Map entityList={allEntities} color="red" />
        </div>
        {/* <div className="col-sm-4">
        </div> */}
      </div>
    </div>
  );
};

export default Facility;
