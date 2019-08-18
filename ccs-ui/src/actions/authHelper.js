export function getJwtToken() {
    let token = JSON.parse(sessionStorage.getItem('token'));
    return 'Bearer ' + token;
}

export function setJwtToken(token) {
    sessionStorage.setItem('token', JSON.stringify(token));
}

export function removeJwtToken() {
    sessionStorage.removeItem('token');
}

export function getLoggedInUser() {
    return JSON.parse(sessionStorage.getItem('user'));;
}

export function setLoggedInUser(user) {
    sessionStorage.setItem('user', JSON.stringify(user));
}

export function removeLoggedInUser(user) {
    sessionStorage.removeItem('user');
}

export function isLoggedIn() {
    let token = JSON.parse(sessionStorage.getItem('token'));
    let loggedInUser = getLoggedInUser() ;
    return token !== null && loggedInUser !== null;
}
