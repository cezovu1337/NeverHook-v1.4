package org.neverhook.client.event.events.impl.packet;

import org.neverhook.client.event.events.callables.EventCancellable;

public class EventMessage extends EventCancellable {

    private final String message;

    public EventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
