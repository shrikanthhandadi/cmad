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


const UserFormComponent = (props) => (
   <Wrapper>
      <Title>Add New User</Title>
      <table>
         <tbody>
            <tr>
               <td>Username</td>
               <td><input value={ props.username } onChange={ props.onUsername } placeholder='User Id' /></td>
            </tr>
            <tr>
               <td>Password</td>
               <td><input value={ props.password } onChange={ props.onPassword } placeholder='Full Name' /></td>
            </tr>
            <tr>
               <td>Roles</td>
               <td>
                  <select multiple value={ props.roles } onChange={ props.onRoles } >
                     <option value="ROLE_ADMIN">ADMIN</option>
                     <option value="ROLE_USER">USER</option>
                  </select>
               </td>
            </tr>
            <tr>
               <td>Makes</td>
               <td>
                  <select multiple value={ props.makes } onChange={ props.onMakes } >
                     <option value="BMW">BMW</option>
                     <option value="Audi">Audi</option>
                     <option value="Audi">Hyundai</option>
                     <option value="Audi">Skoda</option>
                  </select>
               </td>
            </tr>
            <tr><td colSpan='2' align='right'><Button onClick={ props.onAdd }>Add User</Button></td></tr>
         </tbody>
      </table>
   </Wrapper>
);
export default UserFormComponent;
