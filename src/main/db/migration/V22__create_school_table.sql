-- Table: provision.school

-- Dropping reference constraint 
-- ALTER TABLE provision.school DROP CONSTRAINT FK_school_district;

-- Dropping Table: provision.school 
DROP TABLE IF EXISTS provision.school CASCADE;

-- Creating Table: provision.school 
CREATE TABLE provision.school (
	school_id NUMERIC NOT NULL,
	first_name text,
	last_name text,
	password text,
	user_name text,
	license_pool text,
	license_type text,
	number_of_license NUMERIC,
	sname text,
	pilot BOOLEAN,
	pilot_end_date TIMESTAMP,
	pilot_start_date TIMESTAMP,
	userspace text,
	domain_id NUMERIC,
	PRIMARY KEY (school_id)
);

-- Creating referential constraint between Table: provision.school and provision.district 
ALTER TABLE provision.school ADD CONSTRAINT FK_school_district FOREIGN KEY (domain_id) REFERENCES provision.district;

-- Permission to gage_admin
GRANT ALL ON TABLE provision.school TO gage_admin;