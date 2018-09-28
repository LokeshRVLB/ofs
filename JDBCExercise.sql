
CREATE TABLE address(id BIGINT(20) AUTO_INCREMENT
						  ,street VARCHAR(100) NOT NULL
						  ,city VARCHAR(15) NOT NULL
						  ,postal_code INT(11) NOT NULL
						  ,PRIMARY KEY(id));


CREATE TABLE person(id BIGINT(20) AUTO_INCREMENT
						 ,name VARCHAR(50) NOT NULL
						 ,email VARCHAR(100) NOT NULL UNIQUE
						 ,address_id BIGINT(20)
						 ,birth_date DATE NOT NULL
						 ,created_date DATETIME DEFAULT NOW()
						 ,PRIMARY kEY(id)
						 ,CONSTRAINT fk_person_address 
						  FOREIGN KEY(address_id)
						  REFERENCES address(id)
						  ON DELETE CASCADE);
						  
INSERT INTO address(street, city, postal_code) VALUE ("Hassan Street", "chittoor", 517001);

INSERT INTO person(name, email,  address_id, birth_Date) VALUE ("R.Lokesh", "lokeshbalaji68@gmail.com", 1, '1996-08-06');
INSERT INTO person(name, email,  address_id, birth_Date) VALUE ("R.Lokesh1", "lokeshbalaji681@gmail.com", 1, '1996-08-06');
INSERT INTO person(name, email,  address_id, birth_Date) VALUE ("R.Lokesh2", "lokeshbalaji682s@gmail.com", 1, '1996-08-06');
INSERT INTO person(name, email,  address_id, birth_Date) VALUE ("R.Lokesh3", "boovanNaik@gmail.com", 1, '1996-08-06');

SELECT * FROM person;
SELECT * FROM address;

DROP TABLE person;
DROP TABLE address;

delete from address where id = 2;
CREATE TRIGGER person_address AFTER INSERT ON person
SELECT id from address
show variables like 'innodb_autoinc_lock_mode';
SET innodb_autoinc_lock_mode = 0;
select email from person;