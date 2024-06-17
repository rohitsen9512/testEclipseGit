<!--  Stores_software.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Stores Software</a>-->
</div>

<div class="commonDiv" id="displayStoresSoftware">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Stores Report - Assets</p></td>
			</tr>
			<tr>
				<td><b>Select the Software Group<font color="red">*</font></b></td>
				<td>
					<select name="selecttheassetgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Select the Software Sub Group<font color="red">*</font></b></td>
				<td>
					<select name="selecttheassetsubgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayStoresSoftware','createStoresSoftware','')">search</button>
			</p>
</div>

<div class="commonDiv" id="createStoresSoftware" style="display:none;">
	<form action="" name="submitStoresSoftware">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Stores Inventory for Building Group</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>GRN</strong></td>
				<td ><strong>ASSETNAME</strong></td>
				<td ><strong>Software Name</strong></td>
				<td><strong>Software Serial No.</strong></td>
				<td><strong>No. of License</strong></td>
				<td><strong>Software Key</strong></td>
				<td><strong>Software Description</strong></td>
				<td><strong>Unit Cost(RS.)</strong></td>
			</tr>
			<tr>
				<td  colspan="12" ><font color="red"><center>Record(s) not available</center></font></td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 125px;margin-left: 443px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayStoresAsset','createStoresAsset','')">Export To Excel</button>
			</p>
	</form>
</div>