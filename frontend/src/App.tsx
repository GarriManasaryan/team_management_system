import React from 'react';
import logo from './tms_logo.png';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import ListUsers from './elements/user/ListUsers';

function App() {
  return (
    <div className="App">
      <Router>

        {/* <NavBar/> */}
        {/* <Background></Background> */}
        <Routes>
          {/* <Route path="/tasks" element={<ListTasks/>}></Route>
          <Route path="/test_assessments" element={<ListTestTemplates/>}></Route>
          <Route path="/view_test_assessments_test" element={<ViewTestTemplate/>}></Route> */}
          <Route path="/users" element={<ListUsers/>}></Route>
          {/* <Route path="/add_candidate" element={<AddUser/>}></Route>
          <Route path="/add_task" element={<AddTask/>}></Route>
          <Route path="/add_task_to_test/:id" element={<AddTaskToTest/>}></Route>
          <Route path="/edit_task/:id" element={<EditTask/>}></Route>
          <Route path="/:userId/:testTemplateId" element={<StartTest/>}></Route>
          <Route path="/test_processing/:id" element={<StartTest/>}></Route> */}
        </Routes>
        
      </Router>
    </div>
  );
}

export default App;
