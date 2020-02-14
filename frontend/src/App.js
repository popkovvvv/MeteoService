import React from 'react';
import Weather from './components/Weather';
import {
  Navbar
} from 'react-bootstrap';

function App() {
  return (
    <div className="App">
      <Navbar bg="dark" variant="dark">
        <Navbar.Brand href="#home">
          Weather Test Task
        </Navbar.Brand>
      </Navbar>
      <br />
      <Weather />
    </div>
  );
}

export default App;
