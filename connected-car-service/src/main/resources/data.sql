INSERT INTO user (id,username, password)
SELECT * FROM (SELECT 1,'admin' as username, '$2a$10$5lvrPli7hJ4YJxC.sCu4b.WYITuv.6moKD2FvJXH1svEdLrbZEu9a' as password) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user WHERE username = 'admin'
)  ;

INSERT INTO user_role (user_id,roles)
SELECT * FROM (SELECT 1,'ROLE_ADMIN' as roles) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_role WHERE user_id = 1 and roles = 'ROLE_ADMIN'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 1,'Honda' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 1 and makes = 'Honda'
)  ;



-- username user
INSERT INTO user (id,username, password)
SELECT * FROM (SELECT 2,'user' as username, '$2a$10$Ql04XxB8mmw/LeIebsFa0OOwYgUUtxY927XZRzogjO6cRBWcUR2vS' as password) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user WHERE username = 'user'
)  ;

INSERT INTO user_role (user_id,roles)
SELECT * FROM (SELECT 2,'ROLE_USER' as roles) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_role WHERE user_id = 2 and roles = 'ROLE_USER'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 2,'BMW' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 2 and makes = 'BMW'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 2,'Audi' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 2 and makes = 'Audi'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 2,'Hyundai' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 2 and makes = 'Hyundai'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 2,'Honda' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 2 and makes = 'Honda'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 2,'Skoda' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 2 and makes = 'Skoda'
)  ;



-- username admuser
INSERT INTO user (id,username, password)
SELECT * FROM (SELECT 4,'super' as username, '$2a$10$kS92zJvaqToigQdh1hP1M./HmQH4MvYWEP7UMLO8l6a2Wxmkf4oR2' as password) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user WHERE username = 'super'
)  ;

INSERT INTO user_role (user_id,roles)
SELECT * FROM (SELECT 4,'ROLE_USER' as roles) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_role WHERE user_id = 4 and roles = 'ROLE_USER'
)  ;

INSERT INTO user_role (user_id,roles)
SELECT * FROM (SELECT 4,'ROLE_ADMIN' as roles) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_role WHERE user_id = 4 and roles = 'ROLE_ADMIN'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 4,'BMW' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 4 and makes = 'BMW'
)  ;

INSERT INTO user_make (user_id,makes)
SELECT * FROM (SELECT 4,'Audi' as makes) AS tmp
WHERE NOT EXISTS (
    SELECT 	1  FROM user_make WHERE user_id = 4 and makes = 'Audi'
)  ;


