export function jwtToken() {
    let token = JSON.parse(localStorage.getItem('token'));
    return 'Bearer ' + token;
}

export function isAdmin() {
    console.log('auth helper is admin ', user);

    var user =  JSON.parse(localStorage.getItem('user'));
    console.log('auth helper  roles ',user.roles);
    return (user.roles.indexOf('ROLE_ADMIN') > -1);
}