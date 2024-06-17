
function ControlEditFromStore(action , DisplayDiv , HideDiv , HideDiv2 , id , id_loc,type)
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
		if(type=="IT" || type=="nonit"){
			EditAccessoriesFromStore(id);
		}
		else{
			$('.displayDataForAccessories').hide()
		}
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
			var status = $('select[name="device_status1"]').val();
			if(status=="Allocate To Employee Permanant"){
				$('select[name="device_status"]').val("allct_to_emp");
			}
			if(status=="Allocate To Employee Temporary"){
				$('select[name="device_status"]').val("allct_to_emp_temp");
			}
				var x =$('#SubmitFormForEditFromStore').serialize();
				$.post('A_Edit_From_Store',x,function (r)
						{
					
							if(r.data)
								{
								bootbox.alert('Updated successfully.');
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
							
							$('input[name="id"]').val(id);
							$('input[name="rmk_asst"]').val(r.data[0].rmk_asst);
							$('input[name="dt_po"]').val(r.data[0].dtpo);
							$('input[name="dt_inv"]').val(r.data[0].dtinv);
							var device_status=(r.data[0].device_status);
							var dt_po=(r.data[0].dtpo);
							var dt_inv=(r.data[0].dtinv);
							
							if(r.data[0].typ_asst=='IT'){
							$('#process1').show();
							$('#process2').show();
							$('#storage_ram').show();
							}
							if(device_status=='allct_to_emp' || device_status=='allct_to_emp_temp' || device_status=='link_to_asset')
							{
								if(device_status=='allct_to_emp')
								{
									$('input[name="device_status1"]').val('Allocate To Employee Permanant');
								}
								if(device_status=='allct_to_emp_temp')
								{
									$('input[name="device_status1"]').val('Allocate To Employee Temporary');
								}
								if(device_status=='link_to_asset')
								{
									$('input[name="device_status1"]').val('Linked To Asset');
									$('select[name="device_status"]').val('link_to_asset');
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
						$('input[name="std_lease"]').val(r.data[0].stdlease);
						$('input[name="endt_lease"]').val(r.data[0].endtlease);
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
					
				
				$.post('A_Edit_From_Store',{action : 'Track', id : id},function (r)
				{
					var list= '<tr>'+
			'<td colspan="17" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Asset Track History</b></td></tr>'+ 
		'<tr class="tableHeader info">'+
					'<td><strong><center>Field Name</center></strong></td>'+
				
					'<td><strong><center>Old Value</center></strong></td>'+
					'<td><strong><center>New Value</center></strong></td>'+
					'<td><strong><center>Edited By</center></strong></td>'+
					'<td><strong><center>Edit Date</center></strong></td>'+
					
								'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						var old_val='';
						var new_val='';
						params = r.data[i];
						old_val=params.old_value;
						if(params.old_value==undefined){
							old_val='In Store'
						}
						else if(params.old_value=='1900-01-01' ||params.old_value=='Jan  1 1900 12:00AM'){
						old_val='---';
						}
						
						new_val=params.new_value;
						if(params.new_value==undefined){
							new_val='In Store'
						}
						else if(params.field_name=='Allocation Date' ||params.field_name=='Deallocation Date'){
							new_val=params.dt_edit;
						}
						list = list + '<tr class="success">'+
						'<td><center>'+params.field_name+'</center></td>'+
					'<td><center>'+old_val+'</center></td>'+
						'<td><center>'+new_val+'</center></td>'+
						'<td><center>'+params.edit_by+'</center></td>'+
						'<td><center>'+params.dt_edit+'</center></td>'+
						
						'</tr>';
						}
					}
					else
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.displayTrackHistory').html(list);					
				
			},'json');
					
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
	
				$.post(servletName,{action : 'Display' ,searchWord : searchWord} , function (r){
	
					var list= '<thead><tr class="new">'+
					'<th><strong><center>Asset ID</center></strong></th>'+
					'<th><strong><center>Asset Name/Model</center></strong></th>'+
					'<th><strong><center>Asset Name</center></strong></th>'+
					'<th><strong><center>Serial Number</center></strong></th>'+
					'<th><strong><center>Type</center></strong></th>'+
				'</tr></thead><tbody>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.typ_asst=='accessories'){
							var asset_type='Accessorry';
						}
						else if(params.typ_asst=='nonit'){
							var asset_type='NON-IT';
						}
						else if(params.soft=='nonit'){
							var asset_type='Software';
						}
						else{
							var asset_type=params.typ_asst;
						}
						
						list = list + '<tr>'+
						'<td><center><a class="alertlink" href="#" onclick="ControlEditFromStore(\'Edit\',\'EditDetailsForEditFromStore\',\'SearchFromForEditFromstore\',\'DisplayEditFromStore\','+params.id_wh+','+params.id_loc+',\''+params.typ_asst+'\')">'+params.id_wh_dyn+'</a></center></td>'+
					    '<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+asset_type+'</center></td>'+
						'</tr>';
						}
					}
					else
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.displayDataForEditFromStore').html(list);					
				getButtonsForListView('displayDataForEditFromStore','EditFrom_List');
			},'json');
		
			}});
}

