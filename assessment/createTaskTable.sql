use paf_assessment_basedb;

-- insert into task (description, priority, due_date) values("he-ho", 1, "2022-01-01");

create table task (
    task_id int auto_increment,
    description varchar(255),
    priority int,
    due_date date,
    user_id varchar(8),

    primary key(task_id),
    constraint fk_user foreign key(user_id)
        references user(user_id)
);

-- Priority must be between 1 and 3
alter table task add constraint limitPriority
    check (priority between 1 and 3);