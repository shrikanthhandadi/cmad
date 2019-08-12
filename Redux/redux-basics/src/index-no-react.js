// import React from 'react';
// import ReactDOM from 'react-dom';
// import './index.css';
// import App from './App';
// import * as serviceWorker from './serviceWorker';
// import { Provider } from "react-redux";

//Redux imports
import { createStore, combineReducers, applyMiddleware } from "redux";
import logger from "redux-logger";


// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
// serviceWorker.unregister();

const mathReducer = (state = {
    result: 1,
    lastValues: []
}, action) => {
    switch (action.type) {
        case "ADD":
            state = {
                ...state,
                result: state.result + action.payload,
                lastValues: [...state.lastValues, action.payload]
            }
            break;
        case "SUBSTRACT":
            state = {
                ...state,
                result: state.result - action.payload,
                lastValues: [...state.lastValues, action.payload]
            }
            break;
        default:
            //console.log("Unknown action type ", action.type);
            break;
    }
    return state;
};

const userReducer = (state = {
    name: "Shrikanth",
    age: 22
}, action) => {
    switch (action.type) {
        case "SET_NAME":
            state = {
                ...state,
                name: action.payload
            }
            break;
        case "SET_AGE":
            state = {
                ...state,
                age: action.payload
            }
            break;
        default:
            // console.log("unknown type ", action.type);
            break;
    }
    return state;
}

const doLogging = function (store) {
    return function (next) {
        return function (action) {
            console.log("Logged action : ", action);
            next(action);
        };
    };
};

const myLogger = (store) => (next) => (action) => {
    console.log("Logged action fat arrow: ", action);
    next(action);
}

const store = createStore(
    combineReducers({ math: mathReducer, user: userReducer }),
    {},
    applyMiddleware(doLogging, myLogger, logger)
);

store.subscribe(() => {
    // console.log("Store updated", store.getState());
});

store.dispatch({
    type: "ADD",
    payload: 100
})

store.dispatch({
    type: "ADD",
    payload: 22
})

store.dispatch({
    type: "SUBSTRACT",
    payload: 80
})

store.dispatch({
    type: "SET_AGE",
    payload: 33
})

store.dispatch({
    type: "SET_NAME",
    payload: "Name dispatched from store.dispatch"
})

// ReactDOM.render(
//     <Provider store={ store }>
//         <App />
//     </Provider>,
//     document.getElementById('root')
// );

