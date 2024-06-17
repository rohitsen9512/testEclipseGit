//

function ControlCreateInvoice(action, displayContent, createDetails, formName, servletName,id,prodstatus) {
	
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
			
				$('#createinvoicelineitem').hide();
				$('#' + createDetails).hide();
				$('#' + displayContent).show();
				if(id=='CreateInvoiceForDisplaytd'){
				DisplayCreateInvoice(servletName, displayContent, createDetails, '', 'CreateInvoiceForDisplaytd');
				window.location = $('.createLead_event').attr('href');
				}else{
				
				DisplayEditInvoice(servletName, displayContent, createDetails, '',  'EditInvoiceForDisplaytd');
				window.location = $('.editInvoice_event').attr('href');
				}
			
				
				}
	else if(action =='Edit')
	{
		EditCreateInvoice(action , displayContent , createDetails , formName , servletName,id,prodstatus);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
	}
		
		else if(action =='Editforupdate')
	{
		EditforUpdateinvoice(action , displayContent , createDetails , formName , servletName,id,prodstatus);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
	}		 
	
		if (action == 'Update') {
				debugger
			
				$('input[name="acion"]').val('Update');
				
					
				
				t = false;
				//t = true;
			    t = ValidationForm(formName);
				
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
						alert("Update Invoice Sucessfully !!!!!");
						window.location = $('.editInvoice_event').attr('href');
					}
					else if (r.data == 2) {
						//alert("Lead Tag Sucessfully !!!!!");
						bootbox.alert("This Invoice Number Already Exist!!!");
						window.location = $('.editInvoice_event').attr('href');
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
	
	
	if (action == 'Save') {
				debugger
			
				$('input[name="acion"]').val('Save');
				
					
				
				t = false;
				//t = true;
			   t = ValidationForm(formName);
				
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
						alert("Create Invoice Sucessfully !!!!!");
						window.location = $('.createInvoice_event').attr('href');
					}
					else if (r.data == 2) {
						//alert("Lead Tag Sucessfully !!!!!");
						bootbox.alert("This Invoice Number Already Exist!!!");
						window.location = $('.createInvoice_event').attr('href');
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

//display for create inv

function DisplayCreateInvoice(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			/*var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();*/
			var searchWord = $('input[name="searchWord"]').val();
			let count=0;
         
	          $.post(servletName, { action: 'Display', id_lead_m: lead_no,searchWord:searchWord}, function(r) {
				var list
				 // if(state=='Inprogress'){
				list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Create Date</center></strong></td>' +
					'<td><strong><center>Tag Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
				
					 '</tr></thead><tbody>';
		          
				
			         if (r.data.length > 0) {
				
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						
						
					       list = list + '<tr>' +
						 
                          '<td><center>' + ++count + '</center></td>' +
                          '<td><center><a class="alertlink" href="#" onclick="ControlCreateInvoice(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_CreateInvoiceLead \','+params.id_lead_m+',\''+ params.prod_status +'\')">' + params.lead_no + '</a></center></td>' +
	                        '<td><center>' + params.tagdt + '</center></td>' +
							'<td><center>' + params.tagdt + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>' +
							
							
							
						'</tr>';
            
	                 }
					 
					}
				
				
				else {
					
					list += '<tr><td colspan="7"><strong><center>No data found.</center></strong></td></tr>';
					$('#' + DisplayTableClass).html('</tbody>' + list);
				
				}
				
				$('#' + DisplayTableClass).html('</tbody>' + list);
				getButtonsForListView('CreateInvoiceForDisplaytd','inv_List');
				
			}, 'json');
			
	
		}
	});
}

function EditCreateInvoice(action, displayContent, createDetails,formName, servletName,id,prodstatus) {
	SessionCheck(function(ses) {
		if (ses) {
			$('input[name="leadCrCheck"]').val('1');
			  var state =  $('input[name="state"]').val();
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createinvoicelineitem').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			
					$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=inv_created_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
								
							
						}
					}
				
				
					$.post('O_CreateInvoiceLead', { action: 'CreateInvoiceNo' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('input[name=inv_no ]').val(r.data[0].inv_no);
								
							
						}
					}	
					
			debugger;
			$.post(servletName, { action: 'Edit', id: id,prodstatus:prodstatus}, function(r) {
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
					
					
						
					if(r.data[0].prod_status=='Refill'){
						debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}
					
				$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
	        	     $('input[name="id"]').val(id);

					$('input[name="dt_ld"]').val(r.data[0].dtld);
					$('input[name="dt_po"]').val(r.data[0].dtpo);
					$('input[name="no_po"]').val(r.data[0].po_no);
					
					
					
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
						
					
					/*  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {*/
								
					SubDropDownDataDisplaybydesingEmp(r.data[0].id_design_tagemp,'EmpdataforField', 'M_Emp_User',function(status) {
								if (status) {		
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
                             
						        	debugger;
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
					debugger;
					dispalyModifyLineInvItemLead(r);
					//dispalyModifyLineCrItemLead(r);
					}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
			
			
			}, 'json');
			
			}, 'json');
		
			
			
		
	}
	});
}
		


