function checkBoxValidationForInterTransfer(FormName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=false;
	var ids='';
	var tranType = $('#TranTypeForValidation').val();
			$('[name="requestAsset"]:checked').each(function(){
				//$('input[type="checkbox"]').each(function(){
				if(this.checked)
					{
						t=true;
						if(ids == '')
							ids = $(this).val();
						else
							ids += "patel"+$(this).val();
					}
			});
	
	if(!t)
	{
		bootbox.alert("Please select at least one asset.");
	}else {
			t=true;
			if(tranType == 'Returnable')
				{
				$('select[name="id_ven"]').removeClass('error');
				if($('select[name="id_ven"]').val() == '')
					{
						t=false;
						bootbox.alert('Mandatory Field.');
						$('select[name="id_ven"]').addClass('error');
						$('select[name="id_ven"]').focus();
						exit(0);
					}
					
				}
			else
				{
					$('select[name="id_loc_tran"]').removeClass('error');
					$('select[name="id_subl_tran"]').removeClass('error');
					if($('select[name="id_div_tran"]').val() == '' || $('select[name="id_loc_tran"]').val() == '' || $('select[name="id_subl_tran"]').val() == '' || $('select[name="id_building_tran"]').val() == '' || $('select[name="id_flr_tran"]').val() == '')
						{
							t=false;
							bootbox.alert('Please fill all mandatory field.');
							/*$('select[name="id_loc_tran"]').addClass('error');
							$('select[name="id_loc_tran"]').focus();*/
							exit(0);
						}
						
						/*if($('select[name="id_subl_tran"]').val() == '')
						{
							t=false;
							bootbox.alert('Mandatory Field.');
							$('select[name="id_subl_tran"]').addClass('error');
							$('select[name="id_subl_tran"]').focus();
							exit(0);
						}*/
						
				}
			
	}
	if(t)
		{
		$('.requestAssetBtn').attr('disabled','disabled');
		$('input[name="ids"]').val(ids);
		var x = $('#'+FormName).serialize();
		$.post('T_Inter_Transfer_Request',x,function (r)
				{
			 if(r.data == 1){
					bootbox.alert('Selected asset has been requested successfully.');
					//$( ".interunittransferrequest_event" ).trigger( "click" );
					window.location = $('.interunittransferrequest_event').attr('href');
				}
			
				},'json');
			
		
		
			
		}
			}});
}
function ControlInterUnitTransferRequestAsset(action , DisplayDiv , HideDiv , HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == "Edit")
	{
		AddInputValueForTransferRequestForInter(id);
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).hide();
		$('#'+HideDiv2).show();
		
	}else if(action == 'Next'){
		var t=false;
		t = ValidationForm('searchFormForInterUTR');
		if(t){
			$('#'+DisplayDiv).hide();
			$('#'+HideDiv).hide();
			$('#'+HideDiv2).show();
			
			
			$('#displayAssetForIUTSearch').addClass('hideButton');
			$('#DisplayAssetForIntraUnitTransferRequest').removeClass('hideButton');
			DisplayAssetForInterUnitTransferRequest();
		}
		
	}
	else if(action == "Save")
	{
		
	var t = false;
	t = ValidationForm("submitTransferRequest");
	
	if(t)
		{
		var tranType = $('#TranTypeForValidation').val();
		if(tranType == 'Temporary')
			{
			$('select[name="id_ven"]').removeClass('error');
			if($('select[name="id_ven"]').val() == '')
				{
					t=false;
					bootbox.alert('Mandatory Field.');
					$('select[name="id_ven"]').addClass('error');
					$('select[name="id_ven"]').focus();
					exit(0);
				}
				
			}
		else
			{
				$('select[name="id_loc_tran"]').removeClass('error');
				$('select[name="id_subl_tran"]').removeClass('error');
				if($('select[name="id_loc_tran"]').val() == '')
					{
						t=false;
						bootbox.alert('Mandatory Field.');
						$('select[name="id_loc_tran"]').addClass('error');
						$('select[name="id_loc_tran"]').focus();
						exit(0);
					}
					
					if($('select[name="id_subl_tran"]').val() == '')
					{
						t=false;
						bootbox.alert('Mandatory Field.');
						$('select[name="id_subl_tran"]').addClass('error');
						$('select[name="id_subl_tran"]').focus();
						exit(0);
					}
			}
		
		}
	
	
	var x = $('#submitTransferRequest').serialize();
	if(t > 0)
		{
		$('.interUTBTN').attr('disabled','disabled');
			$.post('T_Inter_Transfer_Request',x,function (r)
					{
						if(r.data)
							{
								t=1;
							}
							else
								{
									t=0;
									bootbox.alert("Data is not inserted in database please try again.");
								}
								
						if(t == 1)
						{
							/*DisplayAssetForInterUnitTransferRequest();
							$('#'+HideDiv2).hide();
							$('#'+DisplayDiv).show();
							$('#'+HideDiv).show();*/
							//$( ".interunittransferrequest_event" ).trigger( "click" );
							window.location = $('.interunittransferrequest_event').attr('href');
						}
						$('.interUTBTN').removeAttr('disabled');
					},'json');
		}
	
	
}
	
	else if(action == "Cancel")
		{
		$('#'+HideDiv2).hide();
		$('#'+DisplayDiv).show();
		$('#'+HideDiv).show();
		
		}
			}});
}

