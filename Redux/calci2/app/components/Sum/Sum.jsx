import React from 'react';
import { connect } from 'react-redux';

class Sum extends React.Component {

   render() {
      return (
         <div>
            Sum: {this.props.sum}
         </div>
      )
   }
}

const mapStateToProps = (state) => {
   return {
      sum: state.sumR.sum
   };
};

const mapDispatchToProps = (dispatch) => {
   return { 
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(Sum);