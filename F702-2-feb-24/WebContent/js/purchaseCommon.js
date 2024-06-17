$(function(){
	
	$( "#menu" ).menu();
	
});
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
	
	
function dispalyLineItemDynamically(action,callback){
	
	if(action == 'New')
	{
		var list ='<tr>'+
		'<td colspan="21" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 500px;">Line Item Details</p></td></tr>'+ 
	'<tr class="tableHeader info">'+
		//'<td style="width:400px;"><strong><center>Material Type<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Model<a href=#></a></center></strong></td>'+
		/*'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
		*/
		'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Approx Unit Price<a href=#></a></center></strong></td>'+
		'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
		'</tr>';
		
		for(var i = 0; i < 10 ; i++)
		{
			list+='<tr>'+
			' <td style="display:none;">'+
				'<select name="asset_consumable'+i+'"   style="width:107px" patelUnPrc="'+i+'" class="common-validation" data-valid="mand">'+
						'<option value="ConG">Material Goods</option>'+
						'<option value="CapG">Capital Goods</option>'+
				'</select>'+
			'</td>'+
			'<input type="hidden" name="ConfigurableId'+i+'" value="No">'+	
			
			/*'<td><center>'+
			'<select  name="id_prod'+i+'" id="id_prod" patelUnPrc="'+i+'" taxName="" class="common-validation prod" >'+
			'</select>'+
			'</center></td>'+*/
			'<td><input style="width: 120px !important;" type="text" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
			
			//'<td><input type="text" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
			/*'<td><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
					'<option value="">Select</option></select>'+
			'</center></td>'+
			'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
					'<option value="">Select</option></select>'+
					'</center></td>'+
				*/
		 	'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onBlur="return CalcTotal(\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onBlur="return CalcTotal(\'un_prc\')" class=" common-validation"    ></center></td>'+
		     '<td><center><input type="text"  name="tot_prc'+i+'" value="0.0" patelUnPrc="'+i+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				//'
			
			'<input type="hidden" name="count" value="'+i+'">'+		
		'</tr>';
			
		}
		/*list+='<tr>'+
				'<td colspan="7">Grand Total</td>'+
				'<td><input type="text" name="grand_tot" value="0.0" id="grand_tot " class="resetAcc1 FieldResize" readonly></td>'+
			'</tr>';*/
		
		$('input[name="itemCount"]').val('10');
		$('input[name="grand_tot1"]').val('0.00');
		
		$('#accessoryDetails').html(list);
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				
				DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					});
			}});
	
	}else{
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr>'+
		'<input type="hidden" name="ConfigurableId'+i+'" value="No">'+	
		
		/*'<td><center>'+
		'<select  name="id_prod'+i+'" id="id_prod'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation prod" >'+
		'</select>'+
		'</center></td>'+*/
		/*'<td><input style="width: 120px !important;" type="text" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
		'<td><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
		'<option value="">Select</option></select>'+
		'</center></td>'+
		'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation " data-valid=""  style="width:80" >'+
		'<option value="">Select</option></select>'+
		'</center></td>'+*/
		
		'<td><input style="width: 120px !important;" type="text" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+

	 	'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onBlur="return CalcTotal(\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

		'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onBlur="return CalcTotal(\'un_prc\')" class=" common-validation"    ></center></td>'+
	
	       '<td><center><input type="text"  name="tot_prc'+i+'" value="0.0" patelUnPrc="'+i+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
			//'
	 	'<input type="hidden" name="count" value="'+i+'">'+		
			'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('#accessoryDetails').append(list);
	DropDownDataDisplay("M_Tax","id_tax"+i,function (status){
		if(status)
		{
			
				
			
		}});
	DisplayDropDownDataForGroup("M_Asset_Div","assetDivForInvoice"+i,"CapG",function (status){
		if(status)
		{
			
		}});
	}
	$("#dynItemData0").easyAutocomplete(options);
	$("#dynItemData1").easyAutocomplete(options);
	$("#dynItemData2").easyAutocomplete(options);
	$("#dynItemData3").easyAutocomplete(options);
	$("#dynItemData4").easyAutocomplete(options);
	$("#dynItemData5").easyAutocomplete(options);
	$("#dynItemData6").easyAutocomplete(options);
	$("#dynItemData7").easyAutocomplete(options);
	$("#dynItemData8").easyAutocomplete(options);
	$("#dynItemData9").easyAutocomplete(options);
	$("#dynItemData10").easyAutocomplete(options);
	$("#dynItemData11").easyAutocomplete(options);
	$("#dynItemData12").easyAutocomplete(options);
	$("#dynItemData13").easyAutocomplete(options);
	$("#dynItemData14").easyAutocomplete(options);
	$("#dynItemData15").easyAutocomplete(options);
	$("#dynItemData16").easyAutocomplete(options);
	$("#dynItemData17").easyAutocomplete(options);
	$("#dynItemData18").easyAutocomplete(options);
	$("#dynItemData19").easyAutocomplete(options);
	$("#dynItemData20").easyAutocomplete(options);
	$("#dynItemData21").easyAutocomplete(options);
	$("#dynItemData22").easyAutocomplete(options);
	$("#dynItemData23").easyAutocomplete(options);
	$("#dynItemData24").easyAutocomplete(options);
	$("#dynItemData25").easyAutocomplete(options);
	$("#dynItemData26").easyAutocomplete(options);
	$("#dynItemData27").easyAutocomplete(options);
	$("#dynItemData28").easyAutocomplete(options);
	$("#dynItemData29").easyAutocomplete(options);
	$("#dynItemData30").easyAutocomplete(options);
	
	
	callback(true);	
	
}


