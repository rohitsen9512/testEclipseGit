function ControlPrintMailPO2(HideDiv,DispDiv,id_po,id_ven)  
{
	$.post('Print_Mail_PO',{action : 'Preview2',id_po:id_po,id_ven:id_ven},function (r){		
		var temp='';
				/*if(r.data.length > 0)
					{*/
		var totalVal=0;
		var grandTtotal=0;
		var total_tax_val1=0;
		var total_tax_val2=0;
					var slno=0;
					var x = 0;
					var list="";
					for( x = 0; x < r.data.length ; x++)
						{
						if('+r.venDetails[0].fax+'==undefined){fax='';}else{fax='+r.venDetails[0].fax+';}
						list +='<table class="table  table-bordered DisplayAmendPurchaseOrder" align="center" style="width:92%;border: 2px solid black;page-break-before:always" >'+
							'  <thead class="">'+
							'<tr id="header2" style="line-height: 20px;"">'+
							
						'<td  class="tdClass" colspan="2"  valign="top" style="width: 20%;height:40%;border: 1px solid black; "><div align="left" style="margin-top:30px;"><img src="InvoiceScanFile/'+r.company[0].file_name+'"></div></td>'+
				     	'<td class="tdClass" colspan="5" style="border: 1px solid black;"><font size="2"><b><b style="font-size: medium;">'+r.company[0].nm_com+'</b></font></center> <b style="float:right;font-size: 12px;"></b><br><font size="2"><b><b style="font-size: medium;">'+
						'<b style="font-size: medium;">'+r.company[0].add1+'&nbsp;&nbsp;'+r.company[0].add2+'&nbsp;&nbsp;'+r.company[0].city+'-'+r.company[0].pin+'</b><br>'+
						'<b style="font-size: small;">Tel :'+r.company[0].phone+'&nbsp;&nbsp;&nbsp;&nbsp;Fax:'+r.company[0].fax+'</b><br>'+
						'<b style="font-size: small;">e-mail :'+r.company[0].mailid+'</b><br>'+
						'<b style="font-size: small;">GSTIN-NO :'+r.company[0].cst+'&nbsp;&nbsp;&nbsp;&nbsp;CIN-NO :'+r.company[0].cin+'&nbsp;&nbsp;&nbsp;&nbsp;PAN-NO :'+r.company[0].pan+'</b>'+
						' </td>'+
						'  </tr>'+
						'<tr border: 1px solid black;">'+
							
					     	'<td  colspan="7" class="tdClass" style="border: 1px solid black;"><center><font size="2"><b><b style="font-size: medium;">PURCHASE ORDER W/I</b></font></center> <b style="float:right;font-size: 12px;"></b><center><font size="2"><b><b style="font-size: medium;">'+
							
							'  </tr>'+
							
							
							
							'<tr  id="header2" style="line-height: 20px;">'+
						'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-bottom: none"><Strong>Supplier :</Strong></td>'+
						'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-bottom: none">'+r.venDetails[0].nm_ven+'</td>'+
						'<td class="tdClass" colspan="2" style="border: 1px solid black;;border-bottom: none"><center><strong>GST Details</strong></center></td>'+
					'</tr>'+
					
					'<tr  style="line-height: 20px;">'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none"><Strong>Vendor Code :</Strong></td>'+
					'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none">'+r.venDetails[0].cd_ven+'</td>'+
					'<td class="tdClass" colspan="2"  style="border: 1px solid black;;border-top: none;border-bottom: none"><strong>Delivery Address :</strong></td>'+
				'</tr>'+
				
				'<tr  style="line-height: 20px;">'+
				'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none"><Strong>Address :</Strong></td>'+
				'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none">'+r.venDetails[0].add1+'</td>'+
				'<td class="tdClass" colspan="2" rowspan="3" style="border: 1px solid black;;border-top: none;border-bottom: none">'+r.data[0].remarks+'</td>'+
				
			'</tr>'+
			
			'<tr  style="line-height: 20px;">'+
			'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none"><Strong>Phone No :</Strong></td>'+
			'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none">'+r.venDetails[0].phone+'</td>'+
			
		'</tr>'+
		
		'<tr  style="line-height: 20px;">'+
		'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none"><Strong>Fax No :</Strong></td>'+
		'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none">'+r.venDetails[0].fax+'</td>'+
		
	'</tr>'+
	
	'<tr  style="line-height: 20px;">'+
	'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;"><Strong>Contact person  :</Strong></td>'+
	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;">'+r.venDetails[0].cnt_pr1+'</td>'+
	'<td class="tdClass" colspan="2"  style="border: 1px solid black;;border-top: none;"><strong>GSTIN NO. :</strong>&nbsp;&nbsp;&nbsp;&nbsp;'+r.venDetails[0].gstin+'</td>'+
	'</tr>'+

	'<tr  style="line-height: 20px;">'+
	'<td class="tdClass" colspan="2" style="border: 1px solid black;;border-top: none;border-right: none;"><Strong><center>PO Number </Strong></center></td>'+
	'<td class="tdClass" colspan="3" style="border: 1px solid black;;border-top: none;"><Strong><center>PO Date</Strong></center></td>'+
	'<td class="tdClass" colspan="2" rowspan="3" style="border: 1px solid black;;border-top: none;"><Strong>Delivery Term :</Strong></td>'+
	'</tr>'+
	
	'<tr  style="line-height: 20px;">'+
	'<td class="tdClass" colspan="2" style="border: 1px solid black;border-top: none;border-right: none;"><center>'+r.data[0].no_po+'</center></td>'+
	'<td class="tdClass"  colspan="3" style="border: 1px solid black;;border-top: none;"><center>'+r.data[0].dtpo+'</center></td>'+
	'</tr>'+
						
	'<tr  style="line-height: 20px;">'+
	'<td class="tdClass" colspan="5" style="border: 1px solid black; align:right;border-top: none;"><Strong>Your Quotation Ref No. :</Strong></td>'+
	'</tr>';

					temp='  <tbody><tr class="tableHeader info">'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong >SL No.</strong></td>'+
					'<td colspan="2" class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong >Item Name</strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong >Item Code</strong></td>'+
					//'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong >UOM</strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong >Quantity</strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong >Rate/Unit</strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong >Total Price </strong></td>'+
					'</tr>';
					
					
				
					var total_qty=0;
					
					var total_tax_val=0;
					
			var totalPrice=0,subTotalPrice=0;
			if(r.data.length > 0)
			{
				 var z=x+16;
				 
					if(z > r.data.length){
						z=r.data.length;
						
					}
					for(var i = x; i < z ; i++)
				{
				params=r.data[i];
				params = r.data[i];	
				total_price= parseFloat(params.qty) *parseFloat(params.un_prc);
				totalVal +=total_price;
				total_tax_val1 +=parseFloat(params.tax_val1);
				//alert(total_tax_val1.toFixed(2));
				total_tax_val2 +=parseFloat(params.tax_val2);
				grandTtotal +=parseFloat(params.grand_total);
				temp += '<tr>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+ ++slno +'.</center></td>'+	
					'<td colspan="2" class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.nm_model+'</center></td>'+	
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.cd_model+'</center></td>'+
					//'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.nm_uom+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.qty+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.un_prc+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+total_price.toFixed(2)+'</center></td>'+
				'</tr>';
				x=i;
						}
					var shubhamcurr=parseFloat(grandTtotal);
					var word=number2text(shubhamcurr);
					console.log(word);
					var upload_po='';
			if(r.data[0].upload_po != undefined)
				{upload_po=r.data[0].file_name;}
			var tc_name='';
			if(r.tc_name != undefined)
				{tc_name=r.t_and_c;}
				list +=temp;
				
				    
			}   
			
			console.log(x);
			console.log(r.data.length);
			if(x+1 == r.data.length){		
			list+=
		
		'<tr>'+
		'<td class="tdClass" colspan="5" rowspan="2" style="text-align:left;font-size: medium;border: 1px solid black;" ><strong>Amount In Words :</strong>'+word+'</td>'+
		'<td class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">Net Total</td>'+
		'<td class="tdClass" colspan="1" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">&nbsp;'+totalVal.toFixed(2)+'</td>'+
	'</tr>'+
		
	 '<tr>'+
		'<td class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">P&F Charges %</td>'+
		'<td class="tdClass" style="border: 1px solid black;border-left: none;border-bottom: none;">&nbsp;'+r.data[0].others+'</td>'+
	  '</tr>'+
	  
	  '<tr>'+
		'<td  class="tdClass" style="text-align:left;border: 1px solid black;border-top: none;border-right: none;border-bottom: none;" colspan=2""><strong>Payment Term :</strong></td>'+
		'<td class="tdClass" colspan="3" style="text-align:left;border: 1px solid black;border-top: none;border-left: none;border-bottom: none;" >&nbsp;'+r.data[0].nm_dlvry+'</td>'+
		'<td class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">Fright Charges</td>'+
		'<td class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">&nbsp;&nbsp;'+r.data[0].others+'</td>'+
	'</tr>'+	
	
	 '<tr>'+
		'<td   class="tdClass" rowspan="3" colspan="2" style="text-align:left;border: 1px solid black;border-top: none;border-right: none;" ><strong>Dispatch Through :</strong></td>'+
		'<td class="tdClass" colspan="3" rowspan="3" style="text-align:left;border: 1px solid black;border-top: none;border-left: none;" >&nbsp;'+r.data[0].mode_of_delv+'</td>'+
		'<td class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">'+r.data[0].nm_tax1+'</td>'+
		'<td  class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">'+total_tax_val1.toFixed(2)+'</td>'+
	'</tr>'+
	
	'<tr>'+
	'<td class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">'+r.data[0].nm_tax2+'</td>'+
	'<td   class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;border-bottom: none;">'+total_tax_val2.toFixed(2)+'</td>'+
	'</tr>'+
	
	'<tr>'+
	'<td class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;">Gross Total:</td>'+
	'<td  class="tdClass" style="text-align:left;border: 1px solid black;border-left: none;"><strong>'+r.data[0].tot+'<strong></td>'+
	' </tr>'+
		
				'<tr>'+
				   '<td class="tdClass" colspan="7" style="text-align:left;border: 1px solid black;"><strong >Test Certificate<br></strong ></td>'+
				   '</tr>'+
				   
				  
				    '<tr>'+
					//'<td class="tdClass" colspan="3" style="text-align:left;border: 1px solid black;"><strong>Terms And Conditions :</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+r.data[0].t_and_c+'</td>'+
					'<td class="tdClass" colspan="3"><b style="float:right;">Click for download Term & Conditions :</b></td>'+
		//'<a href="download.jsp?fileName1='+r.uploadedFile+'" target="_blank"> '+tc_name+'</a><br>'+
			'<a href="download.jsp?fileName1='+r.data[0].upload_po+'" target="_blank"> '+upload_po+'</a>'+
			
				'</td>'+
					'<td class="tdClass" colspan="4" style=" font-size:17px;border: 1px solid black;"><center><strong>'+r.company[0].nm_com+'</strong></center><br>'+
					'&nbsp;<br>&emsp;'+
					'<center><SPAN STYLE="text-decoration:overline"><strong>Authorised Signatory</strong></SPAN></center></td>'+
					
					' </tbody></tr>';
			}
				var	temp = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPrintorMailPurchaseOrder\')">Print Preview </button>&nbsp;&nbsp;';

						
							$('#buttonForPoPrint').html(temp);
							$('#displayPrintorMailPurchaseOrder').hide();
							$('#createPrintorMailPurchaseOrder').show();
							$('.DisplayAmendPurchaseOrder').html(list);
						}
                    	
				
			},'json');

}
function number2text(value) {
    var fraction = Math.round(frac(value)*100);
    var f_text  = "";

    if(fraction > 0) {
        f_text = "and "+convert_number(fraction)+" ";
    }

    return convert_number(value)+"  "+f_text+" Only";
}

