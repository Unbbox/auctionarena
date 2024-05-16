package com.example.auctionarena.repository;

import java.util.Arrays;
import java.util.List;
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

import jakarta.transaction.Transactional;

@SpringBootTest
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void noticeInsert() {
        LongStream.rangeClosed(101, 199).forEach(i -> {
            Member member = Member.builder().mid(i).nickname("USER" + i).build();

            Notice notice = Notice.builder()
                    .title("Title" + i)
                    .content("Content" + i)
                    .writer(member)
                    .build();
            noticeRepository.save(notice);
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
