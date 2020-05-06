package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	//A common method to connect to the DB 
		private Connection connect() {
			Connection con = null;
			
			try {
				 Class.forName("com.mysql.jdbc.Driver");
				 //Provide the correct details: DBServer/DBName, username, password 
				 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "");

				//For testing          
				 System.out.print("Successfully connected");
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return con; 
		}
		
		public String readPayment() {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for reading.";
				}

				// Prepare the html table to be displayed
				output = "<table border=\\\"1\\\"><tr><th>Payment ID</th><th>Doctor Name</th><th>Patient Name</th><th>Doctor Charges</th><th>Booking Charges</th><th>Hospital Charges</th><th>Pharmeasy Bill</th><th>Update</th><th>Remove</th></tr>";

				String query = "select * from payment";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				// iterate through the rows in the result set
				while (rs.next()) {
					String paymentId = Integer.toString(rs.getInt("paymentId"));
					String docName = rs.getString("docName");
					String patiName = rs.getString("patiName");
					String docCharges = Double.toString(rs.getDouble("docCharges"));
					String booknCharges = Double.toString(rs.getDouble("booknCharges"));
					String hosptlCharges = Double.toString(rs.getDouble("hosptlCharges"));
					String pharmBill = Double.toString(rs.getDouble("pharmeasyBill"));

					
					// Add into the html table
					//output += "<tr><td>" + itemID + "</td>";
					output += "<tr><td>" + paymentId + "</td>";
					output += "<td>" + docName + "</td>";
					output += "<td>" + patiName + "</td>";
					output += "<td>" + docCharges + "</td>";
					output += "<td>" + booknCharges + "</td>";
					output += "<td>" + hosptlCharges + "</td>";
					output += "<td>" + pharmBill + "</td>";



					// buttons
					output += "<td><input name=\"btnUpdate\" type=\"button\"        "
							+ "value=\"Update\" class=\"btn btn-secondary\"></td>"
							+ "<td><form method=\"post\" action=\"items.jsp\">"
							+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"      "
							+ "class=\"btn btn-danger\">" + "<input name=\"itemID\" type=\"hidden\" value=\"" + paymentId
							+ "\">" + "</form></td></tr>";
				}

				con.close();

				// Complete the html table
				output += "</table>";
				
			} catch (Exception e) {
				output = "Error while reading the payments.";
				System.err.println(e.getMessage());
			}

			return output;

		}
		
		//Insert appointment
		public String insertPayment(String paymentId,String docName, String patiName, String docCharges, String booknCharges,String hosptlCharges, String pharmBill) {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}

				// create a prepared statement
				String query = " INSERT INTO `payment`(`paymentId`, `docName`, `patiName`, `docCharges`, `booknCharges`, `hosptlCharges`, `pharmeasyBill`)" + "VALUES (?,?,?,?,?,?,?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, paymentId);
				preparedStmt.setString(2, docName);
				preparedStmt.setString(3, patiName);
				preparedStmt.setString(4, docCharges);
				preparedStmt.setString(5, booknCharges);
				preparedStmt.setString(6, hosptlCharges);
				preparedStmt.setString(7, pharmBill);


				// execute the statement
				preparedStmt.execute();
				con.close();
				
				//Create JSON Object to show successful msg.
				String newPayment = readPayment();
				output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";

				output = "Inserted successfully";
			} catch (Exception e) {
				//Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting Payment.\"}";   
				System.err.println(e.getMessage());  
			}

			return output;
		}
		
		
		
		//Update appointment
		public String updatePayment(String paymentId,String docName, String patiName, String docCharges, String booknCharges,String hosptlCharges, String pharmBill) {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for updating.";
				}

				// create a prepared statement
				String query = "UPDATE `payment` SET `docName`=?,`patiName`=?,`docCharges`=?,`booknCharges`=?,`hosptlCharges`=?,`pharmeasyBill`=? WHERE `paymentId`=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, docName);
				preparedStmt.setString(2, patiName);
				preparedStmt.setString(3, docCharges);
				preparedStmt.setString(4, booknCharges);
				preparedStmt.setString(5, hosptlCharges);
				preparedStmt.setString(6, pharmBill);
				preparedStmt.setString(7, paymentId);



				// execute the statement
				preparedStmt.execute();
				con.close();
		 
		   //create JSON object to show successful msg
				String newPayment = readPayment();
		   output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		   
		   }   catch (Exception e)   {    
			   output = "{\"status\":\"error\", \"data\": \"Error while Updating Payment Details.\"}";      
			   System.err.println(e.getMessage());   
		   } 
		 
		  return output;  
		  }
		
		public String deleteAppointment(String paymentId) {  
			String output = ""; 
		 
		 try  {   
			 Connection con = connect();
		 
		  if (con == null)   {    
			  return "Error while connecting to the database for deleting.";   
		  } 
		 
		  // create a prepared statement   
		  String query = "DELETE FROM appointment WHERE appID=?"; 
		 
		  PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		  // binding values   
		  preparedStmt.setInt(1, Integer.parseInt(paymentId));       
		  // execute the statement   
		  preparedStmt.execute();   
		  con.close(); 
		 
		  //create JSON Object
		  String newPayment = readPayment();
		  output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		  }  catch (Exception e)  {  
			  //Create JSON object 
			  output = "{\"status\":\"error\", \"data\": \"Error while Deleting Payment.\"}";
			  System.err.println(e.getMessage());  
			  
		 } 
		 
		 return output; 
		 }
}
