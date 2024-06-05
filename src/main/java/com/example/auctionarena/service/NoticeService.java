package com.example.auctionarena.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    Long modify(NoticeDto dto);

    void noticeRemove(Long nno);

    Long noticeCreate(NoticeDto noticeDto);

    Long getPreNo(Long nno);

    Long getNextNo(Long nno);

    public default NoticeDto entityToDto(Notice notice, Member member, List<NoticeImage> noticeImages) {
        NoticeDto noticeDto = NoticeDto.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .mid(member.getMid())
                .writerNickname(member.getNickname())
                .createdDate(notice.getCreatedDate())
                .lastModifiedDate(notice.getLastModifiedDate())
                .build();

        if (noticeImages != null) {

            List<NoticeImageDto> noticeImageDtos = noticeImages.stream().map(noticeImage -> {
                if (noticeImage != null) {

                    return NoticeImageDto.builder()
                            .ninum(noticeImage.getNinum())
                            .uuid(noticeImage.getUuid())
                            .imgName(noticeImage.getImgName())
                            .path(noticeImage.getPath())
                            .build();
                } else {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());

            noticeDto.setNoticeImageDtos(noticeImageDtos);
        }

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
                .member(member)
                .build();

        List<NoticeImageDto> noticeImageDtos = dto.getNoticeImageDtos();

        if (noticeImageDtos != null && noticeImageDtos.size() > 0) {
            List<NoticeImage> noticeImages = noticeImageDtos.stream().map(nDto -> {
                NoticeImage noticeImage = NoticeImage.builder()
                        .imgName(nDto.getImgName())
                        .uuid(nDto.getUuid())
                        .path(nDto.getPath())
                        .notice(notice)
                        .build();
                return noticeImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", noticeImages);
        }
        return entityMap;
    }
}
