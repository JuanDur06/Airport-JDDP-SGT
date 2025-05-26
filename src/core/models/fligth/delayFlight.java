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
public class delayFlight {
    
    public static LocalDateTime delay(LocalDateTime fecha, int hour, int minute){
    return fecha.plusHours(hour).plusMinutes(minute);
    }
    
}
