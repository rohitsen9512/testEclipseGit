$(function() {
	$("#menu").menu();
});

//Main Function For Save,Update,Cancel lead
 /*  var idsloc='';
    var idloc='';*/
function ControlLead(action, displayContent, createDetails, formName, servletName) {
	debugger;
	SessionCheck(function(ses) {
		if (ses) {
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			if (action == 'Create') {
				
				$('.readbaledata').each(function() {
					$(this).removeAttr('readonly', 'readonly');
					if ($(this).is("select")) {
						$(this).attr("disabled", false);
					}
				});
				
				$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				$('button[name="create btn"]').addClass('hideButton');
				$('button[name="delete"]').addClass('hideButton');
				$('input[name="action"]').val('Save');
				$('input[name="id"]').val("0");
				$('#' + formName)[0].reset();
				$('#' + displayContent).hide();
				$('#' + createDetails).show();
				$('#createLineitemLead').show();
				
				//
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
								if (status) {
									
									//SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								//if (status) {
									 	//dropdown acc to city
								//	SubDropDownDataDisplayforemp('', 'EmpdataforCordinator', 'M_Emp_User',r.data[0].id_sloc, function(status) {
							
								//if (status) {
										SubDropDownDataDisplaybydesingEmp(r.data[0].id_design, 'EmpdataforCordinator', 'M_Emp_User' , function(status) {
							
								if (status) {
									//console.log(r.data[0].id_loc);
													$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
													$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
													
													//$('select[name=id_design] option[value=' + r.data[0].id_design + ']').attr('selected', true);
														$('input[name="gstin_no"]').val(r.data[0].gstin_no_city);	
					                                      $('input[name="dl_no"]').val(r.data[0].dl_no_city);	
                                                     //  $('input[name="to_asign_cordi"]').val(r.data[0].id_emp_user);
                                                   $('select[name=id_design] option[value=' + r.data[0].id_design + ']').attr('selected', true);			
													$('select[name=to_asign_cordi] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
													$('select[name=created_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
										  }
							          });
                                          /* }
							         });
								*/
								}
							});
							
						}
					}
				}, 'json');
			}
			debugger;
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
			debugger;
				if(servletName=='M_Exchange')
				{
					window.location = $('.ExchangeProduct_event').attr('href');
					$('#createLineitemLead').hide();
					$('#' + createDetails).hide();
					$('#' + displayContent).show();
					DisplayLead1(servletName, displayContent, createDetails, '', 'LeadForDisplay');					
				}
				else{
					
				
				$('#createLineitemLead').hide();
				$('#' + createDetails).hide();
				$('#' + displayContent).show();
				DisplayLead(servletName, displayContent, createDetails, '', 'LeadForDisplay');
				window.location = $('.createLead_event').attr('href');
				}
				}
				
		  if(action == 'Save' || action == 'Update') {
				var Src=$('input[name="nm_src"]').val();
				if (Src != 'NA') {
					$('.srcCommonClass1').removeAttr('data-valid')
					}
				if (action == 'Save') {
				
					$('input[name="action"]').val('Save');
					
					
					
				}
				if(action == 'Update')  {
					
				  
					$('input[name="action"]').val('Update');
					
				}
				
				/*if(action == 'Approve')  {
					 	
				
				$('input[name="action"]').val('Approve');
				
				}
				*/
				if(servletName=='M_Exchange')
				{
					DataInsertUpdateForLead1("Cancel", displayContent, createDetails, formName, servletName);
				}
				else
				{
					DataInsertUpdateForLead("Cancel", displayContent, createDetails, formName, servletName);
				}
				
			}
		}
	});
}

//For Save & Update Lead
function DataInsertUpdateForLead(action, displayContent, createDetails, formName, servletName) {
	SessionCheck(function(ses) {
		if (ses) {
			//debugger;
			t = false;
            //t=true;
			t = ValidationForm(formName);
			console.log(formName);
	        var Src = $('input[name="nm_src"]').val();
			/*	if(t){
					
				
				if (Src != 'NA') {
					('.').removeAttr('data-valid','mand')
					//t = ShowRowColumnValidation('srcCommonClass1');
				}
				}*/
				
			if (t) {
				$('.led').attr('disabled', 'disabled');
				/*$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				*/
				 $('.form-control').removeAttr('disabled');
				  /* $('#locDataForLead').removeAttr('disabled');
					$('#slocDataForLead').removeAttr('disabled');
					$('#gstin_no').removeAttr('disabled');
					$('#dl_no').removeAttr('disabled');
					$('#empdataforCreateLd').removeAttr('disabled');*/
				var x = $('#' + formName).serialize();
				var itemCount=$('input[name="itemCount"]').val();
			
			
			var t=1;
			for(var i=0;i<itemCount;i++)
			{
				debugger;
				var id_prod=$('input[name="id_prod'+i+'"]').val();
				if(id_prod != '' )	
				{
					var sr_no=$('select[name="sr_no'+i+'"]').val();
	                var id_tax1=$('select[name="id_tax1'+i+'"]').val();
					var typ_service=$('select[name="typ_service'+i+'"]').val();
					var rental_day=$('input[name="rental_day'+i+'"]').val();
					var typ_cylinder=$('input[name="typ_cylinder'+i+'"]').val();
					
				 if(typ_service=='Rental'){
							
							
					if(sr_no == '' ||sr_no == undefined)
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.led').removeAttr('disabled');
						$('select[name="sr_no'+i+'"]').addClass('error');	
						
						
						exit(0);
						
					}if(id_tax1 == ''||id_tax1 == undefined)
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.led').removeAttr('disabled');
						$('select[name="id_tax1'+i+'"]').addClass('error');	
						
						
						exit(0);
						
					}if(rental_day == '' ||rental_day == undefined ){
								t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.led').removeAttr('disabled');
						$('select[name="rental_day'+i+'"]').addClass('error');	
						
						exit(0);	
							}
						}
					
					else if(typ_service=='Sale'){
							if(sr_no == '' ||sr_no == undefined)
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.led').removeAttr('disabled');
						$('select[name="sr_no'+i+'"]').addClass('error');	
						
						
						exit(0);
						
					}if(id_tax1 == ''||id_tax1 == undefined)
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.led').removeAttr('disabled');
						$('select[name="id_tax1'+i+'"]').addClass('error');	
						
						
						exit(0);
						
					}
					
						
						}
				   else if(typ_service=='Refill'){
							
					if(id_tax1 == ''||id_tax1 == undefined)
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.led').removeAttr('disabled');
						$('select[name="id_tax1'+i+'"]').addClass('error');	
						
						
						exit(0);
						
					}if(typ_cylinder == ''||typ_cylinder == undefined)
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						$('.led').removeAttr('disabled');
						$('select[name="typ_cylinder'+i+'"]').addClass('error');	
						
						
						exit(0);
						
					}
						
						
						}
					else
					{
						$('select[name="sr_no'+i+'"]').removeClass('error');
						
						
						$('select[name="dt_exp_rent'+i+'"]').removeClass('error');
						
					}
				}	
				
			}
			
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
						
						if(servletName=='M_Exchange'){
							alert("Saved Successfully !!!!!");
							window.location = $('.ExchangeProduct_event').attr('href');
						}
						else{
							alert("Lead Create Successfully !!!!!");
							window.location = $('.createLead_event').attr('href');
							
						}
						
						
						
						//window.location = $('.createLead_event').attr('href');
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
	});
	}


