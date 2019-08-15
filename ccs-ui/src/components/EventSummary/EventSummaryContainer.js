import React from "react";
import { connect } from 'react-redux';
import EventSummaryComponent from './EventSummaryComponent';

import { getStats } from '../../actions/eventActions';

class EventSummaryContainer extends React.Component {

    constructor(props) {
        super(props);
    }

    componentWillMount() {
        this.props.getStats(this.props.make, this.props.model);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.make !== this.props.make || nextProps.model !== this.props.model ) {
            this.props.getStats(nextProps.make, nextProps.model);
        }
    }

    render() {
        return (
            <EventSummaryComponent
                eventStats={ this.props.eventStats }
            />
        );

    }
}

const mapStateToProps = (state) => {
    return {
        make: state.carReducer.make,
        model: state.carReducer.model,
        eventStats: state.eventReducer.eventStats,
        error: state.eventReducer.error,
    };
};


const mapDispatchToProps = (dispatch) => {
    return {
        getStats: (make, model) => dispatch(getStats(make, model)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(EventSummaryContainer)
