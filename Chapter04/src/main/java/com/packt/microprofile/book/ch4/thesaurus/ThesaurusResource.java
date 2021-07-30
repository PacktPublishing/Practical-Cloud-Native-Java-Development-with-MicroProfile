package com.packt.microprofile.book.ch4.thesaurus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path ("/thesaurus/{word}")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class ThesaurusResource {
    static Map<String, List<String>> map = new HashMap<>();

    @PathParam("word")
    String word;

    @GET
    public String get() throws NoSuchWordException {
        List<String> synonyms = map.get(word);
        if (synonyms == null)
            throw new NoSuchWordException(word);
        return String.join(",", synonyms);
    }

    @POST
    public String post(String synonyms) throws WordAlreadyExistsException {
        List<String> synonymList = new ArrayList<>(Arrays.asList(synonyms.split(",")));
        if (null != map.putIfAbsent(word, synonymList))
            throw new WordAlreadyExistsException(word);
        return String.join(",", synonyms);
    }

    @PUT
    public String put(String synonyms) throws NoSuchWordException {
        List<String> synonymList = Arrays.asList(synonyms.split(","));
        if (null == map.replace(word, synonymList))
            throw new NoSuchWordException(word);
        return String.join(",", synonyms);
    }

    @DELETE
    public boolean delete() throws NoSuchWordException {
        if (null == map.remove(word))
            throw new NoSuchWordException(word);
        return true;
    }

    @PATCH
    public String patch(String newSynonyms) throws NoSuchWordException {
        List<String> synonyms = map.get(word);
        if (synonyms == null)
            throw new NoSuchWordException(word);
        synonyms.addAll(Arrays.asList(newSynonyms.split(",")));
        return String.join(",", synonyms);
    }
}