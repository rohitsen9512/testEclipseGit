//List View Display For De-Allocation
function DisplayAssetForUnInstall(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	var id_s_assetdiv = $('select[name="id_s_assetdiv"]').val();
	$.post(servletName,{action : 'Search' , DisplayType : 'UnInstall' ,searchWord : searchWord,id_s_assetdiv : id_s_assetdiv } , function (r){
					
					var list ='<tr class="new">'+
						'<td colspan="9"><button type="button" style="margin-left: 470px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation1(\'uninstallAsset\')">De-Allocate</button>'+
						'</td></tr>';
					 list= list +'<tr class="new">'+
					'<td><center><strong>Asset ID</strong></center></td>'+
					'<td><center><strong>Serial No.</strong></center></td>'+
					'<td><center><strong>Asset Name</strong></center></td>'+
					'<td><center><strong>Employee Name</strong></center></td>'+
					'<td><center><strong>Employee Code</strong></center></td>'+
					'<td><center><strong>Allocated Date</strong></center></td>'+
					'<td><center><strong>Asset Remarks</strong></center></td>'+
					'<td><center><strong>De-Allocate Date<font color="red">*</font></strong></center></td>'+
					'<td><center><strong>Asset Status<font color="red">*</font></strong></center></td>'+
					
					'<td style="width: 80px;"><center><strong>Check/Uncheck</strong></center></td>'+
				'</tr>';
					
					 if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.rmk_asst == '') rmk_asst = '-'; else rmk_asst = params.rmk_asst;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+params.nm_emp+'</center></td>'+
						'<td><center>'+params.cd_emp+'</center></td>'+
						'<td><center>'+params.dtAlloc+'</center></td>'+
						'<td><center>'+
						'<input type="hidden" name="asst_stat'+params.id_wh+'" id="asst_stat" style="width:200px" class=" resetAcc" data-valid="mand" value="0">'+
						'<input type="text" name="uninstallRmk'+params.id_wh+'" value="'+rmk_asst+'" style="width: 150px;"></center></td>'+
						'<td><center><input type="text" name="uninstallAssetDate'+params.id_wh+'" patel="'+params.id_wh+'" value="" class=" datepicker" data-valid="mand" style="width: 100px;"/></center></td>'+
						'<td><select style="width: 180px;" name = "asset_status'+params.id_wh+'" id="returndate'+i+'" class=""    data-valid="mand" >'+
								
								'<option value = "working" >Received in Working</option>'+
								
								'<option value="physical_dmg_mjr">Received in Physical Damage (Major)</option>'+
								'<option value="physical_dmg_mnr">Received in Physical Damage (Minor) </option>	'+
								'</select>'+
					'</td>'+
					
						'<td><center><strong><input type="checkbox" name="uninstallAsset" class="uninstallAssetForSelectAll deallocateAsset" value="'+params.id_wh+'"/></strong></center></td>'+
						'</tr>';
						}
						
						
					
						$('.DisplayAssetForUnInstallation').html(list);
					
					}
					 else 
						{
							list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
							$('.DisplayAssetForUnInstallation').html(list);
						}
					$('.datepicker').datepicker({
						yearRange: '1985:2025', 
						changeMonth: true,
					      changeYear: true,
					      dateFormat: "dd/mm/yy",
					      autoSize: true,
					      altFormat: "dd/mm/yy",
					      onSelect: function(selected,evnt) {
					    	
					    	  var id_wh = $(this).attr('patel');
					    	  var name = $(this).attr('name');
					    	  var dt_alloc = $(this).val();
					    	$('.datepicker').removeClass('error');
					    $.post('A_Install',{action : 'CheckUnInstallDate' , dt_alloc : dt_alloc ,id : id_wh},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('De-allocated date should be greater or equal to Allocated Date '+r.dt_alloc);
					    			$('input[name="'+name+'"]').focus();
					    			$('input[name="'+name+'"]').val('');
					    			$('input[name="'+name+'"]').addClass('error');
					    				exit(0);
					    			}
					    		
					    },'json');
					      }
					});	
					
					
				
			},'json');
		
			}});
}

