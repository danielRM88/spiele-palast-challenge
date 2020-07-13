import React, { useState } from "react";
import axios from "axios";
import { faClock, faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import EndOfShiftPanel from "./EndOfShiftPanel";
import OngoingShiftPanel from "./OngoingShiftPanel";
import LunchBreakPanel from "./LunchBreakPanel";

const FacilityInfoPanel = ({ facility }) => {
  const [spawnRate, setSpawnRate] = useState(10);

  const onSpawnRateChange = (e) => {
    console.log(e.target.value);
    setSpawnRate(e.target.value);
  };

  const setClockSpeed = async () => {
    let rate;
    if (facility.clockRate === 1) {
      rate = 2;
    } else if (facility.clockRate === 2) {
      rate = 5;
    } else if (facility.clockRate === 5) {
      rate = 10;
    } else {
      rate = 100;
    }
    console.log(rate);
    axios.post(
      "http://localhost:8080/v1/facility/set-clock-rate",
      { rate: rate },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  };

  const setSpawnRateClick = async () => {
    console.log(spawnRate);
    axios.post(
      "http://localhost:8080/v1/facility/set-spawn-rate",
      { spawnRate: spawnRate },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  };

  return (
    <div>
      <div className="card">
        <div className="card-body">
          <FontAwesomeIcon icon={faClock} /> &nbsp;
          {facility.currentTimeFormatted}
          {/* <br /> */}
          {/* Clock Speed: x{facility.clockRate} */}
          {/* &nbsp;
          Could not get this to work, clock presicion depends too much on the hardware
          {facility.clockRate === 1000 ? null : (
            <button onClick={setClockSpeed} className="btn btn-light btn-sm">
              <FontAwesomeIcon icon={faPlus} />
            </button>
          )} */}
        </div>
      </div>
      {facility.endOfShift ? (
        <EndOfShiftPanel />
      ) : facility.lunchBreak ? (
        <LunchBreakPanel />
      ) : (
        <OngoingShiftPanel />
      )}
      <div className="card">
        <div className="card-body">Rewards: {facility.reward}</div>
      </div>
      <div className="card bg-secondary text-white">
        <div className="card-body">
          <h4>Parcels in facility: {facility.parcelCount}</h4>
          <p>Spawn Rate: 1 parcel every {facility.spawnRate} mins</p>
          <div className="form-row align-items-center">
            <div className="col-auto">
              <input
                type="text"
                value={spawnRate}
                onChange={onSpawnRateChange}
              />
            </div>
            <div className="col-auto">Mins.</div>
            <div className="col-auto">
              <button
                className="btn btn-light btn-sm"
                onClick={setSpawnRateClick}
              >
                Set Rate
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FacilityInfoPanel;
