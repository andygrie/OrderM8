DROP TABLE Types CASCADE CONSTRAINTS;
DROP TABLE Products CASCADE CONSTRAINTS;
DROP TABLE Tables CASCADE CONSTRAINTS;
DROP TABLE OrderEntries CASCADE CONSTRAINTS;
DROP TABLE Orders CASCADE CONSTRAINTS;
DROP TABLE Users CASCADE CONSTRAINTS;
DROP TABLE Organisers CASCADE CONSTRAINTS;
DROP TABLE Waiters CASCADE CONSTRAINTS;
DROP TABLE Bills CASCADE CONSTRAINTS;

CREATE TABLE Types (
  idType INTEGER,
  name VARCHAR2(40),

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
  idUser INTEGER,
  username VARCHAR2(40),
  password VARCHAR2(40),

  CONSTRAINT pkUsers PRIMARY KEY (idUser)
);

CREATE TABLE Organisers (
  fkUser INTEGER,

  CONSTRAINT pkOrganisers PRIMARY KEY (fkUser),
  CONSTRAINT fkOrganisersUsers FOREIGN KEY (fkUser) REFERENCES Users (idUser)
);

CREATE TABLE Waiters (
  fkUser INTEGER,

  CONSTRAINT pkWaiters PRIMARY KEY (fkUser),
  CONSTRAINT fkWaitersUsers FOREIGN KEY (fkUser) REFERENCES Users (idUser)
);

CREATE TABLE OrderEntries (
  idOrderEntry INTEGER,
  fkProduct INTEGER,
  fkTable INTEGER,
  fkUser INTEGER,
  note VARCHAR2(50),

  CONSTRAINT pkOrderEntries PRIMARY KEY (idOrderEntry),
  CONSTRAINT fkOrderEntriesProducts FOREIGN KEY (fkProduct) REFERENCES Products (idProduct),
  CONSTRAINT fkOrderEntriesTables FOREIGN KEY (fkTable) REFERENCES Tables (idTable),
  CONSTRAINT fkOrderEntriesWaiters FOREIGN KEY (fkUser) REFERENCES Waiters (fkUser)
);

CREATE TABLE Bills (
  idBill INTEGER,
  billDate DATE,

  CONSTRAINT pkBills PRIMARY KEY (idBill)
);

CREATE TABLE Orders (
  fkOrderEntry INTEGER,
  fkBill INTEGER,
  coupon NUMBER(1,0),

  CONSTRAINT pkOrders PRIMARY KEY (fkOrderEntry, fkBill),
  CONSTRAINT fkOrdersOrderEntries FOREIGN KEY (fkOrderEntry) REFERENCES OrderEntries (idOrderEntry),
  CONSTRAINT fkOrdersBills FOREIGN KEY (fkBill) REFERENCES Bills (idBill)
);