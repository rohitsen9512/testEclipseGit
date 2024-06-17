$(function() {
	$("#menu").menu();
});

//Main Function For Save,Update,Cancel lead
 /*  var idsloc='';
    var idloc='';*/
//for new cord lead
function ControlCrLead(action, displayContent, createDetails, formName, servletName,id,prodstatus) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
				$('#' + displayContent).show();
				DisplayCrLead(servletName, displayContent, createDetails, '', 'LeadForDisplay');
				window.location = $('.cordinatorlead_new_event').attr('href');
				
			
				
				}
			else if(action =='Edit')
	{
		EditCrLead(action , displayContent , createDetails , formName , servletName,id,prodstatus);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
	}
			 
			else if (action == 'TagLead') {
					$('button[name="save1"]').removeClass('hideButton');
					$('input[name="action"]').val('TagLead');
				
				$('input[name="state_aprvdelvr"]').val('NotApproved');
					
				
				t = false;
				//t = true;
			   t = ValidationForm(formName);
				
				if (t) {
				$('.led').attr('disabled', 'disabled');
				/*$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				*/
				 $('.form-control').removeAttr('disabled');
				
				var x = $('#' + formName).serialize();
				
				var itemCount=$('input[name="itemCount"]').val();
			 
			
			var t=1;
				
				if($('input[name="id_prod0"]').val() == '' )	
				{
					t=0;
						bootbox.alert("Make sure you have filled all the mandatory fields !!");
						$('.led').removeAttr('disabled');
					}
					if(t==1)
			{
				$.post(servletName, x, function(r) {
					if (r.data == 1) {
						$('#createLineitemLead').hide();
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						alert("Lead Tag Sucessfully !!!!!");
						window.location = $('.cordinatorlead_new_event').attr('href');
					}
					else if (r.data == 2) {
						alert("Lead Tag Sucessfully !!!!!");
						//bootbox.alert("This Lead Number Already Exist!!!");
						window.location = $('.cordinatorlead_new_event').attr('href');
						//$('#LeadNum').addClass('error');
					}
					else {
						bootbox.alert("Try again.");
					}
					$('.led').removeAttr('disabled');
				}, 'json');
			}
				
				}
				}
			}
			});
		}
		
		
//For Modify Invoice
function EditCrLead(action, displayContent, createDetails,formName, servletName,id,prod_status) {
	SessionCheck(function(ses) {
		if (ses) {
			$('input[name="leadCrCheck"]').val('1');
		    var state =  $('input[name="state"]').val();

		    console.log(state);
			   
		
			
			/*$('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');*/
	
			
	       
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemCrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
					
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=tag_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
								
							
						}
					}
				
			
			$.post(servletName, { action: 'Edit', id: id,state:state,prodstatus:prod_status }, function(r) {
				if (r.data) {
					
			  debugger; 
					for (var i = 0; i < r.data.length; i++) {
					
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							
						}
					}
					
						if(r.data[0].prod_status=='Refill'){
						debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}
					$('input[name="action"]').val("Assign");
	        	     $('input[name="id"]').val(id);
//
					$('input[name="dt_ld"]').val(r.data[0].dtld);
					$('input[name="dt_billing"]').val(r.data[0].dtbill);
					//$('input[name="po_date"]').val(r.data[0].dtpo);
					
					
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							
					//debugger;	
					  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {
									
					//debugger;	
								console.log(r.data[0].id_design);
					//SubDropDownDataDisplaybydesingEmp(r.data[0].id_design, 'EmpdataforField', 'M_Emp_User', function(status) {
								//if (status) {
								
								SubDropDownDataDisplayforemp(r.data[0].id_design, 'EmpdataforField', 'M_Emp_User',r.data[0].id_sloc, function(status) {
								if (status) {		
										
					          $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						      $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
						     $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design + ']').attr('selected', true);
							  $('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					          $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                    		
							   $('select[name=tag_to_emp] option[value=' + r.data[0].to_asign_cordi + ']').attr('selected', true);
							  $('input[name="cd_src"]').val(r.data[0].cd_src);				
															
									}
					             });
					         }
					     });
					  }
					});
					$('input[name="id_lead_m"]').val(r.data[0].id_lead_m);	
						$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					/*var uploadFile='';
						if(r.data[0].invoice_file != undefined)
							uploadFile = r.data[0].invoice_file;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("datachment").innerHTML =download;	*/
					debugger;
					dispalyModifyLineCrItemLead(r);
					
					
			/*		if(butType!='modi'){
				
			$('.form-control').attr('disabled', 'disabled');
			}*/
			 
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
			}, 'json');
		
	}
	});
}
const timer = ms => new Promise(res => setTimeout(res, ms))
function dispalyModifyLineCrItemLead(r) {
	if (r.data) {
		//if(r.data[0].typ_service=='Rental'){
				
			if(r.data[0].prod_status=='Refill'||r.data[0].prod_status=='Refill_and_Extend'){
				
								var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Produc status<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong ><center>Exchange Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td style="display:none;"><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			
			'<td ><strong class><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			//'<td ><strong class><center>Product Status<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Expire Date<a href=#></a></center></strong></td>' +
			/*'<td ><strong><center>Refundable Deposite<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Transportation Charges<a href=#></a></center></strong></td>' +*/
			'<td ><strong><center>Total<a href=#></a></center></strong></td>' +
			'</tr>';
			
		}else{
		//if(r.data[0].prod_status=='Product_on_Rental'||r.data[0].prod_status=='Product_on_Sale'){
			
			var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
				'<td ><strong><center>Type Cylinder<a href=#></a></center></strong></td>' +
			'<td><strong><center>Description<a href=#></a></center></strong></td>' +
			
			
			'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Amount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
				'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Expire Date<a href=#></a></center></strong></td>' +
			/*'<td ><strong><center>Refundable Deposite<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Transportation Charges<a href=#></a></center></strong></td>' +*/
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>' +
			'</tr>';
		}
		//}
		
		for (var i = 0; i < r.data.length; i++) {
			console.log(r.data);
			typcylinder='';
			if(r.data[i].typ_service=='Rental'){
				debugger;
					if(r.data[i].prod_status=='Refill'){
					
					
							list += '<tr>' +
				'<td><center>'+r.data[i].prod_status+'</center></td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				'<td><center>'+r.data[i].prev_sr_no+'</center></td>'+
				'<td><center><select style="width: 170px !important;" id="serialnoDivForlead' + i + '" name="sr_no_refill' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				
					
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly ></center></td>' +
			
				
				
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" + value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
				
			'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotRefillead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"  class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  onChange="TaxCalculationRefilllead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotRefillead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotRefillead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '"   value="' + r.data[i].buyback + '" onChange="calculateTotRefillead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				/*'<td><select style="width: 180px;" class="form-control" name = "product_status'+ i + '" id="product_status'+ i + '"  patelUnPrc="' + i + '" value="'+prodStatus+'"  data-valid="mand" >'+
						'<option value = "Extend days" >Extension of Rental days</option>'+
					'<option value = "Refill" >Refill Rental Cylinder </option>'+
					'<option value = "Refill_and_Extend" >Refill and Extend Cylinder Rental days</option>'+
				  
					'</select>'+
					'</td>'+*/
			
				'<td><center>'+r.data[i].typ_service+'</center></td>'+
				'<td><center>'+r.data[i].rental_day+'</center></td>'+
				'<td><center>'+r.data[i].dt_exprent+'</center></td>'+
	         
			      '<input type="hidden" name="id_prod' + i + '" value="' + r.data[i].nm_prod + '">' +
				   '<input type="hidden" name="sr_no' + i + '" value="' + r.data[i].sr_no + '">' +
					'<input type="hidden" name="dt_exp_rent' + i + '" value="' + r.data[i].dt_exprent + '">' +
					'<input type="hidden" name="rental_day' + i + '" value="' + r.data[i].rental_day + '">' +
					'<input type="hidden" name="typ_service' + i + '" value="' + r.data[i].typ_service + '">' +
					'<input type="hidden" name="un_prc' + i + '" value="0.00">' +
					'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
			     '<input type="hidden" name="prev_sr_no' + i + '" value="' + r.data[i].prev_sr_no + '">' +
				'<input type="hidden" name="prev_lead_no' + i + '" value="' + r.data[i].prev_lead_no + '">'+ 
			     '<input type="hidden" name="refill_id_lead' + i + '" value="' + r.data[i].refill_id_lead + '">'+ 
			
				'<input type="hidden" name="count" value="' + i + '">' +
				'<input type="hidden" name="un_prc' + i + '" value="0">' +
				 
				'</tr>';
			
				}if(r.data[i].prod_status=='Refill_and_Extend'){
					debugger;
						list += '<tr>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"   class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center>'+r.data[i].prod_status+'</center></td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				'<td><center>'+r.data[i].prev_sr_no+'</center></td>'+
				'<td><center><select style="width: 170px !important;" id="serialnoDivForlead' + i + '" name="sr_no_refill' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" style="width:140">'+
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				//'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" ></center></td>' +
			
				
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" + value="0.00 " style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
				
			'<td><input type="text" name="others' + i + '" value="0.00" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotRefillead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"  class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  onChange="TaxCalculationRefilllead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotRefillead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotRefillead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '"   value="0.00" onChange="calculateTotRefillead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			
				'<td><center>'+r.data[i].typ_service+'</center></td>'+
				
	            '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].rental_day + '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')" ></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control datepicker" ></center></td>' +
			      '<input type="hidden" name="id_prod' + i + '" value="' + r.data[i].nm_prod + '">' +
				   '<input type="hidden" name="sr_no' + i + '" value="' + r.data[i].sr_no + '">' +
					
					'<input type="hidden" name="typ_service' + i + '" value="' + r.data[i].typ_service + '">' +
					
					'<td><center><input type="text"  name="gr_tot' + i + '" value="0.00" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
					'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
			     '<input type="hidden" name="prev_sr_no' + i + '" value="' + r.data[i].prev_sr_no + '">' +
				'<input type="hidden" name="prev_lead_no' + i + '" value="' + r.data[i].prev_lead_no + '">'+ 
			     '<input type="hidden" name="refill_id_lead' + i + '" value="' + r.data[i].refill_id_lead + '">'+ 
				'<input type="hidden" name="count" value="' + i + '">' +
				'<input type="hidden" name="un_prc' + i + '" value="0">' +
			
				'</tr>';
				}
				if(r.data[i].prod_status=='Product_on_Rental'){	
					
					list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  readonly="readonly" class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				/*'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +*/
				
				
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" readonly="readonly" style="width:140">'+
					'<td><center><select style="width: 90px !important;display:none; " id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '"  value=""  patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		           '<option value="B_type_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46.7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="4.5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" + value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')"  class="form-control" readonly></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"  class="form-control patelTax" onChange="getTax2(event);" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '"    class="form-control patelTax2" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
			'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].buyback + '" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
			'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\')"  readonly >' +
		       
                  //'<option value="">Select</option>' +
			      '<option value="Sale" >Sale</option>' +
			     '<option value="Rental">Rental</option>'+'</select>' +
				'</center></td>' +
	            '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].rental_day + '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')" readonly></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control datepicker" readonly></center></td>' +
			
					'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				 '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
				 '<input type="hidden" name="prev_sr_no' + i + '" value="">' +
				'<input type="hidden" name="prev_lead_no' + i + '" value="">'+ 
			     '<input type="hidden" name="refill_id_lead' + i + '" value="">'+ 
			
				'<input type="hidden" name="count" value="' + i + '">' +
				
				'</tr>';
				
				}
				
				
			}
			else if(r.data[i].typ_service=='Sale'){
				
				
			
				if(r.data[i].prod_status=='Product_on_Sale'){
				
				
				list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  readonly="readonly" class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				/*'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control  groupdrop" data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				*/
					'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  readonly="readonly"   style="width:140">'+
					'<td><center><select style="width: 90px !important;display:none; " id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '"  value=""  patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		         '<option value="B_type_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46.7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="4.5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].mfr + '"  name="mfr' + i + '" patelUnPrc="' + i + '" readonly="readonly"  class="form-control"></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')"  class="form-control" readonly></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"   class="form-control patelTax" onChange="getTax2(event);" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '"   class="form-control patelTax2" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
		'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].buyback + '" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
		
			'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;" onchange="ShowRowColLead(event,\'rentalday\')"  readonly >' +
		       
                  //'<option value="">Select</option>' +
			      '<option value="Sale" >Sale</option>' +
			     '<option value="Rental">Rental</option>'+'</select>' +
				'</center></td>' +
	            '<td><center><input type="text" id="rentalday' + i + '"  name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' +'0'+ '" patelUnPrc="' + i + '"  class="form-control" readonly></center></td>' +
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' +'' + '" style="display:none;width: 100px";text-align: right;" class="form-control datepicker" readonly></center></td>' +

				
					'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				 '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
				 '<input type="hidden" name="prev_sr_no' + i + '" value="">' +
				'<input type="hidden" name="prev_lead_no' + i + '" value="">'+ 
			     '<input type="hidden" name="refill_id_lead' + i + '" value="">'+ 
				'<input type="hidden" name="count" value="' + i + '">' +
				
				'</tr>';
			}
			}else if(r.data[i].typ_service=='Refill'){
				
			if(r.data[i].prod_status=='Fill'){	
						
						console.log(r.data[0].typ_cylinder);
				if(r.data[i].typ_cylinder=='B_type_OXYGEN_CYLINDER _10_2_Ltr'){
						typcylinder='B-TYPE OXYGEN CYLINDER 10.2 LTR';
						
					}if(r.data[i].typ_cylinder=='D_TYPE_OXYGEN CYLINDER 46_7 LTR'){
						typcylinder='D-TYPE OXYGEN CYLINDER 46.7 LTR';
					}
					if(r.data[i].typ_cylinder=='B_type_Alu_OXYGEN_CYLINDER _10_2_Ltr'){
						typcylinder='B-TYPE ALUMINIUM OXYGEN CYLINDER 10.2 LTR';
					}
					if(r.data[i].typ_cylinder=='4_5_Alu_Cylinder'){
						typcylinder='4.5 ALUMINIUM CYLINDER';
					}
					
						list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				
				'<td><center><select style="width: 120px !important;display:none;" id="serialnoDivForlead' + i + '"  name="sr_no' + i + '" patelUnPrc="' + i + '" value="" class="form-control " data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				'<td><center><select style="width: 90px !important;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '"  value="' + r.data[i].typcylinder + '"  patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		          '<option value="B_type_OXYGEN_CYLINDER _10_2_Ltr">B-TYPE OXYGEN CYLINDER 10.2 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46_7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10_2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10.2 LTR</option>'+
                  '<option value="4_5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].mfr + '"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control"></td>' +
				'<td><input style="width: 120px; !important;display:none;" type="text" value=" " name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control" readonly></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;display:none;"  value="' + r.data[i].quantity + '" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;display:none;"  value="" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00" style="width: 60px;text-align: right;display:none;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="" ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"  class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '"  class="form-control patelTax2" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
		

				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
					'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].buyback + '" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\')"  readonly >' +
                 '<option value="Sale">Sale</option>' +
                 '<option value="Rental">Rental</option>'+		 
                  '<option value="Refill">Refill</option>'+		        

			   
				'</select>' +'</center></td>' +
                 '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;display:none;" value="" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')"></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="" style="width: 100px;text-align: right;display:none;" class="form-control datepicker" ></center></td>' +
			     '<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				
			    '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">' +
               '<input type="hidden" name="prev_sr_no' + i + '" value="">' +
				'<input type="hidden" name="prev_lead_no' + i + '" value="">'+ 
			     '<input type="hidden" name="refill_id_lead' + i + '" value="">'+ 
				'</tr>';
				}
		}
		}
		$('input[name="itemCount"]').val(i);
		$('input[name="grand_tot1"]').val('0.00');
		$('#leadCrDetails1').html(list);
	
			
//get dropdown in a loop 
		var x = 0;
if(r.data[0].product_status='Refill'){
	var loopArray = function(arr) {
    // call itself
    SubDropDownDataDisplayitemwithAllSrNo(arr[x].id_prod,arr[x].id_sloc,arr[x].id_loc,arr[x].sr_no,arr[x].type_grp,'serialnoDivForlead'+x+'','S_Add_To_Stock',function (status){
					              if (status) {
							 
         
    $('select[name=sr_no_refill'+x+'] option[value=' + arr[x].sr_no + ']').attr('selected', true);
  
		 
        // set x to next item
        x++;
        // any more items in array?
        if(x < arr.length) {
         	loopArray(arr);   
        }
    }}); 
}
	// start 'loop'
loopArray(r.data);	
}
else{
	var loopArray = function(arr) {
    // call itself
    SubDropDownDataDisplayitemwithAllSrNo(arr[x].id_prod,arr[x].id_sloc,arr[x].id_loc,arr[x].sr_no,arr[x].type_grp,'serialnoDivForlead'+x+'','S_Add_To_Stock',function (status){
					              if (status) {
							 
         
    $('select[name=sr_no'+x+'] option[value=' + arr[x].sr_no + ']').attr('selected', true);
  
		 
        // set x to next item
        x++;
        // any more items in array?
        if(x < arr.length) {
         	loopArray(arr);   
        }
    }}); 
}
	// start 'loop'
loopArray(r.data);	
}	
		
		
		
		
		
		
		
		//for d]sr_no dropdownn
		/*SubDropDownDataDisplayitemSrNo(r.data[i].id_prod,r.data[i].id_sloc,'serialnoDivForlead'+i+'','S_Add_To_Stock',function (status){
					              if (status) {
						 
											 
						 
			 		 
										}});*/
		//Auto select Dropdown of lineitems
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
						
									for (var i = 0; i < r.data.length; i++) {
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
										if (key == 'typ_service') {
												$('select[name=typ_service' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
										 if (key == 'sr_no') {
											
												$('select[name=sr_no_refill' + i + '] option[value='+r.data[i][key]+']').prop('selected', true);
											}
											 if (key == 'typ_cylinder') {
												debugger;
												//ssconsole.log($('select[name=typ_cylinder' + i + '] option[value='+r.data[i][key]+']').prop('selected', true));
												$('select[name=typ_cylinder' + i + '] option[value="'+r.data[i][key]+'"]').prop('selected', true);
											} 	
									    if (key == 'dt_exprent' && r.data[i][key] !='') {
							                       
                                             $('input[name="dt_exp_rent'+i+']').val(r.data[i][key].dt_exprent);	
								               //key1= r.data[i][key];
								
								               
					//
					
					                     //  ShowRowColdtexp(event ,'dt_exprent');
				
							                  }
										
											
										}
									}
								
					}
				});
			}
		});
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
}

	//Display List View of tag lead
