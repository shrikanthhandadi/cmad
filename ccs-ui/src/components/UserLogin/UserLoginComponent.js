import React from 'react';

const UserLoginComponent = (props) => (
   <div className="container">
      <div className="row">
         <div className="col-md-9 col-lg-9"><h4 style={ { color: '#337ab7' } }>User Login</h4></div>
      </div>
      <div className="row">
         <div className="col-md-10 col-lg-10 " >
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
                     <input type='password' className="form-control-password" onChange={ props.onPassword } placeholder='Password' />
                  </div>
               </div>
               <div className="form-group row">
                  <div className="col-sm-10">
                     <button onClick={ props.onLogin } className="btn btn-primary">Login</button>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </div>
);
export default UserLoginComponent;
