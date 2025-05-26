/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.fligth;

import java.time.LocalDateTime;

/**
 *
 * @author USER
 */
public class  calculateArrivalDate {
    
    public static LocalDateTime calculateArrivalDate(LocalDateTime departureDate, int hoursDurationScale, int hoursDurationArrival, int minutesDurationScale, int minutesDurationArrival) {
        return departureDate.plusHours(hoursDurationScale).plusHours(hoursDurationArrival).plusMinutes(minutesDurationScale).plusMinutes(minutesDurationArrival);
    }
    
}
