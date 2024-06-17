

function dispalyPOLineItemDynamically(action,callback){
	
	if(action == 'New')
	{
		var list ='<tr>'+
			'<td colspan="14" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
		'<tr class="tableHeader info">'+
			'<td style="width:400px;"><strong><center>Material Type<a href=#></a></center></strong></td>'+
			'<td style="width:400px;"><strong><center>Item Name<a href=#></a></center></strong></td>'+
			'<td style="width:60px;"><strong><center>Item Code</center></strong></td>'+
			//'<td><strong><center>Specification</center></strong></td>'+
			'<td><strong><center>UOM</center></strong></td>'+
			//'<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Item Price <font color="red"> * </font></center></strong></td>'+
			
			'<td style="width: 75px;"><strong><center>Freight rate <font color="red"> * </font></center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Others<font color="red"> * </font></center></strong></td>'+
			'<td style="width: 120px;"><strong><center>Tax Name</center></strong></td>'+
			'<td style="width: 75px;"><strong><center>Tax Value</center></strong></td>'+
			'<td><strong><center>Unit Price<font color="red">*</font></center></strong></td>'+
			'<td style="width:70px;"><strong><center>Stock Qty <font color="red">*</font></center></strong></td>'+
			'<td style="width:70px;"><strong><center>Quantity <font color="red">*</font></center></strong></td>'+
			'<td><strong><center>Total Price</center></strong></td>'+
		'</tr>';
		
		for(var i = 0; i < 10 ; i++)
		{
			list+='<tr>'+
			' <td >'+
				'<select name="asset_consumable'+i+'"   style="width:130px" patelUnPrc="'+i+'" class="common-validation" data-valid="mand">'+
						'<option value="ConG">Material Goods</option>'+
						'<option value="CapG">Capital Goods</option>'+
				'</select>'+
			'</td>'+
			'<td><input type="text" list="itemData" name="nm_prod'+i+'" patelUnPrc="'+i+'" class="common-validation resetAcc" id="item" onchange="changeEventHandler(event);MakeDeliveryDateMandatory(event);">'+
				'<datalist id="itemData" >'+
				'</datalist>'+
			'</td>'+
			'<td><input type="text" name="cd_prod'+i+'"  style="width:130px" class="common-validation resetAcc " patelUnPrc="'+i+'" readonly></td>'+
			'<td><input type="text" name="uom'+i+'"  style="width:130px" class="common-validation resetAcc " patelUnPrc="'+i+'" readonly></td>'+
			//'<td><input type="text" name="description'+i+'" style="width:130px" class="resetAcc " patelUnPrc="'+i+'"></td>'+
			//'<td><center><input type="text" name="dt_rec_quot'+i+'" value="" style="width: 100px;" class="common-validation poDatepicker2 autoset" ></center></td>'+
			'<td><center><input type="text" name="item_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'item_prc\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
			
			'<td><center><input type="text" name="freight_rate'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'freight_rate\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
			'<td><center><input type="text" name="others_chrg'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'others_chrg\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="taxId'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'taxId\')">'+
			'</select>'+
			'</center></td>'+
			'<input type="hidden" name="taxPercent'+i+'" value="0">'+
			'<td><center><input type="text" style="width: 60px;" name="taxVal'+i+'" taxVal="'+i+'" value="0.00" readonly style="width: 60px;" class="patelTax"></center></td>'+
			
			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readOnly></center></td>'+
			'<td><input type="text" name="stock_qty'+i+'"  patelUnPrc="'+i+'" class="common-validation resetAcc FieldResize" patelUnPrc="'+i+'"  readonly></td>'+
			'<td><center><input type="text" name="qty'+i+'"  patelUnPrc="'+i+'" value="1.00" onBlur="calculateTot(event,\'qty\')" style="width: 50px;" class="patelQty" data-valid="num"></center></td>'+
			
			'<td><center><input type="text" name="tot_prc'+i+'" value="0.00" style="width: 80px;" class="commonTotal" readonly></center></td>'+
			
			'<input type="hidden" name="count" value="'+i+'">'+		
		'</tr>';
			
		}
		var list2 ='<tr>'+
				'<td colspan="12"><b style="float:right;">Installation Charge</b></td>'+
				'<td style="width: 84px;"><center><input type="text" name="frt_rate" value="0.00" style="width: 80px;" data-valid="num" onBlur="addInTotal(\'frt_rate\')" class="common-validation" ></center></td>'+
			'</tr>'+
			'<tr>'+
				'<td colspan="12"><b style="float:right;">Additional charges</b></td>'+
				'<td><center><input type="text" name="add_chrg" value="0.00" style="width: 80px;" data-valid="num" onBlur="addInTotal(\'add_chrg\')" class="common-validation" ></center></td>'+
			'</tr>'+
			'<tr>'+
				'<td colspan="12"><b style="float:right;">Others Charge</b></td>'+
				'<td><center><input type="text" name="frt_text" value="0.00" style="width: 80px;" data-valid="num" onBlur="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
			'</tr>'+
			'<tr>'+
				'<td colspan="12"><b style="float:right;">Total</b></td>'+
				'<td><center><input type="text" name="tot" value="0.00" style="width: 80px;" class="common-validation" readonly></center></td>'+
				'<input type="hidden" name="gr_tot" value="0.00" style="width: 80px;" class="common-validation" readonly></center>'+
		
				'</tr>';
			/*'<tr>'+
				'<td colspan="7">Grand Total</td>'+
				'<td><input type="text" name="grand_tot" value="0.0" id="grand_tot " class="resetAcc1 FieldResize" readonly></td>'+
			'</tr>';*/
		
		$('input[name="itemCount"]').val('10');
		$('input[name="grand_tot1"]').val('0.00');
		
		$('#accessoryDetails').html(list);
		$('#accessoryDetails1').html(list2);
	}else{
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr>'+
					' <td >'+
					'<select name="asset_consumable'+i+'"   style="width:130px" patelUnPrc="'+i+'" class="common-validation" data-valid="mand">'+
							'<option value="ConG">Material Goods</option>'+
							'<option value="CapG">Capital Goods</option>'+
					'</select>'+
				'</td>'+
				'<td><input type="text" list="itemData" name="nm_prod'+i+'" patelUnPrc="'+i+'" class="common-validation resetAcc" id="item" onchange="changeEventHandler(event);MakeDeliveryDateMandatory(event);">'+
					'<datalist id="itemData" >'+
					'</datalist>'+
				'</td>'+
				'<td><input type="text" name="cd_prod'+i+'"  style="width:130px" class="common-validation resetAcc " patelUnPrc="'+i+'" readonly></td>'+
				'<td><input type="text" name="uom'+i+'"  style="width:130px" class="common-validation resetAcc " patelUnPrc="'+i+'" readonly></td>'+
				//'<td><input type="text" name="description'+i+'" style="width:130px" class="resetAcc " patelUnPrc="'+i+'"></td>'+
				//'<td><center><input type="text" name="dt_rec_quot'+i+'" value="" patelUnPrc="'+i+'" style="width: 100px;" class="common-validation poDatepicker2 autoset"></center></td>'+
				'<td><center><input type="text" name="item_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'item_prc\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				
				'<td><center><input type="text" name="freight_rate'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'freight_rate\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td><center><input type="text" name="others_chrg'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'others_chrg\')" class="patelUnPrc common-validation" data-valid="num"></center></td>'+
				'<td><center>'+
				'<select style="width: 100px;" name="taxId'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'taxId\')">'+
				'</select>'+
				'</center></td>'+
				'<input type="hidden" name="taxPercent'+i+'" value="0">'+
				'<td><center><input type="text" style="width: 60px;" name="taxVal'+i+'" taxVal="'+i+'" value="0.00" readonly style="width: 60px;" class="patelTax"></center></td>'+
				
				'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;" onBlur="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readOnly></center></td>'+
				'<td><input type="text" name="stock_qty'+i+'"  patelUnPrc="'+i+'" class="common-validation resetAcc FieldResize" patelUnPrc="'+i+'"  readonly></td>'+
				'<td><center><input type="text" name="qty'+i+'"  patelUnPrc="'+i+'" value="1.00" onBlur="calculateTot(event,\'qty\')" style="width: 50px;" class="patelQty" data-valid="num"></center></td>'+
				
				'<td><center><input type="text" name="tot_prc'+i+'" value="0.00" style="width: 80px;" class="commonTotal" readonly></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+		
			'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('#accessoryDetails').append(list);
	}
	//callback(true);	
	DropDownDataDisplayForItem("M_Prod_Cart","itemData",function (status){
		if(status)
		{
			DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
				if(status)
				{
					
				}});
		}});
	
	$( ".poDatepicker2" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_po=$('.poDatepicker').val();
	    	  var dt_po1=$('.poDatepicker').val();
	    	  var dt_delv=$(this).val();
	    	  if(dt_po == '')
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
	    		  var temp_strt = dt_po.split("/");
					 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						
					var temp_end = dt_delv.split("/");
					var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						
					dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
					dt_delv = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
	    		  if(dt_po > dt_delv)
	    			  {
	    			  	alert('Delivery date should be greater or equal to P.O date : '+dt_po1);
	    			  	$(this).focus();
	    			  	$(this).val('');
	    			  	$(this).addClass('error');
	    			  	exit(0);
	    			  }
	    		  
	    		  }
	    	  
	      }
	    });
	
}

