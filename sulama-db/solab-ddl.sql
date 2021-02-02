-- Solab v3.0.0
DROP DATABASE sulama;
DROP TABLESPACE sulamaTS;

CREATE TABLESPACE sulamaTS
  OWNER postgres
LOCATION '/Users/mustafatanitek/development/POSTGRES/sulamaDB';
CREATE DATABASE sulama
ENCODING 'UTF8'
OWNER postgres TABLESPACE sulamaTS;

-- Run in sulama db
CREATE SEQUENCE USERS_SEQ
  START 2
  INCREMENT 1;

CREATE TABLE "app_role" (
  id          SERIAL NOT NULL,
  description VARCHAR(255),
  role_name   VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE "app_user" (
  id                  INT4         NOT NULL,
  admin               BOOLEAN      NOT NULL,
  enabled             BOOLEAN      NOT NULL,
  lastInteractionTime TIMESTAMP,
  loginCount          INT4,
  name                VARCHAR(255),
  password            VARCHAR(255),
  surname             VARCHAR(255),
  username            VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE SystemSettings (
  tag   VARCHAR(255) NOT NULL,
  info  VARCHAR(1024),
  value VARCHAR(255),
  PRIMARY KEY (tag)
);
CREATE TABLE user_role (
  user_id INT4 NOT NULL,
  role_id INT4 NOT NULL
);

CREATE INDEX INX_APP_USER_username
  ON "app_user" (username);
ALTER TABLE IF EXISTS "app_user"
  ADD CONSTRAINT UK_3k4cplvh82srueuttfkwnylq0 UNIQUE (username);

CREATE INDEX INX_SYSTEM_SETTINGS_tag
  ON SystemSettings (tag);

ALTER TABLE IF EXISTS user_role
  ADD CONSTRAINT FK3i4q5dfgtkoench9f07jpaxbe FOREIGN KEY (role_id) REFERENCES "app_role";
ALTER TABLE IF EXISTS user_role
  ADD CONSTRAINT FKc6nnnfr5vhlf5f6rvfr0e7l1x FOREIGN KEY (user_id) REFERENCES "app_user";
