insert into users(username,email,password)values('user','email@user.com','user');
insert into users(username,email,password)values('admin','email@admin.com','admin');
insert into users(username,email,password)values('user1','email@user1.com','user1');

insert into roles(name)value('ADMIN');
insert into roles(name)value('USER');

insert into join_user_role(role_id, user_id)values(1,2);
insert into join_user_role(role_id, user_id)values(2,1);
insert into join_user_role(role_id, user_id)values(2,2);
insert into join_user_role(role_id, user_id)values(2,3);
