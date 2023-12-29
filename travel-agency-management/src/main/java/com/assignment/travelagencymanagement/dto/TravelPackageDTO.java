package com.assignment.travelagencymanagement.dto;

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
public class TravelPackageDTO {

    private Long id;

    String travelPackageName;

    @Builder.Default
    int passengerCapacity = -1;

    @Builder.Default
    List<DestinationDTO> destinationDetails = new ArrayList<>();


}
