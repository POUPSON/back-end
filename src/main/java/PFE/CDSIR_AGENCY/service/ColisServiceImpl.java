// src/main/java/PFE/CDSIR_AGENCY/service/impl/ColisServiceImpl.java
package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.dto.ColisRequestDto;
import PFE.CDSIR_AGENCY.dto.ColisResponseDto;
import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.entity.Colis;
import PFE.CDSIR_AGENCY.entity.Colis.ColisStatus;
import PFE.CDSIR_AGENCY.entity.Voyage;
import PFE.CDSIR_AGENCY.repository.AgenceRepository;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import PFE.CDSIR_AGENCY.repository.ColisRepository;
import PFE.CDSIR_AGENCY.repository.VoyageRepository;
import PFE.CDSIR_AGENCY.service.ColisService;
import PFE.CDSIR_AGENCY.util.TrackingNumberGenerator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ColisServiceImpl implements ColisService {

    private final ColisRepository colisRepository;
    private final AgenceRepository agenceRepository;
    private final ClientRepository clientRepository;
    private final VoyageRepository voyageRepository;

    public ColisServiceImpl(ColisRepository colisRepository,
                            AgenceRepository agenceRepository,
                            ClientRepository clientRepository,
                            VoyageRepository voyageRepository) {
        this.colisRepository = colisRepository;
        this.agenceRepository = agenceRepository;
        this.clientRepository = clientRepository;
        this.voyageRepository = voyageRepository;
    }

    @Override
    @Transactional
    public ColisResponseDto deposerColis(ColisRequestDto colisRequestDto) {
        Colis colis = new Colis();

        Agence agence = agenceRepository.findById(colisRequestDto.getAgenceId())
                .orElseThrow(() -> new RuntimeException("Agence non trouvée avec l'ID: " + colisRequestDto.getAgenceId()));
        colis.setAgence(agence);

        Client clientExpediteur = null;
        if (colisRequestDto.getClientId() != null) {
            clientExpediteur = clientRepository.findById(colisRequestDto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + colisRequestDto.getClientId()));
        }
        colis.setClientExpediteur(clientExpediteur);

        colis.setDescription(colisRequestDto.getDescription());
        colis.setWeight(colisRequestDto.getWeight());
        colis.setDimensions(colisRequestDto.getDimensions());
        colis.setEstimatedCost(colisRequestDto.getEstimatedCost());

        colis.setNomExpediteur(colisRequestDto.getSenderName());
        colis.setTelephoneExpediteur(colisRequestDto.getSenderPhone());
        colis.setEmailExpediteur(colisRequestDto.getSenderEmail());
        colis.setVilleOrigine(colisRequestDto.getOriginCity());
        colis.setQuartierExpedition(colisRequestDto.getOriginNeighborhood());

        colis.setNomDestinataire(colisRequestDto.getReceiverName());
        colis.setNumeroDestinataire(colisRequestDto.getReceiverPhone());
        colis.setEmailDestinataire(colisRequestDto.getReceiverEmail());
        colis.setVilleDeDestination(colisRequestDto.getDestinationCity());
        colis.setQuartierDestination(colisRequestDto.getDestinationNeighborhood());

        colis.setModePaiement(colisRequestDto.getModePaiement());
        colis.setReferencePaiement(colisRequestDto.getPaymentReference());

        colis.setDateEnregistrement(LocalDateTime.now());
        colis.setStatut(ColisStatus.ENREGISTRE);
        colis.setTrackingNumber(TrackingNumberGenerator.generateTrackingNumber());
        colis.setDateLivraisonPrevue(colisRequestDto.getPlannedDeliveryDate());

        colis.setCodeValidation(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        Colis savedColis = colisRepository.save(colis);

        return convertToDto(savedColis);
    }

    @Override
    public ColisResponseDto convertToDto(Colis colis) {
        ColisResponseDto dto = new ColisResponseDto();

        dto.setIdColis(colis.getId());
        dto.setDescription(colis.getDescription());
        dto.setWeight(colis.getWeight());
        dto.setDimensions(colis.getDimensions());
        dto.setEstimatedCost(colis.getEstimatedCost());
        dto.setTrackingNumber(colis.getTrackingNumber());
        dto.setStatut(colis.getStatut());
        dto.setDateEnregistrement(colis.getDateEnregistrement());
        dto.setDateExpedition(colis.getDateExpedition());
        dto.setDateArriveeAgenceDestination(colis.getDateArriveeAgenceDestination());
        dto.setDateLivraisonPrevue(colis.getDateLivraisonPrevue());
        dto.setDateLivraisonReelle(colis.getDateLivraisonReelle());

        if (colis.getClientExpediteur() != null) {
            dto.setClientId(colis.getClientExpediteur().getId());
        }

        dto.setSenderName(colis.getNomExpediteur());
        dto.setSenderPhone(colis.getTelephoneExpediteur());
        dto.setSenderEmail(colis.getEmailExpediteur());
        dto.setVilleOrigine(colis.getVilleOrigine());
        dto.setQuartierExpedition(colis.getQuartierExpedition());

        dto.setNomDestinataire(colis.getNomDestinataire());
        dto.setNumeroDestinataire(colis.getNumeroDestinataire());
        dto.setEmailDestinataire(colis.getEmailDestinataire());
        dto.setVilleDeDestination(colis.getVilleDeDestination());
        dto.setQuartierDestination(colis.getQuartierDestination());

        dto.setModePaiement(colis.getModePaiement());
        dto.setPaymentReference(colis.getReferencePaiement());
        dto.setCodeValidation(colis.getCodeValidation());

        // --- CORRECTION HERE ---
        if (colis.getAgence() != null) {
            dto.setAgenceId(colis.getAgence().getId());
            // Corrected from getNom() to getNomAgence()
            dto.setAgenceNom(colis.getAgence().getNomAgence());
        }
        // --- END CORRECTION ---

        if (colis.getAssignedVoyage() != null) {
            dto.setAssignedVoyageId(colis.getAssignedVoyage().getId());
            // This assumes you added 'description' to your Voyage entity as discussed.
            dto.setAssignedVoyageDescription(colis.getAssignedVoyage().getDescription());
            if (colis.getAssignedVoyage().getVehicule() != null) {
                dto.setAssignedVoyageVehiculeImmatriculation(colis.getAssignedVoyage().getVehicule().getImmatriculation());
            }
        }

        return dto;
    }

    @Override
    public Optional<Colis> getColisByTrackingNumber(String trackingNumber) {
        return colisRepository.findByTrackingNumber(trackingNumber);
    }

    @Override
    public List<Colis> getColisByStatus(ColisStatus status) {
        return colisRepository.findByStatut(status);
    }

    @Override
    public List<Colis> searchColis(String keyword) {
        return colisRepository.searchByTrackingNumberOrNames(keyword);
    }
}
