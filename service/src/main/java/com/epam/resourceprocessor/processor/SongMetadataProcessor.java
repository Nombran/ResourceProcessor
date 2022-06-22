package com.epam.resourceprocessor.processor;

import com.epam.songservice.dto.SongMetadataDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

import static com.epam.resourceprocessor.processor.MetadataProperty.ALBUM;
import static com.epam.resourceprocessor.processor.MetadataProperty.ARTIST;
import static com.epam.resourceprocessor.processor.MetadataProperty.DURATION;
import static com.epam.resourceprocessor.processor.MetadataProperty.RELEASE_YEAR;
import static com.epam.resourceprocessor.processor.MetadataProperty.TITLE;


/**
 * @author www.epam.com
 */
@Slf4j
@Component
public class SongMetadataProcessor {

    @SneakyThrows
    public void processSongMetadata() {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        val parseContext = new ParseContext();

        //Mp3 parser
        val mp3Parser = new Mp3Parser();
        mp3Parser.parse(loadSong(), handler, metadata, parseContext);

        var stringDuration = metadata.get(DURATION.getName());
        val metadataDto = SongMetadataDto.builder()
                .album(metadata.get(ALBUM.getName()))
                .artist(metadata.get(ARTIST.getName()))
                .duration(parseSongDuration(stringDuration))
                .name(metadata.get(TITLE.getName()))
                .year(Integer.parseInt(metadata.get(RELEASE_YEAR.getName())))
                .resourceId(1L)
                .build();
        log.info(metadataDto.toString());
    }

    private String parseSongDuration(String stringDuration) {
        val durationInSeconds = stringDuration.substring(0, stringDuration.indexOf('.'));
        val intSeconds = Integer.parseInt(durationInSeconds);
        return intSeconds/60 + ":" + intSeconds%60;
    }

    @SneakyThrows
    private FileInputStream loadSong() {
        val classLoader = getClass().getClassLoader();
        val file = new File(classLoader.getResource("Lose_Yourself.mp3").getFile());
        return new FileInputStream(file);
    }
}