function EditAccessoriesFromStore(id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			
	
				$.post('A_Edit_From_Store',{action : 'DisplayAccess' ,id : id} , function (r){
	
					var list= '<tr>'+
			'<td colspan="17" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;text-align: center;">Linked Accessories/Software</p></td></tr>'+ 
		'<tr class="tableHeader info">'+
					'<td><strong><center>Accessory ID</center></strong></td>'+
					'<td><strong><center>Model No</center></strong></td>'+
					'<td><strong><center>Accessory Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					'<td><strong><center>Linked Date</center></strong></td>'+
					
				'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+params.nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.dtlink+'</center></td>'+
						
						'</tr>';
						}
					}
					else
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.displayDataForAccessories').html(list);					
				
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
					'<td><strong><center>Remark</strong></center></td>'+
					'<td><strong><center>Model No</center></strong></td>'+
					'<td><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Asset Description</center></strong></td>'+
					'<td style="width: 155px;"><strong><center><a href="#">Configuration IT </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.nm_model == '') nm_model = '-'; else nm_model = params.nm_model;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.rmk_asst+'</center></td>'+
						'<td><center>'+nm_model+'</center></td>'+
						'<td><center>'+params.ds_pro+'</center></td>'+
						'<td><center>'+params.ds_asst+'</center></td>'+
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


$(function() {

	
	
    $("#amcYeardatepicker").datepicker({
    	yearRange: '1985:2025',
    	 changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
        onSelect: function(dateText, instance) {
        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
        	 
        	 $('input[name="dt_amc_start"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             date.setDate(date.getDate() - 1);
             
              date.setMonth(date.getMonth() + 12);
              $('input[name="dt_amc_exp"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
              
            
            
        }
    });
	    $("#leasecYeardatepicker").datepicker({
	    	yearRange: '1985:2025',
	    	 changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
	        onSelect: function(dateText, instance) {
	        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
	        	 
	        	 $('input[name="std_lease"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
	             date.setDate(date.getDate() - 1);
	             
	              date.setMonth(date.getMonth() + 12);
	              $('input[name="endt_lease"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
	              
	            
	            
	        }
	    }); 
	
	    $( ".leasedatepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  $('input[name="std_lease"]').removeClass('error');
	    	  var std_lease=$('input[name="std_lease"]').val();
	    	  var std_lease1=$('input[name="std_lease"]').val();
	    	  var dt_end = $(this).val();
	    	  if(std_lease == '')
	    		  {
	    		  	alert('First filled start  date.');
	    		  	$('input[name="std_lease"]').focus();
	    		  	$('input[name="std_lease"]').addClass('error');
	    		  	$('input[name="std_lease"]').val('');
	    		  	$(this).val('');
	    		  	exit(0);
	    		  }
	    	  else
	    		  {
	    		  
	    		  var temp_strt = std_lease.split("/");
				  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						
					var temp_end = dt_end.split("/");
					var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						
					std_lease = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
					dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
	    		  if(std_lease > dt_end)
	    			  {
	    			  	alert('End date should be greater or equal to start date : '+std_lease1);
	    			  	$(this).focus();
	    			  	$(this).val('');
	    			  	$(this).addClass('error');
	    			  	exit(0);
	    			  }
	    		  
	    		  }
	    	  
	      }
	    }); 
	
 



	

	
	
});

function CalcTotPriceEditFromStore()
{var intRegex = /^\d+$/;
var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
$('input[name="cst_asst_add"]').removeClass('error');
var str = $('input[name="cst_asst_add"]').val();

if(str != '' || str != undefined)
	{
		if(intRegex.test(str) || floatRegex.test(str)) {
			
			var addPrice = $('input[name="cst_asst_add"]').val();
			var tt_un_prc = $('input[name="cst_asst"]').val();
			
			var totPrice = parseFloat(addPrice) + parseFloat(tt_un_prc);
			
			$('input[name="val_asst"]').val(totPrice.toFixed(2));
		}
		else
		{
			alert('Invalid number.');
			$('input[name="cst_asst_add"]').addClass('error');
			$('input[name="cst_asst_add"]').focus();
			exit(0);
		}
	}
	
}



