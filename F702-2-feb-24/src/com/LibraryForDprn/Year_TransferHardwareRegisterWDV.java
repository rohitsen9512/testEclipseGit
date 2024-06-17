package com.LibraryForDprn;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;


public class Year_TransferHardwareRegisterWDV
{
  public void transfer(String paramString1, String paramString2,
		  String paramString3, ArrayList paramArrayList,String DepType,HttpServletRequest request)
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
      if(DepType.equals("IT"))
      {
      	 str1 = "it_depreciation";
      }
    }

    String str3 = "-";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    double d1 = 0.0D;
    double d2 = 0.0D;
    String str12 = null;

    localResultSet = localStatement1.executeQuery("select * from dep_param where type_id='"+DepType+"'");
    if (localResultSet.next()) {
      str4 = localResultSet.getString("capitalization_date");
      str5 = localResultSet.getString("dep_start");
      str6 = localResultSet.getString("capitalization_comp");
      str10 = localResultSet.getString("dep_method");
      str11 = localResultSet.getString("dep_based_on");
      d1 = localResultSet.getDouble("write_off_value");
      d2 = localResultSet.getDouble("dep_stop");
      str12 = localResultSet.getString("dep_no_of_days");
    }StringTokenizer localStringTokenizer = new StringTokenizer(str6, ",");
    str6 = "-";
    ArrayList localArrayList = new ArrayList();
    while (localStringTokenizer.hasMoreTokens()) {
      str9 = localStringTokenizer.nextToken();
      localArrayList.add(str9);
    }
    for (int i = 0; i < localArrayList.size(); i++) {
      if (str6.equals("-"))
        str6 = "" + localArrayList.get(i);
      else {
        str6 = str6 + "+" + localArrayList.get(i);
      }
    }
    if (str6.equals("-"))
      str6 = "assetvalue";
    else {
      str6 = "(" + str6 + ")";
    }
    if (str5.equals("Actual_Date")) {
      str7 = str4;
      str8 = "h." + str4;
    } else if (str5.equals("First_Day")) {
      str7 = "" + str4 + " - day(convert(datetime," + str4 + " ,101))+1";
      str8 = "h." + str4 + " - day(convert(datetime,h." + str4 + " ,101))-1";
    } else if (str5.equals("First_Day_Next_Month")) {
      str7 = "dateadd(m,1, convert(datetime," + str4 + ",101)) - day(dateadd(m,1, convert(datetime," + str4 + ",101))) + 1";
      str8 = "dateadd(m,1, convert(datetime,h." + str4 + ",101)) - day(dateadd(m,1, convert(datetime,h." + str4 + ",101))) + 1";
    } else if (str5.equals("First_Day_Half_Break")) {
      str7 = "(convert(datetime," + str4 + ",101) + 17) - day(convert(datetime," + str4 + ",101) + 17) + 1";
      str8 = "(convert(datetime,h." + str4 + ",101) + 17) - day(convert(datetime,h." + str4 + ",101) + 17) + 1";
    }
    for (int j = 0; j < paramArrayList.size(); j++) {
      if (str3.equals("-"))
        str3 = "'" + paramArrayList.get(j) + "'";
      else {
        str3 = str3 + ",'" + paramArrayList.get(j) + "'";
      }
    }
    if (str3.equals("-")) {
      str3 = "'0'";
    }
    System.out.println(str3);
