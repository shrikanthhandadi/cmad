"use strict"
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import * as serviceWorker from './serviceWorker';

import { fetchUsers } from './actions/userActions';
import store from './store/store';
import Admin from './components/Admin';


//store.dispatch(fetchUsers());

ReactDOM.render(
    <Provider store={ store }>
        <Admin store={ store } />
    </Provider>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();


