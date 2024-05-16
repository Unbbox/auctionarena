package com.example.auctionarena.service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.auctionarena.dto.NoticeDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.NoticeRepository;

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

    @Override
    public NoticeDto getRow(Long nno) {
        Object[] row = noticeRepository.getRow(nno);
        return entityToDto((Notice) row[0], (Member) row[1]);
    }

    @Override
    public void modify(NoticeDto dto) {
        Notice entity = noticeRepository.findById(dto.getNno()).get();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        noticeRepository.save(entity);
    }

    @Override
    public void noticeRemove(Long nno) {
        noticeRepository.deleteById(nno);
    }

}
