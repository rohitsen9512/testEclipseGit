
function ControlInstallAsset(action , DisplayDiv , HideDiv , HideDiv2 , id , id_loc , id_flr)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == "Edit")
		{
		
		if(id_flr)
		{
		DisplaySubDropDownData(id_loc,'subLocationForInstallEdit','M_Subloc',function (status){
			if(status)
			{
			DisplaySubDropDownData(id_flr,'floorForInstallEdit','M_Floor',function (status){
				if(status)
				{
					AddInputValueForInstallation(id);
				}});
			}});
		
		}
		
		if(id_flr == undefined && id_loc)
		{
			AddInputValueForInstallation(id);
				/*DisplaySubDropDownData(id_loc,'subLocationForInstall','M_Subloc',function (status){
					if(status)
					{
						
					}});*/
		}
			
			$('#'+DisplayDiv).hide();
			$('#'+HideDiv).hide();
			$('#'+HideDiv2).show();
			
			
			
		}
	else if(action == "Save")
		{
			
		var t = false;
		t = ValidationForm("InstallToUser");
		var x = $('#InstallToUser').serialize();
		if(t)
			{
			$('.installUnInstall').attr('disabled','disabled');
				$.post('A_Link_Dlink',x,function (r)
						{
					
							if(r.data)
								{
									t=true;
								}
								else
									{
										t=false;
										bootbox.alert("Data is not inserted in data base please try again.");
									}
									
							if(t)
							{
								bootbox.alert("Installed successfully.");
								$( ".installasset" ).trigger( "click" );
							}
							$('.installUnInstall').removeAttr('disabled');
							
						},'json');
			}
		
		
	}
	else if(action =="Update1")
		{
		var t = false;
		t = ValidationForm("EditInstalledAsset");
		var x = $('#EditInstalledAsset').serialize();
		if(t)
			{
				
				$.post('A_Link_Dlink',x,function (r)
						{
					
							if(r.data)
								{
									t=true;
								}
								else
									{
										t=false;
										bootbox.alert("Data is not updated in data base please try again.");
									}
									
							if(t)
							{
								DisplayInstalledAssetForEdit("A_Link_Dlink");
								$('#'+HideDiv2).hide();
								$('#'+DisplayDiv).show();
								$('#'+HideDiv).show();
							}
							
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



function AddInputValueForInstallation(id)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	$.post('A_Link_Dlink',{action : 'Edit', id : id},function (r)
			{
		
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						
							for (var key in r.data[i])
								{
									if($('select[name='+key+']').is("select"))
									{
									
										$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									}
								else if($('textarea[name='+key+']').is("textarea"))
									{
										$('textarea[name='+key+']').val(r.data[i][key]);
									}
								else
									{
										$('input[name='+key+']').val(r.data[i][key]);
									}
								}
						
						$('input[name="id"]').val(id);	
						$('input[name="id_grn"]').val(r.data[0].id_grn);	
						$('input[name="dt_alloc"]').val(r.data[0].dtAlloc);	
						
					}
					else
						{
							bootbox.alert("Try again.");
						}
							
				var currentDate = new Date();
				$( ".datepicker" ).datepicker({
					yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				    });
				
				$("#dt_allocId").datepicker("setDate", '');
				
				DisplayDropDownData("M_Loc","locationForInstall",function (status){
					if(status)
						{
						DisplayDropDownData("M_Emp_User","userForInstall",function (status){
							if(status)
							{
								
							}});
							
						}});
				$('#subLocationForInstall').html('<option value = "" >Select</option>');
				$('#floorForInstall').html('<option value = "" >Select</option>');
				$('#costCenterForSingleInstall').val('');
			},'json');
			}});
	}

function DisplayInstalledAssetForEdit(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var id_grp="",id_sgrp="",id_loc="",id_subl="",typ_asst="";
	id_grp = $('select[name="id_grp1"]').val();
	id_sgrp = $('select[name="id_sgrp1"]').val();
	id_loc = $('select[name="id_loc1"]').val();
	id_subl = $('select[name="id_subl1"]').val();
	typ_asst = $('select[name="typ_asst1"]').val();
	
	$.post(servletName,{action : 'Display' , DisplayType : 'UnInstall' ,id_grp :id_grp,id_sgrp:id_sgrp,id_loc:id_loc,id_subl:id_subl,typ_asst:typ_asst} , function (r){
				
		var list= '<tr class="tableHeader info">'+
		'<td><strong>Ware House ID</strong></td>'+
		'<td><strong>Asset Name</strong></td>'+
		'<td><strong>Model Number</strong></td>'+
		'<td><strong>Asset Description</strong></td>'+
		'<td style="width: 105px;"><strong><a href="#">Modify</a></strong></td>'+
	'</tr>';
		
	if(r.data.length > 0)
		{
			
			
			for(var i = 0; i < r.data.length ; i++)
			{
			
			params = r.data[i];
			if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
			if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
			if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
			
			list = list + '<tr class="success">'+
			'<td><center>'+params.id_wh_dyn+'</center></td>'+
			'<td><center>'+ds_pro+'</center></td>'+
			'<td><center>'+no_model+'</center></td>'+
			'<td><center>'+ds_asst+'</center></td>'+
			'<td><strong><a class="alert" href="#" onclick="ControlInstallAsset(\'Edit\',\'EditSearch\',\'DisplayInstalledAssetDiv\',\'EditDetailsForInstalledAsset\','+params.id_wh+','+params.id_loc+','+params.id_subl+')"> Modify </a></strong></td>'+
			'</tr>';
			}
		
		
			$('.displayInstalledAssetForEdit').html(list);
		
		}
	
	else
		{
			list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
			$('.displayInstalledAssetForEdit').html(list);
		}
		
	
},'json');
			}});
}


