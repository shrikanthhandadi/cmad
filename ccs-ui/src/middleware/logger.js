import logger from "redux-logger";

export function doLogging(store) {
    return function (next) {
        return function (action) {
            console.log("Logged action : ", action);
            next(action);
        };
    };
};

export const myLogger = (store) => (next) => (action) => {
    console.log("Logged action fat arrow: ", action);
    next(action);
}

export default logger;