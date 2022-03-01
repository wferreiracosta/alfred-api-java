package br.com.wferreiracosta.alfred.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredenciaisDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

}
