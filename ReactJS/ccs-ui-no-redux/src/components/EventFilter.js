import React from "react";
import Select from 'react-select';
import PropTypes from 'prop-types';

export class EventFilter extends React.Component {
    constructor(props) {
        super();
        this.state = {
            makes: [],
            models: [],
            selectedMake: ''
        }
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-3"><h3>Event Filter</h3></div>
                </div>
                <div className="row">
                    <div className="col-md-1"><h5>Makes:</h5></div>
                    <div className="col-md-4">
                        <Select options={ this.state.makes } onChange={ this.loadModels.bind(this) } />
                    </div>
                    <div className="col-md-1"><h5>Models:</h5></div>
                    <div className="col-md-4">
                        <Select options={ this.state.models } onChange={ this.publishMakeAndModel.bind(this) } />
                    </div>
                </div>
            </div>
        );
    }

    componentDidMount() {
        this.loadMakes();
    }

    loadMakes() {
        fetch('http://localhost:9090/ccs/cars?distinct=true&fields=make&pageNum=0&pageSize=1000')
            .then(res => res.json())
            .then((data) => {
                const carMakes = data.data.map(car => ({
                    label: car.make,
                    value: car.make,
                }));
                carMakes.unshift({ value: '', label: '' });
                this.setState({
                    makes: carMakes
                });

            })

            .catch(console.log)
    }

    loadModels(selectedOption) {
        fetch('http://localhost:9090/ccs/cars?distinct=true&fields=model&make=' + selectedOption.value + '&pageNum=0&pageSize=1000')
            .then(res => res.json())
            .then((data) => {
                const carModels = data.data.map(car => ({
                    label: car.model,
                    value: car.model,
                }));

                carModels.unshift({ value: '', label: '' });
                this.setState({
                    models: carModels,
                    selectedMake: selectedOption.value,
                });

            })

            .catch(console.log)
    }

    publishMakeAndModel(selectedOption) {
        this.props.updateMakeAndModel(this.state.selectedMake, selectedOption.value);
    }
}

EventFilter.propTypes = {
    updateMakeAndModel: PropTypes.func
}
