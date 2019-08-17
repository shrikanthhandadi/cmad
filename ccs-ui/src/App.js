import React from "react";
import { Header } from "./components/Header";
import { LeftMenu } from "./components/LeftMenu";
import { EventFilter } from "./components/EventFilter";
import { InfiniteEvents } from "./components/InfiniteEvents";
import { EventSummary } from "./components/EventSummary";
import { Login } from "./components/Login";

export default class App extends React.Component {
    constructor() {
        super();
        this.state = {
            token: '',
            make: '',
            model: '',
        }
    }

    updateMakeAndModel(make, model) {
        this.setState({
            make: make,
            model: model,
        });
    }

    updateToken(token) {
        console.log('Token update '+token);
        this.setState({
            token: 'Bearer '+token,
        });
    }

    render() {
        return (
            <div className="container  border border-dark" >
                <div className="row">
                    <div className="col-md-12 col-lg-12 col-md-offset-4">
                        <Header />
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-2 col-lg-2">
                        <LeftMenu />
                    </div>
                    <div className="col-md-10 col-lg-10">
                        <div className="row">
                            <div className="col-md-10 col-lg-10 " >
                                <Login updateToken={ this.updateToken.bind(this) } />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-10 col-lg-10 " >
                                <EventFilter updateMakeAndModel={ this.updateMakeAndModel.bind(this) } token={ this.state.token } />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-10 col-lg-10">
                                <EventSummary make={ this.state.make } model={ this.state.model } token={ this.state.token } />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-10 col-lg-10">
                                <InfiniteEvents pageSize={ 20 } make={ this.state.make } model={ this.state.model } token={ this.state.token } />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
