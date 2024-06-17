//Line items While Creating Invoice
function stockTransferlineitem(action, callback) {
	if (action == 'New') {
		
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' +
			'<tr class="tableHeader info">' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Description<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Quantity<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
	
			
			//'<td ><strong><center>Total<a href=#></a></center></strong></td>' +
			'</tr>';
		for (var i = 0; i < 10; i++) {
			list += '<tr>' +
				' <td style="display:none;">' +
	              '<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				'<td><center><input style="width: 150px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></center></td>' +
				'<td><center><select style="width: 150px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				//'<td><input style="width: 120px !important;" type="text"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control "></td>' +
				'<td><center><input style="width: 150px !important;" type="text" name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></center></td>' +
				
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" value="0" style="width:100px;text-align: right;" class="form-control "  onChange="CheckInvoice(event)" readonly></center></td>' +
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;text-align: right;"  value="1" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\');"; class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00" style="width: 60px;text-align: right;"  class="patelUnPrc form-control"  onChange="calculateTotfordecimal1(event,\'un_prc\')"data-valid="num" ></center></td>' +
				
				
				//'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
		}
		$('input[name="itemCount"]').val('10');
		$('input[name="grand_tot1"]').val('0.00');
		$('#leadDetails1').html(list);
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
						}
				});
			}
		});
	} 
	
	
	
	//On clicking Add Button for Line Item
	else {
		var i = $('input[name="itemCount"]').val();
		var list = '<tr>' +
			' <td style="display:none;">' +

	             '<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				'<td><input style="width: 150px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center><select style="width: 150px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				//'<td><input style="width: 120px !important;" type="text"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control "></td>' +
				'<td><input style="width: 150px !important;" type="text"  name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></td>' +
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;display:none;"  value="1" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" value="0" style="width:100px;text-align: right;" class="form-control " readonly></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00" style="width: 60px;text-align: right;"  class="patelUnPrc form-control" data-valid="num"></center></td>' +
				
				
				//'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
			     //'<input type="hidden" name="id_lead' + i + '" value="">' +
			       '<input type="hidden" name="count" value="' + i + '">' +
		      	'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) + 1);
		$('#leadDetails1').append(list);
		var dropDownId = "id_prod" + i + "";
		DropDownDataDisplay("M_Tax", "id_tax1" + i, function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "id_tax2" + i, function(status) {
					if (status) {
					}
				});
			}
		});
	}
	//Auto Completion of Item Name
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
	var currentDate = new Date();
	$(".datepoinvoice").datepicker({
		yearRange: '1985:2025',
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy",
		autoSize: true,
		altFormat: "dd/mm/yy",
	});
	console.log(currentDate);
}

//Getting Model Name from JSON
	
var options = {

	url: "InvoiceScanFile/Model.json",
	 
	getValue: "nm_assetdiv",
	
	template: {
		
		type: "custom",
			method: function(value, item) {
			return value;
			console.log(value);
		}
	},
	list: {
		maxNumberOfElements: 50,
		match: {
			enabled: true
		}
	},
};

function calculateTotfordecimal1(event, name){
debugger;
	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	/*var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;*/
	var intRegex = /^\d+$/;
    var floatRegex = /^\d+(\.\d{1,2})?$/;


	var str ='';
	console.log(floatRegex);
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
		
	}
	else
		{
			alert('Please enter valid number in the field.');
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

//Auto Select Group,Sub-Group,Asset Type
function changeEventHandlerprodd(event) {
	var domElement =$(event.target || event.srcElement);
	
    var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
   console.log(nm_prod);
var idloc = $('select[name="frm_id_loc"]').val();
var idsloc = $('select[name="frm_id_sloc"]').val();
		if(idsloc==''){
			  if(idloc==''){
				  alert("'Please select the From entity. '");
		  $('#locDataForLead').focus();
		     $('#locDataForLead').val('');
		     $('#locDataForLead').addClass('error');
			
			}
			else{
				
				  alert("'Please select the From city. '");
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
				
				debugger;
				SubDropDownDataDisplayitemSrNo(r.data[0].id_assetdiv,idsloc,idloc,'serialnoDivForlead'+domElement.attr('patelUnPrc')+'',r.data[0].type_grp,'S_Add_To_Stock',function (status){
					//$('select[name=sr_no'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					});	
					
				}
	 },'json');
		}

     					
	
	

	
				
} 
 }