function DisplayCrLead(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
				var searchWord = $('input[name="searchWord"]').val();
			var state =  $('input[name="state"]').val();
		 
			$.post(servletName, { action: 'Display', lead_no: lead_no, searchWord:searchWord,state:state }, function(r) {
				var list
				//if(state=='New'){
				let count=0;
					list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Lead Assign Date</center></strong></td>' +
					'<td><strong><center>TAT</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Created By</center></strong></td>' +
					'<td><strong><center>Assign To</center></strong></td>' +
					/*'<td><strong><center>Modify</center></strong></td>' +
					'<td><strong><center>Assign Lead</center></strong></td>' +*/
					'</tr></thead><tbody>';
				//}
	
				
			if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						console.log(r.data[0].prod_status);
						params = r.data[i];
						
						//list = list + '<tr>' +
						//if(state=='New' ){
							list = list + '<tr>' +
							
                            	'<td><center>' + ++count + '</center></td>' +
                           '<td><center><a class="alertlink" href="#" onclick="ControlCrLead(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+',\''+ params.prod_status +'\')">' + params.lead_no + '</a></center></td>' +

						 	
							'<td><center>' + params.dtld + '</center></td>' +
							'<td><center>' + params.my_time + '</center></td>' +
							
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>' +
							'<td><center>' + params.createdby + '</center></td>' +
							'<td><center>' + params.assignTocr + '</center></td>' +
							
					
						'</tr>';
                       // }
	           
	
					 
					}
				}
				else {
					list += '<tr><td colspan="6"><strong><center>No data found.</center></strong></td></tr>';
					$('#' + DisplayTableClass).html('</tbody>' + list);
				}
				$('#' + DisplayTableClass).html('</tbody>' + list);
				getButtonsForListView('leadCrForDisplaytd','Lead_List');
			}, 'json');
		}
	});
}



function ControlForAprovedelivery(action, displayContent, createDetails, formName, servletName,id) {
		debugger;
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			//$('input[name="leadCheck"]').val('0');
		debugger;
			
			if (action == 'Cancel') {
			
				
								
			if(displayContent=='displayAssigntomefordelverLd'){
					
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
			
				$('#' + displayContent).show();
				DisplayForDeliveryLead(servletName, displayContent, createDetails, '',formName, 'lddeliveryForDisplaytd');
				window.location = $('.deliveryLead_event').attr('href');

				}
			
			/*	if(displayContent=='displayforapprv'){
					
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
			
				$('#' + displayContent).show();
				DisplayForAprovedelivery(servletName, displayContent, createDetails, '',formName, 'leadapprvtd');
			    window.location = $('.approvefordelver_event').attr('href');
				}*/
				
				}
	
				
			 if(action =='EditForAprove')
	{
		
		
		debugger;
			/*	if(displayContent=='displayforapprv'){
					
			
				EditForAprovedelivery(action , displayContent , createDetails , formName , servletName,id);
		                $('#'+displayContent).hide();
		                $('#'+createDetails).show();
				}*/
		        
				if(displayContent=='displayAssigntomefordelverLd'){
					
			
				EditFordelivery(action , displayContent , createDetails , formName , servletName,id);
		                $('#'+displayContent).hide();
		                $('#'+createDetails).show();
				}
		        
		
		
		
	}
	
	
			if (action == 'Approvefordeliver') {
					
					$('input[name="action"]').val('Approvefordeliver');
				//
				t = false;
			    t = ValidationForm(formName);
				
				if (t) {
				 $('.form-control').removeAttr('disabled');
				
				var x = $('#' + formName).serialize();
			
			   
			
			var t=1;
				
			
			if(t==1)
			{
				//
				$.post(servletName, x, function(r) {
					setTimeout(function() {
					if (r.data == 1) {
						//$('#createLineitemLead').hide();
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						alert("Approve Succesfully !!!!!");
						window.location = $('.approvefordelver_event').attr('href');
					}
					
					else {
						bootbox.alert("Try again.");
					}
					$('.led').removeAttr('disabled');
				}, 100);
				}, 'json');
			}
				
				}
				}
	
	
	
	 
			}//
        });
}	

/*function EditForAprovedelivery(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			$('input[name="leadCrCheck"]').val('1');
			
			  var state_aprvdelvr =  'NotApproved';
			   
		
			
			
			
	       
			$('select[name="apprvby_frdelvr"]').attr('disabled','disabled');
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemCrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=apprvby_frdelvr] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
								
							
						}
					}
			
			
			$.post(servletName, { action: 'EditForAprove', id: id,state_aprvdelvr:state_aprvdelvr }, function(r) {
				if (r.data) {
					console.log(r.data[0].state_aprvdelvr);
					debugger
					
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							
						else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
					
							
						}
					}
					
					
						$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
						
			             $('input[name="dt_tag"]').val(r.data[0].tagdt);
                         $('input[name="state_aprvdelvr"]').val('Approved');

				
				
					 
	        	     $('input[name="id"]').val(id);
                      
					//$('input[name="dt_apprv_delvr"]').val(r.data[0].dt_apprvdelvr);
					console.log(r.data[0].tagdt);
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
						
					
					  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {
								
					SubDropDownDataDisplayforemp( r.data[0].id_design_tagemp, 'EmpdataforField', 'M_Emp_User',r.data[0].id_sloc,function(status) {
								if (status) {		
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        	
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                    		
							   $('select[name=tag_to_emp] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
				               $('select[name=tag_by] option[value=' + r.data[0].tag_by + ']').attr('selected', true);
												
															
										}
					             });
					         }
					     });
					  }
					});
					$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					dispalyModifyLineCrItemLead(r);
					
					
					
					
			
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
			}, 'json');
			//
		}
	});

}*/



function EditFordelivery(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			$('input[name="leadCrCheck"]').val('1');
			$('select[name="apprvby_frdelvr"]').attr('disabled' ,'disabled');
			
			  var state_aprvdelvr =  'Approved';
			   
		
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemCrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			
			$.post(servletName, { action: 'EditForAprove', id: id,state_aprvdelvr:state_aprvdelvr }, function(r) {
				if (r.data) {
					
					
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							
						else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
					
							
						}
					}
					
					
						
					
			             //$('input[name="dt_tag"]').val(r.data[0].tagdt);
                        

				
				
						
						$('input[name="apprvby_frdelvr"]').val(r.data[0].approvBy);
					    $('input[name="dt_apprv_delvr"]').val(r.data[0].approvdt);
					
					 
	        	     $('input[name="id"]').val(id);
                      
					//$('input[name="dt_apprv_delvr"]').val(r.data[0].dt_apprvdelvr);
					console.log(r.data[0].tagdt);
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
						
					
					  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {
								
					SubDropDownDataDisplayforemp( r.data[0].id_design_tagemp,'EmpdataforField', 'M_Emp_User',r.data[0].id_sloc, function(status) {
								if (status) {		
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        	
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                    		
							   $('select[name=tag_to_emp] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
				               $('select[name=tag_by] option[value=' + r.data[0].tag_by + ']').attr('selected', true);
												
															
										}
					             });
					         }
					     });
					  }
					});
					$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					dispalyModifyLineCrItemLead(r);
					
					
					
					
			
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
			//
		}
	});

}






/*function DisplayForAprovedelivery(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			
	debugger;
			
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
			var state_aprvdelvr = 'NotApproved';
			//$('input[name="state_aprvdelvr"]').val();
			
		    
        
	                    
                  
			$.post(servletName, { action: 'DisplayForAprove', lead_no: lead_no, dt_frm: dt_frm, dt_to: dt_to,state_aprvdelvr:state_aprvdelvr }, function(r) {
				var list
				
				list = '<thead><tr class="new">' +
					
					'<td><strong><center>Lead Number</center></strong></td>' +
					
					'<td><strong><center>Lead Assign Date</center></strong></td>' +
						
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					
					 '</tr></thead><tbody>';
		          
				
				  if(state_aprvdelvr=='NotApproved'){
			         if (r.data.length > 0) {
				         debugger;
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						
						list = list + '<tr>' +
						 '<td><center><a class="alertlink" href="#" onclick="ControlForAprovedelivery(\'EditForAprove\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +
					      '<td><center>' + params.tagdt + '</center></td>' +
	                       '<td><center>' + params.nm_loc + '</center></td>'+
						   '<td><center>' + params.nm_subl + '</center></td>'+
						   '<td><center>' + params.nm_pstn + '</center></td>' +
							'<td><center>' + params.tagBy + '</center></td>' +
							'<td><center>' + params.tagTo + '</center></td>' +
							'</tr>';
						
                     }
					 		
					}
				
		
				else {
					list += '<tr><td colspan="7"><strong><center>No data found.</center></strong></td></tr>';
					$('#leadapprvtd').html('</tbody>' + list);
				}
				
				$('#leadapprvtd').html('</tbody>' + list);
				}
			}, 'json');
			
	
		}
	});
}*/

function DisplayForDeliveryLead(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			
	debugger;
			
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
			var state_aprvdelvr ='Approved';
			
		    
        
	                    
                  
			$.post(servletName, { action: 'DisplayForAprove', lead_no: lead_no, dt_frm: dt_frm, dt_to: dt_to,state_aprvdelvr:state_aprvdelvr }, function(r) {
				var list
				
				list = '<thead><tr class="new">' +
					
					'<td><strong><center>Lead Number</center></strong></td>' +
					
					'<td><strong><center>Lead Assign Date</center></strong></td>' +
						
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					'<td><strong><center>Update</center></strong></td>' +
					
					 '</tr></thead><tbody>';
		          
				
				  if(state_aprvdelvr=='Approved'){
			         if (r.data.length > 0) {
				       
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						
						list = list + '<tr>' +
						 '<td><center><a class="alertlink" href="#" onclick="ControlForAprovedelivery(\'EditForAprove\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +
					      '<td><center>' + params.tagdt + '</center></td>' +
	                       '<td><center>' + params.nm_loc + '</center></td>'+
						   '<td><center>' + params.nm_subl + '</center></td>'+
						   '<td><center>' + params.nm_pstn + '</center></td>' +
							'<td><center>' + params.tagBy + '</center></td>' +
							'<td><center>' + params.tagTo + '</center></td>' +
							//'<td><center><a class="alertlink" href="#" onclick="ControlNewleaddeliver(\'Create\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+',\'Editdelivery\',\'submitdelivery\',,,)">' + 'Update' + '</a></center></td>' +
							'</tr>'
							
  
						
                     }
					 		
					}
				
		
				else {
					list += '<tr><td colspan="7"><strong><center>No data found.</center></strong></td></tr>';
					$('#'+ DisplayTableClass).html('</tbody>' + list);
				}
				
				$('#'+ DisplayTableClass).html('</tbody>' + list);
				}
			}, 'json');
			
	
		}
	});
}
		
//for inp cord lead
function ControlCrInprogressLead(action, displayContent, createDetails, formName, servletName,id,prodstatus) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				
								
			if(displayContent=='displayCrInprogressLead'){
					
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
			
				$('#' + displayContent).show();
				DisplayCrInprogressLead(servletName, displayContent, createDetails, '', 'LeadForDisplay');
				window.location = $('.cordinatorlead_inprogress_event').attr('href');

				}
	
				
				}
	
				
			else if(action =='Edit')
	{
		EditCrInprogressLead(action , displayContent , createDetails , formName , servletName,id,prodstatus);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
		
	}
		
 else if(action =='ClosedFillLead') {
					
					$('input[name="action"]').val('ClosedFillLead');
				
				
				t = false;
				//t = true;
			    t = ValidationForm(formName);
				
				if (t) {
				$('.led').attr('disabled', 'disabled');
				
				 $('.form-control').removeAttr('disabled');
				
				var x = $('#' + formName).serialize();
				
			
			 
			
			
			
			if(t)
			{
				$.post(servletName, x, function(r) {
					if (r.data == 1) {
						$('#createLineitemLead').hide();
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						alert("Lead Closed Sucessfully");
						window.location = $('.cordinatorlead_inprogress_event').attr('href');
					}
					
					else {
						bootbox.alert("Try again.");
					}
					$('.inv').removeAttr('disabled');
				}, 'json');
			}
				
				}
				}
		
			}
        });
}

function ControlCrCloseLead(action, displayContent, createDetails, formName, servletName,id) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
			
				$('#' + displayContent).show();
				DisplayCrClosedLead(servletName, displayContent, createDetails, '', 'LeadForDisplay');
				window.location = $('.cordinatorlead_closed_event').attr('href');
				
			
				
				}
	
				
			else if(action =='Edit')
	{
		EditCrClosedLead(action , displayContent , createDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
		
	}
		
			}
        });
}


function ControlfillCloseLead(action, displayContent, createDetails, formName, servletName,id) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
			
				$('#' + displayContent).show();
				DisplayFillClosedLead(servletName, displayContent, createDetails, '', 'leadFillClosedForDisplaytd');
				window.location = $('.closedFilllead_event').attr('href');
				
			
				
				}
	
				
			else if(action =='EditFillClosedLead')
	{
		EditFillClosedLead(action , displayContent , createDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
		
	}
		
			}
        });
}

function EditCrInprogressLead(action, displayContent, createDetails,formName, servletName,id,prodstatus) {
	SessionCheck(function(ses) {
		if (ses) {
			$('input[name="leadCrCheck"]').val('1');
			  var state =  $('input[name="state"]').val();
			   
		
			
			/*$('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');*/
	
			
	       
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemCrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			debugger;
			$.post(servletName, { action: 'Edit', id: id,state:state,prodstatus:prodstatus }, function(r) {
				if (r.data) {
					console.log('hiii');
					console.log(r.data);
				
					for (var i = 0; i < r.data.length; i++) {
					
						
						for (var key in r.data[i]) {
							
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							
						}
					}
					
				if(r.data[0].prod_status=='Fill'&& r.data[0].ctr_inv_status=='1'){
				$('button[name="Closed"]').removeClass('hideButton');
			
			
			}else{
					$('button[name="Closed"]').addClass('hideButton');
			}
					if(r.data[0].prod_status=='Refill'){
						debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}
						
					//$('input[name="action"]').val("Assign");
	        	     $('input[name="id"]').val(id);
	        	     
	        	    
//
					$('input[name="dt_tag"]').val(r.data[0].tagdt);
					console.log(r.data[0].tagdt);
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
						
					/*
					  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {
								*/
					SubDropDownDataDisplaybydesingEmp(r.data[0].id_design_tagemp,'EmpdataforField', 'M_Emp_User', function(status) {
								if (status) {	
									
									debugger;
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						           $('select[name=tag_by] option[value=' + r.data[0].tgbY + ']').attr('selected', true); 	
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                    		
							   $('select[name=tag_to_emp] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
												
															
										}
					             });
					        /* }
					     });*/
					  }
					});
					
$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					/*var uploadFile='';
						if(r.data[0].invoice_file != undefined)
							uploadFile = r.data[0].invoice_file;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("datachment").innerHTML =download;	*/
					dispalyModifyLineCrItemLead(r);
					
					
					
					
			/*		if(butType!='modi'){
				
			$('.form-control').attr('disabled', 'disabled');
			}*/
			 
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
			
		/*	setTimeout(function() { 
				       dispalyModifyLineReturnItemLeadHasReturn(id,'Closed');
				
						}, 1000);*/
		
	}
	});
}

function EditCrClosedLead(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			$('input[name="leadCrCheck"]').val('1');
			  var state =  $('input[name="state"]').val();
			   
           	$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemCrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			
			$.post(servletName, { action: 'Edit', id: id,state:state }, function(r) {
				if (r.data) {
					
					 
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							
						}
					}
					debugger;
					
					if(r.data[0].prod_status=='Refill'){
						debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}
	        	     $('input[name="id"]').val(id);
//
					$('input[name="dt_ld"]').val(r.data[0].installdt);
					
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							
				
					/*  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {*/
					SubDropDownDataDisplaybydesingEmp(r.data[0].id_design_tagemp,'EmpdataforField', 'M_Emp_User', function(status) {
								if (status) {	
										 	   
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                 
              		
							   $('select[name=tag_to_emp] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
												
															
										}
					             });
					         /*}
					     });*/
					  }
					});
					
$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					dispalyModifyLineCrItemLead(r);
					
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
		
	}
	});
}

function EditFillClosedLead(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			$('input[name="leadCrCheck"]').val('1');
			  var state =  $('input[name="state"]').val();
			   
           	$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemCrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			
			$.post(servletName, { action: 'EditFillClosedLead', id: id,state:state }, function(r) {
				if (r.data) {
					
					 
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							
						}
					}
					debugger;
					
					/*if(r.data[0].prod_status=='Refill'){
						debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}*/
	        	     $('input[name="id"]').val(id);
//
					$('input[name="dt_ld"]').val(r.data[0].installdt);
					
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							
				
					/*  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {*/
					SubDropDownDataDisplaybydesingEmp(r.data[0].id_design_tagemp,'EmpdataforField', 'M_Emp_User', function(status) {
								if (status) {	
										 	   
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                 
              		
							   $('select[name=tag_to_emp] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
												
															
										}
					             });
					         /*}
					     });*/
					  }
					});
					
$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					dispalyModifyLineCrItemLead(r);
					
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
		
	}
	});
}

//display cordinator Inprogress lead
function DisplayCrInprogressLead(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			
			/*var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();*/
			var searchWord = $('input[name="searchWord"]').val();
			var state =  $('input[name="state"]').val();
		    
		    let count=0;
         
	                    
                  
			$.post(servletName, { action: 'Display', lead_no: lead_no, searchWord:searchWord,state:state }, function(r) {
				var list
				 // if(state=='Inprogress'){
				list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					
					'<td><strong><center>Tag Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>TAT</center></strong></td>' +
					'<td><strong><center>Created By</center></strong></td>' +
				/*	'<td><strong><center>Assigned By</center></strong></td>' +*/
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					 '</tr></thead><tbody>';
		          
				
				  if(state=='Inprogress'){
			         if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						
					
					       list = list + '<tr>' +
						 
                           '<td><center>' + ++count + '</center></td>' +
                          '<td><center><a class="alertlink" href="#" onclick="ControlCrInprogressLead(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+',\''+ params.prod_status +'\')">' + params.lead_no + '</a></center></td>' +

							'<td><center>' + params.tagdt + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>' +
						    '<td><center>' + params.my_time + '</center></td>' +
							'<td><center>' + params.createdBy + '</center></td>' +
							/*'<td><center>' + params.assignedBy + '</center></td>' +*/
							'<td><center>' + params.tagBy + '</center></td>' +
							'<td><center>' + params.tagTo + '</center></td>' +
							
							
						'</tr>';
            
	                 }
					 
				debugger	}
				}
				
				else {
					debugger;
					list += '<tr><td colspan="5"><strong><center>No data found.</center></strong></td></tr>';
					$('#' + DisplayTableClass).html('</tbody>' + list);
				
				}
				
				$('#' + DisplayTableClass).html('</tbody>' + list);
				getButtonsForListView('leadCrInprogressInForDisplaytd','Lead_List');
			}, 'json');
			
	
		}
	});
}


