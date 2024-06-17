
function ControlEditFromStore(action , DisplayDiv , HideDiv , HideDiv2 , id , id_loc)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == "Edit")
	{
		
		$('#'+HideDiv).hide();
		$('#'+HideDiv2).hide();
		$('#'+DisplayDiv).show();
		
		EditFunForEditFromStore(id,id_loc);
	}
	else if(action == "Update")
	{
		var t=false;
		t=ValidationForm('SubmitFormForEditFromStore');
		if(t)
			{
			t=false;
			var val = $('#st_lease').val();
			var warr_amc =$('#warr_amc').val();
			var ins =$('#ins').val();
			var cal =$('#cal').val();
			if(cal == 'Yes')
			{
				t = ShowRowColumnValidation('calCommonClass1');
			}
		else
			{
			t=true;
			}
			if(ins == 'Yes')
			{
				t = ShowRowColumnValidation('insCommonClass2');
				
			}
		else
			{
			t=true;
			
			}
			if(warr_amc == 'A' || warr_amc == 'W')
			{
				t = ShowRowColumnValidation('amcCommonClass1');
				
			}
		else
			{
			t=true;
			}
			if(val == 'UL')
				{
					t = ShowRowColumnValidation('leaseCommonClass1');
					
				}
			else
				{
				t=true;
				
				}
			}
		if(t)
			{
			$('.edits').attr('disabled','disabled');
			
				var x =$('#SubmitFormForEditFromStore').serialize();
				$.post('A_Edit_From_Store',x,function (r)
						{
					
							if(r.data)
								{
									/*bootbox.alert('Updated successfully.');
									DisplayAssetForEdit('A_Edit_From_Store');
									$('#'+DisplayDiv).hide();
									$('#'+HideDiv).show();
									$('#'+HideDiv2).show();*/
								bootbox.alert('Updated successfully.');
								//$( ".editFromStore" ).trigger( "click" );
									window.location = $('.editFromStore_event').attr('href');
								}
							else
								{
									bootbox.alert('Please try again.');
								}$('.edits').removeAttr('disabled');
						},'json');
				
		
			}
		
	}
	else if(action == "Cancel")
	{
		$('.editFromStore').trigger('click');
		window.location = $('.editFromStore_event').attr('href');
	
	}
			}});
}


function EditFunForEditFromStore(id,id_loc)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	DisplaySubDropDownData(id_loc,'subLocationForEditFromStore1','M_Subloc',function (status){
	if(status)
		{
		$.post('A_Edit_From_Store',{action : 'Edit', id : id},function (r)
				{
			
			var t=0,t1=0,t2=0,t3=0;
			var val = '',val1='';
					if(r.data)
						{
						
							for(var i = 0; i < r.data.length ; i++)
							
								for (var key in r.data[i])
									{
									
										if($('select[name='+key+']').is("select") && r.data[i][key] != '')
										{
											$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
											
										}
										else
										{
											$('input[name='+key+']').val(r.data[i][key]);
										}
										if(key == 'ins' && r.data[i][key] =='Yes')
										{
											t=1;
											
										}
										
										if(key == 'st_lease' && r.data[i][key] =='UL')
										{
											t1=1;
											
										}
										
										if(key == 'warr_amc' && (r.data[i][key] =='A' || r.data[i][key] =='W'))
										{
											t2=1;
											val=r.data[i][key];
										}
										
										if(key == 'calb' && (r.data[i][key] == 'CAL' || r.data[i][key] == 'PM'))
										{
											t3=1;
											
										}
									}
							
							$('input[name="id"]').val(id);
							/*$('input[name="dt_po"]').val(r.data[0].dtPo);
							$('input[name="dt_inv"]').val(r.data[0].dtInv);*/
							
							
						}
						else
							{
								bootbox.alert("Try again.");
							}
					if(t == 1)
					{
						
						ShowRowColumn('Yes' , 'insCommonClass1');
					}	
					if(t1 == 1)
					{
						
						ShowRowColumn('UL' , 'leaseCommonClass');
						$('input[name="std_lease"]').val(r.data[0].stdLease);
						$('input[name="endt_lease"]').val(r.data[0].endtLease);
					}
					else
					{
					$('input[name="std_lease"]').val('');
					$('input[name="endt_lease"]').val('');
					}
					
					if(t2 == 1)
					{
						
						ShowRowColumn(val , 'amcCommonClass');
						$('input[name="dt_amc_start"]').val(r.data[0].dtAmcStart);
						$('input[name="dt_amc_exp"]').val(r.data[0].dtAmcExp);
					}
					else
					{
					$('input[name="dt_amc_start"]').val('');
					$('input[name="dt_amc_exp"]').val('');
					}
					
					if(t3 == 1)
					{
						
						ShowRowColumn('Yes' , 'calCommonClass');
					}	
					
				},'json');
		
		}
	});
	
			}});
		
	}


