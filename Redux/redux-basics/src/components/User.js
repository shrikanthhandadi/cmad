import React from "react";

const user = (props) => {
    return (
        <div>
            <div className="row">
                <div className="col-xs-12">
                    <h5>The user page:</h5>
                </div>
            </div>
            <div className="row">
                <div className="col-xs-12">
                    <h5>User Name: { props.username }</h5>
                </div>
            </div>
        </div>
    );
}

export default user;