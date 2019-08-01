import React from 'react';
import Operation from './Operation.jsx';
import Sum from './Sum.jsx';

class Calculator extends React.Component {
   render() {
      return (
         <div>
            <Operation />
            <hr />
            <Sum />
         </div>
      )
   }
}

export default Calculator;