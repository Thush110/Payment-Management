<%@page import="model.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/main.js"></script>
</head>
<body>
<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">Payment Management</h1>        
				
				<form id="formPayment" name="formPayment" method="post" action="payment.jsp">  
					Doctor Name:  
					<input id="docName" name="docName" type="text" class="form-control form-control-sm">  
					
					<br> 
					Patient Name:  
					<input id="patiName" name="patiName" type="text" class="form-control form-control-sm">  
					
					<br>
					 Doctor Charges:  
					 <input id="docCharges" name="docCharges" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Booking Charges:  
					 <input id="booknCharges" name="booknCharges" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Hospital Charges:  
					 <input id="hosptlCharges" name="hosptlCharges" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Pharmeasy Charges:  
					 <input id="pharmeasyBill" name="pharmeasyBill" type="text" class="form-control form-control-sm"> 
					 
					 <br> 
					

					 
					 
					 <br>  
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value=""> 
					 
				</form> 
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>  
				<div id="divItemsGrid">   
					<%    
						Payment payObj = new Payment();
						out.print(payObj.readPayment());   
					%>  
					
				</div> 
				  
 			</div>
 		 
 		</div>    
 		
 
	</div> 

</body>

</html>