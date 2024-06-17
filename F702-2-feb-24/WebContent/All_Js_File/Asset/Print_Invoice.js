function DisplayExportInvoiceForPrint()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$('#serialNo').hide();
	var searchWord=$('input[name="search"]').val();
	//$('.ind').attr('disabled','disabled');
	
				$.post('Print_Invoice',{action : 'Display',searchWord:searchWord } , function (r){
					
					var list= '<tr class="new">'+
					'<td class="tdClass" style="line-height: 28px;"><strong><center>Invoice Number</center></strong></td>'+
					'<td class="tdClass" style="line-height: 28px;"><strong><center>Invoice Date</center></strong></td>'+
					'<td class="tdClass" style="line-height: 28px;"><strong><center>Vendor</center></strong></td>'+
					'<td class="tdClass" style="width: 155px;line-height: 28px;"><strong><center>Print Invoice </center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						list = list + '<tr class="success">'+
						'<td class="tdClass" style="line-height: 28px;"><center>'+params.no_inv+'</center></td>'+
						'<td class="tdClass" style="line-height: 28px;"><center>'+params.dtinvoice+'</center></td>'+
						'<td class="tdClass" style="line-height: 28px;"><center>'+params.nm_ven+'</center></td>'+
						'<td class="tdClass" style="line-height: 28px;"><strong><center><a href="#" onclick="ControlExportInvoice1(\'createPrintorMailPurchaseOrder\',\'displayPrintorMailPurchaseOrder\',\''+params.id_inv_m+'\',\''+params.id_ven+'\')"> Preview </a></center></strong></td>'+
						'</tr>';
						}
					
					
						$('.PrintInvoice').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td class="tdClass" colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.PrintInvoice').html(list);
					}
		//		$('.ind').removeAttr('disabled');	
				
			},'json');
		
			}});
}

