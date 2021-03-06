CREATE TABLE DELIVERY_COMPANY
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE DELIVERY_STRIKE_ZIPCODE
(
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    DELIVERY_COMPANY_ID INT NOT NULL,
    ZIPCODE             VARCHAR(100) DEFAULT '' NOT NULL,
    ACTIVE              TINYINT(1) DEFAULT 1 NOT NULL,
    CONSTRAINT ZIPCODE UNIQUE (ZIPCODE),
    CONSTRAINT FK_DELIVERY_STRIKE_ZIPCODE_DELIVERY_COMPANY FOREIGN KEY (DELIVERY_COMPANY_ID) REFERENCES DELIVERY_COMPANY (ID)
);

CREATE TABLE SAMPLE_ENTITY (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    ADDRESS VARCHAR(255),
    NAME VARCHAR(255),
    PRIMARY KEY (ID)
);

INSERT INTO SAMPLE_ENTITY (ADDRESS, NAME) VALUES ( '경기도 고양시 1', '김태환' );
INSERT INTO SAMPLE_ENTITY (ADDRESS, NAME) VALUES ( '경기도 고양시 2', '홍길동' );
INSERT INTO SAMPLE_ENTITY (ADDRESS, NAME) VALUES ( '경기도 고양시 3', '차승원' );
INSERT INTO SAMPLE_ENTITY (ADDRESS, NAME) VALUES ( '경기도 고양시 4', '태사자' );
COMMIT;