INSERT INTO users(id,name)
VALUES (1,'CPU');
INSERT INTO users(id,name)
VALUES (2,'ほんだ');
INSERT INTO users(id,name)
VALUES (3,'いがき');

INSERT INTO matches(id,user1,user2,user1Hand,user2Hand,isActive)
VALUES (1,2,1,'Gu','Choki',FALSE);
INSERT INTO matches(id,user1,user2,user1Hand,user2Hand,isActive)
VALUES (2,2,1,'Gu','Gu',FALSE);
INSERT INTO matches(id,user1,user2,user1Hand,user2Hand,isActive)
VALUES (3,2,1,'Gu','Pa',FALSE);

INSERT INTO matchinfo(id,user1,user2,user1Hand,isActive)
VALUES (1,1,2,'Gu',FALSE);
INSERT INTO matchinfo(id,user1,user2,user1Hand,isActive)
VALUES (2,2,3,'Choki',FALSE);
