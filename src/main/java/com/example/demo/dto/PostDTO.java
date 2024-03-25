package com.example.demo.dto;



import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


/**
 * PostDTO
 */
public record PostDTO(
    // @DefaultValue("0")
    long postID,

    @NotEmpty
    @Size(max = 90)
    String titleString,
    @NotEmpty
    @Size(max = 800)
    String bodyString,
    @NotEmpty
    @Size(max = 15)
    String posterString,


    // @DefaultValue("0")
    Date postedOnDate
) {
}