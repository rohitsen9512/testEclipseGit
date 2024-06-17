<!--assetProducts.jsp-->
<div style="padding:10px;">
	<!-- <a href="#">Master ></a><a href="#"> Asset Product </a> -->
</div>

<script type="text/javascript">

$(function() {
	
	
		//$('input[name="id_loc"]').hide();
	
	$('button[name="update"]').addClass('hideButton');
	
	 DisplayDropDownDataForGroup("M_Asset_Div","groupDataForItem","CapG",function (status){
	if(status)
		{
		DisplayData('M_Prod_Cart','displayItem','createItem');
		}});
	 
	
});

/* $('#nm_ProdId').keyup( function(){
	CheckValWhichAllReadyExit('M_Prod_Cart' , 'Product name all ready exit.' , 'nm_prod');
	});
	
$('#cd_ProdId').keyup( function(){
	CheckValWhichAllReadyExit('M_Prod_Cart' , 'Product code all ready exit.' , 'cd_prod');
	}); */
</script>

<div id="displayItem">
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayItem','createItem','submitProd','M_Prod_Cart')">Create Item</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover itemForDisplay">
						<!-- <tr>
							<td><strong>Sub Group</strong></td>
							<td><strong>Item Name</strong></td>
							<td><strong>Manufacture</strong></td>
							<td><strong>Model Name</strong></td>
							<td><strong>Modify / Delete</strong></td>
						</tr> -->
					</table>
				</td>
			</tr>
		</table>
</div>


<div id="createItem" style="display:none;">
	<form action="" name="submitProd" id="submitProd">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Item Details</p></td>
			</tr>
			<!-- <tr>
			 <td ><b>Capital/Consumable<font color="red">*</font></b></td>
				<td >
					<select name="asset_consumable"   style="width:140"  class="common-validation resetAcc" data-valid="mand" onChange="DisplayDropDownDataForGroup('M_Asset_Div','groupDataForItem',this)">
						<option value="">Select</option>
						<option value="CapG">Capital Goods</option>
						<option value="ConG">Consumable Goods</option>
						
				</select>
				</td>
			</tr> -->
			<input type="hidden" name="asset_consumable" value="CapG">
			<tr>
				<td><strong>Group<font color="red">*</font></strong></td>
				<td>
					<!-- <select id="groupDataForItem" class="common-validation" name="id_grp" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subGroupDataForItem','M_Subasset_Div')">
					 -->
					 <select id="groupDataForItem" class="common-validation" name="id_grp" data-valid="mand" >
					 	<option value="">Select</option>
						
					</select>
					
				</td>
				
			</tr>
			<!-- <tr>
				<td><strong> Sub Group<font color="red"> * </font>:</strong></td>
				<td>
					<select id="subGroupDataForItem" class="common-validation" name="id_sgrp" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr> -->
			<tr>
				<td><strong>Item Name<font color="red">*</font></strong></td>
				<td><input id="nm_ProdId" class="common-validation" type="text" name="nm_prod" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Item Code<font color="red">*</font></strong></td>
				<td><input id="cd_ProdId" class="common-validation" type="text" name="cd_prod" data-valid="mand" ></td>
				
			</tr>
			<tr>
				<td><strong>Manufacture<font color="red">*</font></strong></td>
				<td><input class="common-validation" type="text" name="mfr" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Model Name<font color="red">*</font></strong></td>
				<td><input class="common-validation" type="text" name="model" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Description<font color="red">*</font></strong></td>
				<td><input class="common-validation" type="text" name="description" data-valid="mand"></td>
			</tr>
			
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="type_grp" value="Asset">
				
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayItem','createItem','submitProd','M_Prod_Cart','Item Name all ready exit.,,Floor code already exist.' , 'nm_item,,cd_item')">Save</button>
					<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayItem','createItem','submitProd','M_Prod_Cart','Item Name all ready exit.' , 'nm_item')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayItem','createItem','submitProd','M_Prod_Cart')">Cancel</button>
				</td>
			</tr>
		</table>
	</form>
</div>