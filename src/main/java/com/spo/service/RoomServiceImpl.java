package com.spo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.spo.dto.RoomResponse;

@Service
public class RoomServiceImpl implements RoomService {

    @Override
    public RoomResponse getOptimalResourceForRoom(int rooms, int seniorscore, int juniorscore) {
        //Use BigDecimal to solve some limitations when using double, which could lead to some incorrect results
        BigDecimal roomsBG = BigDecimal.valueOf(rooms);
        BigDecimal seniorscoreBD = BigDecimal.valueOf(seniorscore);
        BigDecimal juniorscoreBD = BigDecimal.valueOf(juniorscore);
        Map<int[],Integer> minimums = new HashMap<int[],Integer>();
        //Get the highest tuple that is a zero of the objective function and round the number up so that it falls in the field of the natural numbers
        BigDecimal superiorlimit = roomsBG.divide(seniorscoreBD,2, RoundingMode.HALF_UP).setScale(0, RoundingMode.CEILING);
        if(superiorlimit.intValue()>=1){
            int[] toputSup = {superiorlimit.intValue(),0};
            int result = getResult(seniorscore,toputSup[0],juniorscore,toputSup[1],rooms);
            minimums.put(toputSup,result);
        }
        for(int i = 1; i<superiorlimit.intValue();i++){
            //Iterate through all the integer tuples from 1 (lowest senior members possible) to superiorlimit and get the zero value of the function
            //Round the number up so that it falls into the natural numbers set 
            BigDecimal juniorzero = roomsBG.divide(juniorscoreBD,2, RoundingMode.HALF_UP).subtract(seniorscoreBD.multiply(BigDecimal.valueOf(i)).divide(juniorscoreBD,2, RoundingMode.HALF_UP)).setScale(0, RoundingMode.CEILING);
            int[] toputMiddle = {i,juniorzero.intValue()};
            int result = getResult(seniorscore,i,juniorscore,juniorzero.intValue(),rooms);
            minimums.put(toputMiddle,result);
        }
        RoomResponse response = new RoomResponse();
        //Get min value from minimums map
        if(!minimums.entrySet().isEmpty()){
            Entry<int[], Integer> min = Collections.min(minimums.entrySet(),
                    Comparator.comparingInt(Entry::getValue));
            response.setSenior(min.getKey()[0]);
            response.setJunior(min.getKey()[1]);
        }
        return response;
    }
    
    /*
     * Retrieves the result of the objective function
     */
    private int getResult(int seniorscore, int seniors, int juniorscore, int juniors, int rooms){
        return (seniorscore*seniors)+(juniorscore*juniors)-rooms; 
    }

}
