SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS codeSchool (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  language VARCHAR
);