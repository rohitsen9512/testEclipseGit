package com.LibraryForDprn;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;

public class TransferHardwareRegisterSLM
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

    String str1 = "-";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    double d1 = 0.0D;
    double d2 = 0.0D;
    String str10 = null;

    localResultSet = localStatement1.executeQuery("select * from dep_param where type_id='CA'");
    if (localResultSet.next()) {
      str2 = localResultSet.getString("capitalization_date");
      str3 = localResultSet.getString("dep_start");
      str4 = localResultSet.getString("capitalization_comp");
      str8 = localResultSet.getString("dep_method");
      str9 = localResultSet.getString("dep_based_on");
      d1 = localResultSet.getDouble("write_off_value");
      d2 = localResultSet.getDouble("dep_stop");
      str10 = localResultSet.getString("dep_no_of_days");
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str4, ",");
    str4 = "-";
    ArrayList localArrayList = new ArrayList();
    while (localStringTokenizer.hasMoreTokens()) {
      str7 = localStringTokenizer.nextToken();
      localArrayList.add(str7);
    }
    for (int i = 0; i < localArrayList.size(); i++) {
      if (str4.equals("-"))
        str4 = "" + localArrayList.get(i);
      else {
        str4 = str4 + "+" + localArrayList.get(i);
      }
    }
    if (str4.equals("-"))
      str4 = "assetvalue";
    else {
      str4 = "(" + str4 + ")";
    }
    if (str3.equals("Actual_Date")) {
      str5 = str2;
      str6 = "h." + str2;
    } else if (str3.equals("First_Day")) {
      str5 = "" + str2 + " - day(convert(datetime," + str2 + " ,101))+1";
      str6 = "h." + str2 + " - day(convert(datetime,h." + str2 + " ,101))-1";
    } else if (str3.equals("First_Day_Next_Month")) {
      str5 = "dateadd(m,1, convert(datetime," + str2 + ",101)) - day(dateadd(m,1, convert(datetime," + str2 + ",101))) + 1";
      str6 = "dateadd(m,1, convert(datetime,h." + str2 + ",101)) - day(dateadd(m,1, convert(datetime,h." + str2 + ",101))) + 1";
    } else if (str3.equals("First_Day_Half_Break")) {
      str5 = "(convert(datetime," + str2 + ",101) + 17) - day(convert(datetime," + str2 + ",101) + 17) + 1";
      str6 = "(convert(datetime,h." + str2 + ",101) + 17) - day(convert(datetime,h." + str2 + ",101) + 17) + 1";
    }
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (str1.equals("-"))
        str1 = "'" + paramArrayList.get(i) + "'";
      else {
        str1 = str1 + ",'" + paramArrayList.get(i) + "'";
      }
    }
    if (str1.equals("-")) {
      str1 = "'0'";
    }
    System.out.println(str1);
    String str11 = "ast_id,assetvalue,leasestatus,instl_date,leasestartdate,deprate,assetid,group_id,leaseenddate,start_date,end_date";

    String str12 = "id_wh," + str4 + "," + "st_lease," + "" + str5 + "," + "std_lease," + "depreciate," + "id_wh_dyn," + "id_grp," + "endt_lease," + "'" + paramString1 + "' as startdate," + "'" + paramString2 + "' as enddate";

    String str13 = "ast_id,assetvalue,asset_status,asset_status_date,group_id,start_date,end_date";

    String str14 = "a.assetid,a.addamt,a.asset_status,a.adddate,a.asset_group_id,'" + paramString1 + "' as startdate," + "'" + paramString2 + "' as enddate";

    if (paramString3.equals("monthly")) {
      System.out.println("Monthly Block");
      try {
        localStatement1.executeUpdate("truncate table HR_DEP");
      } catch (Exception localException2) {
        System.out.println("NO Recorde To Truncate");
      }
      try
      {
        localStatement1.executeUpdate("delete from month_depreciation where enddate >= '" + paramString2 + "' and group_id in(" + str1 + ") AND type_id='CASLM'");

        localStatement1.executeUpdate("insert into HR_DEP(" + str11 + ") select " + str12 + " from A_ware_House where allocate in(1,2,3) and " + str5 + " < '" + paramString2 + "' and id_grp in(" + str1 + ") AND st_lease in('Nolease','NUL') and id_wh not in(select ast_id from month_depreciation where wdv=0  and startdate=dateadd(month,-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CASLM') and " + str4 + " > = " + d1 + "");
        localStatement1.executeUpdate("update hr_dep set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_dep where hr_dep.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
        localStatement1.executeUpdate("update hr_dep set dep_startdate='" + paramString1 + "',dep_enddate='" + paramString2 + "'");
        localStatement1.executeUpdate("update hr_dep set leasestatus = 'NUL' where leasestatus='NoLease'");
        localStatement1.executeUpdate("update hr_dep set deprate = d.cslm from hr_dep h, D_Dprn_Master d where d.id_grp = h.group_id and deprate = 0 ");
        localStatement1.executeUpdate("update hr_dep set previous_depamount=month_depreciation.depreciated_total,wdv=month_depreciation.wdv,dep_flag='EXISTING',assetvalue=month_depreciation.asset_total from month_depreciation,hr_dep where hr_dep.assetid=month_depreciation.assetid and month_depreciation.enddate=convert(datetime,'" + paramString1 + "',101)-1");
        localStatement1.executeUpdate("delete from hr_dep where ast_id in (select id_wh from A_Ware_House a,addition_deletion b where a.id_wh=b.assetid and b.asset_status in('sold','writeoff') and a.id_wh_dyn  in  (select assetid from month_depreciation where type_id='CASLM' and status in('sold','writeoff')))");
      }
      catch (Exception localException3)
      {
        System.out.println("Error 1" + localException3);
      }
      try
      {
        localStatement1.executeUpdate("truncate table ADDITION_DELETION_DEP");
      } catch (Exception localException4) {
        System.out.println("NO Recorde To Truncate");
      }
      try
      {
    	 // System.out.print("insert into ADDITION_DELETION_DEP(" + str13 + ") select " + str14 + " from addition_deletion a left outer join month_depreciation m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.ondate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from month_depreciation) and a.adddate < convert(datetime,'" + paramString1 + "',101))");
    	     
        localStatement1.executeUpdate("insert into ADDITION_DELETION_DEP(" + str13 + ") select " + str14 + " from addition_deletion a left outer join month_depreciation m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from month_depreciation) and a.adddate < convert(datetime,'" + paramString1 + "',101))");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set assetid=h.id_wh_dyn,instl_date=" + str6 + " from addition_deletion_dep a, A_Ware_House h where a.ast_id=h.id_wh");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set deprate=h.cslm from addition_deletion_dep a, D_Dprn_Master h where a.group_id=h.id_grp");
        localStatement1.executeUpdate("update addition_deletion_dep set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,addition_deletion_dep where addition_deletion.assetid=addition_deletion_dep.ast_id and addition_deletion.asset_status in('sold','writeoff')");
      }
      catch (Exception localException5)
      {
        System.out.println("Error 2" + localException5);
      }
      try
      {
        MonthDepCalcNUL localMonthDepCalcNUL = new MonthDepCalcNUL();
        localMonthDepCalcNUL.calculateDep( request);
        localMonthDepCalcNUL.add_del_calculateDep( request);
        localMonthDepCalcNUL.transfer_calculateDep(paramString1, paramString2,  request);
      } catch (Exception localException6) {
        System.out.println("Error 3" + localException6);
      }

      try
      {
        localStatement1.executeUpdate("truncate table HR_DEP");
      } catch (Exception localException7) {
        System.out.println("NO Recorde To Truncate");
      }
      try {
        localStatement1.executeUpdate("insert into HR_DEP(" + str11 + ") select " + str12 + " from A_Ware_House where allocate in(1,2,3) and " + str5 + " < '" + paramString2 + "' and id_grp in(" + str1 + ") AND st_lease in('lease','UL') and id_wh not in(select ast_id from month_depreciation where wdv=0 and startdate=dateadd(month,-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CASLM') and " + str4 + " > = " + d1 + "");
        localStatement1.executeUpdate("update hr_dep set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_dep where hr_dep.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
        localStatement1.executeUpdate("update hr_dep set dep_startdate='" + paramString1 + "',dep_enddate='" + paramString2 + "'");
        localStatement1.executeUpdate("update hr_dep set leasestatus = 'UL' where leasestatus='Lease'");
        localStatement1.executeUpdate("update hr_dep set deprate = d.cslm from hr_dep h, D_Dprn_Master d where d.id_grp = h.group_id and deprate = 0 ");
        localStatement1.executeUpdate("update hr_dep set previous_depamount=month_depreciation.depreciated_total,wdv=month_depreciation.wdv,dep_flag='EXISTING',assetvalue=month_depreciation.asset_total from month_depreciation,hr_dep where hr_dep.assetid=month_depreciation.assetid and month_depreciation.enddate=convert(datetime,'" + paramString1 + "',101)-1");
        localStatement1.executeUpdate("delete from hr_dep where ast_id in (select id_wh from A_Ware_House a,addition_deletion b where a.id_wh=b.assetid and b.asset_status in('sold','writeoff') and a.id_wh_dyn  in  (select assetid from month_depreciation where type_id='CASLM' and status in('sold','writeoff')))");
      }
      catch (Exception localException8) {
        System.out.println("Error 1" + localException8);
      }
      try {
        localStatement1.executeUpdate("truncate table ADDITION_DELETION_DEP");
      } catch (Exception localException9) {
        System.out.println("NO Recorde To Truncate");
      }
      try
      {
    	localStatement1.executeUpdate("insert into ADDITION_DELETION_DEP(" + str13 + ") select " + str14 + " from addition_deletion a left outer join month_depreciation m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from month_depreciation) and a.adddate < convert(datetime,'" + paramString1 + "',101))");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set assetid=h.id_wh_dyn,instl_date=" + str6 + " from addition_deletion_dep a, A_Ware_House h where a.ast_id=h.id_wh");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set deprate=h.cslm from addition_deletion_dep a, D_Dprn_Master h where a.group_id=h.id_grp");
        localStatement1.executeUpdate("update addition_deletion_dep set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,addition_deletion_dep where addition_deletion.assetid=addition_deletion_dep.ast_id and addition_deletion.asset_status in('sold','writeoff')");
      }
      catch (Exception localException10) {
        System.out.println("Error 2" + localException10);
      }

      try
      {
    	  
        MonthDepCalcUL localMonthDepCalcUL = new MonthDepCalcUL();
        localMonthDepCalcUL.calculateDep(   request);
        localMonthDepCalcUL.add_del_calculateDep(   request);
        localMonthDepCalcUL.transfer_calculateDep(paramString1, paramString2,  request);
      } catch (Exception localException11) {
        System.out.println("Error 3" + localException11);
      }
      try {
    	  String sql="insert into month_depreciation (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,'" + paramString1 + "','" + paramString2 + "',writeoff,status from month_depreciation where wdv=0 and startdate=dateadd(month,-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CASLM'";
        localStatement1.executeUpdate(sql);
        localStatement1.executeUpdate("update month_depreciation set asondate=depreciated_total,depreciated_opening=0.0,depreciated_addition=0.0 where ast_id in (select ast_id from month_depreciation where wdv=0 and startdate=dateadd(month,-1,convert(datetime,'" + paramString1 + "'))) and startdate='" + paramString1 + "' and enddate='" + paramString2 + "'");

        localStatement1.executeUpdate("insert into month_depreciation (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select 'CASLM',id_grp,id_wh,id_wh_dyn,'0.00',val_asst,val_asst,'0.00','0.00','0.00',val_asst,val_asst,'0.00','0.00','" + paramString1 + "','" + paramString2 + "','0.00','-' FROM A_Ware_House where val_asst <=" + d1 + " and st_lease='NUL' and id_grp in(" + str1 + ") and id_wh not in(select ast_id from month_depreciation where type_id='CASLM')");
      }
      catch (Exception localException12)
      {
        System.out.println("Error 3.1" + localException12);
      }
    } else if (paramString3.equals("yearly"))
    {
      System.out.println("Yearly Block");
      try {
        localStatement1.executeUpdate("truncate table HR_DEP");
      } catch (Exception localException13) {
        System.out.println("NO Recorde To Truncate");
      }
      try {
        localStatement1.executeUpdate("delete from stline_depreciation where enddate >= '" + paramString2 + "' and group_id in(" + str1 + ") AND type_id='CASLM'");
        localStatement1.executeUpdate("insert into HR_DEP(" + str11 + ") select " + str12 + " from A_Ware_House where allocate in(1,2,3) and " + str5 + " < '" + paramString2 + "' and id_grp in(" + str1 + ") AND st_lease in ('Nolease','NUL') and id_wh not in(select ast_id from stline_depreciation where wdv=0 and startdate=dateadd(year,-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CASLM') and " + str4 + " > = " + d1 + "");
        localStatement1.executeUpdate("update hr_dep set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_dep where hr_dep.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
        localStatement1.executeUpdate("update hr_dep set dep_startdate='" + paramString1 + "',dep_enddate='" + paramString2 + "'");
        localStatement1.executeUpdate("update hr_dep set leasestatus = 'NUL' where leasestatus='NoLease'");
        localStatement1.executeUpdate("update hr_dep set deprate = d.cslm from hr_dep h, D_Dprn_Master d where d.id_grp = h.group_id and deprate = 0 ");
        localStatement1.executeUpdate("update hr_dep set previous_depamount=stline_depreciation.depreciated_total,wdv=stline_depreciation.wdv,dep_flag='EXISTING',assetvalue=stline_depreciation.asset_total from stline_depreciation,hr_dep where hr_dep.assetid=stline_depreciation.assetid and stline_depreciation.enddate=convert(datetime,'" + paramString1 + "',101)-1");
        localStatement1.executeUpdate("delete from hr_dep where ast_id in (select id_wh from A_Ware_House a,addition_deletion b where a.id_wh=b.assetid and b.asset_status in('sold','writeoff') and a.id_wh_dyn  in  (select assetid from stline_depreciation where type_id='CASLM' and status in('sold','writeoff')))");
      }
      catch (Exception localException14)
      {
        System.out.println("Error 1" + localException14);
      }
      try
      {
        localStatement1.executeUpdate("truncate table ADDITION_DELETION_DEP");
      } catch (Exception localException15) {
        System.out.println("NO Recorde To Truncate");
      }

      try
      {
        localStatement1.executeUpdate("insert into ADDITION_DELETION_DEP(" + str13 + ") select " + str14 + " from addition_deletion a left outer join stline_depreciation m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from stline_depreciation) and a.adddate < convert(datetime,'" + paramString1 + "',101))");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set assetid=h.id_wh_dyn,instl_date=" + str6 + " from addition_deletion_dep a, A_Ware_House h where a.ast_id=h.id_wh");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set deprate=h.cslm from addition_deletion_dep a, D_Dprn_Master h where a.group_id=h.id_grp");
        localStatement1.executeUpdate("update addition_deletion_dep set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,addition_deletion_dep where addition_deletion.assetid=addition_deletion_dep.ast_id and addition_deletion.asset_status in('sold','writeoff')");
      }
      catch (Exception localException16)
      {
        System.out.println("Error 2" + localException16);
      }
      try
      {
        YearlyDepCalcNUL localYearlyDepCalcNUL = new YearlyDepCalcNUL();
        localYearlyDepCalcNUL.calculateDep( request);
        localYearlyDepCalcNUL.add_del_calculateDep( request);
        localYearlyDepCalcNUL.transfer_calculateDep(paramString1, paramString2, request);
      } catch (Exception localException17) {
        System.out.println("Error 3" + localException17);
      }

      try
      {
        localStatement1.executeUpdate("truncate table HR_DEP");
      } catch (Exception localException18) {
        System.out.println("NO Recorde To Truncate");
      }
      try {
        localStatement1.executeUpdate("insert into HR_DEP(" + str11 + ") select " + str12 + " from A_Ware_House where allocate in('1') and " + str5 + " < '" + paramString2 + "' and id_grp in(" + str1 + ") AND st_lease in('lease','UL') and id_wh not in(select ast_id from stline_depreciation where wdv=0 and startdate=dateadd(year,-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CASLM') and " + str4 + " > = " + d1 + "");
        localStatement1.executeUpdate("update hr_dep set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_dep where hr_dep.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
        localStatement1.executeUpdate("update hr_dep set dep_startdate='" + paramString1 + "',dep_enddate='" + paramString2 + "'");
        localStatement1.executeUpdate("update hr_dep set leasestatus = 'UL' where leasestatus='Lease'");
        localStatement1.executeUpdate("update hr_dep set deprate = d.cslm from hr_dep h, D_Dprn_Master d where d.id_grp = h.group_id and deprate = 0 ");
        localStatement1.executeUpdate("update hr_dep set previous_depamount=stline_depreciation.depreciated_total,wdv=stline_depreciation.wdv,dep_flag='EXISTING',assetvalue=stline_depreciation.asset_total from stline_depreciation,hr_dep where hr_dep.assetid=stline_depreciation.assetid and stline_depreciation.enddate=convert(datetime,'" + paramString1 + "',101)-1");
        localStatement1.executeUpdate("delete from hr_dep where ast_id in (select id_wh from A_Ware_House a,addition_deletion b where a.id_wh=b.assetid and b.asset_status in('sold','writeoff') and a.id_wh_dyn  in  (select assetid from stline_depreciation where type_id='CASLM' and status in('sold','writeoff')))");
      }
      catch (Exception localException19) {
        System.out.println("Error 1" + localException19);
      }
      try {
        localStatement1.executeUpdate("truncate table ADDITION_DELETION_DEP");
      } catch (Exception localException20) {
        System.out.println("NO Recorde To Truncate");
      }
      try
      {
        localStatement1.executeUpdate("insert into ADDITION_DELETION_DEP(" + str13 + ") select " + str14 + " from addition_deletion a left outer join stline_depreciation m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from stline_depreciation) and a.adddate < convert(datetime,'" + paramString1 + "',101))");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set assetid=h.id_wh_dyn,instl_date=" + str6 + " from addition_deletion_dep a, A_Ware_House h where a.ast_id=h.id_wh");
        localStatement1.executeUpdate("update ADDITION_DELETION_DEP set deprate=h.cslm from addition_deletion_dep a, D_Dprn_Master h where a.group_id=h.id_grp");
        localStatement1.executeUpdate("update addition_deletion_dep set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,addition_deletion_dep where addition_deletion.assetid=addition_deletion_dep.ast_id and addition_deletion.asset_status in('sold','writeoff')");
      }
      catch (Exception localException21) {
        System.out.println("Error 2" + localException21);
      }
      try {
        YearlyDepCalcUL localYearlyDepCalcUL = new YearlyDepCalcUL();
        localYearlyDepCalcUL.calculateDep(  request);
        localYearlyDepCalcUL.add_del_calculateDep( request);
        localYearlyDepCalcUL.transfer_calculateDep(paramString1, paramString2, request);
      } catch (Exception localException22) {
        System.out.println("Error 3" + localException22);
      }
      try
      {
        localStatement1.executeUpdate("insert into stline_depreciation (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,'" + paramString1 + "','" + paramString2 + "',writeoff,status from stline_depreciation where wdv=0 and startdate=dateadd(year,-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CASLM'");
        localStatement1.executeUpdate("update stline_depreciation set asondate=depreciated_total,depreciated_opening=0.0,depreciated_addition=0.0 where ast_id in (select ast_id from stline_depreciation where wdv=0 and startdate=dateadd(year,-1,convert(datetime,'" + paramString1 + "'))) and startdate='" + paramString1 + "' and enddate='" + paramString2 + "'");

        localStatement1.executeUpdate("insert into stline_depreciation (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select 'CASLM',id_grp,id_wh,id_wh_dyn,'0.00',val_asst,val_asst,'0.00','0.00','0.00',val_asst,val_asst,'0.00','0.00','" + paramString1 + "','" + paramString2 + "','0.00','-' FROM A_Ware_House where val_asst <=" + d1 + "  and st_lease='NUL' and id_grp in(" + str1 + ") and id_wh not in(select ast_id from stline_depreciation where type_id='CASLM')");
      } catch (Exception localException23) {
        System.out.println("Error 3.2" + localException23);
      }
    }
  }
}