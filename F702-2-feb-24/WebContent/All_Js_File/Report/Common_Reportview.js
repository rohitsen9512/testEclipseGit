function ControlReportView(action,displayDivId,hideDivId)
{
	switch(action)
	{
	case "ClosedGRN":
		var t=ControleGRN();
		if(t)
			{
				$('#'+hideDivId).hide();
				$('#'+displayDivId).show();
				DisplayClosedGRN();
			}
		
        break;
        
	case "AMCWarranty":
		var t=ControleAMCAndCAL('datepicker11');
		if(t)
			{
				$('#'+hideDivId).hide();
				$('#'+displayDivId).show();
				AMCWarranty();
			}
        break;
        
	case "RejectGRN":
		var t=ControleGRN();
		if(t)
		{
			$('#'+hideDivId).hide();
			$('#'+displayDivId).show();
			DisplayRejectGRN();
		}
        break;  
        
	case "PendingGRNApproval":
		var t=ControleGRN();
		if(t)
		{
			$('#'+hideDivId).hide();
			$('#'+displayDivId).show();
			DisplayPendingGRNApproval();
		}
        break; 
        
	case "InvoiceDetails":
		var t=ControleGRN();
		if(t)
		{
			$('#'+hideDivId).hide();
			$('#'+displayDivId).show();
			DisplayInvoiceDetails();
		}
        break; 
        
        
	case "LocationAsset":
		DisplayLocationAsset();
        break;   
        
	case "EmpDetails":
		var t =ControleEmployee();
		if(t)
			{
				$('#'+hideDivId).hide();
				$('#'+displayDivId).show();
				DisplayEmpDetailsReport();
			}
		
        break; 
        
	}
}


function DisplayInvoiceDetails()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="startdate"]').val();
	var dt_to =$('input[name="enddate"]').val();
	
		$.post('CommonReport',{action : 'InvoiceDetails',dt_frm:dt_frm,dt_to:dt_to},function (r){

			var list= '<tr>'+
			'<td colspan="19" class="tableHeader"><p class="tableHeaderContent" style="margin-left:450px;">Invoice Detail Report</p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><strong>SL No</strong></td>'+
				'<td><strong>Invoice No</strong></td>'+
				'<td><strong>Invoice DT</strong></td>'+
				'<td><strong>PO No</strong></td>'+
				'<td><strong>PO DT</strong></td>'+
				'<td><strong>Asset Name</strong></td>'+
				'<td><strong>Manufacture</strong></td>'+
				'<td><strong>Model No</strong></td>'+
				'<td><strong>Vendor Name</strong></td>'+
				'<td><strong>INV Loc</strong></td>'+
				'<td><strong>INV SLoc</strong></td>'+
				'<td><strong>Qty</strong></td>'+
				'<td><strong>Unit Price</strong></td>'+
				'<td><strong>Tax Value</strong></td>'+
				'<td><strong>Other</strong></td>'+
				'<td><strong>Duty</strong></td>'+
				'<td><strong>Currency Type</strong></td>'+
				'<td><strong>Total Cost</strong></td>'+
				'<td><strong>Download File</strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				var slno = i +1;
				list = list + '<tr class="success">'+
									'<td><center>'+slno+'</center></td>'+
									'<td><center>'+params.no_inv+'</center></td>'+
									'<td><center>'+params.dt_inv+'</center></td>'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.dt_po+'</center></td>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.model+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tt_val+'</center></td>'+
									'<td><center>'+params.othr+'</center></td>'+
									'<td><center>'+params.duty+'</center></td>'+
									'<td><center>'+params.nm_curr+'</center></td>'+
									'<td><center>'+params.tt+'</center></td>'+
									'<td><center><a href="../InvoiceScanFile/'+params.upload_inv+'" download>'+params.upload_inv+'</a></center></td>'+
									'</tr>';
				}
				list +='<tr><td colspan="19"><strong><center>'+
					'<input type="hidden" name="startdate" value="'+dt_frm+'">'+
					'<input type="hidden" name="enddate" value="'+dt_to+'">'+
					'<button name="cancel" type="submit"  class="btn btn-primary" style="margin-left:10px;">Export To Excel</button>'+
				'</center></strong></td></tr>';
				$('#tablePreviewInvoiceDetails').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="20"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('#tablePreviewInvoiceDetails').html(list);
			}
		
		
	},'json');

			}});

}
function DisplayEmpDetailsReport()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var to_assign = $('select[name="to_assign"]').val();
	
		$.post('CommonReport',{action : 'EmpDetails',to_assign:to_assign},function (r){

			var list= '<tr>'+
			'<td colspan="9" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Employee Asset Detail Report</p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><strong>SL No</strong></td>'+
				'<td><strong>Asset Id</strong></td>'+
				'<td><strong>Asset Name</strong></td>'+
				'<td><strong>Serial No.</strong></td>'+
				'<td><strong>Location</strong></td>'+
				'<td><strong>Sub Location</strong></td>'+
				'<td><strong>Cost Center</strong></td>'+
				'<td><strong>Emp Name </strong></td>'+
				'<td><strong>Assigned Date</strong></td>'+
				
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				var slno = i +1;
				
				list = list + '<tr class="success">'+
									'<td><center>'+slno+'</center></td>'+
									'<td><center>'+params.id_wh_dyn+'</center></td>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.no_mfr+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_cc+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.dt_alloc+'</center></td>'+
									'</tr>';
				}
				list +='<tr><td colspan="9"><strong><center>'+
					'<input type="hidden" name="to_assign" value="'+to_assign+'">'+
					'<button name="cancel" type="submit"  class="btn btn-primary">Export To Excel</button>'+
				'</center></strong></td></tr>';
				$('#tablePreviewEmpDetails').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="12"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('#tablePreviewEmpDetails').html(list);
			}
		
		
	},'json');

			}});

}

