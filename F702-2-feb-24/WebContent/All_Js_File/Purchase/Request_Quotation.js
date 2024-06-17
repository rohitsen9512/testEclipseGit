
$("#MoveRight").click(function(){
	
	var selectedList='';
	$.each($("#assetDivForRequestQuotation option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#assetDivForRequestQuotationSelected") != null){
		
			$.each($("#assetDivForRequestQuotationSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#assetDivForRequestQuotationSelected').append(selectedList);
	
	 
});
$("#MoveLeft").click(function(){
	
	var selectedList='';
	$.each($("#assetDivForRequestQuotationSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#assetDivForRequestQuotation').append(selectedList);
	
	 
});
function DisplayDropDownDataForVendorRFQ(servletName,no_ind,dropDownId,type_ven,callback)
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
function DisplayDropDownDataForSelectedVendor(servletName,dropDownId,type_ven,no_ind,callback)
{
	var t =false;
	var list='';
	$.post(servletName,{action : 'DropDownResultForSelectedVendor',type_ven:type_ven,no_ind:no_ind},function (r){
		
		if(r.data)
			{
			t=true;
			
				 list= '';
			
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

function ControlQuotation(action , DisplayDiv , HideDiv , no_ind,id_id_ind,dt_rate_cont_valid)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Create')
		{	
			
			DisplayRequestUnderIndent('Request_Quotation',DisplayDiv , HideDiv , no_ind,id_id_ind,dt_rate_cont_valid);
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			
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
								
									$('select[name=id_apprv] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
									/*$('select[name=firstla] option[value=' + r.data[0].repo_mngr + ']').attr('selected',true);
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
								
								}});*/	
					}
},'json');
		/*	
			DisplayDropDownDataForSelectedVendor('M_Vendor','assetDivForRequestQuotationSelected','procured',no_ind,function (status){
				if(status)
				{
					
				}
			});*/

			
		}
	
	else if(action =='Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	else if(action == 'Save')
		{
		var cont_valid =$('#cont_valid').val();
		var t=false;
		t=ValidationForm('submitRequestQuotation');
		$('#assetDivForRequestQuotationSelected').removeClass('error');
		if(t)
			{
			var gg = $('#assetDivForRequestQuotationSelected');
			
			$.each($("#assetDivForRequestQuotationSelected option"), function(){ 
				//alert($(this).val());
				$(this).attr('selected', 'selected');
					
			    });
			
			if(gg.val() == null)
					{
						bootbox.alert("Mandatory Field.");
						$('#assetDivForRequestQuotationSelected').addClass('error');
						$('#assetDivForRequestQuotationSelected').focus();
						t=false;
						exit(0);
					}
			}
		if(t)
			{
			bootbox.confirm("Are you sure ?", function(result) {
				if(result)
				{	
			$('.reqq').attr('disabled','disabled');
				var x = $('#submitRequestQuotation').serialize();
				console.log(x);
				$.post('Request_Quotation',x,function (r)
						{
					
							if(r.data)
								{
								bootbox.alert("RFQ Successfully Created...");
								$( ".requestQuotation" ).trigger( "click" );
								
								}
								else
									{
										bootbox.alert("Something went wrong. Please try again.");
									}
									
							$('.reqq').removeAttr('disabled');
						},'json');
				}});
			}
		
		
		
		}
	
	
	
	
			}});


}



function DisplayRequestUnderIndent(servletName,DispDiv,HideDiv,no_ind,id_id_ind,dt_rate_cont_valid)
{
	$.post(servletName,{action : 'Display',id:no_ind,id_id_ind:id_id_ind,dt_rate_cont_valid:dt_rate_cont_valid},function (r){
		
		
		var list= '<tr>'+
				'<td colspan="10" class="tableHeader">'+
				'<p class="tableHeaderContent" style="margin-left: 35%;">Material Details For RFQ</p></td>'+
				'</tr>'+
				'<tr class="tableHeader info">'+
				'<td><strong><center>Model Name</center></strong></td>'+
				'<td><strong><center>Model Code</center></strong></td>'+
				'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
				'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
				'<td><center><strong>Qty</strong></center></td>'+
				'<td><strong><center>Remarks</center></strong></td>'+
		'</tr>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				if(params.description == undefined)
					description ='-';else description=params.description;
				
				list  += '<tr class="success">'+
				'<input type="hidden"  name="id_ind_asst" value="'+params.id_ind_asst+'">'+
				'<input type="hidden"  name="id_req" value="'+params.id_req+'">'+
				'<input type="hidden"  name="id_prod" value="'+params.id_prod+'">'+
				
								'<td><center>'+params.nm_model+'</center></td>'+
								'<td><center>'+params.cd_model+'</center></td>'+
								'<td><center>'+params.nm_assetdiv+'</center></td>'+
								'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
								'<td><center><input style="width: 100px;" type="text" name="qty" patelQty="'+i+'" value="'+params.qty+'" class="common-validation qtyClass'+i+'" data-valid="num" onBlur="checkQtyNegativity(event)"></center></td>'+
								'<td><center><input type="text" name="remarks" value="" patelQty="'+i+'" class="common-validation" ></center></td>'+
						 '</tr>';
				}
				if(dt_rate_cont_valid=='1'){
				var list1 ='<tr>'+
				'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 470px;">Quotation from Indent</p></td>'+
				'</tr>'+
				
				
				'<input type="hidden"  name="no_ind" value="'+no_ind+'">'+
				'<input type="hidden"  name="id_ind" value="'+r.data[0].id_ind+'">'+
					'<tr class="info">'+
				'<td><center><strong>RFQ Number :</strong></center></td>'+
				'<td style="background-color: cadetblue;color: white;"><center><b>'+no_ind+'</b></center></td>'+
				'<td><center><strong>RFQ Date :</strong></center></td>'+
				'<td style="background-color: cadetblue;color: white;"><center><b>'+r.data[0].dtind+'</b></center></td>'+
			'</tr>';
				$('#cont_valid').val(dt_rate_cont_valid);
				}
				else{
					var list1 ='<tr>'+
					'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 470px;">RFQ</p></td>'+
					'</tr>'+
						
					'<input type="hidden"  name="no_ind" value="'+no_ind+'">'+
					'<input type="hidden"  name="id_ind" value="'+r.data[0].id_ind+'">'+
						'<tr class="info">'+
					'<td><center><strong>RFQ No :</strong></center></td>'+
					'<td style="background-color: cadetblue;color: white;"><center><b>'+no_ind+'</b></center></td>'+
					'<td><center><strong>RFQ Date :</strong></center></td>'+
					'<td style="background-color: cadetblue;color: white;"><center><b>'+r.data[0].dtind+'</b></center></td>'+
				'</tr>';
					$('#cont_valid').val(dt_rate_cont_valid);
				}
				
				var tc='<center><textarea  style="margin-left: 0px;margin-right: 0px;width: 540px; height: 73px;"  name="quot_t_c">'+
					r.t_and_c+
				'</textarea></center>';
				$('#quot_t_c_data').html(tc);
				$('.indentDetailsForQuotation').html(list1);
				$('.requestDataForQuotaion').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="10"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.requestDataForQuotaion').html(list);
			}
		
	},'json');
}


function DisplayQuotation(servletName,DisplayDiv, HideDiv)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord,for_rate:'0'},function (r){
		var list= '<tr class="tableHeader">'+
		'<td colspan="6"><center><strong><p class="tableHeaderContent" style="">RFQ Details</p></strong></center></td>'+'</tr>'+

		'<tr class="tableHeader info">'+
		'<td><strong><center>RFQ Date</center></strong></td>'+
		'<td><strong><center>RFQ Number</center></strong></td>'+
		'<td><strong><center>PR Number</center></strong></td>'+
		'<td><strong><center>PR BY</center></strong></td>'+
		
		'<td><strong><center>RFQ By</center></strong></td>'+
		'<td style="width:200px;"><center><a href="#"><strong>Create RFQ</strong></a></center></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				list = list + '<tr class="success">'+
									'<td><center>'+params.dtind+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.requestnum+'</center></td>'+
									'<td><center>'+params.reqby+'</center></td>'+
									
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="ControlQuotation(\'Create\',\'createRequestQuotation\',\'displayRequestQuotation\',\''+params.no_ind+'\',\''+params.id_ind+'\',\''+params.dt_rate_cont_valid+'\')"> Create RFQ </a><center></strong></td>'+
							  '</tr>';
				}
				$('.requestquotationForDisplay').html(list);
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.requestquotationForDisplay').html(list);
			}
		
	},'json');
			}});
}
function DisplayQuotationRate(servletName,DisplayDiv, HideDiv)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord,for_rate:'1'},function (r){
		var list= '<tr class="tableHeader">'+
		'<td colspan="5"><center><strong><p class="tableHeaderContent" style="">Request Quotation</p></strong></center></td>'+'</tr>'+

		'<tr class="tableHeader info">'+
		'<td><strong><center>Rate Contract  Date</center></strong></td>'+
		'<td><strong><center>Rate Contract  Number</center></strong></td>'+
		//'<td><strong><center>Request Number</center></strong></td>'+
		
		'<td><strong><center>Created By</center></strong></td>'+
		'<td style="width:200px;"><center><a href="#"><strong>Make Quotation</strong></a></center></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				list = list + '<tr class="success">'+
									'<td><center>'+params.dtind+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									//'<td><center>'+params.requestnum+'</center></td>'+
									
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="ControlQuotation(\'Create\',\'createRequestQuotation\',\'displayRequestQuotation\',\''+params.no_ind+'\',\''+params.id_ind+'\',\''+params.dt_rate_cont_valid+'\')"> Create Quotation </a><center></strong></td>'+
							  '</tr>';
				}
				$('.requestquotationForDisplay').html(list);
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.requestquotationForDisplay').html(list);
			}
		
	},'json');
			}});
}