//display fro cordinator closed lead
function DisplayCrClosedLead(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			/*var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();*/
			var searchWord = $('input[name="searchWord"]').val();
			var id_sloc= $('select[name="id_sloc"]').val();
			var id_loc= $('select[name="id_loc"]').val();
			var state =  $('input[name="state"]').val();
		   let count=0;
			$.post(servletName, { action: 'Display', id_sloc:id_sloc ,id_loc:id_loc ,searchWord: searchWord,state:state }, function(r) {
				var list
				debugger;
					list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
				
					'<td><strong><center>Install Date</center></strong></td>' +
					'<td><strong><center>Closed Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					/*'<td><strong><center>Assign By</center></strong></td>' +
					'<td><strong><center>Assigned To</center></strong></td>' +*/
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					//'<td><strong><center>Closed By</center></strong></td>' +
					 '</tr></thead><tbody>';
						
					
				  if(state=='Closed'){
			if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						
						params = r.data[i];
					if(params.closeddt == 'NULL' || params.closeddt == undefined) closeddt = 'NA'; 
					else closeddt = params.nm_contact;
						//list = list + '<tr>' +
						
					       list = list + '<tr>' +
						 	
                             '<td><center>' + ++count + '</center></td>' +
                             '<td><center><a class="alertlink" href="#" onclick="ControlCrCloseLead(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +

							'<td><center>' + params.installdt + '</center></td>' +
							'<td><center>' + params.closeddt + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>' +
							/*'<td><center>' + params.approveBy + '</center></td>' +
							'<td><center>' + params.assignTo + '</center></td>' +*/
							'<td><center>' + params.TagBy + '</center></td>' +
							'<td><center>' + params.TagTo + '</center></td>' +
							//'<td><center>' + params.closedBy + '</center></td>' +
						'</tr>';
	
					 
					}
				}
				//}
				else {
					list += '<tr><td colspan="9"><strong><center>No data found.</center></strong></td></tr>';
					$('#' + DisplayTableClass).html('</tbody>' + list);
				}
				}
				$('#' + DisplayTableClass).html('</tbody>' + list);
				getButtonsForListView('leadCrClosedForDisplaytd','Lead_List');
			}, 'json');
		}
	});
}

function DisplayFillClosedLead(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			
		/*	var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();*/
				var searchWord = $('input[name="searchWord"]').val();
			var state =  $('input[name="state"]').val();
		   let count=0;
			$.post(servletName, { action: 'DisplayFillClosedLead', lead_no: lead_no, searchWord:searchWord,state:state }, function(r) {
				var list
				
					list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
				
					'<td><strong><center>Lead Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					/*'<td><strong><center>Assign By</center></strong></td>' +
					'<td><strong><center>Assigned To</center></strong></td>' +*/
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					//'<td><strong><center>Closed By</center></strong></td>' +
					 '</tr></thead><tbody>';
						
				  if(state=='Closed'){
			if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						
						//list = list + '<tr>' +
						
					       list = list + '<tr>' +
						 	
                             '<td><center>' + ++count + '</center></td>' +
                             '<td><center><a class="alertlink" href="#" onclick="ControlfillCloseLead(\'EditFillClosedLead\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +

							'<td><center>' + params.installdt + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>' +
							
							'<td><center>' + params.TagBy + '</center></td>' +
							'<td><center>' + params.TagTo + '</center></td>' +
							
						'</tr>';
	
					 
					}
				}
				//}
				else {
					list += '<tr><td colspan="9"><strong><center>No data found.</center></strong></td></tr>';
					$('#' + DisplayTableClass).html('</tbody>' + list);
				}
				}
				$('#' + DisplayTableClass).html('</tbody>' + list);
			   getButtonsForListView('leadFillClosedForDisplaytd','Lead_List');
			}, 'json');
		}
	});
}



//mylead new update 
function ControlNewleaddeliver(action, displayContent, createDetails, formName, servletName,id,createDetails1,formName1,trnsprt,tot,rfd,id_lead_m,tag_by) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			var dat=''; 
		
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
		
			if (action == 'Cancel') {
				if(displayContent=='displayAssigntomefordelverLd'){
					
				$('#' + createDetails).hide();
				$('#' + createDetails1).hide();
				$('#' + displayContent).show();
				DisplayAssignTomeNew(servletName, displayContent, createDetails, '',formName, 'lddeliveryForDisplaytd');
			   window.location = $('.deliveryLead_event').attr('href');
				}
			
				if(displayContent=='displayAssigntomeINPLd'){
					
				$('#' + createDetails).hide();
				$('#' + createDetails1).hide();
				$('#' + displayContent).show();
				DisplayAssignTomeinprogress(servletName, displayContent, createDetails, '',formName, 'ldassignINPForDisplaytd');
			    window.location = $('.assigntomelead_inprogress_event').attr('href');
				}
				
			
			
				
				}
				
				
	 if(action =='Edit1')
	{
		EditDeliverDetails(action , displayContent , createDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+createDetails).hide();
		$('#'+createDetails1).show();
		
	}
				//
		if (action == 'Create') {
			//$('#displayAccessory').hide();
				
				$('#' + createDetails).hide();
				$('#' + displayContent).hide();
				$('#' + createDetails1).show();
			   	$('input[name="id_lead_m"]').val(id);
	
		$('#empdatafordelvrbyLd').attr('disabled','disabled');
		$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=deliver_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
						
				
						}
						
						
					}
					var list='';
					$.post(servletName, { action: 'EditMylead', id:id,tagto_me_state:'New' }, function(r) {
				if (r.data) {
					 debugger;
						
						for (var i = 0; i < r.data.length; i++) {
						list += '<tr>' +
				
			
			      '<input type="hidden" name="id_prod' + i + '" value="' + r.data[i].nm_prod + '">' +
				   '<input type="hidden" name="prev_sr_no' + i + '" value="' + r.data[i].prev_sr_no + '">' +
					
				
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +

				'<input type="hidden" name="count" value="' + i + '">' +
			
			 '</tr>';
				
		
		$('#leaddelivrDetail').html(list);
			$('input[name="itemCount"]').val(i);
	             }
}
}, 'json');
	    	}, 'json');
					 
	    	debugger

	console.log( tot);
	console.log( rfd);
	
                       invamt= parseFloat(tot)-parseFloat(rfd);
                   
							 $('input[name="amount"]').val(invamt);	
						console.log( $('input[name="amount"]').val(invamt));
							console.log( $('input[name="amount"]').val());
						 $('input[name="amt"]').val(invamt);	
                      $('input[name="trns_amt"]').val(trnsprt);
                      $('input[name="ref_amt"]').val(rfd);


	    	var id_lead_m=$('input[name="id_lead_m"]').val();
	    	   $('input[name="deliver_time"]').val(new Date().toLocaleTimeString([], { hour: '2-digit', minute: "2-digit" }));	
                            $('input[name="delivery_time"]').val(new Date().toLocaleTimeString([], {hour12: false, hour: '2-digit', minute: "2-digit" }));	             
   var deliver_time =    $('input[name="delivery_time"]').val();                   
 var ld_time = $('input[name="ld_time"]').val(ld_time);
                 if(deliver_time>ld_time)
          {
	
	 $('input[name=reach_lnformation]').val('Delay in TAT');
	      $('input[name="reson_tat"]').attr('data-valid','mand');     
               
          }else{
	 $('input[name=reach_lnformation]').val('On Time');
      $('input[name="reson_tat"]').removeAttr('data-valid','mand');
}

// setTimeout(function() {
	   /* $.post('O_AssignedLlead',{action :'checkdeliverytime' , deliver_time : deliver_time,id:id},function (r){
	    		
	    		if(r.data == 0)
	    			{
	    			
	    			  $('select[name=reach_lnformation] option[value=' + Delay_in_TAT + ']').attr('selected', true);
	    			
	    			}
	    		else{
	    			 $('select[name=reach_lnformation] option[value=' + On_Time + ']').attr('selected', true);
	    		}
	    		
	    },'json');
}, 100);*/
	      
		}
			if (action == 'Update') {
					
					var reson_tat=$('input[name="reson_tat"]').val();
					var pay_mode=$('input[name="pay_mode"]').val();
				if (reson_tat != 'On_Time') {
					
					$('#reson_tat').removeAttr('data-valid')
					
					}
				
				if (pay_mode != 'Cheque') {
					
					$('#cheque_no').removeAttr('data-valid')
					
					}
					$('input[name="action"]').val('Update');
					
				//
				t = false;
			    t = ValidationForm(formName1);
				
				if (t) {
					$('.led').attr('disabled', 'disabled');
				 $('.form-control').removeAttr('disabled');
				
				var x = $('#' + formName1).serialize();
			
			   console.log(x);
			
			var t=1;
				
			
			if(t==1)
			{
				//
				$.post(servletName, x, function(r) {
					if (r.data == 1) {
						//$('#createLineitemLead').hide();
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						alert("Update Succesfully !!!!!");
						window.location = $('.deliveryLead_event').attr('href');
					}
					else if (r.data == 2) {
						bootbox.alert("This Lead Number Already Exist!!!");
						$('#LeadNum').addClass('error');
					}
					else {
						bootbox.alert("Try again.");
					}
					$('.led').removeAttr('disabled');
				}, 'json');
			}
				
				}
				}
			}
			});
		}
	
	
	
	//mylead new update 
function UpdateDeliverlivetrackingDetails(action, displayContent, createDetails, formName, servletName,id,createDetails1,formName1,trnsprt,tot,rfd,id_lead_m,tag_by) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
	
		$.post('O_AssignedLlead', { action: 'UpdateTrackinglocation',id:id,delivery_man_id,latitude:latitude,longitude:longitude,time_stamp:time_stamp }, function(r) {
					
                  if (r.data == 1) {
						
					
						//alert("Update Succesfully !!!!!");
						
					}
					else if (r.data == 2) {
						/*bootbox.alert("This Lead Number Already Exist!!!");
						$('#LeadNum').addClass('error');*/
					}
					else {
						//bootbox.alert("Try again.");
					}
					//$('.led').removeAttr('disabled');
				}, 'json');
			}
				
				
			});
		}	
		
	//for the mobile customer login order history	
function DipplayCutomerOrderHistory(action, pstn_ct) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
	
		$.post('MobileApp_lead', { action: 'DipplayCutomerOrderHistory',pstn_ct:pstn_ct}, function(r) {
					
                  if (r.data == 1) {
						
					
						//alert("Update Succesfully !!!!!");
						
					}
					else if (r.data == 2) {
						/*bootbox.alert("This Lead Number Already Exist!!!");
						$('#LeadNum').addClass('error');*/
					}
					else {
						//bootbox.alert("Try again.");
					}
					//$('.led').removeAttr('disabled');
				}, 'json');
			}
				
				
			});
		}
//for the mobile customer login order tracking listview			
function Diplaytrcakinglistview(action,pstn_ct) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
	
		$.post('MobileApp_lead', { action: 'Diplaytrcakinglistview',pstn_ct:pstn_ct }, function(r) {
					
                  if (r.data == 1) {
						
					
						//alert("Update Succesfully !!!!!");
						
					}
					else if (r.data == 2) {
						/*bootbox.alert("This Lead Number Already Exist!!!");
						$('#LeadNum').addClass('error');*/
					}
					else {
						//bootbox.alert("Try again.");
					}
					//$('.led').removeAttr('disabled');
				}, 'json');
			}
				
				
			});
		}			
		
function ControlReturntoStore(action, displayContent, createDetails, formName, servletName,id,createDetails1,formName1) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			var dat=''; 
			//alert(createDetails1);
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
		
			if (action == 'Cancel') {
				$('#' + createDetails).hide();
				$('#' + createDetails1).hide();
				$('#' + displayContent).show();
				DisplayAssignTomeinprogress(servletName, displayContent, createDetails, '', 'LeadForDisplay');
			   window.location = $('.assigntomelead_inprogress_event').attr('href');
				}
				
			/*	if (action == 'Create') {
					
			    $('#' + createDetails).hide();
				$('#' + displayContent).hide();
				$('#' + createDetails1).show();
			   	$('input[name="id_lead_m"]').val(id);
		        }*/

	            if (action == 'EditReturn') {
					
					EditReturnToStore(action , displayContent , createDetails , createDetails1,formName , servletName,id);
			        
                    $('input[name="id_lead_m"]').val(id);
		        }
                if (action == 'ReturnStore') {
					
					$('input[name="action"]').val('ReturnStore');
					 $('.form-control').removeAttr('disabled');
					
				t = false;
			    t = ValidationForm(formName1);
				
				if (t) {
					
				
				  var x = $('#' + formName1).serialize();
			      var t=1;
			    if(t==1){
				
				$.post(servletName, x, function(r) {
					if (r.data == 1) {
						
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						
						alert("Return To Store Succesfully !!!!!");
						window.location = $('.assigntomelead_inprogress_event').attr('href');
					}
			/*	else if (r.data == 2) {
						bootbox.alert("This Lead Number Already Exist!!!");
						$('#LeadNum').addClass('error');
					}*/
					else {
						bootbox.alert("Try again.");
					}
					$('.led').removeAttr('disabled');
				}, 'json');
			}
				
				}
				}
			}
			});
		}


function EditReturnToStore(action, displayContent, createDetails,createDetails1,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			
			
	            $('#' + createDetails).hide();
				$('#' + displayContent).hide();
				$('#' + createDetails1).show();
			  
			
			var t = 0;
		
			
			$.post(servletName, { action: 'EditReturn', id: id}, function(r) {
				if (r.data) {
					
				 
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							
						
								$('input[name=' + key + ']').val(r.data[i][key]);
							
							
						}
					}
					
					
	        	    // $('input[name="id"]').val(id);
                     }
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
		
	}
	});
}	


		
function EditDeliverDetails(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			
		  $('#empdatafordelvrbyLd').attr('disabled','disabled');
	        $('#' + displayContent).hide();
			$('#' + createDetails).hide();
			//$('#' + createDetails1).show();
			$('#createLineitemCrLead').hide();
			$('.hideCrled').hide();
			var t = 0;
		  var key2='';
				debugger;
	
			$.post(servletName, { action: 'Edit1', id: id}, function(r) {
				if (r.data) {
					//
					console.log(r.data);
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							//
							 if(key!='add_prod'){
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							
									$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
						
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							/*
							if (key == 'reach_lnformation' && r.data[i][key] == 'Delay_in_TAT') {
								t = 1;
								key1= r.data[i][key];
								
							}*/
							
							if (key == 'cheque_no' && r.data[i][key] !='') {
								t = 1;
								key2= r.data[i][key];
								
							}
							
						}
						}
					}
					
		
										
					      
                                                    		
							  $('select[name=deliver_by] option[value=' + r.data[0].deliver_by + ']').attr('selected', true);
							 	$('input[name="id"]').val(id);
							
							$('input[name="dt_deliver"]').val(r.data[0].dtdeliver);
                              	
															
									
					//edit this is for multiple selectd drop value
			/*	  var m = r.data[0].add_prod.split(",");
			                     console.log(m);

                           for (i=0;i<m.length;i++){
                             $('select[name="add_prod"] option[value=' + m[i] + ']').attr('selected', true);
                               }*/
						var uploadFile=[];
						var download = [];
						var down = [];
						var attach = [];
						//
						if(r.data[0].proof_cltn_file != undefined){
							
								attach= r.data[0].proof_cltn_file.split(',') 
							
							for(var j=0;j<attach.length;j++){
								uploadFile[j]=attach[j];
								down[j]='<a href="downloadLeadDetails.jsp?proof_cltn_file='+uploadFile[j]+'">'+uploadFile[j]+'</a> ';
							console.log(down[j]);
					
				 //  download[j]= '<a href="downloadLeadDetails.jsp?proof_cltn_file='+uploadFile[j]+'"><img src="InvoiceScanFile/'+uploadFile[j]+' " /></a>'; 
				
							document.getElementById("FinleName"+j).innerHTML ='<a href="downloadLeadDetails.jsp?proof_cltn_file='+uploadFile[j]+'" title='+uploadFile[j]+'><img height="100" width="100" src="InvoiceScanFile/'+uploadFile[j]+'" onmouseover="this.width=130;" " onmouseout="this.width=100" ;/></a>'; 
							//download[j]= '<a href="downloadLeadDetails.jsp?proof_cltn_file='+uploadFile[j]+'"><img src="InvoiceScanFile/'+uploadFile[j]+' " /></a>'; 
				
					
							//document.getElementById("FinleName"+j).innerHTML =download[j];	
						
							}
							
							}
							
						var uploadFile1='';
						var download1 = '';
						var attach1 = [];
						
						if(r.data[0].idproof_file != undefined){
							
							uploadFile1=r.data[0].idproof_file;
								 document.getElementById("Filename").innerHTML='<a href="downloadLead_Idproof.jsp?idproof_file='+uploadFile1+'" title='+uploadFile1+'><img height="100" width="100" src="InvoiceScanFile/'+uploadFile1+'" onmouseover="this.width=130;" " onmouseout="this.width=100" ;/></a>'; 
							
							/*console.log(download1);
							document.getElementById("FinleName").innerHTML =download1;	*/
							
							
							}	
							
					var uploadFile2='';
						var download2 = '';
						
						
						if(r.data[0].equpment_file != undefined){
					
							uploadFile2=r.data[0].equpment_file;
							
						//	download2='<a href="downloadLead_prodImageproof.jsp?equpment_file='+uploadFile2+'">'+uploadFile2+'</a> ';
							 document.getElementById("File_name").innerHTML='<a href="downloadLead_prodImageproof.jsp?equpment_file='+uploadFile2+'" title='+uploadFile2+'><img height="100" width="100" src="InvoiceScanFile/'+uploadFile2+'" onmouseover="this.width=130;" " onmouseout="this.width=100" ;/></a>'; 
							
							//document.getElementById("File_name").innerHTML =download2;	
							
							
							}
					   debugger;
	        	     
//$('input[name="dt_expinstall"]').val(r.data[0].dtexp_install);

				
					}
				else {
					bootbox.alert("Try again.");
				}
				/*if (t == 1) {
					Showcolfortat(key1);
				}*/debugger;
			   if (t == 1) {
					Showcolforcheque(key2);
				}
			}, 'json');
			
		
	}
	});
}	


    function show() {
            /* Access image by id and change 
            the display property to block*/
            document.getElementById('image')
                .style.display = "block";
            document.getElementById('btnID')
                .style.display = "none";
        }

