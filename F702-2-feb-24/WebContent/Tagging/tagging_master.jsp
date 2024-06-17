
<script type ="text/javascript" src="js/taggingCommon.js"></script>
<script type ="text/javascript" src="All_Js_File/Tagging/tag_print.js"></script>
<script type ="text/javascript" src="All_Js_File/Tagging/CreateGatePass.js"></script>
<script type ="text/javascript" src="All_Js_File/Tagging/Print_gatepass.js"></script>
<script type ="text/javascript" src="All_Js_File/Tagging/Receive_Asset.js"></script>

<%HttpSession session2 = request.getSession(); %>
	<script type="text/javascript">
		$(function (){
			$( "#menu" ).menu();
			var UserType = '<%= (String)session2.getAttribute("UserType") %>';
			//LoginAccess(UserType , 'taggingAccess.properties' , 'commonActive');
		});
		
		
		</script>

	<table align="center" height="100%" width="100%" border="1" class="table">
			<tr>
				<td id="leftBody" width="16%">
					 
					<div class="subLeftMenu">
						<ul id="menu" class="ui-menu ui-widget">
							<li class="tagPrinting commonActive" onclick="changeSubContent('Tagging/Tag_printing.jsp','tagPrinting')"><a href="#">Tag Printing</a></li>
							<li class="scanning commonActive" onclick="changeSubContent('Tagging/Scanning.jsp','scanning')"><a href="#">Scanning</a></li>
							<!--  <li class="assetVerification commonActive" onclick="changeSubContent('Tagging/Asset_verification.jsp','assetVerification')"><a href="#">Asset Verification</a></li>
							
							<li class="discrepancyReport commonActive" onclick="changeSubContent('Tagging/Discrepancy_report.jsp','discrepancyReport')"><a href="#">Discrepancy Report</a></li>
							
							<li class="createGatepass commonActive" onclick="changeSubContent('Tagging/Create_gatepass.jsp','createGatepass')"><a href="#">Create Gate Pass</a></li>
							<li class="printGatepass commonActive" onclick="changeSubContent('Tagging/Print_gatepass.jsp','printGatepass')"><a href="#">Print Gate Pass</a></li>
							<li class="receiveAsset commonActive" onclick="changeSubContent('Tagging/Receive_Asset.jsp','receiveAsset')"><a href="#">Receive Asset</a></li>
							-->
						</ul>
					</div>
				
				</td>
				<td id="rightBody" width="84%">
					<div id="rightBodyContent">
					<img alt="" src="image/tagging.jpg">		
					</div>
				
				
				</td>
			</tr>
	</table>