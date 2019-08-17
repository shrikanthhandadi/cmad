import React from "react";
import Select from 'react-select';

class EventFilterComponent extends React.Component {

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-3"><h3>Event Filter</h3></div>
                </div>
                <div className="row">
                    <div className="col-md-1"><h5>Makes:</h5></div>
                    <div className="col-md-4">
                        <Select
                            options={ this.props.makeOptions }
                            onChange={ this.props.onMake }
                        />
                    </div>
                    <div className="col-md-1"><h5>Models:</h5></div>
                    <div className="col-md-4">
                        <Select
                            options={ this.props.modelOptions }
                            onChange={ this.props.onModel }
                            value={ this.props.modelOptions.filter(option => {
                                return option.label === this.props.model;
                            }) }
                        />
                    </div>
                </div>
            </div>
        );
    }

}

export default EventFilterComponent;
