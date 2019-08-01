"use strict"
import React from 'react';
import ReactDOM from 'react-dom';
import Calculator from './components/Calculator.jsx';
import { Provider } from 'react-redux';
import store from './store/store';



ReactDOM.render(

    <Provider store={ store }>
        <Calculator />
    </Provider>
    ,


    document.getElementById('dir'));
