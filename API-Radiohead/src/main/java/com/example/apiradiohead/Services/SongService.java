package com.example.apiradiohead.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.apiradiohead.Repositories.SongRepository;
import com.example.apiradiohead.Entities.SongEntity;

import java.util.*;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public ResponseEntity<?> getAllSongs(Pageable pageable) {
        Page<SongEntity> songs = songRepository.findAll(pageable);
        return getResponseEntity(songs);
    }

    public ResponseEntity<?> getSongById(UUID id) {
        Optional<SongEntity> song = songRepository.findById(id);
        if (song.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Status", String.format("Canción con ID %s no encontrada.", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(Collections.singletonMap("Canción", song.get()));
    }

    public ResponseEntity<?> getSongsByTitle(String title, Pageable pageable) {
        Page<SongEntity> songs = songRepository.findAllByTitleContaining(title, pageable);
        return getResponseEntity(songs);
    }

    private ResponseEntity<?> getResponseEntity(Page<SongEntity> songs) {
        Map<String, Object> response = new HashMap<>();
        response.put("TotalElements", songs.getTotalElements());
        response.put("TotalPages", songs.getTotalPages());
        response.put("CurrentPage", songs.getNumber());
        response.put("NumberOfElements", songs.getNumberOfElements());
        response.put("Songs", songs.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addSong(SongEntity songToAdd) {
        Page<SongEntity> songs = songRepository.findAllByTitleContaining(
                songToAdd.getTitle(),
                Pageable.unpaged());
        if (songs.getTotalElements() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("La canción ya existe con %d coincidencias.", songs.getTotalElements())), HttpStatus.CONFLICT);
        } else {
            SongEntity savedSong = songRepository.save(songToAdd);
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Canción añadida con ID %s", savedSong.getId())), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> updateSong(UUID id, SongEntity songToUpdate) {
        Optional<SongEntity> song = songRepository.findById(id);
        if (song.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Canción con ID %s no encontrada.", id)), HttpStatus.NOT_FOUND);
        }
        SongEntity existingSong = song.get();

        existingSong.setTitle(songToUpdate.getTitle());
        existingSong.setAlbum(songToUpdate.getAlbum());
        existingSong.setReleaseYear(songToUpdate.getReleaseYear());
        existingSong.setDuration(songToUpdate.getDuration());
        existingSong.setGenre(songToUpdate.getGenre());
        existingSong.setLyrics(songToUpdate.getLyrics());
        existingSong.setComposer(songToUpdate.getComposer());

        songRepository.save(existingSong);

        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Canción actualizada con ID %s", existingSong.getId())));
    }

    public ResponseEntity<?> deleteSong(UUID id) {
        Optional<SongEntity> song = songRepository.findById(id);
        if (song.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Canción con ID %s no existe.", id)), HttpStatus.NOT_FOUND);
        }
        songRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Canción eliminada con ID %s", id)));
    }
}
