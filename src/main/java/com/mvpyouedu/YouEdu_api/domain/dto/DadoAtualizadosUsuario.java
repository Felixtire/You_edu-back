package com.mvpyouedu.YouEdu_api.domain.dto;

import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioEntity;

public record DadoAtualizadosUsuario(String nome, String email , String senha) {

    public DadoAtualizadosUsuario(UsuarioEntity usuario){
        this(usuario.getNome(),usuario.getEmail(),usuario.getSenha());
    }

}
