import React from "react";

const EntityList = ({ entityName, entityList }) => {
  return entityList !== undefined ? (
    entityList.map((entity, i) => {
      const parcels = entity.parcels;
      return (
        <div key={i}>
          {entityName} {i + 1}: <br />
          <p>
            Parcels: {parcels.length} - Location:{" "}
            {entity.currentLocation.xformatted}, &nbsp;
            {entity.currentLocation.yformatted}
          </p>
          {/* {parcels !== undefined && parcels.length > 0
            ? parcels.map((parcel, i) => {
                return (
                  <div key={i}>
                    <p>
                      x: {parcel.targetAddress.x} - y: {parcel.targetAddress.y}
                    </p>
                  </div>
                );
              })
            : null} */}
        </div>
      );
    })
  ) : (
    <p>--</p>
  );
};

export default EntityList;
