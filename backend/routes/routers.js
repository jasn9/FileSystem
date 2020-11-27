const express = require('express');
const authModel = require('../models/authModel');
const app = express();

//Helper Functions
function generateCode(id){
    console.log("in generateCode")
    var chars='0123456789'.split('')
    var res='';
    for(var i=0;i<6;i++){
        var x=Math.floor(Math.random()*chars.length);
        res+=chars[x]
    }
    return id+res;
}

//get Token for Authentication
app.post('/getCode', async (req,res) => {
	console.log("In getCode"+req.body.id);
	if (!req.body.id) {
        res.status(400).send({ message: "Request should contain field id!" });
        return;
    }
    //check for code reduntancy and then return unique code
    var token=generateCode(req.body.id);
    const temp_auth=await authModel.findOne({Token:token})
    while(temp_auth!=null){
    	token=generateCode(req.body.id);
    	temp_auth=await authModel.findOne({Token:token})
    }
    var id=req.body.id;
    console.log("token : "+token+' id : '+id);
    const json_obj={
    	'Token':token,
    	'AndroidId':id,
    	'ClientId':'temp'
    }
    const auth = new authModel(json_obj);
    try {
        await auth.save();
        res.send({'code':token});
    } catch (err) {
        console.log(err);
        res.status(500).send({message:"Cannot get the code write now please try again"});
    }
});

app.get('/showAll', async (req, res) => {
  const auths = await authModel.find({});
  try {
    res.send(auths);
  } catch (err) {
    res.status(500).send(err);
  }
});

app.post('/verifyCode', async (req,res) => {
	if((!req.body.id)||(!req.body.code)){
        res.status(400).send({message:"Request should contain field id and code!"});
    }
    var clientId=req.body.id;
    var Code=req.body.code;
    var androidid='tmp';
    console.log("In verifyCode api = "+clientId+" "+Code);

    authModel.findOne({Token:Code}, async function(err,data){
    	if(err){
    		console.log(err);
    		res.status(400).send({message:'No such Code Found'});
    	}
    	console.log(data)
    	androidid=data['AndroidId'];
    	var updatedauth={
    		'Token':data['Token'],
    		'AndroidId':androidid,
    		'ClientId':clientId
    	}
    	await authModel.findByIdAndUpdate(data['_id'], updatedauth);
    	// await authModel.save()
    	console.log(updatedauth);
    	res.status(200).send({message:'Code Verfied Successfully'})
    });
});

app.post('/logout', async (req,res) => {
	if(!req.body.code){
        res.status(400).send({message:"Request should contain field code"});
    }
    var Code=req.body.code;
    console.log('In logout code = '+Code);
    authModel.findOne({Token:Code}, async function(err,data){
    	if(err){
    		console.log(err);
    		res.status(404).send({message:'No such Code Found'});
    	}
    	const dlt_auth=await authModel.findByIdAndDelete(data['id'])
    	if(!dlt_auth)res.status(404).send("No item found")
    	else res.status(200).send({message:'Logout Successfully'});
    })
})
module.exports = app