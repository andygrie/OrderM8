package edu.htl.orderm8.Data.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.htl.orderm8.Data.Dao.BillDao;
import edu.htl.orderm8.Data.Dao.OrderEntryDao;
import edu.htl.orderm8.Data.Dao.ProductDao;
import edu.htl.orderm8.Data.Dao.ProductTypeDao;
import edu.htl.orderm8.Data.Dao.StatisticDao;
import edu.htl.orderm8.Data.Dao.TableDao;
import edu.htl.orderm8.Data.Dao.UserDao;
import edu.htl.orderm8.Data.Objects.Bill;
import edu.htl.orderm8.Data.Objects.BillOrderEntriesWrapper;
import edu.htl.orderm8.Data.Objects.BillWrapper;
import edu.htl.orderm8.Data.Objects.OrderEntry;
import edu.htl.orderm8.Data.Objects.OrderEntryProductWrapper;
import edu.htl.orderm8.Data.Objects.Product;
import edu.htl.orderm8.Data.Objects.ProductType;
import edu.htl.orderm8.Data.Objects.Statistic;
import edu.htl.orderm8.Data.Objects.Table;
import edu.htl.orderm8.Data.Objects.TableOrderWrapper;
import edu.htl.orderm8.Data.Objects.TableStatusWrapper;
import edu.htl.orderm8.Data.Objects.User;

public class Database {
	/* 				Singleton 				*/
	private static Database _instance;
	private Database() {}
	public static Database getInstance() {
		if(_instance == null)
			_instance = new Database();
		
		return _instance;
	}
	
	/*                ProductType                */
	public List<ProductType> getProductTypes() {
		return ProductTypeDao.getProductTypes();
	}
	
	public ProductType getProductType(long idType) {
		return ProductTypeDao.getProductType(idType);
	}
	
	public ProductType insertProductType(ProductType pt) throws SQLException {
		return ProductTypeDao.insertProductType(pt);
	}
	
	public void updateProductType(long id, ProductType pt) throws SQLException {
		ProductTypeDao.updateProductType(id, pt);
	}
	
	public void deleteProductType(long id) throws SQLException {
		ProductTypeDao.deleteProductType(id);
	}
	/*--------------------------------------------*/
	
	
	/*                	Product                	  */
	public List<Product> getProducts() {
		return ProductDao.getProducts();
	}
	
	public List<Product> getProducts(long prodType) {
		return ProductDao.getProducts(prodType);
	}
	
	public Product getProduct(long idProduct) {
		return ProductDao.getProduct(idProduct);
	}
	
	public Product insertProduct (Product p) throws SQLException {
		return ProductDao.insertProduct(p);
	}
	
	public void updateProduct(long id, Product p) throws SQLException {
		ProductDao.updateProduct(id, p);
	}
	
	public void deleteProduct(long id) throws SQLException {
		ProductDao.deleteProduct(id);
	}
	/*--------------------------------------------*/
	
	
	/*                	Table                	  */
	public List<Table> getTables() {
		return TableDao.getTables();
	}
	
	public Table getTable(long idTable) {
		return TableDao.getTable(idTable);
	}
	
	public Table insertTable(Table t) throws SQLException {
		return TableDao.insertTable(t);
	}
	
	public int insertTables(ArrayList<Table> tables) throws SQLException {
		return TableDao.insertTables(tables);
	}
	
	public void updateTable (long id, Table t) throws SQLException {
		TableDao.updateTable(id, t);
	}
	
	public void deleteTable(long id) throws SQLException {
		TableDao.deleteTable(id);
	}
	/*--------------------------------------------*/
	
	
	/*                	 Bill                	  */
	public List<Bill> getBills() {
		return BillDao.getBills();
	}
	
	public Bill getBill(long idBill) {
		return BillDao.getBill(idBill);
	}
	
	public Bill insertBill() throws SQLException {
		return BillDao.insertBill();
	}
	
	public void deleteBill (long id) throws SQLException {
		BillDao.deleteBill(id);
	}
	/*--------------------------------------------*/
	
	
	/*                OrderEntry                   */
	public List<OrderEntry> getOrderEntries() {
		return OrderEntryDao.getOrderEntrys();
	}
	
	public OrderEntry getOrderEntry(long idOrderEntry) {
		return OrderEntryDao.getOrderEntry(idOrderEntry);
	}
	
