import ACTION from '../actions/actionTypes';
import { setJwtToken, removeJwtToken, setLoggedInUser,removeLoggedInUser } from '../actions/authHelper';

const INITIAL_STATE = {
    loggedIn: false,
    loggedInUser: undefined,
    error: undefined
};

function authReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case ACTION.AUTH_LOGIN_SUCCESS:
            state = {
                ...state,
                loggedIn: true,
                loggedInUser: action.user,
                error: undefined
            }
            setJwtToken(action.token);
            setLoggedInUser(action.user);
            break;

        case ACTION.AUTH_LOGIN_FAILED:
            state = {
                ...state,
                loggedIn: false,
                loggedInUser: undefined,
                error: action.error
            }
            removeJwtToken();
            removeLoggedInUser();
            break;

        case ACTION.AUTH_LOGOUT_SUCCESS:
            state = {
                ...state,
                loggedIn: false,
                loggedInUser: undefined,
                error: action.error
            }
            removeJwtToken();
            removeLoggedInUser();
            break;

        default:
            if (action.type.startsWith("auth")) {//filtering since called for every dispatch
                console.log("Unmapped reducer action type: ", action.type);
            }
    }
    return state;
}

export default authReducer;

