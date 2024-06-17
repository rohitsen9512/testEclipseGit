function ControlInsuranceUpdate(Action , DisplayDiv , HideDiv , HideDiv2 )
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(Action == 'Back')
		{
		$('.updateInsurance').trigger( "click" );
			
		}
	
	if(Action == 'Next')
	{
				var finId = $('#finDataForInsuranceUpdate').val();
				var id_grp = $('#grpDataForInsuranceUpdate').val();
				var t = false;
				t = ValidationForm("submitInsuranceUpdateNext");
				var x = $('#submitInsuranceUpdateNext').serialize();
				if(t)
					{
					
					var list = '<tr><td colspan="4" class="tableHeader"><center><p class="tableHeaderContent">Insurance Details</p></center></td></tr>'+
						'<tr class="info">'+
							'<td><strong>S No.</strong></td>'+
							'<td ><strong>Asset SubGroups</strong></td>'+
							'<td><strong>Asset Max Price</strong></td>'+
							'<td><strong>Insurance Value<font color="red">*</font></strong></td>'+
						'</tr>';
						
					$.post('Insurance_Update',x,function (r)
								{
						var j=0;
									if(r.data.length > 0)
										{
											t=true;
											for(var i = 0; i < r.data.length ; i++)
											{
												params = r.data[i];
												j=i+1;
												list = list + 
												'<tr>'+
												'<td><strong>'+j+'</strong></td>'+
												'<input type="hidden" name="id_sgrp'+i+'" value="'+params.id_sgrp+'">'+
												
												'<td ><strong>'+params.nm_s_assetdiv+'</strong></td>'+
												'<td><strong><input type="text" name="maxPrc'+i+'" value="'+params.maxAssetVal+'" readonly class="common-validation" data-valid="mand"></strong></td>'+
												'<td><strong><input type="text" name="insPrc'+i+'" value="" class="common-validation" data-valid="num"></strong></td>'+
											'</tr>';
											}
											
										}
									else
									{
										t=false;
										bootbox.alert('No Assets belongs to this group and financial year.');
										
									}
									if(t)
									{
										$('input[name="id_finance"]').val(finId);
										$('input[name="id_grp"]').val(id_grp);
										
										list = list + '<tr>'+
										'<td colspan="4">'+
										'<input type="hidden" name="count" value="'+j+'">'+
										'<center><button type="button" class="btn btn-primary" onclick="ControlInsuranceUpdate(\'Save\',\'createInsuranceUpdates\',\'displayInsuranceUpdates\',\'\')">Save</button></center>'+
										'</td>'+
										'</tr>';
										
										$('#grpdataForInsurance').html(list);
										$('#'+HideDiv).hide();
										$('#'+HideDiv2).hide();
										$('#'+DisplayDiv).show();
									}
								
									
								},'json');
						
					
					}
				
				
	}
	
	if(Action == 'Save')
	{
		
		var t = false;
		t = ValidationForm("submitInsuranceUpdates");
		
		if(t)
			{
				var x = $('#submitInsuranceUpdates').serialize();
				$.post('Insurance_Update',x,function (r)
						{
					
							if(r.data == 1)
								{
									bootbox.alert("Insurance Updated successfully.");
									$('.updateInsurance').trigger( "click" );
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