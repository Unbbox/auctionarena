package com.example.auctionarena.service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.auctionarena.dto.NoticeDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.QNotice;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.NoticeRepository;
import com.querydsl.core.BooleanBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;

    @Override
    public PageResultDto<NoticeDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = noticeRepository.list(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("nno").descending()));

        Function<Object[], NoticeDto> fn = (entity -> entityToDto((Notice) entity[0], (Member) entity[1]));
        return new PageResultDto<>(result, fn);

    }

}