/*function GetDropDownDataForAssetOwnerAccess(dropDownId , callback)
{
	var t=false;
	$.post("UserAccessControle",{action : 'DropDownResult'},function (r){
		
		var list= '<option value=""> Select</option>';
		if(dropDownId == ('typAssetForUser'))
			list ='';
		
			if(r.data.length > 0)
				{
					var id='',val='';
					for(var i = 0; i < r.data.length ; i++)
					{
						
						for (var key in r.data[i])
				        {
							val=r.data[i][key];
							id = key;
				        }
						list = list + '<option value="'+id+'"> '+val+'</option>';
					}
				
				
					$('#'+dropDownId).html(list);
					t=true;
				}
			callback(t);
	},'json');
}*/
function DisplayAssetForInterUnitTransferRequest()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
			var id_div =  $('#divDataForTransferR').val();
			var id_loc = $('#locDataForEmpUser').val();
			var id_sloc = $('#slocDataForEmpUser').val();
			var id_building =  $('#buildingDataForEmpUser').val();
			var id_flr =  $('#floorDataForEmpUser').val();
			var id_dept =  $('#deptDataForEmpUser').val();
			
				$.post('T_Inter_Transfer_Request',{action : 'Display' ,searchWord : searchWord,id_loc:id_loc,id_sloc:id_sloc,id_div:id_div,id_building:id_building,id_flr:id_flr,id_dept:id_dept} , function (r){
	
					var list= '';
					
				if(r.data.length > 0)
					{
						
						list = list + '<tr class="success">'+
						
						'<td ><b>Transfer Type<font color = "Red">*</font></b></td>'+
									'<td><select id="TranTypeForValidation" name = "type_tran"  style="width:140" class="form-control" data-valid="mand" onChange="ShowRowColumnForIUT(this , \'HideLocAndSubLocForIUT\')">'+
										'<option value = "Returnable" >Returnable</option><option value = "Non Returnable" >Non Returnable</option>'+
										'</select></td>'+
										/*'<td >'+
											'<strong>Assign To<font color="red">*</font></strong>&nbsp;&nbsp;'+
											'<select id="UserDataForBulkInstallAsset" class="CommonForBulk" name="to_assign" style="width: 150px;" onchange="getDATAFromEMPuser(this)">'+
												'<option value="">Select</option>'+
											'</select>'+
											'</td>'+*/
											/*'<td >'+
												'<strong>Department<font color="red">*</font></strong>&nbsp;&nbsp;'+
												'<select id="divDataForBulkInstall" class="CommonForBulk" name="id_dept_tran" style="width: 150px;" >'+
													'<option value="">Select</option>'+
												'</select>'+
											'</td>'+*/
											
											'<td >'+
											'<strong>City Location<font color="red">*</font></strong>&nbsp;&nbsp;</td>'+
											'<td ><select id="LocDataForBulkInstallAsset" class="form-control" name="id_loc_tran" style="width: 150px;" data-valid="mand" onChange="DisplaySubDropDownData(this,\'SubLocDataForBulkInstallAsset\',\'M_Subloc\')">'+
												'<option value="">Select</option>'+
											'</select>'+
										'</td>'+
										'<td>'+
											'<strong>City Sub Location<font color="red">*</font></strong>&nbsp;&nbsp; '+
											' <select id="SubLocDataForBulkInstallAsset" class="form-control" name="id_subl_tran" style="width: 150px;" data-valid="mand" onChange="SubDropDownDataDisplay(this,\'buildingDataForBulkInstallAsset\',\'M_Building\')">'+
												'<option value="">Select</option>'+
											'</select>'+
										'</td>'+
											
											'</tr>'+
											
											'<tr class="success">'+
											
											'<td >'+
											'<strong>Building<font color="red">*</font></strong>&nbsp;&nbsp; </td>'+
											' <td ><select id="buildingDataForBulkInstallAsset" class="form-control" name="id_building_tran" style="width: 150px;" data-valid="mand" onChange="SubDropDownDataDisplay(this,\'FloorForBulkInstallAsset\',\'M_Floor\')">'+
												'<option value="">Select</option>'+
											'</select>'+
										'</td>'+
									
									
									
									'<td >'+
										'<strong>Floor<font color="red">*</font></strong>&nbsp;&nbsp; </td >'+
										'<td ><select id="FloorForBulkInstallAsset" class="form-control" name="id_flr_tran" style="width: 150px;"data-valid="mand">'+
											'<option value="">Select</option>'+
										'</select>'+
									'</td>'+
									
									'<td >'+
										'<strong>Department<font color="red">*</font></strong>&nbsp;&nbsp; '+
										'<select id="DeptForBulkInstallAsset" class="form-control" name="id_dept_tran" style="width: 150px;"data-valid="mand">'+
											'<option value="">Select</option>'+
										'</select>'+
									'</td>'+
									
									'<input type="hideen" name="id_cc" value="0">'+
									
									'</tr>'+
									'<tr class="success">'+
									
									 
						
						'<td colspan="6"><button type="button" style="margin-left: 400px;"  class="btn btn-primary requestAssetBtn" onclick="checkBoxValidationForInterTransfer(\'submitTransferRequest\')">Send Request</button>'+
						'</td></tr>';
						
						list= list+ '<tr class="tableHeader info">'+
					'<td><strong><center>Asset ID</center></strong></td>'+
					'<td style="width: 390px;"><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					'<td><strong><center>Asset Description</center></strong></td>'+
					'<td><center><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'uninstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'uninstallAssetForSelectAll\')"> Uncheck </a></center></strong></center></td>'+
				'</tr>';
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
							params = r.data[i];
							if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
							if(params.no_mfr == '') no_mfr = '-'; else no_mfr = params.no_mfr;
							if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
							
							list = list + '<tr class="success">'+
							'<td><center>'+params.id_wh_dyn+'</center></td>'+
							'<td><center>'+ds_pro+'</center></td>'+
							'<td><center>'+params.serial_no+'</center></td>'+
							'<td><center>'+ds_asst+'</center></td>'+
							'<td><strong><center><input type="checkbox" name="requestAsset" class="uninstallAssetForSelectAll" value="'+params.id_wh+'"/></center></strong></td>'+
							'</tr>';
						}
					
						/*list = list + '<tr><td><b>Transfer Type<font color = "Red"> * </font> :</b></td>'+
									'<td><select id="TranTypeForValidation" name = "type_tran"  style="width:140" class="form-control" data-valid="mand" onChange="ShowRowColumnForIUT(this , \'HideLocAndSubLocForIUT\')">'+
										'<option value = "Returnable" >Returnable</option><option value = "Non Returnable" >Non Returnable</option>'+
										'</select></td>'+
								'<td colspan="3" class="vendorHideShowForIUT"><b>Vendor <font color = "Red">*</font> :</b>'+
								'<select id="vendorForIntraUnitTransferRequest" class="vendorHideShowForIUT" name="id_ven"   style="width:140"><option value="">Select</option></select>'+	
					'</td></tr>'+*/
					list = list +/* '<tr class="HideLocAndSubLocForIUT"><td ><b>To Division <font color = "Red">*</font> :</b></td>'+
						'<td colspan="2"><select id="divitionForIntraUnitTransferRequest" name="id_div_tran"  style="width:140" onChange="DisplaySubDropDownData(this,\'locationForIntraUnitTransferRequest\',\'M_Loc\')"><option value="">Select</option></select></td>'+	
						'<td ><b>To Region <font color = "Red">*</font> :</b></td>'+
						'<td><select id="locationForIntraUnitTransferRequest" name="id_loc_tran"  style="width:140" onChange="DisplaySubDropDownData(this,\'subLocationForIntraUnitTransferRequest\',\'M_Subloc\')"><option value="">Select</option></select></td>'+	
						'</tr>'+
					
					'<tr class="HideLocAndSubLocForIUT"><td ><b>To State <font color = "Red">*</font> :</b></td>'+
					'<td colspan="2"><select id="subLocationForIntraUnitTransferRequest" name="id_subl_tran"  style="width:140" onChange="SubDropDownDataDisplay(this,\'buildingForIntraUnitTransferRequest\',\'M_Building\')"><option value="">Select</option></select></td>'+	
					'<td><b>To City <font color = "Red">*</font> :</b></td><td colspan="2"><select id="buildingForIntraUnitTransferRequest" name="id_building_tran"  style="width:140" onChange="SubDropDownDataDisplay(this,\'flrForIntraUnitTransferRequest\',\'M_Floor\')"><option value="">Select</option></select></td>'+	
				'</tr>'+
				'<tr class="HideLocAndSubLocForIUT"><td ><b>To Store <font color = "Red">*</font> :</b></td>'+
				'<td colspan="2"><select id="flrForIntraUnitTransferRequest" name="id_flr_tran"  style="width:140"><option value="">Select</option> </select></td>'+	
				'<td></td><td colspan="2"></td>'+	
			'</tr>'+*/	
				
					//'<tr><td colspan="5"><button type="button" style="margin-left: 400px;"  class="btn btn-primary requestAssetBtn" onclick="checkBoxValidationForInterTransfer(\'submitTransferRequest\')">Send Request</button>'+
					'<input type="hidden" name="ids" value=""><input type="hidden" name="action" value="Save">'+
					
					'<input type="hidden" name="type_tran" value="Non Returnable">'+
					
					'<input type="hidden" name="id_ven" value="0" id="locDataForInterUTR"><input type="hidden" name="loc" value="'+id_loc+'" id="locDataForInterUTR"><input type="hidden" id="subLocDataForInterUTR" name="sloc" value="'+id_sloc+'">'+
					'</td></tr>';
					
						$('.DisplayAssetForIntraUnitTransferRequesting').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available..</strong></center></td></tr>';
						$('.DisplayAssetForIntraUnitTransferRequesting').html(list);
					}
				DisplayDropDownData("M_Emp_User","UserDataForBulkInstallAsset",function (status){
						if(status)
						{
							DropDownDataDisplay("M_Dept","divDataForBulkInstall",function (status){
								if(status)
									{
									
									}});
							DropDownDataDisplay("M_Loc","LocDataForBulkInstallAsset",function (status){
								if(status)
								{
									DropDownDataDisplay("M_Dept","DeptForBulkInstallAsset",function (status){
								if(status)
								{
									/*DropDownDataDisplay("M_Cost_Center","CostCenterForBulkInstallAsset",function (status){
										if(status)
										{
											
											
										}});*/
										}});
								}});
						}});
				/*DisplayDropDownDataForVendor("M_Vendor","vendorForIntraUnitTransferRequest",'service',function (status){
					if(status)
					{
						DisplayDropDownData("M_Loc","locationForIntraUnitTransferRequest",function (status){
							if(status)
							{
								
							}});
						
					}});
				*/
			},'json');
			}});

}

