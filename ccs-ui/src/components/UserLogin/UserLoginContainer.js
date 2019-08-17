import React from 'react';
import { login, loadUser } from '../../actions/userActions';
import { isAdmin } from '../../actions/authHelper';
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
         console.log('UserLoginContainer username ', this.state.username);
         this.props.loadUser(this.state.username);
         console.log('UserLoginContainer user ', localStorage.getItem('user'));
         if (isAdmin()) {
            this.props.history.push('/list');
         } else {
            this.props.history.push('/console');
         }
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
      login: (user) => dispatch(login(user)),
      loadUser: (username) => dispatch(loadUser(username))
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserLoginContainer);
