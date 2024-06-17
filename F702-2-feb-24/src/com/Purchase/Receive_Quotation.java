package com.Purchase;

import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


import dto.Common.DtoCommon;


public class Receive_Quotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
		String logged=(String) session.getAttribute("log_name");
		
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
		 
		String id_ven = "",nm_prod="",cd_prod="",action = "",word="",dt_recv="",id_req_quot="",id_req_quot_ven="",id="0",id_req="",confirm="1",dt_to="",no_ind="",dt_frm="",value="",ColName="",no_req_val="";
		
		if(request.getParameter("id_ven") !=null)
		{
			id_ven = request.getParameter("id_ven");
		}
		if(request.getParameter("nm_prod") !=null)
		{
			nm_prod = request.getParameter("nm_prod");
		}
		if(request.getParameter("cd_prod") !=null)
		{
			cd_prod = request.getParameter("cd_prod");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("dt_recv") !=null)
		{
			dt_recv = request.getParameter("dt_recv");
		}
		if(request.getParameter("id_req_quot") !=null)
		{
			id_req_quot = request.getParameter("id_req_quot");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id_req_quot_ven") !=null)
		{
			id_req_quot_ven = request.getParameter("id_req_quot_ven");
		}
		if(request.getParameter("no_ind") !=null)
		{
			no_ind = request.getParameter("no_ind");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("no_req_val") !=null)
		{
			no_req_val = request.getParameter("no_req_val");
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
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		try
		{
			String User_Type = (String) session.getAttribute("UserType");
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			Date currDate = new Date();
			if(!dt_recv.equals(""))
			{
				dt_recv = dateFormatNeeded.format(userDateFormat.parse(dt_recv));
			}
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
	        case "UpdateRepeat": 
	        	 String countfncl = request.getParameter("count");
	             int countforfncl=Integer.parseInt(countfncl);
	             String id_quotrep = request.getParameter("id_quot");
	            	System.out.println(id_quotrep);
	        	int jj=0;
            	String col_Val1="";
            	
            	rs = Common.GetColumns("P_QUOTATION",  request);
				while (rs.next())
				{
					if(rs.getString(1) !="id_quot")
					{
						    if (map.containsKey(rs.getString(1)))
						    {
						    	if(col_Val1.equals(""))
						    	{
						    		col_Val1 += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
							    	
						    	}
						    	else
						    	{
						    		col_Val1 += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
						    	}
						    }
					  }
				}
				
				String id_quotqt = request.getParameter("id_quot");
				stmt=con.createStatement();
				System.out.println("update P_Quotation set "+col_Val1+" where id_quot = "+id_quotqt+" ");
        		
            		stmt.executeUpdate("update P_Quotation set "+col_Val1+" where id_quot = "+id_quotqt+" ");
            		jj=1;
            		 for(int i=0;i<countforfncl;i++)
            	       	{
            	        	
            	        	String id_po_asst = request.getParameter("id_quot_asst"+i+"");
            	        	String un_prc = request.getParameter("un_prc"+i+"");
            	        	String tot_prc = request.getParameter("tot_prc"+i+"");
            	       	
            	        	String qty = request.getParameter("qty"+i+"");
            	        	String taxId = request.getParameter("taxId"+i+"");
            	        	String taxVal = request.getParameter("taxVal"+i+"");
            	        	String tax_percent = request.getParameter("taxPercent"+i+"");
            	        	
            	        	String item_prc = request.getParameter("item_prc"+i+"");
            	    		String freight_rate = request.getParameter("freight_rate"+i+"");
            	    		String others_chrg = request.getParameter("others_chrg"+i+"");
            	    		String itemins_chrg = request.getParameter("itemIns_chrg"+i+"");
            	    		String item_remarks = request.getParameter("remarks"+i+"");
            	    		
            	    		//extra charges....
            	    		String frt_rate = request.getParameter("frt_rate"+i+"");
            	        	String add_chrg = request.getParameter("add_chrg"+i+"");
            	        	String frt_text = request.getParameter("frt_text"+i+"");
            	    		String add_chrg1 = request.getParameter("add_chrg1"+i+"");
            	    		String add_chrg2 = request.getParameter("add_chrg2"+i+"");
            	    		String buy_back = request.getParameter("buy_back"+i+"");
            	    		String grnd_tot = request.getParameter("grnd_tot"+i+"");
            	    		String taxId_add = request.getParameter("taxIdadd"+i+"");
            	    		
            	    		String freight_percent = request.getParameter("freight_percent"+i+"");
            	    		
            	    	String 	 queryrepeat="update P_quotation_Asset set freight_percent='"+freight_percent+"', taxId_add='"+taxId_add+"', add_chrg='"+add_chrg+"',frt_text='"+frt_text+"',add_chrg1='"+add_chrg1+"',add_chrg2='"+add_chrg2+"',buy_back='"+buy_back+"',grnd_tot='"+grnd_tot+"', frt_rate='"+frt_rate+"',item_prc='"+item_prc+"',freight_rate='"+freight_rate+"',others_chrg='"+others_chrg+"',tax_percent='"+tax_percent+"',tax_val='"+taxVal+"',id_tax='"+taxId+"',split_qty='"+qty+"',qty='"+qty+"',un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',itemins_chrg='"+itemins_chrg+"',remarks='"+item_remarks+"' where  ID_QUOT_ASST="+id_po_asst+"";
            	    	System.out.println(queryrepeat);	
            	    	ps=con.prepareStatement(queryrepeat);
            				 jj=ps.executeUpdate();
            				 String FRT_RATE="",FRT_TEXT="",ADD_CHRG="",BUY_BACK="",TOT="",discount="",FREIGHT_AMOUNT="",oter_text="",emd_amt=""; 
             		        FRT_RATE = request.getParameter("frt_rate");	
             		        discount = request.getParameter("discount");	
               		      
             		        FRT_TEXT = request.getParameter("frt_text");	
             		        ADD_CHRG = request.getParameter("add_chrg");	
             		        BUY_BACK = request.getParameter("buy_back");	
             		        TOT = request.getParameter("tot");	
             		        FREIGHT_AMOUNT = request.getParameter("freight_amount");	
             		        oter_text = request.getParameter("oter_text");	
               		      
             		       // stmt = con.createStatement();
             		        System.out.println("update p_quotation set oter_text='"+oter_text+"', FREIGHT_AMOUNT='"+FREIGHT_AMOUNT+"', discount='"+discount+"', FRT_RATE='"+FRT_RATE+"',FRT_TEXT='"+FRT_TEXT+"',ADD_CHRG='"+ADD_CHRG+"',BUY_BACK='"+BUY_BACK+"', TOT='"+TOT+"' where id_quot="+id_quotrep+"");
             		        stmt.executeUpdate("update p_quotation set oter_text='"+oter_text+"', FREIGHT_AMOUNT='"+FREIGHT_AMOUNT+"', discount='"+discount+"',FRT_RATE='"+FRT_RATE+"',FRT_TEXT='"+FRT_TEXT+"',ADD_CHRG='"+ADD_CHRG+"',BUY_BACK='"+BUY_BACK+"', TOT='"+TOT+"' where id_quot="+id_quotrep+"");
             		
            			 jj=1;
            	       	}
    			jobj.put("data",jj);
			break;
			
            case "Update":
            
         String count1 = request.getParameter("count");
         int countfor=Integer.parseInt(count1);
     	String msgline="";
            	String id_quot1 = request.getParameter("id_quot");
            	System.out.println(id_quot1);
            	
            	String sqimodi="";
            	int k=0;
            	try
        		{
            	
        			String rate_cont="",dt_rec_quot="",id_curr="",emd_amt="",inst_num="",no_quot="",isnt_type="",file="",remarks="",dt_rate_cont_valid="";
        		  	 dt_rec_quot = request.getParameter("dt_rec_quot");
        		  	//rate_cont = request.getParameter("rate_cont");
        		  	no_quot = request.getParameter("no_quot");
        		  	 id_curr = request.getParameter("id_curr");			
        		  	emd_amt = request.getParameter("emd_amt");			
        		  	inst_num = request.getParameter("inst_num");			
        		  	isnt_type = request.getParameter("isnt_type");			
        		  	file = request.getParameter("file");			
        		  	remarks = request.getParameter("remarks");	
//        		  	//dt_rate_cont_valid = request.getParameter("dt_rate_cont_valid");	
//        		  	Date d1 = null ;
//           		 if(!dt_rate_cont_valid.equals("")){
//           		 	 d1 = userDateFormat.parse(dt_rate_cont_valid); 
//           		 	dt_rate_cont_valid=dateFormatNeeded.format(d1);
//           		 }
//           		 
   		   Date d = userDateFormat.parse(dt_rec_quot); 
  		   System.out.println(dt_rate_cont_valid);
       			 System.out.println(dateFormatNeeded.format(d));
       			stmt = con.createStatement();
       	
        sqimodi="update p_quotation set dt_rec_quot='"+dateFormatNeeded.format(d)+"',no_quot='"+no_quot+"',id_curr='"+id_curr+"',emd_amt='"+emd_amt+"',inst_num='"+inst_num+"', isnt_type='"+isnt_type+"',remarks='"+remarks+"' where id_quot="+id_quot1+"";	
        System.out.println(sqimodi);
        stmt.executeUpdate(sqimodi);	
        
        
        String type="receive";
       for(int i=0;i<countfor;i++)
       	{					
        	k=0;
        	String id_po_asst = request.getParameter("id_quot_asst"+i+"");
        	String un_prc = request.getParameter("un_prc"+i+"");
        	String tot_prc = request.getParameter("tot_prc"+i+"");
       	
        	String qty = request.getParameter("qty"+i+"");
        	String taxId = request.getParameter("taxId"+i+"");
        	String taxVal = request.getParameter("taxVal"+i+"");
        	String tax_percent = request.getParameter("taxPercent"+i+"");
        	
        	String item_prc = request.getParameter("item_prc"+i+"");
    		String freight_rate = request.getParameter("freight_rate"+i+"");
    		String others_chrg = request.getParameter("others_chrg"+i+"");
    		String itemins_chrg = request.getParameter("itemIns_chrg"+i+"");
    		String item_remarks = request.getParameter("remarks"+i+"");
    		
    		//extra charges....
    		String frt_rate = request.getParameter("frt_rate"+i+"");
        	String add_chrg = request.getParameter("add_chrg"+i+"");
        	String frt_text = request.getParameter("frt_text"+i+"");
    		String add_chrg1 = request.getParameter("add_chrg1"+i+"");
    		String add_chrg2 = request.getParameter("add_chrg2"+i+"");
    		String buy_back = request.getParameter("buy_back"+i+"");
    		String grnd_tot = request.getParameter("grnd_tot"+i+"");
    		String taxId_add = request.getParameter("taxIdadd"+i+"");
    		
    		String freight_percent = request.getParameter("freight_percent"+i+"");
    		
    		
    		String query="";
    		String id_ven_selected="";
    		 query="select id_ven_selected from P_quotation_Asset where ID_QUOT_ASST="+id_po_asst+"";
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
		if(rs.next()){
			id_ven_selected=rs.getString("id_ven_selected");
		}
		System.out.println(id_ven_selected);
		/*if(id_ven_selected=="null"){

        	System.out.println("update P_quotation_Asset set taxId_add='"+taxId_add+"', add_chrg='"+add_chrg+"',frt_text='"+frt_text+"',add_chrg1='"+add_chrg1+"',add_chrg2='"+add_chrg2+"',buy_back='"+buy_back+"',grnd_tot='"+grnd_tot+"', frt_rate='"+frt_rate+"',item_prc='"+item_prc+"',freight_rate='"+freight_rate+"',others_chrg='"+others_chrg+"',tax_percent='"+tax_percent+"',tax_val='"+taxVal+"',id_tax='"+taxId+"',qty='"+qty+"',un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',itemins_chrg='"+itemins_chrg+"',remarks='"+item_remarks+"' where  ID_QUOT_ASST="+id_po_asst+"");
    			  query="update P_quotation_Asset set taxId_add='"+taxId_add+"', add_chrg='"+add_chrg+"',frt_text='"+frt_text+"',add_chrg1='"+add_chrg1+"',add_chrg2='"+add_chrg2+"',buy_back='"+buy_back+"',grnd_tot='"+grnd_tot+"', frt_rate='"+frt_rate+"',item_prc='"+item_prc+"',freight_rate='"+freight_rate+"',others_chrg='"+others_chrg+"',tax_percent='"+tax_percent+"',tax_val='"+taxVal+"',id_tax='"+taxId+"',qty='"+qty+"',un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',itemins_chrg='"+itemins_chrg+"',remarks='"+item_remarks+"' where  ID_QUOT_ASST="+id_po_asst+"";
    			 ps=con.prepareStatement(query);
      			k=ps.executeUpdate();
            
		}
		else{

        	System.out.println("update P_quotation_Asset set taxId_add='"+taxId_add+"', add_chrg='"+add_chrg+"',frt_text='"+frt_text+"',add_chrg1='"+add_chrg1+"',add_chrg2='"+add_chrg2+"',buy_back='"+buy_back+"',grnd_tot='"+grnd_tot+"', frt_rate='"+frt_rate+"',item_prc='"+item_prc+"',freight_rate='"+freight_rate+"',others_chrg='"+others_chrg+"',tax_percent='"+tax_percent+"',tax_val='"+taxVal+"',id_tax='"+taxId+"',qty='"+qty+"',un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',itemins_chrg='"+itemins_chrg+"',remarks='"+item_remarks+"' where id_ven_selected='"+id_ven_selected+"' and ID_QUOT_ASST="+id_po_asst+"");
    			  query="update P_quotation_Asset set taxId_add='"+taxId_add+"', add_chrg='"+add_chrg+"',frt_text='"+frt_text+"',add_chrg1='"+add_chrg1+"',add_chrg2='"+add_chrg2+"',buy_back='"+buy_back+"',grnd_tot='"+grnd_tot+"', frt_rate='"+frt_rate+"',item_prc='"+item_prc+"',freight_rate='"+freight_rate+"',others_chrg='"+others_chrg+"',tax_percent='"+tax_percent+"',tax_val='"+taxVal+"',id_tax='"+taxId+"',qty='"+qty+"',un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',itemins_chrg='"+itemins_chrg+"',remarks='"+item_remarks+"' where id_ven_selected='"+id_ven_selected+"' and  ID_QUOT_ASST="+id_po_asst+"";
    			 ps=con.prepareStatement(query);
      			k=ps.executeUpdate();
            
		}*/
		String id_produp="";
		String QryAudit="select * from P_quotation_Asset where ID_QUOT_ASST="+id_po_asst+"";
	
		  query="update P_quotation_Asset set freight_percent='"+freight_percent+"', taxId_add='"+taxId_add+"', add_chrg='"+add_chrg+"',frt_text='"+frt_text+"',add_chrg1='"+add_chrg1+"',add_chrg2='"+add_chrg2+"',buy_back='"+buy_back+"',grnd_tot='"+grnd_tot+"', frt_rate='"+frt_rate+"',item_prc='"+item_prc+"',freight_rate='"+freight_rate+"',others_chrg='"+others_chrg+"',tax_percent='"+tax_percent+"',tax_val='"+taxVal+"',id_tax='"+taxId+"',split_qty='"+qty+"',qty='"+qty+"',un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',itemins_chrg='"+itemins_chrg+"',remarks='"+item_remarks+"' where  ID_QUOT_ASST="+id_po_asst+"";
			 ps=con.prepareStatement(query);
			k=ps.executeUpdate();
     
		
		
    			 String FRT_RATE="",FRT_TEXT="",ADD_CHRG="",BUY_BACK="",TOT="",discount="",FREIGHT_AMOUNT="",oter_text=""; 
    		        FRT_RATE = request.getParameter("frt_rate");	
    		        discount = request.getParameter("discount");	
      		      
    		        FRT_TEXT = request.getParameter("frt_text");	
    		        ADD_CHRG = request.getParameter("add_chrg");	
    		        BUY_BACK = request.getParameter("buy_back");	
    		        TOT = request.getParameter("tot");	
    		        FREIGHT_AMOUNT = request.getParameter("freight_amount");	
    		        emd_amt = request.getParameter("emd_amt");	
    		        oter_text = request.getParameter("oter_text");	
      		      
    		       // stmt = con.createStatement();
    		        System.out.println("update p_quotation set oter_text='"+oter_text+"', FREIGHT_AMOUNT='"+FREIGHT_AMOUNT+"', discount='"+discount+"', FRT_RATE='"+FRT_RATE+"',FRT_TEXT='"+FRT_TEXT+"',ADD_CHRG='"+ADD_CHRG+"',BUY_BACK='"+BUY_BACK+"', TOT='"+TOT+"' where id_quot="+id_quot1+"");
    		        stmt.executeUpdate("update p_quotation set oter_text='"+oter_text+"', FREIGHT_AMOUNT='"+FREIGHT_AMOUNT+"', discount='"+discount+"',FRT_RATE='"+FRT_RATE+"',FRT_TEXT='"+FRT_TEXT+"',ADD_CHRG='"+ADD_CHRG+"',BUY_BACK='"+BUY_BACK+"', TOT='"+TOT+"' where id_quot="+id_quot1+"");
    		       	
       		k=1;
       		
        } 	
     					
        	jobj.put("data", k);					
        				
        			}
        			catch(Exception e)
        				{
        					System.out.println("Some error in P_Purchase_Order.");
        				}
        		
                break;
			
			
	        case "Modifyrecvquotation":
            	jobj = DisplayModifyRequest(id,no_ind,word,id_emp_user,User_Type);	
                break;
		        case "DisplayQuotationDetails":
	            	jobj = DisplayQuotationDetails(no_ind);	
	                break;
	            case "Display":
	            	jobj = DisplayReceiveQuotation(id,dt_frm,dt_to,id_emp_user,word,User_Type);	
	                break;
	            
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	            break;
	            case "RateContract":
	            	jobj = RateContract(id_ven,nm_prod,cd_prod);	
	            break;
	            case "ViewDisplay":
	            	jobj = Displayrepeat_po_financial(word);	
	                break;  
	            case "CheckDate":
	            	jobj = CheckReceivedDate(dt_recv,id_req_quot);	
	                break;
	           
	            case "Save":
	            	String colName="";
	            	
	            	value="";
            		int j=0,id_quot=0;
            		String count[] = request.getParameterValues("count");
            		String id_prod[] = request.getParameterValues("id_prod");
            		String id_grp[] = request.getParameterValues("id_grp");
            		String id_sgrp[] = request.getParameterValues("id_sgrp");
            		
            			
	            	try
	        		{
	            		
	        			stmt = con.createStatement();
	        				rs = Common.GetColumns("P_QUOTATION",  request);
	        						while (rs.next())
	        						{
	        						
	        							if(rs.getString(1) !="id_quot")
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
	        					System.out.println("Some error in Receive_Quotation."+e.toString());
	        				}
	            	System.out.println(colName);
	        		String query="insert into P_Quotation("+colName+",add_by) values("+value+","+id_emp_user+")";
	        		System.out.println(query);
	        		
	        		stmt=con.createStatement();
					stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
					rs1 = stmt.getGeneratedKeys();
	        	/*	PreparedStatement stmt_pre = con.prepareStatement(query, new String[] { "id_quot" });
					stmt_pre.executeUpdate();
					rs1 = stmt_pre.getGeneratedKeys();
			     */   
					if (rs1.next()) 
					{
			           	id_quot = rs1.getInt(1);
			           	stmt=con.createStatement();
	        			for(int i=0;i<count.length;i++)
	        			{					
	        				j=0;
	        				String un_prc = request.getParameter("un_prc"+count[i]+"");
	                		String remarks = request.getParameter("remarks"+count[i]+"");
	                		String id_tax1 = request.getParameter("id_tax1"+count[i]+"");
	                		String id_tax2 = request.getParameter("id_tax2"+count[i]+"");
	                		String tax_val1 = request.getParameter("tax_val1"+count[i]+"");
	                		String tax_val2 = request.getParameter("tax_val2"+count[i]+"");
	                		String buyback = request.getParameter("buyback"+count[i]+"");
	                		String others = request.getParameter("others"+count[i]+"");
	                		String qty = request.getParameter("qty"+count[i]+"");
	                		String tot_prc = request.getParameter("gr_tot"+count[i]+"");
		                	
	                		
	                		System.out.println("insert into P_Quotation_Asset(un_prc,remarks,id_tax1,id_tax2,tax_val1,tax_val2,buyback,others,qty,id_quot,id_prod,id_grp,id_sgrp,tot_prc)values('"+un_prc+"','"+remarks+"','"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+others+"','"+qty+"','"+id_quot+"','"+id_prod[i]+"','"+id_grp[i]+"','"+id_sgrp[i]+"','"+tot_prc+"')");
	        				
	                		/*stmt.executeUpdate("insert into P_Quotation_Asset(un_prc,remarks,id_tax1,id_tax2,tax_val1,tax_val2,buyback,others,qty,id_quot,id_prod,id_grp,id_sgrp,tot_prc)"
	                				+ "values('"+un_prc+"','"+remarks+"','"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+others+"','"+qty+"','"+id_quot+"','"+id_prod[i]+"','"+id_grp[i]+"','"+id_sgrp[i]+"','"+tot_prc+"')");
	        			*/	
	                		int id_quot_asst=0;
	                		String insertquotasset="insert into P_Quotation_Asset(un_prc,remarks,id_tax1,id_tax2,tax_val1,tax_val2,buyback,others,qty,id_quot,id_prod,id_grp,id_sgrp,tot_prc)"
	                				+ "values('"+un_prc+"','"+remarks+"','"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+others+"','"+qty+"','"+id_quot+"','"+id_prod[i]+"','"+id_grp[i]+"','"+id_sgrp[i]+"','"+tot_prc+"')";
	                		stmt.executeUpdate(insertquotasset,Statement.RETURN_GENERATED_KEYS);
	    					rs1 = stmt.getGeneratedKeys();
	    					if (rs1.next()) 
	    					{
	    						
	    						id_quot_asst = rs1.getInt(1);
	    					}
	                		System.out.println("insert into Negotiation_History(id_quot_asst,old_qty,old_remarks,old_un_prc,old_others,old_id_tax1,old_id_tax2,old_tax_val1,old_tax_val2,old_buyback,old_tot_prc,un_prc,remarks,id_tax1,id_tax2,tax_val1,tax_val2,buyback,others,qty,tot_prc)"
	            					+ " values('"+qty+"','"+remarks+"','"+un_prc+"','"+others+"','"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+tot_prc+"','"+un_prc+"','"+remarks+"','"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+others+"','"+qty+"','"+tot_prc+"')");
	        				
	                		stmt.executeUpdate("insert into Negotiation_History(id_quot,id_quot_asst,old_qty,old_remarks,old_un_prc,old_others,old_id_tax1,old_id_tax2,old_tax_val1,old_tax_val2,old_buyback,old_tot_prc,un_prc,remarks,id_tax1,id_tax2,tax_val1,tax_val2,buyback,others,qty,tot_prc)"
	            					+ " values("+id_quot+","+id_quot_asst+",'"+qty+"','"+remarks+"','"+un_prc+"','"+others+"','"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+tot_prc+"','"+un_prc+"','"+remarks+"','"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+others+"','"+qty+"','"+tot_prc+"')");
	        				k=1;
	        			
	                		
	                		j=1;
	        			}
	        			
	        		}
			           // String sql="update P_Req_Quot_Vendor set st_rec_quot='Yes' where id_req_quot_ven="+id_req_quot_ven+"";
			            stmt.executeUpdate("update P_Req_Quot_Vendor set st_rec_quot='Yes' where id_req_quot_ven="+id_req_quot_ven+""); 
			            System.out.println("update P_Req_Quot_Vendor set st_rec_quot='Yes' where id_req_quot_ven="+id_req_quot_ven+"");
			            

	        			// Mail Trigger......
			            /*no_ind = map.get("no_ind");
	        			String replyMailId="",name="";
		            	String mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_Request_Quotation rq where emp.id_emp_user=rq.id_apprv and no_ind='"+no_ind+"'";
		            	
		            	DtoCommon dtcm = new DtoCommon();
		    			rs = dtcm.GetResultSet(mailSql);
		    			if(rs.next())
		    			{
		    				replyMailId = rs.getString(1);
		    				name = rs.getString(2);
		    			}
		    			mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_Request_Quotation rq where emp.id_emp_user=rq.add_by and no_ind='"+no_ind+"'";
		    			
		    			List<String> recipients = dtcm.ReturnListData(mailSql);
		    			
		    			String link = dtcm.ReturnParticularMessage("link");
		    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		    			String mailSubject = dtcm.ReturnParticularMessage("purchaseReqQuot");
		    			
		    			String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
		    						  "Please approve the quotation having indent number <b>("+no_ind+")</b>"+
		    						  "<br><br><br>"+link+""+
									  "<p>"+footerMsg+"</p>";
		    		
		    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);*/
		    	        j=1;    
			            
		    	      
			            
			            jobj.put("data",j);
	            	break;  
	           
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Receive_Quotation."+e.toString());
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
	
	public JSONObject Displayrepeat_po_financial(String word)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		String tempSql ="";
		if(!word.isEmpty())
		 tempSql =" and (REGEXP_LIKE(no_quot,'"+word+"','i') or REGEXP_LIKE(ci.no_ind,'"+word+"','i') or REGEXP_LIKE(nm_ven,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
		
		sql = "select ci.no_ind,no_quot,to_char(to_date(dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_approv1,nm_emp,nm_ven,q.id_quot,q.id_ven from P_Quotation q,P_Create_Indent ci,M_Emp_User emp,M_Vendor v "+
				" where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_ven=v.id_ven and st_po='No' and  q.order_type!='1'   and st_quot='Accepted' "+tempSql+" order by id_quot DESC";

		
		System.out.println(sql);
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select no_quot from P_Quotation where "+ColName+" = '"+value+"'";
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",1);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject RateContract(String id_ven , String nm_prod,String cd_prod )
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String temp="";
		if(nm_prod.equals("nm_prod")){
			temp="  cd_prod='"+cd_prod+"'";
		}else{
			temp="  nm_prod='"+nm_prod+"'";
		}
		
		String query = "select distinct item_prc from P_Quotation q,P_Quotation_Asset qa ,M_Prod_Cart pc  where  q.id_quot=qa.id_quot " +
				"  and qa.id_prod=pc.id_prod and rate_cont='Yes' and  "+temp+"  and id_ven='"+id_ven+"' ";
		System.out.println(query);
		try 
		{
		 
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next()){
				jobj.put("item_prc",rs.getString(1));
			}
			else{
				jobj.put("item_prc","0.0");
			}
			 
			
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return jobj;
	}
	public JSONObject CheckReceivedDate(String dt_recv,String id_req_quot)
	{
		JSONObject json=new JSONObject();
		
		String query = "select to_char(to_date(dt_req_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_req_quot from P_Request_Quotation where dt_req_quot >'"+dt_recv+"' and id_req_quot='"+id_req_quot+"'";
		
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("data",0);
				json.put("dt_req_quot",rs.getString(1));
			}
			else
			{
				json.put("data",1);
				
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	
	
	public JSONObject DisplayReceiveQuotation(String id,String dt_frm,String dt_to,int id_emp_user,String word,String User_Type)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			stmt=con.createStatement();
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dt_frm.equals(""))
		{
			dt_frm = "1990-01-01";
		}
		
		if(dt_to.equals(""))
		{
			dt_to = sdf.format(currDate);	
		}
		
		String tempSql ="";
		if(!word.isEmpty())
		 tempSql =" and (REGEXP_LIKE(rq.no_ind,'"+word+"','i') or REGEXP_LIKE(nm_ven,'"+word+"','i') or REGEXP_LIKE(to_char (to_date(rq.dt_req_quot,'YYYY-MM-DD'), 'DD/MM/YYYY'),'"+word+"','i') or REGEXP_LIKE(emp.nm_emp,'"+word+"','i'))";
		
			/*sql ="select distinct(rq.id_req_quot),id_req_quot_ven,rq.no_ind,nm_ven,rqv.id_ven from P_Request_Quotation rq,M_Vendor v,P_Request_Quotation_Asset rqa,P_Req_Quot_Vendor rqv "+
					" where rqv.no_ind=rq.no_ind and rqv.id_ven=v.id_ven and st_rec_quot='No' and rq.no_ind=rqa.no_ind and st_recv_quot='No' and rq.id_ven=v.id_ven order by rq.no_ind";
		*/
		if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ){
		sql ="select distinct(rq.id_req_quot),id_req_quot_ven,rq.no_ind,nm_ven,rqv.id_ven,rq.dt_req_quot as pppkkpk,emp.nm_emp, (replace(convert(NVARCHAR, rq.dt_req_quot, 103), ' ', '-')) as dt_req_quot  from P_Request_Quotation rq,M_Vendor v,P_Request_Quotation_Asset rqa,P_Req_Quot_Vendor rqv,M_Emp_User emp "+
				" where rqv.no_ind=rq.no_ind and rqv.id_ven=v.id_ven and st_rec_quot='No' and rq.no_ind=rqa.no_ind and st_recv_quot='No' and rq.id_ven=v.id_ven and st_recv_quot='No' and emp.id_emp_user=rq.add_by "+tempSql+" order by pppkkpk desc" ;
		}
		else{
			sql ="select distinct(rq.id_req_quot),id_req_quot_ven,rq.no_ind,nm_ven,rqv.id_ven,rq.dt_req_quot as pppkkpk,emp.nm_emp,(replace(convert(NVARCHAR, rq.dt_req_quot, 103), ' ', '-')) as dt_req_quot  from P_Request_Quotation rq,M_Vendor v,P_Request_Quotation_Asset rqa,P_Req_Quot_Vendor rqv,M_Emp_User emp "+
					" where rqv.no_ind=rq.no_ind and rqv.id_ven=v.id_ven and st_rec_quot='No' and rq.no_ind=rqa.no_ind and st_recv_quot='No' and rq.id_ven=v.id_ven and st_recv_quot='No' and emp.id_emp_user=rq.add_by "+tempSql+"  order by pppkkpk desc" ;
		
		}
		if(User_Type.equals("vend")  ){
			/*sql ="select distinct(rq.id_req_quot),id_req_quot_ven,rq.no_ind,nm_ven,rqv.id_ven,rq.dt_req_quot as pppkkpk,emp.nm_emp,(replace(convert(NVARCHAR, rq.dt_req_quot, 103), ' ', '-')) as dt_req_quot  from P_Request_Quotation rq,M_Vendor v,P_Request_Quotation_Asset rqa,P_Req_Quot_Vendor rqv,M_Emp_User emp "+
					" where rqv.no_ind=rq.no_ind and rqv.id_ven=v.id_ven and st_rec_quot='No' and rq.no_ind=rqa.no_ind and st_recv_quot='No' and rq.id_ven=v.id_ven and st_recv_quot='No' and emp.id_emp_user=rq.add_by and v.id_ven='"+id_emp_user+" '  and dt_end_quot >= GETDATE() and dt_start_quot <= GETDATE()  order by pppkkpk desc" ;
			*/
			sql ="select distinct(rq.id_req_quot),id_req_quot_ven,rq.no_ind,nm_ven,rqv.id_ven,rq.dt_req_quot as pppkkpk,emp.nm_emp,(replace(convert(NVARCHAR, rq.dt_req_quot, 103), ' ', '-')) as dt_req_quot  from P_Request_Quotation rq,M_Vendor v,P_Request_Quotation_Asset rqa,P_Req_Quot_Vendor rqv,M_Emp_User emp "+
					" where rqv.no_ind=rq.no_ind and rqv.id_ven=v.id_ven and st_rec_quot='No' and rq.no_ind=rqa.no_ind and st_recv_quot='No' and rq.id_ven=v.id_ven and st_recv_quot='No' and emp.id_emp_user=rq.add_by and v.id_ven='"+id_emp_user+" '  order by pppkkpk desc" ;
			
		}
		System.out.println(sql); 	
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	String ProdName="";
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    	
		    	rs1=stmt.executeQuery("select nm_prod from M_Prod_Cart pc,P_Request_Quotation_Asset rqa where rqa.id_prod=pc.id_prod and rqa.id_req_quot="+rs.getString(1)+"");
		    	while(rs1.next())
		    	{
		    		if(ProdName.equals(""))
		    			ProdName =rs1.getString(1);
		    		else
					ProdName +=","+rs1.getString(1);
				}
		    	jobj2.put("nm_prod",ProdName);
		    	
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Receive_Quotation."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayQuotationDetails(String no_ind)
	{
		
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();

		try 
		{
		/*	String sql = "select  case when dt_rate_cont_valid = '1' THEN '' ELSE  to_char (to_date(dt_rate_cont_valid,'YYYY-MM-DD'), 'DD/MM/YYYY') END as dt_rate_cont_valid,ia.*,ci.id_ind,ci.dt_ind,ci.st_ind,ci.ind_add_by,nm_assetdiv,nm_s_assetdiv,nm_prod,cd_prod,uom,hsn_code from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,M_Prod_Cart pc "+
					" where ci.no_ind=ia.no_ind and ia.id_grp=ad.id_assetdiv and ia.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_prod "+
					" and ci.no_ind='"+no_ind+"' order by ID_IND_ASST";
					
			*/
			
			String sql="select  ia.*,ci.id_ind,ci.dt_ind,ci.st_ind,ci.ind_add_by,nm_assetdiv,nm_s_assetdiv,nm_model,cd_model"+
                 "   from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,m_model pc  where ci.no_ind=ia.no_ind and "+
                  "     ia.id_grp=ad.id_assetdiv and ia.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_model  and ci.no_ind='"+no_ind+"' order by ID_IND_ASST";

					System.out.println(sql);
					ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					
					   ResultSetMetaData metaData = rs.getMetaData();
					    int columns = metaData.getColumnCount();
					    while(rs.next())
					    {
					    	JSONObject jobj2 = new JSONObject();
					    	for(int i=1;i<=columns;i++)
					    	{
					    		String name=metaData.getColumnName(i).toLowerCase();
					    		jobj2.put(name,rs.getString(name));
					    	}
					    	jarr.put(jobj2);
					    }
					
				sql="select (quot_t_c) from P_Request_Quotation where no_ind='"+no_ind+"'";
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					json.put("quot_t_c",rs.getString(1));
				}
				else
				{
					json.put("quot_t_c","-");
				}
				json.put("data",jarr);
				
				
		}
		catch(Exception e)
		{
			System.out.println("Error in Receive_Quotation"+e.toString());
		}
		
			
			
		return json;
	}
	
	public JSONObject DisplayModifyRequest(String id,String no_ind,String word,int id_emp_user,String User_Type)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			System.out.println(User_Type);
		String tempSql ="";
		if(!word.isEmpty())
			tempSql =" and (REGEXP_LIKE(no_quot,'"+word+"','i') or REGEXP_LIKE(ci.no_ind,'"+word+"','i') or REGEXP_LIKE(nm_ven,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
		 	
		
		
		if(id.equals("0"))
		{
			if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ){
			sql="select nm_emp,ci.no_ind,no_quot,nm_ven,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Vendor v,m_emp_user emp  where emp.id_emp_user=q.add_by and q.no_ind=ci.no_ind  and q.id_ven=v.id_ven and q.order_type='1'  and st_po='No'  "+tempSql+"  order by id_quot desc";
			}
			else{
				sql="select nm_emp,ci.no_ind,no_quot,nm_ven,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Vendor v,m_emp_user emp  where emp.id_emp_user=q.add_by and q.no_ind=ci.no_ind  and q.id_ven=v.id_ven  and q.order_type='1'  and st_po='No'  "+tempSql+"   order by id_quot desc";
					
			}
			}
		else
		{
			 sql="select oter_text,freight_percent,qa.remarks as remk,discount,freight_amount,to_char(to_date(q.dt_rec_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_rec_quot,to_char(to_date(q.dt_rate_cont_valid,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_rate_cont_valid,rate_cont,q.remarks,q.inst_num,q.emd_amt,q.dt_rec_quot,q.no_quot,qa.*,indent_dept,pc.uom as uomval,q.frt_rate as frtrate ,q.add_chrg as addchrg,q.frt_text as frttext,q.buy_back as buyback,nm_prod,cd_prod,hsn_code,tot,nm_ven,cst,cd_ven,nm_curr,q.id_ven,q.no_quot,q.propsl_num,q.id_quot,q.tender_num,nm_tax,add1,add2,add3,city,state,country,q.id_curr,q.t_c_quot,q.tax_prc,q.bnm_num,q.payment_term "+
					" from P_Quotation q,P_Quotation_Asset qa,M_Prod_Cart pc,M_Vendor v,M_Currency c,M_Tax t "+
							" where t.id_tax=qa.id_tax and q.id_quot=qa.id_quot and qa.id_prod=pc.id_prod and c.id_curr=q.id_curr  and no_ind='"+no_ind+"' and q.id_quot='"+id+"' and q.id_ven=v.id_ven order by ID_QUOT_ASST ";
					}
		
		System.out.println(sql);
		
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Receive Quoatation."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
}
