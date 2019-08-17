import React from 'react';
import { login } from '../../actions/authActions';
import { connect } from 'react-redux';
import UserLoginComponent from './UserLoginComponent';

class UserLoginContainer extends React.Component {
   constructor(props) {
      super(props);
      this.state = {
         username: '',
         password: ''
      }
      this.login = () => {
         this.setState({
            username: '',
            password: ''
         });
         this.props.login({
            username: this.state.username,
            password: this.state.password
         });
         this.props.history.push('/');
      }
   }

   render() {
      return (
         <UserLoginComponent
            onUsername={ e => this.setState({ username: e.target.value }) }
            onPassword={ e => this.setState({ password: e.target.value }) }
            onLogin={ this.login }
         />
      )
   }
}

const mapStateToProps = (state) => {
   return {
   };
};


const mapDispatchToProps = (dispatch) => {
   return {
      login: (user) => dispatch(login(user))
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserLoginContainer);
