package js.andres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import js.andres.modelos.AutorizarModel;
import js.andres.repositorios.IAutorizarRepositorio;

@Service
@Primary
public class AutorizarService {
    
    @Autowired
    private IAutorizarRepositorio repositorio;

    public void guardar (AutorizarModel autorizar){
        this.repositorio.save(autorizar);
    }
}
