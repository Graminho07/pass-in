package rocketseat.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.Checkin;
import rocketseat.com.passin.domain.checkin.exceptions.CheckinAlreadyExistsException;
import rocketseat.com.passin.repositories.CheckinRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckinService {
    private final CheckinRepository checkinRepository;

    public void registerCheckin(Attendee attendee){
        this.verifyCheckinExixts(attendee.getId());
        Checkin newCheckin = new Checkin();
        newCheckin.setAttendee(attendee);
        newCheckin.setCreatedAt(LocalDateTime.now());
        this.checkinRepository.save(newCheckin);
    }
    private void verifyCheckinExixts(String attendeeId){
        Optional<Checkin> isCheckin = this.getCheckin(attendeeId);
        if(isCheckin.isPresent()) throw new CheckinAlreadyExistsException("Attendee already checked in");
    }

    public Optional<Checkin> getCheckin(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
