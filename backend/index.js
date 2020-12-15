const express=require('express');
const mongoose=require('mongoose');
const authRoutes = require('./routes/routers.js');
var cors = require("cors");
const app=express()
const server=require('http').createServer(app);
const authModel = require('./models/authModel');

//Cors is used to restrict the access to backend resources
var corsOptions = {
    'origin': '*',
    'methods': 'GET,HEAD,PUT,PATCH,POST,DELETE',
    'credentials':false
  };
app.use(cors());
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  next();
  });
//---------------
app.use(express.json());

//Connect ot MongoDb Atlas
const uri = process.env.MONGODB_URL || "mongodb+srv://admin:admin123@cluster0.hnrie.mongodb.net/testdb?retryWrites=true&w=majority";
mongoose.connect(uri,{
	useNewUrlParser: true,
	useUnifiedTopology: true
});

app.use(authRoutes);

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/testing.html');
});

// set port, listen for requests
const PORT = process.env.PORT || 8081;
server.listen(PORT, () => console.log(`Listening on ${PORT}`));
//Web socket connections
const { Server } = require('ws');
const wss = new Server({ server });
console.log("in here1")
wss.on('request',(req)=>{
    console.log("A new request")
    console.log(req);
})
//To differentiate between clients -(2nd ans.) https://stackoverflow.com/questions/13364243/websocketserver-node-js-how-to-differentiate-clients
function snd_msg(tag,android,connection_code,value){//helper function to
	var ini_data={
		'tag':tag,
		'android':android,
		'connection_code':connection_code,
		'value':value
    }
 	return (JSON.stringify(ini_data));   
}


wss.on('connection', (ws) => {
	console.log('Client connected');
	ws.send(snd_msg('ack',null,null,'Connection established'));
	ws.on('message',(msg)=>{
		console.log('Msg for client: '+msg);
		var json_obj=msg;
		if(typeof msg=="string"){
			try{
				json_obj=JSON.parse(msg);
			}
			catch(err){
				console.log("Inside socket connection string to json "+err);
				ws.send(snd_msg('error',null,null,'Message must be in json stringfied form'));
			}
		}
		//checks
		if(json_obj['tag']==null||json_obj['connection_code']==null||json_obj['android']==null){
			ws.send(snd_msg('error',null,null,'Socket message should containe fields : first_msg,connection_code,android and optional fields value'));
		}
		else{
			if(json_obj['tag']==='firstMessage'){
				var Code=json_obj['connection_code']
				authModel.findOne({Token:Code}, async function(err,data){
					if(err||data==null){
			    		console.log("Inside socket connection in first_msg token checks"+err);
			    		ws.send(snd_msg('error',null,null,'Connection Code is wrong'));
			    	}
			    	else{
			    		if(json_obj['android'])
			    			ws.id=data['AndroidId']
			    		else
			    			ws.id=data['ClientId']
			    		ws.send(snd_msg('ack',null,null,'First message recieved Successfully'));
			    	}
				});
			}
			else if(json_obj['tag']==='getDirectory'||json_obj['tag']==='getFile'){
				var Code=json_obj['connection_code'];
				// var msg_snd=json_obj['value']
				authModel.findOne({Token:Code}, async function(err,data){
					if(err||data==null){
			    		console.log("Inside socket connection NOT in first_msg token checks"+err);
			    		ws.send(snd_msg('error',null,null,'Connection Code is wrong'));
			    	}
			    	else{
			    		var id_to_search="tmp";
			    		// console.log(json_obj['android'])
			    		if(json_obj['android']) id_to_search=data['ClientId'];
			    		else id_to_search=data['AndroidId']; 
			    		wss.clients.forEach((client) => {
		    				if(client.id==id_to_search){
		    					// console.log(client.id+" "+String(msg_snd));
		    					client.send(JSON.stringify(json_obj));
		    				}
		    			});
		    			ws.send(snd_msg('ack',null,null,'Message sent Successfully'));
			    	}
				});
			}
			else{
				ws.send(snd_msg('error',null,null,'Tag value must be either FirstMessage or GetDirectory or GetDirectory'));
				console.log("Wrong value inside tag");
			}
		}
	})
	setInterval(() => {
	  wss.clients.forEach((client) => {
	    client.send(snd_msg('ping',null,null,null));
	  });
	},10000);
  	ws.on('close', () => console.log('Client disconnected'));
});

/* Message format
{
	"tag":"firstMessage/getDirectory/ping/ack",
	"android":true,
	"connection_code":"1234012002",
	"value":"something"
}
*/

/*references - 
1)https://javascript.info/websocket
2)https://devcenter.heroku.com/articles/node-websockets
3)https://www.npmjs.com/package/websocket
*/
