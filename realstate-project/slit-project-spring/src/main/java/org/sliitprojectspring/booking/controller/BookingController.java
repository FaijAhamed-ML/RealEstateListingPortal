package org.sliitprojectspring.booking.controller;

import org.springframework.web.bind.annotation.*;
import org.sliitprojectspring.booking.dto.BookingDto;
import org.sliitprojectspring.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin("*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto createBooking(@RequestBody BookingDto dto) {
        return bookingService.createBooking(dto);
    }

    @GetMapping("/user/{userId}")
    public List<BookingDto> getBookingsByUserId(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @GetMapping("/owner/{ownerId}")
    public List<BookingDto> getBookingsByOwnerId(@PathVariable Long ownerId) {
        return bookingService.getBookingsByOwnerId(ownerId);
    }

    @PutMapping("/{bookingId}/status")
    public BookingDto updateStatus(@PathVariable Long bookingId, @RequestBody String status) {
        // Clean status if it comes with quotes
        String cleanStatus = status.replace("\"", "");
        return bookingService.updateBookingStatus(bookingId, cleanStatus);
    }
}
