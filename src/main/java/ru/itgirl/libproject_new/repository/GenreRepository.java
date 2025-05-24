package ru.itgirl.libproject_new.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itgirl.libproject_new.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}