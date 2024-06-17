function ControlPrintMailPO(action,DispDiv,HideDiv,id_po,no_po,id_ven)
{
	
if(action == 'Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DispDiv).show();
	}

if(action == 'Preview')
{
	
	$.post('PO_Report',{action : 'Preview',id_po:id_po,no_po:no_po,id_ven:id_ven},function (r){
				
		var temp='';
				if(r.data.length > 0)
					{
						var list='<tr>'+
							'<td height="20"  valign="top" style="width: 33%;"><div align="left"><img src="image/zaplogo.gif"></div></td>'+
							'<td style="width: 33%;border-left-style: none;">'+
							'<font size="2"><b>HEAD OFFICE :<br>'+r.company[0].nm_com+'<br>'+r.company[0].add1+'<br>'+r.company[0].add2+'<br>'+r.company[0].city+'<br>'+r.company[0].country+'<br>'+r.company[0].pin+'</b></font>'+
							'</td>'+
							
							'<td valign="top" style="border-left-style: none;"><b>'+
							'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.data[0].nm_subl+'<br>'+r.data[0].add1+'<br>'+r.data[0].city+'<br>'+r.data[0].country+'<br>'+r.data[0].pin+'</b></font>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td colspan="2"><b>'+
							'PO Addressed To :<br>'+
							
							r.venDetails[0].nm_ven+'<br>'+
							r.venDetails[0].add1+'<br>'+
							r.venDetails[0].add2+'<br>'+
							r.venDetails[0].city+'<br>'+
							r.venDetails[0].state+'<br>'+
							r.venDetails[0].country+'<br>'+
							'</td>'+
							
							'<td>'+
							'<table class="table table-bordered">'+
								'<tr>'+
									'<td>SAP Number</td>'+
									'<td><b>'+r.data[0].no_sap+'</b></td>'+
								'</tr>'+
								'<tr>'+
									'<td>P.O Number</td>'+
									'<td><b>'+r.data[0].no_po+'</b></td>'+
								'</tr>'+
								'<tr>'+
								'<td>P.O Date</td>'+
								'<td><b>'+r.data[0].dtPo+'</b></td>'+
								'</tr>'+
								'<tr>'+
									'<td>Quotation Number</td>'+
									'<td><b>'+r.data[0].no_quot+'</b></td>'+
								'</tr>'+
								'<tr>	'+
									'<td>Quotation Date</td>'+
									'<td><b>'+r.data[0].dtQuot+'</b></td>'+
								'</tr>'+
							'</table>'+
							'</td>'+
							
						'</tr>	'+
						'<tr>'+
							'<td colspan="3"><b>NOTE : </b>With reference to your quotation we are pleased to place an order on you as per the following terms and conditions:</td>'+
						'</tr>	';
					temp='<tr><td colspan="3">'+
					'<table class="table table_bordered">'+
					'<tr class="tableHeader info">'+
						'<td><strong>Asset Name<a href=#></a></strong></td>'+
						'<td><strong>Description</strong></td>'+
						'<td><strong>Delivery Date</strong></td>'+
						'<td><strong>Tax (%)</strong></td>'+
						'<td><strong>Quantity </strong></td>'+
						'<td><strong>Unit Price('+r.data[0].nm_curr+')</strong></td>'+
						'<td style="border-right: 1px solid #ddd;"><strong>Total Price('+r.data[0].nm_curr+')</strong></td>'+
					'</tr>';
						for(var i=0;i<r.data.length;i++)
						{
						params=r.data[i];
						
						temp += '<tr >'+
						'<td style="width:150px;">'+params.nm_prod+'</td>'+
						'<td>'+params.description+'</td>'+
						'<td>'+params.dt_recv+'</td>'+
						'<td>'+params.tax_per+'</td>'+
						'<td>'+params.qty+'</td>'+
						'<td>'+params.un_prc+'</td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;">'+params.tot_prc+'</td>'+
						'</tr>';
						}
						var total=parseFloat(r.data[0].tax_prc)+parseFloat(r.data[0].tot)+parseFloat(r.data[0].insurance)+parseFloat(r.data[0].frieght);
						
						temp +='<tr><td colspan="6"><b style="float:right;">Sub Total</b></td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tot+'</b></td>'+
						'</tr>'+
						'<tr style="border-bottom: 1px solid #ddd;">'+
						'<td style="border-top-style: none;" colspan="4"><b style="float:right;">Tax Percentage</b></td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_per+'</b></td>'+
						'<td style="border-top-style: none;border-left-style: none;"><b style="float:right;">Tax Price</b></td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_prc+'</b></td>'+
						'</tr>'+
						'<tr style="border-bottom: 1px solid #ddd;">'+
						'<td style="border-top-style: none;border-bottom-style: hidden;border-left-style: hidden;" colspan="6"><b style="float:right;">Insurance</b></td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].insurance+'</b></td>'+
						'</tr><tr>'+
						'<td style="border-top-style: none;border-bottom-style: hidden;border-left-style: hidden;" colspan="6"><b style="float:right;">Frieght</b></td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;border-top-style: none;"><b>'+r.data[0].frieght+'</b></td>'+
						'</tr><tr>'+
						'<td style="border-top-style: none;border-left-style: hidden;" colspan="6"><b style="float:right;">Total</b></td>'+
						'<td style="width:150px;border-bottom-style: groove;border-right-style: groove;"><b>'+total.toFixed(2)+'</b></td>'+
						'</tr>'+
					'</table></td></tr>';
						list +=temp;
						
						
					$('.DisplayAmendPurchaseOrder').html(list);
					$('#'+HideDiv).hide();
					$('#'+DispDiv).show();
					$('#buttonForPoReport').show();
					}
				else
				{
					bootbox.alert("Somthing went wrong Please try again.");
				}
				
			},'json');

}
}

function DisplayPurchaseOrderForPrintMail()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var id_ven =$('input[name="id_ven"]').val();
	
		$.post('PO_Report',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,id_ven:id_ven},function (r){

			var list= '<tr>'+
			'<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Details Report </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>Quotation Number</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong>Approve By</strong></center></td>'+
				'<td style="width: 85px;"><strong><a href="#">Preview</a></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.no_quot+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><a href="#" onclick="ControlPrintMailPO(\'Preview\',\'createPrintorMailPurchaseOrder\',\'displayPrintorMailPurchaseOrder\',\''+params.id_po+'\',\''+params.no_po+'\','+params.id_ven+')"> Preview </a></strong></td>'+
									'</tr>';
				}
			
				$('.PrintMailPODisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.PrintMailPODisplay').html(list);
			}
		
		
	},'json');

			}});

}