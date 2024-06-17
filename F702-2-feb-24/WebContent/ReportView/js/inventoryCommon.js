$(function(){
	
	$( "#menu" ).menu();
	
});
 

function ControlAssetInventory(action,displayContent,createDetails,formName,servletName) {
	
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		
	
	
	if(action=='Create')
		{
			$('.readbaledata').each(function (){
				
				$(this).removeAttr('readonly', 'readonly');
				if($(this).is("select"))
					{
						$(this).attr("disabled", false);
					}
				
			});
		
		$('button[name="update"]').addClass('hideButton');
		$('input[name="action"]').val('Save');
			$('input[name="id"]').val("0");
			$('#'+formName)[0].reset();
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			$('#createAccessoryInventory').show();
			$('#DisplayAccessoryInventory').hide();
			
		}
	if(action=='Cancel')
		{
			$('#DisplayAccessoryInventory').hide();
			$('#createAccessoryInventory').hide();
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();
			DisplayInventory(servletName,displayContent,createDetails,'','inventoryForDisplay');
		}
	if(action=='Save' || action=='Update')
		{
			if(action == 'Save')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Save');
				}
			else
				{
					$('input[name="action"]').val('Update');
				}
			
			DataInsertUpdateForAssetInventory("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
	
}




function DataInsertUpdateForAssetInventory(action,displayContent,createDetails,formName,servletName)
{
	t=false;
	t = ValidationForm(formName);
	
	if(t)
		{
			t=false;
			var val = $('#bdId').val();
			
			if(val == 'Yes')
				{
					t = ShowRowColumnValidation('Yes','common-hideShow');
				}
			else
				{
				t=true;
				}
			
		}
	
		if(t)
		{
		
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data > 0)
					{
					$('#createAccessoryInventory').hide();
					$('#DisplayAccessoryInventory').show();
			            DisplayAccessoryInventory(servletName,displayContent,createDetails,r.data,'accessoryForDisplayInventory');
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
			},'json');
		
		}
}


function EditAssetInventory(servletName,displayContent,createDetails,id)
{
	
	DisplayAccessoryInventory('I_Inventory','DisplayInventory','createInventory',id,'accessoryForDisplayInventory');
	$('.readbaledata').each(function (){
			
			$(this).attr('readonly', 'readonly');
			if($(this).is("select"))
			{
				$(this).attr("disabled", true);
			}
			
		});
	
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
	$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#DisplayAccessoryInventory').show();
	
		$.post(servletName,{action : 'Edit',id : id},function (r){
				
				if(r.data)
					{
					var t=0;
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								
								if($('select[name='+key+']').is("select") && r.data[i][key] != '')
								{
									
									$('option[value=' + r.data[i][key] + ']').attr('selected',true);
									
								}
								else
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
								if(key == 'ins' && r.data[i][key] =='Yes')
								{
									t=1;
									
								}
								
					         
					        }
							 
						}
						
						$('input[name="action"]').val("Update");
						$('input[name="id"]').val(id);
						$('input[name="dt_inv"]').val(r.data[0].dt_inv);
						$('input[name="dt_po"]').val(r.data[0].dt_po);
						}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'insCommonClass2');
				}	
				
		},'json');
		
}

function DeleteInventory(servletName,displayContent,createDetails,id)
{
		bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
		
			$.post(servletName,{action : 'Delete',id : id},function (r){
				
				if(r.data == 1)
				{
					DisplayInventory(servletName,displayContent,createDetails,'','inventoryForDisplay');
				}
				if(r.data == 2)
				{
					bootbox.confirm("If you delete this , then all aseet will be deleted related to this invoice.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'Delete',id : id , id_inventory_m : '0'},function (r){
								
								if(r.data == 1)
									{
										DisplayInventory(servletName,displayContent,createDetails,'','inventoryForDisplay');
									}
							},'json');
						}
						
					});
				}
				
			},'json');
		}
	}); 
}


function DeleteAssetInventory(servletName,displayContent,createDetails,id)
{
		bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
			id_inventory_m = $('input[name="id"]').val();
			$.post(servletName,{action : 'DeleteAsset',id : id , id_inventory_m : id_inventory_m},function (r){
				
				if(r.data == 1)
				{
					id_m=$('#id').val();
					DisplayAccessoryInventory(servletName,displayContent,createDetails,id_m,'accessoryForDisplayInventory');
				}
			if(r.data == 2)
				{
					bootbox.confirm("This is last record of this Invoice if delete this then invoice also deleted.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'DeleteAsset',id : id , id_inventory_m : '0'},function (r){
								
								if(r.data == 1)
									{
										ControlAssetInventory('Cancel',displayContent,createDetails,'',servletName);
									}
							},'json');
						}
						
					});
				}
				
				
			},'json');
		}
	}); 
}

function CreateAccessoryInventory(action,displayContent,createDetails,no_asset)
{
	
		if(action=='Create')
		{
			$('button[name="update"]').addClass('hideButton');
			$('button[name="save"]').removeClass('hideButton');
			
			$('.resetAcc').each (function(){
				$(this).val('');
				});
			$('#'+createDetails).show();
		}
		else if(action=='Cancel')
		{
			$('#'+createDetails).hide();
			$('#'+displayContent).show();
		}
		if(action=='Modify')
		{
$.post('I_Inventory',{action : 'Edit',invoiceId : no_asset},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								$('input[name='+key+']').val(r.data[i][key]);
					         
					        }
							 
						}
						
						$('input[name="invoiceId"]').val(no_asset);
						
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				
				
		},'json');
			$('button[name="update"]').removeClass('hideButton');
			$('button[name="save"]').addClass('hideButton');
			$('#'+createDetails).show();
		}
}

function DisplayAccessoryInventory(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{
	
	$.post(servletName,{action : 'Display' , no_inv : no_inv},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Asset/Accessory</strong></td>'+
								'<<td><strong>Quantity</strong></td>'+
								'<<td><strong>Manufacturer</strong></td>'+
								'<td><strong>Unit Price</strong></td>'+
								'<td><strong>Total Unit Price</strong></td>'+
								'<<td><strong>Total  Price</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_acc_asset+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tt_un_prc+'</center></td>'+
									'<td><center>'+params.tt+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="CreateAccessoryInventory(\'Modify\',\'DisplayAccessoryInventory\',\'createAccessoryInventory\','+params.id_inventory+')"> Modify </a><a class="alert" href="#" onclick="DeleteAssetInventory(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inventory+')"> Delete</a></strong></td>'+
									'</tr>';
				}
			
			
				$('.accessoryForDisplayInventory').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');


}

function DisplayInventory(servletName,displayContent,createDetails,no_inv,DisplayTableClass)
{

	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
			
				$.post(servletName,{action : 'Display' , no_inv : no_inv,dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice Number</strong></td>'+
					'<<td><strong>Invoice Date</strong></td>'+
					'<td><strong>PO Number</strong></td>'+
					'<<td><strong>PO Date</strong></td>'+
					
					'<<td><strong>Vendor</strong></td>'+
					'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
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
											'<td><strong><a class="alert" href="#" onclick="EditAssetInventory(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inventory_m+')"> Modify </a><a class="alert" href="#" onclick="DeleteInventory(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inventory_m+')"> Delete</a></strong></td>'+
											'</tr>';
						}
					
					
						$('.'+DisplayTableClass).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available..</center></strong></td></tr>';
						$('.'+DisplayTableClass).html(list);
					}
				
			},'json');
		

}

