
<%@ page language="java" import="java.sql.*,javax.naming.*,java.util.*,java.io.*,com.Common.Common,java.text.DecimalFormat" %>

<html>
    <head>
        <title>Consolidated IT Report</title>

    </head>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
    <link href="js/tracker.css" rel="stylesheet" type="text/css">
    <body>
        <form name="showform" action="consolidated_it_report.jsp" method="post">
<%
    try
    {
    	
		Connection con               =   null;
        ResultSet rs                 =   null;
		Statement stmt               =   null;
        Statement stmt1              =   null;
		ResultSet rs1                =   null;
            try{
            	con=Common.GetConnection(request);
                stmt                 =   con.createStatement();
				stmt1                =   con.createStatement();
            }catch(Exception e) {
                System.out.println("Error in Connection "+e);
            }
            
            
        int slno                                =   1;
        String Fin_stdate                       =   "";
        String Fin_enddate                      =   "";
        String Fin_stdate1                       =   "";
        String Fin_enddate1                      =   "";
        String Fin_firsthalfenddate             =   "";
        String Fin_secondhalfstdate             =   "";
        String actNameType                      =   "ITWDV";
        String fin_id                           =   request.getParameter("fin_years");
        Vector Group_Name                       =   new Vector();

        double AsOnDate                         =   0.0;
        double soldwriteoff                     =   0.0;
        double openbal                          =   0.0;

        //String strCompanyId=request.getParameter("companyid");
        
        String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "consolidated_it_report.xls");
 
        }

        rs=stmt.executeQuery("Select gm.id_assetdiv,gm.nm_assetdiv,tdm.iwdv from M_Asset_Div as gm,D_Dprn_Master as tdm where gm.id_assetdiv=tdm.id_grp  order by nm_assetdiv");
        while(rs.next()){
            Group_Name.addElement((String)rs.getString(1));
            Group_Name.addElement((String)rs.getString(2));
            Group_Name.addElement((String)rs.getString(3));

        }
        rs=null;
        rs=stmt.executeQuery("Select std_finance,endt_first,stdt_second,std_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance1,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance1 from M_Finance_Year where id_fincance="+fin_id+"");
        if(rs.next()){
            Fin_stdate              =   rs.getString(1);
            Fin_firsthalfenddate    =   rs.getString(2);
            Fin_secondhalfstdate    =   rs.getString(3);
            Fin_enddate             =   rs.getString(4);
            Fin_stdate1              =   rs.getString(5);
            Fin_enddate1             =   rs.getString(6);
        }

//-----~~~~------
        ArrayList fields=new ArrayList();

        fields.add("sum(asset_opening)");
        fields.add("sum(addition_first_half)+sum(asset_addition_first_half)");
        fields.add("sum(addition_second_half)+sum(asset_addition_second_half)");
        fields.add("");
         fields.add("");
	fields.add("sum(asset_opening)+sum(addition_first_half)+sum(asset_addition_first_half) +sum(addition_second_half)+sum(asset_addition_second_half)");
        
   fields.add("(sum(grand_total))");

        //fields.add("(sum(opening_balance)+sum(addition_first_half)+sum(asset_addition_first_half)) + (sum(addition_second_half)+sum(asset_addition_second_half)) - ((sum(depreciated_opening)+sum(depreciated_add_second)+sum(depreciated_asset_add_second)) + (sum(depreciated_add_first)+sum(depreciated_asset_add_first)))");
		fields.add("(sum(wdv))");


