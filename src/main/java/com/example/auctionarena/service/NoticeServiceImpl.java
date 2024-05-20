package com.example.auctionarena.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.auctionarena.dto.NoticeDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.NoticeImage;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.NoticeImageRepository;
import com.example.auctionarena.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;

    private final NoticeImageRepository noticeImageRepository;

    @Override
    public PageResultDto<NoticeDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = noticeImageRepository.getList(requestDto.getType(),
                requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("nno").descending()));

        Function<Object[], NoticeDto> fn = (entity -> entityToDto((Notice) entity[0],
                (List<NoticeImage>) Arrays.asList((NoticeImage) entity[1]), (Member) entity[2]));

        return new PageResultDto<>(result, fn);

    }

    // @Override
    // public NoticeDto getRow(Long nno) {
    // // List<Object[]> row = noticeRepository.getRow(nno);
    // // return entityToDto((Notice) row[0], (Member) row[1]);

    // List<Object[]> result = noticeImageRepository.getRow(nno);

    // Notice notice = (Notice) result.get(0)[0];
    // Member member = (Member) result.get(0)[2];

    // List<NoticeImage> noticeImages = new ArrayList<>();
    // result.forEach(arr -> {
    // NoticeImage noticeImage = (NoticeImage) arr[1];
    // noticeImages.add(noticeImage);
    // });

    // return entityToDto(notice, noticeImages, member);
    // }

    @Override
    public NoticeDto getRow(Long nno) {
        List<Object[]> result = noticeImageRepository.getRow(nno);

        Notice notice = (Notice) result.get(0)[0]; // Notice 객체
        NoticeImage noticeImage = (NoticeImage) result.get(0)[1]; // NoticeImage 객체
        String nickname = (String) result.get(0)[2]; // String 타입의 nickname

        List<NoticeImage> noticeImages = new ArrayList<>();
        result.forEach(arr -> {
            NoticeImage image = (NoticeImage) arr[1]; // NoticeImage 객체
            noticeImages.add(image);
        });

        // Member 객체 생성
        Member member = Member.builder().nickname(nickname).build();

        return entityToDto(notice, noticeImages, member);
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
