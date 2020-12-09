package com.example.smallwebapp.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public Page<TicketDTO> ticketLists(@PageableDefault(size = 10) Pageable pageable,
                                       @RequestParam(name = "username", required = false) String userName) {
        if (userName == null) {
            var page = ticketService.findTickets(pageable);
            return page.map(TicketDTO::toDTO);
        } else {
            var page = ticketService.findTicketsByUserName(userName, pageable);
            return page.map(TicketDTO::toDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> ticketById(@PathVariable int id) {
        var toDoTask = ticketService.findTicketById(id);
        var toDoTaskDTO = TicketDTO.toDTO(toDoTask);
        return ResponseEntity.ok(toDoTaskDTO);
    }

    @PostMapping
    public ResponseEntity<TicketDTO> addNewTicket(@RequestBody TicketDTO ticketDTO) {
        var newToDoTask = ticketService.saveNewTicket(ticketDTO);
        var newToDoTaskDTO = TicketDTO.toDTO(newToDoTask);
        return ResponseEntity.created(URI.create("/ticket/" + newToDoTask.getId())).body(newToDoTaskDTO);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicketById(@PathVariable int id) {
        ticketService.deleteTicketById(id);
    }
}
