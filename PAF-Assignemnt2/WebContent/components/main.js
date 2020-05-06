$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateHospitalForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "PaymentAPI",
		type : t,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalSaveComplete(response.responseText, status);
		}
	});
}); 

function onHospitalSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDSave').val());     
	$("#patiName").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#docName").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#docCharges").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#booknCharges").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#hosptlChargesl").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#pharmeasyBill").val($(this).closest("tr").find('td:eq(5)').text()); 
	

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "PaymentAPI",
		type : "DELETE",
		data : "paymentId=" + $(this).data("paymentId"),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalDeletedComplete(response.responseText, status);
		}
	});
});

function onHospitalDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateHospitalForm() {  
	// NAME  
	if ($("#patiName").val().trim() == "")  {   
		return "Insert fullName.";  
		
	} 
	
	 // DOCTOR  
	if ($("#docName").val().trim() == "")  {   
		return "Insert fullName.";  
		
	} 
	 
	 // Doctor Charges  
	var docCharges = $("#docCharges").val().trim();  
	if (!$.isDouble(docCharges))  {   
		return "Insert a double value for Doctor Charges.";  
		
	}
	 
	 // Booking Charges  
	var booknCharges = $("#booknCharges").val().trim();  
	if (!$.isDouble(booknCharges))  {   
		return "Insert a double value for Doctor Charges.";  
		
	}
	
	// Hospital Charges  
	var hosptlChargesl = $("#hosptlChargesl").val().trim();  
	if (!$.isDouble(hosptlChargesl))  {   
		return "Insert a double value for Hospital Charges .";  
		
	}
	
	// Pharmeasy Charges  
	var pharmeasyBill = $("#pharmeasyBill").val().trim();  
	if (!$.isDouble(pharmeasyBill))  {   
		return "Insert a double value for Pharmeasy Charges .";  
		
	} 
	 
	 
	 return true; 
	 
}