//display for create inv

function DisplayEditInvoice(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			/*var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();*/
			
			var searchWord = $('input[name="searchWord"]').val();
			let count=0;
         
	          $.post(servletName, { action: 'DisplayforEdit', id_lead_m: lead_no, searchWord:searchWord}, function(r) {
				var list
				 // if(state=='Inprogress'){
				list = '<thead><tr class="new">' +
					'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Create Date</center></strong></td>' +
					'<td><strong><center>Invoice No.</center></strong></td>' +
					'<td><strong><center>Invoice Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
				
					 '</tr></thead><tbody>';
		          
				
			         if (r.data.length > 0) {
				
				console.log(r.data[i])
				
					for (var i = 0; i < r.data.length; i++) {
						
						params = r.data[i];
						
						
					       list = list + '<tr>' +
						 
                           '<td><center>' + ++count + '</center></td>' +
                          '<td><center><a class="alertlink" href="#" onclick="ControlCreateInvoice(\'Editforupdate\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_CreateInvoiceLead \','+params.id_lead_m+',\''+ params.prod_status +'\')">' + params.lead_no + '</a></center></td>' +
	                        '<td><center>' + params.dtld + '</center></td>' +
							'<td><center>' + params.inv_no + '</center></td>' +
							'<td><center>' + params.dtinv + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>'+
							'<td><center>' + params.nm_subl + '</center></td>'+
							'<td><center>' + params.nm_pstn + '</center></td>' +
							
							
							
						'</tr>';
            
	                 }
					 
					}
				
				
				else {
					
					list += '<tr><td colspan="7"><strong><center>No data found.</center></strong></td></tr>';
					$('#' + DisplayTableClass).html('</tbody>' + list);
				
				}
				
				$('#' + DisplayTableClass).html('</tbody>' + list);
				getButtonsForListView('EditInvoiceForDisplaytd','inv_List');
				
			}, 'json');
			
	
		}
	});
}

