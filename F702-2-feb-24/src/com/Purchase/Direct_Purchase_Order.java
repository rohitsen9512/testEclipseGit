package com.Purchase;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class Direct_Purchase_Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		HashMap<String, String> map =new HashMap<String,String>();
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		Enumeration elemet = request.getParameterNames();

		String paramName = "";
		String paramValues = "";
		 try
		 {
		        
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      String[] parts = paramName.split("_");
		      paramValues = request.getParameter(paramName);
		      if(parts[0].equals("dt") && !paramValues.equals(""))
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  
		    	  Date d = userDateFormat.parse(paramValues);  
			      map.put(paramName,dateFormatNeeded.format(d));
		      }
		      else
		      {
		    	  paramValues = request.getParameter(paramName);
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		    
		    
		String action = "",frieght="0",insurance="0",dt_po="",no_quot="",id_curr="",id="0",id_req="",confirm="1",dt_to="",dt_frm="",value="",tot_prc="",id_po="",gr_tot="",qty="";
		
		
		if(request.getParameter("dt_po") !=null)
		{
			dt_po = request.getParameter("dt_po");
		}
		if(request.getParameter("no_quot") !=null)
		{
			no_quot = request.getParameter("no_quot");
		}
		if(request.getParameter("qty") !=null)
		{
			qty = request.getParameter("qty");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
		}
		if(request.getParameter("id_req") !=null)
		{
			id_req = request.getParameter("id_req");
		}
		if(request.getParameter("confirm") !=null)
		{
			confirm = request.getParameter("confirm");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("tot_prc") !=null)
		{
			tot_prc = request.getParameter("tot_prc");
		}
		if(request.getParameter("gr_tot1") !=null)
		{
			gr_tot = request.getParameter("gr_tot1");
		}
		if(request.getParameter("frieght") !=null)
		{
			frieght = request.getParameter("frieght");
		}
		if(request.getParameter("insurance") !=null)
		{
			insurance = request.getParameter("insurance");
		}
		if(request.getParameter("id_curr") !=null)
		{
			id_curr = request.getParameter("id_curr");
		}
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			Date currDate = new Date();
			if(dt_frm.equals(""))
			{
				dt_frm = "1990-01-01";
			}
			else
			{
				dt_frm = dateFormatNeeded.format(userDateFormat.parse(dt_frm));
			}
			if(dt_to.equals(""))
			{
				dt_to = dateFormatNeeded.format(currDate);
			}
			else
			{
				dt_to = dateFormatNeeded.format(userDateFormat.parse(dt_to));
			}
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	Double tt =0.0,tt1 =0.0;
	            	if(!id_curr.equals(""))
	            	{
	            		String sql="select rate from M_Exchange_Rate where id_curr="+id_curr+" ";
		            	ps=con.prepareStatement(sql);
						rs=ps.executeQuery();
						if(rs.next())
						{
							tt =(Double.parseDouble(gr_tot) + Double.parseDouble(insurance) + Double.parseDouble(frieght))*rs.getDouble(1);
						}
	            	}
	            	tt1 =(Double.parseDouble(gr_tot) + Double.parseDouble(insurance) + Double.parseDouble(frieght));
					
	            	jobj = AddDirectPurchaseOrder(map,id_po,id_emp_user,gr_tot,qty,tt,tt1,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayPurchaseOrder(id,dt_frm,dt_to,id_emp_user);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayPurchaseOrder(id,"","",id_emp_user);	
	                break;
	            case "DisplayAssetForPO":
	            	jobj = DisplayAssetForPO(id);	
	                break;
	            
	            case "Delete":
	            	jobj = DeletePurchaseOrder(id,confirm);	
	                break;
	            case "DeleteDirectAssetPO":
	            	jobj = DeleteAssetDPO(id , id_po);	
	                break;
	            case "DynGenPOId":
	            	jobj = DynGenPurchaseOrder();	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in P_Request_Asset.");
		}
			finally
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		
	}
	
	public JSONObject DynGenPurchaseOrder()
	{
		JSONObject json=new JSONObject();
		String finyear="",poID2="";
		int PoId=1;
		try 
		{
			ps=con.prepareStatement("select max(id_po) from P_Purchase_Order ");
			rs=ps.executeQuery();
			
				if(rs.next())
				{
					PoId =	rs.getInt(1) +1;
					ps=con.prepareStatement("select substring(convert(varchar(10),std_finance,101),7,10),substring(convert(varchar(10),end_finance,101),9,10) from M_Finance_Year where active_year=1 ");
					rs1=ps.executeQuery();
					if(rs1.next())
					{
						finyear =rs1.getString(1)+"-"+rs1.getString(2);
					}
					if(PoId == 1)
					{
						poID2="PIL/"+finyear+"/1";
					}
					else
					{
						poID2="PIL/"+finyear+"/"+PoId;
					}
				}
				json.put("data", poID2);
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject DeleteAssetDPO(String id ,String id_po)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			if(!id_po.equals("0"))
			{
					String query = "select COUNT(id_po) from P_Purchase_Order_Asset where id_po = "+id_po+" ";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						int rowCount = rs.getInt(1);
						if(rowCount > 1)
						{
							query = "delete P_Purchase_Order_Asset where id_po_asst = "+id+"";
							ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								j=1;
							}
						}
						else
						{
							j=2;
						}
					}
					
			}
			else
			{
				
				String query = "select id_po from P_Purchase_Order_Asset where id_po_asst = "+id+"";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_po = rs.getString(1);
				}
				
				 query = "delete P_Purchase_Order_Asset where id_po_asst = "+id+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					query = "delete P_Purchase_Order where id_po = "+id_po+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=1;
					}
				}
				
			}
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject DeletePurchaseOrder(String id , String confirm)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
						String	query = "delete P_Purchase_Order_Asset where id_po = "+id+"";
							
							PreparedStatement ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "delete P_Purchase_Order where id_po = "+id+"";
								ps=con.prepareStatement(query);
								j=ps.executeUpdate();
								if(j > 0)
								{
									j=1;
								}
							}
						
					
		 
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	
	public JSONObject DisplayAssetForPO(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String	sql ="select poa.*,nm_prod from P_Purchase_Order_Asset poa,M_Prod_Cart pc where poa.id_prod=pc.id_prod and id_po="+id+"";
		
		
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in P_Request_Asset.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayPurchaseOrder(String id,String dt_frm,String dt_to,int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		if(dt_frm.equals(""))
		{
			dt_frm = "1990-01-01";
		}
		
		if(dt_to.equals(""))
		{
			dt_to = sdf.format(currDate);	
		}
		if(id.equals("0"))
		{
		
			sql ="select id_po,no_po,no_quot,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,nm_emp,nm_ven from P_Purchase_Order po,M_Vendor v,M_Emp_User emp where po.id_ven=v.id_ven  and po.add_by=emp.id_emp_user and do_direct=1 and cancel=0 and st_po=0 and dt_po >=(replace(convert(NVARCHAR, '"+dt_frm+"', 106), ' ', '-')) and dt_po <= (replace(convert(NVARCHAR, '"+dt_to+"', 106), ' ', '-'))";
		}
		else
		{
				sql ="select po.*,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, po.dt_quot, 103), ' ', '-')) as dtQuot from P_Purchase_Order po where do_direct=1 and st_po=0 and id_po="+id+"";
		}
		
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Direct_Purchase_Order.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddDirectPurchaseOrder(HashMap<String, String> map,String id_po , int id_emp_user,String tot,String qty,Double gr_tot1,Double gr_tot3,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,id_req=0;
		JSONObject json=new JSONObject();
		JSONObject json1=new JSONObject();
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try 
		{
			
			if(id_po.equals("new"))
			{
				rs = Common.GetColumns("P_Purchase_Order",  request);
				
				
				while (rs.next())
				{
				
					if(!rs.getString(1).equals("id_po"))
					{
						    if (map.containsKey(rs.getString(1)))
						    {
						    	if(colName.equals(""))
						    	{
							    	colName += rs.getString(1);
							    	value += "'"+ map.get(rs.getString(1))+"'";
						    	}
						    	else
						    	{
						    		colName +=","+ rs.getString(1);
							    	value +=", '"+ map.get(rs.getString(1))+"'";
						    		
						    	}
						    }
					  }
				}
				json1 = DynGenPurchaseOrder();	
				
				String sql="insert into P_Purchase_Order("+colName+",add_by,tot,no_po,rem_val_po,gr_tot) values("+value+","+id_emp_user+",'"+tot+"','"+json1.getString("data")+"',"+gr_tot1+","+gr_tot3+")";
					 
				 stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs1 = stmt.getGeneratedKeys();
		            while (rs1.next()) {
		            	id_po = rs1.getString(1);
		            } 
		           
		            json.put("no_po",json1.getString("data"));
			}
		
		
			colName="";value="";
				rs = Common.GetColumns("P_Purchase_Order_Asset",  request);
						
						while (rs.next())
						{
						
							if((!rs.getString(1).equals("id_po_asst")) && (!rs.getString(1).equals("id_po")))
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(colName.equals(""))
								    	{
									    	colName += rs.getString(1);
									    	value += "'"+ map.get(rs.getString(1))+"'";
								    	}
								    	else
								    	{
								    		colName +=","+ rs.getString(1);
									    	value +=", '"+ map.get(rs.getString(1))+"'";
								    		
								    	}
								    }
							  }
						}
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in P_Request_Asset.");
				}
		
		String query="insert into P_Purchase_Order_Asset("+colName+",id_po,rem_qty,tot_prc) values("+value+","+id_po+","+qty+",'"+tot+"')";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				query="select SUM(tot_prc),SUM(poa.tax_prc) from P_Purchase_Order_Asset poa,P_Purchase_Order po where po.id_po=poa.id_po and po.id_po="+id_po+"";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					Double gr_tot=0.0,gr_tot2=0.0;
					String cc="";
					query="select tax_per,no_po,rate,insurance,frieght from P_Purchase_Order po,M_Exchange_Rate er where er.id_curr=po.id_curr and id_po="+id_po+"";
					ps=con.prepareStatement(query);
					rs1=ps.executeQuery();
					if(rs1.next())
					{
						 gr_tot=rs.getDouble(1);
						 gr_tot = + gr_tot + rs1.getDouble(4) +rs1.getDouble(5);
						json.put("no_po",rs1.getString(2));
						
						BigDecimal BDa = new BigDecimal(gr_tot.toString());  
						   
					    BigDecimal BDb = new BigDecimal(rs1.getString(3));  
					   
					    BigDecimal BDc = BDa.multiply(BDb);  
					    cc = BDc.toString();
						
					}
					query="update P_Purchase_Order set tot='"+rs.getString(1)+"',rem_val_po='"+cc+"',gr_tot='"+gr_tot+"' where id_po="+id_po+"";
					ps=con.prepareStatement(query);
					ps.executeUpdate();
					j=1;
				}
				
			}
			
			json.put("id_po",id_po);
			json.put("data",j);
			
			
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
public String getReqNo() throws SQLException{
		
		ResultSet rs1=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		String strReqNo="-" ;
		String strfinyear="";
		String strTemp	=	"";
		int intPoId	=	0;
		boolean boolStatus			=	false;
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		Statement stmt2 = con.createStatement();
		Statement stmt3 = con.createStatement();
		rs=stmt.executeQuery("select year(std_finance) as stdate,year(end_finance) as endyear from M_Finance_Year where active_year='1'");
		if(rs.next()){
			strfinyear=rs.getString("stdate")+"-"+rs.getString("endyear");
			boolStatus=false;
		}else{
			boolStatus=true;
			return strReqNo ;
		}
		
		if(!boolStatus){
		rs=stmt.executeQuery("select * from P_request");
		if(rs.next()){
			rs1=stmt1.executeQuery("select * from P_request where substring(no_req,len(no_req)-8,len(no_req))='"+strfinyear+"'");
			if(rs1.next()){
				rs2=stmt2.executeQuery("select max(id_req) from P_request where substring(no_req,len(no_req)-8,len(no_req))='"+strfinyear+"'");
				if(rs2.next()){
					intPoId=rs2.getInt(1);
					rs3=stmt3.executeQuery("select no_req from P_request where id_req="+intPoId+" ");
					if(rs3.next()){
						strTemp=rs3.getString(1);
						strTemp=strTemp.substring(strTemp.indexOf("q-")+2,strTemp.indexOf("/") );
						int intTempNo = Integer.parseInt(strTemp);
						intTempNo = intTempNo + 1;
						strReqNo="Req-"+intTempNo+"/"+strfinyear;
					}
				}
			}else{
				strReqNo="Req-1/"+strfinyear;
			}
		}else{
			strReqNo="Req-1/"+strfinyear;
		}
	}
	
		return strReqNo ;
	}	
	
	
	
	

}
