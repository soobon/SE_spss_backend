
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
    num_pages int not null,
    foreign key (id) references student(id)
);
CREATE TABLE print (
    printer_id CHAR(9) NOT NULL,
    file_id VARCHAR(100) NOT NULL,
    nb_of_page_used INT NOT NULL,
    nb_of_coppy INT NOT NULL,
    paper_size VARCHAR(5) CHECK (paper_size = 'A3' OR paper_size = 'A4') NOT NULL,
    statuss INT DEFAULT 0 CHECK (statuss = 0 OR statuss = 1 OR statuss = 2),
    one_or_two_side int not null check (one_or_two_side = 1 or one_or_two_side = 2),
    print_date date not null,
    PRIMARY KEY (printer_id, file_id),
    FOREIGN KEY (printer_id) REFERENCES printer(printer_id),
    FOREIGN KEY (file_id) REFERENCES files(file_id)
);

create table spss (
	semester varchar(10) primary key ,
    nb_of_page_default int ,
    nb_of_page_used int,
    reset_date date
);
-- -----------------------------------------
INSERT INTO accounts (username, passwords, rolee) VALUES
('user1', 'password1', 1),
('user2', 'password2', 2),
('user3', 'password3', 1),
('user4', 'password4', 2),
('user5', 'password5', 1),
('user6', 'password6', 2),
('user7', 'password7', 1),
('user8', 'password8', 2),
('user9', 'password9', 1),
('user10', 'password10', 2);
INSERT INTO student (id, namee, email, faculty, nb_of_page_left, username) VALUES
('123456789', 'Alice Smith', 'alice.smith@example.com', 'Engineering', 100, 'user1'),
('234567890', 'Bob Johnson', 'bob.johnson@example.com', 'Science', 120, 'user2'),
('345678901', 'Charlie Lee', 'charlie.lee@example.com', 'Arts', 80, 'user3'),
('456789012', 'David Wilson', 'david.wilson@example.com', 'Medicine', 90, 'user4'),
('567890123', 'Eva Brown', 'eva.brown@example.com', 'Law', 110, 'user5'),
('678901234', 'Frank White', 'frank.white@example.com', 'Engineering', 60, 'user6'),
('789012345', 'Grace Green', 'grace.green@example.com', 'Science', 130, 'user7'),
('890123456', 'Hannah Taylor', 'hannah.taylor@example.com', 'Arts', 70, 'user8'),
('901234567', 'Ian Harris', 'ian.harris@example.com', 'Medicine', 140, 'user9'),
('012345678', 'Jack King', 'jack.king@example.com', 'Law', 50, 'user10');
INSERT INTO adminn (id, namee, email, username) VALUES
('112233445', 'John Doe', 'john.doe@example.com', 'user1'),
('223344556', 'Lisa Martin', 'lisa.martin@example.com', 'user2'),
('334455667', 'Mike Davis', 'mike.davis@example.com', 'user3'),
('445566778', 'Nancy Moore', 'nancy.moore@example.com', 'user4'),
('556677889', 'Oliver Clark', 'oliver.clark@example.com', 'user5'),
('667788990', 'Paul Walker', 'paul.walker@example.com', 'user6'),
('778899001', 'Quincy Lewis', 'quincy.lewis@example.com', 'user7'),
('889900112', 'Rachel Scott', 'rachel.scott@example.com', 'user8'),
('990011223', 'Steve Hall', 'steve.hall@example.com', 'user9'),
('101122334', 'Tracy Allen', 'tracy.allen@example.com', 'user10');
INSERT INTO printer (printer_id, building, state, model, import_date) VALUES
('P12345678', 'Building A', 0, 'HP LaserJet', '2022-01-10'),
('P23456789', 'Building B', 1, 'Canon Pixma', '2021-05-15'),
('P34567890', 'Building C', 0, 'Epson EcoTank', '2023-08-20'),
('P45678901', 'Building D', 1, 'Brother HL-L2350DW', '2020-11-12'),
('P56789012', 'Building E', 0, 'Samsung Xpress', '2021-02-28'),
('P67890123', 'Building F', 1, 'Xerox Phaser', '2023-07-19'),
('P78901234', 'Building G', 0, 'Lexmark C3326dw', '2022-06-22'),
('P89012345', 'Building H', 1, 'Ricoh SP 210SU', '2021-03-03'),
('P90123456', 'Building I', 0, 'Kyocera ECOSYS', '2023-09-09'),
('P01234567', 'Building J', 1, 'Oki B412dn', '2020-12-14');
INSERT INTO files (file_id, upload_date, file_name, id, num_pages) VALUES
('F1234567890', '2024-01-01', 'Document1.pdf', '123456789', 15),
('F2345678901', '2024-02-01', 'Document2.pdf', '234567890', 10),
('F3456789012', '2024-03-01', 'Document3.pdf', '345678901', 20),
('F4567890123', '2024-04-01', 'Document4.pdf', '456789012', 30),
('F5678901234', '2024-05-01', 'Document5.pdf', '567890123', 25),
('F6789012345', '2024-06-01', 'Document6.pdf', '678901234', 35),
('F7890123456', '2024-07-01', 'Document7.pdf', '789012345', 40),
('F8901234567', '2024-08-01', 'Document8.pdf', '890123456', 50),
('F9012345678', '2024-09-01', 'Document9.pdf', '901234567', 45),
('F0123456789', '2024-10-01', 'Document10.pdf', '012345678', 60);
INSERT INTO print (printer_id, file_id, nb_of_page_used, nb_of_coppy, paper_size, print_date, statuss, one_or_two_side) VALUES
('P12345678', 'F1234567890', 5, 1, 'A4', '2024-01-01', 0, 1),
('P23456789', 'F2345678901', 8, 1, 'A3', '2024-02-01', 1, 2),
('P34567890', 'F3456789012', 15, 2, 'A4', '2024-03-01', 2, 1),
('P45678901', 'F4567890123', 10, 3, 'A4', '2024-04-01', 0, 1),
('P56789012', 'F5678901234', 20, 1, 'A3', '2024-05-01', 1, 2),
('P67890123', 'F6789012345', 25, 2, 'A4', '2024-06-01', 2, 1),
('P78901234', 'F7890123456', 30, 1, 'A3', '2024-07-01', 0, 2),
('P89012345', 'F8901234567', 18, 3, 'A4', '2024-08-01', 1, 1),
('P90123456', 'F9012345678', 40, 2, 'A3', '2024-09-01', 0, 2),
('P01234567', 'F0123456789', 50, 1, 'A4', '2024-10-01', 2, 1);
INSERT INTO spss (semester, nb_of_page_default, reset_date) VALUES
('2024', 100, '2024-06-01'),
('2027', 200, '2027-06-01');