//    String str13 = "ast_id,assetvalue,leasestatus,instl_date,leasestartdate,deprate,assetid,group_id,leaseenddate,start_date,startdate,end_date";
//
//    String str14 = "ast_id," + str6 + "," + "leasestatus," + "" + str7 + "," + "leasestartdate," + "deprate," + "assetid," + "group_id," + "leaseenddate," + "'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";
//
//    String str15 = "ast_id," + str6 + "," + "leasestatus," + "" + str7 + "," + "softlife_stdate," + "deprate," + "assetid," + "group_id," + "softlife_enddate," + "'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";
//
//    String str16 = "ast_id,assetvalue,asset_status,asset_status_date,group_id,start_date,startdate,end_date";
//
//    String str17 = "a.assetid,a.addamt,a.asset_status,a.adddate,a.asset_group_id,'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";
    String str13 = "ast_id,assetvalue,leasestatus,instl_date,leasestartdate,deprate,assetid,group_id,leaseenddate,start_date,startdate,end_date";

    String str14 = "id_wh," + str6 + "," + "st_lease," + "" + str7 + "," + "std_lease," + "depreciate," + "id_wh_dyn," + "id_grp," + "endt_lease," + "'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";

    String str15 = "id_wh," + str6 + "," + "st_lease," + "" + str7 + "," + "softlife_stdate," + "depreciate," + "id_wh_dyn," + "id_grp," + "softlife_enddate," + "'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";

    String str16 = "ast_id,assetvalue,asset_status,asset_status_date,group_id,start_date,startdate,end_date";

    String str17 = "a.assetid,a.addamt,a.asset_status,a.adddate,a.asset_group_id,'" + paramString1 + "' as startdate," + "'" + paramString1 + "' as t_startdate," + "'" + paramString2 + "' as enddate";

    System.out.println("Monthly Block RCS");
    
    try {
      localStatement1.executeUpdate("truncate table hr_depWDV");
    } catch (Exception localException2) {
      System.out.println("NO Recorde To Truncate");
    }
    try {
//      localStatement1.executeUpdate("delete from " + str1 + " where enddate >= '" + paramString2 + "' and group_id in(" + str3 + ") AND type_id='CAWDV'");
//      localStatement1.executeUpdate("delete from cost_depreciation where enddate >= '" + paramString2 + "' and group_id in(" + str3 + ") AND type_id='CAWDV'");
//      localStatement1.executeUpdate("insert into hr_depWDV(" + str13 + ") select " + str14 + " from hardware_register where installed in(1,2,3) and " + str7 + " < '" + paramString2 + "' and group_id in(" + str3 + ") AND leasestatus in('Nolease','NUL') and ast_id not in(select ast_id from " + str1 + " where wdv=0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV') and " + str6 + " > = " + d1 + "");
//      localStatement1.executeUpdate("update hr_depWDV set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_depWDV where hr_depWDV.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
//      localStatement1.executeUpdate("update hr_depWDV set dep_startdate='" + paramString1 + "',dep_enddate='" + paramString2 + "'");
//      localStatement1.executeUpdate("update hr_depWDV set leasestatus = 'NUL' where leasestatus='NoLease'");
//      localStatement1.executeUpdate("update hr_depWDV set deprate = d.cwdv from hr_depWDV h, dep_master d where d.group_id = h.group_id and deprate = 0 ");
//      localStatement1.executeUpdate("update hr_depWDV set previous_depamount=" + str1 + ".depreciated_total,wdv=" + str1 + ".wdv,dep_flag='EXISTING' from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + paramString1 + "',101)-1");
//      localStatement1.executeUpdate("update hr_depWDV set assetvalue=" + str1 + ".wdv from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + paramString1 + "',101)-1");
//      localStatement1.executeUpdate("delete from hr_depWDV where ast_id in (select AST_ID from hardware_register a,addition_deletion b where a.ast_id=b.assetid and b.asset_status in('sold','writeoff') and a.assetid  in  (select assetid from " + str1 + " where type_id='CAWDV' and status in('sold','writeoff')))");
//    
    	 localStatement1.executeUpdate("delete from " + str1 + " where enddate >= '" + paramString2 + "' and group_id in(" + str3 + ") AND type_id='CAWDV'");
         localStatement1.executeUpdate("delete from cost_depreciation where enddate >= '" + paramString2 + "' and group_id in(" + str3 + ") AND type_id='CAWDV'");
         localStatement1.executeUpdate("insert into hr_depWDV(" + str13 + ") select " + str14 + " from A_Ware_House where allocate in(1,2,3) and " + str7 + " < '" + paramString2 + "' and id_grp in(" + str3 + ") AND st_lease in('Nolease','NUL') and id_wh not in(select ast_id from " + str1 + " where wdv=0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV') and " + str6 + " > = " + d1 + "");
         localStatement1.executeUpdate("update hr_depWDV set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_depWDV where hr_depWDV.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
         localStatement1.executeUpdate("update hr_depWDV set dep_startdate='" + paramString1 + "',dep_enddate='" + paramString2 + "'");
         localStatement1.executeUpdate("update hr_depWDV set leasestatus = 'NUL' where leasestatus='NoLease'");
         localStatement1.executeUpdate("update hr_depWDV set deprate = d.iwdv from hr_depWDV h, D_Dprn_Master d where d.id_grp = h.group_id and deprate = 0 ");
         localStatement1.executeUpdate("update hr_depWDV set previous_depamount=" + str1 + ".depreciated_total,wdv=" + str1 + ".wdv,dep_flag='EXISTING' from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + paramString1 + "',101)-1");
         localStatement1.executeUpdate("update hr_depWDV set assetvalue=" + str1 + ".wdv from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + paramString1 + "',101)-1");
         localStatement1.executeUpdate("delete from hr_depWDV where ast_id in (select id_wh from A_Ware_House a,addition_deletion b where a.id_wh=b.assetid and b.asset_status in('sold','writeoff') and a.id_wh_dyn  in  (select assetid from " + str1 + " where type_id='CAWDV' and status in('sold','writeoff')))");
       	
    
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
      localStatement1.executeUpdate("insert into ADDITION_DELETION_DEPWDV(" + str16 + ") select " + str17 + " from addition_deletion a left outer join " + str1 + " m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from " + str1 + ") and a.adddate < convert(datetime,'" + paramString1 + "',101))");

      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set assetid=h.id_wh_dyn,instl_date=" + str8 + " from ADDITION_DELETION_DEPWDV a, A_Ware_House h where a.ast_id=h.id_wh");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set deprate=h.iwdv from ADDITION_DELETION_DEPWDV a, D_Dprn_Master h where a.group_id=h.id_grp");

      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,ADDITION_DELETION_DEPWDV where addition_deletion.assetid=ADDITION_DELETION_DEPWDV.ast_id and addition_deletion.asset_status in('sold','writeoff')");
    }
    catch (Exception localException5)
    {
      System.out.println("Error 2" + localException5);
    }
    try
    {
      Year_WDVMonthDepCalcNUL localYear_WDVMonthDepCalcNUL = new Year_WDVMonthDepCalcNUL();
      localYear_WDVMonthDepCalcNUL.calculateDep(  request);
      localYear_WDVMonthDepCalcNUL.add_del_calculateDep(  request);
      localYear_WDVMonthDepCalcNUL.transfer_calculateDep(paramString1, 
    		  paramString2, paramString3,DepType,  request);
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
      localStatement1.executeUpdate("insert into hr_depWDV(" + str13 + ") select " + str14 + " from A_Ware_House where allocate in(1,2,3) and " + str7 + " < '" + paramString2 + "' and id_grp in(" + str3 + ") AND st_lease in('lease','UL') and id_wh not in(select ast_id from " + str1 + " where wdv=0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV') and " + str6 + " > = " + d1 + "");
      localStatement1.executeUpdate("insert into hr_depWDV(" + str13 + ") select " + str15 + " from A_Ware_House where allocate in(1.2,3) and " + str7 + " < '" + paramString2 + "' and id_grp in(" + str3 + ") AND st_lease in('') and id_wh not in(select ast_id from " + str1 + " where wdv=0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV') and " + str6 + " > = " + d1 + "");
      localStatement1.executeUpdate("update hr_depWDV set Sold_writeoff=addition_deletion.asset_status,sold__writeoff_date=addition_deletion.stadate from addition_deletion,hr_depWDV where hr_depWDV.ast_id=addition_deletion.assetid and addition_deletion.asset_status in('writeoff','sold') and addition_deletion.stadate < '" + paramString2 + "' ");
      localStatement1.executeUpdate("update hr_depWDV set dep_startdate='" + paramString1 + "',dep_enddate='" + paramString2 + "'");
      localStatement1.executeUpdate("update hr_depWDV set leasestatus = 'UL' where leasestatus in('Lease','')");
      localStatement1.executeUpdate("update hr_depWDV set deprate = d.cwdv from hr_depWDV h, D_Dprn_Master d where d.id_grp = h.group_id and deprate = 0 ");
      localStatement1.executeUpdate("update hr_depWDV set previous_depamount=" + str1 + ".depreciated_total,wdv=" + str1 + ".wdv,dep_flag='EXISTING',assetvalue=" + str1 + ".asset_total from " + str1 + ",hr_depWDV where hr_depWDV.assetid=" + str1 + ".assetid and " + str1 + ".enddate=convert(datetime,'" + paramString1 + "',101)-1");

      localStatement1.executeUpdate("delete from hr_depWDV where ast_id in (select id_wh from A_Ware_House a,addition_deletion b where a.id_wh=b.assetid and b.asset_status in('sold','writeoff') and a.id_wh_dyn  in  (select assetid from " + str1 + " where type_id='CAWDV' and status in('sold','writeoff')))");
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
      localStatement1.executeUpdate("insert into ADDITION_DELETION_DEPWDV(" + str16 + ") select " + str17 + " from addition_deletion a left outer join " + str1 + " m on a.assetid=m.ast_id where (a.asset_status in('add','delete') and a.adddate between convert(datetime,'" + paramString1 + "',101) and convert(datetime,'" + paramString2 + "',101)) or  (a.asset_status in('add','delete') and  a.assetid not in (select ast_id from " + str1 + ") and a.adddate < convert(datetime,'" + paramString1 + "',101))");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set assetid=h.id_wh_dyn,instl_date=" + str8 + " from ADDITION_DELETION_DEPWDV a, A_Ware_House h where a.ast_id=h.id_wh");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set deprate=h.cwdv from ADDITION_DELETION_DEPWDV a, D_Dprn_Master h where a.group_id=h.id_grp");
      localStatement1.executeUpdate("update ADDITION_DELETION_DEPWDV set sold_writeoff_flag=addition_deletion.asset_status,sold_writeoff_date=addition_deletion.stadate from addition_deletion,ADDITION_DELETION_DEPWDV where addition_deletion.assetid=ADDITION_DELETION_DEPWDV.ast_id and addition_deletion.asset_status in('sold','writeoff')");
    }
    catch (Exception localException10) {
      System.out.println("Error 2" + localException10);
    }

    try
    {
    	
      Year_WDVMonthDepCalcUL localYear_WDVMonthDepCalcUL = new Year_WDVMonthDepCalcUL();
      localYear_WDVMonthDepCalcUL.calculateDep(  request);
      localYear_WDVMonthDepCalcUL.add_del_calculateDep(   request);
      localYear_WDVMonthDepCalcUL.transfer_calculateDep(paramString1,
    		  paramString2, paramString3,DepType,  request);
    }
    catch (Exception localException11) {
      System.out.println("Error 3" + localException11);
    }
    try
    {
      localStatement1.executeUpdate("insert into " + str1 + " (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select type_id,group_id,ast_id,assetid,'0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','" + paramString1 + "','" + paramString2 + "',writeoff,status from " + str1 + " where wdv=0.0 and startdate=dateadd(" + str2 + ",-1,convert(datetime,'" + paramString1 + "')) and status not in ('sold','writeoff') and type_id='CAWDV'");

      localStatement1.executeUpdate("insert into " + str1 + " (type_id,group_id,ast_id,assetid,asset_opening,asset_addition,asset_deletion,asset_total,asondate,depreciated_opening,depreciated_addition,depreciated_deletion,depreciated_total,wdv,startdate,enddate,writeoff,status) select 'CAWDV',id_grp,id_wh,id_wh_dyn,'0.00',val_asst,val_asst,'0.00','0.00','0.00',val_asst,val_asst,'0.00','0.00','" + paramString1 + "','" + paramString2 + "','0.00','-' FROM A_Ware_House where val_asst <='" + d1 + "' and id_grp in(" + str3 + ") and allocate in (1,2,3) and id_wh not in(select ast_id from " + str1 + " where type_id='CAWDV')");
    }
    catch (Exception localException12) {
      System.out.println("Error 3.1" + localException12);
    }
  }
}