package com.example.auctionarena.service;

import java.util.List;
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

    public default Notice dtoToEntity(NoticeDto dto) {
        Member member = Member.builder().mid(dto.getMid()).nickname(dto.getWriterNickname()).build();

        return Notice.builder()
                .nno(dto.getNno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }
}
