<div style="padding:10px;">
	<!--  <a href="#">Organization Master > </a><a href="#"> Send Email</a>-->
</div>
<script type="text/javascript">

$(function() {
	DisplayDropDownData('M_Dept','deptDataForSendEmail',function (status){
		if(status)
		{
			
		}
	},'json');
});

</script>


<div id="sendEmail">
	<form action="" name="submitSendEmail" id="submitSendEmail">
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 110px;">Required Details For Sending Email</p></td>
			</tr>
			
			<tr>
				<td><strong>Department Name<font color="red">*</font></strong></td>
				<td>
				<select id="deptDataForSendEmail" class="common-validation" name="id_dept" data-valid="mand">
					<option></option>
				</select>
				</td>
			</tr>
			<input type="hidden" name="action" value="SendEmail">
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:220px;"   class="btn btn-primary sendMailBtn" onclick="SendEmail();">Send</button>
				</td>
			</tr>
			
		</table>
	</form>
	</div>