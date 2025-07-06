// src/main/java/PFE/CDSIR_AGENCY/service/impl/ColisServiceImpl.java
package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.dto.ColisRequestDto;
import PFE.CDSIR_AGENCY.dto.ColisResponseDto;
import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.entity.Colis;
import PFE.CDSIR_AGENCY.entity.Colis.ColisStatus;
import PFE.CDSIR_AGENCY.entity.Voyage; // Importez Voyage
import PFE.CDSIR_AGENCY.repository.AgenceRepository;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import PFE.CDSIR_AGENCY.repository.ColisRepository;
import PFE.CDSIR_AGENCY.repository.VoyageRepository; // Importez VoyageRepository
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
    private final VoyageRepository voyageRepository; // Injectez VoyageRepository

    public ColisServiceImpl(ColisRepository colisRepository,
                            AgenceRepository agenceRepository,
                            ClientRepository clientRepository,
                            VoyageRepository voyageRepository) { // Ajoutez VoyageRepository au constructeur
        this.colisRepository = colisRepository;
        this.agenceRepository = agenceRepository;
        this.clientRepository = clientRepository;
        this.voyageRepository = voyageRepository; // Initialisez-le
    }

    @Override
    @Transactional
    public ColisResponseDto deposerColis(ColisRequestDto colisRequestDto) {
        Colis colis = new Colis();

        // 1. Récupérer l'agence via agenceId du DTO
        Agence agence = agenceRepository.findById(colisRequestDto.getAgenceId())
                .orElseThrow(() -> new RuntimeException("Agence non trouvée avec l'ID: " + colisRequestDto.getAgenceId()));
        colis.setAgence(agence);

        // 2. Gérer le client expéditeur (optionnel, basé sur clientId si fourni)
        Client clientExpediteur = null;
        if (colisRequestDto.getClientId() != null) {
            clientExpediteur = clientRepository.findById(colisRequestDto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + colisRequestDto.getClientId()));
        }
        colis.setClientExpediteur(clientExpediteur);

        // 3. Mapper les données du DTO de requête vers l'entité Colis
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

        // Dates et statuts automatiques
        colis.setDateEnregistrement(LocalDateTime.now());
        colis.setStatut(ColisStatus.ENREGISTRE);
        colis.setTrackingNumber(TrackingNumberGenerator.generateTrackingNumber());
        colis.setDateLivraisonPrevue(colisRequestDto.getPlannedDeliveryDate());

        colis.setCodeValidation(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        Colis savedColis = colisRepository.save(colis);

        return convertToDto(savedColis);
    }

    // --- MISE À JOUR MAJEURE ICI : convertToDto ---
    @Override
    public ColisResponseDto convertToDto(Colis colis) {
        ColisResponseDto dto = new ColisResponseDto();

        dto.setIdColis(colis.getId()); // Assurez-vous que votre entité Colis a bien un getId() pour l'ID
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

        // Informations du client expéditeur
        if (colis.getClientExpediteur() != null) {
            dto.setClientId(colis.getClientExpediteur().getId());
            // Vous pourriez aussi ajouter le nom du client si nécessaire:
            // dto.setClientName(colis.getClientExpediteur().getNom()); // Assurez-vous d'avoir ce champ dans ClientResponseDto si vous voulez le renvoyer
        }

        // Informations de l'expéditeur (peuvent être différentes du client si dépôt public)
        dto.setSenderName(colis.getNomExpediteur());
        dto.setSenderPhone(colis.getTelephoneExpediteur());
        dto.setSenderEmail(colis.getEmailExpediteur());
        dto.setVilleOrigine(colis.getVilleOrigine());
        dto.setQuartierExpedition(colis.getQuartierExpedition());

        // Informations du destinataire
        dto.setNomDestinataire(colis.getNomDestinataire());
        dto.setNumeroDestinataire(colis.getNumeroDestinataire());
        dto.setEmailDestinataire(colis.getEmailDestinataire());
        dto.setVilleDeDestination(colis.getVilleDeDestination());
        dto.setQuartierDestination(colis.getQuartierDestination());

        // Informations de paiement
        dto.setModePaiement(colis.getModePaiement());
        dto.setPaymentReference(colis.getReferencePaiement());
        dto.setCodeValidation(colis.getCodeValidation());

        // Informations de l'agence d'origine
        if (colis.getAgence() != null) {
            dto.setAgenceId(colis.getAgence().getId());
            dto.setAgenceNom(colis.getAgence().getNom()); // Assurez-vous que l'entité Agence a un champ 'nom'
        }

        // Informations du voyage assigné (si un voyage est assigné)
        if (colis.getAssignedVoyage() != null) {
            dto.setAssignedVoyageId(colis.getAssignedVoyage().getId());
            dto.setAssignedVoyageDescription(colis.getAssignedVoyage().getDescription()); // Assurez-vous d'avoir une description dans votre entité Voyage
            dto.setAssignedVoyageVehiculeImmatriculation(colis.getAssignedVoyage().getVehicule().getImmatriculation()); // Assurez-vous que Voyage a un véhicule et que Véhicule a une immatriculation
        }

        return dto;
    }

    // --- Vos autres implémentations de méthodes existantes (sans changement) ---
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

    // ... (autres méthodes du service si elles existent)
}
