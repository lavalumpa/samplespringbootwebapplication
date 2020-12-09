package com.example.smallwebapp.ticket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketDTO {
    private int id;
    private String name;
    private String issue;
    private String description;

    public static Ticket toEntity(TicketDTO ticketDTO) {
        return Ticket.builder()
                .userName(ticketDTO.getName())
                .issue(ticketDTO.getIssue())
                .description(ticketDTO.getDescription())
                .build();
    }

    public static TicketDTO toDTO(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .name(ticket.getUserName())
                .issue(ticket.getIssue())
                .description(ticket.getDescription())
                .build();
    }
}
