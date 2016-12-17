DROP TABLE Types CASCADE CONSTRAINTS;
DROP TABLE Products CASCADE CONSTRAINTS;
DROP TABLE Tables CASCADE CONSTRAINTS;
DROP TABLE OrderEntries CASCADE CONSTRAINTS;
DROP TABLE Users CASCADE CONSTRAINTS;
DROP TABLE Bills CASCADE CONSTRAINTS;

CREATE TABLE Types (
  idType INTEGER,
  name VARCHAR2(40),
  vat NUMBER(2,0),

  CONSTRAINT pkType PRIMARY KEY (idType)
);

CREATE TABLE Products (
  idProduct INTEGER,
  fkType INTEGER,
  name VARCHAR2(40),
  price NUMBER(*, 2),
  quantity INTEGER,

  CONSTRAINT pkProducts PRIMARY KEY (idProduct),
  CONSTRAINT fkProductTypes FOREIGN KEY (fkType) REFERENCES Types (idType)
);

CREATE TABLE Tables (
  idTable INTEGER,
  sdoCoordinates SDO_GEOMETRY,

  CONSTRAINT pkTables PRIMARY KEY (idTable)
);

CREATE TABLE Users (
  idUser 	INTEGER,
  username 	VARCHAR2(40),
  password 	VARCHAR2(40),
  utype     INTEGER,

  CONSTRAINT pkUsers PRIMARY KEY (idUser)
);

CREATE TABLE Bills (
  idBill INTEGER,
  billDate DATE,

  CONSTRAINT pkBills PRIMARY KEY (idBill)
);

CREATE TABLE OrderEntries (
  idOrderEntry INTEGER,
  fkProduct INTEGER,
  fkTable INTEGER,
  fkUser INTEGER,
  fkBill INTEGER,
  note VARCHAR2(50),
  cancelled NUMBER(1,0),
  coupon NUMBER(1,0),

  CONSTRAINT pkOrderEntries PRIMARY KEY (idOrderEntry),
  CONSTRAINT fkOrderEntriesProducts FOREIGN KEY (fkProduct) REFERENCES Products (idProduct),
  CONSTRAINT fkOrderEntriesTables FOREIGN KEY (fkTable) REFERENCES Tables (idTable),
  CONSTRAINT fkOrderEntriesUsers FOREIGN KEY (fkUser) REFERENCES Users (idUser),
  CONSTRAINT fkOrderEntriesBills FOREIGN KEY (fkBill) REFERENCES Bills (idBill)
);

-- Test Data --
INSERT INTO Users (idUser, username, password, utype) VALUES (0, 'org', 'org', 2);
INSERT INTO Users (idUser, username, password, utype) VALUES (1, 'wat', 'wat', 1);

INSERT INTO Tables (idTable, sdoCoordinates) VALUES
(
  0,
  SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(1,1, 5,7))
);

INSERT INTO Tables (idTable, sdoCoordinates) VALUES
(
  1,
  SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(5,5, 6,6))
);

INSERT INTO Tables (idTable, sdoCoordinates) VALUES
(
  2,
  SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(15,10, 30,25))
);


INSERT INTO Types (idType, name, vat) VALUES (0, 'Food', 10);
INSERT INTO Types (idType, name, vat) VALUES (1, 'Drink', 20);

INSERT INTO Products (idProduct, fkType, name, price, quantity) VALUES
(
  0, 0, 'Schnitzel', 10.2, 150
);

INSERT INTO Products (idProduct, fkType, name, price, quantity) VALUES
(
  1, 0, 'Schopf', 12, 50
);

INSERT INTO Products (idProduct, fkType, name, price, quantity) VALUES
(
  2, 0, 'Gösser 0.5', 3, 2000
);

INSERT INTO Products (idProduct, fkType, name, price, quantity) VALUES
(
  3, 0, 'Gösser 0.3', 2, 1500
);

INSERT INTO Products (idProduct, fkType, name, price, quantity) VALUES
(
  4, 0, 'Cola', 2.8, 800
);

-- paid Orders
INSERT INTO Bills (idBill, billDate) VALUES
(
  0, SYSDATE
);

INSERT INTO Bills (idBill, billDate) VALUES
(
  1, SYSDATE
);

INSERT INTO OrderEntries (idOrderEntry, fkProduct, fkTable, fkUser, fkBill, note, cancelled, coupon) VALUES
(
  0, 1, 0, 1, 0, 'ohne ketchup', 0, 0
);

INSERT INTO OrderEntries (idOrderEntry, fkProduct, fkTable, fkUser, fkBill, note, cancelled, coupon) VALUES
(
  1, 2, 1, 1, 0, '', 0, 0
);

INSERT INTO OrderEntries (idOrderEntry, fkProduct, fkTable, fkUser, fkBill, note, cancelled, coupon) VALUES
(
  2, 4, 2, 1, 1, '', 0, 1
);


INSERT INTO OrderEntries (idOrderEntry, fkProduct, fkTable, fkUser, fkBill, note, cancelled, coupon) VALUES
(
  3, 4, 2, 1, NULL, '', 0, 0
);


