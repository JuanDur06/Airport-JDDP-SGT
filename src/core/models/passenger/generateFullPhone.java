/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.passenger;

/**
 *
 * @author USER
 */
public class generateFullPhone {
    public static String generateFullPhone(int countryPhoneCode, long phone){
        return "+" + countryPhoneCode + " " + phone;
    }
}
