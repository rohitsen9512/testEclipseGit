function SubDropDownDataDisplayjobwork(ids,dropDownId,servletName,callback)
{
	var t=false;
	
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			$.post(servletName,{action:'DropDownResultpoasset' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
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
									list = list + '<option value="'+id+'"> '+val+'</option>';
									}
							}
					
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				callback(t);
			},'json');
		}

}
function SubDropDownDataDisplayjobworkprocess(ids,dropDownId,servletName,callback)
{
	debugger;
	var t=false;
	
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			$.post(servletName,{action:'Dropwornprocess' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
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
									list = list + '<option value="'+id+'"> '+val+'</option>';
									}
							}
					
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				callback(t);
			},'json');
		}

}

function DisplaySubDropDownDatajobworkprocess(ids,dropDownId,servletName,callback)
{
	var t=false;
	
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			$.post(servletName,{action:'Dropwornprocess' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
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
									list = list + '<option value="'+id+'"> '+val+'</option>';
									}
							}
					
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				callback(t);
			},'json');
		}

}
function changeEventHandlerprodforjobwork(event) {
	debugger;
	var domElement =$(event.target || event.srcElement);
	var id_prod = $('select[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
	var nm_prod = $('select[name="id_po_asset'+domElement.attr('patelUnPrc')+'"]').val();
	
	var id_route_card_material = 'id_route_card_material'+domElement.attr('patelUnPrc')+'';
	var id_req = 'id_req'+domElement.attr('patelUnPrc')+'';
	//alert(id_route_card_material);
	SubDropDownDataDisplayForRequest4(nm_prod,id_route_card_material,'Material_Request_Planning',function (status){
		if(status)
		{
			
		debugger;
    $.post('JobWork',{action : 'GetPODetails', id:nm_prod},function (r){
		
		if(r.data)
			{
			
			params = r.data[0];
		console.log(params.id_route_card_material);
			    $('select[name="id_route_card_material'+domElement.attr('patelUnPrc')+'"]').val(params.id_route_card_material);
 			 $('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax1);
                $('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax2);
				$('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val1);
				$('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val2);
				$('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val(params.id_prod);
				$('select[name="id_uom'+domElement.attr('patelUnPrc')+'"]').val(params.id_uom);
				$('input[name="po_qty'+domElement.attr('patelUnPrc')+'"]').val(params.qty);
				$('input[name="rem_qty'+domElement.attr('patelUnPrc')+'"]').val(params.jw_rem_qty);
				$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val(params.un_prc);
				
				
				var id_prod=$('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
				var id_route_card_material=$('select[name="id_route_card_material'+domElement.attr('patelUnPrc')+'"]').val();

				SubDropDownDataDisplayForRequestNO(id_prod,id_route_card_material,id_req,'Material_Request_Planning',function (status){
					if(status)
					{
						
					}});
			}
		
	},'json');
		
		}});
  }

function SubDropDownDataDisplayForRequest4(ids,dropDownId,servletName,callback)
{
	//alert(dropDownId);
	//alert(ids);
	var t=false;
	
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	//alert(id);
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			$.post(servletName,{action:'DropDownResult3' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
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
							}
						
						if(dropDownId == 'routecardforjw')		
							$('.routecardforjw').html(list);
						if(dropDownId == 'id_route_card_material')		
							$('.routecardforjw').html(list);
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				callback(t);
			},'json');
		}

}

function SubDropDownDataDisplayForRequestNO(ids1,ids2,dropDownId,servletName,callback)
{
	
	//alert(dropDownId);
	var t=false;
	
	var id1=ids1.value;
	var id2=ids2.value;
	if(ids1.value == undefined)
		{
		id1 = ids1;
		id2 = ids2;
		}
	//alert(id1);
	//alert(id2);
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids1.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			$.post(servletName,{action:'DropDownResultForReqNO' , id1 : id1,id2 : id2},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
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
									list = list + '<option value="'+id+'"> '+val+'</option>'; 
									}
							}
						
						if(dropDownId == 'routecardforjw')		
							$('.routecardforjw').html(list);
						if(dropDownId == 'id_route_card_material')		
							$('.routecardforjw').html(list);
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				callback(t);
			},'json');
		}

}
function calculateTotjw(event, name,update){
	
	//alert("hii");
//debugger;
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
	
	var rem_qty = $('input[name="rem_qty'+domElement.attr('patelUnPrc')+'"]').val();
	if(update=='update'){
		rem_qty=$('input[name="po_qty'+domElement.attr('patelUnPrc')+'"]').val();
	}
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		if(parseFloat(qty)<=parseFloat(rem_qty)){
				
	 var id_tax1 = $('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val();
	if(id_tax1 != '0')
	TaxCalculation(event,"id_tax1","tax_val1",function (status){
		if(status)
			{
				var id_tax2 = $('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val();
				if(id_tax2 != '0')
				TaxCalculation(event,"id_tax2","tax_val2",function (status){
					if(status)
						{
						
						}});
			}});
	
		}else{
			$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0);
		alert("Receive Quantity should be less than or equal to  Remaining PO Quantity.");
	}
	}

	else
		{
			alert('Invalid number.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}


function dispalyjwLineItemDynamically(action,callback){
	
	if(action == 'New')
	{
		//alert("hello");
		var list ='<tr>'+
			'<td colspan="17" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
		'<tr class="tableHeader info">'+
		
			'<td><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
			//'<td><strong><center>Part Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
			/*'<td ><strong><center>Description<a href=#></a></center></strong></td>'+*/
			'<td ><strong><center>UOM<a href=#></a></center></strong></td>'+
			'<td><strong><center>Process Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Remaining Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Issue Number<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>JWPO Price<a href=#></a></center></strong></td>'+
			'<td ><strong><center>UOM<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
			
		'</tr>';
		
		for(var i = 0; i < 10 ; i++)
		{
			list+='<tr>'+
			' <td style="display:none;">'+
				
			'<td><center>'+
			'<select id="text"  name="id_po'+i+'" patelPo="'+i+'" id="id_po'+i+'" style="width:60px;text-align: right;"  class="common-validation commonPo " patelUnPrc="'+i+'" onchange="SubDropDownDataDisplayjobwork(this,\'id_po_asset'+i+'\',\'JobWork\');"  >'+
			'<option value=""> Select </option>'+
			'</select>'+
			'</center></td>'+
			
			'<td><center>'+
			'<select id="id_po_asset'+i+'" name="id_po_asset'+i+'"  patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation prodforgrn" onchange="changeEventHandlerprodforjobwork(event);" >'+
			'<option value=""> Select </option>'+
			'</select>'+
			'</center></td>'+
			'<input type="hidden" name="id_prod'+i+'"  value="">'+	
			//'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'" style="padding: 0px;width:80px" class="common-validation resetAcc " patelUnPrc="'+i+'"  onchange="changeEventHandlercode(event);"></td>'+
			/*'<td><center><input type="text" maxlength="500" name="description'+i+'"  value="" class="common-validation" ></center></td>'+*/
			
			'<td><center>'+
			'<select style="width: 100px;" name="id_uom'+i+'"  patelUnPrc="'+i+'" taxName="" class="common-validation uomforSales" readonly>'+
			'</select>'+
			'</center></td>'+
			
			//'<td><input type="text" name="id_uom'+i+'"  style="width:60px" class="common-validation resetAcc " patelUnPrc="'+i+'" onchange="changeEventHandlerprod(event);" readonly></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_route_card_material'+i+'" id="id_route_card_material'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation routecardforjw" readonly>'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="po_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			
			'<td><center>'+
			'<select  name="id_req'+i+'" patelPo="'+i+'" id="id_req'+i+'" style="width:60px;text-align: right;"  class="common-validation RequisitionForJW " patelUnPrc="'+i+'" onchange="getPlanQty(event)"  >'+
			'<option value=""> Select </option>'+
			'</select>'+
			'</center></td>'+
			
			'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation planQty" patelUnPrc="'+i+'"  onchange="calculateTotjw(event,\'qty\')" class="patelUnPrc data-valid="num" readonly></center></td>'+
            '<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotjw(event,\'un_prc\')" class=" common-validation" data-valid="num" readonly ></center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_uom1'+i+'"  patelUnPrc="'+i+'" taxName="" class="common-validation uomforSales" >'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text" name="un_prc1'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotjw(event,\'un_prc1\')" class=" common-validation" data-valid="num"  ></center></td>'+
			'<td><input type="text" name="others'+i+'" value="0" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTotjw(event,\'others\')" class="common-validation" ></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax1'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax2'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTotjw(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
			'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTotjw(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
			'<td><center><input type="text"  name="discount'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTotjw(event,\'discount\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
			'<td><center><input type="text"  name="gr_tot'+i+'" value="0.0" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
			//'<td><center><strong><a class="alert" href="#" onclick="DeleteAssetForGRN(\'P_Sales_Order\',\''+displayContent+'\',\''+createDetails+'\','+params.id_asset+')"> Delete</a></strong></center></td>'+
			'<input type="hidden" name="count" value="'+i+'">'+	
			'<input type="hidden" name="id_prod'+i+'" value="">'+
			
		'</tr>';
			
		}
		
		$('input[name="itemCount"]').val('10');
		$('input[name="grand_tot1"]').val('0.00');		
		
		$('#accessoryDetails1').html(list);
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				DropDownDataDisplay("M_Tax","taxDataForDropDown12",function (status){
					if(status)
					{
						DropDownDataDisplay("M_UOM","uomforSales",function (status){
							if(status)
							{
								/*DropDownDataDisplayForReq("Material_Request_Planning","RequisitionForJW",function (status){
									if(status)
									{*/
										for(var i=0;i<r.data.length;i++)
										{
											console.log(r.data.length);
											params=r.data[i];
											for (var key in r.data[i])
											{
								
											if(key == 'id_uom'){
													$('select[name=id_uom'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
					       
												}
												
												if(key == 'id_route_card_material'){
												
													$('select[name=id_route_card_material'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
					       
												}
												
								
											}
										}
									//}});
								
							}});
					}});
				
			}});
	}
	else{
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr>'+
		
		'<td><center>'+
		'<select  name="id_po'+i+'" id="id_po'+i+'" style="width:60px;text-align: right;"  class="common-validation commonPo" patelUnPrc="'+i+'" onchange="SubDropDownDataDisplayjobwork(this,\'id_po_asset'+i+'\',\'JobWork\');"  >'+
		'<option value=""> Select </option>'+
		'</select>'+	
		'</center></td>'+
		
		'<td><center>'+
		'<select id="id_po_asset'+i+'" name="id_po_asset'+i+'" patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation " onchange="changeEventHandlerprodforjobwork(event);" >'+
		'<option value=""> Select </option>'+
		'</select>'+
		'</center></td>'+
		'<input type="hidden" name="id_prod'+i+'"  value="">'+	
		//'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'" style="padding: 0px;width:80px" class="common-validation resetAcc " patelUnPrc="'+i+'" onchange="changeEventHandlercode(event);"></td>'+
		/*'<td><center><input type="text" maxlength="500" name="description'+i+'"  value="" class="common-validation" ></center></td>'+*/
		'<td><center>'+
		'<select style="width: 100px;" name="id_uom'+i+'" id="id_uom'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation uomforSales" readonly>'+
		'</select>'+
		'</center></td>'+
		
		//'<td><input type="text" name="uom'+i+'"  style="width:60px" class="common-validation resetAcc " patelUnPrc="'+i+'" readonly></td>'+
		'<td><center>'+
		'<select style="width: 100px;" name="id_route_card_material'+i+'"  id="id_route_card_material'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation routecardforjw" readonly>'+
		'</select>'+
		'</center></td>'+
		
		'<td><center><input type="text"  name="po_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
		'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
		'<td><center>'+
			'<select  name="id_req'+i+'" patelPo="'+i+'" id="id_req'+i+'" style="width:60px;text-align: right;"  class="common-validation RequisitionForJW " patelUnPrc="'+i+'" onchange="getPlanQty(event)"  >'+
			'<option value=""> Select </option>'+
			'</select>'+
			'</center></td>'+
		'<td><center><input type="text"  name="qty'+i+'" style="width:60px;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onchange="calculateTotjw(event,\'qty\')" class="patelUnPrc data-valid="num" ></center></td>'+
'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;"  class="patelUnPrc common-validation" data-valid="num" readonly></center></td>'+        
'<td><center>'+
		'<select style="width: 100px;" name="id_uom1'+i+'" id="id_uom1'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation uomforSales" >'+
		'</select>'+
		'</center></td>'+
		'<td><center><input type="text" name="un_prc1'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotjw(event,\'un_prc1\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
		'<td><input type="text" name="others'+i+'" value="0" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTotjw(event,\'others\')" class="common-validation" ></td>'+
		'<td><center>'+
		'<select style="width: 100px;" name="id_tax1'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')"  >'+
		'</select>'+
		'</center></td>'+
		'<td><center>'+
		'<select style="width: 100px;" name="id_tax2'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" >'+
		'</select>'+
		'</center></td>'+
		'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTotjw(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
		'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTotjw(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
		'<td><center><input type="text"  name="discount'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTotjw(event,\'discount\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
		'<td><center><input type="text"  name="gr_tot'+i+'" value="0.0" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+	
		//'<td><center><strong><a class="alert" href="#" onclick="DeleteAssetForGRN(\'P_Sales_Order\',\''+displayContent+'\',\''+createDetails+'\','+params.id_asset+')"> Delete</a></strong></center></td>'+
		
		
		
		'<input type="hidden" name="count" value="'+i+'">'+	
		'<input type="hidden" name="id_prod'+i+'" value="">'+
	'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('#accessoryDetails1').append(list);
	
	
	DropDownDataDisplay("M_Tax","id_tax1"+i,function (status){
		if(status)
		{
			DropDownDataDisplay("M_Tax","id_tax2"+i,function (status){
				if(status)
				{
					DropDownDataDisplay("M_UOM","id_uom"+i,function (status){
						if(status)
						{
							DropDownDataDisplay("M_UOM","id_uom1"+i,function (status){
						if(status)
						{
							var id_ven= $('select[name="id_ven"]').val();
							debugger;
							if(id_ven !='')
								SubDropDownDataDisplayForjobwork(id_ven,"id_po"+i,'JobWork');
							/*DropDownDataDisplay("M_Prod_Cart","nm_prod"+i,function (status){
								if(status)
								{
									
								}});*/
							
							
						}});
				}});
				}});
			
		}});
	
	
	
	
	}	
	
	$( ".poDatepicker2" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_so=$('.poDatepicker').val();
	    	  var dt_so1=$('.poDatepicker').val();
	    	  var dt_delv=$(this).val();
	    	  if(dt_so == '')
	    		  {
	    		  	alert('First filled the P.O date.');
	    		  	$('.poDatepicker').focus();
	    		  	$('.poDatepicker').addClass('error');
	    		  	$('.poDatepicker').val('');
	    		  	$(this).val('');
	    		  	exit(0);
	    		  }
	    	  else
	    		  {
	    		  var temp_strt = dt_so.split("/");
					 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						
					var temp_end = dt_delv.split("/");
					var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						
					dt_so = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
					dt_delv = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
	    		  if(dt_so > dt_delv)
	    			  {
	    			  	alert('Delivery date should be greater or equal to P.O date : '+dt_so1);
	    			  	$(this).focus();
	    			  	$(this).val('');
	    			  	$(this).addClass('error');
	    			  	exit(0);
	    			  }
	    		  
	    		  }
	    	  
	      }
	    });
	
	
	
}

function DropDownDataDisplayForReq(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'DropDownResultForReq'},function (r){
		
		if(r.data)
			{
			t=true;
			if(servletName == 'M_Tax')
				 list= '';
			else 
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
			
			if(dropDownId == 'taxDataForDropDown11')		
				$('.patelTax').html(list);
			else if(dropDownId == 'storeLocationID')
				$('.storeLocation').html(list);
			else if(dropDownId == 'RequisitionForJW')
				$('.RequisitionForJW').html(list);
			else if(dropDownId == 'uomforSales')
				$('.uomforSales').html(list);
			else if(dropDownId == 'routecardforjw')
				$('.routecardforjw').html(list);
			else if(dropDownId == 'uomforBOM')
				$('.uomforBOM').html(list);
			else if(dropDownId == 'prodforgrn')
				$('.prodforgrn').html(list);
			else if(dropDownId == 'poforgrn')
				$('.poforgrn').html(list);
			else if(dropDownId == 'exchgangeforcustomer')
				$('.exchgangeforcustomer').html(list);
			else if(dropDownId == 'commonPo')
				$('.commonPo').html(list);
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

function changeEventHandlerprodforGRN(event) {
	var domElement =$(event.target || event.srcElement);
	var nm_prod = $('select[name="id_po_asset'+domElement.attr('patelUnPrc')+'"]').val();
	
    $.post('Direct_Invoice',{action : 'GetPODetails', id:nm_prod},function (r){
		
		if(r.data)
			{
			
			
			params = r.data[0];
			
			
				$('select[name="id_route_card_material'+domElement.attr('patelUnPrc')+'"]').val(params.id_route_card_material);
				$('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val(params.id_prod);
				$('select[name="id_uom'+domElement.attr('patelUnPrc')+'"]').val(params.id_uom);
				$('input[name="po_qty'+domElement.attr('patelUnPrc')+'"]').val(params.qty);
				$('input[name="rem_qty'+domElement.attr('patelUnPrc')+'"]').val(params.jw_rem_qty);
				$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val(params.un_prc);
				
				/*$('input[name="others'+domElement.attr('patelUnPrc')+'"]').val(params.others);
				$('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax);
				$('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax2);
				$('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val);
				$('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val2);*/
				
				//$('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(params.tot_prc);
				

			}
		
	},'json');
    
  }

function getPlanQty(event) {
	var domElement =$(event.target || event.srcElement);
	var id_req = $('select[name="id_req'+domElement.attr('patelUnPrc')+'"]').val();
	
    $.post('JobWork',{action : 'getPlanQty', id_req:id_req},function (r){
		
		if(r.data)
			{
			params = r.data[0];
			
			
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(params.plan_qty);
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').trigger(event,'qty');
				
				/*$('input[name="others'+domElement.attr('patelUnPrc')+'"]').val(params.others);
				$('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax);
				$('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax2);
				$('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val);
				$('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val2);*/
				
				//$('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(params.tot_prc);
				//$('.planQty').val(params.plan_qty).trigger('change');
				//$('.planQty').val(params.plan_qty).change();
			}
		
	},'json');
    
  }

function Controljobwork(action,displayContent,createDetails,formName,servletName) {		
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('input[name="dt_req"]').attr("disabled", false);
	

	if(action=='Create')
		{
		
		dispalyjwLineItemDynamically("New",function (status){
			if(status)
			{
			}});
			
		$('.readbaledata').each(function (){
			
			$(this).removeAttr('readonly', 'readonly');
			if($(this).is("select"))
				{
					$(this).attr("disabled", false);
				}
			
		});

		  $.post('JobWork',{action : 'auto_jwno'},function (r){
			
				$('input[name="no_jw"]').val(r.jw_no);
					
				
			
	},'json');
		  
		 
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		$('input[name="action"]').val('Save');
			$('input[name="id"]').val("0");
			$('input[name="no_req_val"]').val('new');
			
	
			
			//$('#'+formName)[0].reset();
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			$('#createAssetPR').show();
			$('#DisplayAssetRP').hide();
			

			
		}
	if(action=='Cancel')
		{
			
		$( ".jobworkdc" ).trigger( "click" );
		}
	
	
	
	if(action=='Save')
		{
		
			if(action == 'Save')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Save');
				DataInsertUpdateForInvoice("Cancel",displayContent,createDetails,formName,servletName);
				}
			else
				{
					$('button[name="update"]').removeClass('hideButton');
					$('button[name="save"]').addClass('hideButton');
					$('input[name="action"]').val('Update');
				}
			
			
			
		}
	
	if(action == 'Update')
	{
		
		var t=false;
		t=ValidationForm('submitJobWorkOrder');
		if(t)
		{
			$('.upd').attr('disabled','disabled');

			
			var x = $('#submitJobWorkOrder').serialize();
			$('.btn').attr('disabled','disabled');
			$.post('JobWork',x,function (r){
				//alert(x);
				if(r.data == 1)
					{
					bootbox.alert("Update successfully.");
					$('.btn').removeAttr('disabled');
					$( ".jobworkdc" ).trigger( "click" );
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
					$('.btn').removeAttr('disabled');
				}
				$('.upd').removeAttr('disabled');
			},'json');
		
		}
	}
	
			}
});
}

function Jobworkvendordropdown(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'dropdownvendor'},function (r){
			 list= '<option value=""> Select</option>';
		
		if((dropDownId == 'assetDivForYearlyDprn') || (dropDownId == 'assetDivForMonthlyDprn'))
			{
			 list = '';
			}
		if(r.data)
			{
			
				t=true;
				
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
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			if(dropDownId=='userMembers'){
				$('.'+dropDownId).html(list);
			   
			}
			else if(dropDownId == 'currforcustomer')
				$('.currforcustomer').html(list);
			else{
				$('#'+dropDownId).html(list);
			}
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}



function DataInsertUpdateForInvoice(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
	t = ValidationForm(formName);
		if(t)
		{
		///	var id = $('input[name="id"]').val();
			/*
			var id_mrn = $('select[name="mrn"]').val();
			   	console.log(id_mrn);
			$('.req').attr('disabled','disabled');*/
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			$('.btn').attr('disabled','disabled');
			$.post(servletName,x,function (r){
				
				if(r.data)
					{
					
					bootbox.alert("JobWork has been successfully added...");
					$('.btn').removeAttr('disabled');
						$( ".jobworkdc" ).trigger( "click" );
					}
				else
					{
						bootbox.alert("Try again.");
						$('.btn').removeAttr('disabled');
					}
				$('.req').removeAttr('disabled');
				
			},'json');
		
		}
			}
});
}
function DeleteAsset(servletName,displayContent,createDetails,id)
{
	
	SessionCheck(function (ses){
		
		if(ses)
			{
	
				bootbox.confirm("Are you sure?", function(result) {
				if(result)
				{
				
					$.post(servletName,{action : 'Delete',id : id},function (r){
						
						if(r.data == 1)
						{
							$( ".jobworkdc" ).trigger( "click" );
						}
						/*if(r.data == 2)
						{
							bootbox.confirm("If you delete this ,Then all aseet will be deleted related to this sales.", function(result1) {
								if(result1)
								{
									$.post(servletName,{action : 'Delete',id : id , confirm : '0'},function (r){
										
										if(r.data == 1)
											{
											$( ".creategrn" ).trigger( "click" );
											}
									},'json');
								}
								
							});
						}*/
						
					},'json');
				}
			}); 
			}
		
	});
}


function Displayjw(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var searchWord = $('input[name="search"').val();
			$.post(servletName,{action : 'Display' , no_req_val : no_req_val,searchWord:searchWord},function (r){
				

				var list= '<tr class="tableHeader info">'+
				'<td><center><strong>JobWork DC Number</strong></center></td>'+
				'<td><center><strong>JobWork DC Date</strong></center></td>'+
				
				'<td><center><strong>Vendor Name </strong></center></td>'+
				
				'<td><center><strong><a href="#">Modify </a></td>'+
			'</tr>';
					
				if(r.data.length > 0)
					{
						
					
						for(var i = 0; i < r.data.length ; i++)
						{
							params = r.data[i];
							
						
							
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_jw+'</center></td>'+
						'<td><center>'+params.dt_jw+'</center></td>'+
						
						'<td><center>'+params.nm_ven+'</center></td>'+
						
						'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="Editjw(\'Modify\',\''+displayContent+'\',\''+createDetails+'\',\''+params.id_jw+'\')"> Modify </a></center></strong></td>'+
						'</tr>';
						
						}
					
						$('.'+DisplayTableClass).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No data found.</center></strong></td></tr>';
						$('.'+DisplayTableClass).html(list);
					}
				
			},'json');
		
			}
});
}


