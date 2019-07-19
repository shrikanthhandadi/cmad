import React from "react";
import { render } from "react-dom";

import { Header } from "./components/Header";
import { Home } from "./components/Home";
import { throws } from "assert";

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            linkName: "Default Home",
            mountHome: true
        }
    }
    updateHomeLink(newLink) {
        this.setState({
            linkName: newLink
        });
    }
    doGreet() {
        alert("Good day!")
    }
    toggleMountHome(){
        this.setState({
            mountHome: !this.state.mountHome
        });
    }
    render() {
        var user = {
            name: "Shrikanth",
            hobbies: ["Sports", "Coding"]
        }
        let homeComp = "";
        if (this.state.mountHome) {
            homeComp = (
            <Home
                name={ "Shrikanth" }
                initialAge={ 22 }
                user={ user }
                greet={ this.doGreet }
                initialHomeLink={this.state.linkName}
                updateHomeLink={ this.updateHomeLink.bind(this) }>
                    <p>This is children content</p>
            </Home>);
        }

        return (
            <div className="container">
                <div className="row">
                    <div className="col-xs-10 col-xs-offset-1">
                        <Header linkName={ this.state.linkName } />
                    </div>
                </div>
                <div className="row">
                    <div className="col-xs-10 col-xs-offset-1">
                        { homeComp }
                    </div>
                </div>
                <div className="row">
                    <div className="col-xs-10 col-xs-offset-1">
                        <button onClick={()=>this.toggleMountHome()} className="btn btn-primary">mount/unmount home component</button>
                    </div>
                </div>
            </div>
        );
    }
}

render(<App />, window.document.getElementById("app"));
