package com.objectfrontier.training.java.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;
/**
 * @author Lokesh.
 * @since Sep 21, 2018
 */
public class PersonService {

    final int NAME = 1;
    final int EMAIL = 2;
    final int ADDRESS = 3;
    final int BIRTH_DATE = 4;

    final int STREET = 1;
    final int CITY = 2;
    final int POSTAL_CODE = 3;

    final String url = "jdbc:mysql://pc1620:3306/lokeshe_rajendran?SSL=false";

    public static void main(String[] args) {
        PersonService obj = new PersonService();

        try {
            obj.run(args);
        } catch (Throwable t) {
            log(t);
        }
    }

    private void run(String[] args) {

        Address address = new Address("Hassan Street", "chittoor", 517001);
//        address.setId(2);
//        Person personOne = new Person("R.Boovan", "boovanNaik@gmail.com", address, Person.getDate(1997, 1, 1));
//        personOne.setId(3);
//        personOne.setCreatedDate(LocalDateTime.of(2018, 9, 24, 16, 47, 55));
//        Person personTwo = new Person("R.Boovan", "boovanNaik@gmail.com", address, Person.getDate(1997, 1, 1));
//        personOne.setId(3);
//        personOne.setCreatedDate(LocalDateTime.of(2018, 9, 24, 16, 47, 55));
//        personTwo.setCreatedDate(LocalDateTime.of(2018, 9, 24, 16, 47, 55));
//        log("%b", personOne.equals(personTwo));
////        Person person = new Person("R.Vijaya",
//                                   "vijaya.rajendran@gmail.com",
//                                   null,
//                                   Person.getDate(1997, 1, 27));
//        log("%d%n", insertRecord(person));
        List<Person> persons;
        try {
            persons = readAll();
            persons.stream().forEach(perzon -> log("%s%n", perzon));
        } catch (SQLException e) {
            log(e);
        }
//        Person personThree = read(2, true);
//        log("%s%n", personThree);
//        log("%s%n", delete(1));
//        log("%d%n", checkExsistance(getConnection(url, "mysqlCredentials"), address));

    }

