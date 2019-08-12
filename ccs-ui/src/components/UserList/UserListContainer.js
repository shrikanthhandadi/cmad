import UserListComponent from './UserListComponent';
import { fetchUsers } from '../../actions/userActions';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

UserListComponent.propTypes = {
   counter: PropTypes.number.isRequired,
   users: PropTypes.array.isRequired,
}

UserListComponent.defaultProps = {
   counter: 0,
   users: []
}

const mapStateToProps = (state) => {
   return {
      count: state.userReducer.count,
      users: state.userReducer.users
   };
};

const mapDispatchToProps = (dispatch) => {
   return {
      fetchUsers: () => dispatch(fetchUsers())
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserListComponent);