function EditforUpdateinvoice(action, displayContent, createDetails,formName, servletName,id,prodstatus) {
	SessionCheck(function(ses) {
		if (ses) {
			console.log(id);
			$('input[name="leadCrCheck"]').val('1');
		debugger;
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			
					$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=edit_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
								
							
						}
					}
				
		
			$.post(servletName, { action: 'Editforupdate', id: id,prodstatus:prodstatus}, function(r) {
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
					  
			
			        if(r.data[0].prod_status=='Refill'){
						debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}
					
			
			
			               tot= parseFloat(r.data[0].tot_inv_amt)+ parseFloat(r.data[0].refund_amt)
					
					
					$('#grandtotinword').text(number2text(tot));
	        	     $('input[name="id"]').val(id);
                      $('input[name="tot"]').val(tot);
					$('input[name="dt_ld"]').val(r.data[0].dtld);
					$('input[name="dt_po"]').val(r.data[0].dtpo);
					$('input[name="dt_inv"]').val(r.data[0].dtinv);
					$('input[name="id_fincance"]').val(r.data[0].id_fincance);
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
						
					
					  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {
								
					/*SubDropDownDataDisplayforemp(r.data[0].id_design_tagemp,'EmpdataforField', 'M_Emp_User', r.data[0].id_sloc, function(status) {
								if (status) {
									*/
					SubDropDownDataDisplaybydesingEmp(r.data[0].id_design_tagemp,'EmpdataforField', 'M_Emp_User',function(status) {
								if (status) {			
					            $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        	
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   $('select[name=id_design_tagemp] option[value=' + r.data[0].id_design_tagemp + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                    		
							   $('select[name=tag_to_emp] option[value=' + r.data[0].tag_to_emp + ']').attr('selected', true);
												
															
										}
					             });
					         }
					     });
					  }
					});
					debugger;
					//$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					dispalyModifyLineInvItemLead(r);
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
function dispalyModifyLineInvItemLead(r) {
	if (r.data) {
		
		//if(r.data[0].typ_service=='Rental'){
				
				console.log(r.data[0].prod_status);
		if(r.data[0].prod_status=='Refill'||r.data[0].prod_status=='Refill_and_Extend'){
				
								var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Product Status<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong ><center>Serial No<a href=#></a></center></strong></td>' +
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
			
			
			//'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
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
	
		
		for (var i = 0; i < r.data.length; i++) {
			console.log(r.data);
			var typcylinder='';
			if(r.data[i].typ_service=='Rental'){
				
				
					if(r.data[i].prod_status=='Refill'){
						
									list += '<tr>' +
					'<td><center>'+r.data[i].prod_status+'</center></td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				//for now'<td><center>'+r.data[i].prev_sr_no+'</center></td>'+
				'<td><center><select style="width: 170px !important;" id="serialnoDivForlead' + i + '" name="sr_no_refill' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				
					'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly></center></td>' +
			
				
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
					
					    prodstatus='Refill and Extend';
					list += '<tr>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"   class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center>'+r.data[i].prod_status+'</center></td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				//for now '<td><center>'+r.data[i].prev_sr_no+'</center></td>'+
				'<td><center><select style="width: 170px !important;" id="serialnoDivForlead' + i + '" name="sr_no_refill' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
				
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" style="width:140">'+
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				//'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" ></center></td>' +
			
				
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
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + ' "onChange="calculateTotRefillead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '"   value="0.00" onChange="calculateTotRefillead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			
				'<td><center>'+r.data[i].typ_service+'</center></td>'+
				
	            '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].rental_day + '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')" ></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control datepicker" ></center></td>' +
'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +			     
 '<input type="hidden" name="id_prod' + i + '" value="' + r.data[i].nm_prod + '">' +
				   '<input type="hidden" name="sr_no' + i + '" value="' + r.data[i].sr_no + '">' +
					
					'<input type="hidden" name="typ_service' + i + '" value="' + r.data[i].typ_service + '">' +
					'<input type="hidden" name="un_prc' + i + '" value="0.00">' +
					
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				 '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
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
				
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].hsn_cd + '"  name="hsn_cd' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" readonly="readonly" style="width:80">'+
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" readonly="readonly" style="width:80">'+
				'<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '"  value="' + r.data[i].typ_cylinder + '"  patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		        '<option value="B_type_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46.7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="4.5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				//'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
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
				'<input type="hidden" name="sr_no_refill' + i + '" value="">' +
				'<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
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
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" readonly="readonly" style="width:80">'+
				'<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '"  value="' + r.data[i].typ_cylinder + '"  patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		        '<option value="B_type_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46.7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="4.5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
			//	'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].hsn_cd + '"  name="hsn_cd' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid="" readonly="readonly" style="width:80">'+
					//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].sr_no + '"  name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  readonly="readonly"   style="width:80">'+
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].mfr + '"  name="mfr' + i + '" patelUnPrc="' + i + '" readonly="readonly"  class="form-control"></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				//'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
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
			    '<input type="hidden" name="sr_no_refill' + i + '" value="">' +
	            '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
				'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
			}
			}
			else if(r.data[i].typ_service=='Refill'){
			
			if(r.data[i].prod_status=='Fill'){	
			
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
                 '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;display:none;" value="' +'0'+ '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')"></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="" style="width: 100px;text-align: right;display:none;" class="form-control datepicker" ></center></td>' +
			     '<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				
			    '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">' +
               '<input type="hidden" name="prev_sr_no' + i + '" value="">' +
				'<input type="hidden" name="prev_lead_no' + i + '" value="">'+ 
			     '<input type="hidden" name="refill_id_lead' + i + '" value="">'+ 
'<input type="hidden" name="sr_no_refill' + i + '" value="">' +
				'</tr>';
				}
		}
		}
		$('input[name="itemCount"]').val(i);
		$('input[name="grand_tot1"]').val('0.00');
		$('#invoiceDetails1').html(list);
		
		
		
		
		
				
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
										 if (key == 'typ_cylinder') {
												debugger;
												//ssconsole.log($('select[name=typ_cylinder' + i + '] option[value='+r.data[i][key]+']').prop('selected', true));
												$('select[name=typ_cylinder' + i + '] option[value="'+r.data[i][key]+'"]').prop('selected', true);
											} 
											/* if (key == 'sr_no') {
											
												$('select[name=sr_no_refill' + i + '] option[value='+r.data[i][key]+']').prop('selected', true);
											}	*/
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


//Auto Select Group,Sub-Group,Asset Type
function changeEventHandlerprodd(event) {
	var domElement =$(event.target || event.srcElement);
	
    var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
   
//var idloc = $('select[name="id_loc"]').val();
var idsloc = $('select[name="id_sloc"]').val();
		if(idsloc==''){
			  if(idloc==''){
				  alert("'First Select State  '");
		 /*  $('#locDataForLead').focus();
		     $('#locDataForLead').val('');
		     $('#locDataForLead').addClass('error');
			*/
			}
			else{
				
				  alert("'First Select city  '");
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

//Checking Invoice Date Based on PO date
function CheckContraDate() {
	var dt_inv = $('input[name="dt_inv"]').val();
	var dt_inv1 = $('input[name="dt_inv"]').val();
	var dt_po = $('input[name="dt_po"]').val();
	var temp_strt = dt_inv.split("/");
	var temp_dt_strt = new Date(temp_strt[2],
		temp_strt[1] - 1, temp_strt[0]);
	var temp_end = dt_po.split("/");
	var temp_dt_end = new Date(temp_end[2],
		temp_end[1] - 1, temp_end[0]);
	dt_inv = $.datepicker.formatDate(
		'yy-mm-dd', temp_dt_strt);
	dt_po = $.datepicker.formatDate('yy-mm-dd',
		temp_dt_end);
	if (dt_inv < dt_po) {
		alert("'INVOICE DATE MUST BE GREATER OR EQUAL TO PO DATE. '");
		$('#dt_inv').focus();
		$('#dt_inv').val('');
		$('#dt_inv').addClass('error');
	}
	else {
		$('#dt_inv').removeClass('error');
	}
}