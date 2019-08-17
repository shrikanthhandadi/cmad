import React from 'react';
import User from '../User';
import styled from 'styled-components';

const Wrapper = styled.div`

`;

const Table = styled.table`
   width: 100%;
   
   tbody tr th {
      background-color: black;
      color: white;
      padding: 10px;
   }

   tbody tr td {
      border-bottom: 1px dashed gray;
   }

   tbody tr td {
      padding: 10px;
   }

   tbody tr {
      &:hover {
         cursor: pointer;
         background-color: #DDD;
      }
   }
`;

const status = {
   color: 'gray',
   fontSize: '1.5em'
}

class UserListComponent extends React.Component {

   render() {
      return (
         <Wrapper>
            <div style={ status }>Current User Strength: { this.props.count }</div>
            <Table>
               <tbody>
                  <tr>
                     <th>ID</th><th>Name</th><th>Action</th>
                  </tr>
                  {
                     this.props.users.map(e => <User key={ e.id } data={ e } />)
                  }
               </tbody>
            </Table>
         </Wrapper>
      )
   };

   componentWillMount() {
      this.props.fetchUsers();
   }

}
export default UserListComponent;
