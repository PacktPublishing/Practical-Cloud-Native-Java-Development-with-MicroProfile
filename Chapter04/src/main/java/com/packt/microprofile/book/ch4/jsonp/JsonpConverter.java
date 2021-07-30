package com.packt.microprofile.book.ch4.jsonp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import com.packt.microprofile.book.ch4.jsoncommon.Starship;
import com.packt.microprofile.book.ch4.jsoncommon.Weapon;


public class JsonpConverter {
    
    public Starship convertFromJson(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject shipObject = reader.readObject();
        Starship ship = new Starship();
        ship.setName(shipObject.getString("name"));
        ship.setHasHyperdrive(shipObject.getBoolean("hasHyperdrive"));
        ship.setSpeedRating(shipObject.getInt("speedRating"));
        JsonArray weaponsArray = shipObject.getJsonArray("weapons");
        List<Weapon> weapons = new ArrayList<>();
        Weapon weapon;
        for (int i=0; i<weaponsArray.size(); i++) {
            JsonObject weaponObject = weaponsArray.getJsonObject(i);
            weapon = new Weapon();
            weapon.setName(weaponObject.getString("name"));
            weapon.setType(weaponObject.getString("type"));
            weapon.setDamageRating(weaponObject.getInt("damageRating"));
            weapons.add(weapon);
        }
        ship.setWeapons(weapons);

        return ship;
    }

    public String convertToJson(Starship ship) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder weaponBuilder;
        for (Weapon w : ship.getWeapons()) {
            weaponBuilder = Json.createObjectBuilder();
            weaponBuilder.add("name", w.getName());
            weaponBuilder.add("type", w.getType());
            weaponBuilder.add("damageRating", w.getDamageRating());
            arrayBuilder.add(weaponBuilder.build());
        }

        JsonObjectBuilder shipBuilder = Json.createObjectBuilder();
        shipBuilder.add("name", ship.getName());
        shipBuilder.add("hasHyperdrive", ship.isHasHyperdrive());
        shipBuilder.add("speedRating", ship.getSpeedRating());
        shipBuilder.add("weapons", arrayBuilder.build());

        return shipBuilder.build().toString();
    }

    public static void main(String[] args) {
        Weapon w = new Weapon();
        w.setName("Quad Blaster Turret");
        w.setType("Laser");
        w.setDamageRating(24);

        Starship ship = new Starship();
        ship.setName("Coreillian Freighter");
        ship.setHasHyperdrive(true);
        ship.setSpeedRating(22);
        ship.setWeapons(Collections.singletonList(w));

        JsonpConverter converter = new JsonpConverter();
        String json = converter.convertToJson(ship);
        System.out.println(json);

        Starship shipFromJson = converter.convertFromJson(json);
        System.out.println("name: " + shipFromJson.getName());
        System.out.println("hasHyperdrive: " + shipFromJson.isHasHyperdrive());
        System.out.println("speedRating: " + shipFromJson.getSpeedRating());
        System.out.println("weapons:");
        for (Weapon w2 : shipFromJson.getWeapons()) {
            System.out.println("  name: " + w2.getName());
            System.out.println("  type: " + w2.getType());
            System.out.println("  damageRating: " + w2.getDamageRating());
        }
    }
}
