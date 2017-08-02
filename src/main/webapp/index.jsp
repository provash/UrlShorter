<html>
<body>
	<h2>Welcome Shorter Url</h2>

	<form id="form1" runat="server">
		<div>
			<div>
				<div>Input your long URL :</div>
				<input id="longURL" type="text" style="minWidth:200px">
				<input id="createSU" type="button" value="Create Short URL" />
			</div>
			<hr />
			<div>
				<div>Your Short URL :</div>
				<div id="shortURL"></div>
			</div>
		</div>
	</form>
	
	<script type="text/javascript">
	
	var lonUrlInput = document.getElementById('longURL');
	var creatSuBtn = document.getElementById('createSU');
	var shortUrlDiv = document.getElementById('shortURL');
	 
	var request = new XMLHttpRequest();
	 
	request.onreadystatechange = function() {
	  if(request.readyState === 4) {
		 
		  creatSuBtn.disabled = false;
	    if(request.status === 200) { 
	    	shortUrlDiv.innerHTML = request.responseText;
	    } else {
	    	shortUrlDiv.innerHTML = 'An error occurred during your request: ' +  request.status + ' ' + request.statusText;
	    } 
	  }
	}
	 
	creatSuBtn.addEventListener('click', function() {
	  this.disabled = true;
	  var lUrl = lonUrlInput.value;
	  
	  if(lUrl != ""){
		  request.open('POST', '/su/', true);
	  	  request.send(lUrl);
	  }else
		  this.disabled = false;
	});
	
	</script>
</body>
</html>