//Checkbox Validatiojn for Allocate
function checkBoxValidation(FormName)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
	if(FormName == "bulkinstallAsset")
		{
			$('input[type="checkbox"]').each(function(){
				
				
				$('input[name="dt_allocate'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						val = $('input[name="dt_allocate'+$(this).val()+'"]').val();
						if(val == '')
							{
								bootbox.alert("Please fill the date field.");
								$('input[name="dt_allocate'+$(this).val()+'"]').addClass('error');
								$('input[name="dt_allocate'+$(this).val()+'"]').focus();
								exit(0);
							}
						
						$('.CommonForBulk').each(function(){
							
							$(this).removeClass('error');
							
							if($(this).val() == '')
								{
									t=0;
									bootbox.alert("Mandatory field.");
									$(this).addClass('error');
									$(this).focus();
									exit(0);
								}
							else
								{
									t=1;
								}
						});
						
						
						
					}
				
			});
		}
	else
		{
			$('input[type="checkbox"]').each(function(){
				
				
				$('input[name="uninstallAssetDate'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						val = $('input[name="uninstallAssetDate'+$(this).val()+'"]').val();
						if(val == '')
							{
								bootbox.alert("Please fill the date field.");
								$('input[name="uninstallAssetDate'+$(this).val()+'"]').addClass('error');
								$('input[name="uninstallAssetDate'+$(this).val()+'"]').focus();
								exit(0);
							}
					
						t=1;
						
					}
				
			});
		}
	if(t == 0)
	{
		bootbox.alert("Please select at least one asset.");
	}
	else
		{
		$('.installUnInstall').attr('disabled','disabled');
		if(FormName == "bulkinstallAsset")
			{
		$('input[name="action"]').val('BulkInstallCheckDate');
		var x = $('#'+FormName).serialize();
		
		$.post('A_Install',x,function (r)
				{
			if(r.data == 0)
			{
			
			alert('Bulk Allocation Date should be greater or equal to '+r.dt_grn);
			$('.installUnInstall').removeAttr('disabled');
				$('#BulkInstallId').focus();
				$('#BulkInstallId').val('');
				$('#BulkInstallId').addClass('error');
				exit(0);
			}
			else
				{
				$('input[name="action"]').val('BulkInstall');
				var x = $('#'+FormName).serialize();
				$.post('A_Install',x,function (r)
						{
							
							if(r.data ==1)
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("De-allocated successfully.");
									DisplayAssetForUnInstall('A_Install');								
								}
							else if(r.data == 2)
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("Allocated successfully.");
								DisplayAssetForBulkInstall('A_Install');	
								}
								else
									{
										$('.installUnInstall').removeAttr('disabled');
										bootbox.alert("Try again.");
									}
										
							$('.installUnInstall').removeAttr('disabled');
						},'json');
				
				
				
				}
			
				},'json');
		}else
			{
			
			var x = $('#'+FormName).serialize();
			
			$.post('A_Install',x,function (r)
					{
				
						if(r.data ==1)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("De-allocated successfully.");
								DisplayAssetForUnInstall('A_Install');								
							}
						else if(r.data == 2)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("Allocated successfully.");
								DisplayAssetForBulkInstall('A_Install');		
							}
							else
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("Try again.");
								}
									
						
					},'json');
			
			}
		
		
			
		}
			}});
}

