
function Controlforservice(servletName) {
	SessionCheck(function(ses) {
		if (ses) {
		
			debugger;
				
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							DisplaySubDropDownData(r.data[0].id_loc, 'slocdataforservice', 'M_Subloc', function(status) {
								if (status) {
									
							
								
													$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
													$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
													
													$('select[name=send_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
														
										
								}
							});
							
							 setTimeout(function() {
							DisplayProductforService(r.data[0].id_loc,r.data[0].id_sloc,'',servletName);
						}, 10); 
						}
					}
				}, 'json');
			
	
				
				
				
			
		}
	});
}

function ControlforRestore(servletName) {
	SessionCheck(function(ses) {
		if (ses) {
		
			debugger;
				
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							DisplaySubDropDownData(r.data[0].id_loc, 'slocdataforafetrsrvicerestock', 'M_Subloc', function(status) {
								if (status) {
									
							
								
													$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
													$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
													
													$('select[name=added_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
														
										
								}
							});
							debugger;
							 setTimeout(function() {
							DisplayProductRestoreafterservice(r.data[0].id_loc,r.data[0].id_sloc,'',servletName);
						}, 10); 
						}
					}
				}, 'json');
			
	
				
				
				
			
		}
	});
}


//List View Display For De-Allocation
function DisplayProductforService(id_loc,id_sloc,searchWord,servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
				debugger;
	
		    
	$.post('J_Servicing',{action : 'Search' , DisplayType : 'Instore' ,id_loc:id_loc,id_sloc:id_sloc, searchWord : searchWord} , function (r){
		
					
						
						var list1= '<thead><tr class="tableHeader info backcolor">'+
					/*'<td><strong><center>Prodcut ID</center></strong></td>'+*/
					'<td><strong><center>Product Name</center></strong></td>'+
					'<td><strong><center>Serial No.</center></strong></td>'+
					'<td><strong><center>Remarks</center></strong></td>'+
					
					'<td style="width: 125px;"><center><strong> Check  /  Uncheck </strong></center></td>'+
				'</tr></thead><tbody>';
						if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
					
						debugger;
						list1 = list1 + '<tr class="success">'+
					/*	'<td><center>'+params.id_wh_dyn+'</center></td>'+*/
						'<td><center>'+params.nm_product+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center><input type="text" name="rmk'+params.serial_no+'" value="" class="form-control" style="width: 150px;"></center></td>'+
						/*'<td>'+
						'<center><select  name = "device_status'+params.id_wh+'" class="form-control"   data-valid="mand" >'+
								
								'<option value = "allct_to_emp" >Permanent</option>'+
								'<option value = "allct_to_emp_temp" >Temporary</option>'+
								
					
						'</select></center>'+
					'</td>'+*/
					
						'<td><strong><center><input type="checkbox" name="productcheck" class="productforserviceForSelectAll" value="'+params.serial_no+'"/></center></strong></td>'+
						
						'<input type="hidden" name="id_prod'+params.serial_no+'" value="'+params.id_product+'">'+
						'</tr>';
						
						
						}
						

						
					}
					 else{
						 list1 +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
					}
					debugger;
					$('.DisplayProductSendForservicing').html('</tbody>'+list1);
						//getListViewWithoutBtn('DisplayProductSendForservicing','DisplayProductSendForservicing');
										$('#DisplayProductSendForservicing_filter').hide();
				/*	$("#empdataforsendBy").select2();
					DisplayDropDownData("M_Emp_User","empdataforsendBy",function (status){
						if(status)
						{
							
							DropDownDataDisplay("M_Loc","locdataforservice",function (status){
								if(status)
								{
									
									
								}});
						}});*/
					jQuery("input#bfileID").change(function () {
						var formData = new FormData();
					    formData.append('file', $('#bfileID').get(0).files[0]);
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
						    	
						    		$('input[name="upload_asset"]').val(r.upload_inv);
						    		}
						},'json');
					});
					$( ".datepicker" ).datepicker({
					      changeMonth: true,
					      changeYear: true,
					      dateFormat: "dd/mm/yy",
					      autoSize: true,
					      altFormat: "dd/mm/yy",
					    });
	currentDate = new Date();
	$('input[name="dt_snd_service"]').datepicker("setDate", currentDate);
			},'json');
		
			}});
}