function ControlPurchaseRequest(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('input[name="dt_req"]').attr("disabled", false);
	
	
	if(action=='Create')
		{
			
		dispalyLineItemDynamically("New",function (status){
			if(status)
			{
			}});
			
		/*$('.readbaledata').each(function (){
			
			$(this).removeAttr('readonly', 'readonly');
			if($(this).is("select"))
				{
					$(this).attr("disabled", false);
				}
			
		});*/

	
		
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
		$('input[name="action"]').val('Save');
			$('input[name="id"]').val("0");
			$('input[name="no_req_val"]').val('new');
			
			$('#requestPrAssetSubGroup').html('<option value="">Select</option>');
			$('#proNameForReqPR').html('<option value="">Select</option>');
			$('#requestPrAssetGroup').html('<option value="">Select</option>');
			
			
			//$('#'+formName)[0].reset();
			$('#'+displayContent).hide();
			$('#'+createDetails).show();
			$('#createAssetPR').show();
			$('#DisplayAssetRP').hide();
			
$.post('P_Request_PR',{action : 'LoginUserDetails'},function (r){
				
				if(r.data)
					{	for(var i = 0; i < r.data.length ; i++)
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
											if(key == 'remarks')
											{
												$('textarea[name='+key+']').val(r.data[i][key]);
											}
								        }
										 
									}
									console.log(r.data[0].repo_mngr);
								
									$('select[name=req_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected',true);
									$('select[name=firstla] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
								    $('select[name=secondla] option[value=' + r.data[0].secnd_app + ']').attr('selected',true);
				 					$('select[name=repo_mngr] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
									$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected',true);
									$('select[name=id_building] option[value=' + r.data[0].id_building + ']').attr('selected',true);
									$('select[name=id_flr] option[value=' + r.data[0].id_flr + ']').attr('selected',true);
									$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected',true);
																	
									var id_loc=$('select[name="id_loc"]').val();
									var id_sloc=$('select[name="id_loc"]').val();
									var id_building=$('select[name="id_loc"]').val();
									
							DisplaySubDropDownData(id_loc,'subLocDataForFloor','M_Subloc',function (status){
						if(status)
								{
					SubDropDownDataDisplay(id_sloc,'buildingDataForFloor','M_Building',function (status){
						if(status)
									{
							SubDropDownDataDisplay(id_building,'subForFloor','M_Floor',function (status){
						if(status)
										{
									$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected',true);
									$('select[name=id_building] option[value=' + r.data[0].id_building + ']').attr('selected',true);
									$('select[name=id_flr] option[value=' + r.data[0].id_flr + ']').attr('selected',true);

										}});	
									
									}});	
								
								}});	
					}
},'json');
			
		}
	if(action=='Cancel')
		{
			$('#DisplayAssetRP').hide();
			$('#createAssetPR').hide();
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();
			DisplayPR(servletName,displayContent,createDetails,'','PRForDisplay');
		}
	
	if(action == 'SendForApproval'){
		var id = $('input[name="id"]').val();
		var mail = $('#print_mail').html();
		 
		$.post('P_Request_PR',{action : 'SendForApproval',id:id,mailcontant:mail},function (r){
			if(r.data ==1)
			{
				$('#DisplayAssetRP').hide();
				$('#createAssetPR').hide();
				$('#'+createDetails).hide();
				
				$('#'+displayContent).show();
				DisplayPRPreview('P_Request_PR','displayPR','createPR','','PRForDisplay');
				bootbox.alert("Purchase request has been sent for approval.");
			}
		else
			{
				bootbox.alert("Try again.");
			}
			},'json');				
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
			
			DataInsertUpdateForPR("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
			}
});
}




