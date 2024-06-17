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
	
	DisplayDropDownData("M_Emp_User","empdataforAddedBy",function (status){
	if(status)
		{
		DropDownDataDisplay("M_Loc","locdataforafetrsrvicerestock",function (status){
			if(status)
				{
				ControlforRestore("J_Servicing");
				}});
			
		}}); 
	/* setTimeout(function() {
		DisplayAssetForBulkInstall("A_Install");
			 
	}, 10); */
}); 

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
<div class="commonDiv" id="DisplayProductRestoreafterserviceSearch">
	<input type="text" name="search" class="form-control" style="width:20%;" value="" placeholder="Search" onkeyup="DisplayRestore($('#slocdataforafetrsrvicerestock').val())">
	
</div>
	<div id="DisplayProductRestoreafterservice">

<div class="card">

	<form name="productforrestore" id="productforrestore">
		<input type="hidden" name="action" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="upload_asset" value="" class="form-control">
		<table class="table table-bordered DisplayProductAfterservicingRestoreHead" id ="DisplayProductAfterservicingRestoreHead">
		<tr class="new backcolor">
										<td colspan="2">
											<strong>Added By<font color="red">*</font></strong>
											<select id="empdataforAddedBy" class="Commonforservice form-control" name="added_by" style="width: 400px;" onchange="getDATAFromEMPuser(this)">
											
												<option value="">Select</option>
											</select>
											</td>
											<td colspan="2">
											<strong>Entity<font color="red">*</font></strong>
											<select id="locdataforafetrsrvicerestock" class="Commonforservice form-control" name="id_loc" style="width: 300px;" onChange="DisplaySubDropDownData(this,'slocdataforafetrsrvicerestock','M_Subloc');DisplayRestore($(this).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
											<td colspan="3">
											<strong>City<font color="red">*</font></strong>
											<select id="slocdataforafetrsrvicerestock" class="Commonforservice form-control" name="id_sloc" style="width: 200px;" onChange="DisplayRestore($(id_loc).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
											
										
									</tr>
										
								<tr class="new backcolor">
										
											
<!-- 										<td colspan="2">
									<strong>Date<font color="red">*</font></strong>&nbsp;&nbsp;
									<input type="text" value="" id="serviceId" class="common-validation datepicker CommonForBulk form-control" data-valid="mand" name="dt_snd_service" style="width: 150px;"></td> -->
									</tr>	
											<tr style="display:none" class="success new">
										
										</tr>
									<tr class="success">
						<td colspan="6" style="text-align: center;"><button type="button"   class="btn btn-primary restoreafterservice" onclick="checkBoxValidation1('productforrestore')">Re-store</button>
						</td></tr>
		</table>
		<table class="table table-bordered DisplayProductSendForRestore" id ="DisplayProductSendForRestore">
		
			
		</table>
		
	</form>
</div>
</div>
</div>
</div>
