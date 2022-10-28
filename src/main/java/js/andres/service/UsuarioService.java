package js.andres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import js.andres.modelos.UsuarioModel;
import js.andres.repositorios.IUsuarioRepositorio;

@Service
@Primary
public class UsuarioService {
    
    @Autowired
    private IUsuarioRepositorio repositorio;

    public UsuarioModel guardar(UsuarioModel entity){
        return this.repositorio.save(entity);  //El metodo save retorna una entidad.. la que se le pase por argumento
    }

    public UsuarioModel buscarPorCorreo (String correo, Integer estado){
        return this.repositorio.findByCorreoAndEstado(correo, estado);
    }
}