function autoFillData(servletName,id_dept,id_cc){
	SubDropDownDataDisplay(id_dept,'requestedPrCC','M_Cost_Center',function (status){
		if(status)
		{
			SubDropDownDataDisplay(id_cc,'sectionDataForRequest','M_Section',function (status){
				if(status)
				{	
		$.post(servletName,{action : 'Edit',id : id},function (r){
				
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
								if(key == 'remarks')
								{
									$('textarea[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}	
				
					
		},'json');
		}});
			
		}});
	
}

function onChangeProductName(id)
{
	$.post('M_Prod_Cart',{action : 'Edit',id : id.value},function (r){
		
		if(r.data)
			{
			
				for(var i = 0; i < r.data.length ; i++)
				{
					for (var key in r.data[i])
			        {
							$('input[name='+key+']').val(r.data[i][key]);
			         
			        }
				}
				
				}
		else
			{
				bootbox.alert("Try again.");
			}
	},'json');


}


function onChangeAssetConsumable(name,IdNameForHtml)
{
	if(name.value == 'Asset')
		{
		DisplayDropDownDataForGroup('M_Asset_Div',IdNameForHtml,name.value,function (status){
			if(status)
			{
				
			}});
		}
	else if(name.value == 'Consumable')
		{
		DisplayDropDownDataForGroup('M_Asset_Div',IdNameForHtml,name.value,function (status){
			if(status)
			{
				
			}});
		}
	else
		{
			$('#'+IdNameForHtml).html('<option value=""> Select</option>');
			$('#'+dropDownId).focus();
		}
}

function SendForApproval(id)
{
	 DisplayPreviewDetailsformail(id)
	bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
			var mail = $('#print_mail').html();
		 
	$.post('P_Request_PR',{action : 'SendForApproval',id : id,mailcontant:mail},function (r){
		
		if(r.data ==1)
			{
				$('#DisplayAssetRP').hide();
				$('#createAssetPR').hide();
				$('#createPR').hide();
				
				$('#displayPR').show();
				DisplayPRPreview('P_Request_PR','displayPR','createPR','','PRForDisplay');
				bootbox.alert("Purchase request has been sent for approval.");
			}
		else
			{
				bootbox.alert("Try again.");
			}
	},'json');
}
	});
}

