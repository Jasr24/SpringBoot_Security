package js.andres.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Esta clase la utilizaremos para modificar el core del framework
@Configuration //Configuramos el framework.. danos informacion al nucleo del framework
@EnableWebSecurity //Con esta clase habilitamos todo el paquete de bateria para trabajar con spring security
@EnableGlobalMethodSecurity(prePostEnabled = true) //antes de un post.. aqui en esta clase realizamos la configuraciones principales
public class Seguridad {

	@Autowired
	private LoginPersonalizado loginPersonalizado;
    
    @Bean //Se deben de crear otros clase-metodos (***UsuarioLogin -> es un componente asi que lo afecta***) para que este funcione correctamente.
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
 		return authenticationConfiguration.getAuthenticationManager();
 	}

    //Para las contraseñas de los uruarios ... has de contraseñas
    @Bean
 	public BCryptPasswordEncoder passwordEncoder() {
 		return new BCryptPasswordEncoder();
 	}
 	
 	@Bean
 	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 		
        http.authorizeHttpRequests()
 		//las vistas que públicas no requieren autenticación
 		.antMatchers
 		(
 			"/",
 			"/liberada/**", //Los artericos indicacan que todo lo que haya en liberada en adelante se incluya
 			"/acceso/registro"
 		).permitAll()

 		// Asignar permisos a URLs por ROLES
 		.antMatchers
 		(
 			"/protegido/**" //Los artericos indicacan que todo lo que haya en protegido en adelante se incluya
 		).hasAnyAuthority("ROLE_ADMIN") //Este indica el o los roles con os cuales puede accesar a estas rutas
 		//.hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
 		
 		//se hacen las configuraciones generales
 		.anyRequest().authenticated()
 		
 		//página de login //Esto permite que todos puedan vngresar a la vista para loguearse
 		//.and().formLogin().permitAll()  //Esto es la vista por defecto de Spring
		//.and().formLogin().loginPage("/acceso/login").permitAll() //"/acceso/login" donde esta la vista.. vista personalizada
		.and().formLogin().successHandler(loginPersonalizado).loginPage("/acceso/login").permitAll()  //Aqwui le pasamos el login personalizado
 		
 		//ruta de logout //Esto permite que todos puedan ingresar a la vista para desloguearse
 		.and().logout().permitAll();  //cerrando cession
 		
 		return http.build();
 	}

    //Con este metodo le decimos al framework que queremos que los archivos estaicos.. los que ingresemos...esten liberados para poder ser visualizados
 	@Bean
 	public WebSecurityCustomizer webSecurityCustomizer(){ //webSecurityCustomizer conversa con la carpeta estatica que esta en el proyecto
 		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/css/**");
 	}
 	
 	

}
