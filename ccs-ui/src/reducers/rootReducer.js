import { combineReducers } from 'redux';

import userReducer from './userReducer';
import carReducer from './carReducer';
import eventReducer from './eventReducer';

export default combineReducers({
    userReducer: userReducer,
    carReducer: carReducer,
    eventReducer: eventReducer
});
