package com.example.smallwebapp.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TicketControllerTest {
    private final TicketRepository ticketRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;


    @Test
    public void testGetTicketListWithOneTicket() throws Exception {
        var ticket = createTicket();
        ticketRepository.save(ticket);
        var id = ticketRepository.findAll().get(0).getId();
        mockMvc.perform(get("/ticket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id))
                .andExpect(jsonPath("$.content[0].name").value("user"))
                .andExpect(jsonPath("$.content[0].issue").value("issue"))
                .andExpect(jsonPath("$.content[0].description").value("description"));
    }

    @Test
    public void testGetTicketListByUserNameWithOneTicket() throws Exception {
        var ticket = createTicket();
        ticketRepository.save(ticket);
        var id = ticketRepository.findAll().get(0).getId();
        mockMvc.perform(get("/ticket").param("username", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id))
                .andExpect(jsonPath("$.content[0].name").value("user"))
                .andExpect(jsonPath("$.content[0].issue").value("issue"))
                .andExpect(jsonPath("$.content[0].description").value("description"));
    }

    @Test
    public void testGetOneTicketWithSpecifiedId() throws Exception {
        var ticket = createTicket();
        ticketRepository.save(ticket);
        var id = ticketRepository.findAll().get(0).getId();
        mockMvc.perform(get("/ticket/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("user"))
                .andExpect(jsonPath("$.issue").value("issue"))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    public void testPostTicket() throws Exception {
        var ticketDTO = createTicketDTO();
        var jsonRequest = objectMapper.writeValueAsString(ticketDTO);
        mockMvc.perform(post("/ticket").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("user"))
                .andExpect(jsonPath("$.issue").value("issue"))
                .andExpect(jsonPath("$.description").value("description"));
    }


    @Test
    public void testTicketDeleteById() throws Exception {
        var ticket = createTicket();
        ticketRepository.save(ticket);
        var id = ticketRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/ticket/{id}", id))
                .andExpect(status().isNoContent());
    }

    private Ticket createTicket() {
        return Ticket.builder()
                .userName("user")
                .issue("issue")
                .description("description")
                .build();

    }

    private TicketDTO createTicketDTO() {
        return TicketDTO.builder()
                .name("user")
                .issue("issue")
                .description("description")
                .build();
    }


}
