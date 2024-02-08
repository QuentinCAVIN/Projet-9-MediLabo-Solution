package com.projet9.diabetesassessmentservice.service;

import com.projet9.diabetesassessmentservice.dto.NoteDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TriggerCounter {

    // Ajouter ici les nouveaux déclencheurs en minuscule
    List<String> triggers = new ArrayList<>();
    {
        triggers.add(("Hémoglobine A1C").toLowerCase());
        triggers.add(("Microalbumine").toLowerCase());
        triggers.add(("Taille").toLowerCase());
        triggers.add(("Poids").toLowerCase());
        triggers.add(("Fume").toLowerCase());
        triggers.add(("Anormal").toLowerCase());
        triggers.add(("Cholestérol").toLowerCase());
        triggers.add(("Vertiges").toLowerCase());
        triggers.add(("Rechute").toLowerCase());
        triggers.add(("Réaction").toLowerCase());
        triggers.add(("Anticorps").toLowerCase());
    }

    public int countTriggers(List<NoteDto> noteDto) {
        //Collecte toutes les notes dans une List<String> en minuscule
        List<String> notes = getNotesInStringAndInLowercase(noteDto);

        Set<String> triggersInPatientNotes = new HashSet<>();

        //Chaque trigger qui apparait au moins une fois est ajouté à mon set
        for (String trigger : triggers) {
            if (notes.stream().anyMatch(note -> note.contains(trigger))) {
                triggersInPatientNotes.add(trigger);
            }
        }
        return Long.valueOf( triggersInPatientNotes.stream().count()).intValue();
    }
        // Je n'ai pas utilisé un "int count" pour pouvoir faire évoluer la fonctionnalité

    private List<String> getNotesInStringAndInLowercase(List<NoteDto> noteDto) {
        //Collecte toutes les notes dans une List<String> en minuscule
        return noteDto
                .stream()
                .map(NoteDto::getNote)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}