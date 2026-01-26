package com.mvpyouedu.YouEdu_api.service;

import com.mvpyouedu.YouEdu_api.domain.dto.DadosCadastroUsuario;
import com.mvpyouedu.YouEdu_api.domain.dto.DadosLogin;
import com.mvpyouedu.YouEdu_api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncoderService {

    @Autowired
    private PasswordEncoder encoder;



    public String encoder(String senha){
        return encoder.encode(senha);
    }

    public boolean matches(String senhaDigitada, String senhaBanco){
        return encoder.matches(senhaDigitada, senhaBanco);
    }
}