function DataInsertUpdateForLead1(action, displayContent, createDetails, formName, servletName) {
	SessionCheck(function(ses) {
		if (ses) {
			debugger;
			t = false;
			t = ValidationForm(formName);
	        var Src = $('input[name="nm_src"]').val();
			/*	if(t){
					
				
				if (Src != 'NA') {
					('.').removeAttr('data-valid','mand')
					//t = ShowRowColumnValidation('srcCommonClass1');
				}
				}*/
				
			if (t) {
				$('.led').attr('disabled', 'disabled');
				/*$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				*/
				 $('.form-control').removeAttr('disabled');
				  /* $('#locDataForLead').removeAttr('disabled');
					$('#slocDataForLead').removeAttr('disabled');
					$('#gstin_no').removeAttr('disabled');
					$('#dl_no').removeAttr('disabled');
					$('#empdataforCreateLd').removeAttr('disabled');*/
				var x = $('#' + formName).serialize();
				var itemCount=$('input[name="itemCount"]').val();
			
			
			var t=1;
		/*	for(var i=0;i<itemCount;i++)
			{
				var id_prod=$('input[name="id_prod'+i+'"]').val();
				if(id_prod != '' )	
				{
					var id_grp=$('select[name="id_grp'+i+'"]').val();
					var id_sgrp=$('select[name="id_sgrp'+i+'"]').val();
					if(id_grp == '' ||  id_grp == undefined || id_sgrp == '' ||  id_sgrp == undefined )
					{
						
						t=0;
						bootbox.alert("Make sure you have filled all the mandatory fields !!");
						$('.inv').removeAttr('disabled');
						$('select[name="id_grp'+i+'"]').addClass('error');
						$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
					else
					{
						$('select[name="id_grp'+i+'"]').removeClass('error');
						$('select[name="id_sgrp'+i+'"]').removeClass('error');
					}
				}	
				
			}*/
			if($('input[name="id_prod0"]').val() == '' )	
				{
					t=0;
						bootbox.alert("Make sure you have filled all the mandatory fields !!");
						$('.led').removeAttr('disabled');
					}
			if(t==1)
			{
				debugger;
				$.post(servletName, x, function(r) {
					if (r.data) {
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
							alert("Saved Successfully !!!!!");
							window.location = $('.ExchangeProduct_event').attr('href');
							//$('.led').removeAttr('disabled');
						//window.location = $('.createLead_event').attr('href');
					};
				}, 'json');
			}
			}
		}
	});
	}

//For Modify Lead
function EditLead(servletName, displayContent, createDetails, id,butType,product_status) {
	SessionCheck(function(ses) {
		if (ses) {
			
				
			$('input[name="leadCheck"]').val('1');
			
			    if(butType=='modi'){
					$('#locDataForLead').attr('disabled', 'disabled');
					$('#slocDataForLead').attr('disabled', 'disabled');
					$('#gstin_no').attr('disabled', 'disabled');
					 $('select[name="created_by"]').removeAttr('disabled');
				    
					

			
			$('button[name="update"]').removeClass('hideButton');
			}
			
			
	        $('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemLead').show();
			$('.hideled').hide();
			
			var t = 0;
			var key1;
		
			
			
		$.post(servletName, { action: 'Edit', id: id ,prodstatus:product_status}, function(r) {
				if (r.data) {
					console.log(r.data);
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							/*if (key == 'sr_no') {
								$('select[name=sr_no] option[value=' + r.data[0].sr_no + ']').attr('selected', true);
							}*/
						}
					}
					
					if(r.data[0].prod_status=='Refill'){
						debugger;
					$('.hideshow').hide();
				    $('#pay_mode_rfd').hide();
					 $('#cheque_no_rfd').hide();
					 $('#refund').hide();
						
							
					}
				    $('input[name="action"]').val("Update");
					$('input[name="id"]').val(r.data[0].id_lead_m);
					$('input[name="id_lead_m"]').val(r.data[0].id_lead_m);
						
					$('input[name="tot"]').val(r.data[0].tot);
					$('input[name="dt_ld"]').val(r.data[0].dtlead);
					$('input[name="dt_billing"]').val(r.data[0].dtbil);
			
					 $('input[name="id_fincance_lead"]').val(r.data[0].id_fincance_lead);
				 $('input[name="lead_no"]').val(r.data[0].lead_no);
				 $('input[name="ld_time"]').val(r.data[0].ld_time);
				  $('input[name="po_date"]').val(r.data[0].dtpo);
			
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							
					
					//  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
							//	if (status) {
				//	SubDropDownDataDisplayforemp('', 'EmpdataforCordinator', 'M_Emp_User',r.data[0].id_sloc, function(status) {
								//if (status) {	
				SubDropDownDataDisplaybydesingEmp(r.data[0].id_design, 'EmpdataforCordinator', 'M_Emp_User' , function(status) {
							{
								if (status) 			
						         debugger;  
                            $('select[name=id_src] option[value=' + r.data[0].id_src + ']').attr('selected', true);
							
                          $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);    
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   //$('select[name=id_design] option[value=' + r.data[0].id_design + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                 $('select[name=id_design] option[value=' + r.data[0].id_design + ']').attr('selected', true);			            		
							   $('select[name=to_asign_cordi] option[value=' + r.data[0].to_asign_cordi + ']').attr('selected', true);
								$('select[name=created_by] option[value=' + r.data[0].created_by + ']').attr('selected', true);	
									$('input[name="cd_src"]').val(r.data[0].cd_src);
									$('input[name="po_no"]').val(r.data[0].po_no);			
															
									}
					             });
					         //}
					    //});
					  }
					});
			$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					
					
					
					dispalyLineItemLeadformodify(r);
					
					
					if(butType!='modi'){
				
			$('.form-control').attr('disabled', 'disabled');
			}
			 
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					//it is for modify and for create chekothersrc fxn is there
					
				}
			}, 'json');
		}
	});
}



