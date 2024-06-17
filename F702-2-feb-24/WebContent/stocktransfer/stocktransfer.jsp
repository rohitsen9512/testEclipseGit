<!--Install_asset.jsp-->
<style> 
.select2-results__options{ font-size:15px !important;}
.select2-container .select2-selection--single {font-size:15px;}
/* .select2-selection{
width:150px;
} */
.easy-autocomplete-container {
	max-height: 250px;
	overflow: auto;
	overflow-y: auto;
	overflow-x: hidden;
}
</style>
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css">
<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css">
<script type ="text/javascript" src="All_Js_File/stocktransfer/stocktransfer.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">


function no_auto_gen(){
	
	  $.post('StockTransferr',{action : 'autogenrateno'},function (r){
		
			console.log(r.dc);
			$('input[name="no_dc"]').val(r.dc);
			
},'json'); 
	  
} 

$(function() {
	var currentDate = new Date();
	$(".datepicker").datepicker({
		yearRange : '1985:2025',
		changeMonth : true,
		changeYear : true,
		dateFormat : "dd/mm/yy",
		autoSize : true,
		altFormat : "dd/mm/yy",
		 minDate: 0
	});

	$('input[name="dt_dc"]').datepicker("setDate", currentDate);
	
	
	DisplayDropDownData("M_Emp_User","empdataforsendBy",function (status){
	if(status)
		{
		DropDownDataDisplay("M_Loc","frm_id_locdata",function (status){
			if(status)
				{
				DropDownDataDisplay("M_Loc","to_id_locdata",function (status){
					if(status)
						{
						stockTransferlineitem('New');
						no_auto_gen();
						
						}});
					
			
				}});
			
		}}); 
	
}); 

function storeloc(){
	stockTransferlineitem('New');
}



function validateInput1(id) {
    var inputElement = document.getElementById(id);
    var inputValue = inputElement.value.trim();

    // Replace characters that are not alphanumeric, dash, special characters, or forward slash
     var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/-]/g, '');

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
<!-- <div class="commonDiv" id="displayProductforServiceSearch">
	<input type="text" name="search" class="form-control" style="width:20%;" value="" placeholder="Search" onkeyup="Displaydataservice($('#slocdataforservice').val())">
	
</div> -->
<div id="DisplayStocktransfer">

<div class="card">

	<form name="SubmitStockTransfer" id="SubmitStockTransfer">
		
		<table class="table table-bordered DisplayProductForservicingHead" id ="DisplayProductForservicingHead">
		<tr class="new backcolor">
									
								<td colspan="2">
										<strong>DC Number<font color="red">*</font></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="text" value="" id="no_dc" class="Commonforservice form-control" onkeyup="validateInput1('no_dc')" name="no_dc" style="width: 400px;" ></td>
									</td>
											
										<td colspan="2">
									<strong>DC Date<font color="red">*</font></strong>&nbsp;&nbsp;
									<input type="text" value="" id="dt_dc" class="common-validation datepicker Commonforservice form-control" readonly style="background-color:white;font-size: 1em;" data-valid="mand" name="dt_dc" style="width: 400px;"></td>
									</tr>
										
								<tr class="new backcolor">
										<td colspan="2">
											<strong>From Entity<font color="red">*</font></strong>
											<select id="frm_id_locdata" class="Commonforservice form-control" name="frm_id_loc" style="width: 400px;" onChange="DisplaySubDropDownData(this,'frm_id_slocdata','M_Subloc');Displaydataservice($(this).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
												<td colspan="2">
											<strong>To Entity<font color="red">*</font></strong>
											<select id="to_id_locdata" class="Commonforservice form-control" name="to_id_loc" style="width: 400px;"  onChange="DisplaySubDropDownData(this,'to_id_slocdata','M_Subloc');Displaydataservice($(this).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
										
									</tr>	
										<tr class="new backcolor">
									
											<td colspan="2">
											<strong>From City<font color="red">*</font></strong>
											<select id="frm_id_slocdata" class="Commonforservice form-control" name="frm_id_sloc" style="width: 400px;" onChange="Displaydataservice($(this).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
											<td colspan="2">
											<strong>To City<font color="red">*</font></strong>
											<select id="to_id_slocdata" class="Commonforservice form-control" name="to_id_sloc" onkeyup="" style="width: 400px;" onChange="Displaydataservice($(this).val())" >
											
												<option value="">Select</option>
											</select>
											</td>
										
									</tr>	
									<!-- <tr class="success">
						<td colspan="6" style="text-align: center;"><button type="button"   class="btn btn-primary Outforservice" onclick="checkBoxValidation('productforservice')">Out for Service</button>
						</td></tr> -->
		</table>
				<div id="createLineitemtransfer">
					<form name="submitLineitemtransfer" id="submitLineitemtransfer"
						enctype="multipart/form-data">
						<button name="save22" type="button" style=""
							class="btn btn-primary" onclick="stockTransferlineitem('Save')">Add</button>
						<!-- <table class="table table-bordered EditleadOrders">
						</table>
						<table class="table table-bordered" id="leadDetails">
						</table> -->
						<table class="table table-bordered" id="leadDetails1">
						</table>
						<div style="float:right;margin-right: 22px;">
					<!-- 	<strong style="margin-right: 100px;">Grand Total</strong>  -->
						
					<!-- 	<tr><strong>Grand Total<font color="red">*</font></strong></tr>
							<tr><input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr> -->
						
							
						
							</div> 
						<table class="table table-bordered" id="leadDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="transfer" class="form-control"> 
								
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
							
									<button id="transfbutton" name="transfer" type="button" style="margin-left: 502px;"
										class="btn btn-primary upd"
										onclick="Transfer('transfer','DisplayStocktransfer','SubmitStockTransfer','O_Lead')">Transfer</button>
								
									
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
	</div>
</section>
