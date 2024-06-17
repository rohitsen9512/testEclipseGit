<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
a {
    color: green;
}
.button {
  display: inline-block;
  padding: 15px 25px;
  font-size: 24px;
  cursor: pointer;
  text-align: center;	
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: #4CAF50;
  border: none;
  border-radius: 15px;
  box-shadow: 0 9px #999;
}

.button:hover {background-color: #3e8e41}

.button:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
</style>
 <script>
 DisplayDropDownDataForGroup('M_Asset_Div','groupDataForModel','CapG',function (status){
		if(status)
		{
		 
		}});
 //assetStatus();
assetStatus1();
 //allDevice();
 allDevice1();
 groupWise();
 
 subGroup();
 
 
 
 </script>
 <body>

<b style="color:green">Category</b>
		<select id="groupDataForModel"  name="group"  style="width:140" onChange="groupWise();subGroup();assetStatus1();allDevice1()">
						<option value="">Select</option>
	   </select>
	    <div class="card-body-dash"> 

 <div id="container2" 
 class="containeritem"></div>	
 
 <div id="container3"
		class="containeritem "></div>	
		
		
		</div>
	

<div class="card-body-dash"> 

 <div class="containeritem">
 <span style="font-size: 20px; margin-left:14rem;">Hardware Status</span>
 <canvas id="clients" width="600px" height="350px"  ></canvas>
 </div>
  <div class="containeritem">
  <span style="font-size: 20px;margin-left:14rem;">Stock Allocation</span>
 <canvas id="allDevice" width="600px" height="350px" ></canvas>
 </div>
  </div>
 </body>
	

<script type="text/javascript">
 



</script>