function TaxCalculation(event)
{

	var gr_tot =0.00;
	var domElement =$(event.target || event.srcElement);
	var un_prc = $('input[name="item_prc'+domElement.attr('patelTax')+'"]').val();
	var qty = $('input[name="qty'+domElement.attr('patelTax')+'"]').val();
	var id_tax = $('select[name="taxId'+domElement.attr('patelTax')+'"]').val();
var t=true;
console.log(id_tax);
	if(id_tax != undefined && id_tax != '')
		{
			if(id_tax == '0')
				{
				t=false;
				$('input[name="taxId'+domElement.attr('patelTax')+'"]').val('0.00');
				$('input[name="taxVal'+domElement.attr('patelTax')+'"]').val('0.00');
				$('input[name="taxPercent'+domElement.attr('patelTax')+'"]').val('0.00');
				$('input[name="tot_prc'+domElement.attr('patelTax')+'"]').val(parseFloat(un_prc)*parseFloat(qty));
				$('.commonTotal').each(function (){
						if($(this).val() != undefined)
							gr_tot += +$(this).val();
						
					});
				 $('input[name="tot"]').val(gr_tot.toFixed(2));
				 $('input[name="gr_tot"]').val(gr_tot.toFixed(2));
				}
		}
		
	if(t)
		{
	$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
		
		if(r.data.length > 0 )
			{
			 var percent = r.data[0].per_tax;
			 
			 var item_prc = $('input[name="item_prc'+domElement.attr('patelTax')+'"]').val();
			 var freight_rate = $('input[name="freight_rate'+domElement.attr('patelTax')+'"]').val();
			 var others_chrg = $('input[name="others_chrg'+domElement.attr('patelTax')+'"]').val();
			 var taxVal = (((parseFloat(item_prc)+ parseFloat(freight_rate)+parseFloat(others_chrg) )*parseFloat(percent)))/100;
			 
			 var un_prc = parseFloat(item_prc)+parseFloat(taxVal) + parseFloat(freight_rate)+parseFloat(others_chrg);
			   
				 $('input[name="un_prc'+domElement.attr('patelTax')+'"]').val(un_prc.toFixed(2));
			 
			 var tot= +parseFloat(qty)*parseFloat(un_prc);
			 
					 $('input[name="taxVal'+domElement.attr('patelTax')+'"]').val(taxVal.toFixed(2));
					 $('input[name="tot_prc'+domElement.attr('patelTax')+'"]').val(tot.toFixed(2));
					 $('input[name="taxPercent'+domElement.attr('patelTax')+'"]').val(percent);
					 
					 
					 $('.commonTotal').each(function (){
							if($(this).val() != undefined)
								gr_tot += +$(this).val();
							
						});
					 $('input[name="tot"]').val(gr_tot.toFixed(2));
					 $('input[name="gr_tot"]').val(gr_tot.toFixed(2));
			}
		
		
	},'json');
	
		}
}
function ControlVerbalPurchaseOrder(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('input[name="dt_req"]').attr("disabled", false);
	
	
	if(action=='Create')
		{
		dispalyPOLineItemDynamically("New",function (status){
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
			
$.post('Verbal_Purchase_Order',{action : 'LoginUserDetails'},function (r){
				
				if(r.data)
					{
					SubDropDownDataDisplay(r.data[0].id_dept,'requestedPrCC','M_Cost_Center',function (status){
						if(status)
						{
							SubDropDownDataDisplay(r.data[0].id_cc,'sectionDataForRequest','M_Section',function (status){
								if(status)
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
									$('select[name=req_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected',true);
								
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
			DisplayVerbalOrder(servletName,displayContent,createDetails,'','PRForDisplay');
		}
	
	if(action == 'SendForApproval'){
		var id = $('input[name="id"]').val();
		$.post('Verbal_Purchase_Order',{action : 'SendForApproval',id:id},function (r){
			if(r.data ==1)
			{
				$('#DisplayAssetRP').hide();
				$('#createAssetPR').hide();
				$('#'+createDetails).hide();
				
				$('#'+displayContent).show();
				DisplayPRPreview('Verbal_Purchase_Order','displayPR','createPR','','PRForDisplay');
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
	bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
	$.post('Verbal_Purchase_Order',{action : 'SendForApproval',id : id},function (r){
		
		if(r.data ==1)
			{
				$('#DisplayAssetRP').hide();
				$('#createAssetPR').hide();
				$('#createPR').hide();
				
				$('#displayPR').show();
				DisplayPRPreview('Verbal_Purchase_Order','displayPR','createPR','','PRForDisplay');
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
					//$('input[name="no_req_val"]').val(r.no_req);
						bootbox.alert("PO has been successfully added:"+r.data+" ");
			            //DisplayAssetRP(servletName,displayContent,createDetails,r.data,'assetPRForDisplay');
			           /* $('#'+createDetails).show();
						$('#DisplayAssetRP').show();*/
						$( ".POThroughSingle" ).trigger( "click" );
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

function EditPRSendForApproval(servletName,displayContent,createDetails,id,id_dept,id_cc)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			//DisplayAssetRPPreview('Verbal_Purchase_Order','displayPR','createPR',id,'assetPRForDisplay');
	DisplayAssetRPReview('Verbal_Purchase_Order','displayPR','createPR',id,'assetPRForDisplay');
	
	
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
						$('input[name="grand_tot1"]').val(r.data[0].grand_tot);
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_req"]').val(id);
						$('input[name="no_req_val"]').val(r.data[0].no_req);
						$('input[name="id_cc"]').val(r.data[0].id_cc);
						$('input[name="dt_req"]').val(r.data[0].dt_req);
						
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
	
});
}
function EditPR(servletName,displayContent,createDetails,id,id_dept,id_cc)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			//DisplayAssetRPPreview('Verbal_Purchase_Order','displayPR','createPR',id,'assetPRForDisplay');
	DisplayAssetRP('Verbal_Purchase_Order','displayPR','createPR',id,'assetPRForDisplay');
	
	
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
						$('input[name="grand_tot1"]').val(r.data[0].grand_tot);
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_req"]').val(id);
						$('input[name="no_req_val"]').val(r.data[0].no_req);
						$('input[name="id_cc"]').val(r.data[0].id_cc);
						$('input[name="dt_req"]').val(r.data[0].dt_req);
						
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
							DisplayVerbalOrder(servletName,displayContent,createDetails,'','PRForDisplay');
						}
						if(r.data == 2)
						{
							bootbox.confirm("If you delete this , then all aseet will be deleted related to this Request.", function(result1) {
								if(result1)
								{
									$.post(servletName,{action : 'Delete',id : id , confirm : '0'},function (r){
										
										if(r.data == 1)
											{
												DisplayVerbalOrder(servletName,displayContent,createDetails,'','PRForDisplay');
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
										ControlVerbalPurchaseOrder('Cancel',displayContent,createDetails,'',servletName);
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
$.post('Verbal_Purchase_Order',{action : 'EditLineItem',id : no_asset},function (r){
				
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
								'<td><b>Material Type<font color="red"> * </font>:</b></td>'+
				 				
								'<td><strong>Select Item</strong></td>'+
								'<td><strong>Item Code</strong></td>'+
								'<td><strong>Description</strong></td>'+
								'<td><strong>Current Store Stock</strong></td>'+
								'<td><strong>Quantity <font color="red"> * </font></strong></td>'+
								'<td><strong>UOM </strong></td>'+
								'<td><strong>Unit Price <font color="red"> * </font></strong></td>'+
								'<td><strong>Total Price</strong></td>'+
								'<td><strong><a href="#">Delete</a></strong></td>'+
								/*'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							*/'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				j=i+1;
				params = r.data[i];
				
		list = list + '<tr>'+
						' <td >'+
						'<select name="asset_consumable'+i+'"   style="width:130px" patelUnPrc="'+i+'" class="common-validation" data-valid="mand">';
								if(params.asset_consumable =='ConG'){
									list = list+	'<option value="ConG" selected="selected">Material Goods</option>'+
									'<option value="CapG">Capital Goods</option>';
								}
								
								else{
									list = list +'<option value="ConG" >Material Goods</option>'+
									'<option value="CapG" selected="selected">Capital Goods</option>';
								}
									
								
				list = list +'</select>'+
						'<td><input type="text" list="itemData" name="nm_prod'+i+'" value="'+params.nm_prod+'" patelUnPrc="'+i+'" class="common-validation resetAcc" id="item" onchange="changeEventHandler(event);">'+
							'<datalist id="itemData" >'+
							'</datalist>'+
						'</td>'+
						'<td><input type="text" name="cd_prod'+i+'" value="'+params.cd_prod+'" style="width:130px" class="common-validation resetAcc " patelUnPrc="'+i+'"  readonly></td>'+
						'<td><input type="text" name="description'+i+'" value="'+params.description+'" style="width:130px" class="resetAcc " patelUnPrc="'+i+'"></td>'+
						'<td><input type="text" name="stock_qty'+i+'"  value="'+params.stock_qty+'" class="common-validation resetAcc FieldResize" patelUnPrc="'+i+'"  readonly></td>'+
						'<td><input type="text" name="qty'+i+'" value="'+params.qty+'" class="common-validation resetAcc1 FieldResize" patelUnPrc="'+i+'" data-valid="num" id="assetQty"  onBlur="return CalcTotal(event)"></td>'+
						'<td><input type="text" name="uom'+i+'" value="'+params.uom+'" class="common-validation FieldResize" data-valid="mand" patelUnPrc="'+i+'"></td>'+
						'<td><input type="text" name="un_prc'+i+'" value="'+params.un_prc+'" id="assetUnitPrice FieldResize"  patelUnPrc="'+i+'" class="common-validation resetAcc1 FieldResize" data-valid="mand" onBlur="return CalcTotal(event)"></td>'+   
						'<td><input type="text" name="tot_prc'+i+'" value="'+params.tot_prc+'" id="assetTotalPrice " patelUnPrc="'+i+'" class="resetAcc1 FieldResize" readonly></td>'+
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
			
			}
		else
			{
				alert("Try again.");
			}
		DropDownDataDisplayForItem("M_Prod_Cart","itemData",function (status){
			if(status)
			{
				
			}});
		
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
								'<td><b>Material Type<font color="red"> * </font>:</b></td>'+
								'<td><strong>Select Item</strong></td>'+
								'<td><strong>Item Code</strong></td>'+
								'<td><strong>Description</strong></td>'+
								'<td><strong>Current Store Stock</strong></td>'+
								'<td><strong>Quantity <font color="red"> * </font></strong></td>'+
								'<td><strong>UOM </strong></td>'+
								'<td><strong>Unit Price <font color="red"> * </font></strong></td>'+
								'<td><strong>Total Price</strong></td>'+
								//'<td><strong><a href="#">/ Delete</a></strong></td>'+
								/*'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							*/'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				j=i+1;
				params = r.data[i];
				
		list = list + '<tr>'+
						' <td >'+
							'<select name="asset_consumable'+i+'"   style="width:140" patelUnPrc="'+i+'" class="common-validation FieldResize" data-valid="mand" disabled >';
									if(params.asset_consumable =='ConG'){
										list = list+	'<option value="ConG" selected="selected">Material Goods</option>'+
										'<option value="CapG">Capital Goods</option>';
									}
									
									else{
										list = list +'<option value="ConG" >Material Goods</option>'+
										'<option value="CapG" selected="selected">Capital Goods</option>';
									}
										
									
			list = list +'</select>'+
						'</td>'+
						'<td><input type="text" list="itemData" name="nm_prod'+i+'" value="'+params.nm_prod+'" patelUnPrc="'+i+'" class="common-validation resetAcc FieldResize" id="item" readonly onchange="changeEventHandler(event);">'+
							'<datalist id="itemData" >'+
							'</datalist>'+
						'</td>'+
						'<td><input type="text" name="cd_prod'+i+'" value="'+params.cd_prod+'" class="common-validation resetAcc FieldResize" patelUnPrc="'+i+'"  readonly></td>'+
						'<td><input type="text" name="description'+i+'" value="'+params.description+'" class="resetAcc FieldResize" patelUnPrc="'+i+'" readonly></td>'+
						'<td><input type="text" name="stock_qty'+i+'"  value="'+params.stock_qty+'" class="common-validation resetAcc FieldResize" patelUnPrc="'+i+'"  readonly></td>'+
						'<td><input type="text" name="qty'+i+'" value="'+params.qty+'" class="common-validation resetAcc1 FieldResize" patelUnPrc="'+i+'" data-valid="num" readonly id="assetQty"  onBlur="return CalcTotal(event)"></td>'+
						'<td><input type="text" name="uom'+i+'" value="'+params.uom+'" class="common-validation FieldResize" data-valid="mand" patelUnPrc="'+i+'" readonly></td>'+
						'<td><input type="text" name="un_prc'+i+'" value="'+params.un_prc+'" id="assetUnitPrice FieldResize"  patelUnPrc="'+i+'" class="common-validation resetAcc1 FieldResize" readonly data-valid="mand" onBlur="return CalcTotal(event)"></td>'+   
						'<td><input type="text" name="tot_prc'+i+'" value="'+params.tot_prc+'" id="assetTotalPrice " patelUnPrc="'+i+'" class="resetAcc1 FieldResize" readonly></td>'+
						
						'<input type="hidden" name="id_req_asst'+i+'" value="'+params.id_req_asst+'">'+	
						'<input type="hidden" name="count" value="'+i+'">'+
						//'<td ><strong><center><a class="alert" href="#" onclick="DeleteAssetPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req_asst+')"> Delete</a></center></strong></td>'+
						
					'</tr>';
					$('select[name=asset_consumable'+i+'] option[value=' + params.asset_consumable + ']').attr('selected',true);
				}
			
				$('input[name="id"]').val(id);
				$('input[name="itemCount"]').val(j);
				$('#accessoryDetails').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		DropDownDataDisplayForItem("M_Prod_Cart","itemData",function (status){
			if(status)
			{
				
			}});
		
	},'json');

			}
});
}

function DisplayVerbalOrder(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
			$.post(servletName,{action : 'Display' , no_req_val : no_req_val,dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
				
				var list= '<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>Quotation Number</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong>Raised By</strong></center></td>'+
				'<td><center><strong>Raised Date</strong></center></td>'+
				//'<td style="width: 230px;"><strong><a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Add Asset &nbsp;&nbsp;&nbsp;&nbsp;</a><a href="#">/ &nbsp;&nbsp;&nbsp;&nbsp;Delete</a></strong></td>'+
			'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.no_quot+'</center></td>'+
						'<td><center>'+params.nm_ven+'</center></td>'+
						'<td><center>'+params.nm_emp+'</center></td>'+
						'<td><center>'+params.dt_po+'</center></td>'+
						//'<td style="width:200px;"><strong><a class="alert" href="#" onclick="ModifyDirectPO(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_po+','+params.id_dept+','+params.id_cc+','+params.id_section+')"> Add Asset </a><a class="alert" href="#" onclick="DeleteDPO(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_po+')"> Delete</a></strong></td>'+
						'</tr>';
						
						}
					
					
						$('.'+DisplayTableClass).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
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
					
					var list= '<tr class="tableHeader">'+
					'<td colspan="6"><center><strong><p class="tableHeaderContent" style="">Send for approval</p></strong></center></td>'+'</tr>'+
					'<tr class="tableHeader info">'+
					'<td><center><strong>Request Number</strong></center></td>'+
					'<td><center><strong>Requested Date</strong></center></td>'+
					'<td><center><strong>Requested By </strong></center></td>'+
					'<td><center><strong>From Section</strong></center></td>'+
					'<td><center><strong>To Section</strong></center></td>'+
					'<td><center><strong><a href="#">Preview </a> / <a href="#">Send For Approval </a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_req+'</center></td>'+
											'<td><center>'+params.dt_req+'</center></td>'+
											'<td><center>'+params.nm_emp+'</center></td>'+
											'<td><center>'+params.frm_sec+'</center></td>'+
											'<td><center>'+params.to_sec+'</center></td>'+
											'<td style="width:276px;"><strong><center><a class="alert" href="#" onclick="EditPRSendForApproval(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_req+','+params.id_dept+','+params.id_cc+')"> Preview </a><a class="alert" href="#" onclick="SendForApproval('+params.id_req+')"> Send For Approval </a></center></strong></td>'+
											'</tr>';
						}
					
					
						$('.'+DisplayTableClass).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+DisplayTableClass).html(list);
					}
				
			},'json');
		
			}
});
}