function EditLead1(servletName, displayContent, createDetails, id,butType) {
	SessionCheck(function(ses) {
		if (ses) {
			
				
			$('input[name="leadCheck"]').val('1');
			
			    if(butType=='modi'){
					$('#locDataForLead').attr('disabled', 'disabled');
					$('#slocDataForLead').attr('disabled', 'disabled');
					$('#gstin_no').attr('disabled', 'disabled');
					$('#dl_no').attr('disabled', 'disabled');
				
					

			
			$('button[name="update"]').removeClass('hideButton');
			}
			
			
	        $('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemLead').hide();
			$('.hideled').hide();
			var t = 0;
			var key1;
		
			
		debugger;
		$.post(servletName, { action: 'Edit1', id: id }, function(r) {
				if (r.data) {
					console.log(r.data);
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							/*if (key == 'sr_no') {
								$('select[name=sr_no] option[value=' + r.data[0].sr_no + ']').attr('selected', true);
							}*/
						}
					}
					
				    $('input[name="action"]').val("Update");
					$('input[name="id"]').val(id);
					DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							
					
					//  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
							//	if (status) {
					SubDropDownDataDisplayforemp('', 'EmpdataforCordinator', 'M_Emp_User',r.data[0].id_sloc, function(status) {
								if (status) {		
						         debugger;      
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   //$('select[name=id_design] option[value=' + r.data[0].id_design + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                             		
							   $('select[name=to_asign_cordi] option[value=' + r.data[0].to_asign_cordi + ']').attr('selected', true);
								$('select[name=created_by] option[value=' + r.data[0].created_by + ']').attr('selected', true);	
									$('input[name="cd_src"]').val(r.data[0].cd_src);			
															
										}
					             });
					         //}
					    //});
					  }
					});
					
					
					if(butType!='modi'){
				
			$('.form-control').attr('disabled', 'disabled');
			}
			 
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					//it is for modify and for create chekothersrc fxn is there
					
				}
			}, 'json');
		}
	});
}