function DataInsertUpdateForPR(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
	t = ValidationForm(formName);
		if(t)
		{
			$('.req').attr('disabled','disabled');
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data)
					{
					//$('#createAssetPR').hide();
					$('input[name="no_req_val"]').val(r.no_req);
						bootbox.alert("Successfully added. Now request is available to send for approval.");
			            //DisplayAssetRP(servletName,displayContent,createDetails,r.data,'assetPRForDisplay');
			           /* $('#'+createDetails).show();
						$('#DisplayAssetRP').show();*/
						$( ".requestpr" ).trigger( "click" );
						/*$('.readbaledata').each(function (){
							
							$(this).attr('readonly', 'readonly');
							if($(this).is("select"))
							{
								$(this).attr("disabled", true);
							}
							
						});*/
					}
				else
					{
						bootbox.alert("Try again.");
					}
				$('.req').removeAttr('disabled');
				
			},'json');
		
		}
			}
});
}

function EditPRSendForApproval(servletName,displayContent,createDetails,id,id_dept,id_cc,id_loc,id_sloc,id_building)
{
SessionCheck(function (ses){
		
		if(ses)
			{
				DisplayPreviewDetailsformail(id);
			//DisplayAssetRPPreview('P_Request_PR','displayPR','createPR',id,'assetPRForDisplay');
	DisplayAssetRPReview('P_Request_PR','displayPR','createPR',id,'assetPRForDisplay');
	
	
	$('input[name="dt_req"]').attr("disabled", true);
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
	/*$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#DisplayAssetRP').show();*/
	$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#createAssetPR').show();
	$('#DisplayAssetRP').hide();
	
	var t=0;
		DisplaySubDropDownData(id_loc,'subLocDataForFloor','M_Subloc',function (status){
							if(status)
							{
						SubDropDownDataDisplay(id_sloc,'buildingDataForFloor','M_Building',function (status){
							if(status)
							{
				SubDropDownDataDisplay(id_building,'subForFloor','M_Floor',function (status){
							if(status)
							{
		$.post(servletName,{action : 'Edit',id : id},function (r){
				
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
								if(key == 'remarks')
								{
									$('textarea[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						$('input[name="grand_tot1"]').val(r.data[0].grand_tot);
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_req"]').val(id);
						$('input[name="no_req_val"]').val(r.data[0].no_req);
						$('input[name="id_cc"]').val(r.data[0].id_cc);
						$('input[name="dt_req"]').val(r.data[0].dtreq);
						
						}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}	
				
					
		},'json');
			}});
		}});	
				
				
							}});
		}
	
});
}
function EditPR(servletName,displayContent,createDetails,id,id_dept,id_cc,id_loc,id_sloc,id_building)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			//DisplayAssetRPPreview('P_Request_PR','displayPR','createPR',id,'assetPRForDisplay');
	DisplayAssetRP('P_Request_PR','displayPR','createPR',id,'assetPRForDisplay');
	
	
	/*$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});*/
	
	$('input[name="dt_req"]').attr("disabled", true);
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
	$('button[name="update"]').removeClass('hideButton');
	/*$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#DisplayAssetRP').show();*/
	$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#createAssetPR').show();
	$('#DisplayAssetRP').hide();
	
	var t=0;
	DisplaySubDropDownData(id_loc,'subLocDataForFloor','M_Subloc',function (status){
							if(status)
							{
						SubDropDownDataDisplay(id_sloc,'buildingDataForFloor','M_Building',function (status){
							if(status)
							{
				SubDropDownDataDisplay(id_building,'subForFloor','M_Floor',function (status){
							if(status)
							{
		$.post(servletName,{action : 'Edit',id : id},function (r){
				
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
								if(key == 'remarks')
								{
									$('textarea[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						$('input[name="grand_tot1"]').val(r.data[0].grand_tot);
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_req"]').val(id);
						$('input[name="no_req_val"]').val(r.data[0].no_req);
						$('input[name="dt_req"]').val(r.data[0].dtreq);
						ApprovalByPass();
						}
				else
					{
						bootbox.alert("Try again.");
					}
				if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}	
				
					
		},'json');
		
		}});
		}});	
				
				
							}});	
			
			
		}
	
});
}

