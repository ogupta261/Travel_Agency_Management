package com.assignment.travelagencymanagement.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityDTO {

    private Long id;

    private String name;

    private String description;

    private double cost;

    private int capacity;

    private String destination;

}
