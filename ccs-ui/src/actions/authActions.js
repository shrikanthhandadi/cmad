import ACTION from "./actionTypes";

const APIURL = "http://localhost:8080/ccs";

export function login(user) {
    return function (dispatch) {

        return fetch(APIURL + '/users/login', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: user.username,
                password: user.password
            })
        })
            .then(response => response.json().then(body => ({ response, body })))
            .then(({ response, body }) => {
                if (!response.ok) {
                    // If request was failed, dispatching FAILURE action.
                    dispatch({
                        type: ACTION.AUTH_LOGIN_FAILED,
                        error: body.message
                    });
                } else {
                    var token = body.token;
                    return fetch(APIURL + '/users/username/' + user.username, {
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    })
                        .then(response => response.json().then(body => ({ response, body })))
                        .then(({ response, body }) => {
                            if (!response.ok) {
                                dispatch({
                                    type: ACTION.AUTH_LOGIN_FAILED,
                                    error: body.message
                                });
                            } else {
                                dispatch({
                                    type: ACTION.AUTH_LOGIN_SUCCESS,
                                    user: body,
                                    token: token
                                });
                            }
                        });
                }
            });
    }
}


export function logout() {
    return function (dispatch) {
        dispatch({
            type: ACTION.AUTH_LOGOUT_SUCCESS
        });
    }
}