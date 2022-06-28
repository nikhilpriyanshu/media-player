package com.nikhil.mediaplayer.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.mediaplayer.services.MediaPlayerService;

@RequestMapping("/")
@RestController
public class Controller {

    @Autowired
    private MediaPlayerService mediaPlayerService;

    @GetMapping
    public ResponseEntity<List<String>> getAllSongs() {
        List<String> songList = mediaPlayerService.getAllSongs();
        return new ResponseEntity<List<String>>(songList, HttpStatus.OK);
    }

    @GetMapping("/song")
    public String getSongLink(@PathParam(value = "songName") String songName) {
        String songLink = mediaPlayerService.getSongLink(songName);
        return songLink;
    }
}
