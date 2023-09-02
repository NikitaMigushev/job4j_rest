create table user_role (
    id bigserial primary key,
	user_role varchar(50) not null
);

INSERT INTO user_role (user_role) VALUES ('ROLE_USER');
INSERT INTO user_role (user_role) VALUES ('ROLE_ADMIN');