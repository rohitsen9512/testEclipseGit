<Depreciation_master1.jsp>
<div class="commonDiv" style="padding:10px;color:blue;">
	<!--  Depreciation >Depreciation Master -->
</div>
<script type="text/javascript">

$(function() {
	
	DisplayDepreciation();
});
</script>
<div class="card">
<div class="commonDiv" id="displayDepreciationMaster">
	<form name="submitDepreciationMaster" id="submitDepreciationMaster">
	<div class="card-header new">
				<h3 class="card-title1">Depreciation Master</h3>
			</div>
		<!-- <table class="commonTable displayDepreciationMasterTable table table-bordered" align="center" width="100%" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:537px">Depreciation Master</p></td>
			</tr> -->
			<table class="commonTable displayDepreciationMasterTable table table-bordered " style="width: 100%;">
			<tr class="new">
				<td><strong>S No.</strong></td>
				<td ><strong>Asset Groups</strong></td>
				<td><strong>InCome Tax Act(WDV)</strong></td>
				<td><strong>Company Act(SLM)</strong></td>
				<!--  
				<td><strong>US GAAP(in Years)</strong></td>
				-->
			</tr>
			<tr>
				<td><input type="text" name="sino"></td>
				<td><input type="text" name="assetgroups"></td>
				<td><input type="text" name="inCometaxact"></td>
				<td><input type="text" name="companyact"></td>
				<!-- 
				<td><input type="text" name="usgaap"></td>
				 -->
			</tr>
	</table>
	<input type="hidden" name="action" value="Save">
			<p style="width:180px;margin-left: 500px;float:center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDivForDepreceation('submitDepreciationMaster')">Save</button>
			</p>
	</form>
</div>
</div>