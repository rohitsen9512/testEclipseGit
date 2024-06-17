
function ControleAddToStoreForInventory(Action , DisplayDiv , HideDiv  , id_inventory_grn)
{	
	if(Action == 'Back')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
		
	}
	
	if(Action == 'Addtostore')
	{
		
		EditConsumableInventory('I_Add_To_Store',id_inventory_grn);
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();
		
	}
	if(Action == 'Save')
	{
		
		var t = false;
		t = ValidationForm("AddToInventoryWH");
		
		var x = $('#AddToInventoryWH').serialize();
		if(t)
			{
				
				$.post('I_Add_To_Store',x,function (r)
						{
					
							if(r.data)
								{
									t=true;
								}
								else
									{
										t=false;
										bootbox.alert("Data is not inserted in data base please try again.");
									}
									
							if(t)
							{
								DisplayConsumableInventoryForAddToStore("I_Add_To_Store");
								$('#'+HideDiv).hide();
								$('#'+DisplayDiv).show();
							}
							
						},'json');
				
				
			
			}
	}
}


function EditConsumableInventory(servletName,id_inventory_grn)
{
	
	
		$.post(servletName,{action : 'Edit',id_inventory_grn : id_inventory_grn},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								
								if($('select[name='+key+']').is("select"))
									{
									
										$('option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
								else
									{
										$('input[name='+key+']').val(r.data[i][key]);
									}
								
					         
					        }
							 
						}
						
						
						$('input[name="id_inventory"]').val(r.data[0].id_inventory);
						$('input[name="id_inventory_m"]').val(r.data[0].id_inventory_m);
						$('input[name="id_grp"]').val(r.data[0].id_grp1);
						$('input[name="id_sgrp"]').val(r.data[0].id_sgrp1);
						$('input[name="id_loc"]').val(r.data[0].id_loc);
						$('input[name="id_subl"]').val(r.data[0].id_subl);
						$('input[name="id_inventory_grn"]').val(id_inventory_grn);
						$('input[name="qty_recv"]').val(r.data[0].qty_recv);
						$('input[name="extra_prc"]').val('0');
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				
				
		},'json');
		
}


function DisplayConsumableInventoryForAddToStore(servletName)
{
	var dt_frm = $('input[name="dt_frm"]').val();
	var dt_to = $('input[name="dt_to"]').val();
	
				$.post(servletName,{action : 'Display' , dt_frm : dt_frm , dt_to : dt_to } , function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Invoice No</strong></td>'+
					'<<td><strong>Quantity</strong></td>'+
					'<<td><strong>Manufacturer</strong></td>'+
					'<td><strong>Unit Price</strong></td>'+
					'<td><strong>Total Unit Price</strong></td>'+
					'<<td><strong>Total  Price</strong></td>'+
					'<td style="width: 155px;"><strong><a href="#">Add To Store </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_inv+'</center></td>'+
						'<td><center>'+params.qty_recv+'</center></td>'+
						'<td><center>'+params.mfr+'</center></td>'+
						'<td><center>'+params.un_prc+'</center></td>'+
						'<td><center>'+params.tt_un_prc+'</center></td>'+
						'<td><center>'+params.tt+'</center></td>'+
						'<td><strong><a class="alert" href="#" onclick="ControleAddToStoreForInventory(\'Addtostore\',\'displayInventoryAddToStore\',\'InventoryAddToStore\','+params.id_inventory_grn+')"> Add To Store </a></strong></td>'+
						'</tr>';
						}
					
					
						$('.InventoryAddToStoreForDisplay').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="5"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.InventoryAddToStoreForDisplay').html(list);
					}
					
				
			},'json');
		

}