function Editjw(action,displayContent,createDetails,id_jw){
	var id_ven=0;
	
	
	$.post('JobWork',{action : 'Edit',id : id_jw},function (r){
		
		if(r.data.length > 0)
			{
			id_ven=r.data[0].id_ven;
			
			for(var i=0;i<r.data.length;i++)
				{
					for (var key in r.data[i])
			        {
						if($('select[name='+key+']').is("select") && r.data[i][key] != '')
						{
							$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
							
						}
						else
						{
							$('input[name='+key+']').val(r.data[i][key]);
						}
					
			        }
				}
			
				
				$('button[name="save"]').addClass('hideButton');
				$('button[name="update"]').removeClass('hideButton');
				$('input[name="action"]').val('Update');
				$('#'+displayContent).hide();
				$('#'+createDetails).show();
				//$('#DisplayAssetRP').show();
			
				$('#createAssetPR').show();
				//$('#DisplayAssetRP').hide();
					$('input[name="id"]').val(id_jw);
					$('input[name="dt_jw"]').val(r.data[0].dtjw);
					if(r.data[0].remarks!=undefined )
					{
						$('textarea[name="remarks"]').val(r.data[0].remarks);
						}
					if(r.data[0].add1!=undefined)
					{
						$('textarea[name="add1"]').val(r.data[0].add1);
						}

			}
		/*else
			bootbox.alert("Try again.");*/
			setTimeout(function() {
									DisplayLineItemDynamicallyForEditLineItem(displayContent,createDetails,id_jw,id_ven);
									 
								}, 1000);
	
		
	},'json');
	
	

}

