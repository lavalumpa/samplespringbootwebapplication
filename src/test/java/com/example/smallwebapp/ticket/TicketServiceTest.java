package com.example.smallwebapp.ticket;

import com.example.smallwebapp.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;


    @Test
    public void testFindByIdWhenThereIsNoTicketWithGivenId() {
        when(ticketRepository.findById(eq(1))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ticketService.findTicketById(1));
    }

    @Test
    public void testFindByIdWhenTicketFound() {
        var ticket = createTicket();
        when(ticketRepository.findById(eq(1))).thenReturn(Optional.of(ticket));
        assertThat(ticketService.findTicketById(1)).isEqualTo(ticket);
    }

    private Ticket createTicket() {
        return Ticket.builder()
                .userName("user")
                .issue("issue")
                .description("description")
                .build();

    }


}
