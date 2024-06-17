
function ControlAccepRejectQuotationDiv(action , DisplayDiv , HideDiv , no_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		DisplayQuotaionForApproval();
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();
	}
	
	else if(action =='Modify')
		{
			EditAcceptRejectQuotaionFun(action , DisplayDiv , HideDiv , no_ind);
			
		}
	else if(action =='Accepted' || action =='Rejected')
	{
		var t=true;
		$('.removeErrorClass').removeClass('error');
		$('.removeErrorClass').removeClass('error');
		if($('#dtApprovQuot').val() == '')
		{
			$('#dtApprovQuot').addClass('error');
			$('#dtApprovQuot').focus();
			t=false;
			bootbox.alert('Mandatory Field.');
		}
		if($('#dtApprovQuotRemarks').val() == '')
		{
			$('#dtApprovQuotRemarks').addClass('error');
			$('#dtApprovQuotRemarks').focus();
			t=false;
			bootbox.alert('Mandatory Field.');
		}
if(t)
{
	if(action =='Accepted')
		{
			$('input[name="status_approv"]').val('Accepted');
			if($('#acceptQuotNoID').val() == '')
			{
				bootbox.alert('Mandatory Field.');
				$('#acceptQuotNoID').addClass('error');
				$('#acceptQuotNoID').focus();
				t=false;
				
			}
		}
	if(action =='Rejected')
	{
		$('input[name="status_approv"]').val('Rejected');
		var gg = $('#rejectQuotNoID');
		if(gg.val() == null)
		{
			bootbox.alert('Mandatory Field.');
			$('#rejectQuotNoID').addClass('error');
			$('#rejectQuotNoID').focus();
			t=false;
			
		}
	}
}
if(t)
	{
	t=false;
	var acceptQuotNo=$('#acceptQuotNoID').val();
	var dt_recv =$('#dtApprovQuot').val();
	$('#dtApprovQuot').removeClass('error');
	//$('.appq').attr('disabled','disabled');

	
	 $.post('Approval_Quotation',{action : 'CheckDate' ,dt_recv:dt_recv,acceptQuotNo:acceptQuotNo},function (r){
 		if(r.data == 0)
 			{
 			
 			alert('Approval/Reject date should be greater or equal to receive quotation date : '+r.dt_rec_quot);
 			$('#dtApprovQuot').focus();
 			$('#dtApprovQuot').val('');
 			$('#dtApprovQuot').addClass('error');
 				exit(0);
 			}
 		else
 			{
 			t=true;
 			}
 		if(t)
 		{
 			$('.appq').attr('disabled','disabled');
 			var x = $('#submitApproveQuotation').serialize();
 			
 			$.post('Approval_Quotation',x,function (r){
 						
 						if(r.data == 1)
 							{
 								DisplayQuotaionForApproval();
 								bootbox.alert(action +" Successfully.");
 								$( ".approveQuotation" ).trigger( "click" );
 							}
 						else
 						{
 							bootbox.alert("Something went wrong Please try again.");
 						}
 						$('.appq').removeAttr('disabled');
 					},'json');
 			
 		}
 },'json');
	
	}
	}
			}});
}



