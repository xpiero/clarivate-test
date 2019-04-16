CREATE TABLE player (
	id serial NOT NULL,
	username character varying NOT NULL UNIQUE,
	password character varying NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE TABLE level (
	id serial NOT NULL,
	name character varying NOT NULL,
	CONSTRAINT level_pk PRIMARY KEY (id)
);

CREATE TABLE score (
        id serial NOT NULL,
	player bigint NOT NULL,
	level bigint NOT NULL,
	score bigint NOT NULL,
	CONSTRAINT score_pk PRIMARY KEY (id)
);

ALTER TABLE score ADD CONSTRAINT score_fk0 FOREIGN KEY (player) REFERENCES player(id);
ALTER TABLE score ADD CONSTRAINT score_fk1 FOREIGN KEY (level) REFERENCES level(id);