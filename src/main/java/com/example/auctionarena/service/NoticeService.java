package com.example.auctionarena.service;

import com.example.auctionarena.dto.NoticeDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Notice;

public interface NoticeService {

    PageResultDto<NoticeDto, Object[]> getList(PageRequestDto requestDto);

    public default NoticeDto entityToDto(Notice notice, Member member) {
        return NoticeDto.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .mid(member.getMid())
                .writerNickname(member.getNickname())
                .createdDate(notice.getCreatedDate())
                .lastModifiedDate(notice.getLastModifiedDate())
                .build();
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
