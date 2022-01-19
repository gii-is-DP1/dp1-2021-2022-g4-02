<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>


<nav class="navbar navbar-default" role="navigation">
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">
			
				    <petclinic:menuItem active="${name eq 'home'}" url="/"
						title="home page">
						   <img src="/resources/images/favicon.png" width="18" height="18" class="d-inline-block align-top" alt="">
						   <span style='margin-left:0.5em; display:inline-block;'>&nbsp;7Isles</span>
					</petclinic:menuItem>
				
					<sec:authorize access="hasAuthority('admin')">
						<petclinic:menuItem active="${name eq 'users'}" url="/admin/page/users?page=1"
								title="Ver usuarios">
								<span class="glyphicon" aria-hidden="true"></span>
								<span>Usuarios</span>
						</petclinic:menuItem>
							
						<petclinic:menuItem active="${name eq 'gamesList'}" url="/games/unfinishedGames"
								title="Ver partidas en curso">
								<span class="glyphicon" aria-hidden="true"></span>
								<span>Partidas en curso</span>
						</petclinic:menuItem> 
						
						<petclinic:menuItem active="${name eq 'gamesList'}" url="/games/finishedGames"
								title="Ver partidas terminadas">
								<span class="glyphicon" aria-hidden="true"></span>
								<span>Partidas finalizadas</span>
						</petclinic:menuItem>  
					</sec:authorize>
				
					<sec:authorize access="!hasAuthority('admin')">
			
						<petclinic:menuItem active="${name eq 'rules'}" url="/rules"
							title="Ver reglas del juego">
							<span class="glyphicon" aria-hidden="true"></span>
							<span>Reglas</span>
						</petclinic:menuItem>
					
						<petclinic:menuItem active="${name eq 'availableGames'}" url="/games/availableGames"
							title="Ver partidas disponibles">
							<span class="glyphicon" aria-hidden="true"></span>
							<span>Partidas Disponibles</span>
						</petclinic:menuItem>
						
						<petclinic:menuItem active="${name eq 'startedGame'}" url="/games/startedGame"
							title="Volver al tablero de la partida empezada">
							<span class="glyphicon" aria-hidden="true"></span>
							<span>Partida Empezada</span>
						</petclinic:menuItem>
						
						<petclinic:menuItem active="${name eq 'history'}" url="/games/playerHistory"
							title="Ver mi historial">
							<span class="glyphicon" aria-hidden="true"></span>
							<span>Historial</span>
						</petclinic:menuItem>
						
						<petclinic:menuItem active="${name eq 'ranking'}" url="/statistics/ranking"
							title="Ver ranking del juego">
							<span class="glyphicon" aria-hidden="true"></span>
							<span>Ranking</span>
						</petclinic:menuItem>

	          </sec:authorize>
			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar sesión</a></li>
					<li><a href="<c:url value="/users/new" />">Registrarse</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											
										</div>
					
										<div class="col-lg-12">
											<p class="text-center">
												<strong><sec:authentication property="name" /></strong>
											</p>
											
											<p class="text-left">
												<a href="<c:url value="/profile" />"
													class="btn btn-primary btn-block btn-sm">Ver perfil</a>
											</p>
										</div>
										
										
										<div class="col-lg-12">
											
											<p class="text-left">
												<a href="<c:url value="/profile/edit" />"
													class="btn btn-primary btn-block btn-sm">Editar perfil</a>
											</p>
										</div>
									
									</div>
									<div class="row">
										<div class="col-lg-12">
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Cerrar sesión</a>
											</p>
										</div>
									</div>
									
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
