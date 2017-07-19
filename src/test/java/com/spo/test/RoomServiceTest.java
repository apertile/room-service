package com.spo.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.spo.dto.RoomResponse;
import com.spo.service.RoomService;
import com.spo.service.RoomServiceImpl;

public class RoomServiceTest {
    
    RoomService classUnderTest = new RoomServiceImpl();
    
    @Test
    public void assertGetOptimalResourceForRoomRetrievesCorrectResult(){
        RoomResponse response1 = classUnderTest.getOptimalResourceForRoom(35, 10, 6);
        RoomResponse response2 = classUnderTest.getOptimalResourceForRoom(21, 10, 6);
        RoomResponse response3 = classUnderTest.getOptimalResourceForRoom(17, 10, 6);
        RoomResponse response4 = classUnderTest.getOptimalResourceForRoom(24, 11, 6);
        RoomResponse response5 = classUnderTest.getOptimalResourceForRoom(28, 11, 6);
        assertTrue(response1.getSenior()==3 && response1.getJunior()==1);
        assertTrue(response2.getSenior()==1 && response2.getJunior()==2);
        assertTrue(response3.getSenior()==2 && response3.getJunior()==0);
        assertTrue(response4.getSenior()==2 && response4.getJunior()==1);
        assertFalse(response5.getSenior()==1 || response5.getJunior()==3);
    }
    
    @Test(expected = ArithmeticException.class)
    public void verifyZeroSeniorScoreThrowsException() {
        classUnderTest.getOptimalResourceForRoom(35, 0, 1);
    }
    
    @Test
    public void assertGetOptimalResourceRetrievesAtLeastOneSenior(){
        RoomResponse response1 = classUnderTest.getOptimalResourceForRoom(6, 15, 6);        
        RoomResponse response2 = classUnderTest.getOptimalResourceForRoom(3, 15, 6);
        assertTrue(response1.getSenior()==1 && response1.getJunior()==0);
        assertTrue(response2.getSenior()==1 && response2.getJunior()==0);
    }
    
    @Test
    public void verifyGetOptimalResourceRetrievesNullForNegativeNumbers(){
        RoomResponse response1 = classUnderTest.getOptimalResourceForRoom(-6, 15, 6);
        assertTrue(response1.getSenior()==null && response1.getJunior()==null);
    }

}
