import { createStore, applyMiddleware } from "redux";
import thunk from "redux-thunk";

import rootReducer from "../reducers/rootReducer"
import logger from "../middleware/logger";

export default createStore(
    rootReducer,
    {},
    applyMiddleware(logger, thunk)
);
