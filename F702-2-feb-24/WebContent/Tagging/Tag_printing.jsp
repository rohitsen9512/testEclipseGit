<!--Tag_printing.jsp-->

<script type ="text/javascript" src="All_Js_File/Tagging/tag_print.js"></script>
<!-- <script type ="text/javascript" src="All_Js_File/Asset/A_Add_To_Store.js"></script> -->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">

$(function() {
	
	$('#DisplayAssetForTagPrint').hide();
	$('#PreviewAssetForTagPrint').hide();
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForTagPrint','CapG',function (status){
		if(status)
		{
			
		}});
	
	
});
$(document).ready(function() {
    $('.select2search').select2();
});
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
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Asset</a></li>
						<li class="breadcrumb-item">Tag Printing</li>
					</ol>
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
					<td colspan="4" class="new"><center><h2>Category & Sub Category</h2></center></td>
			</tr>
			<tr>
				<td><strong>&nbsp;&nbsp;Category<font color="red">*</font> </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><select id="assetDivForTagPrint" name="id_grp2" class="form-control select2search" onChange="SubDropDownDataDisplay(this,'subassetDivDataForTagPrint','M_Subasset_Div')">
						<option value="">Select</option>
					</select>
					</td>
				<td><strong>Sub Category<font color="red">*</font> </strong>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><select id="subassetDivDataForTagPrint" class="form-control select2search" name="id_grp2">
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

 <div id="DisplayAssetForTagPrint" class="card">
 <div class="card-body">
<input type="text" name="search" id="searchIdForTagging" value="" placeholder="Search" onkeyup="ControlTaggingForTagPrint('Next')">
<form action="Tagging/Take_Print_For_Tag.jsp" method="POST" target="_new">
	<table class="table table-bordered DisplayAssetForTagPrint">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Model Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Tag It </strong></td>
		</tr>
	</table>
</form>
</div>
</div> 