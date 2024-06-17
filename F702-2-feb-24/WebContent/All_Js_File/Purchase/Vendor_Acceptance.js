function ControlVendorAcceptance(action,DispDiv,HideDiv,id_po,no_po)
{
	
if(action == 'Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DispDiv).show();
	}
if(action == 'Acceptance')
{
	var list='<td><strong>P.O Number</strong></td><td>'+no_po+'</td>';
$('#venAcceptanceHeader').html(list);
	
	$('input[name="id_po"]').val(id_po);
	$('input[name="no_po"]').val(no_po);
	
	$.post('Vendor_Acceptance',{action : 'Edit',id_po:id_po},function (r){
		$('input[name="sd_amt"]').val(r.sd_amt);
		$('input[name="dt_sd_amt"]').val(r.dt_sd_amt);
		
		
	},'json');

	
	$('#'+HideDiv).hide();
	$('#'+DispDiv).show();
}

if(action == 'Save')
{
	var t=false;
	t=ValidationForm('saveVendorAcceptance');
	if(t)
		{
		$('.acc').attr('disabled','disabled');
			var x = $('#saveVendorAcceptance').serialize();
			
			$.post('Vendor_Acceptance',x,function (r){
				
				if(r.data == 1)
					{
						bootbox.alert("Accepted Successfully.");
						$( ".vendorAcceptance" ).trigger( "click" );
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				$('.acc').removeAttr('disabled');
			},'json');
		}
	
	
}

}

function DisplayPurchaseOrderForVendorAcceptance()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Vendor_Acceptance',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Vendor Acceptance </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>BRB Ref Number</strong></center></td>'+
				'<td><center><strong>Quotation Number</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong>Approve By</strong></center></td>'+
				'<td style="width: 150px;"><strong><center><a href="#">Acceptance</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.brb_ref_num+'</center></td>'+
									'<td><center>'+params.no_quot+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><center><a href="#" onclick="ControlVendorAcceptance(\'Acceptance\',\'CreateVendorAcceptance\',\'displayVendorAcceptance\',\''+params.id_po+'\',\''+params.no_po+'\')"> Acceptance </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.POForVendorAcceptanceDisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.POForVendorAcceptanceDisplay').html(list);
			}
		
		
	},'json');

			}});

}

$( ".venAcceptanceDatepicker" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_sched=$(this).val();
    	  var id_po=$('input[name="id_po"]').val();
    	  $(".venAcceptanceDatepicker2").val('');
    	  
    	  $.post('Vendor_Acceptance',{action : 'CheckDate' ,dt_sched:dt_sched,id_po:id_po},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('Scheduled date should be greater or equal to P.O approved date : '+r.dt_approv);
	    			$(".venAcceptanceDatepicker").focus();
	    			$(".venAcceptanceDatepicker").val('');
	    			$(".venAcceptanceDatepicker").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  
    	  
      }
    });

$( ".venAcceptanceDatepicker2" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_schedule=$('input[name="dt_schedule"]').val();
    	  var dt_schedule1=$('input[name="dt_schedule"]').val();
    	  var dt_actual = $(this).val();
    	  if(dt_schedule == '')
    		  {
    		  	alert('First filled the schedule date.');
    		  	$('input[name="dt_schedule"]').focus();
    		  	$('input[name="dt_schedule"]').addClass('error');
    		  	$('input[name="dt_schedule"]').val('');
    		  	$(this).val('');
    		  	exit(0);
    		  }
    	  else
    		  {
    		  var temp_strt = dt_schedule.split("/");
				 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
					
				var temp_end = dt_actual.split("/");
				var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
					
				dt_schedule = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
				dt_actual = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
    		  
    		  if(dt_schedule > dt_actual)
    			  {
    			  	alert('Actual date should be greater or equal to schedule date : '+dt_schedule1);
    			  	$(this).focus();
    			  	$(this).val('');
    			  	$(this).addClass('error');
    			  	exit(0);
    			  }
    		  
    		  }
    	  
      }
    });

