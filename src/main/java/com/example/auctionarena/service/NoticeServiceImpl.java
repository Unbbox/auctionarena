package com.example.auctionarena.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

import jakarta.transaction.Transactional;
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

        Function<Object[], NoticeDto> fn = (entity -> entityToDto((Notice) entity[0], (Member) entity[1],
                (List<NoticeImage>) Arrays.asList((NoticeImage) entity[2])));

        return new PageResultDto<>(result, fn);

    }

    @Override
    public NoticeDto getRow(Long nno) {
        List<Object[]> result = noticeImageRepository.getRow(nno);

        Notice notice = (Notice) result.get(0)[0]; // Notice 객체
        String nickname = (String) result.get(0)[1]; // String 타입의 nickname
        NoticeImage noticeImage = (NoticeImage) result.get(0)[2]; // NoticeImage 객체

        List<NoticeImage> noticeImages = new ArrayList<>();
        result.forEach(arr -> {
            NoticeImage image = (NoticeImage) arr[2]; // NoticeImage 객체
            noticeImages.add(image);
        });

        // Member 객체 생성
        Member member = Member.builder().nickname(nickname).build();

        return entityToDto(notice, member, noticeImages);
    }

    @Transactional
    @Override
    public Long modify(NoticeDto dto) {
        Notice notice = noticeRepository.findById(dto.getNno()).get();
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());

        noticeRepository.save(notice);

        // Map<String, Object> entityMap = dtoToEntity(dto);

        // Notice notice = (Notice) entityMap.get("notice");
        noticeImageRepository.deleteByNotice(notice);

        List<NoticeImage> noticeImages = (List<NoticeImage>) dtoToEntity(dto).get("imgList");
        if (noticeImages != null) {
            // noticeImages.forEach(image -> noticeImageRepository.save(image));
            for (NoticeImage noticeImage : noticeImages) {
                noticeImage.setNotice(notice);
                noticeImageRepository.save(noticeImage);
            }
        }
        return notice.getNno();
    }

    @Transactional
    @Override
    public void noticeRemove(Long nno) {
        Notice notice = Notice.builder().nno(nno).build();

        noticeImageRepository.deleteByNotice(notice);
        noticeRepository.delete(notice);
    }

    @Override
    public Long noticeCreate(NoticeDto noticeDto) {
        Map<String, Object> entityMap = dtoToEntity(noticeDto);

        Notice notice = (Notice) entityMap.get("notice");

        // noticeRepository.save(notice);

        // List<NoticeImage> noticeImages = (List<NoticeImage>)
        // entityMap.get("imgList");
        // noticeImages.forEach(image -> noticeImageRepository.save(image));

        // 작성자 동일 시 (확인용)
        if (notice == null) {
            notice = Notice.builder()
                    .title(noticeDto.getTitle())
                    .content(noticeDto.getContent())
                    .build();
        }

        String writerNickname = noticeDto.getWriterNickname();
        Member writer = memberRepository.findByNickname(writerNickname);

        if (writer != null && writer.getNickname().equals(writerNickname)) {
            notice.setWriter(writer);
            noticeRepository.save(notice);

            List<NoticeImage> noticeImages = (List<NoticeImage>) entityMap.get("imgList");
            if (noticeImages != null) {
                // noticeImages.forEach(image -> noticeImageRepository.save(image));
                for (NoticeImage noticeImage : noticeImages) {
                    noticeImage.setNotice(notice);
                    noticeImageRepository.save(noticeImage);
                }
            }

            return notice.getNno();
        } else {
            throw new IllegalArgumentException("작성자 정보를 찾을 수 없습니다.");
        }

    }

}