function getvenAdd(event) {
	var domElement =$(event.target || event.srcElement);
	var id_ven = $('select[name="id_ven"]').val();
	
    $.post('Sales_Invoice_For_FG',{action : 'getVenAdd', id_ven:id_ven},function (r){
		
		if(r.data)
			{
			params = r.data[0];
			
			
				$('textarea[name="add1"]').val(params.add1);
				//$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').trigger(event,'qty');
				
				/*$('input[name="others'+domElement.attr('patelUnPrc')+'"]').val(params.others);
				$('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax);
				$('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax2);
				$('input[name="tax_val1'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val);
				$('input[name="tax_val2'+domElement.attr('patelUnPrc')+'"]').val(params.tax_val2);*/
				
				//$('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(params.tot_prc);
				//$('.planQty').val(params.plan_qty).trigger('change');
				//$('.planQty').val(params.plan_qty).change();
			}
		
	},'json');
    
  }

function SubDropDownDataDisplayForRequest5(ids,dropDownId,servletName,callback)
{
	var t=false;
	
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}
	if(dropDownId == 'subGroupDataForMaintenance')
		$('#assetIdForAssetMaintenance').html('<option value=""> Select </option>');
	
	if(ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
			$.post(servletName,{action:'DropDownResult5' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
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
							}
						
						if(dropDownId == 'routecardforjw')		
							$('.routecardforjw').html(list);
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				callback(t);
			},'json');
		}

}

