import React from 'react';
import { addUser } from '../../actions/userActions';
import { connect } from 'react-redux';
import UserFormComponent from './UserFormComponent';

class UserFormContainer extends React.Component {
   constructor(props) {
      super(props);
      this.state = {
         username: '',
         password: '',
         roles: [],
         makes: []
      }
      this.onAdd = () => {
         this.setState({
            username: '',
            password: '',
            roles: [],
            makes: []
         });
         this.props.addUser({
            username: this.state.username,
            password: this.state.password,
            roles: this.state.roles,
            makes: this.state.makes,
         });
         this.props.history.push('/list');
      }
   }

   render() {
      return (
         <UserFormComponent
            onAdd={ this.onAdd }
            onUsername={ e => this.setState({ username: e.target.value }) }
            onPassword={ e => this.setState({ password: e.target.value }) }
            onRoles={ e => this.setState({ roles:  [...e.target.options].filter(o => o.selected).map(o => o.value) }) }
            onMakes={ e => this.setState({ makes:  [...e.target.options].filter(o => o.selected).map(o => o.value) }) }
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
      addUser: (user) => dispatch(addUser(user))
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserFormContainer);
