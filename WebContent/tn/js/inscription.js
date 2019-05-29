/*
 * creation(document)
 *
 * Elle fait appel à verifChamps(), si tout est ok elle fait appel 
 * à connecte() pour connecter l'utilisation. 
 */
function creation(obj) {
	document = obj.document;
	var prenom = document.getElementsByName("prenom")[0].value;
	var nom = document.getElementsByName("nom")[0].value;
	var login = document.getElementsByName("login")[0].value;
	var email = document.getElementsByName("email")[0].value;
	var mdp = document.getElementsByName("pass")[0].value;
	var mdpConf = document.getElementsByName("re_pass")[0].value;
	
	var ok = verifChamps(mdp, mdpConf, prenom, nom, email, login);
	if (ok) {
		createUser(nom, prenom, login, email, mdp);
	}

}

function verifChamps(mdp, mdpConf, prenom, nom, email, login) { 
	console.log("je suis rentré");
	if ((prenom.length == 0) || (prenom == undefined)) {
		$('#lastName').show();
		return false;
	}
	$('#lastName').hide();

	if ((nom.length == 0) || (nom == undefined)) {
		$('#firstName').show();
		return false;
	}
	$('#firstName').hide();

	/** ********* Login verification ********************* */
	if ((login == undefined) || (login.length == 0)) {
		$('#usernameSignUp').show();
		return false;
	}
	$('#usernameSignUp').hide();

	if (login.length < 6 || login.length > 20) {
		$('#login_tailleSignUp').show();
		return false;
	}
	$('#login_tailleSignUp').hide();

	if ((email == undefined) || (email.length == 0)) {
		$('#email').show();
		return false;
	}
	$('#email').hide();

	if ((mdp == undefined) || (mdp.length == 0)) {
		$('#password1').show();
		return false;
	}
	$('#password1').hide();

	if (mdp.length < 6 || mdp.length > 15) {
		$('#mdp_tailleSignUp').show();
		return false;
	}
	$('#mdp_tailleSignUp').hide();

	if ((mdpConf == undefined) || (mdpConf.length == 0)) {
		$('#password2').show();
		return false;
	}
	$('#password2').hide();

	if (mdp != mdpConf) {
		$('#passwordVerif').show();
		return false;
	}
	$('#passwordVerif').hide();

	return true;

}

function createUser(nom, prenom, login, email, mdp) {
	$('#serveur_erreur').hide();
	$.ajax({
		url : "http://li328.lip6.fr:8280/1.2/NUS",
		type : "get",
		data : "login=" + login + "&nom=" + nom + "&prenom=" + prenom + "&mdps=" +mdp+ "&email=" + email,
		dataType : "json",
		success : function(rep) {
			traiteReponseCreation(rep, login, mdp);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('#serveur_erreur').show();
		}
	});
}

function traiteReponseCreation(reponse, login, mdp) {
	$('#login_existant').hide();
	if(reponse.code == 100) {
		$('#login_existant').show();
	} else {
		connecte(login, mdp);
		//login.conne
	}
}

/*
function refrecheMessages(){
	if(!noConnection){
		$.ajax({
			url : "http://li328.lip6.fr:8280/titi/souscrire",
			type : "get",
			data : "key=" + env.key + "&query=" + env.query + "&from=" + env.fromId + "&id_min=" +env.maxId+ "&id_max=-1 &nb=-1",
			dataType : "text/plain",
			success : refrecheMessageResponse,
			
		});
	}else{
		refrecheMessageReqsponse(JSON.stringify(getfromlocalDB(env.fromID,env.max.id,-1,-1)))
	}
}

function refrecheMessageResponse(rep){
	var tab = JSON.parser(rep,revival);
	if(tab.erreur != undefind){
		alert(erreur);
	}else{
		for(var i=tab.lenght;i>=0;i--){
			$("Messages").prepend(tab[i].getMessage());
			env.msg[tab[i].id]=tab[i];
			if(tab[i].id > env.maxId){
				env.maxId=tab[i].id;
			}
		}
	}
	
}

function newMessage(){
	var text=$("text_new_message").val();
	if(!noConnextion){
		//n'est pas exactement comme ça mais l'idee est la 
		$.ajax({
			url : "http://li328.lip6.fr:8280/titi/souscrire",
			type : "get",
			data : "key=" + env.key + "&query=" + env.query + "&from=" + env.fromId + "&id_min=" +env.maxId+ "&id_max=-1 &nb=-1",
			dataType : "Json",
			success : refrecheMessageResponse,
			
		});
	}else{
		var m =newMessage(localdb.length,{"id ": env.id, "login" : env.login},texte,new Date());
		localdb[min_id]=m;
		//new 
	}
}
*/