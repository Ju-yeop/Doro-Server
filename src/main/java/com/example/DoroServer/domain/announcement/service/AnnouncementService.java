package com.example.DoroServer.domain.announcement.service;

import com.example.DoroServer.domain.announcement.dto.AnnouncementReq;
import com.example.DoroServer.domain.announcement.dto.AnnouncementRes;
import com.example.DoroServer.domain.announcement.entity.Announcement;
import com.example.DoroServer.domain.announcement.repository.AnnouncementRepository;
import com.example.DoroServer.global.exception.BaseException;
import com.example.DoroServer.global.exception.Code;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementRes findById(Long id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        return announcement.orElseThrow(() -> {
            log.info("Announcement를 찾을 수 없습니다. id = {}", id);
            throw new BaseException(Code.ANNOUNCEMENT_NOT_FOUND);
        }).toRes();
    }

    public List<AnnouncementRes> findAllAnnouncements() {
        return announcementRepository.findAllRes();
//        List<Announcement> announcements = announcementRepository.findAll();
//        return announcements.stream()
//                .map(Announcement::toRes)
//                .collect(Collectors.toList());
    }

    @Transactional
    public Long createAnnouncement(AnnouncementReq announcementReq) {
        Announcement announcement = announcementReq.toEntity();
        announcementRepository.save(announcement);
        return announcement.getId();
    }

    @Transactional
    public void updateAnnouncement(Long id, AnnouncementReq announcementReq) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        Announcement updateAnnouncement = announcement.orElseThrow(() -> {
            log.info("update 하려는 Announcement를 찾을 수 없습니다. id = {}", id);
            return new BaseException(Code.ANNOUNCEMENT_NOT_FOUND);
        });
        updateAnnouncement.update(announcementReq);
    }

    @Transactional
    public void deleteAnnouncement(Long id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        announcement.orElseThrow(() -> {
            log.info("delete 하려는 Announcement를 찾을 수 없습니다. id = {}", id);
            return new BaseException(Code.ANNOUNCEMENT_NOT_FOUND);
        });
        announcementRepository.deleteById(id);
    }



}