function EditAcceptRejectQuotaionFun(action , DisplayDiv , HideDiv , no_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Approval_Quotation',{action : 'Edit' , no_ind : no_ind},function (r){
			var list='', temp='';
			
				if(r.data.length > 0)
					{
					
					var id_quotArr = new Array();
					for(var i = 0; i < r.data.length ; i++)
					{
					if(id_quotArr.length === 0)
						{
						id_quotArr.push(r.data[i].id_quot);
						}
					else
						{
						if($.inArray(r.data[i].id_quot, id_quotArr) > -1){
							
						}
						else
							{
							id_quotArr.push(r.data[i].id_quot);
							}
						}
					}
					var t=0;
					var tempOptionVal='';
					
						for(var j = 0; j < id_quotArr.length ; j++)
						{
							
							params=r.data[(t)];
							var tempQuot = params.id_quot;
							tempOptionVal +='<option value="'+tempQuot+'">'+params.nm_ven+'-'+params.no_quot+'</option>';
							
							 temp='<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
							'<td colspan="4" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Name :</strong> <b>'+params.nm_ven+'</b></td>'+
							'<td colspan="3" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Code :</strong> <b>'+params.cd_ven+'</b></td>'+
						'</tr>'+
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="4" style="border-right-style: hidden;"><strong style="">Quotation No  :</strong><b>'+params.no_quot+'</b></td>'+
							'<td colspan="3" style="border-right-style: hidden;"><strong style="">Delivery Date :</strong><b>'+params.dtrecquot+'</b></td>'+
						'</tr>'+
						'<tr class="tableHeader info">'+
							'<td><strong>Item Name<a href=#></a></strong></td>'+
							/*'<td><strong>Group</strong></td>'+
							'<td><strong>Sub Group</strong></td>'+*/
							'<td style="width: 209px"><strong>Item Code</strong></td>'+
							/*'<td><strong>Description</strong></td>'+*/
							'<td style="width: 70px;"><strong>Quantity</strong></td>'+
							'<td style="width: 70px;"><strong>Price Quoted</strong></td>'+
							'<td style="width: 100px;"><strong>Tax Name</strong></td>'+
							'<td style="width: 70px;"><strong>Tax Value</strong></td>'+
							'<td style="width: 70px;font-size: initial;"><strong>Total</strong></td>'+
							
						'</tr>';
							
							for(var i = 0; i < r.data.length ; i++)
							{
								
								params1 = r.data[i];
								if(params1.id_quot == tempQuot)
								{
									t=i+1;
								temp = temp + '<tr class="success">'+
													'<td>'+params1.nm_prod+'</td>'+
													/*'<td>'+params1.nm_assetdiv+'</td>'+
													'<td>'+params1.nm_s_assetdiv+'</td>'+*/
													'<td>'+params1.cd_prod+'</td>'+
													/*'<td>'+params1.description+'</td>'+*/
													'<td>'+params1.qty+'</td>'+
													'<td>'+params1.un_prc+'</td>'+
													'<td>'+params1.nm_tax+'</td>'+
													'<td>'+params1.tax_val+'</td>'+
													'<td style="font-size: initial;">'+params1.tot_prc+'</td>'+
													'</tr>';
									}
							}
							/*var t_c_quot='';
							
							if(params.t_c_quot != undefined){
								var arr = params.t_c_quot.split('\n');
								for(var k=0;k<arr.length;k++)
									{
									t_c_quot += arr[k]+'<br>';
									}
							}
							*/
							 list += temp+'<tr  style="background-color: cadetblue;color: white;">'+
								'<td colspan="6"><strong style="float:right;font-size: initial;">Grand Total ( '+params.nm_curr+' )</strong></td>'+
								'<td><strong style="font-size: initial;">'+params.tot+'</strong></td>'+
								'</tr>'+
								/*'<tr  style="background-color: cadetblue;color: white;">'+
								'<td colspan="3"><strong style="float:right;">Tax Percentage</strong></td>'+
								'<td><strong>'+params.tax_per+'</strong></td>'+
								'<td><strong>Tax Price </strong></td>'+
								'<td><strong>'+params.tax_prc+'</strong></td>'+
								'<td ><strong style="float:right;">Grand Total ('+params.nm_curr+')</strong></td>'+
								'<td><strong>'+params.gr_tot+'</strong></td>'+
								'</tr>'+*/
							/* '<tr>'+
								'<td><strong style="float:right;">Terms & Condition</strong></td>'+
								'<td colspan="7">'+t_c_quot+'</td>'+
								'</tr>'+*/
								'<tr><td colspan="7" style="border-left-style: hidden;border-right-style: hidden;"></td></tr>';
						}

						 list += '<tr>'+
						 '<input type="hidden" name="no_ind" value="'+no_ind+'">'+
							'<td colspan="7" style="background-color: cadetblue;color: white;"><p style="margin-left: 285px;font-size: 20px;">Select Quotation Number to Approve or Reject</p></td>'+
							'</tr>'+
							'<tr>'+
								'<td colspan="7"><b style="margin-left:10px">Approval/Reject Date <font color=red>*</font> :</b>'+
								'<input style="margin-left:10px" id="dtApprovQuot" type="text" name="dt_approv" class="datepicker removeErrorClass">'+
								
								'<b style="margin-left:10px">Select Vendor<font color=red>*</font> :</b>'+
								
									'<select style="margin-left:10px" name="acceptQuotNo" id="acceptQuotNoID" class="removeErrorClass">'+
										'<option value="">Select</option>'+
										''+tempOptionVal+''+
									'</select>'+
									'<b style="margin-left:10px">Remarks :</b>'+
									'<input style="margin-left:10px" type="text" name="remarks" class="removeErrorClass" id="dtApprovQuotRemarks" value="">'+
									
								'</td>'+
								/*'<td colspan="6"><b>Click for download :</b> &nbsp;&nbsp;&nbsp;&nbsp;'+
								'<a href="download.jsp?fileName1='+r.data[0].nm_upload_file+'" target="_blank"> '+r.data[0].nm_upload_file+'</a></td>'+
								*/
							'</tr>'+
							/*'</tr>'+
								'<td><b>Quotation Number<font color=red>*</font> :</b></td>'+
								'<td>'+
									'<select name="acceptQuotNo" id="acceptQuotNoID" class="removeErrorClass">'+
										'<option value="">Select</option>'+
										''+tempOptionVal+''+
									'</select>'+
								'</td>'+
								'<td><b>Quotation Number <font color=red>*</font> :</b></td>'+
								'<td colspan="5">'+
									'<select name="rejectQuotNo" id="rejectQuotNoID" multiple style="width: 300px;" class="removeErrorClass">'+
										''+tempOptionVal+''+
									'</select>'+
								'</td>'+
							'</tr>'+*/
							'<tr>'+
							'<td colspan="7">'+
								'<center><button type="button" style="margin-left: 200px;" class="btn btn-primary appq" onclick="ControlAccepRejectQuotationDiv(\'Accepted\',\'displayApproveQuotation\',\'createApproveQuotation\')">Approve</button></center>'+
//								'<button type="button" class="btn btn-primary appq" style="margin-left: 400px;" onclick="ControlAccepRejectQuotationDiv(\'Rejected\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td>'+
							
								'</td>'+
							
						'</tr>';
						 
						$('.QuotationDetailsForApproval').html(list);	
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Something went wrong please try again.");
					}
				
				$( ".datepicker" ).datepicker({
					yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				      
				    });
				
				var currentDate = new Date();
				$('input[name="dt_approv"]').datepicker("setDate", currentDate);
				
		},'json');
			}});
}


