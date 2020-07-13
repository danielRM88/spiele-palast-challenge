import React from "react";
import Facility from "./components/Facility";
import "./App.css";

function App() {
  return (
    <div
      className="App"
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Facility />
    </div>
  );
}

export default App;
