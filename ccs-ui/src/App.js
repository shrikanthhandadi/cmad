import React from "react";
import { render } from "react-dom";
import { Header } from "./components/Header";
import { LeftMenu } from "./components/LeftMenu";
import { EventFilter } from "./components/EventFilter";
import { InfiniteEvents } from "./components/InfiniteEvents";
import { EventSummary } from "./components/EventSummary";

export default class App extends React.Component {
    constructor() {
        super();
        this.state = {
            token: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5NzYiLCJpYXQiOjE1NjUyMDIwNjcsImV4cCI6MTU2NjA2NjA2N30.zAkVJml-VGsLRoJJA416e1EFoo9H4FbxGWX68TnjQfKCMH1eSW9S5rmxA_3-ZpXBw2DDYFS2ktURt6z9X_KoNw',
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
