alter table tms_sprint drop constraint fk_sprint_task_id;
alter table tms_sprint drop constraint fk_sprint_planning_id;
alter table tms_user_tasks drop constraint fk_user_tasks_user_id;
alter table tms_user_tasks drop constraint fk_user_tasks_planning_id;
alter table tms_user_tasks drop constraint fk_user_tasks_task_id;
drop table tms_task;

alter table tms_employee_capacity drop constraint fk_employee_capacity_user_id;
alter table tms_employee_capacity drop constraint fk_employee_capacity_planning_id;
alter table tms_employee_capacity drop constraint fk_employee_capacity_capacity_template_id;

drop table tms_planning;

drop table tms_sprint;
drop table tms_employee_capacity;
drop table tms_user_tasks;

create table if not exists tms_time_units(
    id varchar(40) not null,
    name varchar(128) not null,
    duration int not null,
    primary key(id)
);

create table if not exists tms_capacity_template(
    id varchar(40) not null,
    title varchar(128) not null,
    description text,
    primary key(id)
);

create table if not exists tms_capacity_eater(
	id varchar(40) not null,
	name varchar(128) not null,
	type_id varchar(40) not null,
	priority varchar(32) not null,
	deadline timestamp with time zone,
	duration integer not null,
	status varchar(64) not null,
	primary key(id),
	constraint fk_tms_capacity_eater_type_id foreign key(type_id) references tms_task_types
);

create table if not exists tms_capacity_template_eaters(
	capacity_template_id varchar(40) not null,
	capacity_eater_id varchar(40) not null,
	constraint fk_tms_capacity_template_eaters_capacity_eater_id foreign key(capacity_eater_id) references tms_capacity_eater,
	constraint fk_tms_capacity_template_eaters_capacity_template_id foreign key(capacity_template_id) references tms_capacity_template
);

create table if not exists tms_sprint_planning(
    id varchar(40) not null,
    name varchar(128) not null,
    time_unit_id varchar(40) not null,
    start_at timestamp with time zone,
    capacity_template_id varchar(40) not null,
    max_capacity int not null,
    constraint fk_tms_sprint_planning_capacity_template_id foreign key(capacity_template_id) references tms_capacity_template,
    constraint fk_tms_sprint_planning_time_unit_id foreign key(time_unit_id) references tms_time_units,
    primary key(id)
);

create table if not exists tms_sprint_planning_participant_capacity(
    user_id varchar(40) not null,
    sprint_planning_id varchar(40) not null,
    capacity_eater_id varchar(40) not null,
    duration int not null,
    constraint fk_tms_spr_plan_part_cap_user_id foreign key(user_id) references tms_users,
    constraint fk_tms_spr_plan_part_cap_sprint_planning_id foreign key(sprint_planning_id) references tms_sprint_planning,
    constraint fk_tms_spr_plan_part_cap_capacity_eater_id foreign key(capacity_eater_id) references tms_capacity_eater
);

create index user_id_i_spr_plan_part_cap on tms_sprint_planning_participant_capacity (user_id);
create index sprint_planning_id_i_spr_plan_part_cap on tms_sprint_planning_participant_capacity (sprint_planning_id);
create index capacity_eater_id_i_spr_plan_part_cap on tms_sprint_planning_participant_capacity (capacity_eater_id);
create unique index user_id_sprint_planning_id_capacity_eater_id_ui_spr_plan_part_cap on tms_sprint_planning_participant_capacity(user_id, sprint_planning_id, capacity_eater_id);

create index capacity_template_id_i_capacity_template_eaters on tms_capacity_template_eaters (capacity_template_id);
create index capacity_eater_id_i_capacity_template_eaters on tms_capacity_template_eaters (capacity_eater_id);
create unique index capacity_template_id_capacity_eater_id_ui_capacity_template_eaters on tms_capacity_template_eaters(capacity_template_id, capacity_eater_id);