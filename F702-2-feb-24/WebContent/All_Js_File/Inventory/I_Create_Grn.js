
$(function(){
	
	$( "#menu" ).menu();
	
});


function ControlGrnDiv(action , DisplayDiv , HideDiv , FormName , id)
{
	if(action =='Create')
		{	
			$('button[name="update"]').addClass('hideButton');
			$('button[name="save"]').removeClass('hideButton');
			$('input[name="action"]').val('Save');
			EditGrnFun(action , DisplayDiv , HideDiv , FormName , id);
			
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
				var qty_recv = $('input[name="qty_recv"]').val();
				var qty = $('input[name="qty"]').val();
				var remQty = (qty) - (qty_recv);
				$.post('I_Create_Grn',x,function (r){
					
					if(r.data == 1)
						{
							var id_inventory_m = $('input[name="id_inventory_m"]').val();
							var id_inventory = $('input[name="id_inventory"]').val();
							DisplayGrnForParticularInventory(id_inventory_m,qty,id_inventory);
							bootbox.alert("Created Successfully.");
							$('#'+HideDiv).hide();
							$('#'+DisplayDiv).show();
						}
					else
					{
						$('#DisplayGrnForParticularInventory').hide();
						$('#displayInventoryForGrn').show();
						DisplayInventoryForCreateGrn('I_Create_Grn','displayInventoryForGrnClass');
					}
					
				},'json');
			}
		
		
	}
	
	else if(action =='Update')
	{

		var t=false;
		t = ValidationForm(FormName);
		
		if(t)
			{
			$('input[name="action"]').val('Update');
			var qty = $('input[name="qty"]').val();
				var x = $('#'+FormName).serialize();
				
				$.post('I_Create_Grn',x,function (r){
					
					if(r.data > 0)
						{
							var id_inventory_m = $('input[name="id_inventory_m"]').val();
							var id_inventory = $('input[name="id_inventory"]').val();
							DisplayGrnForParticularInventory(id_inventory_m,qty,id_inventory);
							
							bootbox.alert("Updated Successfully.");
							$('#'+HideDiv).hide();
							$('#'+DisplayDiv).show();
						}
					else
					{
						bootbox.alert("Try again.");
					}
					
				},'json');
			}
		
	}
	else if(action =='Modify')
	{
		EditGrnFun(action , DisplayDiv , HideDiv , FormName , id);
		$('button[name="save"]').addClass('hideButton');
		$('button[name="update"]').removeClass('hideButton');
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}

}



function EditGrnFun(action , DisplayDiv , HideDiv , FormName , id)
{
		var id_inventory_m = $('input[name="id_inventory_m"]').val();
		
		$.post('I_Create_Grn',{action : 'Edit' , id_inventory_m : id_inventory_m , id : id},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
							for (var key in r.data[i])
					        {
								
									$('input[name='+key+']').val(r.data[i][key]);
									if(key == 'qty_recv')
									{
										$('input[name='+key+']').attr('readonly', 'readonly');
									}
									if(key == 'remarks')
									{
										$('textarea[name='+key+']').val(r.data[i][key]);
									}
					        }
							 
						}
						if(action == 'Create')
						{
							$('input[name="qty_recv"]').removeAttr('readonly');
							$('input[name="qty_recv"]').val('');
							$('input[name="no_dc"]').val('');
							$('input[name="dt_dc"]').val('');
							$('input[name="dt_grn"]').val('');
							$('textarea[name="remarks"]').val('');
						}
						if(r.data[0].dtDc != '01/01/1900')
							$('input[name="dt_dc"]').val(r.data[0].dtDc);
						else
							$('input[name="dt_dc"]').val('');
						
							if(r.data[0].dtGrn != '01/01/1900')
							$('input[name="dt_grn"]').val(r.data[0].dtGrn);
							else
								$('input[name="dt_grn"]').val('');
						
						$('input[name="dt_inv"]').val(r.data[0].dtInv);
						$('input[name="dt_po"]').val(r.data[0].dtPo);
						$('input[name="id"]').val(id);

						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json');
}

function CreateGrnFun(id ,qty , id_inventory)
{
	$('#displayInventoryForGrn').hide();
	$('#DisplayGrnForParticularInventory').show();
	
	DisplayGrnForParticularInventory(id , qty , id_inventory);
	
}

function DisplayGrnForParticularInventory(id_inventory_m , qty , id_inventory)
{
	
			$.post('I_Create_Grn',{action : 'DisplayGrn' , id_inventory_m : id_inventory_m , id_inventory : id_inventory},function (r){
				
				var list= '<tr class="tableHeader info">'+
				'<td><strong>Invoice Number</strong></td>'+
				'<<td><strong>Invoice Date</strong></td>'+
				'<td><strong>GRN Number</strong></td>'+
				'<<td><strong>GRN Date</strong></td>'+
				'<td><strong>Quantity</strong></td>'+
				'<td><strong><a href="#">Modify Grn</a></strong></td>'+
			'</tr>';
				
			if(r.data.length > 0)
				{
					
					
					for(var i = 0; i < r.data.length ; i++)
					{
					
					params = r.data[i];
					
					
					list = list + '<tr class="success">'+
										'<td><center>'+params.no_inv+'</center></td>'+
										'<td><center>'+params.dtInv+'</center></td>'+
										'<td><center>'+params.no_grn+'</center></td>'+
										'<td><center>'+params.dtGrn+'</center></td>'+
										
										'<td><center>'+params.qty_recv+'</center></td>'+
										'<td><strong><a class="alert" href="#" onclick="ControlGrnDiv(\'Modify\',\'CreateGrnInventory\',\'DisplayGrn\',\'submitGRNdetails\','+params.id_inventory_grn+')"> Modify Grn </a></strong></td>'+
										'</tr>';
					}
					
					
					$('.displayGrnOfInvoice').html(list);
					
					
				
				}
			
			else
				{
					list +='<tr><td colspan="6"><strong><center>Grn is not created yet.</center></strong></td></tr>';
					$('.displayGrnOfInvoice').html(list);
				}
			$('input[name="qty"]').val(qty);
			$('input[name="id_inventory_m"]').val(id_inventory_m);
			$('input[name="id_inventory"]').val(id_inventory);
		},'json');
	
}



function DisplayInventoryForCreateGrn(servletName , ClassNameForDisplayData)
{

	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
			
				$.post(servletName,{action : 'Display' ,dt_frm:dt_frm,dt_to:dt_to},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice Number</strong></td>'+
					'<<td><strong>Invoice Date</strong></td>'+
					'<td><strong>PO Number</strong></td>'+
					'<<td><strong>PO Date</strong></td>'+
					'<<td><strong>Vendor</strong></td>'+
					'<<td><strong>Total Qty</strong></td>'+
					'<<td><strong>Remaining Qty</strong></td>'+
					'<td><strong><a href="#">Create Grn </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_inv+'</center></td>'+
											'<td><center>'+params.dtInv+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											'<td><center>'+params.dtPo+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+params.qty+'</center></td>'+
											'<td><center>'+params.ReminQty+'</center></td>'+
											'<td><strong><a class="alert" href="#" onclick="CreateGrnFun('+params.id_inventory_m+' , '+params.qty+' , '+params.id_inventory+')"> Create Grn </a></strong></td>'+
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
}