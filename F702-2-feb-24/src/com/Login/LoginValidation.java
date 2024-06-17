package com.Login;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import com.Common.Common;
import com.Log.Log;

public class LoginValidation extends HttpServlet {
	private static final long serialVersionUID = 19999L;
	HttpSession session;
	public static String dbName;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String UserName = (String) session.getAttribute("UserName");
			if (UserName.equals("")) {
				String dbname = (String) session.getAttribute("dbname");

				System.out.println(dbname + ".html");
				response.sendRedirect(dbname + ".html");
				response.sendRedirect("index.html");
			} else {
				response.sendRedirect("home.jsp");
			}
		} catch (Exception e) {
			System.out.println("Error " + e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		PreparedStatement ps = null;

		String UrlDB = request.getParameter("compname");
		System.out.println(UrlDB);

		session = request.getSession(true);

		session.setAttribute("dbname", UrlDB);

		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String type_Login = request.getParameter("type_Login");
		try {
			  conn = Common.GetConnection(request);
			/*
			 * 
			 * st=conn.createStatement(); rs=st.
			 * executeQuery("select id_log_user,l.id_emp_user,type_user,id_dept,l.id_flr,l.id_div from M_User_Login l, M_Emp_User emp where nm_login='"
			 * +name+"' and pwd='"
			 * +pwd+"' and status_user=1 and emp.id_emp_user=l.id_emp_user");
			 */
			// String query="select
			// id_log_user,l.id_emp_user,type_user,L.id_dept,EMP.id_flr,EMP.id_div from
			// M_User_Login l, M_Emp_User emp where nm_login=? and pwd=? and status_user=1
			// and emp.id_emp_user=l.id_emp_user";
			String query = "";
			if (type_Login.equals("comp")) {

				query = "select id_log_user,l.id_emp_user,type_user,id_dept,emp.id_flr,l.id_div,id_bu,emp.emp_image,nm_com,c.file_name, c.li_enddt,emp.id_emp , emp.cont_no, mf.std_finance, mf.end_finance,emp.nm_emp from M_User_Login l, M_Emp_User emp,M_Company c, M_Finance_Year mf where nm_login=? and pwd=? and status_user=1 and emp.id_emp_user=l.id_emp_user and mf.active_year='1'";

			} else {
				query = "select nm_ven ,id_ven from m_vendor where ct_mailid=? and password=?  ";
			}

			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, pwd);

			rs = stmt.executeQuery();
			System.out.println(query);
			if (rs.next()) {
				if (type_Login.equals("comp")) {
					session = request.getSession(true);
					session.setAttribute("UserName", name);
					session.setAttribute("UserId", rs.getInt(1));
					session.setAttribute("id_emp_user", rs.getInt(2));
					session.setAttribute("UserType", rs.getString(3));
					session.setAttribute("DeptId", rs.getString(4));
					session.setAttribute("FlrId", rs.getString(5));
					session.setAttribute("DivId", rs.getInt(6));
					session.setAttribute("id_bu", rs.getString(7));
					session.setAttribute("emp_image", rs.getString(8));
					session.setAttribute("nm_com", rs.getString(9));
					session.setAttribute("company_logo", rs.getString(10));
					session.setAttribute("li_enddt", rs.getString(11));
					session.setAttribute("id_emp", rs.getString(12));
					session.setAttribute("cont_no", rs.getString(13));
					session.setAttribute("std_finance", rs.getString(14));
					session.setAttribute("end_finance", rs.getString(15));
					session.setAttribute("nm_emp", rs.getString("nm_emp"));
					String ut = rs.getString(3);
					String li = rs.getString(11);
					// String = rs.getString(11);
					String std = rs.getString(14);
					System.out.println(ut);
					System.out.println(li);
					if (li != null) {
						Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(li);
						Date currentDate = new Date();
						System.out.println("date one is" + date1);
						if (currentDate.after(date1)) {

							response.sendRedirect("contactus.jsp");

						}

						else if (ut.isEmpty() || ut == "NULL")

							response.sendRedirect("portal.jsp");

						else
							response.sendRedirect("home.jsp");

					}
				} else {
					session = request.getSession(true);
					session.setAttribute("UserName", name);
					session.setAttribute("UserId", rs.getString(1));
					session.setAttribute("id_emp_user", rs.getInt(2));
					session.setAttribute("UserType", type_Login);
					session.setAttribute("DeptId", "");
					session.setAttribute("FlrId", "");
					session.setAttribute("DivId", "");
					session.setAttribute("id_bu", "");
					session.setAttribute("id_s_function", "");
					session.setAttribute("dbname", UrlDB);

					response.sendRedirect("home.jsp");

				}

				// request.getRequestDispatcher("home.jsp").forward(request, response);

				/////////////// for Item.........
				// String sql="select NM_MODEL from M_MODEL ";
				String sql = "select nm_assetdiv from M_Asset_Div ";
                  
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				JsonFactory jfactory3 = new JsonFactory();

				ServletContext servletContext3 = getServletConfig().getServletContext();
				String relativeWebPath3 = "InvoiceScanFile/Model.json";
				String strdirName3 = servletContext3.getRealPath(relativeWebPath3);

				/*** write to file ***/
				JsonGenerator jGenerator3 = jfactory3.createJsonGenerator(new File(strdirName3), JsonEncoding.UTF8);

				ResultSetMetaData metaData3 = rs.getMetaData();
				int columns3 = metaData3.getColumnCount();

				jGenerator3.writeStartArray(); // [

				while (rs.next()) {
					// JSONObject jobj2 = new JSONObject();

					jGenerator3.writeStartObject(); // {

					for (int i = 1; i <= columns3; i++) {
						String nameTemp = metaData3.getColumnName(i).toLowerCase();
						// jobj2.put(nameTemp,rs.getString(nameTemp));

						jGenerator3.writeStringField(nameTemp, rs.getString(nameTemp));
					}

					jGenerator3.writeEndObject(); // }
					// jarr.put(jobj2);

				}
				jGenerator3.writeEndArray(); // ]

				jGenerator3.close();
				 
				
				
	/////////////// for based on contact customer Record.........
					// String sql="select NM_MODEL from M_MODEL ";
					String sql1 = "select pstn_ct from O_lead";
	                  
					ps = conn.prepareStatement(sql1);
					rs = ps.executeQuery();
					
					JsonFactory jfactory31 = new JsonFactory();

					ServletContext servletContext31 = getServletConfig().getServletContext();
					String relativeWebPath31 = "InvoiceScanFile/Customer_history.json";
					String strdirName31 = servletContext31.getRealPath(relativeWebPath31);

					/*** write to file ***/
					JsonGenerator jGenerator31 = jfactory31.createJsonGenerator(new File(strdirName31), JsonEncoding.UTF8);

					ResultSetMetaData metaData31 = rs.getMetaData();
					int columns31 = metaData31.getColumnCount();

					jGenerator31.writeStartArray(); // [

					while (rs.next()) {
						// JSONObject jobj2 = new JSONObject();

						jGenerator31.writeStartObject(); // {

						for (int i = 1; i <= columns31; i++) {
							String nameTemp = metaData31.getColumnName(i).toLowerCase();
							// jobj2.put(nameTemp,rs.getString(nameTemp));

							jGenerator31.writeStringField(nameTemp, rs.getString(nameTemp));
						}

						jGenerator31.writeEndObject(); // }
						// jarr.put(jobj2);

					}
					jGenerator31.writeEndArray(); // ]

					jGenerator31.close();
					 
//				String sql1 = "select state from O_Assign_Lead ";
//                
//				ps = conn.prepareStatement(sql1);
//				rs = ps.executeQuery();
//				
//				JsonFactory jfactory1 = new JsonFactory();
//
//				ServletContext servletContext1 = getServletConfig().getServletContext();
//				String relativeWebPath1 = "InvoiceScanFile/Lead_state.json";
//				String strdirName1 = servletContext1.getRealPath(relativeWebPath1);
//
//				/*** write to file ***/
//				JsonGenerator jGenerator1 = jfactory1.createJsonGenerator(new File(strdirName1), JsonEncoding.UTF8);
//
//				ResultSetMetaData metaData1 = rs.getMetaData();
//				int columns1 = metaData1.getColumnCount();
//
//				jGenerator1.writeStartArray(); // [
//
//				while (rs.next()) {
//					// JSONObject jobj2 = new JSONObject();
//
//					jGenerator1.writeStartObject(); // {
//
//					for (int i = 1; i <= columns1; i++) {
//						String nameTemp = metaData1.getColumnName(i).toLowerCase();
//						// jobj2.put(nameTemp,rs.getString(nameTemp));
//
//						jGenerator1.writeStringField(nameTemp, rs.getString(nameTemp));
//					}
//
//					jGenerator1.writeEndObject(); // }
//					// jarr.put(jobj2);
//
//				}
//				jGenerator1.writeEndArray(); // ]
//
//				jGenerator1.close();

				

				// new Log().LogGenerate();
				// new Log().LogGenerate();
				/*
				 * String
				 * sql1="select nm_module,id_choice,nm_attribute,val_attr,nm_field,depend_chkbx,depending_field,depending_attr from M_ChoiceList order by ord_num"
				 * ; System.out.println(sql1); ps=conn.prepareStatement(sql1);
				 * rs=ps.executeQuery();
				 * 
				 * JsonFactory jfactory4 = new JsonFactory();
				 * 
				 * 
				 * ServletContext servletContext4 = getServletConfig().getServletContext();
				 * String relativeWebPath4 = "InvoiceScanFile/I_ChoiceList.json"; String
				 * strdirName4 = servletContext4.getRealPath(relativeWebPath4);
				 * System.out.println(strdirName4);
				 *//*** write to file ***//*
											 * JsonGenerator jGenerator4 = jfactory4.createJsonGenerator(new
											 * File(strdirName4), JsonEncoding.UTF8);
											 * 
											 * ResultSetMetaData metaData4 = rs.getMetaData(); int columns4 =
											 * metaData4.getColumnCount();
											 * 
											 * jGenerator4.writeStartArray(); // [ System.out.println("columns4");
											 * System.out.println(columns4); while(rs.next()) { //JSONObject jobj2 = new
											 * JSONObject();
											 * 
											 * jGenerator4.writeStartObject(); // {
											 * 
											 * for(int i=1;i<=columns4;i++) { String
											 * nameTemp=metaData4.getColumnName(i).toLowerCase();
											 * //jobj2.put(nameTemp,rs.getString(nameTemp));
											 * 
											 * jGenerator4.writeStringField(nameTemp, rs.getString(nameTemp)); }
											 * 
											 * jGenerator4.writeEndObject(); // } //jarr.put(jobj2);
											 * 
											 * } jGenerator4.writeEndArray(); // ]
											 * 
											 * 
											 * 
											 * jGenerator4.close();
											 */

				// configurationItem

				String sql12 = "select id_wh_dyn,id_wh,ds_pro,serial_no from A_Ware_House ";

				ps = conn.prepareStatement(sql12);
				rs = ps.executeQuery();

				JsonFactory jfactory = new JsonFactory();

				ServletContext servletContext = getServletConfig().getServletContext();
				String relativeWebPath = "InvoiceScanFile/Asset_Configurationlist.json";
				String strdirName = servletContext.getRealPath(relativeWebPath);
				System.out.println(strdirName);
				/*** write to file ***/
				JsonGenerator jGenerator = jfactory.createJsonGenerator(new File(strdirName), JsonEncoding.UTF8);

				ResultSetMetaData metaData = rs.getMetaData();
				int columns = metaData.getColumnCount();

				jGenerator.writeStartArray(); // [
				System.out.println("columns");
				System.out.println(columns);
				while (rs.next()) {
					// JSONObject jobj2 = new JSONObject();

					jGenerator.writeStartObject(); // {

					for (int i = 1; i <= columns; i++) {
						String nameTemp = metaData.getColumnName(i).toLowerCase();
						// jobj2.put(nameTemp,rs.getString(nameTemp));

						jGenerator.writeStringField(nameTemp, rs.getString(nameTemp));
					}

					jGenerator.writeEndObject(); // }
					// jarr.put(jobj2);

				}
				jGenerator.writeEndArray(); // ]

				jGenerator.close();

			} else {
				String dbname = (String) session.getAttribute("dbname");
				// System.out.println("index page is----" + dbname+".html");
				// JOptionPane.showMessageDialog(null, "Please Enter correct User name and
				// Password","title",JOptionPane.INFORMATION_MESSAGE);

				response.sendRedirect(dbname + ".html");

				// response.sendRedirect("index.html?status=1");
				// request.getRequestDispatcher("index.html").forward(request, response);
				// request.getRequestDispatcher("index.html").forward(request, response);

			}
		} catch (Exception e) {

			System.out.println("Database conectivity error...." + e);

		} finally {
			try {
				conn.close();
			} catch (Exception e) {

			}
		}

	}

}
