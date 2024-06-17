<!--Scanning.jsp-->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type="text/javascript">

$("form#submitScanning").submit(function(event){
	 
	  //disable the default form submission
	  event.preventDefault();
	 
	  //grab all form data  
	  var formData = new FormData($(this)[0]);
	 
	  $.ajax({
	    url: 'T_Scanning',
	    type: 'POST',
	    data: formData,
	    async: false,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (returndata) 
	    {
	    	bootbox.alert("Scanning details updated successfully");
	    }
	  });
	 
	  return false;
	});


$(function() {
	
	
	
	$('#datepicker111').datepicker({
		
	     
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

$("#datepicker111").focus(function () {
	$(".ui-datepicker-calendar").hide();
	$("#ui-datepicker-div").position({
	my: "center top",
	at: "center bottom",
	of: $(this)
	});
	});
	
DropDownDataDisplay("M_Loc","locationForScan",function (status){
	if(status)
		{
		
		}});
	
	/* DisplayDropDownData("M_Loc","locationForScan",function (status){
		if(status)
		{
			DisplayDropDownData("M_Emp_User","userForScan",function (status){
				if(status)
				{
					
				}});
		}}); */
	/* DisplayDropDownData("M_Subloc","subLocationForScan");
	
	DisplayDropDownData("M_Floor","floorForScan"); */
	
});
$(document).ready(function() {
    $('.select2search').select2();
});
</script>
	<section class="content">
		<div class="card-small"id="displayScanning">
		
				<div class="card-header new">
					<h3 class="card-title1">Scan Asset</h3>
				</div>

<div class="card-body">
	<form name="submitScanning" id="submitScanning" enctype="multipart/form-data">
		<table class="table table-bordered ">
			
			
			<tr>
			
				<td><b>Location<font color="red">*</font></b></td>
				<td>
					<select id="locationForScan" name="id_loc"  class="form-control select2search" data-valid="mand" required style="width: 100%"  onChange="DisplaySubDropDownData(this,'subLocationForScan','M_Subloc')">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
				<tr>
				<td><b>Sub-Location<font color="red">*</font></b></td>
				<td>
					<select id="subLocationForScan" name="id_sloc"  class="form-control select2search" data-valid="mand" required style="width: 100%"onChange="SubDropDownDataDisplay(this,'buildingForScan','M_Building')">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
				
			<tr>
				
				
				<td><b>Building<font color="red">*</font></b></td>
				<td>
					<select id="buildingForScan" name="id_building"  class="form-control select2search" data-valid="mand" required  style="width: 100%" onChange="SubDropDownDataDisplay(this,'floorForScan','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
				<tr>
				<td><b>Floor<font color="red">*</font></b></td>
				<td>
					<select id="floorForScan" name="id_flr"  class="form-control select2search" data-valid="mand" required  style="width: 100%" >
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
				<tr>
				
				<td ><b>Year<font color="red">*</font></b></td>
				<td><input type="text" id="datepicker111" name="year" class="form-control select " style="width:350px; display:inline"required data-valid="mand" >
				
				
				 
				<select name="period"   class="form-control select"style="width:160px; display:inline" data-valid="mand" >
						<option value="FH">First Half</option>
						<option value="SH">Second Half</option>
					</select>
				</td> 
				
				</tr>
		 
			<tr>
				
				
				<td><strong>Upload Scan File(.txt)<font color="red">*</font></strong></td>
				<td><input type="file" name="file" class="form-control " value="" required data-valid="mand"></td>
				
			</tr>
			
		    <tr>
		    <input type="hidden" name="action" value="Save" >
				<input type="hidden" name="id" value="0" >
		    </tr>
		</table>
			
			
			
					<p style="width:180px;margin-left: 500px;float:center;">
						<button type="submit" style="float:center;"  class="btn btn-primary">Upload</button>
					</p>
		</form>
</div>
</div>
</div>
</section>
