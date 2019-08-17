import React from 'react';
import { connect } from 'react-redux'
import AppComponent from './AppComponent'

import { isLoggedIn, getLoggedInUser } from '../../actions/authHelper';

class AppContatiner extends React.Component {

  componentWillMount() {
    var loggedIn = isLoggedIn();
    var loggedInUser = getLoggedInUser();
    this.setState({
      loggedIn: loggedIn,
      loggedInUser: loggedInUser
    });
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.loggedIn !== nextProps.loggedIn || (this.props.loggedInUser !== undefined && (this.props.loggedInUser.username !== nextProps.loggedInUser.username))) {
      this.setState({
        loggedIn: nextProps.loggedIn,
        loggedInUser: nextProps.loggedInUser
      });
    }
  }

  render() {
    return (
      <AppComponent
        loggedIn={ this.state.loggedIn }
        loggedInUser={ this.state.loggedInUser }
      />
    );
  }

}

const mapStatetoProps = state => {
  return {
    loggedIn: state.authReducer.loggedIn,
    loggedInUser: state.authReducer.loggedInUser
  }
}

const mapDispatchToProps = dispatch => {
  return {}
}

export default connect(mapStatetoProps, mapDispatchToProps)(AppContatiner);
