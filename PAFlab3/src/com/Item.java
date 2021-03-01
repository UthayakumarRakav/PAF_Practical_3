package com;
import java.sql.*;

public class Item {
	public Connection connect() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paflab3", "root", "");
			System.out.println("Connection successfully established");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	
	public String insertItem(String code, String name, String price, String desc) {
		String output = "";
		
		try {
			Connection con = this.connect();
			
			if (con == null) {
				return "Error connecting to database";
			}
			
			
			String query = "insert into items (itemID, itemCode, itemName, itemPrice, itemDesc)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			
			preparedStmt.execute();
			con.close();
			
			output = "inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		
		return output;		
	}
	
	public String readItems() {
		String output = "";
		
		try {
			Connection con = this.connect();
			if (con == null) {
				return "Error while connecting to database for reading";
			}
			
			
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>Item Code</th>"
					+ "<th>Item Name</th>"
					+ "<th>Item Price</th>"
					+ "<th>Item Description</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th>"
					+ "</tr>";
			
			String query = "select * from items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
		
			while (rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = rs.getString("itemPrice");
				String itemDesc = rs.getString("itemDesc");
				
				
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>";
				output += "<td>" + itemDesc + "</td>";
				
				output += "<td><form method='post' action='itemUpdate.jsp'>"
						+ "<input name='itemID' type='hidden' value='" + itemID + "'>"
						+ "<input name='btnUpdate' type='submit' value='Update'>"
								+ "</form></td>"
						+ "<td><form method='post' action='item.jsp'>"
						+ "<input name='itemID' type='hidden' value='" + itemID + "'>"
						+ "<input name='itemCode' type='hidden' value='" + itemCode + "'>"
						+ "<input name='Action' type='hidden' value='delete'>"
						+ "<input name='btnRemove' type='submit' value='Remove'>"
						+ "</form></td></tr>";
			}
			
			con.close();
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the items";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String getItemForUpdate(String itemID) {
		String output = "";
		
		try {
			Connection con = this.connect();
			if (con == null) {
				return "Error while connecting to get item for update";
			}		
			
			String query = "select * from items where itemID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(itemID));
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {				
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = rs.getString("itemPrice");
				String itemDesc = rs.getString("itemDesc");
				
				
				output = "<form method='post' action='item.jsp'>";				
				output += "Item code: <input name=\"itemCode\" type=\"text\" value=\""+ itemCode + "\"><br>";
				output += "Item name: <input name=\"itemName\" type=\"text\" value=\""+ itemName + "\"><br>";
				output += "Item price: <input name=\"itemPrice\" type=\"text\" value=\""+ itemPrice + "\"><br>";
				output += "Item description: <input name=\"itemDesc\" type=\"text\" value=\""+ itemDesc + "\"><br>";
				output += "<input name='itemID' type='hidden' value='" + itemID + "'>";
				output += "<input name='Action' type='hidden' value='update'>";
				output += "<input type=\"submit\" name=\"btnSubmit\" value=\"save\"></form>";
			}
			
			con.close();			
			
		} catch (Exception e) {
			output = "Error while getting item for update";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String updateItem(String itemID, String code, String name, String price, String desc) {
		String output = "";
		
		try {
			Connection con = this.connect();
			
			if (con == null) {
				return "Error connecting to database";
			}
			
			
			String query = "update items set itemCode = ?, itemName = ?, itemPrice = ?, itemDesc = ? where itemID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, Integer.parseInt(itemID));
			
			preparedStmt.execute();
			con.close();
			
			output = "updated successfully";
		} catch (Exception e) {
			output = "Error while updating";
			System.err.println(e.getMessage());
		}
		
		return output;		
	}
	
	public String deleteItem(String itemID) {
		String output = "";
		
		try {
			Connection con = this.connect();
			
			if (con == null) {
				return "Error connecting to database";
			}
			
			
			String query = "delete from items where itemID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
					
			preparedStmt.setInt(1, Integer.parseInt(itemID));
			
			preparedStmt.execute();
			con.close();
			
			output = "deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting";
			System.err.println(e.getMessage());
		}
		
		return output;		
	}
}