function frac(f) {
    return f % 1;
}

function convert_number(number)
{
    if ((number < 0) || (number > 999999999)) 
    { 
        return "NUMBER OUT OF RANGE!";
    }
    var Gn = Math.floor(number / 10000000);  /* Crore */ 
    number -= Gn * 10000000; 
    var kn = Math.floor(number / 100000);     /* lakhs */ 
    number -= kn * 100000; 
    var Hn = Math.floor(number / 1000);      /* thousand */ 
    number -= Hn * 1000; 
    var Dn = Math.floor(number / 100);       /* Tens (deca) */ 
    number = number % 100;               /* Ones */ 
    var tn= Math.floor(number / 10); 
    var one=Math.floor(number % 10); 
    var res = ""; 

    if (Gn>0) 
    { 
        res += (convert_number(Gn) + " Crore"); 
    } 
    if (kn>0) 
    { 
            res += (((res=="") ? "" : " ") + 
            convert_number(kn) + " Lakh"); 
    } 
    if (Hn>0) 
    { 
        res += (((res=="") ? "" : " ") +
            convert_number(Hn) + " Thousand"); 
    } 

    if (Dn) 
    { 
        res += (((res=="") ? "" : " ") + 
            convert_number(Dn) + " Hundred"); 
    } 


    var ones = Array("", "One", "Two", "Three", "Four", "Five", "Six","Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen","Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen","Nineteen"); 
var tens = Array("", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty","Seventy", "Eighty", "Ninety"); 

    if (tn>0 || one>0) 
    { 
        if (!(res=="")) 
        { 
            res += "  "; 
        } 
        if (tn < 2) 
        { 
            res += ones[tn * 10 + one]; 
        } 
        else 
        { 

            res += tens[tn];
            if (one>0) 
            { 
                res += (" " + ones[one]); 
            } 
        } 
    }

    if (res=="")
    { 
        res = "zero"; 
    } 
    return res;
}
function ControlPrintMailPO(action,DispDiv,HideDiv,id_po,no_po,id_ven)
{
	
if(action == 'Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DispDiv).show();
	}

if(action == 'Preview')
{
	$.post('Print_Mail_PO',{action : 'Preview',id_po:id_po,no_po:no_po,id_ven:id_ven},function (r){
				
		var temp='';
				if(r.data.length > 0)
					{
						var list='<tr>'+
							'<td height="20"  valign="top" style="width: 50%;"><div align="left"><img src="image/rbi.jpg"></div></td>'+
							
							'<td valign="top" style="border-left-style: none;"><b>'+
							//'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.data[0].nm_subl+'<br>'+r.data[0].add1+'<br>'+r.data[0].city+'<br>'+r.data[0].country+'<br>'+r.data[0].pin+'</b></font>'+
							'<font size="2"><b>Proposal For Financial Approval</b></font>'+
							'<b style="margin-left: 15%;">Date: '+r.todaydate+'</b>'+
							'</td>'+
							//'<td style="width: 33%;border-left-style: none;"><div align="center" style="margin-top: 33px;font-size: large;"><b>PURCHASE ORDER</b> </div></td>'+
							/*'<td style="width: 33%;border-left-style: none;">'+
							//'<font size="2"><b>HEAD OFFICE :<br>'+r.company[0].nm_com+'<br>'+r.company[0].add1+'<br>'+r.company[0].add2+'<br>'+r.company[0].city+'<br>'+r.company[0].country+'<br>'+r.company[0].pin+'</b></font>'+
							'</td>'+
							
							'<td valign="top" style="border-left-style: none;"><b>'+
							//'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.data[0].nm_subl+'<br>'+r.data[0].add1+'<br>'+r.data[0].city+'<br>'+r.data[0].country+'<br>'+r.data[0].pin+'</b></font>'+
							'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.company[0].add1+'<br>'+r.company[0].add2+'<br>'+r.company[0].city+'<br>'+r.company[0].country+'<br>'+r.company[0].pin+'</b></font>'+
							
							'</td>'+*/
						'</tr>'+
						'<tr>'+
							'<td ><b>'+
							'<table class="table table-bordered">'+
							'<tr>'+
								'<td>P.O Number</td>'+
								'<td><b>'+r.data[0].no_po+'</b></td>'+
							'<tr>'+
								'<td>P.O Date</td>'+
								'<td><b>'+r.data[0].dtpo+'</b></td>'+
							'</tr>'+
							'<tr>'+
								'<td>Indent Number</td>'+
								'<td><b>'+r.data[0].no_ind+'</b></td>'+
							'</tr>'+
							'<tr>'+
								'<td>Proposal Number </td>'+
								'<td><b>'+r.data[0].propsl_num+'</b></td>'+
							'</tr>'+
							
							'<tr>'+
								'<td>Tender Number</td>'+
								'<td><b>'+r.data[0].tender_num+'</b></td>'+
							'</tr>'+
							'<tr>	'+
								'<td>Nature</td>'+
								'<td><b>'+r.data[0].nature+'</b></td>'+
							'</tr>'+
							'<tr>	'+
								'<td>Repeat Order</td>'+
								'<td><b>'+r.data[0].repeat_order+'</b></td>'+
							'</tr>'+
						'</table>'+
							/*'PO Addressed To :<br><font size="2">'+
							r.venDetails[0].nm_ven+'<br>'+
							r.venDetails[0].add1+'<br>'+
							r.venDetails[0].add2+'<br>'+
							r.venDetails[0].city+'<br>'+
							r.venDetails[0].state+'<br>'+
							r.venDetails[0].country+'<br></font>'+*/
							'</td>'+
							'<td>'+
							'<table class="table table-bordered">'+
								'<tr>'+
									'<td style="width: 50%;">Indent & Approved By</td>'+
									'<td><b>'+r.data[0].indnt_approved_by+'</b></td>'+
								'<tr>'+
									'<td>Requirement</td>'+
									'<td><b>'+r.data[0].requirement+'</b></td>'+
								'</tr>'+
								'<tr>'+
									'<td>Quotation Obtained Vide</td>'+
									'<td><b>'+r.data[0].quot_obtn_vid+'</b></td>'+
								'</tr>'+
								
								'<tr>'+
									'<td>Enquiry Sent/Tender paper sold to</td>'+
									'<td><b>'+r.data[0].enqry_sent+'</b></td>'+
								'</tr>'+
								'<tr>	'+
									'<td>No of parties Responded</td>'+
									'<td><b>'+r.data[0].num_party_respon+'</b></td>'+
								'</tr>'+
								'<tr>	'+
									'<td>No of parties qualified for consideration</td>'+
									'<td><b>'+r.data[0].num_party_qualify+'</b></td>'+
								'</tr>'+
							'</table>'+
							'</td>'+
							
						'</tr>	'+
						'<tr>'+
							'<td colspan="3"><b>NOTE : </b>With reference to your quotation we are pleased to place an order on you as per the following terms and conditions:</td>'+
						'</tr>	';
					temp='<tr><td colspan="2">'+
							'<table class="table table_bordered" border="1">'+
							'<tr class="tableHeader info">'+
								'<td><strong>Item Name<a href=#></a></strong></td>'+
								'<td><strong>Item Code</strong></td>'+
								
								//'<td style="width: 110px;"><strong>Delivery Date</strong></td>'+
								'<td><strong>Item Price </strong></td>'+
								'<td style="width: 75px;"><strong><center>Others</center></strong></td>'+
								'<td style="width: 120px;"><strong>Tax Name</strong></td>'+
								'<td style="width: 75px;"><strong>Tax Value</strong></td>'+
								
								'<td style="width: 115px;"><strong>Unit Price</strong></td>'+
								'<td><strong>Quantity </strong></td>'+
								'<td style="border-right: 1px solid #ddd;"><strong>Total Price</strong></td>'+
							'</tr>';
						for(var i=0;i<r.data.length;i++)
						{
						params=r.data[i];
						
						temp += '<tr >'+
						'<td style="width: 800px;">'+params.nm_model+'</td>'+
						'<td>'+params.cd_model+'</td>'+
						
						'<td>'+params.un_prc+'</td>'+
						'<td>'+params.others+'</td>'+
						
						'<td>'+params.nm_tax+'</td>'+
						'<td>'+params.tax_val+'</td>'+
						'<td>'+params.un_prc+'</td>'+
						'<td>'+params.qty+'</td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;">'+params.tot_prc+'</td>'+
						'</tr>';
						}
						//var grTot = +parseFloat(r.data[0].gr_tot)+parseFloat(r.data[0].insurance)+parseFloat(r.data[0].frieght);
						
						temp +='<tr><td colspan="11"><b style="float:right;">Sub Total</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tot+'</b></td>'+
								'</tr>'+
								/*'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;" colspan="3"><b style="float:right;">Tax Percentage</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_per+'</b></td>'+
								'<td style="border-top-style: none;border-left-style: none;"><b style="float:right;">Tax Price</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_prc+'</b></td>'+
								'</tr>'+*/
								'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="11"><b style="float:right;">Installation Charge</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].frt_rate+'</b></td>'+
								'</tr><tr>'+
								'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="11"><b style="float:right;">Additional charges</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].add_chrg+'</b></td>'+
								'</tr><tr>'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="11"><b style="float:right;">Others Charge</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;border-top-style: none;"><b>'+r.data[0].frt_text+'</b></td>'+
								'</tr><tr>'+
								'<td style="border-top-style: none;" colspan="11"><b style="float:right;">Grand Total ('+r.data[0].nm_curr+')</b></td>'+
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
						
						list += '<tr><td colspan="2">'+
									'<b>Remarks: Details Terms & Conditions for this PO is given as annexure 1</b><br>'+
									/*'Mode of Delivery  <b>: '+r.data[0].nm_dlvry+'</b><br>'+
									'Warranty/Guarranty <b>: '+r.data[0].nm_warr+'</b><br>'+*/
									//'<b>Terms & Conditions</b><br>'+
									//po_t_c+
									//'<br><b>Note :</b>Kindly quote this ref number for in any of your supply document and attach the copy of P.O to the bill.<br>'+ 
									//'Subject to Bangalore Juridiction.'+
									'<br><br>'+
									'<span style="margin-left: 10%;">Signature 1: .....................................</span>'+
									'<span style="margin-left: 10%;">Signature 2: .....................................</span>'+
									'<span style="margin-left: 10%;">Signature 3: .....................................</span>'+
									'<br>'+
								'</td></tr>';
						
							var button=	'<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPurchaseOrder(\'#createPrintorMailPurchaseOrder\')">Print Preview </button>&nbsp;&nbsp;';
						
						
					$('.DisplayAmendPurchaseOrder').html(list);
					$('#buttonForPoPrint').html(button);
					$('#'+HideDiv).hide();
					$('#'+DispDiv).show();
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
				}
				
			},'json');

}
if(action == 'VOPreview')
{
	$.post('Print_Mail_PO',{action : 'VOPreview',id_po:id_po,no_po:no_po,id_ven:id_ven},function (r){
				
		var temp='';
				if(r.data.length > 0)
					{
						var list='<tr>'+
							'<td height="20"  valign="top" style="width: 50%;"><div align="left"><img src="image/rbi.jpg"></div></td>'+
							
							'<td valign="top" style="border-left-style: none;"><b>'+
							//'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.data[0].nm_subl+'<br>'+r.data[0].add1+'<br>'+r.data[0].city+'<br>'+r.data[0].country+'<br>'+r.data[0].pin+'</b></font>'+
							'<font size="2"><b>Verbal Order</b></font>'+
							'<b style="margin-left: 15%;">Date: '+r.todaydate+'</b>'+
							'</td>'+
							//'<td style="width: 33%;border-left-style: none;"><div align="center" style="margin-top: 33px;font-size: large;"><b>PURCHASE ORDER</b> </div></td>'+
							/*'<td style="width: 33%;border-left-style: none;">'+
							//'<font size="2"><b>HEAD OFFICE :<br>'+r.company[0].nm_com+'<br>'+r.company[0].add1+'<br>'+r.company[0].add2+'<br>'+r.company[0].city+'<br>'+r.company[0].country+'<br>'+r.company[0].pin+'</b></font>'+
							'</td>'+
							
							'<td valign="top" style="border-left-style: none;"><b>'+
							//'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.data[0].nm_subl+'<br>'+r.data[0].add1+'<br>'+r.data[0].city+'<br>'+r.data[0].country+'<br>'+r.data[0].pin+'</b></font>'+
							'<font size="2"><b>INVOICE & SHIP TO :<br>'+r.company[0].nm_com+'<br>'+r.company[0].add1+'<br>'+r.company[0].add2+'<br>'+r.company[0].city+'<br>'+r.company[0].country+'<br>'+r.company[0].pin+'</b></font>'+
							
							'</td>'+*/
						'</tr>'+
						'<tr>'+
							'<td><b>'+
							'<table class="table table-bordered">'+
							'<tr>'+
								'<td>P.O Number</td>'+
								'<td><b>'+r.data[0].no_po+'</b></td>'+
							'<tr>'+
								'<td>P.O Date</td>'+
								'<td><b>'+r.data[0].dtpo+'</b></td>'+
							'</tr>'+
							'<tr>'+
								'<td>GST Number</td>'+
								'<td><b>'+r.data[0].gst_num+'</b></td>'+
							'</tr>'+
							'<tr>'+
								'<td>WBST Number </td>'+
								'<td><b>'+r.data[0].wbst_num+'</b></td>'+
							'</tr>'+
							
							'<tr>'+
								'<td>BNM Number</td>'+
								'<td><b>'+r.data[0].bnm_num+'</b></td>'+
							'</tr>'+
							'<tr>'+
								'<td>Proposal Number</td>'+
								'<td><b>'+r.data[0].propsl_num+'</b></td>'+
							'</tr>'+
							'<tr>	'+
								'<td>From Section</td>'+
								'<td><b>'+r.data[0].nm_section+'</b></td>'+
							'</tr>'+
							'<tr>	'+
								'<td>Indetor Employee id and name</td>'+
								'<td><b>'+r.data[0].nm_emp+'</b></td>'+
							'</tr>'+
						'</table>'+
							/*'PO Addressed To :<br><font size="2">'+
							r.venDetails[0].nm_ven+'<br>'+
							r.venDetails[0].add1+'<br>'+
							r.venDetails[0].add2+'<br>'+
							r.venDetails[0].city+'<br>'+
							r.venDetails[0].state+'<br>'+
							r.venDetails[0].country+'<br></font>'+*/
							'</td>'+
							'<td style="width: 25%;">'+
							'<b>Supplier Name and Address :</b><br><font size="1">'+
							r.venDetails[0].nm_ven+'<br>'+
							r.venDetails[0].add1+'<br>'+
							r.venDetails[0].add2+'<br>'+
							r.venDetails[0].city+'<br>'+
							r.venDetails[0].state+'<br>'+
							r.venDetails[0].country+'<br></font>'+
							'</td>'+
							
						'</tr>	'+
						'<tr>'+
							'<td colspan="3">'+
							'<p>Approval for purchase on verbal basis & waiver for placing formal purchase order may be accorded as per the following details, as rates are reasonable & the purchase amount is less than RS 15000/- </p>'+
							'<p>Sub: '+r.data[0].vo_subject+' </p>'+
							'</td>'+
						'</tr>	';
					temp='<tr><td colspan="2">'+
							'<table class="table table_bordered" border="1">'+
							'<tr class="tableHeader info">'+
								'<td><strong>Item Name<a href=#></a></strong></td>'+
								'<td><strong>Item Code</strong></td>'+
								'<td><strong>Specification</strong></td>'+
								'<td><strong>UOM</strong></td>'+
								//'<td style="width: 110px;"><strong>Delivery Date</strong></td>'+
								'<td><strong>Item Price </strong></td>'+
								'<td style="width: 75px;"><strong><center>Freight rate </center></strong></td>'+
								'<td style="width: 75px;"><strong><center>Others</center></strong></td>'+
								'<td style="width: 120px;"><strong>Tax Name</strong></td>'+
								'<td style="width: 75px;"><strong>Tax Value</strong></td>'+
								
								'<td style="width: 115px;"><strong>Unit Price</strong></td>'+
								'<td><strong>Quantity </strong></td>'+
								'<td style="border-right: 1px solid #ddd;"><strong>Total Price</strong></td>'+
							'</tr>';
						for(var i=0;i<r.data.length;i++)
						{
						params=r.data[i];
						
						temp += '<tr >'+
						'<td style="width: 800px;">'+params.nm_prod+'</td>'+
						'<td>'+params.cd_prod+'</td>'+
						'<td>'+params.description1+'</td>'+
						'<td>'+params.uom+'</td>'+
						'<td>'+params.item_prc+'</td>'+
						'<td>'+params.freight_rate+'</td>'+
						'<td>'+params.others_chrg+'</td>'+
						
						'<td>'+params.nm_tax+'</td>'+
						'<td>'+params.tax_val+'</td>'+
						'<td>'+params.un_prc+'</td>'+
						'<td>'+params.qty+'</td>'+
						'<td style="width:150px;border-right: 1px solid #ddd;">'+params.tot_prc+'</td>'+
						'</tr>';
						}
						//var grTot = +parseFloat(r.data[0].gr_tot)+parseFloat(r.data[0].insurance)+parseFloat(r.data[0].frieght);
						
						temp +='<tr><td colspan="11"><b style="float:right;">Sub Total</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tot+'</b></td>'+
								'</tr>'+
								/*'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;" colspan="3"><b style="float:right;">Tax Percentage</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_per+'</b></td>'+
								'<td style="border-top-style: none;border-left-style: none;"><b style="float:right;">Tax Price</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].tax_prc+'</b></td>'+
								'</tr>'+*/
								'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="11"><b style="float:right;">Installation Charge</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].frt_rate+'</b></td>'+
								'</tr><tr>'+
								'<tr style="border-bottom: 1px solid #ddd;">'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="11"><b style="float:right;">Additional charges</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;"><b>'+r.data[0].add_chrg+'</b></td>'+
								'</tr><tr>'+
								'<td style="border-top-style: none;border-bottom-style: hidden;" colspan="11"><b style="float:right;">Others Charge</b></td>'+
								'<td style="width:150px;border-right: 1px solid #ddd;border-top-style: none;"><b>'+r.data[0].frt_text+'</b></td>'+
								'</tr><tr>'+
								'<td style="border-top-style: none;" colspan="11"><b style="float:right;">Grand Total ('+r.data[0].nm_curr+')</b></td>'+
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
						
						list += '<tr><td colspan="2">'+
									'<br><b>The amount to be scantioned has been noted in the budget control register kept division/section vide SL No.............. and verified the balance available amount.	 </b><br>'+
									'<br><b>I, ....................................... am personally satisfied that these goods purchased are of the requisite quality, specification and have been purchased from the reliable supplier ar reasonable price.  </b><br>'+
									/*'Mode of Delivery  <b>: '+r.data[0].nm_dlvry+'</b><br>'+
									'Warranty/Guarranty <b>: '+r.data[0].nm_warr+'</b><br>'+*/
									//'<b>Terms & Conditions</b><br>'+
									//po_t_c+
									//'<br><b>Note :</b>Kindly quote this ref number for in any of your supply document and attach the copy of P.O to the bill.<br>'+ 
									//'Subject to Bangalore Juridiction.'+
									'<br><br>'+
									'<span style="margin-left: 10%;">Signature 1: .....................................</span>'+
									'<span style="margin-left: 10%;">Signature 2: .....................................</span>'+
									'<span style="margin-left: 10%;">Signature 3: .....................................</span>'+
									'<br>'+
								'</td></tr>';
						
							var button=	'<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPurchaseOrder(\'#createPrintorMailPurchaseOrder\')">Print Preview </button>&nbsp;&nbsp;';
						
						
					$('.DisplayAmendPurchaseOrder').html(list);
					$('#buttonForPoPrint').html(button);
					$('#'+HideDiv).hide();
					$('#'+DispDiv).show();
					}
				else
				{
					bootbox.alert("Something went wrong Please try again.");
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
	var id_ven =$('select[name="id_ven"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Print_Mail_PO',{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,id_ven:id_ven,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Print Preview </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				//'<td><center><strong>BRB Ref Number</strong></center></td>'+
				'<td><center><strong>Quotation Number</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td><center><strong>Approve By</strong></center></td>'+
				'<td style="width: 150px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_po+'</center></td>'+
									//'<td><center>'+params.brb_ref_num+'</center></td>'+
									'<td><center>'+params.no_quot+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><center><a href="#" onclick="ControlPrintMailPO2(\'createPrintorMailPurchaseOrder\',\'displayPrintorMailPurchaseOrder\',\''+params.id_po+'\','+params.id_ven+')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.PrintMailPODisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.PrintMailPODisplay').html(list);
			}
		
		
	},'json');

			}});

}
function DisplayVerbalPurchaseOrderForPrintMail()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var id_ven =$('select[name="id_ven"]').val();
	var searchWord = $('input[name="search"').val();
		$.post('Print_Mail_PO',{action : 'DisplayVerbalPo',dt_frm:dt_frm,dt_to:dt_to,id_ven:id_ven,searchWord:searchWord},function (r){

			var list= '<tr>'+
			'<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 325px;">Purchase Order For Print Preview </p></td>'+
			'</tr>'+
			'<tr class="tableHeader info">'+
				'<td><center><strong>PO Number</strong></center></td>'+
				'<td><center><strong>Po Date</strong></center></td>'+
				'<td><center><strong>Vendor Name </strong></center></td>'+
				'<td style="width: 150px;"><strong><center><a href="#">Preview</a></center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_po+'</center></td>'+
									'<td><center>'+params.dt_po+'</center></td>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><strong><center><a href="#" onclick="ControlPrintMailPO(\'VOPreview\',\'createPrintorMailPurchaseOrder\',\'displayPrintorMailPurchaseOrder\',\''+params.id_po+'\',\''+params.no_po+'\','+params.id_ven+')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.PrintMailPODisplay').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.PrintMailPODisplay').html(list);
			}
		
		
	},'json');

			}});

}