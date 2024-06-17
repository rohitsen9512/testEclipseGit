//display listview

//indian currency in word
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




function DisplayLeadExportInvoiceForPrint() {
	SessionCheck(function(ses) {
		if (ses) {
			
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
			var searchWord = $('input[name="searchWord"]').val();
			let count=0;
			$.post('O_AssignedLlead', { action: 'DisplayPreview',searchWord:searchWord}, function(r) {
				
				var list = '<thead><tr class="new">' +
				'<td><strong><center>S.No</center></strong></td>' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Lead Date</center></strong></td>' +
					'<td><strong><center>Patient Name</center></strong></td>' +
					'<td><strong><center>Attendar Name</center></strong></td>' +
					'<td><strong><center>Invoice No</center></strong></td>' +
					'<td><strong><center>Invoice Date</center></strong></td>' +
					'<td><strong><center>Entity</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					
			/*		'<td><strong><center>Assign By</center></strong></td>' +
					'<td><strong><center>Assign To</center></strong></td>' +
					'<td><strong><center>Tag By</center></strong></td>' +
					'<td><strong><center>Tag To</center></strong></td>' +
					'<td><strong><center>Print Chalan</center></strong></td>' +*/
					'<td><strong><center>Print Invoice</center></strong></td>' +
					'</tr></thead><tbody>';
				
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						
						console.log(r.data);
						params = r.data[i];
						list = list + '<tr>' +
						'<td><center>' +  ++count + '</center></td>' +
							'<td><center>' + params.lead_no + '</center></td>' +
							'<td><center>' + params.dtld + '</center></td>' +
							'<td><center>' + params.nm_pstn + '</center></td>' +
							'<td><center>' + params.att_name + '</center></td>' +
							'<td><center>' + params.inv_no + '</center></td>' +
							'<td><center>' + params.dtinv + '</center></td>' +
							//'<td><center>' + params.nm_pstn + '</center></td>' +
							//'<td><center>' + params.typ_service + '</center></td>' +
							'<td><center>' + params.nm_loc + '</center></td>' +
							'<td><center>' + params.nm_subl + '</center></td>' +
							
						    /*'<td><center>' + 'Preview' + '</center></td>' +
							//'<td><center>' + 'Preview'+ '</center></td>' +*/
							'<td><strong><center><a class="alertlink" href="#" onclick="ControlPrintInvoiceLead(\'createPrintforInvoceLead\',\'displayPrintforInvoiceLead\',\''+params.id_cr_inv_m_hist+'\');">' +'Preview'+ '</a></center></strong></td>'+
							

							 /*'<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'modi\');">' +'Preview'+ '</a></center></td>' +
						     '<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'addstock\');">' +'Preview'+ '</a></center></td>' +*/
							'</tr>';
				}
				}
				else {
					list += '<tr><td colspan="11"><strong><center>No data found.</center></strong></td></tr>';
					$('#leadPrForDisplaytd').html('</tbody>' + list);
				}
				$('#leadPrForDisplaytd').html('</tbody>' + list);
				getButtonsForListView('leadPrForDisplaytd','Lead_List');
			}, 'json');
		}
	});
}


