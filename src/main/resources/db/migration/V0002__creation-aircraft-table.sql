CREATE TABLE aircraft
(
    id       bigint auto_increment,
    code     VARCHAR(10) NOT NULL,
    model_id BIGINT      NOT NULL,

    CONSTRAINT pk_aircraft PRIMARY KEY (id),
    CONSTRAINT fk_aircraft_to_aircraft_models FOREIGN KEY (model_id) REFERENCES aircraft_models (id)
) engine = InnoDB;

CREATE INDEX idx_aircraft_code ON aircraft (code);