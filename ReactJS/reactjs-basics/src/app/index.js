import React from "react";
import { render } from "react-dom";
import { Route, BrowserRouter as Router } from 'react-router-dom'

import { Root } from "./components/Root";
import { Home } from "./components/Home";
import { User } from "./components/User";

class App extends React.Component {
    render() {
        return (
            <Router>
                <div>
                    <Route exact path="/" component={ Root } />
                    <Route path="/user" component={ User } />
                    <Route path="/home" component={ Home } />
                </div>
            </Router>
        );
    }
}

render(<App />, window.document.getElementById('app'));
