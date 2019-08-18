import React from "react";

const LeftMenuComponent = (props) => {

   return (
      <div> 
         <ul className="nav nav-pills nav-stacked" hidden={ props.loggedIn }>
            <li><a href='/#/login'>Login</a></li>
         </ul>
         <ul className="nav nav-pills nav-stacked" hidden={ !props.loggedIn || (props.loggedInUser.roles.indexOf('ROLE_ADMIN') === -1) }>
            <li>Admin</li>
            <li>
               <ul className="nav nav-pills nav-stacked">
                  <li ><a href='/#/form'>Add New User</a></li>
                  <li ><a href='/#/list'>List Users</a></li>
               </ul>
            </li>
         </ul>
         <ul className="nav nav-pills nav-stacked" hidden={ !props.loggedIn || (props.loggedInUser.roles.indexOf('ROLE_USER') === -1) }>
            <li>User</li>
            <li>
               <ul className="nav nav-pills nav-stacked">
                  <li><a href='/#/console'>Event Console</a></li>
               </ul>
            </li>
         </ul>
         <ul className="nav nav-pills nav-stacked" hidden={ !props.loggedIn }>
            <li><a href='/#/login' onClick={ props.logout }>Logout</a></li>
         </ul>
      </div>
   );

}

export default LeftMenuComponent;
