<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List,org.scribe.model.Response"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<strong><% 
Response resp =  (Response) session.getAttribute("response"); 
if(resp.getCode() == 200) {
	out.println("Write Success");
}
%></strong> <br>
<%= session.getAttribute("status") %> <br> <br>

<strong>Search Results for #foodbangalore</strong> <br>
<%
	List results = (List) session.getAttribute("search");
    for(int i=0; i<results.size(); i++) {
    	out.println(results.get(i) + "<br>");
    }
		
%>
</body>
</html>