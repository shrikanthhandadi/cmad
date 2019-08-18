import { removeUser } from '../../actions/userActions';
import UserComponent from './UserComponent';
import { connect } from 'react-redux';
import PropTypes from 'prop-types'; 

UserComponent.propTypes = {
   data: PropTypes.shape({
      id: PropTypes.number.isRequired,
      username: PropTypes.string.isRequired,
      password: PropTypes.string.isRequired,
      roles: PropTypes.array.isRequired,
      makes: PropTypes.array.isRequired,
      removeUser: PropTypes.func.isRequired
   })
}

const mapStateToProps = (state) => {
   return {
   };
};

const mapDispatchToProps = (dispatch) => {
   return {
      removeUser: (id) => dispatch(removeUser(id))
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserComponent);