package com.sillycat.easyhbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseMain {

	private static Configuration conf = null;

	// setting
	static {
		conf = HBaseConfiguration.create();
	}

	// create table
	public static void creatTable(String tableName, String[] familys)
			throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("table already exists!");
		} else {
			TableName tableNameObject = TableName.valueOf(tableName);
			HTableDescriptor tableDesc = new HTableDescriptor(tableNameObject);
			for (int i = 0; i < familys.length; i++) {
				tableDesc.addFamily(new HColumnDescriptor(familys[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("create table " + tableName + " ok.");
		}
	}

	// delete table
	public static void deleteTable(String tableName) throws Exception {
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println("delete table " + tableName + " ok.");
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	// insert one line
	public static void addRecord(String tableName, String rowKey,
			String family, String qualifier, String value) throws Exception {
		try {
			HTable table = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
					Bytes.toBytes(value));
			table.put(put);
			System.out.println("insert recored " + rowKey + " to table "
					+ tableName + " ok.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// delete one line
	public static void delRecord(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		List list = new ArrayList();
		Delete del = new Delete(rowKey.getBytes());
		list.add(del);
		table.delete(list);
		System.out.println("del recored " + rowKey + " ok.");
	}

	// query for one line
	public static void getOneRecord(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		Get get = new Get(rowKey.getBytes());
		Result rs = table.get(get);
		for (Cell cell : rs.rawCells()) {
			System.out.print(new String(CellUtil.cloneRow(cell)) + " ");
			System.out.print(new String(CellUtil.cloneFamily(cell)) + ":");
			System.out.print(new String(CellUtil.cloneQualifier(cell)) + " ");
			System.out.print(cell.getTimestamp() + " ");
			System.out.println(new String(CellUtil.cloneValue(cell)));
		}
	}

	// list all data
	public static void getAllRecord(String tableName) {
		try {
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan();
			ResultScanner ss = table.getScanner(s);
			for (Result r : ss) {
				for (Cell cell : r.rawCells()) {
					System.out.print(new String(CellUtil.cloneRow(cell)) + " ");
					System.out.print(new String(CellUtil.cloneFamily(cell)) + ":");
					System.out.print(new String(CellUtil.cloneQualifier(cell)) + " ");
					System.out.print(cell.getTimestamp() + " ");
					System.out.println(new String(CellUtil.cloneValue(cell)));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getRangeRecord(String tableName,String startRowKey,String endRowKey){
		try {
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan(startRowKey.getBytes(),endRowKey.getBytes());
			ResultScanner ss = table.getScanner(s);
			for (Result r : ss) {
				for (Cell cell : r.rawCells()) {
					System.out.print(new String(CellUtil.cloneRow(cell)) + " ");
					System.out.print(new String(CellUtil.cloneFamily(cell)) + ":");
					System.out.print(new String(CellUtil.cloneQualifier(cell)) + " ");
					System.out.print(cell.getTimestamp() + " ");
					System.out.println(new String(CellUtil.cloneValue(cell)));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String tablename = "scores";
			String[] familys = { "grade", "course" };
			HBaseMain.creatTable(tablename, familys);

			// add record sillycat
			HBaseMain.addRecord(tablename, "sillycat-20140723", "grade", "", "5");
			HBaseMain.addRecord(tablename, "sillycat-20140723", "course", "math", "97");
			HBaseMain.addRecord(tablename, "sillycat-20140723", "course", "art", "87");
			
			HBaseMain.addRecord(tablename, "sillycat-20130723", "grade", "", "5");
			HBaseMain.addRecord(tablename, "sillycat-20130723", "course", "math", "97");
			HBaseMain.addRecord(tablename, "sillycat-20130723", "course", "art", "87");
			
			HBaseMain.addRecord(tablename, "sillycat-20120723", "grade", "", "5");
			HBaseMain.addRecord(tablename, "sillycat-20120723", "course", "math", "97");
			HBaseMain.addRecord(tablename, "sillycat-20120723", "course", "art", "87");
			// add record kiko
			HBaseMain.addRecord(tablename, "kiko-20140723", "grade", "", "4");
			HBaseMain.addRecord(tablename, "kiko-20140723", "course", "math", "89");

			System.out.println("===========get one record========");
			HBaseMain.getOneRecord(tablename, "sillycat");

			System.out.println("===========show all record========");
			HBaseMain.getAllRecord(tablename);

			System.out.println("===========del one record========");
			HBaseMain.delRecord(tablename, "kiko");
			HBaseMain.getAllRecord(tablename);

			System.out.println("===========show all record========");
			HBaseMain.getAllRecord(tablename);
			
			System.out.print("=============show range record=======");
			HBaseMain.getRangeRecord(tablename, "sillycat-20130101", "sillycat-20141231");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
