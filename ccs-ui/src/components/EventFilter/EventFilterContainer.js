import React from "react";
import { connect } from 'react-redux';
import EventFilterComponent from './EventFilterComponent';

import { fetchMakes, fetchModels, setModel } from '../../actions/carActions';

class EventFilterContainer extends React.Component {

    constructor(props) {
        super(props);
        this.onMake = (make) => {
            if (make !== undefined) {
                this.props.fetchModels(make);
            }
        }
        this.onModel = (model) => {
            this.props.setModel(model);
        }
    }

    componentWillMount() {
        this.props.fetchMakes();
    }

    toOptions(values) {
        const options = values.map(value => ({
            label: value,
            value: value,
        }));
        //Add blank at top
        options.unshift({ value: '', label: '' });
        return options;
    }

    render() {
        return (
            <EventFilterComponent
                makeOptions={ this.toOptions(this.props.makes) }
                modelOptions={ this.toOptions(this.props.models) }
                onMake={ selectedOption => this.onMake(selectedOption.value) }
                onModel={ selectedOption => this.onModel(selectedOption.value) }
                model={ this.props.model }
            />
        );

    }
}

const mapStateToProps = (state) => {
    return {
        makes: state.carReducer.makes,
        models: state.carReducer.models,
        make: state.carReducer.make,
        model: state.carReducer.model,
    };
};


const mapDispatchToProps = (dispatch) => {
    return {
        fetchMakes: () => dispatch(fetchMakes()),
        fetchModels: (make) => dispatch(fetchModels(make)),
        setModel: (model) => dispatch(setModel(model))
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(EventFilterContainer)