function DisplayAssetForPhysicalAssetVerification(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
				$.post(servletName,{action : 'Display' ,searchWord : searchWord} , function (r){
	
					var list= '<tr class="tableHeader info">'+
					/*'<td><strong><center>Asset ID</center></strong></td>'+*/
					'<td><strong><center>AssetTag No</center></strong></td>'+
					'<td><strong><center>Model No</center></strong></td>'+
					'<td><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					/*'<td><strong><center>Asset Description</center></strong></td>'+*/
					/*'<td><strong><center>Department Name</center></strong></td>'+*/
					'<td style="width: 120px;"><strong><center>Verified(Yes/No) </center></strong></td>'+
					'<td style="width: 120px;"><strong><center>Verified Date</center></strong></td>'+
					'<td style="width: 80px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'uninstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'uninstallAssetForSelectAll\')"> Uncheck </a></strong></center></td>'+
					
				'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						//if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						
						list = list + '<tr class="success">'+
						/*'<td><center>'+params.id_wh_dyn+'</center></td>'+*/
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.no_mfr+'</center></td>'+
						/*'<td><center>'+params.ds_asst+'</center></td>'+*/
						/*'<td><center>'+params.nm_dept+'</center></td>'+
						'<td><strong><center><a class="alert" href="#" onclick="ControlEditFromStore(\'Edit\',\'EditDetailsForEditFromStore\',\'SearchFromForEditFromstore\',\'DisplayEditFromStore\','+params.id_wh+','+params.id_loc+')"> Modify </a></center></strong></td>'+
						*/
						'<td>'+
							'<select style="width: 120px;" id="" name="verified_status'+params.id_wh+'" class="common-validation" data-valid="mand">'+
							'<option value = "" > -- Select --</option>'+
							'<option value = "Yes" >Yes</option>'+
							'<option value = "No" > No</option>'+
							'</select>'+
						'</td>'+
						'<td><input type="text" style="width: 120px;" name="dt_verified'+params.id_wh+'" value="" class="datepicker"></td>'+
						'<td><center><strong><input type="checkbox" name="PhysicalVerification" class="uninstallAssetForSelectAll" value="'+params.id_wh+'"/></strong></center></td>'+
						'<input type="hidden" name="id_flr'+params.id_wh+'" value="'+params.id_flr+'">'+
						'<input type="hidden" name="id_wh_dyn'+params.id_wh+'" value="'+params.id_wh_dyn+'">'+
						'<input type="hidden" name="id_wh'+params.id_wh+'" value="'+params.id_wh+'">'+
						'</tr>';
						}
						
						list = list + '<tr class="success">'+
						'<td colspan="9"><button type="button" style="margin-left: 470px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidationForPhysicalAssetVerification(\'PhysicalAssetVerification\')">Save/Update</button>'+
						'</td></tr>';
					}
					else
						list +='<tr rowspan="2"><td colspan="9"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.displayDataForEditFromStore').html(list);					
				
				$('.datepicker').datepicker({
					yearRange: '1985:2025', 
					changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				      
				});		
				
			},'json');
		
				
				
				
			}});
}

function checkBoxValidationForPhysicalAssetVerification(FormName,id)
{
	$('.uninstallAssetForSelectAll').attr('disabled','disabled');
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
		
		
			$('input[type="checkbox"]').each(function(){
				
				
				$('input[name="verified_status'+$(this).val()+'"]').removeClass('error');
				$('input[name="dt_verified'+$(this).val()+'"]').removeClass('error');
				
				if(this.checked)
					{	
					var q=$("select[name=verified_status"+$(this).val()+"]").val();
				
					if(q==""){
						
						bootbox.alert("Please Select Yes/No.");
						$('select[name="verified_status'+$(this).val()+'"]').addClass('error');
						$('select[name="verified_status'+$(this).val()+'"]').focus();
						$('.uninstallAssetForSelectAll').removeAttr('disabled');
						exit(0);
					}
						val = $('input[name="dt_verified'+$(this).val()+'"]').val();
						if(val == '')
							{
								bootbox.alert("Please fill the date field.");
								$('input[name="dt_verified'+$(this).val()+'"]').addClass('error');
								$('input[name="dt_verified'+$(this).val()+'"]').focus();
								$('.uninstallAssetForSelectAll').removeAttr('disabled');
								exit(0);
								
							}
						
					
						t=1;
						
					}
				
			});
		
	if(t == 0)
	{
		bootbox.alert("Please select at least one asset.");
		$('.uninstallAssetForSelectAll').removeAttr('disabled');
	}
	else
		{
		
		$('input[name="action"]').val('Update');
		var x = $('#'+FormName).serialize();
		$('.uninstallAssetForSelectAll').attr('disabled','disabled');
			$.post('Physical_asset_verification',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.uninstallAssetForSelectAll').removeAttr('disabled');
								bootbox.alert("Asset has been Verified successfully.");
								DisplayAssetForPhysicalAssetVerification("Physical_asset_verification");							
							}
						
							else
								{
									$('.uninstallAssetForSelectAll').removeAttr('disabled');
									bootbox.alert("Try again.");
								}
									
						
					},'json');
			
			
		
		
			
		}
			}});
}

