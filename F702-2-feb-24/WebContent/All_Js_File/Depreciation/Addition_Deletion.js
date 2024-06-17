function ControlAdditionDeletion(Action , DisplayDiv , HideDiv , HideDiv2 )
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(Action == 'Back')
		{
		$('.addition_Deletion').trigger( "click" );
			
		}
	
	if(Action == 'Next')
	{
				var finId = $('#financeYearForAdditionDeletion').val();
				var t = false;
				t = ValidationForm("submitDisplayAdditionDeletion");
				var x = $('#submitDisplayAdditionDeletion').serialize();
				if(t)
					{
					
					var list = '<option value="">Select</option>';
						$.post('D_Addition_Deletion',x,function (r)
								{
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
										$('input[name="fineId"]').val(finId);
										
										
										
										$('#assetIdForAdditionDeletion').html(list);
										$('#'+HideDiv).hide();
										$('#'+HideDiv2).hide();
										$('#'+DisplayDiv).show();
									}
								else
									{
										Bootbox.alert('No Assets belongs to this group and sub group.');
									}
									
								},'json');
						
					
					}
				
				
	}
	if(Action == 'Next1')
	{
		
				var id_wh = $('#assetIdForAdditionDeletion').val();
				var id_finYear = $('#financeYearForAdditionDeletion').val();
				
				var t = false;
				if(id_wh != '')
					t=true;
				
				if(t)
					{
					
						$.post('D_Addition_Deletion',{action : 'Display' , id_wh : id_wh,id_finYear:id_finYear},function (r)
								{
									if(r.data.length > 0)
										{
										for(var i = 0; i < r.data.length ; i++)
											
											for (var key in r.data[i])
												{
														$('input[name='+key+']').val(r.data[i][key]);
												}
										var list = '<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:10%">Addition / Deletion For The Financial Year : '+r.std_finance+' / '+r.end_finance+'</p></td>';
										
										$('#addDelHeader').html(list);
										
										$('input[name="asset_group_id"]').val(r.data[0].id_grp);
										$('input[name="asset_subgroup_id"]').val(r.data[0].id_sgrp);
										$('input[name="asset_vendor_id"]').val(r.data[0].id_ven_proc);
										$('input[name="assetid"]').val(r.data[0].id_wh);
										$('input[name="assetid1"]').val(r.data[0].id_wh_dyn);
										$('input[name="asset_description"]').val(r.data[0].ds_asst);
										
										$('#'+HideDiv).hide();
										$('#'+HideDiv2).hide();
										$('#'+DisplayDiv).show();
										}
									
									
								},'json');
						
					
					}else
						{
							bootbox.alert('Please select Asset.');
						}
				
				
	}
	if(Action == 'Save')
	{
		
		var t = false;
		t = ValidationForm("submitAdditionDeletiondetails");
		
		if(t)
			{
				var x = $('#submitAdditionDeletiondetails').serialize();
				$.post('D_Addition_Deletion',x,function (r)
						{
					
							if(r.data)
								{
									bootbox.alert("Addition / Deletion successfully happend.");
									$('.addition_Deletion').trigger( "click" );
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