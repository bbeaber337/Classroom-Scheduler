<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>Upload</title>


</head>
<body>	
<%@ include file="/WEB-INF/AdminMenu.jspf" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Upload an Excel Document
			</h3>
		</div>
	</div>
</div>
<br/><br/><br/>

<form id="data" action="<%= response.encodeURL(menucontext.getContextPath()+"/Admin/Upload") %>">
<input type="hidden" name="fileUpload" value="fileUpload">
	<div class="form-group">	 
		<label for="file">
			File input
		</label>
		<input type="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" required/>
	</div>
	<div class="row-md-5">				
	<button type="submit" class="btn btn-default" onclick="return confirm('Are you sure you want to upload this file?  This will replace all existing class data.')">
		</t>Submit		
	</button>
	</div>				
</form>
<div id="loadingDiv" style="display:none;"></div>
<script>
$(document).ready(//Program a custom submit function for the form
	$("form#data").submit(function(event){
			 
		//disable the default form submission
		event.preventDefault();
		
		//grab all form data  
		var formData = new FormData($(this)[0]);
		
		$.ajax({
		  url: $(this).attr('action'),
		  type: 'POST',
		  data: formData,
		  async: true,
		  cache: false,
		  contentType: false,
		  processData: false,
		  success: function (returndata) {
		    $("#loadingDiv").append("Done");
		    window.location.href("<%= response.encodeURL(menucontext.getContextPath()+"/Admin/Upload/Confirm") %>");
		  }
		});
		$("form#data").hide();
		$("#loadingDiv").text("Loading...");
		$("#loadingDiv").show();
		 
		return false;
	})
);
</script>

</body>
</html>