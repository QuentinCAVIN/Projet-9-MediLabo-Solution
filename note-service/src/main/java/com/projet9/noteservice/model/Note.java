package com.projet9.noteservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    private int patId;

    private String patient;

    private String note;

    //Si vous souhaitez définir un nom différent pour la caractéristique du document, vous pouvez utiliser l’annotation @Field(“nom”), où nom peut être remplacé par la valeur de votre choix.
    //Pour exclure l’un des attributs de la classe afin qu’il ne soit pas interprété comme faisant partie de la structure du document, vous pouvez utiliser l’annotation @Transient.

}