function DisplayAssetForConfigIT(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
				$.post(servletName,{action : 'Display' ,searchWord : searchWord} , function (r){
	
					var list= '<tr class="tableHeader info">'+
					'<td><strong><center>Asset Tag</center></strong></td>'+
					'<td><strong><center>Remark</strong></center></td>'+
					'<td><strong><center>Model No</center></strong></td>'+
					'<td><strong><center>Asset Name</center></strong></td>'+
					//'<td><strong>Serial Number</strong></td>'+
					'<td><strong><center>Department</center></strong></td>'+
					'<td><strong><center>Asset Description</center></strong></td>'+
					'<td><strong><center>Storage Name</center></strong></td>'+
					'<td style="width: 155px;"><strong><center><a href="#">Configuration IT </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.nm_model == '') nm_model = '-'; else nm_model = params.nm_model;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.rmk_asst+'</center></td>'+
						'<td><center>'+nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						//'<td><center>'+params.no_mfr+'</center></td>'+
						'<td><center>'+params.nm_dept+'</center></td>'+
						'<td><center>'+params.ds_asst+'</center></td>'+
						'<td><center>'+params.nm_flr+'</center></td>'+
						'<td><strong><center><a class="alert" href="#" onclick="ControlConfigITAsset(\'Edit\',\'SubmitFormForITConfig\',\'EditDetailsForEditFromStore\',\'SearchFromForEditFromstore\',\'DisplayEditFromStore\','+params.id_wh+')"> Configuration  </a></center></strong></td>'+
						'</tr>';
						}
					}
					else
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.displayDataForEditFromStore').html(list);					
				
			},'json');
		
			}});
}

function ControlConfigITAsset(action ,formName ,  DisplayDiv , HideDiv , HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			if(action == 'Cancel')
				{
				$( ".ConfigIT" ).trigger( "click" );
				}
			else if(action == 'Save')
			{
				var t=false;
				t=ValidationForm(formName);
				if(t)
					{
					var x = $('#'+formName).serialize();
					$('.config').attr('disabled','disabled');

					
				$.post('A_Config_IT',x,function (r)
						{
							
					
							if(r.data==1)
								{
									bootbox.alert("Configuration Successful.");
									$( ".ConfigIT" ).trigger( "click" );
								}
								else
									{
										t=false;
										bootbox.alert(" please try again.");
									}
							
							$('.config').removeAttr('disabled');
						},'json');
					}
			}
			else
				{
					$('#'+HideDiv).hide();
					$('#'+HideDiv2).hide();
					$('#'+DisplayDiv).show();
					
					$.post('A_Config_IT',{action : 'Edit', id : id},function (r){
						
						var t2=0,t3=0;
						var val = '';
								if(r.data)
									{
									
										for(var i = 0; i < r.data.length ; i++)
										
											for (var key in r.data[i])
												{
												
													if($('select[name='+key+']').is("select") && r.data[i][key] != '')
													{
														$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
														
													}
													else
													{
														$('input[name='+key+']').val(r.data[i][key]);
													}
													
													if(key == 'warr_amc' && (r.data[i][key] =='A' || r.data[i][key] =='W'))
													{
														t2=1;
														val=r.data[i][key];
													}
													
												}
										
										$('input[name="id_wh"]').val(id);
										
										
									}
									else
										{
											bootbox.alert("Try again.");
										}
								if(t2 == 1)
								{
									
									ShowRowColumn(val , 'amcCommonClass');
									$('input[name="dt_amc_start"]').val(r.data[0].dtAmcStart);
									$('input[name="dt_amc_exp"]').val(r.data[0].dtAmcExp);
								}
								else
								{
								$('input[name="dt_amc_start"]').val('');
								$('input[name="dt_amc_exp"]').val('');
								}
								
								if(t3 == 1)
								{
									
									ShowRowColumn('Yes' , 'calCommonClass');
								}	
						
					},'json');
					
				}
			}});
}






