
CREATE TABLE visits (
     id INT PRIMARY KEY AUTO_INCREMENT,
     visitDate TIMESTAMP NOT NULL,
     name VARCHAR(100) NOT NULL,
     species VARCHAR(100) NOT NULL,
     purpose MEDIUMTEXT,
     user_id INT
);

CREATE TABLE users (
     id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(100) NOT NULL,
     surname VARCHAR(100) NOT NULL,
     log VARCHAR(100) NOT NULL,
     password VARCHAR(100) NOT NULL,
     type VARCHAR(20) NOT NULL
);
