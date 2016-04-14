create table t_pi(
	n_id SERIAL primary key,
	d_dateline	timestamp, 
	c_name varchar(100),
	c_category varchar(50),
	c_value varchar(50),
	c_quality varchar(50)
);