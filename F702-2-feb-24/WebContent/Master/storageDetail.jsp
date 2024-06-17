<!--department.jsp-->
<div style="padding:10px;">
	<!-- <a href="#">Organization Master > </a><a href="#"> Storage Detail</a> -->
</div>

<script type="text/javascript">

$(function() {
	DisplayData('M_StorageDetail','displayStorage','createStorage');
	$('button[name="update"]').addClass('hideButton');
	//DisplayDropDownData("M_Division","DivDataForStorage");
});
</script>

<div id="displayStorage">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayStorage','createStorage','submitStorage','M_StorageDetail')">Create Storage</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover deptForDisplay">
						<tr class="new">
						    <td><strong>S No.</strong></td>
							<td><strong>Department Name</strong></td>
							<td><strong>Department Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createStorage" style="display:none;">
	<form action="" name="submitStorage" id="submitStorage">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 200px;">Storage Details</p></td>
			</tr>
			<tr>
				<td><strong>Division Name <font color="red">*</font></strong></td>
				<td>
					<select id="DivDataForStorage" name="id_div" value="" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						<option value="1">EASY BUY</option>
						<option value="2">LIFESTYLE</option>
						<option value="3">LOIPL</option>
						<option value="4">MAX RETAIL</option>
						<option value="5">SPLASH</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Storage Name <font color="red">*</font></strong></td>
				<td><input id="nm_departmentId" class="common-validation" type="text" name="nm_strg" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Storage Code <font color="red">*</font></strong></td>
				<td><input id="cd_departmentId" class="common-validation" type="text" name="cd_strg" data-valid="mand"></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:220px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayStorage','createStorage','submitStorage','M_StorageDetail','Storage name already exist.,,Storage code already exist.' , 'nm_strg,,cd_strg')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayStorage','createStorage','submitStorage','M_StorageDetail','Storage name already exist.' , 'nm_strg')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayStorage','createStorage','submitStorage','M_StorageDetail')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
</div>