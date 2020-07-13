import React from "react";

const EndOfShiftPanel = () => {
  return (
    <div className="alert alert-danger" role="alert">
      <h4 className="alert-heading">Today's Shift has Ended</h4>
      <p>
        The shift starts at 6:00 am and finishes at 9:00 pm. During this period,
        all entities return to the facility and drop any undelivered packages at
        the location.
      </p>
      <hr />
      <p className="mb-0">
        When the shift restarts, all entities pick their corresponding packages
        and head out for delivery.
      </p>
    </div>
  );
};

export default EndOfShiftPanel;
