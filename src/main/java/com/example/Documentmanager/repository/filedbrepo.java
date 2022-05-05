package com.example.Documentmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Documentmanager.model.filedb;

@Repository
public interface filedbrepo extends JpaRepository<filedb, String> {
}