//Line items While Creating Invoice
function dispalyLineItemLead(action, callback) {
	if (action == 'New') {
		
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' +
			'<tr class="tableHeader info">' +
			'<td ><strong><center>Product/Gas-O2<a href=#></a></center></strong></td>' +
			
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Oxygen Gas<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Type Cylinder<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td style="display:none;"><strong><center>Qty<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Expire<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Amount<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			/*'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Expire<a href=#></a></center></strong></td>' +*/
			
			'<td ><strong><center>Total<a href=#></a></center></strong></td>' +
			'</tr>';
		for (var i = 0; i < 10; i++) {
			list += '<tr>' +
				' <td style="display:none;">' +
	              '<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				
				'<td><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '"  patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center><select style="width: 200px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				//'<td><input style="width: 120px !important;display:none;" type="text"  id="dynItemData' + i + '" name="id_product' + i + '" patelUnPrc="' + i + '"  id="id_product' + i + '"  class="form-control " onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		          '<option value="">Select</option>' +
                   '<option value="B_type_OXYGEN_CYLINDER _10_2_Ltr">B-TYPE OXYGEN CYLINDER 10.2 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46_7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10_2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10.2 LTR</option>'+
                  '<option value="4_5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
				//'<td><input style="width: 120px !important;" type="text"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control "></td>' +
				'<td><input style="width: 120px !important;" type="text" name="ds_product' + i + '" id ="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></td>' +
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;display:none;"  value="1" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\');"; class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" value="0"  id="in_stoc_qty' + i + '" style="width:100px;text-align: right;" class="form-control "  onChange="CheckInvoice(event)" readonly></center></td>' +
				'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid="" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\',\'un_prc\',\'in_stoc_qty\',\'serialnoDivForlead\',\'CylindertypeDivForLead\',\'ds_product\')" style="width:80">' +
		       //'<option value="">Select</option>' +
                
			     '<option value="Sale">Sale</option>' +
                  '<option value="Rental">Rental</option>'+
                   '<option value="Refill">Refill</option>'+
			     '</center></td>' +
                 '<td ><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '" patelUnPrc="' + i + '" value="" style="width: 60px;display:none"; text-align: right;"  class="form-control"  onchange="checkaval(event,\'rental_day\',\'dt_exp_rent\');ShowRowColdtexp(event,\'dt_exprent\');" onkeyup="Validatespacelineitemfield(event,\'rentalday\')" ></center></td>' +
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="" style="width: 100px;display:none";text-align: right;" class="form-control " readonly ></center></td>' +
				
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00"  id="un_prc' + i + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num" data-valid="" ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '"  value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="" ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="0.00" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" style="width: 120px;" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2" style="width: 120px;" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
				
				'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="sr_no_refill' + i +'" value="">' +
				'<input type="hidden" name="prod_status' + i +'" value="">' +
				
				'<input type="hidden" name="id_lead' + i +'" value="">' +
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
		list += '<tr>' +
				' <td style="display:none;">' +
	              '<input type="hidden" name="ConfigurableId' + i + '" value="No">' +
				
				'<td><input style="width: 120px !important;" type="text" id="dynItemData' + i + '" name="id_prod' + i + '"  patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><center><select style="width: 200px !important;" id="serialnoDivForlead' + i + '" name="sr_no' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				//'<td><input style="width: 120px !important;display:none;" type="text"  id="dynItemData' + i + '" name="id_product' + i + '" patelUnPrc="' + i + '"  id="id_product' + i + '"  class="form-control " onchange="changeEventHandlerprodd(event);"></td>' +
				//'<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		         '<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		           '<option value="">Select</option>' +
                   '<option value="B_type_OXYGEN_CYLINDER _10_2_Ltr">B-TYPE OXYGEN CYLINDER 10.2 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46_7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10_2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10.2 LTR</option>'+
                  '<option value="4_5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
				//'<td><input style="width: 120px !important;" type="text"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control "></td>' +
				'<td><input style="width: 120px !important;" type="text" name="ds_product' + i + '" id ="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control " readonly></td>' +
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;display:none;"  value="1" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\');"; class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" value="0"  id="in_stoc_qty' + i + '" style="width:100px;text-align: right;" class="form-control "  onChange="CheckInvoice(event)" readonly></center></td>' +
					'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid="" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\',\'un_prc\',\'in_stoc_qty\',\'serialnoDivForlead\',\'CylindertypeDivForLead\',\'ds_product\')" style="width:80">' +
		       //'<option value="">Select</option>' +
                
			     '<option value="Sale">Sale</option>' +
                  '<option value="Rental">Rental</option>'+
                   '<option value="Refill">Refill</option>'+
			     '</center></td>' +
                 '<td ><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '" patelUnPrc="' + i + '" value="" style="width: 60px;display:none"; text-align: right;"  class="form-control"  onchange="checkaval(event,\'rental_day\',\'dt_exp_rent\');ShowRowColdtexp(event,\'dt_exprent\');" onkeyup="Validatespacelineitemfield(event,\'rentalday\')" ></center></td>' +
				 '<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="" style="width: 100px;display:none";text-align: right;" class="form-control " readonly ></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00"  id="un_prc' + i + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num" data-valid="" ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '"  value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="" ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="0.00" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax1' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" class="form-control patelTax" style="width: 120px;" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 120px;" name="id_tax2' + i + '" value="" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" class="form-control patelTax2" style="width: 120px;" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="0.00" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +
			/*	'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid="" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\',\'un_prc\',\'in_stoc_qty\',\'serialnoDivForlead\',\'CylindertypeDivForLead\',\'ds_product\')" style="width:80">' +
		       //'<option value="">Select</option>' +
                
			     '<option value="Sale">Sale</option>' +
                  '<option value="Rental">Rental</option>'+
                   '<option value="Refill">Refill</option>'+
			     '</center></td>' +
                 '<td ><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '" patelUnPrc="' + i + '" value="" style="width: 60px;display:none"; text-align: right;"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')"></center></td>' +
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="" style="width: 100px;display:none";text-align: right;" class="form-control datepicker" ></center></td>' +*/
			/*	'<td><center><input type="text" name="tr_chrg' + i + '" patelUnPrc="' + i + '" value="0.0" style="width: 100px;text-align: right;" onChange="calculateTotlead(event,\'tr_chrg\')" class="form-control"></center></td>' +*/
				
				'<td><center><input type="text"  name="gr_tot' + i + '" value="0.0" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="sr_no_refill' + i +'" value="">' +
				'<input type="hidden" name="prod_status' + i +'" value="">' +
				
				'<input type="hidden" name="id_lead' + i +'" value="">' +
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

//Line Item While modifying Invoice
function dispalyLineItemLeadformodify(r) {
	
	if (r.data) {
	
		//if(r.data[0].typ_service=='Rental'||r.data[0].typ_service=='Refill'){
				
			if(r.data[0].prod_status=='Refill'||r.data[0].prod_status=='Refill_and_Extend'){
					var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Product status<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong ><center>Exchange Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td style="display:none;"><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
		
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
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
					//if(r.data[0].prod_status=='Product_on_Rental'||r.data[0].prod_status=='Fill'){
					var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Product<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Serial No<a href=#></a></center></strong></td>' +
				'<td ><strong><center>Type Cylinder<a href=#></a></center></strong></td>' +
			//'<td ><strong class ="hidecolumn"><center>Serial No<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Description<a href=#></a></center></strong></td>' +
			//'<td style="display:none;"><strong><center>Description<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
		     
			
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Rental Days<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Expire Date<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Discount<a href=#></a></center></strong></td>' +
			//'<td ><strong class="hidecolumn1"><center>Product Status<a href=#></a></center></strong></td>' +
		
			/*'<td ><strong><center>Refundable Deposite<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Transportation Charges<a href=#></a></center></strong></td>' +*/
			'<td ><strong><center>Total<a href=#></a></center></strong></td>' +
			'</tr>';	
						
					}
		
					
					//}
		
		for (var i = 0; i < r.data.length; i++) {
			console.log('ss');
		var typcylinder='';
			if(r.data[i].typ_service=='Rental'){
				
				if(r.data[i].prod_status=='Refill'){
					
				
							list += '<tr>' +
					'<td><center>'+r.data[i].prod_status+'</center></td>'+
				'<td><center>'+r.data[i].nm_prod+'</center></td>'+
				'<td><center>'+r.data[i].prev_sr_no+'</center></td>'+
				'<td><center><select style="width: 170px !important;" id="serialnoDivForlead' + i + '" name="sr_no_refill' + i + '" patelUnPrc="' + i + '"  class="form-control   " data-valid=""  style="width:80">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				
			
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="0.00" style="width: 60px;text-align: right;" onChange="calculateTotRefillead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly></center></td>' +
			
				
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
				/*'<td><select style="width: 180px;" class="form-control" name = "product_status'+ i + '" id="product_status'+ i + '"  patelUnPrc="' + i + '" value="'+r.data[i].product_status+'"  data-valid="mand" >'+
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
				 '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				'<input type="hidden" name="un_prc' + i + '" value="0">' +
				 '<input type="hidden" name="typ_cylinder' + i + '" value="">'+ 
			
			
				'</tr>';
			
					
					
					}if(r.data[i].prod_status=='Refill_and_Extend'){
						debugger;
					console.log(r.data[0].prod_status);
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
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control "  readonly></center></td>' +
			      '<input type="hidden" name="id_prod' + i + '" value="' + r.data[i].nm_prod + '">' +
				   '<input type="hidden" name="sr_no' + i + '" value="' + r.data[i].sr_no + '">' +
					
					'<input type="hidden" name="typ_service' + i + '" value="' + r.data[i].typ_service + '">' +
					'<input type="hidden" name="un_prc' + i + '" value="0.00">' +
					'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				 '<input type="hidden" name="prod_status' + i + '" value="' + r.data[i].prod_status + '">'+ 
				'<input type="hidden" name="count" value="' + i + '">' +
				'<input type="hidden" name="un_prc' + i + '" value="0">' +
			
				'</tr>';
				}
				if(r.data[i].prod_status=='Product_on_Rental'){	
					debugger;
					
			
						list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				
				'<td><center><select style="width: 120px !important;" id="serialnoDivForlead' + i + '"  name="sr_no' + i + '" patelUnPrc="' + i + '" value="'+r.data[i].sr_no+'" class="form-control " data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
				//'<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		        '<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		          '<option value="">Select</option>' +
                   '<option value="B_type_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46.7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="4.5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER"</option>'+
			     '</center></td>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].mfr + '"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control"></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control" readonly></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" value="' + r.data[i].in_stoc_qty + '" id="in_stoc_qty' + i + '" style="width:100px;text-align: right;" class="form-control "  onChange="CheckInvoice(event)" readonly></center></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;display:none;"  value="' + r.data[i].quantity + '" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\',\'un_prc\',\'in_stoc_qty\',\'serialnoDivForlead\',\'CylindertypeDivForLead\',\'ds_product\')"   >' +
                '<option value="Sale">Sale</option>' +
                  '<option value="Rental">Rental</option>'+
                   '<option value="Refill">Refill</option>'+
			   
				'</select>' +'</center></td>' +
                 '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].rental_day + '" patelUnPrc="' + i + '"  class="form-control"  onchange="checkaval(event,\'rental_day\',\'dt_exp_rent\');ShowRowColdtexp(event,\'dt_exprent\');" onkeyup="Validatespacelineitemfield(event,\'rentalday\')" ></center></td>' +
				
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control " readonly ></center></td>' +
				
				
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
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
				/*'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\')"  readonly >' +
                 '<option value="Sale" >Sale</option>' +
                 '<option value="Rental">Rental</option>'+		        

			   
				'</select>' +'</center></td>' +
                 '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].rental_day + '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')"></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].dt_exprent + '" style="width: 100px;text-align: right;" class="form-control datepicker" ></center></td>' +*/
			     '<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				  '<input type="hidden" name="sr_no_refill' + i + '" value="">' +
			    '<input type="hidden" name="prod_status' + i + '" value="">' +
               '<input type="hidden" name="typ_cylinder' + i + '" value="">'+ 
			
				'</tr>';
				}
			
				
			
			}
			else if(r.data[i].typ_service=='Sale'){
				
				
			
				if(r.data[i].prod_status=='Product_on_Sale'){
					
					list += '<tr>' +
					
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				
				'<td><center><select style="width: 200px !important;" id="serialnoDivForlead' + i + '"  name="sr_no' + i + '" value="'+r.data[i].sr_no+'" patelUnPrc="' + i + '"  class="form-control " data-valid=""  style="width:140">' +
				'<option value="">Select</option></select>' +
				'</center></td>' +
					'<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		        '<td><center><select style="width: 90px !important;display:none;" id="CylindertypeDivForLead' + i + '" name="typ_cylinder' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80">' +
		          '<option value="">Select</option>' +
                   '<option value="B_type_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="D_TYPE_OXYGEN CYLINDER 46.7 LTR">D-TYPE OXYGEN CYLINDER 46.7 LTR</option>'+
                  '<option value="B_type_Alu_OXYGEN_CYLINDER _10.2_Ltr">B-TYPE ALUMINIUM OXYGEN CYLINDER 10 LTR</option>'+
                  '<option value="4.5_Alu_Cylinder">4.5 ALUMINIUM CYLINDER</option>'+
			     '</center></td>' +
				//'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].mfr + '"  name="mfr' + i + '" patelUnPrc="' + i + '"  class="form-control"></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  class="form-control"readonly></td>' +
				
				//'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;text-align: right;display:none;"  value="' + r.data[i].quantity + '" class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num"></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
					'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\',\'un_prc\',\'in_stoc_qty\',\'serialnoDivForlead\',\'CylindertypeDivForLead\',\'ds_product\')">'+
	              '<option value="Sale">Sale</option>' +
                 '<option value="Rental">Rental</option>'+		 
                  '<option value="Refill">Refill</option>'+	
				'</select>' +'</center></td>' +
                 '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;display:none";text-align: right;" value="' +'0'+ '" patelUnPrc="' + i + '"  class="form-control" onchange="checkaval(event,\'rental_day\',\'dt_exp_rent\');ShowRowColdtexp(event,\'dt_exprent\');" onkeyup="Validatespacelineitemfield(event,\'rentalday\')" ></center></td>' +
				
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' +''+ '" style="width: 100px;display:none";text-align: right;" class="form-control " readonly ></center></td>' +
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')" class="patelUnPrc form-control" data-valid="num"  ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="" ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')" class="form-control" ></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" style="width:120px;" class="form-control patelTax" onChange="getTax2(event);">' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '"   style="width:120px;" class="form-control patelTax2" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" disabled="true">' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><input type="text"  name="buyback' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].buyback + '" onChange="calculateTotlead(event,\'buyback\')" style="width:60px;text-align: right;" class="form-control" ></center></td>' +

			/*	'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\')"  readonly >' +
	            '<option value="Sale" >Sale</option>' +
                '<option value="Rental">Rental</option>'+
               
				'</select>' +'</center></td>' +
                 '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;display:none";text-align: right;" value="' +'0'+ '" patelUnPrc="' + i + '"  class="form-control" onchange="ShowRowColdtexp(event,\'dt_exprent\')"></center></td>'+
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="' +''+ '" style="width: 100px;display:none";text-align: right;" class="form-control datepicker" ></center></td>' +*/
			
					'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				 '<input type="hidden" name="sr_no_refill' + i + '" value="">'+ 
			 '<input type="hidden" name="typ_cylinder' + i + '" value="">'+ 
			
			    '<input type="hidden" name="prod_status' + i + '" value="">'+ 
				'</tr>';
			 
				}
					
		
			
			}
			else if(r.data[i].typ_service=='Refill'){
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
				'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].typ_service + '" class="form-control" data-valid=""  style="width:80;display:none;" onchange="ShowRowColLead(event,\'rentalday\',\'dt_exprent\',\'un_prc\',\'in_stoc_qty\',\'serialnoDivForlead\',\'CylindertypeDivForLead\',\'ds_product\')">'+
                 '<option value="Sale">Sale</option>' +
                 '<option value="Rental">Rental</option>'+		 
                  '<option value="Refill">Refill</option>'+		        

			   
				'</select>' +'</center></td>' +
                 '<td><center><input type="text"  id="rentalday' + i + '" name="rental_day' + i + '"  style="width: 60px;text-align: right;display:none;" value="" patelUnPrc="' + i + '"  class="form-control" onchange="checkaval(event,\'rental_day\',\'dt_exp_rent\');ShowRowColdtexp(event,\'dt_exprent\');" onkeyup="Validatespacelineitemfield(event,\'rentalday\')" ></center></td>' +
				
				'<td><center><input type="text" id="dt_exprent' + i + '"  name="dt_exp_rent' + i + '" patelUnPrc="' + i + '" value="" style="width: 100px;text-align: right;display:none;" class="form-control datepicker" ></center></td>' +
			     '<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:100px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				  '<input type="hidden" name="sr_no_refill' + i + '" value="">' +
			    '<input type="hidden" name="prod_status' + i + '" value="">' +
				'</tr>';
				}
		}
		}
		
		$('input[name="itemCount"]').val(i);
		$('input[name="grand_tot1"]').val('0.00');
		$('#leadDetails1').html(list);
		
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
	console.log("kajol");
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

	/*	 if(r.data.length > 0)
		{
			 
		for(var k=0;k<r.data.length;k++)
			{
				(function(k){
            		setTimeout(function(){
	
	SubDropDownDataDisplayitemSrNo(r.data[k].id_prod,r.data[k].id_sloc,r.data[k].id_loc,'serialnoDivForlead'+k+'','S_Add_To_Stock',function (status){
		if(status)
			{
			
			}});
												
           		 }, 50*k);

         		})(k);
		
			}
		 }*/
			
		//Auto select Dropdown of lineitems
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
										
									for (var i = 0; i < r.data.length; i++) {
										
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + i + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + i + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
											if (key == 'typ_service') {
													debugger;
												$('select[name=typ_service' + i + '] option[value=' + r.data[i][key] + ']').prop('selected', true);
											}
											 if (key == 'sr_no') {
											
												$('select[name=sr_no' + i + '] option[value='+r.data[i][key]+']').prop('selected', true);
											} 
											 if (key == 'typ_cylinder') {
												debugger;
												//ssconsole.log($('select[name=typ_cylinder' + i + '] option[value='+r.data[i][key]+']').prop('selected', true));
												$('select[name=typ_cylinder' + i + '] option[value="'+r.data[i][key]+'"]').prop('selected', true);
											} 
									if (key == 'dt_exprent' && r.data[i][key] !='') {
							                       
                                              $('input[name="dt_exp_rent'+i+']').val(r.data[i][key].dt_exprent);	
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

//Deletion of Lead
function DeleteLead(servletName, displayContent, createDetails, id) {
	SessionCheck(function(ses) {
		if (ses) {
			bootbox.confirm("Are you sure?", function(result) {
				if (result) {
					$.post(servletName, { action: 'Delete', id: id }, function(r) {
						if (r.data == 1) {
							window.location = $('.createLead_event').attr('href');
							DisplayLead(servletName, displayContent, createDetails, '', 'leadForDisplaytd');
						}
						if (r.data == 2) {
							bootbox.confirm("If you delete this , then all aseet will be deleted related to this invoice.", function(result1) {
								if (result1) {
									$.post(servletName, { action: 'Delete', id: id, id_inv_m: '0' }, function(r) {
										if (r.data == 1) {
											window.location = $('.createLead_event').attr('href');
											DisplayLead(servletName, displayContent, createDetails, '', 'leadForDisplaytd');
										}
									}, 'json');
								}
							});
						}
					}, 'json');
				}
			});
		}
	});
}

//Display List View of led
function DisplayLead(servletName, displayContent, createDetails, lead_no, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			//var dt_frm = $('#dt_frm').val();
			//var dt_to = $('#dt_to').val();
			var searchWord = $('input[name="searchWord"]').val();
			$.post(servletName, { action: 'Display', lead_no: lead_no,searchWord :searchWord }, function(r) {
				var list = '<thead><tr class="new">' +
				'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Lead Date</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Modify</center></strong></td>' +
					//'<td><strong><center>Assign Lead</center></strong></td>' +
					'</tr></thead><tbody>';
				if (r.data.length > 0) {
					console.log(r.data);
				let num=0;
					for (var i = 0; i < r.data.length; i++) {
					
						params = r.data[i];
							
						list = list + '<tr>' +
							//'<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',' + params.id_lead_m + ')">' + params.lead_no + '</a></center></td>' +
							'<td><center>'+ ++num +'</center></td>' +
							'<td><center>' + params.lead_no + '</center></td>' +
							'<td><center>' + params.dtlead + '</center></td>' +
							'<td><center>' + params.nm_pstn + '</center></td>' +
							
							 '<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'modi\',\'' + params.prod_status + '\');">' +'Modify'+ '</a></center></td>' +
						    /* '<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'addstock\');">' +'Assign Lead'+ '</a></center></td>' +*/
							'</tr>';
					}
				}
				else {
					list += '<tr><td colspan="5"><strong><center>No data found.</center></strong></td></tr>';
					$('#leadForDisplaytd').html('</tbody>' + list);
				}
				$('#leadForDisplaytd').html('</tbody>' + list);
				getButtonsForListView('leadForDisplaytd','Lead_List');
			}, 'json');
		}
	});
}

function DisplayLead2(servletName, displayContent, createDetails, lead_no, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			$.post(servletName, { action: 'Display1', lead_no: lead_no}, function(r) {
			let count=0;
				var list = '<thead><tr class="new">' +
				'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Product</center></strong></td>' +
					'<td><strong><center>Serial Number</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Service Status</center></strong></td>' +
					'<td><strong><center>Exchange</center></strong></td>' +
					//'<td><strong><center>Assign Lead</center></strong></td>' +
					'</tr></thead><tbody>';
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						list = list + '<tr>' +
						'<td><center>' + ++count + '</center></td>' +
							//'<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',' + params.id_lead_m + ')">' + params.lead_no + '</a></center></td>' +
							'<td><center>' + params.lead_no + '</center></td>' +
							'<td><center>' + params.nm_prod + '</center></td>' +
							'<td><center>' + params.sr_no + '</center></td>' +
							'<td><center>' + params.nm_pstn + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>' +
							'<td><center>' + params.nm_subl + '</center></td>' +
							'<td><center>' + params.typ_service + '</center></td>' +
							 '<td><center><a class="alertlink" href="#" onclick="EditLead1(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead + '\',\'modi\');">' +'Exchange'+ '</a></center></td>' +
						    /* '<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'addstock\');">' +'Assign Lead'+ '</a></center></td>' +*/
							'</tr>';
					}
				}
				else {
					list += '<tr><td colspan="5"><strong><center>No data found.</center></strong></td></tr>';
					$('#leadForDisplaytd').html('</tbody>' + list);
				}
				$('#leadForDisplaytd').html('</tbody>' + list);
				//getButtonsForListView('leadForDisplaytd','Lead_List');
			}, 'json');
		}
	});
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

