package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.config.security.UserSS;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final var cli = clienteRepository.findByEmail(email);

        if (isNull(cli)) {
            throw new UsernameNotFoundException(email);
        }

        final var authorities = cli.getPerfis().stream().map(
                perfil -> new SimpleGrantedAuthority(perfil.getDescricao()))
                .collect(Collectors.toList());

        return UserSS.builder()
                .id(cli.getId()).email(cli.getEmail()).senha(cli.getSenha())
                .authorities(authorities).build();
    }

}
