package com.nikhil.mediaplayer.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MediaPlayerService {
    Logger log = LoggerFactory.getLogger(MediaPlayerService.class);

    private List<String> songList = new ArrayList<String>();

    @Value("${song.directory.context-path}")
    private String songDirectory;

    @Value("${song.directory.path}")
    private String songDirectoryPath;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        File songDirectoryFile = new File(songDirectory);
        if (!songDirectoryFile.isDirectory()) {
            log.error("Song directoty expected. Not a directory: {}", songDirectoryFile.getAbsolutePath());
        } else {
            log.info("Selected song direcory: {}", songDirectoryFile.getAbsolutePath());
            File[] files = songDirectoryFile.listFiles();
            if (Objects.isNull(files)) {
                log.error("Error reading song direcory: {}", songDirectoryFile.getAbsolutePath());
            } else {
                Arrays.stream(files).filter(file -> file.isFile())
                        .forEach(file -> songList.add(file.getAbsolutePath()));
            }
        }
        log.info("{} started", this.getClass().getName());
    }

    @EventListener(ContextClosedEvent.class)
    public void onContextClosed() {
        log.info("{} stopped", this.getClass().getName());
    }

    public List<String> getAllSongs() {
        if (Objects.isNull(songList)) {
            log.error("Invalid song list");
            return Collections.emptyList();
        }
        return this.songList;
    }

    public String getSongLink(String songName) {
        if (Objects.isNull(songList)) {
            log.error("Invalid song list");
            return "";
        }
        return songList.stream().filter(file -> file.equals(songName))
                .map(file -> file.replace(songDirectory, songDirectoryPath)).findFirst().orElse("");
    }
}