function AMCWarranty()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dateMonth =$('input[name="dateMonth"]').val();
	var warr_amc =$('select[name="warr_amc"]').val();
	
		$.post('CommonReport',{action : 'AMCWarranty',warr_amc:warr_amc,dateMonth:dateMonth},function (r){

			var list= '<tr>'+
			'<td colspan="12" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">AMC Warranty Report </p></td>'+
			'</tr>'+
				
			'<tr class="tableHeader info">'+
				'<td><strong>SL No</strong></td>'+
				'<td><strong>Asset Id</strong></td>'+
				'<td><strong>Asset Name</strong></td>'+
				'<td><strong>Serial No.</strong></td>'+
				'<td><strong>Asset Description</strong></td>'+
				'<td><strong>Manufacture</strong></td>'+
				'<td><strong>Model</strong></td>'+
				'<td><strong>Vendor Name</strong></td>'+
				'<td><strong>Location</strong></td>'+
				'<td><strong>Sub Location</strong></td>'+
				'<td><strong>Start Date</strong></td>'+
				'<td><strong>End Date</strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				var slno = i +1;
				
				list = list + '<tr class="success">'+
									'<td><center>'+slno+'</center></td>'+
									'<td><center>'+params.id_wh_dyn+'</center></td>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.no_mfr+'</center></td>'+
									'<td><center>'+params.ds_asst+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.no_model+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.dt_amc_start+'</center></td>'+
									'<td><center>'+params.dt_amc_exp+'</center></td>'+
									'</tr>';
				}
				list +='<tr><td colspan="12"><strong><center>'+
					'<input type="hidden" name="dateMonth" value="'+dateMonth+'">'+
					'<input type="hidden" name="warr_amc" value="'+warr_amc+'">'+
					'<button name="cancel" type="submit"  class="btn btn-primary">Export To Excel</button>'+
				'</center></strong></td></tr>';
				$('#tablePreviewAMCWarranty').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="12"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('#tablePreviewAMCWarranty').html(list);
			}
		
		
	},'json');

			}});

}


function DisplayClosedGRN()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="startdate"]').val();
	var dt_to =$('input[name="enddate"]').val();
	
		$.post('CommonReport',{action : 'ClosedGRN',dt_frm:dt_frm,dt_to:dt_to},function (r){

			var list= '<tr>'+
			'<td colspan="20" class="tableHeader"><p class="tableHeaderContent" style="margin-left:450px;">GRN Report </p></td>'+
			'</tr>'+
				
			'<tr class="tableHeader info">'+
				'<td><strong>SL No</strong></td>'+
				'<td><strong>Asset Id</strong></td>'+
				'<td><strong>GRN No</strong></td>'+
				'<td><strong>GRN DT</strong></td>'+
				'<td><strong>Asset Name</strong></td>'+
				'<td><strong>Manufacture</strong></td>'+
				'<td><strong>Bonded</strong></td>'+
				'<td><strong>BOE NO</strong></td>'+
				'<td><strong>Total Qty</strong></td>'+
				'<td><strong>Receive Qty</strong></td>'+
				'<td><strong>PO No</strong></td>'+
				'<td><strong>PO DT</strong></td>'+
				'<td><strong>Invoice No</strong></td>'+
				'<td><strong>Invoice DT</strong></td>'+
				'<td><strong>DC No</strong></td>'+
				'<td><strong>DC Date</strong></td>'+
				'<td><strong>INV Loc</strong></td>'+
				'<td><strong>INV SLoc</strong></td>'+
				'<td><strong>Vendor Name</strong></td>'+
				'<td><strong>Remarks</strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				var slno = i +1;
				
				list = list + '<tr class="success">'+
									'<td><center>'+slno+'</center></td>'+
									'<td><center>'+params.id_wh_dyn+'</center></td>'+
									'<td><center>'+params.no_grn+'</center></td>'+
									'<td><center>'+params.dt_grn+'</center></td>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.bd+'</center></td>'+
									'<td><center>'+params.no_boe+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.qty_recv+'</center></td>'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.dt_po+'</center></td>'+
									'<td><center>'+params.no_inv+'</center></td>'+
									'<td><center>'+params.dt_inv+'</center></td>'+
									'<td><center>'+params.no_dc+'</center></td>'+
									'<td><center>'+params.dt_dc+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.remarks+'</center></td>'+
									'</tr>';
				}
				list +='<tr><td colspan="20"><strong><center>'+
					'<input type="hidden" name="startdate" value="'+dt_frm+'">'+
					'<input type="hidden" name="enddate" value="'+dt_to+'">'+
					'<button name="cancel" type="submit"  class="btn btn-primary" style="margin-left:10px;">Export To Excel</button>'+
				'</center></strong></td></tr>';
				$('#tablePreviewClosedGRN').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="20"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('#tablePreviewClosedGRN').html(list);
			}
		
		
	},'json');

			}});

}

