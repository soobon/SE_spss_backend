

create database spss;
use spss ;
create table accounts(
	username varchar(20) primary key ,
	passwords varchar(20) ,
    rolee int check (rolee = 1 or rolee =2)
);
create table student(
	id char(9) primary key check (id like '_________'),
    namee varchar(20) , 
	email varchar(40) not null check (email like '%_@_%'),
	faculty varchar(20),
    nb_of_page_left int,
    username varchar(20)  ,
    foreign key (username ) references accounts(username) 
);
create table adminn (
	id char(9) primary key check (id like '_________'),
    namee varchar(20) , 
	email varchar(40) not null check (email like '%_@_%'),
	username varchar(20)  ,
    foreign key (username ) references accounts(username) 
);
create table printer (
	printer_id char(9) primary key check (printer_id like '_________'),
    building varchar(10) not null,
    state int check (state  = 0 or state = 1),
    model varchar(20) not null,
    import_date  date not null
);

create table files (
	file_id varchar (100) primary key,
    upload_date date not null,
    file_name varchar(20) not null	,
    id char(9) ,
    foreign key (id) references student(id)
);
create table print(
	printer_id char (9) not null,
    file_id varchar(100) not null,
    primary key(printer_id , file_id),
    nb_of_page_used int not null,
    nb_of_coppy int not null ,
    paper_size varchar(5) check (paper_size = 'A3'  or paper_size = 'A4') not null,
    print_date date not null,
    foreign key (printer_id) references printer (printer_id),
    foreign key (file_id) references files(file_id)
);
create table spss (
	semester varchar(10) primary key ,
    nb_of_page_default int ,
    reset_date date
);
-- --------------------------sample data -----------

-- Insert sample data into 'accounts' table
INSERT INTO accounts (username, passwords, rolee)
VALUES 
('user1', 'password123', 1),
('user2', 'password456', 2),
('admin1', 'adminpass', 1);

-- Insert sample data into 'student' table
INSERT INTO student (id, namee, email, faculty, nb_of_page_left, username)
VALUES 
('123456789', 'Alice Smith', 'alice@example.com', 'Engineering', 10, 'user1'),
('987654321', 'Bob Johnson', 'bob@example.com', 'Science', 5, 'user2');

-- Insert sample data into 'adminn' table
INSERT INTO adminn (id, namee, email, username)
VALUES 
('111222333', 'Admin One', 'admin1@example.com', 'admin1');

-- Insert sample data into 'printer' table
INSERT INTO printer (printer_id, building, state, model, import_date)
VALUES 
('PRN000001', 'H6', 1, 'HP LaserJet', '2021-05-10'),
('PRN000002', 'H1', 0, 'Canon Pixma', '2022-03-15');

-- Insert sample data into 'files' table
INSERT INTO files (file_id, upload_date, file_name, id)
VALUES 
('FILE123', '2024-01-12', 'Thesis.pdf', '123456789'),
('FILE456', '2024-02-20', 'Report.docx', '987654321');

-- Insert sample data into 'print' table
INSERT INTO print (printer_id, file_id, nb_of_page_used, nb_of_coppy, paper_size, print_date)
VALUES 
('PRN000001', 'FILE123', 5, 1, 'A4', '2024-03-01'),
('PRN000002', 'FILE456', 10, 2, 'A3', '2024-03-05');

-- Insert sample data into 'spss' table
INSERT INTO spss (semester, nb_of_page_default, reset_date)
VALUES 
('Spring2024', 20, '2024-06-01'),
('Fall2024', 20, '2024-12-01');



select * from files










