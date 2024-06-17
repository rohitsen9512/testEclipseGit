<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Tagging ></a><a href="#">Asset Verification</a>-->
</div>

<script type="text/javascript">

		
$(function() {
		
		$('#datepicker2').datepicker({
			
		     
		     changeYear: true,
		
		     dateFormat: ' yy',
		
		     onClose: function() {
		     var iYear = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		     $(this).datepicker('setDate', new Date(iYear, 1));
		
		     },
		
		     beforeShow: function() {
		     if ((selDate = $(this).val()).length > 0)
		    {
		         iYear = selDate.substring(selDate.length - 4, selDate.length);
		        $(this).datepicker('option', 'defaultDate', new Date(iYear, 1));
		        $(this).datepicker('setDate', new Date(iYear, 1));
		    }
		  }
		     
		 });

	$("#datepicker2").focus(function () {
		$(".ui-datepicker-calendar").hide();
		$("#ui-datepicker-div").position({
		my: "center top",
		at: "center bottom",
		of: $(this)
		});
		});
		
	var currentDate = new Date();
	$( ".avdatepicker" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true,
	      altFormat: "DD-MMM-YYYY",
	      defaultDate: new Date()
	    });
	$(".datepicker").datepicker("setDate", currentDate);
	
	$('#AssetVerificationDetails').addClass('hideButton');
	DisplayAssetForAssetVerification();
	DisplayDropDownData("M_Loc","locationForAVS");
	
	DisplayDropDownData('M_Asset_Div','groupForAssetVerification');
	DisplayDropDownData("M_Loc","locationForAssetVerification");
	DisplayDropDownData("M_Subloc","subLocationForAssetVerification");
	DisplayDropDownData("M_Floor","floorForAssetVerification");
	
	
});
</script>

<div class="commonDiv" id="DisplayAssetForAssetVerificationSearch">
	<form action="" name="AssetVerification" id="AssetVerification">
		<table class="table table-bordered commonTable" align="center" width="600px" height="100%">
			<tr>
				<td>
				<strong>&nbsp;&nbsp;Location</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="locationForAVS" name="id_locAV" class="select"  style="width:140" onChange="SubDropDownDataDisplay(this,'sublocationForAVS','M_Subloc')">
						<option value="">Select</option>
					</select>
					
				&nbsp;&nbsp;<strong>Sub Location</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="sublocationForAVS" name="id_sublAV" class="select"  style="width:140" onChange="DisplaySubDropDownData(this,'floorForAVS','M_Floor')">
						<option value="">Select</option>
					</select>
				<br>	
				&nbsp;&nbsp;<strong>Select Facility</strong>&nbsp;
					<select id="floorForAVS" name="id_flrAV"  style="width:140">
						<option value="">Select</option>
					</select>	
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Year</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" id="datepicker2" style="width:100px">
					<select name="" class="select"  style="width:100px">
						<option >Select</option>
						<option value="FH">First Half</option>
						<option value="SH">Second Half</option>
					</select>
				
					
				<button type="button" style="margin-left:93px;margin-top: -12px;"  class="btn btn-primary" onclick="DisplayAssetForAssetVerification()">Search</button>
					
				</td>
			</tr>
		</table>
	</form>
</div>


<div id="DisplayAssetForAssetVerification">
	<table class="table table-bordered displayAssetForAssetVerification">
		<tr class="info new" >
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Group</strong></td>
			<td><strong>Verify</strong></td>
		</tr>
	</table>
</div>


<div id="AssetVerificationDetails">
	<form name="AssetVerify" id="AssetVerify">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset Transfer Request Details</strong></center></td>
				</tr>
				<tr>
					<td ><b>Asset Name</b></td>
					<td ><input type= "text" name="ds_pro" size= "25" value="" readonly></td>
					<td ><b>Installation Date</b></td>
					<td ><input type= "text"  name="dt_alloc"   size= "25" value="" readonly ></td>
				</tr>
				<tr>
					<td ><b>Asset Serial Number</b></td>
					<td ><input type="text"  name="no_mfr" size="25" value="" readonly ></td>
					<td ><b>Group</b></td>
					<td>
						<input type= "text" name="nm_assetdiv" size= "25" value="" readonly>
					</td>
				</tr>
				<tr>
					<td ><b>Asset Type</b></td>
					<td ><input type="text"  name="typ_asst" size="25" value="" readonly ></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td ><b>Current Location</b></td>
					<td >
					<input type= "text" name="nm_loc" size= "25" value="" readonly>
					
				</td>
				<td ><b>To Location</b></td>
					<td>
					<select id="locationForAssetVerification" name="id_loc" class="common-validation " data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'subLocationForAssetVerification','M_Subloc')">
						<option value="">Select</option>
						
					</select>
					</td>	
				</tr>
				<tr>
					<td><b>Current SubLocation<font color = 'Red'>*</font></b></td>
					<td >
					<input type= "text" name="nm_subl" size= "25" value="" readonly>
					
				</td>
				<td><b>To SubLocation<font color = 'Red'>*</font></b></td>
					<td >
					<select id="subLocationForAssetVerification" name="id_subl" class="common-validation " data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'floorForAssetVerification','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td>	
				</tr>
				<tr>
					<td><b>Current Facility<font color = 'Red'>*</font></b></td>
					<td>
						<input type= "text" name="nm_flr" size= "25" value="" readonly>
					</td>
					<td><b>To Facility<font color = 'Red'>*</font></b></td>
					<td>
						<select id="floorForAssetVerification" name = "id_flr"  style="width:140" class="common-validation" data-valid="mand">
							<option value = "" >Select</option>
						</select>
					</td>
					
				</tr>
				<tr>
					<td ><b>Verified on<font color = 'Red'>*</font></b></td>
					<td><input  type="text" name="verified_on" value="" class="common-validation avdatepicker" data-valid="mand"></td>
					<td ><b>Verified By<font color = 'Red'>*</font></b></td>
					<td ><input type= "text"  name="verified_by"   size= "25" value="-"  class="common-validation" data-valid="mand"></td>
				</tr>
				<tr>
				<td><strong>Comments</strong></td>
				<td colspan="3">
					<textarea name="comments" cols="150" rows="2" style="margin: 0px 0px 10px;width: 599px;height: 60px;" value="">
					</textarea>
				</td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="">
				<input type="hidden" name="id_wh_dyn" value="">
				<input type="hidden" name="id_loc" value="">
				<input type="hidden" name="id_subl" value="">
				<input type="hidden" name="id_flr" value="">
				<input type="hidden" name="comments" value="">
				<input type="hidden" name="verified_by" value="">
				<input type="hidden" name="verified_on" value="">
			</tr>
				<tr>
					<td colspan="4">
						<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlAssetVerificationAsset('Save','DisplayAssetForAssetVerificationSearch','DisplayAssetForAssetVerification','AssetVerificationDetails','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlAssetVerificationAsset('Cancel','DisplayAssetForAssetVerificationSearch','DisplayAssetForAssetVerification','AssetVerificationDetails','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>