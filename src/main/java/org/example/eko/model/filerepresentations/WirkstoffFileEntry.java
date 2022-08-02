package org.example.eko.model.filerepresentations;

public record WirkstoffFileEntry(String pharmaNummer, String pharAtcCode, String wirkAtcCode, Integer laufnummer, String wirkstoffStärke, String wirkstoffStärkenDimension, String wirkstoffEigenschaft) {
}
