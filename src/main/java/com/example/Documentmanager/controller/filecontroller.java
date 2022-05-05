package com.example.Documentmanager.controller;

//import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.Documentmanager.message.responsemessage;
//import com.example.DocumentManager.service.fileStorageService;
import com.example.Documentmanager.service.filestorageservice;
import com.example.Documentmanager.model.doc;
import com.example.Documentmanager.message.responsefile;
//import com.example.Documentmanager.message.responsemessage;
import com.example.Documentmanager.model.filedb;
import com.example.Documentmanager.repository.docrepo;

@RestController
@CrossOrigin("http://localhost:4200")
public class filecontroller {
@Autowired
private filestorageservice storageService;
private docrepo docrepo;
String f_id;

@PostMapping("/upload")
public ResponseEntity<responsemessage> uploadFile(@RequestParam("file") MultipartFile file) {
   String message = "";
  try {
    storageService.store(file);
//    message = "Uploaded the file successfully: " + file.getOriginalFilename();
    message = "Uploaded the file successfully";
    return ResponseEntity.status(HttpStatus.OK).body(new responsemessage(message));
  } catch (Exception e) {
//    message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      message = "Could not upload the file !";
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new responsemessage(message));
  }
}

@PostMapping("/upload/doc")
public ResponseEntity<responsemessage> docAdd(@RequestBody doc newDoc) {
   String message = "";
  try {
//    storageService.storedoc(newDoc);
//    message = "Uploaded the file successfully: " + file.getOriginalFilename();
//  	Doc doc = new Doc(newDoc.getName(),newDoc.getDesc());
	    docrepo.save(newDoc);
    message = "Uploaded the file successfully";
    return ResponseEntity.status(HttpStatus.OK).body(new responsemessage(message));
  } catch (Exception e) {
//    message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      message = "Could not upload the file !";
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new responsemessage(message));
  }
}

@GetMapping("/files")
public ResponseEntity<List<responsefile>> getListFiles() {
  List<responsefile> files = storageService.getAllFiles().map(dbFile -> {	
    String fileDownloadUri = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/files/")
        .path(dbFile.getId())
        .toUriString();
    
//         String f_id = dbFile.getId();
//         System.out.println(f_id);
    
String fileName = dbFile.getName();
  int pos = fileName.lastIndexOf(".");
  if (pos > 0 && pos < (fileName.length() - 1)) { 
      fileName = fileName.substring(0, pos);
  }
    
    return new responsefile(
  	  dbFile.getId(),
//        dbFile.getName(),
  	  fileName,
        fileDownloadUri,
        dbFile.getType(),
        dbFile.getData().length);
  }).collect(Collectors.toList());
 
  return ResponseEntity.status(HttpStatus.OK).body(files);
}


@GetMapping("/files/{id}")
public ResponseEntity<byte[]> getFile(@PathVariable String id) {
  filedb fileDB = storageService.getFile(id);
  return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
      .body(fileDB.getData());
}
@DeleteMapping("/deleteall")
public ResponseEntity<String> deleteStatus() {
	 String status = storageService.deleteAllFiles(); 
	 return ResponseEntity.status(HttpStatus.OK).body(status);
}

@DeleteMapping("/deleteById/{id}")
public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable String id) {
  try {
  	storageService.deleteFileById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}



}