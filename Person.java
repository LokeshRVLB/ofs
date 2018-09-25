package com.objectfrontier.training.java.jdbc;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Lokesh.
 * @since Sep 21, 2018
 */
public class Person {

    private long id;
    private String name;
    private String email;
    private Address address;
    private LocalDate birthDate;
    private LocalDateTime createdDate;


    public Person() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Person(String name, String email, Address address, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public static LocalDate getDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format("Person [id=%s, name=%s, email=%s%n, address=%s%n, birthDate=%s, createdDate=%s]", id, name,
                email, address, birthDate, createdDate);
    }

    public boolean equals(Person obj) {
        if (obj == null && this == null) { return true; }
        if (obj == null ^ this == null) { return false; }
        System.out.println(this.name.equals(obj.getName()));
        System.out.println(this.email.equals(obj.getEmail()));
        System.out.println(this.address.equals(obj.getAddress()));
        System.out.println(this.birthDate.isEqual(obj.getBirthDate()));
        System.out.println(this.createdDate.isEqual(obj.getCreatedDate()));
        if(this.name == obj.getName() &&
           this.email == obj.getEmail() &&
           this.address.equals(obj.getAddress()) &&
           this.birthDate.isEqual(obj.getBirthDate()) &&
           this.createdDate.isEqual(obj.getCreatedDate())) { return true; }
        return false;
    }




}
