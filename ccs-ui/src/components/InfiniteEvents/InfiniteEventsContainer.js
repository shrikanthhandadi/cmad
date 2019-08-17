import React from "react";
import { connect } from 'react-redux';
import InfiniteEventsComponent from './InfiniteEventsComponent';

import { loadEvents } from '../../actions/eventActions';

class InfiniteEventsContainer extends React.Component {

    constructor(props) {
        super(props);
    }

    componentWillMount() {
        this.props.loadEvents(this.props.make, this.props.model, 20, 0);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.make !== this.props.make || nextProps.model !== this.props.model) {
            this.props.loadEvents(nextProps.make, nextProps.model, 20, 0);
        }
    }

    render() {
        return (
            <InfiniteEventsComponent
                make={ this.props.make }
                model={ this.props.model }
                pageSize={ this.props.pageSize }
                pageNumber={ this.props.pageNumber }
                events={ this.props.events }
                total={ this.props.total }
                hasMore={ this.props.total > this.props.events.length }
                error={ this.props.error }
                pending={ this.props.pending }
                loadEvents={ this.props.loadEvents }
            />
        );

    }
}

const mapStateToProps = (state) => {
    return {
        make: state.carReducer.make,
        model: state.carReducer.model,
        pageSize: state.eventReducer.pageSize,
        pageNumber: state.eventReducer.pageNumber,
        events: state.eventReducer.events,
        total: state.eventReducer.total,
        error: state.eventReducer.error,
        pending: state.eventReducer.pending
    };
};


const mapDispatchToProps = (dispatch) => {
    return {
        loadEvents: (make, model, pageSize, pageNumber) => dispatch(loadEvents(make, model, pageSize, pageNumber)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(InfiniteEventsContainer)
