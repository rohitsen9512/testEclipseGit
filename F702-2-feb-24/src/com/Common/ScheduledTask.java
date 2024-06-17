package com.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

/**
 *
 * @author Runjay Patel.
 */

public class ScheduledTask extends TimerTask {
	public static Connection con=null;
	public static Connection con1=null;
	public static Connection con2=null;
	public static Connection con3=null;
	public static Connection con4=null;
	public static Connection con5=null;
	public static ResultSet rs = null;
	public static ResultSet rs1 = null;
	public static ResultSet rs2 = null;
	public static ResultSet rs4 = null;
	public static ResultSet rs5 = null;
	public static ResultSet rs6 = null;
	public static Statement st=null;
	public static Statement st1=null;
	public static Statement st4=null;
	public static Statement st5=null;
	public static PreparedStatement ps=null;
	public static PreparedStatement ps1=null;
	public static PreparedStatement ps2=null;
	public static PreparedStatement ps4=null;
	public static PreparedStatement ps5=null;
	public static PreparedStatement ps6=null;
	static Statement stmt=null;
	Date now; // to display current time

	// Add your task here
	public void run(HttpServletRequest request) {
		now = new Date(); // initialize date
		
		String id_ven="0",id_loc="0", id_po="0";
		String location="", venName="",orcl_po_id="",po_header_id="", po_number="",po_creation_date="",total_price="",unit_price="",quantity="",assetName="";
		
		try{
		
		String SqlQuery = "select po_header_id from Last_Date_Sync where id=1";	
		
		con=Common.GetConnection(request);
		ps6=con.prepareStatement(SqlQuery);
		rs6=ps6.executeQuery();
	if(rs6.next()){
		po_header_id = rs6.getString(1);
	}
			
		String query="SELECT * "+
" FROM OPENQUERY(ILPRD, 'select pha.po_header_id orcl_po_id,pha.segment1 PO_NUMBER,pha.creation_date PO_CREATION_DATE,aps.vendor_name,msi.segment1 item_name,plla.quantity,pla.unit_price,hla.location_code location ,pha.org_id, (plla.quantity*pla.unit_price) total_price"+
" from po_headers_All pha, po_lines_all pla, po_line_locations_all plla,po_distributions_all pda,"+
" gl_code_combinations gcc,ap_suppliers aps,hr_locations_all hla,mtl_System_items_b msi where pha.po_header_id=pla.po_header_id and "+
" pla.po_line_id=plla.po_line_id and plla.line_location_id =pda.line_location_id"+                             
" and pda.code_combination_id=gcc.code_combination_id and pha.vendor_id=aps.vendor_id and pha.ship_to_location_id=hla.location_id "+
" and gcc.segment4 in (11405, 11413)and pha.creation_Date>=''2017-09-01 00:00:00'' and msi.inventory_item_id = pla.item_id"+
" and msi.organization_id = plla.ship_to_organization_id and pha.po_header_id > "+po_header_id+" ORDER BY pha.po_header_id ASC')";
		/*and pha.segment1=2011004511	*/
		
			con=Common.GetConnection(request);
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
		while(rs.next()){
			
			//location = rs.getString("location");
			venName= rs.getString("vendor_name");
			po_number =rs.getString("po_number");
			po_creation_date = rs.getString("po_creation_date");
			total_price =rs.getString("total_price");
			unit_price=rs.getString("unit_price");
			quantity = rs.getString("quantity");
			assetName = rs.getString("item_name");
			orcl_po_id = rs.getString("orcl_po_id");		
			//id_loc = returnLocId(location);
			id_ven = returnVenId(venName,request);
			
			String sql = "select id_po from P_purchase_order where no_po = '"+po_number+"'";
			con1=Common.GetConnection(request);
			ps1=con1.prepareStatement(sql);
			rs1=ps1.executeQuery();
			if(rs1.next()){
				id_po = rs1.getString(1);
				
				/*query="update P_Purchase_Order set id_sloc="+id_loc+" where id_po="+id_po+"";
				con3=Common.GetConnection(request);
	            PreparedStatement ps2=con3.prepareStatement(query);
				ps2.executeUpdate();*/
	            
	            
			}else{
			 sql="insert into P_Purchase_Order(no_po, dt_po, id_ven, dt_approv, st_approv, approv_by, add_by, id_tax,tot, gr_tot) " +
					" values('"+po_number+"','"+po_creation_date+"',"+id_ven+",'"+po_creation_date+"','Accepted',1,1,1008,round("+total_price+",3,1),round("+total_price+",3,1))";
			 /*try{*/
			 stmt=con1.createStatement();
			 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			 rs2 = stmt.getGeneratedKeys();
	            if(rs2.next()) {
	            	id_po = rs2.getString(1);
	            }
			 /*}catch(Exception e){
				 System.out.println("Error while po insertion : "+ e);
			 }*/
			}   
	            query="insert into P_Purchase_Order_Asset(id_po,qty,rem_qty,un_prc,tot_prc,dt_recv,asset_consumable,description,po_header_id) values("+id_po+",round("+quantity+",3,1),round("+quantity+",3,1),round("+unit_price+",3,1),round("+total_price+",3,1),'"+po_creation_date+"','CapG', '"+assetName+"', "+orcl_po_id+")";
	        	/*try{*/
	            con3=Common.GetConnection(request);
	            PreparedStatement ps2=con3.prepareStatement(query);
				ps2.executeUpdate();
	        	/*}catch(Exception e){
	        		System.out.println("Error while asset insertion : " +e);
	        	}*/
   
	           System.out.println("One row inserted."); 
	            
		}
		
		}catch(Exception e)
		{
			System.out.println("Here some error." + e);
		}
		
		
		System.out.println("Schedule is runing :" + now); // Display current time
	}
	
	
	
	private static String returnVenId(String venName,HttpServletRequest request){
		String id="0", sql="";
		try{
			sql ="select id_ven from M_Vendor where nm_ven='"+venName+"' ";
			con5=Common.GetConnection(request);
			ps5=con5.prepareStatement(sql);
			rs5=ps5.executeQuery();
			if(rs5.next()){
				id = rs5.getString(1);
			}else{
				sql = "insert into M_Vendor(cd_ven, nm_ven, add1, add2, add3, city, state, country, phone, nm_contact, ct_phone, service, procured)"+
					  " VALUES ('NA', '"+venName+"', 'NA','NA','NA','NA','NA','NA','NA','NA','NA','service','procured')";
				stmt=con5.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs5 = stmt.getGeneratedKeys();
		            if(rs5.next()) {
		            	id = rs5.getString(1);
		            } 
				
			}
			
		}catch(Exception e){
			
		}
		
		return id;
	}
	
	
	private static String returnLocId(String LocName,HttpServletRequest request){
		String id="0"; String sql="";
		try{
			sql ="select id_loc from M_Loc where nm_loc='"+LocName+"' ";
			con4=Common.GetConnection(request);
			ps4=con4.prepareStatement(sql);
			rs4=ps4.executeQuery();
			if(rs4.next()){
				id = rs4.getString(1);
			}else{
				sql = "insert into M_Loc(cd_loc, nm_loc, id_country)"+
					  " VALUES ('NA', '"+LocName+"', 1)";
				stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs4 = stmt.getGeneratedKeys();
		            if(rs4.next()) {
		            	id = rs4.getString(1);
		            } 
				
			}
			
		}catch(Exception e){
			
		}
		
		return id;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}