$( ".datepicker1" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_req=$(this).val();
    	  var id_ind=$('input[name="id_ind"]').val();
    	  $(".datepicker2").val('');
    	  
    	/*  $.post('Request_Quotation',{action : 'CheckDate' ,dt_req:dt_req,id_ind:id_ind},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('Requested Date should be greater or equal to indent date : '+r.dt_ind);
	    			$(".datepicker1").focus();
	    			$(".datepicker1").val('');
	    			$(".datepicker1").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  */
    	  
      }
    });


$( ".datepicker2" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_req_quot=$('input[name="dt_req_quot"]').val();
    	  var dt_req_quot1=$('input[name="dt_req_quot"]').val();
    	  var dt_end = $(this).val();
    	  if(dt_req_quot == '')
    		  {
    		  	alert('First filled requested date.');
    		  	$('input[name="dt_req_quot"]').addClass('error');
    		  	$('input[name="dt_req_quot"]').val('');
    		  	$(this).val('');
    		  }
    	  else
    		  {
    		  
    		  var temp_strt = dt_req_quot.split("/");
				 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
					
				var temp_end = dt_end.split("/");
				var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
					
				dt_req_quot = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
	    		  dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
    		/*  if(dt_req_quot > dt_end)
    			  {
    			  	alert('Last date of quotation should be greater or equal to requested date : '+dt_req_quot1);
    			  	$(".datepicker2").focus();
    			  	$(".datepicker2").val('');
    			  	$(".datepicker2").addClass('error');
    			  	exit(0);
    			  }*/
    		  
    		  }
    	  
      }
      
    });