//-----~~~~------
        ArrayList field_name=new ArrayList();

        field_name.add("<b>Opening written down value</b>");
        field_name.add("<b>Addition more than 180 Days</b>");
        field_name.add("<b>Additions less than 180 Days</b>");
	field_name.add("<b>Sale</b>");
        
        field_name.add("<b>Depreciation Rate</b>");
	 field_name.add("<b>Total</b>");
        field_name.add("<b>Total Depreciation </b>");
        field_name.add("<b>Closing WDV </b>");




        rs=stmt.executeQuery("Select * from it_depreciation where startdate='"+Fin_stdate+"'");

        if(rs.next()){
%>          
            <table border="1" class ="table1" width="100%">
                <tr>
                     <td class="headerTR" colspan=9 ><center><p class="tableHeaderContent" style="font-size: 24px;color: white;">Consolidated IT Report for Financial Year (<%=Fin_stdate1+" To "+Fin_enddate1%>  )</p></center></td>
                </tr>
                <tr>
                    <td class="title" ><b>Groups</b></td>
<%
            for(int k=0; k<8;k++)
            {
%>                  <td class="title" ><b><%=field_name.get(k)%></b></td>
<%          }
%>
                     </tr>
<%
          for(int i=0; i<Group_Name.size();i=i+3)
		 {
%>           <tr>
                    <td nowrap nowrap><b><%=Group_Name.elementAt(i+1)%></td>

 


<%
                double sum=0.0f;
                double sum_percent=0.0f;
                 for(int kk=0; kk<8;kk++)
                {

 
                    double temp=0.0f;
                    String str="";

//---------------------------------
                    soldwriteoff=0.0;
                    openbal=0.0;
                    AsOnDate=0.0;

                    rs=stmt.executeQuery("select sum(val_asst) from A_Ware_House as hr, addition_deletion as tad where hr.id_wh=tad.assetid and tad.asset_status in ('sold','writeoff') and  id_grp='"+Group_Name.elementAt(i)+"' and stadate between convert(datetime,'"+Fin_stdate+"',120) and convert(datetime,'"+Fin_enddate+"',120)");
                    if(rs.next())
                    {
                        soldwriteoff=rs.getDouble(1);
                    }
                     rs=stmt.executeQuery("select sum(asset_opening),sum(AsOnDate) from addition_deletion as tad,it_depreciation as it where   tad.assetid1=it.assetid and tad.asset_status in ('sold','writeoff') and  tad.asset_group_id='"+Group_Name.elementAt(i)+"' and stadate between convert(datetime,'"+Fin_stdate+"',120) and convert(datetime,'"+Fin_enddate+"',120)");
                    if(rs.next())
                    {
                        openbal=rs.getDouble(1);

                    }


				//out.println("select sum(AsOnDate) from it_depreciation  where  group_id='"+Group_Name.elementAt(i)+"' and startdate=convert(datetime,'"+Fin_stdate+"',120) and enddate=convert(datetime,'"+Fin_enddate+"',120) and assetid not in (select assetid1 from addition_deletion where asset_status  in ('sold','writeoff') and stadate between convert(datetime,'"+Fin_stdate+"',120) and convert(datetime,'"+Fin_enddate+"',120))");
                     rs=stmt.executeQuery("select sum(AsOnDate) from it_depreciation  where  group_id='"+Group_Name.elementAt(i)+"' and startdate=convert(datetime,'"+Fin_stdate+"',120) and enddate=convert(datetime,'"+Fin_enddate+"',120) and assetid not in (select assetid1 from addition_deletion where asset_status  in ('sold','writeoff') and stadate between convert(datetime,'"+Fin_stdate+"',120) and convert(datetime,'"+Fin_enddate+"',120))");
                    if(rs.next())
                    {
                        AsOnDate=rs.getDouble(1);
                    }
//---------------------------------


//out.println("iiiiiiiiii");
                    if(kk!=3 && kk!=4)
                    {
						//out.println("select "+fields.get(kk)+" from it_depreciation  where startdate='"+Fin_stdate+"' and group_id='"+Group_Name.elementAt(i)+"'");
                       rs1=stmt1.executeQuery("select "+fields.get(kk)+" from it_depreciation  where startdate='"+Fin_stdate+"' and group_id='"+Group_Name.elementAt(i)+"'");
                       if(rs1.next())
                        {   temp=rs1.getDouble(1);   }
                    }

                    if(kk==3)
                        temp=soldwriteoff;

                    if(kk==4)
                    {
                        temp=Double.parseDouble((String)Group_Name.elementAt(i+2));
                        str="%";
                    }

sum+=temp;
                    

%>               	<td class="righttd" height="16"><%=temp + str%></td>

<%              }
%>            <%          }

%>
                </tr>


     
            </table>
            <%
	        if (exportToExcel == null) {
	    %>
            <br>
                <a href="consolidated_it_reports.jsp?exportToExcel=YES&fin_years=<%=fin_id%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
				</a>	
           <%
	        }
	    %>
            <input type="hidden" name="fin_years" value="<%=fin_id%>">
            <input type="hidden" name="actNameType" value="<%=actNameType%>">
            </form>
    </body>
<%      }else
        {
            out.println("<br><br><br><center><b><font color='red' class=navigate>Depreciation Yet To Be Calculated </font></b></center>");
        }

    }catch(Exception e){out.println(e);}
    //try{tb.close();}catch(Exception ee){out.println(ee);}
%>
</html>