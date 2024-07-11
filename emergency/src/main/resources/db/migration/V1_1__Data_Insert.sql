INSERT INTO role (name) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- testpss
INSERT INTO caller (id, username, password, email) VALUES
(1, 'bladeattheneck', '$2a$10$vyZLDsIIZJ5vdzjlbqE0D.l19rRtSk43TAknHWhhOsT2t4V1dhj.K', 'kalibrovkaaccount@mail.ru');

INSERT INTO caller2role (id, caller_id, role_name) VALUES
(1, 1, 'ROLE_ADMIN');

INSERT INTO receiver (id, caller_id, name, email, telegram_id) VALUES
(1, 1, 'Andrey', 'vesselofgarbage@gmail.com', null);

select nextval('caller2role_id_seq');
select nextval('caller_id_seq');
select nextval('receiver_id_seq');
