function ControlforRestoreFill(servletName) {
	SessionCheck(function(ses) {
		if (ses) {
		
			debugger;
				
				$.post('M_Emp_User', { action: 'LoginUserDetails' }, function(r) {
					if (r.data) {
						
						for (var i = 0; i < r.data.length; i++) {
							
							DisplaySubDropDownData(r.data[0].id_loc, 'slocdataforfillstock', 'M_Subloc', function(status) {
								if (status) {
									
							
								
													$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
													$('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
													
													$('select[name=fill_add_by] option[value=' + r.data[0].id_emp_user + ']').attr('selected', true);
														
										
								}
							});
							debugger;
							 setTimeout(function() {
							DisplayProductAfterFillRestore(r.data[0].id_loc,r.data[0].id_sloc,'',servletName);
						}, 10); 
						}
					}
				}, 'json');
			
	
				
				
				
			
		}
	});
}




//Checkbox Validatiojn for Allocate
function checkBoxValidation(FormName)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0;
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
function DisplayProductAfterFillRestore(id_loc,id_sloc,searchWord,servletName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
		
	
	$.post(servletName,{action : 'Search' , DisplayType : 'Instore_Empty' ,id_loc:id_loc,id_sloc:id_sloc,searchWord : searchWord} , function (r){
					
				/*	var list1 ='<tr class="new">'+
						'<td colspan="10"><button type="button" style="margin-left: 470px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidation1(\'uninstallAsset\')">De-Allocate</button>'+
						'</td></tr>';
						$('.DisplayAssetForUnInstallationbtn').html(list1);*/
					var list= '<thead><tr class="tableHeader info backcolor">'+
				/*	'<td><center><strong>Product ID</strong></center></td>'+
				*/	'<td><center><strong>Serial No.</strong></center></td>'+
					'<td><center><strong>Product Name</strong></center></td>'+
					//'<td><center><strong>Service Date</strong></center></td>'+
					'<td><center><strong>Remarks</strong></center></td>'+
					'<td><center><strong>fill Date<font color="red">*</font></strong></center></td>'+
					
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
				       // '<td><center>'+params.dtsnd+'</center></td>'+
						'<td><center>'+
						
						'<input type="text" id="remarkid" name="rmk'+params.serial_no+'" value="'+rmk+'" onkeyup="validateInput1()" class="form-control" ></center></td>'+
						'<td><center><input type="text" name="dt_fill'+params.serial_no+'"  patel="'+params.serial_no+'" value="" readonly style="background-color:transparent;font-size: 1em;" class=" datepicker form-control" data-valid="mand" style="width: 100px;"/></center></td>'+
		                 '<td><center><strong><input type="checkbox" name="addstockrefill" class="restoreproductForSelectAll restoreproduct" value="'+params.serial_no+'"/></strong></center></td>'+
						'<input type="hidden" name="id_prod'+params.serial_no+'" value="'+params.id_product+'">'+
						'<input type="hidden" name="id_iwh'+params.serial_no+'" value="'+params.id_iwh+'">'+
						'<input type="hidden" name="nm_prod'+params.serial_no+'" value="'+params.nm_product+'">'+
						'</tr>';
						}
						
						
					
						$('.DisplayProductFillRestore').html('</tbody>'+list);
					
					}
					 else 
						{
							list +='<tr ><td colspan="10"><center><strong>No record(s) is available.</strong></center></td></tr>';
							$('.DisplayProductFillRestore').html('</tbody>'+list);
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
					    $.post('S_Fill_Add_To_Stock',{action : 'sendforserviceDate' , dt_snd_service : dt_snd_service ,id : id_iwh},function (r){
					    		
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
					getListViewWithoutBtn('DisplayProductFillRestore','DisplayProductFillRestore');
					$('#DisplayProductFillRestore_filter').hide();
					
				
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
		
		$('input[name="action"]').val('RestoretostockRefill');
		var x = $('#'+FormName).serialize();
		//alert(x);
		$('.restoreafterRefill').attr('disabled','disabled');
			$.post('S_Fill_Add_To_Stock',x,function (r)
					{
				
				
						if(r.data ==1)
							{
								$('.restoreafterRefill').removeAttr('disabled');
								bootbox.alert("Re-Store successfully.");
									 changeSubContent('Store/fill_In_Stock.jsp','fill_instock');
														
							}
						
							else
								{
									$('.restoreafterRefill').removeAttr('disabled');
									bootbox.alert("try again.");
								}
									
						
					},'json');
			
			
		
		
			
		}
			}});
}




function DisplayRestoreFill(idloc){
	debugger;
	var id_loc=idloc;
	var id_sloc=$('select[name=id_sloc]').val();
		var searchWord = $('input[name="search"]').val();
			 setTimeout(function() {
							DisplayProductAfterFillRestore(id_loc,id_sloc,searchWord,'S_Fill_Add_To_Stock');
						}, 10); 
}

