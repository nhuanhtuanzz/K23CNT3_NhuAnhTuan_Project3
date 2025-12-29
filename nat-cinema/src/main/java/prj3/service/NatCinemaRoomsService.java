package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatCinemaRooms;
import prj3.repository.NatCinemaRoomsRepository;

import java.util.List;

@Service
@Transactional
public class NatCinemaRoomsService {

    private final NatCinemaRoomsRepository roomsRepository;

    public NatCinemaRoomsService(NatCinemaRoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    public List<NatCinemaRooms> getAllRooms() {
        return roomsRepository.findAll();
    }

    public NatCinemaRooms getRoomById(Long id) {
        return roomsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public NatCinemaRooms createRoom(NatCinemaRooms room) {
        if (room.getNatRoomName() == null || room.getNatRoomName().trim().isEmpty()) {
            throw new RuntimeException("Room name is required");
        }
        if (roomsRepository.existsByNatRoomName(room.getNatRoomName())) {
            throw new RuntimeException("Room name already exists");
        }
        return roomsRepository.save(room);
    }

    public NatCinemaRooms updateRoom(Long id, NatCinemaRooms roomDetails) {
        NatCinemaRooms room = getRoomById(id);
        room.setNatRoomName(roomDetails.getNatRoomName());
        room.setNatTotalSeats(roomDetails.getNatTotalSeats());
        return roomsRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomsRepository.deleteById(id);
    }
}