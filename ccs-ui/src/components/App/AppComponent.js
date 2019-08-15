import React from 'react';
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import UserForm from '../UserForm';
import UserList from '../UserList';
import UserLogin from '../UserLogin';
import Console from '../Console';
import Header from "./../Header";
import LeftMenu from "./../LeftMenu";

const AppComponent = () => (
   <div className="container  border border-dark" >
      <div className="row">
         <div className="col-md-12 col-lg-12 col-md-offset-4">
            <Header />
         </div>
      </div>
      <div className="row">
         <div className="col-md-2 col-lg-2">
            <LeftMenu />
         </div>
         <div className="col-md-10 col-lg-10">

            <div className="row">
               <div className="col-md-10 col-lg-10 " >
                  <Router>
                     <Switch>
                        <Route path='/login' component={ UserLogin } />
                        <Route path='/form' component={ UserForm } />
                        <Route path='/list' component={ UserList } />
                        <Route path='/console' component={ Console } />
                     </Switch>
                  </Router>
               </div>
            </div>
         </div>
      </div>
   </div>

);

export default AppComponent;
