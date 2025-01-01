-- Create custom 'movie-list' role
CREATE ROLE "movie-list" WITH
    LOGIN
    CREATEDB
    CREATEROLE
    INHERIT
    CONNECTION LIMIT -1
    PASSWORD '12345678';

-- Create the 'actors' table
CREATE TABLE actors (
                        id serial PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        gender VARCHAR(50) NOT NULL,
                        nationality VARCHAR(100) NOT NULL
);

-- Create the 'studios' table
CREATE TABLE studios (
                         id serial PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         location VARCHAR(100) NOT NULL
);

-- Create the 'movies' table
CREATE TABLE movies (
                        id serial PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        genre VARCHAR(100) NOT NULL,
                        release_date DATE NOT NULL,
                        lead_actor_id BIGINT NOT NULL,
                        studio_id BIGINT NOT NULL,
                        CONSTRAINT fk_movies_lead_actor_id FOREIGN KEY (lead_actor_id) REFERENCES actors(id),
                        CONSTRAINT fk_movies_studio_id FOREIGN KEY (studio_id) REFERENCES studios(id)
);
