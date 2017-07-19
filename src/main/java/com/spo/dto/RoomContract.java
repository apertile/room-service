package com.spo.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoomContract {
    
    private List<Integer> rooms;
    
    private Integer senior;
    
    private Integer junior;

}
