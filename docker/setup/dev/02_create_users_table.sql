CREATE TABLE dev_database.users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       role_id INT NOT NULL ,
                       name VARCHAR(50) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       FOREIGN KEY(role_id) REFERENCES dev_database.roles(id)
);