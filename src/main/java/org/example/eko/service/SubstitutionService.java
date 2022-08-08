package org.example.eko.service;

import org.example.eko.model.entities.DateEntity;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.WirkstoffAtcCode;
import org.example.eko.model.entities.Wirkstoff;
import org.example.eko.model.repositories.MedikamentRepository;
import org.example.eko.model.repositories.WirkstoffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubstitutionService {

    private final MedikamentRepository medikamentRepository;
    private final WirkstoffRepository wirkstoffRepository;

    public SubstitutionService(MedikamentRepository medikamentRepository, WirkstoffRepository wirkstoffRepository){
        this.medikamentRepository = medikamentRepository;
        this.wirkstoffRepository = wirkstoffRepository;
    }

//    public Map<DateEntity, Map<Double, List<Medikament>>> getSubstitutesForMedikamentHistorical(String pharmaNummer){
//        getSubstitutesForMedikament(pharmaNummer, null);
//        // Get all entries with pharmanummer
//        Collection<Medikament> entries = medikamentRepository.findByPharmaNummer(pharmaNummer);
//
//        // Only consider most recent
//        //Medikament medikament = entries.stream().min(Comparator.comparing(AuditModel::getTimestamp)).get();
//
//        Collection<Medikament> medikamente = medikamentRepository.findAll();
//
//        HashMap<DateEntity, Map<Double,List<Medikament>>> resultMap = new HashMap<>();
//        for(Medikament medikament : entries){
//            // Get all entries from same import
//            List<Medikament> substitutes = medikamente
//                    .stream()
//                    .filter(m -> m.getValidDate().equals(medikament.getValidDate()))
//                    .collect(Collectors.toList());
//
//            // Reduce possible substitutes to all that have at least one wirkstoff where first 3 characters are equal to a wirkstoff from the medikament
//            for(Wirkstoff w : medikament.getWirkstoffe()){
//                substitutes = substitutes.stream().filter(s -> {
//                    boolean isValid = false;
//                    for(Wirkstoff ws : s.getWirkstoffe()){
//                        if(ws.getPharWirkstoff().getAtcCode().substring(0, 3).equals(w.getPharWirkstoff().getAtcCode().substring(0, 3))){
//                            isValid = true;
//                        }
//                    }
//                    return  isValid;
//                }).collect(Collectors.toList());
//            }
//
//            // Group possible substitutes by the number of overlapping characters in the atccodes of the respective wirkstoffe
//            int possibleScore = medikament
//                    .getWirkstoffe()
//                    .stream().mapToInt(w -> w.getPharWirkstoff().getAtcCode().length())
//                    .reduce(0, (s1, s2)->s1+s2);
//
//            resultMap.put(medikament.getValidDate(), substitutes.stream()
//                    .filter(s -> !s.getPharmaNummer().equals(medikament.getPharmaNummer()))
//                    .collect(Collectors.groupingBy(substitute -> {
//                        int substituteScore = 0;
//                        for(Wirkstoff w : medikament.getWirkstoffe()){
//                            var bestWirkstoff = substitute.getWirkstoffe().stream().max(Comparator.comparingInt(wi -> {
//                                int wirkstoffScoreTmp = 0;
//                                for(int i = 0; i < w.getPharWirkstoff().getAtcCode().length(); i++){
//                                    if(wi.getPharWirkstoff().getAtcCode().charAt(i) == w.getPharWirkstoff().getAtcCode().charAt(i)) wirkstoffScoreTmp++;
//                                }
//                                return wirkstoffScoreTmp;
//                            })).get();
//
//                            int wirkstoffScore = 0;
//                            for(int i = 0; i < w.getPharWirkstoff().getAtcCode().length(); i++){
//                                if(bestWirkstoff.getPharWirkstoff().getAtcCode().charAt(i) == w.getPharWirkstoff().getAtcCode().charAt(i)) wirkstoffScore++;
//                            }
//                            substituteScore += wirkstoffScore;
//                        }
//                        return (double) substituteScore / possibleScore * 100;
//                    })));
//        }
//        return resultMap;
//    }
//
//
//    public Map<WirkstoffAtcCode, Map<WirkstoffAtcCode, List<Medikament>>> getSubstitutesForMedikament(String pharmaNummer, LocalDate d){
//        var medikamente = medikamentRepository.findByPharmaNummer(pharmaNummer);
//        var medikament = medikamente.stream().filter(med -> med.getValidDate().getDate().compareTo(d) <= 0).max(Comparator.comparing(m -> m.getValidDate().getDate()));
//
//        if(medikament.isEmpty()) return null;
//
//        Medikament med = medikament.get();
//        var wirkstoffe = med.getWirkstoffe()
//                .stream()
//                .map(wi -> wi.getPharWirkstoff())
//                .distinct()
//                .collect(Collectors.toList());
//        wirkstoffe.addAll(med.getWirkstoffe()
//                .stream()
//                .map(wi -> wi.getWirkWirkstoff())
//                .filter(wi -> wi != null)
//                .distinct()
//                .collect(Collectors.toList()));
//
//        Map<WirkstoffAtcCode, Map<WirkstoffAtcCode, List<Medikament>>> substituteMap = new HashMap<>();
//        var possibleSubstitutes = medikamentRepository.getMedikamentsByValidDateID(med.getValidDate().getId())
//                .stream()
//                .filter(m -> !m.getPharmaNummer().equals(med.getPharmaNummer()))
//                .collect(Collectors.toList());
//
//        for(WirkstoffAtcCode w : wirkstoffe){
//            substituteMap.put(w, new HashMap<>());
//            List<WirkstoffAtcCode> dimensions = new ArrayList<>();
//            dimensions.add(w);
//            substituteMap.get(w).put(w, new ArrayList<>());
//            String atcCode = w.getAtcCode().substring(0, w.getAtcCode().length() - 1);
//            while (atcCode.length() >= 5){
//                var optW = wirkstoffRepository.findById(atcCode);
//                if(optW.isPresent()) {
//                    dimensions.add(optW.get());
//                    substituteMap.get(w).put(optW.get(), new ArrayList<>());
//                }
//                atcCode = atcCode.substring(0, atcCode.length() - 1);
//            }
//
//            for(Medikament s : possibleSubstitutes){
//                boolean exit = false;
//                for(WirkstoffAtcCode wirkstoff : dimensions){
//                    for(Wirkstoff wirkstoffInformation : s.getWirkstoffe()){
//                        if(wirkstoffInformation.getPharWirkstoff().getAtcCode().contains(wirkstoff.getAtcCode()) || (wirkstoffInformation.getWirkWirkstoff() != null && wirkstoffInformation.getWirkWirkstoff().getAtcCode().contains(wirkstoff.getAtcCode()))){
//                            exit = true;
//                            substituteMap.get(w).get(wirkstoff).add(s);
//                            break;
//                        }
//                    }
//                    if(exit) break;
//                }
//            }
//
//        }
//
//        return substituteMap;
//    }


}
