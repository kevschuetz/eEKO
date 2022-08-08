package org.example.eko.model.filerepresentations;

import io.swagger.models.auth.In;

public record MedikamentFileEntry(String pharmaNummer, String registerNummer, String name, String box, String kassenZeichen, Double menge, String mengenArt, Double kvp, Double kvpEinheit, String darreichungsform, String teilbarkeit, String preisModell, Integer abgabeAnzahl, String packungsHinweis, String euRegisterNummerPrefix, String euRegisterNummerPackungsNummer) {
}
