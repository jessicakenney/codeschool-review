SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS codeschools (
  id int PRIMARY KEY auto_increment,
  name VARCHAR
);
CREATE TABLE IF NOT EXISTS reviews (
  id int PRIMARY KEY auto_increment,
  content VARCHAR,
  rating INTEGER,
  codeschoolid INTEGER
);