//List View Display For De-Allocation
function DisplayAssetForBulkInstall(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
			var id_s_assetdiv = $('select[name="id_s_assetdiv"]').val();
	$.post('A_Install',{action : 'Search' , DisplayType : 'Install' ,searchWord : searchWord, id_s_assetdiv : id_s_assetdiv} , function (r){
		
					var list="";
					 if(r.data.length > 0)
					{
						
						list = list + '<tr class="new"  >'+
										'<td colspan="2">'+
											'<strong>Assign To<font color="red">*</font></strong>'+
											'<select id="UserDataForBulkInstallAsset" class="CommonForBulk form-control" name="to_assign" style="width: 150px;" onchange="getDATAFromEMPuser(this)">'+
											
												'<option value="">Select</option>'+
											'</select>'+
											'</td>'+
											
											'<td colspan="2">'+
											'<strong>Location<font color="red">*</font></strong>&nbsp;&nbsp;'+
											'<select id="LocDataForBulkInstallAsset" class="CommonForBulk form-control" name="id_loc" style="width: 150px;" onChange="DisplaySubDropDownData(this,\'SubLocDataForBulkInstallAsset\',\'M_Subloc\')">'+
												'<option value="">Select</option>'+
											'</select>'+
										'</td>'+
										'<td colspan="2">'+
											'<strong>Sub Location<font color="red">*</font></strong>&nbsp;&nbsp;'+
											'<select id="SubLocDataForBulkInstallAsset" class="CommonForBulk form-control" name="id_subl" style="width: 150px;" onChange="SubDropDownDataDisplay(this,\'buildingDataForBulkInstallAsset\',\'M_Building\')">'+
												'<option value="">Select</option>'+
											'</select>'+
										'</td>'+
											
											'</tr>'+
											
											'<tr class="success new">'+
											
											'<td colspan="2">'+
											'<strong>Building<font color="red">*</font></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
											'<select id="buildingDataForBulkInstallAsset" class="CommonForBulk form-control" name="id_building" style="width: 150px;" onChange="SubDropDownDataDisplay(this,\'FloorForBulkInstallAsset\',\'M_Floor\')">'+
												'<option value="">Select</option>'+
											'</select>'+
										'</td>'+
									
									
									
									'<td colspan="2">'+
										'<strong>Floor<font color="red">*</font></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
										'<select id="FloorForBulkInstallAsset" class="CommonForBulk form-control" name="id_flr" style="width: 150px;">'+
											'<option value="">Select</option>'+
										'</select>'+
									'</td>'+
									'</td><td colspan="2">'+
									'<strong>Allocate Date<font color="red">*</font></strong>&nbsp;&nbsp;'+
									'<input type="text" value="" id="BulkInstallId" class="common-validation datepicker CommonForBulk form-control" data-valid="mand" name="dt_allocate" style="width: 150px;"></td>'+
									'<input type="hideen" name="id_cc" value="0">'+
									'</tr>'+
									'<tr  class="success new">'+
											
											'<td colspan="2">'+
											'<strong>Reporting Manager<font color="red">*</font></strong>&nbsp;&nbsp;'+
											'<select id="repoManagerDataForBulkInstall" class="CommonForBulk form-control" name="repo_mngr" style="width: 150px;" >'+
												'<option value="">Select</option>'+
											'</select>'+
										'</td>'+
										'<td colspan="2">'+
										'<strong>Department<font color="red">*</font></strong>&nbsp;&nbsp;'+
											'<select id="DeptDataForBulkInstall" class="CommonForBulk form-control" name="id_dept" style="width: 150px;" >'+
												'<option value="">Select</option>'+
											'</select>'+
										
									'</td>'+
									'</td><td colspan="2">'+
									'</td>'+
										'</tr>'+
									'<tr class="success">'+
						'<td colspan="6"><button type="button" style="margin-left: 450px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation(\'bulkinstallAsset\')">Allocate</button>'+
						'</td></tr>';
						
						
						list= list+ '<tr class="tableHeader info new">'+
					'<td><strong><center>Asset ID</center></strong></td>'+
					'<td><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					'<td><strong><center>Asset Remarks</center></strong></td>'+
					'<td><strong><center>Allocation Type</center></strong></td>'+
					'<td style="width: 125px;"><center><strong> Check/ Uncheck </strong></center></td>'+
				'</tr>';
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.rmk_asst == '') rmk_asst = '-'; else rmk_asst = params.rmk_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center><input type="text" name="installRmk'+params.id_wh+'" value="'+rmk_asst+'" style="width: 150px;"></center></td>'+
						'<td>'+
						'<select  name = "device_status'+params.id_wh+'" class="common-validation"   data-valid="mand" >'+
								
								'<option value = "allct_to_emp" >Permanent</option>'+
								'<option value = "allct_to_emp_temp" >Temporary</option>'+
								
					
						'</select>'+
					'</td>'+
						'<td><strong><center><input type="checkbox" name="bulkInstallAsset" class="bulkinstallAssetForSelectAll" value="'+params.id_wh+'"/></center></strong></td>'+
						'</tr>';
						}
						
						
					}
					 else
						 list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
					 $('.DisplayAssetForBulkInstallation').html(list);
					 $("#UserDataForBulkInstallAsset").select2();
					DisplayDropDownData("M_Emp_User","UserDataForBulkInstallAsset",function (status){
						if(status)
						{
							DisplayDropDownData("M_BU","divDataForBulkInstall",function (status){
								if(status)
									{
									
									}});
							DropDownDataDisplay("M_Loc","LocDataForBulkInstallAsset",function (status){
								if(status)
								{
									DisplayDropDownData("M_Emp_User","repoManagerDataForBulkInstall",function (status){
								if(status)
								{
									DropDownDataDisplay("M_Dept","DeptDataForBulkInstall",function (status){
										if(status)
										{
											
											
										}});
								}});
								}});
						}});
					jQuery("input#bfileID").change(function () {
						var formData = new FormData();
					    formData.append('file', $('#bfileID').get(0).files[0]);
						$.ajax({
						  url: 'Upload_File',
						    type: 'POST',
						    data: formData,
						    async: false,
						    cache: false,
						    contentType: false,
						    dataType: "json",
						    processData: false,
						    success: function (r) {
						    	
						    		$('input[name="upload_asset"]').val(r.upload_inv);
						    		}
						},'json');
					});
					$( ".datepicker" ).datepicker({
					      changeMonth: true,
					      changeYear: true,
					      dateFormat: "dd/mm/yy",
					      autoSize: true,
					      altFormat: "dd/mm/yy",
					    });
			},'json');
		
			}});
}


