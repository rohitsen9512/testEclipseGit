
function TaxCalculationadd(event)
{
	//alert('Warning : You are not supposed to change the value.');
	var gr_tot =0.00;
	var domElement =$(event.target || event.srcElement);
	
	var id_tax = $('select[name="taxIdadd'+domElement.attr('patelTaxadd')+'"]').val();

console.log(id_tax);

		
	$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
		
		if(r.data.length > 0 )
			{
			 var percent = r.data[0].per_tax;
			  
			
				 //line item extra charges......
				 var frt_rate = $('input[name="frt_rate'+domElement.attr('patelTaxadd')+'"]').val();
				 var frt_text = $('input[name="frt_text'+domElement.attr('patelTaxadd')+'"]').val();
				 var add_chrg = $('input[name="add_chrg'+domElement.attr('patelTaxadd')+'"]').val();
				 var buy_back = $('input[name="buy_back'+domElement.attr('patelTaxadd')+'"]').val();
				// var add_chrg1 = $('input[name="add_chrg1'+domElement.attr('patelTaxadd')+'"]').val();
				 var add_chrg2 = $('input[name="add_chrg2'+domElement.attr('patelTaxadd')+'"]').val();
					var add_chrg1=0.0;
					add_chrg1=((parseFloat(frt_rate)+parseFloat(frt_text)+parseFloat(add_chrg))*parseFloat(percent))/100;
					// alert(add_chrg1);
			 var tot= $('input[name="tot_prc'+domElement.attr('patelTaxadd')+'"]').val();
			 var grnd_tot=parseFloat(tot)+parseFloat(frt_rate)+parseFloat(frt_text)+parseFloat(add_chrg)+parseFloat(add_chrg1)+parseFloat(add_chrg2)-parseFloat(buy_back);
			 //alert(grnd_tot);
			 
			 $('input[name="add_chrg1'+domElement.attr('patelTaxadd')+'"]').val(add_chrg1.toFixed(2));
			 $('input[name="grnd_tot'+domElement.attr('patelTaxadd')+'"]').val(grnd_tot.toFixed(2));
					 
			//budget....
				var flag=domElement.attr('patelTaxadd');
				var j=parseInt(flag)+1;
				console.log(j);
				var id_bud2 = $('input[name="id_bud'+j+'"]').val();
				console.log(id_bud2);
					console.log("shanu");
					var ind_bidge2='';
					var x=2;
					var k=parseInt(flag)-1;
					console.log(k);
					var exchange_rate = $('input[name="exchange_rate"]').val();
					if(exchange_rate==0.0){
						alert("Please Select The Currency.");
						$('input[name="grnd_tot'+domElement.attr('patelUnPrc')+'"]').val(0.0);
						$('input[name="tot_prc'+domElement.attr('patelUnPrc')+'"]').val(0.0);
						$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0.0);
						
					}else{
					var ind_bidgecondition = $('input[name="ind_bidge'+domElement.attr('patelTaxadd')+'"]').val();
					if(parseFloat(ind_bidgecondition)>grnd_tot){
					
					for(var i=0;i<=500;i++){
						var ind_bidgecom=$('input[name="id_bud'+domElement.attr('patelTaxadd')+'"]').val();
						var ind_bidgecom2=$('input[name="id_bud'+i+'"]').val();
						console.log(ind_bidgecom2);
						if(ind_bidgecom == ind_bidgecom2){
						
							console.log("pritesh");
							if(x==2){
							 ind_bidge2 = $('input[name="ind_bidge'+i+'"]').val();
								x=3;	
							}
						//	var qty1 = $('input[name="qty'+i+'"]').val();
							//var unitprice= $('#assetUnitPrice').val();
							var grnd_tot = $('input[name="grnd_tot'+i+'"]').val();   
							if(parseFloat(grnd_tot)<=parseFloat(ind_bidge2)){
								
							 ind_bidge2=parseFloat(ind_bidge2)-parseFloat(grnd_tot)*parseFloat(exchange_rate);
							$('input[name="budget'+i+'"]').val(ind_bidge2.toFixed(2));
							}else{
								alert("Budget not Available! ");
								$('input[name="budget'+i+'"]').val(0.0);
							}
						}
						
						
						
				}
					}else{
						alert("Budget not Available! ");
						$('input[name="budget'+domElement.attr('patelTaxadd')+'"]').val(0.0);
					}	
					}
					 $('.commonTotal').each(function (){
							if($(this).val() != undefined)
								gr_tot += +$(this).val();
							
						});
					 var bottomtotal = parseFloat($('input[name="frt_rate"]').val())+parseFloat($('input[name="freight_amount"]').val()) +parseFloat($('input[name="frt_text"]').val()) +parseFloat($('input[name="add_chrg"]').val())-parseFloat($('input[name="buy_back"]').val())-parseFloat($('input[name="discount"]').val());
						
						// var others= $('input[name="others"]').val();
						 gr_tot = gr_tot + parseFloat(bottomtotal);
					 	 console.log(gr_tot);
					
					 
					 $('input[name="tot"]').val(gr_tot.toFixed(2));
				
			}
		
		
	},'json');
	
		
}


