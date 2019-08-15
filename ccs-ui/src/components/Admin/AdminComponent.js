import React from 'react';
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import UserForm from '../UserForm';
import UserList from '../UserList';
import UserLogin from '../UserLogin';
import Console from '../Console';
import styled from 'styled-components';

const Banner = styled.div`
   position: fixed;
   left: 0;
   top: 0;
   width: 100%;
   padding-left: 40px;
   padding-top: 10px;
   padding-bottom: 10px;
   background-color: white;
   border-bottom: gray solid 1px;
   box-shadow: 1px 1px 2px #DDD;
`

const Logo = styled.div`
   font-size: 3em;
   color: #0BB;
`;

const Menu = styled.div`

`;

const Buffer = styled.div`
   height: 60px;
`
const AdminWrapper = styled.div`
   margin: 40px;
`;

const AdminComponent = () => (
   <Router>
      <AdminWrapper>
         <Banner>
            <Logo>User Admin</Logo>
            <Menu> <Link to='/login'>Login</Link> |  <Link to='/form'>Add New User</Link> | <Link to='/list'>List Users</Link> | <Link to='/console'>Console</Link></Menu>
         </Banner>
         <Buffer />
         <Switch>
            <Route path='/login' component={ UserLogin } />
            <Route path='/form' component={ UserForm } />
            <Route path='/list' component={ UserList } />  
            <Route path='/console' component={ Console } />
         </Switch>
      </AdminWrapper>
   </Router>
);

export default AdminComponent;
