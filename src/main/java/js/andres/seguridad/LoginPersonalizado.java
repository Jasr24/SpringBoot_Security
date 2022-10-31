package js.andres.seguridad;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

//CON ESTE COMPONENTE INTERVENIMOS EL PROCEDIMIENTO DEL LOGIN.... AQUI PODEMOS HACER CUALQUIER CONFIGURACION ADICIONAL AL LOGIN

@Component
public class LoginPersonalizado extends SimpleUrlAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authentication) throws IOException, ServletException {

        //response.sendRedirect("/"); aqui podemos hacer michas cosas
        //Por ejemplo podemos guardar un log
        //Que cuando este logueado en paginas diferentes, le cierre la cession en todos lados, se puede hacer a travex de la implementacion de un procemiento de socket
        // Otra cosa que se puede hacer, es verificar cuaando el usuario se logue si el pago el servicio para poder accesar a los contenidos restringidos o no. (un filtro generico para no agregarlo en todos los controladores)
        //y mucho mas..

        super.onAuthenticationSuccess(request, response, chain, authentication);
    } //SimpleUrlAuthenticationSuccessHandler permite agregar un filtro en el minuto en el que hace la petición del inicio de sesión, permite intervenir el procedimiento del post.

    /*HACER EL METODO ANTERIOR NO ES SUFICIENTE SE NESESITA HACER ALGO MAS PARA DECIRLE A SPRING SECURITY QUE UTILICE EL LOGIN PERSONALIZADO.
    Lo inyectamos en la clase Seguridad y lo usamos*/

    
    
}