function ControlAssingTomeNewlead(action, displayContent, createDetails, formName, servletName,id) {
	//
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
			    $('#' + displayContent).show();
			
				DisplayAssignTomeNew(servletName, displayContent, createDetails, '', 'LeadForDisplay');
				window.location = $('.assingtomeNewLead_event').attr('href');
				
			
				
				}
				
		//	
			 if(action =='EditMylead')
	{
		debugger;
		EditAssingTomeNew(action , displayContent , createDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
	
	}
		
			}
        });
}


function ControlAssingTomeInprogresslead(action, displayContent, createDetails, formName,DisplayTableClass, servletName,id) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
			    $('#' + displayContent).show();
			
				DisplayAssignTomeinprogress(servletName, displayContent, createDetails, '', 'LeadForDisplay');
				window.location = $('.assigntomelead_inprogress_event').attr('href');
				
			
				
				}
				
			//
			 if(action =='EditMylead')
	{ 
		//alert(createDetails);
		EditAssingTomeNew(action , displayContent , createDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
	
	}
	
 if (action == 'ReturnStore') {
	debugger;
		$('input[name="action"]').val('ReturnStore');
					
					
			var t=0;
	
			$('.restoreitem').each(function(){
				
				
				$('input[name="dt_return_str'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						
					
				
					
						val = $('input[name="dt_return_str'+$(this).val()+'"]').val();
						if(val == '')
							{
							
							
								
								bootbox.alert("Please fill the date field.");
								$('input[name="dt_return_str'+$(this).val()+'"]').addClass('error');
								$('input[name="dt_return_str'+$(this).val()+'"]').focus();
								exit(0);
								
							}
						
					
						t=1;
						
					}
				
			});
		
	if(t == 0)
	{
		bootbox.alert("Please select at least one asset.");
	}
	else
		{
		
		 $('.form-control').removeAttr('disabled');
		var x = $('#'+formName).serialize();
		//alert(x);
	//debugger;
	   console.log(x);
		$('.Returnitemtostr').attr('disabled','disabled');
			$.post('O_AssignedLlead',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.Returnitemtostr').removeAttr('disabled');
								alert("Product Return to Stock successfully.");
								window.location = $('.assigntomelead_inprogress_event').attr('href');
														
							}
						
							else
								{
									$('.Returnitemtostr').removeAttr('disabled');
									bootbox.alert("Try again.");
								}
									
						
					},'json');
		
			
		
		
			
		}
	       }
			}
				
 if (action == 'ExtendRentalDays') {
	debugger;
		$('input[name="action"]').val('ExtendRentalDays');
					
					
			var t=0;
	
			$('.restoreitem').each(function(){
				
				
				$('input[name="dt_extend_str'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						
					
				
					
				
						val1 = $('input[name="extend_un_prc'+$(this).val()+'"]').val();
						val = $('input[name="dt_extend_str'+$(this).val()+'"]').val();
						if(val == '')
							{
							
							
								
								bootbox.alert("Please fill the date field.");
								$('input[name="dt_extend_str'+$(this).val()+'"]').addClass('error');
								$('input[name="dt_extend_str'+$(this).val()+'"]').focus();
								exit(0);
								
							}
						if(val1 == '')
							{
							
							
								
								bootbox.alert("Please fill the date field.");
								$('input[name="dt_extend_str'+$(this).val()+'"]').addClass('error');
								$('input[name="dt_extend_str'+$(this).val()+'"]').focus();
								exit(0);
								
							}
						
					
						t=1;
						
					}
				
			});
		
	if(t == 0)
	{
		bootbox.alert("Please select at least one lead.");
	}
	else
		{
		
		 $('.form-control').removeAttr('disabled');
		var x = $('#'+formName).serialize();
		console.log(x);
	
		$('.Returnitemtostr').attr('disabled','disabled');
			$.post('O_AssignedLlead',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.Returnitemtostr').removeAttr('disabled');
								alert("Extension of Rental Date Product successfully.");
								window.location = $('.assigntomelead_inprogress_event').attr('href');
														
							}
						
							else
								{
									$('.Returnitemtostr').removeAttr('disabled');
									bootbox.alert("Try again.");
								}
									
						
					},'json');
		
			
		
		
			
		}
	       }

 if (action == 'InitiateForRefill') {
	//debugger;
		$('input[name="action"]').val('InitiateForRefill');
					
					
			var t=0;
	
			$('.restoreitem').each(function(){
				
				
			
				if(this.checked)
					{	
						t=1;
					}
				
			});
		
	if(t == 0)
	{
		bootbox.alert("Please select at least one lead.");
	}
	else
		{
		
		 $('.form-control').removeAttr('disabled');
		var x = $('#'+formName).serialize();
		
	
		$('.InitiateitemRefill').attr('disabled','disabled');
			$.post('O_AssignedLlead',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.InitiateitemRefill').removeAttr('disabled');
								alert("Product initiate for Refill successfully.");
								window.location = $('.assigntomelead_inprogress_event').attr('href');
														
							}
						if(r.data ==2)
							{
								$('.InitiateitemRefill').removeAttr('disabled');
								alert("Product is not Cylinder Can not  initiate for Refill'.");
								window.location = $('.assigntomelead_inprogress_event').attr('href');
														
							}
						/*	else
								{
									$('.InitiateitemRefill').removeAttr('disabled');
									bootbox.alert("Try again.");
								}*/
									
						
					},'json');
		
			
		
		
			
		}
	       }
			
			});
		}

function EditAssingTomeNew(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			var invnoforextension='';
			
			//$('input[name="leadCrCheck"]').val('1');
			  var tagto_me_state =  $('input[name="tagto_me_state"]').val();
			   $('#empdataforreturnbyLd').attr('disabled','disabled'); 
		
			
			/*$('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');*/
	
			
	       
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemCrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			//
			
			$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=return_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
								
							
						}
					}
			$.post(servletName, { action: 'EditMylead', id:id,tagto_me_state:tagto_me_state }, function(r) {
				if (r.data) {
					
					
					
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							 
								
							 
                                 
                            
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							if(key == 'refund_amt'){
								rfd=r.data[i][key];
								if(rfd!='0.00'){
							     $('#refund_amt_adjust').removeAttr('readonly');
								 //$('#tot_ref_amt').attr('disabled');
								}
								else{
									 $('#refund_amt_adjust').attr('readonly', 'readonly');
								}
							}//
						 if(key == 'trnsprt_amt'){
							$('input[name=' + key + ']').val('0.00');
							
						}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							
							
							
						}
						
					}
					
					  if(r.data[0].prod_status=='Refill'){
						//debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}
					
					 
				
					$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					//$('input[name="action"]').val("Assign");
	        	     $('input[name="id"]').val(id);


                 $('input[name="dt_billing"]').val(r.data[0].dtbill);
                 $('input[name="no_po"]').val(r.data[0].po_no);
                 $('input[name="dt_po"]').val(r.data[0].po_date);
            
           
					$('input[name="dt_tag"]').val(r.data[0].tagdt);
					$('input[name="refund_amount"]').val(r.data[0].refund_amt);
					$('input[name="refund_amt"]').val(r.data[0].refund_amt);
			
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							
					
					  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {
								debugger;	 
				SubDropDownDataDisplayforemp(r.data[0].id_design_tagemp, 'EmpdataforField', 'M_Emp_User',r.data[0].id_sloc , function(status) {
								if (status) {		
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							      debugger;
			                   $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
							$('select[name=tag_to_emp] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                 		
							  
												
															
										}
					             });
					         }
					     });
					  }
					});
					/*var uploadFile='';
						if(r.data[0].invoice_file != undefined)
							uploadFile = r.data[0].invoice_file;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("datachment").innerHTML =download;	*/
							
							//debugger;
					if(tagto_me_state=='New'){
							dispalyModifyLineCrItemLead(r);
					}
					else{
						
						dispalyModifyLineReturnItemLead(r);
					
					}
					
				
				 for (var i = 0; i < r.data.length; i++) {
					console.log(r.data[i].inv_no);
					
					if(invnoforextension==''){
						
						invnoforextension=invnoforextension+r.data[i].inv_no;
					}
					else{
						invnoforextension=invnoforextension+';'+r.data[i].inv_no;
					}
				}
					
					
			/*		if(butType!='modi'){
				
			$('.form-control').attr('disabled', 'disabled');
			}*/
			 
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
			}, 'json');
			if(tagto_me_state=='Inprogress'){
			
			setTimeout(function() { 
				       dispalyModifyLineReturnItemLeadHasReturn(id,tagto_me_state);
				    setTimeout(function() { 
					  debugger;
				       dispalyModifyLineExtensionReturnItem(id,tagto_me_state,invnoforextension);
				
						}, 1000);
				
						}, 1000);
						
						
			}
		/*	if(tagto_me_state=='Inprogress'){
			setTimeout(function() { 
				       dispalyModifyLineExtensionReturnItem(id,tagto_me_state);
				
						}, 1000);
			}*/
			
		
	}
	});
}	

//lineitem for return store

