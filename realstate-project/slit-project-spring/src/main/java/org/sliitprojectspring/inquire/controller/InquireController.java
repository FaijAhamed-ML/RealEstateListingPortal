package org.sliitprojectspring.inquire.controller;

import org.springframework.web.bind.annotation.*;
import org.sliitprojectspring.inquire.dto.InquireDto;
import org.sliitprojectspring.inquire.dto.InquireRequest;
import org.sliitprojectspring.inquire.dto.ReplyRequest;
import org.sliitprojectspring.inquire.service.InquireService;

import java.util.List;

@RestController
@RequestMapping("/api/inquire")
@CrossOrigin("*")
public class InquireController {

    private final InquireService inquireService;

    public InquireController(InquireService inquireService) {
        this.inquireService = inquireService;
    }

    @PostMapping()
    public InquireDto makeAnInquire(@RequestBody InquireRequest dto) {
        return inquireService.makeAnInquire(dto);
    }

    @GetMapping("/all")
    public List<InquireDto> getAllInquire() {
        return inquireService.getAllInquires();
    }

    @PutMapping("/{userId}/{propertyId}")
    public InquireDto editInquire(@PathVariable Long userId, @PathVariable Long propertyId, @RequestBody InquireDto dto) {
        return inquireService.editInquirePosted(userId, propertyId, dto);
    }

    @GetMapping("/{userId}")
    public List<InquireDto> getInquiresByUserId(@PathVariable Long userId) {
        return inquireService.getInquiresByUserId(userId);
    }

    @GetMapping("/owner/{ownerId}")
    public List<InquireDto> getInquiresByOwnerId(@PathVariable Long ownerId) {
        return inquireService.getInquiresByOwnerId(ownerId);
    }

    @GetMapping("/unread/count/{ownerId}")
    public long getUnreadCount(@PathVariable Long ownerId) {
        return inquireService.getUnreadInquireCount(ownerId);
    }

    @PutMapping("/{inquireId}/read")
    public void markAsRead(@PathVariable Long inquireId) {
        inquireService.markAsRead(inquireId);
    }

    @PutMapping("/{inquireId}/reply")
    public InquireDto replyToInquire(@PathVariable Long inquireId, @RequestBody ReplyRequest request) {
        return inquireService.replyToInquire(inquireId, request.content(), request.senderId());
    }
}
