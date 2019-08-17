import React from 'react';
import EventFilter from "./../EventFilter";
import InfiniteEvents from "./../InfiniteEvents";
import EventSummary from "./../EventSummary";


const ConsoleComponent = (props) => (
   <div>
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
