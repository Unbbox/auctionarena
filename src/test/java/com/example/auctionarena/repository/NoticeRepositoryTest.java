package com.example.auctionarena.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
                        .nuuid(UUID.randomUUID().toString())
                        .notice(notice)
                        .nimgName("img" + j + ".jpg")
                        .build();
                noticeImageRepository.save(nImage);
            }
        });
    }

    @Test
    public void noticeList() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("nno").descending());

        Page<Object[]> list = noticeRepository.list("tcw", "Title", pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Transactional
    @Test
    public void noticeRead() {
        Notice notice = noticeRepository.findById(1L).get();
        System.out.println(notice);
        System.out.println(notice.getWriter());
    }

    @Transactional
    @Test
    public void noticeRemove() {
        noticeRepository.deleteById(299L);
    }
}