function DisplayRejectGRN()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="startdate"]').val();
	var dt_to =$('input[name="enddate"]').val();
	
		$.post('CommonReport',{action : 'RejectGRN',dt_frm:dt_frm,dt_to:dt_to},function (r){

			var list= '<tr>'+
			'<td colspan="20" class="tableHeader"><p class="tableHeaderContent" style="margin-left:450px;">Reject GRN Report </p></td>'+
			'</tr>'+
				
			'<tr class="tableHeader info">'+
				'<td><strong>SL No</strong></td>'+
				'<td><strong>GRN No</strong></td>'+
				'<td><strong>GRN DT</strong></td>'+
				'<td><strong>Asset Name</strong></td>'+
				'<td><strong>Manufacture</strong></td>'+
				'<td><strong>Bonded</strong></td>'+
				'<td><strong>BOE NO</strong></td>'+
				'<td><strong>Receive Qty</strong></td>'+
				'<td><strong>PO No</strong></td>'+
				'<td><strong>PO DT</strong></td>'+
				'<td><strong>Invoice No</strong></td>'+
				'<td><strong>Invoice DT</strong></td>'+
				'<td><strong>DC No</strong></td>'+
				'<td><strong>DC Date</strong></td>'+
				'<td><strong>INV Loc</strong></td>'+
				'<td><strong>INV SLoc</strong></td>'+
				'<td><strong>Vendor Name</strong></td>'+
				'<td><strong>Remarks</strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				var slno = i +1;
				
				list = list + '<tr class="success">'+
									'<td><center>'+slno+'</center></td>'+
									'<td><center>'+params.no_grn+'</center></td>'+
									'<td><center>'+params.dt_grn+'</center></td>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.bd+'</center></td>'+
									'<td><center>'+params.no_boe+'</center></td>'+
									'<td><center>'+params.qty_recv+'</center></td>'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.dt_po+'</center></td>'+
									'<td><center>'+params.no_inv+'</center></td>'+
									'<td><center>'+params.dt_inv+'</center></td>'+
									'<td><center>'+params.no_dc+'</center></td>'+
									'<td><center>'+params.dt_dc+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.remarks+'</center></td>'+
									'</tr>';
				}
				list +='<tr><td colspan="20"><strong><center>'+
					'<input type="hidden" name="startdate" value="'+dt_frm+'">'+
					'<input type="hidden" name="enddate" value="'+dt_to+'">'+
					'<button name="cancel" type="submit"  class="btn btn-primary" style="margin-left:-500px;">Export To Excel</button>'+
				'</center></strong></td></tr>';
				$('#tablePreviewRejectGRN').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="20"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('#tablePreviewRejectGRN').html(list);
			}
		
		
	},'json');

			}});

}

