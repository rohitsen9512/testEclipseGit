
<script type="text/javascript" src="js/masterCommon.js"></script>
<script type="text/javascript" src="common.js"></script>
<script type="text/javascript">
	DisplayDropDownData("M_User_Type", "usertypeDataForuser",
			function(status) {
				if (status) {
					DisplayData('M_User_Access', 'displayUserAccess',
							'createUserAccess');
				DisplayDropDownData('M_Designation','designDataForUserPermission',function (status){
						if(status)
						{
							DropDownDataDisplay('M_Loc','locDataForUser',function (status){
								if(status)
								{
							GetDropDownDataForAssetOwner('typAssetForUser',function (status){
								if(status)
								{
								}});
								}});
						}
					});
						
				}
			});
	$( "#locDataForUser" ).change(function() {
		var locIds ='';
	  $( "#locDataForUser option:selected" ).each(function() {
	    console.log($( this ).val());
	    if(locIds == '')
	    	locIds = $( this ).val();
	    else
	    	locIds +=','+ $( this ).val();
	  });
	  
	  $.post('M_Loc',{action : 'GetAllSubloc' , locIds : locIds} , function (r){
		 if(r.data.length > 0)
			 {
			 	var list = '';
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
				$('#sublocDataForUser').html(list);
			 }
		 else
			 {
			 $('#sublocDataForUser').html('');
			 }
	  },'json');
	  
	  
	});
	
	function Validationcheck() {
		debugger;
		var tot=(parseFloat($('input[name="un_prc_assetdiv"').val()));
		var x = $("#subgroupDataForMaster").val();
		var number = Number(x);
		if(x==null)
			{
				alert('Please enter valid number.');
/* 				$("#un_prc_groupId").val('');
				$("#un_prc_groupId").focus(); */
				
			}
/* 		else if(isNaN(tot) || isNaN(number)) {
	        alert('Please enter valid number.');
			$("#un_prc_groupId").val('');
			$("#un_prc_groupId").focus();
		} */
		
	}
	function getUserType() {

		var id_usertype = $('select[name="id_usertype"]').val();

		$.post('M_User_Type', {
			action : 'getUserType',
			id_usertype : id_usertype
		}, function(r) {

			if (r.data) {
				$('input[name="type_user"]').val(r.data[0].nm_usertype);

			}

		}, 'json');

	}
	
	
	

	function selectAll(selectBox, selectBox1, selectBox2, selectBox3,
			selectBox4, selectBox5, selectBox6, selectBox7, selectAll) {
		// have we been passed an ID 
		if (typeof selectBox == "string") {
			selectBox = document.getElementById(subgroupDataForMasterSelected);
			alert(selectBox);
		}
		// is the select box a multiple select box? 
		if (selectBox.type == "select-multiple") {
			for (var i = 0; i < selectBox.options.length; i++) {

				selectBox.options[i].selected = selectAll;
			}

		}
		if (selectBox1.type == "select-multiple") {
			for (var i = 0; i < selectBox1.options.length; i++) {

				selectBox1.options[i].selected = selectAll;
			}

		}
		if (selectBox2.type == "select-multiple") {
			for (var i = 0; i < selectBox2.options.length; i++) {

				selectBox2.options[i].selected = selectAll;
			}

		}
		if (selectBox3.type == "select-multiple") {
			for (var i = 0; i < selectBox3.options.length; i++) {

				selectBox3.options[i].selected = selectAll;
			}

		}
		if (selectBox4.type == "select-multiple") {
			for (var i = 0; i < selectBox4.options.length; i++) {

				selectBox4.options[i].selected = selectAll;
			}

		}
		if (selectBox5.type == "select-multiple") {
			for (var i = 0; i < selectBox5.options.length; i++) {

				selectBox5.options[i].selected = selectAll;
			}

		}
		if (selectBox6.type == "select-multiple") {
			for (var i = 0; i < selectBox6.options.length; i++) {

				selectBox6.options[i].selected = selectAll;
			}

		}
		/* if (selectBox7.type == "select-multiple") {
			for (var i = 0; i < selectBox7.options.length; i++) {

				selectBox7.options[i].selected = selectAll;
			} 

		}*/

		ControlDivAccess('Update', 'displayUserAccess', 'createUserAccess',
				'submitUserAccess', 'M_User_Access', '', '');
	}
	
	
	function validateForm() {

			debugger;

	        var checkbox1 = document.getElementById("master");

	        var checkbox2 = document.getElementById("stock");

	        var checkbox3 = document.getElementById("order");

	        var checkbox4 = document.getElementById("jobwork");

	        var checkbox5 = document.getElementById("stocktransfer");

	        var checkbox6 = document.getElementById("report"); 

	        var sub =$('#subgroupDataForMasterSelected').text().trim();

	        var sub1=$('#subgroupDataForStockSelected').text().trim();

	        var sub2=$('#subgroupDataForOrderSelected').text().trim();

	        var sub3=$('#subgroupDataForJobWorkSelected').text().trim();

	        var sub4=$('#subgroupDataForStockTransferSelected').text().trim();

	        var sub5=$('#subgroupDataForReportSelected').text().trim(); 



	        if (!checkbox1.checked && !checkbox2.checked && !checkbox3.checked && !checkbox4.checked &&   !checkbox5.checked &&   !checkbox6.checked) {

	           // alert("Please select at least one checkbox.");

	        	bootbox.alert('Please fill-up all mandatory field.');

	          }

	        else if(sub=="" && sub1=="" && sub2=="" && sub3=="" && sub4==""  && sub5==""){

	        	bootbox.alert('Please fill-up all mandatory field.');

	        }

	        else {

	        	//ControlDivAccess('Save','displayUserAccess','createUserAccess','submitUserAccess','M_User_Access','User Permission is already exists.' , 'id_usertype');

	        	ControlDivAccess('Save','displayUserAccess','createUserAccess','submitUserAccess','M_User_Access','User Permission is already exits.','id_usertype');

	        }

	    }
