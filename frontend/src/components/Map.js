import React from "react";

import {
  ScatterChart,
  Scatter,
  Cell,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  LabelList,
  ResponsiveContainer,
} from "recharts";

const colors = {
  Postman: "red",
  Bicycle: "blue",
  Truck: "orange",
  Location: "green",
};

const Map = ({ entityList, color }) => {
  let data;
  if (entityList !== undefined) {
    data = entityList.map((entity, i) => {
      return {
        x: entity.currentLocation.xformatted,
        y: entity.currentLocation.yformatted,
        z: 0,
        name: entity.entityName,
        parcels: entity.parcels.length,
        speed: entity.speed,
        label: entity.name,
      };
    });
  } else {
    data = [];
  }
  return (
    <ResponsiveContainer width="100%">
      <ScatterChart
        width={700}
        height={700}
        margin={{ top: 20, right: 20, bottom: 20, left: 20 }}
      >
        <XAxis
          type="number"
          dataKey={"x"}
          name="Location X"
          unit="Km"
          domain={[0, 5.5]}
        />
        <YAxis
          type="number"
          dataKey={"y"}
          name="Location Y"
          unit="Km"
          domain={[0, 5.5]}
        />
        <CartesianGrid />
        <Tooltip cursor={{ strokeDasharray: "3 3" }} />
        <Scatter name="A school" data={data} fill="#8884d8">
          <LabelList dataKey="label" />
          {data.map((entry, index) => {
            return <Cell key={`cell-${index}`} fill={colors[entry.name]} />;
          })}
        </Scatter>
      </ScatterChart>
    </ResponsiveContainer>
  );
};

export default Map;
