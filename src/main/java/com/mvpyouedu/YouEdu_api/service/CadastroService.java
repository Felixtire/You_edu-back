package com.mvpyouedu.YouEdu_api.service;

import com.mvpyouedu.YouEdu_api.domain.dto.DadosAtualizarUsuario;
import com.mvpyouedu.YouEdu_api.domain.dto.DadosCadastroUsuario;
import com.mvpyouedu.YouEdu_api.domain.dto.DadosDeletarUsuario;
import com.mvpyouedu.YouEdu_api.domain.dto.ListagemTodoDto;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioEntity;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EncoderService encode;


    public UsuarioEntity cadastrar(DadosCadastroUsuario dadosCadastroUsuario){

        var usuario = new UsuarioEntity(dadosCadastroUsuario);

        usuario.setSenha(encode.encoder(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }




    public UsuarioEntity atualizarCadastro(DadosAtualizarUsuario dados){
        var usuario = usuarioRepository.findByEmail(dados.email())
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));

        if (dados.nome() != null) {
            usuario.setNome(dados.nome());
        }

        if (dados.email() != null && !dados.email().equals(usuario.getEmail())) {
            usuarioRepository.findByEmail(dados.email())

                    .ifPresent(u->{
                        throw new RuntimeException("Login já está em uso");
                    });
            usuario.setEmail(dados.email());
        }

        if (dados.senha() != null) {
            usuario.setSenha(encode.encoder(dados.senha()));
        }
        return usuarioRepository.save(usuario);

    }

    public void deletarCadastro(Long id){
        var usuario = usuarioRepository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
    usuarioRepository.deleteById(usuario.getId());

    }

    public List<ListagemTodoDto> listarUsuario(){

        var usuario = usuarioRepository.findAll().stream().map(ListagemTodoDto::new).toList();
        return usuario;
    }


}