function Transfer(action,displayContent,formName,servletName) {
	SessionCheck(function (ses){		
			if(ses)
				{
					debugger;
				if(action =='transfer'){
						$('#transfbutton').attr('disabled','disabled');
			var t=true;
			//t=ValidationForm('SubmitStockTransfer');
			if(t)
			{
				$('.upd').attr('disabled','disabled');	
				var x = $('#SubmitStockTransfer').serialize();
				var no_dc=$('input[name="no_dc"]').val();
					//var temp_invoice_no=$('input[name="temp_no_inv"]').val();
					var frm_id_loc=$('select[name="frm_id_sloc"]').val();
					var to_id_loc=$('select[name="to_id_sloc"]').val();
					
				var itemCount=$('input[name="itemCount"]').val();
				if(no_dc == '' || no_dc == undefined )	
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('#transfbutton').removeAttr('disabled');
						//$('.patelTax2').attr('disabled', 'disabled');
						$('input[name="no_dc"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					if(frm_id_loc == to_id_loc)	
						{
		
						t=0;
							bootbox.alert("Please selects different city from the field.");
						//$('.inv').removeAttr('disabled');
					 //$('.patelTax2').attr('disabled', 'disabled');
					  $('#transfbutton').removeAttr('disabled');
						$('select[name="to_id_sloc"]').addClass('error');
							//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
						}
						else
					{
						$('input[name="no_dc"]').removeClass('error');
					//	$('select[name="sr_no"]').removeClass('error');
					$('select[name="to_id_sloc"]').removeClass('error');
						//$('.patelTax2').attr('disabled', 'disabled');
					}
			for(var i=0;i<itemCount;i++)
			{
			debugger;

					var sr_no=$('select[name="sr_no'+i+'"]').val();
					var id_prod=$('input[name="id_prod'+i+'"]').val();
					var un_prc=$('input[name="un_prc'+i+'"]').val();
	
					if(id_prod != '' )	
				{
					if(sr_no == '' || sr_no == undefined )	
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('#transfbutton').removeAttr('disabled');
						//$('.patelTax2').attr('disabled', 'disabled');
						$('select[name="sr_no'+i+'"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					if(un_prc == '' || un_prc == undefined )	
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('#transfbutton').removeAttr('disabled');
						//$('.patelTax2').attr('disabled', 'disabled');
						$('select[name="un_prc'+i+'"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					
					
					else
					{
						//$('input[name="no_dc"]').removeClass('error');
						$('select[name="sr_no'+i+'"]').removeClass('error');
					//$('select[name="to_id_sloc"]').removeClass('error');
						//$('.patelTax2').attr('disabled', 'disabled');
					}
					}
				}
					if($('input[name="id_prod0"]').val() == '' )	
					{
					t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						//$('.inv').removeAttr('disabled');
						 $('#transfbutton').removeAttr('disabled');
						$('input[name="id_prod0"]').addClass('error');
						exit(0);
						}
					else{
						$('select[name="id_prod0"]').removeClass('error');
					}
				
			CheckValWhichAllReadyExit('StockTransferr' , 'DC number is already exists.' , 'no_dc' ,function (t1){
		
			
				$.post('StockTransferr',x,function (r){
					if(r.data == 1)
						{
						bootbox.alert("Transferred successfully.");
						window.location = $('.stocktransfer_event').attr('href');
					//	$( ".materialrequestplanningc" ).trigger( "click" );
						/*$('#createRequestPlanDiv').hide();
						$('.req').hide();
						$('#againtransfer').show();*/
						}
					else
					{
						bootbox.alert("Stock Quantity should be from Store Location,Location and Process.");
					}
					$('#transfbutton').removeAttr('disabled');
				},'json');
			});
					$('#transfbutton').removeAttr('disabled');
			}
		}
	}
	else{
		
	}
			
	});
	}