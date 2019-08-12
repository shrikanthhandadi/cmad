import React from "react";
import PropTypes from 'prop-types';

export class Login extends React.Component {
    constructor(props) {
        super();
        this.state = {
            token: '',
            makes: [],
            models: [],
            selectedMake: ''
        }
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-3"><h3>Login</h3></div>
                </div>
                <div className="row">
                    <div className="col-md-10 col-lg-10">
                        <form onSubmit={ this.handleSignIn.bind(this) }>
                            Username: <input type="text" ref="username" placeholder="enter you username" />
                            Password: <input type="password" ref="password" placeholder="enter password" />
                            <input type="submit" value="Login" />
                        </form>
                    </div>
                </div>

            </div>
        );
    }

    handleSignIn(e) {
        e.preventDefault()
        let username = this.refs.username.value
        let password = this.refs.password.value
        fetch('http://localhost:9090/ccs/users/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: this.refs.username.value,
                password: this.refs.password.value,
            })
        })
            .then(res => res.json())
            .then((data) => {
                const token = data.token;

                this.setState({
                    token: token
                });
                this.publishToken();
            })

            .catch(console.log)
    }

    publishToken() {
        console.log("Source.. ");
        this.props.updateToken(this.state.token);
    }
}

Login.propTypes = {
    updateToken: PropTypes.func
}
