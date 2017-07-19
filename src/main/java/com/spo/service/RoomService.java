package com.spo.service;

import com.spo.dto.RoomResponse;

public interface RoomService {
    
    /**
     * Returns the optimal amount of senior and junior members to use for a specified number of rooms, seniors score
     * and juniors score
     * Objective function to minimize is of the type : f(seniors,juniors)=(seniorscore*seniors)+(juniorscore*juniors)-rooms
     * Restrictions are :
     * 1. seniors >= 1
     * 2. juniors >= 0
     * 3. seniors, juniors should be integer values
     * @param rooms - number of rooms
     * @param seniorscore - Cleaning capacity of senior cleaner
     * @param juniorscore - Cleaning capacity of junior cleaner
     * @return A RoomResponse object with the optimal amount of senior and junior members to use
     */
    public RoomResponse getOptimalResourceForRoom(int rooms, int seniorscore, int juniorscore);

}
