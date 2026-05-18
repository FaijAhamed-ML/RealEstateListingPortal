package org.sliitprojectspring.booking.service;

import org.springframework.stereotype.Service;
import org.sliitprojectspring.booking.dto.BookingDto;
import org.sliitprojectspring.booking.model.Booking;
import org.sliitprojectspring.user.model.User;
import org.sliitprojectspring.booking.repository.BookingRepository;
import org.sliitprojectspring.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public BookingDto createBooking(BookingDto dto) {
        User user = userRepository.findById(dto.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        User owner = userRepository.findById(dto.ownerId()).orElseThrow(() -> new RuntimeException("Owner not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setOwner(owner);
        booking.setPropertyId(dto.propertyId());
        booking.setPreferredDate(dto.preferredDate());
        booking.setStatus("PENDING");
        booking.setMessage(dto.message());
        booking.setCreatedAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        return mapToDto(saved);
    }

    public List<BookingDto> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByOwnerId(Long ownerId) {
        return bookingRepository.findByOwnerId(ownerId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BookingDto updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return mapToDto(bookingRepository.save(booking));
    }

    private BookingDto mapToDto(Booking b) {
        return new BookingDto(
                b.getId(),
                b.getUser().getId(),
                b.getUser().getName(),
                b.getOwner().getId(),
                b.getPropertyId(),
                b.getPreferredDate(),
                b.getStatus(),
                b.getMessage(),
                b.getCreatedAt()
        );
    }
}
