<!--Vendor_report.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#"> Reports ></a><a href="#">MIS report ></a><a href="#">Vendor report</a>-->
</div>

<div class="commonDiv" id="displayVendorReport">
	<form action="ReportView/Vendor_Report_View.jsp" method="POST" target="_new">
		<table class="commonTable table table-bordered" align="center" style="width:50%">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 135px;">Select a Vendor Type</p></td>
			</tr>
			<tr>
				<td><strong >Vendor Type</strong></td>
				<td >
					<select name="venType">
						<option value="All">All</option>
						<option value="Service">Service</option>
						<option value="Procured">Procured</option>
					</select>
				</td>
			</tr>
			<tr>
			<td colspan="2">
			<p style="margin-left: 175px;">
						<button type="submit"  class="btn btn-primary" >Search</button>
					</p>
			</td>
			</tr>
		</table>
	</form>
</div>
