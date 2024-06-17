
<script type="text/javascript">

$(function() {
	
	DisplayData('M_Country','displayCountry','createCountry');
	$('button[name="update"]').addClass('hideButton');
});
var time = Date.now();
$('input[name="time"]').val(time);

/* $('#nm_countryId').keyup( function(){
	CheckValWhichAllReadyExit('M_Country' , 'Country name already exist.' , 'nm_country');
	});
	
$('#cd_countryId').keyup( function(){
	CheckValWhichAllReadyExit('M_Country' , 'Country code already exist.' , 'cd_country');
	}); */

</script>


<div id="displayCountry">
	
		<table width="100%" height="100%">
			<tr>
				<td>
				<div  id="displayCountry">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayData('M_Country','displayCountry','createCountry')">
</div>
					<div class="message">Your update was successful</div>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayCountry','createCountry','submitCountry','M_Country')">Create Country</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover dataForDisplay">
						<tr class="success new">
							<td><strong>Country Code</strong></td>
							<td><strong>Country Name</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>

<div id="createCountry" style="display:none;">

	<form action="" name="submitCountry" id="submitCountry">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Country Details</p></td>
			</tr>
			<tr>
				<td><strong>Country Name</strong></td>
				<td><input id="nm_countryId" class="common-validation" type="text" name="nm_country" data-valid="mand" value=""></td>
				
				
			</tr>
			<tr>
				<td><strong>Country Code</strong></td>
				<td><input id="cd_countryId" class="common-validation" data-valid="mand" type="text" name="cd_country"  value=""></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="time" value="0">
			</tr>
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayCountry','createCountry','submitCountry','M_Country','Country Name all ready exit.,,Country code already exist.' , 'nm_country,,cd_country')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayCountry','createCountry','submitCountry','M_Country','Country name all ready exit.' , 'nm_country')">Update</button>
						<button type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayCountry','createCountry','submitCountry','M_Country')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>