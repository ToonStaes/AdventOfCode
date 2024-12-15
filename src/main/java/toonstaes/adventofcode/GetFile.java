package toonstaes.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class GetFile {
    public static File getFile(String filename ) throws URISyntaxException {
        URL resource = AdventOfCodeApplication.class.getClassLoader().getResource( filename );
        File file = null;
        if ( resource == null ) {
            throw new IllegalArgumentException( "file not found!" );
        } else {
            file = new File( resource.toURI() );
        }
        return file;
    }

    public static List<String> readLines(File file) throws IOException {
        BufferedReader br = new BufferedReader( new FileReader( file ) );
        List<String> lines = new ArrayList<>();
        String st;

        while (  (st = br.readLine()) != null ){
            lines.add(st);
        }
        return lines;
    }
}
