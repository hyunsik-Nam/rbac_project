-- 사용자 데이터
INSERT INTO tb_users (id, user_id, user_name, password, auth) VALUES 
(1, 'admin', '관리자', '{bcrypt}$2a$10$PQZgI8tDC0Xq5SfAzWxYMeCHl9wyHO2.8yWEbASmE6ZFGBBsGW4.u', 'A'),
(2, 'user1', '유저1', '{bcrypt}$2a$10$PQZgI8tDC0Xq5SfAzWxYMeCHl9wyHO2.8yWEbASmE6ZFGBBsGW4.u', 'B'),
(3, 'user2', '유저2', '{bcrypt}$2a$10$PQZgI8tDC0Xq5SfAzWxYMeCHl9wyHO2.8yWEbASmE6ZFGBBsGW4.u', 'C'),
(4, 'user3', '유저3', '{bcrypt}$2a$10$PQZgI8tDC0Xq5SfAzWxYMeCHl9wyHO2.8yWEbASmE6ZFGBBsGW4.u', 'D');

INSERT INTO tb_pricing_plans (id, plan_name, price) VALUES
(1, 'BASIC', 9.99),
(2, 'PRO', 19.99);