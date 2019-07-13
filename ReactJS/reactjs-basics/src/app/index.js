import React from "react";
import { render } from "react-dom";

import { Header } from "./components/Header";
import { Home } from "./components/Home";

class App extends React.Component {
    render() {
        var user = {
            name: "Shrikanth",
            hobbies: ["Sports", "Coding"]
        }
        return (
            <div className="container">
                <div className="row">
                    <div className="col-xs-10 col-xs-offset-1">
                        <Header />
                    </div>
                </div>
                <div className="row">
                    <div className="col-xs-10 col-xs-offset-1">
                        <Home name={ "Shrikanth" } initialAge={ 22 } user={ user }>
                            <p>This is children content</p>
                        </Home>
                    </div>
                </div>
            </div>
        );
    }
}

render(<App />, window.document.getElementById("app"));
