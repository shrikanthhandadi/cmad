import { getJwtToken } from './authHelper';
import ACTION from "./actionTypes";

const APIURL = "http://localhost:8080/ccs";

export function fetchMakes() {
  return function (dispatch) {
    dispatch({
      type: ACTION.CAR_FETCH_MAKES_PENDING
    });
    return fetch(APIURL + '/cars?pageNum=0&pageSize=1000', {
      headers: {
        'Accept': 'application/json',
        'Authorization': getJwtToken()
      },
    })
      .then(response => response.json().then(body => ({ response, body })))
      .then(({ response, body }) => {
        if (!response.ok) {
          dispatch({
            type: ACTION.CAR_FETCH_MAKES_FAILED,
            error: body.error
          });
        } else {
          const allMakes = body.data.map(car => (
            car.make
          ));
          const uniqMakes = Array.from(new Set(allMakes));
          dispatch({
            type: ACTION.CAR_FETCH_MAKES_SUCCESS,
            makes: uniqMakes,
          });
        }
      });
  }

}


export function fetchModels(make) {
  return function (dispatch) {
    dispatch({
      type: ACTION.CAR_FETCH_MODELS_PENDING
    });
    if(make.trim() === ""){
      dispatch({
        type: ACTION.CAR_FETCH_MODELS_SUCCESS,
        models: [],
        make: ''
      });
      return;
    }
    return fetch(APIURL + '/cars?make=' + make + '&pageNum=0&pageSize=1000', {
      headers: {
        'Accept': 'application/json',
        'Authorization': getJwtToken()
      },
    })
      .then(response => response.json().then(body => ({ response, body })))
      .then(({ response, body }) => {
        if (!response.ok) {
          dispatch({
            type: ACTION.CAR_FETCH_MODELS_FAILED,
            error: body.error
          });
        } else {
          const allModels = body.data.map(car => (
            car.model
          ));
          const uniqModels = Array.from(new Set(allModels));
          dispatch({
            type: ACTION.CAR_FETCH_MODELS_SUCCESS,
            models: uniqModels,
            make: make
          });
        }
      });
  }
}


export function setModel(model) {
  return function (dispatch) {
    dispatch({
      type: ACTION.CAR_SET_MODEL,
      model: model
    });
  }
}



