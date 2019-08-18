import React from 'react';
import User from '../User';

class UserListComponent extends React.Component {

   render() {
      return (
         <div className="container">
            <div className="row" style={ { paddingBottom: '10px' } } >
               <div className="col-md-10 col-lg-10"><h4 style={{color: '#337ab7'}}>Users List</h4></div>
            </div>
            <div className="row">
               <div className="col-md-10 col-lg-10">
                  <table className="table table-striped table-hover">
                     <tbody className="thead-dark">
                        <tr>
                           <th>ID</th><th>Name</th><th>Roles</th><th>Makes</th><th>Action</th>
                        </tr>
                        {
                           this.props.users.map(e => <User key={ e.id } data={ e } />)
                        }
                     </tbody>
                  </table>
               </div>
            </div>
         </div>
      )
   };

   componentWillMount() {
      this.props.fetchUsers();
   }

}
export default UserListComponent;
