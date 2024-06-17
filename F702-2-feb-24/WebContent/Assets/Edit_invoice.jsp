<!--Edit_invoice.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Assets ></a><a href="#">Edit invoice ></a> -->
</div>

<div class="commonDiv" id="displayEditInvoice">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:206px">Invoice Dates Between</p></td>
			</tr>
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input class="common-validation" type="date" name="startdate" data-valid="mand"></td>
				<td><strong>End Date</strong></td>
				<td><input class="common-validation" type="date" name="enddate" data-valid="mand"></td>
			</tr>
			<tr>
				<td colspan="2"><b>Select PO Type<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selectpotype class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
		</table>
			
			<br>
			
					<p style="width:180px;margin-left: 470px;float:center;">
						<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayEditInvoice','createEditInvoice','')">Next</button>
					</p>
				

</div>


<div class="commonDiv" id="createEditInvoice" style="display:none;">
	<form action="" name="submitEditInvoice">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:260px">Edit Invoice</p></td>
			</tr>
			<tr>
				<td><strong>Invoice Date</strong></td>
				<td><input type="date" name="invoicedate"></td>
				<td><strong>Invoice Number</strong></td>
				<td><input type="text" name="invoicenumber"></td>
			</tr>
			<tr>
				<td ><b>Vendor<font color="red">*</font></b></td>
				<td >
					<select name="vendor" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td ><b>currency<font color="red">*</font></b></td>
				<td >
					<select name="currency" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>Location<font color="red">*</font></b></td>
				<td >
					<select name="location" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td ><b>SubLocation<font color="red">*</font></b></td>
				<td >
					<select name="sublocation" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>Bonded<font color="red">*</font></b></td>
				<td >
					<select name="bonded" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td>
				</td>
				<td>
				</td>
			</tr>
	</table>
	
	<br>
	<br>
	
	<table class="commonTable" align="center" width="600px" border="1">
		
			<tr>
				<td colspan="17"  class="tableHeader"><p class="tableHeaderContent" style="margin-left: 449px;">Add Asset/Accessory</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>Asset/Accessory</strong></td>
				<td ><strong>Group</strong></td>
				<td><strong>Subgroup</strong></td>
				<td><strong>Product Name</strong></td>
				<td><strong>Product desc</strong></td>
				<td><strong>Manufacturer</strong></td>
				<td><strong>Model</strong></td>
				<td><strong>Goods Arrival Date</strong></td>
				<td><strong>Quantity</strong></td>
				<td><strong>Unit Price</strong></td>
				<td><strong>VAT</strong></td>
				<td><strong>Service Tax</strong></td>
				<td><strong>Duty</strong></td>
				<td><strong>Others</strong></td>
				<td><strong>Total Price</strong></td>
				<td><strong>Total Unit Price</strong></td>
				<td><strong>Add</strong></td>
			</tr>
			<tr>
				<td >
					<select name="assetaccessory" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td >
					<select name="group" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td >
					<select name="subgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td><input type="text" name="productname"></td>
				<td><input type="text" name="productdesc"></td>
				<td><input type="text" name="manufacturer"></td>
				<td><input type="text" name="model"></td>
				<td><input type="date" name="goodsarrivaldate"></td>
				<td><input type="text" name="quantity"></td>
				<td><input type="text" name="unitprice"></td>
				<td><input type="text" name="VAT"></td>
				<td><input type="text" name="servicetax"></td>
				<td><input type="text" name="duty"></td>
				<td><input type="text" name="others"></td>
				<td><input type="text" name="totalprice"></td>
				<td><input type="text" name="totalunitprice"></td>
				<td>
					<p style="width:180px;margin-left: 200px;float:center;">
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayRequest','request pr','')">Add</button>
					</p>
				</td>	
			</tr>
	</table>
	
	<br>
	<br>
	
	<table class="commonTable" align="center" width="600px" border="1">
		
			<tr>
				<td class="tableHeader" colspan="17" ><p class="tableHeaderContent" style="margin-left: 449px;">Asset/Accessory</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>Asset/Accessory</strong></td>
				<td ><strong>Group</strong></td>
				<td><strong>Subgroup</strong></td>
				<td><strong>Manufacturer</strong></td>
				<td><strong>Model</strong></td>
				<td><strong>Goods Arrival Date</strong></td>
				<td><strong>Quantity</strong></td>
				<td><strong>Unit Price</strong></td>
				<td><strong>Total Price</strong></td>
				<td><strong>Check</strong></td>
			</tr>
			<tr>
				<td><input type="text" name="assetassceory"></td>
				<td><input type="text" name="group"></td>
				<td><input type="text" name="subgroup"></td>
				<td><input type="text" name="manufacturer"></td>
				<td><input type="text" name="model"></td>
				<td><input type="date" name="goodsarrivaldate"></td>
				<td><input type="text" name="quantity"></td>
				<td><input type="text" name="unitprice"></td>
				<td><input type="text" name="totalprice"></td>
				<td><input type="checkbox" name="check"></td>
			</tr>
	</table>
	<br>
	<br>
	
	<table class="commonTable" border="0">	
			<tr>
				<td>	
					<p style="width:180px;margin-left: 200px;float:center;">
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayRequest','request pr','')">Delete</button>
					</p>
					
				</td>
				<td>
					<p style="width:180px;margin-left: 200px;float:center;">
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayRequest','request pr','')">Update</button>
					</p>
				</td>
			</tr>
		</table>
	</form>			
</div>
