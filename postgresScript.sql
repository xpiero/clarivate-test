CREATE TABLE "player" (
	"id" serial NOT NULL,
	"username" character varying NOT NULL UNIQUE,
	"password" character varying NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "level" (
	"id" serial NOT NULL,
	"name" character varying NOT NULL,
	CONSTRAINT level_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "score" (
        "id" serial NOT NULL,
	"player" bigint NOT NULL,
	"level" bigint NOT NULL,
	"score" bigint NOT NULL,
	CONSTRAINT score_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

ALTER TABLE "score" ADD CONSTRAINT "score_fk0" FOREIGN KEY ("player") REFERENCES "player"("id");
ALTER TABLE "score" ADD CONSTRAINT "score_fk1" FOREIGN KEY ("level") REFERENCES "level"("id");

INSERT INTO player (id, username, password) VALUES (1, 'Luke', '$2a$10$B4UdYw6FkhZeSsyjtKsM1ueZbBvHiKU3DTDpO/hNBCGn1DU8roo.G');
INSERT INTO player (id, username, password) VALUES (2, 'Liam', '$2a$10$2mfFqlsK1RxOMKYD0VD5a.XlOmoS07n/uc9Yp5L695i8UY1KsYEhG');

INSERT INTO level (id,name) VALUES (1, '1');
INSERT INTO level (id,name) VALUES (2, '2');

INSERT INTO score (id, player, level, score) VALUES (1, 1, 1, 103);
INSERT INTO score (id, player, level, score) VALUES (2, 1, 2, 1234);
INSERT INTO score (id, player, level, score) VALUES (3, 2, 1, 30);
INSERT INTO score (id, player, level, score) VALUES (4, 2, 1, 5);
INSERT INTO score (id, player, level, score) VALUES (5, 2, 2, 120);
INSERT INTO score (id, player, level, score) VALUES (6, 2, 2, 64);



