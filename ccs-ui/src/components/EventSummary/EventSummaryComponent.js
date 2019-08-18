import React from "react";
import Chart from "react-google-charts";

const options = {
    //title: "Event Activities Statistics",
    pieHole: 0.4,
    is3D: true
};

const EventSummaryComponent = (props) => {
    return (
        <div className="container">
            <div className="row">
                <div className="col-md-10 col-lg-10">
                    <h4 style={ { color: '#337ab7' } }>Event Summary</h4>
                </div>
            </div>
            <div className="row">
                <div className="col-md-10 col-lg-10">
                    <div className="Summary">
                        <Chart
                            chartType="PieChart"
                            width="100%"
                            height="500px"
                            data={ props.eventStats }
                            options={ options }
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EventSummaryComponent;