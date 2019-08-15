import ACTION from '../actions/actionTypes';

const INITIAL_STATE = {
    eventStats: [],
    make: undefined,
    model: undefined,
    pageSize: 20,
    pageNumber: 0,
    events: [],
    total: 0,
    pending: false,
    error: undefined
};

function eventReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case ACTION.EVENT_STATS_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.EVENT_STATS_SUCCESS:
            state = {
                ...state,
                eventStats: [...action.eventStats],
                pending: false,
                error: undefined
            }
            break;
        case ACTION.EVENT_STATS_FAILED:
            state = {
                ...state,
                eventStats: [],
                error: action.error,
                pending: false
            }
            break;


        case ACTION.EVENT_FETCHALL_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.EVENT_FETCHALL_SUCCESS:
            if (action.pageNumber === 0) {
                state = {
                    ...state,
                    events: []
                }
            }
            state = {
                ...state,
                make: action.make,
                model: action.model,
                pageSize: action.pageSize,
                pageNumber: action.pageNumber,
                events: [...state.events, ...action.nextEvents],
                total: action.total,
                pending: false,
                error: undefined
            }
            break;
        case ACTION.EVENT_FETCHALL_FAILED:
            state = {
                ...state,
                events: [],
                error: action.error,
                pending: false
            }
            break;

        default:
            if (action.type.startsWith("event")) {
                console.log("Unmapped reducer action type: ", action.type);
            }
    }
    return state;
}

export default eventReducer;
