package com.example.apiradiohead.Repositories;

import com.example.apiradiohead.Entities.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, UUID> {

    Page<SongEntity> findAllByTitleContaining(String title, Pageable pageable);

    @Override
    Page<SongEntity> findAll(Pageable pageable);
}
