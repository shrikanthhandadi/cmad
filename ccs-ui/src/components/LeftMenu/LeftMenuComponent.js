import React from "react";

export default class LeftMenu extends React.Component {

   render() {
      return (
         <div>
            <ul className="nav nav-pills nav-stacked">
               <li ><a href='/#/login'>Login</a></li>
               <li>Admin</li>
               <li>
                  <ul className="nav nav-pills nav-stacked">
                     <li ><a href='/#/form'>Add New User</a></li>
                     <li ><a href='/#/list'>List Users</a></li>
                  </ul>
               </li>
            </ul>
            <ul className="nav nav-pills nav-stacked">
               <li>User</li>
               <li>
                  <ul className="nav nav-pills nav-stacked">
                     <li><a href='/#/console'>Console</a></li>
                  </ul>
               </li>
            </ul>
         </div>
      );
   }
}
