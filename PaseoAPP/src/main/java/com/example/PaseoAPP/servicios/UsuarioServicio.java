package com.example.PaseoAPP.servicios;

import com.example.PaseoAPP.modelos.Usuario;
import com.example.PaseoAPP.repositorios.IRepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServicio {

    //Inyectando una dependencia al repositorioUsuario
    @Autowired
    private IRepositorioUsuario repositorioUsuario;

    public Usuario guardarUsuarioEnBD(Usuario datos){

        //condiciones logicas para validar datos a guardar

        //1. validar que el correo a registrar no se haya guardado previamente
        if(repositorioUsuario.findByCorreo(datos.getCorreo()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un correo <<<<regisitrado>>>> igual al que me entregas"
            );
        }

        if(datos.getNombres().isEmpty()||datos.getNombres().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre digitado no puede enviarse vacio"
            );
        }

        if(datos.getContraseña().length()<6){
            throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              "La contraseña debe tener al menos 6 caracteres"
            );

        }

        //Si paso la zona de validaciones procedo a preparar la
        // receta(Ejecuto la query que se necesite)
        return this.repositorioUsuario.save(datos);


    }
    public Usuario modificarUsuarioEnBD(Usuario datos, UUID id){
        //validar que datos me envian y si estos cumplen las reglas del negocio
        //Modificar los datos en BD con ayuda del repositorio

        //1.Buscar si el suario existe en BD
        Optional<Usuario>usuario_que_estoy_buscando=this.repositorioUsuario.findById(id);
        if(usuario_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario que quieres editar, no existe en BD"
            );
        }
        Usuario usuario_que_encontre=usuario_que_estoy_buscando.get();

        //2.Validar la informacion nueva que me manda el cliente
        if(datos.getNombres().isEmpty() || datos.getNombres().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Revisa el nombre ingresado"
            );

        }

        //3.Ejecutar el nuevo guardado y retronar
        usuario_que_encontre.setNombres(datos.getNombres());
        return this.repositorioUsuario.save(usuario_que_encontre);



    }
    public boolean eliminarUsuarioEnBD(UUID id){
        //Buscar y validar si el ID que me envian existe
        //Elimino el registro en BD
        Optional<Usuario>usuario_que_estoy_buscando=this.repositorioUsuario.findById(id);
        if(usuario_que_estoy_buscando.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario que quieres eliminar, no existe en BD"
            );
        }
        Usuario usuario_que_encontre=usuario_que_estoy_buscando.get();
        this.repositorioUsuario.deleteById(id);
        return true;
    }
    public List<Usuario> buscarUsuariosEnBD(){
        //**** Dependiendo del parametro de busqueda debo implementar validaciones
        //devuelvo los usuarios o suario que encuentre eb BD
        List<Usuario>usuariosEncontrados=this.repositorioUsuario.findAll();
        return usuariosEncontrados;
    }


}
