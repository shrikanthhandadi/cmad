import React from 'react';

const UserFormComponent = (props) => (
   <div className="container">
      <div className="row" style={ { paddingBottom: '20px' } } >
         <div className="col-md-10 col-lg-10">
            <h4 style={ { color: '#337ab7' } }>Add New User</h4>
         </div>
      </div>
      <div className="row">
         <div className="col-md-10 col-lg-10" >
            <form>
               <div className="form-group row">
                  <label className="col-sm-2 col-form-label">Username</label>
                  <div className="col-sm-10">
                     <input type="text" className="form-control-plaintext" onChange={ props.onUsername } placeholder='Username' />
                  </div>
               </div>
               <div className="form-group row">
                  <label className="col-sm-2 col-form-label">Password</label>
                  <div className="col-sm-10">
                     <input type="text" className="form-control-plaintext" onChange={ props.onPassword } placeholder='Password' />
                  </div>
               </div>
               <div className="form-group row">
                  <label className="col-sm-2 col-form-label">Roles</label>
                  <div className="col-sm-10">
                     <select multiple className="form-control" value={ props.roles } onChange={ props.onRoles } >
                        <option value="ROLE_ADMIN">ADMIN</option>
                        <option value="ROLE_USER">USER</option>
                     </select>
                  </div>
               </div>

               <div className="form-group row">
                  <label className="col-sm-2 col-form-label">Makes</label>
                  <div className="col-sm-10">
                     <select multiple className="form-control" value={ props.makes } onChange={ props.onMakes } >
                        { props.makeOptions.map((makeOption) => <option key={ makeOption.value } value={ makeOption.value }>{ makeOption.display }</option>) }
                     </select>
                  </div>
               </div>
               <div className="form-group row">
                  <div className="col-sm-10">
                     <button onClick={ props.onAdd } className="btn btn-primary">Add User</button>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </div>
);
export default UserFormComponent;