function dispalyModifyLineReturnItemLead(r) {
	if (r.data) {
		
		var list ='<tr class="">'+
			'<td colspan="23">'+
			'<button type="button" style="margin-left: 470px;"  class="btn btn-primary Returnitemtostr" onclick="ControlAssingTomeInprogresslead(\'ReturnStore\',\'displayAssigntomeINPLd\',\'EditAssigntomeINPLd\',\'submitAssigntomeINPLd\',\'ldassignINPForDisplaytd\',\'O_AssignedLlead\',\'\')">Return To Stock</button>'+
			'<button type="button"  style="margin-left: 5px;" class="btn btn-primary Returnitemtostr" onclick="ControlAssingTomeInprogresslead(\'ExtendRentalDays\',\'displayAssigntomeINPLd\',\'EditAssigntomeINPLd\',\'submitAssigntomeINPLd\',\'ldassignINPForDisplaytd\',\'O_AssignedLlead\',\'\')">Extend</button>'+
			'<button type="button"  style="margin-left: 5px;" class="btn btn-primary InitiateitemRefill" onclick="ControlAssingTomeInprogresslead(\'InitiateForRefill\',\'displayAssigntomeINPLd\',\'EditAssigntomeINPLd\',\'submitAssigntomeINPLd\',\'ldassignINPForDisplaytd\',\'O_AssignedLlead\',\'\')">Inititate Refill</button>'+
			'</td>+</tr>';
			 list = list +'<tr class="new">'+
		    '<tr tr class="new">' +
			'<td colspan="21"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>'+'<tr class="tableHeader info">' +
			
			'<td style="width: 80px;"><center><strong>Check/Uncheck</strong></center></td>'+
				
			'<td><strong><center>Product Status<a href=#></a></center></strong></td>' +
			'<td><strong><center>Return Date<a href=#></a></center></strong></td>' +
			'<td><strong><center>Remark<a href=#></a></center></strong></td>' +
			'<td><strong><center>Extension of Rental Days<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td><strong><center>Extend Date<a href=#></a></center></strong></td>' +
			'<td><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td><strong><center>New Expire Date<a href=#></a></center></strong></td>' +
			//'<td><strong><center>Unit Price of Extension of Rental Days<a href=#></a></center></strong></td>' +
		
			'<td><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
			
			'<td><strong><center>Current Invoice No<a href=#></a></center></strong></td>' +
			'<td><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Qty<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Qty<a href=#></a></center></strong></td>' +
			'<td><strong><center>Current Pay Mode<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Cheque No<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Current Unit Price<a href=#></a></center></strong></td>' +
		     '<td ><strong><center>Fill Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Current Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Current Expire Date<a href=#></a></center></strong></td>' +
		
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>' +
			'</tr>';
			
			var tot=0.0;
		for (var i = 0; i < r.data.length; i++) {
			
			if(r.data[i].typ_service=='Rental'&&r.data[i].extension_status!='Yes'){
				//consile.log(r.data[i].buyback);
				debugger;
				tot=tot+parseFloat(r.data[i].gr_tot);
				list += '<tr>' +
				'<td><center><strong><input type="checkbox" name="retrunstr" class="uninstallitemForSelectAll restoreitem" value="'+r.data[i].sr_no+'"/></strong></center></td>'+
					'<td><select style="width: 180px;" class="form-control" name = "prod_status'+r.data[i].sr_no+'" id="prod_status'+r.data[i].sr_no+'"  patelUnPrc="'+r.data[i].sr_no+'"  onchange ="showRowBasedonStatus(event)" data-valid="mand" >'+
					'<option value = "working" >Received in Working</option>'+
					'<option value = "Extend days" >Extension of Rental days</option>'+
					'<option value = "Refill" >Refill Rental Cylinder </option>'+
						'<option value = "Refill_and_Extend" >Refill and Extend Cylinder Rental days</option>'+
				    '<option value="physical_dmg_mjr">Received in Physical Damage (Major)</option>'+
					'<option value="physical_dmg_mnr">Received in Physical Damage (Minor) </option>	'+
					'</select>'+
					'</td>'+
				'<td><center><input type="text" id="dt_return_str' +r.data[i].sr_no + '"  name="dt_return_str' +r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="' +''+ '" style="width: 100px;text-align: right;" class="form-control datepicker commonhide" ></center></td>' +
				'<td><center><input type="text"  name="rmk' +r.data[i].sr_no+ '" patelUnPrc="' +r.data[i].sr_no+ '" style="width: 100px;text-align: right;" class="form-control commonhide " ></center></td>' +
				'<td><select style="width: 80px;" class="form-control commonhide" name = "extension_status'+r.data[i].sr_no+'" id="extension_status'+r.data[i].sr_no+'" patelUnPrc="' + r.data[i].sr_no + '"  onchange="showRowExtension(event,\'current_exp_dt\',\'extend_un_prc\',\'extend_days\',\'dt_return_str\',\'dt_extend_str\')"  data-valid="mand" >'+
					'<option value ="No" >No</option>'+
				    '<option value="Yes">Yes</option>'+
					'</select>'+
					'</td>'+
				'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + r.data[i].sr_no + '" name="renew_sr_no' +r.data[i].sr_no + '" patelUnPrc="' +r.data[i].sr_no  + '"  class="form-control commonhide" data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				'<td><center><input type="text"  id="dt_extend_str' +r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '"  name="dt_extend_str' +r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="' +''+ '" style="width:100px;text-align:right;display:none;" class="form-control extenddt datepicker commonhide" ></center></td>' +
				'<td ><center><input type="text"  id="extend_days' + r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '" name="extend_days' + r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="" style="width: 60px;display:none";text-align: right;" onchange="ShowRowColdtextend(event,\'current_exp_dt\')" class="form-control commonhide"></center></td>' +
				'<td><center><input type="text"  id="current_exp_dt' +r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '"  name="current_exp_dt' +r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="' +''+ '" style="width:100px;text-align:right;display:none;" class="form-control datepicker commonhide" ></center></td>' +
				//'<td><center><input type="text" id="extend_un_prc' +r.data[i].sr_no + '" name="extend_un_prc' +r.data[i].sr_no+ '" patelUnPrc="' +r.data[i].sr_no+ '" style="width:100px;text-align:right;display:none;" class="form-control " ></center></td>' +
			
					
					'<td><center>'+r.data[i].nm_prod+'</center></td>'+
					
					'<td><center>'+r.data[i].sr_no+'</center></td>'+
					'<td><center><input type="text"  name="current_inv_no' +r.data[i].sr_no+ '" patelUnPrc="' +r.data[i].sr_no+ '" value="' + r.data[i].inv_no + '" style="width: 100px;text-align: right;" class="form-control " ></center></td>' +
					//'<td><center>'+r.data[i].inv_no+'</center></td>'+
					'<td><center>'+r.data[i].ds_product+'</center></td>'+
			       // '<td><center>'+r.data[i].un_prc+'</center></td>'+
                   '<td><center><select style="width: 90px !important;" id="paymodeDivForLead'  + r.data[i].sr_no + '" name="cr_pay_mode' + r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no+ '" value="' + r.data[i].pay_mode + '" class="form-control commonhide" data-valid=""  style="width:80;display:none;" onchange="ShowRowColchequeExtend(event,\'cr_cheque_no\')"  readonly >' +
		             '<option value="FOC">FOC</option>'+
					'<option value="Cash">Cash</option>'+
						'<option value="UPI">UPI/Online</option>'+
						'<option value="Credit">Credit</option>'+
						'<option value="Cheque">Cheque</option>'+'</select>' +
			   '</center></td>' +
	            '<td ><center><input type="text"  id="cr_cheque_no' + r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '" name="cr_cheque_no' + r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="" style="width: 60px;display:none;"text-align: right;"  class="form-control commonhide"></center></td>' +
	              '<td><center><input type="text" name="un_prc'+ r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotextendld(event,\'un_prc\')" class="patelUnPrc form-control commonhide" data-valid="num"  ></center></td>' +
					//'<td><center>'+r.data[i].others+'</center></td>'+
					//'<td><center>'+r.data[i].nm_tax1+'</center></td>'+
					//'<td><center>'+r.data[i].nm_tax2+'</center></td>'+
					'<td><center><input type="text" name="cylndr_fill_mt' +  r.data[i].sr_no + '" patelUnPrc="' +  r.data[i].sr_no + '" value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotextendld(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control commonhide" data-valid="num" data-valid="mand" ></center></td>' +
					'<td><input type="text" name="others' + r.data[i].sr_no + '" value="' + r.data[i].others + '" patelUnPrc="' + r.data[i].sr_no +  '" style="float:right;text-align: right;width:80px""  onChange="calculateTotextendld(event,\'others\')"  class="form-control commonhide" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + r.data[i].sr_no + '" value="' + r.data[i].id_tax1 + '" patelTax="' + r.data[i].sr_no +  '" patelUnPrc="' + r.data[i].sr_no +  '" id="id_tax1' + r.data[i].sr_no + '"   class="form-control patelTax commonhide" onChange="getTax2(event);" >' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + r.data[i].sr_no +'" value="' + r.data[i].id_tax2 + '" patelTax="' + r.data[i].sr_no +  '" patelUnPrc="'+ r.data[i].sr_no +  '" id="id_tax2' + r.data[i].sr_no + '"   class="form-control patelTax2 commonhide" onChange="TaxCalculationextendld(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + r.data[i].sr_no +'" patelUnPrc="' + r.data[i].sr_no +  '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotextendld(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control commonhide" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no +  '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotextendld(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control commonhide" readonly></center></td>' +
		'<td><center><input type="text"  name="buyback' + r.data[i].sr_no +'" patelUnPrc="'+ r.data[i].sr_no +'" value="' + r.data[i].buyback + '" onChange="calculateTotextendld(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control commonhide" ></center></td>' +
					//'<td><center>'+r.data[i].tax_val1+'</center></td>'+
					//'<td><center>'+r.data[i].tax_val2+'</center></td>'+
					//'<input type="hidden" name="buyback' + r.data[i].buyback + '" value="' + r.data[i].gr_tot + '">' +
				    //'<td><center>'+r.data[i].buyback+'</center></td>'+
					'<td><center>'+r.data[i].typ_service+'</center></td>'+
					'<td><center>'+r.data[i].rental_day+'</center></td>'+
					//'<td><center>'+r.data[i].dt_exprent+'</center></td>'+
					'<td><center><input type="text"  id="dt_exprent' +r.data[i].sr_no + '"  name="dt_exprent' +r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="' +r.data[i].dt_exprent+ '" style="width:100px;text-align:right;" class="form-control commonhide" readonly></center></td>' +
					//'<td><center>'+r.data[i].gr_tot+'</center></td>'+
					'<td><center><input type="text"  name="gr_tot' + r.data[i].sr_no + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal commonhide" readonly></center></td>' +
					//'<input type="hidden" name="gr_tot' + r.data[i].sr_no + '" value="' + r.data[i].gr_tot + '">' +
					'<input type="hidden" name="typ_service' + r.data[i].sr_no + '" value="' + r.data[i].typ_service + '">' +
			        '<input type="hidden" name="id_lead' + r.data[i].sr_no + '" value="' + r.data[i].id_lead + '">' +
                    '<input type="hidden" name="id_lead_m' + r.data[i].sr_no + '" value="' + r.data[i].id_lead_m + '">' +
                    '<input type="hidden" name="id_prod' + r.data[i].sr_no + '" value="' + r.data[i].id_prod + '">' +
                     '<input type="hidden" name="ds_product' + r.data[i].sr_no + '" value="' + r.data[i].ds_product + '">' +
                    '<input type="hidden" name="nm_prod' + r.data[i].sr_no + '" value="' + r.data[i].nm_prod + '">' +
				        '<input type="hidden" name="type_prod' + r.data[i].sr_no + '" value="' + r.data[i].type_grp + '">' +
                        '<input type="hidden" name="rental_day' + r.data[i].sr_no + '" value="' + r.data[i].rental_day + '">' +
                     
				        '<input type="hidden" name="lead_no' + r.data[i].sr_no + '" value="' + r.data[i].lead_no + '">' +
                        '<input type="hidden" name="id_sloc' + r.data[i].sr_no + '" value="' + r.data[i].id_sloc + '">' +
                        '<input type="hidden" name="id_loc' + r.data[i].sr_no + '" value="' + r.data[i].id_loc + '">' +
                       
				'</tr>';
			}
			if(r.data[i].typ_service=='Sale'){
				//it is not use now
				
				
				tot=tot+parseFloat(r.data[i].gr_tot);
				list += '<tr>' +
				'<td><center><strong><input type="checkbox" name="retrunstr" class="uninstallitemForSelectAll restoreitem" value="'+r.data[i].sr_no+'"/></strong></center></td>'+
				'<td><center><input type="text" id="dt_return_str' +r.data[i].sr_no + '"  name="dt_return_str' +r.data[i].sr_no+ '" patelUnPrc="' +r.data[i].sr_no+ '" value="' +''+ '" style="width: 100px;text-align: right;" class="form-control datepicker" ></center></td>' +
				'<td><center><input type="text"  name="rmk' +r.data[i].sr_no+ '" patelUnPrc="' +r.data[i].sr_no+ '" style="width: 100px;text-align: right;" class="form-control " ></center></td>' +
				'<td><select style="width: 180px;" name = "prod_status'+r.data[i].sr_no+'" id="returndate'+i+'" class=""    data-valid="mand" >'+
					'<option value = "working" >Received in Working</option>'+
				    '<option value="physical_dmg_mjr">Received in Physical Damage (Major)</option>'+
					'<option value="physical_dmg_mnr">Received in Physical Damage (Minor) </option>	'+
					'</select>'+
					'</td>'+
					'<td><center>'+r.data[i].nm_prod+'</center></td>'+
					'<td><center>'+r.data[i].sr_no+'</center></td>'+
					'<td><center>'+r.data[i].ds_product+'</center></td>'+
					
					//'<td><center>'+r.data[i].un_prc+'</center></td>'+
					 '<td><center><input type="text" name="un_prc'+ r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotextendld(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
					'<td><input type="text" name="others' + r.data[i].sr_no + '" value="' + r.data[i].others + '" patelUnPrc="' + r.data[i].sr_no +  '" style="float:right;text-align: right;width:80px""  onChange="calculateTotextendld(event,\'others\')"  class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + r.data[i].sr_no + '" value="' + r.data[i].id_tax1 + '" patelTax="' + r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no +  '" id="id_tax1' + r.data[i].sr_no + '"   class="form-control patelTax" onChange="getTax2(event);" >' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + r.data[i].sr_no +'" value="' + r.data[i].id_tax2 + '" patelTax="' + r.data[i].sr_no +  '" patelUnPrc="' + r.data[i].sr_no +  '" id="id_tax2' + r.data[i].sr_no +  '"   class="form-control patelTax2" onChange="TaxCalculationextendld(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + r.data[i].sr_no +'" patelUnPrc="' + r.data[i].sr_no + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotextendld(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotextendld(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
		'<td><center><input type="text"  name="buyback' + r.data[i].sr_no + '" patelUnPrc="' + r.data[i].sr_no + '" value="' + r.data[i].buyback + '" onChange="calculateTotextendld(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
					//'<td><center>'+r.data[i].others+'</center></td>'+
					//'<td><center>'+r.data[i].nm_tax1+'</center></td>'+
					//'<td><center>'+r.data[i].nm_tax2+'</center></td>'+
					//'<td><center>'+r.data[i].tax_val1+'</center></td>'+
					//'<td><center>'+r.data[i].tax_val2+'</center></td>'+
				    //'<td><center>'+r.data[i].buyback+'</center></td>'+
					'<td><center>'+r.data[i].typ_service+'</center></td>'+
					'<td><center>'+"NA"+'</center></td>'+
					'<td><center>'+"NA"+'</center></td>'+
					'<td><center><input type="text"  name="gr_tot' + r.data[i].sr_no + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
					//'<td><center>'+r.data[i].gr_tot+'</center></td>'+
					//'<input type="hidden" name="typ_service' + r.data[i].sr_no + '" value="' + r.data[i].typ_service + '">' +
					'<input type="hidden" name="id_prod' + r.data[i].sr_no + '" value="' + r.data[i].id_prod + '">' +
				    '<input type="hidden" name="id_lead' + r.data[i].sr_no + '" value="' + r.data[i].id_lead + '">' +
	                  '<input type="hidden" name="id_prod' + r.data[i].sr_no + '" value="' + r.data[i].id_prod + '">' +
                       '<input type="hidden" name="type_prod' + r.data[i].sr_no + '" value="' + r.data[i].type_grp + '">' +
                     
						
      //'<input type="hidden" name="action" value="ReturnStore" class="form-control">' 
				
				//'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
			}
			
			
		
		}
		 
	
	
	    $('#leadCrDetails1').html(list);
	  $('input[name="amount"]').val(r.data[0].tot_inv_amt);
//total bill amount refund is not add in this
		 $('input[name="amt"]').val(r.data[0].tot_inv_amt);	
	      $('input[name="colct_amount"]').val(r.data[0].colct_amount);	
           $('input[name="due_amount"]').val(r.data[0].due_amount);	
	 $('input[name="lead_no"]').val(r.data[0].lead_no);	
	



//get dropdown in a loop 
		var x = 0;

var loopArray = function(arr) {
    // call itself
     debugger;
      console.log(arr[x].id_prod);
    SubDropDownDataDisplayitemwithAllSrNo(arr[x].id_prod,arr[x].id_sloc,arr[x].id_loc,arr[x].sr_no,arr[x].type_grp,'serialnoDivForlead'+arr[x].sr_no+'','S_Add_To_Stock',function (status){
					              if (status) {
						 
         
    $('select[name=renew_sr_no'+arr[x].sr_no+'] option[value=' + arr[x].sr_no + ']').attr('selected', true);
  
		 
        // set x to next item
        x++;
        // any more items in array?
        if(x < arr.length) {
         	loopArray(arr);   
        }
    }}); 
}


loopArray(r.data);	



	
	//Auto select Dropdown of lineitems
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
										
									for (var i = 0; i < r.data.length; i++) {
										
										params = r.data[i];
										for (var key in r.data[i]) {
											
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + r.data[i].sr_no + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + r.data[i].sr_no + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
											if (key == 'pay_mode') {
												$('select[name=cr_pay_mode' + r.data[i].sr_no + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
										
										
									

										}
										
									}
					}
				});
			}
		});
		
	}
	
	
		$('.extenddt').datepicker({
						yearRange: '1985:2025', 
						changeMonth: true,
					      changeYear: true,
					      dateFormat: "dd/mm/yy",
					      autoSize: true,
					      altFormat: "dd/mm/yy",
					      onSelect: function(selected,evnt) {
					    	
					    	  var srno = $(this).attr('patelUnPrc');
					    	  var name = $(this).attr('name');
					    	  var dt_return_str = $(this).val();
					    	$('.datepicker').removeClass('error');
				     	
					
					      }
					});
					
	$('.datepicker').datepicker({
						yearRange: '1985:2025', 
						changeMonth: true,
					      changeYear: true,
					      dateFormat: "dd/mm/yy",
					      autoSize: true,
					      altFormat: "dd/mm/yy",
					      onSelect: function(selected,evnt) {
					    	
					    	  var srno = $(this).attr('patelUnPrc');
					    	  var name = $(this).attr('name');
					    	  var dt_return_str = $(this).val();
					    	$('.datepicker').removeClass('error');
				     	
					 /*   $.post('O_AssignedLlead',{action : 'CheckRetrunstkDate' , dt_return_str : dt_return_str ,id : srno},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('Return date should be greater or equal to Expiry Date '+r.dt_return_str);
					    			$('input[name="'+name+'"]').focus();
					    			$('input[name="'+name+'"]').val('');
					    			$('input[name="'+name+'"]').addClass('error');
					    				exit(0);
					    			}
					    		
					    },'json');*/
					      }
					});
	
}



//lineitem for return store

