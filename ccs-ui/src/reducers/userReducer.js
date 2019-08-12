import ACTION from '../actions/actionTypes';
import { stat } from 'fs';

const INITIAL_STATE = {
    users: [],
    pending: false,
    error: undefined
};

function userReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case ACTION.USER_FETCHALL_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.USER_FETCHALL_SUCCESS:

            state = {
                ...state,
                users: [...action.users],
                pending: false,
                error: undefined
            }
            console.log("USER_FETCHALL_SUCCESS", state);
            break;
        case ACTION.USER_FETCHALL_FAILED:
            state = {
                ...state,
                error: action.error,
                pending: false
            }
            break;


        case ACTION.USER_ADD_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.USER_ADD_SUCCESS:
            state = {
                ...state,
                users: [...state.users, action.user],
                pending: false,
                error: undefined
            }
            break;
        case ACTION.USER_ADD_FAILED:
            state = {
                ...state,
                error: action.error,
                pending: false
            }
            break;


        case ACTION.USER_REMOVE_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.USER_REMOVE_SUCCESS:
            let filtered = state.users.filter(e => e.id != action.id);
            state = {
                ...state,
                users: [...filtered],
                pending: false,
                error: undefined
            }
            break;
        case ACTION.USER_REMOVE_FAILED:
            state = {
                ...state,
                error: action.error,
                pending: false
            }
            break;


        case ACTION.USER_LOGIN_PENDING:
            state = {
                ...state,
                pending: true,
                error: undefined
            }
            break;
        case ACTION.USER_LOGIN_SUCCESS:
            state = {
                ...state,
                pending: false,
                error: undefined
            }
            break;
        case ACTION.USER_LOGIN_FAILED:
            state = {
                ...state,
                error: action.error,
                pending: false,
            }
            break;


        default:
            console.log("Unmapped reducer action: ", action.type);
            break;
    }
    return state;
}

export default userReducer;
