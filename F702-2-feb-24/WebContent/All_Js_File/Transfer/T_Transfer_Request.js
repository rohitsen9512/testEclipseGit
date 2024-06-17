
function DisplayAssetForIntraUnitTransferRequest()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
			var id_loc = $('#locDataForIUTR').val();
			var id_sloc = $('#subLocDataForIUTR').val();
			var id_building = $('#buildingDataForEmpUser').val();
			var typ_asst = $('#asstTypDataForIntraUTR').val();
	$.post('T_Transfer_Request',{action : 'Display',searchWord : searchWord,id_loc:id_loc,id_sloc:id_sloc,typ_asst:typ_asst,id_building:id_building} , function (r){
					
					var list= '<thead><tr>'+
					'<td><strong><center>Asset ID</center></strong></td>'+
					'<td style="width: 390px;"><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					'<td><strong><center>Asset Description</center></strong></td>'+
					/*'<td><strong><center>Employee Name</center></strong></td>'+*/
					'<td><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'uninstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'uninstallAssetForSelectAll\')"> Uncheck </a></center></strong></center></td>'+
				'</tr></thead>';
					if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.serial_no == '') serial_no = '-'; else serial_no = params.serial_no;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						if(params.nm_emp == '') nm_emp = '-'; else nm_emp = params.nm_emp;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+serial_no+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						/*'<td><center>'+nm_emp+'</center></td>'+*/
						'<td><strong><center><input type="checkbox" name="requestAsset" class="uninstallAssetForSelectAll" value="'+params.id_wh+'"/></strong></center></td>'+
						'</tr>';
						}
						
						list = list + '<tr class="success">'+
						
						'<td colspan="8">'+
						'<strong>Floor<font color="red"> * </font>:</strong>&nbsp;&nbsp;'+
						'<select id="FloorForIntraTransferAsset" class="CommonForBulk" name="id_flr" style="width: 150px;">'+
							'<option value="">Select</option>'+
						'</select>'+
						'<button type="button" style="margin-left: 400px;"  class="btn btn-primary requestAssetBtn" onclick="checkBoxValidation11(\'requestAsset\')">Send Request</button>'+
						'<input type="hidden" name="loc" value="'+id_loc+'" id="locDataForIUTR"><input type="hidden" id="subLocDataForIUTR" name="sloc" value="'+id_sloc+'">'+
						'</td></tr>';
					
						$('.DisplayAssetForIntraUnitTransferRequesting').html(list);
					
					}
					else
						{
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available..</strong></center></td></tr>';
						$('.DisplayAssetForIntraUnitTransferRequesting').html(list);
						}
					SubDropDownDataDisplay(id_building,"FloorForIntraTransferAsset","M_Floor",function (status){
						if(status)
						{
						}});
					
					$('.datepicker').datepicker({
						yearRange: '1985:2025', 
						changeMonth: true,
					      changeYear: true,
					      dateFormat: "yy-mm-dd",
					      autoSize: true,
					      onSelect: function(selected,evnt) {
					    	
					    	  var id_wh = $(this).attr('patel');
					    	  var name = $(this).attr('name');
					    	  var dt_alloc = $(this).val();
					    	
					    	//var id_wh = $('input[name="id_wh"]').val();
					    	$('.datepicker').removeClass('error');
					    $.post('A_Install',{action : 'CheckUnInstallDate' , dt_alloc : dt_alloc ,id : id_wh},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('Uninstall Date should be greater than installed / last IUT date '+r.dt_alloc);
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


function ControlIntraUnitTransferRequestAsset(action , DisplayDiv , HideDiv , HideDiv2 , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == "Edit")
	{
		AddInputValueForTransferRequest(id);
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).hide();
		$('#'+HideDiv2).show();
		
	}else if(action == 'Next'){
		var t=false;
		t = ValidationForm('searchFormForIUTR');
		if(t){
			$('#displayAssetForIUTSearch').addClass('hideButton');
			$('#DisplayAssetForIntraUnitTransferRequest').removeClass('hideButton');
			DisplayAssetForIntraUnitTransferRequest();
		}
		
	}
	else if(action == "Save")
	{
		
	var t = 0;
	t = ValidationForm("submitTransferRequest");
	var x = $('#submitTransferRequest').serialize();
	if(t > 0)
		{
		$('.IntraUTR').attr('disabled','disabled');
			$.post('T_Transfer_Request',x,function (r)
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
							/*DisplayAssetForIntraUnitTransferRequest();
							$('#'+HideDiv2).hide();
							$('#'+DisplayDiv).show();
							$('#'+HideDiv).show();*/
							//$( ".intraunittransferrequest" ).trigger( "click" );
							window.location = $('.intraunittransferrequest_event').attr('href');
						}
						$('.IntraUTR').removeAttr('disabled');	
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

function DisplayAssetForIntraUnitTransferRequestkkk()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
				$.post('T_Transfer_Request',{action : 'Display' ,searchWord : searchWord} , function (r){
	
					var list= '<tr class="tableHeader info">'+
					'<td><strong><center>Ware House ID</center></strong></td>'+
					'<td style="width: 390px;"><strong><center>Asset Name</center></strong></td>'+
					'<td><strong><center>Serial Number</center></strong></td>'+
					'<td><strong><center>Asset Description</center></strong></td>'+
					'<td><strong><center>Employee Name</center></strong></td>'+
					'<td style="width: 115px;"><strong><center><a href="#">Request </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.no_mfr == '') no_mfr = '-'; else no_mfr = params.no_mfr;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						if(params.nm_emp == '') nm_emp = '-'; else nm_emp = params.nm_emp;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+serial_no+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						'<td><center>'+nm_emp+'</center></td>'+
						'<td><strong><center><a class="alert" href="#" onclick="ControlIntraUnitTransferRequestAsset(\'Edit\',\'displayAssetForIntraUnitTransferRequestSearch\',\'DisplayAssetForIntraUnitTransferRequest\',\'IntraUnitTransferRequestDetails\','+params.id_wh+')"> Request</a></center></strong></td>'+
						'</tr>';
						}
					
					
						$('.DisplayAssetForIntraUnitTransferRequesting').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available..</strong></center></td></tr>';
						$('.DisplayAssetForIntraUnitTransferRequesting').html(list);
					}
					
				
			},'json');
			}});

}