function dispalyModifyLineExtensionReturnItem(id,tagto_me_state,invnoforextension) {
	
	debugger;
	console.log(id);
		$.post('O_AssignedLlead', { action: 'EditExtensionReturn', id:id,tagto_me_state:tagto_me_state,invnoforextension:invnoforextension }, function(r) {
				 
					 
				 
		
	if (r.data) {
		
		var list ='<tr class="">'+
			 
			'<tr class="new">'+
		    '<tr tr class="new">' +
			'<td colspan="23"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details Extension of Rental Days</b></td></tr>'+'<tr class="tableHeader info">' +
			'<td><strong><center>Product Status<a href=#></a></center></strong></td>' +
			 
			'<td style="width: 80px;"><center><strong>Check/Uncheck</strong></center></td>'+
			'<td><strong><center>Return Date<a href=#></a></center></strong></td>' +
			'<td><strong><center>Remark<a href=#></a></center></strong></td>' +
			'<td><strong><center>Extension of Rental Days<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
				'<td><strong><center>Extension Date<a href=#></a></center></strong></td>' +
			'<td><strong><center>Extension Days<a href=#></a></center></strong></td>' +
		
			'<td><strong><center>New Expire<a href=#></a></center></strong></td>' +
			'<td><strong><center>Extension Status<a href=#></a></center></strong></td>' +
			'<td><strong><center>Date of Extension<a href=#></a></center></strong></td>' +
			'<td><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			
			'<td><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td><strong><center>Current Invoice No<a href=#></a></center></strong></td>' +
			'<td><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Qty<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			'<td><strong><center>Current Pay Mode<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Cheque No<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Expire Date<a href=#></a></center></strong></td>' +
		
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>' 
			'</tr>';
			
			 
		for (var i = 0; i < r.data.length; i++) {
		 debugger;
			if(r.data[i].typ_service=='Rental'&&r.data[i].extension_status=='Yes'&&r.data[i].cr_pay_mode!='Cheque'){
				//consile.log(r.data[i].buyback);
				 
				 var prod_status="";
				 if(r.data[i].prod_status=='Extend days'){
					prod_status='Extension of Rental days'; 
				 }
				
			

				
				//tot=tot+parseFloat(r.data[i].gr_tot);
				list += '<tr>' +
				'<td><center><strong><input type="checkbox" name="retrunstr" class="uninstallitemForSelectAll restoreitem" value="'+r.data[i].renew_sr_no+'"/></strong></center></td>'+
				'<td><select style="width: 180px;" class="form-control" name = "prod_status'+r.data[i].renew_sr_no+'" id="prod_status'+r.data[i].renew_sr_no+'"  patelUnPrc="prod_status'+r.data[i].renew_sr_no+'"  value="'+r.data[i].prod_status+'" data-valid="mand" >'+
					'<option value = "working" >Received in Working</option>'+
					'<option value = "Extend_days" >Extension of Rental days</option>'+
					'<option value = "Refill" >Refill Rental Cylinder days</option>'+
					'<option value = "Refill_and_Extend" >Refill_and_Extend Cylinder Rental days</option>'+
				    '<option value="physical_dmg_mjr">Received in Physical Damage (Major)</option>'+
					'<option value="physical_dmg_mnr">Received in Physical Damage (Minor) </option>	'+
					'</select>'+
					'</td>'+
					
				'<td><center><input type="text" id="dt_return_str' +r.data[i].renew_sr_no + '"  name="dt_return_str' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="' +''+ '" style="width: 100px;text-align: right;" class="form-control datepicker"></center></td>' +
				'<td><center><input type="text"  name="rmk' +r.data[i].renew_sr_no+ '" patelUnPrc="' +r.data[i].renew_sr_no+ '" value="'+r.data[i].rmk+'" style="width: 100px;text-align: right;" class="form-control " ></center></td>' +
			     '<td><select style="width: 80px;" class="form-control" name = "extension_status'+r.data[i].renew_sr_no+'" id="extension_status'+r.data[i].renew_sr_no+'" patelUnPrc="' + r.data[i].renew_sr_no + '"  onchange="showRowExtension(event,\'current_exp_dt\',\'extend_un_prc\',\'extend_days\',\'dt_return_str\',\'dt_extend_str\')"  data-valid="mand" >'+
					'<option value ="No" >No</option>'+
				    '<option value="Yes">Yes</option>'+
					'</select>'+
					'</td>'+
					'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + r.data[i].renew_sr_no + '" name="renew_sr_no' +r.data[i].renew_sr_no + '" patelUnPrc="' +r.data[i].renew_sr_no  + '"  class="form-control" data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
					'<td><center><input type="text"  id="dt_extend_str' +r.data[i].renew_sr_no + '"  name="dt_extend_str' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value=" " style="width:100px;text-align:right;" class="form-control datepicker" ></center></td>' +
				'<td ><center><input type="text"  id="extend_days' + r.data[i].renew_sr_no + '" name="extend_days' + r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value=" "  onchange="ShowRowColdtextend(event,\'current_exp_dt\')" style="width: 60px;text-align: right;"  class="form-control"></center></td>' +
				
				'<td><center><input type="text"  id="current_exp_dt' +r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no + '"  name="current_exp_dt' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="" style="width:100px;text-align:right;" class="form-control datepicker" ></center></td>' +
				//'<td><center><input type="text" id="extend_un_prc' +r.data[i].sr_no + '" name="extend_un_prc' +r.data[i].sr_no+ '" patelUnPrc="' +r.data[i].sr_no+ '" value="'+r.data[i].extend_un_prc+'" style="width:100px;text-align:right;" class="form-control " ></center></td>' +
				'<td><center>'+r.data[i].extension_status+'</center></td>'+
					'<td><center>'+r.data[i].dtextend_str+'</center></td>'+
						'<td><center>'+r.data[i].rental_day+'</center></td>'+
					
					
				
					'<td><center>'+r.data[i].nm_prod+'</center></td>'+
					'<td><center>'+r.data[i].renew_sr_no+'</center></td>'+
					'<td><center><input type="text"  name="current_inv_no' +r.data[i].renew_sr_no+ '" patelUnPrc="' +r.data[i].renew_sr_no+ '" value="' + r.data[i].current_inv_no + '" style="width: 100px;text-align: right;" class="form-control " ></center></td>' +
					//'<td><center>'+r.data[i].current_inv_no+'</center></td>'+
					'<td><center>'+r.data[i].ds_product+'</center></td>'+
					   '<td><center><select style="width: 90px !important;" id="paymodeDivForLead' +r.data[i].renew_sr_no+ '" name="cr_pay_mode' +r.data[i].renew_sr_no+ '" patelUnPrc="' +r.data[i].renew_sr_no+ '" value="' + r.data[i].cr_pay_mode + '" class="form-control" data-valid=""  style="width:80;" onchange="ShowRowColchequeExtend(event,\'cr_cheque_no\')"  readonly >' +
		       
                  '<option value="FOC">FOC</option>'+
					'<option value="Cash">Cash</option>'+
						'<option value="UPI">UPI/Online</option>'+
						'<option value="Credit">Credit</option>'+
						'<option value="Cheque">Cheque</option>'+'</select>' +
			   '</center></td>' +
	            '<td ><center><input type="text"  id="cr_cheque_no' + r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no + '" name="cr_cheque_no' + r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="" style="width: 60px;display:none;text-align: right;"  class="form-control"></center></td>' +
					 '<td><center><input type="text" name="un_prc'+ r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotextendld(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' +  r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotextendld(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand"></center></td>' +
					'<td><input type="text" name="others' + r.data[i].renew_sr_no + '" value="' + r.data[i].others + '" patelUnPrc="' + r.data[i].renew_sr_no +  '" style="float:right;text-align: right;width:80px""  onChange="calculateTotextendld(event,\'others\')"  class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_tax1 + '" patelTax="' + r.data[i].renew_sr_no +  '" patelUnPrc="' + r.data[i].renew_sr_no +  '" id="id_tax1' + r.data[i].renew_sr_no + '"   class="form-control patelTax" onChange="getTax2(event);" >' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + r.data[i].renew_sr_no +'" value="' + r.data[i].id_tax2 + '" patelTax="' + r.data[i].renew_sr_no +  '" patelUnPrc="'+ r.data[i].renew_sr_no +  '" id="id_tax2' + r.data[i].renew_sr_no + '"   class="form-control patelTax2" onChange="TaxCalculationextendld(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + r.data[i].renew_sr_no +'" patelUnPrc="' + r.data[i].renew_sr_no +  '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotextendld(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no +  '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotextendld(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
		        '<td><center><input type="text"  name="buyback' + r.data[i].renew_sr_no +'" patelUnPrc="'+ r.data[i].renew_sr_no +'" value="' + r.data[i].buyback + '" onChange="calculateTotextendld(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			     
					'<td><center>'+r.data[i].typ_service+'</center></td>'+
					'<td><center><input type="text"  id="dt_exprent' +r.data[i].renew_sr_no + '"  name="dt_exprent' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="' +r.data[i].dtexprent+ '" style="width:100px;text-align:right;" class="form-control" readonly></center></td>' +
					
					//'<td><center>'+r.data[i].dtexprent+'</center></td>'+
					//'<td><center>'+r.data[i].rental_day+'</center></td>'+
					'<td><center><input type="text"  name="gr_tot' + r.data[i].renew_sr_no + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
					
					
					//'<td><center><input type="text"  id="dt_exprent' +r.data[i].sr_no + '"  name="dt_exprent' +r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="' +r.data[i].dt_exprent+ '" style="width:100px;text-align:right;" class="form-control" readonly></center></td>' +
					//'<td><center>'+r.data[i].gr_tot+'</center></td>'+
					//'<input type="hidden" name="gr_tot' + r.data[i].sr_no + '" value="' + r.data[i].gr_tot + '">' +
					'<input type="hidden" name="typ_service' + r.data[i].renew_sr_no + '" value="' + r.data[i].typ_service + '">' +
					
			        '<input type="hidden" name="id_lead' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_lead + '">' +
                    '<input type="hidden" name="id_lead_m' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_lead_m + '">' +
                    '<input type="hidden" name="id_prod' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_prod + '">' +
                     '<input type="hidden" name="nm_prod' + r.data[i].renew_sr_no + '" value="' + r.data[i].nm_prod + '">' +
                   '<input type="hidden" name="type_prod' + r.data[i].renew_sr_no + '" value="' + r.data[i].type_prod + '">' +
                    '<input type="hidden" name="ds_product' + r.data[i].renew_sr_no + '" value="' + r.data[i].ds_product + '">' +
				      '<input type="hidden" name="dt_exprent' + r.data[i].renew_sr_no + '" value="' + r.data[i].dt_exprent + '">' +
				     '<input type="hidden" name="rental_day' + r.data[i].renew_sr_no + '" value="' + r.data[i].rental_day + '">' +
				     
				'</tr>';
			}
				if(r.data[i].typ_service=='Rental'&&r.data[i].extension_status=='Yes'&&r.data[i].cr_pay_mode=='Cheque'){
				//consile.log(r.data[i].buyback);
				 
				 var prod_status="";
				 if(r.data[i].prod_status=='Extend days'){
					prod_status='Extension of Rental days'; 
				 }
				
			

				
				//tot=tot+parseFloat(r.data[i].gr_tot);
				list += '<tr>' +
				'<td><center><strong><input type="checkbox" name="retrunstr" class="uninstallitemForSelectAll restoreitem" value="'+r.data[i].renew_sr_no+'"/></strong></center></td>'+
				'<td><select style="width: 180px;" class="form-control" name = "prod_status'+r.data[i].renew_sr_no+'" id="prod_status'+r.data[i].renew_sr_no+'"  patelUnPrc="prod_status'+r.data[i].renew_sr_no+'"  value="'+r.data[i].prod_status+'" data-valid="mand" >'+
					'<option value = "working" >Received in Working</option>'+
					'<option value = "Extend days" >Extension of Rental days</option>'+
					'<option value = "Refill" >Refill Rental Cylinder days</option>'+
						'<option value = "Refill_and_Extend" >Refill_and_Extend Cylinder Rental days</option>'+
				    '<option value="physical_dmg_mjr">Received in Physical Damage (Major)</option>'+
					'<option value="physical_dmg_mnr">Received in Physical Damage (Minor) </option>	'+
					'</select>'+
					'</td>'+
				'<td><center><input type="text" id="dt_return_str' +r.data[i].renew_sr_no + '"  name="dt_return_str' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="' +''+ '" style="width: 100px;text-align: right;" class="form-control datepicker"></center></td>' +
				'<td><center><input type="text"  name="rmk' +r.data[i].renew_sr_no+ '" patelUnPrc="' +r.data[i].renew_sr_no+ '" value="'+r.data[i].rmk+'" style="width: 100px;text-align: right;" class="form-control " ></center></td>' +
			     '<td><select style="width: 80px;" class="form-control" name = "extension_status'+r.data[i].renew_sr_no+'" id="extension_status'+r.data[i].renew_sr_no+'" patelUnPrc="' + r.data[i].sr_no + '"  onchange="showRowExtension(event,\'current_exp_dt\',\'extend_un_prc\',\'extend_days\',\'dt_return_str\',\'dt_extend_str\')"  data-valid="mand" >'+
					'<option value ="No" >No</option>'+
				    '<option value="Yes">Yes</option>'+
					'</select>'+
					'</td>'+
					'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + r.data[i].renew_sr_no + '" name="renew_sr_no' +r.data[i].renew_sr_no + '" patelUnPrc="' +r.data[i].renew_sr_no  + '"  class="form-control" data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
					'<td><center><input type="text"  id="dt_extend_str' +r.data[i].renew_sr_no + '"  name="dt_extend_str' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value=" " style="width:100px;text-align:right;" class="form-control datepicker" ></center></td>' +
				'<td ><center><input type="text"  id="extend_days' + r.data[i].renew_sr_no + '" name="extend_days' + r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value=" "  onchange="ShowRowColdtextend(event,\'current_exp_dt\')" style="width: 60px;text-align: right;"  class="form-control"></center></td>' +
				
				'<td><center><input type="text"  id="current_exp_dt' +r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no + '"  name="current_exp_dt' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="" style="width:100px;text-align:right;" class="form-control datepicker" ></center></td>' +
				//'<td><center><input type="text" id="extend_un_prc' +r.data[i].sr_no + '" name="extend_un_prc' +r.data[i].sr_no+ '" patelUnPrc="' +r.data[i].sr_no+ '" value="'+r.data[i].extend_un_prc+'" style="width:100px;text-align:right;" class="form-control " ></center></td>' +
				'<td><center>'+r.data[i].extension_status+'</center></td>'+
					'<td><center>'+r.data[i].dtextend_str+'</center></td>'+
						'<td><center>'+r.data[i].rental_day+'</center></td>'+
					
					
				
					
					'<td><center>'+r.data[i].nm_prod+'</center></td>'+
					'<td><center>'+r.data[i].renew_sr_no+'</center></td>'+
					'<td><center><input type="text"  name="current_inv_no' +r.data[i].renew_sr_no+ '" patelUnPrc="' +r.data[i].renew_sr_no+ '" value="' + r.data[i].current_inv_no + '" style="width: 100px;text-align: right;" class="form-control " ></center></td>' +
					
					//'<td><center>'+r.data[i].current_inv_no+'</center></td>'+
					'<td><center>'+r.data[i].ds_product+'</center></td>'+
					   '<td><center><select style="width: 90px !important;" id="paymodeDivForLead' +r.data[i].renew_sr_no+ '" name="cr_pay_mode' +r.data[i].renew_sr_no+ '" patelUnPrc="' +r.data[i].renew_sr_no+ '" value="' + r.data[i].cr_pay_mode + '" class="form-control" data-valid=""  style="width:80;" onchange="ShowRowColchequeExtend(event,\'cr_cheque_no\')"  readonly >' +
		       
                  '<option value="FOC">FOC</option>'+
					'<option value="Cash">Cash</option>'+
						'<option value="UPI">UPI/Online</option>'+
						'<option value="Credit">Credit</option>'+
						'<option value="Cheque">Cheque</option>'+'</select>' +
			   '</center></td>' +
	            '<td ><center><input type="text"  id="cr_cheque_no' + r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no + '" name="cr_cheque_no' + r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="' + r.data[i].cr_cheque_no + '" style="width: 60px;text-align: right;"  class="form-control"></center></td>' +
					 '<td><center><input type="text" name="un_prc'+ r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotextendld(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' +  r.data[i].renew_sr_no + '" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotextendld(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
					'<td><input type="text" name="others' + r.data[i].renew_sr_no + '" value="' + r.data[i].others + '" patelUnPrc="' + r.data[i].renew_sr_no +  '" style="float:right;text-align: right;width:80px""  onChange="calculateTotextendld(event,\'others\')"  class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_tax1 + '" patelTax="' + r.data[i].renew_sr_no +  '" patelUnPrc="' + r.data[i].renew_sr_no +  '" id="id_tax1' + r.data[i].renew_sr_no + '"   class="form-control patelTax" onChange="getTax2(event);" >' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + r.data[i].renew_sr_no +'" value="' + r.data[i].id_tax2 + '" patelTax="' + r.data[i].renew_sr_no +  '" patelUnPrc="'+ r.data[i].renew_sr_no +  '" id="id_tax2' + r.data[i].renew_sr_no + '"   class="form-control patelTax2" onChange="TaxCalculationextendld(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + r.data[i].renew_sr_no +'" patelUnPrc="' + r.data[i].renew_sr_no +  '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotextendld(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + r.data[i].renew_sr_no + '" patelUnPrc="' + r.data[i].renew_sr_no +  '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotextendld(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
		        '<td><center><input type="text"  name="buyback' + r.data[i].renew_sr_no +'" patelUnPrc="'+ r.data[i].renew_sr_no +'" value="' + r.data[i].buyback + '" onChange="calculateTotextendld(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			     
					'<td><center>'+r.data[i].typ_service+'</center></td>'+
					'<td><center><input type="text"  id="dt_exprent' +r.data[i].renew_sr_no + '"  name="dt_exprent' +r.data[i].renew_sr_no+ '" patelUnPrc="' + r.data[i].renew_sr_no + '" value="' +r.data[i].dtexprent+ '" style="width:100px;text-align:right;" class="form-control" readonly></center></td>' +
					
					//'<td><center>'+r.data[i].dtexprent+'</center></td>'+
					//'<td><center>'+r.data[i].rental_day+'</center></td>'+
					'<td><center><input type="text"  name="gr_tot' + r.data[i].renew_sr_no + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
					
					
					//'<td><center><input type="text"  id="dt_exprent' +r.data[i].renew_sr_no + '"  name="dt_exprent' +r.data[i].sr_no+ '" patelUnPrc="' + r.data[i].sr_no + '" value="' +r.data[i].dt_exprent+ '" style="width:100px;text-align:right;" class="form-control" readonly></center></td>' +
					//'<td><center>'+r.data[i].gr_tot+'</center></td>'+
					//'<input type="hidden" name="gr_tot' + r.data[i].sr_no + '" value="' + r.data[i].gr_tot + '">' +
					'<input type="hidden" name="typ_service' + r.data[i].renew_sr_no + '" value="' + r.data[i].typ_service + '">' +
			        '<input type="hidden" name="id_lead' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_lead + '">' +
                    '<input type="hidden" name="id_lead_m' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_lead_m + '">' +
                    '<input type="hidden" name="id_prod' + r.data[i].renew_sr_no + '" value="' + r.data[i].id_prod + '">' +
                     '<input type="hidden" name="nm_prod' + r.data[i].renew_sr_no + '" value="' + r.data[i].nm_prod + '">' +
                    '<input type="hidden" name="ds_product' + r.data[i].renew_sr_no + '" value="' + r.data[i].ds_product + '">' +
				      '<input type="hidden" name="type_prod' + r.data[i].renew_sr_no + '" value="' + r.data[i].type_prod + '">' +
				  '<input type="hidden" name="dt_exprent' + r.data[i].renew_sr_no + '" value="' + r.data[i].dt_exprent + '">' +
				     '<input type="hidden" name="rental_day' + r.data[i].renew_sr_no + '" value="' + r.data[i].rental_day + '">' +
				     
				'</tr>';
			}
			
			
			
		
		}
		 
	//get dropdown in a loop 
		var x = 0;

var loopArray = function(arr) {
    // call itself
 debugger;
console.log(arr[x].id_prod);
    SubDropDownDataDisplayitemwithAllSrNo(arr[x].id_prod,arr[x].id_sloc,arr[x].id_loc,arr[x].renew_sr_no,arr[x].type_prod,'serialnoDivForlead'+arr[x].renew_sr_no+'','S_Add_To_Stock',function (status){
					              if (status) {
						 
         
    $('select[name=renew_sr_no'+arr[x].renew_sr_no+'] option[value=' + arr[x].renew_sr_no + ']').attr('selected', true);
  
		 
        // set x to next item
        x++;
        // any more items in array?
        if(x < arr.length) {
         	loopArray(arr);   
        }
    }}); 
}


loopArray(r.data);	


			DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
										
									for (var i = 0; i < r.data.length; i++) {
										
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + r.data[i].renew_sr_no + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + r.data[i].renew_sr_no + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
										   if (key == 'cr_pay_mode') {
												$('select[name=cr_pay_mode' + r.data[i].renew_sr_no + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
										
									

										}
										
									}
					}
				});
			}
		});
	    $('#leadCrDetails1').append(list);
	  
                         for (var i = 0; i < r.data.length; i++) {
										
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'extension_status') {
												
			   $('select[name=extension_status' + r.data[i].renew_sr_no + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
										}
										}

		
	}
	$('.datepicker').datepicker({
						yearRange: '1985:2025', 
						changeMonth: true,
					      changeYear: true,
					      dateFormat: "dd/mm/yy",
					      autoSize: true,
					      altFormat: "dd/mm/yy",
					      onSelect: function(selected,evnt) {
					    	
					    	  var srno = $(this).attr('patelUnPrc');
					    	  var name = $(this).attr('name');
					    	  var dt_return_str = $(this).val();
					    	$('.datepicker').removeClass('error');
				 
					      }
					});
	
	},'json');
 
	
}


function dispalyModifyLineReturnItemLeadHasReturn(id,tagto_me_state) {
		$.post('O_AssignedLlead', { action: 'EditMyleadHasReturn', id:id,tagto_me_state:tagto_me_state }, function(r) {
				 
					 
				 
		
	if (r.data) {
		console.log(r.data);
		var list ='<tr class="">'+
			 
			'<tr class="new">'+
		    '<tr tr class="new">' +
			'<td colspan="23"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details of Return to stock</b></td></tr>'+'<tr class="tableHeader info">' +
			
			 
			'<td><strong><center>Return Date<a href=#></a></center></strong></td>' +
			'<td><strong><center>Remark<a href=#></a></center></strong></td>' +
		
		//'<td><strong><center>Status Extension of Rental Days<a href=#></a></center></strong></td>' +
			//'<td><strong><center>Extension Days<a href=#></a></center></strong></td>' +
			//'<td><strong><center>Extend Date<a href=#></a></center></strong></td>' +
			//'<td><strong><center>Unit Price of Extension of Rental Days<a href=#></a></center></strong></td>' +
			//'<td><strong><center>Product Status<a href=#></a></center></strong></td>' +
			'<td><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
			//'<td><strong><center>Renew Serial No<a href=#></a></center></strong></td>' +
				'<td><strong><center>Invoice No<a href=#></a></center></strong></td>' +
			'<td><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Qty<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Expire Date<a href=#></a></center></strong></td>' +
		
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>' +
			'</tr>';
			
			 
		for (var i = 0; i < r.data.length; i++) {
			
			if(r.data[i].typ_service=='Rental'){
				
				 
				 var prod_status="";
			     console.log(r.data[i].prod_status);
				 if(r.data[i].prod_status=='working'){
					console.log(prod_status);
					prodstatus='Received in Working'; 
				 }
				 if(r.data[i].prod_status=='physical_dmg_mjr'){
					prodstatus='Received in Physical Damage (Major)'; 
				 }
				 
			      if(r.data[i].prod_status=='physical_dmg_mnr'){
					prodstatus='Received in Physical Damage (Minor)'; 
				 }
				  if(r.data[i].renew_sr_no=='' || r.data[i].renew_sr_no=='NULL' || r.data[i].renew_sr_no==undefined){
					renew_sr_no=r.data[i].sr_no; 
				 }
			else
				renew_sr_no=r.data[i].renew_sr_no;
			
				 
			 
			/*	if(r.data[i].dt_extendstr==''||r.data[i].dt_extendstr=='undefined'){
					dtextendstr='NA'; 
				 }
			  if(r.data[i].dt_extend_str!=''){
					dtextendstr=r.data[i].dt_extendstr; 
				 }*/
				/*if(r.data[i].extend_un_prc==''||r.data[i].extend_un_prc=='undefined'){
					extendunprc='NA'; 
				 }
			  if(r.data[i].extend_un_prc!=''){
					extendunprc=r.data[i].extend_un_prc; 
				 }
				if(r.data[i].extend_days==''||r.data[i].extend_days=='undefined'){
					extenddays='NA'; 
				 }
			 if(r.data[i].extend_days!=''){
					extenddays=r.data[i].extend_days; 
				 }*/
			  /*if(r.data[i].extension_status!=''){
					extenddays=r.data[i].extend_days; 
				 }
				if(r.data[i].extension_status==''||r.data[i].extension_status=='undefined'){
					extensionstatus='NA'; 
				 }*/
			 
				list += '<tr>' +
				
				
						'<td><center>'+r.data[i].dt_return_strdt+'</center></td>'+
				'<td><center>'+r.data[i].rmk+'</center></td>'+
				//'<td> <center>'+r.data[i].extensionsts+'</center> </td>'+
				//'<td> <center>'+'NA'+'</center> </td>'+
				//'<td> <center>'+dtextendstr+'</center> </td>'+
				//'<td> <center>'+'NA'+'</center> </td>'+
				//'<td> <center>'+prodstatus+'</center> </td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				//'<td><center>'+r.data[i].sr_no+'</center></td>'+
				'<td><center>'+renew_sr_no+'</center></td>'+
				'<td><center>'+r.data[i].latest_inv_no+'</center></td>'+
				
				'<td><center>'+r.data[i].ds_product+'</center></td>'+
			        '<td><center>'+r.data[i].un_prc+'</center></td>'+
					'<td><center>'+r.data[i].others+'</center></td>'+
					'<td><center>'+r.data[i].nm_tax1+'</center></td>'+
					'<td><center>'+r.data[i].nm_tax2+'</center></td>'+
					'<td><center>'+r.data[i].tax_val1+'</center></td>'+
					'<td><center>'+r.data[i].tax_val2+'</center></td>'+
				    '<td><center>'+r.data[i].buyback+'</center></td>'+
					'<td><center>'+r.data[i].typ_service+'</center></td>'+
					'<td><center>'+r.data[i].rental_day+'</center></td>'+
					'<td><center>'+r.data[i].dt_exprent+'</center></td>'+
					'<td><center>'+r.data[i].gr_tot+'</center></td>'+
				
				
				
				 
			/*	'<td><center>'+r.data[i].dt_return_strdt+'</center></td>'+
				'<td><center>'+r.data[i].rmk+'</center></td>'+
				//'<td> <center>'+r.data[i].extensionsts+'</center> </td>'+
				//'<td> <center>'+r.data[i].extend_days+'</center> </td>'+
				//'<td> <center>'+dtextendstr+'</center> </td>'+
				'<td> <center>'+r.data[i].extend_un_prc+'</center> </td>'+
				//'<td> <center>'+prodstatus+'</center> </td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				'<td><center>'+r.data[i].sr_no+'</center></td>'+
				'<td><center>'+r.data[i].ds_product+'</center></td>'+
			      //  '<td><center>'+r.data[i].un_prc+'</center></td>'+
					'<td><center>'+r.data[i].others+'</center></td>'+
					'<td><center>'+r.data[i].nm_tax1+'</center></td>'+
					'<td><center>'+r.data[i].nm_tax2+'</center></td>'+
					'<td><center>'+r.data[i].tax_val1+'</center></td>'+
					'<td><center>'+r.data[i].tax_val2+'</center></td>'+
				    '<td><center>'+r.data[i].buyback+'</center></td>'+
					'<td><center>'+r.data[i].typ_service+'</center></td>'+
					'<td><center>'+r.data[i].rental_day+'</center></td>'+
					'<td><center>'+r.data[i].dt_exprent+'</center></td>'+
					'<td><center>'+r.data[i].gr_tot+'</center></td>'+*/
					      //'<input type="hidden" name="action" value="ReturnStore" class="form-control">' 
		             //'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
			}
			else{
		//if(r.data[i].typ_service=='Rental'&&r.data[i].extension_status=='No'){
				
				 
				 var prod_status="";
			console.log(r.data[i].prod_status);
				 if(r.data[i].prod_status=='working'){
					prodstatus='Received in Working'; 
				 }
				 if(r.data[i].prod_status=='physical_dmg_mjr'){
					prodstatus='Received in Physical Damage (Major)'; 
				 }
				 
			      if(r.data[i].prod_status=='physical_dmg_mnr'){
					prodstatus='Received in Physical Damage (Minor)'; 
				 }
				 
			
				 
			 
			/*	if(r.data[i].dt_extendstr==''||r.data[i].dt_extendstr=='undefined'){
					dtextendstr='NA'; 
				 }
			  if(r.data[i].dt_extend_str!=''){
					dtextendstr=r.data[i].dt_extendstr; 
				 }*/
		
			 
				list += '<tr>' +
				 
				'<td><center>'+r.data[i].dt_return_strdt+'</center></td>'+
				'<td><center>'+r.data[i].rmk+'</center></td>'+
				//'<td> <center>'+r.data[i].extensionsts+'</center> </td>'+
				//'<td> <center>'+'NA'+'</center> </td>'+
				//'<td> <center>'+dtextendstr+'</center> </td>'+
				//'<td> <center>'+'NA'+'</center> </td>'+
				//'<td> <center>'+prodstatus+'</center> </td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				'<td><center>'+r.data[i].sr_no+'</center></td>'+
				'<td><center>'+r.data[i].ds_product+'</center></td>'+
			        '<td><center>'+r.data[i].un_prc+'</center></td>'+
					'<td><center>'+r.data[i].others+'</center></td>'+
					'<td><center>'+r.data[i].nm_tax1+'</center></td>'+
					'<td><center>'+r.data[i].nm_tax2+'</center></td>'+
					'<td><center>'+r.data[i].tax_val1+'</center></td>'+
					'<td><center>'+r.data[i].tax_val2+'</center></td>'+
				    '<td><center>'+r.data[i].buyback+'</center></td>'+
					'<td><center>'+r.data[i].typ_service+'</center></td>'+
					'<td><center>'+r.data[i].rental_day+'</center></td>'+
					'<td><center>'+r.data[i].dt_exprent+'</center></td>'+
					'<td><center>'+r.data[i].gr_tot+'</center></td>'+
					      //'<input type="hidden" name="action" value="ReturnStore" class="form-control">' 
		             //'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
			}
			
		
		}
		 
	    $('#leadCrDetails2').append(list);
	  
	
		
	}
	
	},'json');
 
	
}




