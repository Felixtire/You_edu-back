package com.mvpyouedu.YouEdu_api.domain.dto;

import com.mvpyouedu.YouEdu_api.domain.usuario.TipoUsuario;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioEntity;

public record RespostaLoginDto(String token, TipoUsuario tipoUsuario) {

}
