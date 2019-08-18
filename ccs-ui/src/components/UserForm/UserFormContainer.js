import React from 'react';
import { addUser } from '../../actions/userActions';
import { fetchMakes } from '../../actions/carActions';
import { connect } from 'react-redux';
import UserFormComponent from './UserFormComponent';

class UserFormContainer extends React.Component {
   constructor(props) {
      super(props);
      this.state = {
         username: '',
         password: '',
         roles: [],
         makeOptions: []
      }
      this.onAdd = () => {
         this.setState({
            username: '',
            password: '',
            roles: [],
            makeOptions: []
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

   componentWillMount() {
      this.props.fetchMakes();
   }

   componentWillReceiveProps(nextProps) {
      let makeOptions = nextProps.makes.map(make => { return { value: make, display: make } });
      this.setState({
         makeOptions: makeOptions
      });
   }

   render() {
      return (
         <UserFormComponent
            makeOptions={ this.state.makeOptions }
            onAdd={ this.onAdd }
            onUsername={ e => this.setState({ username: e.target.value }) }
            onPassword={ e => this.setState({ password: e.target.value }) }
            onRoles={ e => this.setState({ roles: [...e.target.options].filter(o => o.selected).map(o => o.value) }) }
            onMakes={ e => this.setState({ makes: [...e.target.options].filter(o => o.selected).map(o => o.value) }) }
         />
      )
   }

}

const mapStateToProps = (state) => {
   return {
      makes: state.carReducer.makes
   };
};


const mapDispatchToProps = (dispatch) => {
   return {
      addUser: (user) => dispatch(addUser(user)),
      fetchMakes: () => dispatch(fetchMakes())
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserFormContainer);