function DeletePR(servletName,displayContent,createDetails,id)
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
							DisplayPR(servletName,displayContent,createDetails,'','PRForDisplay');
						}
						if(r.data == 2)
						{
							bootbox.confirm("If you delete this , then all aseet will be deleted related to this Request.", function(result1) {
								if(result1)
								{
									$.post(servletName,{action : 'Delete',id : id , confirm : '0'},function (r){
										
										if(r.data == 1)
											{
												DisplayPR(servletName,displayContent,createDetails,'','PRForDisplay');
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


function DeleteAssetPR(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{	
	bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
			id_req=$('#id').val();
			$.post(servletName,{action : 'DeleteAssetPR',id : id ,id_req:id_req},function (r){
				
				if(r.data == 1)
				{
					id_req=$('#id').val();
					DisplayAssetRP(servletName,displayContent,createDetails,id_req,'assetPRForDisplay');
				}
			if(r.data == 2)
				{
					bootbox.confirm("This is last record of this Request if delete this then Request also deleted.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'DeleteAssetPR',id : id , id_req : '0'},function (r){
								
								if(r.data == 1)
									{
										ControlPurchaseRequest('Cancel',displayContent,createDetails,'',servletName);
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

function CreateAssetPR(action,displayContent,createDetails,no_asset,id_grp,id_sgrp)
{
SessionCheck(function (ses){
		
		if(ses)
			{
		if(action=='Create')
		{
			$('button[name="update"]').addClass('hideButton');
			$('button[name="save"]').removeClass('hideButton');
			$('#requestPrAssetSubGroup').html('<option value="">Select</option>');
			$('#proNameForReqPR').html('<option value="">Select</option>');
			$('#requestPrAssetGroup').html('<option value="">Select</option>');
			$('.resetAcc').each (function(){
				$(this).val('');
				});
			$('.resetAcc1').each (function(){
				$(this).val('0');
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
SubDropDownDataDisplay(id_grp,'requestPrAssetSubGroup','M_Subasset_Div',function (status){
	if(status)
	{
		SubDropDownDataDisplay(id_sgrp,'proNameForReqPR','M_Prod_Cart',function (status){
			if(status)
			{
$.post('P_Request_PR',{action : 'EditLineItem',id : no_asset},function (r){
				
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
						//$('input[name="id"]').val(r.data[0].id_inv_m);
						
						
						
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

function DisplayAssetRPPreview(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'DisplayAssetForPR' , id : id},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong><center>Product Name</center></strong></td>'+
								'<td><strong><center>Quantity</center></strong></td>'+
								'<td><strong><center>Unit Price</center></strong></td>'+
								'<td><strong><center>Total  Price</center></strong></td>'+
								/*'<td style="width: 105px;"><strong><center><a href="#">Delete</a></center></strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							*/'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_prod+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tot_prc+'</center></td>'+
									/*'<td><center><strong><a class="alert" href="#" onclick="DeleteAssetPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req_asst+')"> Delete</a></strong></center></td>'+
									
									'<td style="width: 190px;"><center><strong><a class="alert" href="#" onclick="CreateAssetPR(\'Modify\',\'DisplayAssetRP\',\'createAssetPR\','+params.id_req_asst+','+params.id_grp+','+params.id_sgrp+')"> Modify </a><a class="alert" href="#" onclick="DeleteAssetPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req_asst+')"> Delete</a></strong></td>'+
									*/
									'</tr>';
				}
			
				$('input[name="id"]').val(id);
				$('.assetPRForDisplayPre').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

			}
});
}


function DisplayAssetRP(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'DisplayAssetForPR' , id : id},function (r){
		var list= '';
		if(r.data)
			{
				 list= '<tr class="tableHeader info">'+
				 '<td ><strong><center>Model<a href=#></a></center></strong></td>'+
					/*'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+*/
					
					'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
					'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
	
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				j=i+1;
				params = r.data[i];
				
	
								
								list = list +	'<td><input style="width: 120px !important;" type="text"  value="'+params.nm_model+'" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
										/*'<td><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
										'<option value="">Select</option></select>'+
										'</center></td>'+
										'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
										'<option value="">Select</option></select>'+
										'</center></td>'+*/
										
										
									 	'<td><center><input type="text"  name="qty'+i+'" value="'+params.qty+'" style="width:60px;;text-align: right;"   class="common-validation" patelUnPrc="'+i+'"  onBlur="return CalcTotal(\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

										'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onBlur="return CalcTotal(\'un_prc\')" class=" common-validation"    ></center></td>'+
									
									       '<td><center><input type="text"  name="tot_prc'+i+'" value="'+params.tot_prc+'" patelUnPrc="'+i+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
									
											'<td ><a class="alert" href="#" onclick="DeleteAssetPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req_asst+')"> Delete</a></td>'+
						
						'<input type="hidden" name="id_req_asst'+i+'" value="'+params.id_req_asst+'">'+	
						'<input type="hidden" name="count" value="'+i+'">'+
						
					'</tr>';
					$('select[name=asset_consumable'+i+'] option[value=' + params.asset_consumable + ']').attr('selected',true);
				}
				
				$('input[name="id"]').val(id);
				$('input[name="itemCount"]').val(j);
				$('input[name="grand_tot1"]').val(r.data[0].grand_tot);
				$('#accessoryDetails').html(list);
			
				DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					if(status)
					{
					SubDropDownDataDisplay('0','dropsgrp','M_Subasset_Div',function (status){
						
						
						
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							
							for (var key in r.data[i])
					        {
								
									
									if(key=='id_grp')
									{
										$('select[name=id_grp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									if(key=='id_sgrp')
									{
										$('select[name=id_sgrp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									
					        }
							
						}	
					});
					
					
					}});
				
				
			}
		else
			{
				alert("Try again.");
			}
		
		
		$("#dynItemData0").easyAutocomplete(options);
		$("#dynItemData1").easyAutocomplete(options);
		$("#dynItemData2").easyAutocomplete(options);
		$("#dynItemData3").easyAutocomplete(options);
		$("#dynItemData4").easyAutocomplete(options);
		$("#dynItemData5").easyAutocomplete(options);
		$("#dynItemData6").easyAutocomplete(options);
		$("#dynItemData7").easyAutocomplete(options);
		$("#dynItemData8").easyAutocomplete(options);
		$("#dynItemData9").easyAutocomplete(options);
		$("#dynItemData10").easyAutocomplete(options);
		$("#dynItemData11").easyAutocomplete(options);
		$("#dynItemData12").easyAutocomplete(options);
		$("#dynItemData13").easyAutocomplete(options);
		$("#dynItemData14").easyAutocomplete(options);
		$("#dynItemData15").easyAutocomplete(options);
		$("#dynItemData16").easyAutocomplete(options);
		$("#dynItemData17").easyAutocomplete(options);
		$("#dynItemData18").easyAutocomplete(options);
		$("#dynItemData19").easyAutocomplete(options);
		$("#dynItemData20").easyAutocomplete(options);
		$("#dynItemData21").easyAutocomplete(options);
		$("#dynItemData22").easyAutocomplete(options);
		$("#dynItemData23").easyAutocomplete(options);
		$("#dynItemData24").easyAutocomplete(options);
		$("#dynItemData25").easyAutocomplete(options);
		$("#dynItemData26").easyAutocomplete(options);
		$("#dynItemData27").easyAutocomplete(options);
		$("#dynItemData28").easyAutocomplete(options);
		$("#dynItemData29").easyAutocomplete(options);
		$("#dynItemData30").easyAutocomplete(options);
		
	},'json');
	

			}

});

}

function DisplayAssetRPReview(servletName,displayContent,createDetails,id,DisplayTableClass)
{
	$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#DisplayAssetRP').show();
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'DisplayAssetForPR' , id : id},function (r){
		var list= '';
		if(r.data)
			{
				 list= '<tr class="tableHeader info">'+
				 '<td ><strong><center>Model<a href=#></a></center></strong></td>'+
					/*'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+*/
					
					'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
					'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
	'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				j=i+1;
				params = r.data[i];
				

				list = list +	'<td><input style="width: 120px !important;" type="text" readonly value="'+params.nm_model+'" id="dynItemData'+i+'" name="id_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);"></td>'+
						/*'<td><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" readonly name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
						'<option value="">Select</option></select>'+
						'</center></td>'+
						'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" readonly name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
						'<option value="">Select</option></select>'+
						'</center></td>'+*/
						
						
					 	'<td><center><input type="text"  name="qty'+i+'" value="'+params.qty+'" readonly style="width:60px;;text-align: right;"   class="common-validation" patelUnPrc="'+i+'"  onBlur="return CalcTotal(\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

						'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" readonly value="'+params.un_prc+'" style="width: 60px;text-align: right;" onBlur="return CalcTotal(\'un_prc\')" class=" common-validation"    ></center></td>'+
					
					       '<td><center><input type="text"  name="tot_prc'+i+'" value="'+params.tot_prc+'" readonly patelUnPrc="'+i+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
					
						
						
						'<input type="hidden" name="id_req_asst'+i+'" value="'+params.id_req_asst+'">'+	
						'<input type="hidden" name="count" value="'+i+'">'+
						//'<td ><strong><center><a class="alert" href="#" onclick="DeleteAssetPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req_asst+')"> Delete</a></center></strong></td>'+
						
					'</tr>';
				}
			
				$('input[name="id"]').val(id);
				$('input[name="itemCount"]').val(j);
				$('#accessoryDetails').html(list);
				$('input[name="grand_tot1"]').val(r.data[0].grand_tot);
				
				DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					if(status)
					{
					SubDropDownDataDisplay('0','dropsgrp','M_Subasset_Div',function (status){
						
						
						
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							
							for (var key in r.data[i])
					        {
								
									
									if(key=='id_grp')
									{
										$('select[name=id_grp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									if(key=='id_sgrp')
									{
										$('select[name=id_sgrp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									
					        }
							
						}	
					});
					
					
					}});
			}
		else
			{
				alert("Try again.");
			}
		
		
	},'json');

			}
});
}

function DisplayPR(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
				$.post(servletName,{action : 'Display' , no_req_val : no_req_val,dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>Request Number</strong></center></td>'+
					'<td><center><strong>Request Date</strong></center></td>'+
					'<td><center><strong>Requested By </strong></center></td>'+
					'<td><center><strong>Sub-Function </strong></center></td>'+
					'<td><center><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_req+'</center></td>'+
											'<td><center>'+params.reqdt+'</center></td>'+
											'<td><center>'+params.nm_emp+'</center></td>'+
											'<td><center>'+params.nm_dept+'</center></td>'+
											'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req+','+params.id_dept+',\'\','+params.id_loc+','+params.id_sloc+','+params.id_building+')"> Modify </a><a class="alert" href="#" onclick="DeletePR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req+')"> Delete</a></center></strong></td>'+
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
});
}
function DisplayPRPreview(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
				$.post(servletName,{action : 'Display' , no_req_val : no_req_val,dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>Request Number</strong></center></td>'+
					'<td><center><strong>Request Date</strong></center></td>'+
					'<td><center><strong>Requested By </strong></center></td>'+
					'<td><center><strong>Sub-Function </strong></center></td>'+
					'<td><center><strong><a href="#">Preview </a> / <a href="#">Send For Approval </a></strong></center></td>'+
							'</tr>';
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_req+'</center></td>'+
						'<td><center>'+params.reqdt+'</center></td>'+
						'<td><center>'+params.nm_emp+'</center></td>'+
						'<td><center>'+params.nm_dept+'</center></td>'+
						'<td style="width:300px;"><strong><center><a class="alert" href="#" onclick="EditPRSendForApproval(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req+','+params.id_dept+','+params.id_cc+','+params.id_loc+','+params.id_sloc+','+params.id_building+')"> Preview </a><a class="alert" href="#" onclick="SendForApproval('+params.id_req+')"> Send For Approval </a></center></strong></td>'+
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
});
}
function DisplayPreviewDetailsformail(id_req)
{
	
	
	$.post('P_Request_PR',{action : 'DisplayRequestForPreview',id:id_req},function (r){
		
		var list='<tr>'+
						'<td width="20%" style=""><img style="height:100px;"src="https://pbs.twimg.com/profile_images/1271385506505347074/QIc_CCEg.jpg"><center><font size="2"><b><br><b style="font-size: larger;">Purchase Request Print/E-mail Preview</b></font></center> <b style="float:right;">Date : '+r.todaydate+'</b></td>'+
				'</tr>'+
		'<tr >'+
			'<td style="border-bottom-style: hidden;border-top-style: hidden;">'+
				'<table align="center" width="100%">'+
					'<tr style="border-bottom-style: hidden;">'+
						'<td style="border-left-style: none;" colspan="2"><strong>PR Number : '+r.data[0].no_req+'</strong></td>'+
						'<td style="border-left-style: none;"><strong>PR Date : '+r.data[0].reqdt+'</strong></td>'+
					'</tr>'+
					'<tr style="border-bottom-style: hidden;">'+
						'<td style="border-left-style: none;" colspan="2"><strong> Sub-Function : '+r.data[0].nm_dept+'</strong></td>'+
						'<td style="border-left-style: none;"><strong>Created By :'+r.data[0].nm_emp+' </strong></td>'+
					'</tr>'+
				'</table>'+
			'</td>'+
		'</tr>'+
		'<tr ><td>'+
		'<table width="100%" class="table table-bordered" border="1"><tr>'+
				'<td><strong>Model Name</strong></td>'+
				'<td><strong>Model Code</strong></td>'+
				'<td><strong>Order Qty </strong></td>'+
				'<td><strong>Unit Price </strong></td>'+
				'<td><strong>Total Price</strong></td>'+
			
			'</td>'+
		'</tr>';
		
				
		if(r.data.length > 0)
		{
				var tot=0;
			for(var i = 0; i < r.data.length ; i++)
			{
				params = r.data[i];	
				tot=tot+parseFloat(params.tot_prc);
				list += '<tr>'+
					'<td>'+params.nm_model+'</td>'+
					'<td>'+params.cd_model+'</td>'+
					'<td>'+params.qty+'</td>'+
					'<td>'+params.un_prc+'</td>'+
					'<td>'+params.tot_prc+'</td>'+
				'</tr>';
			}
		list += '<tr>'+
					'<td colspan="3"></td>'+
					
					'<td>Grand Total</td>'+
					'<td>'+tot+'</td>'+
				'</tr>';
			list +='</table></td></tr>'+
					'<tr>'+
						'<td style="border-bottom-style: hidden;border-top-style: hidden;">'+
						'<table align="center" width="100%" style="margin-top: 30px;">'+
							/*'<tr style="border-bottom-style: hidden;">'+
								'<td style="border-left-style: none;line-height: 100px;" ><strong>Proposer : ................................</strong></td>'+
								'<td style="border-left-style: none;line-height: 100px;"><strong>Vetted By : ................................</strong></td>'+
								'<td style="border-left-style: none;line-height: 100px;"><strong>Approved By : ................................</strong></td>'+
							'</tr>'+
							'<tr style="border-bottom-style: hidden;">'+
								'<td style="border-left-style: none;" ><strong>Signature 1 : ................................</strong></td>'+
								'<td style="border-left-style: none;"><strong>Signature 2 : ................................</strong></td>'+
								'<td style="border-left-style: none;"><strong>Signature 3 : ................................</strong></td>'+
							'</tr>'+*/
						'</table>'+
					'</td>'+
				'</tr>';	
			
			$('#print_mail').html(list);
			
		}else
			{
				list +='<tr><td><strong><center>No record(s) is available..</center></strong></td></tr>';
				$('.previewIndentForPrint').html(list);
			}
		
	},'json');
	
	

}