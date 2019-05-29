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
		var rep=connecte(login, mdp);
		console.log(typeof rep["login"]);
		
		main(rep["id"],rep["login"],rep["key"],rep["follows"]);
		console.log(rep);
		showProfil(obj,rep["id"]);
	}
}

function showProfil(obj,id){
	login =getLoginByID(id);
	console.log(id);
	console.log(login);
	document=obj.document;
	
	
	$('#tout').replaceWith('<div id="tout"><div id="profil">'+
			
                
                  	
           
                    +' <p> Salut <span id="connected_name"></span></p>'
                    
                    +'<div id="add_msg">'
                    +'	<form method="post" action="javascript:add_message(this);">'
                    +'		<p>Tapez votre message</p>  <TEXTAREA name="text" rows=4 cols=40>Valeur par défaut</TEXTAREA></br>'
                    +'		<input type="submit" value="Envoyer">'
                    +'	</form>'
                    +'</div>'
                    +'<div id="msg">'
                    +'	<script type="text/javascript">'
					+'		showMessages(this,'+id+');</script>'
					+'</div>'
                    +'<div id="amis">	<script type="text/javascript">'
					+'		lister_friends(this,'+id+');</script> </div>'
					+'</div>'+'</div>');
	
                    
	           
                
	
	afficheBarreSearch(obj);
	
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
function afficheBarreSearch(obj){
	console.log("affiche barre search entrée");
	document =obj.document;
	$('#form1').replaceWith('<div class ="form">'+
			'<form method="post" action="javascript:search(this);">'
			'<input type="search" name="recherche" '+
				'placeholder="Tapez l element de votre recherche"> <input type="submit" value="Rechercher">'+
		'</form></div>');
	console.log("affiche barre search sortie");
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
	var reps={};
	$('#serveur_erreur').hide();
	$.ajax({
		url : "http://li328.lip6.fr:8280/14/LS",
		type : "get",
		data : "login=" + login + "&mdps=" + password,
		dataType : "json",
		success : function(rep) {
			console.log(rep); 
			traiteReponseConnexion(rep);
			reps=jQuery.extend(true,{}, rep);
			console.log(reps); 
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('#serveur_erreur').show();
			
		},async: false
	});
	return reps;
}

function traiteReponseConnexion(reponse) {
	$('#deja_connecter').hide();
	$('#erreur_login').hide();
	$('#erreur_mdp').hide();
	
	
		
		
	if(reponse.code == 1) {
		$('#erreur_mdp').show();
	}
	else if(reponse.code == 2) {
		$('#erreur_login').show();
	}
}

