package org.example.eko.model.filerepresentations;

import java.util.List;

public record VergleichsdateiFileEntry(String pharmanummer, Integer positionPreisvergleich, List<SubstitutionEntry> substitutionsMedikamente) {
}
