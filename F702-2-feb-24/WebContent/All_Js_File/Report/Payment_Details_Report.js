function DisplayInvoiceForPaymentReport()
{
	var dt_to = $('input[name="dt_to"]').val();
	var dt_frm = $('input[name="dt_frm"]').val();
	var rt_tt =0.0;
	$.post('Payment_Details_Report',{action : 'Display' , dt_to : dt_to,dt_frm:dt_frm},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>PO Number</strong></td>'+
					'<td><strong>PO Date</strong></td>'+
					'<td><strong>Vendor</strong></td>'+
					'<td><strong>PO Value(INR)</strong></td>'+
					'<td style="width: 120px;"><strong><a href="#">Preview </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						params = r.data[i];
						 rt_tt = parseFloat(params.gr_tot).toFixed(2);
						 
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_po+'</center></td>'+
											'<td><center>'+params.dt_po+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td><center>'+ rt_tt+'</center></td>'+
											'<td><strong><a href="ReportView/Payment_Details_Report.jsp?id_po='+params.id_po+'&value='+rt_tt+'" target="_blank"> Preview </a></strong></td>'+
											'</tr>';
						}
						$('.invoiceDisplayForPaymentReport').html(list);
					}
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.invoiceDisplayForPaymentReport').html(list);
					}
				
			},'json');
}
