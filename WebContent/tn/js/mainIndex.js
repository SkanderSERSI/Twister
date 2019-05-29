/****************** Main Function **********************/
function main(id, login, key,follows) {

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
		if(follows.length!=0){
			env.follows=follows.split(",");
		}else{
			env.follows=follows;
		}
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


function User(id, login, contact) {

	this.login = login;
	this.id = id;
	if (contact == undefined)
		this.contact = false;
	this.contact = contact;
	env.users[id] = this;
}



function chargerlistesFriends(key) {

	$.ajax({
		url : "http://li328.lip6.fr:8280/1.2/LFS",
		type : "get",
		data : "key=" + env.key,
		dataType : "json",
		success :  function(rep){
			traiteReponseListesFriends(rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		}
	});
}

	
function traiteReponseListesFriends(rep){
	console.log("je rentre");
	if(rep.listes_friends == ""){
		var a = "<p><a href class ='stats'>#SansAmis</a></p>";
		$('#zone_stats').append(a);
	}else{	
		console.log("je rentre dans else");
		var size = rep.listes_friends.length;
		var s = "<form>";
		s+="<select name='SelectMenu' onChange='AllerA(this.form)'>";
		s+="<option> la liste de vos friends";
		for(var i=0;i<size;i++){
			var am = rep.listes_friends[i] ;
			//s += "<a href onclick = 'javascript : voir_amis(";
			s += "<option>"+JSON.stringify(am) ;
			//s += ");return false;' class ='stats'>@" ;
			//s += "</a>" ;
		}
		s+='</select>';
		s+='</form>';
		$('#zone_stats').append(s);
	}
}

var CibleURL = new CreeTableau("",
		"enregistrement.html",
		"home.html",
		"login.html",
		"web.html"
);

function CreeTableau() {
	this.length = CreeTableau.arguments.length
	for (var i = 0; i < this.length; i++)
	this[i+1] = CreeTableau.arguments[i]
	}

function AllerA(form) {
	i = form.SelectMenu.selectedIndex;
	if (i == 0) return;
	parent.location.href = CibleURL[i+1];
}


function voir_amis(login){
	$.ajax({
		url : "http://li328.lip6.fr:8280/1.2/LFS",
		type : "get",
		data : "login=" + login,
		dataType : "json",
		success :  function(rep){
			traiteReponseListesFriends(rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		}
	});
}
