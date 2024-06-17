<!--Model.jsp-->
<div style="padding:10px;">
	<!--  <a href="#"> Master > </a><a href="#">View Item</a>-->
</div>

<script type="text/javascript">

$(function() {
	
	
		//$('input[name="id_loc"]').hide();
	DisplayModelView('M_Model','displayModel','createModel');
	$('button[name="update"]').addClass('hideButton');
	
	/* DisplayDropDownDataForGroup('M_Asset_Div','groupDataForModel','CapG',function (status){
		if(status)
		{
		 DisplaySubDropDownData('0','subLocDataForModel','M_Subloc',function (status){
			if(status)
				{
				
				}}); 
		}}); */
});

/* $('#nm_ModelId').keyup( function(){
	CheckValWhichAllReadyExit('M_Model' , 'Model name all ready exit.' , 'nm_model');
	});
	
$('#cd_ModelId').keyup( function(){
	CheckValWhichAllReadyExit('M_Model' , 'Model code all ready exit.' , 'cd_model');
	}); */
</script>



<div id="displayModel">
		<table width="100%" height="100%">
			<tr>
				<td>
				<div  id="displayModel">
	<input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayData('M_Model','displayModel','createModel')">
</div>
					<!-- <p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayModel','createModel','submitModel','M_Model')">Create Item</button>
					</p> -->
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover ModelForDisplay">
						<tr class="new">
							<td><strong>Group</strong></td>
							<!-- <td><strong>Sub Group</strong></td> -->
							<td><strong>Item Name</strong></td>
							<td><strong>Item Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>


<div id="createModel" style="display:none;">
	<form action="" name="submitModel" id="submitModel">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 230px;">Item Details</p></td>
			</tr>
			<tr>
				<td><strong>Group <font color="red">*</font></strong></td>
				<td>
 					<select id="groupDataForModel" class="common-validation" name="id_assetdiv" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
					<!-- <select id="groupDataForModel" class="common-validation" name="id_assetdiv" data-valid="mand" ">
  -->
 				<option value="">Select</option>
						
					</select>
					<!--  
					<input type="text" name="id_loc" value="" readonly="readonly">
					-->
				</td>
				
			</tr>
			<tr>
				<td><strong> Sub Group <font color="red">*</font></strong></td>
				<td>
					<select id="subgroupDataForModel" class="common-validation" name="id_s_assetdiv" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr> 
			<tr>
				<td><strong>Item Name <font color="red">*</font></strong></td>
				<td><input id="nm_ModelId" class="common-validation" type="text" name="nm_model" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Item Code <font color="red">*</font></strong></td>
				<td><input id="cd_ModelId" class="common-validation" type="text" name="cd_model" data-valid="mand"></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:230px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayModel','createModel','submitModel','M_Model','Model name already exist.,,Model Code already exist.' , 'nm_model,,cd_model')">Save</button>
					<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayModel','createModel','submitModel','M_Model','Model name already exist.' , 'nm_model')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayModel','createModel','submitModel','M_Model')">Back</button>
				</td>
			</tr>
		</table>
	</form>
</div>