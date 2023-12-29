package com.assignment.travelagencymanagement.dto;

import com.assignment.travelagencymanagement.custom.PassengerType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassengerInfo {

    private Long id;

    String name;

    @Builder.Default
    int passengerNumber = -1;

    @Builder.Default
    PassengerType type = PassengerType.DEFAULT;

    @Builder.Default
    double balance = -1;

    @Builder.Default
    List<ActivityDTO> activities = new ArrayList<>();

}
