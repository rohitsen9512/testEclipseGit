<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="java.io.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.ServletOutputStream"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
 
int BUFSIZE = 4096;

String fileName1 = request.getParameter("proof_cltn_file");

String filePath;
{
	ServletContext servletContext = request.getSession().getServletContext();
	String relativeWebPath = "InvoiceScanFile/"+fileName1;
	 filePath = servletContext.getRealPath(relativeWebPath);
	 System.out.println("kanhiii"+filePath);
//filePath = getServletContext().getRealPath("") + File.separator + "truncate command.txt";
//System.out.println("kanhiii"+filePath);
//filePath = "../InvoiceScanFile/truncate command.txt";
File file = new File(filePath);
int length   = 0;
ServletOutputStream outStream = response.getOutputStream();
response.setContentType("text/html");
response.setContentLength((int)file.length());
String fileName = (new File(filePath)).getName();
response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
 
byte[] byteBuffer = new byte[BUFSIZE];
DataInputStream in = new DataInputStream(new FileInputStream(file));
 
while ((in != null) && ((length = in.read(byteBuffer)) != -1))
{
outStream.write(byteBuffer,0,length);
}
 
in.close();
outStream.close();
}
%>
</body>
</html>