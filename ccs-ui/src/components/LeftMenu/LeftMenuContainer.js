import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import { logout } from '../../actions/authActions';
import LeftMenuComponent from './LeftMenuComponent';

LeftMenuComponent.propTypes = {
   data: PropTypes.shape({
      loggedIn: PropTypes.bool.isRequired,
      loggedInUser: PropTypes.object.isRequired,
      logout: PropTypes.func.isRequired
   })
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

export default connect(mapStateToProps, mapDispatchToProps)(LeftMenuComponent);
