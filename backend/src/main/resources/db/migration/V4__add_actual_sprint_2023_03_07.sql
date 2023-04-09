alter table tms_sprint_planning drop column start_at;

create table if not exists tms_actual_sprint(
    id varchar(40) not null,
    name varchar(128) not null,
    planning_id varchar(40) not null,
    start_at timestamp with time zone,
    constraint fk_tms_actual_sprint_planning_id foreign key(planning_id) references tms_sprint_planning,
    primary key(id)
);