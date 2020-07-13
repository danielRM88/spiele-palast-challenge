import React from "react";

const OngoingShiftPanel = () => {
  return (
    <div className="alert alert-success" role="alert">
      <h4 className="alert-heading">Shift is Ongoing</h4>
      <p>
        The shift is ongoing during the day for 13 hours. From 6:00 am till 9:00
        pm, during this time all entities go out and deliver the parcels they
        have picked at the facility.
      </p>
      <hr />
      <p className="mb-0">
        All entities are out trying to deliver their parcels.
      </p>
    </div>
  );
};

export default OngoingShiftPanel;
