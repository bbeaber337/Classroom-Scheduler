<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">




<title>Insert title here</title>
</head>
<body>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Login Error
			</h3>
			<p class="text-center">
				We're sorry, something seems to have gone wrong.  Click <a href="<%= response.encodeURL(request.getServletContext().getContextPath()+"/Home") %>">here</a> to try again.
			</p>

		</div>
	</div>
</div>


</body>
</html>