function DisplayAssetForInstall(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post(servletName,{action : 'Search' , DisplayType : 'Install' ,searchWord:searchWord} , function (r){
				
					var list= '<tr class="tableHeader info">'+
					'<td><strong><center>Asset ID</center></strong></td>'+
					//'<td><strong><center>SAP No</center></strong></td>'+
					/*'<td><strong><center>Model No</center></strong></td>'+*/
					'<td><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					//'<td><strong><center>Department</center></strong></td>'+
					
					'<td style="width: 120px;"><center><strong><a href="#">Allocate It </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						//if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						if(params.nm_dept == '') nm_dept = '-'; else nm_dept = params.nm_dept;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
					//	'<td><center>'+params.serial_no+'</center></td>'+
						/*'<td><center>'+params.nm_model+'</center></td>'+*/
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						//'<td><center>'+params.nm_dept+'</center></td>'+
						
						
						'<td><strong><a class="alert" href="#" onclick="ControlInstallAsset(\'Edit\',\'displayAssetForInstallSearch\',\'DisplayAssetForInstall\',\'InstallDetails\','+params.id_wh+','+params.id_subl+')"> Allocate It </a></strong></td>'+
						'</tr>';
						}
					
					
						$('.DisplayAssetForInstallation').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.DisplayAssetForInstallation').html(list);
					}
					
				
			},'json');
		
			}});
}




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
				$('input[name="action"]').val('link_accessories');
				var x = $('#'+FormName).serialize();
				$.post('A_Link_Dlink',x,function (r)
						{
							
							if(r.data == 1)
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("Linked successfully.");
									DisplayAssetForBulkInstall('A_Link_Dlink');								
								}
							else if(r.data == 2)
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("Installed successfully.");
									DisplayAssetForBulkInstall('A_Link_Dlink');		
								}
								else
									{
										$('.installUnInstall').removeAttr('disabled');
										bootbox.alert("Try again.");
									}
										
							$('.installUnInstall').removeAttr('disabled');
						},'json');
				
				
				
				
			
				
		}else
			{
			
			var x = $('#'+FormName).serialize();
			
			$.post('A_Link_Dlink',x,function (r)
					{
				
						if(r.data ==1)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("Uninstalled successfully.");
								DisplayAssetForUnInstall('A_Link_Dlink');								
							}
						else if(r.data == 2)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("Installed successfully.");
								DisplayAssetForBulkInstall('A_Link_Dlink');		
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

function DisplayAssetForBulkInstall(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
			var id_s_assetdiv = $('select[name="id_s_assetdiv"]').val();
	$.post('A_Link_Dlink',{action : 'link_down',searchWord : searchWord} , function (r){
		
					var list="";
					 if(r.data.length > 0)
					{
						
						list = list + '<tr class="new">'+
										'<td colspan="1">'+
											'<strong>Link To<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>&nbsp;&nbsp;</td>'+
											'<td colspan="1"><select id="UserDataForBulkInstallAsset" class="form-control CommonForBulk" name="to_assign" style="width: 150px;" onchange="value_changed()">'+
												'<option value="">Select</option>'+
											'</select>'+
											'</td>'+
											
											
											'<td colspan="2">'+
										'<strong>Asset ID<font color="red">*</font></strong>&nbsp;&nbsp;'+
									'<input id="cd_employeeId" type="text" name="cd_emp"  class="form-control" style="display: inline;   width: 190px;" data-valid="mand" readonly>'+
									'</td>'+
										'<td colspan="1">'+
									'<strong>Link Date<font color="red">*</font></strong>&nbsp;&nbsp;</td>'+
									'<td colspan="2"><input type="text" value="" id="BulkInstallId" class="form-control datepicker CommonForBulk" data-valid="mand" name="dt_allocate" style="width: 150px;"></td>'+
											
											'</tr>'+
											
											/*'<tr class="success">'+
											'<td colspan="2">'+
											'<strong>Sub Location<font color="red"> * </font>:</strong>&nbsp;&nbsp;</td>'+
											'<td colspan="2"><input id="SubLocDataForBulkInstallAsset" class="CommonForBulk" name="id_subl" style="width: 150px;" readonly>'+
												
										'</td>'+
											'<td colspan="2">'+
											'<strong>Building<font color="red"> * </font>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>&nbsp;&nbsp;</td>'+
											'<td colspan="2"><input id="buildingDataForBulkInstallAsset" class="CommonForBulk" name="id_building" style="width: 150px;" readonly>'+
												
										'</td>'+
										'</tr><tr class="success">'+
									
									
									
									'<td colspan="2">'+
										'<strong>Floor<font color="red"> * </font>:</strong>&nbsp;&nbsp;</td>'+
										'<td colspan="2"><input id="FloorForBulkInstallAsset" class="CommonForBulk" name="id_flr" style="width: 150px;" readonly>'+
											
									'</td><td colspan="2">'+
									'<strong>Link Date<font color="red"> * </font>:</strong>&nbsp;&nbsp;</td>'+
									'<td colspan="2"><input type="text" value="" id="BulkInstallId" class="common-validation datepicker CommonForBulk" data-valid="mand" name="dt_allocate" style="width: 150px;"></td>'+
									'<input type="hideen" name="id_cc" value="0">'+
									/*'<td colspan="2">'+
										'<strong>Cost Center<font color="red"> * </font>:</strong>&nbsp;&nbsp;'+
									'<select id="CostCenterForBulkInstallAsset" class="CommonForBulk" name="id_cc" style="width: 150px;">'+
										'<option value="">Select</option>'+
										'</select>'+
									*/
									/*'</tr>'+
									'<tr class="success">'+
									/*'<input type="hidden" name="id_grn" value="'+id_grn+'">'+*/
									/*'<td colspan="8">'+
										'<strong>Awarehouse Id<font color="red"> * </font>:</strong>&nbsp;&nbsp;'+
									'<input id="cd_employeeId" type="text" name="cd_emp"  class="common-validation" data-valid="mand" readonly>'+
									'</td>'+*/
									
									/*'<center><td colspan="7"><strong>Upload Configuration Certificate:</strong>'+
									'<input id="bfileID" type="file" name="file" class="common-validation " class="CommonForBulk" value="" data-valid="mand"></td>'+
									*/
									/*'</tr>'+*/
						'<tr class="new">'+
						'<td colspan="12"><button type="button" style="margin-left: 450px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation(\'bulkinstallAsset\')">LINK</button>'+
						'</td></tr>';
						
						list= list+ '<tr class="new ">'+
					'<td colspan="2"><strong><center>Accessories ID</center></strong></td>'+
					/*'<td><strong><center>Model No</center></strong></td>'+*/
					'<td><strong><center>Accessories Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					//'<td><strong><center>Department</center></strong></td>'+
					'<td><strong><center>Accessories Remarks<font color="red"> * </font></center></strong></td>'+
					/*'<td><strong><center>Allocation Type</center></strong></td>'+*/
					'<td style="display:none" id="returndate"><strong><center>Return Date<a href=#></a></center></strong></td>'+
					'<td style="width: 125px;"><center><strong> Check / Uncheck </strong></center></td>'+
				'</tr>';
						/*id_grn = r.data[0].id_grn;*/
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.rmk_asst == '') rmk_asst = '-'; else rmk_asst = params.rmk_asst;
						
						list = list + '<tr class="success">'+
						'<td colspan="2"><center>'+params.id_wh_dyn+'</center></td>'+
						/*'<td><center>'+params.no_model+'</center></td>'+*/
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						//'<td><center>'+params.nm_dept+'</center></td>'+
						'<td><center><input type="text" name="installRmk'+params.id_wh+'" value="'+rmk_asst+'" style="width: 150px;"></center></td>'+
					/*	'<td>'+
						'<select  name = "device_status'+params.id_wh+'" id="returndate'+i+'" class="common-validation" onchange = "ShowHideDiv('+i+')"   data-valid="mand" >'+
								
								'<option value = "allct_to_emp" >Permanent</option>'+
								'<option value = "allct_to_emp_temp" >Temporary</option>'+
								'<option value = "allct_to_emp_stock" >Stock</option>'+
								'<option value = "allct_to_emp_faulty" >Faulty</option>'+
								'<option value = "allct_to_emp_scrap" >Scrap needed</option>'+
					
						'</select>'+
					'</td>'+*/
					'<td style="display:none" id="returndate1'+i+'"><center><input type="text" name="dt_return'+i+'"  value="" patelUnPrc="'+i+'"  class="form-control datepicker"  style="width: 120px;"  ></center></td>'+
						'<td><strong><center><input type="checkbox" name="bulkInstallAsset" class="bulkinstallAssetForSelectAll" value="'+params.id_wh+'"/></center></strong></td>'+
						'</tr>';
						}
						
						
					}
					 else
						 list +='<tr rowspan="2"><td colspan="12"><center><strong>No record(s) is available..</strong></center></td></tr>';
					 $('.DisplayAssetForBulkInstallation').html(list);
				 $("#UserDataForBulkInstallAsset").select2();
					 
					DisplayDropDownData("A_Link_Dlink","UserDataForBulkInstallAsset",function (status){
						if(status)
						{
							
							
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
						    		//bootbox.alert("File Uploaded successfully");
						    		
						    	
						    }
						},'json');
					});
					//DisplayDropDownData("M_Floor","FloorForBulkInstallAsset");
					//DropDownDataDisplay("M_Cost_Center","CostCenterForBulkInstallAsset");
					/*$('.datepicker1').datepicker({
					      changeMonth: true,
					      changeYear: true,
					      dateFormat: "yy-mm-dd",
					      autoSize: true,
					      onSelect: function(selected,evnt) {
					    	
					    	  var dt_grn = $('#BulkInstallId').val();
						    	 
						    	var id_grn = $('input[name="id_grn"]').val();
					    	$('#BulkInstallId').removeClass('error');
					    $.post('A_Link_Dlink',{action : 'CheckDate' , dt_grn : dt_grn ,id_grn : id_grn},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('Allocation Date should be greater or equal to GRN date '+r.dt_grn);
					    				$('#BulkInstallId').focus();
					    				$('#BulkInstallId').val('');
					    				$('#BulkInstallId').addClass('error');
					    				exit(0);
					    			}
					    		
					    },'json');
					      }
					});	*/
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

function ShowHideDiv(i) {
	
	var showdate=$('select[id=returndate'+i+']').val();
	console.log(showdate);
	if(showdate == "allct_to_emp_temp" || showdate == "allct_to_emp_adv")
	{
	$('#returndate').show();
	$('#returndate1'+i+'').show();
	}
	else if(showdate == "allct_to_emp")
	{
	$('#returndate').hide();
	$('#returndate1'+i+'').hide();
	}
	else{
		
	}
	
        /*var warrenty = document.getElementById("warrenty");
        var showwarrenty = document.getElementById("showwarrenty");
        showwarrenty.style.display = warrenty.value == "Y" ? "block" : "none";*/
    }





function DisplayAssetForConfigure(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post(servletName,{action : 'Search' , DisplayType : 'UnInstall' ,searchWord : searchWord} , function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Asset ID</strong></td>'+
					'<td><strong>Model No</strong></td>'+
					'<td><strong>Asset Name</strong></td>'+
					'<td><strong>Asset Description</strong></td>'+
					'<td><strong>Asset Remarks</strong></td>'+
					'<td><strong>Notify IT</strong></td>'+
					//'<td><strong>Un Install Date<font color="red"> * </font></strong></td>'+
					//'<td style="width: 125px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'uninstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'uninstallAssetForSelectAll\')"> Uncheck </a></strong></center></td>'+
				'</tr>';
					
					 if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						if(params.rmk_asst == '') rmk_asst = '-'; else rmk_asst = params.rmk_asst;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+no_model+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						'<td><input type="text" name="uninstallRmk'+params.id_wh+'" value="'+rmk_asst+'" style="width: 150px;"></td>'+
						'<td><strong><a class="alert" href="#"> Notify </a></strong></td>'+
						
						//'<td><input type="text" name="uninstallAssetDate'+params.id_wh+'" patel="'+params.id_wh+'" value="" class="common-validation datepicker" data-valid="mand" style="width: 150px;"/></td>'+
					//	'<td><strong><input type="checkbox" name="uninstallAsset" class="uninstallAssetForSelectAll" value="'+params.id_wh+'"/></strong></td>'+
						'</tr>';
						}
						
					//	list = list + '<tr class="success">'+
						//'<td colspan="7"><button type="button" style="margin-left: 400px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation(\'uninstallAsset\')">Un Install</button>'+
					//	'</td></tr>';
					
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
					    	
					    	//var id_wh = $('input[name="id_wh"]').val();
					    	$('.datepicker').removeClass('error');
					    $.post('A_Link_Dlink',{action : 'CheckUnInstallDate' , dt_alloc : dt_alloc ,id : id_wh},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('De-Allocate Date should be greater or equal to Allocated / Transfer date '+r.dt_alloc);
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

function DisplayAssetForConfigureIT(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post(servletName,{action : 'Search' , DisplayType : 'UnInstall' ,searchWord : searchWord} , function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Asset ID</strong></td>'+
					'<td><strong>Model No</strong></td>'+
					'<td><strong>Asset Name</strong></td>'+
					'<td><strong>Asset Description</strong></td>'+
					'<td><strong>Asset Remarks</strong></td>'+
					'<td><strong>Configuration IT</strong></td>'+
					//'<td><strong>Un Install Date<font color="red"> * </font></strong></td>'+
					//'<td style="width: 125px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'uninstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'uninstallAssetForSelectAll\')"> Uncheck </a></strong></center></td>'+
				'</tr>';
					
					 if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						if(params.rmk_asst == '') rmk_asst = '-'; else rmk_asst = params.rmk_asst;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+no_model+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						'<td><input type="text" name="uninstallRmk'+params.id_wh+'" value="'+rmk_asst+'" style="width: 150px;"></td>'+
						'<td><strong><a class="alert" href="#" onclick="ControlCofigIT(\'Edit\',\'EditDetailsForEditFromStore\',\'SearchFromForEditFromstore\',\'DisplayEditFromStore\','+params.id_wh+','+params.id_loc+')"> Modify </a></strong></td>'+
						
						
						//'<td><input type="text" name="uninstallAssetDate'+params.id_wh+'" patel="'+params.id_wh+'" value="" class="common-validation datepicker" data-valid="mand" style="width: 150px;"/></td>'+
					//	'<td><strong><input type="checkbox" name="uninstallAsset" class="uninstallAssetForSelectAll" value="'+params.id_wh+'"/></strong></td>'+
						'</tr>';
						}
						
					//	list = list + '<tr class="success">'+
						//'<td colspan="7"><button type="button" style="margin-left: 400px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation(\'uninstallAsset\')">Un Install</button>'+
					//	'</td></tr>';
					
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
					    	
					    	//var id_wh = $('input[name="id_wh"]').val();
					    	$('.datepicker').removeClass('error');
					    $.post('A_Link_Dlink',{action : 'CheckUnInstallDate' , dt_alloc : dt_alloc ,id : id_wh},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('De-allocate date should be greater or equal to Installed / Transfer date '+r.dt_alloc);
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


function DisplayAssetForConfigureForIT(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post(servletName,{action : 'Display' , DisplayType : 'Install' ,searchWord:searchWord} , function (r){
				
					var list= '<tr class="tableHeader info">'+
					//'<td><strong><center>Asset Tag</center></strong></td>'+
					'<td><strong><center>Remark</center></strong></td>'+
					'<td><strong><center>Model No</center></strong></td>'+
					'<td><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					'<td><strong><center>Asset Description</center></strong></td>'+
					//'<td><strong><center>Storage Name</center></strong></td>'+
					'<td style="width: 120px;"><strong><center><a href="#">Notify </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						//if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						if(params.nm_flr == '') nm_flr = '-'; else nm_flr = params.nm_flr;
						
						list = list + '<tr class="success">'+
						//'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.rmk_asst+'</center></td>'+
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						//'<td><center>'+nm_flr+'</center></td>'+
						
						'<td><strong><center><button class="btn alert notify" style="margin: -5px;" onclick="ControlConfigForITAsset(\'Notify\',\'displayAssetForInstallSearch\',\'DisplayAssetForInstall\',\'InstallDetails\','+params.id_wh+')"> Notify </button></center></strong></td>'+
						'</tr>';
						}
					
					
						$('.DisplayAssetForInstallation1').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.DisplayAssetForInstallation1').html(list);
					}
					
				
			},'json');
		
			}});
}