//Checkbox Validatiojn for Allocate
function checkBoxValidation(FormName)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
	var service_provide=$('input[name="service_by"]').val();
					
			if(service_provide == '' ||  service_provide == undefined )	
					{
						
						t=0;
						bootbox.alert("Please fill-up all the mandatory fields.");
						//$('.inv').removeAttr('disabled');
						//$('.patelTax2').attr('disabled', 'disabled');
						$('input[name="service_by"]').addClass('error');
						//$('select[name="id_sgrp'+i+'"]').addClass('error');
						exit(0);
					}
	if(FormName == "productforservice")
		{
			
			$('input[type="checkbox"]').each(function(){
				
				debugger;
				$('input[name="dt_snd_service'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						val = $('input[name="dt_snd_service'+$(this).val()+'"]').val();
						if(val == '')
							{
								debugger;
								bootbox.alert("Please fill the date field.");
								$('input[name="dt_snd_service'+$(this).val()+'"]').addClass('error');
								$('input[name="dt_snd_service'+$(this).val()+'"]').focus();
								exit(0);
							}
						
						$('.Commonforservice').each(function(){
							
							$(this).removeClass('error');
							
							if($(this).val() == '')
								{
									t=0;
									bootbox.alert("Mandatory field.");
									$(this).addClass('error');
									$(this).focus();
									exit(0);
								}
							else
								{
									t=1;
								}
						});
						
						
						
					}
				
			});
		}
	
	if(t == 0)
	{
		bootbox.alert("Please select at least one Product.");
	}
	else
		{
		$('.Outforservice').attr('disabled','disabled');
		if(FormName == "productforservice")
			{
		//$('input[name="action"]').val('BulkInstallCheckDate');
		var x = $('#'+FormName).serialize();
		
		$.post('J_Servicing',x,function (r)
				{
			if(r.data == 0)
			{
			
			/*alert('Bulk Allocation Date should be greater or equal to '+r.dt_grn);
			$('.Outforservice').removeAttr('disabled');
				$('#BulkInstallId').focus();
				$('#BulkInstallId').val('');
				$('#BulkInstallId').addClass('error');
				exit(0);*/
			}
			else
				{
					
				$('input[name="action"]').val('OutforService');
				var x = $('#'+FormName).serialize();
				$.post('J_Servicing',x,function (r)
						{
							
							if(r.data ==1)
								{
									$('.Outforservice').removeAttr('disabled');
									bootbox.alert("Re-Store successfully.");
								window.location = $('.jobwork_event').attr('href');	
								}
							else if(r.data == 2)
								{
									/*$('.Outforservice').removeAttr('disabled');
									bootbox.alert("Allocated successfully.");
								DisplayProductforService('J_Servicing');*/	
								}
								else
									{
										$('.Outforservice').removeAttr('disabled');
										bootbox.alert("Please select at least one Product.");
									}
										
							$('.Outforservice').removeAttr('disabled');
						},'json');
				
				
				
				}
			
				},'json');
		}else
			{
			
			var x = $('#'+FormName).serialize();
			
			$.post('J_Servicing',x,function (r)
					{
				
						if(r.data ==1)
							{
							/*	$('.Outforservice').removeAttr('disabled');
								bootbox.alert("De-allocated successfully.");
								DisplayProductforService('J_Servicing');*/								
							}
						else if(r.data == 2)
							{
								$('.Outforservice').removeAttr('disabled');
								bootbox.alert("Allocated successfully.");
								DisplayProductforService('J_Servicing');		
							}
							else
								{
									$('.Outforservice').removeAttr('disabled');
									bootbox.alert("Please select at least one Product.");
								}
									
						
					},'json');
			
			}
		
		
			
		}
			}});
}


function validateInput1() {
    var inputElement = document.getElementById("remarkid");
    var inputValue = inputElement.value.trim();

    // Replace characters that are not alphanumeric, dash, special characters, or forward slash
    var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/\-!@#$%^&*()_+={}[\]:;<>,.?~\\'"]/g, '');

    // Remove spaces between characters
    sanitizedValue = sanitizedValue.replace(/\s/g, '');

    if (sanitizedValue !== inputValue) {
       // bootbox.alert("Please enter valid characters in the field (i.e., alphabets, numbers, dash, special characters).");
        inputElement.value = sanitizedValue.toUpperCase();
        return;
    }

    inputElement.value = sanitizedValue.toUpperCase();
}


