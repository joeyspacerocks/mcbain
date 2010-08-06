/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
