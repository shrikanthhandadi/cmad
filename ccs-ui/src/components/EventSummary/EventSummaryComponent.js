import React from "react";
import Chart from "react-google-charts";

class EventSummaryComponent extends React.Component {

    render() {
        const options = {
            //title: "Event Activities Statistics",
            pieHole: 0.4,
            is3D: true
        };
        if (this.props.error !== undefined) {
            return (
                <div className="container" >
                    <div className="row">
                        <div className="col-md-10 col-lg-10">{this.props.error}</div>
                    </div>
                </div>
            );
        } else {
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
                                    data={ this.props.eventStats }
                                    options={ options }
                                />
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
    }

}

export default EventSummaryComponent;
