package com.LibraryForDprn;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;

public class Year_WDVMonthDepCalcNUL
{
  public void calculateDep( HttpServletRequest request)
    throws Exception
  {
    
    Connection localConnection = null;
    Statement localStatement1 = null;
    Statement localStatement2 = null;
    Object localObject = null;
    try
    {
     
      localConnection = Common.GetConnection(request);
      localStatement1 = localConnection.createStatement();
      localStatement2 = localConnection.createStatement();
    } catch (Exception localException1) {
      System.out.println("Error in Connection :" + localException1);
    }

    String str = "";

    str = "select assetid,instl_date,dep_startdate,dep_enddate,sold_writeoff,group_id,previous_depamount,cast(0.0000 as decimal) as asset_deletion,cast(0.0000 as decimal) as depreciation_deletion,ast_id, CASE WHEN DEP_FLAG='NEW' then  assetvalue  else cast(0.0000 as decimal) end as assetaddition, CASE WHEN DEP_FLAG<>'NEW' then  assetvalue  else cast(0.0000 as decimal) end as assetopening, case when Sold_writeoff not in('WRITEOFF','SOLD') and  instl_date between convert(datetime, dep_startdate,101)  and  convert(datetime, dep_enddate,101) then ((AssetValue*(deprate/100)*(datediff(day,convert(datetime, instl_date,101),convert(datetime, dep_enddate,101))+1)/365.0)) when Sold_writeoff not in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101)  AND DEP_FLAG = 'NEW' then(assetvalue-(assetvalue*power((1-(deprate/100.0)),(datediff(day,convert(datetime, instl_date,101),convert(datetime, dep_enddate,101))+1)/365.0))) else cast(0.0000 as decimal)  end as depreciated_addition, case when Sold_writeoff not in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101)  AND DEP_FLAG <> 'NEW' then ( (AssetValue*(deprate/100)*(datediff(day,convert(datetime, dep_startdate,101),convert(datetime, dep_enddate,101))+1)/(datediff(day,convert(datetime, dep_startdate,101),convert(datetime, dep_enddate,101))+1))) else cast(0.0000 as decimal)  end as depreciated_opening,   case when Sold_writeoff in('WRITEOFF','SOLD') and  instl_date between convert(datetime, dep_startdate,101)  and convert(datetime, dep_enddate,101) and sold__writeoff_date between convert(datetime, dep_startdate,101)  and convert(datetime, dep_enddate,101) and sold__writeoff_date > instl_date then ((AssetValue*(deprate/100)*(datediff(day,convert(datetime,instl_date,101),convert(datetime,sold__writeoff_date,101))+1)/365.0))\t when Sold_writeoff in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101)  AND DEP_FLAG = 'NEW' then(assetvalue-(assetvalue*power((1-(deprate/100.0)),(datediff(day,convert(datetime, instl_date,101),convert(datetime, sold__writeoff_date,101))+1)/365.0))) else cast(0.0000 as decimal)  end as sold_writeoff_instl, case when Sold_writeoff in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101) and sold__writeoff_date between convert(datetime, dep_startdate,101)  and convert(datetime, dep_enddate,101) and sold__writeoff_date > instl_date AND DEP_FLAG <> 'NEW' then  ((AssetValue*(deprate/100)*(datediff(day,convert(datetime,dep_startdate,101),convert(datetime,sold__writeoff_date,101))+1)/ 365)) else cast(0.0000 as decimal)  end as sold_writeoff_startdate into temp_month_dep from hr_depWDV ";
    try
    {
      localStatement1.executeUpdate("drop table temp_month_dep");
    } catch (Exception localException2) {
      System.out.println("\nPhase 1: Problem occured during droping the temp_month_dep table:\n" + localException2 + "\n...ends\n");
    }
    try {
      localStatement1.executeUpdate(str);
    } catch (Exception localException3) {
      System.out.println("\nPhase 2: This message is from DepCalc.java file ... \n   Eception Occured while calculating depreciation:\n" + localException3 + "\n...ends\n");
    }
    try
    {
      localConnection.close(); } catch (Exception localException4) {
      System.out.println("Exception while closing the connection.." + localException4);
    }
  }

