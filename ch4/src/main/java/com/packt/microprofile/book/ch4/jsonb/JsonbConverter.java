package com.packt.microprofile.book.ch4.jsonb;

import java.io.StringWriter;
import java.util.Collections;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import com.packt.microprofile.book.ch4.jsoncommon.Starship;
import com.packt.microprofile.book.ch4.jsoncommon.Weapon;

public class JsonbConverter {

    public static void main(String[] args) throws Throwable {
        Weapon w = new Weapon();
        w.setName("Quad Blaster Turret");
        w.setType("Laser");
        w.setDamageRating(24);

        Starship ship = new Starship();
        ship.setName("Coreillian Freighter");
        ship.setHasHyperdrive(true);
        ship.setSpeedRating(22);
        ship.setWeapons(Collections.singletonList(w));

        StringWriter sw = new StringWriter();
        Jsonb jsonb = JsonbBuilder.create();
        jsonb.toJson(ship, sw);
        String json = sw.getBuffer().toString();
        System.out.println(json);

        sw = new StringWriter();
        jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
        jsonb.toJson(ship, sw);
        json = sw.getBuffer().toString();
        System.out.println(json);

        Starship shipFromJson = jsonb.fromJson(json, Starship.class);
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

        Person p = new Person();
        p.setFirstName("John");
        p.setMiddleName("Tiberius");
        p.setLastName("Doe");
        p.setFavoriteColor("Green");
        p.setAge(25);
        String jsonPerson = jsonb.toJson(p);
        System.out.println(jsonPerson);
    
        try {
            Person p2 = jsonb.fromJson(jsonPerson, Person.class);
            System.out.println(p2.getFirstName());
            System.out.println(p2.getMiddleName());
            System.out.println(p2.getLastName());
            System.out.println(p2.getFavoriteColor());
            System.out.println(p2.getAge());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