//Auto Select Group,Sub-Group,Asset Type
function changeEventHandlerprodd(event) {
	
	var domElement =$(event.target || event.srcElement);
	
    var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
    var service = $('select[name="typ_service'+domElement.attr('patelUnPrc')+'"]').val();

           if(service=='Refill'){
	
	               $.post('M_Asset_Div',{action : 'GetProductdetails', id:nm_prod},function (r){
	 
			if(r.data)
				{
			
					
					console.log(r.data);
					console.log(r.data[0].mfr_assetdiv);
			
			$('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].mfr_assetdiv);			 
		    $('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].ds_assetdiv);
		   $('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].un_prc_assetdiv);
		   
		
				
				
					
				}
	 },'json');
	
}else{
	   console.log(nm_prod);
var idloc = $('select[name="id_loc"]').val();
var idsloc = $('select[name="id_sloc"]').val();
		if(idsloc==''){
			  if(idloc==''){
				  alert("'Please select the entity. '");
		  $('#locDataForLead').focus();
		     $('#locDataForLead').val('');
		     $('#locDataForLead').addClass('error');
			
			}
			else{
				
				  alert("'Please select the city.'");
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
					$('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val('0');
		          $('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val('0');
                 // $('select[name=id_sr'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
				
				}
				
				else{
					
					console.log(r.data);
					console.log(r.data[0].mfr_assetdiv);
			
			$('input[name="mfr' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].mfr_assetdiv);			 
		    $('input[name="ds_product' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].ds_assetdiv);
		   x= $('input[name="un_prc' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].un_prc_assetdiv);
		    $('input[name="in_stoc_qty' + domElement.attr('patelUnPrc') + '"]').val(r.data[0].tot_aval_qty);
				
		
          if(r.data[0].type_grp=='TYPE-CYLINDER') 
	        {     
                   debugger;
                 
               SubDropDownDataDisplayitemSrNo(r.data[0].id_assetdiv,idsloc,idloc,'serialnoDivForlead'+domElement.attr('patelUnPrc')+'',r.data[0].type_grp,'S_Add_To_Stock',function (status){
					//$('select[name=sr_no'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					});	


			 }else{
				    SubDropDownDataDisplayitemSrNo(r.data[0].id_assetdiv,idsloc,idloc,'serialnoDivForlead'+domElement.attr('patelUnPrc')+'',r.data[0].type_grp,'S_Add_To_Stock',function (status){
					//$('select[name=sr_no'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					});	

				
			}
				
				
					
				}
	 },'json');
		}
}//

} 
 }



function changeEventHandlercleardata(event) {
	
	var domElement =$(event.target || event.srcElement);
    if (domElement === "Backspace" || domElement === "Delete") {
        return false;

     alert("HH");
    }
}

//Auto Select Group,Sub-Group,Asset Type
function changeEventHandlercustomerhist() {
	
	
    var custm_cont_no = $('input[name="pstn_ct"]').val();
   

        
	
	               $.post('O_Lead',{action : 'GetCustomerRecordDetail', id:custm_cont_no},function (r){
	 
			if(r.data)
				{
			     debugger;
                    	

                    	
					
					  // $('select[name=id_sr'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
					$('input[name="alt_pstn_ct"]').val(r.data[0].alt_pstn_ct);
					$('input[name="nm_pstn"]').val(r.data[0].nm_pstn);
					$('input[name="att_name"]').val(r.data[0].att_name);
					$('input[name="pstn_email"]').val(r.data[0].pstn_email);
					$('input[name="address"]').val(r.data[0].address);
					$('input[name="nm_dr"]').val(r.data[0].nm_dr);
					$('input[name="dr_sp"]').val(r.data[0].dr_sp);
					$('input[name="nm_hsptl"]').val(r.data[0].nm_hsptl);
					$('input[name="ct_hsptl"]').val(r.data[0].ct_hsptl);
					$('input[name="add_hsptl"]').val(r.data[0].add_hsptl);
					
					$('input[name="gstin_no"]').val(r.data[0].gstin_no);
					$('input[name="dl_no"]').val(r.data[0].dl_no);
					$('input[name="ct_hsptl"]').val(r.data[0].ct_hsptl);
					$('input[name="add_hsptl"]').val(r.data[0].add_hsptl);
					
			
			
		
				
				
					
				}
	 },'json');
	
}




$(function() {
		$('.hideRowCol').hide();
		
				
		var currentDate = new Date();
		$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
		});
		$('input[name="dt_ld"]').datepicker("setDate", currentDate);
		$('input[name="dt_to"]').datepicker("setDate", currentDate);
		currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
		
	});
	jQuery("input#fileID").change(function() {
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
		}, 'json');
	});
	
	
	