  public void add_del_calculateDep( HttpServletRequest request) throws Exception {
   
    Connection localConnection = null;
    Statement localStatement1 = null;
    Statement localStatement2 = null;
    Object localObject = null;
    try
    {
      
      localConnection = Common.GetConnection(request);
      localStatement1 = localConnection.createStatement();
      localStatement2 = localConnection.createStatement();
    } catch (Exception localException1) {
      System.out.println("Error in Connection " + localException1);
    }

    String str = "";

    str = "select assetid,instl_date,start_date,end_date,sold_writeoff_flag,assetvalue as assetaddition,asset_status_date,asset_status,case when sold_writeoff_flag not in('WRITEOFF','SOLD') and asset_status='add' then  ((AssetValue*(deprate/100)*(datediff(day,convert(datetime, asset_status_date,101),convert(datetime, end_date,101))+1)/365.0))  else  cast(0.0000 as decimal)  end as depreciated_addition,case when sold_writeoff_flag in('WRITEOFF','SOLD') and asset_status='add' and sold_writeoff_date < convert(datetime, end_date,101) and sold_writeoff_date > asset_status_date then  ((AssetValue*(deprate/100)*(datediff(day,convert(datetime,asset_status_date,101),convert(datetime,sold_writeoff_date,101))+1)/365.0))  else  cast(0.0000 as decimal)  end as depreciated_addition_sold_writeoff,case when sold_writeoff_flag not in('WRITEOFF','SOLD') and asset_status='delete' then  ((AssetValue*(deprate/100)*(datediff(day,convert(datetime, asset_status_date,101),convert(datetime, end_date,101))+1)/365.0))  else  cast(0.0000 as decimal)  end as depreciated_deletion,case when sold_writeoff_flag in('WRITEOFF','SOLD') and asset_status='delete' and sold_writeoff_date < convert(datetime, end_date,101) and sold_writeoff_date > asset_status_date then  ((AssetValue*(deprate/100)*(datediff(day,convert(datetime,asset_status_date,101),convert(datetime,sold_writeoff_date,101))+1)/365.0))  else  cast(0.0000 as decimal)  end as depreciated_deletion_sold_writeoff into temp_month_ADDITION_DELETION_DEPWDV from ADDITION_DELETION_DEPWDV";
    try
    {
      localStatement1.executeUpdate("drop table temp_month_ADDITION_DELETION_DEPWDV");
    } catch (Exception localException2) {
      System.out.println("\nPhase 1A: Problem occured during droping the temp_month_ADDITION_DELETION_DEPWDV table:\n" + localException2 + "\n...ends\n");
    }
    try
    {
      localStatement1.executeUpdate(str);
      localStatement1.executeUpdate("update temp_month_dep set assetaddition=(t.assetaddition+td.assetaddition),depreciated_addition=(t.depreciated_addition+t.depreciated_addition_sold_writeoff+td.depreciated_addition) from temp_month_ADDITION_DELETION_DEPWDV t,temp_month_dep td where t.assetid=td.assetid and t.asset_status='add'");
      localStatement1.executeUpdate("update temp_month_dep set asset_deletion=(t.assetaddition+td.asset_deletion),depreciation_deletion=(t.depreciated_deletion+t.depreciated_deletion_sold_writeoff+td.depreciation_deletion) from temp_month_ADDITION_DELETION_DEPWDV t,temp_month_dep td where t.assetid=td.assetid and t.asset_status='delete'");
    }
    catch (Exception localException3) {
      System.out.println("Error 4.1" + localException3);
    }
    try {
      localConnection.close(); } catch (Exception localException4) {
      System.out.println("Exception while closing the connection.." + localException4);
    }
  }

