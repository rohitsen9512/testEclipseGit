

<script type="text/javascript"
		src="All_Js_File/Order/PrintInvoice.js"></script>


<script type="text/javascript" src="https://unpkg.com/jszip/dist/jszip.min.js"></script>
<script src="js/docx-preview.js"></script>
<script type="text/javascript">
   
</script>


<script type="text/javascript">

document.getElementById("forback").style.display = "none";

$(function() {
	//$('#createPrintorMailPurchaseOrder').hide();
	var currentDate = new Date();
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      
	    });
	$('input[name="dt_to"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
	DisplayLeadExportInvoiceForPrint();
	
});
function forback(){
	window.location = $('.printInvoice_event').attr('href');
	
}

function printPg(elem)
{
	 w=window.open();
		w.document.write('<html><title>Export Invoice Print</title>');
		//w.document.write('<body onload="window.print()">' + $(elem).html() + '</body>');
		w.document.write('<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
		w.document.write('<link href="CSS/CommonPrint.css" rel="stylesheet" type="text/css">');
		
		w.document.write('<style>@page :last {display:none;}</style>');
		w.document.write($(elem).html());
		w.document.write('</html>');
		w.document.close();
}
function printPgForExcel(elem){
	window.open('data:application/vnd.ms-excel,' + encodeURIComponent( $('div[id$=venDetails]').html()));
	e.preventDefault();
}
function printlatterhead(elem)
{ w=window.open();
	w.document.write('<html><title>Purchase Order</title>');
	//w.document.write('<body onload="window.print()">' + $(elem).html() + '</body>');
	w.document.write('<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
	w.document.write('<link href="CSS/CommonPrint.css" rel="stylesheet" type="text/css">');
	w.document.write($(elem).html());
		w.document.write('<style>@page :first {margin-top:6cm;margin-bottom:2cm;scale:60%;}</style><html><head><title></title>');
	
	w.document.write('</html>');
	
	
	w.document.close();
	
	
	
	
	
	/* 
	 w=window.open();
		w.document.write('<html><title>Purchase Order</title>');
		//w.document.write('<body onload="window.print()">' + $(elem).html() + '</body>');
		w.document.write('<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
		w.document.write('<link href="CSS/CommonPrint.css" rel="stylesheet" type="text/css">');
		w.document.write($(elem).html());
		w.document.write('</html>');
		w.document.close(); */
}
 function Popup(data) 
{
    var mywindow = window.open('', 'my div', '');
    /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />');
    mywindow.document.write('');
    mywindow.document.write(data);
   // w.disableExternalCapture();
    mywindow.print();
    //window.onfocus=function(){ window.close();};
 } 
 


</script>
<script>
</script>
<!-- Content Wrapper. Contains page content -->
<!-- <section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>
					 Create Lead
					<button type="button" name="create btn" class="btn btn-primary led hideled"
						onclick="ControlLead('Create','displayLead','createLead','submitLead','O_Lead')">Create
						Lead</button>
				</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Order</a></li>
					<li class="breadcrumb-item">Lead</li>
				</ol>
			</div>
		</div>
	</div>
	/.container-fluid
</section> -->
<div class="card">
	<div id="displayPrintforInvoiceLead">
		<div class="card-body">
			<!-- <table width="100%" height="100%">
				<tr>
					<td colspan="4">
					<td><strong>From Date</strong></td>
					<td><input id="dt_frm" type="text" name="dt_frm" value=""
						class="form-control datepicker" data-valid="mand" readonly style="background-color:transparent;font-size: 1em;"></td>
					<td><strong>&nbsp;&nbsp;To Date</strong></td>
					<td><input id="dt_to" type="text" name="dt_to" value=""
						class="form-control datepicker" data-valid="mand" readonly style="background-color:transparent;font-size: 1em;"></td>
					<td>
						<button type="button" style="margin-top: 10px; margin-left: 25px;"
							class="btn btn-primary"
							onclick="DisplayLeadExportInvoiceForPrint()">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table> -->
			<table id="leadPrForDisplaytd"
				class="table table-bordered table-hover leadPrForDisplaytd">
			
			</table>
		</div>
	</div>
</div>

<div class="card">
<div class="commonDiv DisplayAmendInvoice" id="createPrintforInvoceLead">


<div id="venDetails" style="width: 250%;">

</div>
</div>
</div>

<div id="buttonForPoPrint">

</div>
<button name="cancel" type="button" id="forback" style="margin-left: 592px; margin-top: -38px" class="btn btn-primary " 
										onclick="forback()">Back</button>
									
<script src='dist/jspdf.min.js'></script>
<script src='dist/html2pdf.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.3/jspdf.min.js"></script>
<script src="https://html2canvas.hertzen.com/dist/html2canvas.js"></script>
<script>

function getPDF(no_inv,dt_inv,nm_pstn){
	
   var pdfname=nm_pstn+dt_inv+no_inv;
	var HTML_Width = $(".DisplayAmendInvoice").width();
	var HTML_Height = $(".DisplayAmendInvoice").height();
	var top_left_margin = 15;
	var PDF_Width = HTML_Width+(top_left_margin*2);
	var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
	var canvas_image_width = HTML_Width;
	var canvas_image_height = HTML_Height;
	
	var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
	

	html2canvas($(".DisplayAmendInvoice")[0],{allowTaint:true}).then(function(canvas) {
		canvas.getContext('2d');
		
		console.log(canvas.height+"  "+canvas.width);
		
		
		var imgData = canvas.toDataURL("image/png", 1.0);
		var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
	    pdf.addImage(imgData, 'JPG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);
		
		
		for (var i = 1; i <= totalPDFPages; i++) { 
			pdf.addPage(PDF_Width, PDF_Height);
			pdf.addImage(imgData, 'JPG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
		}
		console.log(pdf);
	    pdf.save(pdfname+'.pdf');
	    var emaila = $('input[name="email"]').val();
	    var product = $('select[name="pprice"]').val();
	    var blob = pdf.output('blob');
	    newFile = new File([blob], product+emaila+'.pdf', {type: 'pdf/png'});
        var formData = new FormData();
	    
	    
	    
    });
};

function demoFromHTML() {
 

    var element = document.getElementById('createPrintforInvoceLead');

    
//html2pdf(element);
//console.log(html2pdf(element));
var pdf = new jsPDF();
var source = document.body;

html2pdf(element, pdf, function(pdf) {
var iframe = document.createElement('iframe');
iframe.setAttribute('style', 'position:absolute;right:0;top:0;bottom:0;height:100%;width:50%;');
document.body.appendChild(iframe);
console.log(iframe);
iframe.src = pdf.output('datauristring');

});

}

</script>











