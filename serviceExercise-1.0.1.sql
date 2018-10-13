DROP TABLE scv_person;
DROP TABLE scv_address;

CREATE TABLE scv_address(id BIGINT(20) AUTO_INCREMENT
						  ,street VARCHAR(100) NOT NULL
						  ,city VARCHAR(15) NOT NULL
						  ,postal_code INT(11) NOT NULL
						  ,PRIMARY KEY(id));


CREATE TABLE scv_person(id BIGINT(20) AUTO_INCREMENT
						 	  ,first_name VARCHAR(50) NOT NULL
						 	  ,last_name VARCHAR	(50) NOT NULL
							  ,email VARCHAR(100) NOT NULL UNIQUE
						 	  ,address_id BIGINT(20)
					   	  ,dob DATE NOT NULL
						 	  ,PRIMARY kEY(id)
						 	  ,CONSTRAINT fk_sv_person_sv_address 
						 		FOREIGN KEY(address_id)
						  		REFERENCES scv_address(id)
						  		ON DELETE CASCADE);
						  
INSERT INTO scv_address(street, city, postal_code) VALUE ("Hassan Street", "chittoor", 517001);
INSERT INTO scv_address(street, city, postal_code) VALUE ("Ragava street", "chennai", 600113);
INSERT INTO scv_address(street, city, postal_code) VALUE ("Rajendran street", "chennai", 600114);

INSERT INTO scv_person(first_name, last_name, email,  address_id, dob) VALUE ("Lokesh", "Balaji", "lokeshbalaji68@gmail.com", 1, '1996-08-06');
INSERT INTO scv_person(first_name, last_name, email,  address_id, dob) VALUE ("Lokesh2", "Balaji2", "lokeshbalaji682@gmail.com", 2, '1996-08-06');
INSERT INTO scv_person(first_name, last_name, email,  address_id, dob) VALUE ("Lokesh4", "Balaji4", "lokeshbalaji684@gmail.com", 3, '1996-08-06');

SELECT * FROM scv_person;
SELECT * FROM scv_address;


UPDATE scv_person LEFT JOIN scv_address ON scv_person.address_id = scv_address.id 
SET scv_person.first_name = "updated lokesh"
	,scv_person.last_name = "updated Balaji"
	,scv_person.dob = '1996-08-05'
	,scv_address.street = "indian street"
	,scv_address.city = "Chittoor"
	,scv_address.postal_code = 123456
WHERE scv_person.id = 1;

delete from scv_person,scv_address using scv_person inner join scv_address on scv_person.address_id = scv_address.id
where scv_person.id = 1;
