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
public class DestinationDTO {

    private Long id;

    private String name;

    @Builder.Default
    private List<ActivityDTO> activities = new ArrayList<>();

}
