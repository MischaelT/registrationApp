package com.upscale.registration.model;

import jakarta.persistence.*;

@Entity
@Table(name="event_attendee")
public class EventAttendee {
        @Id
        @Column(name = "event_attendee_id")
        private long eventAttendeeId;

        @Column(name = "event_id")
        private long eventId;

        @Column(name = "attendee_id")
        private long attendeeId;

        @Column( name = "is_attended")
        private boolean isAttended;

        public long getEventId() {
                return eventId;
        }

        public void setEventId(long eventId) {
                this.eventId = eventId;
        }

        public long getAttendeeId() {
                return attendeeId;
        }

        public void setAttendeeId(long attendeeId) {
                this.attendeeId = attendeeId;
        }

        public boolean isAttended() {
                return isAttended;
        }

        public void setAttended(boolean attended) {
                isAttended = attended;
        }

        public EventAttendee() {
        }

}
