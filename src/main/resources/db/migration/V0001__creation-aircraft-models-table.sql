CREATE TABLE aircraft_models
(
    id          bigint auto_increment,
    description VARCHAR(100) NOT NULL,

    CONSTRAINT pk_aircraft_models PRIMARY KEY (id)
) engine = InnoDB;