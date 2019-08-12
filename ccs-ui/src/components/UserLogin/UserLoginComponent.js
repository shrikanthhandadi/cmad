import React from 'react';
import styled from 'styled-components';

const Wrapper = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  border-radius: 10px;
  padding-top: 50px;
  padding-bottom: 50px;
  padding-left: 150px;
  padding-right: 150px;
  box-shadow: 0px 0px 10px gray;
`;

const Title = styled.div`
   font-size: 1.5em;
`;

const Button = styled.button`
   padding: 5px;
   background-color: black;
   color: white;
   border: none;
`;


const UserLoginComponent = (props) => (
   <Wrapper>
      <Title>User Login</Title>
      <table>
         <tbody>
            <tr>
               <td>Username</td>
               <td><input value={ props.username } onChange={ props.onUsername } placeholder='Username' /></td>
            </tr>
            <tr>
               <td>Password</td>
               <td><input type='password' value={ props.password } onChange={ props.onPassword } placeholder='Password' /></td>
            </tr>

            <tr>
               <td colSpan='2' align='right'><Button onClick={ props.onLogin }>Login</Button></td>
            </tr>
         </tbody>
      </table>
   </Wrapper>
);
export default UserLoginComponent;
