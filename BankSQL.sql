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

CREATE OR REPLACE FUNCTION get_current_time() RETURNS TIME WITH TIME ZONE
AS $$
-- curent_time is a built value that is just the current time.  
SELECT current_time;
$$ LANGUAGE SQL; 

CREATE OR REPLACE FUNCTION trigger_set_time() RETURNS TRIGGER 
AS $$
--Returning a Trigger allows for use of some special varaibles NEW and OLD which represent the 
--state of the database before and after the event. 

BEGIN
	NEW.update_at = NOW();
	RETURN NEW; 
END;
$$ LANGUAGE plpgsql; 


ALTER TABLE Users ADD COLUMN update_at TIMESTAMP;
alter table Accounts add column update_at timestamp;
alter table ownership add column update_at timestamp;

CREATE TRIGGER set_time_u BEFORE UPDATE ON users FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

CREATE TRIGGER set_time_a BEFORE UPDATE ON accounts FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

CREATE TRIGGER set_time_o BEFORE UPDATE ON ownership FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

CREATE TRIGGER set_time_ui BEFORE INSERT ON users FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

CREATE TRIGGER set_time_ua BEFORE INSERT ON accounts FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

CREATE TRIGGER set_time_uo BEFORE INSERT ON ownership FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

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
