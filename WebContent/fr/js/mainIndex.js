/****************** Main Function **********************/
function main(id, login, key,follows) {
	alert("Hello! I am an alert box!");
	env = new Object();

    env.users = new Array();
    /*
    env.commentaires = new Array();
    env.comment_cpt = 0;
    env.erreur_cpt = 0;
    env.recherche = new Object();
*/
    
	if(id != undefined && login != undefined && key != undefined && follows != undefined) {	
		env.actif=true;
		env.key=key;
		env.login=login;
		env.id=id;
		
		
		console.log(login);
		console.log(follows);
		env.follows=follows;
		console.log(env.follows);
		new User(id, login, true);
		$('#connected_name').text(login);
		$('#number_amis').text(env.follows.length);
		$('#button_logout').show();
		$('#log_enr').hide();
		$('#session').hide();
		//chargerlistesFriendrs(key);
		//chargerCommentaire(key, id, login);
	} else {
		console.log("Erreur : main (probleme avec les parametres)");
		window.location.href = "index.html";
	}

}


function aff(follows){
	for( var i in follows){
		console.log(follows[i]);
	}
}
function toArray(follows){
	var tab=[];
	for(var i in follows){
		tab.push([i,follows[i]]);
	}
	return tab;
}

function User(id, login, contact) {

	this.login = login;
	this.id = id;
	if (contact == undefined)
		this.contact = false;
	this.contact = contact;
	env.users[id] = this;
}

function lister_friends(obj,id){
	document = obj.document;
	var login= getLoginByID(id);
	console.log(id);
	console.log(login);
	var rep= chargerlistesFriends(login);
	console.log(rep["follows"]);
	var ch='<div id="amis">';
	
	var ar=rep["follows"];
	console.log(rep);
	for(var i=0; i<rep["follows"].length; i++){
		
		var am= ar[i];
		console.log(am);
		ch+='<div id="ami_'+i+'">';
		
		ch+='<a href="javascript:showProfil(this,'+am+');">';
		ch+=JSON.stringify(am);
		ch+='</a>';
		
		ch+='<a href="javascript:removeFriend('+am+');">';
		ch+='Desabonner </a>';
		ch+='</div>';
		
	}
	
	ch+='</div>';
	console.log(ch);
	$('#amis').replaceWith(ch);
	
}

/** pas encore finis**/
function lister_messages(obj,id){
	document = obj.document;
	showMessages(obj,id);		
}

function showMessages(obj,id){
	console.log(getKeyByID(id));
	console.log(id);
	var rep= chargerMessages(getKeyByID(id),id,0,10);
	document = obj.document;
	var ch='<div id="msg">';
	console.log(rep);
	var ar= rep[""+id];
	console.log(ar);
	for(var i=0; i<ar.length; i++){
		ch+='<p>';	
		var am= ar[i];
		console.log(am["message"]);
		ch+='<div id="msg_'+am["id_message"]+'">';
		ch+=am["message"];
		ch+='<div id="em">';
		ch+='	</BR><a href="javascript:editMessage(this,'+am["id_message"]+');"><img src="edit.png" height="30" width="30"/></a>';
		ch+=' 	<a href="javascript:removeMessage(this,'+am["id_message"]+');"><img src="remove.png" height="30" width="30"/></a>'
		
		
		ch+=' <a href="javascript:commenter(this,'+am["id_message"]+');"><img src="comment.png" height="30" width="30"/></a>'
		ch+='</div></BR>';
		ch+='<div id="comments'+am["id_message"]+'">';
		ch+='	<script type="text/javascript">showCommentaire(this,'+am["id_message"]+')</script>';
		ch+='</div>';
			
		ch+='</div>';
		ch+='</p>';
	}
	ch+='</div>';
	
	console.log(ch);
	$('#msg').replaceWith(ch);
	
}



function commenter(obj,id){
	console.log("appel a commenter");
	console.log(id);
	var ch=prompt("Commenter ce message","Votre commentaire");
	console.log("on va commenter "+ch);
	commenterServ(ch,id);
	showCommentaire(obj,id);
}



function commenterServ(ch,id){
	reps={};
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/ACS",
		type : "get",
		data : "ch=" + ch+"&id="+id+"&key="+env.key,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(reps);
		},async:false
	});	
	return reps;
}

function showCommentaire(obj,id){
	console.log("appel a showCommentaire ");
	console.log(id);
	document =obj.document;
	var rep=getComsByid(id);
	
	var ar=rep["commentaire"];
	console.log(rep);
	console.log(rep["commentaire"]);//ARRAY
	var ch='<div id="comments'+id+'">';
	for(var i=0; i<rep["commentaire"].length; i++){
		
		var am= ar[i];
		console.log(am);//OBJECT
		
		ch+='<div id="com_'+i+'">';
		//Probleme d'appel d'une fonction en javascript avec innerhtml ? 
		ch+='<p>';
		ch+=am;
		ch+='</p>';
		ch+='</div>';
		
	}
	
	ch+='</div>';
	console.log(ch);
	console.log("comments"+id);
	$('#comments'+id).replaceWith(ch);
	
}

