package com.LibraryForDprn;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;
public class WDVYearlyDepCalcNUL
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

    str = "select assetid,instl_date,dep_startdate,dep_enddate,sold_writeoff,group_id,previous_depamount,cast(0.0000 as decimal) as asset_deletion,cast(0.0000 as decimal) as depreciation_deletion,ast_id, CASE WHEN DEP_FLAG='NEW' then  assetvalue  else cast(0.0000 as decimal) end as assetaddition, CASE WHEN DEP_FLAG<>'NEW' then  assetvalue  else cast(0.0000 as decimal) end as assetopening, case when Sold_writeoff not in('WRITEOFF','SOLD') and  instl_date between convert(datetime, dep_startdate,101)  and  convert(datetime, dep_enddate,101) then ((AssetValue*(deprate/100)*(datediff(day,convert(datetime, instl_date,101),convert(datetime, dep_enddate,101))+1)/365.0)) when Sold_writeoff not in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101)  AND DEP_FLAG = 'NEW' then(assetvalue-(assetvalue*power((1-(deprate/100.0)),(datediff(day,convert(datetime, instl_date,101),convert(datetime, dep_enddate,101))+1)/365.0))) else cast(0.0000 as decimal)  end as depreciated_addition, case when Sold_writeoff not in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101)  AND DEP_FLAG <> 'NEW' then ( (AssetValue*(deprate/100)*(datediff(day,convert(datetime, dep_startdate,101),convert(datetime, dep_enddate,101))+1)/365.0)) else cast(0.0000 as decimal)  end as depreciated_opening,   case when Sold_writeoff in('WRITEOFF','SOLD') and  instl_date between convert(datetime, dep_startdate,101)  and convert(datetime, dep_enddate,101) and sold__writeoff_date between convert(datetime, dep_startdate,101)  and convert(datetime, dep_enddate,101) and sold__writeoff_date > instl_date then ((AssetValue*(deprate/100)*(datediff(day,convert(datetime,instl_date,101),convert(datetime,sold__writeoff_date,101))+1)/365.0))\t when Sold_writeoff in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101)  AND DEP_FLAG = 'NEW' then(assetvalue-(assetvalue*power((1-(deprate/100.0)),(datediff(day,convert(datetime, instl_date,101),convert(datetime, sold__writeoff_date,101))+1)/365.0))) else cast(0.0000 as decimal)  end as sold_writeoff_instl, case when Sold_writeoff in('WRITEOFF','SOLD') and  instl_date <  convert(datetime, dep_startdate,101) and sold__writeoff_date between convert(datetime, dep_startdate,101)  and convert(datetime, dep_enddate,101) and sold__writeoff_date > instl_date AND DEP_FLAG <> 'NEW' then  ((AssetValue*(deprate/100)*(datediff(day,convert(datetime,dep_startdate,101),convert(datetime,sold__writeoff_date,101))+1)/ 365)) else cast(0.0000 as decimal)  end as sold_writeoff_startdate into temp_month_dep from hr_depWDV ";
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

  public void transfer_calculateDep(String paramString1, String paramString2,HttpServletRequest request)
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

    String str1 = "ast_id,type_id,group_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status";

    String str2 = "ast_id,'CAWDV',group_id,assetid,assetopening,assetaddition,asset_deletion,(assetopening+assetaddition-asset_deletion),previous_depamount,(depreciated_opening+sold_writeoff_startdate),(depreciated_addition+sold_writeoff_instl),depreciation_deletion,(depreciated_opening+sold_writeoff_startdate+depreciated_addition+sold_writeoff_instl+previous_depamount-depreciation_deletion),((assetopening+assetaddition-asset_deletion)-(depreciated_opening+sold_writeoff_startdate+depreciated_addition+sold_writeoff_instl-depreciation_deletion)),dep_startdate,dep_enddate,0,sold_writeoff";
    try
    {
      localStatement1.executeUpdate("insert into stline_depreciation(" + str1 + ") select " + str2 + " from temp_month_dep");

      localStatement1.executeUpdate("update stline_depreciation set asset_deletion=cast((asset_deletion+asset_opening+asset_addition-asset_deletion) as float),depreciated_deletion=cast((depreciated_deletion+depreciated_opening+depreciated_addition+asondate-depreciated_deletion) as float) from stline_depreciation where status in ('sold','writeoff')");
      localStatement1.executeUpdate("update stline_depreciation set asset_total=(asset_opening+asset_addition-asset_deletion),wdv=0.0, depreciated_total=(depreciated_opening+depreciated_addition-depreciated_deletion+asondate) where status in ('sold','writeoff')");
    }
    catch (Exception localException2) {
      System.out.println("Error 4A" + localException2);
    }
    try {
      localConnection.close(); } catch (Exception localException3) {
      System.out.println("Exception while closing the connection.." + localException3);
    }
  }
}