    public long insertRecord(Person person) throws SQLException {

        Connection connection = getConnection(url, "mysqlCredentials.txt");
        Address address = person.getAddress();
        try {
            connection.setAutoCommit(false);
            if (address != null) {
                createAddress(connection, address);
            }
            return createPersonRecord(person, connection);
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException exception) {
                throw exception;
            }
            throw e;
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e2) {
                    throw e2;
                }
            }
        }
    }

    private long createPersonRecord(Person person, Connection connection) throws SQLException {
        String personInsertquery = "INSERT INTO person(name, email, address_id, birth_date)"
                                    + " VALUES(?, ?, ?, ?);";
        try (PreparedStatement statement = createStatement(connection,
                                                           personInsertquery,
                                                           Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(NAME, person.getName());
            statement.setString(EMAIL, person.getEmail());

            if (person.getAddress() != null) {
                statement.setLong(ADDRESS, person.getAddress().getId());
            } else {
                statement.setNull(ADDRESS, Types.BIGINT);
            }

            statement.setDate(BIRTH_DATE, java.sql.Date.valueOf(person.getBirthDate()));
            executeUpdate(statement);
            long personId = 0;

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                personId = resultSet.getInt(1);
                log("%s%n", personId);
            }
            connection.commit();
            return personId;
        } catch (SQLException e) {
            throw e;
        }
    }

    private long createAddress(Connection connection, Address address) throws SQLException {

        long addressId = -1;

        if ((addressId = checkAddressExsistance(connection, address)) != -1) {
            address.setId(addressId);
            return addressId;
        } else {
            String addressInsertQuery = "INSERT INTO address(street, city, postal_code) VALUES(?, ?, ?);";
            try (PreparedStatement statement = createStatement(connection, addressInsertQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(STREET, address.getStreet());
                statement.setString(CITY, address.getCity());
                statement.setLong(POSTAL_CODE, address.getPostalCode());
                executeUpdate(statement);
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    addressId = resultSet.getInt(1);
                    log("%s%n", addressId);
                }
                log("%d%n", addressId);
                address.setId(addressId);
                return addressId;
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    private long checkAddressExsistance(Connection connection, Address address) throws SQLException {

        long id = 0;
        String getIdQuery = "SELECT id FROM address WHERE city = ? AND street = ? AND postal_code = ?;";
        try (PreparedStatement statement = createStatement(connection, getIdQuery)) {
            statement.setString(1, address.getCity());
            statement.setString(2, address.getStreet());
            statement.setLong(3, address.getPostalCode());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                    return id;
                }
                return -1;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private List<Person> readAll() throws SQLException {

        try (Connection connection = getConnection(url, "mysqlCredentials.txt")){
            String fetchAllQuery = "SELECT person.id FROM person";
            try (PreparedStatement statement = createStatement(connection, fetchAllQuery)) {
                try (ResultSet resultSet = executeQuery(statement)) {
                    ArrayList<Person> persons = new ArrayList<>(resultSet.getFetchSize());
                    while (resultSet.next()) {
                        persons.add(read(resultSet.getLong(1), true));
                    }
                    return persons;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private Person read(long id) throws SQLException {
        try (Connection connection = getConnection(url, "mysqlCredentials.txt")){
            String fetchAllQuery = "SELECT person.name, person.email,"
                                   + " address.street, address.id, address.city, address.postal_code,"
                                   + " person.birth_date, person.created_date "
                                   + "FROM address,person WHERE address.id = person.address_id AND person.id = ?;";
            try (PreparedStatement statement = createStatement(connection, fetchAllQuery)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = executeQuery(statement)) {
                    Person person = new Person();
                    while (resultSet.next()) {
                        person.setId(id);
                        person.setName(resultSet.getString(1));
                        person.setEmail(resultSet.getString(2));
                        Address address = new Address(resultSet.getString(3),
                                                      resultSet.getString(5),
                                                      resultSet.getLong(6));
                        address.setId(resultSet.getLong(4));
                        person.setAddress(address);
                        person.setBirthDate(resultSet.getDate(7).toLocalDate());
                        person.setCreatedDate(resultSet.getTimestamp(8).toLocalDateTime());
                    }
                    return person;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public Person read(long id, boolean includeAddress) throws SQLException {
        try (Connection connection = getConnection(url, "mysqlCredentials.txt")) {
            if (!includeAddress || getIdIFPersonHasAddress(connection, id) == 0) {
                String fetchAllQuery = "SELECT person.name, person.email, person.birth_date, person.created_date "
                                        + "FROM person WHERE person.id = ?;";
                try (PreparedStatement statement = createStatement(connection, fetchAllQuery)) {
                    statement.setLong(1, id);
                    try (ResultSet resultSet = executeQuery(statement)) {
                        Person person = new Person();
                        while (resultSet.next()) {
                            person.setId(id);
                            person.setName(resultSet.getString(1));
                            person.setEmail(resultSet.getString(2));
                            person.setBirthDate(resultSet.getDate(3).toLocalDate());
                            person.setCreatedDate(resultSet.getTimestamp(4).toLocalDateTime());
                        }
                        return person;
                    }
                }
            }
            return read(id);
        } catch (SQLException e) {
            throw e;
        }
    }

    public int update(long id, Person person) throws SQLException {

        try (Connection connection = getConnection(url, "mysqlCredentials.txt")) {
            if (person.getAddress() != null) {
                long addressId = 0;
                if ((addressId = getIdIFPersonHasAddress(connection, id)) != 0) {
                    return updateRecord(id, person, connection, true, false);
                } else {
                    return updateRecord(id, person, connection, true, true);
                }
            } else {
                return updateRecord(id, person, connection, false, false);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private long getIdIFPersonHasAddress(Connection connection, long id) throws SQLException {

        String getIdQuery = "SELECT address_id FROM person WHERE id = ?;";
        try (PreparedStatement statement = createStatement(connection, getIdQuery)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                    return id;
                }
                return 0;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private int updateRecord(long id, Person person, Connection connection, boolean doUpdateAddress, boolean doCreateAddress) throws SQLException {

        if (doUpdateAddress) {
            if (doCreateAddress) {
                return updateCreatingAddress(id, person, connection);
            } else {
                return updateWithAddress(id, person, connection);
            }
        } else {
            return updateWithoutAddress(id, person, connection);
        }
    }

    private int updateWithoutAddress(long id, Person person, Connection connection) throws SQLException {
        Address address = person.getAddress();
        String updateQuery = "UPDATE person "
                            + "SET person.name = ?, person.email = ?, person.birth_date = ?, person.address_id = ? "
                            + "WHERE person.id = ?;";
        try (PreparedStatement statement = createStatement(connection, updateQuery)) {
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setDate(3, new java.sql.Date(person.getBirthDate().toEpochDay()));
            statement.setNull(4, Types.BIGINT);
            statement.setLong(5, id);
            return executeUpdate(statement);
        } catch (SQLException e) {
            throw e;
        }
    }

    private int updateCreatingAddress(long id, Person person, Connection connection) throws SQLException {
        Address address = person.getAddress();
        long newAddressId = createAddress(connection, new Address(address.getStreet(),
                                                                 address.getCity(),
                                                                 address.getPostalCode()));
        String updateQuery = "UPDATE person "
                            + "SET person.name = ?, person.email = ?, person.birth_date = ?, person.address_id = ? "
                            + "WHERE person.id = ?;";
        try (PreparedStatement statement = createStatement(connection, updateQuery)) {
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setDate(3, new java.sql.Date(person.getBirthDate().toEpochDay()));
            statement.setLong(4, newAddressId);
            statement.setLong(5, id);
            return executeUpdate(statement);
        } catch (SQLException e) {
            throw e;
        }
    }

    private int updateWithAddress(long id, Person person, Connection connection) throws SQLException {
        String updateQuery = "UPDATE person, address "
                            + "SET person.name = ?, person.email = ?, person.birth_date = ?,"
                            + "address.street = ?, address.city = ?, address.postal_code = ? "
                            + "WHERE person.id = ? AND address.id = (SELECT address_id FROM "
                            + "(SELECT * FROM person) AS person_alias "
                            + "WHERE id = ?);";
        try (PreparedStatement statement = createStatement(connection, updateQuery)) {
            Address address = person.getAddress();
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setDate(3, new java.sql.Date(person.getBirthDate().toEpochDay()));
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getCity());
            statement.setLong(6, address.getPostalCode());
            statement.setLong(7, id);
            statement.setLong(8, id);
            return executeUpdate(statement);
        } catch (SQLException e) {
            throw e;
        }
    }

    public int delete(long id) throws SQLException {
        try (Connection connection = getConnection(url, "mysqlCredentials.txt")){
            String deleteQuery ="DELETE FROM person WHERE id = ?;";
            try (PreparedStatement statement = createStatement(connection, deleteQuery)) {
                statement.setLong(1, id);
                return executeUpdate(statement);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        try {
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw e;
        }
    }

    private int executeUpdate(PreparedStatement statement) throws SQLException {
        try {
            int result = statement.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw e;
        }
    }

    private PreparedStatement createStatement(Connection connection, String sqlQuery) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);;
            return statement;
        } catch (SQLException e) {
            throw e;
        }
    }

    private PreparedStatement createStatement(Connection connection,
                                              String sqlQuery,
                                              int autoGeneratedKeyStatus) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, autoGeneratedKeyStatus);
            return statement;
        } catch (SQLException e) {
            throw e;
        }
    }

    private Connection getConnection(String url, String prpertiesFile) throws SQLException{
        try (InputStream propertyFileStream = getClass().getResourceAsStream("mysqlCredentials.txt")) {
            Properties mysqlCredentials = new Properties();
            mysqlCredentials.load(propertyFileStream);
            try {
                Connection connection = DriverManager.getConnection(url, mysqlCredentials);
                return connection;
            } catch (SQLException e) {
                throw e;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void log(Throwable t) {
        t.printStackTrace(System.err);
    }

    private static void log(String format, Object... vals) {
        System.out.format(format, vals);
    }
}
