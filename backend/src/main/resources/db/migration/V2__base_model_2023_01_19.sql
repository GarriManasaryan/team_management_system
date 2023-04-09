create table if not exists tms_teams (
	id varchar(40) not null,
	name varchar(128) not null,
	manager_id varchar(40) not null,
	primary key(id),
	constraint fk_teams_manager_id foreign key(manager_id) references tms_users(id)
);

alter table tms_users add constraint user_unique unique (id);

create table if not exists tms_responsibility(
	id varchar(40) not null,
	name text not null,
	outer_id varchar(128),
	primary key(id),
	label varchar(128) not null
);

create table if not exists tms_user_responsibilities(
	user_id varchar(40) not null,
	resp_id varchar(40) not null,
	constraint fk_user_resp_user_id foreign key(user_id) references tms_users,
	constraint fk_user_resp_resp_id foreign key(resp_id) references tms_responsibility
);

create table if not exists tms_skills(
	id varchar(40) not null,
	name varchar(40) not null,
	label varchar(40) not null,
	primary key(id)
);

create table if not exists tms_user_skills(
	user_id varchar(40) not null,
	skill_id varchar(40) not null,
	constraint fk_users_skills_user_id foreign key(user_id) references tms_users,
	constraint fk_users_skills_skill_id foreign key(skill_id) references tms_skills
);

create table if not exists tms_task_types(
	id varchar(40) not null,
	name varchar(128) not null,
	default_duration integer,
	primary key(id)
);

create table if not exists tms_task(
	id varchar(40) not null,
	name varchar(128) not null,
	type_id varchar(40) not null,
	priority varchar(32) not null,
	deadline timestamp with time zone,
	duration integer not null,
	status varchar(64) not null,
	primary key(id),
	constraint fk_tms_task_type_id foreign key(type_id) references tms_task_types
);

create table if not exists tms_capacity_template(
	id varchar(40) not null,
	title varchar(1024) not null,
	description text not null,
	duration integer not null,
	primary key(id)
);

create table if not exists tms_planning(
	id varchar(40) not null,
	name varchar(128) not null,
	time_unit varchar(128) not null,
	primary key(id)
);

create table if not exists tms_sprint(
	task_id varchar(40) not null,
	planning_id varchar(40) not null,
	sprint_start_date timestamp with time zone,
	sprint_end_date timestamp with time zone,
	type varchar(32) not null,
	constraint fk_sprint_task_id foreign key(task_id) references tms_task,
	constraint fk_sprint_planning_id foreign key(planning_id) references tms_planning

);

create table if not exists tms_employee_capacity(
	user_id varchar(40) not null,
	planning_id varchar(40) not null,
	capacity_template_id varchar(40) not null,
	constraint fk_employee_capacity_user_id foreign key(user_id) references tms_users,
	constraint fk_employee_capacity_planning_id foreign key(planning_id) references tms_planning,
	constraint fk_employee_capacity_capacity_template_id foreign key(capacity_template_id) references tms_capacity_template

);

create table if not exists tms_user_tasks(
	user_id varchar(40) not null,
	planning_id varchar(40) not null,
	task_id varchar(40) not null,
	constraint fk_user_tasks_user_id foreign key(user_id) references tms_users,
	constraint fk_user_tasks_planning_id foreign key(planning_id) references tms_planning,
	constraint fk_user_tasks_task_id foreign key(task_id) references tms_task

);

create table if not exists tms_news_categories(
	id varchar(40) not null,
	name varchar(128) not null,
	primary key(id)

);

create table if not exists tms_news(
	id varchar(40) not null,
	title varchar(128) not null,
	description text not null,
	news_category_id varchar(40) not null,
	constraint fk_news_news_categ_id foreign key(news_category_id) references tms_news_categories,
	primary key(id)

);

create index user_id_i_user_resp on tms_user_responsibilities (user_id);
create index resp_id_i_user_resp on tms_user_responsibilities (resp_id);
create unique index user_id_resp_id_ui_user_responsibilities on tms_user_responsibilities(user_id, resp_id);

create index user_id_i_user_skills on tms_user_skills(user_id);
create index skill_id_i_user_skills on tms_user_skills(skill_id);
create unique index user_id_skill_id_user_skills on tms_user_skills(user_id, skill_id);

create index task_id_i_sprint on tms_sprint(task_id);
create index planning_id_i_sprint on tms_sprint(planning_id);
create unique index task_id_planning_id_ui_sprint on tms_sprint(task_id, planning_id);

create index user_id_i_emploee_capacity on tms_employee_capacity(user_id);
create index planning_id_emploee_capacity on tms_employee_capacity(planning_id);
create index capicity_template_id_emploee_capacity on tms_employee_capacity(capacity_template_id);
create unique index user_id_planning_id_capicity_template_id_ui_emploee_capacity on tms_employee_capacity(user_id, planning_id, capacity_template_id);

create index user_id_i_user_tasks on tms_user_tasks(user_id);
create index planning_id_user_tasks on tms_user_tasks(planning_id);
create index task_id_user_tasks on tms_user_tasks(task_id);
create unique index user_id_planning_id_task_id_ui_user_tasks on tms_user_tasks(user_id, planning_id, task_id);

