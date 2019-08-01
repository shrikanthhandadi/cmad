import React from 'react';
import { add } from '../actions/ajax';
import { connect } from 'react-redux';

class Operation extends React.Component {
   constructor(props) {
      super(props);
      this.state = {
         first: '',
         second: ''
      }
      this.onFirst = (e) => {
         this.setState({
            first: e.target.value
         });
      }
      this.onSecond = (e) => {
         this.setState({
            second: e.target.value
         });

      }

      this.onAdd = () => {
         this.setState({
            first: '',
            second: ''
         });
         this.props.add({
            first: this.state.first,
            second: this.state.second
         });
      }
   }

   
   render() {
      return (
         <div>
            <table>
               <tbody>
                  <tr>
                     <td>First Number</td>
                     <td><input value={ this.state.first } onChange={ this.onFirst } /></td>
                  </tr>
                  <tr>
                     <td>Second Number</td>
                     <td><input value={ this.state.second } onChange={ this.onSecond } /></td>
                  </tr>

                  <tr><td><button onClick={ this.onAdd }>Add</button></td></tr>
               </tbody>
            </table>
         </div>
      )
   }
}

const mapStateToProps = (state) => {
   return {
   };
};


const mapDispatchToProps = (dispatch) => {
   return {
       add: (params) => dispatch(add(params))
   };
};

export default connect(mapStateToProps, mapDispatchToProps)(Operation);