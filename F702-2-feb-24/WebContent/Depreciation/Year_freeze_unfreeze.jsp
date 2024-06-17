<Year_freeze_unfreeze.jsp>
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Depreciation ></a><a href="#">Freeze/Unfreeze ></a><a href="#">Year-Freeze/Unfreeze </a>-->
</div>

<div class="commonDiv" id="displayYearFreezeUnfreeze">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:230px">Freezing/Unfreezing of Years</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>Start Date</strong></td>
				<td ><strong>End Date</strong></td>
				<td><strong>Status</strong></td>
				<td><strong>Check</strong></td>
			</tr>
			<tr>
				<td><input type="text" name="startdate"></td>
				<td><input type="text" name="enddate"></td>
				<td><input type="text" name="status"></td>
				<td><input class="common-validation" type="checkbox" name="check" data-valid="mand"></td>
			</tr>
	</table>
	
	<br>
	
			<p style="width:180px;margin-left: 412px;float:center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayYearFreezeUnfreeze','','')">Freeze/Unfreeze</button>
			</p>
</div>