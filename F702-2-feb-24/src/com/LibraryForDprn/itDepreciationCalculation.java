package com.LibraryForDprn;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;

public class itDepreciationCalculation
{
  public void calculateITDep(String paramString1, String paramString2, ArrayList paramArrayList,HttpServletRequest request)
    throws Exception
  {
    String str1 = "-";
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (str1.equals("-"))
        str1 = "" + paramArrayList.get(i) + "";
      else {
        str1 = str1 + "," + paramArrayList.get(i) + "";
      }
    }
    if (str1.equals("-")) {
      str1 = "0";
    }

    Statement localStatement1 = null;
    Connection localConnection = null;
    ResultSet localResultSet = null;
    Statement localStatement2 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    String str2 = null;
    try
    {
      
      localConnection = Common.GetConnection(request);
      localStatement1 = localConnection.createStatement();
    }
    catch (Exception localException1)
    {
    }
    String str3 = paramString1;
    localObject3 = paramString2;
    String str4 = "";
    String str5 = "";

    localStatement1 = localConnection.createStatement();
    localStatement2 = localConnection.createStatement();

    localResultSet = localStatement1.executeQuery("select dateadd(yyyy,-1,'" + paramString1 + "'),dateadd(yyyy,-1,'" + paramString2 + "')");
    if (localResultSet.next()) {
      str4 = localResultSet.getString(1);
      str5 = localResultSet.getString(2);
    }
    try
    {
      localStatement1.executeUpdate("drop table normal_wdv");
    } catch (Exception localException2) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while droping normal_wdv table:\n" + localException2 + "\n...ends\n");
    }
    try
    {
      localStatement1.executeUpdate("drop table normal_wdv_2");
    } catch (Exception localException3) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while droping normal_wdv_2 table:\n" + localException3 + "\n...ends\n");
    }
    try
    {
      localStatement1.executeUpdate("drop table normal_wdv_3");
    } catch (Exception localException4) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while droping normal_wdv_3 table:\n" + localException4 + "\n...ends\n");
    }

    try
    {
      localStatement1.executeUpdate("delete from it_depreciation where startdate = convert(datetime,'" + str3 + "', 101) and enddate = convert(datetime,'" + (String)localObject3 + "', 101) and group_id in(" + str1 + ")");
    }
    catch (Exception localException5)
    {
    }
   // str2 = " SELECT 0 ID,H.ASSETID,     'ITWDV' TYPE_ID,      H.GROUP_ID GROUPID, 0 OPENING_BALANCE,    CASE WHEN xxx.F_OR_S = 'FIR' THEN     ASSETVALUE \t\t\t\t\t    ELSE 0 \t\t\t\t\t\t    END AS ADDITION_FIRST_HALF, \t    CASE WHEN xxx.F_OR_S = 'SEC' THEN   ASSETVALUE                        ELSE 0                             end  as ADDITION_SECOND_HALF,      0 ASSET_ADDITION_FIRST_HALF ,      0 ASSET_ADDITION_SECOND_HALF,      0 DELETION_FIRST_HALF,             0 DELETION_SECOND_HALF,            0 CURRENT_YEAR_ASSET_TOTAL,        0 ASONDATE,                        0 DEPRECIATED_OPENING,             ISNULL(XXX.FIR_DEP_VALUE,0) DEPRECIATED_ADD_FIRST,           ISNULL(XXX.SEC_DEP_VALUE,0) DEPRECIATED_ADD_SECOND,          0 DEPRECIATED_ASSET_ADD_FIRST,  0 DEPRECIATED_ASSET_ADD_SECOND, 0 DEPRECIATED_ASSET_DEL_FIRST,     0 DEPRECIATED_ASSET_DEL_SECOND ,   0 CURRENT_YEAR_DEP_TOTAL,          0 GRAND_TOTAL ,                    0 WDV,                             0 WRITEOFF,\t\t\t\t\t    CONVERT(DATETIME,'" + str3 + "', 101) START_DATE," + " CONVERT(DATETIME,'" + (String)localObject3 + "', 101) END_DATE " + " INTO normal_wdv\t\t\t\t\t   " + " from HARDWARE_REGISTER H, DEP_MASTER D, " + " (SELECT ASSETID, CASE WHEN (MONTH(dep_date) = 4 OR  MONTH(dep_date) = 5 OR MONTH(dep_date) = 6 " + " OR MONTH(dep_date) = 7 OR MONTH(dep_date) = 8 OR MONTH(dep_date) = 9) and H.leasestatus in ('NoLease','NUL') AND H.SOLD <> 'YES' THEN " + " ASSETVALUE * (IWDV / 100) " + " WHEN (MONTH(dep_date) = 4 OR  MONTH(dep_date) = 5 OR MONTH(dep_date) = 6 " + " OR MONTH(dep_date) = 7 OR MONTH(dep_date) = 8 OR MONTH(dep_date) = 9) and H.leasestatus in ('NoLease','NUL') AND H.SOLD = 'YES' AND ((YEAR(H.SOLD_DATE) = YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(H.SOLD_DATE)=4 OR MONTH(H.SOLD_DATE)=5 OR MONTH(H.SOLD_DATE)=6 OR MONTH(H.SOLD_DATE)=7 OR MONTH(H.SOLD_DATE)=8 OR MONTH(H.SOLD_DATE)=9 OR MONTH(H.SOLD_DATE)=10 OR MONTH(H.SOLD_DATE)=11 OR MONTH(H.SOLD_DATE)=12)) or (YEAR(H.SOLD_DATE) = YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.SOLD_DATE)=1 OR MONTH(H.SOLD_DATE)=2 OR MONTH(H.SOLD_DATE)=3))) THEN " + " ASSETVALUE * ((IWDV / 100)/2) " + " WHEN (MONTH(dep_date) = 4 OR  MONTH(dep_date) = 5 OR MONTH(dep_date) = 6 " + " OR MONTH(dep_date) = 7 OR MONTH(dep_date) = 8 OR MONTH(dep_date) = 9) and H.leasestatus in ('NoLease','NUL') AND H.SOLD = 'YES' AND ((YEAR(H.SOLD_DATE) <> YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(H.SOLD_DATE)=4 OR MONTH(H.SOLD_DATE)=5 OR MONTH(H.SOLD_DATE)=6 OR MONTH(H.SOLD_DATE)=7 OR MONTH(H.SOLD_DATE)=8 OR MONTH(H.SOLD_DATE)=9 OR MONTH(H.SOLD_DATE)=10 OR MONTH(H.SOLD_DATE)=11 OR MONTH(H.SOLD_DATE)=12)) or (YEAR(H.SOLD_DATE) <> YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.SOLD_DATE)=1 OR MONTH(H.SOLD_DATE)=2 OR MONTH(H.SOLD_DATE)=3))) THEN " + " ASSETVALUE * (IWDV / 100) " + " WHEN (MONTH(dep_date) = 4 OR  MONTH(dep_date) = 5 OR MONTH(dep_date) = 6 " + " OR MONTH(dep_date) = 7 OR MONTH(dep_date) = 8 OR MONTH(dep_date) = 9) and H.leasestatus in ('lease','UL') THEN " + " CASE WHEN LEASEENDDATE BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ASSETVALUE * (datediff(d,'" + paramString1 + "',LEASEENDDATE))/(DATEDIFF(D,LEASESTARTDATE, LEASEENDDATE)+1) " + " ELSE " + " ASSETVALUE * (datediff(d,'" + paramString1 + "',convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,LEASESTARTDATE, LEASEENDDATE)+1) " + " END " + " END AS FIR_DEP_VALUE, " + " CASE WHEN (MONTH(dep_date) = 3 OR  MONTH(dep_date) = 2 OR MONTH(dep_date) = 1 " + " OR MONTH(dep_date) = 12 OR MONTH(dep_date) = 11 OR MONTH(dep_date) = 10) and H.leasestatus in ('NoLease','NUL') AND H.SOLD <> 'YES' THEN " + " ASSETVALUE * ((IWDV / 100)/2) " + " WHEN (MONTH(dep_date) = 3 OR  MONTH(dep_date) = 2 OR MONTH(dep_date) = 1 " + " OR MONTH(dep_date) = 12 OR MONTH(dep_date) = 11 OR MONTH(dep_date) = 10) and H.leasestatus in ('NoLease','NUL') AND ((YEAR(H.SOLD_DATE) = YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(H.SOLD_DATE)=10 OR MONTH(H.SOLD_DATE)=11 OR MONTH(H.SOLD_DATE)=12)) or (YEAR(H.SOLD_DATE) = YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.SOLD_DATE)=1 OR MONTH(H.SOLD_DATE)=2 OR MONTH(H.SOLD_DATE)=3))) THEN " + " ASSETVALUE * ((IWDV / 100)) " + " WHEN (MONTH(dep_date) = 3 OR  MONTH(dep_date) = 2 OR MONTH(dep_date) = 1 " + " OR MONTH(dep_date) = 12 OR MONTH(dep_date) = 11 OR MONTH(dep_date) = 10) and H.leasestatus in ('NoLease','NUL') AND YEAR(H.SOLD_DATE) <> YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.SOLD_DATE)=10 OR MONTH(H.SOLD_DATE)=11 OR MONTH(H.SOLD_DATE)=12 OR MONTH(H.SOLD_DATE)=1 OR MONTH(H.SOLD_DATE)=2 OR MONTH(H.SOLD_DATE)=3) THEN " + " ASSETVALUE * ((IWDV / 100)) " + " WHEN MONTH(dep_date) = 3 OR  MONTH(dep_date) = 2 OR MONTH(dep_date) = 1 " + " OR MONTH(dep_date) = 12 OR MONTH(dep_date) = 11 OR MONTH(dep_date) = 10 and H.leasestatus in('lease','UL') THEN " + " CASE WHEN LEASEENDDATE BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ASSETVALUE * (datediff(d,'" + paramString1 + "',LEASEENDDATE))/(DATEDIFF(D,LEASESTARTDATE, LEASEENDDATE)+1) " + " ELSE " + " ASSETVALUE * (datediff(d,'" + paramString1 + "',convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,LEASESTARTDATE, LEASEENDDATE)+1) " + " END " + " END AS SEC_DEP_VALUE, " + " CASE WHEN MONTH(dep_date) = 3 OR  MONTH(dep_date) = 2 OR MONTH(dep_date) = 1 " + " OR MONTH(dep_date) = 12 OR MONTH(dep_date) = 11 OR MONTH(dep_date) = 10 THEN " + " 'SEC' " + " ELSE  " + " 'FIR' " + " END AS F_OR_S " + " FROM HARDWARE_REGISTER H, DEP_MASTER D WHERE H.GROUP_ID = D.GROUP_ID and H.group_id in (" + str1 + ") and H.INSTALLED <> 0 ) XXX  " + " WHERE D.GROUP_ID = H.GROUP_ID and H.group_id in(" + str1 + ") and H.INSTALLED <> 0  AND XXX.ASSETID = H.ASSETID AND dep_date <= CONVERT(DATETIME,'" + (String)localObject3 + "',101)" + " AND H.ASSETID NOT IN (SELECT ASSETID FROM it_depreciation) AND H.ASSETID NOT IN (select ASSETID from hardware_register where SOLD = 'YES' and SOLD_DATE < CONVERT(DATETIME,'" + str3 + "',101) and group_id in (" + str1 + "))";
    
    str2 = " SELECT 0 ID,H.id_wh_dyn,     'ITWDV' TYPE_ID,      H.id_grp GROUPID, 0 OPENING_BALANCE,    CASE WHEN xxx.F_OR_S = 'FIR' THEN     val_asst \t\t\t\t\t    ELSE 0 \t\t\t\t\t\t    END AS ADDITION_FIRST_HALF, \t    CASE WHEN xxx.F_OR_S = 'SEC' THEN   val_asst                        ELSE 0                             end  as ADDITION_SECOND_HALF,      0 ASSET_ADDITION_FIRST_HALF ,      0 ASSET_ADDITION_SECOND_HALF,      0 DELETION_FIRST_HALF,             0 DELETION_SECOND_HALF,            0 CURRENT_YEAR_ASSET_TOTAL,        0 ASONDATE,                        0 DEPRECIATED_OPENING,             ISNULL(XXX.FIR_DEP_VALUE,0) DEPRECIATED_ADD_FIRST,           ISNULL(XXX.SEC_DEP_VALUE,0) DEPRECIATED_ADD_SECOND,          0 DEPRECIATED_ASSET_ADD_FIRST,  0 DEPRECIATED_ASSET_ADD_SECOND, 0 DEPRECIATED_ASSET_DEL_FIRST,     0 DEPRECIATED_ASSET_DEL_SECOND ,   0 CURRENT_YEAR_DEP_TOTAL,          0 GRAND_TOTAL ,                    0 WDV,                             0 WRITEOFF,\t\t\t\t\t    CONVERT(DATETIME,'" + str3 + "', 101) START_DATE," + " CONVERT(DATETIME,'" + (String)localObject3 + "', 101) END_DATE " + " INTO normal_wdv\t\t\t\t\t   " + " from A_Ware_House H, D_Dprn_Master D, " + " (SELECT id_wh_dyn, CASE WHEN (MONTH(dt_dprn) = 4 OR  MONTH(dt_dprn) = 5 OR MONTH(dt_dprn) = 6 " + " OR MONTH(dt_dprn) = 7 OR MONTH(dt_dprn) = 8 OR MONTH(dt_dprn) = 9) and H.st_lease in ('NoLease','NUL') AND H.sell <> 'YES' THEN " + " val_asst * (IWDV / 100) " + " WHEN (MONTH(dt_dprn) = 4 OR  MONTH(dt_dprn) = 5 OR MONTH(dt_dprn) = 6 " + " OR MONTH(dt_dprn) = 7 OR MONTH(dt_dprn) = 8 OR MONTH(dt_dprn) = 9) and H.st_lease in ('NoLease','NUL') AND H.sell = 'YES' AND ((YEAR(H.dt_sell) = YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(H.dt_sell)=4 OR MONTH(H.dt_sell)=5 OR MONTH(H.dt_sell)=6 OR MONTH(H.dt_sell)=7 OR MONTH(H.dt_sell)=8 OR MONTH(H.dt_sell)=9 OR MONTH(H.dt_sell)=10 OR MONTH(H.dt_sell)=11 OR MONTH(H.dt_sell)=12)) or (YEAR(H.dt_sell) = YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.dt_sell)=1 OR MONTH(H.dt_sell)=2 OR MONTH(H.dt_sell)=3))) THEN " + " val_asst * ((IWDV / 100)/2) " + " WHEN (MONTH(dt_dprn) = 4 OR  MONTH(dt_dprn) = 5 OR MONTH(dt_dprn) = 6 " + " OR MONTH(dt_dprn) = 7 OR MONTH(dt_dprn) = 8 OR MONTH(dt_dprn) = 9) and H.st_lease in ('NoLease','NUL') AND H.sell = 'YES' AND ((YEAR(H.dt_sell) <> YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(H.dt_sell)=4 OR MONTH(H.dt_sell)=5 OR MONTH(H.dt_sell)=6 OR MONTH(H.dt_sell)=7 OR MONTH(H.dt_sell)=8 OR MONTH(H.dt_sell)=9 OR MONTH(H.dt_sell)=10 OR MONTH(H.dt_sell)=11 OR MONTH(H.dt_sell)=12)) or (YEAR(H.dt_sell) <> YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.dt_sell)=1 OR MONTH(H.dt_sell)=2 OR MONTH(H.dt_sell)=3))) THEN " + " val_asst * (IWDV / 100) " + " WHEN (MONTH(dt_dprn) = 4 OR  MONTH(dt_dprn) = 5 OR MONTH(dt_dprn) = 6 " + " OR MONTH(dt_dprn) = 7 OR MONTH(dt_dprn) = 8 OR MONTH(dt_dprn) = 9) and H.st_lease in ('lease','UL') THEN " + " CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " val_asst * (datediff(d,'" + paramString1 + "',endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " val_asst * (datediff(d,'" + paramString1 + "',convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END " + " END AS FIR_DEP_VALUE, " + " CASE WHEN (MONTH(dt_dprn) = 3 OR  MONTH(dt_dprn) = 2 OR MONTH(dt_dprn) = 1 " + " OR MONTH(dt_dprn) = 12 OR MONTH(dt_dprn) = 11 OR MONTH(dt_dprn) = 10) and H.st_lease in ('NoLease','NUL') AND H.sell <> 'YES' THEN " + " val_asst * ((IWDV / 100)/2) " + " WHEN (MONTH(dt_dprn) = 3 OR  MONTH(dt_dprn) = 2 OR MONTH(dt_dprn) = 1 " + " OR MONTH(dt_dprn) = 12 OR MONTH(dt_dprn) = 11 OR MONTH(dt_dprn) = 10) and H.st_lease in ('NoLease','NUL') AND ((YEAR(H.dt_sell) = YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(H.dt_sell)=10 OR MONTH(H.dt_sell)=11 OR MONTH(H.dt_sell)=12)) or (YEAR(H.dt_sell) = YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.dt_sell)=1 OR MONTH(H.dt_sell)=2 OR MONTH(H.dt_sell)=3))) THEN " + " val_asst * ((IWDV / 100)) " + " WHEN (MONTH(dt_dprn) = 3 OR  MONTH(dt_dprn) = 2 OR MONTH(dt_dprn) = 1 " + " OR MONTH(dt_dprn) = 12 OR MONTH(dt_dprn) = 11 OR MONTH(dt_dprn) = 10) and H.st_lease in ('NoLease','NUL') AND YEAR(H.dt_sell) <> YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(H.dt_sell)=10 OR MONTH(H.dt_sell)=11 OR MONTH(H.dt_sell)=12 OR MONTH(H.dt_sell)=1 OR MONTH(H.dt_sell)=2 OR MONTH(H.dt_sell)=3) THEN " + " val_asst * ((IWDV / 100)) " + " WHEN MONTH(dt_dprn) = 3 OR  MONTH(dt_dprn) = 2 OR MONTH(dt_dprn) = 1 " + " OR MONTH(dt_dprn) = 12 OR MONTH(dt_dprn) = 11 OR MONTH(dt_dprn) = 10 and H.st_lease in('lease','UL') THEN " + " CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " val_asst * (datediff(d,'" + paramString1 + "',endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " val_asst * (datediff(d,'" + paramString1 + "',convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END " + " END AS SEC_DEP_VALUE, " + " CASE WHEN MONTH(dt_dprn) = 3 OR  MONTH(dt_dprn) = 2 OR MONTH(dt_dprn) = 1 " + " OR MONTH(dt_dprn) = 12 OR MONTH(dt_dprn) = 11 OR MONTH(dt_dprn) = 10 THEN " + " 'SEC' " + " ELSE  " + " 'FIR' " + " END AS F_OR_S " + " FROM A_Ware_House H, D_Dprn_Master D WHERE H.id_grp = D.id_grp and H.id_grp in (" + str1 + ") and H.allocate <> 0 ) XXX  " + " WHERE D.id_grp = H.id_grp and H.id_grp in(" + str1 + ") and H.allocate <> 0  AND XXX.id_wh_dyn = H.id_wh_dyn AND dt_dprn <= CONVERT(DATETIME,'" + (String)localObject3 + "',101)" + " AND H.id_wh_dyn NOT IN (SELECT ASSETID FROM it_depreciation) AND H.id_wh_dyn NOT IN (select id_wh_dyn from A_Ware_House where sell = 'YES' and dt_sell < CONVERT(DATETIME,'" + str3 + "',101) and id_grp in (" + str1 + "))";
   
    try
    {
      localStatement1.executeUpdate(str2);
    }
    catch (Exception localException6) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while calculating depreciation for Non-Depreciated Assets:\n" + localException6 + "\n...ends\n");
    }

    str2 = " update normal_wdv set asset_addition_first_half = ISNULL(asset_addition_first_half,0) + ISNULL(xxx.tot,0),   DEPRECIATED_ASSET_ADD_FIRST = ISNULL(DEPRECIATED_ASSET_ADD_FIRST,0) + (ISNULL(XXX.TOT,0.00) * (IWDV /100))   from ( select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate <=       convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H where normal_wdv.id_wh_dyn = xxx.assetid1 AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND  T.id_grp = normal_wdv.GROUPID and st_lease in ('NoLease','NUL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException7) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Addition for Non-Depreciated Assets - 1st Half Not Under Lease:\n" + localException7 + "\n...ends\n");
    }
    str2 = "update normal_wdv set asset_addition_SECOND_half = ISNULL(asset_addition_second_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_ADD_second = ISNULL(DEPRECIATED_ASSET_ADD_second,0) + (ISNULL(XXX.TOT,0) * ((IWDV /100)/2))  from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate       between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H  where normal_wdv.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv.GROUPID AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND st_lease in ('NoLease','NUL') ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException8) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Addition for Non-Depreciated Assets - 2nd Half Not Under Lease:\n" + localException8 + "\n...ends\n");
    }

    str2 = " update normal_wdv set asset_addition_first_half = ISNULL(asset_addition_first_half,0) + ISNULL(xxx.tot,0),   DEPRECIATED_ASSET_ADD_FIRST = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_ADD_FIRST,0.00) + ISNULL(val_asst,0) * (datediff(d,std_lease,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_ADD_FIRST,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate   <=    " + " convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H where normal_wdv.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv.GROUPID AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND st_lease in ('lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException9) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Addition for Non-Depreciated Assets - 1st Half Under Lease:\n" + localException9 + "\n...ends\n");
    }
    str2 = "update normal_wdv set asset_addition_SECOND_half = ISNULL(asset_addition_second_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_ADD_SECOND = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_ADD_SECOND,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_ADD_SECOND,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate      " + " between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H where normal_wdv.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv.GROUPID AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND st_lease in ( 'lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException10) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Addition for Non-Depreciated Assets - 2nd Half Under Lease:\n" + localException10 + "\n...ends\n");
    }

    str2 = " update normal_wdv set deletion_first_half =ISNULL(deletion_first_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_del_FIRST = ISNULL(DEPRECIATED_ASSET_del_FIRST,0) + (ISNULL(XXX.TOT,0) * (IWDV /100))   from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate <=  convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H where normal_wdv.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv.GROUPID AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND st_lease in ('NoLease','NUL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException11) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Deletion for Non-Depreciated Assets - 1st Half Not Under Lease:\n" + localException11 + "\n...ends\n");
    }

    str2 = " update normal_wdv set DELETION_SECOND_half =ISNULL(DELETION_SECOND_HALF,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_DEL_second = ISNULL(DEPRECIATED_ASSET_DEL_second,0) + (ISNULL(XXX.TOT,0) * ((IWDV /100)/2))  from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate   between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H  where normal_wdv.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv.GROUPID AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND st_lease in ('NoLease','NUL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException12) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Deletion for Non-Depreciated Assets - 2nd Half Not Under Lease:\n" + localException12 + "\n...ends\n");
    }

    str2 = " update normal_wdv set deletion_first_half =ISNULL(deletion_first_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_DEL_FIRST = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_DEL_FIRST,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_DEL_FIRST,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate  <=    " + " convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H where normal_wdv.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv.GROUPID AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND st_lease in ('lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException13) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Deletion for Non-Depreciated Assets - 1st Half Under Lease:\n" + localException13 + "\n...ends\n");
    }

    str2 = " update normal_wdv set DELETION_SECOND_half =ISNULL(DELETION_SECOND_HALF,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_DEL_SECOND = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_DEL_SECOND,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_DEL_SECOND,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate      " + " between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv , D_Dprn_Master T, A_Ware_House H where normal_wdv.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv.GROUPID  AND H.id_wh_dyn = normal_wdv.id_wh_dyn AND st_lease in ('lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException14) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking Deletion for Non-Depreciated Assets - 2nd Half Under Lease:\n" + localException14 + "\n...ends\n");
    }

    str2 = " update normal_wdv set CURRENT_YEAR_ASSET_TOTAL = ISNULL(OPENING_BALANCE,0) + ISNULL(ADDITION_FIRST_HALF,0) + ISNULL(ADDITION_SECOND_HALF,0) + ISNULL(ASSET_ADDITION_FIRST_HALF,0) + ISNULL(ASSET_ADDITION_SECOND_HALF,0) - (ISNULL(DELETION_FIRST_HALF,0) + ISNULL(DELETION_SECOND_HALF,0)), CURRENT_YEAR_DEP_TOTAL  = ISNULL(DEPRECIATED_OPENING,0) + ISNULL(DEPRECIATED_ADD_FIRST,0) + ISNULL(DEPRECIATED_ASSET_ADD_FIRST,0) + ISNULL(DEPRECIATED_ADD_SECOND,0) + ISNULL(DEPRECIATED_ASSET_ADD_SECOND,0) - (ISNULL(DEPRECIATED_ASSET_DEL_FIRST,0) + ISNULL(DEPRECIATED_ASSET_DEL_SECOND,0))";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException15) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while updating normal_wdv table:\n" + localException15 + "\n...ends\n");
    }

    str2 = "update normal_wdv set GRAND_TOTAL  = ISNULL(CURRENT_YEAR_DEP_TOTAL,0)";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException16) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while updating normal_wdv table:\n" + localException16 + "\n...ends\n");
    }
    str2 = "update normal_wdv set WDV  = CASE WHEN (ISNULL(CURRENT_YEAR_ASSET_TOTAL,0) - ISNULL(GRAND_TOTAL,0)) < 0 THEN  0  ELSE  ISNULL(CURRENT_YEAR_ASSET_TOTAL,0) - ISNULL(GRAND_TOTAL,0)  END ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException17) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while updating normal_wdv table:\n" + localException17 + "\n...ends\n");
    }

    str2 = "select 0 ID, H.id_wh_dyn,'ITWDV' TYPE_ID,  H.id_grp GROUPID,  xxx.wdv OPENING_BALANCE,  0 ADDITION_FIRST_HALF,    0  ADDITION_SECOND_HALF ,  0 ASSET_ADDITION_FIRST_HALF,  0 ASSET_ADDITION_SECOND_HALF,  0 DELETION_FIRST_HALF,  0 DELETION_SECOND_HALF,  0 current_year_asset_total,  xxx.grand_total asondate,   case when st_lease in ('lease','UL') then  CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(xxx.wdv,0) * (datediff(d,'" + paramString1 + "',endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(xxx.wdv,0) * (datediff(d,'" + paramString1 + "',convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END " + " else " + " CASE WHEN st_lease in ('NoLease','NUL') AND sell <> 'YES' THEN " + "    ISNULL(xxx.wdv,0) * IWDV/100   " + " WHEN st_lease in ('NoLease','NUL') AND sell = 'YES' AND YEAR(H.dt_sell) = YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(dt_sell)=4 OR MONTH(dt_sell)=5 OR MONTH(dt_sell)=6 OR MONTH(dt_sell)=7 OR MONTH(dt_sell)=8 OR MONTH(dt_sell)=9) THEN " + " ISNULL(xxx.wdv,0) * ((IWDV / 100)/2) " + " WHEN st_lease in ('NoLease','NUL') AND sell = 'YES' AND YEAR(H.dt_sell) <> YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(dt_sell)=4 OR MONTH(dt_sell)=5 OR MONTH(dt_sell)=6 OR MONTH(dt_sell)=7 OR MONTH(dt_sell)=8 OR MONTH(dt_sell)=9) THEN " + " ISNULL(xxx.wdv,0) * (IWDV / 100) " + " WHEN st_lease in ('NoLease','NUL') AND sell = 'YES' AND YEAR(H.dt_sell) = YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(dt_sell)=10 OR MONTH(dt_sell)=11 OR MONTH(dt_sell)=12 OR MONTH(dt_sell)=1 OR MONTH(dt_sell)=2 OR MONTH(dt_sell)=3) THEN " + " ISNULL(xxx.wdv,0) * (IWDV / 100) " + " WHEN st_lease in ('NoLease','NUL') AND sell = 'YES' AND YEAR(H.dt_sell) <> YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(dt_sell)=10 OR MONTH(dt_sell)=11 OR MONTH(dt_sell)=12 OR MONTH(dt_sell)=1 OR MONTH(dt_sell)=2 OR MONTH(dt_sell)=3) THEN " + " ISNULL(xxx.wdv,0) * (IWDV / 100) " + " END " + " end  as depreciated_opening, " + " 0 DEPRECIATED_ADD_FIRST, " + " 0 DEPRECIATED_ADD_SECOND, " + " 0 DEPRECIATED_ASSET_ADD_FIRST, " + " 0 DEPRECIATED_ASSET_ADD_SECOND," + " 0 DEPRECIATED_ASSET_DEL_FIRST, " + " 0 DEPRECIATED_ASSET_DEL_SECOND, " + " 0 CURRENT_YEAR_DEP_TOTAL," + " 0 GRAND_TOTAL ," + " 0 WDV,         " + " 0 WRITEOFF,     " + " CONVERT(DATETIME,'" + str3 + "', 101) START_DATE, " + " CONVERT(DATETIME,'" + (String)localObject3 + "', 101) END_DATE " + " INTO normal_wdv_2" + " FROM A_Ware_House h, (select group_id, assetid, wdv, grand_total from it_depreciation where enddate = convert(datetime,'" + str3 + "',101)-1) xxx," + " D_Dprn_Master d where h.id_wh_dyn = xxx.assetid and d.id_grp = xxx.group_id ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException18) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while calculating depreciation for Depreciated Assets:\n" + localException18 + "\n...ends\n");
    }

    str2 = "update normal_wdv_2 set asset_addition_first_half = ISNULL(asset_addition_first_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_ADD_FIRST = ISNULL(DEPRECIATED_ASSET_ADD_FIRST,0) + (ISNULL(XXX.TOT,0) * IWDV /100)   from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate  between convert(datetime,'" + str3 + "', 101)    " + " and convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T, A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND H.id_wh_dyn =normal_wdv_2.id_wh_dyn AND T.id_grp = normal_wdv_2.GROUPID AND st_lease in ('NoLease','NUL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException19) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking additions for Depreciated Assets:\n" + localException19 + "\n...ends\n");
    }
    str2 = "update normal_wdv_2 set asset_addition_SECOND_half = ISNULL(asset_addition_second_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_ADD_second = ISNULL(DEPRECIATED_ASSET_ADD_second,0) + (ISNULL(XXX.TOT,0) * ((IWDV /100)/2))  from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate       between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T , A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND H.id_wh_dyn =normal_wdv_2.id_wh_dyn AND T.id_grp = normal_wdv_2.GROUPID AND st_lease in ('NoLease','NUL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException20) {
      System.out.println("Error 15 " + localException20);
    }

    str2 = "update normal_wdv_2 set asset_addition_first_half = ISNULL(asset_addition_first_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_ADD_FIRST = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_ADD_FIRST,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_ADD_FIRST,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate  between convert(datetime,'" + str3 + "', 101) and    " + " convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T , A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv_2.GROUPID AND H.id_wh_dyn =normal_wdv_2.id_wh_dyn AND st_lease in ('lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException21) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking additions for Depreciated Assets:\n" + localException21 + "\n...ends\n");
    }

    str2 = "update normal_wdv_2 set asset_addition_SECOND_half = ISNULL(asset_addition_second_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_ADD_SECOND = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_ADD_SECOND,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_ADD_SECOND,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate      " + " between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'ADD' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T , A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv_2.GROUPID AND H.id_wh_dyn =normal_wdv_2.id_wh_dyn AND st_lease in ('lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException22) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking additions for Depreciated Assets:\n" + localException22 + "\n...ends\n");
    }

    str2 = " update normal_wdv_2 set deletion_first_half = ISNULL(deletion_first_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_del_FIRST = ISNULL(DEPRECIATED_ASSET_del_FIRST,0) + (ISNULL(XXX.TOT,0) * IWDV /100)   from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate  between convert(datetime,'" + str3 + "', 101) and    " + " convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T , A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv_2.GROUPID AND H.id_wh_dyn = normal_wdv_2.id_wh_dyn AND st_lease in ('NoLease','NUL') ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException23) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking deletions for Depreciated Assets:\n" + localException23 + "\n...ends\n");
    }

    str2 = " update normal_wdv_2 set DELETION_SECOND_half = ISNULL(DELETION_SECOND_HALF,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_DEL_second = DEPRECIATED_ASSET_DEL_second + (XXX.TOT * ((IWDV /100)/2))  from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate       between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T , A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND H.id_wh_dyn =normal_wdv_2.id_wh_dyn AND T.id_grp = normal_wdv_2.GROUPID AND st_lease in ('NoLease','NUL') ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException24) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking deletions for Depreciated Assets:\n" + localException24 + "\n...ends\n");
    }

    str2 = " update normal_wdv_2 set deletion_first_half = ISNULL(deletion_first_half,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_DEL_FIRST = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_DEL_FIRST,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_DEL_FIRST,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate  between convert(datetime,'" + str3 + "', 101) and    " + " convert(datetime,'" + str3 + "', 101) + 182 and upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T , A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND normal_wdv_2.id_wh_dyn = H.id_wh_dyn AND T.id_grp = normal_wdv_2.GROUPID AND st_lease in ('lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException25) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking deletions for Depreciated Assets:\n" + localException25 + "\n...ends\n");
    }

    str2 = " update normal_wdv_2 set DELETION_SECOND_half = ISNULL(DELETION_SECOND_HALF,0) + ISNULL(xxx.tot,0),  DEPRECIATED_ASSET_DEL_second = CASE WHEN endt_lease BETWEEN convert(datetime,'" + paramString1 + "',101)  AND convert(datetime,'" + paramString2 + "',101) THEN " + " ISNULL(DEPRECIATED_ASSET_DEL_second,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,endt_lease))/(DATEDIFF(D,std_lease, endt_lease)+1) " + " ELSE " + " ISNULL(DEPRECIATED_ASSET_DEL_second,0) + ISNULL(val_asst,0) * (datediff(d,dt_dprn,convert(datetime,'" + paramString2 + "',101))+1)/(DATEDIFF(D,std_lease, endt_lease)+1) " + " END" + " from (select assetid1 ,sum(ISNULL(addamt,0)) tot from addition_deletion where adddate      " + " between convert(datetime,'" + str3 + "', 101) + 183 and convert(datetime,'" + (String)localObject3 + "', 101) AND upper(asset_status) = 'DELETE' group by assetid1 ) xxx, " + " normal_wdv_2 , D_Dprn_Master T , A_Ware_House H where normal_wdv_2.id_wh_dyn = xxx.assetid1 AND T.id_grp = normal_wdv_2.GROUPID AND H.id_wh_dyn =normal_wdv_2.id_wh_dyn AND st_lease in ('lease','UL')";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException26) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while checking deletions for Depreciated Assets:\n" + localException26 + "\n...ends\n");
    }

    str2 = " update normal_wdv_2 set CURRENT_YEAR_ASSET_TOTAL = ISNULL(OPENING_BALANCE,0) + ISNULL(ASSET_ADDITION_FIRST_HALF,0) + ISNULL(ASSET_ADDITION_SECOND_HALF,0) + ISNULL(ADDITION_SECOND_HALF,0) + ISNULL(ADDITION_first_HALF,0) - (ISNULL(DELETION_FIRST_HALF,0) + ISNULL(DELETION_SECOND_HALF,0)), CURRENT_YEAR_DEP_TOTAL  = ISNULL(DEPRECIATED_OPENING,0) + ISNULL(DEPRECIATED_ADD_FIRST,0) + ISNULL(DEPRECIATED_ADD_SECOND,0) + ISNULL(DEPRECIATED_ASSET_ADD_SECOND,0) - (ISNULL(DEPRECIATED_ASSET_DEL_FIRST,0) + ISNULL(DEPRECIATED_ASSET_DEL_SECOND,0))  ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException27) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while updating normal_wdv_2:\n" + localException27 + "\n...ends\n");
    }
    str2 = "update normal_wdv_2 set GRAND_TOTAL  = ISNULL(CURRENT_YEAR_DEP_TOTAL,0)";
    try {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException28) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while updating normal_wdv_2:\n" + localException28 + "\n...ends\n");
    }
    str2 = "update normal_wdv_2 set WDV  = CASE WHEN (ISNULL(CURRENT_YEAR_ASSET_TOTAL,0) - ISNULL(GRAND_TOTAL,0)) < 0 THEN  0  ELSE  ISNULL(CURRENT_YEAR_ASSET_TOTAL,0) - ISNULL(GRAND_TOTAL,0)  END ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException29) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while updating normal_wdv_2:\n" + localException29 + "\n...ends\n");
    }

    str2 = "select * into normal_wdv_3 from normal_wdv_2 union all select * from normal_wdv ";
    try
    {
      localStatement1.executeUpdate(str2);
    } catch (Exception localException30) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while updating normal_wdv_2:\n" + localException30 + "\n...ends\n");
    }

    str2 = "INSERT INTO it_depreciation(assetid,TYPE_ID,group_id,OPENING_BALANCE,ADDITION_FIRST_HALF,ADDITION_SECOND_HALF,ASSET_ADDITION_FIRST_HALF,ASSET_ADDITION_SECOND_HALF,DELETION_FIRST_HALF,DELETION_SECOND_HALF,current_year_asset_total,asondate,DEPRECIATED_OPENING,DEPRECIATED_ADD_FIRST,DEPRECIATED_ADD_SECOND,DEPRECIATED_ASSET_ADD_FIRST,DEPRECIATED_ASSET_ADD_SECOND,DEPRECIATED_ASSET_DEL_FIRST,DEPRECIATED_ASSET_DEL_SECOND,CURRENT_YEAR_DEP_TOTAL,GRAND_TOTAL,WDV,WRITEOFF,STARTDATE,ENDDATE) SELECT id_wh_dyn,TYPE_ID,GROUPID,OPENING_BALANCE,ADDITION_FIRST_HALF,ADDITION_SECOND_HALF,ASSET_ADDITION_FIRST_HALF,ASSET_ADDITION_SECOND_HALF,DELETION_FIRST_HALF,DELETION_SECOND_HALF,current_year_asset_total,asondate,DEPRECIATED_OPENING,DEPRECIATED_ADD_FIRST,DEPRECIATED_ADD_SECOND,DEPRECIATED_ASSET_ADD_FIRST,DEPRECIATED_ASSET_ADD_SECOND,DEPRECIATED_ASSET_DEL_FIRST,DEPRECIATED_ASSET_DEL_SECOND,CURRENT_YEAR_DEP_TOTAL,GRAND_TOTAL,WDV,WRITEOFF,START_DATE,END_DATE FROM normal_wdv_3 ";
    try
    {
      localStatement1.executeUpdate(str2);
     /* str2 = "update it_depreciation set div_id=hr.div_id,cost_center_id=hr.id_cc from A_Ware_House hr where hr.id_wh_dyn=it_depreciation.assetid and startdate='" + paramString1 + "' ";
      localStatement1.executeUpdate(str2);
*/
      localResultSet = localStatement1.executeQuery("select * from it_depreciation where wdv <= 0 and startdate = convert(datetime,'" + str4 + "',101) and enddate = convert(datetime,'" + str5 + "',101)");
      try {
        while (localResultSet.next()) {
          localStatement2.executeUpdate("update it_depreciation set opening_balance = " + localResultSet.getString("current_year_asset_total") + ", addition_first_half = 0.00," + " addition_second_half = 0.00, asset_addition_first_half = 0.00,asset_addition_second_half = 0.00, deletion_first_half = 0.00, deletion_second_half = 0.00,current_year_asset_total = " + localResultSet.getString("current_year_asset_total") + ", asondate = " + localResultSet.getString("grand_total") + "," + " depreciated_opening = 0.00, depreciated_add_first = 0.00,depreciated_add_second=0.00,depreciated_asset_add_first=0.00,depreciated_asset_add_second=0.00,depreciated_asset_del_first=0.00,depreciated_asset_del_second = 0.00," + " current_year_dep_total = " + localResultSet.getString("current_year_dep_total") + ",grand_total=" + localResultSet.getString("grand_total") + ", wdv = 0.00" + " where startdate = convert(datetime,'" + paramString1 + "',101) and enddate = convert(datetime,'" + paramString2 + "',101)" + " and group_id = '" + localResultSet.getString("group_id") + "' and id_wh_dyn = '" + localResultSet.getString("id_wh_dyn") + "'");
        }

      }
      catch (Exception localException31)
      {
        System.out.println("query 1");
      }
      try {
        localStatement1.executeUpdate("update it_depreciation set wdv = 0.00 where wdv <= 1");
      } catch (Exception localException32) {
        System.out.println("query 2");
      }
      try {
        localStatement2.executeUpdate("update it_depreciation set current_year_dep_total = current_year_asset_total where wdv <= 0");
        localStatement1.executeUpdate("update it_depreciation set grand_total=current_year_dep_total+asondate where startdate='" + paramString1 + "'");
      } catch (Exception localException33) {
        System.out.println("query 3");
      }
      try {
        localStatement2.executeUpdate("update it_depreciation set deletion_first_half = (isnull(opening_balance,0.00) + isnull(addition_first_half,0.00) + isnull(asset_addition_first_half,0)+isnull(addition_second_half,0.00) + isnull(asset_addition_second_half,0)), current_year_asset_total = 0.00,  depreciated_asset_del_first = (isnull(asondate,0.00) + isnull(depreciated_opening,0.00) + isnull(depreciated_add_second,0.00) + isnull(depreciated_asset_add_second,0.00)+isnull(depreciated_add_first,0.00) + isnull(depreciated_asset_add_first,0.00)),current_year_dep_total = 0.00, grand_total = 0, wdv=0.00 where assetid in  (select id_wh_dyn from A_Ware_House where sell = 'YES' AND (YEAR(dt_sell) = YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(dt_sell)=4 OR MONTH(dt_sell)=5 OR MONTH(dt_sell)=6 OR MONTH(dt_sell)=7 OR MONTH(dt_sell)=8 OR MONTH(dt_sell)=9))) and startdate = '" + paramString1 + "' and enddate = '" + paramString2 + "' and group_id in(" + str1 + ")");
      }
      catch (Exception localException34)
      {
        System.out.println("query 4");
      }
      try
      {
    	  str2 = "update it_depreciation set deletion_second_half = (isnull(opening_balance,0.00) + isnull(addition_first_half,0.00) + isnull(asset_addition_first_half,0.00) + isnull(addition_second_half,0.00) + isnull(asset_addition_second_half,0)), current_year_asset_total = 0.00,  depreciated_asset_del_second = (isnull(asondate,0.00) + isnull(depreciated_opening,0.00) + isnull(depreciated_add_second,0.00) + isnull(depreciated_asset_add_second,0.00)+isnull(depreciated_add_first,0.00) + isnull(depreciated_asset_add_first,0.00)),current_year_dep_total = 0.00, grand_total = 0, wdv=0.00 where assetid in  (select id_wh_dyn from A_Ware_House where sell = 'YES' AND (YEAR(dt_sell) = YEAR(convert(datetime,'" + paramString2 + "',101)) AND (MONTH(dt_sell)=1 OR MONTH(dt_sell)=2 OR MONTH(dt_sell)=3)) or (YEAR(dt_sell) = YEAR(convert(datetime,'" + paramString1 + "',101)) AND (MONTH(dt_sell)=10 OR MONTH(dt_sell)=11 OR MONTH(dt_sell)=12))) and startdate = '" + paramString1 + "' and enddate = '" + paramString2 + "' and group_id in(" + str1 + ")";
        localStatement2.executeUpdate(str2);
      }
      catch (Exception localException35)
      {
        System.out.println("query 5");
      }
      System.out.println("This is from Called IT Dep: " + paramString1 + " - " + paramString2);
    } catch (Exception localException36) {
      System.out.println("\nThis message is from itDepreciationCalculation.java file ... \n Sorry, Eception Occured while inserting data into it_depreciation :\n" + localException36 + "\n...ends\n");
    }
  }
}
