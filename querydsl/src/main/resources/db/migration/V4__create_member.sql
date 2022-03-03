CREATE TABLE MEMBER (
    MEMBER_ID   BIGINT NOT NULL,
    AGE         INTEGER NOT NULL,
    USERNAME    VARCHAR(255),
    TEAM_ID     BIGINT,
    PRIMARY KEY (MEMBER_ID)
);

INSERT INTO MEMBER (AGE, TEAM_ID, USERNAME, MEMBER_ID) VALUES (10, 1, 'MEMBER1', 1);
INSERT INTO MEMBER (AGE, TEAM_ID, USERNAME, MEMBER_ID) VALUES (20, 1, 'MEMBER1', 2);
INSERT INTO MEMBER (AGE, TEAM_ID, USERNAME, MEMBER_ID) VALUES (30, 1, 'MEMBER1', 3);
INSERT INTO MEMBER (AGE, TEAM_ID, USERNAME, MEMBER_ID) VALUES (40, 2, 'MEMBER1', 4);
INSERT INTO MEMBER (AGE, TEAM_ID, USERNAME, MEMBER_ID) VALUES (50, 2, 'MEMBER1', 5);