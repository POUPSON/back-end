package PFE.CDSIR_AGENCY.dto;

import  PFE.CDSIR_AGENCY.entity.Colis;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistoriqueDTO {
    private Long id;
    private String description;
    private String typeEvenement;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateEvenement;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreation;

    private Long reservationId;
    private String reservationStatut;

    private Long colisId;
    private String colisTrackingNumber;
    private String colisStatut; // Gardé en String pour la compatibilité

    private Long clientId;
    private String clientNomComplet;
    private Long administrateurId;
    private String administrateurNom;

    // Ajoutez cette méthode pour la conversion
    public void setColisStatutFromEnum(Colis.ColisStatus status) {
        this.colisStatut = status != null ? status.name() : null;
    }

    // Ajoutez cette méthode si vous avez besoin de récupérer l'enum
    public Colis.ColisStatus getColisStatusAsEnum() {
        return colisStatut != null ? Colis.ColisStatus.valueOf(colisStatut) : null;
    }
}