function DropDownResultpovendor(servletName,dropDownId,callback)
{
	var t =false;
	var list= '';
	$.post(servletName,{action : 'DropDownResultpovendor'},function (r){
	
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



function dispalyDirectInvoiceLineItemDynamically(action,callback){
	
	if(action == 'New')
	{
		//alert("hello");
		var list ='<tr>'+
			'<td colspan="14" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
		'<tr class="tableHeader info">'+
		
			'<td style="min-width:40px"><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Remaining Quantity<a href=#></a></center></strong></td>'+
			'<td ><strong><center>GRN Quantity<a href=#></a></center></strong></td>'+
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
			'<select id="text"  name="id_po'+i+'" patelPo="'+i+'" id="id_po'+i+'" style="min-width:40px;text-align: right;"  class="common-validation commonPo " patelUnPrc="'+i+'" onchange="SubDropDownDataDisplayforpoitem(this,\'id_po_asset'+i+'\',\'Direct_Invoice\');"  >'+
			'<option value=""> Select </option>'+
			'</select>'+
			'</center></td>'+
			
			'<td><center>'+
			'<select id="id_po_asset'+i+'" name="id_po_asset'+i+'" patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation prodforgrn" onchange="changeEventHandlerprodforGRN(event);" >'+
			'<option value=""> Select </option>'+
			'</select>'+
			'</center></td>'+
			'<input type="hidden" name="id_prod'+i+'"  value="">'+	
			'<td><center><input type="text"  name="po_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onchange="calculateTot(event,\'qty\');checkRemQty1(event);" class="patelUnPrc data-valid="num"></center></td>'+//checkRemQty(event);
			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class=" common-validation" data-valid="num" readonly ></center></td>'+
			'<td><input type="text" name="others'+i+'" value="0" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax1'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax2'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
			'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
			'<td><center><input type="text"  name="buyback'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
			'<td><center><input type="text"  name="gr_tot'+i+'" value="0.0" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
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
						
					}});
				
			}});
	}
	else{
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr>'+
		
		'<td><center>'+
		'<select  name="id_po'+i+'" id="id_po'+i+'" style="min-width:40px;text-align: right;"  class="common-validation commonPo" patelUnPrc="'+i+'" onchange="SubDropDownDataDisplayforpoitem(this,\'id_po_asset'+i+'\',\'Direct_Invoice\');"  >'+
		'<option value=""> Select </option>'+
		'</select>'+	
		'</center></td>'+
		
		'<td><center>'+
		'<select id="id_po_asset'+i+'" name="id_po_asset'+i+'" patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation " onchange="changeEventHandlerprodforGRN(event);" >'+
		'<option value=""> Select </option>'+
		'</select>'+
		'</center></td>'+
		'<input type="hidden" name="id_prod'+i+'"  value="">'+	
		'<td><center><input type="text"  name="po_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
		'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
		'<td><center><input type="text"  name="qty'+i+'" style="width:60px;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onchange="calculateTot(event,\'qty\');checkRemQty1(event);" class="patelUnPrc data-valid="num" ></center></td>'+
		'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readonly></center></td>'+
		'<td><input type="text" name="others'+i+'" value="0" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
		'<td><center>'+
		'<select style="width: 100px;" name="id_tax1'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')"  >'+
		'</select>'+
		'</center></td>'+
		'<td><center>'+
		'<select style="width: 100px;" name="id_tax2'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" >'+
		'</select>'+
		'</center></td>'+
		'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
		'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
		'<td><center><input type="text"  name="buyback'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
		'<td><center><input type="text"  name="gr_tot'+i+'" value="0.0" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+	
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
					
							var id_ven= $('select[name="id_ven"]').val();
							
							if(id_ven !='')
								{
								SubDropDownDataDisplayForClass(id_ven,"id_po"+i,'Direct_Invoice');
								/*SubDropDownDataDisplayForClass(id_ven,"id_po"+i,'Direct_Invoice',function (status){
									if(status)
									{
									}});*/
								}
							/*DropDownDataDisplay("M_Prod_Cart","nm_prod"+i,function (status){
								if(status)
								{
									
								}});*/
						
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

function checkRemQty1(event) {	
	var domElement =$(event.target || event.srcElement);
	var rem_qty = $('input[name="rem_qty'+domElement.attr('patelUnPrc')+'"]').val();
	var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
	if(parseFloat(qty)>parseFloat(rem_qty))
		{
		alert("Quantity Should not be Greater Than Remaining Quantity...");
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').addClass('error');
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').focus();
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0);
		}
	else
		{
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').removeClass('error');
		}
    
    
  }