function ControlExportInvoice1(HideDiv,DispDiv,id_fg_sales_inv,id_ven)
{
	$.post('Print_Invoice',{action : 'PreviewExportInvoice',id_sales_invoice:id_fg_sales_inv,id_ven:id_ven},function (r){
		
		var temp='';
		var slno=0;
		var x = 0;
		var list="";
		for( x = 0; x < r.data.length ; x++)
		{
					var wbst_num='',gst_num='',bnm_num='',mode_of_delv='',tod_dest='', typ_of_po='',remarks='';
					if(r.data[0].remarks !=undefined){remarks=r.data[0].remarks;}
					
					if(r.data[0].bnm_num !=undefined){bnm_num=r.data[0].bnm_num;}
					if(r.data[0].gst_num !=undefined){gst_num=r.data[0].gst_num;}
					if(r.data[0].wbst_num !=undefined){wbst_num=r.data[0].wbst_num;}
					if(r.data[0].mode_of_delv !=undefined){mode_of_delv=r.data[0].mode_of_delv;}
					if(r.data[0].tod_dest !=undefined){tod_dest=r.data[0].tod_dest;}
					
					var state=r.venDetails[0].state,commast='';
					if(state==undefined){state='';}else{state=r.venDetails[0].state;commast=',';}
					var country=r.venDetails[0].country,commastcnt='';
					if(country==undefined){country='';}else{country=r.venDetails[0].country;commastcnt='.';}
					var city=r.venDetails[0].city,commastcntcty='';
					if(city==undefined){city='';}else{city=r.venDetails[0].city;commastcntcty=',';}
					
					var phone=r.venDetails[0].phone,commaphone='';
					if(phone==undefined){phone='';}else{phone=r.venDetails[0].phone;commaphone=',';}
					var ct_mailid=r.venDetails[0].ct_mailid,commact_mailid='';
					if(ct_mailid==undefined){ct_mailid='';}else{ct_mailid=r.venDetails[0].ct_mailid;commact_mailid=',';}
					
					var pin=r.venDetails[0].pin,commact_pin='';
					if(pin==undefined){pin='';}else{pin=r.venDetails[0].pin;commact_mailid=',';}
					
					
					var cst=r.venDetails[0].cst;
					if(cst==undefined){cst='';}else{cst=r.venDetails[0].cst;comma=',';}
					
					list +='<table class="table table-bordered DisplayAmendPurchaseOrder" align="center" style="width:98%;border: none;border-left-style: none;border-right-style: none;page-break-before:always" >'+
						'  <thead class=""><tr ">'+
							
					     	'<td class="tdClass" colspan="6" style="border: none;"<center><font size="2"><b><b style="font-size: medium;margin-left: 450px;"><center> Invoice</center></b></font></center> <b style="float:right;font-size: 12px;"></b><center><font size="2"><b></td>'+
							'  </tr>'+
							
							/*'  <tr id="header2" >'+
							'<td class="tdClass" colspan="8" style="border: 1px solid black;">&nbsp; <br><font></font>'+
							'&nbsp;<br>'+
							'<font size="small">Tel :&nbsp; &emsp;&emsp;&emsp;  Fax :&nbsp; <br>'+
							'<font size="small">E-mail :&nbsp;</strong> <br>'+
							'<font size="medium">GSTIN NO :&nbsp; &emsp;&emsp;&emsp;PAN NO :&nbsp; &emsp;&emsp;&emsp;CIN NO :&nbsp;<br></td>'+
							'  </tr>'+*/
							
							'<tr  id="header2" style="line-height: 20px;">'+
						'<td class="tdClass" colspan="1" rowspan="7" style="border: 1px solid black; font-size: 12px;border-right:none;">'+
						' <Strong>&nbsp;Exporter</Strong><br> <div><img src="InvoiceScanFile/'+r.company[0].file_name+'" style="width:150px;height: 60px;"></div><font-size="large"><br>'+
						' <b>&nbsp;'+r.venDetails[0].nm_ven+'</b><br>&nbsp;'+r.venDetails[0].add1+','+r.venDetails[0].add2+','+r.venDetails[0].city+','+r.venDetails[0].pin+' <br>'+
						' &nbsp;Tel No : &nbsp;&nbsp;'+r.venDetails[0].phone+'<br>&nbsp;PAN NO.   :&nbsp;'+r.venDetails[0].pan+' <br>'+
						' &nbsp;E-mail :&nbsp;&nbsp;'+r.venDetails[0].mailid+'<br>'+
                        '</td>'+
									'<td class="tdClass"   style="border: 1px solid black;border-bottom: none;border-right:none;">&nbsp;<Strong style="font-size: 12px;border-right:none;width:120px;">Invoice No &nbsp;&nbsp;</Strong></td>'+
									'<td class="tdClass"   style="border: 1px solid black;border-bottom: none;font-size: 12px;border-right:none;">&nbsp;Date :&nbsp;&nbsp;</td>'+
					'</tr>'+
					
					'<tr style="border: 1px solid black;">'+
					'<td class="tdClass"  style="border: 1px solid black; border-top: none;font-size: 12px;border-right:none;border-bottom:none;">'+r.data[0].no_inv+'</td>'+
					'<td class="tdClass"   style="border: 1px solid black;border-top: none;font-size: 12px;border-right:none;border-bottom:none;">'+r.data[0].dtinvoice+'</td>'+
			    	'</tr>'+
			    	
			    	
		        	'<tr style="border: 1px solid black;">'+
		        	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;font-size: 12px;border-top:none;"><b>&nbsp;PAN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].pan+'</td>'+
			        '</tr>'+
			        
			        '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;GSTIN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].fax+'</td>'+
				    '</tr>'+
				    
				    '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="display: none;border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;TIN No :</b>&nbsp;&nbsp;&nbsp;</td>'+
				    '</tr>'+
				    
				    '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;CIN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].kst+'</td>'+
				    '</tr>'+
				    
				    '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;TIN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].tin+'</td>'+
				    '</tr>'+
				    
								
						
					
					'<tr>'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-bottom:none;border-top:none;border-right:none;"><Strong style="font-size: 12px;">&nbsp;Consignee</strong>&nbsp;'+
					'<td class="tdClass" colspan="4"   style="border: 1px solid black;border-bottom:none;border-top:none;"><Strong style="font-size: 12px;"> &nbsp;Ship to </strong>&nbsp;'+
					'</tr>'+
					 
					'<tr>'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-bottom: none;border-top:none;border-right:none;"><Strong style="font-size: 12px;">&nbsp;'+r.company[0].nm_com+'</strong>&nbsp;'+
					'<td class="tdClass" colspan="4"   style="border: 1px solid black;border-bottom: none;border-top:none;"><Strong style="font-size: 12px;">&nbsp;'+r.company[0].nm_com+'</strong>&nbsp;'+
					'</tr>'+
					
					'<tr>'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-top:none;font-size: 12px;border-right:none;border-bottom:none;">&nbsp;'+r.company[0].add1+',<br>'+r.company[0].city+'-'+r.company[0].pin+','+r.company[0].nm_state+','+r.company[0].country+'&nbsp;'+
					'<td class="tdClass" colspan="4"   style="border: 1px solid black;border-top:none;font-size: 12px;border-bottom:none;">&nbsp;'+r.company[0].add1+',<br>'+r.company[0].city+'-'+r.company[0].pin+','+r.company[0].nm_state+','+r.company[0].country+' &nbsp;'+
					'</tr>';
				
		        
						if(r.data.length > 0)
				{	var z=x+12;
					if(z > r.data.length){
						z=r.data.length;
					}
							
						temp='  <table id="productlist'+z+'" class="table  table-bordered DisplayAmendPurchaseOrder" align="center" style="width:98%;height: 400px;"><tr class="tableHeader info" style="height:10px;">'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 60px;border-right:none;width:30px !important;"><strong ><center>SL No.</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-right:none;width:90px !important;"><strong ><center>PO NO.</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-right:none;"><strong ><center>Description of Goods</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 70px;border-right:none;"><strong ><center>Quantity in Nos </center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 90px;border-right:none;"><strong ><center>Rate</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 90px;border-right:none;"><strong ><center>Tax1</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 90px;border-right:none;"><strong ><center>Tax2</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 100px;text-align:right;"><strong >Amount </strong></td>'+
						'</tr>';
						var totalVal=0;
						var grandTtotal=0;
						var total_qty=0;
						
						var total_tax_val=0;
				var totalPrice=0,subTotalPrice=0;
				
					
					for(var i = x; i < z ; i++)
					{
					params=r.data[i];
						var total_price=0;
						total_price= parseFloat(params.qty) *parseFloat(params.un_prc);
					totalVal +=total_price;
					total_qty +=parseFloat(params.qty);
					grandTtotal +=parseFloat(params.gr_tot);
					total_tax_val  =parseFloat(r.data[0].tax_val1)+parseFloat(r.data[0].tax_val2);
					var currency='';
					if(params.cd_curr=='RS'){
						currency='₹';
					}
					else if(params.cd_curr=='EURO'){
						currency='€';
					}
					else if(params.cd_curr=='USD'){
						currency='$';
					}
					else if(params.cd_curr=='GBP'){
						currency='£';
					}
					else{
						currency=' ';
					}
					temp += '<tr style="height:21px;">'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;"><center>'+ ++slno +'.</center></td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;"><center>'+params.no_po+'</center></td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align:left;">&nbsp;&nbsp;'+params.model+'</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;"><center>'+params.qty+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;"><center>'+params.un_prc+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;"><center>'+params.tax_val1+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;"><center>'+params.tax_val2+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;text-align: right;">'+params.gr_tot+'</td>'+
					'</tr>';
					x=i;	
							}
							temp += '<tr class="rowid'+i+'">'+
				
					'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;text-align: right;">&nbsp;</td>'+
				'</tr>'
				
				'</tr>';
					//var shubhamcurr=parseFloat(totalVal);
					var shubhamcurr=parseFloat(r.total[0].total);
					var word=number2text(shubhamcurr);
					console.log(word);

					var shubhamcurr1=parseFloat(total_tax_val);
					var word1=number2text(shubhamcurr1);
					console.log(word1);
                	
				
					list +=temp;
					
					list+='<table id="hidetotal'+i+'" class="table  table-bordered " align="center" style="display:none;width:98%;margin-top: -23px;"><tr class="total'+i+'" >'+
					'<td class="tdClass" colspan="3" style="text-align:left;font-size: 12px;border: 1px solid black;border-right:none;" ><strong>&nbsp;Amount Chargeable (in words) : '+word+'</strong></td>'+
					'<td class="tdClass" colspan="3" style="text-align:left;border: 1px solid black;font-size: 12px;width: 270px;"><b>&nbsp;Grand Total:</b>&nbsp;&nbsp;<span style="float:right;">'+currency+'&nbsp'+shubhamcurr.toFixed(2)+'</span></td>'+
					
					
					 '  </tr></table>';
				
				
                 
                
                
				var netweight='';
				var grossweight='';
				if(r.data[0].gross_wt==undefined){
				  grossweight='0'   ;
		netweight='0';
                      }
else{

netweight=r.data[0].net_wt;
grossweight=r.data[0].gross_wt;

}
               
				list+='<table id="hidepaymethod'+i+'" class="table table-bordered " align="center" style="display:none;width:98%;margin-top: -23px;"><tr>'+
					'<td class="tdClass" colspan="6" style="text-align:left;font-size: 12px;border: 1px solid black;border-bottom:none;" ><strong>&nbsp;Payment Method:-<br>&nbsp;</strong></td>'+
					
					
					
					 '  </tr>';
				list+='<table class="table table-bordered " align="center" style="width:98%;margin-top: -23px;"><tr>'+
					'<td class="tdClass" colspan="2" style="text-align:left;font-size: 12px;border: 1px solid black;border-right:none;" ><strong>&nbsp;Declaration :<br>&nbsp;We declare that this Invoice shows the actual price of the goods described and that all particulars are true and correct.</strong></td>'+
					'<td class="tdClass" colspan="4" style="text-align:left;border: 1px solid black;width: 400px;height: 80px;"></td>'+
					
					
					 '  </tr>';
			
					/* '  <tfoot><tr >'+
					   '<td class="tdClass" colspan="4" rowspan="2" style=" font-size:12px;border: 1px solid black;"><font-size="medium">VAT Declaration :</font><br>'+
					   '<b>&nbsp;</b></td>'+
					   '<td class="tdClass" colspan="4" style="font-size:12px;border: 1px solid black;"><center><br>For ESS ENN Auto CNC Pvt Ltd<br>&nbsp;</center></td>'+
					   '  </tr>'+
						
					   
					   '<tr >'+
					   '<td class="tdClass" colspan="4" style="font-size:12px;border: 1px solid black;"><center><br><SPAN STYLE="text-decoration:overline">Authorised Signatory</SPAN><br>&nbsp;</center></td>'+
				      '  </tfoot></tr>';*/
					
				}	
					
					
								
					
					
					
				var	temp = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPrintorMailPurchaseOrder\')">Print Preview </button>&nbsp;&nbsp;';

						
							$('#buttonForPoPrint').html(temp);
							$('#displayPrintorMailPurchaseOrder').hide();
							$('#createPrintorMailPurchaseOrder').show();
							$('.DisplayAmendPurchaseOrder').html(list);
		}
		/*console.log(i);tb.style.display = "none";
					alert(i);*/
				 $('#hidetotal'+i+'').show();
			     $('#hidepaymethod'+i+'').show();
                 $('#hideproduct'+i+'').show();
                // $('#productlist'+i+'').style.height = "10px";
                  $('#productlist'+z+'').css({'height':'200px'});
                  $('#productlist'+z+'').css({'margin-top':'-23px'});
		//$('.hidetable').hide();
					
                    	//$('#'+HideDiv).hide();
					//$('#'+DispDiv).show();
					//}
				/*else
				{
					bootbox.alert("Something went wrong Please try again.");
				}*/
				
			},'json');

}

