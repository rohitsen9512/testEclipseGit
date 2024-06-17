
function GetDynamicAssetId(idForDisplayData,formName)
{
	var t=false;
		var id_grp = $('#assetDivForMaintenance').val();
		var id_sgrp = $('#subGroupDataForMaintenance').val();
		if(id_sgrp != '')
			{
			$.post('Asset_Maintenance',{action : 'DropDownResult' , id_grp : id_grp , id_sgrp : id_sgrp},function (r)
					{
				var list = '<option value=""> Select </option>';
						if(r.data.length > 0)
							{
								t=true;
								for(var i = 0; i < r.data.length ; i++)
								{
									params = r.data[i];
									list = list + '<option value="'+params.id_wh+'"> '+params.id_wh_dyn+'</option>';
								}
								
							}
						if(t)
						{
							$('#'+idForDisplayData).html(list);
							$('#'+idForDisplayData).focus();
						}
					else
						{
							bootbox.alert('No Assets available for maintenance.');
						}
						
					},'json');
			}
		else
			{
				bootbox.alert('Please select sub group.');
				$('#'+idForDisplayData).focus();
				$('#'+idForDisplayData).html('<option value=""> Select </option>');
			}
}

function ControlAssetMaintenance(Action , DisplayDiv , HideDiv)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(Action == 'Back')
		{
		$('.maintenance').trigger( "click" );
			
		}
	
	if(Action == 'Next')
	{
		var t =false;
		t = ValidationForm("submitDisplayAssetMaintenance");
		var x = $('#submitDisplayAssetMaintenance').serialize();
			
				if(t)
					{
					
						$.post('Asset_Maintenance',x,function (r)
								{
									if(r.data.length > 0)
										{
										for(var i = 0; i < r.data.length ; i++)
											
											for (var key in r.data[i])
												{
														$('input[name='+key+']').val(r.data[i][key]);
												}
										
										$('#'+HideDiv).hide();
										$('#'+DisplayDiv).show();
										}
									
									
								},'json');
						
					
					}
				
				
	}
	if(Action == 'Save')
	{
		
		var t = false;
		t = ValidationForm("submitAssetMaintenance");
		
		if(t)
			{
				var x = $('#submitAssetMaintenance').serialize();
				$.post('Asset_Maintenance',x,function (r)
						{
					
							if(r.data)
								{
									bootbox.alert("Maintenance details added successfully.");
									$('.maintenance').trigger( "click" );
								}
								else
									{
										bootbox.alert("Somthing went wrong please try again.");
									}
									
							
						},'json');
				
				
			
			}
		
		
	}
			}});
}