create table app_user (
    id bigserial primary key,
    username varchar(2000) not null unique,
    password varchar(2000) not null,
    enabled boolean not null
);

insert into app_user (username, password, enabled) values ('parsentev', '123', true);
insert into app_user (username, password, enabled) values ('ban', '123', true);
insert into app_user (username, password, enabled) values ('ivan', '123', true);