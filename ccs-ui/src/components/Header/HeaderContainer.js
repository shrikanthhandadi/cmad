import React from 'react';
import { connect } from 'react-redux';

import HeaderComponent from './HeaderComponent';

const HeaderContainer = (props) => {
   return (
      <HeaderComponent
         loggedIn={ props.loggedIn }
         loggedInUser={ props.loggedInUser }
      />
   )
}

const mapStateToProps = (state) => {
   return {
   };
};

const mapDispatchToProps = (dispatch) => {
   return {
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeaderContainer);
