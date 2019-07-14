import React from "react";
import PropTypes from 'prop-types';

export class Home extends React.Component {
    constructor(props){
        super();
        this.state={
            age: props.initialAge,
            status: 0,
            homeLink: "New Home Link"
        }
        setTimeout(()=>{
            this.setState({
                status: status + 1
            })
        },3000);
        console.log("constuction age "+this.state.age)
    }
    onAgeButtonClick(){
       this.setState({
           age: this.state.age - 1
       });
       console.log("on button click age "+this.state.age)
    }
    setNewHomeLink(){
        this.props.updateHomeLink(this.state.homeLink);
    }
    refreshHomeLink(event){
        this.setState({
            homeLink: event.target.value
        });
    }
    render() {
        var text = "Something";
        return (
            <div>
                <p>In a new Component</p>
                <p>Text: {text}</p>
                <p>Name: { this.props.name }</p>
                <p>Age: { this.state.age }</p>
                <p>Status: {this.state.status}</p>
                <hr/>
                <button onClick={()=>this.onAgeButtonClick()} className="btn btn-primary">Make me young!</button>
                <p/>
                <button onClick={this.onAgeButtonClick.bind(this)} className="btn btn-primary">Make me young! by bind</button>
                <hr/>
                <button onClick={this.props.greet} className="btn btn-primary">Do greet</button>
                <hr/>
                <input type="text" value={this.state.homeLink} onChange={(event)=>this.refreshHomeLink(event)} />
                <button onClick={this.setNewHomeLink.bind(this)} className="btn btn-primary">Update home link</button>
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
    name: PropTypes.string,
    initialAge: PropTypes.number,
    user: PropTypes.object,
    children: PropTypes.element.isRequired,
   // greet: PropTypes.func
    updateHomeLink: PropTypes.func
}