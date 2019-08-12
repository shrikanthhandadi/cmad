export function jwtToken() {
    let token = JSON.parse(localStorage.getItem('token'));
    return 'Bearer ' + token;
}