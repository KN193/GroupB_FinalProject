CREATE database serviceexchange;

use serviceexchange;

CREATE TABLE User (
  username     VARCHAR(30),
  password     VARCHAR(255),
  usertype     VARCHAR(10) NOT NULL  DEFAULT 'ROLE_user',
  nickname     VARCHAR(30) UNIQUE,
  firstname    VARCHAR(20) NOT NULL,
  lastname     VARCHAR(20) NOT NULL,
  gender       CHAR(1) NOT NULL DEFAULT  'M',
  verified     CHAR        NOT NULL  DEFAULT 'N',
  mobile       VARCHAR(10),
  Nationality  VARCHAR(20),
  PreferNation VARCHAR(20),
  DateOfBirth  DATE,
  photo        VARCHAR(100),
  creditLevel  INT         NOT NULL  DEFAULT 5,
  virtualMoney DOUBLE      NOT NULL  DEFAULT 100,
  CONSTRAINT user_pk PRIMARY KEY (username),
  CONSTRAINT Ver_check CHECK (verified = 'Y' OR verified = 'N'),
  CONSTRAINT type_const CHECK (usertype = 'ROLE_user' OR usertype = 'ROLE_admin'),
  CONSTRAINT genderCheck CHECK (gender ='M' or gender ='F')
);




CREATE TABLE Service (
  serviceID       INT     AUTO_INCREMENT,
  provider      VARCHAR(30)  NOT NULL,
  name          VARCHAR(40)  NOT NULL,
  currentPrice  DOUBLE      NOT NULL,
  originalPrice DOUBLE      NOT NULL    DEFAULT 0,
  description   VARCHAR(200) NOT NULL,
  capacity      INT          NOT NULL,
  number_of_deal INT         NOT NULL   DEFAULT 0,
  RegisterTime   TIMESTAMP   NOT NULL   DEFAULT current_timestamp(),
  nationality    VARCHAR(20) NOT NULL,
  rank           DOUBLE      NOT NULL,
  type           VARCHAR(20) NOT NULL,
  image          VARCHAR(200),
  CONSTRAINT service_pk PRIMARY KEY (serviceID),
  CONSTRAINT service_fk1 FOREIGN KEY (provider) REFERENCES User (username)
);



CREATE TABLE SearchRecord (
  recordID   INT                 AUTO_INCREMENT,
  username VARCHAR(30) NOT NULL,
  searchInfo VARCHAR(100),
  searchTime TIMESTAMP NOT NULL  DEFAULT current_timestamp(),
  CONSTRAINT searchRecord_pk PRIMARY KEY (recordID),
  CONSTRAINT searchRecord_fk FOREIGN KEY (username) REFERENCES User (username)
);



CREATE TABLE Favourites (
  username  VARCHAR(30) NOT NULL,
  serviceID INT         NOT NULL,
  CONSTRAINT favourites_pk PRIMARY KEY (username, serviceID),
  CONSTRAINT favourites_fk1 FOREIGN KEY (username) REFERENCES user(username),
  CONSTRAINT favourites_fk2 FOREIGN KEY (serviceID) REFERENCES Service(serviceID)
);



CREATE TABLE Purchased (
  PurchasedID   INT                   AUTO_INCREMENT,
  username    VARCHAR(30) NOT NULL,
  serviceID   INT         NOT NULL,
  purchasedTime TIMESTAMP NOT NULL    DEFAULT current_timestamp(),
  providerCheck CHAR      NOT NULL    DEFAULT 'N',
  customerCheck CHAR      NOT NULL    DEFAULT 'N',
  quantity      INT       NOT NULL,
  transferredfrom  int,
  CONSTRAINT purchased_pk PRIMARY KEY (PurchasedID),
  CONSTRAINT purchased_fk1 FOREIGN KEY (username) REFERENCES User (username),
  CONSTRAINT purchased_fk2 FOREIGN KEY (serviceID) REFERENCES Service (serviceID),
  CONSTRAINT purchased_check1 CHECK (providerCheck = 'Y' OR providerCheck = 'N'),
  CONSTRAINT purchased_check2 CHECK (customerCheck = 'Y' OR customerCheck = 'N')
);


CREATE TABLE Comment (
  commentID INT                      AUTO_INCREMENT,
  serviceID       INT          NOT NULL,
  commenter VARCHAR(30) NOT NULL,
  commentInfo VARCHAR(100) NOT NULL,
  visible     CHAR         NOT NULL  DEFAULT 'Y',
  commentTime TIMESTAMP    NOT NULL  DEFAULT current_timestamp(),
  previousComment INT,
  CONSTRAINT comment_pk PRIMARY KEY (commentID),
  CONSTRAINT comment_fk1 FOREIGN KEY (serviceID) REFERENCES Service (serviceID),
  CONSTRAINT comment_fk2 FOREIGN KEY (commenter) REFERENCES User (username),
  CONSTRAINT comment_check CHECK (visible = 'Y' OR visible = 'N')
);


CREATE TABLE Address (
  owner       VARCHAR(30) NOT NULL,
  type  VARCHAR(20) NOT NULL,
  country VARCHAR(30) NOT NULL,
  state   VARCHAR(30) NOT NULL,
  ZIPCode INT         NOT NULL,
  city    VARCHAR(30) NOT NULL,
  street  VARCHAR(30) NOT NULL,
  houseNumber INT         NOT NULL,
  unitNumber  INT         NOT NULL,
  CONSTRAINT address_pk PRIMARY KEY (owner, type),
  CONSTRAINT address_fk FOREIGN KEY (owner) REFERENCES User (username)
);


CREATE TABLE Discount (
  serviceID     INT         NOT NULL,
  discountValue DOUBLE NOT NULL,
  target        VARCHAR(30) NOT NULL,
  expiryDate    TIMESTAMP   NOT NULL,
  CONSTRAINT discount_pk PRIMARY KEY (serviceID, target),
  CONSTRAINT discount_fk1 FOREIGN KEY (serviceID) REFERENCES Service (serviceID),
  CONSTRAINT discount_fk2 FOREIGN KEY (target) REFERENCES User (username),
  CONSTRAINT discount_check CHECK (discountValue > 0 AND discountValue < 1)
);



CREATE TABLE CheckBoxMessage (
  MessageID   INT                    AUTO_INCREMENT,
  sender    VARCHAR(30)  NOT NULL,
  receiver  VARCHAR(30) NOT NULL,
  content   VARCHAR(300) NOT NULL,
  sendTime  TIMESTAMP    NOT NULL  DEFAULT current_timestamp(),
  readMessage CHAR       NOT NULL    DEFAULT 'N',
  CONSTRAINT message_pk PRIMARY KEY (MessageID),
  CONSTRAINT message_fk1 FOREIGN KEY (sender) REFERENCES User (username),
  CONSTRAINT message_fk2 FOREIGN KEY (receiver) REFERENCES User (username),
  CONSTRAINT message_check CHECK ( readMessage = 'Y' OR readMessage = 'N')
);



CREATE TABLE ShoppingCart (
  username  VARCHAR(30) NOT NULL,
  serviceID INT         NOT NULL,
  quantity  INT         NOT NULL    DEFAULT 1,
  CONSTRAINT shopping_pk PRIMARY KEY (username, serviceID),
  CONSTRAINT shopping_fk1 FOREIGN KEY (username) REFERENCES User (username),
  CONSTRAINT shopping_fk2 FOREIGN KEY (serviceID) REFERENCES Service (serviceID)
);