function ShowRowColumnForIUT(action)
{
	
	if(action.value == 'Non Returnable')
		{
		$('.vendorHideShowForIUT').hide();
		$('.HideLocAndSubLocForIUT').show();
		$('.rowCol').show();
		
		}
	else if(action.value == 'Returnable')
		{
		
		$('.rowCol').hide();
		$('.HideLocAndSubLocForIUT').hide();
		$('.vendorHideShowForIUT').show();
		}
	else
		{
		$('.rowCol').show();
		$('.vendorHideShowForIUT').hide();
		$('.HideLocAndSubLocForIUT').hide();
		}
	
}

function AddInputValueForTransferRequestForInter(id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	//DisplayDropDownData("M_Floor","floorForIntraUnitTransferRequest");
	//DropDownDataDisplay("M_Cost_Center","costCenterForIntraUnitTransferRequest");
			DisplayDropDownData("M_Loc","locationForIntraUnitTransferRequest",function (status){
				if(status)
				{
					
				
	$.post('T_Inter_Transfer_Request',{action : 'Edit', id : id},function (r)
			{
		
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						
							for (var key in r.data[i])
								{
								
										$('input[name='+key+']').val(r.data[i][key]);
									
								}
						
						$('input[name="id"]').val(id);	
						
					}
					else
						{
							bootbox.alert("Try again.");
						}
				
				
			},'json');
	
	
				}});
	
		}});
			
	}



