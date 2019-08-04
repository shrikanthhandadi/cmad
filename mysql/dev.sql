select * from event ;

drop table car ;
drop table customer_car ;
drop table hibernate_sequence ;
drop table user ;
drop table user_role;
drop table user_cars;
drop table car_customerCars;
drop table CustomerCar ;
drop table Employees ;
drop table Managers ;
drop table customercar ;

select * from user_role;
select * from user;
update user_role set role = 'ROLE_ADMIN'  where id =1 ;
select * from user;

insert into user(id,username, password) values (1,'admin','admin') ;
insert into user_role(id,role, user_id) values (1,'ADMIN',1 );

INSERT INTO user (id,username, password)
SELECT * FROM (SELECT 1,'admin' as username, 'admin' as password) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user WHERE username = 'admin'
)  ;

INSERT INTO user_role (id,role, user_id)
SELECT * FROM (SELECT 1,'ADMIN' as role, 1 as user_id) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_role WHERE user_id = 1
)  ;

truncate table user ;
truncate table user_role ;

select * from user_cars ;

select * from car ;

select * from car ;


select * from customer_car ;

describe customer_car ;


ALTER TABLE customer_car
ADD FOREIGN KEY (car_id) REFERENCES car(ID);

    ALTER TABLE tbl_name
    ADD [CONSTRAINT [symbol]] FOREIGN KEY
    [index_name] (col_name, ...)
    REFERENCES tbl_name (col_name,...)
    [ON DELETE reference_option]
    [ON UPDATE reference_option]

ALTER table customer_car
ADD CONSTRAINT constraint_name_1 
FOREIGN KEY foreign_key_name_1(car_id)
REFERENCES car(id)

insert into customer_car(id,reg_num,year,car_id) values(111,"mm",1111, 122);

ALTER TABLE customer_car
ADD CONSTRAINT FK_PersonOrder
FOREIGN KEY (car_id) REFERENCES Car(id);


create table book_authors (
  id int auto_increment not null primary key,
  book_id bigint not null,
  author_id int not null,
  foreign key (book_id) references car(id)
);


create table Employees(
   EmployeeID int not null,
   FirstName varchar(255),
   LastName varchar(255),
   address varchar(255),
   phoneNO varchar(11),
   ManagerID int not null,
   primary key (EmployeeID)
 );

create table Managers(
    mgrID int not null,
    salary float,
   MaxSupervisingCapacity int,
   foreign key (mgrID) references Employees(EmployeeID),
   primary key (mgrID) 
);


