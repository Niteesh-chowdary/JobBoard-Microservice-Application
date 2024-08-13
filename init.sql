-- Create databases
CREATE DATABASE company;
CREATE DATABASE job;
CREATE DATABASE review;

-- Create a new user with password
CREATE USER niteesh WITH PASSWORD '123456789';

-- Grant privileges to the new user on the databases
GRANT ALL PRIVILEGES ON DATABASE company TO niteesh;
GRANT ALL PRIVILEGES ON DATABASE job TO niteesh;
GRANT ALL PRIVILEGES ON DATABASE review TO niteesh;