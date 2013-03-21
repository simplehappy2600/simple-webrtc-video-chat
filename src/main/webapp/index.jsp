<html>

<body>
<h2>Hello World!</h2>

<input type="button" onclick="test()" value="test"> 

<script type="text/javascript">

	var socket = new WebSocket("ws://127.0.0.1:8080/WebSocketServer/SampleServlet");

	socket.onmessage = function(message) {	
		alert(message.data);	
	};	

	function test()
	{
		var s = "111122334";	
		socket.send(s);
	}	

</script>

</body>
</html>