jQuery("input#uploadFileForRecQuot").change(function () {
	var formData = new FormData();
    formData.append('file', $('#uploadFileForRecQuot').get(0).files[0]);
	$.ajax({
	  url: 'Upload_File',
	    type: 'POST',
	    data: formData,
	    async: false,
	    cache: false,
	    contentType: false,
	    dataType: "json",
	    processData: false,
	    success: function (r) {
	    	
	    		$('input[name="nm_upload_file"]').val(r.upload_inv);
	    	//	bootbox.alert("File Uploaded successfully");
	    		
	    	
	    }
	},'json');
});

function dispalyPOLineItemDynamically(action,callback){
	
	if(action == 'New')
	{
		var list ='<tr class="success">'+
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' +
			'<tr class="tableHeader info">' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td style="display:none;"><strong><center>Qty<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td><strong><center>Remarks</center></strong></td>'+
	 	    '<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +			
			'<td ><strong><center>Total<a href=#></a></center></strong></td>' +
			'</tr>';
		
		for(var i = 0; i < 10 ; i++)
		{
			list+='<tr class="success">'+
			
				' <td style="display:none;">' +
	              '<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				'<td><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				//'<td><input style="width: 120px !important;" type="text"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control "></td>' +
				'<td><input style="width: 120px !important;" type="text" name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></td>' +
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;display:none;"  value="1" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\');"; class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" value="0" style="width:100px;text-align: right;" class="form-control "  onChange="CheckInvoice(event)" readonly></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.0" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num" ></center></td>' +
		 		'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" class="form-control" remarks="'+i+'" value=""  style="width: 100px;" maxlength="500"></center></td>'+
				'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"  value="" patelUnPrc="'+i+'"  class="form-control poDatepicker2"  style="width: 120px;"  ></center></td>'+					
				'<td><input type="text" name="others' + i + '" value="0.0" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" style="width: 120px;" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2" style="width: 120px;" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			/*	'<td><center><input type="text" name="tr_chrg' + i + '" patelUnPrc="' + i + '" value="0.0" style="width: 100px;text-align: right;" onChange="calculateTotlead(event,\'tr_chrg\')" class="form-control"></center></td>' +*/
				
				'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
		}
		var list2 ='<tr class="success">'+
		'<td colspan="13"><input type="text" name="oter_text" value="Other Changes" style="float:right;"  class="common-validation" ></td>'+
		 '<td><center><input type="text" name="frt_text" value="0.0" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
	        '</tr>'+
	
	        '<tr class="success">'+
	      '<td colspan="13"><b style="float:right;">Discount</b></td>'+
	        '<td><center><input type="text" name="discount" value="0.0" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
             '</tr>'+
        	'<tr class="success">'+
	     	'<td colspan="13"><b style="float:right;">Grand Total</b></td>'+
	      	'<td><center><input type="text" name="tot" value="0.0" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
         '</tr>';
    
		
		$('input[name="itemCount"]').val('10');
		$('input[name="grand_tot1"]').val('0.00');
		
		$('#accessoryDetails2').html(list);
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
						}
				});
			}
		});

		$('#accessoryDetails1').html(list2);
		
		
		
	}else{
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr class="success">'+

			' <td style="display:none;">' +

	             '<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				'<td><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				//'<td><input style="width: 120px !important;" type="text"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control "></td>' +
				'<td><input style="width: 120px !important;" type="text"  name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></td>' +
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;display:none;"  value="1" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" value="0" style="width:100px;text-align: right;" class="form-control " readonly></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.0" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"></center></td>' +
			 	'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value=""  style="width: 100px;" maxlength="500"></center></td>'+
				'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"  value="" patelUnPrc="'+i+'"  class="form-control poDatepicker2"  style="width: 120px;"  ></center></td>'+				
				'<td><input type="text" name="others' + i + '" value="0.0" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.0" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				//'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '"   value="0.0" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				
				'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
			     '<input type="hidden" name="id_lead' + i + '" value="">' +
			       '<input type="hidden" name="count" value="' + i + '">' +
		      	'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('#accessoryDetails2').append(list);
	//$('#accessoryDetails1').html(list);
	
		DropDownDataDisplay("M_Tax", "id_tax1" + i, function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "id_tax2" + i, function(status) {
					if (status) {
					}
				});
			}
		});
	
	
	
	}

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
	
	
	
}


