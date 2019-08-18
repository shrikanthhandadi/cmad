import React from "react";
import Select from 'react-select';

const EventFilterComponent = (props) => {
    return (
        <div className="container">
            <div className="row" style={ { paddingBottom: '10px' } } >
                <div className="col-md-10 col-lg-10">
                    <h4 style={ { color: '#337ab7' } }>Event Filter</h4>
                </div>
            </div>
            <div className="row">
                <div className="col-md-1 text-right col-md-offset-0">
                    <h5>Makes:</h5>
                </div>
                <div className="col-md-3">
                    <Select
                        options={ props.makeOptions }
                        onChange={ props.onMake }
                    />
                </div>
                <div className="col-md-1 text-right col-md-offset-1" >
                    <h5>Models:</h5>
                </div>
                <div className="col-md-3">
                    <Select
                        options={ props.modelOptions }
                        onChange={ props.onModel }
                        value={ props.modelOptions.filter(option => {
                            return option.label === props.model;
                        }) }
                    />
                </div>
            </div>
        </div>
    );
}

export default EventFilterComponent;
