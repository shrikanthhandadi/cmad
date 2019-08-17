import React from 'react';
import { connect } from 'react-redux';

import { logout } from '../../actions/authActions';
import LeftMenuComponent from './LeftMenuComponent';

class LeftMenuContainer extends React.Component {

   render() {
      return (
         <LeftMenuComponent
            loggedIn={ this.props.loggedIn }
            loggedInUser={ this.props.loggedInUser }
            logout={ this.props.logout }
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
      logout: () => dispatch(logout())
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(LeftMenuContainer);
