package com.spo.test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.spo.controller.RoomController;
import com.spo.dto.RoomResponse;
import com.spo.service.RoomService;

@WebAppConfiguration
public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    RoomController classUnderTest = new RoomController();

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build();
    }

    @Test
    public void verifyJsonIsRetrievedForAcceptedValues() throws Exception {
        when(roomService.getOptimalResourceForRoom(anyInt(), anyInt(), anyInt())).thenReturn(new RoomResponse(3, 1));
        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"rooms\":[35,21,17]," + " \"senior\": 10," + " \"junior\": 6" + "}")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].senior", is(3)));
    }

    @Test
    public void verifySeniorScoreCannotBeZero() throws Exception {
        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"rooms\":[35,21,17]," + " \"senior\": 0," + " \"junior\": 6" + "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void verifyJuniorScoreCannotBeZero() throws Exception {
        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"rooms\":[35,21,17]," + " \"senior\": 1," + " \"junior\": 0" + "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void verifySeniorScoreCannotBeLesserThanJuniorScore() throws Exception {
        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"rooms\":[35,21,17]," + " \"senior\": 1," + " \"junior\": 2" + "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void verifyThereCannotBeMoreThan100Rooms() throws Exception {
        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"rooms\":[150,21,17]," + " \"senior\": 2," + " \"junior\": 1" + "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void verifyGetOptimalResourceIsCalledTwice() throws Exception {
        when(roomService.getOptimalResourceForRoom(anyInt(), anyInt(), anyInt())).thenReturn(new RoomResponse(3, 1));
        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content("{\"rooms\":[35,21]," + " \"senior\": 10," + " \"junior\": 6" + "}"));
        verify(roomService, times(2)).getOptimalResourceForRoom(anyInt(), anyInt(), anyInt());
    }

}
