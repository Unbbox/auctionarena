package com.example.auctionarena.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.NoticeImage;

import jakarta.transaction.Transactional;

@SpringBootTest
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeImageRepository noticeImageRepository;

    @Transactional
    @Test
    public void noticeInsert() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().mid(i).nickname("USER" + i).build();

            Notice notice = Notice.builder()
                    .title("Title" + i)
                    .content("Content" + i)
                    .writer(member)
                    .build();
            noticeRepository.save(notice);

            // 이미지 샘플 추가
            int count = (int) (Math.random() * 5) + 1;

            for (int j = 0; j < count; j++) {
                NoticeImage nImage = NoticeImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .notice(notice)
                        .imgName("img" + j + ".jpg")
                        .build();
                noticeImageRepository.save(nImage);
            }
        });

        // 저장 완료 메시지 출력
        System.out.println("100개의 공지사항 및 이미지가 성공적으로 저장되었습니다.");
    }

    // @Test
    // public void noticeList() {
    // PageRequest pageRequest = PageRequest.of(0, 10);

    // Page<Object[]> list = noticeRepository.getList("t", "Title", pageRequest);

    // for (Object[] objects : list) {
    // System.out.println(Arrays.toString(objects));
    // }
    // }

    @Test
    public void noiceImageList() {
        PageRequestDto requestDto = PageRequestDto.builder()
                .type("t")
                .keyword("Title")
                .page(1)
                .size(10)
                .build();

        Page<Object[]> list = noticeImageRepository.getList(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("nno").descending()));

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Transactional
    @Test
    public void noticeRead() {
        // Notice notice = noticeRepository.findById(1L).get();
        // System.out.println(notice);
        // System.out.println(notice.getWriter());

        List<Object[]> resuList = noticeImageRepository.getRow(1L);

        for (Object[] objects : resuList) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Transactional
    @Test
    public void noticeRemove() {
        noticeRepository.deleteById(299L);
    }
}
