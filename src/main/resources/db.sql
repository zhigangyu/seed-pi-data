--CREATE USER lathe WITH PASSWORD 'lathe';

create table t_pi(
	n_id SERIAL primary key,
	d_dateline	timestamp, 
	c_name varchar(100),
	c_category varchar(50),
	c_value varchar(50),
	c_quality varchar(50),
	c_address character varying(200),
	c_deviceid varchar(50)
);

create table t_device(
	n_id SERIAL primary key,
	d_dateline	timestamp, 
	c_name varchar(100),
	c_value decimal(10,3),
	c_device varchar(50)
);