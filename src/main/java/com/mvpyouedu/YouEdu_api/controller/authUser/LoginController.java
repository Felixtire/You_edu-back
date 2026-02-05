package com.mvpyouedu.YouEdu_api.controller.authUser;

import com.mvpyouedu.YouEdu_api.domain.dto.DadoParaAlterarSenha;
import com.mvpyouedu.YouEdu_api.domain.dto.DadoParaRecuperarSenha;
import com.mvpyouedu.YouEdu_api.domain.dto.DadosLogin;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioRepository;
import com.mvpyouedu.YouEdu_api.infra.security.TokenService;
import com.mvpyouedu.YouEdu_api.infra.security.dto.TokenAuthenticationJwt;
import com.mvpyouedu.YouEdu_api.service.UsuarioLogadoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
  private UsuarioLogadoService service;


    @PostMapping
    public ResponseEntity logar(@RequestBody @Valid DadosLogin dados) {

       var userLogado= service.logar(dados);

        return ResponseEntity.ok((userLogado));
    }
    @PostMapping("/recuperar-senha")
    public ResponseEntity recuperarSenha(@RequestBody DadoParaRecuperarSenha dados){

        var token = service.recuperarSenha(dados.email());

        return ResponseEntity.ok().body(token);

    }

    @PutMapping("/redefinir-senha/{id}")
    @Transactional
    public ResponseEntity redefinirSenha( @PathVariable Long id, @RequestBody @Valid DadoParaAlterarSenha dado){
        service.alterarSenha(id,dado);
        return ResponseEntity.ok().body("Senha alterada com sucesso");
    }

}
