package com.example.smallwebapp.ticket;

import com.example.smallwebapp.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public Page<Ticket> findTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Page<Ticket> findTicketsByUserName(String userName, Pageable pageable) {
        return ticketRepository.findAllByUserName(userName,pageable);
    }

    public Ticket findTicketById(int id) {
        return ticketRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Ticket saveNewTicket(TicketDTO ticketDTO) {
        var ticket = TicketDTO.toEntity(ticketDTO);
        return ticketRepository.save(ticket);
    }


    public void deleteTicketById(int id) {
        ticketRepository.deleteById(id);
    }
}