function ControlConfigForITAsset(action , DisplayDiv , HideDiv , HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	
			bootbox.confirm("Are you sure?", function(result) {
			    if (result) {
			    	$('.notify').attr('disabled','disabled');
		
				$.post('A_Conifg_For_IT',{action:action,id:id},function (r)
						{
					
							if(r.data==1)
								{
								bootbox.alert("Asset has been notified sucessfully.");
								$( ".ConfigforIT" ).trigger( "click" );
								}
								else
									{
										t=false;
										bootbox.alert(" please try again.");
									}
									
							
							$('.notify').removeAttr('disabled');
						},'json');
			
			    }
			});
		
	
	
	
			}});
}
function checkBoxValidation1(FormName,id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
	
			$('input[type="checkbox"]').each(function(){
				
				
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
		
		$('input[name="action"]').val('Update1');
		var x = $('#'+FormName).serialize();
		$('.installUnInstall').attr('disabled','disabled');
			$.post('A_Link_Dlink',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("Dlinked successfully.");
								DisplayAssetDlink('A_Link_Dlink');								
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

function DisplayAssetForRepair(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post(servletName,{action : 'Display' ,searchWord:searchWord} , function (r){
				
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>Asset ID</strong></center></td>'+
				//	'<td><center><strong>SAP No</strong></center></td>'+
					'<td><center><strong>Model No</strong></center></td>'+
					'<td><center><strong>Asset Name</strong></center></td>'+
					'<td><center><strong>Unistalled Date</strong></center></td>'+
					'<td><center><strong>Active Date <font color ="red"> * </font></strong></center></td>'+
					//'<td><center><strong>Serial Number</strong></td>'+
					'<td><center><strong>Asset Status</strong></center></td>'+
					//'<td><center><strong>Remarks</strong></center></td>'+
					'<td style="width: 120px;"><center><strong><a href="#">Action </a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						//if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
					//	'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+params.dt_de_allocate+'</center><input type="hidden" value="'+params.dt_de_allocate+'" name="patel'+params.id_wh+'"></td>'+
						'<td><center><input type="text" name="ActiveAssetDate'+params.id_wh+'" patel="'+params.id_wh+'" value="" class="common-validation datepicker" data-valid="mand" style="width: 100px;"/></center></td>'+
						'<td><center><select name="asst_stat'+params.id_wh+'" id="asst_stat'+params.id_wh+'" style="width:100px" class="common-validation resetAcc" data-valid="mand"><option value="2">Active</option><option value="7">To be Disposed/To be Sold</option></select></center></td>'+
						'<td><center><button type="button" class="alert activeBtn" onclick="ControlRepairForITAsset(\'Active\',\'displayAssetForInstallSearch\',\'DisplayAssetForInstall\',\'InstallDetails\','+params.id_wh+')"> Save </button></center></td>'+
						'</tr>';
						}
					
					
						$('.DisplayAssetForInstallation').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.DisplayAssetForInstallation').html(list);
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
				    	  var dt_unAlloc = $('input[name="patel'+id_wh+'"]').val();
				    	  var dt_unAlloc1 = $('input[name="patel'+id_wh+'"]').val();
				    	$('.datepicker').removeClass('error');
				    	 var temp_strt = dt_alloc.split("/");
						  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
								
							var temp_end = dt_unAlloc.split("/");
							var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
								
							dt_alloc = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
							dt_unAlloc = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
				    	
				    	
				    		if(dt_unAlloc > dt_alloc)
				    			{
				    			
				    			alert('Active Date should be greater or equal to Uninstalled date '+dt_unAlloc1);
				    			$('input[name="'+name+'"]').focus();
				    			$('input[name="'+name+'"]').val('');
				    			$('input[name="'+name+'"]').addClass('error');
				    				exit(0);
				    			}
				    
				      }
				});	
				
					
				
			},'json');
		
			}});
}
function ControlRepairForITAsset(action , DisplayDiv , HideDiv , HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_active=$('input[name=ActiveAssetDate'+id+']').val();
	var asst_stat=$('#asst_stat'+id+'').val();
	
	if(dt_active=="")
	{
		
		bootbox.alert("Please fill the date field.");
		$('input[name=ActiveAssetDate'+id+']').addClass('error');
		$('input[name=ActiveAssetDate'+id+']').focus();
		exit(0);
		
	}
		$('.activeBtn').attr('disabled','disabled');
				
				$.post('A_Repair',{action:action,id:id,dt_active:dt_active,asst_stat:asst_stat},function (r)
						{
					
							if(r.data==1)
								{
									if(asst_stat == 2)
										bootbox.alert("Asset has been activated successfully.");
									else
										bootbox.alert("Asset has been send for sold.");
								$( ".underrepairasset" ).trigger( "click" );
								}
								else
									{
										t=false;
										bootbox.alert(" please try again.");
									}
									
							$('.activeBtn').removeAttr('disabled');
							
						},'json');
			}});
}


