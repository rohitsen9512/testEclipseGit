function ControlCancelPurchaseOrder(id_po)
{

	bootbox.confirm("Reason for Cancellation <br><textarea  style='width: 400px;height: 80px;' name='cancel_Remarks'></textarea>", 
	function(result) {
		if(result)
		{
			
			var cancelremarks=$('textarea[name="cancel_Remarks"]').val();
			
	$.post('Cancel_Purchase_Order',{action : 'Update',id_po:id_po,cancelremarks:cancelremarks},function (r){
				
				if(r.data == 1)
					{
						bootbox.alert("Canceled Successfully.");
						$( ".cancelPurchaseOrder" ).trigger( "click" );
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				
			},'json');
		
		}});
}

function DisplayPurchaseOrderForCancel()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var id_ven =$('select[name="id_ven"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Cancel_Purchase_Order',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,id_ven:id_ven,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Cancel </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
			'<td><center><strong>PO Number</strong></center></td>'+
			'<td><center><strong>PO Date</strong></center></td>'+
			'<td><center><strong>Vendor Name </strong></center></td>'+
			'<td ><center><strong><a href="#"> &nbsp;&nbsp;&nbsp;&nbsp;Cancel PO</a></strong></center></td>'+
			'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				tender_num ='';
				if(params.tender_num !=undefined)
					tender_num=params.tender_num;
				list = list + '<tr class="success">'+
				'<td><center>'+params.no_po+'</center></td>'+
				'<td><center>'+params.dt_po+'</center></td>'+
				'<td><center>'+params.nm_ven+'</center></td>'+
				'<td ><center><strong><a href="#" class="alert" onclick="ControlCancelPurchaseOrder(\''+params.id_po+'\')"> Cancel  PO </a></strong></center></td>'+
									
									'</tr>';
				}
			
				$('.cancelPODisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="10"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.cancelPODisplay').html(list);
			}
		
		
	},'json');

			}});

}
function PreviewPOForCancelatio(action,id_po,no_po,id_ven)
{
	if(action == 'Cancel')
	{
		$('#PreviewForCancelPO').hide();
		$('#displayCancelPurchaseOrder').show();
	}
	else if(action == 'Preview')
{
	
	$.post('Cancel_Purchase_Order',{action : 'Preview',id_po:id_po,no_po:no_po,id_ven:id_ven},function (r){
				
		var temp='';
				if(r.data.length > 0)
					{
						var list='<tr>'+
							'<td height="20"  valign="top" style="width: 33%;"><div align="left"><img src="image/zaplogo.gif"></div></td>'+
							/*'<td style="width: 33%;border-left-style: none;"><div align="center" style="margin-top: 33px;font-size: large;"><b>PURCHASE ORDER</b> </div></td>'+
							*/
							'<td style="width: 33%;border-left-style: none;">'+
							'<font size="2"><b>HEAD OFFICE :<br>'+r.company[0].nm_com+'<br>'+r.company[0].add1+'<br>'+r.company[0].add2+'<br>'+r.company[0].city+'<br>'+r.company[0].country+'<br>'+r.company[0].pin+'</b></font>'+
							'</td>'+
							
							'<td valign="top" style="border-left-style: none;"><b>'+
							'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.data[0].nm_subl+'<br>'+r.data[0].add1+'<br>'+r.data[0].city+'<br>'+r.data[0].country+'<br>'+r.data[0].pin+'</b></font>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td colspan="2"><b>'+
							'PO Addressed To :<br><font size="2">'+
							r.venDetails[0].nm_ven+'<br>'+
							r.venDetails[0].add1+'<br>'+
							r.venDetails[0].add2+'<br>'+
							r.venDetails[0].city+'<br>'+
							r.venDetails[0].state+'<br>'+
							r.venDetails[0].country+'<br></font>'+
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
					'<table class="table table_bordered" border="1">'+
					'<tr class="tableHeader info">'+
						'<td><strong>Asset Name<a href=#></a></strong></td>'+
						'<td><strong>Description</strong></td>'+
						'<td style="width: 110px;"><strong>Delivery Date</strong></td>'+
						'<td><strong>Quantity </strong></td>'+
						'<td style="width: 115px;"><strong>Unit Price</strong></td>'+
						'<td style="width: 120px;"><strong>Tax Name</strong></td>'+
						'<td style="width: 75px;"><strong>Tax Value</strong></td>'+
						'<td style="border-right: 1px solid #ddd;"><strong>Total Price</strong></td>'+
					'</tr>';
						for(var i=0;i<r.data.length;i++)
						{
						params=r.data[i];
						
						temp += '<tr >'+
						'<td style="width:150px;">'+params.nm_prod+'</td>'+
						'<td>'+params.description+'</td>'+
						'<td>'+params.dtRecv+'</td>'+
						'<td>'+params.qty+'</td>'+
						'<td>'+params.un_prc+'</td>'+
						'<td>'+params.nm_tax+'</td>'+
						'<td>'+params.tax_val+'</td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;">'+params.tot_prc+'</td>'+
						'</tr>';
						}
					//	var grTot = +parseFloat(r.data[0].gr_tot)+parseFloat(r.data[0].insurance)+parseFloat(r.data[0].frieght);
						
						temp +='<tr><td colspan="7"><b style="float:right;">Sub Total</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tot+'</b></td>'+
								'</tr>'+
								/*'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;" colspan="3"><b style="float:right;">Tax Percentage</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_per+'</b></td>'+
								'<td style="border-top-style: none;border-left-style: none;"><b style="float:right;">Tax Price</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_prc+'</b></td>'+
								'</tr>'+*/
								'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="7"><b style="float:right;">Insurance</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].insurance+'</b></td>'+
								'</tr><tr>'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="7"><b style="float:right;">Frieght</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;border-top-style: none;"><b>'+r.data[0].frieght+'</b></td>'+
								'</tr><tr>'+
								'<td style="border-top-style: none;" colspan="7"><b style="float:right;">Grand Total ( '+r.data[0].nm_curr+' )</b></td>'+
								'<td style="width:150px;border-top-style: none;"><b>'+r.data[0].gr_tot+'</b></td>'+
								'</tr>'+
							'</table></td></tr>';
						list +=temp;
						var arr = r.data[0].po_t_c.split('\n');
						var po_t_c='';
						for(var k=0;k<arr.length;k++)
							{
							po_t_c += arr[k]+'<br>';
							}
						
						list += '<tr><td colspan="3">'+
									'<b>General Terms & Conditions</b><br>'+
									'Mode of Delivery  <b>: '+r.data[0].nm_dlvry+'</b><br>'+
									'Warranty/Guarranty <b>: '+r.data[0].nm_warr+'</b><br>'+
									'<b>Terms & Conditions</b><br>'+
									po_t_c+
									'<br><b>Note :</b>Kindly quote this ref number for in any of your supply document and attach the copy of P.O to the bill.<br>'+ 
									'Subject to Bangalore Juridiction.<br>'+
									
							'</td></tr>';
						
						
					$('.DataForCancelPO').html(list);
					/*$('#buttonForPoPrint').html(button);*/
					$('#displayCancelPurchaseOrder').hide();
					$('#PreviewForCancelPO').show();
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				
			},'json');

}
}