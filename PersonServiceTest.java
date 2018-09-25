package com.objectfrontier.training.java.jdbc;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Lokesh.
 * @since Sep 24, 2018
 */
public class PersonServiceTest {

    private PersonService personService;
    @BeforeClass
    private void initClass() {

        personService = new PersonService();
    }

    @Test (dataProvider = "positiveCase_insertRecord")
    private void insertRecordTest_positive(int expectedResult, Person person) {
        int actualResult = 0;
        try {
            actualResult = personService.insertRecord(person);
            Assert.assertEquals(actualResult, expectedResult);
        } catch (Exception e) {
            Assert.fail(MessageFormat.format("UnExpected behaviour {0} for input {1}", e.getMessage(), person));
        }
    }

//    @Test (dataProvider = "positiveCase_read")
//    private void readTest_positive(Person expectedResult, int id, boolean includeAddress) {
//        Person actualResult = null;
//        try {
//            actualResult = personService.read(id, includeAddress);
//            Assert.assertEquals(actualResult, expectedResult);
//        } catch (Exception e) {
//            Assert.fail(MessageFormat.format("UnExpected behaviour {0} for id = {1}, includeAddress = {2}",
//                                                e.getMessage(),
//                                                id,
//                                                includeAddress));
//        }
//    }

//    @Test
//    private void updateRecordTest_positive(int expectedResult, int id, Person person) {
//        int actualResult = 0;
//        try {
//            actualResult = personService.update(id, person);
//            Assert.assertEquals(actualResult, expectedResult);
//        } catch (Exception e) {
//            Assert.fail(MessageFormat.format("UnExpected behaviour {0} for id = {1}, includeAddress = {2}",
//                                                e.getMessage(), id, person));
//        }
//    }

    @DataProvider (name = "positiveCase_insertRecord")
    private Object[][] insertRecordTest_positiveDP() {
        Address address = new Address("hassan khan street", "Chittoor", 517001);
        return new Object[][] {
            {2, new Person("R.Boovan", "boovanNaik@gmail.com", address, Person.getDate(1997, 1, 1))},
            {3, new Person("R.Rajendran", "RajendranNaikar@gmail.com", null, Person.getDate(1997, 1, 1))},
            {4, new Person("R.BoovanClone", "boovanNaiker@gmail.com", address, Person.getDate(1997, 1, 1))}
        };
    }

    @DataProvider (name = "positiveCase_read")
    private Object[][] readTest_positiveDP() {
        Address address = new Address("hassan khan street", "Chittoor", 517001);
        address.setId(2);
        Person personOne = new Person("R.Boovan", "boovanNaik@gmail.com", address, Person.getDate(1997, 1, 1));
        personOne.setId(3);
        personOne.setCreatedDate(LocalDateTime.of(2018, 9, 24, 16, 47, 55));
        Person personTwo = new Person("R.Rajendran", "RajendranNaikar@gmail.com", null, Person.getDate(1997, 1, 1));
        personTwo.setId(4);
        personTwo.setCreatedDate(LocalDateTime.of(2018, 9, 24, 16, 47, 55));
        return new Object[][] {
            {personOne, 3, true},
            {personTwo, 4, false}
        };
    }

    @DataProvider(name = "positiveCase_update")
    private Object[][] updateTest_positiveDP() {
        return new Object[][] {
            
        };
    }
    @AfterClass
    private void afterClass() {

    }
}