function DisplayAssetForDispose(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post(servletName,{action : 'Display' , DisplayType : 'UnInstall' ,searchWord : searchWord} , function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>Asset ID</strong></center></td>'+
					'<td><center><strong>Model No</strong></center></td>'+
					'<td><center><strong>Asset Name</strong></center></td>'+
				//	'<td><center><strong>Asset Description</strong></center></td>'+
					'<td><center><strong>Uninstalled Date</strong></center></td>'+
				//	'<td><center><strong>Asset Remarks</strong></center></td>'+
					//'<td><center><strong>Asset status</strong></center></td>'+
					'<td><center><strong>Dispose Date<font color="red"> * </font></strong></center></td>'+
					'<td style="width: 80px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'uninstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'uninstallAssetForSelectAll\')"> Uncheck </a></strong></center></td>'+
				'</tr>';
					
					 if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						if(params.rmk_asst == '') rmk_asst = '-'; else rmk_asst = params.rmk_asst;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						//'<td><center>'+ds_asst+'</center></td>'+
						'<td><center>'+params.dt_de_allocate+'</center><input type="hidden" value="'+params.dt_de_allocate+'" name="patel'+params.id_wh+'"></td>'+
					//	'<td><input type="text" name="uninstallRmk'+params.id_wh+'" value="'+rmk_asst+'" style="width: 150px;"></td>'+
					//	'<td><select name="asst_stat" id="assetstatus"  style="width:200px" class="common-validation resetAcc" data-valid="mand"><option value="">Select</option><option value="Update">Active</option><option value="Repair">Under Repair</option><option value="Sold">To be Disposed/To be Sold</option></select></td>'+
						'<td><center><input type="text" name="DisposeAssetDate'+params.id_wh+'" patel="'+params.id_wh+'" value="" class="common-validation datepicker" data-valid="mand" style="width: 100px;"/></center></td>'+
						'<td><center><strong><input type="checkbox" name="uninstallAsset" class="uninstallAssetForSelectAll" value="'+params.id_wh+'"/></strong></center></td>'+
						'</tr>';
						}
						
						list = list + '<tr class="success">'+
						'<td colspan="8"><button type="button" style="margin-left: 470px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation2(\'uninstallAsset\')">Dispose</button>'+
						'</td></tr>';
					
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
					    	
					    	//var id_wh = $('input[name="id_wh"]').val();
					    	$('.datepicker').removeClass('error');
					    	  var dt_unAlloc = $('input[name="patel'+id_wh+'"]').val();
					    	  var dt_unAlloc1 = $('input[name="patel'+id_wh+'"]').val();
						    	$('.datepicker').removeClass('error');
						    	 var temp_strt = dt_alloc.split("/");
								  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
										
									var temp_end = dt_unAlloc.split("/");
									var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
										
									dt_alloc = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
									dt_unAlloc = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
						    	
						    	
						    		if(dt_unAlloc > dt_alloc)
						    			{
						    			
						    			alert('Dispose Date should be greater or equal to Uninstalled date '+dt_unAlloc1);
						    			$('input[name="'+name+'"]').focus();
						    			$('input[name="'+name+'"]').val('');
						    			$('input[name="'+name+'"]').addClass('error');
						    				exit(0);
						    			}
					/*    $.post('A_Link_Dlink',{action : 'CheckUnInstallDate' , dt_alloc : dt_alloc ,id : id_wh},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('Dispose Date should be greater or equal to UnInstalled date '+r.dt_alloc);
					    			$('input[name="'+name+'"]').focus();
					    			$('input[name="'+name+'"]').val('');
					    			$('input[name="'+name+'"]').addClass('error');
					    				exit(0);
					    			}
					    		
					    },'json');*/
					      }
					});	
					
					
				
			},'json');
		
			}});
}