//display for MYLead 
function DisplayAssignTomeNew(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			//debugger;
		
			
		/*	var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();*/
				var searchWord = $('input[name="searchWord"]').val();
			var tagto_me_state =  $('input[name="tagto_me_state"]').val();
		    let count=0;
        
	                    
                  
			$.post(servletName, { action: 'DisplayMylead', lead_no: lead_no, searchWord:searchWord,tagto_me_state:tagto_me_state }, function(r) {
				var list
				
				list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					
					'<td><strong><center>Lead Assign Date</center></strong></td>' +
						
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					'<td><strong><center>Update</center></strong></td>' +
					 '</tr></thead><tbody>';
		          
				
				  if(tagto_me_state=='New'){
			         if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						debugger
						list = list + '<tr>' +
						'<td><center>' + ++count + '</center></td>'+
						 '<td><center><a class="alertlink" href="#" onclick="ControlAssingTomeNewlead(\'EditMylead\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +
					      '<td><center>' + params.tagdt + '</center></td>' +
	                       '<td><center>' + params.nm_loc + '</center></td>'+
						   '<td><center>' + params.nm_subl + '</center></td>'+
						   '<td><center>' + params.nm_pstn + '</center></td>' +
							'<td><center>' + params.assingBy + '</center></td>' +
							'<td><center>' + params.assignTo + '</center></td>' +
								
							'<td><center><a class="alertlink" href="#" onclick="ControlNewleaddeliver(\'Create\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+',\'Editdelivery\',\'submitdelivery\','+params.trnsprt_amt+','+params.tot+','+params.refund_amt+','+params.id_lead_m+' ,'+params.tag_by+' )">' + 'Update' + '</a></center></td>' +
							'</tr>'
						
						/* invamt =r.data[0].tot-r.data[0].refund_amt;
							 $('input[name="amount"]').val(invamt);	
						console.log( $('input[name="amount"]').val(invamt));
							console.log( $('input[name="amount"]').val());
						 $('input[name="amt"]').val(invamt);	
                      $('input[name="trns_amt"]').val(r.data[0].trnsprt_amt);
                      $('input[name="ref_amt"]').val(r.data[0].refund_amt);
console.log('hi'+params.id_lead_m);*/
                  /*   $('input[name="ld_time"]').val(r.data[0].ld_time);
                     $('input[name="id_lead_m"]').val(params.id_lead_m);
                     $('input[name="tag_by"]').val(params.tag_by);*/

						 //autoselect
				       
                     }
						
					}
				
		
				else {
					list += '<tr><td colspan="9"><strong><center>No data found.</center></strong></td></tr>';
					$('#lddeliveryForDisplaytd').html('</tbody>' + list);
				}
				
				$('#lddeliveryForDisplaytd').html('</tbody>' + list);
				getButtonsForListView('lddeliveryForDisplaytd','Lead_List');
				}
			}, 'json');
			
	
		}
	});
}








//mylead inprogress
function DisplayAssignTomeinprogress(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			

			
			/*var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();*/
			var searchWord = $('input[name="searchWord"]').val();
			var tagto_me_state =  $('input[name="tagto_me_state"]').val();
		    let count=0;
        
	                    
                  
			$.post(servletName, { action: 'DisplayMylead', lead_no: lead_no, searchWord:searchWord,tagto_me_state:tagto_me_state }, function(r) {
				var list
				 // if(state=='Inprogress'){
				list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Refill Status</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					
					'<td><strong><center>Lead Assign Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					'<td><strong><center>View</center></strong></td>' +
					//'<td><strong><center>Return To Store</center></strong></td>' +
					 '</tr></thead><tbody>';
		          
				
				  if(tagto_me_state=='Inprogress'){
			         if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						//debugger;
						//if(params.type_grp=='TYPE-CYLINDER'||params.type_grp=='type-cylinder'){}
						if(params.lead_no_refill==undefined||params.lead_no_refill=='NULL'||params.lead_no_refill==''){
							titlletaxhover="Not Refill Till Now";
						}
						if(params.lead_no_refill!=''||params.lead_no_refill!='NA'){
							leadNORefill='Refill Lead No.'+params.lead_no_refill;
							refillsrNo='Refill Sr No. Of Cylinder'+params.sr_no_refill;
						    beforeRefillsrNo='Before Sr No. Of Cylinder of +'+params.lead_no+'+'+params.sr_no_refill;
						    titlletaxhover=leadNORefill+refillsrNo+beforeRefillsrNo;
						}
						
				              refillst='Refill Status';
					       list = list + '<tr>' +
						 

                            '<td><center>' + ++count + '</center></td>' +
                           '<td><center><a class="alertlink" href="#" onmouseover="DisplayRefillhistory(\''+params.lead_no+'\','+params.id_proceed_m+',\''+params.type_grp+'\')">' +refillst+ '</a></center></td>' +
			                '<td><center><a class="alertlink" href="#"  onclick="ControlAssingTomeInprogresslead(\'EditMylead\',\''+displayContent+'\',\''+createDetails+'\',\''+formName+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead\','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +
							'<td><center>' + params.tagdt + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>' +
							'<td><center>' + params.assingBy + '</center></td>' +
							'<td><center>' + params.assignTo + '</center></td>' +
							
							'<td><center><a class="alertlink" href="#" onclick="ControlNewleaddeliver(\'Edit1\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+',\'EditAssign\',\'submitAssign\' )">' + 'View' + '</a></center></td>' +
							
							
						'</tr>';
						//alert(params.tagby);
                      $('input[name="rental_day"]').val(params.rental_day);
                      $('input[name="tag_by"]').val(params.tagby);
//comment
                    /*  $('input[name="amount"]').val(params.tot);
                       $('input[name="amt"]').val(params.tot);*/

                     }
					}
				
				else {
					list += '<tr><td colspan="7"><strong><center>No data found.</center></strong></td></tr>';
					$('#ldassignINPForDisplaytd').html('</tbody>' + list);
				}
				$('#ldassignINPForDisplaytd').html('</tbody>' + list);
				getButtonsForListView('ldassignINPForDisplaytd','Lead_List');
				}
			}, 'json');
			
	
		}
	});
}






function DisplayRefillhistory(lead_no,id,type_product) {
	SessionCheck(function(ses) {
		if (ses) {
				$('#displayRefillHistory').show();
	$.post('O_AssignedLlead', { action: 'Display_Refillhistory',lead_no:lead_no,id:id,type_product:type_product }, function(r) {
				
		          	var list
				var result = '',result1 = '';
				debugger;
			         if (r.data.length > 0) {
				//leadNORefill='',result='';titlletaxhover='';
					for (var i = 0; i < r.data.length; i++) {
						//params = r.data[i];
						
						console.log(r.data[i]);
						
						if(r.data[i].lead_no_refill==undefined||r.data[i].lead_no_refill=='NULL'||r.data[i].lead_no_refill==''&&r.data[i].prev_lead_no!==undefined||r.data[i].prev_lead_no=='NULL'||r.data[i].prev_lead_no==''){
							titlletaxhover="Not Refill Till Now";
						}
						if(r.data[i].prev_lead_no!==undefined||r.data[i].prev_lead_no=='NULL'||r.data[i].prev_lead_no==''){
								prevleadRefill='Refill lead agained lead no'+':'+r.data[i].prev_lead_no
						
							//titlletaxhover="Not Refill Till Now";
							alert(prevleadRefill);
						}
						if(r.data[i].lead_no_refill!=''||r.data[i].lead_no_refill!='NA'){
							
							leadNORefill='Refill lead  '+':'+r.data[i].lead_no_refill
							
							refillsrNo=r.data[i].sr_no_refill;
							 if(i>0){
                                  result+= ','; 
                                     }
                             result += refillsrNo;
							
								beforeRefillsrNo=r.data[i].sr_no;
							 if(i>0){
                                  result1 += ','; 
                                     }
                             result1 += beforeRefillsrNo;
							}
						
						   }
                       
                            
							rflsrNo='Refill Sr No. Of Cylinder  '+' : '+result;
						    beforeRflsrNo='Before Refill Sr No. Of Cylinder'+' : '+result1;
                          titlletaxhover=leadNORefill+'\n'+rflsrNo+'\n'+beforeRflsrNo;
                           alert(titlletaxhover);
                            /*     var alt = bootbox.alert(titlletaxhover)
                              alt.show();
                                setTimeout(function(){alt.modal('hide'); }, 10000); */
					}
				
				else {
					titlletaxhover="Not Any Cylinder Refill Till Now";
					  var alt = bootbox.alert(titlletaxhover)
                               alt.show();
                                setTimeout(function(){alt.modal('hide'); }, 1000); 
						
				}
				
				
			}, 'json');
		
		}
	});
}

///
function ControlInitiatedRefillLead(action, displayContent, createDetails, formName, servletName,id) {
	
    SessionCheck(function(ses) {
		if (ses) {
			
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			
			
			
			if (action == 'Cancel') {
			
				$('#createinvoicelineitem').hide();
				$('#' + createDetails).hide();
				$('#' + displayContent).show();
			
				DisplayInititatedRefill(servletName, displayContent, createDetails, '', 'EditForRefillForDisplaytd');
				window.location = $('.Inititate_Refill_event').attr('href');
				}
				
				
	else if(action =='EditInitiateRefillItem')
	{
		EditInititateRefill(action , displayContent , createDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
	}
		
	if (action == 'Save') {
				debugger
			
				$('input[name="acion"]').val('Save');
				
					
				
				t = false;
						//t = true;
			    t = ValidationForm(formName);
				console.log(formName);
				if (t) {
				$('.led').attr('disabled', 'disabled');
				
				 $('.form-control').removeAttr('disabled');
				
				var x = $('#' + formName).serialize();
				
				var itemCount=$('input[name="itemCount"]').val();
			 
			
			var t=1;
				
				if($('input[name="id_prod0"]').val() == '' )	
				{
					t=0;
						bootbox.alert("Make sure you have filled all the mandatory fields !!");
						$('.led').removeAttr('disabled');
					}
					if(t==1)
			{
				$.post('O_Lead', x, function(r) {
					if (r.data == 1) {
						$('#createLineitemLeadRefill').hide();
						$('.readbaledata').each(function() {
							$(this).attr('readonly', 'readonly');
							if ($(this).is("select")) {
								$(this).attr("disabled", true);
							}
							$('.readbaledata').each(function() {
								$(this).attr("disabled", true);
							});
						});
						alert("Create Lead Sucessfully !!!!!");
						window.location = $('.Inititate_Refill_event').attr('href');
					}
				
					else {
						bootbox.alert("Try again.");
					}
					$('.led').removeAttr('disabled');
				}, 'json');
			}
				
				}
				}
			}
			});
		}

//display for create inv

function DisplayInititatedRefill(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			//debugger;
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
				//var searchWord = $('input[name="searchWord"]').val();
			let count=0;
         
	          $.post(servletName, { action: 'Displayfor_RefillItem',  dt_frm: dt_frm, dt_to: dt_to }, function(r) {
				var list
				 // if(state=='Inprogress'){
				list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Create Date</center></strong></td>' +
				     '<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Contact Number</center></strong></td>' +
				
					 '</tr></thead><tbody>';
		          
				       console.log(r.data);
			         if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						debugger
						
					       list = list + '<tr>' +
						 
                          '<td><center>' + ++count + '</center></td>' +

                          '<td><center><a class="alertlink" href="#" onclick="ControlInitiatedRefillLead(\'EditInitiateRefillItem\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +
	                        '<td><center>' + params.dtld + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>' +
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>'+
							'<td><center>' + params.pstn_ct + '</center></td>'+
							
							
							
							
						'</tr>';
            
	                 }
					 
					}
				
				
				else {
					
					list += '<tr><td colspan="7"><strong><center>No data found.</center></strong></td></tr>';
					$('#' + DisplayTableClass).html('</tbody>' + list);
				
					
				}
				
				$('#' + DisplayTableClass).html('</tbody>' + list);
					getButtonsForListView('EditForRefillForDisplaytd','Refill_lead_list');
			}, 'json');
			
	
		}
	});
}

function EditInititateRefill(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			
			
			
		
			   $('#empdataforreturnbyLd').attr('disabled','disabled'); 
		
		
	       
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemLeadRefill').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			//
			
			$.post('M_Emp_User', { action:'LoginUserDetails' }, function(r) {
					if (r.data) {
							console.log(r);
					
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=created_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
								
							
						}
					}
					//debugger;
			$.post(servletName, { action:'EditInitiateRefillItem', id:id }, function(r) {
				if (r.data) {
					console.log(r);
					
					
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							 
								
							 
                                 
                            
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
						 	else if(key=='dt_billing') {
								 $('input[name="dt_billing"]').val('');
                  
							}
							else if(key=='dt_ld') {
								 $('input[name="dt_ld"]').val('');
                  
							}
							else if(key=='po_date') {
								 $('input[name="po_date"]').val('');
                  
							}
							else if(key=='ld_time') {
								 $('input[name="ld_time"]').val('');
                  
							}
							else if(key=='trnsprt_amt') {
							 $('input[name="trnsprt_amt"]').val('0');
                  
							}
							else if(key=='tot') {
							 $('input[name="tot"]').val('0.00');
                  
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
						
							
							
						}
						
					}
						     
	               
 				 
                  
	              
					$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					//$('input[name="action"]').val("Assign");
	        	     $('input[name="id"]').val(id);


               
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							
				
								debugger;	 
				SubDropDownDataDisplaybydesingEmp(r.data[0].id_design_tagemp, 'EmpdataforCordinator', 'M_Emp_User', function(status) {
								if (status) {
									 $('select[name=id_src] option[value=' + r.data[0].idsrc + ']').attr('selected', true);
									
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							      debugger;
			                   $('select[name=id_design] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
							$('select[name=to_asign_cordi] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                 		
							  
												
															
										}
					             });
					     
					  }
					});
					
				
						//debugger;
						dispalyModifyLineForRefillItem(r);
					
				
					
			
					
					
			
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
			}, 'json');
	
	
		
	}
	});
}	