function DisplayLineItemDynamicallyForEditLineItem(displayContent,createDetails,id_jw,id_ven)
{
	
	/*SubDropDownDataDisplayForRequest5(id_jw,'routecardforjw','JobWork',function (status){
		if(status)
		{*/
			
$.post('JobWork',{action : 'DisplayLineItem',id:id_jw},function (r){
	
	if(r.data.length > 0)
		{
			for(var i=0;i<r.data.length;i++)
			{
				for (var key in r.data[i])
		        {
					if($('select[name='+key+']').is("select") && r.data[i][key] != '')
					{
						$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
					
		
					}
					else
					{
						$('input[name='+key+']').val(r.data[i][key]);
					}
					
		        }
			}
			
			var params='';
		var list ='<tr class="tableHeader info">'+
		'<td><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		//'<td><strong><center>Part Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		/*'<td ><strong><center>Description<a href=#></a></center></strong></td>'+*/
		'<td ><strong><center>UOM<a href=#></a></center></strong></td>'+
		'<td><strong><center>Process Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		
		'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Remaining Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Issue No Number<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
		'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
		//'<td style="width: 105px;"><center><strong><a href="#">Delete</a></strong></center></td>'+
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			'<td><center>'+
			'<select   name="id_po'+i+'" patelPo="'+i+'" id="id_po'+i+'" value="'+params.id_po+'" style="width:60px;text-align: right;"  class="common-validation commonPo " data-valid="mand" patelUnPrc="'+i+'" readonly onchange="SubDropDownDataDisplayjobwork(this,\'id_po_asset'+i+'\',\'JobWork\');"  >'+
			'</select>'+
			'</center></td>'+
			
			'<td><center>'+
			'<select id="id_po_asset'+i+'" name="id_po_asset'+i+'" value="'+params.id_po_asst+'" patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation prodforgrn" data-valid="mand" readonly onchange="SubDropDownDataDisplayjobworkprocess(this,\'id_route_card_material'+i+'\',\'JobWork\');" >'+
			'</select>'+
			'</center></td>'+
			'<input type="hidden" name="id_prod'+i+'"  value="">'+	
			//'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'" style="padding: 0px;width:80px" class="common-validation resetAcc " patelUnPrc="'+i+'"  onchange="changeEventHandlercode(event);"></td>'+
			/*'<td><center><input type="text" maxlength="500" name="description'+i+'"  value="" class="common-validation" ></center></td>'+*/
			
			'<td><center>'+
			'<select style="width: 100px;" name="id_uom'+i+'" value="'+params.id_uom+'"  patelUnPrc="'+i+'" taxName="" data-valid="mand" class="common-validation uomforSales" readonly>'+
			'</select>'+
			'</center></td>'+
			
			//'<td><input type="text" name="id_uom'+i+'"  style="width:60px" class="common-validation resetAcc " patelUnPrc="'+i+'" onchange="changeEventHandlerprod(event);" readonly></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_route_card_material'+i+'"  id="id_route_card_material'+i+'" value="'+params.id_route_card_material+'" data-valid="mand" patelUnPrc="'+i+'" taxName="" class="common-validation routecardforjw" readonly>'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="po_qty'+i+'"  style="width:60px;;text-align: right;"  value="'+params.po_qty+'" class="common-validation" patelUnPrc="'+i+'" data-valid="mand" class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.rem_qty+'" class="common-validation" patelUnPrc="'+i+'" data-valid="mand"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="no_issue'+i+'" style="width:60px;;text-align: right;"  value="'+params.no_issue+'" class="common-validation" patelUnPrc="'+i+'" data-valid="mand" class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<input type="hidden" name="id_req'+i+'"  value="'+params.id_req+'">'+	
			'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.qty+'" class="common-validation" patelUnPrc="'+i+'" data-valid="mand" onChange="calculateTotjw(event,\'qty\',\'update\')"  class="patelUnPrc data-valid="num"></center></td>'+

			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" data-valid="mand" onChange="calculateTotjw(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num"  ></center></td>'+
			'<td><input type="text" name="others'+i+'" value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px"" data-valid="mand" onChange="calculateTotjw(event,\'others\')" class="common-validation" ></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax1'+i+'" value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" data-valid="mand" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax2'+i+'" value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" data-valid="mand" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val1+'" data-valid="mand" onChange="calculateTotjw(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
			'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val2+'" data-valid="mand" onChange="calculateTotjw(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
			'<td><center><input type="text"  name="discount'+i+'" patelUnPrc="'+i+'" value="'+params.discount+'" data-valid="mand" onChange="calculateTotjw(event,\'discount\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
			'<td><center><input type="text"  name="gr_tot'+i+'" data-valid="mand" value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
			
			
		//	'<td><center><strong><a class="alert" href="#" onclick="DeleteAssetForGRN(\'Direct_Invoice\',\''+displayContent+'\',\''+createDetails+'\','+params.id_jw_asset+')"> Delete</a></strong></center></td>'+
			
			//'<input type="hidden" name="id_jw_asset'+i+'" value="'+params.id_jw_asset+'">'+
			'<input type="hidden" name="id_prod'+i+'" value="'+params.id_prod+'">'+
			'<input type="hidden" name="count" value="'+i+'">'+	
			'<input type="hidden" name="id_jw_asset'+i+'"  value="'+params.id_jw_asset+'">'+
			'</tr>';
			//$('select[name=id_po_asset'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
		
			}
		
		
		
			
		$('input[name="itemCount"]').val(parseInt(i));
		
	
		$('.EditSalesOrder').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
		}
		
		if(r.data.length > 0)
		{
			 
		for(var k=0;k<r.data.length;k++)
			{
				(function(k){
            		setTimeout(function(){
					params=r.data[k];
	     			console.log(params);
                	 console.log(k);
              		 	SubDropDownDataDisplayjobwork(params.id_po,'id_po_asset'+k+'','JobWork');
           		 }, 500*k);

         		})(k);
		
			}
		 }
	
	/*for(i=0;i<r.data.length;i++)
	{
		params=r.data[i];
		console.log('id_po_asset'+params.id_po);
		SubDropDownDataDisplayjobwork(params.id_po,'id_po_asset'+i,'JobWork',function (status){
		if(status)
			{
			
			}});
	}*/
	/*if(r.data.length > 0)
		{
			 
		for(var k=0;k<r.data.length;k++)
			{
				(function(k){
            		setTimeout(function(){
					params=r.data[k];
	     			console.log(params);
                	 console.log(k);
              		 	SubDropDownDataDisplayRequest5(params.id_jw_asset,'id_route_card_material'+k+'','JobWork');
           		 }, 100*k);

         		})(k);
		
			}
		 }*/
	for(i=0;i<r.data.length;i++)
	{
		
		params=r.data[i];
		console.log(params.id_jw_asset);
		SubDropDownDataDisplayForRequest5(params.id_jw_asset,'id_route_card_material'+i,'JobWork',function (status){
		if(status)
			{
			
			}});
			
		
	}
	/*for(i=0;i<r.data.length;i++)
	{
		params=r.data[i];
		//console.log('id_po_asset'+params.id_po);
		SubDropDownDataDisplayjobwork(params.id_po,'id_po_asset'+i,'JobWork',function (status){
			if(status)
			{
				SubDropDownDataDisplayjobworkprocess(params.id_po_asst,'id_route_card_material'+i,'JobWork',function (status){
					if(status)
					{
						SubDropDownDataDisplayForRequest5(params.id_jw_asset,'id_route_card_material'+i,'JobWork',function (status){
							if(status)
							{
								
							}});
					}});
			}});
		
	}
	
		*/	
			SubDropDownDataDisplayForjobwork(id_ven,'commonPo','JobWork',function (status){
				if(status)
				{
					DropDownDataDisplay("M_UOM","uomforSales",function (status){
						if(status)
						{
					DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
						if(status)
						{
							DropDownDataDisplay("M_Tax","taxDataForDropDown12",function (status){
								if(status)
								{
									
									setTimeout(function(){
									    
										for(var i=0;i<r.data.length;i++)
										{
											console.log(r.data.length);
											params=r.data[i];
											for (var key in r.data[i])
											{
								
											if(key == 'id_uom'){
													$('select[name=id_uom'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
					       
												}
												if(key == 'id_po'){
													$('select[name=id_po'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
					       
												}
												if(key == 'id_po_asst'){
													$('select[name=id_po_asset'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
					       
												}
												if(key == 'id_route_card_material'){
												
													$('select[name=id_route_card_material'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
					       
												}
												if(key == 'id_tax1'){
													$('select[name=id_tax1'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
								       
												}
												if(key=='id_tax2')
												{
													$('select[name=id_tax2'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
													
												}
								
											}
										}
										
									}, 2000);
									
								}});		
								
						}});
							
						}});
		}});
	
},'json');

}






function CalculateRejectQty(event, name){

	var domElement =$(event.target || event.srcElement);
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		var qty= $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
		var accept_qty= $('input[name="accept_qty'+domElement.attr('patelUnPrc')+'"]').val();
		var reject_qty=0;
		reject_qty=parseInt(qty)-parseInt(accept_qty);
		$('input[name="reject_qty'+domElement.attr('patelUnPrc')+'"]').val(reject_qty);
			
	}
	else
		{
			alert('Invalid number.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}




function SubDropDownDataDisplayForjobwork(ids,dropDownClass,servletName,callback)
{
	debugger;
	var t=false;
	
	var id=ids.value;
	if(ids.value == undefined)
		{
		id = ids;
		}

	if(ids.value == '')
	{
	
		$('.'+dropDownClass).html('<option value=""> Select</option>');
		$('.'+dropDownClass).focus();
	}
	
	else
		{
		
			$.post(servletName,{action:'DropDownResultForjobwork' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('.'+dropDownClass).html(list);
								$('.'+dropDownClass).focus();
							}
						else
							{
							
									
									
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
									list = list + '<option value="'+id+'"> '+val+'</option>';
									}
							}
						if(dropDownClass =='commonPo'){
							$('.'+dropDownClass).html(list);
							$('.'+dropDownClass).focus();
						}else{
							$('#'+dropDownClass).html(list);
							$('#'+dropDownClass).focus();
						}
						
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				callback(t);
			},'json');
		}

}

function DisplayLineItemlistforrejobwork(id_req)
{
	$.post('JobWork',{action : 'DisplayLineItemrejobwork',id_jwre:id_req},function (r){
		
		if(r.data.length > 0)
			{
				
				for(var i=0;i<r.data.length;i++)
				{
					for (var key in r.data[i])
			        {
						if($('select[name='+key+']').is("select") && r.data[i][key] != '')
						{
							$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
							
						}
						else
						{
							$('input[name='+key+']').val(r.data[i][key]);
						}
					
			        }
				}
				debugger;
			
			
				var params='',totPrice=0;
			var list ='<tr class="tableHeader info">'+
			'<td><strong><center>JWDC No<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Part Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
			/*'<td ><strong><center>Description<a href=#></a></center></strong></td>'+*/
			'<td ><strong><center>UOM<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Process Name<a href=#></a></center></strong></td>'+
			
			//'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>NC Quantity<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center>Unit Price<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center>Others<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center>Discount<a href=#></a></center></strong></td>'+
			'<td style="display:none;"><strong><center> Grand Total<a href=#></a></center></strong></td>'+
			//'<td style="width: 105px;"><center><strong><a href="#">Delete</a></strong></center></td>'+
			
			'</tr>';
			var i=0;
			for(i=0;i<r.data.length;i++)
				{
				params=r.data[i];
			
				list += '<tr class="success">'+
			
				'<td><center><input type="text"    style="width:100px;;text-align: right;"  value="'+params.no_jw+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
				'<td><center><input type="text"   style="width:100px;;text-align: right;"  value="'+params.nm_prod+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
				
				'<td><center><input type="text"   style="width:100px;;text-align: right;"  value="'+params.cd_prod+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
				'<td><input type="text"   style="width:80px" value="'+params.nm_uom+'" class="common-validation resetAcc " patelUnPrc="'+i+'" onchange="changeEventHandlerprod(event);" readonly></td>'+
				'<td><center><input type="text"   style="width:100px;;text-align: right;"  value="'+params.nm_process+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
				
			//	'<td><center><input type="text"    style="width:60px;;text-align: right;"  value="'+params.po_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
				'<td><center><input type="text" name="qty'+i+'"  style="width:60px;;text-align: right;"  value="'+params.nc_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
				
				'<td style="display:none;"><center><input type="text" name="un_prc'+i+'"  style="width:60px;;text-align: right;" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readonly ></center></td>'+
				'<td style="display:none;"><input type="text"  name="others'+i+'" value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation"></td>'+
				'<td style="display:none;"><center><input type="text"    patelUnPrc="'+i+'" value="'+params.t1nm+'" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td style="display:none;"><center><input type="text"    patelUnPrc="'+i+'" value="'+params.t2nm+'" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
			
				'<td style="display:none;"><center><input type="text"  name="tax_val1'+i+'"  patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td style="display:none;"><center><input type="text"  name="tax_val2'+i+'"  patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				'<td style="display:none;"><center><input type="text"  name="discount'+i+'"  patelUnPrc="'+i+'" value="'+params.discount+'" onChange="calculateTot(event,\'discount\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td style="display:none;"><center><input type="text"  name="gr_tot'+i+'"  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				'<input type="hidden" name="id_route_card_material'+i+'" value="'+params.id_route_card_material+'">'+
				
				'<input type="hidden" name="id_ven1'+i+'" value="'+params.id_ven1+'">'+
				'<input type="hidden" name="count222" value="'+i+'">'+	
				'<input type="hidden" name="id_po'+i+'" id="id_po'+i+'" value="'+params.id_po+'">'+
				'<input type="hidden" name="id_tax1'+i+'"  value="'+params.id_tax1+'">'+
				'<input type="hidden"  name="id_tax2'+i+'" id="id_tax2'+i+'" value="'+params.id_tax2+'">'+
				'<input type="hidden" name="id_prod'+i+'"  value="'+params.id_prod+'">'+
				
				'<input type="hidden" name="id_jw_grn'+i+'"  value="'+params.id_jw_grn+'">'+
				'<input type="hidden"  name="id_uom'+i+'" id="id_uom'+i+'" value="'+params.id_uom+'">'+
				//'<input type="hidden" name="po_qty'+i+'" value="'+params.po_qty+'">'+
				
			
					
			
				
				'</tr>';
				totPrice += parseFloat(params.grnd_tot);
				}
			
			
				
			$('input[name="itemCount"]').val(parseInt(i));
			
		
			$('.EditSalesOrder').html(list);
			}
		
	},'json');

	}
function Displayforrejobwork(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			//alert(servletName);
			$.post(servletName,{action : 'Displaygrnrework' , no_req_val : no_req_val},function (r){
				

				var list= '<tr class="tableHeader info">'+
				'<td><center><strong>JobWork DC Number</strong></center></td>'+
				'<td><center><strong>JobWork DC Date</strong></center></td>'+
				
				'<td><center><strong>Vendor Name </strong></center></td>'+
				
				'<td><center><strong><a href="#">Select </a></td>'+
			'</tr>';
					
				if(r.data.length > 0)
					{
						
					
						for(var i = 0; i < r.data.length ; i++)
						{
							params = r.data[i];
							
						
							
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_jw+'</center></td>'+
						'<td><center>'+params.dt_jw+'</center></td>'+
						
						'<td><center>'+params.nm_ven+'</center></td>'+
						'<input type="hidden" name="id_jw" value="'+params.id_jw+'">'+	
						'<td><strong><center><input type="checkbox" name="count" class="prRequestItemForIndentSelectAll" patelUnPrc="'+i+'"  value="'+params.id_jw+'"/></center></strong></td>'+
							'</tr>';
					
						
						}
					
						$('.'+DisplayTableClass).html(list);
						$('button[name="save"]').removeClass('hideButton');
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No data found.</center></strong></td></tr>';
						$('.'+DisplayTableClass).html(list);
					}
				
			},'json');
		
			}
});
}
function Checkselectedvendorforrejobwork(e){
var count=	$('input[name="itemCount"]').val();
var idven=	e.target.value;
console.log(idven);
for(var i=0;i<count;i++){
	var ven=	$('input[name="id_ven1'+i+'"]').val();
	if(idven!=ven){
		i=count;
		$('select[name=id_ven]').val('');
		alert("Please select the similer vendor. ");
	}
	
}
	
}



function ControlREjobwork(action,displayContent,createDetails,formName,servletName) {
	SessionCheck(function (ses){		
			if(ses)
				{
		
			$(".common-validation").each(function(){
			
				$(".common-validation").removeClass('error');
			});
			$('input[name="dt_req"]').attr("disabled", false);
			if(action=='Cancelrejw')
			{
				
			$( ".Re_jobworkdc" ).trigger( "click" );
			}

	if(action=='ReworkSave')
			              {
				
				
				var t=false;
				var id_req="0";
				$('.prRequestItemForIndentSelectAll').each(function(){
					if(this.checked)
					{
						t=true;
						if(id_req == '0')
							id_req = $(this).val();
							else
								id_req +='Patel'+ $(this).val();
					}
					
				});
			if(!t)
				{
					bootbox.alert("Please select at least one request then proceed.");
				}	
				
			else{
				
				
			     t=false;
				t=ValidationForm('submitJobWorkOrdergrn');
				debugger;
				  $.post('JobWork',{action : 'auto_jwno',id_jwre:id_req},function (r){
						
						$('input[name="no_jw"]').val(r.jw_no);
						$('select[name="id_ven"]').val(r.id_ven);
							
						
					
			},'json');		
				
				  //
				  
				  
				  
				  DisplayLineItemlistforrejobwork(id_req);	    
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			$('#createAssetPR').show();
			$('#DisplayAssetRP').hide();
			$('button[name="cancel"]').removeClass('hideButton');
			$('button[name="update"]').removeClass('hideButton');
			$('button[name="save"]').addClass('hideButton');
			
			
			}
			}
		
	
		
		if(action == 'Updatejobwork')
		{
		
			var t=false;
			t=ValidationForm('submitreJobWorkOrder');
			if(t)
			{
				$('.upd').attr('disabled','disabled');

				
				var x = $('#submitreJobWorkOrder').serialize();
				$.post('JobWork',x,function (r){
					if(r.data == 1)
						{
						bootbox.alert("Update successfully.");
						$( ".Re_jobworkdc" ).trigger( "click" );
						}
					else
					{
						bootbox.alert("Something went wrong Please try again.");
					}
					$('.upd').removeAttr('disabled');
				},'json');
			
			}
		}
	
		
	}
	});
	}