</script>
<!-- Content Wrapper. Contains page content -->

<!-- Content Header (Page header) -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>
					<!--  User Permission-->
					<button type="button" name="create btn" class="btn btn-primary"
						onclick="ControlDivAccess('Create','displayUserAccess','createUserAccess','submitUserAccess','M_User_Access')">Create
						User Permission</button>
				</h1>
			</div>
			<div class="col-sm-6">
				<!-- <ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Master</a></li>
					<li class="breadcrumb-item">User Permission</li>
				</ol> -->
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>

<!-- Main content -->
<div class="card">


	<div id="displayUserAccess">
		<div class="card-body">
			<table id="userpermissionForDisplay"
				class="table table-bordered table-hover userpermissionForDisplay">
				<thead>


					<tr class="new">
						<td><strong>User Permission</strong></td>

						<td><strong>Modify /
									Delete</strong></td>
					</tr>
			</table>

		</div>
		</div>
		</div>

		<section class="content">
			<div class="card">
				<div id="createUserAccess" style="display: none;">
					<div class="card-header new">
						<h3 class="card-title1">User Permission</h3>
					</div>



					<div class="card-body">


						<form action="" name="submitUserAccess" id="submitUserAccess">
							<table class="table table-bordered" id="UserAccessDetails">




								<tr>
									<td><b style="margin-left: 10px;">User Type<font
											color="red">*</font> 
									</b></td>
									<td colspan="3"><select id="usertypeDataForuser" name="id_usertype"
										style="width: 200px;" style="width:140" class="form-control"
										onChange="getUserType()" data-valid="mand">
											<option value="">Select</option>
									</select> <input style="display: none;" type="text" name="type_user">
									</td>
								</tr>

								<tr>
									<td><label for="Master"> <input type="checkbox"
											id="master" name="master" value="master" 
											onclick="getSubMenu(this,'subgroupDataForMaster','M_User_Type');getcheck('subgroupDataForMasterSelected')"> Master
									</label></td>
									<td><select multiple="multiple" id="subgroupDataForMaster"
										style="height: 150px; width: 250px" class="form-control" data-valid="mand" 
										name="selected_submenu_master" >


									</select></td>
									<td>

										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;" id="MoveRight"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeft">

											</div>
										</center>
									</td>
									<td ><b> (Selected Sub-Modules for User
											Permission)</b><br> <select
										style="height: 120px; width: 250px" multiple="multiple"
										id="subgroupDataForMasterSelected" name="sub_menu"
										class="form-control" data-valid="mand" required>

									</select></td>
								</tr>




								<!-- For stock	 -->
								<tr>
									<td><label for="Stock"> <input type="checkbox"
											id="stock" name="stock" value="stock"
											onclick="getSubMenu(this,'subgroupDataForStock','M_User_Type');getcheck('subgroupDataForStockSelected')"> Stock
									</label></td>
									<td><select multiple="multiple" id="subgroupDataForStock"
										style="height: 150px; width: 250px" class="form-control"
										name="selected_submenu_client">
									</select></td>
									<td>
										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;"
													id="MoveRightstock"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeftstock">

											</div>
										</center>
									</td>
									<td ><select
										style="height: 150px; width: 250px; "
										multiple="multiple" id="subgroupDataForStockSelected"
										name="sub_menu1" class="form-control">

									</select></td>
								</tr>
								<!-- For direct Po -->
						<!-- 		<tr>
									<td><label for="DirectPO"> <input type="checkbox"
											id="directpo" name="directpo" value="DirectPO"
											onclick="getSubMenu(this,'subgroupDataForDirectPo','M_User_Type');getcheck('subgroupDataForDirectPoSelected')"> Direct Po
									</label></td>
									<td><select multiple="multiple"
										id="subgroupDataForDirectPo"
										style="height: 150px; width: 250px" class="form-control"
										name="selected_submenu_directpo">
									</select></td>
									<td>
										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;"
													id="MoveRightDirectpo"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeftDirectpo">

											</div>
										</center>
									</td>
									<td ><select
										style="height: 150px; width: 250px;" multiple="multiple"
										id="subgroupDataForDirectPoSelected" name="sub_menu2"
										class="form-control">

									</select></td>
								</tr> -->
								<!-- For order	 -->
								<tr>
								 <td><label for="Order"> <input type="checkbox"
											id="order" name="order" value="Lead"
											onclick="getSubMenu(this,'subgroupDataForOrder','M_User_Type');getcheck('subgroupDataForOrderSelected')"> Order
									</label></td>
									<td><select multiple="multiple"
										id="subgroupDataForOrder"
										style="height: 150px; width: 250px" class="form-control"
										name="selected_submenu_Order">
									</select></td>
									<td>
										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;"
													id="MoveRightOrder"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeftOrder">

											</div>
										</center>
									</td>
									<td ><select
										style="height: 150px; width: 250px; "
										multiple="multiple" id="subgroupDataForOrderSelected"
										name="sub_menu3" class="form-control">

									</select></td>
								</tr> 
								<!-- For Job Work	 -->
								<tr>
									<td><label for="JobWork"><input type="checkbox"
											id="jobwork" name="jobwork" value="jobworkmaster"
											onclick="getSubMenu(this,'subgroupDataForJobWork','M_User_Type');getcheck('subgroupDataForJobWorkSelected')"> Job Work
									</label></td>
									<td><select multiple="multiple"
										id="subgroupDataForJobWork"
										style="height: 150px; width: 250px" class="form-control"
										name="selected_submenu_jobwork">
									</select></td>
									<td>
										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;"
													id="MoveRightJobWork"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeftJobWork">

											</div>
										</center>
									</td>
									<td ><select
										style="height: 150px; width: 250px; "
										multiple="multiple" id="subgroupDataForJobWorkSelected"
										name="sub_menu4" class="form-control">

									</select></td>
								</tr>
								<!-- For Stock Transfer	 -->
								<tr>
									<td><label for="StockTransfer"> <input
											type="checkbox" id="stocktransfer" name="stocktransfer"
											value="stocktransfermaster"
											onclick="getSubMenu(this,'subgroupDataForStockTransfer','M_User_Type');getcheck('subgroupDataForStockTransferSelected')"> Stock Transfer
									</label></td>
									<td><select multiple="multiple"
										id="subgroupDataForStockTransfer"
										style="height: 150px; width: 250px" class="form-control"
										name="selected_submenu_stocktransfer">
									</select></td>
									<td>
										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;"
													id="MoveRightStocktransfer"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeftStocktransfer">

											</div>
										</center>
									</td>
									<td ><select
										style="height: 150px; width: 250px;" multiple="multiple"
										id="subgroupDataForStockTransferSelected" name="sub_menu5"
										class="form-control">

									</select></td>
								</tr>
								<!-- For Analytic Report	 -->
								<tr>
									<td><label for="Report"> <input type="checkbox"
											id="report" name="report" value="report"
											onclick="getSubMenu(this,'subgroupDataForReport','M_User_Type');getcheck('subgroupDataForReportSelected')"> Analytic Report
									</label></td>
									<td><select multiple="multiple"
										id="subgroupDataForReport"
										style="height: 150px; width: 250px" class="form-control"
										name="selected_submenu_report">
									</select></td>
									<td>
										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;"
													id="MoveRightReport"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeftReport">

											</div>
										</center>
									</td>
									<td ><select
										style="height: 150px; width: 250px; "
										multiple="multiple" id="subgroupDataForReportSelected"
										name="sub_menu6" class="form-control">

									</select></td>
								</tr>
								<!-- For Tagging-->
								<tr>
									<td><label for="Tagging"> <input type="checkbox"
											id="tagging1" name="tagging1" value="tagging1"
											onclick="getSubMenu(this,'subgroupDataForTagging1','M_User_Type');getcheck('subgroupDataForTagging1Selected1')"> Tagging
									</label></td>
									<td><select multiple="multiple" id="subgroupDataForTagging1"
										style="height: 150px; width: 250px" class="form-control"
										name="selected_submenu_tagging1">
									</select></td>
									<td>
										<center style="margin-top: -150px;">
											<div class="w3-bar" style="margin-top: 200%;">
												<input type="button" value="Right &raquo;"
													id="MoveRightTagging1"><br>
												<br> <input type="button" value="&laquo; Left"
													id="MoveLeftTagging1">

											</div>
										</center>
									</td>
									<td ><select
										style="height: 150px; width: 250px;" multiple="multiple"
										id="subgroupDataForTagging1Selected1" name="sub_menu7"
										class="form-control">

									</select></td>
								</tr>

								

								<input type="hidden" name="action" value="Save">
								<input type="hidden" name="type_user" value="Save">
								<input type="hidden" id="random" value="">
								<input type="hidden" name="id" value="0">
								<tr class="uPermission">
				<td  colspan="4" style="background-color: ##3f6791;"><center><p style="font-size: 28px;background-color: #3f6791;color: white;">User Permissions Details</p></center></td>
			</tr>
			<!-- <tr class="owner">
				<td class="uPermissionCommon owner"> <strong>Asset Owner <font color="red">*</font></strong></td>
				<td class="uPermissionCommon owner" colspan="3">
				<select name="id_asst_ownr1" style="width: 400px;height: 100px;" id="typAssetForUser" multiple class="form-control" data-valid="mand" required>
				</select>
				</td>
			</tr> -->
		<!-- 	<tr class="uDept">	
				<td class="uPermissionCommon uDept"><strong>Department<font color="red">*</font></strong></td>
				<td class="uPermissionCommon uDept" colspan="3">
				<select name="id_dept1" style="width: 400px;height: 100px;" id="deptDataForUserPermission" multiple class="form-control" data-valid="mand" required><option value="">Select</option></select>
				</td>
			</tr> -->
				<tr class="uDesign">	
				<td class="uPermissionCommon uDesign"><strong>Designation<font color="red">*</font></strong></td>
				<td class="uPermissionCommon uDesign" colspan="3">
				<select name="id_design1" style="width: 400px;height: 100px;" id="designDataForUserPermission" multiple class="form-control"  required><option value="">Select</option></select>
				</td>
			</tr>
			<tr class="uLocation">	
				<td class="uPermissionCommon uLocation"><strong>Entity<font color="red">*</font></strong></td>
				<td class="uPermissionCommon uLocation" colspan="3">
				<select name="id_loc1" style="width: 400px;height: 100px;" id="locDataForUser" multiple class="form-control"  required><option value="">Select</option></select>
				</td>
			</tr>
			<tr class="uSubLocation">
				<td class="uPermissionCommon uSubLocation"><strong>Sub-Location<font color="red">*</font></strong></td>
				<td class="uPermissionCommon uSubLocation" colspan="3">
				<select name="id_subl1" style="width: 400px;height: 100px;" id="sublocDataForUser" multiple class="form-control" data-valid="mand" required>
				</select>
				</td>
			</tr>
			</table>
						</form>
								<tr>
									<td colspan="4"><center>
										<button name="save" type="button" class="btn btn-primary"
											onclick="validateForm()">Save</button>
										<!-- <button name="update" type="button" style="margin-left:400px;"   class="button" onclick="ControlDivAccess('Update','displayUserAccess','createUserAccess','submitUserAccess','M_User_Access','' , '')">Update</button> -->
										<button name="update" class="btn btn-primary"
											onclick="selectAll(subgroupDataForMasterSelected,subgroupDataForStockSelected,subgroupDataForOrderSelected,subgroupDataForJobWorkSelected,subgroupDataForStockTransferSelected,subgroupDataForReportSelected,selectAll)">Update</button>
										<button name="cancel" type="button" class="btn btn-primary"
											onclick="ControlDivAccess('Cancel','displayUserAccess','createUserAccess','submitUserAccess','M_User_Access')">Back</button>
									</center></td>
								</tr>
								
							
					</div>
				</div>

			</div>
</section>



	<!-- Page specific script -->





	</body>
	</html>