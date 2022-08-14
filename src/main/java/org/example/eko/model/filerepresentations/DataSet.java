package org.example.eko.model.filerepresentations;

import java.util.List;

public record DataSet(List<AtcCodeFileEntry> atcCodeFileEntries, List<HinweisFileEntry> hinweisFileEntries,
                      List<MedikamentFileEntry> medikamentFileEntries, List<RegeltextFileEntry> regeltextFileEntries,
                      List<WirkstoffFileEntry> wirkstoffFileEntries, List<MedikamentZusatzFileEntry> medikamentZusatzFileEntries,
                      List<RezeptpflichtFileEntry> rezeptpflichtFileEntries, List<WirkstoffInformationFileEntry> wirkstoffInformationFileEntries,
                      List<VergleichsdateiFileEntry> vergleichsdateiFileEntries) {
}