function dispalyModifyLineForRefillItem(r) {
	if (r.data) {
		//debugger;
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td><strong><center>status for lead<a href=#></a></center></strong></td>' +
			'<td><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td><strong><center>Description<a href=#></a></center></strong></td>' +
			
			
			'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Amount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			//'<td><strong><center>Product Status<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Expire Date<a href=#></a></center></strong></td>' +
			/*'<td ><strong><center>Refundable Deposite<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Transportation Charges<a href=#></a></center></strong></td>' +*/
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>' +
			'</tr>';
		for (var i = 0; i < r.data.length; i++) {
			console.log(r.data);
			
				if(r.data[i].prod_status=='Refill'){
					
					list += '<tr>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"   class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center>'+r.data[i].prod_status+'</center></td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				'<td><center>'+r.data[i].sr_no+'</center></td>'+
				'<td><center><select style="width: 170px !important;" id="serialnoDivForlead' + i + '" name="sr_no_refill' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" style="width:140">'+
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly></center></td>' +
				
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" + value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
				
			'<td><input type="text" name="others' + i + '" value="0.00" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotRefillead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"  class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  onChange="TaxCalculationRefilllead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotRefillead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotRefillead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '"   value="0.00" onChange="calculateTotRefillead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				/*'<td><select style="width: 180px;" class="form-control" name = "product_status'+ i + '" id="product_status'+ i + '"  patelUnPrc="' + i + '"  data-valid="mand" >'+
					'<option value = "working" >Received in Working</option>'+
					'<option value = "Extend days" >Extension of Rental days</option>'+
					'<option value = "Refill" >Refill Rental Cylinder </option>'+
					'<option value = "Refill_and_Extend" >Refill and Extend Cylinder Rental days</option>'+
				    '<option value="physical_dmg_mjr">Received in Physical Damage (Major)</option>'+
					'<option value="physical_dmg_mnr">Received in Physical Damage (Minor) </option>	'+
					'</select>'+
					'</td>'+*/
			/*'<td><center><select style="width: 110px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\')"  readonly >' +
		       
                  //'<option value="">Select</option>' +
			      '<option value="Sale" >Sale</option>' +
			     '<option value="Rental">Rental</option>'+
                  '<option value="Rental">Refill on Rental</option>'+'</select>' +
				'</center></td>' +*/
				'<td><center>'+r.data[i].typ_service+'</center></td>'+
				'<td><center>'+r.data[i].rental_day+'</center></td>'+
				'<td><center>'+r.data[i].dt_exprent+'</center></td>'+
	            //'<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].rental_day + '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')" readonly></center></td>'+
				//'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control datepicker" readonly></center></td>' +
			      '<input type="hidden" name="id_prod' + i + '" value="' + r.data[i].nm_prod + '">' +
				   '<input type="hidden" name="sr_no' + i + '" value="' + r.data[i].sr_no + '">' +
					'<input type="hidden" name="dt_exp_rent' + i + '" value="' + r.data[i].dt_exprent + '">' +
					'<input type="hidden" name="rental_day' + i + '" value="' + r.data[i].rental_day + '">' +
					'<input type="hidden" name="typ_service' + i + '" value="' + r.data[i].typ_service + '">' +
					
					'<td><center><input type="text"  name="gr_tot' + i + '" value="0.00" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				 '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
				'<input type="hidden" name="count" value="' + i + '">' +
				'<input type="hidden" name="un_prc' + i + '" value="0">' +
			
				'</tr>';
			
			
					
				}if(r.data[i].prod_status=='Refill_and_Extend'){
					
					    prodstatus='Refill and Extend';
						list += '<tr>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"   class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center>'+prodstatus+'</center></td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				'<td><center>'+r.data[i].sr_no+'</center></td>'+
				'<td><center><select style="width: 170px !important;" id="serialnoDivForlead' + i + '" name="sr_no_refill' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" style="width:140">'+
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" ></center></td>' +
			
				
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" + value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
				/*'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')"  class="form-control" readonly></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"  class="form-control patelTax" onChange="getTax2(event);" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '"    class="form-control patelTax2" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
			'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].buyback + '" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
			*/
			'<td><input type="text" name="others' + i + '" value="0.00" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotRefillead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '"  class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2"  onChange="TaxCalculationRefilllead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotRefillead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotRefillead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '"   value="0.00" onChange="calculateTotRefillead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			
				'<td><center>'+r.data[i].typ_service+'</center></td>'+
				
	            '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].rental_day + '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')" ></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control datepicker" ></center></td>' +
			      '<input type="hidden" name="id_prod' + i + '" value="' + r.data[i].nm_prod + '">' +
				   '<input type="hidden" name="sr_no' + i + '" value="' + r.data[i].sr_no + '">' +
					
					'<input type="hidden" name="typ_service' + i + '" value="' + r.data[i].typ_service + '">' +
					'<input type="hidden" name="un_prc' + i + '" value="0.00">' +
					'<td><center><input type="text"  name="gr_tot' + i + '" value="0.00" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				 '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
				'<input type="hidden" name="count" value="' + i + '">' +
				'<input type="hidden" name="un_prc' + i + '" value="0">' +
			
				'</tr>';
				}
			
			
			
		
		}
		$('input[name="itemCount"]').val(i);
		$('input[name="grand_tot1"]').val('0.00');
		$('#leadDetails1').html(list);
		
		
		
		
//get dropdown in a loop 
		var x = 0;	
var loopArray = function(arr) {
    // call itself
    SubDropDownDataDisplayitemwithAllSrNo(arr[x].id_prod,arr[x].id_sloc,arr[x].id_loc,arr[x].sr_no,arr[x].type_grp,'serialnoDivForlead'+x+'','S_Add_To_Stock',function (status){
					              if (status) {
							 
         
    $('select[name=sr_no_refill'+x+'] option[value=' + arr[x].sr_no_refill + ']').attr('selected', true);
  
		 
        // set x to next item
        x++;
        // any more items in array?
        if(x < arr.length) {
         	loopArray(arr);   
        }
    }}); 
}
	// start 'loop'
loopArray(r.data);	
		//Auto select Dropdown of lineitems
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
						
									for (var i = 0; i < r.data.length; i++) {
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + i + '] option[value='+r.data[i].id_tax1+']').attr('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + i + '] option[value='+r.data[i].id_tax2+']').attr('selected', true);
											}
										if (key == 'typ_service') {
												$('select[name=typ_service' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											
									    if (key == 'dt_exprent' && r.data[i][key] !='') {
							                       
                                             $('input[name="dt_exp_rent'+i+']').val(r.data[i][key].dt_exprent);	
								               //key1= r.data[i][key];
								
								               
					//
					
					                     //  ShowRowColdtexp(event ,'dt_exprent');
				
							                  }
										
											
										}
									}
								
					}
				});
			}
		});
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
}

//Getting Tax2 on selection Tax1
function getTax2(event) {
	console.log("hello");
	var domElement = $(event.target || event.srcElement);
	var id_tax1 = $('select[name="id_tax1' + domElement.attr('patelUnPrc') + '"]').val();
	$.post('M_Tax', { action: 'getTax2', id_tax1: id_tax1 }, function(r) {
		if (r.data) {
			$('select[name="id_tax2' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].id_tax);
		}
		TaxCalculationlead(event, 'id_tax1', 'tax_val1');
	}, 'json');
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



//Check duplicate lead Number
function checkInvoiceNo() {
	var t = 1;
	t = $('input[name="leadCheck"]').val();
	if (t == 0) { 
	}
}


function Statelead(){
	
	if($('#state').val()=='New'){
	
	$('input[name="state"]').val('Inprogress'); 
	}
	
}

//for total adjust refund amt
function CalculateRefundamt(name){
   var refunamt=(parseFloat($('input[name="refund_amt"').val()));
   var adjustrfd=(parseFloat($('input[name="refund_amt_adjust"').val()));	 
  debugger;
   if(adjustrfd<=refunamt){
	var adjust_ref  = refunamt-adjustrfd;	
               
      	
	    $('input[name="tot_ref_amt"').val(adjust_ref.toFixed(2));
       /*   var rfd_amt = $('input[name="refund_amount"').val()-$('input[name="tot_ref_amt"').val();	
              $('input[name="refund_amt"').val(rfd_amt.toFixed(2));*/
           var amt = $('input[name="amt"').val()-$('input[name="tot_ref_amt"').val();	
              $('input[name="amount"').val(amt.toFixed(2));
	
}
else{
	
	$('input[name="refund_amt_adjust"').val('');  
	 $('input[name="tot_ref_amt"').val('');
	alert("Adjustment refund amount can't be greater than Refund amount");
}
 
       

}


//this is fro rental installed and Exp date
function installed(){
  
        var type_srv= $('select[name="services"]').val();
 dat= parseInt($('input[name="rental_day"]').val());
		//alert(dat);
      $(function() {
	
	
		  if(type_srv=="Sale_Only"){
			if(dat==0)
		{
		
			//$(".expireDt").show();
			
			 var currentDate = new Date();
		     $(".datepicker").datepicker({
		      yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      
		    });
		
		$('input[name="dt_install"]').datepicker("setDate", currentDate);
		 currentDate.setDate(currentDate.getDate() + dat);
	 	$('input[name="dt_expinstall"]').datepicker("setDate", currentDate);
      }
        else{
			alert("Days should be zero ");
			   $('#rental_day').focus();
		    // $('#slocDataForLead').val('');
		     $('#rental_day').addClass('error');
		}
         
		
}//
		
	else {
		
		if(dat>0){
			
			 var currentDate = new Date();
		     $(".datepicker").datepicker({
		      yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      
		    });
		
		$('input[name="dt_install"]').datepicker("setDate", currentDate);
		 currentDate.setDate(currentDate.getDate() + dat);
	 	$('input[name="dt_expinstall"]').datepicker("setDate", currentDate);
		
		}
		else{
			alert("days should not be zero");
			   $('#rental_day').focus();
		    // $('#slocDataForLead').val('');
		     $('#rental_day').addClass('error');
		}
			//$(".expireDt").hide();
	}
		  });}
//rental day fxn
function installeddate(action){
	$('input[name="rental_day"]').val('');
	$('input[name="dt_install"]').val('');
	$('input[name="dt_expinstall"]').val('');

	if(action.value=="Sale_Only"){
  
		$(".expireDt").hide();
	}
	 if(action.value=="Rental_Only"||action.value=="Sale_and_Rental"){
	
	
			$(".expireDt").show();
			}
}	

//for reached location reason for tat   
			
function Showcolfortat(action){
	

	if(action.value=="On_Time"){
       $('input[name="reson_tat"]').val('');
		$(".reached_location").hide();
	}
	 if(action.value=="Delay_in_TAT"){
	
	
			$(".reached_location").show();
			}
}
		
function Showcolforcheque(action){
	
debugger;
	if(action.value=="Cheque"){
        $('input[name="cheque_no"]').val('');
		$('.srv').show();
	}
	 if(action.value!="Cheque"){
	
           $('.srv').hide();
           console.log('hi');
			}
}	

function ShowcolforAdhar(action){
	
debugger;
	if(action.value=="Yes"){
        $('input[name="idproof_file"]').val('');
		$('.adhar').show();
	}
	 if(action.value!="Yes"){
	
           $('.adhar').hide();
           alert("adhar is no verified");
			}
}		
	
		
        
		

		
		  
	       
		
        
		
	
//Auto Select Group,Sub-Group,Asset Type
function changeEventHandlerprodd(event) {
	var domElement =$(event.target || event.srcElement);
	
    var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
   
//var idloc = $('select[name="id_loc"]').val();
var idsloc = $('select[name="id_sloc"]').val();
		if(idsloc==''){
			  if(idloc==''){
				  alert("'Please select the entity.'");
		 /*  $('#locDataForLead').focus();
		     $('#locDataForLead').val('');
		     $('#locDataForLead').addClass('error');
			*/
			}
			else{
				
				  alert("'Please select the city. '");
	         $('#slocDataForLead').focus();
		     $('#slocDataForLead').val('');
		     $('#slocDataForLead').addClass('error');
			}
						
						}
						else{
								
		
		  

     					
	
	 $.post('M_Asset_Div',{action : 'GetProductdetails', id:nm_prod,idsloc:idsloc},function (r){
	 
			if(r.data.length==0)
				{
				
					
				alert("Not in Stock");
				$('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val('');			 
		$('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val('');
					$('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val('');
		          $('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val('0');

				/*SubDropDownDataDisplay(r.data[0].id_assetdiv,'subGroupDataForInvoice'+domElement.attr('patelUnPrc')+'','M_Subasset_Div',function (status){
					$('select[name=id_sgrp'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					$('select[name=typ_asst'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].typ_asst + ']').attr('selected',true);
					});*/
				}
				
				else{
					
					console.log(r.data);
					console.log(r.data[0].mfr_assetdiv);
			$('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].mfr_assetdiv);			 
		$('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].ds_assetdiv);
		$('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].un_prc_assetdiv);
		
		$('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].tot_aval_qty);
				
					
					
				}
	 },'json');

	
				
} }
$(function() {
	
		var currentDate = new Date();
		$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
		});
		
		$('input[name="dt_to"]').datepicker("setDate", currentDate);
		currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
		
	});
	
	/*jQuery("input#fileID").change(function() {
		var formData = new FormData();
		formData.append('file', $('#fileID').get(0).files[0]);
		$.ajax({
			url : 'Upload_Fil',
			type : 'POST',
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			dataType : "json",
			processData : false,
			mimeType : "multipart/form-data",
			success : function(r) {
				$('input[name="upload_led"]').val(r.upload_led);
			}
		}, 'json');*/
	//});
//rental expire date autoselect	
function ShowRowColdtextend(event , Id)
{
	
	  var domElement =$(event.target || event.srcElement);
      var IdName= Id + domElement.attr('patelUnPrc');
	 var value =  parseInt($('input[name="extend_days'+domElement.attr('patelUnPrc')+'"]').val());

	if(value==0)
		{
		
			$('#'+IdName).hide();
		}
	else if(value>0)
		{
			$('#'+IdName).show();
			
			var dt_exp_rent=$('input[name="dt_exprent'+domElement.attr('patelUnPrc')+'"]').val();	
			
	       console.log(dt_exp_rent);

var dateParts = dt_exp_rent.split("/");

// month is 0-based, that's why we need dataParts[1] - 1
var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 

dateObject.setDate(dateObject.getDate() + value);


$('input[name="current_exp_dt'+domElement.attr('patelUnPrc')+'"]').val(formatDate(dateObject));	
			if(dt_exp_rent==''){
		/*		alert('First filled start  date.');
			$('input[name="dt_ld"]').focus();
			$('input[name="dt_ld"]').addClass('error');
			$('input[name="dt_ld"]').val('');*/
			$('input[name="current_exp_dt'+domElement.attr('patelUnPrc')+'"]').val('');
			$('input[name="extend_days'+domElement.attr('patelUnPrc')+'"]').val('');
			$(this).val('');
			exit(0);
			}
		 if(dt_exp_rent!==''){
				
		//$(function() {
		
	        // var currentDate = new Date();
		     $(".datepicker").datepicker({
		      yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      	
				  onSelect: function(dateText, instance) {	
					 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
				
					$('input[name="current_exp_dt'+domElement.attr('patelUnPrc')+'"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));	
					}		
								
		    });
		
	
		 //currentDate.setDate(dt_install.getDate()+value);
	 	//$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val(setDate,currentDate)
		//});
				
				
			}//
			
			
			
		
			
		}//
	

}


function showRowBasedonStatus(event)
{
	//debugger;
	  var domElement =$(event.target || event.srcElement);
     var className= className + domElement.attr('patelUnPrc');
	 var value = $('select[name="prod_status' + domElement.attr('patelUnPrc') + '"]').val();


	if(value=='Refill'||value=='Refill_and_Extend')
		{
		
		
			$('[name="dt_return_str'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="rmk'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="extension_status'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="renew_sr_no'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('select[name="cr_pay_mode'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="cr_cheque_no'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="un_prc'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="cylndr_fill_mt'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="others'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('select[name="id_tax1' + domElement.attr('patelUnPrc') + '"]').hide();
			$('select[name="id_tax2' + domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="cr_cheque_no'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="tax_val1'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="tax_val2'+ domElement.attr('patelUnPrc') + '"]').hide();
			$('[name="buyback'+ domElement.attr('patelUnPrc') + '"]').hide();
			
			 
				
		}
	else{
		
		$('[name="dt_return_str'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="rmk'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="extension_status'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="renew_sr_no'+ domElement.attr('patelUnPrc') + '"]').show();
			$('select[name="cr_pay_mode'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="cr_cheque_no'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="un_prc'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="cylndr_fill_mt'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="others'+ domElement.attr('patelUnPrc') + '"]').show();
			$('select[name="id_tax1' + domElement.attr('patelUnPrc') + '"]').show();
			$('select[name="id_tax2' + domElement.attr('patelUnPrc') + '"]').show();
			$('[name="cr_cheque_no'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="tax_val1'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="tax_val2'+ domElement.attr('patelUnPrc') + '"]').show();
			$('[name="buyback'+ domElement.attr('patelUnPrc') + '"]').show();
			
	}	
		}	
			
			
			
			
			
//rental expire date autoselect	
function ShowRowColdtexp(event , Id)
{
	
	  var domElement =$(event.target || event.srcElement);
      var IdName= Id + domElement.attr('patelUnPrc');
	 var value =  parseInt($('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val());

	if(value==0)
		{
		
			$('#'+IdName).hide();
		}
	else if(value>0)
		{
			$('#'+IdName).show();
			var dt_install=$('input[name="dt_ld"]').val();
		/*	if(dt_install==''){
				alert('First filled start  date.');
			$('input[name="dt_ld"]').focus();
			$('input[name="dt_ld"]').addClass('error');
			$('input[name="dt_ld"]').val('');
			$(this).val('');
				

			//exit(0);
			}*/

var dateParts = dt_install.split("/");

// month is 0-based, that's why we need dataParts[1] - 1
var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 

dateObject.setDate(dateObject.getDate() + value);


$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val(formatDate(dateObject));	
			if(dt_install==''){
				alert('Please fill start  date.');
			$('input[name="dt_ld"]').focus();
			$('input[name="dt_ld"]').addClass('error');
			$('input[name="dt_ld"]').val('');
			$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val('');
			$('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val('');
			$(this).val('');
			exit(0);
			}
		 if(dt_install!==''){
				
		//$(function() {
		
	        // var currentDate = new Date();
		     $(".datepicker").datepicker({
		      yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      	
				  onSelect: function(dateText, instance) {	
					 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
				
					$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));	
					}		
								
		    });
		
	
		 //currentDate.setDate(dt_install.getDate()+value);
	 	//$('input[name="dt_exp_rent'+domElement.attr('patelUnPrc')+'"]').val(setDate,currentDate)
		//});
				
				
			}//
			
			
			
		
			
		}//
	

}			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	

function formatDate(date) {
  return [
    padTo2Digits(date.getDate()),
    padTo2Digits(date.getMonth() + 1),
    date.getFullYear(),
  ].join('/');
}

function padTo2Digits(num) {
  return num.toString().padStart(2, '0');
}


var yourImg = document.getElementById('imagee');
if(yourImg && yourImg.style) {
    yourImg.style.height = '70px';
    yourImg.style.width = '70px';
 

 }