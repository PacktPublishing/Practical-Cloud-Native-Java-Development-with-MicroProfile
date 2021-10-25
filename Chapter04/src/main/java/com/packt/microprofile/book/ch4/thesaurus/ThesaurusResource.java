package com.packt.microprofile.book.ch4.thesaurus;

import com.packt.microprofile.book.ch4.client.ThesaurusClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.PathParam;
import java.util.*;

@RestClient
public class ThesaurusResource implements ThesaurusClient {
    static Map<String, List<String>> map = new HashMap<>();

    @PathParam("word")
    String word;

    @Override
    public String getSynonymsFor(String word) throws NoSuchWordException {
        List<String> synonyms = map.get(word);
        if (synonyms == null)
            throw new NoSuchWordException(word);
        return String.join(",", synonyms);
    }

    @Override
    public String setSynonymsFor(String word, String synonyms) throws WordAlreadyExistsException {
        List<String> synonymList = new ArrayList<>(Arrays.asList(synonyms.split(",")));
        if (null != map.putIfAbsent(word, synonymList))
            throw new WordAlreadyExistsException(word);
        return String.join(",", synonyms);
    }

    @Override
    public String updateSynonymsFor(String word, String synonyms) throws NoSuchWordException {
        List<String> synonymList = Arrays.asList(synonyms.split(","));
        if (null == map.replace(word, synonymList))
            throw new NoSuchWordException(word);
        return String.join(",", synonyms);
    }

    @Override
    public boolean deleteSynonyms(String word) throws NoSuchWordException {
        if (null == map.remove(word))
            throw new NoSuchWordException(word);
        return true;
    }

    @Override
    public String addNewSynonymsFor(String word, String newSynonyms) throws NoSuchWordException {
        List<String> synonyms = map.get(word);
        if (synonyms == null)
            throw new NoSuchWordException(word);
        synonyms.addAll(Arrays.asList(newSynonyms.split(",")));
        return String.join(",", synonyms);
    }
}