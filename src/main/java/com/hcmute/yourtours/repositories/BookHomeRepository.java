package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.BookHomesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookHomeRepository extends JpaRepository<BookHomesCommand, Long> {
    Optional<BookHomesCommand> findByBookId(UUID bookId);
}