function checkRemQty(event) {	
	var domElement =$(event.target || event.srcElement);
	var rem_qty = $('input[name="rem_qty'+domElement.attr('patelUnPrc')+'"]').val();
	var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
	if(parseInt(qty)>parseInt(rem_qty))
		{
		alert("Quantity Should not be Greater Than Remaining Quantity...");
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').addClass('error');
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').focus();
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0);
		}
	else
		{
		$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').removeClass('error');
		}
    
    
  }


function changeEventHandlerprodforGRN(event) {	
	var domElement =$(event.target || event.srcElement);
	var id_po_asset = $('select[name="id_po_asset'+domElement.attr('patelUnPrc')+'"]').val();
	
    $.post('Direct_Invoice',{action : 'GetPODetails', id:id_po_asset},function (r){
		
		if(r.data)
			{
			params = r.data[0];
			
			
				$('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val(params.id_prod);
				$('input[name="po_qty'+domElement.attr('patelUnPrc')+'"]').val(params.qty);
				$('input[name="rem_qty'+domElement.attr('patelUnPrc')+'"]').val(params.rem_qty);
				$('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val(params.un_prc);
				$('select[name="id_tax1'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax1);
				$('select[name="id_tax2'+domElement.attr('patelUnPrc')+'"]').val(params.id_tax2);
				$('input[name="buyback'+domElement.attr('patelUnPrc')+'"]').val(params.buyback);
				$('input[name="others'+domElement.attr('patelUnPrc')+'"]').val(params.others);
				$('input[name="gr_tot'+domElement.attr('patelUnPrc')+'"]').val(params.gr_tot);
				$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(params.qty);
				

			}
		
	},'json');
    
  }



function ControlDirectInvoice(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('input[name="dt_req"]').attr("disabled", false);
	
	
	if(action=='Create')
		{
		
		$.post('Direct_Invoice',{action : 'auto_jwno'},function (r){
			
			$('input[name="no_inv"]').val(r.wo_no);
				
},'json');
	
		
		dispalyDirectInvoiceLineItemDynamically("New",function (status){
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
			

			
		}
	if(action=='Cancel')
		{
			
		$( ".creategrn" ).trigger( "click" );
		}
	
	
	
	if(action=='Save' || action=='Update')
		{
		
			if(action == 'Save')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Save');
				DataInsertUpdateForInvoice("Cancel",displayContent,createDetails,formName,servletName);
				}
			else
				{
					$('input[name="action"]').val('Update');
				}
			
			
			
		}
	
	if(action == 'Update')
	{
		var t=false;
		t=ValidationForm('submitVerbalOrder');
		if(t)
		{
			$('.upd').attr('disabled','disabled');

			
			var x = $('#submitVerbalOrder').serialize();
			$.post('Direct_Invoice',x,function (r){
				if(r.data == 1)
					{
					bootbox.alert("Update successfully.");
					$( ".creategrn" ).trigger( "click" );
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

function DisplayDropDownDataForVendorLaunch(servletName,dropDownId,type_ven,callback)
{
	//alert("hello");
	var t =false;
	var list='';
	//$.post(servletName,{action : 'DropDownResultRFQ',type_ven:type_ven,no_ind:no_ind},function (r){
		$.post(servletName,{action : 'DropDownResultLaunch',type_ven:type_ven},function (r){
		if(r.data)
			{
			t=true;
			if(dropDownId != 'assetDivForRequestQuotation')
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
				list = list + '<option value="'+id+'"> '+val+'</option>';
				}
			
			
				$('#'+dropDownId).html(list);
			
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
			
			$.post(servletName,x,function (r){
				
				if(r.data)
					{
					
					bootbox.alert("GRN has been successfully Created");
						$( ".creategrn" ).trigger( "click" );
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


function DisplayGRN(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var searchWord=$('input[name="search"]').val();
			//alert(servletName);
			$.post(servletName,{action : 'Display' , no_req_val : no_req_val,searchWord : searchWord},function (r){
				

				var list= '<tr class="tableHeader info">'+
				'<td><center><strong>GRN Number</strong></center></td>'+
				'<td><center><strong>GRN Date</strong></center></td>'+
				
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong><a href="#">Modify </a>  </strong></center></td>'+
				//'<td><center><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></center></td>'+
			'</tr>';
					
				if(r.data.length > 0)
					{
						
					
						for(var i = 0; i < r.data.length ; i++)
						{
							params = r.data[i];
							
						
							
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_inv+'</center></td>'+
						'<td><center>'+params.dt_inv+'</center></td>'+
						
						'<td><center>'+params.nm_ven+'</center></td>'+
						
						'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditDC(\'Modify\',\''+displayContent+'\',\''+createDetails+'\',\''+params.id_inv_m+'\')"> Modify </a></center></strong></td>'+
					
						//'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditDC(\'Modify\',\''+displayContent+'\',\''+createDetails+'\',\''+params.id_dc+'\')"> Modify </a><a class="alert" href="#" onclick="DeleteAsset(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dc+')"> Delete</a></center></strong></td>'+
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

function DisplayMyGRN(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var searchWord=$('input[name="search"]').val();
			//alert(servletName);
			$.post(servletName,{action : 'DisplayMy' , no_req_val : no_req_val,searchWord : searchWord},function (r){
				

				var list= '<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
					'<td><center><strong>PO Date</strong></center></td>'+
				
				'<td><center><strong>GRN Number</strong></center></td>'+
				'<td><center><strong>GRN Date</strong></center></td>'+
				
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong><a href="#">Preview </a>  </strong></center></td>'+
				//'<td><center><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></center></td>'+
			'</tr>';
					
				if(r.data.length > 0)
					{
						
					
						for(var i = 0; i < r.data.length ; i++)
						{
							params = r.data[i];
							
						
							
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.dt_po+'</center></td>'+
						
						
						'<td><center>'+params.no_inv+'</center></td>'+
						'<td><center>'+params.dt_inv+'</center></td>'+
						
						'<td><center>'+params.nm_ven+'</center></td>'+
						
						'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditMyDC(\'Modify\',\''+displayContent+'\',\''+createDetails+'\',\''+params.id_inv_m+'\')"> Preview </a></center></strong></td>'+
					
						//'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditDC(\'Modify\',\''+displayContent+'\',\''+createDetails+'\',\''+params.id_dc+'\')"> Modify </a><a class="alert" href="#" onclick="DeleteAsset(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dc+')"> Delete</a></center></strong></td>'+
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
							$( ".creategrn" ).trigger( "click" );
						}
						if(r.data == 2)
						{
							bootbox.confirm("If you delete this ,Then all aseet will be deleted related to this purchase.", function(result1) {
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
						}
						
					},'json');
				}
			}); 
			}
		
	});
}


// opposite SubDropDown using class
function SubDropDownDataDisplayForClass(ids,dropDownClass,servletName,callback)
{
	
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
		
			$.post(servletName,{action:'DropDownResultForClass' , id : id},function (r){
				
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
									list = list + '<option value=" '+id+' ">'+val+'</option>';
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

function SubDropDownDataDisplayforpoitem(ids,dropDownClass,servletName,callback)
{
	var t = false;
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
		
			$.post(servletName,{action:'DropdownPOItem' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('.'+dropDownId).html(list);
								$('.'+dropDownId).focus();
								
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
									list = list + '<option value="'+val+'">'+id+'</option>';
									}
							}
					
						$('#'+dropDownClass).html(list);
						$('#'+dropDownClass).focus();
					}
				else
					{
						bootbox.alert(" Please select the value.");
					}
				callback(t);
			},'json');
		}
}
// SubDropDown using class
function DisplaySubDropDownDataForClass(ids,dropDownClass,servletName,callback)
{
	var t = false;
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
		
			$.post(servletName,{action:'DropDownResultForClass' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('.'+dropDownId).html(list);
								$('.'+dropDownId).focus();
								
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
					
						$('.'+dropDownClass).html(list);
						$('.'+dropDownClass).focus();
					}
				else
					{
						bootbox.alert(" Please select the value.");
					}
				callback(t);
			},'json');
		}
}

function DisplaySubDropDownDataForClassCreation(ids,dropDownClass,servletName,callback)
{
	var t = false;
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
		
			$.post(servletName,{action:'DropDownResultForClassCreation' , id : id},function (r){
				
				if(r.data)
					{
					t=true;
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('.'+dropDownId).html(list);
								$('.'+dropDownId).focus();
								
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
					
						$('.'+dropDownClass).html(list);
						$('.'+dropDownClass).focus();
					}
				else
					{
						bootbox.alert(" Please select the value.");
					}
				callback(t);
			},'json');
		}
}

function EditDC(action,displayContent,createDetails,id_inv_m){
	var id_ven=0;
	$.post('Direct_Invoice',{action : 'Edit',id : id_inv_m},function (r){
		
		if(r.data.length > 0)
			{
			id_ven=r.data[0].id_ven;
			
			for(var i = 0; i < r.data.length ; i++)
				
				for (var key in r.data[i])
					{
					
						if($('select[name='+key+']').is("select") && r.data[i][key] != '')
							
							$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
						else
							$('input[name='+key+']').val(r.data[i][key]);
						
					}
			
				
				$('button[name="save"]').addClass('hideButton');
				$('button[name="update"]').removeClass('hideButton');
				$('#'+displayContent).hide();
				$('#'+createDetails).show();
				//$('#DisplayAssetRP').show();
			
				$('#createAssetPR').show();
				//$('#DisplayAssetRP').hide();
				$('input[name="dt_inv"]').val(r.data[0].dtinv);
				$('input[name="id"]').val(id_inv_m);
			

			}
		/*else
			bootbox.alert("Try again.");*/
		DisplayLineItemDynamicallyForEditLineItem(displayContent,createDetails,id_inv_m,id_ven);
	},'json');
	
	

}
function EditMyDC(action,displayContent,createDetails,id_inv_m){
	var id_ven=0;
	$.post('Direct_Invoice',{action : 'EditMy',id : id_inv_m},function (r){
		
		if(r.data.length > 0)
			{
			id_ven=r.data[0].id_ven;
			
			for(var i = 0; i < r.data.length ; i++)
				
				for (var key in r.data[i])
					{
					
						if($('select[name='+key+']').is("select") && r.data[i][key] != '')
							
							$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
						else
							$('input[name='+key+']').val(r.data[i][key]);
						
					}
			
				
				$('button[name="save"]').addClass('hideButton');
				$('button[name="update"]').removeClass('hideButton');
				$('#'+displayContent).hide();
				$('#'+createDetails).show();
				//$('#DisplayAssetRP').show();
			
				$('#createAssetPR').show();
				//$('#DisplayAssetRP').hide();
				$('input[name="dt_inv"]').val(r.data[0].dtinv);
				$('input[name="id"]').val(id_inv_m);
			

			}
		/*else
			bootbox.alert("Try again.");*/
		DisplayLineItemDynamicallyForEditLineItemMy(displayContent,createDetails,id_inv_m,id_ven);
	},'json');
	
	

}
function DisplayLineItemDynamicallyForEditLineItem(displayContent,createDetails,id_inv_m,id_ven)
{
$.post('Direct_Invoice',{action : 'DisplayLineItem',id:id_inv_m},function (r){
	
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
		'<td style="min-width:40px"><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Remaining Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>GRN Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
		'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			'<td><center>'+
			'<select   name="id_po'+i+'" patelPo="'+i+'" id="id_po'+i+'" value="'+params.id_po+'" style="min-width:40px;text-align: right;"  class="common-validation commonPo " patelUnPrc="'+i+'" onchange="SubDropDownDataDisplayforpoitem(this,\'id_po_asset'+i+'\',\'Direct_Invoice\');"  >'+
			'</select>'+
			'</center></td>'+
			
			'<td><center>'+
			'<select id="id_po_asset'+i+'" name="id_po_asset'+i+'" value="'+params.id_po_asset+'" patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation prodforgrn" onchange="changeEventHandlerprodforGRN(event);" >'+
			'</select>'+
			'</center></td>'+
			
			
			'<td><center><input type="text"  name="po_qty'+i+'"  style="width:60px;;text-align: right;"  value="'+params.po_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.po_rem_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.qty+'" class="common-validation" patelUnPrc="'+i+'" onchange="calculateTot(event,\'qty\');checkRemQty1(event);" class="patelUnPrc data-valid="num"></center></td>'+
			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readonly ></center></td>'+
			'<td><input type="text" name="others'+i+'" value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax1'+i+'" value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax2'+i+'" value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')">'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
			'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
			'<td><center><input type="text"  name="buyback'+i+'" patelUnPrc="'+i+'" value="'+params.buyback+'" onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
			'<td><center><input type="text"  name="gr_tot'+i+'" value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
			
			
			//'<td><center><strong><a class="alert" href="#" onclick="DeleteAssetForGRN(\'Direct_Invoice\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dc_asset+')"> Delete</a></strong></center></td>'+
			
			
			'<input type="hidden" name="id_prod'+i+'" value="'+params.id_model+'">'+
			'<input type="hidden" name="count" value="'+i+'">'+	
			
			'</tr>';
	
			}
		
			
		$('input[name="itemCount"]').val(parseInt(i));
		
	
		$('.EditSalesOrder').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
		
		
		
		}
	for(i=0;i<r.data.length;i++)
	{
		params=r.data[i];
		
		SubDropDownDataDisplayforpoitem(params.id_po,'id_po_asset'+i,'Direct_Invoice',function (status){
			if(status)
			{
				
			}});
	
	}
	
			
			SubDropDownDataDisplayForClass(id_ven,'commonPo','Direct_Invoice',function (status){
				if(status)
				{
			
					DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
						if(status)
						{
							DropDownDataDisplay("M_Tax","taxDataForDropDown12",function (status){
								if(status)
								{
									for(var i=0;i<r.data.length;i++)
									{
										params=r.data[i];
										for (var key in r.data[i])
										{
							
												if(key == 'id_po'){
													console.log(key);
												$('select[name=id_po'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
				       
											}
											if(key == 'id_po_asst'){
												$('select[name=id_po_asset'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
				       
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
									
								}});
							
						}});
							
						}});
		
	
	$( ".modifyPODatepicker2" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_po=$('.poDatepicker').val();
	    	  var dt_po1=$('.poDatepicker').val();
	    	  var dt_dlvr = $(this).val();
	    	  
	    	  if(dt_po == '')
	    		  {
	    		  	alert('First filled the P.O date.');
	    		  	$('.modifyPODatepicker2').focus();
	    		  	$('.modifyPODatepicker2').addClass('error');
	    		  	$('.modifyPODatepicker2').val('');
	    		  	$(this).val('');
	    		  	exit(0);
	    		  }
	    	  else
	    		  {
	    		  var temp_strt = dt_po.split("/");
					 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						
					var temp_end = dt_dlvr.split("/");
					var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						
					dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
		    		  dt_dlvr = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
	    		  if(dt_po > dt_dlvr)
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
	
	
	
	
},'json');

}
function DisplayLineItemDynamicallyForEditLineItemMy(displayContent,createDetails,id_inv_m,id_ven)
{
$.post('Direct_Invoice',{action : 'DisplayLineItemMy',id:id_inv_m},function (r){
	
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
		//'<td style="min-width:40px"><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Remaining Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>GRN Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
		'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			//'<td><center>'+params.no_po+'</center></td>'+
			'<td><center>'+params.nm_model+'</center></td>'+
			'<td><center>'+params.po_qty+'</center></td>'+
			'<td><center>'+params.po_rem_qty+'</center></td>'+
			'<td><center>'+params.qty+'</center></td>'+
			'<td><center>'+params.un_prc+'</center></td>'+
			'<td><center>'+params.others+'</center></td>'+
			'<td><center>'+params.nm_tax1+'</center></td>'+
			'<td><center>'+params.nm_tax2+'</center></td>'+
			'<td><center>'+params.tax_val1+'</center></td>'+
			'<td><center>'+params.tax_val1+'</center></td>'+
			'<td><center>'+params.buyback+'</center></td>'+
			'<td><center>'+params.gr_tot+'</center></td>'+
			'</tr>';
	
			}
		
			
		$('.EditSalesOrder').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
		
		
		
		}
		
	
	
},'json');

}
function DeleteAssetForGRN(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{	
	bootbox.confirm("Are you sure?", function(result) {
		if(result)
		{
			id_dc=$('#id').val();
			//alert(id_dc);
			//alert(id);
			
			$.post(servletName,{action : 'DeleteAssetForGRN',id : id ,id_dc1:id_dc},function (r){
				
				if(r.data == 1)
				{
					alert("Item is succesfully deleted!!!");
					$( ".creategrn" ).trigger( "click" );
					
				}
			if(r.data == 2)
				{
					bootbox.confirm("This is last record of this Request if delete this then Sales Order also deleted.", function(result1) {
						if(result1)
						{
							$.post(servletName,{action : 'DeleteAssetForGRN',id : id , id_dc : '0'},function (r){
								
								if(r.data == 1)
									{
									$( ".creategrn" ).trigger( "click" );
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





function DisplayGRNForAcceptReject(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var searchWord=$('input[name="search"]').val();
			$.post(servletName,{action : 'DisplayForAcceptReject' , no_req_val : no_req_val,searchWord:searchWord},function (r){
				

				var list= '<tr class="tableHeader info">'+
				'<td><center><strong>DC Number</strong></center></td>'+
				'<td><center><strong>DC Date</strong></center></td>'+
				'<td><center><strong>Vendor</strong></center></td>'+
				'<td><center><strong>GRN Number </strong></center></td>'+
				'<td><center><strong>GRN Date </strong></center></td>'+
				
				
				'<td><center><strong><a href="#">Accept/Reject Grn</a></strong></center></td>'+
			'</tr>';
					
				if(r.data.length > 0)
					{
						
					
						for(var i = 0; i < r.data.length ; i++)
						{
							params = r.data[i];
							
						
							
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_inv+'</center></td>'+
						'<td><center>'+params.dtinv+'</center></td>'+
						'<td><center>'+params.nm_ven+'</center></td>'+
						'<td><center>'+params.no_gd_rcv+'</center></td>'+
						'<td><center>'+params.dtgdrcv+'</center></td>'+
						
				
						
						'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditDCForAcceptReject(\'Modify\',\''+displayContent+'\',\''+createDetails+'\',\''+params.id_dc+'\')"> Accept/Reject Grn </a></center></strong></td>'+
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

function EditDCForAcceptReject(action,displayContent,createDetails,id_dc){
	var id_ven=0;
	$.post('Direct_Invoice',{action : 'Edit',id : id_dc},function (r){
		
		if(r.data.length > 0)
			{
			id_ven=r.data[0].id_ven;
			
			for(var i = 0; i < r.data.length ; i++)
				
				for (var key in r.data[i])
					{
					
						if($('select[name='+key+']').is("select") && r.data[i][key] != '')
							
							$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
						else
							$('input[name='+key+']').val(r.data[i][key]);
						
					}
			
				
				$('button[name="save"]').addClass('hideButton');
				$('button[name="update"]').removeClass('hideButton');
				$('#'+displayContent).hide();
				$('#'+createDetails).show();
				//$('#DisplayAssetRP').show();
			
				$('#createAssetPR').show();
				//$('#DisplayAssetRP').hide();
				$('input[name="dt_inv"]').val(r.data[0].dtinv);
				$('input[name="dt_gd_rcv"]').val(r.data[0].dtgdrcv);
				$('input[name="id"]').val(id_dc);
			

			}
		/*else
			bootbox.alert("Try again.");*/
		DisplayLineItemDynamicallyForEditLineItemForAcceptReject(displayContent,createDetails,id_dc,id_ven);
	},'json');
	
	

}

function DisplayLineItemDynamicallyForEditLineItemForAcceptReject(displayContent,createDetails,id_dc,id_ven)
{
$.post('Direct_Invoice',{action : 'DisplayLineItem',id:id_dc},function (r){
	
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
			
			var params='',totPrice=0;
		var list ='<tr class="tableHeader info">'+
		'<td><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		//'<td><strong><center>Part Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		/*'<td ><strong><center>Description<a href=#></a></center></strong></td>'+*/
		'<td ><strong><center>UOM<a href=#></a></center></strong></td>'+
		'<td ><strong><center>PO Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Remaining Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>GRN Quantity<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Others<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
		'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
		'<td ><strong><center> Accept Quantity <a href=#></a></center></strong></td>'+
		'<td ><strong><center> Reject Quantity<a href=#></a></center></strong></td>'+
		//'<td style="width: 105px;"><center><strong><a href="#">Delete</a></strong></center></td>'+
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			'<td><center>'+
			'<select   name="id_po'+i+'" patelPo="'+i+'" id="id_po'+i+'" value="'+params.id_po+'" style="width:60px;text-align: right;"  class="common-validation commonPo " patelUnPrc="'+i+'"  readonly onchange="SubDropDownDataDisplay(this,\'id_po_asset'+i+'\',\'Direct_Invoice\');" readonly  >'+
			'</select>'+
			'</center></td>'+
			
			'<td><center>'+
			'<select id="id_po_asset'+i+'" name="id_po_asset'+i+'" value="'+params.id_po_asset+'" patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation prodforgrn" onchange="changeEventHandlerprodforGRN(event);" readonly >'+
			'</select>'+
			'</center></td>'+
			/*'<input type="hidden" name="id_prod'+i+'"  value="">'+	*/
			//'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'" style="padding: 0px;width:80px" class="common-validation resetAcc " patelUnPrc="'+i+'"  onchange="changeEventHandlercode(event);"></td>'+
			/*'<td><center><input type="text" maxlength="500" name="description'+i+'"  value="" class="common-validation" ></center></td>'+*/
			
			'<td><center>'+
			'<select style="width: 100px;" name="id_uom'+i+'" value="'+params.id_uom+'"  patelUnPrc="'+i+'" taxName="" class="common-validation uomforSales" readonly>'+
			'</select>'+
			'</center></td>'+
			
			//'<td><input type="text" name="id_uom'+i+'"  style="width:60px" class="common-validation resetAcc " patelUnPrc="'+i+'" onchange="changeEventHandlerprod(event);" readonly></td>'+
			
			'<td><center><input type="text"  name="po_qty'+i+'"  style="width:60px;;text-align: right;"  value="'+params.po_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="rem_qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.rem_qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+
			'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="'+params.qty+'" class="common-validation" patelUnPrc="'+i+'"  class="patelUnPrc data-valid="num" readonly></center></td>'+

			'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="'+params.un_prc+'" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" readonly ></center></td>'+
			'<td><input type="text" name="others'+i+'" value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  onChange="calculateTot(event,\'others\')" class="common-validation" readonly></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax1'+i+'" value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')" readonly>'+
			'</select>'+
			'</center></td>'+
			'<td><center>'+
			'<select style="width: 100px;" name="id_tax2'+i+'" value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_val2\')" readonly>'+
			'</select>'+
			'</center></td>'+
			'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val1+'" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
			'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="'+params.tax_val2+'" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
			'<td><center><input type="text"  name="discount'+i+'" patelUnPrc="'+i+'" value="'+params.discount+'" onChange="calculateTot(event,\'discount\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
			'<td><center><input type="text"  name="gr_tot'+i+'" value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
			'<td><center><input type="text"  name="accept_qty'+i+'" patelUnPrc="'+i+'" value="'+params.qty+'" style="width:60px;text-align: right;" class="common-validation " onchange="CalculateRejectQty(event,\'accept_qty\');checkRemQty(event);" ></center></td>'+
			'<td><center><input type="text"  name="reject_qty'+i+'" value="'+params.reject_qty+'" style="width:60px;text-align: right;" class="common-validation "  readonly ></center></td>'+
			
			//'<td><center><strong><a class="alert" href="#" onclick="DeleteAssetForGRN(\'Direct_Invoice\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dc_asset+')"> Delete</a></strong></center></td>'+
			
			
			'<input type="hidden" name="id_prod'+i+'" value="'+params.id_prod+'">'+
			'<input type="hidden" name="count" value="'+i+'">'+	
			
			'</tr>';
			totPrice += parseFloat(params.grnd_tot);
			}
		
			
		$('input[name="itemCount"]').val(parseInt(i));
		
	
		$('.EditSalesOrder').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
		
		
		
		}
	for(i=0;i<r.data.length;i++)
	{
		params=r.data[i];
		
		SubDropDownDataDisplay(params.id_po,'id_po_asset'+i,'Direct_Invoice',function (status){
			if(status)
			{
				
			}});
	
	}
	DropDownDataDisplay("M_UOM","uomforSales",function (status){
		if(status)
		{
			
			SubDropDownDataDisplayForClass(id_ven,'commonPo','Direct_Invoice',function (status){
				if(status)
				{
			
					DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
						if(status)
						{
							DropDownDataDisplay("M_Tax","taxDataForDropDown12",function (status){
								if(status)
								{
									for(var i=0;i<r.data.length;i++)
									{
										params=r.data[i];
										for (var key in r.data[i])
										{
							
											if(key == 'id_uom'){
												$('select[name=id_uom'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
				       
											}
											if(key == 'id_po'){
												$('select[name=id_po'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
				       
											}
											if(key == 'id_po_asset'){
												$('select[name=id_po_asset'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
				       
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
									
								}});
							
						}});
							
						}});
		}});
	
	$( ".modifyPODatepicker2" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_po=$('.poDatepicker').val();
	    	  var dt_po1=$('.poDatepicker').val();
	    	  var dt_dlvr = $(this).val();
	    	  
	    	  if(dt_po == '')
	    		  {
	    		  	alert('First filled the P.O date.');
	    		  	$('.modifyPODatepicker2').focus();
	    		  	$('.modifyPODatepicker2').addClass('error');
	    		  	$('.modifyPODatepicker2').val('');
	    		  	$(this).val('');
	    		  	exit(0);
	    		  }
	    	  else
	    		  {
	    		  var temp_strt = dt_po.split("/");
					 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						
					var temp_end = dt_dlvr.split("/");
					var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						
					dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
		    		  dt_dlvr = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
	    		  if(dt_po > dt_dlvr)
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
	
	
	
	
},'json');

}


function checkRemQty(event) {	
	var domElement =$(event.target || event.srcElement);
	var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
	var accept_qty = $('input[name="accept_qty'+domElement.attr('patelUnPrc')+'"]').val();
	if(parseFloat(accept_qty)>parseFloat(qty))
		{
		alert("Accept Quantity Should not be Greater Than GRN Quantity...");
		$('input[name="accept_qty'+domElement.attr('patelUnPrc')+'"]').addClass('error');
		$('input[name="accept_qty'+domElement.attr('patelUnPrc')+'"]').focus();
		$('input[name="accept_qty'+domElement.attr('patelUnPrc')+'"]').val(0);
		}
	else
		{
		$('input[name="accept_qty'+domElement.attr('patelUnPrc')+'"]').removeClass('error');
		}
    
    
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
		reject_qty=parseFloat(qty)-parseFloat(accept_qty);
		$('input[name="reject_qty'+domElement.attr('patelUnPrc')+'"]').val(reject_qty.toFixed(3));
			
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



function ControlGRNForAcceptReject(action,displayContent,createDetails,formName,servletName) {
	SessionCheck(function (ses){		
			if(ses)
				{
		
			$(".common-validation").each(function(){
			
				$(".common-validation").removeClass('error');
			});
			$('input[name="dt_req"]').attr("disabled", false);
		
		
		
		if(action=='Cancel')
			{
				
			$( ".acceptrejectgrn" ).trigger( "click" );
			}
		
		
		
		if(action=='Save' || action=='Update1')
			{
			
				if(action == 'Save')
					{
					$('button[name="save1"]').removeClass('hideButton');
					$('input[name="action"]').val('Save');
					DataInsertUpdateForInvoice("Cancel",displayContent,createDetails,formName,servletName);
					}
				else
					{
						$('input[name="action"]').val('Update1');
					}
				
				
				
			}
		
		if(action == 'Update1')
		{
			var t=false;
			t=ValidationForm('submitVerbalOrder');
			if(t)
			{
				$('.upd').attr('disabled','disabled');

				
				var x = $('#submitVerbalOrder').serialize();
				$.post('Direct_Invoice',x,function (r){
					if(r.data == 1)
						{
						bootbox.alert("Update successfully.");
						$( ".acceptrejectgrn" ).trigger( "click" );
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

