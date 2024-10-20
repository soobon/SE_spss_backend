
-- create database SPSS
-- use spss
-- create table login (
--     id int PRIMARY KEY ,
-- 	username VARCHAR(20)  NOT NULL,
--     pass 	VARCHAR(20) NOT NULL

-- )

-- CREATE TABLE student(
-- 	student_id INT PRIMARY KEY,
--     student_name VARCHAR(20)  NOT NULL,
--     faculty VARCHAR (20) NOT NULL,
--     nb_of_paper_left int 
-- )

-- ALTER TABLE student
-- ADD COLUMN login_id INT,
-- ADD CONSTRAINT fk_login FOREIGN KEY (login_id) REFERENCES login(id);


-- INSERT INTO student (student_id, student_name, faculty, nb_of_paper_left, login_id)
-- VALUES (1, 'John Doe', 'Computer Science', 3, 1);

-- INSERT INTO login(id , username, pass)
-- VALUES (1, "jDoe" , "jj123");

-- select  s.student_id, s.student_name, s.faculty, s.nb_of_paper_left, l.username ,l.pass
-- FROM student s
-- JOIN login l ON  s.login_id =l.id



