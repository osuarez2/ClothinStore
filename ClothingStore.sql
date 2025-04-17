Create Table store.users (
	user_id int,
    user_name varchar(255),
    user_password varchar(255),
    user_type varchar(255),
    user_email varchar(255),
    user_address varchar(255),
    PRIMARY KEY (user_id)
    );
    
    SELECT *
    FROM store.users
    