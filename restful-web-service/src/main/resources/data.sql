insert into users(id, join_date, name, password, ssn) values(90001, now(), 'User1', 'test111', '701010-1111111');
insert into users(id, join_date, name, password, ssn) values(90002, now(), 'User2', 'test112', '701010-1111112');
insert into users(id, join_date, name, password, ssn) values(90003, now(), 'User3', 'test113', '701010-1111113');

insert into post(description, user_id) values('my first post', 90001);
insert into post(description, user_id) values('my first post', 90001);