package com.example.Documentmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Documentmanager.model.doc;
//import com.example.Documentmanager.model.filedb;

@Repository
public interface docrepo extends JpaRepository<doc, String> {

}