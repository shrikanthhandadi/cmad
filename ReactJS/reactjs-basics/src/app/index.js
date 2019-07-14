import React from "react";
import { render } from "react-dom";

import { Header } from "./components/Header";
import { Home } from "./components/Home";

class App extends React.Component {
    constructor(){
        super();
        this.state = {
            linkName: "Default Home"
        }
    }
    updateHomeLink(newLink){
        this.setState({
            linkName: newLink
        });
    }
    doGreet(){
        alert("Good day!")
    }
    render() {
        var user = {
            name: "Shrikanth",
            hobbies: ["Sports", "Coding"]
        }
        return (
            <div className="container">
                <div className="row">
                    <div className="col-xs-10 col-xs-offset-1">
                        <Header linkName={this.state.linkName}/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-xs-10 col-xs-offset-1">
                        <Home name={ "Shrikanth" } initialAge={ 22 } user={ user } greet={this.doGreet} updateHomeLink={this.updateHomeLink.bind(this)}>
                            <p>This is children content</p>
                        </Home>
                    </div>
                </div>
            </div>
        );
    }
}

render(<App />, window.document.getElementById("app"));
