package org.example.eko.model.filerepresentations;

public record MedikamentFileEntry(String pharmaNummer, String registerNummer, String name, String box, String kassenZeichen, Double menge, String mengenArt, Double kvp, String kvpEinheit, String darreichungsform, String teilbarkeit, String preisModell, Double abgabeAnzahl, String packungsHinweis, String euRegisterNummerPrefix, String euRegisterNummerPackungsNummer) {
}
