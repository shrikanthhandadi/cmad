import { getJwtToken } from './authHelper';
import ACTION from "./actionTypes";

const APIURL = "http://localhost:8080/ccs";

export function getStats(make, model) {
  if (make === undefined) {
    make = '';
  }
  if (model === undefined) {
    model = '';
  }
  return function (dispatch) {
    dispatch({
      type: ACTION.EVENT_STATS_PENDING
    });
    return fetch(APIURL + '/stats?make=' + make + '&model=' + model, {
      headers: {
        'Accept': 'application/json',
        'Authorization': getJwtToken()
      },
    })
      .then(response => response.json().then(body => ({ response, body })))
      .then(({ response, body }) => {
        if (!response.ok) {
          dispatch({
            type: ACTION.EVENT_STATS_FAILED,
            error: body.error
          });
        } else {
          const eventStats = body.map(stat => ([
            stat.severity,
            stat.count
          ]));
          eventStats.unshift(['Task', 'Event stats']);//google-charts requisite

          dispatch({
            type: ACTION.EVENT_STATS_SUCCESS,
            eventStats: eventStats,
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

export function loadEvents(make, model, pageSize, pageNumber) {
  if (make === undefined) {
    make = '';
  }
  if (model === undefined) {
    model = '';
  }
  return function (dispatch) {
    dispatch({
      type: ACTION.EVENT_FETCHALL_PENDING
    });
    return fetch(APIURL + '/events?make=' + make + '&model=' + model + '&pageSize=' + pageSize + '&pageNum=' + pageNumber, {
      headers: {
        'Accept': 'application/json',
        'Authorization': getJwtToken()
      },
    })
      .then(response => response.json().then(body => ({ response, body })))
      .then(({ response, body }) => {
        if (!response.ok) {
          dispatch({
            type: ACTION.EVENT_FETCHALL_FAILED,
            error: body.error
          });
        } else {
          const nextEvents = body.data.map(event => ({
            id: event.id,
            carId: event.carId,
            make: event.make,
            model: event.model,
            date: event.date,
            severity: event.severity,
            data: event.data,
          }));

          dispatch({
            type: ACTION.EVENT_FETCHALL_SUCCESS,
            nextEvents: nextEvents,
            total: body.totalElements,
            pageSize: pageSize,
            pageNumber: pageNumber,
          });
        }
      });
  }
}

