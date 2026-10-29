package tn.esprit.autoloc.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PaiementDTOValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void acceptsValidPayment() {
        PaiementDTO dto = new PaiementDTO();
        dto.setMontant(new BigDecimal("25.50"));
        dto.setDatePaiement(LocalDate.now());
        dto.setModePaiement(ModePaiement.CARTE);
        dto.setContratId(1L);

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void rejectsMissingFieldsAndNegativeAmount() {
        PaiementDTO dto = new PaiementDTO();
        dto.setMontant(new BigDecimal("-1"));

        Set<String> invalidFields = validator.validate(dto).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());

        assertTrue(invalidFields.containsAll(Set.of("montant", "datePaiement", "modePaiement", "contratId")));
        assertFalse(validator.validate(dto).isEmpty());
    }
}
