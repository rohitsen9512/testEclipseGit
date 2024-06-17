<!--Tag_printing.jsp-->

<script type ="text/javascript" src="All_Js_File/Tagging/tag_print.js"></script>

<!-- <script type ="text/javascript" src="All_Js_File/Asset/A_Add_To_Store.js"></script> -->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">

$(function() {
	
	$('#DisplayprodForTagPrint').hide();
	$('#PreviewProdForTagPrint').hide();
	DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
		if (status) {
			
			/* DisplayDropDownData('M_Designation','dgnForEmp',function (status){
				if(status)
				{ */
					
					//dispalyLineItemLead('New');
					/* }
			}); */
		 }
	}); 
	
	
});
$(document).ready(function() {
    $('.select2search').select2();
});


function DisplayDropDownDataFortagging(id,servletName,dropDownId,loc,callback)
{
	var t =false;
	var list= '';
	 
	var id_sloc=id.value;
	var id_loc=$('#'+loc).val();
	$.post(servletName,{action : 'DropDownResult',id:id_sloc,id1:id_loc},function (r){
		//alert();
		if(r.data)
			{
			if(dropDownId == ('assetDivForStoreAssetProcReport') || dropDownId == ('assetDivForMaintenanceReport') ||  dropDownId == ('assetGrpForTransferReport') || dropDownId == ('assetDivForAssetHReport') || dropDownId == ('assetDivForStoreAssetReport') || (dropDownId == 'assetDivForMonthlyFaReport') || (dropDownId == 'assetDivForFaYearlyReport') || (dropDownId == 'typeIdForFaYearlyReport') || (dropDownId == 'assetDivForStoreAssetLReport') || (dropDownId == 'assetDivFordeptReport') || (dropDownId == 'assetGrpForTransferReport11'))
				list= '<option value="All"> All</option>';
			else
			 list= '<option value=""> Select</option>';
			if(dropDownId ==('assetDivForYearlyDprn') || dropDownId ==('assetDivForMonthlyDprn'))
				list ='';
				t=true;
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			if(dropDownId=='groupDrop'){
				$('.groupdrop').html(list);
			}
			else if(dropDownId == 'group')
				$('.group').html(list);
			else{
				$('#'+dropDownId).html(list);
			}
			
				$('#'+dropDownId).focus();
			}
		/*else
			{
				bootbox.alert("Try again.");
			}*/
		callback(t);
	},'json');

}

</script>
 <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Tag Printing-->
						
					</h1>
				</div>
				<div class="col-sm-6">
				<!-- 	<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Asset</a></li>
						<li class="breadcrumb-item">Tag Printing</li>
					</ol> -->
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
<section class="content">
		
		
<div class="commonDiv" id="displayAssetForTagPrintSearch">
	<div class="card-body">
	<div class="card-small">
		<table class="table table-bordered" >
			<tr>
					<td colspan="4" class="new"><center><h2>Product</h2></center></td>
			</tr>
			<tr>
			<td><b>Entity<font color="red">*</font></b></td>
						<td><select id="locDataForLead" name="id_loc"
							class="form-control select2search" data-valid="mand" style="max-width: 140"
							onChange="DisplaySubDropDownData(this,'slocDataForTagPrint','M_Subloc')">
								<option value="">Select</option>
						</select></td>
						</tr>
						<tr>
				<td><strong>&nbsp;&nbsp;City<font color="red">*</font> </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><select id="slocDataForTagPrint" name="id_subl" class="form-control select2search" onChange="DisplayDropDownDataFortagging(this,'M_Asset_Div','prodDivDataForTagPrint','locDataForLead')">
						<option value="">Select</option>
					</select>
					</td>
					</tr>
					<tr>
				<td><strong>Product<font color="red">*</font> </strong>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><select id="prodDivDataForTagPrint" class="form-control select2search" name="id_grp">
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
					<td colspan="4">
						<center><button name="next" type="button" class="btn btn-primary" onclick="ControlTaggingForTagPrint('Next')">Next</button></center>
					</td>
				</tr>
		</table>
			
</div>
</div>

</div>
</section>

 <div id="DisplayprodForTagPrint" class="card">
 <div class="card-body">
<input type="text" name="search" id="searchIdForTagging" value="" placeholder="Search" onkeyup="ControlTaggingForTagPrint('Next')">
<form action="Tagging/Take_Print_For_Tag.jsp" method="POST" target="_new">
	<table class="table table-bordered DisplayprodForTagPrint">
		<tr class="info new">
			
			<td><strong>Product Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Phone No</strong></td>
			
		</tr>
	</table>
</form>
</div>
</div> 