//Checkbox Validatiojn for Deallocate
function checkBoxValidation1(FormName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
	
			$('.deallocateAsset').each(function(){
				
				
				$('input[name="uninstallAssetDate'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						
					var q=$("select[name=asst_stat"+$(this).val()+"]").val();
				
					if(q==""){
						
						bootbox.alert("Please fill the asset status.");
						$('select[name="asst_stat'+$(this).val()+'"]').addClass('error');
						$('select[name="asst_stat'+$(this).val()+'"]').focus();
						exit(0);
					}
						val = $('input[name="uninstallAssetDate'+$(this).val()+'"]').val();
						if(val == '')
							{
							
							
								
								bootbox.alert("Please fill the date field.");
								$('input[name="uninstallAssetDate'+$(this).val()+'"]').addClass('error');
								$('input[name="uninstallAssetDate'+$(this).val()+'"]').focus();
								exit(0);
								
							}
						
					
						t=1;
						
					}
				
			});
		
	if(t == 0)
	{
		bootbox.alert("Please select at least one asset.");
	}
	else
		{
		
		$('input[name="action"]').val('Update');
		var x = $('#'+FormName).serialize();
		$('.installUnInstall').attr('disabled','disabled');
			$.post('A_Install',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("De-allocated successfully.");
									 changeSubContent('Assets/Uninstall_asset.jsp','uninstallasset');
														
							}
						
							else
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("Try again.");
								}
									
						
					},'json');
			
			
		
		
			
		}
			}});
}
