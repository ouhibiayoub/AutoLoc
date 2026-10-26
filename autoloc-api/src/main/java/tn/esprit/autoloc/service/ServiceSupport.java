package tn.esprit.autoloc.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

public final class ServiceSupport {
    private ServiceSupport() {
    }

    public static <T> T reference(JpaRepository<T, Long> repository, Long id, String resource) {
        if (id == null) {
            throw new IllegalArgumentException(resource + " est obligatoire et doit contenir son identifiant");
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resource + " introuvable: " + id));
    }

    public static void requireNewId(Long id, String resource) {
        if (id != null) {
            throw new IllegalArgumentException("Ne pas fournir d'identifiant pour creer " + resource);
        }
    }
}
