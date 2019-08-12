import React from "react";
import Select from 'react-select';
import PropTypes from 'prop-types';

export class EventFilter extends React.Component {
    constructor(props) {
        super();
        this.state = {
            makes: [],
            models: [],
            selectedMake: '',
            token: props.token
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

     //when component receives new props
     componentWillReceiveProps(nextProps) {
         console.log('Props receive ',nextProps);
        this.setState({
            token: nextProps.token,
        });
        this.loadMakes();
    }

    loadMakes() {
        fetch('http://localhost:9090/ccs/cars?pageNum=0&pageSize=1000', {
            headers: new Headers({
                'Authorization': this.state.token,
            })
        })
            .then(res => res.json())
            .then((data) => {
                const allMakes = data.data.map(car => (
                    car.make
                ));
                const uniqMakes = Array.from(new Set(allMakes));
                const carMakes = uniqMakes.map(make => ({
                    label: make,
                    value: make,
                }));

                //Add blank at top
                carMakes.unshift({ value: '', label: '' });

                this.setState({
                    makes: carMakes
                });

            })

            .catch(console.log)
    }

    loadModels(selectedOption) {
        fetch('http://localhost:9090/ccs/cars?make=' + selectedOption.value + '&pageNum=0&pageSize=1000', {
            headers: new Headers({
                'Authorization': this.state.token,
            })
        }).then(res => res.json())
            .then((data) => {
                const allModels= data.data.map(car => (
                    car.model
                ));
                const uniqModels= Array.from(new Set(allModels));
                const carModels = uniqModels.map(model => ({
                    label: model,
                    value: model,
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
    updateMakeAndModel: PropTypes.func,
    token: PropTypes.string
}
