package com.assignment.travelagencymanagement.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassengerListDTO {

    String travelPackageName;

    int passengerCapacity;

    int passengerCount;

    List<PassengerInfo> passengers;

}