DELIMITER //
CREATE PROCEDURE GetStudentPrintDetails()
BEGIN
    SELECT 
        s.id, 
        f.file_name, 
        p.nb_of_page_used, 
        p.statuss, 
        p.print_date, 
        pr.building,
        pr.printer_id
    FROM 
        student s
    JOIN 
        files f ON s.id = f.id
    JOIN 
        print p ON p.file_id = f.file_id
    JOIN 
        printer pr ON pr.printer_id = p.printer_id;
END //
DELIMITER ;

-- CALL GetStudentPrintDetails();

DELIMITER //

CREATE PROCEDURE GetStudentPrintDetailsByPrinterId(IN printerId VARCHAR(20))
BEGIN
    SELECT 
        s.id, 
        f.file_name, 
        p.nb_of_page_used, 
        p.statuss, 
        p.print_date, 
        pr.building,
        pr.printer_id
    FROM 
        student s
    JOIN 
        files f ON s.id = f.id
    JOIN 
        print p ON p.file_id = f.file_id
    JOIN 
        printer pr ON pr.printer_id = p.printer_id
    WHERE 
        pr.printer_id = printerId;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetStudentPrintDetailsByStudentId(IN studentId VARCHAR(9))
BEGIN
    SELECT 
        s.id, 
        f.file_name, 
        p.nb_of_page_used, 
        p.statuss, 
        p.print_date, 
        pr.building,
        pr.printer_id
    FROM 
        student s
    JOIN 
        files f ON s.id = f.id
    JOIN 
        print p ON p.file_id = f.file_id
    JOIN 
        printer pr ON pr.printer_id = p.printer_id
    WHERE 
        s.id = studentId;
END //

DELIMITER ;

DELIMITER $$
CREATE PROCEDURE InsertPrinter(IN building VARCHAR(255), IN model VARCHAR(255), IN import_date DATE)
BEGIN
    DECLARE id VARCHAR(9);
    SET id = LPAD(FLOOR(RAND() * 1000000000), 9, '0');
    INSERT INTO printer (printer_id, building, state, model, import_date) 
    VALUES (id, building, 0, model, import_date);
    SELECT * 
    FROM printer
    WHERE printer_id = id;
END $$
DELIMITER ;
DELIMITER //
CREATE PROCEDURE UpdateNbOfPageLeft(std_id VARCHAR(50)  , page_add int )  
BEGIN
    UPDATE student
    SET nb_of_page_left = nb_of_page_left + page_add	
    WHERE id = std_id;
    
    select * from student where id=std_id;
END;
//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE UpdatePrinterState(p_printer_id VARCHAR(50))
BEGIN
    DECLARE current_state INT;
    SELECT state INTO current_state
    FROM printer
    WHERE printer_id = p_printer_id;
    IF current_state = 0 THEN
        SET current_state = 1;
    ELSEIF current_state = 1 THEN
        SET current_state = 0;
    END IF;
    UPDATE printer
    SET state = current_state
    WHERE printer_id = p_printer_id;
END;
//
DELIMITER ;
DELIMITER //
CREATE PROCEDURE UpdatePrintStatus(p_printer_id VARCHAR(50), p_file_id VARCHAR(50), p_status INT)
BEGIN
    UPDATE print
    SET statuss = p_status
    WHERE printer_id = p_printer_id AND file_id = p_file_id;
    
    select * from print WHERE printer_id = p_printer_id AND file_id = p_file_id;
END;
//
DELIMITER ;


select * from files;
select * from print ;
select * from student ;
select * from printer;

