import React, { useState } from "react";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";

const EntitiesPanel = ({ facility }) => {
  const [type, setType] = useState("POSTMAN");

  const onTypeChange = (e) => {
    console.log(e.target.value);
    setType(e.target.value);
  };

  const addEntity = async (e) => {
    e.preventDefault();
    console.log(type);
    axios.post(
      "http://localhost:8080/v1/facility",
      { type: type },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  };

  const removeEntity = async (e) => {
    e.preventDefault();
    console.log(type);
    axios.delete(
      "http://localhost:8080/v1/facility",
      { data: { type: type } },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  };

  return (
    <div>
      <div className="card bg-light">
        <div className="card-body">
          <div>
            <span className="red-dot"></span>&nbsp; Postmans:{" "}
            {facility.postmansCount}
          </div>
          <hr />
          <div>
            <span className="blue-dot"></span> &nbsp; Bycicles:{" "}
            {facility.byciclesCount}
          </div>
          <hr />
          <div>
            <span className="orange-dot"></span>&nbsp; Trucks:{" "}
            {facility.trucksCount}
          </div>
          <hr />
          <div className="form-row align-items-center">
            <div className="col-auto">Add Entity:</div>
            <div className="col-auto">
              <select
                name="type"
                onChange={onTypeChange}
                className="form-control"
              >
                <option value="POSTMAN">Postman</option>
                <option value="BICYCLE">Bycicle</option>
                <option value="TRUCK">Truck</option>
              </select>
            </div>
            <div className="col-auto btn-group">
              <button onClick={addEntity} className="btn btn-primary">
                <FontAwesomeIcon icon={faPlus} />
              </button>
              <button onClick={removeEntity} className="btn btn-primary">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EntitiesPanel;