function ControlExportInvoice(HideDiv,DispDiv,id_fg_sales_inv,id_ven)
{
	$('#'+DispDiv).show();
	$('#'+HideDiv).hide();
	
	$.post('Print_Export_Invoice',{action : 'PreviewExportInvoice',id_sales_invoice:id_fg_sales_inv,id_ven:id_ven},function (r){
		var shift="";
	//	if(r.data[0].shift_typ !=undefined)shift=r.data[0].shift_typ;
		var slno=0;
		
		//console.log(r.aut_by);
		var list='<tr>'+
		'<td class="tdClass" colspan="2" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><center><b style="font-size: medium;">Export Invoice</b> <br><font></font></center>'+
		'<center font size="small">For Supply of Goods and Service [Section 31 of the GST Act, 2017 read with Rule 7 of Invoice Rules, 2017]</center></td>'+
'</tr>'+
/*'<tr>'+
'<td class="tdClass" colspan="2" style="border-top:2px solid black;border-left: 2px solid black;border-right: 2px solid black;border-bottom: 2px solid black;"><b style="font-size: medium;">'+r.company[0].add1+'</b> <br><font></font>'+
'<b style="font-size: medium;">'+r.company[0].city+'-'+r.company[0].pin+'</b><br>'+
'<font size="small"><strong>Tel :'+r.company[0].phone+' &emsp;&emsp;&emsp;  Fax :'+r.company[0].fax+' <br>'+
'<font size="small"><strong>E-mail :'+r.company[0].mailid+'</strong> <br>'+
'<font size="medium"><strong>GSTIN NO :'+r.company[0].cst+' &emsp;&emsp;&emsp;PAN NO :'+r.company[0].pan+' &emsp;&emsp;&emsp;CIN NO :'+r.company[0].cin+'</strong><br></td>'+
'</tr>'+*/

'<tr  >'+

'<td class="tdClass" style="width:40%;border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;">'+
'<table width="100%" height="100%"  style=";border-left-style: none;">'+
'<tr><td class="tdClass">'+
'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">Exporter<br></font>'+
'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium"><Strong>'+r.company[0].add1+'&nbsp;'+r.company[0].add2+'<br></font>'+//'+r.venDetails[0].nm_ven+'
'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">Tel :'+r.company[0].phone+'<br></font>'+
//'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">560066 BANGALORE<br></font>'+
'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">Fax.   :'+r.company[0].fax+'<br></font>'+
'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">E-mail.   :'+r.company[0].mailid+'</b><br></font>'+
'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">State Name   :'+r.company[0].city+'<br></font>'+
'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">State Code   :'+r.company[0].pin+'<br></font></Strong>'+
'</td></tr></table></td>'+

'<td class="tdClass" style="width:60%;border-right-style: none; border-left-style: none">'+
	'<table width="100%" height="100%"  style=";border-left-style: none;">'+
		'<tr>'+
			'<td class="tdClass" style="width:50%;border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Export Invoice No.</b></font></td>'+
			'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Dated:</b></font></td>'+
			'<td class="tdClass" style="width:50%;border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">IE Code No.</b></font></td>'+
		'</tr>'+
		
		'<tr>'+
		'<td class="tdClass" style="width:50%;border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">nbsp;</b></font></td>'+
		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">nbsp;</b></font></td>'+
		'<td class="tdClass" style="width:50%;border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">nbsp;</b></font></td>'+
	'</tr>'+
	'<tr>'+
	'<td class="tdClass" colspan="3">nbsp;</td>';
	'</tr>'+
		
		'<tr>'+
		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">PAN No.</b></font></td>'+
		'<td class="tdClass" colspan="2" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
		'</tr>'+
		
		'<tr>'+
		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">GSTIN No No.</b></font></td>'+
		'<td class="tdClass" colspan="2" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
		'</tr>'+
		
		'<tr>'+
		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">TIN No.</b></font></td>'+
		'<td class="tdClass" colspan="2" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+		
		'</tr>'+
		
		'<tr>'+
		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">CIN No.</b></font></td>'+
		'<td class="tdClass" colspan="2" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
		'</tr>'+
		
		'<tr>'+
		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">TAN No.</b></font></td>'+
		'<td class="tdClass" colspan="2" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
		'</tr>'+
		
		'<tr>'+
		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">LUT No./Date</b></font></td>'+
		'<td class="tdClass" colspan="2" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
		'</tr>'+
	'</table>'+
 '</td>'+
 '</tr>'+
		
 '<tr  >'+

 '<td class="tdClass" style="width:40%;border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;">'+
 '<table width="100%" height="100%"  style=";border-left-style: none;">'+
 '<tr><td class="tdClass">'+
 '<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">Buyer (if other than consignee)<br></font>'+
 '<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium"><Strong>'+r.venDetails[0].nm_ven+'<br></font>'+
 '<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">'+r.venDetails[0].add1+'&nbsp;'+r.venDetails[0].add2+'&nbsp;'+r.venDetails[0].add3+'<br></font>'+
 //'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">560066 BANGALORE<br></font>'+
 '<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">GSTIN No.   :'+r.venDetails[0].gstin+'<br></font>'+
 //'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">PAN NO.   :'+r.venDetails[0].pan+'</b><br></font>'+
 '<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">Place of Delivery   :'+r.venDetails[0].add1+'<br></font>'+
 //'<style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;"><font-size="medium">Customer Code   :<br></font></Strong>'+
 '</td></tr></table></td>'+

 '<td class="tdClass" style="width:60%;border-right-style: none; border-left-style: none">'+
 	'<table width="100%" height="100%"  style=";border-left-style: none;">'+
 		'<tr>'+
 			'<td class="tdClass" style="width:50%;border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Vechile LR\RR No</b></font></td>'+
 			'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Destination</b></font></td>'+
 		'</tr>'+
 		
 		'<tr>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">--NA--</b></font></td>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
 		'</tr>'+
 		
 		'<tr>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Vehicle No</b></font></td>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Transporter</b></font></td>'+
 		'</tr>'+
 		
 		'<tr>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">--NA--</b></font></td>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">--NA--</b></font></td>'+
 		'</tr>'+
 		
 		'<tr>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Preparation Date\Time</b></font></td>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Removal Date\Time</b></font></td>'+
 		'</tr>'+
 		
 		'<tr>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">&nbsp;</b></font></td>'+
 		'</tr>'+
 		/*'<tr>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Consignee</b></font></td>'+
 		'<td class="tdClass" style="border-top-style: none;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black;"><b font-size="medium">Consignee</b></font></td>'+
 		'</tr>'+*/
 	'</table>'+
  '</td>'+
  '</tr>'+
		

'<tr >'+
'<td class="tdClass" style="border: 1px solid black;" colspan="2">'+
'<table width="100%" class="table table-bordered" border="1" style="border-block-end-style: none;border: 1px solid black;"><tr>'+
'<td class="tdClass"><strong style="font-size: 13px;">SL No.</strong></td>'+
'<td class="tdClass"><strong style="font-size: 13px;">PO NO.</strong></td>'+
'<td class="tdClass"><strong style="font-size: 13px;">Part NO.</strong></td>'+
'<td class="tdClass"><strong style="font-size: 13px;">Description of Goods</strong></td>'+
'<td class="tdClass"><strong style="font-size: 13px;">HSN Code</strong></td>'+
'<td class="tdClass"><strong style="font-size: 13px;">Quantity in Nos </strong></td>'+
'<td class="tdClass"><strong style="font-size: 13px;">Unit Price</strong></td>'+
'<td class="tdClass"><strong style="font-size: 13px;">Total Price</strong></td>'+
'</td>'+
'</tr>';

var totalVal=0;
var grandTtotal=0;
var total_qty=0;
var total_tax_val=0;
if(r.data.length > 0)
{

for(var i = 0; i < r.data.length ; i++)
{
	
params = r.data[i];	
total_price= parseFloat(params.qty) *parseFloat(params.un_prc);
totalVal +=total_price;
total_qty +=parseFloat(params.qty);
grandTtotal +=parseFloat(params.gr_tot);
total_tax_val  =parseFloat(r.data[0].tax_val1)+parseFloat(r.data[0].tax_val2);
list += '<tr>'+
	'<td class="tdClass" style="font-size: 13px;">'+ ++slno +'.</td>'+	
	'<td class="tdClass" style="font-size: 13px;">&nbsp;'+params.no_so+'</td>'+	
	'<td class="tdClass" style="font-size: 13px;">&nbsp;'+params.cd_prod+'</td>'+
	'<td class="tdClass" style="font-size: 13px;">&nbsp;'+params.nm_prod+'</td>'+
	'<td class="tdClass" style="font-size: 13px;">&nbsp;</td>'+
	'<td class="tdClass" style="font-size: 13px;">&nbsp;'+params.qty+'</td>'+
	'<td class="tdClass" style="font-size: 13px;">&nbsp;'+params.un_prc+'</td>'+
	'<td class="tdClass" style="font-size: 13px;">&nbsp;'+total_price.toFixed(2)+'</td>'+
'</tr>';
	}

var shubhamcurr=parseFloat(totalVal);
var word=number2text(shubhamcurr);
console.log(word);

var shubhamcurr1=parseFloat(total_tax_val);
var word1=number2text(shubhamcurr1);
console.log(word1);


list+='<tr>'+
			'<td class="tdClass" colspan="5" style="text-align:right;font-size: 12px;border-right: 2px solid black;border-top: 2px solid black;" ><strong>Total</strong></td>'+
			'<td class="tdClass" colspan="2" style="text-align:center;border-right: 2px solid black;border-top: 2px solid black;">'+total_qty.toFixed(2)+'</td>'+
			'<td class="tdClass" style="text-align:right;border-right: 2px solid black;border-top: 2px solid black;">&nbsp;</td>'+
	'</tr>'+
	
	
	
	'<tr>'+
	'<td class="tdClass" colspan="5" rowspan="2" style="text-align:left;font-size: 12px;border-right: 2px solid black;border-top: 2px solid black;" ><strong>Amount Chargeable (in words) :</strong>&nbsp;'+word+'</td>'+
	'<td class="tdClass" colspan="2" style="text-align:left;border-right: 2px solid black;border-top: 2px solid black;">Gross Total</td>'+
	'<td class="tdClass" style="text-align:left;border-right: 2px solid black;border-top: 2px solid black;">&nbsp;'+totalVal.toFixed(2)+'</td>'+
'</tr>'+
	
	  '<tr>'+
		'<td class="tdClass" colspan="2"style="text-align:left;border-right: 2px solid black;border-top: 2px solid black;">P& F Charges</td>'+
		'<td class="tdClass" style="border-top: 2px solid black;">&nbsp;</td>'+
	  '</tr>'+															

	  '<tr>'+
	  	'<td class="tdClass" colspan="5" rowspan="2" style="text-align:left;border-right: 2px solid black;"><strong>Tax In Words :'+word1+'</strong></td>'+
	  	'<td class="tdClass" colspan="2" style="text-align:left;border-right: 2px solid black;">---</td>'+
	  	'<td class="tdClass" >&nbsp;</td>'+
	  '</tr>'+

	  '<tr>'+
	  	'<td class="tdClass" colspan="2" style="text-align:left;border-right: 2px solid black;">---</td>'+
	  	'<td class="tdClass" >&nbsp;</td>'+
	  '</tr>'+

	  '<tr>'+
	  	'<td class="tdClass" colspan="5" rowspan="2" style="text-align:left;border-right: 2px solid black;"><strong>Serial No. :</strong></td>'+
	  	'<td class="tdClass" colspan="2" style="text-align:left;border-right: 2px solid black;">'+r.data[0].nm_tax1+'@'+r.data[0].per_tax1+'</td>'+
	  	'<td class="tdClass" >'+r.data[0].tax_val1+'</td>'+
	  '</tr>'+

	  '<tr>'+
	  	'<td class="tdClass" colspan="2"  style="text-align:left;border-right: 2px solid black;"><strong>'+r.data[0].nm_tax2+'@'+r.data[0].per_tax2+'</strong></td>'+
	  	'<td class="tdClass" style="text-align:left;border-right: 2px solid black;">'+r.data[0].tax_val2+'</td>'+
	  '</tr>'+

	  '<tr>'+
	  	'<td class="tdClass" colspan="5" rowspan="3" style="text-align:left;border-right: 2px solid black;">Mode / Terms of Payment :</td>'+
	  	'<td class="tdClass" colspan="2" style="text-align:left;border-right: 2px solid black;"><strong>Additional Duty</strong></td>'+
	  	'<td class="tdClass" >&nbsp;</td>'+
	  '</tr>'+

	  '<tr>'+
	  	'<td class="tdClass" colspan="2" style="text-align:left;border-right: 2px solid black;"><strong>Others</strong></td>'+
	  	'<td class="tdClass" >&nbsp;'+r.data[0].others+'</td>'+
	   '</tr>'+
	   
	   '<tr>'+
	  	'<td class="tdClass" colspan="2" style="text-align:left;border-right: 2px solid black;"><strong>Grand Total</strong></td>'+
	  	'<td class="tdClass" >'+grandTtotal.toFixed(3)+'</td>'+
	   '</tr>';
	  
list +='</table></td></tr>'+

'<tr>'+
'<td class="tdClass" style=" font-size:12px;border-right:2px solid black;border-left:2px solid black;"><font-size="medium"><strong>VAT Declaration :</strong></font><br>'+
'<b>&nbsp;</b></td>'+
'<td class="tdClass" style="font-size:12px;border-right:2px solid black;"><center><strong>For ESS ENN Auto CNC Pvt Ltd</strong></center><br>'+
'<hr style="border: 2px solid black">'+
'<center> <strong>Authorised Signatory</strong></center></td>'+
'</tr>'+


	'</table>'+
'</td>'+
'</tr>';

var	tempButton = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPgForExcel(\'#venDetails\')">Export to Excel </button>&nbsp;&nbsp;'+
'<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPrintorMailPurchaseOrder\')">Print Preview </button>&nbsp;&nbsp;';
var list2='<table>'+
'<tr><td class="tdClass">'+header+'</td></tr>'+
'<tr>'+
//templist2+
'</tr>'+
'</table>';
$('#venDetails').html(list2);

$('#buttonForPoPrint').html(tempButton);
$('#displayPrintorMailPurchaseOrder').hide();
$('#createPrintorMailPurchaseOrder').show();
$('.DisplayAmendPurchaseOrder').html(list);

}

},'json');

$('#'+DispDiv).show();
$('#'+HideDiv).hide();

}

function foo(){
	alert("hello");
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


function ControlExportInvoice3(HideDiv,DispDiv,id_fg_sales_inv,id_ven)
{
	var currentLocation = window.location.href;
		
		var url='';
		
		
		var url=currentLocation.slice(0, -37)+"Print_Invoice";
	$.post(url,{action : 'PreviewExportInvoice',id_sales_invoice:id_fg_sales_inv,id_ven:id_ven},function (r){
		
		var temp='';
		var slno=0;
		var x = 0;
		var list="";
		for( x = 0; x < r.data.length ; x++)
		{
					var wbst_num='',gst_num='',bnm_num='',mode_of_delv='',tod_dest='', typ_of_po='',remarks='';
					if(r.data[0].remarks !=undefined){remarks=r.data[0].remarks;}
					
					if(r.data[0].bnm_num !=undefined){bnm_num=r.data[0].bnm_num;}
					if(r.data[0].gst_num !=undefined){gst_num=r.data[0].gst_num;}
					if(r.data[0].wbst_num !=undefined){wbst_num=r.data[0].wbst_num;}
					if(r.data[0].mode_of_delv !=undefined){mode_of_delv=r.data[0].mode_of_delv;}
					if(r.data[0].tod_dest !=undefined){tod_dest=r.data[0].tod_dest;}
					
					var state=r.venDetails[0].state,commast='';
					if(state==undefined){state='';}else{state=r.venDetails[0].state;commast=',';}
					var country=r.venDetails[0].country,commastcnt='';
					if(country==undefined){country='';}else{country=r.venDetails[0].country;commastcnt='.';}
					var city=r.venDetails[0].city,commastcntcty='';
					if(city==undefined){city='';}else{city=r.venDetails[0].city;commastcntcty=',';}
					
					var phone=r.venDetails[0].phone,commaphone='';
					if(phone==undefined){phone='';}else{phone=r.venDetails[0].phone;commaphone=',';}
					var ct_mailid=r.venDetails[0].ct_mailid,commact_mailid='';
					if(ct_mailid==undefined){ct_mailid='';}else{ct_mailid=r.venDetails[0].ct_mailid;commact_mailid=',';}
					
					var pin=r.venDetails[0].pin,commact_pin='';
					if(pin==undefined){pin='';}else{pin=r.venDetails[0].pin;commact_mailid=',';}
					
					
					var cst=r.venDetails[0].cst;
					if(cst==undefined){cst='';}else{cst=r.venDetails[0].cst;comma=',';}
					
					list +='<table class="table table-bordered DisplayAmendPurchaseOrder" align="center" style="width:98%;border: none;border-left-style: none;border-right-style: none;page-break-before:always" >'+
						'  <thead class=""><tr ">'+
							
					     	'<td class="tdClass" colspan="6" style="border: none;"<center><font size="2"><b><b style="font-size: medium;margin-left: 450px;"><center> Invoice</center></b></font></center> <b style="float:right;font-size: 12px;"></b><center><font size="2"><b></td>'+
							'  </tr>'+
							
							/*'  <tr id="header2" >'+
							'<td class="tdClass" colspan="8" style="border: 1px solid black;">&nbsp; <br><font></font>'+
							'&nbsp;<br>'+
							'<font size="small">Tel :&nbsp; &emsp;&emsp;&emsp;  Fax :&nbsp; <br>'+
							'<font size="small">E-mail :&nbsp;</strong> <br>'+
							'<font size="medium">GSTIN NO :&nbsp; &emsp;&emsp;&emsp;PAN NO :&nbsp; &emsp;&emsp;&emsp;CIN NO :&nbsp;<br></td>'+
							'  </tr>'+*/
							
							'<tr  id="header2" style="line-height: 20px;">'+
						'<td class="tdClass" colspan="1" rowspan="7" style="border: 1px solid black; font-size: 12px;border-right:none;">'+
						' <Strong>&nbsp;Exporter</Strong><br> <div><img src="../InvoiceScanFile/'+r.company[0].file_name+'" style="width:150px;height: 60px;"></div><font-size="large"><br>'+
						' <b>&nbsp;'+r.venDetails[0].nm_ven+'</b><br>&nbsp;'+r.venDetails[0].add1+','+r.venDetails[0].add2+','+r.venDetails[0].city+','+r.venDetails[0].pin+' <br>'+
						' &nbsp;Tel No : &nbsp;&nbsp;'+r.venDetails[0].phone+'<br>&nbsp;PAN NO.   :&nbsp;'+r.venDetails[0].pan+' <br>'+
						' &nbsp;E-mail :&nbsp;&nbsp;'+r.venDetails[0].mailid+'<br>'+
                        '</td>'+
									'<td class="tdClass"   style="border: 1px solid black;border-bottom: none;border-right:none;">&nbsp;<Strong style="font-size: 12px;border-right:none;width:120px;">Invoice No &nbsp;&nbsp;</Strong></td>'+
									'<td class="tdClass"   style="border: 1px solid black;border-bottom: none;font-size: 12px;border-right:none;">&nbsp;Date :&nbsp;&nbsp;</td>'+
					'</tr>'+
					
					'<tr style="border: 1px solid black;">'+
					'<td class="tdClass"  style="border: 1px solid black; border-top: none;font-size: 12px;border-right:none;border-bottom:none;">'+r.data[0].no_inv+'</td>'+
					'<td class="tdClass"   style="border: 1px solid black;border-top: none;font-size: 12px;border-right:none;border-bottom:none;">'+r.data[0].dtinvoice+'</td>'+
			    	'</tr>'+
			    	
			    	
		        	'<tr style="border: 1px solid black;">'+
		        	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;font-size: 12px;border-top:none;"><b>&nbsp;PAN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].pan+'</td>'+
			        '</tr>'+
			        
			        '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;GSTIN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].fax+'</td>'+
				    '</tr>'+
				    
				    '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="display: none;border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;TIN No :</b>&nbsp;&nbsp;&nbsp;</td>'+
				    '</tr>'+
				    
				    '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;CIN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].kst+'</td>'+
				    '</tr>'+
				    
				    '<tr style="border: 1px solid black;">'+
			    	'<td class="tdClass" colspan="4" style="border: 1px solid black;border-bottom: none;border-top: none;font-size: 12px;"><b>&nbsp;TIN No :</b>&nbsp;&nbsp;&nbsp;'+r.venDetails[0].tin+'</td>'+
				    '</tr>'+
				    
								
						
					
					'<tr>'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-bottom:none;border-top:none;border-right:none;"><Strong style="font-size: 12px;">&nbsp;Consignee</strong>&nbsp;'+
					'<td class="tdClass" colspan="4"   style="border: 1px solid black;border-bottom:none;border-top:none;"><Strong style="font-size: 12px;"> &nbsp;Ship to </strong>&nbsp;'+
					'</tr>'+
					 
					'<tr>'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-bottom: none;border-top:none;border-right:none;"><Strong style="font-size: 12px;">&nbsp;'+r.company[0].nm_com+'</strong>&nbsp;'+
					'<td class="tdClass" colspan="4"   style="border: 1px solid black;border-bottom: none;border-top:none;"><Strong style="font-size: 12px;">&nbsp;'+r.company[0].nm_com+'</strong>&nbsp;'+
					'</tr>'+
					
					'<tr>'+
					'<td class="tdClass" colspan="1" style="border: 1px solid black;border-top:none;font-size: 12px;border-right:none;border-bottom:none;">&nbsp;'+r.company[0].add1+',<br>'+r.company[0].city+'-'+r.company[0].pin+','+r.company[0].nm_state+','+r.company[0].country+'&nbsp;'+
					'<td class="tdClass" colspan="4"   style="border: 1px solid black;border-top:none;font-size: 12px;border-bottom:none;">&nbsp;'+r.company[0].add1+',<br>'+r.company[0].city+'-'+r.company[0].pin+','+r.company[0].nm_state+','+r.company[0].country+' &nbsp;'+
					'</tr>';
				
		        
						if(r.data.length > 0)
				{	var z=x+12;
					if(z > r.data.length){
						z=r.data.length;
					}
							
						temp='  <table id="productlist'+z+'" class="table  table-bordered DisplayAmendPurchaseOrder" align="center" style="width:98%;height: 400px;"><tr class="tableHeader info" style="height:10px;">'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 60px;border-right:none;width:30px !important;"><strong ><center>SL No.</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-right:none;width:90px !important;"><strong ><center>PO NO.</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-right:none;"><strong ><center>Description of Goods</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 70px;border-right:none;"><strong ><center>Quantity in Nos </center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 90px;border-right:none;"><strong ><center>Rate</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 90px;border-right:none;"><strong ><center>Tax1</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 90px;border-right:none;"><strong ><center>Tax2</center></strong></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;width: 100px;text-align:right;"><strong >Amount (&nbsp;'+r.data[0].nm_curr+'&nbsp;)</strong></td>'+
						'</tr>';
						var totalVal=0;
						var grandTtotal=0;
						var total_qty=0;
						
						var total_tax_val=0;
				var totalPrice=0,subTotalPrice=0;
				
					
					for(var i = x; i < z ; i++)
					{
					params=r.data[i];
						var total_price=0;
						total_price= parseFloat(params.qty) *parseFloat(params.un_prc);
					totalVal +=total_price;
					total_qty +=parseFloat(params.qty);
					grandTtotal +=parseFloat(params.gr_tot);
					total_tax_val  =parseFloat(r.data[0].tax_val1)+parseFloat(r.data[0].tax_val2);
					var currency='';
					if(params.cd_curr=='RS'){
						currency='₹';
					}
					else if(params.cd_curr=='EURO'){
						currency='€';
					}
					else if(params.cd_curr=='USD'){
						currency='$';
					}
					else if(params.cd_curr=='GBP'){
						currency='£';
					}
					else{
						currency=' ';
					}
					temp += '<tr style="height:21px;">'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;"><center>'+ ++slno +'.</center></td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;"><center>'+params.no_po+'</center></td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align:left;">&nbsp;&nbsp;'+params.model+'</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;"><center>'+params.qty+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;"><center>'+params.un_prc+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;"><center>'+params.tax_val1+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;"><center>'+params.tax_val2+'</center></td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;text-align: right;">'+params.gr_tot+'</td>'+
					'</tr>';
					x=i;	
							}
							temp += '<tr class="rowid'+i+'">'+
				
					'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+	
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;border-right:none;text-align: right;">&nbsp;</td>'+
						'<td class="tdClass" style="font-size: 13px;border: 1px solid black;border-top: none;border-bottom: none;text-align: right;">&nbsp;</td>'+
				'</tr>'
				
				'</tr>';
					//var shubhamcurr=parseFloat(totalVal);
					var shubhamcurr=parseFloat(r.total[0].total);
					var word=number2text(shubhamcurr);
					console.log(word);

					var shubhamcurr1=parseFloat(total_tax_val);
					var word1=number2text(shubhamcurr1);
					console.log(word1);
                	
				
					list +=temp;
					
					list+='<table id="hidetotal'+i+'" class="table  table-bordered " align="center" style="display:none;width:98%;margin-top: -23px;"><tr class="total'+i+'" >'+
					'<td class="tdClass" colspan="3" style="text-align:left;font-size: 12px;border: 1px solid black;border-right:none;" ><strong>&nbsp;Amount Chargeable (in words) : '+word+'</strong></td>'+
					'<td class="tdClass" colspan="3" style="text-align:left;border: 1px solid black;font-size: 12px;width: 270px;"><b>&nbsp;Grand Total:</b>&nbsp;&nbsp;<span style="float:right;">'+currency+'&nbsp'+shubhamcurr.toFixed(3)+'</span></td>'+
					
					
					 '  </tr></table>';
				
				
                 
                
                
				var netweight='';
				var grossweight='';
				if(r.data[0].gross_wt==undefined){
				  grossweight='0'   ;
		netweight='0';
                      }
else{

netweight=r.data[0].net_wt;
grossweight=r.data[0].gross_wt;

}
               
				list+='<table id="hidepaymethod'+i+'" class="table table-bordered " align="center" style="display:none;width:98%;margin-top: -23px;"><tr>'+
					'<td class="tdClass" colspan="6" style="text-align:left;font-size: 12px;border: 1px solid black;border-bottom:none;" ><strong>&nbsp;Payment Method:-<br>&nbsp;</strong></td>'+
					
					
					
					 '  </tr>';
				list+='<table class="table table-bordered " align="center" style="width:98%;margin-top: -23px;"><tr>'+
					'<td class="tdClass" colspan="2" style="text-align:left;font-size: 12px;border: 1px solid black;border-right:none;" ><strong>&nbsp;Declaration :<br>&nbsp;We declare that this Invoice shows the actual price of the goods described and that all particulars are true and correct.</strong></td>'+
					'<td class="tdClass" colspan="4" style="text-align:left;border: 1px solid black;width: 400px;height: 80px;"></td>'+
					
					
					 '  </tr>';
			
					/* '  <tfoot><tr >'+
					   '<td class="tdClass" colspan="4" rowspan="2" style=" font-size:12px;border: 1px solid black;"><font-size="medium">VAT Declaration :</font><br>'+
					   '<b>&nbsp;</b></td>'+
					   '<td class="tdClass" colspan="4" style="font-size:12px;border: 1px solid black;"><center><br>For ESS ENN Auto CNC Pvt Ltd<br>&nbsp;</center></td>'+
					   '  </tr>'+
						
					   
					   '<tr >'+
					   '<td class="tdClass" colspan="4" style="font-size:12px;border: 1px solid black;"><center><br><SPAN STYLE="text-decoration:overline">Authorised Signatory</SPAN><br>&nbsp;</center></td>'+
				      '  </tfoot></tr>';*/
					
				}	
					
					
								
					
					
					
				var	temp = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPrintorMailPurchaseOrder\')">Print Preview </button>&nbsp;&nbsp;';

						
							$('#buttonForPoPrint').html(temp);
							$('#InvoiceDetailReport').hide();
							$('#InvoiceDetailReport_wrapper').hide();
							$('#createPrintorMailPurchaseOrder').show();
							$('.DisplayAmendPurchaseOrder').html(list);
		}
		/*console.log(i);tb.style.display = "none";
					alert(i);*/
				 $('#hidetotal'+i+'').show();
			     $('#hidepaymethod'+i+'').show();
                 $('#hideproduct'+i+'').show();
                // $('#productlist'+i+'').style.height = "10px";
                  $('#productlist'+z+'').css({'height':'200px'});
                  $('#productlist'+z+'').css({'margin-top':'-23px'});
		//$('.hidetable').hide();
					
                    	//$('#'+HideDiv).hide();
					//$('#'+DispDiv).show();
					//}
				/*else
				{
					bootbox.alert("Something went wrong Please try again.");
				}*/
				
			},'json');

}
