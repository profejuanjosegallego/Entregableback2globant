package com.example.PaseoAPP.controladores;

import com.example.PaseoAPP.modelos.Usuario;
import com.example.PaseoAPP.servicios.UsuarioServicio;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/paseoapi/v1/usuarios")
public class UsuarioControlador {

    //Por cada serivicio ofrecido
    //configuro 1 funcion controladora

    @Autowired
    UsuarioServicio servicio;

    //funcion para controlar el guardado
    @PostMapping
    public ResponseEntity<Usuario>controlarGuardado(@RequestBody Usuario datos){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.servicio.guardarUsuarioEnBD(datos));
    }
    //funcion para controlar las modificaciones
    @PutMapping("/{id}")
    public ResponseEntity<Usuario>controlarModificado(@RequestBody Usuario datos, @PathVariable UUID id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.servicio.modificarUsuarioEnBD(datos,id));
    }

    //funcion para controlar el borrado
    @DeleteMapping("/{id}")
    public ResponseEntity<?>controlarBorrado(@PathVariable UUID id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.servicio.eliminarUsuarioEnBD(id));
    }


    //funcion para controlar el listar
    @GetMapping
    public ResponseEntity<?>controlarListado(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.servicio.buscarUsuariosEnBD());
    }

}
