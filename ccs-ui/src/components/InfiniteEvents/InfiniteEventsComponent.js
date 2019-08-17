import React, { Fragment } from "react";

class InfiniteEventsComponent extends React.Component {
    constructor(props) {
        super(props);

        // Binds our scroll event handler
        window.onscroll = () => {
            // Bails early if:
            // * there's an error
            // * it's already loading
            // * there's nothing left to load
            
            if (this.props.error || this.props.pending || !this.props.hasMore) return;

            var totalHeight = document.documentElement.scrollTop + window.innerHeight + 1;// adding 1 for chrome issue
            // Checks that the page has scrolled to the bottom
            if (totalHeight.toFixed(0) >= document.documentElement.offsetHeight) {
                this.props.loadEvents(this.props.make, this.props.model, this.props.pageSize, this.props.pageNumber + 1);
            }
        };
    }

    render() {
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
                                { this.props.events.map(event => (
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
                                { this.props.error &&
                                    <tr>
                                        <td>{ this.props.error }</td>
                                    </tr>
                                }
                                { this.props.pending &&
                                    <tr><td>Loading...</td></tr>
                                }
                                { !this.props.hasMore &&
                                    <tr><td colSpan={ 3 }>End of events. Total: { this.props.events.length }</td></tr>
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );
    }

}

export default InfiniteEventsComponent;
