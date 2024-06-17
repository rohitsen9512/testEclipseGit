<!--consumableProduct.jsp-->
<div style="padding:10px;">
	<!--  <a href="#">Master ></a><a href="#"> Consumable Product </a>-->
</div>

<script type="text/javascript">

$(function() {
	DisplayData('M_Prod_Consumable','displayCItem','createCItem');
	$('button[name="update"]').addClass('hideButton');
	
	DisplayDropDownDataForGroup("M_Asset_Div","groupDataForCItem",'ConG',function (status){
		if(status)
			{
			
			}});
		
		
	});
/* $('#nm_CProdId').keyup( function(){
	CheckValWhichAllReadyExit('M_Prod_Consumable' , 'Product name all ready exit.' , 'nm_prod');
	});
	
$('#cd_CProdId').keyup( function(){
	CheckValWhichAllReadyExit('M_Prod_Consumable' , 'Product code all ready exit.' , 'cd_prod');
	}); */
</script>

<div id="displayCItem">
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayCItem','createCItem','submitCItem','M_Prod_Consumable')">Create Item</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover citemForDisplay">
						<tr class="new">
							<td><strong>Sub Group</strong></td>
							<td><strong>Item Name</strong></td>
							<td><strong>Manufacture</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>


<div id="createCItem" style="display:none;">
	<form action="" name="submitCItem" id="submitCItem">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Item Details</p></td>
			</tr>
			<tr>
				<td><strong>Group<font color="red">*</font></strong></td>
				<td>
					<select id="groupDataForCItem" class="common-validation" name="id_grp" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subGroupDataForCItem','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
					
				</td>
				
			</tr>
			<tr>
				<td><strong> Sub Group<font color="red">*</font></strong></td>
				<td>
					<select id="subGroupDataForCItem" class="common-validation" name="id_sgrp" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Product Name<font color="red">*</font></strong></td>
				<td><input id="nm_CProdId" class="common-validation" type="text" name="nm_prod" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Product Code<font color="red">*</font></strong></td>
				<td><input id="cd_CProdId" class="common-validation" type="text" name="cd_prod" data-valid="mand"></td>
				
			</tr>
			<tr>
				<td><strong>Manufacture<font color="red">*</font></strong></td>
				<td><input class="common-validation" type="text" name="mfr" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Description<font color="red">*</font></strong></td>
				<td><input class="common-validation" type="text" name="description" data-valid="mand"></td>
			</tr>
			
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="type_grp" value="Consumable">
				
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayCItem','createCItem','submitCItem','M_Prod_Consumable','Item Name all ready exit.,,Item code already exist.' , 'nm_item,,cd_item')">Save</button>
					<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayCItem','createCItem','submitCItem','M_Prod_Consumable','' , '')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayCItem','createCItem','submitCItem','M_Prod_Consumable')">Back</button>
				</td>
			</tr>
		</table>
	</form>
</div>