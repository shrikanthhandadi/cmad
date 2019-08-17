import React from 'react';
import styled from 'styled-components';
import { Header } from "./../Header";
import { LeftMenu } from "./../LeftMenu";
import EventFilter from "./../EventFilter";
import InfiniteEvents from "./../InfiniteEvents";
import EventSummary from "./../EventSummary";


const ConsoleComponent = (props) => (
   <div className="container  border border-dark" >
      <div className="row">
         <div className="col-md-10 col-lg-10 " >
            <EventFilter />
         </div>
      </div>
      <div className="row">
         <div className="col-md-10 col-lg-10">
            <EventSummary />
         </div>
      </div>
      <div className="row">
         <div className="col-md-10 col-lg-10">
            <InfiniteEvents />
         </div>
      </div>
   </div>
);
export default ConsoleComponent;