function AddInputValueForTransferRequest(id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	//DisplayDropDownData("M_Floor","floorForIntraUnitTransferRequest");
	//DropDownDataDisplay("M_Cost_Center","costCenterForIntraUnitTransferRequest");
		
	$.post('T_Transfer_Request',{action : 'Edit', id : id},function (r)
			{
		var id_subl=0;
				if(r.data)
					{
					id_subl=r.data[0].id_subl_tran;
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
				DropDownDataDisplaySub(id_subl,"floorForIntraUnitTransferRequest","M_Floor",function (status){
					if(status)
					{
						
					}});
				
			},'json');
	
	
	
	
		}});
			
	}

function checkBoxValidation11(FormName)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
				
	var t=0;
	var ids='';
	$('#FloorForIntraTransferAsset').removeClass('error');
	var id_flr = $('#FloorForIntraTransferAsset').val();
	
	
	
			$('[name="'+FormName+'"]:checked').each(function(){
				if(this.checked)
					{
						
						t=1;
						if(ids == '')
							ids = $(this).val();
						else
							ids += "patel"+$(this).val();
					}
			});
	
	if(t == 0)
	{
		bootbox.alert("Please select at least one asset.");
	}else if(id_flr == ''){
		bootbox.alert('Please select floor.');
		$('#FloorForIntraTransferAsset').addClass('error');
		$('#FloorForIntraTransferAsset').focus();
	}
	else
		{
		$('.requestAssetBtn').attr('disabled','disabled');
		
		$.post('T_Transfer_Request',{action : "Update" , ids : ids , id_flr : id_flr},function (r)
				{
			if(r.data == 0)
			{
			
			alert('Bulk Allocation Date should be greater or equal to '+r.dt_grn);
				$('#BulkInstallId').focus();
				$('#BulkInstallId').val('');
				$('#BulkInstallId').addClass('error');
				exit(0);
			}
			else if(r.data == 1){
					bootbox.alert('Selected asset has been requested sucessfully.');
					window.location = $('.intraunittransferrequest_event').attr('href');
					//$( "." ).trigger( "click" );
				}
			
				},'json');
			
		
		
			
		}
			}});
}


