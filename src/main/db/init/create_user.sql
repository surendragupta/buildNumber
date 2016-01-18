-- Create gage_admin
CREATE ROLE gage_admin LOGIN ENCRYPTED PASSWORD 'md50fa09ef72a01470ad28d3e7409b31865' VALID UNTIL 'infinity';

-- Permission to gage_admin
GRANT ALL PRIVILEGES ON DATABASE gage to gage_admin;