package com.foya;


import java.io.*;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;

import static com.sun.org.apache.xerces.internal.xinclude.XIncludeHandler.BUFFER_SIZE;

/**
 * Created by tommy on 2016/9/21.
 */
public class UnZipFileByFolderName {

    private static final int BUFFER_SIZE = 4096;
    private static String dir = "D:/convert";

    public static void main(String[] args) throws IOException {
        Path rootPath = Paths.get(dir);


        Files.list(rootPath).forEach(path -> {
            if (Files.isDirectory(path)) {

                Path unZipPdfFileName = path.getFileName();


                try {
                    Path zipFile = Files.list(path).collect(Collectors.toList()).get(0);
                    System.out.println(zipFile);
                    ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(zipFile));


                    extractFile(zipIn, dir + File.separator + unZipPdfFileName + ".pdf");

                    zipIn.close();

                    Files.deleteIfExists(zipFile);
                    Files.deleteIfExists(path);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        });

    }


    /**
     * Extracts a zip entry (file entry)
     *
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