function ControlPrintInvoiceLead(HideDiv,DispDiv,id_lead_m)  
{
	$.post('O_AssignedLlead',{action : 'Preview',id:id_lead_m},function (r){		
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
					var pickupbycustomer='No';
						 var service="";
					console.log(r.data);
						console.log(r.data.length);
							
					for( x = 0; x < r.data.length ; x++)
						{
						debugger;
					
						
						
						
						
						
							
						if(r.data[0].id_loc==1){
							var entity='All Medical Equipment';
						}
						if(r.data[0].id_loc==2){
							var entity='HealthCare Equipment';
						}
						
						if(r.data[0].pay_mode==''||r.data[0].pay_mode==undefined||r.data[0].pay_mode==null){
							var paymentMode='';
						}else{var paymentMode=r.data[0].pay_mode;}
						
						if(r.data[0].remark==''||r.data[0].remark==undefined||r.data[0].remark==null){
							var remark='NA';
						}else{var remark=r.data[0].remark;}
						
						if(r.data[0].cheque_no==''||r.data[0].cheque_no==undefined||r.data[0].cheque_no==null){
							var checkno='';
						}else{var checkno=r.data[0].cheque_no;}	
						
						if(r.data[0].rv_charge==''||r.data[0].rv_charge==undefined){
							var rvCharge='';
						}
						else{
							rvCharge=r.data[0].rv_charge;
						}
						if(r.data[0].dtpo==''||r.data[0].dtpo==undefined){
							var dtPo='';
						}
						else{
							dtPo=r.data[0].dtpo;
						}
						if('+r.venDetails[0].fax+'==undefined){fax='';}else{fax='+r.venDetails[0].fax+';}
						list +='<table class="table  table-bordered DisplayAmendInvoice" align="center" style="width:96%;page-break-before:always;    margin-bottom: 0px;" >'+
							 
							'<tr id="header2" style="line-height: 20px;"">'+
						'<td  class="tdClass" colspan="2"  valign="top" style="width: 20%;height:20%;border: 1px solid black; border-right: none;border-bottom: none;"><div align="left"><img src="image/logof7.png"></div></td>'+
				     	'<td class="tdClass" colspan="5" style="border: 1px solid black;border-left: none;border-left: none;border-bottom: none; text-align:center;"><font size="2"><b><b style="font-size: medium;">We Deal with '+entity+' </b></font></center>  <br> <b style="font-size: medium;">'+
						'<b style="font-size: 12px;">'+r.data[0].office_add+'</b><br>'+
						'<b style="font-size: small;">Tel :'+r.company[0].phone+'&nbsp;&nbsp;&nbsp;&nbsp;DL:'+r.data[0].dl_no+'</b> '+
						'<b style="font-size: small;">e-mail :'+r.company[0].mailid+'</b><br>'+
						'<b style="font-size: small;">\"We are also in Hyderabad,Vijaywada & Bangalore\"</b><br>'+
						'<br><b style="font-size: large;">WWW.F7HealthCare.com</b><br>'+
						' </td>'+
						
						'  </tr>'+
						'<tr  id="header2" style="line-height: 20px;">'+
						'<td class="tdClass" colspan="6" style="border: 1px solid black;border-right: none;border-top: none;font-size: 12px;"></td>'+
						'<td class="tdClass" colspan="1" style="border: 2px solid black;border-bottom: none;font-size: 12px;text-align:left;font-size: large;    padding: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;GSTIN-NO :&nbsp;'+r.data[0].gstin_no+'</td>'+
						 
					'</tr>'+
						'<tr  id="header2" style="line-height: 20px;">'+
						'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-bottom: none;font-size: 12px;"><Strong>Invoice No   :    </Strong></td>'+
						'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-bottom: none;font-size: 12px;">'+r.data[0].inv_no+'</td>'+
						'<td class="tdClass" colspan="1" style="border: 1px solid black;;border-bottom: none;font-size: 12px;border-right: none;"><strong>Lead.no.:</strong></td>'+
						'<td class="tdClass" colspan="1" style="border: 1px solid black;;border-bottom: none;font-size: 12px;border-left: none;border-top: 2px solid black;">'+r.data[0].lead_no+'</td>'+
						
					'</tr>'+
					
					'<tr  style="line-height: 20px;">'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong> Date of Invoice :</Strong></td>'+
					'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].dtinv+'</td>'+
					'<td class="tdClass" colspan="1"  style="border: 1px solid black;;border-top: none;border-bottom: none;font-size: 12px;border-right: none;"><strong>Date of intallation    : </strong></td>'+
					'<td class="tdClass" colspan="1"  style="border: 1px solid black;;border-top: none;border-bottom: none;font-size: 12px;border-left: none;">'+r.data[0].dtld+'</td>'+
					
				'</tr>'+
				
			
				
					
					'<tr  style="line-height: 20px;">'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Patient Name :</Strong></td>'+
					'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].nm_pstn+'</td>'+
					'<td class="tdClass" colspan="1"  style="border: 1px solid black;;border-top: none;border-bottom: none;font-size: 12px;border-right: none;"><strong>Patient Contact No.    : </strong></td>'+
					'<td class="tdClass" colspan="1"  style="border: 1px solid black;;border-top: none;border-bottom: none;font-size: 12px;border-left: none;">'+r.data[0].pstn_ct+'</td>'+
					
				'</tr>'+
				'<tr  style="line-height: 20px;">'+
				'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: 1px solid black;font-size: 12px;"><Strong>Attender Name    :  </Strong></td>'+
				'<td class="tdClass" colspan="4" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: 1px solid black;font-size: 12px;">'+r.data[0].att_name+'</td>'+
				
				'<td class="tdClass" colspan="1" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: 1px solid black;font-size: 12px;"><Strong>Attender No.    :  </Strong></td>'+
				'<td class="tdClass" colspan="1" style="border: 1px solid black;;border-top: none;border-bottom: 1px solid black;font-size: 12px;border-left: none;">'+r.data[0].alt_pstn_ct+'</td>'+
				
			'</tr>'+
			
			'<tr  style="line-height: 20px; tyle="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"> '+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Source Code   :  </Strong></td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].cd_src+'</td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Address   :  </Strong></td>'+
			'<td class="tdClass" colspan="1" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].address+'</td>'+
		'</tr>'+
		
		'<tr  style="line-height: 20px;">'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Adhaar No/ID Proof:</Strong></td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].adhar_no+'</td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>DL No:</Strong></td>'+
			'<td class="tdClass" colspan="1" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].dl_no+'</td>'+
			
			
		'</tr>'+
		'<tr  style="line-height: 20px;">'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Payment Mode :</Strong></td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+paymentMode+'</td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Cheque No :</Strong></td>'+
			'<td class="tdClass" colspan="1" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+checkno+'</td>'+
			
			
		'</tr>'+
		'<tr  style="line-height: 20px;">'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom:none;font-size: 12px;"><Strong>Emp ID:</Strong></td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-left: none;border-top: none;border-bottom:none;font-size: 12px;">'+r.data[0].createdby+'</td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom:none;font-size: 12px;"><Strong>Pick up by Customer:</Strong></td>'+
			'<td class="tdClass" colspan="1" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+pickupbycustomer+'</td>'+
			
			
		'</tr>'+	
		'<tr  style="line-height: 20px;">'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Delivery Executive:</Strong></td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].deliveryboy+'</td>'+
			'<td class="tdClass" colspan="2" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;"><Strong>Phone No.:</Strong></td>'+
			'<td class="tdClass" colspan="1" style="border: 1px solid black;border-left: none;border-top: none;border-bottom: none;font-size: 12px;">'+r.data[0].cont_no+'</td>'+
		'</tr></table>';

	debugger
	if(r.data.length > 0)
			{
			debugger;	
				 var z=x+16;
				 
					if(z > r.data.length){
						z=r.data.length;
						
					}

			temp='  <table  class="table  table-bordered " align="center" style="width:96%;height: 492px;border:1px solid black;border-left: none;"><tr class="tableHeader info" style="height: 10px;">'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;width: 50px;"><strong ><center>SL No.</center></strong></td>'+

			'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;width:400px;"><strong ><center>Description of Goods</center></strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;width:400px;"><strong ><center>Serial Number</center></strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;width:400px;"><strong ><center>Service</center></strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;width:160px;"><strong ><center>HSN/SAC Code</center></strong></td>'+
										'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;;"><strong ><center>Rental Days</center></strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;;"><strong ><center>Rental Expiry</center></strong></td>'+
															'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong ><center>Qty</center></strong></td>'+
										'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;;"><strong ><center>Rental/Sale Price</center></strong></td>'+




					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong>'+r.data[0].nm_tax1+'</strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;"><strong>'+r.data[0].nm_tax2+'</strong></td>'+
/*					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black ;width:150px;"><strong ><center>other Price</center></strong></td>'+*/
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black ;width:150px;"><strong ><center>Fill Price</center></strong></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black ;width:150px;"><strong ><center>Discount</center></strong></td>'+
					
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;border-bottom: 1px solid black;width:150px;"><strong >Amount </center></strong></td>'+
				
					'</tr>';
					
					
					
					/*console.log(params.refund_amt);*/
					var total_qty=0;
					
					var total_tax_val=0;
					var refund_amt ='';
			var totalPrice=0,subTotalPrice=0;
			
					for(var i = x; i < z ; i++)
				{
					if(r.data[i].prod_status=='Refill'){
										console.log(r.data[x].prod_status);
							  service='Refill';
						}if(r.data[i].prod_status=='Product_on_Rental'){
									console.log(r.data[x].prod_status);
									
							  service='Rental';
					       rentDays=r.data[i].rental_day;
						}if(r.data[i].prod_status=='Product_on_Sale'){
							debugger;
									console.log(r.data[x].prod_status);
									
							  service='Sale';
				                rentDays='';
					          
						}if(r.data[i].prod_status=='Fill'){
									console.log(r.data[x].prod_status);
							  service='Refill';
						}
						if(r.data[i].prod_status=='Extend days'){
									console.log(r.data[x].prod_status);
							  service='Renew of Rental';
						}
						if(r.data[i].prod_status=='Refill_and_Extend'){
									console.log(r.data[x].prod_status);
							  service='Refill and Renew of Rental';
						}	
						
						if(r.data[i].dt_exp_rent=='1900-01-01'){
									console.log(r.data[i].dt_exp_rent);
							  dtexp_rent='';
						}else{
							dtexp_rent=r.data[i].dtexprent;
						}	
						if(params.refund_amt==undefined || params.refund_amt==''){
									console.log(params.refund_amt);
							  refund_amt=0.00;
						}else{
							refund_amt=params.refund_amt;
						}		
				//params=r.data[i];
				
				params = r.data[i];	
			
				total_price= parseFloat(params.quantity) *parseFloat(params.un_prc)+parseFloat(params.cylndr_fill_mt)+parseFloat(params.others)+parseFloat(params.tax_val1)+parseFloat(params.tax_val2)-parseFloat(params.buyback);
				console.log(total_price);
				totalVal +=total_price;
				total_tax_val1 +=parseFloat(params.tax_val1);
				tottaxv1=total_tax_val1.toFixed(2);
				total_tax_val2 +=parseFloat(params.tax_val2);
				tottaxv2=total_tax_val2.toFixed(2);
				total_tax_amt=total_tax_val1+total_tax_val2;
				//grandTtotal +=parseFloat(params.tot);
				
				grandTtotal =totalVal+parseFloat(params.trnsprt_amt)+parseFloat(params.refund_amt);
				taxrate=params.tax_rate;
				tax_rate=taxrate%100;
			
			
				temp += '<tr style="height: 25px;">'+
				
				
				
				
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+ ++slno +'.</center></td>'+	

					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;">&nbsp;'+params.nm_assetdiv+'</td>'+	
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;">&nbsp;'+params.sr_no+'</td>'+	
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;">&nbsp;'+service+'</td>'+	
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;">&nbsp;'+params.hsn_cd_assetdiv+'</td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;">&nbsp;'+rentDays+'</td>'+	
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;">&nbsp;'+dtexp_rent+'</td>'+	

					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.quantity+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.un_prc+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.tax_val1+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.tax_val2+'</center></td>'+
/*					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.others+'</center></td>'+*/
				'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.cylndr_fill_mt+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;"><center>'+params.buyback+'</center></td>'+
					'<td class="tdClass" style="font-size: 13px;border: none;border-left: 1px solid black;text-align:center;">'+total_price.toFixed(2)+'</td>'+
				'</tr>';
				x=i;
						}
					 
				
				
					var shubhamcurr=parseFloat(grandTtotal);
					var word=number2text(shubhamcurr);
					console.log(word);
				
				list +=temp;
				
				    
		
			
			
				
			list+=
		
		'<tr>'+
		'<td class="tdClass" colspan="13"  style="text-align:left;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black ;border-bottom: none;" ></td>'+
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-left: none; width:15%;border-bottom: none;border-top:1px solid black; font-size: 12px;"><strong>&nbsp;'+totalVal.toFixed(2)+'</strong></td>'+
		
		
		'</tr>'+
				'<tr>'+
				
              
		'<td class="tdClass" colspan="13"  style="text-align:right;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-top: none;" > <div style="">Transport Charge </div></td>'+
       
		
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-right: none; border-left: none;  width:15%;border-bottom: none;border-top:1px solid black; font-size: 12px;border-top: none;"> <div style="">'+params.trnsprt_amt+'</div> </td>'+
               

/*  '</tr>'+
		'<tr>'+
		'<td class="tdClass" colspan="11"  style="text-align:right;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-right: none;border-top: none;" > <div style=""> Add: </div></td>'+
       
		'<td class="tdClass" colspan="1"  style="text-align:left;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-left: none;border-top: none;" > <div style="">'+params.nm_tax1+'</div></td>'+
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-left: none; width:15%;border-bottom: none;border-top:1px solid black; font-size: 12px;border-top: none;"> <div style="">'+tottaxv1+'</div> </td>'+
        '</tr>'+
        '<tr>'+
		'<td class="tdClass" colspan="11"  style="text-align:right;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-right: none;border-top: none;" > <div style=""> Add: </div></td>'+
       
		'<td class="tdClass" colspan="1"  style="text-align:left;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-left: none;border-top: none;" > <div style="">'+params.nm_tax1+'</div></td>'+
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-left: none; width:15%;border-bottom: none;border-top:1px solid black; font-size: 12px;border-top: none;"> <div style="">'+tottaxv2+'</div> </td>'+
        '</tr>'+*/
        
        '<tr>'+
'<tr>'+
		'<td class="tdClass" colspan="13"  style="text-align:right;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-top: none;" > <div style="">Refund charges </div></td>'+
       
		
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-right: none; border-left: none;  width:15%;border-bottom: none;border-top:1px solid black; font-size: 12px;border-top: none;"> <div style="">'+refund_amt+'</div> </td>'+
               

/*  '</tr>'+
		'<tr>'+
		'<td class="tdClass" colspan="11"  style="text-align:right;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-right: none;border-top: none;" > <div style=""> Add: </div></td>'+
       
		'<td class="tdClass" colspan="1"  style="text-align:left;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-left: none;border-top: none;" > <div style="">'+params.nm_tax1+'</div></td>'+
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-left: none; width:15%;border-bottom: none;border-top:1px solid black; font-size: 12px;border-top: none;"> <div style="">'+tottaxv1+'</div> </td>'+
        '</tr>'+
        '<tr>'+
		'<td class="tdClass" colspan="11"  style="text-align:right;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-right: none;border-top: none;" > <div style=""> Add: </div></td>'+
       
		'<td class="tdClass" colspan="1"  style="text-align:left;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;border-left: none;border-top: none;" > <div style="">'+params.nm_tax1+'</div></td>'+
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-left: none; width:15%;border-bottom: none;border-top:1px solid black; font-size: 12px;border-top: none;"> <div style="">'+tottaxv2+'</div> </td>'+
        '</tr>'+*/
        
        '<tr>'+
       '<td class="tdClass" colspan="13"  style="text-align:right;font-size: medium;border: 1px solid black;font-size: 12px;border-top:1px solid black;border-right:1px solid black;border-bottom: none;" > <div style=""><strong> Grand Total:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></div> </td>'+
        '<td class="tdClass" colspan="1"  style="text-align:center;border: 1px solid black;border-left: none; width:15%;border-bottom: 1px solid black;border-top:1px solid black; font-size: 12px;"><strong>'+grandTtotal.toFixed(2)+'</strong></td>'+
		'</tr>'+
		
	 	'<tr  id="header2" style="line-height: 20px;" colspan="4">'+
			'<td class="tdClass" colspan="4" style="border: 1px solid black;border-right: none;border-top: none;border-bottom: none;font-size: 12px;">'+
			'<Table> '+
			//comment part for tax headings
			/*'<tr>'+
       '<td class="tdClass"   style="border: 1px solid black;border-right: none;border-top: none;font-size: 12px;border-left: none;" > <div style=""><strong> Tax Rate</strong></div> </td>'+
        '<td class="tdClass"  style="border: 1px solid black;border-right: none;border-top: none;font-size: 12px;border-left: none;"><strong>Taxable Amt.</strong></td>'+
		 '<td class="tdClass"  style="border: 1px solid black;border-right: none;border-top: none;font-size: 12px;border-left: none;"><strong>'+params.nm_tax1+'</strong></td>'+
		
		 '<td class="tdClass"   style="border: 1px solid black;border-right: none;border-top: none;font-size: 12px;border-left: none;" ><strong>'+params.nm_tax2+'</strong></td>'+
		 '<td class="tdClass"    style="border: 1px solid black;border-right: none;border-top: none;font-size: 12px;border-left: none;" ><strong>TotalTax</strong></td>'+
		
		'</tr>'+*/
		/*	'<tr>'+
       '<td class="tdClass"   style="border: 1px solid black;border-right: none;border-bottom: none;font-size: 12px;border-left: none;" > <div style=""><strong>'+tax_rate+'%</strong></div> </td>'+
        '<td class="tdClass"  style="border: 1px solid black;border-right: none;border-bottom: none;font-size: 12px;border-left: none;"><strong>'+totalVal.toFixed(2)+'</strong></td>'+
		 '<td class="tdClass"  style="border: 1px solid black;border-right: none;border-bottom: none;font-size: 12px;border-left: none;"><strong>'+tottaxv1+'</strong></td>'+
		
		 '<td class="tdClass"   style="border: 1px solid black;border-right: none;border-bottom: none;font-size: 12px;border-left: none;" ><strong>'+tottaxv2+'</strong></td>'+
		 '<td class="tdClass"    style="border: 1px solid black;border-right: none;border-bottom: none;font-size: 12px;border-left: none;" ><strong>'+total_tax_amt.toFixed(2)+'</strong></td>'+
		
		'</tr>'+*/
		'<tr>'+
       '<td class="tdClass" colspan="5"  style="border: 1px solid black;border-right: none;border-bottom: none;font-size: 12px;border-left: none;border-top: none;" > <div style="font-weight:bold;font-size:20px;"><strong>'+word+'</strong></div> </td>'+
        
		'</tr>'+
			'</table>'+
			
			
			
			'</td>'+
					 	 
		'</tr>'+
		
			'<tr  style="line-height: 20px; tyle="border: 1px solid black;border-right: none; border-bottom: none;font-size: 12px;"> '+
			'<td class="tdClass" colspan="9" style="border: 1px solid black;  border-bottom: none;font-size: 12px;"><Strong> Remark : '+remark+'</Strong></td>'+
			 	'<td class="tdClass" colspan="5" style="border: 1px solid black;  border-bottom: none;font-size: 12px;"><Strong> Receiver\'s Signature</Strong></td>'+
		 
		'</tr>'+
		'<tr  style="line-height: 20px; style="border: 1px solid black;border-right: none; border-bottom: none;font-size: 12px;"> '+
			'<td class="tdClass" colspan="9" style="text-align:left;border: 1px solid black;  border-bottom: none;font-size: 12px;">'+
			'<Strong><u>Terms & Conditions </u> </Strong><br><Strong>1. F7 HealthCare</Strong> does not take any responsibility for damage,short circuit,Etc. <br><Strong>2.</strong> In case of troubleshoot our engineer will rectify with in 24 hours.<br><Strong>3. F7 HealthCare</strong> does not take any responsibility towards the patient and his condition,we strictly follow the guidlines as per  &nbsp&nbsp&nbsp&nbsp your doctor or his Prescription.<br><strong>4.</strong> Payment Terms & condition are strictly as per company norms.<br><strong>5. "Bill amount is non refundable after installation of any equipment."</strong><br><strong>6.</strong> Subject to Hydrabad Juridiction.</td>'+
			 	'<td class="tdClass" colspan="5" style="text-align:right;border: 1px solid black;  border-bottom: none;font-size: 12px;"><Strong>For F7 O2 Supply Pvt. Ltd <br> <br> <br><br><br> Authorized Signature</Strong></td>'+
		 
		'</tr>'+
		
		
		' </table>';
				}   
				var	temp = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPrintforInvoceLead\')">Print Preview </button>&nbsp;&nbsp; <button type="button" class="btn btn-primary"  onclick="getPDF(\''+r.data[0].inv_no+'\',\''+r.data[0].dtinv+'\',\''+r.data[0].nm_pstn+'\'); ">PDF</button>&nbsp;&nbsp;'
		document.getElementById("forback").style.display = "block";
	
						
							$('#buttonForPoPrint').html(temp);
							$('#displayPrintforInvoiceLead').hide();
							$('#createPrintforInvoceLead').show();
							$('.DisplayAmendInvoice').html(list);
						}//first loop 177
						 
                    	$('#hideTotal'+i+'').show();
				
			},'json');

}







