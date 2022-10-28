package js.andres.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import js.andres.modelos.AutorizarModel;

public interface IAutorizarRepositorio extends JpaRepository<AutorizarModel,Integer>{
    
}
