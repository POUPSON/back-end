package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.dto.ColisRequestDto;
import PFE.CDSIR_AGENCY.dto.ColisResponseDto;
import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.entity.Colis;
import PFE.CDSIR_AGENCY.entity.Colis.ColisStatus;
import PFE.CDSIR_AGENCY.entity.Voyage;
import PFE.CDSIR_AGENCY.exception.OperationNotAllowedException;
import PFE.CDSIR_AGENCY.repository.AgenceRepository;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import PFE.CDSIR_AGENCY.repository.ColisRepository;
import PFE.CDSIR_AGENCY.repository.VoyageRepository;
import PFE.CDSIR_AGENCY.service.ColisService;
import PFE.CDSIR_AGENCY.util.TrackingNumberGenerator; // Assurez-vous que cette classe existe et est correcte

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors; // Import pour Stream API

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

    // Méthode utilitaire pour convertir Colis en ColisResponseDto
    // Pas un @Override car elle n'est pas dans l'interface
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

        if (colis.getAgence() != null) {
            dto.setAgenceId(colis.getAgence().getId());
            dto.setAgenceNom(colis.getAgence().getNomAgence());
        }

        if (colis.getAssignedVoyage() != null) {
            dto.setAssignedVoyageId(colis.getAssignedVoyage().getId());
            dto.setAssignedVoyageDescription(colis.getAssignedVoyage().getDescription());
            if (colis.getAssignedVoyage().getVehicule() != null) {
                dto.setAssignedVoyageVehiculeImmatriculation(colis.getAssignedVoyage().getVehicule().getImmatriculation());
            }
        }

        return dto;
    }

    // Implémentation de createColis (anciennement deposerColis)
    @Override
    @Transactional
    public ColisResponseDto createColis(ColisRequestDto colisRequestDto) {
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
    public ColisResponseDto createColisForClient(Long clientId, ColisRequestDto colisRequestDto) {
        // Implémentation de base, à compléter selon votre logique métier
        // Assurez-vous que le clientId correspond au clientExpediteur
        colisRequestDto.setClientId(clientId);
        return createColis(colisRequestDto);
    }

    @Override
    public ColisResponseDto getColisById(Long id) {
        return colisRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec l'ID: " + id));
    }

    @Override
    public ColisResponseDto getColisByIdAndClientId(Long colisId, Long clientId) {
        return colisRepository.findById(colisId)
                .filter(colis -> colis.getClientExpediteur() != null && colis.getClientExpediteur().getId().equals(clientId))
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé ou non associé à ce client: " + colisId));
    }

    @Override
    public List<ColisResponseDto> getAllColis() {
        return colisRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ColisResponseDto> getColisBySenderId(Long senderId) {
        return colisRepository.findByClientExpediteurId(senderId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ColisResponseDto> getColisByRecipientId(Long recipientId) {
        // Cette méthode nécessite d'abord de trouver le client destinataire par son ID
        // et ensuite de chercher les colis où ce client est le destinataire.
        // Si vous n'avez pas de relation directe 'ClientDestinataire' sur Colis,
        // vous devrez peut-être ajuster votre entité Colis ou la logique de recherche.
        // Pour l'instant, je vais simuler une recherche par numéro de téléphone du destinataire
        // si l'ID du destinataire est en fait un ID de client enregistré.
        // Ou si 'recipientId' est l'ID d'un client, vous devriez avoir une relation.
        // Pour l'exemple, je vais retourner une liste vide ou vous pouvez adapter.
        return colisRepository.findAll().stream() // Exemple: à remplacer par une vraie requête
                .filter(colis -> colis.getNumeroDestinataire() != null && colis.getNumeroDestinataire().equals(String.valueOf(recipientId))) // Exemple de filtre
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ColisResponseDto updateColis(Long id, ColisRequestDto colisRequestDto) {
        Colis existingColis = colisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec l'ID: " + id));

        // Mettre à jour les champs nécessaires
        existingColis.setDescription(colisRequestDto.getDescription());
        existingColis.setWeight(colisRequestDto.getWeight());
        existingColis.setDimensions(colisRequestDto.getDimensions());
        existingColis.setEstimatedCost(colisRequestDto.getEstimatedCost());
        existingColis.setNomExpediteur(colisRequestDto.getSenderName());
        existingColis.setTelephoneExpediteur(colisRequestDto.getSenderPhone());
        existingColis.setEmailExpediteur(colisRequestDto.getSenderEmail());
        existingColis.setVilleOrigine(colisRequestDto.getOriginCity());
        existingColis.setQuartierExpedition(colisRequestDto.getOriginNeighborhood());
        existingColis.setNomDestinataire(colisRequestDto.getReceiverName());
        existingColis.setNumeroDestinataire(colisRequestDto.getReceiverPhone());
        existingColis.setEmailDestinataire(colisRequestDto.getReceiverEmail());
        existingColis.setVilleDeDestination(colisRequestDto.getDestinationCity());
        existingColis.setQuartierDestination(colisRequestDto.getDestinationNeighborhood());
        existingColis.setModePaiement(colisRequestDto.getModePaiement());
        existingColis.setReferencePaiement(colisRequestDto.getPaymentReference());
        existingColis.setDateLivraisonPrevue(colisRequestDto.getPlannedDeliveryDate());

        // Mise à jour de l'agence si fournie
        if (colisRequestDto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(colisRequestDto.getAgenceId())
                    .orElseThrow(() -> new RuntimeException("Agence non trouvée avec l'ID: " + colisRequestDto.getAgenceId()));
            existingColis.setAgence(agence);
        }

        // Mise à jour du client expéditeur si fournie
        if (colisRequestDto.getClientId() != null) {
            Client clientExpediteur = clientRepository.findById(colisRequestDto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + colisRequestDto.getClientId()));
            existingColis.setClientExpediteur(clientExpediteur);
        }
        
        Colis updatedColis = colisRepository.save(existingColis);
        return convertToDto(updatedColis);
    }

    @Override
    @Transactional
    public ColisResponseDto updateColisForClient(Long colisId, Long clientId, ColisRequestDto colisRequestDto) {
        Colis existingColis = colisRepository.findById(colisId)
                .filter(colis -> colis.getClientExpediteur() != null && colis.getClientExpediteur().getId().equals(clientId))
                .orElseThrow(() -> new RuntimeException("Colis non trouvé ou non associé à ce client: " + colisId));

        // Mettre à jour les champs autorisés pour un client
        existingColis.setDescription(colisRequestDto.getDescription());
        existingColis.setWeight(colisRequestDto.getWeight());
        existingColis.setDimensions(colisRequestDto.getDimensions());
        // Les autres champs sensibles (statut, agence, etc.) ne devraient pas être modifiables par le client ici.

        Colis updatedColis = colisRepository.save(existingColis);
        return convertToDto(updatedColis);
    }

    @Override
    @Transactional
    public void deleteColis(Long id) {
        colisRepository.deleteById(id);
    }

    @Override
    public void cancelColisForClient(Long colisId, Long clientId) {
        Colis colis = colisRepository.findById(colisId)
                .filter(c -> c.getClientExpediteur() != null && c.getClientExpediteur().getId().equals(clientId))
                .orElseThrow(() -> new RuntimeException("Colis non trouvé ou non associé à ce client."));

        if (colis.getStatut() == ColisStatus.ENREGISTRE) {
            colis.setStatut(ColisStatus.ANNULE);
            colisRepository.save(colis);
        } else {
            throw new RuntimeException("Le colis ne peut être annulé que s'il est au statut ENREGISTRE.");
        }
    }

    @Override
    public ColisResponseDto getColisByTrackingNumber(String trackingNumber) {
        return colisRepository.findByTrackingNumber(trackingNumber)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec le numéro de suivi: " + trackingNumber));
    }

    @Override
    @Transactional
    public ColisResponseDto assignColisToVoyage(Long colisId, Long voyageId) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec l'ID: " + colisId));
        Voyage voyage = voyageRepository.findById(voyageId)
                .orElseThrow(() -> new RuntimeException("Voyage non trouvé avec l'ID: " + voyageId));

        colis.setAssignedVoyage(voyage);
        colis.setStatut(ColisStatus.EN_TRANSIT);
        colis.setDateExpedition(LocalDateTime.now()); // Date d'expédition quand assigné au voyage
        Colis updatedColis = colisRepository.save(colis);
        return convertToDto(updatedColis);
    }

    @Override
    @Transactional
    public ColisResponseDto updateColisStatus(Long colisId, String newStatus) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec l'ID: " + colisId));

        try {
            ColisStatus status = ColisStatus.valueOf(newStatus.toUpperCase());
            colis.setStatut(status);
            if (status == ColisStatus.LIVRE) {
                colis.setDateLivraisonReelle(LocalDateTime.now());
            } else if (status == ColisStatus.ARRIVE_AGENCE_DESTINATION) {
                colis.setDateArriveeAgenceDestination(LocalDateTime.now());
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut de colis invalide: " + newStatus);
        }

        Colis updatedColis = colisRepository.save(colis);
        return convertToDto(updatedColis);
    }

    @Override
    public void sendSenderPickupNotification(Long colisId) {
        // Logique pour envoyer une notification à l'expéditeur (email/SMS)
        System.out.println("Notification d'enlèvement envoyée à l'expéditeur pour le colis ID: " + colisId);
    }

    @Override
    public void sendRecipientReadyForPickupNotification(Long colisId) {
        // Logique pour envoyer une notification au destinataire (email/SMS)
        System.out.println("Notification de colis prêt à être enlevé envoyée au destinataire pour le colis ID: " + colisId);
    }

    @Override
    public void sendDeliveryConfirmationNotification(Long colisId) {
        // Logique pour envoyer une notification de confirmation de livraison
        System.out.println("Notification de confirmation de livraison envoyée pour le colis ID: " + colisId);
    }

    @Override
    public List<ColisResponseDto> getColisByStatus(String status) {
        ColisStatus colisStatus = ColisStatus.valueOf(status.toUpperCase());
        return colisRepository.findByStatut(colisStatus).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ColisResponseDto> getColisByVoyageId(Long voyageId) {
        return colisRepository.findByAssignedVoyageId(voyageId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void verifyColisOwnership(Long colisId, Long clientId) throws OperationNotAllowedException {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new OperationNotAllowedException("Colis non trouvé."));
        if (colis.getClientExpediteur() == null || !colis.getClientExpediteur().getId().equals(clientId)) {
            throw new OperationNotAllowedException("Le client n'est pas l'expéditeur de ce colis.");
        }
    }

    @Override
    public List<ColisResponseDto> searchColis(String keyword) {
        return colisRepository.searchByTrackingNumberOrNames(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ColisResponseDto updateColisDeliveryDate(Long colisId, String newDeliveryDate) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec l'ID: " + colisId));
        colis.setDateLivraisonPrevue(newDeliveryDate); // Assurez-vous que le type de date est compatible
        Colis updatedColis = colisRepository.save(colis);
        return convertToDto(updatedColis);
    }

    @Override
    @Transactional
    public ColisResponseDto confirmColisPayment(String paymentReference) {
        Colis colis = colisRepository.findByReferencePaiement(paymentReference)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec la référence de paiement: " + paymentReference));

        if (colis.getStatut() == ColisStatus.ENREGISTRE) { // Ou un autre statut approprié pour le paiement
            colis.setStatut(ColisStatus.PAID); // Assurez-vous d'avoir un statut PAID dans votre enum ColisStatus
            colisRepository.save(colis);
            return convertToDto(colis);
        } else {
            throw new RuntimeException("Le paiement pour ce colis ne peut pas être confirmé dans son état actuel.");
        }
    }
}
