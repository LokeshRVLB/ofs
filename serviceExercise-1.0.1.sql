use  lokesh_rajendran;
DROP TABLE scv_person;
DROP TABLE scv_address;
CREATE TABLE scv_address(id BIGINT(20) AUTO_INCREMENT
							,street VARCHAR(100) NOT NULL
							,city VARCHAR(15) NOT NULL
							,postal_code INT(11) NOT NULL
							, PRIMARY KEY(id));
CREATE TABLE scv_person(id BIGINT(20) AUTO_INCREMENT
							 	,first_name VARCHAR(50) NOT NULL
							 	,last_name VARCHAR	(50) NOT NULL
								,email VARCHAR(100) NOT NULL UNIQUE
							 	,address_id BIGINT(20)
						 	,dob DATE NOT NULL
							 	, PRIMARY KEY(id)
							 	, CONSTRAINT fk_sv_person_sv_address FOREIGN KEY(address_id) REFERENCES scv_address(id) ON
DELETE CASCADE);
							
INSERT INTO scv_address(street, city, postal_code) VALUE ("Hassan Street", "chittoor", 517001);
INSERT INTO scv_address(street, city, postal_code) VALUE ("Ragava street", "chennai", 600113);
INSERT INTO scv_address(street, city, postal_code) VALUE ("Rajendran street", "chennai", 600114);

INSERT INTO scv_person(first_name, last_name, email,  address_id, dob) VALUE ("Lokeshext", "Balaji", "lokeshbalaji68ext@gmail.com", 1, '1996-08-06');
INSERT INTO scv_person(first_name, last_name, email,  address_id, dob) VALUE ("Lokeshext2", "Balaji2", "lokeshbalaji68ext2@gmail.com", 2, '1996-08-06');
INSERT INTO scv_person(first_name, last_name, email,  address_id, dob) VALUE ("Lokeshext4", "Balaji4", "lokeshbalaji68ext4@gmail.com", 3, '1996-08-06');

SELECT * FROM scv_person;
SELECT * FROM scv_address;

select count(email) from scv_person where id in (1,2);
SELECT scv_person.id                
      ,scv_person.first_name        
      ,scv_person.last_name         
      ,scv_person.email             
      ,scv_person.dob               
      ,scv_person.address_id        
FROM scv_person                     
WHERE scv_person.address_id IN ( 2,3 );

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

DROP table person_admin;
DROP table person_credentials;
CREATE TABLE person_admin(email VARCHAR(100) NOT NULL UNIQUE);
INSERT INTO person_admin VALUES ("lokesh@gmail.com");

CREATE TABLE person_credentials(email VARCHAR(100) NOT NULL UNIQUE, pass VARCHAR(50) NOT NULL);
INSERT INTO person_credentials values ("lokesh@gmail.com","lokesh@1234"), ("lokeshbalaji68@gmail.com", "lokesh1234");



select * from person_credentials;