/*
 * connexion(document)
 *
 * Elle fait appel à verifForm(), si tout est ok elle fait appel 
 * à connecte() pour connecter l'utilisation. 
 *
 */
function connexion(obj) {
	document = obj.document;
	var login = document.getElementsByName("loginc")[0].value;
	var mdp = document.getElementsByName("passc")[0].value;
	
	var ok = verifForm(login, mdp);
	if (ok) {
		connecte(login, mdp);
	}
}

/*
 * verifForm(login, mot_de_passe)
 *
 * Cette fonction verifies si le login et le mot de passe de 
 * sont bien saisis. 
 *
 */

function afficheL(){
	$('#enregistrement').hide();
	$('#session').show();

}

function afficheE(){
	$('#session').hide();
	$('#enregistrement').show();
}
function verifForm(login, mdp) {

	/*********** Login verification **********************/
	if ((login == undefined) || (login.length == 0)) {
		$('#login_obligatoire').show();
		return false;
	}
	$('#login_obligatoire').hide();

	/************* Mot de passe verification **************/
	if ((mdp == undefined) || (mdp.length == 0)) {
		$('#mdp_obligatoire').show();
		return false;
	}
	$('#mdp_obligatoire').hide();

	return true;

}

function connecte(login, password) {
	$('#serveur_erreur').hide();
	$.ajax({
		url : "http://li328.lip6.fr:8280/1.2/LS",
		type : "get",
		data : "login=" + login + "&mdps=" + password,
		dataType : "json",
		success : function(rep) {
			console.log(rep); 
			traiteReponseConnexion(rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('#serveur_erreur').show();
		}
	});
}

function traiteReponseConnexion(reponse) {
	$('#deja_connecter').hide();
	$('#erreur_login').hide();
	$('#erreur_mdp').hide();
	if(reponse.code == 200){
		$('#deja_connecter').show();
	}else if(reponse.code == 1) {
		$('#erreur_mdp').show();
	}
	else if(reponse.code == 2) {
		$('#erreur_login').show();
	}else {
		window.location.href = "main.jsp?id=" + reponse.id + "&login=" + reponse.login + "&key=" + reponse.key + 
		"&follows="+reponse.follows;
	}
}

