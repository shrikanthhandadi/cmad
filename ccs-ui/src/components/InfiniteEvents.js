import React, { Fragment } from "react";
import request from "superagent";
import PropTypes from 'prop-types';

export class InfiniteEvents extends React.Component {
    constructor(props) {
        super();

        // Sets up our initial state
        this.state = {
            token: props.token,
            error: false,
            hasMore: true,
            isLoading: false,
            make: props.make,
            model: props.model,
            events: [],
            pageNumber: 0,
        };

        // Binds our scroll event handler
        window.onscroll = () => {
            const {
                loadEvents,
                state: {
                    error,
                    isLoading,
                    hasMore,
                },
            } = this;
            // Bails early if:
            // * there's an error
            // * it's already loading
            // * there's nothing left to load
            if (error || isLoading || !hasMore) return;

            var totalHeight = document.documentElement.scrollTop + window.innerHeight + 1;// adding 1 for chrome issue
            // Checks that the page has scrolled to the bottom
            if (totalHeight.toFixed(0) >= document.documentElement.offsetHeight) {
                this.setState({
                    pageNumber: this.state.pageNumber + 1
                });
                loadEvents();
            }

        };
    }

    componentWillMount() {
        // Loads some events on initial load
        this.loadEvents();
    }

    //when component receives new props
    componentWillReceiveProps(nextProps) {
        this.setState({
            make: nextProps.make,
            model: nextProps.model,
            events: [],
            pageNumber: 0,
        });
        this.loadEvents();
    }

    loadEvents = () => {
        this.setState({ isLoading: true }, () => {
            request
                .get('http://localhost:9090/ccs/events?make=' + this.state.make + '&model=' + this.state.model + '&pageSize=' + this.props.pageSize + '&pageNum=' + this.state.pageNumber)
                .set('Authorization', this.state.token)
                .then((results) => {
                    // Creates a massaged array of event data
                    const nextEvents = results.body.data.map(event => ({
                        id: event.id,
                        carId: event.carId,
                        make: event.make,
                        model: event.model,
                        date: event.date,
                        severity: event.severity,
                        data: event.data,
                    }));

                    // Merges the next events into our existing events
                    this.setState({
                        // Note: Depending on the API you're using, this value may be
                        // returned as part of the payload to indicate that there is no
                        // additional data to be loaded
                        hasMore: (this.state.events.length < results.body.totalElements),
                        isLoading: false,
                        events: [
                            ...this.state.events,
                            ...nextEvents,
                        ],
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

        const {
            error,
            hasMore,
            isLoading,
            events,
        } = this.state;

        return (
            //table-striped table-bordered table-hover table-dark
            <div className="container">
                <div className="row">
                    <div className="col-md-10 col-lg-10 "><h3>Event List</h3></div>
                </div>
                <div className="row">
                    <div className="col-md-10 col-lg-10">
                        <table className="table table-striped table-hover">
                            <thead className="thead-dark">
                                <tr>
                                    <th scope="col">Event ID</th>
                                    <th scope="col">Car ID</th>
                                    <th scope="col">Make</th>
                                    <th scope="col">Model</th>
                                    <th scope="col">Date</th>
                                    <th scope="col">Severity</th>
                                    <th scope="col">Data</th>
                                </tr>
                            </thead>
                            <tbody>
                                { events.map(event => (
                                    <Fragment key={ event.id }>
                                        <tr>
                                            <th scope="row">{ event.id }</th>
                                            <td>{ event.carId }</td>
                                            <td>{ event.make }</td>
                                            <td>{ event.model }</td>
                                            <td>{ event.date }</td>
                                            <td>{ event.severity }</td>
                                            <td>{ event.data }</td>
                                        </tr>
                                    </Fragment>
                                )) }
                                { error &&
                                    <tr>
                                        <td>{ error }</td>
                                    </tr>
                                }
                                { isLoading &&
                                    <tr><td>Loading...</td></tr>
                                }
                                { !hasMore &&
                                    <tr><td colSpan={ 3 }>End of events. Total: { this.state.events.length }</td></tr>
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );
    }
}

InfiniteEvents.propTypes = {
    make: PropTypes.string,
    model: PropTypes.string,
    pageSize: PropTypes.number
}