function checkBoxValidation2(FormName,id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
	
	
			$('input[type="checkbox"]').each(function(){
				
				
				$('input[name="DisposeAssetDate'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						val = $('input[name="DisposeAssetDate'+$(this).val()+'"]').val();
						
						if(val == '')
							{
								bootbox.alert("Please fill the date field.");
								$('input[name="DisposeAssetDate'+$(this).val()+'"]').addClass('error');
								$('input[name="DisposeAssetDate'+$(this).val()+'"]').focus();
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
		
		$('input[name="action"]').val('Sold');
		var x = $('#'+FormName).serialize();
				
			
		$.post('A_Dispose_Sold',x,function (r)
				{
			
					if(r.data==1)
						{
						bootbox.alert("Asset has been disposed successfully.");
						$( ".dispose" ).trigger( "click" );
						}
						else
							{
								t=false;
								bootbox.alert(" please try again.");
							}
							
					
					
				},'json');
	
			
		
		
			
		}
			}});
}

function checkBoxValidation3(FormName,id)
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
		
		$.post('T_Transfer',x,function (r)
				{
			if(r.data == 0)
			{
			
			alert('Bulk Allocation Date should be greater or equal to '+r.dt_grn);
				$('#BulkInstallId').focus();
				$('#BulkInstallId').val('');
				$('#BulkInstallId').addClass('error');
				exit(0);
			}
			else
				{
				$('input[name="action"]').val('Update1');
				$('input[name="id"]').val(id);
				var x = $('#'+FormName).serialize();
				$.post('T_Transfer',x,function (r)
						{
							
							if(r.data ==1)
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("Uninstalled successfully.");
									DisplayAssetDlink('A_Link_Dlink');								
								}
							else if(r.data == 2)
								{
									$('.installUnInstall').removeAttr('disabled');
									bootbox.alert("Installed successfully.");
									DisplayAssetDlink('A_Link_Dlink');		
								}
								else
									{
										$('.installUnInstall').removeAttr('disabled');
										bootbox.alert("Try again.");
									}
										
							
						},'json');
				
				
				
				}
			
				},'json');
		}else
			{
			
			var x = $('#'+FormName).serialize();
			
			$.post('T_Transfer',x,function (r)
					{
				
						if(r.data ==1)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("Uninstalled successfully.");
								DisplayAssetForUnInstall('A_Link_Dlink');								
							}
						else if(r.data == 2)
							{
								$('.installUnInstall').removeAttr('disabled');
								bootbox.alert("Installed successfully.");
								DisplayAssetForBulkInstall('A_Link_Dlink');		
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
function value_changed(){
	id_wh = document.getElementById("UserDataForBulkInstallAsset").value
	console.log(id_wh);
	$.post("A_Link_Dlink",{action : 'Display_additional' , id : id_wh} , function (r){
		r = JSON.parse(r)
		if(r.data.length > 0)
		{
		
		params = r.data[0]
		/*document.getElementById("divDataForBulkInstall").value =params.nm_s_assetdiv*/
		document.getElementById("cd_employeeId").value =params.id_wh_dyn
		/*document.getElementById("FloorForBulkInstallAsset").value =params.nm_flr
		document.getElementById("LocDataForBulkInstallAsset").value =params.nm_loc
		document.getElementById("buildingDataForBulkInstallAsset").value =params.nm_building
		document.getElementById("SubLocDataForBulkInstallAsset").value =params.nm_subl*/
		}
		else{
			/*document.getElementById("divDataForBulkInstall").value =""*/
		document.getElementById("cd_employeeId").value =""
		/*document.getElementById("FloorForBulkInstallAsset").value =""
		document.getElementById("LocDataForBulkInstallAsset").value =""
		document.getElementById("buildingDataForBulkInstallAsset").value =""
		document.getElementById("SubLocDataForBulkInstallAsset").value =""*/
		}
		});
	
}
function DisplayAssetDlink(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
		var searchWord = $('input[name="search"]').val();
	var id_s_assetdiv = $('select[name="id_s_assetdiv"]').val();
	$.post(servletName,{action : 'linked_prods',searchWord : searchWord  } , function (r){
					
					var list ='<tr class="new" >'+
						'<td colspan="9"><button type="button" style="margin-left: 470px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation1(\'uninstallAsset\')">D-Link</button>'+
						'</td></tr>';
					 list= list +'<tr class="new">'+
					'<td><center><strong>Accessories ID</strong></center></td>'+
					'<td><center><strong>Serial No</strong></center></td>'+
					'<td><center><strong>Accessories Name</strong></center></td>'+
					'<td><center><strong>Asset Name</strong></center></td>'+
					'<td><center><strong>Asset ID</strong></center></td>'+
					'<td><center><strong>Linked Date</strong></center></td>'+
					'<td><center><strong>Accessories Remarks</strong></center></td>'+
					/*'<td><center><strong>Asset status <font color ="red"> * </font></strong></center></td>'+*/
					'<td><center><strong>D-Link Date<font color="red"> * </font></strong></center></td>'+
					/*'<td><center><strong>A Status<font color="red"> * </font></strong></center></td>'+*/
					'<td style="width: 80px;"><center><strong> Check/ Uncheck </a></strong></center></td>'+
				'</tr>';
					
					 if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						//if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						if(params.rmk_asst == '') rmk_asst = '-'; else rmk_asst = params.rmk_asst;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.asset_nm+'</center></td>'+
						'<td><center>'+params.asset_cd+'</center></td>'+
						'<td><center>'+params.link_date+'</center></td>'+
						'<td><center>'+
						'<input type="hidden" name="asst_stat'+params.id_wh+'" id="asst_stat" style="width:200px" class="common-validation resetAcc" data-valid="mand" value="2">'+
						'<input type="text" name="uninstallRmk'+params.id_wh+'" value="'+params.rmk_asst+'" style="width: 150px;"></center></td>'+
						
						/*'<td><center><select name="asst_stat'+params.id_wh+'" id="asst_stat" style="width:200px" class="common-validation resetAcc" data-valid="mand"><option value="">Select</option><option value="2">Active</option><option value="6">Under Repair</option><option value="7">To be Disposed/To be Sold</option></select></center></td>'+
						*/'<td><center><input type="text" name="uninstallAssetDate'+params.id_wh+'" patel="'+params.id_wh+'" value="" class="common-validation datepicker" data-valid="mand" style="width: 100px;"/></center></td>'+
						/*'<td>'+
						'<select style="width: 180px;" name = "asset_status'+params.id_wh+'" id="returndate'+i+'" class="common-validation"    data-valid="mand" >'+
								
								'<option value = "working" >Received in Working</option>'+
								'<option value = "phy_damage" >Received in Physical Damage</option>'+
								'<option value = "not_working" >Received in Not working Condition</option>'+
								'<option value = "transite_damage" >Transite Damage</option>'+
								'<option value = "buy_back" >BuyBack</option>'+
								'<option value = "not_received" >Not Received</option>'+
								
					
						'</select>'+
					'</td>'+*/
						'<td><center><strong><input type="checkbox" name="uninstallAsset" class="uninstallAssetForSelectAll" value="'+params.id_wh+'"/></strong></center></td>'+
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
					    	
					    	//var id_wh = $('input[name="id_wh"]').val();
					    	$('.datepicker').removeClass('error');
					    $.post('A_Link_Dlink',{action : 'CheckDlinkDate' , dt_alloc : dt_alloc ,id : id_wh},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('De-Link date should be greater or equal to Linked Date '+r.dt_alloc);
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

