package js.andres.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import js.andres.modelos.UsuarioModel;

public interface IUsuarioRepositorio extends JpaRepository<UsuarioModel,Integer>{

    // select * from where correo = 'correo@gmail.com' and estado = 1;
    public UsuarioModel findByCorreoAndEstado(String correo, Integer estado);
    
    // select * from where correo = 'correo@gmail.com' o estado = 1;
    //public UsuarioModel findByCorreoOrEstado(String correo, Integer estado);

}
