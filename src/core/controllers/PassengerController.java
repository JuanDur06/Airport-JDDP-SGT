/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Passenger;
import core.models.Storage.PassengerStorage;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 *
 * @author juand
 */
public class PassengerController {

    public static Response createPassenger(String id, String firstname, String lastname, String yearStr, String monthStr, String dayStr, String numberCode, String number, String country) {
        try {
            Long idLong, numberLong;
            int numberCodeInt;

            try {
                idLong = Long.valueOf(id);
                if (idLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                } else {
                    if (idLong > 999999999999999l) {
                        return new Response("Id must be less than 15 digits", Status.BAD_REQUEST);
                    }
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }

            if (firstname.equals("")) {
                return new Response("Firstname must be not empty", Status.BAD_REQUEST);
            }

            if (lastname.equals("")) {
                return new Response("Lastname must be not empty", Status.BAD_REQUEST);
            }

            int year = Integer.parseInt(yearStr);

            try {
                if (year < 0) {
                    return new Response("Year must be positive", Status.BAD_REQUEST);
                }

            } catch (NumberFormatException ex) {
                return new Response("Year must be numeric", Status.BAD_REQUEST);
            }

            if (monthStr.equals("Month")) {
                return new Response("Please select a month", Status.BAD_REQUEST);
            }

            if (dayStr.equals("Day")) {
                return new Response("Please select a day", Status.BAD_REQUEST);
            }

            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);

            LocalDate birthDate;

            try {
                birthDate = LocalDate.of(year, month, day);
            } catch (DateTimeException e) {
                return new Response("Date is invalid ", Status.BAD_REQUEST);
            }

            // Validación lógica de fecha
            LocalDate today = LocalDate.now();
            LocalDate minDate = today.minusYears(120); // Edad máxima razonable: 120 años

            if (birthDate.isAfter(today)) {
                return new Response("The date of birth cannot be in the future.", Status.BAD_REQUEST);
            }

            if (birthDate.isBefore(minDate)) {
                return new Response("The date of birth is too old to be valid.", Status.BAD_REQUEST);
            }

            try {
                numberCodeInt = Integer.parseInt(numberCode);
                if (numberCodeInt < 0 || numberCodeInt > 999) {
                    return new Response("Number code must be in a range of 1 to 3 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Number code must be numeric", Status.BAD_REQUEST);
            }
            try {
                numberLong = Long.valueOf(number);
                if (numberLong < 0 || numberLong > 99999999999l) {
                    return new Response("Phone number must in a range of 1 to 11 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Phone number must be numeric", Status.BAD_REQUEST);
            }
            if (country.equals("")) {
                return new Response("Country must not be empty", Status.BAD_REQUEST);
            }

            PassengerStorage storage = PassengerStorage.getInstance();
            if (!storage.addPassenger(new Passenger(idLong, firstname, lastname, birthDate, numberCodeInt, numberLong, country))) {
                return new Response("A Passenger with that id already exists", Status.BAD_REQUEST);
            }

            return new Response("Passenger created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
// Second method:

    public static Response updatePassenger(String id, String firstname, String lastname, String yearStr, String monthStr, String dayStr, String numberCode, String number, String country) {
        try {
            Long idLong, numberLong;
            int numberCodeInt;

            // Validar ID
            try {
                idLong = Long.valueOf(id);
                if (idLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                } else if (idLong > 999999999999999L) {
                    return new Response("Id must be less than 15 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }

            // Validar nombres
            if (firstname.isEmpty()) {
                return new Response("Firstname must not be empty", Status.BAD_REQUEST);
            }
            if (lastname.isEmpty()) {
                return new Response("Lastname must not be empty", Status.BAD_REQUEST);
            }

            // Validar año
            int year;
            try {
                year = Integer.parseInt(yearStr);
                if (year < 0) {
                    return new Response("Year must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Year must be numeric", Status.BAD_REQUEST);
            }

            // Validar mes y día
            if (monthStr.equals("Month")) {
                return new Response("Please select a month", Status.BAD_REQUEST);
            }
            if (dayStr.equals("Day")) {
                return new Response("Please select a day", Status.BAD_REQUEST);
            }

            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);

            // Validar fecha
            LocalDate birthDate;
            try {
                birthDate = LocalDate.of(year, month, day);
            } catch (DateTimeException e) {
                return new Response("Date is invalid", Status.BAD_REQUEST);
            }

            LocalDate today = LocalDate.now();
            if (birthDate.isAfter(today)) {
                return new Response("The date of birth cannot be in the future.", Status.BAD_REQUEST);
            }
            if (birthDate.isBefore(today.minusYears(120))) {
                return new Response("The date of birth is too old to be valid.", Status.BAD_REQUEST);
            }

            // Validar código de país
            try {
                numberCodeInt = Integer.parseInt(numberCode);
                if (numberCodeInt < 0 || numberCodeInt > 999) {
                    return new Response("Number code must be in a range of 1 to 3 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Number code must be numeric", Status.BAD_REQUEST);
            }

            // Validar número de teléfono
            try {
                numberLong = Long.parseLong(number);
                if (numberLong < 0 || numberLong > 99999999999L) {
                    return new Response("Phone number must be in a range of 1 to 11 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Phone number must be numeric", Status.BAD_REQUEST);
            }

            // Validar país
            if (country.isEmpty()) {
                return new Response("Country must not be empty", Status.BAD_REQUEST);
            }

            // Buscar pasajero existente
            PassengerStorage storage = PassengerStorage.getInstance();
            Passenger existingPassenger = storage.getPassenger(idLong.intValue());
            if (existingPassenger == null) {
                return new Response("Passenger with given ID does not exist", Status.NOT_FOUND);
            }

            // Actualizar los datos del pasajero existente
            existingPassenger.setFirstname(firstname);
            existingPassenger.setLastname(lastname);
            existingPassenger.setBirthDate(birthDate);
            existingPassenger.setCountryPhoneCode(numberCodeInt);
            existingPassenger.setPhone(numberLong);
            existingPassenger.setCountry(country);

            return new Response("Passenger updated successfully", Status.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

}
