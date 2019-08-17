import React from "react";
import './../../App.css';

export default class HeaderComponenet extends React.Component {

    render() {
        return (
            <div className="row">
                <div>
                    <h1>Connected Car Service Event Console</h1>
                </div>
                <div>
                    { this.props.loggedIn &&
                        <span>Welcome { this.props.loggedInUser.username }</span>
                    }
                </div>
            </div>
        );
    }
}

