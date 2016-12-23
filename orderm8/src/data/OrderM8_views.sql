DROP VIEW view_stats;

CREATE VIEW view_stats AS
select * from 
(select count(*) as cntBills from bills),
(select count(*) as cntUsers from users),
(select count(*) as cntTables from tables),
(select count(*) as cntOrderEntries from orderentries),
(select count(*) as cntProductTypes from  types),
(select count(*) as cntProducts from products),
(select count(*) OPEN_BILLS from orderentries INNER JOIN products ON (fkproduct = products.idproduct) where cancelled = 0 AND FKBILL is null),
(select SUM(PRICE * QUANTITY) as profit from orderentries INNER JOIN products ON (fkproduct = products.idproduct) where cancelled = 0 AND FKBILL is not null);