/*
 * creation(document)
 *
 * Elle fait appel à verifChamps(), si tout est ok elle fait appel 
 * à connecte() pour connecter l'utilisateur. 
 *
 */


function logout() {

	cle = env.key;

	$.ajax({
		url : "http://li328.lip6.fr:8280/1.2/LOS",
		type : "get",
		data : "key=" + cle,
		dataType : "json",
		success : function (rep){
			traiteReponseDeconnexion(rep);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			func_erreur(-1, jqXHR.responseText, errorThrown);
		}
	});
}

function traiteReponseDeconnexion(rep) {
	env.actif = false;
	env = {};
	env.users = [];
	window.location.href = "index.html";
}