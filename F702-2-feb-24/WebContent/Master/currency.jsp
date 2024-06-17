<div style="padding:10px;">
	<!--  <a href="#">Master ></a><a href="#">Budget Master > </a><a href="#">Currency</a>-->
</div>
<script type="text/javascript">

$(function() {
	DisplayData('M_Currency','displayCurrency','createCurrency');
	$('button[name="update"]').addClass('hideButton');
	
});

/* $('#nm_currencyId').keyup( function(){
	CheckValWhichAllReadyExit('M_Currency' , 'Currency name already exist.' , 'nm_curr');
	});
	
$('#cd_currencyId').keyup( function(){
	CheckValWhichAllReadyExit('M_Currency' , 'Currency code already exist.' , 'cd_curr');
	}); */

</script>
<div id="displayCurrency">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayCurrency','createCurrency','submitCurrency','M_Currency')">Create Currency</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover currencyForDisplay">
						<tr class="new">
							<td><strong>Currency Name</strong></td>
							<td><strong>Currency Symbole</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createCurrency" style="display:none;">
	<form action="" name="submitCurrency" id="submitCurrency">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Currency Details</p></td>
			</tr>
			<tr>
				<td><strong>Currency Name <font color="red">*</font></strong></td>
				<td><input type="text" id="nm_currencyId" name="nm_curr" value="" class="common-validation" data-valid="mand"></td>
				
				
			</tr>
			<tr>
				<td><strong>Currency Symbol <font color="red">*</font></strong></td>
				<td><input type="text" id="cd_currencyId" name="cd_curr" value="" class="common-validation" data-valid="mand"></td>
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayCurrency','createCurrency','submitCurrency','M_Currency','','')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayCurrency','createCurrency','submitCurrency','M_Currency','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayCurrency','createCurrency','submitCurrency','M_Currency')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>