//List View Display For De-Allocation
function DisplayProductRestoreafterservice(id_loc,id_sloc,searchWord,servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
		
	
	$.post(servletName,{action : 'Search' , DisplayType : 'Restore' ,id_loc:id_loc,id_sloc:id_sloc,searchWord : searchWord} , function (r){
					
				/*	var list1 ='<tr class="new">'+
						'<td colspan="10"><button type="button" style="margin-left: 470px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation1(\'uninstallAsset\')">De-Allocate</button>'+
						'</td></tr>';
						$('.DisplayAssetForUnInstallationbtn').html(list1);*/
					var list= '<thead><tr class="tableHeader info backcolor">'+
				/*	'<td><center><strong>Product ID</strong></center></td>'+
				*/	'<td><center><strong>Serial No.</strong></center></td>'+
					'<td><center><strong>Product Name</strong></center></td>'+
					'<td><center><strong>Service Date</strong></center></td>'+
					'<td><center><strong>Remarks</strong></center></td>'+
					'<td><center><strong>Re-Store Date<font color="red">*</font></strong></center></td>'+
					
					'<td ><center><strong>Check/Uncheck</strong></center></td>'+
				'</tr></thead><tbody>';
					
					 if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						console.log(r.data);
						params = r.data[i];
						
						if(params.rmk == null) {
                           rmk = 'NA';
                         }
                     else if(params.rmk == '') {
                          rmk = '-';
                    }
                         else if(params.rmk == 'undefined') {
                          rmk = '-';
                    }
 				 else {
                    rmk = params.rmk;
             		  }
						debugger;
						list = list + '<tr class="success">'+
						
						/*'<td><center>'+params.id_wh_dyn+'</center></td>'+*/
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.nm_product+'</center></td>'+
				        '<td><center>'+params.dtsnd+'</center></td>'+
						'<td><center>'+
						
						'<input type="text" id="remarkid" name="rmk'+params.serial_no+'" value="'+rmk+'" onkeyup="validateInput1()" class="form-control" ></center></td>'+
						'<td><center><input type="text" name="dt_restore'+params.serial_no+'"  patel="'+params.serial_no+'" value=""  readonly style="background-color:transparent;font-size: 1em;" class=" datepicker form-control" data-valid="mand" style="width: 100px;"/></center></td>'+
		                 '<td><center><strong><input type="checkbox" name="addstock" class="restoreproductForSelectAll restoreproduct" value="'+params.serial_no+'"/></strong></center></td>'+
						'<input type="hidden" name="id_prod'+params.serial_no+'" value="'+params.id_product+'">'+
						'</tr>';
						}
						
						
					
						$('.DisplayProductSendForRestore').html('</tbody>'+list);
					
					}
					 else 
						{
							list +='<tr ><td colspan="10"><center><strong>No record(s) is available.</strong></center></td></tr>';
							$('.DisplayProductSendForRestore').html('</tbody>'+list);
						}
					$('.datepicker').datepicker({
						yearRange: '1985:2025', 
						changeMonth: true,
					      changeYear: true,
					      dateFormat: "dd/mm/yy",
					      autoSize: true,
					      altFormat: "dd/mm/yy",
					      onSelect: function(selected,evnt) {
					    	
					    	  var id_iwh = $(this).attr('patel');
                           // alert(id_iwh);
					    	  var name = $(this).attr('name');
					    	  var dt_snd_service = $(this).val();
					    	$('.datepicker').removeClass('error');
					    $.post('J_Servicing',{action : 'sendforserviceDate' , dt_snd_service : dt_snd_service ,id : id_iwh},function (r){
					    		
					    		if(r.data == 0)
					    			{
					    			
					    			alert('Restore date should be greater or equal to Send for Service Date '+r.dt_snd_service);
					    			$('input[name="'+name+'"]').focus();
					    			$('input[name="'+name+'"]').val('');
					    			$('input[name="'+name+'"]').addClass('error');
					    				exit(0);
					    			}
					    		
					    },'json');
					      }
					});	
					$(".datepicker").datepicker();
$(".datepicker").datepicker("setDate", new Date());
					getListViewWithoutBtn('DisplayProductSendForRestore','DisplayProductSendForRestore');
					$('#DisplayProductSendForRestore_filter').hide();
					
				
			},'json');
		
			}});
}


//Checkbox Validatiojn for Restore
function checkBoxValidation1(FormName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
	
			$('.restoreproduct').each(function(){
				debugger;
				
				$('input[name="restoredate'+$(this).val()+'"]').removeClass('error');
				if(this.checked)
					{	
						
						
					var q=$("select[name=asst_stat"+$(this).val()+"]").val();
				
					if(q==""){
						
						bootbox.alert("Please fill the asset status.");
					/*	$('select[name="asst_stat'+$(this).val()+'"]').addClass('error');
						$('select[name="asst_stat'+$(this).val()+'"]').focus();*/
						exit(0);
					}
						val = $('input[name="restoredate'+$(this).val()+'"]').val();
						if(val == '')
							{
							
							
								
								bootbox.alert("Please fill the date field.");
								$('input[name="restoredate'+$(this).val()+'"]').addClass('error');
								$('input[name="restoredate'+$(this).val()+'"]').focus();
								exit(0);
								
							}
						
					
						t=1;
						
					}
				
			});
		
	if(t == 0)
	{
		bootbox.alert("Please select at least one Product.");
	}
	else
		{
		
		$('input[name="action"]').val('Restore');
		var x = $('#'+FormName).serialize();
		//alert(x);
		$('.restoreafterservice').attr('disabled','disabled');
			$.post('J_Servicing',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.restoreafterservice').removeAttr('disabled');
								bootbox.alert("Re-Store successfully.");
									 changeSubContent('jobwork/jobwork1.jsp','jobwork1');
														
							}
						
							else
								{
									$('.restoreafterservice').removeAttr('disabled');
									bootbox.alert("try again.");
								}
									
						
					},'json');
			
			
		
		
			
		}
			}});
}


function Displaydataservice(idloc){
	debugger;
	//var id_sloc=idsloc.value;
	var id_loc=idloc;
	var id_sloc=$('select[name=id_sloc]').val();
		var searchWord = $('input[name="search"]').val();
			 setTimeout(function() {
							DisplayProductforService(id_loc,id_sloc,searchWord,'J_Servicing');
						}, 10); 
}

function DisplayRestore(idloc){
	debugger;
	var id_loc=idloc;
	var id_sloc=$('select[name=id_sloc]').val();
		var searchWord = $('input[name="search"]').val();
			 setTimeout(function() {
							DisplayProductRestoreafterservice(id_loc,id_sloc,searchWord,'J_Servicing');
						}, 10); 
}

