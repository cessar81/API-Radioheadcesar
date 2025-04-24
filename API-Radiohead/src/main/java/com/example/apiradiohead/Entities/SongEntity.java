package com.example.apiradiohead.Entities;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    @NotBlank(message = "El título de la canción es obligatorio")
    @Size(min = 1, max = 200, message = "El título debe tener entre 1 y 200 caracteres")
    private String title;

    @JsonProperty("album")
    @NotBlank(message = "El nombre del álbum es obligatorio")
    private String album;

    @JsonProperty("releaseYear")
    @Min(value = 1980, message = "El año de lanzamiento debe ser válido")
    private int releaseYear;

    @JsonProperty("duration")
    @Min(value = 1, message = "La duración debe ser en segundos y mayor a 0")
    private int duration;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("lyrics")
    private String lyrics;

    @JsonProperty("composer")
    private String composer;

    @Override
    public String toString() {
        return "SongEntities{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", releaseYear=" + releaseYear +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", composer='" + composer + '\'' +
                '}';
    }
}
