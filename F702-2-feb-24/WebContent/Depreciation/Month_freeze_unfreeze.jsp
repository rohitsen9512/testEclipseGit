<Month_freeze_unfreeze.jsp>
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Depreciation ></a><a href="#">Freeze/Unfreeze ></a><a href="#">Month-Freeze/Unfreeze </a> -->
</div>

<div class="commonDiv" id="displayMonthFreezeUnfreeze">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:206px">Custom Depreciation</p></td>
			</tr>
			<tr>
				<td  style="text-align: right;"><b>Select Year<font color="red">*</font></b></td>
				<td  style="text-align: left;">
					<select name="selectyear" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td>
					<p style="width: 188px;margin-left: 37px;float: center;">
						<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayMonthFreezeUnfreeze','createMonthFreezeUnfreeze','')">Go</button>
					</p>
				</td>
			</tr>
	  </table>
</div>

<div class="commonDiv" id="createMonthFreezeUnfreeze" style="display:none;">
	<form action="" name="submitMonthFreezeUnfreeze">	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr  style="background-color: blue;">
				<td><strong>Month</strong></td>
				<td ><strong>Status</strong></td>
				<td><strong>Check</strong></td>
			</tr>
			<tr>
				<td><input type="text" name="month"></td>
				<td><input type="text" name="status"></td>
				<td><input class="common-validation" type="checkbox" name="check" data-valid="mand"></td>
			</tr>
	</table>
	
	<br>
	
			<p style="width:180px;margin-left: 412px;float:center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayYearFreezeUnfreeze','','')">Freeze/Unfreeze</button>
			</p>
	</form>
</div>