	public List<OrderEntry> getOrderEntriesOpenByTable(User user, long idtable) {
		return OrderEntryDao.getOrderEntriesOpenByTable(user, idtable);
	}
	
	public List<OrderEntry> getOrderEntriesOpen(User user) {
		return OrderEntryDao.getOrderEntriesOpen(user);
	}
	
	public OrderEntry insertOrderEntry(OrderEntry oe) throws SQLException {
		return OrderEntryDao.insertOrderEntry(oe);
	}
	
	public void updateOrderEntry(long id, OrderEntry oe) throws SQLException {
		OrderEntryDao.updateOrderEntry(id, oe);
	}
	
	public void deleteOrderEntry(long id) throws SQLException {
		OrderEntryDao.delteOrderEntry(id);
	}
	
	public void pay(long id, BillWrapper billWrapper) throws SQLException {
		OrderEntryDao.pay(id, billWrapper);
	}
	/*--------------------------------------------*/
	
	
	/*                  User                      */
	public User findUser(String username, String password) throws SQLException {
		return UserDao.findUser(username, password);
	}
	
	public List<User> getUsers() {
		return UserDao.getUsers();
	}
	
	public User getUser(long idUser) {
		return UserDao.getUser(idUser);
	}
	
	public User insertUser(User u) throws SQLException {
		return UserDao.insertUser(u);
	}
	
	public void updateUser(long id, User u) throws SQLException {
		UserDao.updateUser(id, u);
	}
	
	public void deleteUser(long id) throws SQLException {
		UserDao.deleteUser(id);
	}
	/*--------------------------------------------*/
	
	/*                 Statistic                   */
	public Statistic getStatistic() throws Exception {
		return StatisticDao.getStatistic();
	}
	/*--------------------------------------------*/
	
	/*					Other					  */
	public List<TableOrderWrapper> getTableOrderWrappers(User user) throws SQLException {
		List<TableOrderWrapper> tow = new ArrayList<>();
		
		List<Table> tables = TableDao.getTables();
		for(Table table : tables) {
			ArrayList<OrderEntry> oes = (ArrayList<OrderEntry>)OrderEntryDao.getOrderEntriesOpenByTable(user, table.getIdTable());
			tow.add(new TableOrderWrapper(table, oes));
		}
		
		return tow;
	}
	
	public List<OrderEntryProductWrapper> getOrderEntryProductWrappers(User user) throws SQLException {
		List<OrderEntryProductWrapper> oepw = new ArrayList<>();
		
		List<OrderEntry> oes = OrderEntryDao.getOrderEntriesOpen(user);
		for(OrderEntry oe : oes) {
			Product prod = ProductDao.getProduct(oe.getFkProduct());
			oepw.add(new OrderEntryProductWrapper(oe, prod));
		}
		
		return oepw;
	}
	
	public List<OrderEntryProductWrapper> getOrderEntryProductWrappersByTable(User user, long tableid) throws SQLException {
		List<OrderEntryProductWrapper> oepw = new ArrayList<>();
		
		List<OrderEntry> oes = OrderEntryDao.getOrderEntriesOpenByTable(user, tableid);
		for(OrderEntry oe : oes) {
			Product prod = ProductDao.getProduct(oe.getFkProduct());
			oepw.add(new OrderEntryProductWrapper(oe, prod));
		}
		
		return oepw;
	}
	 
	public List<BillOrderEntriesWrapper> getBillOrderEntriesWrappers(User user) throws SQLException {
		List<BillOrderEntriesWrapper> boew = new ArrayList<>();
		
		List<Bill> bills = BillDao.getBills();
		for(Bill bill : bills) {
			ArrayList<OrderEntry> oes = (ArrayList<OrderEntry>)OrderEntryDao.getOrderEntriesByBill(user, bill);
			if(oes.size() > 0) {
				boew.add(new BillOrderEntriesWrapper(bill, oes));
			}
		}
		
		return boew;
	}
	
	public List<TableStatusWrapper> getTableStatusWrappers(User user) throws SQLException {
		List<TableStatusWrapper> tsw = new ArrayList<>();
		List<Table> tables = TableDao.getTables();
		for(Table table: tables) {
			long cnt = OrderEntryDao.getCountByTable(user, table.getIdTable());
			tsw.add(new TableStatusWrapper(table, cnt > 0));
		}
		
		return tsw;
	}
	/*--------------------------------------------*/
}
