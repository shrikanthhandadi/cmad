import React from "react";
import { Header } from "./Header";
import { LeftMenu } from "./LeftMenu";
import { EventFilter } from "./EventFilter";
import { InfiniteEvents } from "./InfiniteEvents";
import { EventSummary } from "./EventSummary";
import { Login } from "./Login";

export default class Console extends React.Component {
    constructor() {
        super();
        this.state = {
            token: 'Bearer '+ JSON.parse(localStorage.getItem('token')),
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
