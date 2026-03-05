CREATE TABLE dev_database.user_credential (
                                 user_id INT PRIMARY KEY,
                                 password_hash VARCHAR(255) NOT NULL,
                                 FOREIGN KEY(user_id) REFERENCES users(id)
);
