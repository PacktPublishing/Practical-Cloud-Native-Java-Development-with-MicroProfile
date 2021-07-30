package com.packt.microprofile.book.ch4.jsonp;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import com.packt.microprofile.book.ch4.jsoncommon.Starship;
import com.packt.microprofile.book.ch4.jsoncommon.Weapon;

public class JsonpStreamingConverter {

    public Starship convertFromJson(String json) {
        Starship ship = new Starship();
        JsonParser parser = Json.createParser(new StringReader(json));
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == Event.KEY_NAME) {
                String keyName = parser.getString();
                parser.next();
                switch(keyName) {
                    case "name": ship.setName(parser.getString()); break;
                    case "hasHyperdrive": ship.setHasHyperdrive(parser.getValue().equals(JsonValue.TRUE)); break;
                    case "speedRating": ship.setSpeedRating(parser.getInt()); break;
                    case "weapons": ship.setWeapons(getWeaponsFromParser(parser)); break;
                }
            }
        }
        return ship;
    }

    private List<Weapon> getWeaponsFromParser(JsonParser parser) {
        List<Weapon> weapons = new ArrayList<>();
        Event event = null;
        while ((event = parser.next()) != Event.END_ARRAY) {
            Weapon w = new Weapon();
            while (event != Event.END_OBJECT) {

                if (event == Event.KEY_NAME) {
                    String keyName = parser.getString();
                    parser.next();
                    switch(keyName) {
                        case "name": w.setName(parser.getString()); break;
                        case "type": w.setType(parser.getString()); break;
                        case "damageRating": w.setDamageRating(parser.getInt()); break;
                    }
                }
                event = parser.next();
            }
            weapons.add(w);
        }
        return weapons;
    }

    public String convertToJson(Starship ship) {
        StringWriter sw = new StringWriter();
        JsonGenerator generator = Json.createGenerator(sw);
        generator.writeStartObject()
                 .write("name", ship.getName())
                 .write("hasHyperdrive", ship.isHasHyperdrive())
                 .write("speedRating", ship.getSpeedRating())
                 .writeStartArray("weapons");
        for (Weapon w : ship.getWeapons()) {
            generator.writeStartObject()
                     .write("name", w.getName())
                     .write("type", w.getType())
                     .write("damageRating", w.getDamageRating())
                     .writeEnd();
        }
        generator.writeEnd()
                 .writeEnd();
        generator.close();

        return sw.toString();
    }

    public static void main(String[] args) {
        Weapon w = new Weapon();
        w.setName("Quad Blaster Turret");
        w.setType("Laser");
        w.setDamageRating(24);

        Weapon w2 = new Weapon();
        w2.setName("Proton Torpedoes");
        w2.setType("Missile");
        w2.setDamageRating(76);

        Starship ship = new Starship();
        ship.setName("Coreillian Freighter");
        ship.setHasHyperdrive(true);
        ship.setSpeedRating(22);
        ship.setWeapons(Arrays.asList(w, w2));

        JsonpStreamingConverter converter = new JsonpStreamingConverter();
        String json = converter.convertToJson(ship);
        System.out.println(json);

        Starship shipFromJson = converter.convertFromJson(json);
        System.out.println("name: " + shipFromJson.getName());
        System.out.println("hasHyperdrive: " + shipFromJson.isHasHyperdrive());
        System.out.println("speedRating: " + shipFromJson.getSpeedRating());
        System.out.println("weapons:");
        for (Weapon weapon : shipFromJson.getWeapons()) {
            System.out.println("  name: " + weapon.getName());
            System.out.println("  type: " + weapon.getType());
            System.out.println("  damageRating: " + weapon.getDamageRating());
            System.out.println();
        }
    }
}