function ControlDirectVerbalPurchaseOrder(action,displayContent,createDetails,formName,servletName) {
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
	/*	$('.EditPurchaseOrder').hide();
		$('.EditPurchaseOrder2').hide();
		$('.dodirect').hide();
	*/	$('.readbaledata').each(function (){
			
			$(this).removeAttr('readonly', 'readonly');
			if($(this).is("select"))
				{
					$(this).attr("disabled", false);
				}
			
		});

	
		debugger;
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
			$('#pobutton').hide();
			$('#directh1').hide();
		}
	if(action=='Cancel')
		{
			$('#pobutton').show();
			$('#directh1').show();
			$('#DisplayAssetRP').hide();
			$('#createAssetPR').hide();
			$('#'+createDetails).hide();
			
			$('#'+displayContent).show();
			//DisplayVerbalOrder(servletName,displayContent,createDetails,'','PRForDisplaydirect');
			DisplaydirectVerbalOrder('Verbal_Purchase_Order','displaydirectVerbalOrder','createdirectVerbalOrder','','PRForDisplaydirect');
			
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
				//DisplayPRPreview('Verbal_Purchase_Order','displayPR','createPR','','PRForDisplaydirect');
				DisplaydirectVerbalOrder('Verbal_Purchase_Order','displaydirectVerbalOrder','createdirectVerbalOrder','','PRForDisplaydirect');
				
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
	
	if(action == 'Update')
	{
		var t=false;
		t=ValidationForm('submitdirectVerbalOrder');
		if(t)
		{
			//$('.upd').attr('disabled','disabled');

			
			var x = $('#submitdirectVerbalOrder').serialize();
			$.post('Modify_Purchase_Order',x,function (r){
				if(r.data == 1)
					{
					bootbox.alert("Update successfully.");
					$( ".DirectPurchaseOrder" ).trigger( "click" );
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				//$('.upd').removeAttr('disabled');
			},'json');
		
		}
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
			
$.post('NewPurchaseOrder',{action : 'CreatePOIdDynamic'},function (r){
				
				if(r.PONum)
					{
					bootbox.confirm("Are you sure you want to create this po number:"+r.PONum+" ?", function(result) {
						if(result)
						{
						$('.req').attr('disabled','disabled');
						$('button[name="save"]').removeClass('hideButton');
						$('button[name="update"]').addClass('hideButton');
						var x = $('#'+formName).serialize();
							$.post(servletName,x,function (r){
								
								if(r.data)
									{
										bootbox.alert("PO has been successfully added:"+r.data+" ");
										$( ".DirectPurchaseOrder" ).trigger( "click" );
										$('#pobutton').show();
										$('#directh1').show();
										$('#DisplayAssetRP').hide();
										$('#createAssetPR').hide();
										$('#'+createDetails).hide();
										$('#'+displayContent).show();
										DisplaydirectVerbalOrder('Verbal_Purchase_Order','displaydirectVerbalOrder','createdirectVerbalOrder','','PRForDisplaydirect');
									}
								else
									{
										bootbox.alert("Try again.");
									}
								$('.req').removeAttr('disabled');
								
							},'json');
						}
					});
					
					}
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
						//$('input[name="dt_transptr"]').val(r.data[0].dt_transptrs);
						
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




function DisplaydirectVerbalOrder(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
				$('#createdirectVerbalOrder').hide();
				debugger;
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
			$.post(servletName,{action : 'DisplayDirect' , dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
				
				var list= '<tr class="tableHeader info new">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>PO Date</strong></center></td>'+
				//'<td><center><strong>Po Type</strong></center></td>'+
					'<td style="width: 70px;"><strong><center>Modify</center></strong></td>'+	'</tr>';
					'</tr>';
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						debugger
						params = r.data[i];
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.dtpo+'</center></td>'+
						//'<td><center>'+params.nm_po_type+'</center></td>'+
					'<td><strong><center><a href="#" onclick="ReclassificationModifyPO(\'Edit\',\'createdirectVerbalOrder\',\'displaydirectVerbalOrder\',\''+params.id_po+'\')"> Modify </a></center></strong></td>'+
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
function ReclassificationModifyPO(action,DisplayDiv,HideDiv,id_po){
	/*SubDropDownDataDisplay(id_dept,'requestedPrCC','M_Cost_Center',function (status){
		if(status)
		{
			SubDropDownDataDisplay(id_cc,'sectionDataForRequest','M_Section',function (status){
				if(status)
				{
					DisplaySubDropDownData(id_section,'orgLocDataForPO','M_ORG_LOCATION',function (status){
						if(status)
						{
							ReclassificationPurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po);
						}});
				}});
		}});*/
		
	PurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po);
}
function ReclassificationPurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po)
{	debugger;
			

			
			/*$('#displaydirectVerbalOrder').hide();
			$('#createdirectVerbalOrder').show();*/
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			$('#pobutton').hide();
			$('#createAssetPR').show();
			$('#directh1').hide();
			
	$.post('Modify_Purchase_Order',{action : 'Edit',id_po:id_po},function (r){
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
					if(r.data[0].remarks!=undefined || r.data[0].mode_pur!='')
					{
						$('#remarks').val(r.data[0].remarks);
						}
					
					
				}
				
				var params='',totPrice=0;
			var list ='<tr class="success">'+
			'<td ><strong><center>Product<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Description<a href=#></a></center></strong></td>'+
			'<td><strong><center>Stock</center></strong></td>'+
		    //'<td><strong><center>Delivery Date <font color="red">*</font></center></strong></td>'+
			
				//'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
				'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
				'<td ><strong><center>Remarks<a href=#></a></center></strong></td>'+
				'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
				'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
				'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
				'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
				//'<td ><strong><center>Discount<a href=#></a></center></strong></td>'+
				'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
				
		'</tr>';
			var i=0;
			for(i=0;i<r.data.length;i++)
				{
				 params=r.data[i];
				
				 
					var remarks='',dt_scheduled_dlvry2='';
					if(params.item_remarks !=undefined)
						remarks = params.item_remarks;
					if(params.dt_scheduled_dlvry2 !=undefined)
						dt_scheduled_dlvry2 = params.dt_scheduled_dlvry2;
					console.log(params.item_remarks);
					console.log(remarks);
					
					
				list += '<tr class="success">'+
				 '<td><input style="width: 120px; !important" type="text" value="' + params.nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				 '<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + i + '"  name="sr_no' + i + '" patelUnPrc="' + i + '" value="'+r.data[i].sr_no+'" class="form-control " data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +	
				'<td><input style="width: 120px; !important" type="text" value="' + params.ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control" readonly></td>' +	
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + params.in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + params.un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				
				//'<td><center><input type="text"  name="qty'+i+'"  value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				//'<td><center><input type="text" name="un_prc'+i+'"  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				//'<td><input type="text" name="others'+i+'"  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				//'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax1'+i+'"  value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax2'+i+'"  value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_va2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'"  value="'+params.tax_val1+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'"  value="'+params.tax_val2+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				//'<td><center><input type="text"  name="buyback'+i+'"  value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				//'
			
		
				'<input type="hidden" name="actionType'+i+'" value="Update">'+
				
				'</tr>';
				totPrice += parseFloat(params.gr_tot);
				}
			$('input[name="po_total"]').val(totPrice);
			totPrice += (parseFloat(params.frt_text) - parseFloat(params.discount));	
			var list2 ='<tr class="success">'+
			'<td colspan="13"><input type="text" name="oter_text" value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
			 '<td><center><input type="text" name="frt_text" value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		        '</tr>'+
		
		        '<tr class="success">'+
		      '<td colspan="13"><b style="float:right;">Discount</b></td>'+
		        '<td><center><input type="text" name="discount" value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	             '</tr>'+
	        	'<tr class="success">'+
		     	'<td colspan="13"><b style="float:right;">Grand Total</b></td>'+
		      	'<td><center><input type="text" name="tot" value="'+totPrice+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
	         '</tr>';
			$('input[name="itemCount"]').val(parseInt(i));
			
		list2 += 
			'<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			
			'<input type="hidden" name="reclassification" value="Yes">'+
		
		
		
		'<tr>'+

		 '<tr>'+
         '	<td>'+
         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary upd" onclick="ControlDirectVerbalPurchaseOrder(\'Update\',\'displaydirectVerbalOrder\',\'createdirectVerbalOrder\',\'\')">Update </button>&nbsp;&nbsp;'+
		 '	<button type="button" class="btn btn-primary upd" onclick="ControlDirectVerbalPurchaseOrder(\'Cancel\',\'displaydirectVerbalOrder\',\'createdirectVerbalOrder\',\'\')">cancel </button>'+
		 '	</td>'+
		'</tr>';
			$('#createAssetPR').show();
			$('#accessoryDetails2').html(list);
			$('#accessoryDetails1').html(list2);
			$('#CresteSaveUpdateButton').hide();
			$('#createdirectVerbalOrder').show();
			$('#'+HideDiv).hide();
			}
		
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
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
				debugger;
				/*DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					if(status)
					{*/
					//SubDropDownDataDisplay('0','dropsgrp','M_Subasset_Div',function (status){
					
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							for (var key in r.data[i])
					        {
								
									if(key == 'id_tax1')
										$('select[name=id_tax1'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									if(key=='id_tax2')
										$('select[name=id_tax2'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
								/*	if(key=='id_grp')
									{
										$('select[name=id_grp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									if(key=='id_sgrp')
									{
										$('select[name=id_sgrp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}	*/	
							}
						}
                          //});
					
				
					//}});	
				
				
				}});
				
			}});//tax ecnd
		
		
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
		
		
	},'json')
	.fail(function(jqXHR,status)
		    {
		
		    });

	
	
}
function PurchaseOrderForEdit(action,DisplayDiv,HideDiv,id_po,id_dept,id_cc,id_section)
{
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			$('#pobutton').hide();
			$('#createAssetPR').show();
			$('#directh1').hide();
	$.post('Modify_Purchase_Order',{action : 'Edit',id_po:id_po},function (r){
		if(r.data.length > 0)
			{
				for(var i=0;i<r.data.length;i++)
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
					if(r.data[0].remarks!=undefined || r.data[0].mode_pur!='')
					{
						$('#remarks').val(r.data[0].remarks);
						}
						
				}
				
				debugger;
				var totPrice=0;	var params='';
			var list ='<tr class="tableHeader info">'+
			'<tr class="tableHeader info">'+
			'<td ><strong><center>Product<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Description<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Stock</center></strong></td>'+
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Remarks<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>'+
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
			
		
		'</tr>';
			for(var i=0;i<r.data.length;i++)
				{
				 params=r.data[i];
					console.log(r.data);
					var remarks='';
					if(params.item_remarks !=undefined)
						remarks = params.item_remarks;
					
				list += '<tr class="success">'+
				 '<td><input style="width: 120px; !important" type="text" value="' + params.nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				 '<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + i + '"  name="sr_no' + i + '" patelUnPrc="' + i + '" value="'+r.data[i].sr_no+'" class="form-control " data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +	
				'<td><input style="width: 120px; !important" type="text" value="' + params.ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control" readonly></td>' +	
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + params.in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + params.un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
				
				'<input type="hidden" name="count" value="'+i+'">'+
				'<input type="hidden" name="id_po_asst" value="'+params.id_po_asst+'">'+
				
				//'<td><center><input type="text"  name="qty'+i+'"  value="'+params.qty+'" style="width:60px;;text-align: right;" class="common-validation" patelUnPrc="'+i+'" onChange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+
				//'<td><center><input type="text" name="un_prc'+i+'"  value="'+params.un_prc+'" patelUnPrc="'+i+'"  style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class="patelUnPrc common-validation" data-valid="num" ></center></td>'+
				//'<td><input type="text" name="others'+i+'"  value="'+params.others+'" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
				//'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value="'+remarks+'"  style="width: 100px;" maxlength="500"></center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax1'+i+'"  value="'+params.id_tax1+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center>'+
				'<select style="width: 80px;" name="id_tax2'+i+'"  value="'+params.id_tax2+'" patelTax="'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax2\',\'tax_va2\')">'+
				'</select>'+
				'</center></td>'+
				'<td><center><input type="text"  name="tax_val1'+i+'"  value="'+params.tax_val1+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
				'<td><center><input type="text"  name="tax_val2'+i+'"  value="'+params.tax_val2+'" patelUnPrc="'+i+'" onChange="calculateTot(event,\'val_tax2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
				//'<td><center><input type="text"  name="buyback'+i+'"  value="'+params.buyback+'" patelUnPrc="'+i+'"  onChange="calculateTot(event,\'buyback\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
				'<td><center><input type="text"  name="gr_tot'+i+'"  value="'+params.gr_tot+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
				
				//'
			
		
				'<input type="hidden" name="actionType'+i+'" value="Update">'+
				
				'</tr>';
				totPrice += parseFloat(params.gr_tot);
				}
			totPrice += (parseFloat(params.frt_text) - parseFloat(params.discount));	
			
			list += 
			'<input type="hidden" name="id_po" value="'+id_po+'">'+
			'<input type="hidden" name="no_ind" value="'+r.data[0].no_ind+'">'+
			'<input type="hidden" name="no_quot" value="'+r.data[0].no_quot+'">'+
			/*'<input type="hidden" name="do_direct" value="'+r.data[0].do_direct+'">'+*/
			
			'<input type="hidden" name="reclassification" value="No">'+
/*			'<tr class="success">'+
			'<td colspan="13"><input type="text" name="oter_text" value="'+params.oter_text+'" style="float:right;"  class="common-validation" ></td>'+
			 '<td><center><input type="text" name="frt_text" value="'+params.frt_text+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'frt_text\')" class="common-validation" ></center></td>'+
		        '</tr>'+*/
		
/*		        '<tr class="success">'+
		      '<td colspan="13"><b style="float:right;">Discount</b></td>'+
		        '<td><center><input type="text" name="discount" value="'+params.discount+'" style="width: 60px;text-align: right" data-valid="num" onChange="addInTotal(\'discount\')" class="common-validation" ></center></td>'+
	             '</tr>'+
	        	'<tr class="success">'+
		     	'<td colspan="13"><b style="float:right;">Grand Total</b></td>'+
		      	'<td><center><input type="text" name="tot" value="'+totPrice+'" style="width: 60px;text-align: right" class="common-validation" readonly></center></td>'+
	         '</tr>'+*/
		 '<tr>'+
		      
         '	</td></tr><tr><td colspan="14">'+
         '	<button type="button" style="margin-left: 400px;" class="btn btn-primary upd" onclick="ControlEditPurchaseOrder(\'Update\',\'displayModifyPurchaseOrder\',\'createModifyPurchaseOrder\',\'\')">Update </button>&nbsp;&nbsp;'+
		 '	<button type="button" class="btn btn-primary upd" onclick="ControlEditPurchaseOrder(\'Cancel\',\'displayModifyPurchaseOrder\',\'createModifyPurchaseOrder\',\'\')">Cancel </button>'+
		 '	</td>'+
		'</tr>';
			
			$('#accessoryDetails2').html(list);
			$('#'+DisplayDiv).show();
			$('#'+HideDiv).hide();
			$('#CresteSaveUpdateButton').hide();
			$('input[name="po_total"]').val(totPrice);
			
			}
		
		$( ".modifyPODatepicker2" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  var dt_po=$('.modifyPODatepicker').val();
		    	  var dt_po1=$('.modifyPODatepicker').val();
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
		
		DropDownDataDisplay("M_Tax","taxDataForDropDown11",function (status){
			if(status)
			{
					
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							for (var key in r.data[i])
					        {
								
									if(key == 'id_tax1')
										$('select[name=id_tax1'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									if(key=='id_tax2')
										$('select[name=id_tax2'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);	
							}
						}
                        
	
				
				
				
				
			}});
		
		
	},'json')
	.fail(function(jqXHR,status)
		    {
		alert('hi');
		    });

}