//Approve lead	
	
/*function ControlAprvlead(action, displayContent, editDetails, formName, servletName,id) {
		//
    SessionCheck(function(ses) {
		if (ses) {
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="invoiceCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				$('#createLineitemLead').hide();
				$('#' + editDetails).hide();
				$('#' + displayContent).show();
				DisplayApproveLead(servletName, displayContent, editDetails, '', 'AprleadForDisplay');
				window.location = $('.aprvLead_event').attr('href');
				}
			else if(action =='Edit')
	{
		//
		EditAprvlead(action , displayContent , editDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+editDetails).show();
	}
			 
			else	if (action == 'Approve') {
					$('button[name="save1"]').removeClass('hideButton');
					$('input[name="action"]').val('Approve');
				
				t = false;
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
						alert("Lead Assign Succesfully !!!!!");
						window.location = $('.aprvLead_event').attr('href');
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
        */





//For Modify Invoice
/*function EditAprvlead(action, displayContent, editDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		
		if (ses) {
			$('input[name="invoiceCheck"]').val('1');
			

			

			$('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');
			
			
			$('#' + displayContent).hide();
			$('#' + editDetails).show();
			$('#createLineitemLead').show();
			$('.hideinv').hide();
			var t = 0;
			var key1;
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							$('select[name=approve_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
								
							
						}
					}
				
			
			$.post(servletName, { action: 'Edit', id: id }, function(r) {
				if (r.data) {
					
					console.log(r.data);
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
					
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							if (key == 'bd' && r.data[i][key] == 'Yes') {
								t = 1;
							}
						//	
							if (key == 'warranty_amc' && r.data[i][key] == 'A'||r.data[i][key] == 'W'||r.data[i][key] == 'O') {
								t = 1;
								key1= r.data[i][key];
								
							}
						}
					}
					//
		
				  	var shubhamcurr=parseFloat(r.data[0].tot);
					$('#grandtotinword').text(number2text(shubhamcurr));

				    $('input[name="action"]').val("Approve");
	        	
             
					$('input[name="id"]').val(id);
					$('input[name="dt_ld"]').val(r.data[0].dtlead);
					//$('input[name="approve_dt"]').val(r.data[0].approvedt);
					
			  
					
					 
				
				DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
						
					
					 // SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
							//	if (status) {
					SubDropDownDataDisplayforemp('', 'EmpdataforCordinator', 'M_Emp_User',r.data[0].id_sloc, function(status) {
								if (status) {		
					          $('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   //$('select[name=id_design] option[value=' + r.data[0].id_design + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                    		
							   $('select[name=to_asign_cordi] option[value=' + r.data[0].to_asign_cordi + ']').attr('selected', true);
												
															
										}
					             });
					        }
					     });
					  //}
					//});
							
					dispalyLineItemLeadformodify(r);
		            $('.form-control').attr('disabled', 'disabled');
                   // $('#empdataforAssingLd').removeAttr('disabled');
                   
                   }
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					
					//ShowRowColumn('Yes', 'hideRowCol');
					ShowRowColumn(key1, 'amcAddToStockCommonClass');
				}
				
			}, 'json');
			
			}, 'json');
		}
	});
}*/

	
	
	
//Display Approve lead
/*function DisplayApproveLead(servletName, displayContent, editDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
			$.post(servletName, { action: 'Display', lead_no: lead_no, dt_frm: dt_frm, dt_to: dt_to }, function(r) {
				var list = '<thead><tr class="new">' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Lead Date</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					
					'<td><strong><center>Assign Lead</center></strong></td>' +
					'</tr></thead><tbody>';
				if (r.data.length > 0) {
					
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						list = list + '<tr>' +
							//'<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',' + params.id_lead_m + ')">' + params.lead_no + '</a></center></td>' +
							'<td><center>' + params.lead_no + '</center></td>' +
							'<td><center>' + params.dtlead + '</center></td>' +
							'<td><center>' + params.nm_pstn + '</center></td>' +
							
							 '<td><center><a class="alertlink" href="#" onclick="ControlAprvlead(\'Edit\',\''+displayContent+'\',\''+editDetails+'\',\''+formName+'\',\'O_Lead \','+params.id_lead_m+')">' + 'Assign Lead' + '</a></center></td>' +
						    //'<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'addstock\');">' +'Assign Lead'+ '</a></center></td>' +
							'</tr>';
					}
				}
				else {
					list += '<tr><td colspan="5"><strong><center>No data found.</center></strong></td></tr>';
					$('#AprvleadForDisplaytd').html('</tbody>' + list);
				}
				$('#AprvleadForDisplaytd').html('</tbody>' + list);
			}, 'json');
		}
	});
}*/








