<!--Create_Grn.jsp-->
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<link href="CSS/loading.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="All_Js_File/Master/Upload_Master.js"></script>
<script src="js/jszip.js"></script>
<script src="js/xlsx.js"></script>

<!-- Content Wrapper. Contains page content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1><!--  Upload Excel--></h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Master</a></li>
					<li class="breadcrumb-item">Upload Excel</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div id="displayInvoiceForStore" class="commonDiv">
<div style="display:none;" class="loading">Loading&#8230;</div>
	<div class="card">
		<table width="100%" height="100%">
			<tr class="new">
			<td><b>Module Name<font color="red">*</font></b> </td>
				<td>
					<select  name="module" id="module" class="form-control"  data-valid="mand">
						<option value="">Select</option>
						<option value="location">Location</option>
						<option value="sub_location">Sub-Location</option>
						<option value="building">Building</option>
						<option value="floor">Floor</option>
						<option value="category">Category</option>
						<option value="sub_category">Sub-Category</option>
						<option value="model">Model/Item</option>
						<option value="department">Department</option>
						<option value="cost_center">Cost Center/Project</option>
						<option value="user_type">User Type</option>
						<option value="designation">Designation</option>
						<option value="employee">Employee</option>
						<option value="vendor">Vendor</option>
						<option value="tax">Tax</option>
					</select>
				</td>
				<td><b style="font-size : small1; margin-left: 50px;">Upload
						File</b><input id="upload" type=file
					style="margin-left: 5px; width: 200px;" name="files[]"><a style="background-color: darkgray;"
					id="download_master" href="#" onclick="testif(event);">Download
						Reference File</a></td>
			</tr>
			
		</table>
	</div>
</div>
</div>
<div id="dialog" style="display: none;" width="500px" height="400px"></div>


