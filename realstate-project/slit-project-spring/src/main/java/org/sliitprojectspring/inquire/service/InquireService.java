package org.sliitprojectspring.inquire.service;

import org.springframework.stereotype.Service;
import org.sliitprojectspring.inquire.dto.InquireDto;
import org.sliitprojectspring.inquire.dto.InquireRequest;
import org.sliitprojectspring.inquire.dto.MessageDto;
import org.sliitprojectspring.inquire.exception.InquireException;
import org.sliitprojectspring.user.exception.UserNotFoundException;
import org.sliitprojectspring.inquire.model.Inquire;
import org.sliitprojectspring.inquire.model.InquireMessage;
import org.sliitprojectspring.property.model.Property;
import org.sliitprojectspring.user.model.User;
import org.sliitprojectspring.inquire.repository.InquireMessageRepository;
import org.sliitprojectspring.inquire.repository.InquireRepository;
import org.sliitprojectspring.property.repository.PropertyRepository;
import org.sliitprojectspring.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquireService {

    private final InquireRepository inquireRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final InquireMessageRepository messageRepository;

    public InquireService(InquireRepository repository, UserRepository userRepository,
                          PropertyRepository propertyRepository, InquireMessageRepository messageRepository) {
        this.inquireRepository = repository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.messageRepository = messageRepository;
    }

    public void deleteInquireById(Long id) {
        inquireRepository.deleteById(id);
    }

    public List<InquireDto> getAllInquires() {
        return inquireRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public boolean deleteInquire(Long inquireId) {
        inquireRepository.findById(inquireId).orElseThrow(
                () -> new RuntimeException("Inquiry ID not found")
        );
        inquireRepository.deleteById(inquireId);
        return true;
    }

    public List<InquireDto> getInquiresByUserId(Long id) {
        userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with the id")
        );
        List<Inquire> inquires = inquireRepository.getInquiresByUserId(id);
        return inquires.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<InquireDto> getInquiresByOwnerId(Long id) {
        List<Inquire> inquires = inquireRepository.getInquiresByOwnerId(id);
        return inquires.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public long getUnreadInquireCount(Long ownerId) {
        return inquireRepository.getInquiresByOwnerId(ownerId).stream()
                .filter(inq -> !inq.isRead())
                .count();
    }

    public void markAsRead(Long inquireId) {
        Inquire inq = inquireRepository.findById(inquireId).orElse(null);
        if (inq != null) {
            inq.setRead(true);
            inquireRepository.save(inq);
        }
    }

    public InquireDto editInquirePosted(Long userId, Long propertyId, InquireDto dto) {
        propertyRepository.findById(propertyId).orElseThrow(
                () -> new RuntimeException("Property not found")
        );
        Inquire inquire = inquireRepository.getInquireByPropertyIdAndUserId(userId, propertyId).orElseThrow(
                () -> new RuntimeException("Inquiry not found")
        );
        inquire.setDescription(dto.description());
        inquireRepository.save(inquire);
        return mapToDto(inquire);
    }

    public List<InquireDto> getAllInquiresByPropertyId(Long propertyId) {
        return inquireRepository.getInquireByPropertyId(propertyId).stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    public InquireDto makeAnInquire(InquireRequest dto) {
        User user = userRepository.findById(dto.userId()).orElseThrow(
                () -> new UserNotFoundException("User is not found")
        );
        User owner = userRepository.findById(dto.ownerId()).orElseThrow(
                () -> new UserNotFoundException("Owner is not found")
        );

        List<Property> properties = propertyRepository.getPropertiesByUserId(dto.ownerId());
        boolean isOwner = properties.stream()
                .anyMatch(property -> property.getUser().getId().equals(dto.userId()));

        if (isOwner) {
            throw new RuntimeException("You are not allowed to inquire on your own property");
        }

        propertyRepository.findById(dto.propertyId()).orElseThrow(
                () -> new InquireException("Property not found")
        );

        Inquire inquire = new Inquire();
        inquire.setUser(user);
        inquire.setDescription(dto.description());
        inquire.setCreatedAt(LocalDateTime.now());
        inquire.setName(dto.name());
        inquire.setContactNumber(dto.contactNumber());
        inquire.setOwner(owner);
        inquire.setPropertyId(dto.propertyId());
        
        inquireRepository.save(inquire);
        return mapToDto(inquire);
    }

    private InquireDto mapToDto(Inquire inq) {
        List<MessageDto> messages = inq.getMessages().stream()
                .map(m -> new MessageDto(m.getId(), m.getSenderId(), m.getContent(), m.getTimestamp()))
                .collect(Collectors.toList());

        String propDetails = "Unknown Property";
        try {
            Long pId = Long.parseLong(inq.getPropertyId());
            propDetails = propertyRepository.findById(pId)
                .map(p -> p.getDetails() + " in " + p.getLocation())
                .orElse("Property #" + inq.getPropertyId());
        } catch (Exception e) {
            // keep as default
        }

        return new InquireDto(
                inq.getId(),
                inq.getUser().getId(),
                inq.getOwner() != null ? inq.getOwner().getId() : null,
                inq.getOwner() != null ? inq.getOwner().getName() : "Unknown",
                inq.getPropertyId(),
                propDetails,
                inq.getDescription(),
                inq.getName(),
                inq.getContactNumber(),
                inq.getReply(),
                messages,
                inq.isRead()
        );
    }

    public InquireDto replyToInquire(Long inquireId, String content, Long senderId) {
        Inquire inquire = inquireRepository.findById(inquireId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        InquireMessage msg = new InquireMessage();
        msg.setInquire(inquire);
        msg.setSenderId(senderId);
        msg.setContent(content);
        msg.setTimestamp(LocalDateTime.now());
        messageRepository.save(msg);

        // Update legacy fields for compatibility
        if (inquire.getOwner().getId().equals(senderId)) {
            inquire.setReply(content);
            inquire.setRepliedAt(LocalDateTime.now());
        } else {
            inquire.setDescription(content);
            inquire.setUpdateAt(LocalDateTime.now());
            // Ifinquirer sends a new message, mark as unread for the owner
            inquire.setRead(false);
        }
        inquireRepository.save(inquire);

        return mapToDto(inquire);
    }
}
