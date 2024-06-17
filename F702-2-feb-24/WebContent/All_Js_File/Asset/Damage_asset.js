
function ControlEditFromStore(action , DisplayDiv , HideDiv , HideDiv2 , id , id_loc)
{
	SessionCheck(function (ses){		
		if(ses)
			{
					//
	if(action == "Edit")
	{
		
		$('#'+HideDiv).hide();
		$('#'+HideDiv2).hide();
		$('#'+DisplayDiv).show();
		
		EditFunForEditFromStore(id,id_loc);
	}
	else if(action == "Update")
	{
		$('input[name="action"]').val('UpdateDamage');
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
				$.post('A_Damage_Asset',x,function (r)
						{
					
							if(r.data)
								{
									/*bootbox.alert('Updated successfully.');
									DisplayAssetForEdit('A_Damage_Asset');
									$('#'+DisplayDiv).hide();
									$('#'+HideDiv).show();
									$('#'+HideDiv2).show();*/
								bootbox.alert('Updated successfully.');
								//$( ".damageAsset" ).trigger( "click" );
									window.location = $('.damageAsset_event').attr('href');
								}
							else
								{
									bootbox.alert('Please try again.');
								}$('.edits').removeAttr('disabled');
						},'json');
				
		
			}
		
	}
	else if(action == "Approve")
	{
		$('input[name="action"]').val('UpdateApproveDamage');
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
				$.post('A_Damage_Asset',x,function (r)
						{
					
							if(r.data)
								{
									/*bootbox.alert('Updated successfully.');
									DisplayAssetForEdit('A_Damage_Asset');
									$('#'+DisplayDiv).hide();
									$('#'+HideDiv).show();
									$('#'+HideDiv2).show();*/
								bootbox.alert(action+' successfully.');
								//$( ".approvedamageAsset" ).trigger( "click" );
									window.location = $('.approvedamageAsset_event').attr('href');
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
	
		//$('.damageAsset').trigger('click');
		window.location = $('.damageAsset_event').attr('href');
			//alert("inside damage");
	
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
		$.post('A_Damage_Asset',{action : 'Edit', id : id},function (r)
				{
			
			var t=0,t1=0,t2=0,t3=0,t4=0;
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
									
									if(r.data[0].file_name==undefined){
										
									}
									else{
							document.getElementById("image").src="InvoiceScanFile/"+r.data[0].file_name;
							document.getElementById("download_po").textContent=r.data[0].upload_po;
							document.getElementById("download_po").href="InvoiceScanFile/"+r.data[0].upload_po;
							}
							$('input[name="id"]').val(id);
							$('input[name="dt_po"]').val(r.data[0].dtpo);
							$('input[name="dt_inv"]').val(r.data[0].dtinv);
							var device_status=(r.data[0].device_status);
							var dt_po=(r.data[0].dtpo);
							var dt_inv=(r.data[0].dtinv);
							//alert(device_status);
							//
							if(device_status=='allct_to_emp' || device_status=='allct_to_emp_temp')
							{
								if(device_status=='allct_to_emp')
								{
									$('input[name="device_status1"]').val('Allocate To Employee Permanant');
								}
								if(device_status=='allct_to_emp_temp')
								{
									$('input[name="device_status1"]').val('Allocate To Employee Temporary');
								}
								$('.device_status').hide();
								$('.device_status1').show();
								
							}
							else
							{
								$('.device_status').show();
								$('.device_status1').hide();
							}
							
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


function DisplayAssetForEdit(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
				$.post(servletName,{action : 'DisplayDamage' ,searchWord : searchWord} , function (r){
	
					var list= '<thead><tr class="new">'+
					'<th><strong><center>Asset ID</center></strong></th>'+
				/*	'<td><strong><center>AssetTag No</center></strong></td>'+*/
					'<th><strong><center>Model No</center></strong></th>'+
					'<th><strong><center>Asset Name</center></strong></th>'+
					'<th><strong><center>Serial Number</center></strong></th>'+
					/*'<td><strong><center>Asset Description</center></strong></td>'+*/
					/*'<td><strong><center>Department Name</center></strong></td>'+*/
					'<th style="width: 105px;"><strong><center>Modify </center></strong></th>'+
				'</tr></thead><tbody>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						//if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						
						list = list + '<tr>'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
					/*	'<td><center>'+params.serial_no+'</center></td>'+*/
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						/*'<td><center>'+params.ds_asst+'</center></td>'+*/
						/*'<td><center>'+params.nm_dept+'</center></td>'+*/
						'<td><strong><center><a class="alert" href="#" onclick="ControlEditFromStore(\'Edit\',\'EditDetailsForEditFromStore\',\'SearchFromForEditFromstore\',\'DisplayEditFromStore\','+params.id_wh+','+params.id_loc+')"> <i class="fas fa-edit"></i> </a></center></strong></td>'+
						'</tr>';
						}
						$('.displayDataForEditFromStore').html('</tbody>'+list);
					}
					else
					{
						list +='<tr><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.displayDataForEditFromStore').html('</tbody>'+list);
						}
				
									
				
				getButtonsForListView('displayDataForEditFromStore','Damage_Asset_List');
			},'json');
		
			}});
}

function DisplayAssetForEditApprove(servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
				$.post(servletName,{action : 'DisplayApproveDamage' ,searchWord : searchWord} , function (r){
	
					var list= '<thead><tr class="new">'+
					'<th><strong><center>Asset ID</center></strong></th>'+
					'<th><strong><center>Model No</center></strong></th>'+
					'<th><strong><center>Asset Name</center></strong></th>'+
					'<th><strong><center>Serial Number</center></strong></th>'+
					/*'<td><strong><center>Asset Description</center></strong></td>'+*/
					/*'<td><strong><center>Department Name</center></strong></td>'+*/
					'<th style="width: 105px;"><strong><center>Modify </center></strong></th>'+
				'</tr></thead><tbody>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						//if(params.no_model == '') no_model = '-'; else no_model = params.no_model;
						
						list = list + '<tr">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						/*'<td><center>'+params.ds_asst+'</center></td>'+*/
						/*'<td><center>'+params.nm_dept+'</center></td>'+*/
						'<td><strong><center><a class="alert" href="#" onclick="ControlEditFromStore(\'Edit\',\'EditDetailsForEditFromStore\',\'SearchFromForEditFromstore\',\'DisplayEditFromStore\','+params.id_wh+','+params.id_loc+')"> <i class="fas fa-check-circle"></i> </a></center></strong></td>'+
						'</tr>';
						}
						$('.displayDataForEditFromStore').html('</tbody>'+list);
					}
					else
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.displayDataForEditFromStore').html('</tbody>'+list);					
				//getButtonsForListView('displayDataForEditFromStore','ApprovalDamagedAsset');
			},'json');
		
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
				//	'<td><strong><center>Asset Tag</center></strong></td>'+
					'<td><strong><center>Remark</strong></center></td>'+
					'<td><strong><center>Model No</center></strong></td>'+
					'<td><strong><center>Asset Name</center></strong></td>'+
					//'<td><strong>Serial Number</strong></td>'+
					//'<td><strong><center>Department</center></strong></td>'+
					'<td><strong><center>Asset Description</center></strong></td>'+
					//'<td><strong><center>Storage Name</center></strong></td>'+
					'<td style="width: 155px;"><strong><center><a href="#">Configuration IT </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.nm_model == '') nm_model = '-'; else nm_model = params.nm_model;
						
						list = list + '<tr class="success">'+
					//	'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.rmk_asst+'</center></td>'+
						'<td><center>'+nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						//'<td><center>'+params.no_mfr+'</center></td>'+
					//	'<td><center>'+params.nm_dept+'</center></td>'+
						'<td><center>'+params.ds_asst+'</center></td>'+
						//'<td><center>'+params.nm_flr+'</center></td>'+
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






