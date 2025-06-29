package PFE.CDSIR_AGENCY.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String merchantCode;
    private String phoneNumber;
    private double amount;
    private String reference;
}