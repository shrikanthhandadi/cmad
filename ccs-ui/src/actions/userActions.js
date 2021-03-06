import { getJwtToken } from './authHelper';
import ACTION from "./actionTypes";

const APIURL = "http://localhost:8080/ccs";

export function setName(name) {
  return (dispatch) => {
    setTimeout(() => {
      dispatch({
        type: "SET_NAME",
        payload: name
      })
    }, 2000)
  }
  // return{
  //     type:"SET_NAME",
  //     payload: new Promise((resolve,reject)=>{
  //         setTimeout(()=>{
  //             resolve(name)
  //         },2000)
  //     })
  // }
}

export function fetchUsers() {
  // Instead of plain objects, we are returning function.
  return function (dispatch) {
    // Dispatching REQUEST action, which tells our app, that we are started requesting todos.
    dispatch({
      type: ACTION.USER_FETCHALL_PENDING
    });
    return fetch(APIURL + '/users?pageNum=0&pageSize=1000', {
      headers: {
        'Accept': 'application/json',
        'Authorization': getJwtToken()
      },
    })
      // Here, we are getting json body(in our case it will contain `todos` or `error` prop, depending on request was failed or not) from server response
      // And providing `response` and `body` variables to the next chain.
      .then(response => response.json().then(body => ({ response, body })))
      .then(({ response, body }) => {
        if (!response.ok) {
          // If request was failed, dispatching FAILURE action.
          dispatch({
            type: ACTION.USER_FETCHALL_FAILED,
            error: body.error
          });
        } else {
          // When everything is ok, dispatching SUCCESS action.
          dispatch({
            type: ACTION.USER_FETCHALL_SUCCESS,
            users: body.data
          });
        }
      });
  }
}

export function addUser(user) {
  // Instead of plain objects, we are returning function.
  return function (dispatch) {
    // Dispatching REQUEST action, which tells our app, that we are started requesting todos.
    dispatch({
      type: ACTION.USER_ADD_PENDING
    });
    return fetch(APIURL + '/users', {
      method: "post",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getJwtToken()
      },

      //make sure to serialize your JSON body
      body: JSON.stringify(user)
    })
      // Here, we are getting json body(in our case it will contain `todos` or `error` prop, depending on request was failed or not) from server response
      // And providing `response` and `body` variables to the next chain.
      .then(response => response.json().then(body => ({ response, body })))
      .then(({ response, body }) => {
        if (!response.ok) {
          // If request was failed, dispatching FAILURE action.
          dispatch({
            type: ACTION.USER_ADD_FAILED,
            error: body.error
          });
        } else {
          // When everything is ok, dispatching SUCCESS action.
          user.id = body
          dispatch({
            type: ACTION.USER_ADD_SUCCESS,
            user: user
          });
        }
      });
  }
}

export function removeUser(id) {
  // Instead of plain objects, we are returning function.
  return function (dispatch) {
    // Dispatching REQUEST action, which tells our app, that we are started requesting todos.
    if (id === 1) {
      dispatch({
        type: ACTION.USER_REMOVE_FAILED,
        error: 'Cannot delete uder id 1'
      });
      return;
    }
    dispatch({
      type: ACTION.USER_REMOVE_PENDING
    });
    return fetch(APIURL + '/users/' + id, {
      method: "delete",
      headers: {
        'Authorization': getJwtToken()
      }
    })
      // Here, we are getting json body(in our case it will contain `todos` or `error` prop, depending on request was failed or not) from server response
      // And providing `response` and `body` variables to the next chain.
      .then(response => response.json().then(body => ({ response, body })))
      .then(({ response, body }) => {
        if (!response.ok) {
          // If request was failed, dispatching FAILURE action.
          dispatch({
            type: ACTION.USER_REMOVE_FAILED,
            error: body.error
          });
        } else {
          // When everything is ok, dispatching SUCCESS action.
          dispatch({
            type: ACTION.USER_REMOVE_SUCCESS,
            id: id
          });
        }
      });
  }
}


