package com.projet9.diabetesassessmentservice.service;

import com.projet9.diabetesassessmentservice.dto.NoteDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TriggerCounterTest {

    private TriggerCounter triggerCounterUnderTest = new TriggerCounter();

    @Nested
    @Tag("countTriggersTest")
    public class countTriggersTest {
        @Test
        public void ZeroTriggerWord() {
            List<NoteDto> notes = setNotes("Le sucre c'est génial");

            int count = triggerCounterUnderTest.countTriggers(notes);

            Assertions.assertThat(count).isEqualTo(0);
        }

        @Test
        public void OneTriggerWordInLowerAndUpperCase() {
            List<NoteDto> notes = setNotes("Anticorps");

            int count = triggerCounterUnderTest.countTriggers(notes);

            Assertions.assertThat(count).isEqualTo(1);
        }

        @Test
        public void OneTriggerWordInUpperCase() {
            List<NoteDto> notes = setNotes("ANTICORPS");

            int count = triggerCounterUnderTest.countTriggers(notes);

            Assertions.assertThat(count).isEqualTo(1);
        }

        @Test
        public void OneTriggerWordInLowerCase() {
            List<NoteDto> notes = setNotes("anticorps");

            int count = triggerCounterUnderTest.countTriggers(notes);

            Assertions.assertThat(count).isEqualTo(1);
        }

        @Test
        public void OneTriggerWordSeveralTimes() {
            List<NoteDto> notes = setNotes("Anticorps, ANTICORPS, anticorps");

            int count = triggerCounterUnderTest.countTriggers(notes);

            Assertions.assertThat(count).isEqualTo(1);
        }

        @Test
        public void AllTriggerWords() {
            List<NoteDto> notes = setNotes("Hémoglobine A1C , Microalbumine, Taille, Poids, Fumeur, Fumeuse, Anormal" +
                    "Cholestérol, Vertiges, Rechute, Réaction, Anticorps");

            int count = triggerCounterUnderTest.countTriggers(notes);

            Assertions.assertThat(count).isEqualTo(11);
        }
    }


    ///////////////////// SET UP //////////////////////////////
    public List<NoteDto> setNotes(String content) {
        NoteDto note = new NoteDto();
        note.setNote(content);
        return List.of(note);
    }
}
