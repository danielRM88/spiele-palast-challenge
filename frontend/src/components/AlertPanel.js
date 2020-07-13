import React, { useState, useEffect } from "react";

const AlertPanel = ({ endOfShift, lunchBreak }) => {
  const [cssClass, setCssClass] = useState("alert-success");
  const [title, setTitle] = useState("");
  const [message, setMessage] = useState("");

  if (endOfShift) {
    setCssClass("alert-danger");
    setTitle("Today's Shift has Ended");
    setMessage(
      "The shift starts at 6:00 am and finishes at 9:00 pm. During this period, all entities return to the facility and frop any undelivered packages at the location."
    );
  } else if (lunchBreak) {
    setCssClass("alert-danger");
    setTitle("Lunch Break");
    setMessage(
      "The shift stops for a lunch break of an hour at 12:00 am and finishes at 1:00 pm. During this period, all entities stop and wait for the break to be over."
    );
  } else {
    setCssClass("alert-success");
    setTitle("Shift is Ongoing");
    setMessage(
      "The shift is ongoing during the day for 13 hours. From 6:00 am till 9:00 pm, during this time all entities go out and deliver the parcels they have picked at the facility."
    );
  }

  return (
    <div className={`alert ${cssClass}`} role="alert">
      <h4 className="alert-heading">{title}</h4>
      <p>{message}</p>
      <hr />
      <p className="mb-0">
        When the shift restarts, all entities pick their corresponding packages
        and head out for delivery.
      </p>
    </div>
  );
};

export default AlertPanel;
