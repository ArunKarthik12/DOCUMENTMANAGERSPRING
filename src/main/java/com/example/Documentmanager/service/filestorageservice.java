package com.example.Documentmanager.service;

import java.io.IOException;
//import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

//import com.example.Documentmanager.model.doc;
import com.example.Documentmanager.model.filedb;
import com.example.Documentmanager.repository.filedbrepo;
//import com.example.Documentmanager.repository.docrepo;
//import org.apache.commons.io.FilenameUtils;
//String fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithExt);
@Service
public class filestorageservice {
  @Autowired
  private filedbrepo fileDBRepository;
  
  
  public filedb store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    filedb FileDB = new filedb(fileName, file.getContentType(), file.getBytes());
    return fileDBRepository.save(FileDB);
  }
  

  
  
  
  public filedb getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<filedb> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
  
  public Stream<filedb> deleteFileById(String id) {
	  fileDBRepository.deleteById(id);
	  return fileDBRepository.findAll().stream();
	}
  
  
  
  public String deleteAllFiles() {
		String result = "";
		try {	
			fileDBRepository.deleteAll();
			result = "all records deleted";
		}
		catch(Exception e) {
			result = "error occurred";
		}
		
		return result;
	}
}