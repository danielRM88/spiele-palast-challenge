import React from "react";

const LunchBreakPanel = () => {
  return (
    <div className="alert alert-danger" role="alert">
      <h4 className="alert-heading">Lunch Break</h4>
      <p>
        The shift stops for a lunch break of an hour at 12:00 am and finishes at
        1:00 pm. During this period, all entities stop and wait for the break to
        be over.
      </p>
      <hr />
      <p className="mb-0">
        When the shift restarts, all entities resume operations.
      </p>
    </div>
  );
};

export default LunchBreakPanel;
