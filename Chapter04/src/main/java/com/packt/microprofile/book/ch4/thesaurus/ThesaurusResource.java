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
    public synchronized String get() throws NoSuchWordException {
        List<String> synonyms = map.get(word);

        String result = "[map=" + map + "=, word=" + word  + "=, synonyms=" + synonyms + "=]";

        if (synonyms == null) {
            Exception ee = new NoSuchWordException(word);
            result += " [ee=" + ee + "=]";
            System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.get() [result=" + result + "=]");

            throw new NoSuchWordException(word);
        }

        System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.get() [result=" + result + "=]");

        return String.join(",", synonyms);
    }

    @POST
    public synchronized String post(String synonyms) throws WordAlreadyExistsException {
        List<String> synonymList = new ArrayList<>(Arrays.asList(synonyms.split(",")));

        String result = "[map=" + map + "=, word=" + word  + "=, synonyms=" + synonyms  + "=, synonymList=" + synonymList + "=]";

        if (null != map.putIfAbsent(word, synonymList)) {
            Exception ee = new WordAlreadyExistsException(word);
            result += " [ee=" + ee + "=]";
            System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.post(synonyms=" + synonyms + "=) [result=" + result + "=]");

            throw new WordAlreadyExistsException(word);
        }

        System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.post(synonyms=" + synonyms + "=) [result=" + result + "=]");

        return String.join(",", synonyms);
    }

    @PUT
    public synchronized String put(String synonyms) throws NoSuchWordException {
        List<String> synonymList = Arrays.asList(synonyms.split(","));

        String result = "[map=" + map + "=, word=" + word  + "=, synonyms=" + synonyms  + "=, synonymList=" + synonymList + "=]";

        if (null == map.replace(word, synonymList)) {
            Exception ee = new NoSuchWordException(word);
            result += " [ee=" + ee + "=]";
            System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.put(synonyms=" + synonyms + "=) [result=" + result + "=]");

            throw new NoSuchWordException(word);
        }

        System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.put(synonyms=" + synonyms + "=) [result=" + result + "=]");

        return String.join(",", synonyms);
    }

    @DELETE
    public synchronized boolean delete() throws NoSuchWordException {
        String result = "[map=" + map + "=, word=" + word  + "=]";

        if (null == map.remove(word)) {
            Exception ee = new NoSuchWordException(word);
            result += " [ee=" + ee + "=]";
            System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.delete() [result=" + result + "=]");

            throw new NoSuchWordException(word);
        }
        result += " [result=true]";
        System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.delete() [result=" + result + "=]");

        return true;
    }

    @PATCH
    public synchronized String patch(String newSynonyms) throws NoSuchWordException {
        List<String> synonyms = map.get(word);

        String result = "[map=" + map + "=, word=" + word  + "=, newSynonyms=" + newSynonyms  + "=, synonyms=" + synonyms + "=]";

        if (synonyms == null) {
            Exception ee = new NoSuchWordException(word);
            result += " [ee=" + ee + "=]";
            System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.patch(newSynonyms=" + newSynonyms + "=) [result=" + result + "=]");

            throw new NoSuchWordException(word);
        }
        synonyms.addAll(Arrays.asList(newSynonyms.split(",")));

        result = "[map=" + map + "=, word=" + word  + "=, newSynonyms=" + newSynonyms  + "=, synonyms=" + synonyms + "=]";
        System.out.println("com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.patch(newSynonyms=" + newSynonyms + "=) [result=" + result + "=]");

        return String.join(",", synonyms);
    }
}