function checkotherSRC(id , ClassName,servletName)
{          
	
  			$.post(servletName, { action: 'CheckExistforSource', id:id }, function(r) {
			
				if (r.data.length>0) {
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							debugger;
							if (key == 'nm_src' && r.data[i][key] == 'NA') {
								$('.'+ClassName).show();
								$('input[name="nm_src"]').val(r.data[i][key]);
							
							
							 
							}
							else{
								$('.'+ClassName).hide();
							$('input[name="nm_src"]').val(r.data[i][key]);
							
							}
								
						}
						}
				
				     
				}
				else {
								}
					
			}, 'json');
	

}


function Showcolrfdcheque(action){
	
debugger;
	if(action.value=="Cheque"){
        $('input[name="cheque_no_rfd"]').val('');
		$('.srv').show();
	}
	 if(action.value!="Cheque"){
	
           $('.srv').hide();
           console.log('hi');
			}
}		
	
function Validatespacelineitemfield(event,Id)
{
	debugger;
		
	  var domElement =$(event.target || event.srcElement);
     var nm_Cat1=$('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val().replace(/\s+/g, " ").trim();
	 $('input[name="rental_day'+domElement.attr('patelUnPrc')+'"]').val(nm_Cat1);
	 
	
}

//for extend
function checkaval(event, name,name1){
debugger;
	var domElement =$(event.target || event.srcElement);
	  //var IdName= Id + domElement.attr('patelUnPrc');
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	//var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';

	var fieldName= name + domElement.attr('patelUnPrc');
	var fieldName1= name1 + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	
	if(str != '')
		{
	if(intRegex.test(str)) {
		
		
      
	
		
	}//
	else
		{
			//str1 = $('input[name="'+fieldName1+'"]').val();
			alert('Please provide the valid number in the field.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			$('input[name="'+fieldName1+'"]').val('');
			exit(0);
		}
			
			
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
			$('input[name="'+fieldName1+'"]').val('');
		exit(0);
		
	} 
	
	
}



//rental expire date autoselect	
function ShowRowColdtexp(event , Id)
{
	debugger;
	
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