/*function dispalyLineItemDynamically(action,callback){
	
	
	var i=$('input[name="itemCount"]').val();
		
	var list = '<tr class="success">'+
	'<td><input type="text" id="dynItemData'+i+'" name="nm_prod'+i+'" patelUnPrc="'+i+'" style="padding: 0px;width: 150px;" class="common-validation resetAcc" onchange="changeEventHandlerprod(event);">'+
	'</td>'+
	'<td><input type="text" name="cd_prod'+i+'" id="dynCdProd'+i+'" style="padding: 0px;width:130px" class="common-validation resetAcc " patelUnPrc="'+i+'" onchange="changeEventHandlercode(event);"></td>'+
	'<input type="hidden" name="id_prod'+i+'" value="">'+
	'<td><center>'+
	'<select style="width: 100px;" name="id_uom'+i+'"  id="id_uom'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation uomforSales" readonly>'+
	'</select>'+
	'</center></td>'+
	'<td><center><input type="text" style="width: 150px;" name="item_remarks'+i+'" remarks="'+i+'" value=""  style="width: 100px;" maxlength="500"></center></td>'+
	'<td><center><input type="text" name="dt_scheduled_dlvry'+i+'"  value="" patelUnPrc="'+i+'"  class="common-validation modifyPODatepicker2"  style="width: 120px;"  ></center></td>'+
	
 		'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onchange="calculateTot(event,\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

	'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTot(event,\'un_prc\')" class=" common-validation" data-valid="num"   ></center></td>'+
	'<td><input type="text" name="others'+i+'" value="0" patelUnPrc="'+i+'" style="float:right;text-align: right;width:60px""  onChange="calculateTot(event,\'others\')" class="common-validation" ></td>'+
	'<td><center>'+
	'<select style="width: 80px;" name="id_tax1'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax1'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val1\')">'+
	'</select>'+
	'</center></td>'+
	'<td><center>'+
	'<select style="width: 80px;" name="id_tax2'+i+'" patelTax="'+i+'" patelUnPrc="'+i+'" id="id_tax2'+i+'" class="common-validation patelTax" onChange="TaxCalculation(event,\'id_tax1\',\'tax_val2\')">'+
	'</select>'+
	'</center></td>'+
	'<td><center><input type="text"  name="tax_val1'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'tax_val1\')" style="width:60px;text-align: right;" class="common-validation" readonly ></center></td>'+
	'<td><center><input type="text"  name="tax_val2'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'tax_val2\')" style="width:60px;text-align: right;" class="common-validation" readonly></center></td>'+
	'<td><center><input type="text"  name="discount'+i+'" patelUnPrc="'+i+'" value="0.0" onChange="calculateTot(event,\'discount\')" style="width:60px;text-align: right;" class="common-validation" ></center></td>'+
	'<td><center><input type="text"  name="gr_tot'+i+'" value="0.0" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
	//'
	'<input type="hidden" name="count" value="'+i+'">'+		

  '<input type="hidden" name="actionType'+i+'" value="Insert">'+
			
		'</tr>';
			
	$('input[name="itemCount"]').val(parseInt(i) +1);
$('.EditPurchaseOrder').append(list);
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
DropDownDataDisplay("M_Tax","id_tax1"+i,function (status){
	if(status)
	{
		DropDownDataDisplay("M_Tax","id_tax2"+i,function (status){
			if(status)
			{
				/*DropDownDataDisplay("M_UOM","id_uom"+i,function (status){
					if(status)
					{
					}});
			}});
		
	}});


$("#dynCdProd0").easyAutocomplete(options2);
$("#dynCdProd1").easyAutocomplete(options2);
$("#dynCdProd2").easyAutocomplete(options2);
$("#dynCdProd3").easyAutocomplete(options2);
$("#dynCdProd4").easyAutocomplete(options2);
$("#dynCdProd5").easyAutocomplete(options2);
$("#dynCdProd6").easyAutocomplete(options2);
$("#dynCdProd7").easyAutocomplete(options2);
$("#dynCdProd8").easyAutocomplete(options2);
$("#dynCdProd9").easyAutocomplete(options2);
$("#dynCdProd10").easyAutocomplete(options2);
$("#dynCdProd11").easyAutocomplete(options2);
$("#dynCdProd12").easyAutocomplete(options2);
$("#dynCdProd13").easyAutocomplete(options2);
$("#dynCdProd14").easyAutocomplete(options2);
$("#dynCdProd15").easyAutocomplete(options2);
$("#dynCdProd16").easyAutocomplete(options2);
$("#dynCdProd17").easyAutocomplete(options2);
$("#dynCdProd18").easyAutocomplete(options2);
$("#dynCdProd19").easyAutocomplete(options2);
$("#dynCdProd20").easyAutocomplete(options2);
$("#dynCdProd21").easyAutocomplete(options2);
$("#dynCdProd22").easyAutocomplete(options2);
$("#dynCdProd23").easyAutocomplete(options2);
$("#dynCdProd24").easyAutocomplete(options2);
$("#dynCdProd25").easyAutocomplete(options2);
$("#dynCdProd26").easyAutocomplete(options2);
$("#dynCdProd27").easyAutocomplete(options2);
$("#dynCdProd28").easyAutocomplete(options2);
$("#dynCdProd29").easyAutocomplete(options2);
$("#dynCdProd30").easyAutocomplete(options2);

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

}*/