function getComsByid(id){
	var reps={};
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/GC",
		type : "get",
		data : "id="+id,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(reps);
		},async:false
	});
	return reps;

}
function editMessage(obj,id){
	var ch=prompt("Modifiez le message","message");
	editMessageServ(ch,id);
	showMessages(obj,id);
}

function editMessageServ(ch,id){
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/edit",
		type : "get",
		data : "ch=" + ch+"&id="+id,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(reps);
		},async:false
	});
	
}

function removeMessage(obj,id){
	removeMessageServ(id);
	showMessages(obj,env.id);
}

function removeMessageServ(id){
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/remove",
		type : "get",
		data : "id="+id,
		dataType : "json",
		success :  function(rep){
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		},async:false
	});
	
	
}

function chargerMessages(key,id,id_min,id_max){
	var reps={};
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/list",
		type : "get",
		data : "key=" + key+"&from="+id+"&id_max="+id_max+"&id_min="+id_min+"&nb="+6,
		dataType : "json",
		success :  function(rep){
		
			reps=jQuery.extend(true,{}, rep);
			console.log(reps);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(reps);
		},async: false
	});
	return reps;	
}

function chargerlistesFriends(login) {
	var reps=[];
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/LFS",
		type : "get",
		data : "login=" + login,
		dataType : "json",
		success :  function(rep){
		
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("saint-michel");
		},async: false
	});
	return reps;
}

/** encore pas fait**/

	




function add_message(obj){
	document = obj.document;
	var ch=document.getElementsByName("text")[0].value;
	console.log(ch);
	addmsg(ch);
	showProfil(obj,env.id);
}


function addmsg(ch){
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/AMS",
		type : "get",
		data : "key=" + env.key+"&ch="+ch,
		dataType : "json",
		success :  function(rep){
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		},async:false
	});
}



function search(obj){
	document=obj.document;
	var ch=document.getElementsByName("recherche")[0].value;
	var rep=seekFriend(ch);
	console.log(rep)
	console.log(rep["friends"]);
	showFriendsResult(obj,rep);
	
	
}

function seekFriend(ch){
	var reps={};
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/SFS",
		type : "get",
		data : "nom=" + ch,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		},async: false
	});	
	return reps;
	
}

function showFriendsResult(obj,rep){
	console.log(rep);
	document = obj.document;
	var ch='<div id="tout">';
	
	var ar=rep["friends"];
	console.log(rep);
	for(var i=0; i<rep["friends"].length; i++){
		var am= ar[i];
		console.log(am);
		ch+='	<div id="ami_'+i+'>"';
		//Probleme d'appel d'une fonction en javascript avec innerhtml ? 
		ch+='		<a href="javascript:showProfil(this,'+am["id"]+');">';
		ch+=JSON.stringify(am["nom"]);
		ch+=" ";
		ch+=JSON.stringify(am["prenom"]);
		ch+='		</a>';
		ch+='		<a href="javascript:addFriend('+am["id"]+');">';
		ch+='abonner </a>';
		ch+='		<a href="javascript:removeFriend('+am["id"]+');">';
		ch+='Desabonner </a>';
		ch+='	</div>';
		
	}
	
	ch+='</div>';
	console.log(ch);
	$('#tout').replaceWith(ch);
}

function getLoginByID(id){
	var reps=0;
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/gL",
		type : "get",
		data : "id=" + id,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		},async: false
	});	
	return reps["login"];	
}
function getKeyByID(id){
	var reps="";
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/gK",
		type : "get",
		data : "id=" + id,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		},async: false
	});	
	return reps["key"];
	
}
function getIdBylogin(login){
	var reps=0;
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/gI",
		type : "get",
		data : "login=" + login,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		},async: false
	});	
	return reps["id"];	
}

function addFriend(id){
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/AFS",
		type : "get",
		data : "key=" + env.key+"&to="+id,
		dataType : "json",
		success :  function(rep){
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		}
	});	
	
}

function removeFriend(id){
	
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/RFS",
		type : "get",
		data : "key=" +env.key+"&to="+id,
		dataType : "json",
		success :  function(rep){
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		}
	});	
	
}

function AllFriendsMessages(obj,id){
	document=obj.document;
	var rep= friendsMessages(id);
	
	
	
	
}

function friendsMessages(id){
	var reps={};
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/LFM",
		type : "get",
		data : "id=" + id,
		dataType : "json",
		success :  function(rep){
			reps=jQuery.extend(true,{}, rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		},async: false
	});	
	return reps;
	
}