//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;

public class PaymentValidationRequest {
    private @NotBlank(
            message = "Le code de validation est requis"
    ) String codeValidation;
    private @NotNull(
            message = "L'ID de la transaction (réservation ou colis) est requis"
    ) Long transactionId;

    @Generated
    public String getCodeValidation() {
        return this.codeValidation;
    }

    @Generated
    public Long getTransactionId() {
        return this.transactionId;
    }

    @Generated
    public void setCodeValidation(final String codeValidation) {
        this.codeValidation = codeValidation;
    }

    @Generated
    public void setTransactionId(final Long transactionId) {
        this.transactionId = transactionId;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PaymentValidationRequest)) {
            return false;
        } else {
            PaymentValidationRequest other = (PaymentValidationRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$transactionId = this.getTransactionId();
                Object other$transactionId = other.getTransactionId();
                if (this$transactionId == null) {
                    if (other$transactionId != null) {
                        return false;
                    }
                } else if (!this$transactionId.equals(other$transactionId)) {
                    return false;
                }

                Object this$codeValidation = this.getCodeValidation();
                Object other$codeValidation = other.getCodeValidation();
                if (this$codeValidation == null) {
                    if (other$codeValidation != null) {
                        return false;
                    }
                } else if (!this$codeValidation.equals(other$codeValidation)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof PaymentValidationRequest;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $transactionId = this.getTransactionId();
        result = result * 59 + ($transactionId == null ? 43 : $transactionId.hashCode());
        Object $codeValidation = this.getCodeValidation();
        result = result * 59 + ($codeValidation == null ? 43 : $codeValidation.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "PaymentValidationRequest(codeValidation=" + this.getCodeValidation() + ", transactionId=" + this.getTransactionId() + ")";
    }
}
