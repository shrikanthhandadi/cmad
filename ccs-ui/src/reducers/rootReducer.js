import { combineReducers } from 'redux';

import userReducer from './userReducer';
import authReducer from './authReducer';
import carReducer from './carReducer';
import eventReducer from './eventReducer';

export default combineReducers({
    userReducer: userReducer,
    authReducer: authReducer,
    carReducer: carReducer,
    eventReducer: eventReducer
});