function changeEventHandlerprodd1(event) {
	debugger;
	var domElement =$(event.target || event.srcElement);
	
    var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
   console.log(nm_prod);
var idloc = "1";
var idsloc = "2";
		if(idsloc==''){
			  if(idloc==''){
				  alert("'First Select Entity  '");
		  $('#locDataForLead').focus();
		     $('#locDataForLead').val('');
		     $('#locDataForLead').addClass('error');
			
			}
			else{
				
				  alert("'First Select city  '");
	         $('#slocDataForLead').focus();
		     $('#slocDataForLead').val('');
		     $('#slocDataForLead').addClass('error');
			}
						
						}
						else{
								
		                   if(nm_prod.length<5){
			                  
		                     // alert('this condition for not in stock remove problem')     
                                }
		                   else{
			 
		
		   $.post('M_Asset_Div',{action : 'GetProductdetails', id:nm_prod,idsloc:idsloc,idloc:idloc},function (r){
	 
			if(r.data.length==0)
				{
				console.log(r.data);
					//debugger
				alert("Not in Stock");
				//$('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val('');			 
		         $('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val('');
					$('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val('');
		          $('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val('0');
                 // $('select[name=id_sr'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
				
				}
				
				else{
					
					console.log(r.data);
					console.log(r.data[0].mfr_assetdiv);
			$('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].mfr_assetdiv);			 
		    $('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].ds_assetdiv);
		    $('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].un_prc_assetdiv);
		    $('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].tot_aval_qty);
				
				SubDropDownDataDisplayitemSrNo(r.data[0].id_assetdiv,idsloc,idloc,'serialnoDivForlead'+domElement.attr('patelUnPrc')+'','S_Add_To_Stock',function (status){
					//$('select[name=sr_no'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					});	
					
				}
	 },'json');
		}

     					
	
	

	
				
} 
 }