package js.andres.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import js.andres.modelos.AutorizarModel;
import js.andres.modelos.UsuarioModel;
import js.andres.service.AutorizarService;
import js.andres.service.UsuarioService;

@Controller
@RequestMapping("/acceso")
public class AccesoController {
    

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutorizarService autorizarService;

    @Autowired
    private BCryptPasswordEncoder  bCryptPasswordEncoder;//Esto lo tenemos como un BEan metodo en la clase Seguridad.java

    @GetMapping("/login")
    public String login (@RequestParam(value = "error", required = false) String error, Principal principal,/*Principal pemite un tipo de informacion de si el usuario esta logueado*/
            @RequestParam(value = "logout", required = false) String logout, RedirectAttributes flash){
            
        if(principal != null){
            flash.addFlashAttribute("clase", "success");
            flash.addFlashAttribute("mensaje", "Ya ha iniciado sesión anteriormente");
            return "redirect:/";
        }

        if(error != null){
            flash.addFlashAttribute("clase", "danger");
            flash.addFlashAttribute("mensaje", "Los datos ingresados no son correctos, vuelva a intentarlo");
            return "redirect:/acceso/login";
        }
        if(logout != null){
            flash.addFlashAttribute("clase", "success");
            flash.addFlashAttribute("mensaje", "Ha cerrado sesión exitosamente");
            return "redirect:/acceso/login";
        }
        return "acceso/login";
    }

    @GetMapping("/registro")
    public String registro(Model model){
        model.addAttribute("usuario", new UsuarioModel());
        return "acceso/registro";
    }

    @PostMapping("/registro") //Recibe la url registro
    public String registro_post(@Valid UsuarioModel usuario, BindingResult result, RedirectAttributes flash, Model model){

        if(result.hasErrors()){
            Map<String,String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            
            model.addAttribute("errores", errores);
            model.addAttribute("usuario", usuario);
            return "acceso/registro";
        }

        //Creamos el usuario
        UsuarioModel guardar = this.usuarioService.guardar(new UsuarioModel(usuario.getNombre(), usuario.getCorreo(), usuario.getTelefono(), this.bCryptPasswordEncoder.encode(usuario.getPassword()), 1, null));
             //El 1 lo podiamos enviar con un estado 0  aun correo electronico para que cuando confirme se cambie a estado 1,,,(Abriendo la url del correo electronico,, la que llega) asi es para confirma el correo y el registro

        //Creamos algun Rol
        this.autorizarService.guardar(new AutorizarModel("ROLE_USER", guardar));

        //Redireccionaoms
        flash.addFlashAttribute("clase", "success");
        flash.addFlashAttribute("mensaje", "Se creo el usuario correctamente");
        return "redirect:/acceso/login";
    }

}
