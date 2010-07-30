package org.mcbain.activerecord;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

@Test
public class ScribbleTest {

    @BeforeMethod
    public void setupEngine() {
        ActiveRecord.engine(new MemoryEngine());
    }

    public void shouldStoreRecord() {
        Person person = new Person("danny", "dynamite");
        person.store();

        assertEquals(person.id(), 1);
    }

    public void shouldFetchRecordById() {
        Person person = new Person("danny", "dynamite");
        person.store();

        Person stored = Person.find().byId(person.id());
        assertEquals(stored, person);
    }

    public void shouldUpdateRecord() {
        Person person = new Person("danny", "dynamite");
        person.store();

        Person stored = Person.find().byId(person.id());
        stored.setForename("forename");
        stored.store();

        assertEquals(stored.id(), person.id());
    }

    public void shouldFetchAll() {
        Person person1 = new Person("danny", "dynamite");
        person1.store();

        Person person2 = new Person("nitro", "glycerene");
        person2.store();

        List<Person> people = Person.find().all();
        assertEquals(people, asList(person1, person2));
    }

    public void shouldFetchBySurname() {
        Person person = new Person("danny", "dynamite");
        person.store();

        List<Person> people = Person.find().bySurname("dynamite");
        assertEquals(people, asList(person));
    }
}
