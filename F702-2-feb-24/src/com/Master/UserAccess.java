package com.Master;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import com.Common.Common;

public class UserAccess extends HttpServlet {
	

	
	private static final long serialVersionUID = 3822440991612452007L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext servletContext1 = request.getSession().getServletContext();
		System.out.println(servletContext1);
		String relativeWebPath1 = "";
		String strdirName1 = servletContext1.getRealPath(relativeWebPath1);
		System.out.println(strdirName1);
		System.out.println(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties");  
		 FileInputStream fis = null;
		  Properties prop1 = null;
		  Connection con=null;
			try {
				ResultSet rs=null;
				con=Common.GetConnection(request);
				PreparedStatement ps=null;
		        fis = new FileInputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"); 
		        prop1 = new Properties();
		        prop1.load(fis);
		         
		        String master="/",stock="/",directpo="/",order="/",jobwork="/",stocktransfer="/",report="/",tagging="/";
		        String[] sub_menu= {},sub_menu1={},sub_menu2={},sub_menu3={},sub_menu4={},sub_menu5={},sub_menu6={},sub_menu7={};
		          String key = request.getParameter("type_user");
		          String id_usertype = request.getParameter("id_usertype");
		        //Write File in  Main Module properties File...
		          if(request.getParameter("master") !=null)
			  		{
		        	  master = request.getParameter("master");
			  		}
		          if(request.getParameter("stock") !=null)
		  		{
		        	  stock = request.getParameter("stock");
		  		}
		          if(request.getParameter("directpo") !=null)
			  		{
		        	  directpo = request.getParameter("directpo");
			  		}
		          if(request.getParameter("order") !=null)
			  		{
		        	  order = request.getParameter("order");
			  		}
			          if(request.getParameter("jobwork") !=null)
				  		{
			        	  jobwork = request.getParameter("jobwork");
				  		}
		          if(request.getParameter("stocktransfer") !=null)
			  		{
		        	  stocktransfer = request.getParameter("stocktransfer");
			  		}
		          if(request.getParameter("report") !=null)
			  		{
		        	  report = request.getParameter("report");
			  		}
		          if(request.getParameter("tagging1") !=null)
			  		{
		        	  tagging = request.getParameter("tagging1");
			  		}
		          if((!master.isEmpty()) && (!stock.isEmpty()) && (!directpo.isEmpty()) && (!order.isEmpty()) && (!jobwork.isEmpty()) && (!stocktransfer.isEmpty()) && (!report.isEmpty()) && (!tagging.isEmpty()))
		          {
		          String val = String.join(",", master,stock,directpo,order,jobwork,stocktransfer,report,tagging);
		          System.out.println("Val is" + val);
		          System.out.println("Key is " + key);	
		          
		          try
		          {
		        	 String query = "select User_Type from M_Main_Menu_Access where id_usertype='"+id_usertype+"' ";
		        		System.out.println(query);
		        	  	ps=con.prepareStatement(query);
						rs=ps.executeQuery();
						if(rs.next())
						{
							 query="Update M_Main_Menu_Access set access='"+val+"', User_Type='"+key+"' where id_usertype='"+id_usertype+"' ";
				        		System.out.println(query);
				        		 ps=con.prepareStatement(query);
				        		ps.executeUpdate();
							
						}
						else
						{
							 query="insert into M_Main_Menu_Access(User_Type,access,id_usertype) values('"+key+"','"+val+"','"+id_usertype+"')";
				        		System.out.println(query);
				        		 ps=con.prepareStatement(query);
				        		ps.executeUpdate();
						}
		          }
		          catch(Exception e)
		          {
		        	  System.out.println(e);
		          }
		         
		          prop1.setProperty(key, val);
		          prop1.store(new FileOutputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"),null);
		          System.out.println("Operation completly successfuly for Main module!!");
		          
		        //Write File in Master-Sub-Module properties File...
		          ServletContext servletContext = request.getSession().getServletContext();
					System.out.println(servletContext);
					String relativeWebPath = "";
					String strdirName = servletContext.getRealPath(relativeWebPath);
					System.out.println(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties");  
					 FileInputStream fis1 = null;
					  Properties prop2 = null;
					  fis1 = new FileInputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"); 
				        prop2 = new Properties();
				        prop2.load(fis1);
				         
				          String key1 = request.getParameter("type_user");
				          if(request.getParameterValues("sub_menu") !=null)
					  		{
				        	   sub_menu = request.getParameterValues("sub_menu");
					  		}
				          if(request.getParameterValues("sub_menu1") !=null)
					  		{
				        	  sub_menu1 = request.getParameterValues("sub_menu1");
					  		}
				          if(request.getParameterValues("sub_menu2") !=null)
					  		{
				        	  sub_menu2 = request.getParameterValues("sub_menu2");
					  		}
				          if(request.getParameterValues("sub_menu3") !=null)
					  		{
				        	  sub_menu3 = request.getParameterValues("sub_menu3");
					  		}
				          if(request.getParameterValues("sub_menu4") !=null)
					  		{
				        	  sub_menu4 = request.getParameterValues("sub_menu4");
					  		}
				          if(request.getParameterValues("sub_menu5") !=null)
					  		{
				        	  sub_menu5 = request.getParameterValues("sub_menu5");
					  		}
				          if(request.getParameterValues("sub_menu6") !=null)
					  		{
				        	  sub_menu6 = request.getParameterValues("sub_menu6");
					  		}
				          if(request.getParameterValues("sub_menu7") !=null)
					  		{
				        	  sub_menu7 = request.getParameterValues("sub_menu7");
					  		}
				          List list = new ArrayList(Arrays.asList(sub_menu)); //returns a list view of an array  
					      //returns a list view of str2 and adds all elements of str2 into list  
					      list.addAll(Arrays.asList(sub_menu1));  
					      list.addAll(Arrays.asList(sub_menu2));     
					      list.addAll(Arrays.asList(sub_menu3));     
					      list.addAll(Arrays.asList(sub_menu4)); 
					      list.addAll(Arrays.asList(sub_menu5));     
					      list.addAll(Arrays.asList(sub_menu6));     
					      list.addAll(Arrays.asList(sub_menu7));     
					      Object[] result1 = list.toArray();  
					      String[] result = Arrays.copyOf(result1, result1.length, String[].class);  
				          String val1 = String.join(",", result);
				         
				          System.out.println("Val1 is" + val1);
				          System.out.println("Key1 is" + key1);
				          
				          try
				          {
				        	  String query = "select User_Type from M_Sub_Menu_Access where User_Type='"+key1+"' ";
				        		System.out.println(query);
				        	  	ps=con.prepareStatement(query);
								rs=ps.executeQuery();
								if(rs.next())
								{
									 query="Update M_Sub_Menu_Access set access='"+val1+"', User_Type='"+key1+"' where id_usertype='"+id_usertype+"' ";
						        		System.out.println(query);
						        		 ps=con.prepareStatement(query);
						        		ps.executeUpdate();
									
								}
								else
								{
									 query="insert into M_Sub_Menu_Access(User_Type,access,id_usertype) values('"+key1+"','"+val1+"','"+id_usertype+"')";
						        		System.out.println(query);
						        		 ps=con.prepareStatement(query);
						        		ps.executeUpdate();
								}
				          }
				          catch(Exception e)
				          {
				        	  System.out.println(e);
				          }
				          
				          
				          prop2.setProperty(key1, val1);
				          prop2.store(new FileOutputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"),null);
				          System.out.println("Operation completly successfuly for Master Sub-module!!!");
				         
		        }
		          /* 
		        //Write File in Payroll Main Module properties File...
		           menu = request.getParameter("payroll");
		          if(!menu.isEmpty())
		          {
		          String val = String.join(",", menu);
		          System.out.println("Val is" + val);
		          prop1.setProperty(key, val);
		          prop1.store(new FileOutputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"),null);
		          System.out.println("Operation completly successfuly for payroll-Main module!!");
		          
		        
		          //Write File in Payroll-Sub-Module properties File...
		          ServletContext servletContext = request.getSession().getServletContext();
					System.out.println(servletContext);
					String relativeWebPath = "";
					String strdirName = servletContext.getRealPath(relativeWebPath);
					System.out.println(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties");  
					 FileInputStream fis1 = null;
					  Properties prop2 = null;
					  fis1 = new FileInputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"); 
				        prop2 = new Properties();
				        prop2.load(fis1);
				         
				          String key1 = request.getParameter("type_user");
				          String[] name1 = request.getParameterValues("sub_menu_payroll");
				          String val1 = String.join(",", name1);

				          prop2.setProperty(key1, val1);
				          prop2.store(new FileOutputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"),null);
				          System.out.println("Operation completly successfuly for  payroll-Sub-module!!!");*/
		        }  

		    catch(IOException e){
		    System.out.println(e.getMessage());
		    }
			
			

				
				response.sendRedirect("home.jsp");
	}

	
	
}