function DisplayPendingGRNApproval()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="startdate"]').val();
	var dt_to =$('input[name="enddate"]').val();
	
		$.post('CommonReport',{action : 'PendingGRNApproval',dt_frm:dt_frm,dt_to:dt_to},function (r){

			var list= '<tr>'+
			'<td colspan="21" class="tableHeader"><p class="tableHeaderContent" style="margin-left:450px;">Pending GRN Approval Report </p></td>'+
			'</tr>'+
				
			'<tr class="tableHeader info">'+
				'<td><strong>SL No</strong></td>'+
				'<td><strong>GRN No</strong></td>'+
				'<td><strong>GRN DT</strong></td>'+
				'<td><strong>Asset Name</strong></td>'+
				'<td><strong>Manufacture</strong></td>'+
				'<td><strong>Bonded</strong></td>'+
				'<td><strong>BOE NO</strong></td>'+
				'<td><strong>Total Qty</strong></td>'+
				'<td><strong>Receive Qty</strong></td>'+
				'<td><strong>PO No</strong></td>'+
				'<td><strong>PO DT</strong></td>'+
				'<td><strong>Invoice No</strong></td>'+
				'<td><strong>Invoice DT</strong></td>'+
				'<td><strong>DC No</strong></td>'+
				'<td><strong>DC Date</strong></td>'+
				'<td><strong>INV Loc</strong></td>'+
				'<td><strong>INV SLoc</strong></td>'+
				'<td><strong>Vendor Name</strong></td>'+
				'<td><strong>Remarks</strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				var slno = i +1;
				
				list = list + '<tr class="success">'+
									'<td><center>'+slno+'</center></td>'+
									'<td><center>'+params.no_grn+'</center></td>'+
									'<td><center>'+params.dt_grn+'</center></td>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.bd+'</center></td>'+
									'<td><center>'+params.no_boe+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.qty_recv+'</center></td>'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.dt_po+'</center></td>'+
									'<td><center>'+params.no_inv+'</center></td>'+
									'<td><center>'+params.dt_inv+'</center></td>'+
									'<td><center>'+params.no_dc+'</center></td>'+
									'<td><center>'+params.dt_dc+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.remarks+'</center></td>'+
									'</tr>';
				}
				list +='<tr><td colspan="21"><strong><center>'+
					'<input type="hidden" name="startdate" value="'+dt_frm+'">'+
					'<input type="hidden" name="enddate" value="'+dt_to+'">'+
					'<button name="cancel" type="submit"  class="btn btn-primary" style="margin-left:10px;">Export To Excel</button>'+
				'</center></strong></td></tr>';
				$('#tablePreviewPendingGRN').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="20"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('#tablePreviewPendingGRN').html(list);
			}
		
		
	},'json');

			}});

}

function DisplayLocationAsset()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="startdate"]').val();
	var dt_to =$('input[name="enddate"]').val();
	
		$.post('CommonReport',{action : 'LocationAsset',dt_frm:dt_frm,dt_to:dt_to},function (r){

			var list= '<tr>'+
			'<td colspan="20" class="tableHeader"><p class="tableHeaderContent" style="margin-left:450px;">Pending GRN Approval Report </p></td>'+
			'</tr>'+
				
			'<tr class="tableHeader info">'+
				'<td><strong>SL No</strong></td>'+
				'<td><strong>GRN No</strong></td>'+
				'<td><strong>GRN DT</strong></td>'+
				'<td><strong>Asset Name</strong></td>'+
				'<td><strong>Manufacture</strong></td>'+
				'<td><strong>Bonded</strong></td>'+
				'<td><strong>BOE NO</strong></td>'+
				'<td><strong>Total Qty</strong></td>'+
				'<td><strong>PO No</strong></td>'+
				'<td><strong>PO DT</strong></td>'+
				'<td><strong>Invoice No</strong></td>'+
				'<td><strong>Invoice DT</strong></td>'+
				'<td><strong>DC No</strong></td>'+
				'<td><strong>DC Date</strong></td>'+
				'<td><strong>INV Loc</strong></td>'+
				'<td><strong>INV SLoc</strong></td>'+
				'<td><strong>Vendor Name</strong></td>'+
				'<td><strong>Remarks</strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				var slno = i +1;
				
				list = list + '<tr class="success">'+
									'<td><center>'+slno+'</center></td>'+
									'<td><center>'+params.no_grn+'</center></td>'+
									'<td><center>'+params.dt_grn+'</center></td>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.bd+'</center></td>'+
									'<td><center>'+params.no_boe+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.dt_po+'</center></td>'+
									'<td><center>'+params.no_inv+'</center></td>'+
									'<td><center>'+params.dt_inv+'</center></td>'+
									'<td><center>'+params.no_dc+'</center></td>'+
									'<td><center>'+params.dt_dc+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.remarks+'</center></td>'+
									'</tr>';
				}
				list +='<tr><td colspan="20"><strong><center>'+
					'<input type="hidden" name="startdate" value="'+dt_frm+'">'+
					'<input type="hidden" name="enddate" value="'+dt_to+'">'+
					'<button name="cancel" type="submit"  class="btn btn-primary" style="margin-left:10px;">Export To Excel</button>'+
				'</center></strong></td></tr>';
				$('#"tablePreviewGRNApprovePending"').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="20"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('#"tablePreviewGRNApprovePending"').html(list);
			}
		
		
	},'json');

			}});

}