package com.example.auctionarena.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.auctionarena.dto.NoticeDto;
import com.example.auctionarena.dto.NoticeImageDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.NoticeImage;

public interface NoticeService {

    PageResultDto<NoticeDto, Object[]> getList(PageRequestDto requestDto);

    NoticeDto getRow(Long nno);

    void modify(NoticeDto dto);

    void noticeRemove(Long nno);

    Long noticeCreate(NoticeDto noticeDto);

    public default NoticeDto entityToDto(Notice notice, List<NoticeImage> noticeImages, Member member) {
        NoticeDto noticeDto = NoticeDto.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .mid(member.getMid())
                .writerNickname(member.getNickname())
                .createdDate(notice.getCreatedDate())
                .lastModifiedDate(notice.getLastModifiedDate())
                .build();

        List<NoticeImageDto> noticeImageDtos = noticeImages.stream().map(noticeImage -> {
            return NoticeImageDto.builder()
                    .ninum(noticeImage.getNinum())
                    .nuuid(noticeImage.getNuuid())
                    .nimgName(noticeImage.getNimgName())
                    .npath(noticeImage.getNpath())
                    .build();
        }).collect(Collectors.toList());

        noticeDto.setNoticeImageDtos(noticeImageDtos);

        return noticeDto;
    }

    public default Map<String, Object> dtoToEntity(NoticeDto dto) {
        // Member member =
        // Member.builder().mid(dto.getMid()).nickname(dto.getWriterNickname()).build();

        // return Notice.builder()
        // .nno(dto.getNno())
        // .title(dto.getTitle())
        // .content(dto.getContent())
        // .writer(member)
        // .build();

        Map<String, Object> entityMap = new HashMap<>();

        Member member = Member.builder().mid(dto.getMid()).nickname(dto.getWriterNickname()).build();

        // notice entity 생성
        Notice notice = Notice.builder()
                .nno(dto.getNno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        List<NoticeImageDto> noticeImageDtos = dto.getNoticeImageDtos();

        if (noticeImageDtos != null && noticeImageDtos.size() > 0) {
            List<NoticeImage> noticeImages = noticeImageDtos.stream().map(nDto -> {
                NoticeImage noticeImage = NoticeImage.builder()
                        .nimgName(nDto.getNimgName())
                        .nuuid(nDto.getNuuid())
                        .npath(nDto.getNpath())
                        .notice(notice)
                        .build();
                return noticeImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", noticeImages);
        }
        return entityMap;
    }
}
