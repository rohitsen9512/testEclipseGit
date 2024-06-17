package com.LibraryForDprn;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;

public class TransferHardwareRegisterWDV
{
  public void transfer(String paramString1, String paramString2, String paramString3, 
		  ArrayList paramArrayList,HttpServletRequest request)
    throws Exception
  {
    
    Connection localConnection = null;
    Statement localStatement1 = null;
    Statement localStatement2 = null;
    ResultSet localResultSet = null;
    try {
      
      localConnection = Common.GetConnection(request);
      localStatement1 = localConnection.createStatement();
      localStatement2 = localConnection.createStatement();
    } catch (Exception localException1) {
      System.out.println("Error in Connection " + localException1);
    }

    String str1 = "";
    String str2 = "";
    if (paramString3.equals("monthly"))
    {
      str2 = "month";
      str1 = "month_depreciation";
    }
    else if (paramString3.equals("yearly"))
    {
      str2 = "year";
      str1 = "stline_depreciation";
    }

    String str3 = "";
    String str4 = "-";
    String str5 = "";
    String str6 = "";
    String str7 = "";
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    String str12 = null;
    double d1 = 0.0D;
    double d2 = 0.0D;
    String str13 = null;

    localResultSet = localStatement1.executeQuery("select * from dep_param where type_id='CA'");
    if (localResultSet.next()) {
      str5 = localResultSet.getString("capitalization_date");
      str6 = localResultSet.getString("dep_start");
      str7 = localResultSet.getString("capitalization_comp");
      str11 = localResultSet.getString("dep_method");
      str12 = localResultSet.getString("dep_based_on");
      d1 = localResultSet.getDouble("write_off_value");
      d2 = localResultSet.getDouble("dep_stop");
      str13 = localResultSet.getString("dep_no_of_days");
    }
    localResultSet = localStatement1.executeQuery("select fin_stdate from finyear_master where '" + paramString1 + "' between fin_stdate and fin_enddate");
    if (localResultSet.next())
    {
      str3 = localResultSet.getString(1);
    }

    StringTokenizer localStringTokenizer = new StringTokenizer(str7, ",");
    str7 = "-";
    ArrayList localArrayList = new ArrayList();
    while (localStringTokenizer.hasMoreTokens()) {
      str10 = localStringTokenizer.nextToken();
      localArrayList.add(str10);
    }
    for (int i = 0; i < localArrayList.size(); i++) {
      if (str7.equals("-"))
        str7 = "" + localArrayList.get(i);
      else {
        str7 = str7 + "+" + localArrayList.get(i);
      }
    }
    if (str7.equals("-"))
      str7 = "assetvalue";
    else {
      str7 = "(" + str7 + ")";
    }
    if (str6.equals("Actual_Date")) {
      str8 = str5;
      str9 = "h." + str5;
    } else if (str6.equals("First_Day")) {
      str8 = "" + str5 + " - day(convert(datetime," + str5 + " ,101))+1";
      str9 = "h." + str5 + " - day(convert(datetime,h." + str5 + " ,101))-1";
    } else if (str6.equals("First_Day_Next_Month")) {
      str8 = "dateadd(m,1, convert(datetime," + str5 + ",101)) - day(dateadd(m,1, convert(datetime," + str5 + ",101))) + 1";
      str9 = "dateadd(m,1, convert(datetime,h." + str5 + ",101)) - day(dateadd(m,1, convert(datetime,h." + str5 + ",101))) + 1";
    } else if (str6.equals("First_Day_Half_Break")) {
      str8 = "(convert(datetime," + str5 + ",101) + 17) - day(convert(datetime," + str5 + ",101) + 17) + 1";
      str9 = "(convert(datetime,h." + str5 + ",101) + 17) - day(convert(datetime,h." + str5 + ",101) + 17) + 1";
    }
    for (int j = 0; j < paramArrayList.size(); j++) {
      if (str4.equals("-"))
        str4 = "'" + paramArrayList.get(j) + "'";
      else {
        str4 = str4 + ",'" + paramArrayList.get(j) + "'";
      }
    }
    if (str4.equals("-")) {
      str4 = "'0'";
    }
    System.out.println(str4);
    String str14 = "ast_id,assetvalue,leasestatus,instl_date,leasestartdate,deprate,assetid,group_id,leaseenddate,start_date,startdate,end_date";

    String str15 = "ast_id," + str7 + "," + "leasestatus," + "" + str8 + "," + "leasestartdate," + "deprate," + "assetid," + "group_id," + "leaseenddate," + "'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";

    String str16 = "ast_id," + str7 + "," + "leasestatus," + "" + str8 + "," + "softlife_stdate," + "deprate," + "assetid," + "group_id," + "softlife_enddate," + "'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";

    String str17 = "ast_id,assetvalue,asset_status,asset_status_date,group_id,start_date,startdate,end_date";

    String str18 = "a.assetid,a.addamt,a.asset_status,a.adddate,a.asset_group_id,'" + str3 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";

    System.out.println("Monthly Block RCS");
    try {
      localStatement1.executeUpdate("truncate table hr_depWDV");
    } catch (Exception localException2) {
      System.out.println("NO Recorde To Truncate");
    }
    try {
      localStatement1.executeUpdate("delete from " + str1 + " where enddate >= '" + paramString2 + "' and group_id in(" + str4 + ") AND type_id='CAWDV'");
      localStatement1.executeUpdate("delete from cost_depreciation where enddate >= '" + paramString2 + "' and group_id in(" + str4 + ") AND type_id='CAWDV'");
      localStatement1.executeUpdate("insert into hr_depWDV(" + str14 + ") select " + str15 + " from hardware_register where installed in(1,2,3) and " + str8 + " < '" + paramString2 + "' and group_id in(" + str4 + ") AND leasestatus in('Nolease','NUL') and ast_id not in(select ast_id from " + str1 + " where wdv=0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV') and " + str7 + " > = " + d1 + "");
      localStatement1.executeUpdate("update hr_depWDV set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_depWDV where hr_depWDV.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
      localStatement1.executeUpdate("update hr_depWDV set dep_startdate='" + str3 + "',dep_enddate='" + paramString2 + "'");
      localStatement1.executeUpdate("update hr_depWDV set leasestatus = 'NUL' where leasestatus='NoLease'");
      localStatement1.executeUpdate("update hr_depWDV set deprate = d.cwdv from hr_depWDV h, dep_master d where d.group_id = h.group_id and deprate = 0 ");
      localStatement1.executeUpdate("update hr_depWDV set previous_depamount=" + str1 + ".depreciated_total,wdv=" + str1 + ".wdv,dep_flag='EXISTING' from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + paramString1 + "',101)-1");
      System.out.println("update hr_depWDV set assetvalue=" + str1 + ".wdv from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + str3 + "',101)-1");
      localStatement1.executeUpdate("update hr_depWDV set assetvalue=" + str1 + ".wdv from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + str3 + "',101)-1");
      localStatement1.executeUpdate("delete from hr_depWDV where ast_id in (select AST_ID from hardware_register a,addition_deletion b where a.ast_id=b.assetid and b.asset_status in('sold','writeoff') and a.assetid  in  (select assetid from " + str1 + " where type_id='CAWDV' and status in('sold','writeoff')))");
    }
    catch (Exception localException3)
    {
      System.out.println("Error 1" + localException3);
    }
    try
    {
      localStatement1.executeUpdate("truncate table ADDITION_DELETION_DEPWDV");
    } catch (Exception localException4) {
      System.out.println("NO Recorde To Truncate");
    }
    try
    {
      localStatement1.executeUpdate("insert into ADDITION_DELETION_DEPWDV(" + str17 + ") select " + str18 + " from addition_deletion a left outer join " + str1 + " m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from " + str1 + ") and a.adddate < convert(datetime,'" + paramString1 + "',101))");

      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set assetid=h.assetid,instl_date=" + str9 + " from ADDITION_DELETION_DEPWDV a, hardware_Register h where a.ast_id=h.ast_id");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set deprate=h.cwdv from ADDITION_DELETION_DEPWDV a, dep_master h where a.group_id=h.group_id");

      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,ADDITION_DELETION_DEPWDV where addition_deletion.assetid=ADDITION_DELETION_DEPWDV.ast_id and addition_deletion.asset_status in('sold','writeoff')");
    }
    catch (Exception localException5)
    {
      System.out.println("Error 2" + localException5);
    }
    try
    {
      WDVMonthDepCalcNUL localWDVMonthDepCalcNUL = new WDVMonthDepCalcNUL();
      localWDVMonthDepCalcNUL.calculateDep(  request);
      localWDVMonthDepCalcNUL.add_del_calculateDep(   request);
      localWDVMonthDepCalcNUL.transfer_calculateDep(paramString1, paramString2,
    		  paramString3,  request);
    } catch (Exception localException6) {
      System.out.println("Error 3" + localException6);
    }

    try
    {
      localStatement1.executeUpdate("truncate table hr_depWDV");
    } catch (Exception localException7) {
      System.out.println("NO Recorde To Truncate");
    }
    try {
      localStatement1.executeUpdate("insert into hr_depWDV(" + str14 + ") select " + str15 + " from hardware_register where installed in(1,2,3) and " + str8 + " < '" + paramString2 + "' and group_id in(" + str4 + ") AND leasestatus in('lease','UL') and ast_id not in(select ast_id from " + str1 + " where wdv=0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV') and " + str7 + " > = " + d1 + "");
      localStatement1.executeUpdate("insert into hr_depWDV(" + str14 + ") select " + str16 + " from hardware_register where installed in(1.2,3) and " + str8 + " < '" + paramString2 + "' and group_id in(" + str4 + ") AND leasestatus in('') and ast_id not in(select ast_id from " + str1 + " where wdv=0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV') and " + str7 + " > = " + d1 + "");
      localStatement1.executeUpdate("update hr_depWDV set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_depWDV where hr_depWDV.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
      localStatement1.executeUpdate("update hr_depWDV set dep_startdate='" + str3 + "',dep_enddate='" + paramString2 + "'");
      localStatement1.executeUpdate("update hr_depWDV set leasestatus = 'UL' where leasestatus in('Lease','')");
      localStatement1.executeUpdate("update hr_depWDV set deprate = d.cwdv from hr_depWDV h, dep_master d where d.group_id = h.group_id and deprate = 0 ");
      localStatement1.executeUpdate("update hr_depWDV set previous_depamount=" + str1 + ".depreciated_total,wdv=" + str1 + ".wdv,dep_flag='EXISTING',assetvalue=" + str1 + ".asset_total from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + paramString1 + "',101)-1");

      localStatement1.executeUpdate("delete from hr_depWDV where ast_id in (select AST_ID from hardware_register a,addition_deletion b where a.ast_id=b.assetid and b.asset_status in('sold','writeoff') and a.assetid  in  (select assetid from " + str1 + " where type_id='CAWDV' and status in('sold','writeoff')))");
    }
    catch (Exception localException8) {
      System.out.println("Error 1" + localException8);
    }
    try {
      localStatement1.executeUpdate("truncate table ADDITION_DELETION_DEPWDV");
    } catch (Exception localException9) {
      System.out.println("NO Recorde To Truncate");
    }
    try
    {
      localStatement1.executeUpdate("insert into ADDITION_DELETION_DEPWDV(" + str17 + ") select " + str18 + " from addition_deletion a left outer join " + str1 + " m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from " + str1 + ") and a.adddate < convert(datetime,'" + paramString1 + "',101))");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set assetid=h.assetid,instl_date=" + str9 + " from ADDITION_DELETION_DEPWDV a, hardware_Register h where a.ast_id=h.ast_id");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set deprate=h.cwdv from ADDITION_DELETION_DEPWDV a, dep_master h where a.group_id=h.group_id");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,ADDITION_DELETION_DEPWDV where addition_deletion.assetid=ADDITION_DELETION_DEPWDV.ast_id and addition_deletion.asset_status in('sold','writeoff')");
    }
    catch (Exception localException10) {
      System.out.println("Error 2" + localException10);
    }

    try
    {
      WDVMonthDepCalcUL localWDVMonthDepCalcUL = new WDVMonthDepCalcUL();
      localWDVMonthDepCalcUL.calculateDep(  request);
      localWDVMonthDepCalcUL.add_del_calculateDep(   request);
      localWDVMonthDepCalcUL.transfer_calculateDep(paramString1, paramString2,
    		  paramString3,  request);
    }
    catch (Exception localException11) {
      System.out.println("Error 3" + localException11);
    }
    try
    {
      localStatement1.executeUpdate("insert into " + str1 + " (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select type_id,group_id,ast_id,assetid,'0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','" + paramString1 + "','" + paramString2 + "',writeoff,status from " + str1 + " where wdv=0.0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV'");

      localStatement1.executeUpdate("insert into " + str1 + " (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select 'CAWDV',GROUP_ID,ast_id,assetid,'0.00',assetvalue,assetvalue,'0.00','0.00','0.00',assetvalue,assetvalue,'0.00','0.00','" + paramString1 + "','" + paramString2 + "','0.00','-' FROM hardware_register where assetvalue <=" + d1 + " and group_id in(" + str4 + ") and installed in (1,2,3) and ast_id not in(select ast_id from " + str1 + " where type_id='CAWDV')");
    }
    catch (Exception localException12) {
      System.out.println("Error 3.1" + localException12);
    }
  }
}