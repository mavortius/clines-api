CREATE TABLE users
(
    id       bigint auto_increment,
    name     VARCHAR(50)  NOT NULL,
    email    varchar(50)  NOT NULL,
    password varchar(255) NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (id)
) engine = InnoDB;
