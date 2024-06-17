
function ControlDivForReceivAsset(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Create')
		{	
		EditReceiveAssetFun(action , DisplayDiv , HideDiv , FormName , id);
		
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
		}
	else if(action =='Next')
	{	
		var t =false;
t = ValidationForm(FormName);
		if(t)
			{
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			$('#DisplayAssetDataForView').show();
			DisplayAssetForReceiveAsset(FormName);
			}
		
		
	}
	else if(action =='Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	
	else if(action =='Save')
	{
		
		var t=false;
		t = ValidationForm(FormName);
		
		if(t)
			{
				var x = $('#'+FormName).serialize();
				
				$.post('Receive_Asset',x,function (r){
					
					if(r.data == 1)
						{
							DisplayAssetForReceiveAsset(FormName);
							bootbox.alert("Created Successfully.");
							$('#'+HideDiv).hide();
							$('#'+DisplayDiv).show();
						}
					else
						{
						bootbox.alert("Try Again.");
						}

				},'json');
			}
			}
		
			}});
}



function EditReceiveAssetFun(action , DisplayDiv , HideDiv , FormName , id)
{
	SessionCheck(function (ses){		
		if(ses)
			{	
	
		$.post('Receive_Asset',{action : 'Edit' , id : id},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
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
					        }
						}
						
						$('input[name="id_gp"]').val(id);
						$('input[name="id_iut"]').val(r.data[0].id_iut);
						$('input[name="type_tran"]').val(r.data[0].type_tran);
						$('input[name="id_wh"]').val(r.data[0].id_wh);
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json');
			}});
}

function CreateGrnFun(id ,qty , id_inv)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$('#displayInvoiceForGrn').hide();
	$('#DisplayGrnForParticularInvoice').show();
	
	DisplayGrnForparticularInvoice(id , qty , id_inv);
			}});
}

function DisplayAssetForReceiveAsset(FormName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var x = $('#'+FormName).serialize();
			$.post('Receive_Asset',x,function (r){
				
				var list= '<tr>'+
					'<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:370px">Select AssetId To Receive</p></td>'+
					'</tr>'+
					'<tr class="tableHeader info">'+
				'<td><center><strong>Asset ID</strong></center></td>'+
				'<td><center><strong>Transfer Type</strong></center></td>'+
				'<td><center><strong>Asset Name</strong></center></td>'+
				'<td><center><strong>Serial No</strong></center></td>'+
				'<td><strong><a href="#">Receive Asset</a></strong></td>'+
			'</tr>';
				
			if(r.data.length > 0)
				{
					
					
					for(var i = 0; i < r.data.length ; i++)
					{
					
					params = r.data[i];
					
					list = list + '<tr class="success">'+
										'<td><center>'+params.id_wh_dyn+'</center></td>'+
										'<td><center>'+params.type_tran+'</center></td>'+
										'<td><center>'+params.ds_pro+'</center></td>'+
										'<td><center>'+params.no_mfr+'</center></td>'+
										'<td><strong><a class="alert" href="#" onclick="ControlDivForReceivAsset(\'Create\',\'DisplayAssetDataForReceiveAsset\',\'DisplayAssetDataForReceive\',\'submitReciveAsset\','+params.id_gp+')"> Receive Asset </a></strong></td>'+
										'</tr>';
					}
					
					
					$('.DisplayAssetDataForReceiveAssetClass').html(list);
					
					
				
				}
			
			else
				{
					list +='<tr><td colspan="3"><strong><center>Grn is not created yet.</center></strong></td></tr>';
					$('.DisplayAssetDataForReceiveAssetClass').html(list);
				}
			
		},'json');
			}});
}


/*
function DisplayInvoiceForCreateGrn(servletName , ClassNameForDisplayData)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var dt_frm =$('input[name="dt_frm"]').val();
			var dt_to =$('input[name="dt_to"]').val();
			//var invType =$('select[name="invType"]').val();
			
				$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice Number</strong></td>'+
					'<td><strong>Invoice Date</strong></td>'+
					'<td><strong>PO Number</strong></td>'+
					'<td><strong>PO Date</strong></td>'+
					'<td><strong>Vendor</strong></td>'+
					'<td><strong>Total Qty</strong></td>'+
					'<td><strong>Remaining Qty</strong></td>'+
					'<td><strong><a href="#">Create Grn </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_inv+'</center></td>'+
											'<td><center>'+params.dt_inv+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											'<td><center>'+params.dt_po+'</center></td>'+
											
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+params.qty+'</center></td>'+
											'<td><center>'+params.ReminQty+'</center></td>'+
											'<td><strong><a class="alert" href="#" onclick="CreateGrnFun('+params.id_inv_m+' , '+params.qty+' , '+params.id_inv+')"> Create Grn </a></strong></td>'+
											'</tr>';
						}
					
						$('.'+ClassNameForDisplayData).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+ClassNameForDisplayData).html(list);
					}
				
			},'json');
			}});
}*/