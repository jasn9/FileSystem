


import React, { Component } from 'react'
import './App.css'
import Socket from './component/Socket' 
import { w3cwebsocket as W3CWebSocket } from "websocket";

const client = new W3CWebSocket('ws://file-system-backend.herokuapp.com');

 class App extends Component {
  constructor(props) {
    super(props)

    this.state = {
      isSuccess: false
    }
  }

  componentDidMount() {

   const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body : JSON.stringify({
          code:"1234219877",
          id:"24555"
        })
       };   

   var proxyUrl = 'https://cors-anywhere.herokuapp.com/',
    targetUrl = 'https://file-system-backend.herokuapp.com/verifyCode'

    fetch(proxyUrl + targetUrl, requestOptions)
    .then(response => {
      console.log("ajeet Ram singh")
      
      this.state.isSuccess = true
      console.log(response)
      console.log(this.state.isSuccess)
      //--------------------
    //   client.onopen = () => {
    //   console.log('WebSocket Client Connected');
    //   client.send('Ajeet ne bheja hai')
    // };
    // client.onmessage = (message) => {
    //   console.log(message);
    // };
    //----------------------
    
    })
    .catch(e => {
      console.log("ram ram g")
      console.log(e)
    })
    
    // console.log("ajeet singh")
    // console.log(response)

  //   .then(response => {
      
  //     console.log("ajeet singh")
  //    // this.setState({posts: response.data})
  //   })
  //   .catch(error => {
  //     console.log("ram ram g")
  //     console.log(error.headers)
  //   })
  // }

}
  render() {
    if (! this.state.isSuccess) {
       return (
      // <div>
      //   <p>Hello bolo World</p>
      // </div>
      <Socket/>
    )
    } else  {

      return (
      <div>
        <p>Hello World</p>
      </div>
      )
    }
   
  }
}

 export default App;


// import React, { Component } from 'react';
// import { w3cwebsocket as W3CWebSocket } from "websocket";

// const client = new W3CWebSocket('ws://file-system-backend.herokuapp.com');

// class App extends Component {
  
//   componentDidMount() {
//     client.onopen = () => {
//       console.log('WebSocket Client Connected');
//       client.send('Ajeet ne bheja hai')
//     };
//     client.onmessage = (message) => {
//       console.log(message);
//     };
//   }
  
//   render() {
//     return (
//       <div>
//         Practical Intro To WebSockets.
//       </div>
//     );
//   }
// }

// export default App;