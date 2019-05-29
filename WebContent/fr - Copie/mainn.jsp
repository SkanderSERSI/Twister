<%@page import="java.util.List"%>
<html>

<head>
		<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-language" content="fr" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="robots" content="index,follow">
        <meta name="viewport" content="width=device-width , initial-scale=1.0">
        <title>Zineb</title>

        <link rel="stylesheet" href="css/enregistrement.css" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/smoothness/jquery-ui.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>    
        <script src="js/inscription.js"></script>
        <script src="js/login.js"></script>
        <script src="js/mainIndex.js"></script>
        <script src="js/deconnexion.js"></script>
</head>

<body class="body">	

<script type="text/javascript">
        $(document).ready(function() {
        	function go() {
       <%
            		String id = request.getParameter("id");
                    String login = request.getParameter("login");
            		String key = request.getParameter("key");
            		String follows = request.getParameter("follows");
            		if((id != null) && (login != null) && (key != null) && (follows != null)){
            			out.println("main('" + id + "','" + login + "','" + key + "','" + follows + "');");
            		} else {
            			out.println("main();");
                    }
        %>            
            }
            $(go());
        });		
    
	</script>
        <header class="mainheader">
                <nav>
                    <div id="title">
                        <a href="#"  onclick="javascript:home(); return false;"></a>
                    </div>
                    <div id="search_box">
                        <form class="form-wrapper"  method="get" action="javascript:rechercheCommentaire(this);">
	                       <input type="text" placeholder="Search ..." name="search" >
	                       <button type="submit">Go</button>
                        </form>
                    </div>
                    <div id="small_menu">
                        <input type="checkbox" name="search_friend" value="1" id="checkbox"><span style="{font-size: 0.8em}"> Friends only</span>
                    </div>       
                    <div id="button_logout">
                        Hi, <span id="connected_name"></span>
                        <a href="#" onclick="logout(); return false;">Disconnect</a>
                    </div>  
                </nav>
        </header>    
     
        <div id="main_content1">
                <div id="menu">
                  	<div id="menu1" class="menu1">
                        <a href="javascript:profile();"></a>
                        <a href="javascript:profile();">Profile</a>
                    </div>
                    <div id="menu2" class="menu2">
                        <a href="javascript:talkies();"></a>
                        <a href="javascript:talkies();">My Talkie's</a>
                    </div>
                    <div id="menu3" class="menu3">
                   	    <a href="javascript:chargerlistesFriends(this);"></a>
                        <a href="javascript:chargerlistesFriends(this);">Number of friends : </a><span id="number_amis"></span>
                        <span id="zone_stats"></span>
                    </div>
                </div>
                <div id="messages_contenu1">
                    <div id="message_box">
                        <form class="message_form" method="get" action="javascript:envoiMessage(this);">
                            <textarea class="box" name='message' id='message'></textarea>
                            <input type="submit" value="message" id="message_button" />
                        </form>
                        <hr>
                    </div>
                    <h3 id="msgTilte">La liste de mes messages </h3>
                    <div id="insert_messages">
						
                    </div>
                </div>
         </div>
               <div id="footer">
                	<a href="#">About Us</a> | 
                	<a href="#">Help</a> | 
               	 2017 Copyright &copy; <a href="#" title="issathink">All rights reserved, Zineb Site.</a>
          	  </div>
</body>
</html>
