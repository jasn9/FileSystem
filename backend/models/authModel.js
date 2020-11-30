const mongoose = require('mongoose');

const AuthSchema=new mongoose.Schema({
  Token:{
    type: String,
    required: true
  },
  AndroidId:{
    type: String,
    required: true
  },
  ClientId:{
    type: String,
    required: true
  },
  TimeStamp:{
    type: Date,
    required: true
  }
});
const Auth = mongoose.model("Auth", AuthSchema);
module.exports = Auth;
