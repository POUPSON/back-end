//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.config.JwtUtils;
import PFE.CDSIR_AGENCY.dto.LoginResponse;
import PFE.CDSIR_AGENCY.entity.Administrateur;
import PFE.CDSIR_AGENCY.entity.Role;
import PFE.CDSIR_AGENCY.exception.DuplicateCniException;
import PFE.CDSIR_AGENCY.exception.InvalidCredentialsException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.AdministrateurRepository;
import PFE.CDSIR_AGENCY.service.AdministrateurService;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdministrateurServiceImpl implements AdministrateurService {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(AdministrateurServiceImpl.class);
    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public Administrateur enregistrerAdministrateur(Administrateur administrateur) {
        if (this.administrateurRepository.existsByNumeroCni(administrateur.getNumeroCni())) {
            throw new DuplicateCniException("Le numéro de CNI " + administrateur.getNumeroCni() + " est déjà utilisé.");
        } else {
            administrateur.setMotPasse(this.passwordEncoder.encode(administrateur.getMotPasse()));
            if (administrateur.getRole() == null) {
                administrateur.setRole(Role.ADMIN);
            }

            administrateur.setStatut("ACTIF");
            log.info("Enregistrement du nouvel administrateur : {}", administrateur.getNomAdministrateur());
            return (Administrateur)this.administrateurRepository.save(administrateur);
        }
    }

    public LoginResponse loginAdministrateur(String numeroCni, String motPasse) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(numeroCni, motPasse));
            Administrateur admin = (Administrateur)authentication.getPrincipal();
            if (!admin.isEnabled()) {
                throw new InvalidCredentialsException("Le compte administrateur est inactif.");
            } else {
                String token = this.jwtUtils.generateTokenAdmin(admin);
                log.info("Administrateur {} connecté avec succès", numeroCni);
                return new LoginResponse(true, "Connexion administrateur réussie", token, admin.getId(), admin.getNomAdministrateur(), (String)null, admin.getNumeroCni(), admin.getRole().name());
            }
        } catch (AuthenticationException e) {
            log.warn("Tentative de connexion échouée pour l'administrateur {}: {}", numeroCni, e.getMessage());
            throw new InvalidCredentialsException("Numéro CNI ou mot de passe incorrect.");
        }
    }

    public Administrateur getAdministrateurById(Long id) {
        return (Administrateur)this.administrateurRepository.findById(id).orElseThrow(() -> new NotFoundException("Administrateur non trouvé avec l'ID : " + id));
    }

    public List<Administrateur> getAllAdministrateurs() {
        return this.administrateurRepository.findAll();
    }

    public Administrateur updateAdministrateur(Long id, Administrateur updatedAdministrateur) {
        Administrateur existingAdmin = (Administrateur)this.administrateurRepository.findById(id).orElseThrow(() -> new NotFoundException("Administrateur non trouvé avec l'ID : " + id));
        existingAdmin.setNomAdministrateur(updatedAdministrateur.getNomAdministrateur());
        existingAdmin.setAgence(updatedAdministrateur.getAgence());
        existingAdmin.setStatut(updatedAdministrateur.getStatut());
        if (updatedAdministrateur.getMotPasse() != null && !updatedAdministrateur.getMotPasse().isEmpty()) {
            existingAdmin.setMotPasse(this.passwordEncoder.encode(updatedAdministrateur.getMotPasse()));
        }

        log.info("Mise à jour de l'administrateur avec ID : {}", id);
        return (Administrateur)this.administrateurRepository.save(existingAdmin);
    }

    public void deleteAdministrateur(Long id) {
        if (!this.administrateurRepository.existsById(id)) {
            throw new NotFoundException("Administrateur non trouvé avec l'ID : " + id);
        } else {
            this.administrateurRepository.deleteById(id);
            log.info("Administrateur avec ID {} supprimé avec succès", id);
        }
    }

    @Generated
    public AdministrateurServiceImpl(final AdministrateurRepository administrateurRepository, final PasswordEncoder passwordEncoder, final JwtUtils jwtUtils, final AuthenticationManager authenticationManager) {
        this.administrateurRepository = administrateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }
}
