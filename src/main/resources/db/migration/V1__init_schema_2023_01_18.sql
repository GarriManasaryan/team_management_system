create table if not exists tms_users(
	id varchar(40) not null,
	name varchar(128) not null,
	email varchar(256) not null,
	role varchar(32) not null,
	primary key (id)
);