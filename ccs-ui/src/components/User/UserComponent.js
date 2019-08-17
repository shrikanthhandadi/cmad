import React from 'react';

const UserComponent = (props) => (
   <tr key={props.data.id}>
      <td>{props.data.id}</td>
      <td>{props.data.username}</td>
      <td>{props.data.roles.join(', ')}</td>
      <td>{props.data.makes.join(', ')}</td>
      <td><button onClick={()=>props.removeUser(props.data.id)}>Remove</button></td>
   </tr>
);

export default UserComponent;