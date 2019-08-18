import React, { Fragment } from 'react';

const UserComponent = (props) => (
   <Fragment key={ props.data.id }>
      <tr key={ props.data.id }>
         <td>{ props.data.id }</td>
         <td>{ props.data.username }</td>
         <td>{ props.data.roles && props.data.roles.join(', ') }</td>
         <td>{ props.data.makes && props.data.makes.join(', ') }</td>
         <td><button onClick={ () => props.removeUser(props.data.id) }>Remove</button></td>
      </tr>
   </Fragment>
);

export default UserComponent;