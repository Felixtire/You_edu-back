package com.mvpyouedu.YouEdu_api.domain.dto;

import com.mvpyouedu.YouEdu_api.domain.usuario.TipoUsuario;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioEntity;

public record DadosSaidaCadastroUsuario(String nome, String email, TipoUsuario tipoUsuario) {

    public DadosSaidaCadastroUsuario(UsuarioEntity usuario) {
        this(usuario.getNome(), usuario.getEmail(),usuario.getTipo());
    }
}
