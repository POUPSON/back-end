//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.dto.LoginResponse;
import PFE.CDSIR_AGENCY.entity.Administrateur;
import java.util.List;

public interface AdministrateurService {
    Administrateur enregistrerAdministrateur(Administrateur administrateur);

    LoginResponse loginAdministrateur(String numeroCni, String motPasse);

    Administrateur getAdministrateurById(Long id);

    List<Administrateur> getAllAdministrateurs();

    Administrateur updateAdministrateur(Long id, Administrateur updatedAdministrateur);

    void deleteAdministrateur(Long id);
}
