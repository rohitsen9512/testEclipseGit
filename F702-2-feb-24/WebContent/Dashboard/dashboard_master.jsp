



<%-- 
<%HttpSession session2 = request.getSession(); %>
	<script type="text/javascript">
		$(function (){
			$( "#menu" ).menu();
			var UserType = '<%= (String)session2.getAttribute("UserType") %>';
			//LoginAccess(UserType , 'dashboard.properties' , 'nav-item');
		}); --%>
		
		
			
		<ul class="nav nav-treeview sidebar_css">
              <li class="commonActive verbalds nav-item" onclick="">

             <a href="javascript:changeSubContent('Dashboard/DeviceStatus.jsp','verbalds_event')" class="nav-link verbalds_event">
             <i class="fas fa-hand-point-right"></i>
                  <p>Hardware Status</p>
                </a>
              </li>
              
			<li class="commonActive allassetstore nav-item "  onclick="">
             <a href="javascript:changeSubContent('Dashboard/allDeviceStatus.jsp','allassetstore_event')" class="nav-link allassetstore_event">
             <i class="fas fa-hand-point-right"></i>
                  <p>Complete Stock</p>
                </a>
              </li>
              <li class="commonActive groupwise nav-item " onclick="">
             <a href="javascript:changeSubContent('Dashboard/groupwise.jsp','groupwise_event')" class="nav-link groupwise_event" >
             <i class="fas fa-hand-point-right"></i>
                  <p>Category Wise Asset Register</p>
                </a>
              </li>
              <li class="commonActive subgroupwise nav-item " onclick="">
             <a href="javascript:changeSubContent('Dashboard/subgroupwise.jsp','subgroupwise_event')" class="nav-link subgroupwise_event" >
             <i class="fas fa-hand-point-right"></i>
                
                  <p>Sub-Category Wise</p>
                </a>
              </li>
              <li class="commonActive assetStatus nav-item" onclick="">
             <a href="javascript:changeSubContent('Dashboard/assetStatus.jsp','assetStatus_event')" class="nav-link assetStatus_event" >
                  <p>Asset Status</p>
                </a>
              </li>
               <li class="commonActive locwise nav-item " onclick="">
             <a href="javascript:changeSubContent('Dashboard/IT_SubLocationWise.jsp','locwise_event')" class="nav-link locwise_event">
             <i class="fas fa-hand-point-right"></i>
                  <p>Location Wise</p>
                </a>
              </li>
               <li class="commonActive transferdash nav-item " onclick="">
             <a href="javascript:changeSubContent('Dashboard/tranferdash.jsp','transferdash_event')" class="nav-link transferdash_event">
                <i class="fas fa-hand-point-right"></i>
                  <p>Transfer</p>
                </a>
              </li>
               
               <li class="commonActive helpdeskdash nav-item "  onclick="">
             <a href="javascript:changeSubContent('Dashboard/helpdeskdash.jsp','helpdeskdash_event')" class="nav-link helpdeskdash_event">
             <i class="fas fa-hand-point-right"></i>
                  <p>Help Desk</p>
                </a>
              </li>
              <li class="commonActive warrantydash nav-item " onclick="">
<a href="javascript:changeSubContent('Dashboard/warrenty_dash.jsp','warrantydash_event')" class="nav-link warrantydash_event">
<i class="fas fa-hand-point-right"></i>
<p>AMC/WARRANTY</p>
</a>
</li>

	
					
						
						
						
						
								</ul>
							
						
					
				
 <script type="text/javascript">
 /* $(function(){
			var currentDate = new Date();
				
				$('.datepicker').each(function () {
			        if ($(this).hasClass('hasDatepicker')) {
			            $(this).removeClass('hasDatepicker');
			        } 
			         $(this).datepicker({
			        	 yearRange: '1985:2025',
				      changeMonth: true,
				      changeYear: true,
				      dateFormat: "dd/mm/yy",
				      autoSize: true,
				      altFormat: "dd/mm/yy",
				    });
			    });
				
				$('input[name="enddate"]').datepicker("setDate", currentDate);
				currentDate.setMonth(currentDate.getMonth() - 1);
				$('input[name="startdate"]').datepicker("setDate", currentDate);
				
			}); */ 


		function HideShowFunForReport(val)
		{
		if(val.value == 'b')
			{
			$('#hideShowFloor').hide();
			}
		else
			{
			$('#hideShowFloor').show();
			}
			
		}

		/* $(function (){
			
			DisplayDropDownDataForReport('M_Loc','assetDivForLocAssetReport',function (status){
				if(status)
				{
					
				}});
			DisplayDropDownDataForLGroup('M_Asset_Div','assetDivForStoreAssetLReport','CapG',function (status){
				if(status)
				{
					
				}});	
			
		});
		$(function() {
			
			$('button[name="update"]').addClass('hideButton');
			DisplayDropDownDataForReport('M_Dept','fordept');
			DisplayDropDownDataForGroup('M_Asset_Div','assetDivFordeptReport','CapG',function (status){
				if(status)
				{
					
				}});
					
			
		}); */
	
	
	</script>
	