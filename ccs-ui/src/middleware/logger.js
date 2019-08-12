import logger from "redux-logger";

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

export default logger;
