$(function(){
	
	$( "#menu" ).menu();
	
});
function ControCongiguration(asstType){
	asstTyp = asstType.value;
	if(asstTyp != '')
		if(asstTyp == 'NIT'){
			$('select[name="config"] option[value="No"]').attr('selected',true);
			$('#ConfigurableId').val('No');
			$('#ConfigurableId').attr("disabled", true);
			$('input[name="st_config"]').val('No');
			
	}else{
		$('select[name="config"] option[value="Yes"]').attr('selected',true);
		$('#ConfigurableId').attr("disabled", false);
		$('#ConfigurableId').val('Yes');
		$('input[name="st_config"]').val('Yes');
	}
	
}

function SetConfigValue(val){
	if(val.value == 'Yes')
		$('input[name="st_config"]').val('Yes');
	else
		$('input[name="st_config"]').val('No');
}

function GenerateAssetName(){
	var assetName='';
	var id_sgrp='';
	id_sgrp = $('#subGroupDataForInvoice').val();
	/*var model = $('input[name="model"]').val();
	var mfr = $('input[name="mfr"]').val();
	*/
	if(id_sgrp != ''){
		
		$.post('M_Subasset_Div',{action:'DisplayName',id:id_sgrp},function (r){
			if(r.data){
					assetName = r.data[0].nm_s_assetdiv;
					
					$('input[name="ds_pro"]').val(assetName);
				}
			
		},'json');
	}
	else{
		$('input[name="ds_pro"]').val(assetName);
	}
		
	
}








function DeleteAsset(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{	
	bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
			id_inv_m = $('input[name="id"]').val();
			$.post(servletName,{action : 'DeleteAsset',id : id , id_inv_m : id_inv_m},function (r){
				
				if(r.data == 1)
				{
					id_m=$('#id').val();
					DisplayAccessory(servletName,displayContent,createDetails,id_m,'accessoryForDisplay',function(status){
						
					});
				}
			if(r.data == 2)
				{
					bootbox.confirm("This is last record of this Invoice if delete this then invoice also deleted.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'DeleteAsset',id : id , id_inv_m : '0'},function (r){
								
								if(r.data == 1)
									{
										ControlAsset('Cancel',displayContent,createDetails,'',servletName);
									}
							},'json');
						}
						
					});
				}
				
				
			},'json');
		}
	}); 
			}
});
}

function CreateAccessory(action,displayContent,createDetails,no_asset,id_grp)
{
SessionCheck(function (ses){
		
		if(ses)
			{
		if(action=='Create')
		{
			$('button[name="update"]').addClass('hideButton');
			$('button[name="save"]').removeClass('hideButton');
			
			$('#subGroupDataForInvoice').html('<option value="">Select</option>');
			//$('#modelDataForInvoice').html('<option value="">Select</option>');
			//$('select[name="config"] option[value="No"]').attr('selected',true);
			$('.resetAcc').each (function(){
				$(this).val('');
				});
			$('.resetAcc1').each (function(){
				$(this).val('0.0');
				});
			$('select[name="id_tax"] option[value="0"]').attr('selected',true);
			$('input[name="qty"]').val('1');
			$('#'+createDetails).show();
		}
		else if(action=='Cancel')
		{
			$('#'+createDetails).hide();
			$('#'+displayContent).show();
		}
		if(action=='Modify')
		{
			
			SubDropDownDataDisplay('0','subGroupDataForInvoice','M_Subasset_Div',function (status){
				if(status)
				{
					
					SubDropDownDataDisplay('0','modelDataForInvoice','M_Model',function (status){
						if(status)
						{
			
$.post('A_Invoice',{action : 'Edit',invoiceId : no_asset},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								if($('select[name='+key+']').is("select"))
								{
									$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									
								}
							else
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						
						$('input[name="invoiceId"]').val(no_asset);
						$('input[name="id"]').val(r.data[0].id_inv_m);
						
						
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				
				
		},'json');
			$('button[name="update"]').removeClass('hideButton');
			$('button[name="save"]').addClass('hideButton');
			$('#'+createDetails).show();
	}});
}});
		}
	
			}
		
});
}

function DisplayAccessory(servletName,displayContent,createDetails,no_inv,DisplayTableClass,callback)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'Display' , no_inv : no_inv},function (r){
		var t=true;
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong><center>Asset Name</center></strong></td>'+
								'<td><strong><center>Quantity</center></strong></td>'+
								'<td><strong><center>Manufacturer</center></strong></td>'+
								'<td><strong><center>Unit Price</center></strong></td>'+
								'<td><strong><center>Total Unit Price</center></strong></td>'+
								'<td><strong><center>Total  Price</center></strong></td>'+
								'<td style="width: 205px;"><strong><center><a href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tt_un_prc+'</center></td>'+
									'<td><center>'+params.tt+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="CreateAccessory(\'Modify\',\'displayAccessory\',\'createAccessory\','+params.id_inv+','+params.id_grp+')"> Modify </a><a class="alert" href="#" onclick="DeleteAsset(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_inv+')"> Delete</a></strong></td>'+
									'</tr>';
				}
			
				$('input[name="id"]').val(r.data[0].id_inv_m);
				$('.accessoryForDisplay').html(list);
			
			}
		else
			{
			t=false;
				alert("Try again.");
			}
		callback(t);
	},'json');

			}
});
}








function DropDownDataDisplayForItem(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'DropDownResult'},function (r){
		
		if(r.data)
			{
			t=true;
			
				list= '<option value=""> Select</option>';
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				
				for (var key in r.data[i])
		        {
					id=r.data[i][key];
					break;
		        }
				for (var key in r.data[i])
		        {
					val=r.data[i][key];
					
		        }
				list = list + '<option value="'+val+'"> '+id+'</option>';
				}
				
				if(dropDownId=='prod'){
					
						$('.prod').html(list);
					
				}
				else
					$('#'+dropDownId).html(list);
					
				
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}
var options = {
		url: "InvoiceScanFile/Model.json",
		getValue: "nm_model",
		template: {
			type: "custom",
			method: function(value, item) {
				return  value ;
			}
		},
		list: {
			maxNumberOfElements: 50,
			match: {
				enabled: true
			}
		},

	};


