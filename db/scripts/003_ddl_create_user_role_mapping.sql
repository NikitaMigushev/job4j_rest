CREATE TABLE user_role_mapping (
  id bigserial PRIMARY KEY,
  user_id bigint NOT NULL REFERENCES app_user(id),
  role_id bigint NOT NULL REFERENCES user_role(id),
  UNIQUE (user_id, role_id)
);