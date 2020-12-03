
import React, { Component } from 'react';
import './style.css'
import { w3cwebsocket as W3CWebSocket } from "websocket";
import { base64StringToBlob } from 'blob-util';

const client = new W3CWebSocket('ws://file-system-backend.herokuapp.com');
const secondFile = [
     	{
          "path":"seeta","type":"folder"
     	},
     	{
         "path":"ram","type":"file"
     	}
     ] 

class Socket extends Component {
  
  constructor() {
  	super()
  	this.state = {
  		messge: 'welcome visitor',

  		directory:
  		[
  		{"path":"c/programs/cpp/practical"}
  		],

  		Files:
  		[
     	{
          "path":"c/programs/cpp/practical","type":"folder"
     	},
     	{
         "path":"d/7them/adsa/lab/ajeet.c","type":"file"
     	}
         ] 
  	}
  }

  componentDidMount() {
    client.onopen = () => {

      console.log('WebSocket Client Connected');

      var obj = { "tag": "firstMessage", "android": false, "connection_code": "1234219877", "value":{"path": "Nothing"} };
     // obj['value']=JSON.stringify(obj['value'])
      client.send(JSON.stringify(obj))

      var obj1 = { "tag": "getDirectory", "android": false, "connection_code": "1234219877", "value":{"path": "/storage/emulated/0"} };
      //obj1['value']=JSON.stringify(obj1['value'])
      client.send(JSON.stringify(obj1))

    };
    client.onmessage = (message) => {

      var object = JSON.parse(message.data);
      if(object.android == true ) {
       console.log("kya hai ye yar ")
       console.log(typeof object.value.directories[0])
       console.log( object.value.directories[0])
       if(object.value!==null) {
       this.setState({
       	Files :object.value.directories,
       	directory:object.value.directories.slice(0,1)
       })
        }

       
      }
      
     // console.log(this.state.directory)
    };
  }


    clickHandler = (file) => {
  	console.log("clicked bro")
  	console.log(file.path)

    console.log("type=",typeof file.type)

  	  if (file.type==="File") {
        console.log("this is file")
        var obj1 = { "tag": "getFile", "android": false, "connection_code": "1234219877", "value":{"path": file.path} };
      //obj1['value']=JSON.stringify(obj1['value'])
      client.send(JSON.stringify(obj1))
      console.log(obj1)
       client.onmessage = (event) => {
          
         // e.data instanceof Blob
          //console.log("event=",event)
          var object = JSON.parse(event.data);
          

       	   if( object.tag =="getFile") {
          console.log("inside onmessage ,inside if ",object)
          console.log(typeof object.value.data)

          const base64 = object.value.data

          const blob = base64StringToBlob(base64, "audio/mp3");

         
          var url = URL.createObjectURL(blob);
          window.open(url);
         }
       }

  	  } else {
  	  console.log("in deep")
      var obj1 = { "tag": "getDirectory", "android": false, "connection_code": "1234219877", "value":{"path": file.path} };
      //obj1['value']=JSON.stringify(obj1['value'])
      client.send(JSON.stringify(obj1))
      console.log(obj1)
     
       client.onmessage = (message) => {
      var object = JSON.parse(message.data);
      if(object.android == true ) {
       

       if(object.value!==null) {
       this.setState({
       	Files :object.value.directories,
       	directory:object.value.directories.slice(0,1)
       })
        }

      }
     }
    }  //else close

  		console.log(this.state.Files)
  };

   
   backHandler(path) {
   	console.log("back button clicked")
    var dummy = path
    console.log(typeof path)
    var ind=-1
    for(var i=0; i<dummy.length;i++)
    {
      if(dummy[i]==='/')
      	ind=i
      
      	
    }
    var real = dummy.substring(0,ind)
     ind=-1
     for(var i=0; i<real.length;i++)
    {
      if(real[i]==='/')
      	ind=i
      
      	
    }
   real  = real.substring(0,ind)

    var dd ={"path":real}
    //---------------
      console.log("in back")
      var obj1 = { "tag": "getDirectory", "android": false, "connection_code": "1234939801", "value":dd };
      //obj1['value']=JSON.stringify(obj1['value'])
      client.send(JSON.stringify(obj1))
      console.log(obj1)
     
       client.onmessage = (message) => {
      var object = JSON.parse(message.data);
      if(object.android == true ) {
       

       if(object.value!==null) {
       this.setState({
       	Files :object.value.directories,
       	directory:object.value.directories.slice(0,1)
       })
        }

      }
     }
    //---------------
    

    
    console.log("dd=",dd)
    // this.setState({
    // 	directory:dd
    // })
    console.log("you are inside the backHandler")
    console.log(this.state.directory[0].path)
   }
  
  render() {


  	const directory2 = this.state.directory
  	console.log("jatin")
     console.log(this.state)
     const directoryName =directory2.map(directory2 => 

      <div>
      <table>
      <tr> 
      <td> <button onClick={() => this.backHandler(directory2.path)}>Back</button></td> 
      </tr>
      </table>
      </div>
     )


  	const file2=this.state.Files;


  	const fileName = file2.map(file2 => 
  		

      <div id="conainer">
        <center>

      <table class="gfg">
      
       <tr > <button type="button" onClick={() => this.clickHandler(file2)}>
            <td class="geeks">  {file2.path} </td>

            <td class="geeks"> {file2.type} </td>
           </button>
       </tr >
   
       </table>
       </center>
      </div>
      )
     
     

      

    return (

     <div id="conainer">
     <h3 class="backButton">
     {directoryName}
     </h3>
     <center>
     <h1>Directory Contents</h1>
     <table>
         
            <tr>
          
              <th>Filename</th>
              <th>Type</th>
            </tr>
     </table>
     
     <table class="gfg">
            <tr> {fileName}  </tr>
          
     </table>
     </center>
     </div>
     

    );
  }
}

export default Socket;



