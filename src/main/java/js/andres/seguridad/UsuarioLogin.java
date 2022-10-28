package js.andres.seguridad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import js.andres.modelos.AutorizarModel;
import js.andres.modelos.UsuarioModel;
import js.andres.service.UsuarioService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Component
public class UsuarioLogin implements UserDetailsService{

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        
        UsuarioModel usuario = this.usuarioService.buscarPorCorreo(correo, 1);
        if(usuario == null ){
            throw new UsernameNotFoundException("El E-Mail:" + correo + " no existe en el sistema!");
        }

        //Configuramos los autorites
        List<GrantedAuthority> autorities =  new ArrayList<>();  //para averiguar los roles del usuario
        for (AutorizarModel role: usuario.getAutorizar()) {
            autorities.add(new SimpleGrantedAuthority(role.getNombre())); //Aqui le pasamos los roles que tenga el usuario
        }
        if(autorities.isEmpty()){
            throw new UsernameNotFoundException("Error en el login: E-Mail '" + correo + "' no tiene roles asignados");
        }

        //Retornamos el usuarioDetail = datos del usuarios logueado
        return new User(usuario.getNombre(), usuario.getPassword(), true , true , true , true, autorities);
    }
    
}
