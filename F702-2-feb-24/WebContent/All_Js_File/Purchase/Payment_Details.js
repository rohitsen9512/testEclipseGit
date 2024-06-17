function ControlPaymentDetail(action,displayContent,createDetails,formName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	if(action == 'Save')
		{
		AddPaymentDetails(displayContent,createDetails,formName);
		}


			}
});

}
function paymentOfInvoice(displayContent,createDetails,id_po)
{
SessionCheck(function (ses){
		
		if(ses)
			{
$.post('Payment_Details',{action : 'Edit',id_po : id_po},function (r){
				
				if(r.data)
					{
					var t =(r.data[0].rem_val)*1.00;
					var t1 =(r.data[0].po_val)*1.00;
						var list='<tr>'+
							'<td  colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 200px;">Payment Details</p></td>'+
							'</tr>'+
							'<tr><td><b>PO Number</b></td><td><b>'+r.data[0].no_po+'</b></td></tr>'+
						'<tr><td><b>PO Date</td><td><b>'+r.data[0].dt_po+'</b></td></tr>'+
						'<tr><td><b>PO Value(INR)</td><td><b>'+t1.toFixed(2)+'</b></td></tr>'+
						'<tr><td><b>Remaining PO Value(INR)</td><td><b>'+t.toFixed(2)+'</b></td></tr>';
						
						
						$('input[name="id_po"]').val(id_po);
						$('input[name="dt_po"]').val(r.data[0].dt_po);
						$('input[name="no_po"]').val(r.data[0].no_po);
						$('input[name="val_po"]').val(t1.toFixed(2));
						$('input[name="rem_val_po"]').val(t.toFixed(2));
						
						$('.PaymentDetailDisplayForInvoic').html(list);
						
						
						$('#'+createDetails).hide();
						$('#'+displayContent).show();
						
					}
},'json');
			}
});
}



function AddPaymentDetails(displayContent,createDetails,formName)
{

	t=false;
	t = ValidationForm(formName);
		if(t)
		{
			$('.pay').attr('disabled','disabled');
		var x = $('#'+formName).serialize();
		
			$.post('Payment_Details',x,function (r){
				
				if(r.data == 1)
					{
						bootbox.alert('Payment successfully');
						$( ".paymentDetails" ).trigger( "click" );
					}
				$('.pay').removeAttr('disabled');
			},'json');
	
		
		}
			
}



function DisplayInvoiceForPayment(id_po)
{
if(id_po != '')
	//id_po = id_po.value;
var searchWord = $('input[name="search"').val();

				$.post('Payment_Details',{action : 'Display' , id_po : id_po,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>BRB Ref Number</strong></center></td>'+
					'<td><center><strong>PO Number</strong></center></td>'+
					'<td><center><strong>PO Date</strong></center></td>'+
					'<td><center><strong>Vendor</strong></center></td>'+
					'<td><center><strong>PO Value(INR)</strong></center></td>'+
					'<td><center><strong>Remaining PO Value(INR)</strong></center></td>'+
					'<td style="width: 120px;"><strong><center><a href="#">Payment </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						var gr_tot =(params.gr_tot)*1.00;
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.brb_ref_num+'</center></td>'+
											'<td><center>'+params.no_po+'</center></td>'+
											'<td><center>'+params.dt_po+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+gr_tot.toFixed(2)+'</center></td>'+
											'<td><center>'+params.rem_val_po+'</center></td>'+
											'<td><strong><center><a class="alert" href="#" onclick="paymentOfInvoice(\'CreatePaymentDetails\',\'displayPaymentDetails\','+params.id_po+')"> Payment </a></center></strong></td>'+
											'</tr>';
						}
					
					
						$('.invoiceDisplayForPayment').html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.invoiceDisplayForPayment').html(list);
					}
				
			},'json');
		
			
}
$( ".paymentDatepicker" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_po=$('input[name="dt_po"]').val();
    	  var dt_po1=$('input[name="dt_po"]').val();
    	  var dt_pmt = $(this).val();
    	  
    	  var temp_strt = dt_po.split("/");
			 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
				
			var temp_end = dt_pmt.split("/");
			var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
				
			dt_po = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
			dt_pmt = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
    	  
    		  if(dt_po > dt_pmt)
    			  {
    			  	alert('Payment date should be greater or equal to P.O date : '+dt_po1);
    			  	$(this).focus();
    			  	$(this).val('');
    			  	$(this).addClass('error');
    			  	exit(0);
    			  }
      }
    });
