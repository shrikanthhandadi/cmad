INSERT INTO user (id,username, password)
SELECT * FROM (SELECT 1,'admin' as username, '$2a$10$5lvrPli7hJ4YJxC.sCu4b.WYITuv.6moKD2FvJXH1svEdLrbZEu9a' as password) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user WHERE username = 'admin'
)  ;

INSERT INTO user_role (id,role, user_id)
SELECT * FROM (SELECT 1,'ROLE_ADMIN' as role, 1 as user_id) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_role WHERE user_id = 1
)  ;
