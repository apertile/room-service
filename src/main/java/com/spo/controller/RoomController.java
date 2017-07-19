package com.spo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spo.dto.RoomContract;
import com.spo.dto.RoomResponse;
import com.spo.service.RoomService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class RoomController {
    
    @Autowired
    RoomService roomService;

    @ApiOperation(value = "Retrieves the optimal amount of senior and junior resources given the number of rooms to clean")
    @RequestMapping(value = "/rooms", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<RoomResponse> getOptimalResourceForRoom(@RequestBody RoomContract roomContract) {
        if(roomContract.getSenior()<=0 || roomContract.getJunior()<=0)
            throw new InputValidationException("Senior score and junior score should be greater than zero");
        if(roomContract.getSenior()<=roomContract.getJunior())
            throw new InputValidationException("Senior cleaners should have a higher work capacity than junior cleaners");
        for(int rooms : roomContract.getRooms()){
            if(rooms>100){
                throw new InputValidationException("Rooms cannot be greater than 100");
            }
        }
        List<RoomResponse> roomResponse = new ArrayList<RoomResponse>();
        for(Integer rooms : roomContract.getRooms()){
            RoomResponse response = roomService.getOptimalResourceForRoom(rooms, roomContract.getSenior(), roomContract.getJunior());
            roomResponse.add(response);
        }
        return roomResponse;
    }
    
    //Same operation as POST but with a GET method, which makes more sense to standardize
    @ApiOperation(value = "Retrieves the optimal amount of senior and junior resources given the number of rooms to clean")
    @RequestMapping(value = "/rooms", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RoomResponse> getOptimalResourceForRoomAsGet(
            @RequestParam(value = "rooms", required = true) @ApiParam(value = "The number of rooms to clean in each building") int[] rooms,
            @RequestParam(value = "senior", required = true) @ApiParam(value = "Cleaning capacity of senior members")int senior,
            @RequestParam(value = "junior", required = true) @ApiParam(value = "Cleaning capacity of junior members")int junior) {
        if(senior<=0 || junior<=0)
            throw new InputValidationException("Senior score and junior score should be greater than zero");
        if(senior<=junior)
            throw new InputValidationException("Senior cleaners should have a higher work capacity than junior cleaners");
        for(int room : rooms){
            if(room>100){
                throw new InputValidationException("Rooms cannot be greater than 100");
            }
        }
        List<RoomResponse> roomResponse = new ArrayList<RoomResponse>();
        for(Integer room : rooms){
            RoomResponse response = roomService.getOptimalResourceForRoom(room, senior, junior);
            roomResponse.add(response);
        }
        return roomResponse;
    }
}
