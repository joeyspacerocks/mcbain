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
