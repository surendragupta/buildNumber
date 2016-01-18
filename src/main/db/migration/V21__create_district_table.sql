-- Table: provision.district

-- Dropping reference constraint 
-- ALTER TABLE school DROP CONSTRAINT FK_school_district IF EXISTS school;

-- Dropping Table: provision.district 
DROP TABLE IF EXISTS provision.district CASCADE;

-- Creating Table: provision.district 
CREATE TABLE provision.district (
	domain_id NUMERIC NOT NULL,
	admin_user text,
	first_name text,
	last_name text,
	password text,
	user_name text,
	license_pool text,
	license_type text,
	number_of_license NUMERIC,
	name text,
	pilot BOOLEAN,
	pilot_end_date TIMESTAMP,
	pilot_start_date TIMESTAMP,
	userspace text,
	PRIMARY KEY (domain_id)
);

-- Permission to gage_admin
GRANT ALL ON TABLE provision.district TO gage_admin;
