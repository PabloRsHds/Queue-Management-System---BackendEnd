package br.com.queue.service.adm;

import br.com.queue.entities.unit.Unit;
import br.com.queue.entities.user.User;
import br.com.queue.enums.Role;
import br.com.queue.repositories.unit.UnitRepository;
import br.com.queue.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdmService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        var unit = this.unitRepository.findByName("Unidade 01");
        var user = this.userRepository.findByEmail("admin@gmail.com");

        unit.ifPresentOrElse(
                present -> System.out.println("Unidade 01"),
                () -> {

                    var unit_central = new Unit();

                    unit_central.setName("Unidade 01");
                    unit_central.setAddress("sem endereço físico");
                    unit_central.setActive(true);

                    this.unitRepository.save(unit_central);
                }
        );

        unit.ifPresent(
                present -> user.ifPresentOrElse(
                        userPresent -> System.out.println("ADM On"),
                        () -> {
                            var adm1 = new User();

                            adm1.setUsername("P2");
                            adm1.setName("Pablo");
                            adm1.setSurname("Renato");
                            adm1.setActive(true);
                            adm1.setRole(Role.ADMIN);
                            adm1.setEmail("admin@gmail.com");
                            adm1.setPassword(this.passwordEncoder.encode("99218841Pp@"));
                            adm1.setCreatedAt(LocalDateTime.now());
                            adm1.setUnit(null);

                            this.userRepository.save(adm1);
                        }
                )
        );
    }
}
