package yte.intern.spring.management.usecases.common;

import lombok.*;
import yte.intern.spring.management.usecases.common.Enum.MessageType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageResponse{

    private String message;
    private MessageType messageType;

}
