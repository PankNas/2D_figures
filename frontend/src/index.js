import "./index.css";
import React, { Component } from "react";
import { createRoot } from 'react-dom/client';

import TopPanel from "./top";

import MainPage from "./main_page";
import { CreationPage } from "./creation";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { AreaPage } from "./area";
import { PerimeterPage } from "./perimeter";
import { DeletePage } from "./delete";
import { IntersectionPage } from "./intersection";
import { MovePage } from "./move";

class Main extends Component {
  render() {
    return (<Router>
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/add" element={<CreationPage />}></Route>
        <Route path="/delete" element={<DeletePage />}></Route>
        <Route path="/move" element={<MovePage />}></Route>
        <Route path="/area" element={<AreaPage />}></Route>
        <Route path="/perimeter" element={<PerimeterPage />}></Route>
        <Route path="/intersection" element={<IntersectionPage />}></Route>
      </Routes>
    </Router >)
  }
}


class App extends Component {
  render() {
    return (
      <div>
        <TopPanel />
        <Main />
      </div>
    )
  }
}


const root = createRoot(document.getElementById('root'));

root.render(<App />);
