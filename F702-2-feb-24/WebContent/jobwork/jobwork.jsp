<!--Install_asset.jsp-->
<style> 
.select2-results__options{ font-size:15px !important;}
.select2-container .select2-selection--single {font-size:15px;}
/* .select2-selection{
width:150px;
} */
</style>
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type ="text/javascript" src="All_Js_File/Order/jobwork.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">

$(function() {
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	DisplayDropDownData("M_Emp_User","empdataforsendBy",function (status){
	if(status)
		{
		DropDownDataDisplay("M_Loc","locdataforservice",function (status){
			if(status)
				{
				Controlforservice("J_Servicing");
				}});
			
		}}); 
	/* setTimeout(function() {
		DisplayAssetForBulkInstall("A_Install");
			 
	}, 10); */
}); 

function validateInput1() {
    var inputElement = document.getElementById("serviceProvideBY");
    var inputValue = inputElement.value.trim();

    // Replace characters that are not alphanumeric, dash, special characters, or forward slash
    var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/\-!@#$%^&*()_+={}[\]:;<>,.?~\\'"]/g, '');

    // Remove spaces between characters
    sanitizedValue = sanitizedValue.replace(/\s/g, '');

    if (sanitizedValue !== inputValue) {
       // bootbox.alert("Please enter valid characters in the field (i.e., alphabets, numbers, dash, special characters).");
        inputElement.value = sanitizedValue.toUpperCase();
        return;
    }

    inputElement.value = sanitizedValue.toUpperCase();
}


</script>



<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Allocation  -->
						
					</h1>
				</div>
				<div class="col-sm-6">
					<!-- <ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Asset</a></li>
						<li class="breadcrumb-item">Allocate</li>
					</ol> -->
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>


<div class="card">
<div class="card-body">
<div class="commonDiv" id="displayProductforServiceSearch">
	<input type="text" name="search" class="form-control" style="width:20%;" value="" placeholder="Search" onkeyup="Displaydataservice($('#slocdataforservice').val())">
	
</div>
	<div id="DisplayProductforService">

<div class="card">

	<form name="productforservice" id="productforservice">
		<input type="hidden" name="action" value="">
		<input type="hidden" name="id" value="">
		
		<input type="hidden" name="upload_asset" value="" class="form-control">
		<table class="table table-bordered DisplayProductForservicingHead" id ="DisplayProductForservicingHead">
		<tr class="new backcolor">
										<td colspan="2">
											<strong>Send By<font color="red">*</font></strong>
											<select id="empdataforsendBy" class="Commonforservice form-control" name="send_by" style="width: 400px;">
											
												<option value="">Select</option>
											</select>
											</td>
											<td colspan="2">
										<strong>Service Provider<font color="red">*</font></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="text" value="" id="serviceProvideBY" class="Commonforservice form-control" name="service_by" onkeyup="validateInput1();" style="width: 400px;"></td>
									</td>
											
										<td colspan="2">
									<strong>Date<font color="red">*</font></strong>&nbsp;&nbsp;
									<input type="text" value="" id="serviceId" class="common-validation datepicker Commonforservice form-control" data-valid="mand" readonly style="background-color:white;font-size: 1em;" name="dt_snd_service" style="width: 150px;"></td>
									</tr>
										
								<tr class="new backcolor">
										<td colspan="2">
											<strong>Entity<font color="red">*</font></strong>
											<select id="locdataforservice" class="Commonforservice form-control" name="id_loc" style="width: 400px;" onChange="DisplaySubDropDownData(this,'slocdataforservice','M_Subloc');Displaydataservice($(this).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
											<td colspan="3">
											<strong>City<font color="red">*</font></strong>
											<select id="slocdataforservice" class="Commonforservice form-control" name="id_sloc" style="width: 400px;" onChange="Displaydataservice($(id_loc).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
										
									</tr>	
									
									<tr class="success">
						<td colspan="6" style="text-align: center;"><button type="button"   class="btn btn-primary Outforservice" onclick="checkBoxValidation('productforservice')">Out for Service</button>
						</td></tr>
		</table>
		<table class="table table-bordered DisplayProductSendForservicing" id ="DisplayProductSendForservicing">
		
			
		</table>
		
	</form>
</div>
</div>
</div>
</div>