function ControlPrLead(action, displayContent, createDetails, formName, servletName,id) {
	
    SessionCheck(function(ses) {
		if (ses) {
			$(".form-control").each(function() {
				$(".form-control").removeClass('error');
			});
			$('.readbaledata').each(function() {
				$(this).attr("disabled", false);
			});
			$('input[name="leadCheck"]').val('0');
			
			
			if (action == 'Cancel') {
			//$('#displayAccessory').hide();
				$('#createLineitemPrLead').hide();
				$('#' + createDetails).hide();
				$('#' + displayContent).show();
				DisplayAssignTome(servletName, displayContent, createDetails, '', 'ldassignForDisplaytd');
				window.location = $('.assingtomeLead_event').attr('href');
				}
			else if(action =='Edit')
	{
		EditPrLead(action , displayContent , createDetails , formName , servletName,id);
		$('#'+displayContent).hide();
		$('#'+createDetails).show();
	}
			
				}
				});
		}
        




function DisplayAssignTome(servletName, displayContent, createDetails, lead_no,formName, DisplayTableClass) {
	SessionCheck(function(ses) {
		if (ses) {
			
			var dt_frm = $('#dt_frm').val();
			var dt_to = $('#dt_to').val();
			$.post(servletName, { action: 'AssignTome', dt_frm: dt_frm, dt_to: dt_to }, function(r) {
				var list = '<thead><tr class="new">' +
					'<td><strong><center>Lead Number</center></strong></td>' +
					'<td><strong><center>Lead Date</center></strong></td>' +
					//'<td><strong><center>Patient Name</center></strong></td>' +
					//'<td><strong><center>Service Type</center></strong></td>' +
					'<td><strong><center>City</center></strong></td>' +
					'<td><strong><center>Created By</center></strong></td>' +
					'<td><strong><center>Assign By</center></strong></td>' +
					//'<td><strong><center>Assign To</center></strong></td>' +
					'<td><strong><center>Print Chalan</center></strong></td>' +
					'<td><strong><center>Print Invoice</center></strong></td>' +
					'<td><strong><center>Update</center></strong></td>' +
					'<td><strong><center>Return To Store</center></strong></td>' +
					'</tr></thead><tbody>';
				
				if (r.data.length > 0) {
					for (var i = 0; i < r.data.length; i++) {
						params = r.data[i];
						list = list + '<tr>' +
						    '<td><center><a class="alertlink" href="#" onclick="ControlPrLead(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\''+DisplayTableClass+'\',\'O_AssignedLlead \','+params.id_lead_m+')">' + params.lead_no + '</a></center></td>' +
							//'<td><center>' + params.lead_no + '</center></td>' +
							'<td><center>' + params.dtlead + '</center></td>' +
							//'<td><center>' + params.nm_pstn + '</center></td>' +
							//'<td><center>' + params.typ_service + '</center></td>' +
							
							'<td><center>' + params.nm_subl + '</center></td>' +
							'<td><center>' + params.createdBy + '</center></td>' +
							'<td><center>' + params.assignBY + '</center></td>' +
							//'<td><center>' + params.assingto+ '</center></td>' +
						    '<td><center>' + 'Preview' + '</center></td>' +
							'<td><center>' + 'Preview'+ '</center></td>' +
							 '<td><center>' + 'Update' + '</center></td>' +
							'<td><center>' + 'Return To Store'+ '</center></td>' +
							
							 /*'<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'modi\');">' +'Preview'+ '</a></center></td>' +
						     '<td><center><a class="alertlink" href="#" onclick="EditLead(\'' + servletName + '\',\'' + displayContent + '\',\'' + createDetails + '\',\'' + params.id_lead_m + '\',\'addstock\');">' +'Preview'+ '</a></center></td>' +*/
							'</tr>';
					}
				}
				else {
					list += '<tr><td colspan="3"><strong><center>No data found.</center></strong></td></tr>';
					$('#ldassignForDisplaytd').html('</tbody>' + list);
				}
				$('#ldassignForDisplaytd').html('</tbody>' + list);
			}, 'json');
		}
	});
}


