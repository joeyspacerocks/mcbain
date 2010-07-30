package org.mcbain.activerecord;

import java.util.List;

public class Person extends ActiveRecord<Person> {

    private String forename;
    private String surname;

    public Person() {
    }

    public Person(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static PersonFinder find() {
        return new PersonFinder();
    }

    public static class PersonFinder extends Finder<Person> {
        public List<Person> bySurname(String surname) {
            return where().eq("surname", surname).toList();
        }
    }
}
