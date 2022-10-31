package js.andres.seguridad;

import java.util.Collection;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PermisosService {

    //SIGUIENTE METODO GENERICO PARA USAR ENCUALQUIER CONTROLADOR
    //Metodo de la comprobacin del rol
    public boolean comprobarRol(String nombreRol){
        
        SecurityContext context = SecurityContextHolder.getContext();

        if(context == null){
            return false;
        }

        Authentication auth = context.getAuthentication();

        if(auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(nombreRol));
    }

}
