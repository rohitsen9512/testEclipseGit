<!--Addition_deletion.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#" >Assets > </a><a href="#">Insurance Update </a>-->
</div>
<script type ="text/javascript" src="js/depreciationCommon.js"></script>

<script type="text/javascript">

$(function(){
	
	DisplayDropDownDataForGroup('M_Asset_Div','grpDataForInsuranceUpdate','CapG',function (status){
		if(status)
		{
			DropdownResultForFinanceYear('finDataForInsuranceUpdate');
		}});
	
	
	
});

</script>

<div class="commonDiv" id="displayInsuranceUpdates">
<form action="" name="submitInsuranceUpdateNext" id="submitInsuranceUpdateNext">	
		<table class="table table-bordered  " align="center" style="width:650px;" height="100%" border="1">
			<tr class="tableHeader ">
				<td colspan="4" class="tableHeader"><center><p class="tableHeaderContent">Select Financial Yr & Group</p></center></td>
			</tr>
			<tr>
				<td  style="text-align: left;"><b>Select Financial Year<font color="red">*</font> </b></td>
				<td  style="text-align: left;">
					<select name="id_finance" class="common-validation" data-valid="mand" id="finDataForInsuranceUpdate">
						
					</select>
				</td>
			</tr>
			<tr>
				<td  style="text-align: left;"><b>Group<font color="red">*</font> </b></td>
				<td  style="text-align: left;">
					<select name="id_grp" class="common-validation" data-valid="mand" id="grpDataForInsuranceUpdate">
						
					</select>
				</td>
			</tr>
			<input type="hidden" name="action" value="getGroupData">
			<tr>
			<td colspan="4">
			<center><button type="button" class="btn btn-primary" onclick="ControlInsuranceUpdate('Next','createInsuranceUpdates','displayInsuranceUpdates','')">Go</button></center>
			</td>
			</tr>
	  </table>
</form>	
</div>

<div class="commonDiv" id="createInsuranceUpdates" style="display:none;">
	<form action="" name="submitInsuranceUpdates" id="submitInsuranceUpdates">
	<input type="hidden" name="id_finance" value="">
	<input type="hidden" name="id_grp" value="">
	<input type="hidden" name="action" value="Save">
		<table class="table table-bordered" id="grpdataForInsurance">
			<tr class="tableHeader ">
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:360px">Insurance Details</p></td>
			</tr>
			<tr class="tableHeader">
				<td><strong>S No.</strong></td>
				<td ><strong>Asset SubGroups</strong></td>
				<td><strong>Asset Max Price</strong></td>
				<td><strong>Insurance Value</strong></td>
			</tr>
			
			
	</table>
	</form>
</div>
