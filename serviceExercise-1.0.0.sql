CREATE TABLE ser_address(id BIGINT(20) AUTO_INCREMENT
							  	,street VARCHAR(100) NOT NULL
							  	,city VARCHAR(15) NOT NULL
						  		,postal_code INT(11) NOT NULL
						  		,PRIMARY KEY(id));


CREATE TABLE ser_person(id BIGINT(20) AUTO_INCREMENT
							 	,first_name VARCHAR(50) NOT NULL
							 	,last_name VARCHAR(50) NOT NULL
							 	,email VARCHAR(100) NOT NULL UNIQUE
							 	,address_id BIGINT(20)
							 	,birth_date DATE NOT NULL
							 	,created_date DATETIME DEFAULT NOW()
							 	,PRIMARY kEY(id)
							 	,CONSTRAINT fk_serverPerson_serverAddress 
						     	 FOREIGN KEY(address_id)
						  	  	 REFERENCES address(id));
						  