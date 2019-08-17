import ACTION from '../actions/actionTypes';

const INITIAL_STATE = {
    makes: [],
    models: [],
    make: undefined,
    model: undefined,
    pending: false,
    error: undefined
};

function carReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case ACTION.CAR_FETCH_MAKES_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.CAR_FETCH_MAKES_SUCCESS:

            state = {
                ...state,
                makes: [...action.makes],
                models: [],
                make: undefined,
                model: undefined,
                pending: false,
                error: undefined
            }
            break;
        case ACTION.CAR_FETCH_MAKES_FAILED:
            state = {
                ...state,
                error: action.error,
                pending: false
            }
            break;


        case ACTION.CAR_FETCH_MODELS_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.CAR_FETCH_MODELS_SUCCESS:

            state = {
                ...state,
                models: [...action.models],
                make: action.make,
                model: undefined,
                pending: false,
                error: undefined
            }
            break;
        case ACTION.CAR_FETCH_MODELS_FAILED:
            state = {
                ...state,
                error: action.error,
                pending: false
            }
            break;

        case ACTION.CAR_SET_MODEL:
            state = {
                ...state,
                model: action.model,
                pending: false,
                error: undefined
            }
            break;

        default:
            if (action.type.startsWith("user")) {
                console.log("Unmapped reducer action type: ", action.type);
            }
    }
    return state;
}

export default carReducer;
