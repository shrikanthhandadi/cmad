import React from "react";

const main = (props) => {
    return (
        <div>
            <div className="row">
                <div className="col-xs-12">
                    <h5>Main page</h5>
                </div>
            </div>
            <div className="row">
                <div className="col-xs-12">
                    <button className="btn btn-primary" onClick={ () => props.changeUsername('Anna') }>Change the username</button>
                </div>
            </div>
        </div>
    );
}

export default main;