package com.mvpyouedu.YouEdu_api.service;

import com.mvpyouedu.YouEdu_api.domain.dto.DadoParaAlterarSenha;
import com.mvpyouedu.YouEdu_api.domain.dto.DadosLogin;
import com.mvpyouedu.YouEdu_api.domain.dto.RespostaLoginDto;
import com.mvpyouedu.YouEdu_api.domain.usuario.TipoUsuario;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioEntity;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioRepository;
import com.mvpyouedu.YouEdu_api.infra.security.TokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioLogadoService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService service;
    @Autowired
    private EncoderService encode;

    @Transactional
    public RespostaLoginDto logar(DadosLogin login) {
        var usuarioOpt = repository.findByLogin(login.login());

        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Login ou senha inválidos"
            );
        }

        var usuario = usuarioOpt.get();

        if (!encode.matches(login.senha(), usuario.getSenha())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Login ou senha inválidos"
            );
        }
        var token = service.gerarToken(usuario);
        var tipo = usuario.getTipo();
        return new RespostaLoginDto(token, tipo);

    }


    public String recuperarSenha(String login) {
        var usuario = repository.findByLogin(login)
                .orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado"));

        var idUsuario = usuario.getId();


        var link = "Link para redefinir a senha:\n" + "http://localhost:8080/login/redefinir-senha/"+idUsuario + "\n" + "Esse link é válido por 2 horas.";
        return link;

    }

    public UsuarioEntity alterarSenha(Long id,DadoParaAlterarSenha dado) {

        var usuario = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado"));

        if (dado.senha() !=null && !dado.senha().isBlank()){
           usuario.setSenha(encode.encoder(usuario.getSenha()));
        }

        return repository.save(usuario);

    }
}
