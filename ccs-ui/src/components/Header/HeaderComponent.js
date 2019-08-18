import React from "react";
import './../../App.css';

export default class HeaderComponenet extends React.Component {

    render() {
        return (
            <div className="container">
                <div>
                    <div className="col col-md-12 col-lg-12 text-right" >
                        { this.props.loggedIn &&
                            <span style={ { paddingRight: '50px' } }>Welcome <b><i>{ this.props.loggedInUser.username }</i></b></span>
                        }
                    </div>
                </div>
                <div className="row text-center">
                    <div className="col col-md-12 col-lg-12">
                        <h1 style={ { color: '#337ab7' } }>Connected Car Service</h1>
                    </div>
                </div>
            </div>
        );
    }
}

