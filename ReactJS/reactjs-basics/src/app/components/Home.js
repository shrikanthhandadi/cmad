import React from "react";

export class Home extends React.Component {
    render() {
        var text = "Something";
        return (
            <div>
                <p>In a new Component</p>
                <p>Text: {text}</p>
                <p>Name: { this.props.name }</p>
                <p>Age: { this.props.age }</p>
                <div>
                    <h4>hobbies</h4>
                    <ul>
                        { this.props.user.hobbies.map((hobby,i) => <li key={i}>{ hobby }</li>) }
                    </ul>
                </div>
                {this.props.children}
            </div>
        );
    }
}

Home.propTypes = {
    name: React.PropTypes.string,
    age: React.PropTypes.number,
    user: React.PropTypes.object,
    children: React.PropTypes.element.isRequired
}