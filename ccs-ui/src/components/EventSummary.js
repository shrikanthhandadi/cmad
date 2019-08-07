import React from "react";
import request from "superagent";

import Chart from "react-google-charts";
import PropTypes from 'prop-types';

export class EventSummary extends React.Component {
    constructor(props) {
        super();

        // Sets up our initial state
        this.state = {
            make: props.make,
            model: props.model,
            stats: []
        };
    }

    componentWillMount() {
        this.loadStats();
    }

    //when component receives new props
    componentWillReceiveProps(nextProps) {
        this.setState({
            make: nextProps.make,
            model: nextProps.model,
            stats: []
        });
        this.loadStats();
    }

    loadStats = () => {
        this.setState({ isLoading: true }, () => {
            request
                .get('http://localhost:9090/ccs/stats?make=' + this.state.make + '&model=' + this.state.model)
                .set('Authorization', 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5NzYiLCJpYXQiOjE1NjUxOTMxNjAsImV4cCI6MTU2NjA1NzE2MH0.rlw9v3kTzuZdGPFzR3Omskd6YPqZ0zwP4BCkK_Q-yn70l0PjCE4u7AN85XRbWb3OpJnyT1B29R0JevZq4xMQEQ')
                .then((results) => {
                    // Creates a array of array of stats data
                    const eventStats = results.body.map(stat => ([
                        stat.severity,
                        stat.count
                    ]));

                    eventStats.unshift(['Task', 'Event stats']);//google-charts requisite
                    this.setState({
                        isLoading: false,
                        stats: eventStats,
                    });

                })
                .catch((err) => {
                    this.setState({
                        error: err.message,
                        isLoading: false,
                    });
                })
        });
    }

    render() {
        const options = {
            //title: "Event Activities Statistics",
            pieHole: 0.4,
            is3D: true
        };

        return (
            <div className="container" >
                <div className="row">
                    <div className="col-md-10 col-lg-10"><h3>Event Summary</h3></div>
                </div>
                <div className="row">
                    <div className="col-md-10 col-lg-10">
                        <div className="Summary">
                            <Chart
                                chartType="PieChart"
                                width="100%"
                                height="500px"
                                data={ this.state.stats }
                                options={ options }
                            />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

EventSummary.propTypes = {
    make: PropTypes.string,
    model: PropTypes.string
}