  public void transfer_calculateDep(String paramString1, String paramString2,
		  String paramString3,String DepType,HttpServletRequest request)
    throws Exception
  {
   
    Connection localConnection = null;
    Statement localStatement1 = null;
    Statement localStatement2 = null;
    Object localObject = null;
    try
    {
      
      localConnection = Common.GetConnection(request);
      localStatement1 = localConnection.createStatement();
      localStatement2 = localConnection.createStatement();
    } catch (Exception localException1) {
      System.out.println("Error in Connection " + localException1);
    }

    String str1 = "";
    if (paramString3.equals("monthly"))
    {
      str1 = "month_depreciation";
    }
    else if (paramString3.equals("yearly"))
    {
      str1 = "stline_depreciation";
      if(DepType.equals("IT"))
      {
      	 str1 = "it_depreciation";
      }
    }
    System.out.println("Table :" + str1);

    String str2 = "ast_id,type_id,group_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status";

    String str3 = "ast_id,'CAWDV',group_id,assetid,assetopening,assetaddition,asset_deletion,(assetopening+assetaddition-asset_deletion),previous_depamount,(depreciated_opening+sold_writeoff_startdate),(depreciated_addition+sold_writeoff_instl),depreciation_deletion,(depreciated_opening+sold_writeoff_startdate+depreciated_addition+sold_writeoff_instl-depreciation_deletion),((assetopening+assetaddition-asset_deletion)-(depreciated_opening+sold_writeoff_startdate+depreciated_addition+sold_writeoff_instl-depreciation_deletion)),dep_startdate,dep_enddate,0,sold_writeoff";

    String str4 = "a.ast_id,a.type_id,a.group_id,a.assetid,a.asset_opening,a.asset_addition,a.asset_deletion,a.asset_total,a.asondate,a.depreciated_opening,a.depreciated_addition,a.depreciated_deletion,a.depreciated_total,a.wdv,a.startdate,a.enddate,a.writeoff,a.status";
    try
    {
      localStatement1.executeUpdate("insert into " + str1 + "(" + str2 + ") select " + str3 + " from temp_month_dep");

      localStatement1.executeUpdate("update " + str1 + " set asset_deletion=cast((asset_deletion+asset_opening+asset_addition-asset_deletion) as float),depreciated_deletion=cast((depreciated_deletion+depreciated_opening+depreciated_addition+asondate-depreciated_deletion) as float) from " + str1 + " where status in ('sold','writeoff')");
      localStatement1.executeUpdate("update " + str1 + " set asset_total=(asset_opening+asset_addition-asset_deletion),wdv=0.0, depreciated_total=(depreciated_opening+depreciated_addition-depreciated_deletion+asondate) where status in ('sold','writeoff')");

      if (paramString3.equals("monthly"))
      {
        localStatement2.executeUpdate("insert into cost_depreciation(" + str2 + ",costcenter_id) select a.ast_id,a.type_id,a.group_id,a.assetid,a.asset_opening,a.asset_addition,a.asset_deletion,a.asset_total,a.asondate,a.depreciated_opening,a.depreciated_addition,a.depreciated_deletion,a.depreciated_total,a.wdv,a.startdate,a.enddate,a.writeoff,a.status,b.costcenter_id from month_depreciation a, iut_history b where a.ast_id=b.ast_id and b.transferdate=convert(datetime,'1900-01-01',101) and b.iut_flag=0 and a.startdate=convert(datetime,'" + paramString1 + "',101) and a.type_id='CAWDV'");
        localStatement2.executeUpdate("insert into cost_depreciation(" + str2 + ",costcenter_id) select a.ast_id,a.type_id,a.group_id,a.assetid,a.asset_opening,a.asset_addition,a.asset_deletion,a.asset_total,a.asondate,a.depreciated_opening,a.depreciated_addition,a.depreciated_deletion,a.depreciated_total,a.wdv,a.startdate,a.enddate,a.writeoff,a.status,yyy.costcenter_id from (select ast_id, costcenter_id, max(nn) days from (select a.ast_id,b.costcenter_id, case when transferdate = convert(datetime,'1900-01-01',101) then      datediff(d,b.startdate,'" + paramString2 + "') else      datediff(d,b.startdate,b.transferdate) end as nn from month_depreciation a, iut_history b where a.ast_id=b.ast_id and b.iut_flag in(1,2) and a.startdate=convert(datetime,'" + paramString1 + "',101)) xxx group by ast_id, costcenter_id) yyy, month_depreciation a where a.ast_id = yyy.ast_id and a.startdate=convert(datetime,'" + paramString1 + "',101) and a.type_id='CAWDV'");
      }

    }
    catch (Exception localException2)
    {
      System.out.println("Error 4A" + localException2);
    }
    try {
      localConnection.close(); } catch (Exception localException3) {
      System.out.println("Exception while closing the connection.." + localException3);
    }
  }
}