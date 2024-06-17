package com.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.Order.LeadModel;

public class ExpiryAllTaskNotification {

	public static Connection con=null;
	public static ResultSet rs = null;
	public static Statement st=null;
	public static PreparedStatement ps=null;
	public static void ExpiryRental( )
	{
		JSONObject json=new JSONObject();
		String query="select  id_lead_m,sr_no from O_Assign_lead where DATEADD(day, -3, dt_exp_rent)=Convert(date, getdate()) ";
		System.out.println(query);
		int j=0;
		try{
			con=Common.GetConnectionForMail();
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
		while(rs.next()) {
			System.out.println("mail");
			String id_lead_m=rs.getString(1);
			System.out.println(id_lead_m);
			LeadModel ldmd = new LeadModel();
			ldmd.SendEmailforintimationforrentalexpiry(id_lead_m);
			System.out.println(rs.getString(1));
		}
			 
		
		}catch(Exception e)
		{
			System.out.println("Error while CheckValWhichAllReadyExit." +e);
		}
		 
	}
}
