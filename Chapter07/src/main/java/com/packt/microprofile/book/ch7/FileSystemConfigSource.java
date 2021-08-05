package com.packt.microprofile.book.ch7;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class FileSystemConfigSource implements ConfigSource {
    private File dir = new File("secrets");

    @Override
    public Set<String> getPropertyNames() {
        Set<String> props = Arrays.asList(dir.listFiles()).stream().map(f -> f.getName()).collect(Collectors.toSet());

        return props;
    }

    @Override
    public String getValue(String s) {
        File f = new File(dir, s);
        try {
            return new String(Files.readAllBytes(new File(dir, s).toPath()), StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            return null;
        }
    }

    @Override
    public String getName() {
        return "kube.secret";
    }

    @Override
    public int getOrdinal() {
        return 5;
    }
}
