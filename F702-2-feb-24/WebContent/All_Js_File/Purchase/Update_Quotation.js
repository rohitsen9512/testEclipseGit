
function ControlUpdateQuotationDiv(action , DisplayDiv , HideDiv , no_ind,dt_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		DisplayFinalizeVendor();
		$('#'+DisplayDiv).hide();
		$('#'+HideDiv).show();
	}
	
	else if(action =='Modify')
		{
			EditFinalizeVendorFun(action , DisplayDiv , HideDiv , no_ind,dt_ind);
			
		}
	else if(action =='Update' || action =='Rejected')
	{
		
		var t=false;
		t=ValidationForm('submitUpdateQuotation');

 if(t)
 	{
	 $('.appq').removeAttr('disabled');
 			var x = $('#submitUpdateQuotation').serialize();
 			$.post('Update_Quotation',x,function (r){
 						if(r.data == 1)
 							{
 								DisplayUpdateQuotation();
 								bootbox.alert("Quotation has been updated.");
 								$( ".UpdateQuotation" ).trigger( "click" );
 							}
 						else
 						{
 							bootbox.alert("Something went wrong Please try again.");
 						}
 						$('.appq').removeAttr('disabled');
 					},'json');
 			
 		}
 
	
	
	}
			}});
}



function EditFinalizeVendorFun(action , DisplayDiv , HideDiv , no_ind,dt_ind)
{

	SessionCheck(function (ses){		
		if(ses)
			{
		$.post('Update_Quotation',{action : 'Edit' , no_ind : no_ind},function (r){
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
					var t=0,m=0;
					
					for(var i = 0; i < r.data.length ; i++)

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
					
						for(var j = 0; j < id_quotArr.length ; j++)
						{
							
							params=r.data[(j)];
							var tempQuot = params.id_quot;
							
							var ven_ddelivery='',ven_payment='',ven_validity='',ven_others='';
							if(params.ven_ddelivery != undefined) ven_ddelivery= params.ven_ddelivery;
							if(params.ven_payment != undefined) ven_payment= params.ven_payment;
							if(params.ven_validity != undefined) ven_validity= params.ven_validity;
							if(params.ven_others != undefined) ven_others= params.ven_others;
							
							//tempOptionVal +='<option value="'+tempQuot+'">'+params.nm_ven+'-'+params.no_quot+'</option>';
							
							 temp +='<tr style="background-color: cadetblue;color: white;border-bottom-style: hidden;">'+
							'<td colspan="2" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Name :</strong> <b>'+params.nm_ven+'</b></td>'+
							'<td colspan="2" style="border-right-style: hidden;"><strong style="border-bottom-style: hidden;">Vendor Code :</strong> <b>'+params.cd_ven+'</b></td>'+
						'</tr>'+
						'<tr style="background-color: cadetblue;color: white;">'+
							'<td colspan="2" style="border-right-style: hidden;"><strong style="">Quotation No  :</strong><b>'+params.no_quot+'</b></td>'+
							'<td colspan="2" style="border-right-style: hidden;"><strong style="">Quotation Date :</strong><b>'+params.dt_rec_quot1+'</b></td>'+
						'</tr>'+
						'<tr class="tableHeader info">'+
							'<td><strong>Delivery<a href=#></a></strong></td>'+
							'<td><strong>Payment<a href=#></a></strong></td>'+
							'<td><strong>Validity<a href=#></a></strong></td>'+
							'<td><strong>Others Note<a href=#></a></strong></td>'+
							
						'</tr>'+
						'<tr>'+
								'<input type="hidden" name="count" value="'+j+'">'+
								 
								'<input type="hidden" name="id_quot'+j+'" value="'+tempQuot+'">'+
													'<td><input  type="text" name="ven_Ddelivery'+j+'" patelUnPrc="'+j+'" class="common-validation" value="'+ven_ddelivery+'"></td>'+
													'<td><input  type="text" name="ven_payment'+j+'" patelUnPrc="'+j+'" class="common-validation" value="'+ven_payment+'"></td>'+
													'<td><input  type="text" name="ven_validity'+j+'" patelUnPrc="'+j+'" class="common-validation" value="'+ven_validity+'"></td>'+
													'<td><input  type="text" name="ven_others'+j+'" patelUnPrc="'+j+'" class="common-validation" value="'+ven_others+'"></td>'+
													
									'</tr>';
							
						}

						 list = temp+
							
							'<tr>'+
							'<td colspan="4">'+
							
								'<center><button type="button" style="margin-left: 200px;" class="btn btn-primary appq" onclick="ControlUpdateQuotationDiv(\'Update\',\'displayApproveQuotation\',\'createApproveQuotation\')">Save & Update</button></center>'+
//								'<button type="button" class="btn btn-primary appq" style="margin-left: 400px;" onclick="ControlAccepRejectQuotationDiv(\'Rejected\',\'displayApproveQuotation\',\'createApproveQuotation\')">Reject</button></td>'+
							
								'</td>'+
							
						'</tr>';
						 
						$('.QuotationDetailsForPrintUpdate').html(list);	
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						$('input[name="no_ind"]').val(no_ind);
						$('input[name="dt_ind"]').val(dt_ind);
						$('input[name="dt_bid_open"]').val(r.data[0].dtbidopen);
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


function DisplayUpdateQuotation()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Update_Quotation',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Pending Item For Vendor selection</p></td>'+
			'</tr>'+
				'<tr class="tableHeader info">'+
			'<td><strong>Indent Code<a href=#></a></strong></td>'+
			'<td><strong>Indent Date</strong></td>'+
			'<td><strong>Indented By</strong></td>'+
			'<td style="width: 150px;"><strong><a href="#">Preview</a></strong></td>'+
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
									'<td><strong><a class="alert" href="#" onclick="ControlUpdateQuotationDiv(\'Modify\',\'createApproveQuotation\',\'displayApproveQuotation\',\''+params.no_ind+'\', \''+params.dt_ind+'\')"> Preview </a></strong></td>'+
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
    	  
    	  $.post('Update_Quotation',{action : 'CheckDate' ,dt_recv:dt_recv,id_quot:id_quot},function (r){
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