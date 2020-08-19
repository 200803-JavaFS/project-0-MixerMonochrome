drop table if exists Ownership;
drop table if exists Users;
drop table if exists Accounts;

create table Users (
  username VarChar(30) not null primary key,
  psswrd VarChar(30) not null,
  first_name VarChar(30) not null,
  last_name VarChar(30) not null,
  acct_type VarChar(1) not null,
  phone_num VarChar(10),
  address VarChar(30),
  num_accts int not null
);

create table Accounts (
  acct_id serial primary key,
  acct_type VarChar(1) not null,
  balance int not null,
  status VarChar(1) not null
);

create table Ownership (
  rel_id serial primary key,
  acct_id int not null references Accounts(acct_id),
  username varchar(30) not null references Users(username)
);

insert into Users(username, psswrd, first_name, last_name, 
acct_type, phone_num, address, num_accts)
values
('Groot','Groot','Groot','Groot','A',null,null,0),
('deputy','snakeinmyboot','Woody','Pride','E','0000000000','Andys house',0),
('cuppa','tooEarly','Joe','Schmoe','C','1234567890','113 Coffee Lane',0);

insert into Accounts(acct_type,balance,status)
values
('S',292,'A'),
('S',300,'A');

insert into Ownership(acct_id,username)
values
(1,'cuppa'),
(2,'cuppa');
