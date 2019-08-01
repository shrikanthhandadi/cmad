import { createStore, combineReducers, applyMiddleware } from 'redux';
import validator from '../middleware/validator';

import sumReducer from './sumReducer';

let store = createStore(combineReducers({
    sumR: sumReducer
}), applyMiddleware(validator));

export default store;