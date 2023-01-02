create database paf_assessment_basedb;
use paf_assessment_basedb;

create table user (
    user_id varchar(8) not null,
    username varchar(128) not null,
    name varchar(128),

    primary key(user_id)
);

-- To ensure column has no white spaces:
-- https://stackoverflow.com/questions/10708184/how-do-i-make-sure-a-string-column-has-no-spaces
-- >
alter table user add constraint chkNoSpaces
    check (username not like '% %');