function DisplayQuotaionForApproval()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Approval_Quotation',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Pending Quotation For Approval</p></td>'+
			'</tr>'+
				'<tr class="tableHeader info">'+
			'<td><strong>Indent Code<a href=#></a></strong></td>'+
			'<td><strong>Indent Date</strong></td>'+
			'<td><strong>Indented By</strong></td>'+
			'<td style="width: 150px;"><strong><a href="#">Accept/Reject</a></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td>'+params.no_ind+'</td>'+
									'<td>'+params.dt_ind+'</td>'+
									'<td>'+params.nm_emp+'</td>'+
									'<td><strong><a class="alert" href="#" onclick="ControlAccepRejectQuotationDiv(\'Modify\',\'createApproveQuotation\',\'displayApproveQuotation\',\''+params.no_ind+'\')"> Accept/Reject </a></strong></td>'+
									'</tr>';
				}
			
				$('.approvequotatioForDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong>No record(s) is available.</strong></td></tr>';
				$('.approvequotatioForDisplay').html(list);
			}
		
		
	},'json');

			}});
}

/*
$( ".approvQuotDatepicker" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "yy-mm-dd",
      autoSize: true,
      altFormat: "DD-MMM-YYYY",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_recv=$(this).val();
    	  var id_quot=$('input[name="id_quot"]').val();
    	  
    	  $.post('Approval_Quotation',{action : 'CheckDate' ,dt_recv:dt_recv,id_quot:id_quot},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('Approval/Reject date should be greater or equal to receive quotation date : '+r.dt_rec_quot);
	    			$(".approvQuotDatepicker").focus();
	    			$(".approvQuotDatepicker").val('');
	    			$(".approvQuotDatepicker").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  
    	  
      }
    });

*/