function EditPrLead(action, displayContent, createDetails,formName, servletName,id) {
	SessionCheck(function(ses) {
		if (ses) {
			$('input[name="leadPrCheck"]').val('1');
			
			   
		
			
			/*$('button[name="save"]').addClass('hideButton');
			$('button[name="save1"]').addClass('hideButton');*/
	
			
	       
			
			
			$('#' + displayContent).hide();
			$('#' + createDetails).show();
			$('#createLineitemPrLead').show();
			$('.hideCrled').hide();
			var t = 0;
			var key1;
			
			$.post(servletName, { action: 'EditAssign', id: id }, function(r) {
				if (r.data) {
					
					console.log(r.data);
					for (var i = 0; i < r.data.length; i++) {
						for (var key in r.data[i]) {
							if ($('select[name=' + key + ']').is("select") && r.data[i][key] != '') {
								$('select[name=' + key + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
							}
							else {
								$('input[name=' + key + ']').val(r.data[i][key]);
							}
							
						}
					}
					
					//$('#grandtotinword').text(number2text(parseFloat(r.data[0].tot)));
					$('input[name="action"]').val("Assign");
	        	     $('input[name="id"]').val(id);
					$('input[name="dtld_create"]').val(r.data[0].dtlead);
					
				/*	DisplaySubDropDownData(r.data[0].id_loc, 'slocDataForLead', 'M_Subloc', function(status) {
						if (status) {
							*/
					
					  SubDropDownDataDisplay(r.data[0].id_sloc, 'dgnForEmp', 'M_Designation', function(status) {
								if (status) {
					SubDropDownDataDisplayforemp(r.data[0].id_design, 'EmpdataforField', 'M_Emp_User',r.data[0].id_sloc, function(status) {
								if (status) {		
					            //$('select[name=id_loc] option[value=' + r.data[0].id_loc + ']').attr('selected', true);
						        
                          $('select[name=id_sloc] option[value=' + r.data[0].id_sloc + ']').attr('selected', true);
							    
			                   $('select[name=id_design] option[value=' + r.data[0].id_design + ']').attr('selected', true);
										$('input[name="gstin_no"]').val(r.data[0].gstin_no);	
					                    $('input[name="dl_no"]').val(r.data[0].dl_no);	
                                                    		
							  // $('select[name=assing_to_emp] option[value=' + r.data[0].to_asign_cordi + ']').attr('selected', true);
												
															
										//}
					            // });
					         }
					     });
					  }
					});
					/*var uploadFile='';
						if(r.data[0].invoice_file != undefined)
							uploadFile = r.data[0].invoice_file;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("datachment").innerHTML =download;	*/
					dispalyModifyLinePrItemLead(r);
					
					
					if(butType!='modi'){
				
			$('.form-control').attr('disabled', 'disabled');
			}
			 
				}
				else {
					bootbox.alert("Try again.");
				}
				if (t == 1) {
					ShowRowColumn('Yes', 'hideRowCol');
				}
			}, 'json');
		
	}
	});
}

function dispalyModifyLinePrtemLead(r) {
	if (r.data) {
		var list = '<tr>' +
			'<td colspan="21" style="background-color: #20c997;"><style="font-size: 10px;color: black;text-align: center;"><b>Line Item Details</b></td></tr>' + '<tr class="tableHeader info">' +
			'<td ><strong><center>Assets/Model<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Manufacture<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Description<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Qty<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Stock<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Fill Price<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Others<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Name<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax1 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Tax2 Value<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Service<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Refundable Deposite<a href=#></a></center></strong></td>' +
			'<td ><strong><center>Transportation Charges<a href=#></a></center></strong></td>' +
			'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>' +
			'</tr>';
		for (var i = 0; i < r.data.length; i++) {
			console.log('ss');
			list += '<tr>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].nm_prod + '" id="dynItemData' + i + '" name="id_prod' + i + '" patelUnPrc="' + i + '"  readonly="readonly" class="form-control resetAcc" onchange="changeEventHandlerprodd(event);"></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].mfr + '"  name="mfr' + i + '" patelUnPrc="' + i + '" readonly="readonly"  class="form-control"></td>' +
				'<td><input style="width: 120px; !important" type="text" value="' + r.data[i].ds_product + '" name="ds_product' + i + '" patelUnPrc="' + i + '"  readonly="readonly"  class="form-control"></td>' +
				
				'<td><center><input type="text"  name="quantity' + i + '" style="width:60px;;text-align: right;"  value="' + r.data[i].quantity + '"  class="form-control" patelUnPrc="' + i + '"  onChange="calculateTotlead(event,\'quantity\')" class="patelUnPrc data-valid="num" readonly></center></td>' +
				'<td><center><input type="text"  name="in_stoc_qty' + i + '" style="width:60px;text-align: right;"  value="' + r.data[i].in_stoc_qty + '" class="form-control" patelUnPrc="' + i + '" readonly></center></td>' +
				
				'<td><center><input type="text" name="un_prc' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].un_prc + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'un_prc\')"   class="patelUnPrc form-control" data-valid="num" readonly ></center></td>' +
				'<td><center><input type="text" name="cylndr_fill_mt' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].cylndr_fill_mt + '" style="width: 60px;text-align: right;" onChange="calculateTotlead(event,\'cylndr_fill_mt\')" class="patelUnPrc form-control" data-valid="num" data-valid="mand" ></center></td>' +
				'<td><input type="text" name="others' + i + '" value="' + r.data[i].others + '" patelUnPrc="' + i + '" style="float:right;text-align: right;width:80px""  onChange="calculateTotlead(event,\'others\')"  class="form-control" readonly></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax1' + i + '" value="' + r.data[i].id_tax1 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax1' + i + '" readonly  class="form-control patelTax" onChange="getTax2(event);"readonly>' +
				'</select>' +
				'</center></td>' +
				'<td><center>' +
				'<select style="width: 100px;" name="id_tax2' + i + '" value="' + r.data[i].id_tax2 + '" patelTax="' + i + '" patelUnPrc="' + i + '" id="id_tax2' + i + '" readonly  class="form-control patelTax2" onChange="TaxCalculationlead(event,\'id_tax2\',\'tax_val2\')" readonly>' +
				'</select>' +
				'</center></td>' +
				'<td><center><input type="text"  name="tax_val1' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val1 + '" onChange="calculateTotlead(event,\'tax_val1\')" style="width:60px;text-align: right;" class="form-control" readonly ></center></td>' +
				'<td><center><input type="text"  name="tax_val2' + i + '" patelUnPrc="' + i + '" value="' + r.data[i].tax_val2 + '" onChange="calculateTotlead(event,\'tax_val2\')" style="width:60px;text-align: right;" class="form-control" readonly></center></td>' +
				'<td><center><select style="width: 90px !important;" id="servicetypeDivForLead' + i + '" name="typ_service' + i + '" patelUnPrc="' + i + '"  class="form-control" data-valid=""  style="width:80" readonly >' +
		       	'<option value="">Select</option>' +
			     '<option value="Rent">Rental</option>' +
			     '<option value="Sale">Sale</option>'+'</center></td>' +
				'<td><center><input type="text" name="refnd' + i + '"  style="width: 60px;text-align: right;" value="' + r.data[i].refnd + '" patelUnPrc="' + i + '" onChange="calculateTotlead(event,\'refnd\')" class="form-control" readonly></center></td>' +
				'<td><center><input type="text" name="tr_chrg' + i + '" style="width: 60px;text-align: right;" value="' + r.data[i].tr_chrg + '"  patelUnPrc="' + i + '" onChange="calculateTotlead(event,\'tr_chrg\')" class="form-control" readonly></center></td>' +
					'<td><center><input type="text"  name="gr_tot' + i + '" value="' + r.data[i].gr_tot + '" style="width:60px;text-align: right;" class="form-control commonTotal" readonly></center></td>' +
				'<input type="hidden" name="id_lead' + i + '" value="' + r.data[i].id_lead + '">' +
				'<input type="hidden" name="count" value="' + i + '">' +
				'</tr>';
		}
		$('input[name="itemCount"]').val(i);
		$('input[name="grand_tot1"]').val('0.00');
		$('#leadPrDetails1').html(list);
		
		//Auto select Dropdown of lineitems
		DropDownDataDisplay("M_Tax", "taxDataForDropDown11", function(status) {
			if (status) {
				DropDownDataDisplayForTax2("M_Tax", "patelTax2", function(status) {
					if (status) {
						
									for (var i = 0; i < r.data.length; i++) {
										params = r.data[i];
										for (var key in r.data[i]) {
											if (key == 'id_tax1') {
												$('select[name=id_tax1' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_tax2') {
												$('select[name=id_tax2' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
										/*	if (key == 'id_grp') {
												$('select[name=id_grp' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'id_sgrp') {
												$('select[name=id_sgrp' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}
											if (key == 'typ_asst') {
												$('select[name=typ_asst' + i + '] option[value=' + r.data[i][key] + ']').attr('selected', true);
											}*/
										}
									}
								
					}
				});
			}
		});
	}
	$("#dynItemData0").easyAutocomplete(options);
	$("#dynItemData1").easyAutocomplete(options);
	$("#dynItemData2").easyAutocomplete(options);
	$("#dynItemData3").easyAutocomplete(options);
	$("#dynItemData4").easyAutocomplete(options);
	$("#dynItemData5").easyAutocomplete(options);
	$("#dynItemData6").easyAutocomplete(options);
	$("#dynItemData7").easyAutocomplete(options);
	$("#dynItemData8").easyAutocomplete(options);
	$("#dynItemData9").easyAutocomplete(options);
	$("#dynItemData10").easyAutocomplete(options);
	$("#dynItemData11").easyAutocomplete(options);
	$("#dynItemData12").easyAutocomplete(options);
	$("#dynItemData13").easyAutocomplete(options);
	$("#dynItemData14").easyAutocomplete(options);
	$("#dynItemData15").easyAutocomplete(options);
	$("#dynItemData16").easyAutocomplete(options);
	$("#dynItemData17").easyAutocomplete(options);
	$("#dynItemData18").easyAutocomplete(options);
	$("#dynItemData19").easyAutocomplete(options);
	$("#dynItemData20").easyAutocomplete(options);
	$("#dynItemData21").easyAutocomplete(options);
	$("#dynItemData22").easyAutocomplete(options);
	$("#dynItemData23").easyAutocomplete(options);
	$("#dynItemData24").easyAutocomplete(options);
	$("#dynItemData25").easyAutocomplete(options);
	$("#dynItemData26").easyAutocomplete(options);
	$("#dynItemData27").easyAutocomplete(options);
	$("#dynItemData28").easyAutocomplete(options);
	$("#dynItemData29").easyAutocomplete(options);
	$("#dynItemData30").easyAutocomplete(options);
}



