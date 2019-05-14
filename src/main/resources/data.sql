insert into users(username,password)values('user','user');
insert into users(username,password)values('admin','admin');
insert into users(username,password)values('user1','user1');

insert into roles(name)value('ADMIN');
insert into roles(name)value('USER');

insert into join_user_role(role_id, user_id)values(1,2);
insert into join_user_role(role_id, user_id)values(2,1);
insert into join_user_role(role_id, user_id)values(2,2);
insert into join_user_role(role_id, user_id)values(2,3);
